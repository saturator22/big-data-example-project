import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window
object DSApproach {

  def main(args: Array[String]) {

    //Creating SparkSession needed by Datasets/DataFrames
    val spark = SparkSession
      .builder()
      .appName("Top Values DS")
      .config("spark.master", "local")
      .getOrCreate()

    //Importing spark implicits._ to use inferring by reflection
    //which helps with deserialization
    import spark.implicits._

    //Defining properties used by jdbc
    val prop = new java.util.Properties
    prop.setProperty("driver", "org.postgresql.Driver")
    prop.setProperty("user", "cloudera")
    prop.setProperty("password", "cloudera")

    //Variables needed to connect with proper database and it's tables
    val url = "jdbc:postgresql://localhost/cloudera"
    val top10catTable = "top10categoriesspark"
    val top10productsInCat = "top10productsspark"
    val top10spendingsspark = "top10spendingsspark"

    //Reading files from hdfs to eventsFiles Dataset variable
    val eventsFiles = spark.read
      .option("inferSchema", "true")
      .csv("hdfs://localhost/user/cloudera/flumetest/events/2019/07/*/*.*")
      .toDF("product_name", "product_price", "purchase_date", "product_category", "ip_address")
      .as[Event]
    eventsFiles.cache()

    //Getting top categories as DataFrames
    val topCategories = eventsFiles.groupBy("product_category")
      .agg(count("product_category").as("count"))
      .orderBy(desc("count"))

    //Writing 10 rows from top10categories into database
    topCategories.limit(10).write.mode("overwrite").jdbc(url, top10catTable, prop)

    //Creating dataframe grouped by 2 columns and counted by occurences
    val productsPerCategory = eventsFiles.groupBy($"product_category", $"product_name")
      .agg(count("product_name").alias("product_count"))

    //Partitioning for window function used in top10perCategory
    val partitionedByCategory = Window.partitionBy("product_category")
      .orderBy(desc("product_count"))

    //Getting top 10 products by category
    val top10perCategory =
      productsPerCategory.withColumn("row_num", row_number.over(partitionedByCategory))
      .where($"row_num" <= 10)

    //Writing results into database
    top10perCategory.write.mode("overwrite").jdbc(url, top10productsInCat, prop)

    //Reading files from hdfs to geoData Dataset variable
    val geoData = spark.read
      .option("inferSchema", "true")
      .csv("hdfs://localhost/user/hive/warehouse/marketsalesdb.db/countryspendings/*")
      .toDF("ip_address", "geoname_id", "country_name", "product_price").as[CountryExpense]

    //Getting top n country spendings
    val topCountrySpendings = geoData.groupBy("country_name")
      .agg(sum("product_price").as("spendings"))
      .orderBy(desc("spendings"))

    //Writing results into database
    topCountrySpendings.limit(10).write.mode("overwrite").jdbc(url, top10spendingsspark, prop)
  }
  //Event class as a Encoder for deserialization
  case class Event(product_name: String, product_price: Double, purchase_date: String, product_category: String,
                   ip_address: String)

  //CountryExpense class as a Encoder for deserialization
  case class CountryExpense(ip_address: String, geoname_id: Int, country_name: String, product_price: Int)
}

import org.apache.spark.{SparkConf, SparkContext}

object RDDApproach {

  def main(args: Array[String]) {

    //Configuration of Spark Context
    val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName("Top Values RDD")
    val sc = new SparkContext(conf)

    //Creating RDD based on textfile
    val eventsFiles = sc.textFile("hdfs://localhost/user/cloudera/flumetest/events/2019/07/*/*.*")

    //Getting top 10 categories from RDD
    val top10categories = eventsFiles.map(line => line.split(","))
      .map(words => (words(3), 1))
      .reduceByKey(_+_)
      .top(10)(Ordering.by(_._2))
      .foreach(println)

    //Getting top 10 products for category from RDD
    val top10productsForCategory = eventsFiles.map(line => line.split(","))
      .map(words => ((words(3), words(0)), 1))
      .reduceByKey(_+_)
      .groupBy(_._1._1)
      .mapValues(r => r.toList.sortWith(_._2 > _._2).take(10))
      .foreach(_._2.map(v => (v._1._1, v._1._2, v._2)).foreach(println))

    //Creating RDD based on a textfile
    val geoData = sc.textFile("hdfs://localhost/user/hive/warehouse/marketsalesdb.db/countryspendings/*")


    //Getting top 10 countries (by spendings) from RDD
    val top10countrySpendings = geoData.map(_.split(","))
      .map(words => (words(2), words(3).toInt))
      .reduceByKey(_+_)
      .top(10)(Ordering.by(_._2))
      .foreach(println)
  }
}

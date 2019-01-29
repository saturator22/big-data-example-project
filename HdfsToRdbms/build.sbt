name := "HdfsToRdbms"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.0", 
  "org.apache.spark" %% "spark-sql" % "2.4.0",
  "org.postgresql" % "postgresql" % "42.2.5"
)
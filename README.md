# Big Data Simple Workflow example

- **Event** (log) producer implemented in Java,<br>
  cofigured to send data via TCP socket.

- **Flume agent configuration** attached,<br>
  source: **Netcat**<br>
  channel: **Memory**<br>
  sink: **Data stream into Textfile**<br>
  Configured to store data on hdfs _'partitioned'_ by date.
  
- **Hive QL** queries with some simple data processing<br>
  with scores stored in Hive tables.
  
  Data for countries can be found here:<br>
  https://geolite.maxmind.com/download/geoip/database/GeoLite2-Country-CSV.zip
  
- **Scoop** commands for transfering HiveQL results into<br>
  PostgreSQL RDBMS.
  
- **Spark approach** written in _Scala_, consisting<br>
  same processing like in HiveQL, but using different Spark API's<br>
  - ***RDD (Resilient Distributed Datased)***
  - ***Dataset/Dataframe*** with loading data into RDBMS.

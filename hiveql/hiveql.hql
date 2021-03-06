#TABLE CREATION & PARTITIONING

CREATE EXTERNAL TABLE IF NOT EXISTS marketsalesdb.productlog
    (
    product_name STRING,
    product_price INT,
    purchase_date STRING,
    product_category STRING,
    ip_address STRING
    )
    COMMENT 'External table for product logs'
    PARTITIONED BY (date STRING)
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
    LOCATION '/user/cloudera/flumetest/events';

ALTER TABLE productlog ADD PARTITION(date='2019-07-01')
    LOCATION '/user/cloudera/flumetest/events/2019/07/01';
ALTER TABLE productlog ADD PARTITION(date='2019-07-02')
    LOCATION '/user/cloudera/flumetest/events/2019/07/02';
ALTER TABLE productlog ADD PARTITION(date='2019-07-03')
    LOCATION '/user/cloudera/flumetest/events/2019/07/03';
ALTER TABLE productlog ADD PARTITION(date='2019-07-04')
    LOCATION '/user/cloudera/flumetest/events/2019/07/04';
ALTER TABLE productlog ADD PARTITION(date='2019-07-05')
    LOCATION '/user/cloudera/flumetest/events/2019/07/05';
ALTER TABLE productlog ADD PARTITION(date='2019-07-06')
    LOCATION '/user/cloudera/flumetest/events/2019/07/06';
ALTER TABLE productlog ADD PARTITION(date='2019-07-07')
    LOCATION '/user/cloudera/flumetest/events/2019/07/07';

#SELECT 10 TOP FREQUENTLY PURCHASED CATEGORIES
CREATE TABLE top10categories
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ',' as
SELECT product_category, count(*) as count FROM productlog
GROUP BY (product_category)
ORDER BY (count) DESC
LIMIT 10;

#SELECT 10 TOP FREQUENTLY PURCHASED PRODUCT IN EACH CATEGORY
CREATE TABLE top10products_for_category
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','  as
SELECT product_category, product_name, num_of_products, row_num
    FROM (SELECT product_category, product_name, count(*) as num_of_products,
    ROW_NUMBER() OVER (PARTITION BY product_category order by count(*) desc) as row_num
    FROM productlog
    GROUP BY product_category, product_name) T
    WHERE row_num <= 10; 

#CREATING TABLES FOR GEODATA
CREATE TABLE IF NOT EXISTS country_ip
    (
    network STRING,
    geoname_id INT,
    registered_country_geoname_id INT,
    represented_country_geoname_id INT,
    is_anonymous_proxy INT,
    is_satellite_provider INT
    )
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
    STORED AS TEXTFILE
    TBLPROPERTIES("skip.header.line.count"="1");	


LOAD DATA INPATH '/user/hive/csv_data/GeoLite2-Country-Blocks-IPv4.csv' OVERWRITE INTO TABLE country_ip;

CREATE TABLE IF NOT EXISTS country_locations
    (
    geoname_id INT,
    locale_code STRING,
    continent_code STRING,
    continent_name STRING,
    country_iso_code STRING,
    country_name STRING,
    is_in_european_union INT
    )
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
    STORED AS TEXTFILE
    TBLPROPERTIES("skip.header.line.count"="1");

LOAD DATA INPATH '/user/hive/csv_data/GeoLite2-Country-Locations-en.csv' OVERWRITE INTO TABLE country_locations;

#JOIN countryip, countrylocations and product log as a TABLE

CREATE TABLE country_spendings
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ',' AS
SELECT productlog.ip_address, countryip.geoname_id, countrylocations.country_name, productlog.product_price FROM country_ip
    INNER JOIN productlog
    ON regexp_replace(network, "(/[0-9]{2})", "") = productlog.ip_address
    INNER JOIN country_locations
    ON countryip.geoname_id = countrylocations.geoname_id;

#GET 10 COUNTRIES WITH MOST SPENDINGS
CREATE TABLE top10spendings
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','  as 
SELECT country_name, sum(product_price) as total FROM country_spendings
GROUP BY country_name
ORDER BY total DESC
LIMIT 10;


# SAMPLE DATA for countryip WITH IP's matching geodataskoruszkasa

INSERT INTO TABLE productlog partition(date='2019-07-07')
    VALUES('product2', 12331, '2019-07-07 16:23:48', 'Cars', '221.128.66.252'),
    ('product3', 12331, '2019-07-07 16:23:48', 'Cars', '221.120.112.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '221.120.112.0'),
    ('product2', 12331, '2019-07-07 16:23:48', 'Cars', '221.120.112.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '221.120.112.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '221.120.112.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '221.120.112.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '221.120.112.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '221.120.112.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '221.120.112.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '5.8.47.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '5.8.47.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '5.8.47.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '5.8.47.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '5.8.47.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '5.8.47.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '5.8.47.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '5.8.47.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '5.8.47.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '5.8.47.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '154.94.32.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '154.94.65.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '154.95.207.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '192.3.16.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '192.3.96.128'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '192.3.246.156'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '192.8.32.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '192.12.118.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '192.12.118.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '192.25.46.0'),
    ('product1', 12331, '2019-07-07 16:23:48', 'Cars', '192.30.124.0');


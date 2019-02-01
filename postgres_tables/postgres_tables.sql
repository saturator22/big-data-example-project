CREATE TABLE top10categories
    (
    product_category TEXT,
    count INT);

CREATE TABLE top10productsforcategory
    (
    product_category TEXT,
    product_name TEXT,
    num_of_products INT,
    row_pos INT);

CREATE TABLE top10spendings
    (
    country_name TEXT,
    total INT);

CREATE TABLE top10categoriesspark
    (
    product_category TEXT,
    count INT);

CREATE TABLE top10productsspark
    (
    product_category TEXT,
    product_name TEXT,
    product_count INT,
    row_num INT);

CREATE TABLE top10spendingsspark
    (
    country_name TEXT,
    spendings INT);

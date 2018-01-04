CREATE TABLE flights AS
  SELECT "SFO" AS departure, "LAX" AS arrival, 97 AS price UNION
  SELECT "SFO"             , "AUH"           , 848         UNION
  SELECT "LAX"             , "SLC"           , 115         UNION
  SELECT "SFO"             , "PDX"           , 192         UNION
  SELECT "AUH"             , "SEA"           , 932         UNION
  SELECT "SLC"             , "PDX"           , 79          UNION
  SELECT "SFO"             , "LAS"           , 40          UNION
  SELECT "SLC"             , "LAX"           , 117         UNION
  SELECT "SEA"             , "PDX"           , 32          UNION
  SELECT "SLC"             , "SEA"           , 42          UNION
  SELECT "SFO"             , "SLC"           , 97          UNION
  SELECT "LAS"             , "SLC"           , 50          UNION
  SELECT "LAX"             , "PDX"           , 89               ;

CREATE TABLE main_course AS
  SELECT "turkey" AS meat, "cranberries" AS side, 2000 AS calories UNION
  SELECT "turducken"     , "potatoes"           , 4000             UNION
  SELECT "tofurky"       , "cranberries"        , 1000             UNION
  SELECT "tofurky"       , "stuffing"           , 1000             UNION
  SELECT "tofurky"       , "yams"               , 1000             UNION
  SELECT "turducken"     , "turducken"          , 9000             UNION
  SELECT "turkey"        , "potatoes"           , 2000             UNION
  SELECT "turkey"        , "bread"              , 1500             UNION
  SELECT "tofurky"       , "soup"               , 1200             UNION
  SELECT "chicken"       , "cranberries"        , 2500             UNION
  SELECT "turducken"     , "butter"             , 10000            UNION
  SELECT "turducken"     , "more_butter"        , 15000                 ;

CREATE TABLE pies AS
  SELECT "pumpkin" AS pie, 500 AS calories UNION
  SELECT "apple"         , 400             UNION
  SELECT "chocolate"     , 600             UNION
  SELECT "cherry"        , 550                  ;

CREATE TABLE products AS
  SELECT "phone" AS category, "uPhone" AS name, 99.99 AS MSRP, 4.5 AS rating UNION
  SELECT "phone"            , "rPhone"        , 79.99        , 3             UNION
  SELECT "phone"            , "qPhone"        , 89.99        , 4             UNION
  SELECT "games"            , "GameStation"   , 299.99       , 3             UNION
  SELECT "games"            , "QBox"          , 399.99       , 3.5           UNION
  SELECT "computer"         , "iBook"         , 112.99       , 4             UNION
  SELECT "computer"         , "wBook"         , 114.29       , 4.4           UNION
  SELECT "computer"         , "kBook"         , 99.99        , 3.8                ;

CREATE TABLE inventory AS
  SELECT "Hallmart" AS store, "uPhone" AS item, 99.99 AS price UNION
  SELECT "Targive"          , "uPhone"        , 100.99         UNION
  SELECT "RestBuy"          , "uPhone"        , 89.99          UNION

  SELECT "Hallmart"         , "rPhone"        , 69.99          UNION
  SELECT "Targive"          , "rPhone"        , 79.99          UNION
  SELECT "RestBuy"          , "rPhone"        , 75.99          UNION

  SELECT "Hallmart"         , "qPhone"        , 85.99          UNION
  SELECT "Targive"          , "qPhone"        , 88.98          UNION
  SELECT "RestBuy"          , "qPhone"        , 87.98          UNION

  SELECT "Hallmart"         , "GameStation"   , 298.98         UNION
  SELECT "Targive"          , "GameStation"   , 300.98         UNION
  SELECT "RestBuy"          , "GameStation"   , 310.99         UNION

  SELECT "Hallmart"         , "QBox"          , 399.99         UNION
  SELECT "Targive"          , "QBox"          , 390.98         UNION
  SELECT "RestBuy"          , "QBox"          , 410.98         UNION

  SELECT "Hallmart"         , "iBook"         , 111.99         UNION
  SELECT "Targive"          , "iBook"         , 110.99         UNION
  SELECT "RestBuy"          , "iBook"         , 112.99         UNION

  SELECT "Hallmart"         , "wBook"         , 117.29         UNION
  SELECT "Targive"          , "wBook"         , 119.29         UNION
  SELECT "RestBuy"          , "wBook"         , 114.29         UNION

  SELECT "Hallmart"         , "kBook"         , 95.99          UNION
  SELECT "Targive"          , "kBook"         , 96.99          UNION
  SELECT "RestBuy"          , "kBook"         , 94.99               ;

CREATE TABLE stores AS
  SELECT "Hallmart" AS store, "50 Lawton Way" AS address, 25 AS Mbs UNION
  SELECT "Targive"          , "2 Red Circle Way"        , 40        UNION
  SELECT "RestBuy"          , "1 Kiosk Ave"             , 30             ;

-------------------------------------------------------------
-- PLEASE DO NOT CHANGE ANY SQL STATEMENTS ABOVE THIS LINE --
------------------------------------------------------------- 

CREATE TABLE schedule AS
  WITH recur(content, total_price, flight_count, ending) AS (
  	SELECT departure, price, 1, arrival FROM flights WHERE departure = "SFO"	 UNION
  	--ALWAYS CHECK FOR THE BASE CASE AND SEE HOW YOU ARE GOING TO GET DESIRED OUPUT FROM IT IN RECURSION
  	--With recursion you usually keep aux columns to keep track of when to stop recursion
  	SELECT content || ", " || ending, total_price + price, flight_count + 1, arrival
  	FROM recur, flights
  	WHERE ending = departure AND flight_count < 2 --Remeber that you always have some sort of recursion stop!! in this case its flight_count
  )
  SELECT content || ", " || ending, total_price FROM recur WHERE ending = "PDX" ORDER BY total_price ASC;

CREATE TABLE number_of_options AS
  SELECT count(DISTINCT meat) FROM main_course;

CREATE TABLE calories AS
  SELECT count(*) FROM main_course AS t1, pies AS t2
  WHERE t1.calories + t2.calories < 2500;
--if we want something to be equal to 2500 IN the table make sure to do only < 2500 
--Because it does the check BEFORE it adds the row. Doing <= will allow things over 2500 to be added to
--the count

CREATE TABLE healthiest_meats AS
  SELECT t1.meat, min(t2.calories + t1.calories) -- IT aggregates to create the row then it tags on the 'meat' associated with the "min"- aggregated row
  FROM main_course AS t1, pies AS t2
  GROUP BY t1.meat 
  	HAVING max(t2.calories + t1.calories) < 3000;
--You can group by an aggregation, then you can perform another aggregation on that group.
--Recall that aggregation goes first when determining which row gets included
--Recall that group by executes before determinig information from rows

CREATE TABLE average_prices AS
  SELECT category, avg(MSRP) FROM products GROUP BY category;
--Gets groupped first then aggregated on

CREATE TABLE lowest_prices AS
  SELECT store, item, price FROM inventory
  GROUP BY item
  	HAVING min(price); 
--Having = aggregation over the GROUP, essentially decides what is gonna be displayed for that group

CREATE TABLE shopping_list AS
  WITH rating_weight(product) AS (
  	SELECT name FROM products
  	GROUP BY category
  		HAVING min(MSRP / rating)
  )
  SELECT product, store FROM rating_weight, lowest_prices WHERE product = item;
--ALWAYS look at previous tables to check if you can use that information in getting desired output

CREATE TABLE total_bandwidth AS
  SELECT SUM(t1.Mbs) FROM stores AS t1, shopping_list AS t2
  WHERE t1.store = t2.store;
--Joins are often something that you want to do when cross checking for something 

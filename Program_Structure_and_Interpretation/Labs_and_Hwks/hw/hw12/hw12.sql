CREATE TABLE parents AS
  SELECT "abraham" AS parent, "barack" AS child UNION
  SELECT "abraham"          , "clinton"         UNION
  SELECT "delano"           , "herbert"         UNION
  SELECT "fillmore"         , "abraham"         UNION
  SELECT "fillmore"         , "delano"          UNION
  SELECT "fillmore"         , "grover"          UNION
  SELECT "eisenhower"       , "fillmore";

CREATE TABLE dogs AS
  SELECT "abraham" AS name, "long" AS fur, 26 AS height UNION
  SELECT "barack"         , "short"      , 52           UNION
  SELECT "clinton"        , "long"       , 47           UNION
  SELECT "delano"         , "long"       , 46           UNION
  SELECT "eisenhower"     , "short"      , 35           UNION
  SELECT "fillmore"       , "curly"      , 32           UNION
  SELECT "grover"         , "short"      , 28           UNION
  SELECT "herbert"        , "curly"      , 31;

CREATE TABLE sizes AS
  SELECT "toy" AS size, 24 AS min, 28 AS max UNION
  SELECT "mini"       , 28       , 35        UNION
  SELECT "medium"     , 35       , 45        UNION
  SELECT "standard"   , 45       , 60;

-------------------------------------------------------------
-- PLEASE DO NOT CHANGE ANY SQL STATEMENTS ABOVE THIS LINE --
-------------------------------------------------------------

-- The size of each dog
CREATE TABLE size_of_dogs AS
  SELECT t1.name, t2.size AS size FROM dogs AS t1, sizes AS t2 WHERE t2.min < t1.height AND t2.max >= t1.height;

-- All dogs with parents ordered by decreasing height of their parent
CREATE TABLE by_height AS
  SELECT t1.child FROM parents AS t1, dogs AS t2 WHERE t1.parent = t2.name ORDER BY t2.height DESC;
  --ALWAYS CHECK AND MAKE SURE THAT THE OUTPUT MATCHES!!! ALWAYS CHECK ROW OUTPUT

-- Sentences about siblings that are the same size
CREATE TABLE sentences AS
  WITH siblings AS 
  	(SELECT t1.child AS child1, t2.child AS child2 
  		FROM parents AS t1, parents AS t2 
  		WHERE t1.parent = t2.parent AND t1.child < t2.child)
  		--'<' keeps name in alphabetical order

  SELECT child1 || " and " || child2 || " are " || t1.size || " siblings"
  	FROM siblings, size_of_dogs AS t1, size_of_dogs as t2
  	WHERE child1 = t1.name AND child2 = t2.name AND t1.size = t2.size;
  	--Using a join here compares *all* possibilities between 3 tables and WHERE filters them out!

-- Ways to stack 4 dogs to a height of at least 170, ordered by total height
CREATE TABLE stacks AS
  WITH recur (content, prev_height, height, num_dogs) AS (
  	SELECT name, height, height, 1 FROM dogs 			UNION
  	SELECT t1.content || ", " || t2.name,
  		   t2.height,
  		   t1.height + t2.height,
  		   t1.num_dogs + 1
  	FROM recur AS t1, dogs AS t2
  	WHERE num_dogs < 4 AND t1.height < 170 AND t1.prev_height < t2.height
  	--RECAL THAT THE CHECK IS DONE BEFORE YOU DO THE NEXT ROW, so created conditions that generally
  	--end one row before desired so for a row of up to 4 dogs, num_dogs < 4 is the check.
  	--Think of this as do the check first before creating the row 
  )
  SELECT content, height FROM recur WHERE height >= 170 AND num_dogs = 4 ORDER BY height ASC;

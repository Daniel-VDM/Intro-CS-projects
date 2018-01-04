.read sp17data.sql
.read fa17data.sql

CREATE TABLE obedience AS
  SELECT seven, denero, hilfinger FROM students;

CREATE TABLE smallest_int AS
  SELECT time, smallest FROM students WHERE smallest > 18 ORDER BY smallest LIMIT 20 ;

CREATE TABLE greatstudents AS
  SELECT t1.date, t1.color, t1.pet, t1.number AS this_semesters_number, t2.number AS last_semesters_number 
    FROM students AS t1, sp17students AS t2 
    WHERE t1.date = t2.date AND t1.color = t2.color AND t1.pet = t2.pet;

CREATE TABLE sevens AS
  SELECT t1.seven 
    FROM students AS t1, checkboxes AS t2 
    WHERE t1.number = 7 AND t2."7" = "True" AND t1.time = t2.time;

CREATE TABLE matchmaker AS
  SELECT t1.pet, t1.song, t1.color, t2.color 
    FROM students AS t1, students AS t2 
    WHERE t1.pet = t2.pet AND t1.song = t2.song AND t1.time < t2.time;

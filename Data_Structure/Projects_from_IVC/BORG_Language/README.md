# BORG Interpreter Program
This was the last project for Data Structures taken at Irvine Valley College (Spring 2017)

************************************************
The entire program was written by me.
************************************************

Short Description of the project 
-------------------------------------------------------------
The goal of the project was to create an interpreter for my own made up language, nicknamed BORG. BORG had to support basic features that include: variable assignment, global / local scoping, comments, unary and binary operator (++, --, + ,- , \*, /, %, ^), and error catching. 

Variables and their scope was stored in a custom Hashtable (written by hand instead of using the java Hashtable)

*Note: this program was created before I attended UC Berkeley so it's probably not done in an efficient or robust manner.*

**Here is a sample code of BORG**
~~~
COM This is my BORG program
COM It will do some math and print the answer
START
   VAR ANSWER
   PRINT ANSWER
   START
      VAR ARG1 = 10
      VAR ARG2 = 3
      PRINT ARG1
      PRINT ARG2
      PRINT ARG1 ^ ARG2
      ANSWER = ARG1 - ARG2
      PRINT ANSWER
      ANSWER ++
      PRINT ANSWER
   FINISH
   PRINT ARG1
   PRINT ANSWER / ANSWER
   PRINT ANSWER
FINISH
~~~
*Contents of ProgramTest.txt*

##### IF YOU WANT TO RUN THE PROGRAM #####

**I included a folder (Titled "Runnable") with all the information and files needed to run it. *Please read the readme.***






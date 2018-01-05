# -Project-HashTest
This was part 3 of project 2 for Data Structures (CS47B)

************************************************
Only a small portion of the code was written by me. (I noted all the parts that I added as comments)
************************************************

Short Description of the project (Copied from the prompt)
-------------------------------------------------------------
Two CS 47B students have a jug filled with eight liters of Jolt Cola that they wish to divide evenly between them. They have two empty jugs with capacities of five and three liters respectively. These jugs are unmarked and no other measuring device is available. How can they accomplish the equal division?

The file JugSolver.java contains part of a program to solve this problem. The program recursively searches to find a sequence of steps (each step pours one jug into another) that produces the desired amount. The program has a flaw, namely that it doesn't keep track of configurations that it has seen before; infinite recursion results.

Without changing any of the existing code (except perhaps to change the value of the DEBUGGING variable), add statements that fix the program. In particular, you are to use a java.util.Hashtable object to keep track of configurations already seen. Test your completed program on the arguments

>8 5 3 8 0 4

which correspond to the problem stated above. Indicate, on the program listing you bring to the Self-Paced Center for grading, what you added to the framework code.
************************************************

##### PERSONAL NOTE #####
  
I implemented the hashtable into the program inorder to make it work. Relatively simple stuff.

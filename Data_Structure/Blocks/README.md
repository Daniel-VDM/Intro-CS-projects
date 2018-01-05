# -Project-Blocks
This was the Major project for Data Structures (CS47B)

************************************************
The main program was written from scratch.
************************************************

Short Description of the project (Copied from the prompt)
-------------------------------------------------------------
Those of you who spend much time in toy stores may be familiar with "sliding-block" puzzles. They consist of a number of rectangular blocks in a tray; the problem is to slide the pieces without lifting any out of the tray, until achieving a certain configuration. An example (from Winning Ways, E.R. Berlekamp et al., Academic Press, 1982) is shown in the diagram on the right.

This project is to write a program that generates a solution to such a puzzle. The first part is a design, the second an implementation of the design, and the third is an optimization and writeup.


**For more details on the project and its specification take a look at the prompt *(http://www-inst.eecs.berkeley.edu/~selfpace/47b/dp/blocks-project/)*. Just incase it has been changed, I have included the webfiles for the Fall 2017 version of the project. Its in the folder titled "Project Prompt (Web Files)"**

##### PERSONAL NOTE #####
  
This was my first major program of which I had to design from the ground up. It incorporates a lot of different ideas in computer science with an emphasis on Data Structures.

*The main logic of the program lies in the Solve class. Here is a brief overview of what the program does:*  
>The program reads in the initial board layout (creating an initial 'board' object) and reads in the desired goal state. Then it performs a Breadth First Search on versions of the initial board until it finds a solution. The Solver has 2 main data structures: A Linked List of 'boards' that are to be searched (used for breadth first search), and a Hashset of seen 'boards'. The way the Solver finds a solution is by dequeuing a board from the linked list, then it generates new boards from all possible moves of said dequeued board. All possible moves are: Moving a block up, down, left or right. The Solver then checks if any of the new boards have been seen before by checking if they are in the 'seen' Hash Set. If it they are not, the Solver adds them to the seen Hash Set. Then it checks any of the new boards matches the goal state. If one of them do, the Solver exits the BFS and prints all the board states leading up to the solution. If none of the new boards match the goal state, it dequeues the next board from the Linked List and repeats the process. *This is just a brief overview of the program. The actual implementation is more involved. Reference my code, Javadoc or write up for more details*

*The program specification also required that I implemented useful debugging features of which I included the following:*
>- Printing the number of moves from start to finish
>- Only Checking if the board can be solved (so it does NOT output the board states leading up to the solution)
>- Print before and after board states ONLY
>- Prints number of boards that have been checked
>- Prints how long it takes to solve the puzzle
>- Prints how long it takes to Hash a board

Lastly, I had to do a writeup of the project where I covered what parts I could improve on as well as how I implemented certain parts of the program. I would recommend reading that since it describes the program in depth as well as my thought process. It's the file: "47B Blocks Write Up.pdf" in the main Blocks directory.




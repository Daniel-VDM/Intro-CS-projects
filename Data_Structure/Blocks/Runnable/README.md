# READ THIS PLEASE

If you want to run my code for yourself, execute the the Solver.class file via the terminal.

Reference the project prompt for full specification of the program.

**An example would be something like this: "java Solver c52 50.52.goal" executed when in a directory with all of the contents of this folder**

**An example of verifying that the program works as desired is by piping my program through a checker with the following command: "java Solver c52 50.52.goal | java Checker c52 50.52.goal"**

*Note that the Checker class was provided by the course.*

__More puzzles can be found in the puzzle folder, found in the previous directory.__

************************************************

##### WHAT THE OUTPUT MEANS #####

*For the default output, it prints the moves required from top to bottom. So the first move in the solution is at the top of the output. Furthermore, each line has 4 numbers in this format: `x1 y1 x2 y2`. This means that the block at position **x1,y1** must be moved to position **x2,y2**. For example:*
~~~
1 1 0 1		// move block at (1,1) to (0,1) - First Step
3 1 2 1		// move block at (3,1) to (2,1)
4 1 3 1		// move block at (4,1) to (3,1)
4 2 3 2		// move block at (4,2) to (3,2)
4 3 4 2		// move block at (4,3) to (4,2) - Last Step
~~~


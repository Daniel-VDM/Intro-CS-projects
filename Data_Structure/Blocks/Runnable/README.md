# READ THIS PLEASE

If you want to run my code for yourself, execute the the Solver.Solver.class file via the terminal.

Reference the project prompt for full specification of the program and starting arguments.

**An example would be something like this: `java Solver.Solver c52 50.52.goal` executed when in a directory with all of the contents of this folder**

**An example of verifying that the program works as desired is by piping my program through a checker with the following command: `java Solver.Solver c52 50.52.goal | java Checker c52 50.52.goal`**

*Note that the Checker class was provided by the course.*

__More puzzles can be found in the puzzle folder (found in the previous directory).__

************************************************

##### Quick Discription #####

The 2 required arguments are the initial board and the goal board. The program takes the initial board and solves it to get to the desired goal board. In doing so, it will output all the necessary steps to transform the inital board into the goal board. Below are descriptions of how a board is represented in a text file and what the output of the program mean.

************************************************

##### BOARD REPRESENTATION #####

A inital board is represented in a text file where the first line has 2 arguments, `BoardHeight BoardWidth` then all remaning lines represent the blocks of said board. The remaining lines have 4 arguments, `BlockHeight BlockWidth BlockXPos BlockYPos`. As noted in the program specification, the `(X, Y)` position of a block is the **top left** of the described block.

A goal board is almost identical to the inital board. However, it *does not* have the arguments for the board size, so it does not have the first line of an initial board. It just has 4 arguments per line to describe the blocks in the desired goal configuration.


![Pic](SampleConfig.gif?raw=true)

So for the picture above, the text file representing the inital board would be:

~~~
5 4
1 1 3 1
1 1 4 1
1 1 3 2
~~~

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
*Note that the x,y coordinates reference the top left of the blocks. The program knows the size of the block, it just uses the top left as an anchor.*

package Solver;

import java.util.*;

/**
 * This object represents a Solver.Board and has method to set paths,
 * generate moves, and check for win condition
 * 
 * @author Daniel Van Der Maden
 *
 */

public class Board
{
    private HashSet<Block> layout;
    private HashSet<Location> free;

    //Tools used for printing a board
    private Board parent = null;
    private Block movedBlockRef = null;
    private Location movedDirRef = null;

    //Overloaded Constructors
    Board(HashSet<Block> layout){
        this.layout = layout;
    }

    Board(HashSet<Block> layout, HashSet<Location> free){
        this.layout = layout;
        this.free = free;
    }

    //Accessors
    public HashSet<Block> getLayout()    { return layout; }
    public HashSet<Location> getFree()   { return free; }
    public Board getParent()             { return parent; }
    public Block getMovedBlockRef()      { return movedBlockRef; }
    public Location getMovedDirRef()     { return movedDirRef; }

    /**
     * Sets the path pointers so that we can print the valid path once a
     * solution is found
     *  
     * @param parent the board that generated the current board
     * @param movedBlockRef the Solver.Block that was moved to get to this
     * @param movedDirRef the direction taken to get to this
     */
    public void setPath(Board parent, Block movedBlockRef, Location movedDirRef){
        this.parent = parent;
        this.movedBlockRef = movedBlockRef;
        this.movedDirRef = movedDirRef;
    }

    /**
     * Move a block in a desired direction
     * 
     * @param b the block you are trying to move
     * @param dir the Solver.Location 'direction' that you are moving
     * @return A board with the block moved or null if it was an invalid move
     */
    public Board move(Block b, Location dir){
        Board newBoard = null;
        Location[] moves = b.getFreeMoves(dir);
        Block movedBlock;
        if(b.movable(moves, this.free)){
            //Copy the board
            newBoard = this.copy();
            movedBlock = b.copy();
        
            //Make a move on board
            newBoard.layout.remove(b);
            movedBlock.move(dir, moves, newBoard.free);
            newBoard.layout.add(movedBlock);
        } return newBoard;      
    }

    /**
    * Check if the this board matches the argument (used for goal check) 
    *
    * @param goal the target you are checking for
    * @return T/F if it matches or not
    */
    public boolean checkGoal(Board goal){
        for(Block b: goal.getLayout())
        if(!layout.contains(b))
            return false;
        return true;
    }

    //Assumes the argument is always a board object
    public boolean equals (Object o) {
        if (o == null)
            return false;
        Board other = (Board) o;
        for (Block b: this.layout)
            if (!other.layout.contains(b))
                return false;
        return true;
    }

    public int hashCode(){
        int code = 1;
        for (Block b : this.layout)
            code *= b.hashCode();
        return code;
    }

    public String toString(){
        StringBuilder s = new StringBuilder("\n");
        for (Block b:this.layout)
            s.append(b.toString()).append("\n");
        return s.toString();
    }

    /**
     * @return A new instance of a board that is the same as current
     */
    public Board copy(){
        HashSet<Block> newLayout = new HashSet<>(layout);
        HashSet<Location> newFree = new HashSet<>(free);
        return new Board(newLayout, newFree);
    }



    //Main test file for this class
    public static void main(String[] args){
        Location.genBoardLocations(3, 3);
        
        //Test code to create a new board
        HashSet<Block> parts = new HashSet<>();
        parts.add(new Block(2,2,1,1));
        parts.add(new Block(1,1,0,0));
        
        //Test Code to create a new board
        HashSet<Location> free = new HashSet<>();
        free.add(Location.getLocationRef(1, 0));
        free.add(Location.getLocationRef(2, 0));
        free.add(Location.getLocationRef(0, 1));
        free.add(Location.getLocationRef(0, 2));
        
        //Test Code for Black Solver.Board
        HashSet<Location> allLocations = new HashSet<>();
        for (int i = 0; i < Location.getTableWidth(); i++)
            for (int j = 0; j < Location.getTableHeight(); j++)
                allLocations.add(Location.getLocationRef(i, j));
        
        Board test = new Board(parts, free);
        Board blank = new Board(new HashSet<>(), allLocations);
        
        System.out.println("Test Solver.Board output: " + test);
        System.out.println("Blank board output: " + blank);
      
        System.out.println("\nTesting Set Paths");
        test.setPath(new Board(new HashSet<>()), new Block(1,1,0,2),
                                           Location.getLocationRef(2,0));
        System.out.println("Parent Solver.Board: " + test.getParent());
        System.out.println("Moved Solver.Block: " + test.getMovedBlockRef());
        System.out.println("Moved direction: " + test.getMovedDirRef());
        
        System.out.println("\nTesting Solver.Block movement");
        
        Board resultGood = test.move(new Block(2,2,1,1), Location.NORTH);
        Board resultBad = test.move(new Block(2,2,1,1), Location.SOUTH);
        
        System.out.println("Moving the 2x2 block up -> " + resultGood);
        System.out.println("Moving the 2x2 block down -> " + resultBad);
        
        System.out.println("\nTesting if two board are the same");
        Board testCopy = test.copy();
        System.out.println(test + " equals? " + testCopy + " -> " 
                + test.equals(testCopy));
        System.out.println(test + " equals? " + resultGood + " -> " 
                + test.equals(resultGood));
        System.out.println(test + " equals? " + resultBad + " -> " 
                + test.equals(resultBad));
        
        System.out.println("\nGoal Checking");
        System.out.println("Goal is the current state: " + test.checkGoal(test));
        System.out.println("Goal is something else: " 
                + test.checkGoal(resultGood));
        System.out.println("Goal is the current blank: " + test.checkGoal(blank));
        
        System.out.println("\nHash Code");
        System.out.println("HashCode of " + test + " --> " + test.hashCode());
    }
}
//Output for this main
/*
 Test Solver.Board output:
2 2 1 1
1 1 0 0

Blank board output: 


Testing Set Paths
Parent Solver.Board:

Moved Solver.Block: 1 1 2 0
Moved direction: 0 2

Testing Solver.Block movement
Moving the 2x2 block up -> 
2 2 0 1
1 1 0 0

Moving the 2x2 block down -> null

Testing if two board are the same

2 2 1 1
1 1 0 0
 equals? 
2 2 1 1
1 1 0 0
 -> true

2 2 1 1
1 1 0 0
 equals? 
2 2 0 1
1 1 0 0
 -> false

2 2 1 1
1 1 0 0
 equals? null -> false

Goal Checking
Goal is the current state: true
Goal is something else: false
Goal is the current blank: true

Hash Code
HashCode of 
2 2 1 1
1 1 0 0
 --> 586365452
 */


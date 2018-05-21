package Solver;

import java.util.HashSet;

/**
 * This is an object that represents a block. It contains methods to move a block
 * check if the block is movable as well as calculate free blocks needed for a
 * move (needed in solver)
 * 
 * @author Daniel van der Maden
 *
 */

public class Block
{
    private int height, width;
    private Location postition;
    private int blockCount;
   
    //Overloaded Constructors
    Block(int height, int width, int x, int y){
        this.blockCount++;
        this.height = height;
        this.width = width;
        this.postition = Location.getLocationRef(x, y);
    }
   
    Block(int height, int width, Location position){
        this.blockCount++;
        this.height = height;
        this.width = width;
        this.postition = position;
    }
   
    //Accessors
    public int getHeight()         { return height; }
    public int getWidth()          { return width; }
    public Location getPosition()  { return postition; }
    public int getRowPosition()    { return postition.getRow(); }
    public int getColPosition()    { return postition.getColumn(); }
   
   
    /**
     * Generates an array of Solver.Location(s) which represent the spaces needed for
     * a move in a given direction (the argument)
     * 
     * @param direction the Solver.Location direction desired
     * @return Solver.Location Array of free in desired direction
     */
    public Location[] getFreeMoves(Location direction){
        int checkSize = 0;
        Location start = null, checkedDir = null;
      
        //Check Directions
        if(direction == Location.NORTH){
            checkSize = this.width;
            start = this.postition.add(Location.NORTH);
            checkedDir = Location.EAST;
        } else if(direction == Location.SOUTH) {
            checkSize = this.width;
            start = this.postition.add(Location.SOUTH.mult(height));
            checkedDir = Location.EAST;
        } else if(direction == Location.EAST) {
            checkSize = this.height;
            start = this.postition.add(Location.EAST.mult(width));
            checkedDir = Location.SOUTH;
        } else if(direction == Location.WEST) {
            checkSize = this.height;
            start = this.postition.add(Location.WEST);
            checkedDir = Location.SOUTH;
        }
        //Return null if there is no such direction
        if(start == null) return null;

        //Generate the free spaces
        Location[] checked = new Location[checkSize];
        for(int i = 0; i < checkSize; i++)
            checked[i] = start.add(checkedDir.mult(i));
        return checked;
      
   }
   
    /**
     * Checks if there is space for this block to move in the current board
     * 
     * @param free space needed for a move
     * @param emptyLocation free spaces in current table
     * @return T/F if you can move the block
     */
    public boolean movable(Location[] free, HashSet<Location> emptyLocation){
        if(free == null || free.length == 0)
            return false;
        for(Location space : free)
            if(!emptyLocation.contains(space))
                return false;
        return true;
    }
   
    /**
     * Move current block in the direction desired
     * 
     * @param direction direction that is desired
     * @param occupied Array of occupied Solver.Location places
     * @param empty HashSet of empty Solver.Location places
     */
    public void move(Location direction, Location[] occupied, HashSet<Location> empty){
        Location newFreeTuple = null;
        int stepsToOppSide = (occupied.length == this.width) ? this.height 
                                                             : this.width;
        this.postition = this.postition.add(direction);
        for(Location p: occupied){
            empty.remove(p);
            if(direction == Location.NORTH || direction == Location.WEST)
                newFreeTuple = p.add(direction.mult(-stepsToOppSide));
            if(direction == Location.SOUTH || direction == Location.EAST)
                newFreeTuple = p.sub(direction.mult(stepsToOppSide));
            empty.add(newFreeTuple);
        }      
    }
   
    //Assumes that argument is always a block object
    public boolean equals(Object o){
        Block other = (Block) o;
        return (other.postition == this.postition 
            && other.height == this.height 
            && other.width == this.width);
    }
      
    public int hashCode(){
        return (int) (this.height*(Math.pow(this.blockCount, 3)) 
            + this.width*(Math.pow(this.blockCount, 2))
            + this.getRowPosition() * this.blockCount + this.getColPosition());
    }

    public String toString(){
        return this.height + " " + this.width + " " + this.postition;
    }

    /**
     * @return a new INSTANCE of a Solver.Block object that has the same contents as
     * the instance it was called on.
     */
    public Block copy(){
        return new Block(this.height, this.width, this.postition);
    }

    //Test Main for this Class
    public static void main(String[] args){
        Location.genBoardLocations(3, 3);
        Block b;
        HashSet<Location> free;
      
        //Testing movement on a 2x2 block in the bottom right corner
        Location[] move;
        for (Location dir: Location.DIRECTIONS) {
            free = new HashSet<>();
            b = new Block(2,2,1,1);
            free.add(Location.getLocationRef(0, 0));
            free.add(Location.getLocationRef(1, 0));
            free.add(Location.getLocationRef(2, 0));
            free.add(Location.getLocationRef(0, 1));
            free.add(Location.getLocationRef(0, 2));
         
            move = b.getFreeMoves(dir);
            System.out.println("Solver.Block: " + b);
            System.out.println("Free Spaces: " + free);
            System.out.println("Direction: " + dir);
      
            if(b.movable(move, free)) {
                System.out.println("Moved");
                b.move(dir, move, free);
                System.out.println("Moved Solver.Block:" + b + "\n");
            } else {
                System.out.println("No Move\n");
            }
        }
        System.out.println("Testing equals");
        Block b1 = new Block(2, 2, 1, 1);
        Block b2 = new Block(2, 2, 1, 1);
        Block b3 = new Block(2, 2, 0, 1);
        System.out.println(b1 + " == " + b2 + " -> " + b1.equals(b2));
        System.out.println(b1 + " == " + b3 + " -> " + b1.equals(b3));
        System.out.println("\nTesting Copy:\n Copy: " + b3 + " --> " + b3.copy());
        System.out.println("\nTesting hashCode of " + b1 + ": " + b1.hashCode());
    }
}
//output for this main
/*
Solver.Block: 2 2 1 1
Free Spaces: [0 0, 0 1, 1 0, 0 2, 2 0]
Direction: -1 0
Moved
Moved Solver.Block:2 2 0 1

Solver.Block: 2 2 1 1
Free Spaces: [0 0, 0 1, 1 0, 0 2, 2 0]
Direction: 1 0
No Move

Solver.Block: 2 2 1 1
Free Spaces: [0 0, 0 1, 1 0, 0 2, 2 0]
Direction: 0 1
No Move

Solver.Block: 2 2 1 1
Free Spaces: [0 0, 0 1, 1 0, 0 2, 2 0]
Direction: 0 -1
Moved
Moved Solver.Block:2 2 1 0

Testing equals
2 2 1 1 == 2 2 1 1 -> true
2 2 1 1 == 2 2 1 0 -> false

Testing Copy:
 Copy: 2 2 1 0 --> 2 2 1 0

Testing hashCode of 2 2 1 1: -1906145882

 */





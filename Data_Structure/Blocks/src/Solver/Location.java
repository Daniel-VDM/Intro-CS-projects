package Solver;

import java.util.Arrays;

/**
 *
 *  This is an Object that represents a location in a board
 *  It saves a 2D array of Solver.Location Objects for a given board
 *  So that we can REFERENCE the objects and instead of created new 
 *  Solver.Location instances. This Object also supports Solver.Location addition, used
 *  to move blocks in the Solver.Block object.
 * 
 * @author Daniel Van Der Maden
 *
 */

public class Location
{
    private int row, column;

    //Reference Array to all locations
    private static Location[][] allLocations = null;

    //Constructor
    Location(int row, int column){
        this.row = row; 
        this.column = column;
    }

    //Accessors
    public int getRow()                { return this.row; }
    public int getColumn()             { return this.column; }
    public static int getTableWidth()  { return allLocations.length; }
    public static int getTableHeight() { return allLocations[0].length; }


    /**
     * Sets a Class array of all Solver.Location objects for the given board
     *
     * @param width of the board
     * @param height of the board
     */
    public static void genBoardLocations(int width, int height){
        if(allLocations != null) return;
        allLocations = new Location[width][height];
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++)
                allLocations[i][j] = new Location(i, j);
    }

    /**
     * Returns a Solver.Location reference for a table
     * 
     * @param row desired this.row
     * @param col desired this.column
     * @return desired Solver.Location object
     */
    public static Location getLocationRef(int row, int col){
        if (row >= 0 && col >= 0 && row < allLocations.length 
                                 && col < allLocations[row].length)
            return allLocations[row][col];
        return null;
    }


    /**
     * Adds a location to this instance of a location
     *
     * @param other location
     * @return the sum of the two locations
     */
    public Location add(Location other){
        if(other == null) return null;
        return getLocationRef(this.row + other.row, this.column + other.column); 
    }

    /**
     * Subtracts this instance of a location with another (in that order)
     *
     * @param other location
     * @return this - location
     */
    public Location sub(Location other){
        if(other == null) return null;
        return getLocationRef(this.row - other.row, this.column - other.column); 
    }

    /**
     * Multiplies this location my some multiple
     *
     * @param multiple the multiple
     * @return a location with this location * the multiple
     */
    public Location mult(int multiple){
        return getLocationRef(this.row* multiple, this.column * multiple);
    }

    public int hashCode(){
        return this.toString().hashCode();
    }

    public String toString(){
        return this.column + " " + this.row;
    }


    /**
     * Movement Instances of a Solver.Location - Used in computing the Moves
     */
    public final static Location NORTH = new Location(0,-1), 
                                SOUTH = new Location(0,1),
                                EAST = new Location(1,0),
                                WEST = new Location(-1,0);
    public final static Location[] DIRECTIONS = { NORTH, SOUTH, EAST, WEST };


    //Test main for this Class
    public static void main(String args[]){
        Location.genBoardLocations(3,3);
        System.out.println("Table position test for a 3x3 board");
        System.out.println(Arrays.deepToString(allLocations));

        System.out.println("\nFetching table position (1 0): " 
            + getLocationRef(1,0));
        System.out.println("Fetching table position (9 9): " 
            + getLocationRef(9,9));

        Location test = getLocationRef(0,0);
        System.out.println("\nTest equals on same obj (using getLocationRef): " 
            + test.equals(getLocationRef(0, 0)));
        System.out.println("Test equals on different obj (creating new obj): " 
            + test.equals(new Location(0,0)));

        System.out.println("\nTesting Math");
        Location arg1 = new Location(1,1);
        Location arg2 = new Location(1,0);

        System.out.println("Testing addition: " + arg1 + " + " + arg2 + " = "
            + arg1.add(arg2));
        System.out.println("Testing addition out of bound: " + arg1 + " + " 
            + new Location(9,9) + " = " + arg1.add( new Location(9,9)));
        System.out.println("Testing subtraction: " + arg1 + " - " + arg2 + " = "
            + arg1.sub(arg2));
        System.out.println("Testing subtraction out of bound: " + arg1 + " - " 
            + new Location(9,9) + " = " + arg1.add( new Location(9,9)));
        System.out.println("\nTesting multiplication by 1 for valid and 10"
            + " for invalid");
        System.out.println("Valid: " + arg1.mult(1));
        System.out.println("Invalid: " + arg1.mult(10));

        System.out.println("\nHashCode test: "+ new Location(9,9).hashCode());
    }
}
//Output for this main
/*
Table position test for a 3x3 board
[[0 0, 1 0, 2 0], [0 1, 1 1, 2 1], [0 2, 1 2, 2 2]]

Fetching table position (1 0): 0 1
Fetching table position (9 9): null

Test equals on same obj (using getLocationRef): true
Test equals on different obj (creating new obj): false

Testing Math
Testing addition: 1 1 + 0 1 = 1 2
Testing addition out of bound: 1 1 + 9 9 = null
Testing subtraction: 1 1 - 0 1 = 1 0
Testing subtraction out of bound: 1 1 - 9 9 = null

Testing multiplication by 1 for valid and 10 for invalid
Valid: 1 1
Invalid: null

HashCode test: 55826
 */






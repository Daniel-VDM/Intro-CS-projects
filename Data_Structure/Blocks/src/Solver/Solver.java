package Solver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
/**
 * CS47B Blocks Project
 * 
 * @author Daniel Van Der Maden
 */

public class Solver
{
    private final int DEBUG;
    private HashSet<Board> seen;
    private Board ini, goal;

    //Constructor
    public Solver(HashSet<Block> ini, HashSet<Block> goal, int height, int width, int DEBUG){
        this.DEBUG = DEBUG;
        HashSet<Location> free = new HashSet<>();
      
        //Boolean Array to setup the board
        boolean[][] occupied = new boolean[width][height];
        for(Block b: ini)
            for(int i = 0; i < b.getWidth(); i++)
                for(int j = 0; j < b.getHeight(); j++)
                    occupied[b.getRowPosition() + i][b.getColPosition() + j] = true;
      
        //Set the free locations of currCopy board
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++)
                if(!occupied[i][j]) free.add(Location.getLocationRef(i, j));
      
        //Set Boards
        this.ini = new Board(ini, free);
        this.goal = new Board(goal);
    }

    /**
     * The main 'logic' of the program
     * 
     * @return Solved Solver.Board or Null if no solution
     */
    private Board solve(){
        //Check if the initial Solver.Board is the goal
        if(this.ini.checkGoal(this.goal))
            return this.ini;
        
        LinkedList<Board> queue = new LinkedList<>();
        this.seen = new HashSet<>();
        queue.add(this.ini);
        this.seen.add(this.ini);
        Board currCopy, newBoard;
    
        while(!queue.isEmpty()){
            currCopy = queue.get(0);
            queue.remove(0);
    
            for(Block b: currCopy.getLayout()){
                for(Location direction: Location.DIRECTIONS){
                    newBoard = currCopy.move(b, direction);
                    //If it is an invalid move, do the next item in queue
                    if(newBoard == null) continue;
                    if(!this.seen.contains(newBoard)){
                        this.seen.add(newBoard);
                        queue.add(newBoard);
                        //Set links for displaying path when done
                        newBoard.setPath(currCopy, b, direction);
                    }
                    //Check goal
                    if(newBoard.checkGoal(goal)){
                        if (this.DEBUG == 3){
                            System.out.println("Boards tested: " + this.seen.size());
                        }
                        return newBoard;
                    }
                }
            }
        }
        if (this.DEBUG == 3)
            System.out.println("Boards tested: " + this.seen.size());
        return null;
    }

    /**
     * Method to print path from solution to start. Uses a DFS to print path 
     * 
     * @param curr The board you want to start printing from
     */
    private void printPath(Board curr){
        if(curr == null){
            //System.err.println("No Solution");
            System.exit(1);
        }
      
        //Set the Stack for the path trace
        Stack<Board> path = new Stack<>();
        Board currCopy = curr;
        while(currCopy != null){
            path.push(currCopy);
            currCopy = currCopy.getParent();
        }
        
        Block moved;
        Location direction;
        while(!path.isEmpty()){
            currCopy = path.pop();
            moved = currCopy.getMovedBlockRef();
            direction = currCopy.getMovedDirRef();
            
            //Standard Output Print right here
            if(currCopy.getParent() != null)
                System.out.println(moved.getPosition() 
                    + " " + moved.getPosition().add(direction));
        }
    }

    /**
     * Print the number of moves needed to get to a solution if there is one
     * 
     * This is a DEBUG method
     * 
     * @param curr The board you want to start printing from 
     * @return  number of moves required
     */
    private int numOfMoves(Board curr){
        if(curr == null){
            System.err.println("No Solution");
            System.exit(1);
        }
        int count = 1;
        while(curr != null){
            count++;
            curr = curr.getParent();
        }
        return count;
    }


    /**
     * 
     * HERE ARE A BUNCH OF STATIC METHODS FOR READING BOARDS FROM FILES
     * 
     */

    /**
     * Configures the Solver.Block Hashset of a board from the buffer.
     *
     * @param reader A buffer from the file that contains the desired blocks
     * @param bdBlks THe Hashset of a desired board state
     */
    private static void ConfigBlocks(BufferedReader reader, HashSet<Block> bdBlks) {
        String[] token;
        while (true) {
            try {
                token = reader.readLine().split(" ");
            } catch (Exception e) {
                break;
            } if (token != null) {
                bdBlks.add (new Block(Integer.parseInt(token[0]),
                        Integer.parseInt(token[1]),
                        Integer.parseInt(token[3]),
                        Integer.parseInt(token[2])));
            } else break;
        }
    }

    /**
     * Simple method to create a BufferReader given a file
     *
     * @param fileName location of file
     * @return buffered reader of the file
     * @throws Exception if anything goes wrong (standard exception)
     */
    private static BufferedReader reader (String fileName) throws Exception {
        File wordFile = new File (fileName);
        FileReader stuff = new FileReader(wordFile);
        return new BufferedReader (stuff);
    }

    /**
     * Static method to set a board up and returns it
     * 
     * @param iniFile The initial file location
     * @param goalFile the goal file location
     * @return a Solver.Solver instance with the board setup
     * @throws Exception File read exception
     */
    private static Solver setSolver(String iniFile, String goalFile, int DebugMode)throws Exception{
        BufferedReader reader;
        HashSet<Block> iniConfig = new HashSet<>();
        HashSet<Block> goalConfig = new HashSet<>();
        String[] token;

        //Set the iniConfig for the given board
        reader = reader(iniFile);
        token = reader.readLine().split(" ");
        int height = Integer.parseInt(token[0]);
        int width = Integer.parseInt(token[1]);
        Location.genBoardLocations(width, height);
        ConfigBlocks(reader, iniConfig);

        //Set the goalConfig for the solver
        ConfigBlocks(reader(goalFile), goalConfig);

        return new Solver(iniConfig, goalConfig, height, width, DebugMode);
    }

    /**
     * Sets the desired debug mode
     * 
     * @param arg user debug mode
     * @throws Exception if invalid argument
     */
    private static int setDEBUG(String arg) throws Exception{
        //Debug option output
        switch (arg) {
            case "-ooption":
                System.out.println("Help: Here are all the available "
                    + "debugging options");
                System.out.println("-> '-oMoves': Print moves required from "
                    + "start to finish");
                System.out.println("-> '-oCheckSolveOnly': Only prints if a "
                    + "solution can be found or not");
                System.out.println("-> '-oSeeBoards': Prints board before and"
                    + "after it is solved");
                System.out.println("-> '-oTestedConfigs': Prints the number of"
                    + "boards that we checked");
                System.out.println("-> '-oShowTimes': Print the time it takes"
                   + "to execute the solver");
                System.out.println("-> '-oShowHashSetCheckTime': Print the time "
                   + "it takes check a hashTable of boards (checks hashCode)");
                System.exit(1);
            case "-oMoves": return 0;
            case "-oCheckSolveOnly": return 1;
            case "-oSeeBoards": return 2;
            case "-oTestedConfigs": return 3;
            case "-oShowTimes": return 4;
            case "-oShowHashSetCheckTime": return 5;
            default:
                throw new Exception("Invalid argument for Debug");
        }
    }


    //Main Method
    public static void main(String[] args) throws Exception{
        int argLen = args.length;
        
        //Throw exception if invalid argument
        if (argLen < 2 || argLen > 3) {
            System.err.println("Invalid Arguments");
            System.exit(1);
        }
        
        //Set File Locations
        String iniFile = null, goalFile = null;
        int DebugMode = -1;
        
        if (argLen == 2){
            iniFile = args[0];
            goalFile = args[1];
        } 
        
        if (argLen == 3){
            DebugMode = setDEBUG(args[0]);
            iniFile = args[1];
            goalFile = args[2];
        }
        
        //Setup puzzle
        Solver puzzle = setSolver(iniFile, goalFile, DebugMode);
        
        //Debug stuff
        if(puzzle.DEBUG == 2)
            System.out.println("Solver.Board before solve:" + puzzle.ini);
        
        long start = 0;
        if(puzzle.DEBUG == 4)
            start = System.currentTimeMillis();
        
        //Solve the board
        Board solved = puzzle.solve();
        
        //Debug stuff
        if (puzzle.DEBUG == -1)
            puzzle.printPath(solved);
        if (puzzle.DEBUG == 0)
            System.out.println("Number of moves required to solve puzzle: " 
                + puzzle.numOfMoves(solved));
        if (puzzle.DEBUG == 1) {
            if (solved != null)
                System.out.println("Solution Found!");
            else 
                System.out.println("No Solution-");
        }
        if(puzzle.DEBUG == 2)
            System.out.println("Solver.Board after solve:" + solved);
        
        long time = System.currentTimeMillis() - start;
        if(puzzle.DEBUG == 4)
            System.out.println("Time to solve: " + time/1000 + "." 
                + time%1000 + " seconds");
        
        if(puzzle.DEBUG == 5) {
            long checkStart = System.nanoTime();
            puzzle.seen.contains(solved);
            long checkEnd = System.nanoTime() - checkStart;
            System.out.println("Time to check a board on a hashSet of boards: " 
                + checkEnd + " ns");
        }
    }
}

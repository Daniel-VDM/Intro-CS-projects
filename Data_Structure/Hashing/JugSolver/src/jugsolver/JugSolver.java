package jugsolver;
import java.util.*;

/**
 * JugSolver: TODO:
 */
public class JugSolver
{
    private static boolean DEBUGGING = false;
    
    private int desired;
    private int capacity[];
    //CODE ADDED HERE
    private Hashtable<String, JugContents> seen; //Create a HashTable
	
    public JugSolver(int amt0, int amt1, int amt2, int d){
        capacity = new int[3];
        capacity[0] = amt0;
        capacity[1] = amt1;
        capacity[2] = amt2;
        desired = d;
        
        //Initilize the HashTable (CODE ADDED HERE)
        seen = new Hashtable<>();
    }
	
    /**
     * Try to solve the puzzle, starting at configuration b.
     * @param b configuration jars
     * @return t/f if jug was able to pour
     */
    public boolean tryPouring(JugContents b){
        
        debugPrint(b.toString());
        if (b.jugs[0] == desired){
            debugPrint("Jug 0 contains " + b.jugs[0]);
            return true;
        } else if (b.jugs[1] == desired) {
            debugPrint("Jug 1 contains " + b.jugs[1]);
            return true;
        } else if (b.jugs[2] == desired) {
            debugPrint("Jug 2 contains " + b.jugs[2]);
            return true;
        }
        
        
        //CODE ADDED HERE
        //logic that keep track configs that have been seen
        String key = b.toString();
        if (seen.containsKey(key))  
            return false;
        seen.put(key, b);
        
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (i != j && tryPouring(b.pour(i, j))){
                    System.out.println("Pouring from jug " + i + " to jug "+j);
                    return true;
                }

        return false;
    }
	
    /**
     * @param args Three jug capacities, plus the contents of jugs 0 and 1,
     * plus the desired amount.
     */
    public static void main (String [ ] args) throws Exception {
        if (args.length != 6) {
            System.err.println ("Wrong number of arguments.");
            System.exit (1);
        }
        JugSolver puzzle = new JugSolver (Integer.parseInt (args[0]),
                Integer.parseInt (args[1]), Integer.parseInt (args[2]),
                Integer.parseInt (args[5]));
        JugContents init = puzzle.new JugContents (Integer.parseInt (args[3]),
                Integer.parseInt (args[4]), 0);
        System.out.println (puzzle.tryPouring (init));
    }
	
    private static void debugPrint(String s) {
        if (DEBUGGING)
            System.out.println (s);
    }
		
    static int min(int x, int y) { return x < y ? x: y; }
	
    
    /**
     * JugContents Class
     */
    class JugContents {
        public int jugs[];	// Contents of the three jugs.
	
        //Explicit integer Overloaded Constructor
        public JugContents(int amt0, int amt1, int amt2){
            jugs = new int[3];
            jugs[0] = amt0;
            jugs[1] = amt1;
            jugs[2] = amt2;
        }
        
        //JugContents object Overloaded Constructor
        public JugContents(JugContents b) {
            jugs = new int[3];
            jugs[0] = b.jugs[0];
            jugs[1] = b.jugs[1];
            jugs[2] = b.jugs[2];
        }
		
        /**
         * Return the result of pouring as much as possible from jug from to jug to.
         * @param from Start jug content
         * @param to Desired jug content
         * @return jug(config) after the pour is done
         */
        public JugContents pour(int from, int to) {
            JugContents afterPour = new JugContents(this);  //Save current state
            int amtToCanGet = capacity[to] - jugs[to];      //Howmuch is remainding in the 'to' jug before it is full
            int amtFromCanSupply = jugs[from];              //Howmuch 'from' can give before it is empty
            int amtPoured = min(amtToCanGet, amtFromCanSupply);
            debugPrint ("Pouring " + amtPoured + " from jug"
                    + from + " to jug " + to);
            afterPour.jugs[from] -= amtPoured;
            afterPour.jugs[to] += amtPoured;
            return afterPour;
        }
		
        public String toString(){
            return "Configuration = (" + jugs[0] + "," 
                    + jugs[1] + "," + jugs[2] + ")";
        }
		
	// TODO: You add more code to this class. 
        //Not needed for question?
    }
}
/*
             ###Prior To Including Hash Table###

E:\Programing Projects (Python)>java -jar "E:\Programing Projects (Java)\JugSolver\dist\JugSolver.jar" 8 5 3 8 0 4
Exception in thread "main" java.lang.StackOverflowError
        at java.lang.AbstractStringBuilder.ensureCapacityInternal(Unknown Source)
        at java.lang.AbstractStringBuilder.append(Unknown Source)
        at java.lang.StringBuilder.append(Unknown Source)
        at jugsolver.JugSolver$JugContents.toString(JugSolver.java:131)
        at jugsolver.JugSolver.tryPouring(JugSolver.java:34)
        at jugsolver.JugSolver.tryPouring(JugSolver.java:56)
        at jugsolver.JugSolver.tryPouring(JugSolver.java:56)
                            .
                            .
                            .  

                    ##After Hash Table##

E:\Programing Projects (Python)>java -jar "E:\Programing Projects (Java)\JugSolver\dist\JugSolver.jar" 8 5 3 8 0 4
Pouring from jug 0 to jug 2
Pouring from jug 2 to jug 1
Pouring from jug 1 to jug 0
Pouring from jug 2 to jug 1
Pouring from jug 0 to jug 2
Pouring from jug 2 to jug 1
Pouring from jug 1 to jug 0
Pouring from jug 0 to jug 2
Pouring from jug 0 to jug 1
true


*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttthashtest;

public class TTTboard {

    private StringBuffer myBoard;
    private final static boolean DEBUG = true;

    // Initialize a blank Tic-tac-toe board.
    public TTTboard ( ) {
            myBoard = new StringBuffer ("         ");
    }

    // Initialize a Tic-tac-toe board from the given value
    // by interpreting the value in base 3 and representing
    // a 0 as a blank, a 1 as an X, and a 2 as an O.
    public TTTboard (int base3) {
        myBoard = new StringBuffer ("         ");
        for (int k=8; k>=0; k--) {
            int digit = base3 % 3; 
            switch (digit) {
            case 0:
                myBoard.setCharAt (k, BLANK); 
                break;
            case 1: 
                myBoard.setCharAt (k, 'X'); 
                break;
            case 2:
                myBoard.setCharAt (k, 'O'); 
                break;
            }
            base3 = base3/3;
        }
        if (DEBUG)
            System.out.println("Added: " + myBoard);
    }

    final char BLANK = ' ';

    // More methods appear here, not particularly relevant to this exercise.

    // Print the board.
    public void print ( ) {
        // Code omitted to save paper.
    }
    
    //Code added here ~~~
    //hashCode method to make it ezer if to add other hash implimentations 
    @Override
    public int hashCode (){
        //return StringHashCode();    //String HashCode  Ex: 2
        return Base3HashCode();  //Basae 3 HashCode  Ex: 2
    }
    
    /**
     * This converts the board into strings and generates a hash code with
     * that string using the default hash-code method
     * 
     * Average insert time was around 10-15 ms    ****
     * Note: Code ran on a different machine than ex: 1
     * 
     * @return string hash using built in method
     */
    private int StringHashCode(){
        if (DEBUG)
            System.out.print("hash:" + myBoard.toString().hashCode() + "\t");
        return myBoard.toString().hashCode();
    }
    
    /**
     * Creates a 9 digit hash from the string.
     * 0 = blank, 1 = x and 2 = O. Each iteration of the for loop adds an
     * integer place that can be set to either 0,1,2. 
     * creating integer place is hash*= 10
     * 
     * Average insert time was around 15-20 ms   ****
     * Note: Code was ran on a different machine than ex:1 but same machine as
     * string hash
     * 
     * @return 9 digit hash 
     */
    private int Base3HashCode(){
        double hash = 0.0;
        for (int i = 0; i< myBoard.length(); i++){
            hash *=10;
            switch(myBoard.charAt(i)){
                case ' ':    
                    //for BLANK its a 0 so you dont add anything to hash
                    break;
                case 'X':
                    hash += 1;
                    break;
                case 'O':
                    hash += 2;
                    break;
                default:
                    System.err.println("Invalid Hash logic");
                    i = myBoard.length();
            }
        }
        if (DEBUG)
            System.out.print("hash:" + (int)hash + "\t");
        return (int)hash;
    }
    //~~~ end of code added
}
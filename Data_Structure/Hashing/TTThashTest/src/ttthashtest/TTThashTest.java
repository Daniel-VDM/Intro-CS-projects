package ttthashtest;

import java.io.*;
import java.util.*;

public class TTThashTest {

    public static void main (String [ ] args) {
        Hashtable table = new Hashtable<TTTboard, Integer> ( );
        //Note that upon instatiation  Hashtable<Key,Value> dictates what is 
        //going to be the key and what is going to be the value
        long startTime = System.currentTimeMillis ( );
        //set k 19683
        for (int k=0; k<19683; k++) {
            TTTboard b = new TTTboard (k);
            table.put (b.hashCode(), new Integer (k));
        }
        long finishTime = System.currentTimeMillis ( );
        System.out.println ("Time to insert all Tic-tac-toe boards = "
            + (finishTime-startTime));
    }
}

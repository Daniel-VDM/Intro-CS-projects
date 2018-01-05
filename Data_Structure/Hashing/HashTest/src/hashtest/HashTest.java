package hashtest;

import java.io.*;
// import java.util.*;

public class HashTest {

    //FUNCTION that returns a buffer-reader
    static BufferedReader reader (String fileName) throws Exception {
        File wordFile;
        FileInputStream wordFileStream;
        InputStreamReader wordsIn; 
        
        wordFile = new File (fileName);
        wordFileStream = new FileInputStream (wordFile);
        wordsIn = new InputStreamReader (wordFileStream);
        return new BufferedReader (wordsIn);
    }

    // Arguments are the file of words and the table size.
    public static void main (String [ ] args) throws Exception {
        
        //Instantiate a buffer reader
        BufferedReader wordReader;
        int tableSize = 1050;           //**CHANGE THIS**
        
        Hashtable table = new Hashtable (tableSize);
        String word;

        // Read all the words into the hash table.
        int wordCount = 0;
        //**CHANGE THIS**
        wordReader = reader ("words.txt"); //set the buffer to be all the words in passed argument of the file
        do {
            try {
                word = wordReader.readLine ();
            } catch (Exception e) {
                System.err.println (e.getMessage());
                break;
            }
            if (word == null) {
                break;
            } else {
                wordCount++;
                table.put (word, new Integer(wordCount)); //add to hash-table, data stored in hashtable is only the world count
            }
        } while (true);
        
        // Now look up all the words.
        //**CHANGE THIS**
        wordReader = reader ("words.txt");
        long startTime = System.currentTimeMillis ( );
        do {
            try {
                word = wordReader.readLine ();
            } catch (Exception e) {
                System.err.println (e.getMessage());
                break;
            }
            if (word == null) {
                break;
            } else {
                boolean result = table.containsKey (word); //doesn't fetch anything from hastable, it only checks that it exists
            }
        } while (true);
        long finishTime = System.currentTimeMillis ( );
        System.out.println ("Time to hash " + wordCount + " words is " 
            + (finishTime-startTime) + " milliseconds.");
        table.printStatistics ( );
    }
}


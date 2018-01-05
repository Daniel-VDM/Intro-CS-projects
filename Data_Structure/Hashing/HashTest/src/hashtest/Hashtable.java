package hashtest;

public class Hashtable {
        
    private static boolean DEBUGGING = false;
    private LinkedList [] myTable; //This is an array of linked lists

    public Hashtable (int size) {
        myTable = new LinkedList [size]; 
        for (int k=0; k<size; k++) {
            myTable[k] = new LinkedList ( );
        }            
    }

    private static long hash (String key) {
	// Uncomment one of the following lines to use the 
	// corresponding hash function.
	// return hash1 (key);
	// return hash2 (key);
	// return hash3 (key);
	return Math.abs (key.hashCode ( ));
    }

    // Slight variation on the ETH hashing algorithm
    private static int MAGIC1 = 257;
    private static long hash1 (String key) {
	long h = 1;
	for (int k=0; k<key.length(); k++) {
		h = ((h % MAGIC1)+1) * (int) key.charAt(k);
	}
	return h;
    }

    // Slight variation on the GNU-cpp hashing algorithm
    private static int MAGIC2 = 4;
    private static long hash2 (String key) {
	long h = 0;
	for (int k=0; k<key.length(); k++) {
		h = MAGIC2 * h + (int) key.charAt(k);
	}
	return h << 1 >>> 1;
    }

    // Slight variation on the GNU-cc1 hashing algorithm
    private static int MAGIC3 = 613;
    private static long hash3 (String key) {
	long h = key.length();
	for (int k=0; k<key.length(); k++) {
		h = MAGIC3 * h + (int) key.charAt(k);
	}
	return h << 2 >>> 2;
    }

    // Add the key to the table. The value is included just for compatibility
    // with the Hashtable class in java.util.
    public void put (String key, Integer value) {
        int h = (int) (hash (key) % myTable.length);    //h = normalized hash of key
        if (!myTable[h].find (key)) {                   //Chaining resolution here. 
/*It goes to the 'hth' node in the MAIN linked list and searches the linked list IN the 'hth' node for the key to see if key is already in*/
            myTable[h].insert (new Info (key, value));  //info = node in Linked List
            if (DEBUGGING) {
                System.out.println ("Inserting " + key);
            }
        } else {
            System.err.println (key + " already in table.");
        }
    }
    
    // Return true if key is in the table, and false otherwise.
    public boolean containsKey (String key) {
        int h = (int) (hash (key) % myTable.length);
        return (myTable[h].find(key));
    }

    // Print statistics about the table:
    //   the maximum length of a collision list;
    //   the optimal length of a collision list;
    //   the average number of comparisons needed for a successful search;
    //   the standard deviation for the number of comparisons needed for
    //     a successful search.

    // The average number of comparisons is computed by summing the total
    // number of comparisons necessary to find each element in the table,
    // and dividing that total by the number of elements.

    // The standard deviation is computed by finding the total over all
    // elements of the square of the number of comparisons necessary to
    // find that element, dividing by the number of elements, subtracting
    // the square of the average number of comparisons, and taking the
    // square root of that difference.

    public void printStatistics ( ) {
	int elementCount = 0;
	int maxLen = 0;
	int sum = 0;
	int sqSum = 0;
	double avg;
	for (int k=0; k<myTable.length; k++) {
	    int len = myTable[k].size ( );
	    maxLen = (len > maxLen)? len: maxLen;
	    elementCount += len;
	    // 1 + 2 + 3 + ... + len = len(len+1)/2
	    sum += (len*(len+1))/2;
	    // 1^2 + 2^2 + 3^2 + ... + len^2 = len(len+1)(2 len+1)/6
	    sqSum += (len*(len+1)*(2*len+1))/6;
	}
	avg = ((float) sum)/elementCount;

	System.out.println ("Number of elements  = " + elementCount);
	System.out.println ("Maximum list length = " + maxLen);
	System.out.println ("Optimal list length = "
	    + (elementCount + myTable.length - 1)/myTable.length);
	System.out.println ("Average # compares  = " + avg);
	System.out.println ("Standard deviation  = "
	    + Math.sqrt (((double) sqSum)/elementCount - avg*avg));
    }

    public static void main (String [ ] args) {
        Hashtable table = new Hashtable (1000);
        table.put ("Mike", new Integer(3));
        System.out.println ("Does table contain Mike? " 
            + table.containsKey ("Mike"));
        System.out.println ("Does table contain Clancy? " 
            + table.containsKey ("Clancy"));
	table.printStatistics ( );
    }
}

// Node in the hash table.
class Info {

    public Info (String s, Integer v) {
        key = s;
        value = v;
    }

    public boolean equals (String s) {
        return key.equals (s);
    }

    public boolean equals (Object s) {
        return key.equals ((String) s);
    }

    public String key;
    public Integer value;
}

class LinkedList {

    private int myCount;	// number of items in the list
    private Node myHead;	// reference to the list's first node
    
    private class Node {
	public Info myInfo;
	public Node myNext;
    }
    
    public LinkedList ( ) {
	myCount = 0;
	myHead = null;
    }
    
    // Insert at the start of the list.
    public void insert (Info x) {
	Node ptr = new Node ( );
	ptr.myInfo = x;
	ptr.myNext = myHead;
	myHead = ptr;
	myCount++;
    }
    
    // Return the length of the linked list.
    public int size ( ) {
	return myCount;
    }
    
    // Return a reference to a node of the linked list that contains s,
    // or null if no such node exists.
    public boolean find (String s) {
	Node ptr;
	for (ptr=myHead; ptr!=null && !ptr.myInfo.equals(s); ptr=ptr.myNext) {
	}
	return ptr!=null;
    }
}

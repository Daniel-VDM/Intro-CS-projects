package borg_main;
import java.io.IOException;

/**
 * @author Daniel Van Der Maden
 */
public class HashTable 
{
    /**
     * A linked list entry for the chaining collision resolution.
     */
    public class Entry 
    {
      private final String key;
      private final int scope;
      private int data;
      private Entry next;
 
      Entry(String key, int data, int scope) 
      {
            this.key = key;
            this.data = data;
            this.scope = scope;
            this.next = null;
      }
    }
    
    //Define the table size here ---
    private final static int TABLE_SIZE = 256;
    
    //The Hashtable
    private final Entry[] table;
    
    //Default Constructor
    HashTable()
    {
        table = new Entry[TABLE_SIZE];
    }
    
    /**
     * @param key the Variable name used in BORG
     * @return hash
     */
    private int hash(String key)
    {
        int hash = 0;
        for(int i = 0; i < key.length() ; i++)
            hash += (int)key.charAt(i)*(i+1);
        return hash % TABLE_SIZE;
    }
    
    /**
     * @param key the Variable name used in BORG
     * @param data the data of Variable name
     * @param scope the scope level of the node
     */
    @SuppressWarnings("null")
    public void add (String key, int data, int scope)
    {
        //Get Hash
        int hash = hash(key);
        //Empty element
        if (table[hash] == null)
            table[hash] = new Entry(key,data,scope);
        //If not empty check rest
        else
        {
            Entry temp = table[hash];
            while (temp.next != null && !temp.key.equals(key))
                temp = temp.next;
            //If key matches then just replace data
            if(temp.key.equals(key))
                temp.data = data;
            else
                temp.next = new Entry(key,data,scope);
        }
    }
    
    /**
     * replace key with a new data set | Scope is NOT touched
     * @param key the Variable name used in BORG
     * @param data the data of Variable name
     * @throws IOException if it can not replace key data
     */
    @SuppressWarnings("null")
    public void replace (String key, int data) throws IOException
    {
        //Get Hash
        int hash = hash(key);
        //Empty element
        if (table[hash] == null)
            throw new IOException(key +" DNE in hashTable");
        //If not empty check rest
        else
        {
            Entry temp = table[hash];
            while (temp.next != null && !temp.key.equals(key))
                temp = temp.next;
            //If key matches then just replace data
            if(temp.key.equals(key))
                temp.data = data;
            else
                throw new IOException(key +" DNE in hashTable");
        }
    }
    
    
    /**
     * @param key Variable name used in BORG
     * @return the value for key, min integer value for not found. 
     * @throws IOException if key is not found in hash table
     */
    public int getData(String key) throws IOException
    {
        //Get Hash
        int hash = hash(key);
        //Empty return
        if (table[hash]==null)
            throw new IOException(key +" DNE in hashTable");
        //Check rest if not empty
        else
        {
            Entry temp = table[hash];
            while (temp != null && !temp.key.equals(key))
                temp = temp.next;
            if(temp == null)
                throw new IOException(key +" DNE in hashTable");
            else
                return temp.data;
        }
    }
    
    /**
     * Check if key exists
     * @param key variable name used in BORG
     * @return true = exists, false = NOTexists
     */
    public boolean contains(String key)
    {
        //Get Hash
        int hash = hash(key);
        //Empty return
        if (table[hash]==null)
            return false;
        //Check rest if not empty
        else
        {
            Entry temp = table[hash];
            while (temp != null && !temp.key.equals(key))
                temp = temp.next;
            if(temp == null)
                return false;
            else
                return true;
        }
    }
    
    /**
     * @param key Variable name used in BORG
     * @return the scope of the key
     */
    public int getScope(String key)
    {
        //Get Hash
        int hash = hash(key);
        //Empty return
        if (table[hash]==null)
            return -1;
        //Check rest if not empty
        else
        {
            Entry temp = table[hash];
            while (temp != null && !temp.key.equals(key))
                temp = temp.next;
            if(temp == null)
                return -1;
            else
                return temp.scope;
        }
    }
    
    /**
     * Deletes data that is greater than the scope
     * @param scope the scope of the BORG program at time of method call
     */
    public void deleteNotInScope(int scope)
    {
        Entry curr;
        for (int i = 0; i<TABLE_SIZE; i++)
        {
            curr = table[i];
            
            //first element
            if(curr != null && curr.next == null)
            {
               if(curr.scope > scope)
                    delete(curr.key);
            }
            //if theres 2 or more entrys in the chain
            else if(curr != null && curr.next != null)
            {
                //Check all entrys in the chain
                do
                {
                    if(curr.scope > scope)
                        delete(curr.key);
                    curr = curr.next;
                }
                while(curr.next != null);
                
                //Check last element in chain
                if(curr.scope > scope)
                    delete(curr.key);
            }
        }
    }
    
    /** 
     * Will delete anything that matches Hash
     * @param key Variable name used in BORG
     */
    public void delete(String key)
    {
        int hash = hash(key);
        //Make sure that elemet being delented is not null
        if (table[hash] != null)
        {
            //Keeping tack of previous so that we can shift accordingly
            Entry prev = null;
            Entry curr = table[hash];
            //Setting appropriate pointers to 'delete' hash
            //Note: i do not delete obj, i just point it out of scope.
            while(curr.next != null && !curr.key.equals(key))
            {
                prev = curr;
                curr = curr.next;
            }
            if (curr.key.equals(key))
            {
                if (prev == null)
                    table[hash] = curr.next;
                else
                    prev.next = curr.next;
            }
        }
    }
    
}

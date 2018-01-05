package amoebafamily;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.LinkedList;
//import java.lang.Exception; (Used for exception handeling)

/**
 * AmoebaFamily: Create a tree of Amoeba. Methods to print family members 
 * include pre-order and Methods to iterator through in pre-order or 
 * Breadth first
 * 
 * Note: project was converted from the Enumeration version to the Iterator 
 *       version
 *
 *  @author Daniel Van Der Maden
 *    @date 9/2/17
 * @project Iterator Exercises
 */
public class AmoebaFamily
{
    private final Amoeba myHead;  // Head of the AmoebaFamily
    
    /**
     * Constructs a new AmoebaFamily containing a single Amoeba (the head of
     * the family).
     * @param name Name of the head Amoeba.
     */
    public AmoebaFamily(String name)
    {
        myHead = new Amoeba(name, null);
        addChildBool = false;
    }

    
    private boolean addChildBool;   //Used in adding children
    /**
     * Add a new Amoeba child with a given name to the specified Amoeba parent.
     * Precondition: The AmoebaFamily contains an Amoeba named parentName.
     * 
     * Uses Aux method and the instance boolean created above
     * 
     * @param parentName Name of the parent Amoeba that we want to add a child
     *                   to.
     * @param childName Name of the new Amoeba child.
     */
    public void addChild(String parentName, String childName)
    {
        addChildAux(myHead, parentName, childName);
        addChildBool = false;
    }
    
    /**
     * This method is auxiliary method to addChild that is recursive.
     * Its goal is to search all children of the current parent - and all of the 
     * children's children etc... - for a parent name that matches the parent 
     * name of the child we are trying to add
     * 
     * It uses the addChildBool (boolean) to indicate if it has found a match
     * and stops looking for a match if it has. 
     * 
     * @param curr The current Amoeba that we are using as reference
     * @param pName The parent name what we are searching for
     * @param cName The child name that we want to add
     */
    private void addChildAux(Amoeba curr, String pName, String cName) {
        if (pName.equals(curr.myName)){
            curr.addChild(cName);
            addChildBool = true; //Boolean is true because child has been added
        } else {
            Iterator children = curr.myChildren.iterator();
            //Iterrate over the current parent's children
            while (children.hasNext()){
                if (addChildBool) break;    //stop looking if child is added
                addChildAux((Amoeba)children.next(),pName, cName);
            }
        }
        //Personal Notes and Questions:
        //This works, but how can i make this more efficient?
        //Maybe insert using parents where we keep track of tail?
    }
    
    /**
     * Print the names of all amoebas in the family. Names should appear in
     * preorder, with children's names printed oldest first. Members of the
     * family constructed with the main driver program below should be printed
     * in the following sequence:
     *
     *     Amos McCoy, mom/dad, me, Mike, Bart, Lisa, Homer, Marge, Bill,
     *     Hilary, Fred, Wilma
     * 
     * Uses Aux method
     */
    public void print()
    {
        printAux(myHead);
    }
    
    /**
     * This is an auxiliary function from print that prints each child in the
     * family through recursion. It is done in a pre-order fashion (print
     * then process). It stops when there are no more children to print. 
     * 
     * @param curr the current Amoeba that we are printing 
     */
    private void printAux(Amoeba curr) {
        System.out.println(curr.toString());
        if (curr.myChildren.isEmpty()) return;  //stops when no children
        Iterator children = curr.myChildren.iterator();
        while (children.hasNext())
            printAux((Amoeba)children.next());   
    }
    
    /**
    * Returns a new iterator for this AmoebaFamily.
    * @param arg the type of iterator desired
    * @return New Iterator instance corresponding to this AmoebaFamily.
    */
    public Iterator iterator(int arg) //throws Exception
    {
        switch(arg){
            case 0:
                return new AmoebaIterPreOrder();
            case 1:
                return new AmoebaIterBreadthFirst();
            default:
                //throw new Exception("Iterator not supported");
                return null;
        }
    }
    
    /**
     * This class is used for the pre order Iterator 
     * Implementation uses a stack (built into java)
     */
    private class AmoebaIterPreOrder implements Iterator {

        Stack<Amoeba> stack;
        
        AmoebaIterPreOrder() {
            stack = new Stack<>();
            stack.push(myHead);
        }
        
        /**
         * @return false if the stack is empty
         */
        @Override
        public boolean hasNext() { return !stack.empty(); }

        
        /**
         * @return the next child in a pre order fashion
         */
        @Override
        public Object next() {
            //pop what you are going to return
            Amoeba curr = stack.pop();
            
            //push the next child from pop
            if (!curr.myChildren.isEmpty())
                //Decrementing for-loop because when popping from stack
                //we want it in pre-order, so b/c stacks are last-in first-out
                //the oldest is popped first with this implementation
                for (int i = curr.myChildren.size()-1; i>=0; i--)
                    stack.push((Amoeba)curr.myChildren.get(i));
            
            return curr;
        }
    }
    
    /**
     * This class is used for the Breadth First Iterator
     * Implementation uses a linked list for queue.
     */
    private class AmoebaIterBreadthFirst implements Iterator {

        LinkedList<Amoeba> queue;
        
        AmoebaIterBreadthFirst() {
            queue = new LinkedList<>();
            queue.add(myHead);
        }
        
        /**
         * @return false if the queue is empty
         */
        @Override
        public boolean hasNext() { return !queue.isEmpty(); }

        /**
         * @return next the next child in a breadth first fashion
         */
        @Override
        public Object next() {
            //Dequeue what you are going to return
            //Note: poll just means take out from the head of the Linked List
            Amoeba curr = queue.poll();
            
            //enqueue the children from what you just dequeued
            if (!curr.myChildren.isEmpty())
                for (int i = 0; i < curr.myChildren.size(); i++)
                    queue.add((Amoeba)curr.myChildren.get(i));
            
            return curr;
        }
    }
    
    /**
     * Construct a family of Amoebas, and then print the family tree using the
     * print() method as well as the AmoebaIterator.
     * @param args Command-line arguments.
     */
    public static void main(String[] args)
    {
        AmoebaFamily family = new AmoebaFamily("Amos McCoy");
        family.addChild("Amos McCoy", "mom/dad");
        family.addChild("mom/dad", "me");
        family.addChild("mom/dad", "Fred");
        family.addChild("mom/dad", "Wilma");
        family.addChild("me", "Mike");
        family.addChild("me", "Homer");
        family.addChild("me", "Marge");
        family.addChild("Mike", "Bart");
        family.addChild("Mike", "Lisa");
        family.addChild("Marge", "Bill");
        family.addChild("Marge", "Hilary");

        System.out.println("Here's the family:");
        family.print();
        
        System.out.println ();
        System.out.println ("Here it is again! Pre Order:");
        Iterator AmoebaEnumDF = family.iterator(0);
        while (AmoebaEnumDF.hasNext() )
            System.out.println (((Amoeba) AmoebaEnumDF.next() ));
        
        System.out.println ();
        System.out.println ("Here it is again! Breadth First:");
        Iterator AmoebaEnumBF = family.iterator(1);
        while (AmoebaEnumBF.hasNext() )
            System.out.println (((Amoeba) AmoebaEnumBF.next() ));
    }

    /**
     * Amoeba: Defines an Amoeba with a name, a parent, and children.
     */
    private class Amoeba
    {
        public String myName;                   // Amoeba's Name
        public Amoeba myParent;                 // Amoeba's Parent
        public ArrayList<Amoeba> myChildren;    // Amoeba's Children

        /**
         * Constructs a new Amoeba with a given name and parent.
         * @param name Name of this Amoeba.
         * @param parent Parent of this Amoeba.
         */
        public Amoeba(String name, Amoeba parent)
        {
            myName = name;
            myParent = parent;
            myChildren = new ArrayList<>();
        }

        /**
         * Returns the name of this Amoeba.
         * @return Amoeba name.
         */
        public String getName()
        {
            return myName;
        }

        /**
         * Returns the parent of this Amoeba.
         * @return Parent Amoeba.
         */
        public Amoeba getParent()
        {
            return myParent;
        }

        /**
         * Returns the children of this Amoeba.
         * @return ArrayList containing the children of this Amoeba.
         */
        public ArrayList<Amoeba> getChildren()
        {
            return myChildren;
        }

        /**
         * Constructs a new Amoeba with a given name, and adds it as the
         * youngest child of this current Amoeba.
         * @param childName Amoeba child name.
         */
        public void addChild(String childName)
        {
            Amoeba child = new Amoeba(childName, this);
            myChildren.add(child);
        }

        /**
         * Returns the String representation of this Amoeba.
         * @return Name of this Amoeba.
         */
        @Override
        public String toString()
        {
            return myName;
        }
    }
}	

//Console Output 
/*
Here's the family:
Amos McCoy
mom/dad
me
Mike
Bart
Lisa
Homer
Marge
Bill
Hilary
Fred
Wilma

Here it is again! Pre Order:
Amos McCoy
mom/dad
me
Mike
Bart
Lisa
Homer
Marge
Bill
Hilary
Fred
Wilma

Here it is again! Breadth First:
Amos McCoy
mom/dad
me
Fred
Wilma
Mike
Homer
Marge
Bart
Lisa
Bill
Hilary
BUILD SUCCESSFUL (total time: 0 seconds)
*/
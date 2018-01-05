package heap;
import java.util.*;

/**
 * @author Daniel Van Der Maden
 */
public final class NodeHeap {
   
    private final boolean DEBUGG = false;
    
    private int size;
    private Node root, addParent;
    
    //Constructor
    NodeHeap(){
        size = 0;
        root = addParent = null;
        
        if (DEBUGG)
            print();
    }
    
    /**
     * @return if the heap is empty
     */
    public boolean isEmpty()    { return root == null; }
    
    /**
     * @return value of root node (top)
     * @throws NoSuchElementException if user attempted to access an empty node 
     */
    public int top() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException("accessing root of empty heap");
        return root.getValue();
    }
    
    /**
     * Adds a value to the heap and maintains the addParent 
     * 
     * @param value added to heap 
     */
    public void add(int value){
        Node newNode = new Node(value, null, null, null);
        size++;
        //Handles empty tree
        if (root == null){
            root = newNode;
            return;
        }
        
        if (addParent == null){
            addParent = root;
            addParent.setLeft(newNode);
        } else if (addParent.getRight() == null) {
            addParent.setRight(newNode);
        } else if (addParent.getRight() != null) {
            findAddParent();
            addParent.setLeft(newNode);
        }
        newNode.setParent (addParent);
        swapUp(newNode);      
        
        if (DEBUGG)     { print(); }
    }
    
    /**
     * Removes the root via the swapDown method
     * Corrects the AddParent once the remove is made
     * Corrects the size
     * 
     * @throws NoSuchElementException 
     */
    public void remove() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException ("attempt to access "
                    + "top of empty heap");
        
        size--;
        swapDown(root);
        findAddParent();
        
        if (DEBUGG)
            print();
    }
    
    
    /**
     * Helper Method for swapping values down from root when inserting to root
     * Used in remove method
     * 
     * @param node 
     */
    private void swapDown(Node node){
        if (size == 0){
            root = null;
            return;
        }
        
        Node curr = node;
        while (curr != null){
            //If current is a leaf
            if (curr.getLeft() == null && curr.getRight() == null){
                if (addParent == null){
                    root = null;
                    return;
                }
                if (curr.getParent() == addParent){
                    if (addParent.getLeft() == curr)
                        addParent.setLeft(addParent.getRight());
                    addParent.setRight(null);
                    return;
                } else if (curr.getParent() != addParent) {
                    if (addParent.getRight() == null){
                        curr.setValue(addParent.getLeft().getValue());
                        addParent.setLeft(null);
                    } else {
                        curr.setValue(addParent.getRight().getValue());
                        addParent.setRight(null);
                    }
                    return;
                }
            }
            //Check if left has something
            if (curr.getRight() == null && curr.getLeft() != null){
                curr.setValue(curr.getLeft().getValue());
                curr.setLeft(null);
                return;
            } else {    //Swap to maintain the heap
                if (curr.getLeft().getValue() > curr.getRight().getValue()){
                    curr.setValue(curr.getLeft().getValue());
                    curr = curr.getLeft();
                } else { 
                    curr.setValue(curr.getRight().getValue());
                    curr = curr.getRight();
                }
            }
        }
    }
    
    /**
     * A helper method that maintains the heap by swapping up values that are
     * smaller than their parents
     * 
     * @param node that you are swapping
     */
    private void swapUp(Node node){
        int value = node.getValue();
        Node parent = node.getParent();
        int parentValue = parent.getValue();
        Node current = node;
        
        //Swap to maintain the heap
        while(value > parentValue){
            current.setValue(parentValue);
            current = parent;
            if (parent == root)
                break;
            parent = parent.getParent();
            parentValue = parent.getValue();
        }
        current.setValue(value);
    }
    
    
    /**
     * Find and set addParent using the binary string of size
     * to iterate over the tree
     */
    public void findAddParent(){
        String binary = Integer.toBinaryString(size);
        int length = binary.length() - 1;
        addParent = root;
        
        for (int i = 1 ; i < length ; i++){
            if (binary.charAt(i) == '0')
                addParent = addParent.getLeft();
            else
                addParent = addParent.getRight();
        }
    }
    
    /**
     * Debugging tools that allows us to see the heap in order.
     * If it is successful it should print the data in order. 
     */
    public void print(){
        System.out.print("--Heap of size: " + size + "--");
        
        if (isEmpty()){
            System.out.println("\nEmpty Heap\n");
            return;
        }
        
        //Uses list to keep track of heap (Iterator could be done here?)
        LinkedList<Node> queue =  new LinkedList<>();
        queue.add(root);
        int count = 1, i = 1, level = 1;
        while (!queue.isEmpty()){
            Node curr = queue.remove();
            
            if (i == 0) {
                count *= 2;
                i = count;
            }
            
            if (i == count)
                System.out.print("\n Level " + level++ +":    " 
                        + curr.getValue() + " ");
            else 
                System.out.print(" " + curr.getValue() + " ");
            
            i--;
            if (curr.getLeft() != null)
                queue.add(curr.getLeft());
            if (curr.getRight() != null)
                queue.add(curr.getRight());
        }
        System.out.println("\n");
        
    }
    
}

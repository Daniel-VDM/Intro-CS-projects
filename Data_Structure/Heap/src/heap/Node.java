package heap;

/**
 * Simple node for a Binary Tree that keeps track of parents
 * 
 * @author Daniel Van Der Maden
 */
public class Node {
    private Node left, right, parent;
    private int value;
    
    Node(int value, Node left, Node right, Node parent){
        this.value = value;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }
    
    //Accessors
    public int getValue()   { return value; }
    public Node getLeft()   { return left; }
    public Node getRight()  { return right; }
    public Node getParent() { return parent; }
    
    //Mutators
    public void setValue(int value)     { this.value = value; }
    public void setLeft(Node left)      { this.left = left; }
    public void setRight(Node right)    { this.right = right; }
    public void setParent(Node parent)  { this.parent = parent; }
    
}

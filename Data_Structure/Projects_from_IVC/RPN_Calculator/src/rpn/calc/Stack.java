package rpn.calc;

/**
 * @author Daniel Van Der Maden
 */
public class Stack 
{
    /**
     *  Node Class
     */
    private static class Node
    {
        private double data;
        private Node next;
              
        public Node(double data)
        {
            this.data = data;
            next = null;
        }
    }
    
    //Instance Var
    private Node top;
    private int nodeCount;
    
    //Default Constructor
    public Stack()
    {
        top = null;
        nodeCount = 0;
    }
        
    //Add to front of Stack
    public void  push(double Data)
    {
        Node temp = new Node(Data);
        temp.next =  top;
        top = temp;
        nodeCount++;
    }
    
    //Gets Data from top of the stack
    public double pop()
    {
        double data = top.data;
        Node temp = top;        //Might not need temp? b/of JVM cleanup?
        top = top.next;
        temp = null;            //Might not need? b/c of ^
        nodeCount--;
        return data;
    }
    
    //Check if stack is empty
    public boolean isEmpty()
    {
        return top == null;
    }
    
    //Accessor Method
    public int getNodeCount(){return this.nodeCount;}
}

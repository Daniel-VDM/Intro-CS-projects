package bstproject_main;

/**
 * @author Daniel Van Der Maden
 * 
 *    ***
 * Future Note: you can do insert and search iteratively, google it.
 *    ***
 */
public class BinarySearchTree 
{
    /**
     *  Node Class
     */
    public class Node
    {
        private final int data;
        private final String name;
        public Node left,right;
        
        public Node(String name, int data)
        {
            this.data=data;
            this.name=name;
            left = right = null;
        }
    }
    
    //Instance var
    private Node root;
    private int minWeight; //set = -1 if doing if statement in insert method
    
    //Inserts Data - Uses recursive algorithm
    public void insert(String Name, int Data)
    {
        Node temp = new Node(Name, Data);

        //Alt code that would also keep tack of min weight
        //You would just add an accessor if u take this approach
        //if(minWeight == -1 || Data < minWeight){ minWeight = Data; }
        
        if(root == null) //For empty tree
            root = temp;
        else
            recursiveInsert(root, temp); //for the rest of the tree
    }
        //Recursive method for inserting (private)
        private void recursiveInsert(Node lastRoot, Node temp)
        {
            //Uses names as the key
            if( lastRoot.name.compareTo(temp.name) > 0) 
            {
                if(lastRoot.left == null)
                    lastRoot.left = temp;
                else
                    recursiveInsert(lastRoot.left, temp);
            }
            else
            {
                if(lastRoot.right == null)
                    lastRoot.right = temp;
                else
                    recursiveInsert(lastRoot.right, temp);
            }
        }
    
    //Searches for data, the key is the name
    public void search(String Key)
    {
        Node found = recurSearch(root, Key);
        
        if(found == null)
            System.out.print("\tThe name '" + Key + "' is not found.");
        else
            System.out.print("\tThe weight for '" + found.name 
                    + "' is: " + found.data);
    }
        //Finds the node with that matches the key
        //doesn't return anything if key doesn't match
        //BST so only search half of the tree
        private Node recurSearch (Node root, String Key)
        {
            if (root == null || root.name.equals(Key))
                return root;
            if (root.name.compareTo(Key) > 0)
                return recurSearch(root.left, Key);
            return recurSearch(root.right, Key);
        }
        
    //Method to find height
    public int height()
    {
        return heightRecur(root)-1; //minus one b/c it counts root as 1
    }
        //Recursive part of finding height (private)
        private int heightRecur (Node node)
        {
            if (node == null)
                return 0;
            else
            {
                int lHeight = heightRecur(node.left);
                int rHeight = heightRecur(node.right);
                
                if(lHeight > rHeight)
                    return ++lHeight;
                else
                    return ++rHeight;
            }
        }
                
    //Public method call for inorder traverse
    public void printInOrder()
    {
        recursivePrintInOrder(root);
    }
        //Recursive method for traversing (private)
        private void recursivePrintInOrder(Node currNode)
        {
            if(currNode == null){return;}
            
            recursivePrintInOrder(currNode.left);
            System.out.print(currNode.name + " " + currNode.data + ", ");
            recursivePrintInOrder(currNode.right);
        }
        
    //Public method call for Postorder traverse
    public void printPostOrder()
    {
        recursivePrintPostOrder(root);
    }
        //Recursive method for traversing (private)
        private void recursivePrintPostOrder(Node currNode)
        {
            if(currNode == null){return;}
            
            recursivePrintInOrder(currNode.left);
            recursivePrintInOrder(currNode.right);
            System.out.print(currNode.name + " " + currNode.data + ", ");
        }
    
    //Public method call for Preorder traverse
    public void printPreOrder()
    {
        recursivePrintPreOrder(root);
    }
        //Recursive method for traversing (private)
        private void recursivePrintPreOrder(Node currNode)
        {
            if(currNode == null){return;}
            
            System.out.print(currNode.name + " " + currNode.data + ", ");
            recursivePrintInOrder(currNode.left);
            recursivePrintInOrder(currNode.right);
        }
    
    //Public method to count the leaves
    public int leafCount()
    {
        return recurLeafCount(root);
    }
        //Recursive mathod for getting leaf count (private)
        private int recurLeafCount(Node node)
        {
            if (node == null)
                return 0;
            if (node.left == null && node.right == null)
                return 1;
            else
                return recurLeafCount(node.left) + recurLeafCount(node.right);
        }

    //Finds smallest weight (traverses all to find weight)
    public int minWeight() 
    { 
        if (root == null) return -1; // For empty trees
        minWeight = root.data; //Needed so there is somewhere to start
        modRecurPrintPreOrder(root);
        return minWeight; 
    }
        //Recursive Method that set the instance var for min weight
        //Modified Pre-Order traversal
        private void modRecurPrintPreOrder(Node currNode)
        {
            if(currNode == null){return;}
       
            if (currNode.data < minWeight)
                minWeight = currNode.data;
            modRecurPrintPreOrder(currNode.left);
            modRecurPrintPreOrder(currNode.right);
        }
    

    //Method that returns first alphabetical name
    public String firstAlphaName()
    {
        if(root == null) //For empty trees
            return "Root is empty, no name found.";
        
        Node curr = root;
        //just goes to the very left node b/c its a BST
        while(curr.left != null)
            curr = curr.left;
        return curr.name;
    }
        
}

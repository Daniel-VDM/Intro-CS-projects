package program2fromfile;

/**
 * @author Daniel van der Maden
 */
public class TwoLinkList 
{
     /**
     *  Node Class
     */
    private static class Node
    {
        protected int weight;
        protected String name;
        protected Node nextName, nextWeight;
              
        public Node(String name, int weight)
        {
            this.weight = weight;
            this.name = name;
            nextName = nextWeight = null;
        }
    }
    
    //Instance Var
    private Node headName, headWeight;
    
    //Default Constructor
    public TwoLinkList()
    {
        headName = headWeight= null;
    }
 
    //Adding to list
    public void addToList(String Name, int Data)
    {
        Node temp = new Node(Name,Data);
        
        //Setting Weight Links
        if (headWeight  ==  null || headWeight.weight > Data)
        {
            temp.nextWeight =  headWeight;
            headWeight = temp;
        }
        else if(headWeight.nextWeight == null)
        {
            if (headWeight.weight > Data )
            {
                temp.nextWeight =  headWeight;
                headWeight = temp;
            }
            else
                headWeight.nextWeight = temp;
        }
        else
        {
            Node curr = headWeight.nextWeight, prev = headWeight;
            while (curr != null && (curr.weight <= Data)) 
            {
                prev = curr;
                curr = curr.nextWeight;
            }
        
            temp.nextWeight = curr;
            prev.nextWeight = temp;
        }
        
        //Setting Name Links
        if (headName  ==  null || headName.name.compareTo(Name) >= 0)
        {
            temp.nextName =  headName;
            headName = temp;
        }
        else if(headName.nextName == null)
        {
            if (headName.name.compareTo(Name) >= 0)
            {
                temp.nextName =  headName;
                headName = temp;
            }
            else
                headName.nextName = temp;
        }
        else
        {
            Node curr = headName.nextName, prev = headName;
            while (curr != null && (curr.name.compareTo(Name) < 0)) 
            {
                prev = curr;
                curr = curr.nextName;
            }
        
            temp.nextName = curr;
            prev.nextName = temp;
        }
        
    }
    
    /*
        Print Method
        @param choice 'W' or 'w' = print weight order
                      'N' or 'n' = print name order
    */
    public void print(char choice)
    {
       switch(choice)
       {
            case 'W':case'w':
                System.out.print("Weight order: ");
                Node tempW = headWeight;
                while (tempW != null)
                {
                    System.out.print(tempW.name + " - " + tempW.weight + ", ");
                    tempW = tempW.nextWeight;
                }
                break;
            case 'N':case'n':
                System.out.print("Name order: ");
                Node tempN = headName;
                while (tempN != null)
                {
                    System.out.print(tempN.name + " - " + tempN.weight + ", ");
                    tempN = tempN.nextName;
                }
                break;
            default:
                System.out.println("Invalid choice for print order");
       }
       System.out.println();
    }
}


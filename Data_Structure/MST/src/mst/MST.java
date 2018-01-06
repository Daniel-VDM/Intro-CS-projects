package mst;
import java.util.*;
import java.io.IOException;

/**
 * @author Daniel Van Der Maden
 */
public class MST {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        Graph MainGraph =  new Graph("data.txt");
        HashMap<Integer, Vertex> Graph =  MainGraph.getGraph();
        GraphSorter test = new GraphSorter(Graph);
        
        System.out.println("#########################");
        System.out.println("## All Edges for Graph ##");
        System.out.println("#########################\n");
        System.out.println(MainGraph);
        
        
        System.out.println("#########################");
        System.out.println("## All MSTs  for Graph ##");
        System.out.println("#########################\n");
                
        for (int i =1;i<=6;i++){
            System.out.println("Starting on vertex: " + i);
            test.findMST_Prim(i);
            System.out.println(test);
            System.out.println("----------------");         
        }

    }
    
}

/*OUTPUT
#########################
## All Edges for Graph ##
#########################

1->2	W: 1
1->3	W: 2
2->1	W: 1
2->3	W: 1
2->4	W: 1
2->5	W: 2
3->1	W: 2
3->2	W: 1
3->6	W: 1
3->4	W: 2
4->2	W: 1
4->3	W: 2
4->5	W: 2
4->6	W: 1
5->2	W: 2
5->4	W: 2
5->6	W: 1
6->3	W: 1
6->4	W: 1
6->5	W: 1

#########################
## All MSTs  for Graph ##
#########################

Starting on vertex: 1
1->2
2->3
2->4
3->6
6->5
END | Weight: 5
----------------
Starting on vertex: 2
2->1
2->3
2->4
3->6
6->5
END | Weight: 5
----------------
Starting on vertex: 3
3->2
3->6
2->1
2->4
6->5
END | Weight: 5
----------------
Starting on vertex: 4
4->2
4->6
2->1
2->3
6->5
END | Weight: 5
----------------
Starting on vertex: 5
5->6
6->3
6->4
3->2
2->1
END | Weight: 5
----------------
Starting on vertex: 6
6->3
6->4
6->5
3->2
2->1
END | Weight: 5
----------------
BUILD SUCCESSFUL (total time: 0 seconds)
*/
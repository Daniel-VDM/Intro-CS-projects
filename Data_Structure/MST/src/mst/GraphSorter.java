package mst;
import java.util.*;

/**
 *
 * @author Daniel Van Der Maden
 */
public class GraphSorter {
    
    private final HashMap<Integer,Vertex> graphMap;
    private ArrayList<Edge> mstPath;
    
    //Used for Prim's Algorithm
    private ArrayList<Vertex> seenVertex;
    private ArrayList<Edge> seenEdge;
    
    //Constructor - Clone so you cannot mutate the graph inbetween sorts
    GraphSorter(HashMap<Integer,Vertex> graphMap){
        this.graphMap = (HashMap<Integer, Vertex>) graphMap.clone();
    }
    
    /**
     * Method for finding the MST using Prim's method.
     * Clones the graph so that we don't mutate instance graph 
     * for future implementations for different methods
     * 
     * Also creates new ArrayLists to be used through the Algorithm
     * 
     * @param start the vertex you want to start at
     */
    public void findMST_Prim(int start){
        HashMap<Integer,Vertex> graph;
        seenVertex = new ArrayList<>();
        seenEdge = new ArrayList<>();
        graph = (HashMap<Integer,Vertex>)graphMap.clone();
        
        seenVertex.add(graph.remove(start));
        
        while(!graph.isEmpty()){
            Edge addE = findLightestEdge();
            seenEdge.add(addE);
            seenVertex.add(graph.remove(addE.getEnd().getLabel()));
        }
        
        //Sets the instance version of a sorted path
        mstPath = (ArrayList<Edge>) seenEdge.clone();
    }
    
    /**
     * Helper method for the findMST_Prim
     * Contain core logic for Prim's Algorithm
     * 
     * @return Lightest Edge found in current state of path
     */
    private Edge findLightestEdge(){
        Edge temp = null;
        for (Vertex seen: seenVertex){
            for (Edge newEdge : seen.getConnected()){
                //The core logic of prim's Algorithm
                if(!seenVertex.contains(newEdge.getEnd()))
                    if (temp == null || newEdge.getWeight() < temp.getWeight())
                        temp = newEdge;
            }
        }
        return temp;
    }
    
    /**
     * @return String representation of the mstPath
     */
    @Override
    public String toString(){
        if (mstPath == null || mstPath.isEmpty())
            return "Graph not sorted";
        String order = "";
        int weight = 0;
        for (Edge e : mstPath){
            order += e.getStart().getLabel() + "->" 
                     + e.getEnd().getLabel() + "\n";
            weight += e.getWeight();
        }
        return order + "END | Weight: " + weight;
    }
    
}

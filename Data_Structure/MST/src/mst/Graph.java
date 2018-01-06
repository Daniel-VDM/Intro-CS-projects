package mst;
import java.util.*;
import java.io.*;
/**
 * Creates a HashMap to represent a graph
 * 
 * File input must look like following for each line:
 * Start End Weight
 * 1 2 5\n
 * 
 * ie: vertex 1 is connected to vertex 2 with a weight of 5
 * so..vertex 2 is connected to vertex 1 with a weight of 5
 * 
 * @author Daniel van der Maden
 */
public class Graph {
    //Internal Data Structure used to process the graph
    private final HashMap<Integer, Vertex> vertexMap;
    
    //Constructor
    Graph(String FileName) throws IOException{
        vertexMap = new HashMap<>();
        createGraph(FileName);
    }
    
    /**
     * Method used to process a file and convert it to a graph
     * @param FileName the name of the file
     * @throws IOException if the file is not found
     */
    private void createGraph(String FileName) throws IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {
            String line = br.readLine();
            while (line != null){
                addToMap(line);
                line = br.readLine();
            }
        }
    }
    
    /**
     * Converts a string version of the vertex / weight relation (edge)
     * to a representation of vertices with weighted connections in the HashMap.
     * @param rawVertex the string representation of an edge
     */
    private void addToMap(String rawVertex){
        String[] cleanVertex = rawVertex.split(" ");
        int Start = Integer.parseInt(cleanVertex[0]);
        int End = Integer.parseInt(cleanVertex[1]);
        int Weight = Integer.parseInt(cleanVertex[2]);

        Vertex startV = getVertex(Start);
        Vertex endV = getVertex(End);

        startV.addEdge(endV, Weight);
        endV.addEdge(startV, Weight);

    }
    
    /**
     * Helper method for addToMap to fetch a vertex in the map
     * @param label the vertex we are looking for
     * @return vertex object
     */
    private Vertex getVertex(int label){
        Vertex temp = vertexMap.get(label);
        //Recall that adding a vertex obj the map just adds a pointer to the obj
        //So u can mutate the object in the map so long as you have a pointer to
        //it, which is what temp is returning / creating and returning
        if (temp == null){
            temp = new Vertex(label);
            vertexMap.put(label, temp);
        }
        return temp;
    }
    
    /**
     * @return a clone of the HashMap instance variable 
     */
    public HashMap<Integer, Vertex> getGraph() { 
        return (HashMap<Integer, Vertex>)vertexMap.clone();
    }
    
    /**
     * @return A String representation of all the edges in the graph
     */
    @Override
    public String toString(){
        if (vertexMap == null || vertexMap.isEmpty())
            return "!--No Graph--!";
        
        String outPut = "";
        Iterator mapIter = vertexMap.keySet().iterator();
        while (mapIter.hasNext()){
            Vertex temp = vertexMap.get((Integer)mapIter.next());
            for(Edge e : temp.getConnected()){
                outPut += e.getStart().getLabel() + "->" 
                        + e.getEnd().getLabel() 
                        + "\tW: " + e.getWeight() + "\n";
            }
        }
        return outPut;
    }
    
}

package mst;
import java.util.ArrayList;
/**
 * Simple Vertex Object, works with the edge object
 * @author Daniel Van Der Maden
 */
public class Vertex {
    private final int label;
    private final ArrayList<Edge> connected;
    
    Vertex (int label){
        this.label = label;
        connected = new ArrayList<>();
    }
    
    //Mutator
    public void addEdge(Vertex v, int weight){
        connected.add(new Edge(this, v, weight));
    }
    
    //Accessors
    public int getLabel()                   { return label; }
    public ArrayList<Edge> getConnected ()  { return connected; }
    
}

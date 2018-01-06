package mst;
/**
 * Simple edge object. Works with a vertex object
 * @author Daniel
 */
public class Edge {
    private final Vertex Start, End;
    private final int weight;
    
    Edge(Vertex Start, Vertex End, int weight){
        this.Start = Start;
        this.End = End;
        this.weight = weight;
    }
    
    //Accessors for an Edge
    public Vertex getStart()    { return Start; }
    public Vertex getEnd()      { return End;   }
    public int getWeight()      { return weight;}
    
}

package eg.edu.alexu.csd.filestructure.graphs;

import java.io.File;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        Graph g=new Graph();
        g.readGraph(new File("D:\\Study\\2nd semester\\Data structure II\\Graph\\res\\graph_5_5.txt"));
        ArrayList<Integer> a=g.getVertices();
        int d[] = new int[5];
        boolean bb= g.runBellmanFord(0,d);
        
    }

}

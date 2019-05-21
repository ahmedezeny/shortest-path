package eg.edu.alexu.csd.filestructure.graphs;

import java.io.File;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        IGraph g=new Graph();
        g.readGraph(new File("D:\\Study\\2nd semester\\Data structure II\\Graph\\res\\graph_500_250.txt"));
        ArrayList<Integer> a=g.getVertices();
        int d[] = new int[500];
        boolean bb= g.runBellmanFord(0,d);
        int n=g.size();
        int[] testGraphsVertices = new int[] { 5, 9, 6, 8};
        //testGraphsVertices=g.getVertices();
    }

}

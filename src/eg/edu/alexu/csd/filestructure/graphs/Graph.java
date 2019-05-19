package eg.edu.alexu.csd.filestructure.graphs;

import java.io.*;
import java.util.ArrayList;

public class Graph implements IGraph {

     Graph() {
        v = 0;
        e = 0;
    }

    class edge{
        int to;
        int cost;
         edge(int to,int cost){
            this.to=to;
            this.cost=cost;
        }
    }

    private ArrayList<edge>[] adj;
    private int v,e;
    @Override
    public void readGraph(File file) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String[] dummy;
            dummy= br.readLine().split("\\s");
            v=Integer.parseInt(dummy[0]);
            e=Integer.parseInt(dummy[1]);
            adj = new ArrayList[ v];
            for (int i = 0; i < v; i++) {
                adj[i] = new ArrayList<edge>();
            }
            for (int i =0;i<e;i++){
                dummy= br.readLine().split("\\s");
                edge ed=new edge(Integer.parseInt(dummy[1]),Integer.parseInt(dummy[2]));
                adj[Integer.parseInt(dummy[0])].add(ed);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int size() {
        int count=0;

        for(int i=0;i<adj.length;i++){
            if(adj[i].size()>0)
                count++;
        }
        return count;
    }

    @Override
    public ArrayList<Integer> getVertices() {
        ArrayList<Integer> a=new ArrayList<Integer>(adj.length);
        for(int i=0;i<adj.length;i++){
            if(adj[i].size()>0)
                a.add(i);
        }
        return a;
    }

    @Override
    public ArrayList<Integer> getNeighbors(int v) {
        ArrayList<Integer> a=new ArrayList<Integer>(adj[v].size());
        for(int i=0;i<adj[v].size();i++){
            a.add(adj[v].get(i).to);
        }
        return a;
    }

    @Override
    public void runDijkstra(int src, int[] distances) {

    }

    @Override
    public ArrayList<Integer> getDijkstraProcessedOrder() {
        return null;
    }

    @Override
    public boolean runBellmanFord(int src, int[] distances) {
        distances= new int[v];
        java.util.Arrays.fill(distances, Integer.MAX_VALUE);
        distances[src]=0;

        for(int i=0;i<v-1;i++)
            for(int ii=0;ii<adj.length;ii++)
                for(int iii=0;iii<adj[ii].size();iii++)
                    if(distances[ii]+adj[ii].get(iii).cost<distances[adj[ii].get(iii).to])
                        distances[adj[ii].get(iii).to]=distances[ii]+adj[ii].get(iii).cost;

        for(int i=0;i<v-1;i++)
            for(int ii=0;ii<adj.length;ii++)
                for(int iii=0;iii<adj[ii].size();iii++)
                    if(distances[ii]+adj[ii].get(iii).cost<distances[adj[ii].get(iii).to])
                       return false;

        return true;
    }
}

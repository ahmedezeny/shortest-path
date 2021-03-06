package eg.edu.alexu.csd.filestructure.graphs;

import javax.management.RuntimeErrorException;
import java.io.*;
import java.util.ArrayList;

public class Graph implements IGraph {

    Graph() {
        v = 0;
        e = 0;
    }

    class edge {
        int to;
        int cost;

        edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    private ArrayList<ArrayList<edge>> adj;
    private int v, e;
    private ArrayList<Integer> djk;

    @Override
    public void readGraph(File file) {

        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String[] dummy;
            dummy = br.readLine().split(" ");
            v = Integer.parseInt(dummy[0]);
            e = Integer.parseInt(dummy[1]);
            adj = new ArrayList<>(v);
            for (int i = 0; i < v; i++) {
                adj.add(new ArrayList<>());
            }
            String s;
            int i;
            for (i = 0; (s = br.readLine()) != null; i++) {
                if (i > e) {
                    throw new RuntimeErrorException(new Error());
                }
                //String s=br.readLine().trim();
                if (s.isEmpty()) {
                    throw new RuntimeErrorException(new Error());
                }
                dummy = s.split(" ");
                edge ed = new edge(Integer.parseInt(dummy[1]), Integer.parseInt(dummy[2]));
                if (adj.get(Integer.parseInt(dummy[0])).isEmpty())
                    adj.set(Integer.parseInt(dummy[0]), new ArrayList<>());
                adj.get(Integer.parseInt(dummy[0])).add(ed);
            }
            if (i < e) {
                throw new RuntimeErrorException(new Error());
            }
            br.close();
        } catch (FileNotFoundException exception) {
            System.out.println("The file " + file.getPath() + " was not found.");
            throw new RuntimeErrorException(new Error());
        } catch (Exception e) {
            throw new RuntimeErrorException(new Error());
        }


    }

    @Override
    public int size() {
        int count = 0;

        for (ArrayList<edge> anAdj : adj) {
            if (anAdj.size() > 0)
                count += anAdj.size();
        }
        return count;
    }

    @Override
    public ArrayList<Integer> getVertices() {
        ArrayList<Integer> a = new ArrayList<>(adj.size());
        for (int i = 0; i < adj.size(); i++) {
            a.add(i);
        }
        return a;
    }

    @Override
    public ArrayList<Integer> getNeighbors(int v) {
        ArrayList<Integer> a = new ArrayList<>(adj.get(v).size());
        for (int i = 0; i < adj.get(v).size(); i++) {
            a.add(adj.get(v).get(i).to);
        }
        return a;
    }

    @Override
    public void runDijkstra(int src, int[] distances) {

        Boolean sptSet[] = new Boolean[v];

        for (int i = 0; i < v; i++) {
            distances[i] = Integer.MAX_VALUE / 2;
            sptSet[i] = false;
        }

        distances[src] = 0;
        djk = new ArrayList<>();
        for (int count = 0; count < v - 1; count++) {
            int u = findMin(distances, sptSet);
            djk.add(u);
            sptSet[u] = true;

            for (int vv = 0; vv < v; vv++) {
                int uv = findEdge(u, vv);
                if (!sptSet[vv] && uv != 0 && distances[u] != Integer.MAX_VALUE && distances[u] + uv < distances[vv])
                    distances[vv] = distances[u] + uv;
            }
        }
        djk.add(findMin(distances, sptSet));
    }

    private int findEdge(int u, int v) {
        for (edge e : adj.get(u)) {
            if (e.to == v) return e.cost;
        }
        return 0;
    }

    private int findMin(int[] distances, Boolean[] sptSet) {

        int min = Integer.MAX_VALUE, min_index = -1;

        for (int vv = 0; vv < v; vv++)
            if (!sptSet[vv] && distances[vv] <= min) {
                min = distances[vv];
                min_index = vv;
            }

        return min_index;
    }

    @Override
    public ArrayList<Integer> getDijkstraProcessedOrder() {
        return djk;
    }

    @Override
    public boolean runBellmanFord(int src, int[] distances) {
        //   java.util.Arrays.fill(distances, Integer.MAX_VALUE);

        for (int i = 0; i < distances.length; i++) {
            distances[i] = Integer.MAX_VALUE / 2;
        }
        distances[src] = 0;
        for (int i = 0; i < v - 1; i++)
            for (int ii = 0; ii < adj.size(); ii++)
                for (int iii = 0; iii < adj.get(ii).size(); iii++)
                    if (distances[ii] + adj.get(ii).get(iii).cost < distances[adj.get(ii).get(iii).to])
                        distances[adj.get(ii).get(iii).to] = distances[ii] + adj.get(ii).get(iii).cost;

        for (int i = 0; i < v - 1; i++)
            for (int ii = 0; ii < adj.size(); ii++)
                for (int iii = 0; iii < adj.get(ii).size(); iii++)
                    if (distances[ii] + adj.get(ii).get(iii).cost < distances[adj.get(ii).get(iii).to])
                        return false;

        return true;
    }
}

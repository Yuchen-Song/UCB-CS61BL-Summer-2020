import java.util.*;

public class Graph implements Iterable<Integer> {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    /* Adds a directed Edge (V1, V2) to the graph. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        adjLists[v1].add(new Edge(v1, v2, weight));
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        adjLists[v1].add(new Edge(v1, v2, weight));
        adjLists[v2].add(new Edge(v2, v1, weight));
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        for(Edge e : adjLists[from]){
            if(e.to == to){
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        List<Integer> neighborList = new ArrayList<Integer>();
        for(Edge e : adjLists[v]){
            neighborList.add(e.to);
        }
        return neighborList;
    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        int numEdges = 0;
        for(int i = 0; i < adjLists.length; i++){
            if(isAdjacent(i , v)){
                numEdges += 1;
            }
        }
        return numEdges;
    }

    public List<Integer> shortestPath(int start, int stop) {
        //Initialization
        List<Integer> shortest = new ArrayList<Integer>();
        HashMap<Integer, Integer> dist = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> pred = new HashMap<Integer, Integer>();
        HashSet<Integer> visited = new HashSet<Integer>();
        PriorityQueue<Integer> fringe = new PriorityQueue<Integer>((v, u) -> dist.get(v).compareTo(dist.get(u)));
        fringe.add(start);
        for(int i = 0; i < vertexCount; i++){
            dist.put(i, Integer.MAX_VALUE);
        }
        dist.put(start, 0);
        //While Loop
        while(!fringe.isEmpty()){
            Integer v = fringe.poll();
            if(!visited.contains(v)){
                visited.add(v);
                for(Edge e : adjLists[v]){
                    int w = e.to;
                    fringe.add(w);
                    if(dist.get(v) + e.weight < dist.get(w)){
                        dist.put(w, dist.get(v) + e.weight);
                        pred.put(w, v);
                    }
                }
            }
        }
        //Construct shortest starting from stop -> pred -> ... -> start
        shortest.add(stop);
        int ptr = pred.get(stop);
        while(ptr != start){
            shortest.add(ptr);
            ptr = pred.get(ptr);
        }
        shortest.add(start);
        Collections.reverse(shortest);
        return shortest;
    }

    public Edge getEdge(int u, int v) {
        for(Edge e : adjLists[u]){
            if(e.to == v){
                return e;
            }
        }
        return null;
    }

    /* Returns an Iterator that outputs the vertices of the graph in topological
       sorted order. */
    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    /**
     *  A class that iterates through the vertices of this graph,
     *  starting with a given vertex. Does not necessarily iterate
     *  through all vertices in the graph: if the iteration starts
     *  at a vertex v, and there is no path from v to a vertex w,
     *  then the iteration will not include w.
     */
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        public DFSIterator(Integer start) {
            fringe = new Stack<>();
            visited = new HashSet<>();
            fringe.push(start);
        }

        public boolean hasNext() {
            if (!fringe.isEmpty()) {
                int i = fringe.pop();
                while (visited.contains(i)) {
                    if (fringe.isEmpty()) {
                        return false;
                    }
                    i = fringe.pop();
                }
                fringe.push(i);
                return true;
            }
            return false;
        }

        public Integer next() {
            int curr = fringe.pop();
            ArrayList<Integer> lst = new ArrayList<>();
            for (int i : neighbors(curr)) {
                lst.add(i);
            }
            lst.sort((Integer i1, Integer i2) -> -(i1 - i2));
            for (Integer e : lst) {
                fringe.push(e);
            }
            visited.add(curr);
            return curr;
        }

        //ignore this method
        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
        }

    }

    /* Returns the collected result of performing a depth-first search on this
       graph's vertices starting from V. */
    public List<Integer> dfs(int v) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(v);

        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    /* Returns true iff there exists a path from START to STOP. Assumes both
       START and STOP are in this graph. If START == STOP, returns true. */
    public boolean pathExists(int start, int stop) {
        for(Integer v : dfs(start)){
            if(v == stop){
                return true;
            }
        }
        return false;
    }


    /* Returns the path from START to STOP. If no path exists, returns an empty
       List. If START == STOP, returns a List with START. */
    public List<Integer> path(int start, int stop) {
        ArrayList<Integer> route = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(start);
        if(!pathExists(start, stop)){
            return route;
        }else if(start == stop){
            route.add(start);
            return route;
        }else{
            while(iter.hasNext()){
                int temp = iter.next();
                route.add(temp);
                if(temp == stop){
                    break;
                }
            }
            int minus = 1;
            int k = route.size() - minus;
            while (k > 0) {
                for (int i = k - 1; i >= 0; i--) {
                    if (isAdjacent(route.get(i), route.get(k))) {
                        break;
                    } else {
                        route.remove(i);
                        k -= 1;
                    }
                }
                minus += 1;
                k = route.size() - minus;
            }
            return route;

            // Still have to implement
        }
    }

    public List<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private int[] currentInDegree;

        TopologicalIterator() {
            fringe = new Stack<Integer>();
            currentInDegree = new int[adjLists.length];
            for(int i = 0; i < adjLists.length; i++){
                currentInDegree[i] = inDegree(i);
                if(currentInDegree[i] == 0){
                    fringe.push(i);
                    currentInDegree[i] -= 1;
                }
            }
        }

        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        public Integer next() {
            Integer v = fringe.pop();
            for(Edge e : adjLists[v]){
                currentInDegree[e.to] -= 1;
            }
            for(int i = 0; i < adjLists.length; i++){
                if(currentInDegree[i] == 0){
                    fringe.push(i);
                    currentInDegree[i] -= 1;
                }
            }
            return v;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private class Edge {

        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

    }

    private void generateG1() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG2() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG3() {
        addUndirectedEdge(0, 2);
        addUndirectedEdge(0, 3);
        addUndirectedEdge(1, 4);
        addUndirectedEdge(1, 5);
        addUndirectedEdge(2, 3);
        addUndirectedEdge(2, 6);
        addUndirectedEdge(4, 5);
    }

    private void generateG4() {
        addEdge(0, 1);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 2);
    }

    private void printDFS(int start) {
        System.out.println("DFS traversal starting at " + start);
        List<Integer> result = dfs(start);
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printPath(int start, int end) {
        System.out.println("Path from " + start + " to " + end);
        List<Integer> result = path(start, end);
        if (result.size() == 0) {
            System.out.println("No path from " + start + " to " + end);
            return;
        }
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printTopologicalSort() {
        System.out.println("Topological sort");
        List<Integer> result = topologicalSort();
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

    public static void main(String[] args) {
        /*
        Graph g1 = new Graph(5);
        g1.generateG1();
        g1.printDFS(0);
        g1.printDFS(2);
        g1.printDFS(3);
        g1.printDFS(4);

        g1.printPath(0, 3);
        g1.printPath(0, 4);
        g1.printPath(1, 3);
        g1.printPath(1, 4);
        g1.printPath(4, 0);

        Graph g2 = new Graph(5);
        g2.generateG2();
        g2.printTopologicalSort();
         */
        Graph g1 = new Graph(5);
        g1.addEdge(0, 1, 10);
        g1.addEdge(0, 3, 30);
        g1.addEdge(0, 4, 100);
        g1.addEdge(1, 2, 50);
        g1.addEdge(2, 4, 10);
        g1.addEdge(3, 2, 20);
        g1.addEdge(3, 4, 60);
        List<Integer> temp = g1.shortestPath(0, 4);
        System.out.println(temp);
    }
}
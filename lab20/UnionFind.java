public class UnionFind {

    public int[] items;

    /* Creates a UnionFind data structure holding N vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int N) {
        items = new int[N];
        for(int i = 0; i < items.length; i++){
            items[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return -items[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return items[v];
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid vertices are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if(v >= items.length){
            throw new IllegalArgumentException();
        }
        if(items[v] < 0){
            return v;
        }else{
            return find(parent(v));
        }
    }

    /* Connects two elements V1 and V2 together. V1 and V2 can be any element,
       and a union-by-size heuristic is used. If the sizes of the sets are
       equal, tie break by connecting V1's root to V2's root. Union-ing a vertex
       with itself or vertices that are already connected should not change the
       structure. */
    public void union(int v1, int v2) {
        if(v1 == v2 || connected(v1, v2)){
            return;
        }
        int rootV1 = find(v1);
        int rootV2 = find(v2);
        if(sizeOf(rootV1) <= sizeOf(rootV2)){
            items[rootV1] = rootV2;
            items[rootV2] -= sizeOf(rootV1);
        } else {
            items[rootV2] = rootV1;
            items[rootV1] -= sizeOf(rootV2);
        }
    }
}
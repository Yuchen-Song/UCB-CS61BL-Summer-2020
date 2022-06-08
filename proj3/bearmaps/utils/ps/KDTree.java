package bearmaps.utils.ps;

import java.util.HashSet;
import java.util.List;

public class KDTree implements PointSet {

    private Node root;
    private HashSet<Point> track;

    public KDTree(List<Point> points){
        track = new HashSet<Point>();
        for(Point p : points){
            add(p);
            track.add(p);
        }
    }

    private void add(Point p){
        if(track.contains(p)){
            return;
        }else if(root == null){
            root = new Node(p, true);
        }else{
            addHelper(root, p, true);
        }
    }

    private Node addHelper(Node n, Point p, boolean compareX) {
        if(n == null){
            return new Node(p, compareX);
        }else if(n.getCompareX()){
            if(Double.compare(n.getPoint().getX(), p.getX()) > 0){
                n.left = addHelper(n.left, p, false);
            }else{
                n.right = addHelper(n.right, p, false);
            }
        }else{
            if(Double.compare(n.getPoint().getY(), p.getY()) > 0){
                n.left = addHelper(n.left, p, true);
            }else{
                n.right = addHelper(n.right, p, true);
            }
        }
        return n;
    }

    @Override
    public Point nearest(double x, double y) {
        return nearestHelper(root, new Point(x, y), root).getPoint();
    }

    private Node nearestHelper(Node n, Point goal, Node best){
        if(n == null){
            return best;
        }
        double distCG = Point.distance(n.getPoint(), goal);
        double distBG = Point.distance(best.getPoint(), goal);
        if(Double.compare(distCG, distBG) < 0){
            best = n;
        }
        // Choose Good Side Bad Side
        Node goodSide;
        Node badSide;
        if(n.getCompareX()){
            if(Double.compare(n.getPoint().getX(),goal.getX()) > 0){
                goodSide = n.left;
                badSide = n.right;
            }else{
                goodSide = n.right;
                badSide = n.left;
            }
        }else{
            if(Double.compare(n.getPoint().getY(), goal.getY()) > 0){
                goodSide = n.left;
                badSide = n.right;
            }else{
                goodSide = n.right;
                badSide = n.left;
            }
        }
        best = nearestHelper(goodSide, goal ,best);
        //best = nearestHelper(badSide, goal, best);

        //fixed the wired bug due to the given Point.distance method
        //@Andrew Zhang on his post at https://us.edstem.org/courses/630/discussion/100653 inspires me a lot
        if (n.getCompareX() &&
                Double.compare(Math.abs(goal.getX() - n.getPoint().getX()),Math.sqrt(distBG)) < 0) {
            best = nearestHelper(badSide, goal, best);
        } else if (!n.getCompareX() &&
                Double.compare(Math.abs(goal.getY() - n.getPoint().getY()),Math.sqrt(distBG)) < 0) {
            best = nearestHelper(badSide, goal, best);
        }


        return best;
    }

    private class Node {
        private Point p;
        private Node left;
        private Node right;
        private boolean compareX; // true if compared by X value

        public Node(Point p, boolean cmp) {
            this.p = p;
            this.left = null;
            this.right = null;
            this.compareX = cmp;
        }
        public Point getPoint(){
            return this.p;
        }
        public Node getLeft(){
            return this.left;
        }
        public Node getRight(){
            return this.right;
        }
        public boolean getCompareX() {
            return this.compareX;
        }
    }

}

/** A class that represents a path via pursuit curves. */
public class Path {

    Point curr = new Point(0,0);
    Point next = new Point(0,0);//remember to initialize the instance variables

    public Path(double x, double y) {
        next.setX(x);
        next.setY(y);
    }

    double getCurrX(){ return curr.getX(); }
    double getCurrY(){ return curr.getY(); }
    double getNextX(){ return next.getX(); }
    double getNextY(){ return next.getY(); }

    Point getCurrentPoint(){ return curr; }
    void setCurrentPoint(Point point){
        curr.setX(point.getX());
        curr.setY(point.getY());
    }

    void iterate(double dx, double dy){
        setCurrentPoint(next);
        next.setX(curr.getX() + dx);
        next.setY(curr.getY() + dy);
    }

}
package bearmaps.utils.ps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    //@source Professor Hug's Test Cases from Video

    private static Random r = new Random(5000);

    private static KDTree buildLectureTree(){
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        return new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
    }

    @Test
    public void buildTreeTest(){
        buildLectureTree();
    }

    private Point randomPoint(){
        double x = r.nextDouble() * 100;
        double y = r.nextDouble() * 100;
        return new Point(x, y);
    }

    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>();
        for(int i = 0; i < N; i++){
            points.add(randomPoint());
        }
        return points;
    }

    @Test
    public void NaivePointSetTest(){
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        assertEquals(ret.getX(), 3.3, -1); // evaluates to 3.3
        assertEquals(ret.getY(), 4.4, -1); // evaluates to 4.4
    }

    @Test
    public void testNearestDemoSlides(){
        KDTree kd = buildLectureTree();
        Point actual = kd.nearest(0, 7);
        Point expected = new Point(1, 5);
        assertEquals(expected, actual);
    }

    public void testWithNPointsAndQQueries(int N, int Q){
        List<Point> points = randomPoints(N);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> queries = randomPoints(Q);
        for(Point p : queries){
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testWith10PointsAnd2Queries(){
        testWithNPointsAndQQueries(10, 2);
    }

    @Test
    public void testWith100PointsAnd20Queries(){
        testWithNPointsAndQQueries(100, 20);
    }

    @Test
    public void testWith1000PointsAnd200Queries(){
        testWithNPointsAndQQueries(1000, 200);
    }

    @Test
    public void testWith10000PointsAnd2000Queries(){
        testWithNPointsAndQQueries(10000, 2000);
    }

}
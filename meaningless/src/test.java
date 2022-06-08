public class test {
    public static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class PointList {
        public Point p;
        public PointList next;

        public PointList(Point p, PointList next) {
            this.p = p;
            this.next = next;
        }
    }
    public static void main(String[] args) {
        int x = 3;
        int y = 4;
        Point p = new Point(x, y);
        PointList lst = new PointList(p, null);
        lst.next = new PointList(p, this);
    }
}

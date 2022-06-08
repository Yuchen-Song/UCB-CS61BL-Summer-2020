

public class drawBackground {

    public static void main(String[] args) {

        String starfield = "../images/starfield.jpg";
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(0,1000);
        StdDraw.clear();
        StdDraw.picture(500, 500, starfield);
        StdDraw.show();
        StdDraw.pause(2000);

    }

}
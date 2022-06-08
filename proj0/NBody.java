public class NBody{
    //this has to be static because the test uses a static method
    public static double readRadius(String filename){
        In in = new In(filename);
        int number = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Body[] readBodies(String filename){
        In in = new In(filename);
        int bodyNum = in.readInt();
        Body[] bodies = new Body[bodyNum];
        double radius = in.readDouble();
        //I use for loop instead of while mainly because some extra info behind the data in 3body.txt
        for(int index=0; index< bodyNum; index++){
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            bodies[index] = new Body(xxPos,yyPos,xxVel,yyVel,mass,imgFileName);
        }
        //and this one is not fully correct by the way, I had to allocate space for every Body
        //int index = 0;
        /*while(!in.isEmpty()){
            bodies[index].xxPos = in.readDouble();
            bodies[index].yyPos = in.readDouble();
            bodies[index].xxVel = in.readDouble();
            bodies[index].yyVel = in.readDouble();
            bodies[index].mass = in.readDouble();
            bodies[index].imgFileName = in.readString();
            index++;
        }*/
        return bodies;
    }

    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);//another way is Double.valueOf
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Body[] bodies = readBodies(filename);


        //animation part
        StdDraw.enableDoubleBuffering();//prevent flickering in the animation

        int time = 0;
        while(time < T){
            double[] xForces = new double[bodies.length];
            double[] yForces = new double[bodies.length];
            for(int i=0; i<bodies.length; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }

            //instrctions asked me to do so(after storing all the data to x&yForces)
            for(int i=0; i<bodies.length; i++){
                bodies[i].update(dt, xForces[i], yForces[i]);
            }
            //draw background
            String starfield = "images/starfield.jpg";
            StdDraw.setScale(-radius,radius);
            StdDraw.clear();
            StdDraw.picture(0, 0, starfield);
            //drawing bodies
            for(Body b : bodies){
                b.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }

        //print out the final state of the universe
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i += 1) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }
    }
}
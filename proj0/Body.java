import java.lang.Math;

public class Body{
    private static final double G=6.67e-11;//gravity constant
    public double xxPos;    //The body’s current x position
    public double yyPos;    //The body’s current y position
    public double xxVel;    //The body’s current velocity in the x direction
    public double yyVel;    //The body’s current velocity in the y direction
    public double mass;    //The body’s mass
    public String imgFileName;    //The name of the file that corresponds to the image that depicts the body (for example, jupiter.gif)

    public Body(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Body other){
        double distance;
        double dx = this.xxPos - other.xxPos;
        double dy = this.yyPos - other.yyPos;
        distance = Math.pow((dx * dx + dy * dy) , 0.5);//recommended against using Math.pow
        // because it will be slower than manually multiplying two numbers together
        return distance;
    }

    public double calcForceExertedBy(Body other){
        double F;
        double d = calcDistance(other);
        F = G * this.mass * other.mass/(d*d);
        return F;
    }

    public double calcForceExertedByX(Body other){
        double F = calcForceExertedBy(other);
        double dx = other.xxPos - this.xxPos;
        double r = calcDistance(other);
        double Fx;
        Fx = F * dx / r;
        return Fx;
    }

    public double calcForceExertedByY(Body other){
        double F = calcForceExertedBy(other);
        double dy = other.yyPos - this.yyPos;
        double r = calcDistance(other);
        double Fy;
        Fy = F * dy / r;
        return Fy;
    }

    public double calcNetForceExertedByX(Body[] bodies){
        double netFx = 0;
        for(Body body : bodies){
            if(!body.equals(this)){
                netFx += calcForceExertedByX(body);
            }
            else{continue;}
        }
        return netFx;
    }

    public double calcNetForceExertedByY(Body[] bodies){
        double netFy = 0;
        for(Body body : bodies){
            if(!body.equals(this)){
                netFy += calcForceExertedByY(body);
            }
            else{continue;}
        }
        return netFy;
    }

    //update the velocity and position with provided time dt, net forces fx &fy
    public void update(double dt, double fX, double fY){
        double ax = fX/this.mass;
        double ay = fY/this.mass;
        this.xxVel += ax * dt;
        this.yyVel += ay * dt;
        this.xxPos += xxVel * dt;
        this.yyPos += yyVel * dt;
    }

    //drawing method: draw the body
    public void draw(){
        //StdDraw.enableDoubleBuffering();
        //StdDraw.setScale(0,radius);
        //StdDraw.clear();
        StdDraw.picture(xxPos, yyPos, "images/"+imgFileName);
        //StdDraw.show();
        //StdDraw.pause(2000);
    }
}
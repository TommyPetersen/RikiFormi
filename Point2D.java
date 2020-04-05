package RikiFormi;

import java.awt.*;

public class Point2D{

    public double x, y;

    public Point2D(double x, double y){
	this.x = x;
	this.y = y;
    }

    public Point2D(Point2D otherPoint){
	this.x = otherPoint.x;
	this.y = otherPoint.y;
    }

    public boolean strictlyLessThan(Point2D otherPoint){
	return (x < otherPoint.x) || ((x == otherPoint.x) && (y < otherPoint.y));
    }

    public String toString(){
    	return "(" + x + ", " + y +")";
    }
}

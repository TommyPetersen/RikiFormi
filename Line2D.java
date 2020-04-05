package RikiFormi;

public class Line2D{

    public Point2D P0, P1;

    public Line2D(Point2D P0, Point2D P1){
	this.P0 = new Point2D(P0);
	this.P1 = new Point2D(P1);
    }

    public String toString(){
	return P0.toString() + ", " + P1.toString();
    }
}

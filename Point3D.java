package RikiFormi;

import java.awt.*;
import java.io.Serializable;

public class Point3D implements Serializable {

    public double x, y, z, zBeforeProjection, distanceBeforeProjection;
    public Color color;

    public Point3D(double _x, double _y, double _z){
	x = _x;
	y = _y;
	z = _z;
	zBeforeProjection = _z;
	distanceBeforeProjection = Math.sqrt(x * x + y * y + z * z);
	color = new Color(0.0f, 0.0f, 1.0f);
    }

    public Point3D(double _x, double _y, double _z, Color _color){
	x = _x;
	y = _y;
	z = _z;
	zBeforeProjection = _z;
	distanceBeforeProjection = Math.sqrt(x * x + y * y + z * z);
	color = _color;
    }

    public Point3D(double _x, double _y, double _z,
		   double _zBeforeProjection, double _distanceBeforeProjection,
		   Color _color){
	x = _x;
	y = _y;
	z = _z;
	zBeforeProjection = _zBeforeProjection;
	distanceBeforeProjection = _distanceBeforeProjection;
	color = _color;
    }

    public Point3D(Point3D otherPoint){
	x = otherPoint.x;
	y = otherPoint.y;
	z = otherPoint.z;
	zBeforeProjection = otherPoint.zBeforeProjection;
	distanceBeforeProjection = otherPoint.distanceBeforeProjection;
	color = otherPoint.color;
    }

    public boolean equals(Point3D otherPoint){
	return
	    ((int)(x * 10000.0)) == ((int)(otherPoint.x * 10000.0)) &&
	    ((int)(y * 10000.0)) == ((int)(otherPoint.y * 10000.0)) &&
	    ((int)(z * 10000.0)) == ((int)(otherPoint.z * 10000.0));
    }

    public boolean strictlyLessThan(Point3D otherPoint){
	return (x < otherPoint.x) || ((x == otherPoint.x) && (y < otherPoint.y));
    }

    public double distance(){
	return Math.sqrt(x * x + y * y + z * z);
    }

    public String toString(){
    	return "(" + x + ", " + y + ", " + z +")";
    }
}

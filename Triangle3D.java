package RikiFormi;

import java.util.*;
import java.io.Serializable;

public class Triangle3D extends Polygon3D implements Serializable{

    //DoToo: Remove P0, P1 and P2:
    public Point3D P0, P1, P2;

    public Triangle3D(Point3D _P0, Point3D _P1, Point3D _P2){
	super();
	Point3dList.add(_P0);
	Point3dList.add(_P1);
	Point3dList.add(_P2);
	P0 = new Point3D(_P0);
	P1 = new Point3D(_P1);
	P2 = new Point3D(_P2);
    }

    public boolean equals(Triangle3D otherTriangle){
	return Point3dList.get(0).equals(otherTriangle.Point3dList.get(0))
	    && Point3dList.get(1).equals(otherTriangle.Point3dList.get(1))
	    && Point3dList.get(2).equals(otherTriangle.Point3dList.get(2));
    }
}

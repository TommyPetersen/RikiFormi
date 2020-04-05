package RikiFormi;

import java.util.*;
import java.io.Serializable;

public class Polygon3D implements Serializable{

    public ArrayList<Point3D> Point3dList;

    public Polygon3D(){
	Point3dList = new ArrayList<Point3D>();
    }

    public Polygon3D(ArrayList<Point3D> point3dList){
	Point3dList = point3dList;
    }

    public void addPoint(Point3D point3d){
	Iterator<Point3D> pointIterator = Point3dList.iterator();

	while (pointIterator.hasNext()){
	    if (point3d.equals(pointIterator.next())){
		return;
	    }
	}
	
	Point3dList.add(new Point3D(point3d));
    }

    public ArrayList<Point3D> getCopyOfPoint3dList(){
	ArrayList<Point3D> point3dListCopy = new ArrayList<Point3D>();
	
	Iterator<Point3D> IL = Point3dList.iterator();

	while (IL.hasNext()){
	    point3dListCopy.add(new Point3D(IL.next()));
	}

	return point3dListCopy;
    }

    public ArrayList<Line3D> toLine3dList(){
	Iterator<Point3D> IL = Point3dList.iterator();
	
	ArrayList<Line3D> lines = new ArrayList<Line3D>();
	
	if (IL.hasNext()){
	    Point3D p0, pi, pj;
	    
	    p0 = pi = (Point3D) IL.next();

	    while (IL.hasNext()){
		pj = (Point3D) IL.next();

		lines.add(new Line3D(pi, pj));

		pi = pj;
	    }

	    lines.add(new Line3D(pi, p0));
	}

	return lines;
    }

    public Iterator<Point3D> iterator(){
	return Point3dList.iterator();
    }

    public String toString(){	
	String s = "";

	Iterator<Point3D> IL = Point3dList.iterator();
	
	if (IL.hasNext()){
	    s = ((Point3D) IL.next()).toString();
	}

	while (IL.hasNext()){
	    s = s + ", " + ((Point3D) IL.next()).toString();
	}

	return s;
    }
}

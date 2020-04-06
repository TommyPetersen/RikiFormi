package RikiFormi.Utils;

import java.io.*;
import java.awt.*;
import java.util.*;
import java.time.*;
import RikiFormi.*;
import java.lang.Math;

public class Util3d{

    Util3d(){
    }

    public static Color makeInterpolatedColor(Color c0, Color c1, double t){
    	float[] components0 = c0.getRGBColorComponents(null);
    	float cr0 = components0[0]; float cg0 = components0[1]; float cb0 = components0[2];

    	float[] components1 = c1.getRGBColorComponents(null);
    	float cr1 = components1[0]; float cg1 = components1[1]; float cb1 = components1[2];
	
	float cr = ((float)(1.0 - t)) * cr0 + ((float)t) * cr1;
	float cg = ((float)(1.0 - t)) * cg0 + ((float)t) * cg1;
	float cb = ((float)(1.0 - t)) * cb0 + ((float)t) * cb1;

	return new Color(cr, cg, cb);
    }

    public static Point3D makeInterpolatedPoint3D(Point3D p0, Point3D p1, double t){
	return new Point3D((1.0 - t) * p0.x + t * p1.x,
			   (1.0 - t) * p0.y + t * p1.y,
			   (1.0 - t) * p0.z + t * p1.z,			   
			   (1.0 - t) * p0.zBeforeProjection + t * p1.zBeforeProjection,
			   (1.0 - t) * p0.distanceBeforeProjection + t * p1.distanceBeforeProjection,
			   makeInterpolatedColor(p0.color, p1.color, t));
    }
    
    public static OrderedPair<Line3D> splitLineInTwoHalves(Line3D line) throws Exception{
	Point3D p0 = line.P0;
	Point3D p1 = line.P1;

	if (lineIsDegenerate(line)) throw new Exception("Input line is degenerate:\np0: " + line.P0 + "\np1:" + line.P1);

	Point3D halfPoint = makeInterpolatedPoint3D(p0, p1, 0.5);

	Line3D firstLine = new Line3D(p0, halfPoint);
	Line3D secondLine = new Line3D(halfPoint, p1);

	if (lineIsDegenerate(firstLine)) throw new Exception("First line is degenerate: " + "\np0: " + firstLine.P0 + "\np1:" + firstLine.P1);
	if (lineIsDegenerate(secondLine)) throw new Exception("Second line is degenerate: " + "\np0: " + secondLine.P0 + "\np1:" + secondLine.P1);

	return new OrderedPair<Line3D>(firstLine, secondLine);
    }

    private static OrderedPair<Triangle3D> createAnchoredTriangles(OrderedPair<Line3D> halfLines, Point3D anchorPoint){
	return
	    new OrderedPair<Triangle3D>(new Triangle3D(halfLines.getFirst().P0,
						       halfLines.getFirst().P1,
						       anchorPoint),
					new Triangle3D(halfLines.getSecond().P0,
						       halfLines.getSecond().P1,
						       anchorPoint));
    }

    public static <T> ArrayList<T> ensureGranularity(T geom, double epsilon)
	throws Exception{
	ArrayList<T> coarseObjects = new ArrayList<T>();
       	ArrayList<T> subdividedCoarseObject;
	ArrayList<T> finalObjects = new ArrayList<T>();
	ArrayList<T> finerObjects = new ArrayList<T>();

	Iterator<T> coarseObjectsIterator;
	Iterator<T> finerObjectsIterator;

	T finerObject;
	T coarseObject = geom;
	
	coarseObjects.add(coarseObject);

	while (coarseObjects.size() > 0){
	    coarseObjectsIterator = coarseObjects.iterator();

	    while (coarseObjectsIterator.hasNext()){
		coarseObject = coarseObjectsIterator.next();
		if (geomIsFineGrained(coarseObject, epsilon)){
		    finalObjects.add(coarseObject);
		} else {
		    subdividedCoarseObject = subdivideGeomObject(coarseObject, epsilon);
		    //DoToo: Some lines degenerate to single points, why?
		    finerObjects.addAll(subdividedCoarseObject);
		}
	    }

	    coarseObjects = new ArrayList<T>();
	    
	    if (finerObjects.size() > 0){
		finerObjectsIterator = finerObjects.iterator();
		while (finerObjectsIterator.hasNext()){
		    finerObject = finerObjectsIterator.next();
		    coarseObjects.add(finerObject);
		}
		finerObjects = new ArrayList<T>();
	    }
	}

	return finalObjects;
    }

    public static <T> ArrayList<T> ensureGranularity(ArrayList<T> geoms, double epsilon)
	throws Exception{
	ArrayList<T> granularGeomObjects = new ArrayList<T>();

	T geom;
	
	Iterator<T> geomsIterator = geoms.iterator();
	
	while (geomsIterator.hasNext()){
	    geom = geomsIterator.next();
	    granularGeomObjects.addAll(ensureGranularity(geom, epsilon));
	}

 	return granularGeomObjects;
    }

    @SuppressWarnings("unchecked") // Suppress warnings on cast from generic to specific type.
    private static <T> ArrayList<T> subdivideGeomObject(T geom, double epsilon)
	throws Exception{
	OrderedPair<T> tPair = new OrderedPair<T>();

	switch (geom.getClass().getName()){
	case "RikiFormi.Line3D":
	    tPair = (OrderedPair<T>)splitLineInTwoHalves((Line3D) geom);
	    T line1 = tPair.getFirst();
	    T line2 = tPair.getSecond();
	    return new ArrayList<T>(){{add(line1); add(line2);}};
	case "RikiFormi.Triangle3D":
	    tPair = (OrderedPair<T>)subdivideTriangle((Triangle3D) geom, epsilon);
	    T triangle1 = tPair.getFirst();
	    T triangle2 = tPair.getSecond();
	    return new ArrayList<T>(){{add(triangle1); add(triangle2);}};
	case "RikiFormi.Polygon3D":
	    ArrayList<Triangle3D> triangles = doRecursiveTriangulation((Polygon3D) geom);
	    return (ArrayList<T>)triangles;
	default:
	    throw new Exception("Class type '" + geom.getClass().getName() + "' is not supported");
	}
    }
    
    public static OrderedPair<Triangle3D> subdivideTriangle(Triangle3D triangle, double epsilon)
	throws Exception{

	if (epsilon <= 0.0){
	    throw new Exception("Invalid epsilon: " + epsilon +
				". Epsilon must be positive");
	}

	OrderedPair<Triangle3D> triangles = new OrderedPair<Triangle3D>();

	Line3D lineP0_P1 = new Line3D(triangle.P0, triangle.P1);
	Line3D lineP0_P2 = new Line3D(triangle.P0, triangle.P2);
	Line3D lineP1_P2 = new Line3D(triangle.P1, triangle.P2);

	double lineP0_P1_length = lineP0_P1.length();
	double lineP0_P2_length = lineP0_P2.length();
	double lineP1_P2_length = lineP1_P2.length();

	Boolean lineP0_P1_amongLargest = lineP0_P1_length >= lineP0_P2_length && lineP0_P1_length >= lineP1_P2_length;
	Boolean lineP0_P2_amongLargest = lineP0_P2_length >= lineP0_P1_length && lineP0_P2_length >= lineP1_P2_length;
	Boolean lineP1_P2_amongLargest = lineP1_P2_length >= lineP0_P1_length && lineP1_P2_length >= lineP0_P2_length;

	OrderedPair<Triangle3D> anchoredTriangles;

	if (lineP0_P1_amongLargest && lineP0_P1_length > epsilon){
	    anchoredTriangles =
		createAnchoredTriangles(splitLineInTwoHalves(lineP0_P1),
					triangle.P2);
	    triangles = new OrderedPair<Triangle3D>(anchoredTriangles.getFirst(),
						    anchoredTriangles.getSecond());
	} else if (lineP0_P2_amongLargest && lineP0_P2_length > epsilon){
	    anchoredTriangles =
		createAnchoredTriangles(splitLineInTwoHalves(lineP0_P2),
					triangle.P1);
	    triangles = new OrderedPair<Triangle3D>(anchoredTriangles.getFirst(),
						    anchoredTriangles.getSecond());
	} else if (lineP1_P2_amongLargest && lineP1_P2_length > epsilon){
	    anchoredTriangles =
		createAnchoredTriangles(splitLineInTwoHalves(lineP1_P2),
					triangle.P0);
	    triangles = new OrderedPair<Triangle3D>(anchoredTriangles.getFirst(),
						    anchoredTriangles.getSecond());
	} else {
	    throw new Exception("No length is large enough for split");
	}

	return triangles;
    }

    private static <T> Boolean geomIsFineGrained(T geom, double epsilon) throws Exception{
	if (epsilon <= 0.0){throw new Exception("Invalid epsilon: " + epsilon + ". Epsilon must be positive");}
	
	switch (geom.getClass().getName()){
	case "RikiFormi.Point3D":
	    return true;
	case "RikiFormi.Line3D":
	    return ((Line3D) geom).length() <= epsilon;
	case "RikiFormi.Triangle3D":
	    Triangle3D triangle = (Triangle3D) geom;
	    return 
		(new Line3D(triangle.P0, triangle.P1).length() <= epsilon) &&
		(new Line3D(triangle.P0, triangle.P2).length() <= epsilon) &&
		(new Line3D(triangle.P1, triangle.P2).length() <= epsilon);
	case "RikiFormi.Polygon3D":
	    return false;
	default:
	    throw new Exception("Class type '" + geom.getClass().getName() + "' is not supported");
	}
    }

    private static Boolean lineIsDegenerate(Line3D line){
	return line.P0.equals(line.P1);
    }
    
    // *** Transformation ***
    public static <T> ArrayList<T> transformGeoms(ArrayList<T> geoms, Matrix4x4 R) throws Exception{
	T geom;
	
	ArrayList<T> transformedGeoms = new ArrayList<T>();

	Iterator<T> geomsIterator = geoms.iterator();
	
	while (geomsIterator.hasNext()){
	    geom = geomsIterator.next();
	    transformedGeoms.add(transformGeom(geom, R));
	}

	return transformedGeoms;
    }
    
    @SuppressWarnings("unchecked") // Suppress warnings on cast from generic to specific type.
    public static <T> T transformGeom(T geom, Matrix4x4 R) throws Exception{
	switch (geom.getClass().getName()){
	case "RikiFormi.Point3D":
	    Point3D point = (Point3D) geom;

	    double[] a41 = new double[4];

	    a41[0] = point.x; a41[1] = point.y; a41[2] = point.z; a41[3] = 1.0;

	    Matrix4x1 M41 = new Matrix4x1(a41);
	    Matrix4x1 N41 = R.multiply4x1(M41);

	    double[] b41 = N41.getTable();

	    if (b41[3] != 1.0){
		throw new Exception("b41[3] = " + b41[3] + ", which is different from 1.0!");
	    }

	    return (T) new Point3D(b41[0], b41[1], b41[2], point.color);
	case "RikiFormi.Line3D":
	    Line3D line = (Line3D) geom;
	    return (T) new Line3D(transformGeom(line.P0, R), transformGeom(line.P1, R));
	case "RikiFormi.Triangle3D":
	    return transformGeom((T)(new Polygon3D(((Triangle3D) geom).getCopyOfPoint3dList())), R);
	case "RikiFormi.Polygon3D":
	    Polygon3D polygon = (Polygon3D) geom;
	    Polygon3D transformedPolygon = new Polygon3D();
	    ArrayList<Point3D> polygonPoints = polygon.getCopyOfPoint3dList();
	    Iterator<Point3D> pointIterator = polygonPoints.iterator();
	    while (pointIterator.hasNext()){
		transformedPolygon.addPoint(transformGeom(pointIterator.next(), R));
	    }
	    return (T) transformedPolygon;
	default:
	    throw new Exception("Class type '" + geom.getClass().getName() + "' is not supported");
	}	
    }

    // *** Clipping ***
    public static <T> ArrayList<T> clipGeoms(ArrayList<T> geoms, CameraBoundingVolume cameraBoundingVolume) throws Exception {
	ArrayList<T> frontClippedGeoms, backClippedGeoms, leftClippedGeoms, rightClippedGeoms, topClippedGeoms, bottomClippedGeoms;

	frontClippedGeoms = clipGeoms(geoms, BoundingPlaneName.FRONT, cameraBoundingVolume.getBoundary(BoundingPlaneName.FRONT)); if (frontClippedGeoms == null){return null;}
	backClippedGeoms = clipGeoms(frontClippedGeoms, BoundingPlaneName.BACK, cameraBoundingVolume.getBoundary(BoundingPlaneName.BACK)); if (backClippedGeoms == null){return null;}
	leftClippedGeoms = clipGeoms(backClippedGeoms, BoundingPlaneName.LEFT, cameraBoundingVolume.getBoundary(BoundingPlaneName.LEFT)); if (leftClippedGeoms == null){return null;}
	rightClippedGeoms = clipGeoms(leftClippedGeoms, BoundingPlaneName.RIGHT, cameraBoundingVolume.getBoundary(BoundingPlaneName.RIGHT)); if (rightClippedGeoms == null){return null;}
	topClippedGeoms = clipGeoms(rightClippedGeoms, BoundingPlaneName.TOP, cameraBoundingVolume.getBoundary(BoundingPlaneName.TOP)); if (topClippedGeoms == null){return null;}
	bottomClippedGeoms = clipGeoms(topClippedGeoms, BoundingPlaneName.BOTTOM, cameraBoundingVolume.getBoundary(BoundingPlaneName.BOTTOM));

	return bottomClippedGeoms;
    }

    public static <T> ArrayList<T> clipGeoms(ArrayList<T> geoms, BoundingPlaneName clipPlaneName, Line2D clipPlaneBoundary) throws Exception{
	T geom;
	ArrayList<T> clippedGeoms = new ArrayList<T>();
	Iterator<T> geomsIterator = geoms.iterator();
	
	while (geomsIterator.hasNext()){
	    geom = geomsIterator.next();
	    clippedGeoms.addAll(clipGeom(geom, clipPlaneName, clipPlaneBoundary));
	}

	return clippedGeoms;
    }
    
    @SuppressWarnings("unchecked") // Suppress warnings on cast from generic to specific type.
    public static <T> ArrayList<T> clipGeom(T geom, BoundingPlaneName clipPlaneName, Line2D clipPlaneBoundary) throws Exception{
	switch (geom.getClass().getName()){
	case "RikiFormi.Point3D":
	    ArrayList<Point3D> clippedPoints = new ArrayList<Point3D>();
	    Point3D clippedPoint = clipPoint((Point3D) geom, clipPlaneName, clipPlaneBoundary);
	    if (clippedPoint != null){clippedPoints.add(clippedPoint);}
	    return (ArrayList<T>) clippedPoints;
	case "RikiFormi.Line3D":
	    ArrayList<Line3D> clippedLines = new ArrayList<Line3D>();
	    Line3D clippedLine = clipLine((Line3D) geom, clipPlaneName, clipPlaneBoundary);
	    if (clippedLine != null){clippedLines.add(clippedLine);}
	    return (ArrayList<T>) clippedLines;
	case "RikiFormi.Triangle3D":
	    return clipGeom((T)(new Polygon3D(((Triangle3D) geom).getCopyOfPoint3dList())), clipPlaneName, clipPlaneBoundary);
	case "RikiFormi.Polygon3D":
	    ArrayList<Polygon3D> clippedPolygons = new ArrayList<Polygon3D>();
	    Polygon3D clippedPolygon = clipPolygon((Polygon3D) geom, clipPlaneName, clipPlaneBoundary);
	    if (clippedPolygon != null){clippedPolygons.add(clippedPolygon);}
	    return (ArrayList<T>) clippedPolygons;
	default:
	    throw new Exception("Class type '" + geom.getClass().getName() + "' is not supported");
	}
    }

    public static Point3D clipPoint(Point3D point, BoundingPlaneName clipPlaneName, Line2D clipPlaneBoundary) throws Exception{
	if (geomIsCompletelyIncludedByClipPlane(point, clipPlaneName, clipPlaneBoundary)){
	    return point;
	} else {
	    return null;
	}
    }

    public static Line3D clipLine(Line3D line, BoundingPlaneName clipPlaneName, Line2D clipPlaneBoundary) throws Exception{
	if (geomIsCompletelyExcludedByClipPlane(line, clipPlaneName, clipPlaneBoundary)){
	    return null;
	}

	if (geomIsCompletelyIncludedByClipPlane(line, clipPlaneName, clipPlaneBoundary)){
	    return line;
	}

	Line3D origoLine = new Line3D(new Point3D(0.0, 0.0, 0.0),
				      new Point3D(line.P1.x - line.P0.x, line.P1.y - line.P0.y, line.P1.z - line.P0.z));
	Line2D projectedLine, reparametrizedClipPlaneBoundary;
	double crossValue, intersectX, intersectY, intersectZ;
	double scalar = 0.0;

	switch (clipPlaneName){
	case FRONT:
	case BACK:
	    if (Math.abs(origoLine.P1.z) > 0){
		projectedLine = new Line2D(new Point2D(line.P0.z, line.P0.z), new Point2D(line.P1.z, line.P1.z));
		reparametrizedClipPlaneBoundary = new Line2D(new Point2D(clipPlaneBoundary.P0.x, clipPlaneBoundary.P0.y),
							     new Point2D(clipPlaneBoundary.P1.x, clipPlaneBoundary.P1.y));
		crossValue = valueForLine2DCrossLine2D(projectedLine, reparametrizedClipPlaneBoundary);
		scalar = (crossValue - line.P0.z) / origoLine.P1.z;
	    } else {
		throw new Exception("Unexpected non-variance in z-values");
	    }
	    
	    if (Double.isNaN(scalar)) throw new Exception("scalar is NaN for clipPlaneName " + clipPlaneName.toString());
	    break;
	case LEFT:
	case RIGHT:
	    if (Math.abs(origoLine.P1.x) > Math.abs(origoLine.P1.z)){
		projectedLine = new Line2D(new Point2D(line.P0.x, line.P0.z), new Point2D(line.P1.x, line.P1.z));
		reparametrizedClipPlaneBoundary = new Line2D(new Point2D(clipPlaneBoundary.P0.x, clipPlaneBoundary.P0.y),
							     new Point2D(clipPlaneBoundary.P1.x, clipPlaneBoundary.P1.y));
		crossValue = valueForLine2DCrossLine2D(projectedLine, reparametrizedClipPlaneBoundary);
		scalar = (crossValue - line.P0.x) / origoLine.P1.x;
	    } else {
		projectedLine = new Line2D(new Point2D(line.P0.z, line.P0.x), new Point2D(line.P1.z, line.P1.x));
		reparametrizedClipPlaneBoundary = new Line2D(new Point2D(clipPlaneBoundary.P0.y, clipPlaneBoundary.P0.x),
							     new Point2D(clipPlaneBoundary.P1.y, clipPlaneBoundary.P1.x));
		crossValue = valueForLine2DCrossLine2D(projectedLine, reparametrizedClipPlaneBoundary);
		scalar = (crossValue - line.P0.z) / origoLine.P1.z;
	    }
	    
	    if (Double.isNaN(scalar)) throw new Exception("scalar is NaN for clipPlaneName " + clipPlaneName.toString());
	    break;
	case TOP:
	case BOTTOM:
	    if (Math.abs(origoLine.P1.y) > Math.abs(origoLine.P1.z)){
		projectedLine = new Line2D(new Point2D(line.P0.y, line.P0.z), new Point2D(line.P1.y, line.P1.z));
		reparametrizedClipPlaneBoundary = new Line2D(new Point2D(clipPlaneBoundary.P0.x, clipPlaneBoundary.P0.y),
							     new Point2D(clipPlaneBoundary.P1.x, clipPlaneBoundary.P1.y));
		crossValue = valueForLine2DCrossLine2D(projectedLine, reparametrizedClipPlaneBoundary);
		scalar = (crossValue - line.P0.y) / origoLine.P1.y;
	    } else {
		projectedLine = new Line2D(new Point2D(line.P0.z, line.P0.y), new Point2D(line.P1.z, line.P1.y));
		reparametrizedClipPlaneBoundary = new Line2D(new Point2D(clipPlaneBoundary.P0.y, clipPlaneBoundary.P0.x),
							     new Point2D(clipPlaneBoundary.P1.y, clipPlaneBoundary.P1.x));
		crossValue = valueForLine2DCrossLine2D(projectedLine, reparametrizedClipPlaneBoundary);
		scalar = (crossValue - line.P0.z) / origoLine.P1.z;
	    }
	    	
	    if (Double.isNaN(scalar)) throw new Exception("scalar is NaN for clipPlaneName " + clipPlaneName.toString());
	    break;
	default:
	    throw new Exception("Clip plane name '" + clipPlaneName.toString() + "' is not supported");
	}

	intersectX = (scalar * origoLine.P1.x) + line.P0.x;
	intersectY = (scalar * origoLine.P1.y) + line.P0.y;
	intersectZ = (scalar * origoLine.P1.z) + line.P0.z;

	// We now have values "intersectX", "intersectY" and "intersectZ" such that (intersectX, intersectY, intersectZ) is a point on the line "line".
	Point3D newP0;
	Point3D newP1;
	double tmpLineLength;
	double fraction;

	if (geomIsCompletelyIncludedByClipPlane(line.P0, clipPlaneName, clipPlaneBoundary)){ // Then line.P1 is excluded.
	    newP0 = new Point3D(line.P0.x, line.P0.y, line.P0.z, line.P0.color);
	    newP1 = new Point3D(intersectX, intersectY, intersectZ);
	    tmpLineLength = new Line3D(newP0, newP1).length();
	    fraction = tmpLineLength / line.length();
	    newP1.color = makeInterpolatedColor(line.P0.color, line.P1.color, fraction);
	} else { // Then line.P0 is excluded and hence line.P1 is (completely) included.
	    newP0 = new Point3D(intersectX, intersectY, intersectZ);
	    newP1 = new Point3D(line.P1.x, line.P1.y, line.P1.z, line.P1.color);
	    tmpLineLength = new Line3D(line.P0, newP0).length();
	    fraction = tmpLineLength / line.length();
	    newP0.color = makeInterpolatedColor(line.P0.color, line.P1.color, fraction);
	}

	return new Line3D(newP0, newP1);
    }

    public static Polygon3D clipPolygon(Polygon3D spacePolygon, BoundingPlaneName clipPlaneName, Line2D clipPlaneBoundary) throws Exception{
	ArrayList<Line3D> unclippedLines = spacePolygon.toLine3dList();
	ArrayList<Line3D> clippedLines = new ArrayList<Line3D>();
	Line3D unclippedLine, clippedLine;
	Iterator<Line3D> unclippedLinesIterator = unclippedLines.iterator();
	while (unclippedLinesIterator.hasNext()){
	    unclippedLine = unclippedLinesIterator.next();
	    clippedLine = clipLine(unclippedLine, clipPlaneName, clipPlaneBoundary);
	    if (clippedLine != null){
		clippedLines.add(clippedLine);
	    }
	}
	
	Point3D previouslyAddedPoint = null;
	clippedLine = null;

	Polygon3D clippedPolygon = new Polygon3D();
	Iterator<Line3D> clippedLinesIterator = clippedLines.iterator();

	if (!clippedLinesIterator.hasNext()){
	    return null;
	} else {
	    while (clippedLinesIterator.hasNext()){
		clippedLine = (Line3D) clippedLinesIterator.next();

		if (previouslyAddedPoint == null || !clippedLine.P0.equals(previouslyAddedPoint)){
		    clippedPolygon.addPoint(clippedLine.P0);
		}
	    
		clippedPolygon.addPoint(clippedLine.P1);
		previouslyAddedPoint = clippedLine.P1;
	    }
	
	    return clippedPolygon;
	}
    }

    private static boolean geomIsCompletelyIncludedByClipPlane(Line3D line, BoundingPlaneName clipPlaneName, Line2D clipPlaneBoundary)
	throws Exception{
	return geomIsCompletelyIncludedByClipPlane(line.P0, clipPlaneName, clipPlaneBoundary) &&
	    geomIsCompletelyIncludedByClipPlane(line.P1, clipPlaneName, clipPlaneBoundary);
    }

    private static boolean geomIsCompletelyExcludedByClipPlane(Line3D line, BoundingPlaneName clipPlaneName, Line2D clipPlaneBoundary)
	throws Exception{
	return !geomIsCompletelyIncludedByClipPlane(line.P0, clipPlaneName, clipPlaneBoundary) &&
	    !geomIsCompletelyIncludedByClipPlane(line.P1, clipPlaneName, clipPlaneBoundary);
    }

    private static boolean geomIsCompletelyIncludedByClipPlane(Point3D point, BoundingPlaneName clipPlaneName, Line2D clipPlaneBoundary)
	throws Exception{

	OrderedPair<Point2D> sortedPointsLine = sortPoints(new OrderedPair<Point2D>(clipPlaneBoundary.P0, clipPlaneBoundary.P1));
	double x0, y0, x1, y1;
	
	switch (clipPlaneName){
	case LEFT:
	case RIGHT:
	case BOTTOM:
	case TOP:
	    x0 = clipPlaneBoundary.P0.y;
	    y0 = clipPlaneBoundary.P0.x;
	    x1 = clipPlaneBoundary.P1.y;
	    y1 = clipPlaneBoundary.P1.x;
	    break;
	case FRONT:
	case BACK:
	    x0 = clipPlaneBoundary.P0.x;
	    y0 = clipPlaneBoundary.P0.y;
	    x1 = clipPlaneBoundary.P1.x;
	    y1 = clipPlaneBoundary.P1.y;
	    break;
	default:
	    throw new Exception("Clip plane name '" + clipPlaneName.toString() + "' is not supported");
	}

	if (x1 == x0){
	    throw new Exception("Invalid clipPlaneBoundary: " + clipPlaneBoundary.toString());
	}
	
	double clipPlaneValue = functionValue(new Line2D(new Point2D(x0, y0), new Point2D(x1, y1)), point.z);
	
	switch (clipPlaneName){
	case LEFT:
	    return point.x >= clipPlaneValue; // x as a function of z, (x(z), 0, z).
	case RIGHT:
	    return point.x <= clipPlaneValue; // x as a function of z, (x(z), 0, z).
	case BOTTOM:
	    return point.y >= clipPlaneValue; // y as a function of z, (0, y(z), z).
	case TOP:
	    return point.y <= clipPlaneValue; // y as a function of z, (0, y(z), z).
	case FRONT:
	    return point.z >= clipPlaneValue; // z as a function of z, (0, 0, z(z)).
	case BACK:
	    return point.z <= clipPlaneValue; // z as a function of z, (0, 0, z(z)).
	default:
	    throw new Exception("Clip plane name '" + clipPlaneName.toString() + "' is not supported");
	}
    }

    public static OrderedPair<Point2D> sortPoints(OrderedPair<Point2D> points){
	if (points.getFirst().strictlyLessThan(points.getSecond())){
	    return new OrderedPair<Point2D>(points.getFirst(), points.getSecond());
	} else {
	    return new OrderedPair<Point2D>(points.getSecond(), points.getFirst());
	}
    }

    public static double valueForLine2DCrossLine2D(Line2D l1, Line2D l2){
	// Points for line1:
	OrderedPair<Point2D> sortedPointsL1 = sortPoints(new OrderedPair<Point2D>(l1.P0, l1.P1));
	double x0 = sortedPointsL1.element1.x;
	double y0 = sortedPointsL1.element1.y;
	double x1 = sortedPointsL1.element2.x;
	double y1 = sortedPointsL1.element2.y;
	// Points for line2:
	OrderedPair<Point2D> sortedPointsL2 = sortPoints(new OrderedPair<Point2D>(l2.P0, l2.P1));
	double a0 = sortedPointsL2.element1.x;
	double b0 = sortedPointsL2.element1.y;
	double a1 = sortedPointsL2.element2.x;
	double b1 = sortedPointsL2.element2.y;

	// Special vertical cases:
	if (x0 == x1){
	    if ((a0 == a1) || (y0 == y1)){
		return Double.NaN;
	    } else {
		return x0;
	    }
	}

	if (a0 == a1){
	    if ((x0 == x1) || (b0 == b1)){
		return Double.NaN;
	    } else {
		return a0;
	    }
	}

	// From here, none of the lines are vertical:
	double slope1 = (y1 - y0) / (x1 - x0);
	double slope2 = (b1 - b0) / (a1 - a0);

	// Lines with same slope cannot cross at a single point:
	if (slope1 == slope2){
	    return Double.NaN;
	}

	return ((b0 - a0 * slope2) - (y0 - x0 * slope1)) / (slope1 - slope2);
    }
    
    public static double functionValue(Line2D line, double x) throws Exception{
	OrderedPair<Point2D> sortedPointsLine = sortPoints(new OrderedPair<Point2D>(line.P0, line.P1));
	double x0 = sortedPointsLine.element1.x;
	double y0 = sortedPointsLine.element1.y;
	double x1 = sortedPointsLine.element2.x;
	double y1 = sortedPointsLine.element2.y;

	if (x0 == x1){
	    throw new Exception("The given line cannot be interpreted as a function. Line is " + line.toString());
	}

	double slope = (y1 - y0) / (x1 - x0);

	return y0 + ((x - x0) * slope);
    }
    
    // *** Edge categorization ***
    public static double determinant(double a, double b, double c, double d){
	//(a b)^T and (c d)^T are vectors.
	return (a * d) - (b * c);
    }

    // *** Projection ***
    public static <T> ArrayList<T> projectGeoms(ArrayList<T> geoms, double projectPlaneValue) throws Exception{
	ArrayList<T> projectedGeoms = new ArrayList<T>();
	
	T geom;
	Iterator<T> geomIterator = geoms.iterator();

	while (geomIterator.hasNext()){
	    geom = geomIterator.next();
	    projectedGeoms.add(projectGeom(geom, projectPlaneValue));
	}
	
	return projectedGeoms;
    }

    @SuppressWarnings("unchecked") // Suppress warnings on cast from generic to specific type.
    public static <T> T projectGeom(T geom, double projectPlaneValue) throws Exception{
	T projectedGeom;

	switch (geom.getClass().getName()){
	case "RikiFormi.Point3D":
	    projectedGeom = (T) projectPoint((Point3D) geom, projectPlaneValue);
	    break;
	case "RikiFormi.Line3D":
	    projectedGeom = (T) projectLine((Line3D) geom, projectPlaneValue);
	    break;
	case "RikiFormi.Triangle3D":
	    projectedGeom = (T) projectTriangle((Triangle3D) geom, projectPlaneValue);
	    break;
	default:
	    throw new Exception("Class type '" + geom.getClass().getName() + "' is not supported");
	}

	return projectedGeom;
    }

    public static Triangle3D projectTriangle(Triangle3D spaceTriangle, double projectPlaneValue) throws Exception{
	Triangle3D projectedTriangle = new Triangle3D(projectPoint(spaceTriangle.P0, projectPlaneValue),
						      projectPoint(spaceTriangle.P1, projectPlaneValue),
						      projectPoint(spaceTriangle.P2, projectPlaneValue));
	return projectedTriangle;
    }

    public static Line3D projectLine(Line3D l, double projectPlaneValue) throws Exception{
	return new Line3D(projectPoint(l.P0, projectPlaneValue),
			  projectPoint(l.P1, projectPlaneValue));
    }
    
    public static Point3D projectPoint(Point3D p, double projectPlaneValue) throws Exception{
	double x = p.x;
	double y = p.y;
	double z = p.z;

	if (z <= 0.0){
	    throw new Exception("The z-value '" + z + "' is invalid when projecting");
	}

	//In order to ensure consistency with clipping, the below projection code is
	//used instead of a more straight forward projection formula:
	Line2D projectedLine = new Line2D(new Point2D(0, 0), new Point2D(p.z, p.z));
	Line2D clipPlaneBoundary = new Line2D(new Point2D(0.0, projectPlaneValue),
					      new Point2D(1.0, projectPlaneValue));
	double crossValue = valueForLine2DCrossLine2D(projectedLine, clipPlaneBoundary);
	double scalar = crossValue / p.z;

	return new Point3D(scalar * p.x, scalar * p.y, scalar * p.z, p.z, p.distance(), p.color);
    }

    // *** Triangulation ***
    public static ArrayList<Triangle3D> useFanTriangulation(Polygon3D polygon){
	ArrayList<Triangle3D> triangles = new ArrayList<Triangle3D>();
	
	//DoToo: Find better name for the iterator:
	Iterator<Point3D> pointIterator = polygon.iterator();

	if (pointIterator.hasNext()){
	    Point3D p0 = pointIterator.next();

	    if (pointIterator.hasNext()){
		Point3D pi = pointIterator.next();

		if (pointIterator.hasNext()){
		    Triangle3D triangle;
		    Point3D pj;

		    do {
			pj = pointIterator.next();
			
			triangle = new Triangle3D(p0, pi, pj);
			triangles.add(triangle);

			pi = pj;
		    } while (pointIterator.hasNext());
		}
	    }
	}

	return triangles;
    }

    public static ArrayList<Triangle3D> useRecursiveTriangulation(Polygon3D polygon)
    throws Exception{
	ArrayList<Point3D> polygonPoints = polygon.getCopyOfPoint3dList();
	ArrayList<Triangle3D> initialTriangulation = doRecursiveTriangulation(polygonPoints);
	ArrayList<Triangle3D> finalTriangulation = new ArrayList<Triangle3D>();
	Iterator<Triangle3D> initialTriangleIterator = initialTriangulation.iterator();

	while (initialTriangleIterator.hasNext()){
	    finalTriangulation.addAll(ensureGranularity(initialTriangleIterator.next(), 70.0));
	}

	return finalTriangulation;
    }
    
    public static ArrayList<Triangle3D> doRecursiveTriangulation(Polygon3D polygon)
    throws Exception{
	return doRecursiveTriangulation(polygon.getCopyOfPoint3dList());
    }
    
    public static ArrayList<Triangle3D> doRecursiveTriangulation(ArrayList<Point3D> polygonPoints){
	if (polygonPoints == null || polygonPoints.size() < 3){
	    return new ArrayList<Triangle3D>();
	} else {
	    ArrayList<Triangle3D> triangles = new ArrayList<Triangle3D>();
	    
	    Triangle3D triangle = new Triangle3D(polygonPoints.get(0),
						 polygonPoints.get(1),
						 polygonPoints.get(2));

	    if (polygonPoints.size() == 3){
		triangles.add(triangle);
	    } else {
		polygonPoints.add(polygonPoints.remove(0)); // Avoid fan triangulation
		polygonPoints.remove(0);
		triangles.add(triangle);
		triangles.addAll(doRecursiveTriangulation(polygonPoints));
	    }
	    return triangles;
	}
    }

    public static Color fadeColorToBackground(Color fadeColor, double fadeZValue, Color backgroundColor, double backClipPlaneValue){
	double t = fadeZValue / backClipPlaneValue;

	if (t < 0.0) t = 0.0;
	if (t > 1.0) t = 1.0;
	
	return makeInterpolatedColor(fadeColor, backgroundColor, t);
    }

    public static void serializeObject(Object objectToSerialize, String fileName) throws Exception{
	FileOutputStream fileOut = new FileOutputStream(fileName);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
	outputStream.writeObject(objectToSerialize);
        outputStream.close();
        fileOut.close();
    }

    public static Object deserializeObject(String fileName) throws Exception{
	FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream inStream = new ObjectInputStream(fileIn);
	Object deserializedObject = inStream.readObject();
        inStream.close();
        fileIn.close();
	return deserializedObject;
    }
}

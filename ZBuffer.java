package RikiFormi;

import java.awt.*;
import java.util.*;
import java.lang.Math;
import RikiFormi.Utils.*;

public class ZBuffer extends Component{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    RasterPoint[][] raster = null;

    int W = 0, H = 0;
    double D = 0.0;
    Color BackgroundColor = null;
    double BackClipPlaneValue = 1.0;

    public ZBuffer(int w, int h, double d, Color backgroundColor, double backClipPlaneValue){
	W = w; H = h;
	D = d; BackgroundColor = backgroundColor;
	BackClipPlaneValue = backClipPlaneValue;

	raster = new RasterPoint[W][H];

	for (int i = 0; i < W; i++){
	    for (int j = 0; j < H; j++){
		raster[i][j] = new RasterPoint(i, j, D, BackgroundColor);
	    }
	}
    }

    public RasterPoint[][] clear(){
	for (int i = 0; i < W; i++){
	    for (int j = 0; j < H; j++){
		raster[i][j] = new RasterPoint(i, j, D, BackgroundColor);
	    }
	}

	return raster;
    }

    public RasterPoint[][] getRaster(){
	return raster;
    }

    public RasterPoint getEntry(int a, int b) throws Exception{
	if ((a >= 0) && (a < W) && (b >= 0) && (b < H)){
	    return raster[a][b];
	} else{
	    throw new Exception("Argument out of bounds: a = " + a +
				", b = " + b);
	}
    }

    public void clearEntry(int a, int b){
	if ((a >= 0) && (a < W) && (b >= 0) && (b < H)){
	    raster[a][b] = new RasterPoint(a, b, D, BackgroundColor);
	}
    }

    public void update(ArrayList<ArrayList<RasterPoint>> listOfScannedRasterPoints){
	Iterator I1, I2;
	ArrayList<RasterPoint> scannedRasterPoints;

	I1 = listOfScannedRasterPoints.iterator();
	while (I1.hasNext()){
	    scannedRasterPoints = (ArrayList<RasterPoint>) I1.next();
	    I2 = scannedRasterPoints.iterator();
	    while (I2.hasNext()){
		update((RasterPoint) I2.next());
	    }
	}
    }
    
    public void update(RasterPoint rasterPoint){
	if (rasterPoint == null) return;

	if ((rasterPoint.a >= 0) && (rasterPoint.a < W) &&
	    (rasterPoint.b >= 0) && (rasterPoint.b < H)){
	    //Use four decimal precision:
	    double rasterEntryDistance =
		Math.rint((raster[rasterPoint.a][rasterPoint.b]).distance * 10000.0) / 10000.0;
	    double rasterPointDistance = Math.rint(rasterPoint.distance * 10000.0) / 10000.0;
	    if (rasterPointDistance <= rasterEntryDistance){
		raster[rasterPoint.a][rasterPoint.b] = rasterPoint;
	    }
	} else{
	    System.out.println(">>>ZBuffer > update: WARNING! Discarded point " + rasterPoint.toString());
	}
    }

    public ArrayList<ArrayList<RasterPoint>> midPointLine(int a0, int b0, double D0, Color C0, double Z0,
							  int a1, int b1, double D1, Color C1, double Z1,
							  boolean includeStartAndEndPixels){

	ArrayList<ArrayList<RasterPoint>> scannedRasterPointsList = new ArrayList();
	
    	if ((a0 == a1) && (b0 == b1)){
	    if (includeStartAndEndPixels){
		if (D0 <= D1){
		    addRasterPoint(scannedRasterPointsList, new RasterPoint(a0, b0, D0, Util3d.fadeColorToBackground(C0, Z0, BackgroundColor, BackClipPlaneValue)));
		} else{
		    addRasterPoint(scannedRasterPointsList, new RasterPoint(a1, b1, D1, Util3d.fadeColorToBackground(C1, Z1, BackgroundColor, BackClipPlaneValue)));
		}
	    }
	    return scannedRasterPointsList;
    	}

    	boolean CASE_I		= (a1 > a0) && (b1 >= b0) && (b1 - b0 <= a1 - a0);
    	boolean CASE_II		= (a1 >= a0) && (b1 > b0) && (b1 - b0 > a1 - a0);
    	boolean CASE_III	= (a0 > a1) && (b1 > b0) && (a0 - a1 < b1 - b0);
    	boolean CASE_IV		= (a0 > a1) && (b1 >= b0) && (b1 - b0 <= a0 - a1);
    	boolean CASE_V		= (a0 > a1) && (b0 > b1) && (b0 - b1 <= a0 - a1);
    	boolean CASE_VI		= (a0 >= a1) && (b0 > b1) && (b0 - b1 > a0 - a1);
    	boolean CASE_VII	= (a0 < a1) && (b0 > b1) && (a1 - a0 < b0 - b1);
    	boolean CASE_VIII	= (a0 < a1) && (b0 >= b1) && (a1 - a0 >= b0 - b1);
    	
    	int x0 = 0, y0 = 0, x1 = 0, y1 = 0;
    	
    	boolean east = true; //if east is true then East is used else North East is used

    	if (CASE_I){east = true; x0 = a0; y0 = b0; x1 = a1; y1 = b1;}
	else if (CASE_II){east = true; x0 = b0; y0 = a0; x1 = b1; y1 = a1;}
	else if (CASE_III){east = true; x0 = b0; y0 = -a0; x1 = b1; y1 = -a1;}
	else if (CASE_IV){east = true; x0 = -a0; y0 = b0; x1 = -a1; y1 = b1;}
	else if (CASE_V){east = false; x0 = -a0; y0 = -b0; x1 = -a1; y1 = -b1;}
	else if (CASE_VI){east = false; x0 = -b0; y0 = -a0; x1 = -b1; y1 = -a1;}
	else if (CASE_VII){east = false; x0 = -b0; y0 = a0; x1 = -b1; y1 = a1;}
	else if (CASE_VIII){east = false; x0 = a0; y0 = -b0; x1 = a1; y1 = -b1;}
    	
    	//The scan:
    	float[] components0 = C0.getRGBColorComponents(null);
    	float cr0 = components0[0]; float cg0 = components0[1]; float cb0 = components0[2];

    	float[] components1 = C1.getRGBColorComponents(null);
    	float cr1 = components1[0]; float cg1 = components1[1]; float cb1 = components1[2];

	double t = 0.0;
	double D = D0;
	float cr = cr0;
	float cg = cg0;
	float cb = cb0;
	double zBeforeProjection = Z0;
	Color originalColor = new Color(cr, cg, cb);
	Color fadedColor = Util3d.fadeColorToBackground(originalColor, zBeforeProjection, BackgroundColor, BackClipPlaneValue);
	
    	int dx = x1 - x0;
    	int dy = y1 - y0;
    	int d = 2 * dy - dx;
    	int incrE = 2 * dy;
    	int incrNE = 2 * (dy - dx);
    	int x = x0;
    	int y = y0;

	if (includeStartAndEndPixels){
	    //Write the start pixel:
	    if (CASE_I){addRasterPoint(scannedRasterPointsList, new RasterPoint(x, y, D, originalColor, fadedColor, zBeforeProjection));}
	    else if (CASE_II){addRasterPoint(scannedRasterPointsList, new RasterPoint(y, x, D, originalColor, fadedColor, zBeforeProjection));}
	    else if (CASE_III){addRasterPoint(scannedRasterPointsList, new RasterPoint(-y, x, D, originalColor, fadedColor, zBeforeProjection));}
	    else if (CASE_IV){addRasterPoint(scannedRasterPointsList, new RasterPoint(-x, y, D, originalColor, fadedColor, zBeforeProjection));}
	    else if (CASE_V){addRasterPoint(scannedRasterPointsList, new RasterPoint(-x, -y, D, originalColor, fadedColor, zBeforeProjection));}
	    else if (CASE_VI){addRasterPoint(scannedRasterPointsList, new RasterPoint(-y, -x, D, originalColor, fadedColor, zBeforeProjection));}
	    else if (CASE_VII){addRasterPoint(scannedRasterPointsList, new RasterPoint(y, -x, D, originalColor, fadedColor, zBeforeProjection));}
	    else if (CASE_VIII){addRasterPoint(scannedRasterPointsList, new RasterPoint(x, -y, D, originalColor, fadedColor, zBeforeProjection));}
	}

    	//Scan to end pixel:
    	while (x < x1){
	    //Select pixel closest to the line and compute next d:
	    if (d < 0){
		d = d + incrE;
		x++;
	    } else if (d > 0){
		d = d + incrNE;
		x++;
		y++;
	    } else {
		if (east){
		    d = d + incrE;
		    x++;
		} else{
		    d = d + incrNE;
		    x++;
		    y++;
		}
	    }

	    //Interpolate depth, color and z-value:
	    t = ((double)(x - x0)) / ((double)(x1 - x0));
	    D = (1.0 - t) * D0 + t * D1;
	    cr = ((float)(1.0 - t)) * cr0 + ((float)t) * cr1;
	    cg = ((float)(1.0 - t)) * cg0 + ((float)t) * cg1;
	    cb = ((float)(1.0 - t)) * cb0 + ((float)t) * cb1;
	    zBeforeProjection = (1.0 - t) * Z0 + t * Z1;
	    originalColor = new Color(cr, cg, cb);
	    fadedColor = Util3d.fadeColorToBackground(originalColor, zBeforeProjection, BackgroundColor, BackClipPlaneValue);

	    //Write the selected pixel closest to the line:
	    if ((x < x1) || includeStartAndEndPixels){
		if (CASE_I){addRasterPoint(scannedRasterPointsList, new RasterPoint(x, y, D, originalColor, fadedColor, zBeforeProjection));}
		else if (CASE_II){addRasterPoint(scannedRasterPointsList, new RasterPoint(y, x, D, originalColor, fadedColor, zBeforeProjection));}
		else if (CASE_III){addRasterPoint(scannedRasterPointsList, new RasterPoint(-y, x, D, originalColor, fadedColor, zBeforeProjection));}
		else if (CASE_IV){addRasterPoint(scannedRasterPointsList, new RasterPoint(-x, y, D, originalColor, fadedColor, zBeforeProjection));}
		else if (CASE_V){addRasterPoint(scannedRasterPointsList, new RasterPoint(-x, -y, D, originalColor, fadedColor, zBeforeProjection));}
		else if (CASE_VI){addRasterPoint(scannedRasterPointsList, new RasterPoint(-y, -x, D, originalColor, fadedColor, zBeforeProjection));}
		else if (CASE_VII){addRasterPoint(scannedRasterPointsList, new RasterPoint(y, -x, D, originalColor, fadedColor, zBeforeProjection));}
		else if (CASE_VIII){addRasterPoint(scannedRasterPointsList, new RasterPoint(x, -y, D, originalColor, fadedColor, zBeforeProjection));}
	    }
    	}
	
	return scannedRasterPointsList;
    }

    private void addRasterPoint(ArrayList<ArrayList<RasterPoint>> scannedRasterPointsList, RasterPoint rasterPoint){
	if ((scannedRasterPointsList.size() == 0) || (scannedRasterPointsList.get(scannedRasterPointsList.size() - 1).get(0).b != rasterPoint.b)){
	    scannedRasterPointsList.add(new ArrayList<RasterPoint>());
	}
	
	scannedRasterPointsList.get(scannedRasterPointsList.size() - 1).add(rasterPoint);
    }

    protected OrderedTriple<RasterPoint> sortRasterPointsBySecondCoordinate(RasterPoint unsortedP0,
									    RasterPoint unsortedP1,
									    RasterPoint unsortedP2)
	throws Exception{
	
	RasterPoint p0, p1, p2;
	p0 = p1 = p2 = null;

	p0 = unsortedP0;

	if (unsortedP1.verticallyStrictlyLessThan(p0)){
	    p1 = p0;
	    p0 = unsortedP1;
	} else {
	    p1 = unsortedP1;
	}

	if (unsortedP2.verticallyStrictlyLessThan(p0)){
	    p2 = p1;
	    p1 = p0;
	    p0 = unsortedP2;
	} else {
	    if (unsortedP2.verticallyStrictlyLessThan(p1)){
		p2 = p1;
		p1 = unsortedP2;
	    } else {
		p2 = unsortedP2;
	    }
	}

	OrderedTriple<RasterPoint> sortedPoints = new OrderedTriple<RasterPoint>(p0, p1, p2);

	return sortedPoints;
    }

    private void scanConvertTriangleLinesHorizontally(ArrayList<ArrayList<RasterPoint>> listOfScannedRasterPointsP0_P1,
						      ArrayList<ArrayList<RasterPoint>> listOfScannedRasterPointsP0_P2,
						      ArrayList<ArrayList<RasterPoint>> listOfScannedRasterPointsP1_P2,
						      boolean P0_P2IsRightSided)
    throws Exception{
	if (listOfScannedRasterPointsP0_P1.get(listOfScannedRasterPointsP0_P1.size() - 1).get(0).b != listOfScannedRasterPointsP1_P2.get(0).get(0).b){
	    throw new Exception("listOfScannedRasterPointsP0_P1 and listOfScannedRasterPointsP1_P2 do not meet");
	}
	if (listOfScannedRasterPointsP0_P1.get(0).get(0).b != listOfScannedRasterPointsP0_P2.get(0).get(0).b){
	    throw new Exception("listOfScannedRasterPointsP0_P1 and listOfScannedRasterPointsP0_P2 do not meet");
	}
	if (listOfScannedRasterPointsP1_P2.get(listOfScannedRasterPointsP1_P2.size() - 1).get(0).b != listOfScannedRasterPointsP0_P2.get(listOfScannedRasterPointsP0_P2.size() - 1).get(0).b){
	    throw new Exception("listOfScannedRasterPointsP0_P1 and listOfScannedRasterPointsP0_P2 do not meet");
	}
	
	ArrayList<RasterPoint> scannedRasterPointsP0_P1, scannedRasterPointsP0_P2, scannedRasterPointsP1_P2;
	RasterPoint P0_P1RasterPoint, P0_P2RasterPoint, P1_P2RasterPoint;
	ArrayList<ArrayList<RasterPoint>> scanLine;
	
	scanConvertBetweenMainAndOtherLineHorizontally(listOfScannedRasterPointsP0_P2, listOfScannedRasterPointsP0_P1, P0_P2IsRightSided, 0);
	scanConvertBetweenMainAndOtherLineHorizontally(listOfScannedRasterPointsP0_P2, listOfScannedRasterPointsP1_P2, P0_P2IsRightSided, listOfScannedRasterPointsP0_P1.size() - 1);
    }
    
    private void scanConvertBetweenMainAndOtherLineHorizontally(ArrayList<ArrayList<RasterPoint>> mainLine,
								ArrayList<ArrayList<RasterPoint>> otherLine,
								boolean MainLineIsRightSided,
								int indexOffset)
    throws Exception{
	ArrayList<RasterPoint> scannedRasterPointsMainLine, scannedRasterPointsOtherLine;
	RasterPoint MainRasterPoint, OtherRasterPoint;
	ArrayList<ArrayList<RasterPoint>> scanLine;

	for (int i = 0; i < otherLine.size(); i++){
	    scannedRasterPointsOtherLine = otherLine.get(i);
	    scannedRasterPointsMainLine = mainLine.get(i + indexOffset);
	    if (MainLineIsRightSided){
		OtherRasterPoint = getExtremumRasterPointOnA(scannedRasterPointsOtherLine, ExtremumType.MAXIMUM);
		MainRasterPoint = getExtremumRasterPointOnA(scannedRasterPointsMainLine, ExtremumType.MINIMUM);
	    } else {
		OtherRasterPoint = getExtremumRasterPointOnA(scannedRasterPointsOtherLine, ExtremumType.MINIMUM);
		MainRasterPoint =  getExtremumRasterPointOnA(scannedRasterPointsMainLine, ExtremumType.MAXIMUM);
	    }

	    scanLine = midPointLine(OtherRasterPoint.a, OtherRasterPoint.b, OtherRasterPoint.distance, OtherRasterPoint.originalColor, OtherRasterPoint.zBeforeProjection,
				    MainRasterPoint.a, MainRasterPoint.b, MainRasterPoint.distance, MainRasterPoint.originalColor, MainRasterPoint.zBeforeProjection,
				    false);
	    update(scanLine);
	}
    }
    
    private RasterPoint getExtremumRasterPointOnA(ArrayList<RasterPoint> rasterPoints, ExtremumType extremumType)
    throws Exception{
	if (rasterPoints.size() == 0){
	    return null;
	}
	
	RasterPoint bestFoundRasterPoint = rasterPoints.get(0);
	int bestFoundA = bestFoundRasterPoint.a;

	RasterPoint currRasterPoint;
	Iterator I = rasterPoints.iterator();

	while (I.hasNext()){
	    currRasterPoint = (RasterPoint) I.next();
	    switch (extremumType){
	    case MAXIMUM:
		if (currRasterPoint.a > bestFoundA){
		    bestFoundA = currRasterPoint.a;
		    bestFoundRasterPoint = currRasterPoint;
		}
		break;
	    case MINIMUM:
		if (currRasterPoint.a < bestFoundA){
		    bestFoundA = currRasterPoint.a;
		    bestFoundRasterPoint = currRasterPoint;
		}
		break;
	    default:
		throw new Exception("Extremum type '" + extremumType.toString() + "' is not supported");
	    }
	}
	
	return bestFoundRasterPoint;
    }

    private RasterPoint convertPoint3DToRasterPoint(Point3D point3d, double X, double Y) throws Exception{
	    
	double pxt, pyt;
	int a, b;

	pxt = point3d.x + (X / 2.0);
	pyt = point3d.y + (Y / 2.0);
	    
	a = (int) Math.round(((pxt / X) * ((double) (W - 1))));
	b = (int) Math.round(((pyt / Y) * ((double) (H - 1))));
	
	Color fadedColor = Util3d.fadeColorToBackground(point3d.color, point3d.zBeforeProjection, BackgroundColor, BackClipPlaneValue);

	RasterPoint newRasterPoint = new RasterPoint(a, b, point3d.distanceBeforeProjection, point3d.color, fadedColor, point3d.zBeforeProjection);

	return newRasterPoint;
    }
    
    public <T> void rasterizeGeoms(ArrayList<T> geoms, double X, double Y)
	throws Exception{
	Iterator<T> geomsIterator = geoms.iterator();
	
	while (geomsIterator.hasNext()){
	    rasterizeGeom(geomsIterator.next(), X, Y);
	}
    }
    
    public <T> void rasterizeGeom(T geom, double X, double Y)
	throws Exception{
	
	switch (geom.getClass().getName()){
	case "RikiFormi.Point3D":
	    rasterizePoint((Point3D) geom, X, Y);
	    break;
	case "RikiFormi.Line3D":
	    rasterizeLine((Line3D) geom, X, Y);
	    break;
	case "RikiFormi.Triangle3D":
	    rasterizeTriangle((Triangle3D) geom, X, Y);
	    break;
	default:
	    throw new Exception("Class type '" + geom.getClass().getName() + "' is not supported");
	}
    }

    public void rasterizeTriangle(Triangle3D triangle, double X, double Y)
	throws Exception{
	RasterPoint unsortedRasterPoint0, unsortedRasterPoint1, unsortedRasterPoint2;
	OrderedTriple<RasterPoint> sortedRasterPoints;
	RasterPoint sortedRasterPoint0, sortedRasterPoint1, sortedRasterPoint2;
	ArrayList<ArrayList<RasterPoint>> lineOfRasterPointsP0_P1, lineOfRasterPointsP0_P2, lineOfRasterPointsP1_P2;

	lineOfRasterPointsP0_P1 = new ArrayList<ArrayList<RasterPoint>>();
	lineOfRasterPointsP0_P2 = new ArrayList<ArrayList<RasterPoint>>();
	lineOfRasterPointsP1_P2 = new ArrayList<ArrayList<RasterPoint>>();

	unsortedRasterPoint0 = convertPoint3DToRasterPoint(triangle.Point3dList.get(0), X, Y);
	unsortedRasterPoint1 = convertPoint3DToRasterPoint(triangle.Point3dList.get(1), X, Y);
	unsortedRasterPoint2 = convertPoint3DToRasterPoint(triangle.Point3dList.get(2), X, Y);
		    
	sortedRasterPoints = sortRasterPointsBySecondCoordinate(unsortedRasterPoint0, unsortedRasterPoint1, unsortedRasterPoint2);
	    
	sortedRasterPoint0 = sortedRasterPoints.getFirst();
	sortedRasterPoint1 = sortedRasterPoints.getSecond();
	sortedRasterPoint2 = sortedRasterPoints.getThird();


	lineOfRasterPointsP0_P1 = midPointLine(sortedRasterPoint0.a, sortedRasterPoint0.b, sortedRasterPoint0.distance, sortedRasterPoint0.originalColor, sortedRasterPoint0.zBeforeProjection,
					       sortedRasterPoint1.a, sortedRasterPoint1.b, sortedRasterPoint1.distance, sortedRasterPoint1.originalColor, sortedRasterPoint1.zBeforeProjection,
					       true);
	lineOfRasterPointsP1_P2 = midPointLine(sortedRasterPoint1.a, sortedRasterPoint1.b, sortedRasterPoint1.distance, sortedRasterPoint1.originalColor, sortedRasterPoint1.zBeforeProjection,
					       sortedRasterPoint2.a, sortedRasterPoint2.b, sortedRasterPoint2.distance, sortedRasterPoint2.originalColor, sortedRasterPoint2.zBeforeProjection,
					       true);
	lineOfRasterPointsP0_P2 = midPointLine(sortedRasterPoint0.a, sortedRasterPoint0.b, sortedRasterPoint0.distance, sortedRasterPoint0.originalColor, sortedRasterPoint0.zBeforeProjection,
					       sortedRasterPoint2.a, sortedRasterPoint2.b, sortedRasterPoint2.distance, sortedRasterPoint2.originalColor, sortedRasterPoint2.zBeforeProjection,
					       true);


	double determinant = Util3d.determinant(sortedRasterPoint2.a - sortedRasterPoint0.a, sortedRasterPoint2.b - sortedRasterPoint0.b,
						sortedRasterPoint1.a - sortedRasterPoint0.a, sortedRasterPoint1.b - sortedRasterPoint0.b);

	boolean P0_P2IsRightSided = determinant > 0;
	
	if (P0_P2IsRightSided){
	    update(lineOfRasterPointsP0_P1);
	    update(lineOfRasterPointsP1_P2);
	} else {
	    update(lineOfRasterPointsP0_P2);
	}

	scanConvertTriangleLinesHorizontally(lineOfRasterPointsP0_P1,
					     lineOfRasterPointsP0_P2,
					     lineOfRasterPointsP1_P2,
					     P0_P2IsRightSided);
    }

    public void rasterizeLine(Line3D line, double X, double Y) throws Exception{
	ArrayList<ArrayList<RasterPoint>> listOfScannedRasterPoints;
	RasterPoint rasterPoint0, rasterPoint1;
	
	rasterPoint0 = convertPoint3DToRasterPoint(line.P0, X, Y);
	rasterPoint1 = convertPoint3DToRasterPoint(line.P1, X, Y);

	listOfScannedRasterPoints = midPointLine(rasterPoint0.a, rasterPoint0.b, rasterPoint0.distance, rasterPoint0.originalColor, rasterPoint0.zBeforeProjection,
						 rasterPoint1.a, rasterPoint1.b, rasterPoint1.distance, rasterPoint1.originalColor, rasterPoint1.zBeforeProjection,
						 true);
	update(listOfScannedRasterPoints);
    }

    public void rasterizePoint(Point3D point, double X, double Y) throws Exception{
	if (!Double.isNaN(point.x) && !Double.isNaN(point.y)){
	    update(convertPoint3DToRasterPoint(point, X, Y));
	}
    }
}

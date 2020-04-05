package RikiFormi;

import java.awt.*;
import java.util.*;
import RikiFormi.Utils.*;

public class Camera{

    protected double FrontClipPlaneValue = 0.0;
    protected double ProjectionPlaneValue = 0.0;
    protected double ProjectionWindowWidth = 0.0;
    protected double ProjectionWindowHeight = 0.0;
    protected Color BackgroundColor = Color.black;
    protected double BackClipPlaneOffset = 2000.0;
    protected double BackClipPlaneValue = ProjectionPlaneValue + BackClipPlaneOffset;
    protected double LeftClipPlaneValue = 0.0;
    protected double RightClipPlaneValue = 0.0;
    protected double TopClipPlaneValue = 0.0;
    protected double BottomClipPlaneValue = 0.0;
    protected Line2D FrontClipPlaneBoundary = null;
    protected Line2D BackClipPlaneBoundary = null;
    protected Line2D LeftClipPlaneBoundary = null;
    protected Line2D RightClipPlaneBoundary = null;
    protected Line2D TopClipPlaneBoundary = null;
    protected Line2D BottomClipPlaneBoundary = null;

    protected Matrix4x4 TransformationMatrix = null;

    // Raster:
    ZBuffer zBuffer;

    // Display device:
    Screen screen;

    public Camera(double frontClipPlaneValue,
		  double projectionPlaneValue,
		  double projectionWindowWidth,
		  double projectionWindowHeight,
		  int w,
		  int h) throws Exception{

	FrontClipPlaneValue = frontClipPlaneValue;
	if (FrontClipPlaneValue <= 0.0){
	    throw new Exception("The value for FrontClipPlaneValue must be positive");
	}
	ProjectionPlaneValue = projectionPlaneValue;
	ProjectionWindowWidth = projectionWindowWidth;
	ProjectionWindowHeight = projectionWindowHeight;
	if (w <= 0 || h <= 0){
	    throw new Exception("The values for W and H must be positive");
	}
	BackClipPlaneValue = ProjectionPlaneValue + BackClipPlaneOffset;

	RightClipPlaneValue = Util3d.valueForLine2DCrossLine2D(new Line2D(new Point2D(0.0, 0.0),
									  new Point2D(projectionWindowWidth / 2.0, projectionPlaneValue)),
							       new Line2D(new Point2D(0.0, BackClipPlaneValue),
									  new Point2D(1.0, BackClipPlaneValue)));
	LeftClipPlaneValue = -RightClipPlaneValue;
	double leftBackClipPlaneValue = LeftClipPlaneValue;
	double rightBackClipPlaneValue = RightClipPlaneValue;
	
	TopClipPlaneValue = Util3d.valueForLine2DCrossLine2D(new Line2D(new Point2D(0.0, 0.0),
									new Point2D(projectionWindowHeight / 2.0, projectionPlaneValue)),
							     new Line2D(new Point2D(0.0, BackClipPlaneValue),
									new Point2D(1.0, BackClipPlaneValue)));

	BottomClipPlaneValue = -TopClipPlaneValue;
	double topBackClipPlaneValue = TopClipPlaneValue;
	double bottomBackClipPlaneValue = BottomClipPlaneValue;

	FrontClipPlaneBoundary = new Line2D(new Point2D(0.0, FrontClipPlaneValue), new Point2D(1.0, FrontClipPlaneValue));
	BackClipPlaneBoundary = new Line2D(new Point2D(0.0, BackClipPlaneValue), new Point2D(1.0, BackClipPlaneValue));
	
	double rightFrontClipPlaneValue = Util3d.valueForLine2DCrossLine2D(new Line2D(new Point2D(0.0, 0.0),
										      new Point2D(projectionWindowWidth / 2.0, projectionPlaneValue)),
									   new Line2D(new Point2D(0.0, FrontClipPlaneValue),
										      new Point2D(1.0, FrontClipPlaneValue)));

	double leftFrontClipPlaneValue = -rightFrontClipPlaneValue;
	RightClipPlaneBoundary = new Line2D(new Point2D(rightFrontClipPlaneValue, FrontClipPlaneValue), new Point2D(rightBackClipPlaneValue, BackClipPlaneValue));
	LeftClipPlaneBoundary = new Line2D(new Point2D(leftFrontClipPlaneValue, FrontClipPlaneValue), new Point2D(leftBackClipPlaneValue, BackClipPlaneValue));

	double topFrontClipPlaneValue = Util3d.valueForLine2DCrossLine2D(new Line2D(new Point2D(0.0, 0.0),
										    new Point2D(projectionWindowHeight / 2.0, projectionPlaneValue)),
									 new Line2D(new Point2D(0.0, FrontClipPlaneValue),
										    new Point2D(1.0, FrontClipPlaneValue)));

	double bottomFrontClipPlaneValue = -topFrontClipPlaneValue;
	TopClipPlaneBoundary = new Line2D(new Point2D(topFrontClipPlaneValue, FrontClipPlaneValue), new Point2D(topBackClipPlaneValue, BackClipPlaneValue));
	BottomClipPlaneBoundary = new Line2D(new Point2D(bottomFrontClipPlaneValue, FrontClipPlaneValue), new Point2D(bottomBackClipPlaneValue, BackClipPlaneValue));

	zBuffer = new ZBuffer(w, h, Double.MAX_VALUE, BackgroundColor, BackClipPlaneValue);
	screen = new Screen(w, h);

	transformationReset();
    }

    public ZBuffer getZBuffer(){
	return zBuffer;
    }

    public synchronized void transformationReset(){
	double[][] a44 = new double[4][4];
	a44[0][0] = 1.0; a44[0][1] = 0.0; a44[0][2] = 0.0; a44[0][3] = 0.0;
	a44[1][0] = 0.0; a44[1][1] = 1.0; a44[1][2] = 0.0; a44[1][3] = 0.0;
	a44[2][0] = 0.0; a44[2][1] = 0.0; a44[2][2] = 1.0; a44[2][3] = 0.0;
	a44[3][0] = 0.0; a44[3][1] = 0.0; a44[3][2] = 0.0; a44[3][3] = 1.0;

	TransformationMatrix = new Matrix4x4(a44);
    }
    
    public synchronized void updateTransformationByTranslation(double x, double y, double z){
	updateTransformationByTranslation(new Point3D(x, y, z));
    }
    
    public synchronized void updateTransformationByTranslation(Point3D p){
	double[][] a44 = new double[4][4];

	a44[0][0] = 1.0; a44[0][1] = 0.0; a44[0][2] = 0.0; a44[0][3] = -p.x;
	a44[1][0] = 0.0; a44[1][1] = 1.0; a44[1][2] = 0.0; a44[1][3] = -p.y;
	a44[2][0] = 0.0; a44[2][1] = 0.0; a44[2][2] = 1.0; a44[2][3] = -p.z;
	a44[3][0] = 0.0; a44[3][1] = 0.0; a44[3][2] = 0.0; a44[3][3] = 1.0;

	Matrix4x4 A = new Matrix4x4(a44);

	TransformationMatrix = A.multiply4x4(TransformationMatrix);
    }

    public synchronized void updateTransformationByXRotation(double theta){	
	// The camera is rotated by degree theta hence points should be
	// rotated by the opposite degree -theta:
	double cosTheta = Math.cos(-theta);
	double sinTheta = Math.sin(-theta);

	double[][] a44 = new double[4][4];

	a44[0][0] = 1.0; a44[0][1] = 0.0;       a44[0][2] = 0.0;      a44[0][3] = 0.0;
	a44[1][0] = 0.0; a44[1][1] = cosTheta;  a44[1][2] = sinTheta; a44[1][3] = 0.0;
	a44[2][0] = 0.0; a44[2][1] = -sinTheta; a44[2][2] = cosTheta; a44[2][3] = 0.0;
	a44[3][0] = 0.0; a44[3][1] = 0.0;       a44[3][2] = 0.0;      a44[3][3] = 1.0;

	Matrix4x4 A = new Matrix4x4(a44);

	TransformationMatrix = TransformationMatrix.multiply4x4(A);
    }

    public synchronized void updateTransformationByYRotation(double theta){
	// The camera is rotated by degree theta hence points should be
	// rotated by the opposite degree -theta:
	double cosTheta = Math.cos(-theta);
	double sinTheta = Math.sin(-theta);

	double[][] a44 = new double[4][4];

	a44[0][0] = cosTheta; a44[0][1] = 0.0; a44[0][2] = -sinTheta; a44[0][3] = 0.0;
	a44[1][0] = 0.0;      a44[1][1] = 1.0; a44[1][2] = 0.0;       a44[1][3] = 0.0;
	a44[2][0] = sinTheta; a44[2][1] = 0.0; a44[2][2] = cosTheta;  a44[2][3] = 0.0;
	a44[3][0] = 0.0;      a44[3][1] = 0.0; a44[3][2] = 0.0;       a44[3][3] = 1.0;

	Matrix4x4 A = new Matrix4x4(a44);

	TransformationMatrix = TransformationMatrix.multiply4x4(A);
    }

    public synchronized void updateTransformationByZRotation(double theta){
	// The camera is rotated by degree theta in the xy-plane.
	// The following matrix rotates in the yx-plane
	// (giving opposite rotation). Hence the sign for theta
	// is already ok, because the transformation is about rotating
	// points in the scene (which rotate opposite than the camera):
	double cosTheta = Math.cos(theta);
	double sinTheta = Math.sin(theta);

	double[][] a44 = new double[4][4];

	a44[0][0] = cosTheta;  a44[0][1] = sinTheta; a44[0][2] = 0.0; a44[0][3] = 0.0;
	a44[1][0] = -sinTheta; a44[1][1] = cosTheta; a44[1][2] = 0.0; a44[1][3] = 0.0;
	a44[2][0] = 0.0;       a44[2][1] = 0.0;      a44[2][2] = 1.0; a44[2][3] = 0.0;
	a44[3][0] = 0.0;       a44[3][1] = 0.0;      a44[3][2] = 0.0; a44[3][3] = 1.0;

	Matrix4x4 A = new Matrix4x4(a44);

	TransformationMatrix = TransformationMatrix.multiply4x4(A);
    }

    public void clearScene() throws Exception{
	RasterPoint[][] raster = zBuffer.clear();
	screen.drawRaster(raster);
    }
    
    public <T> void updateScene(ArrayList<T> geoms) throws Exception{
	T geom;
	
	Iterator<T> geomsIterator = geoms.iterator();
	
	while (geomsIterator.hasNext()){
	    geom = geomsIterator.next();
	    updateScene(geom);
	}
    }
    
    public <T> void updateScene(T geom) throws Exception{
	ArrayList<T> geoms = new ArrayList<T>(){{add(geom);}};
	ArrayList<T> transformedGeoms, clippedGeoms;
	ArrayList<T> granularGeoms;
	ArrayList<T> projectedGeoms;
	double epsilon = 150.0;

	transformedGeoms = Util3d.transformGeoms(geoms, TransformationMatrix);
	clippedGeoms = Util3d.clipGeoms(transformedGeoms, FrontClipPlaneBoundary, BackClipPlaneBoundary,
					LeftClipPlaneBoundary, RightClipPlaneBoundary,
					TopClipPlaneBoundary, BottomClipPlaneBoundary);
	granularGeoms = Util3d.ensureGranularity(clippedGeoms, epsilon);
	projectedGeoms = Util3d.projectGeoms(granularGeoms, ProjectionPlaneValue);
	zBuffer.rasterizeGeoms(projectedGeoms, ProjectionWindowWidth, ProjectionWindowHeight);
    }

    public void showScene() throws Exception{
	screen.drawRaster(zBuffer.getRaster());
    }
}

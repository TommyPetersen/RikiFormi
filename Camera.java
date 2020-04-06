package RikiFormi;

import java.awt.*;
import java.util.*;
import RikiFormi.Utils.*;

public class Camera{
    protected double ProjectionPlaneValue = 0.0;
    protected double ProjectionWindowWidth = 0.0;
    protected double ProjectionWindowHeight = 0.0;
    protected CameraBoundingVolume cameraBoundingVolume = null;
    protected Matrix4x4 TransformationMatrix = null;
    protected ZBuffer zBuffer;
    protected Screen screen;

    public Camera(double frontPlaneValue,
		  double projectionPlaneValue,
		  double projectionWindowWidth,
		  double projectionWindowHeight,
		  int w,
		  int h) throws Exception{

	if (frontPlaneValue <= 0.0){
	    throw new Exception("The value for frontPlaneValue must be positive");
	}
	
	double backPlaneValue = projectionPlaneValue + 2000.0;
	
	cameraBoundingVolume = new CameraBoundingVolume(frontPlaneValue,
							backPlaneValue,
							projectionPlaneValue,
							projectionWindowWidth,
							projectionWindowHeight);

	ProjectionPlaneValue = projectionPlaneValue;
	ProjectionWindowWidth = projectionWindowWidth;
	ProjectionWindowHeight = projectionWindowHeight;
	
	if (w <= 0 || h <= 0){
	    throw new Exception("The values for W and H must be positive");
	}
	
	zBuffer = new ZBuffer(w, h, Double.MAX_VALUE, Color.black, backPlaneValue);
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
	clippedGeoms = Util3d.clipGeoms(transformedGeoms, cameraBoundingVolume);
	granularGeoms = Util3d.ensureGranularity(clippedGeoms, epsilon);
	projectedGeoms = Util3d.projectGeoms(granularGeoms, ProjectionPlaneValue);
	zBuffer.rasterizeGeoms(projectedGeoms, ProjectionWindowWidth, ProjectionWindowHeight);
    }

    public void showScene() throws Exception{
	screen.drawRaster(zBuffer.getRaster());
    }
}

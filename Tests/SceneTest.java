package RikiFormi.Tests;

import java.util.*;
import java.awt.*;
import RikiFormi.*;

public class SceneTest{

    Camera cam;

    ArrayList<Line3D> lines = new ArrayList<Line3D>();
    Line3D line = null;

    public SceneTest() throws Exception{
	//makeTestLine();
	//makeTestLineCut();
	//makeTestStarBlink();
	//makeTestRectangles();
	//doTestPolygonLinesCut();
	doTestPolygonN();
	//doTestTriangle();
	//doTestDegenerateTriangle();
    }

    private void makeTestRectangles() throws Exception{
        cam = new Camera(40.0,
			 70.0,
			 600.0,
			 400.0,
			 1600,
			 1000);

	makeTestRectangle(20.0, 100.0,
			  20.0, 100.0,
			  150.0);
	makeTestRectangle(20.0, 100.0,
			  20.0, 100.0,
			  500.0);
	makeTestRectangle(20.0, 100.0,
			  20.0, 100.0,
			  1500.0);
	
	makeTestRectangle(-20.0, -100.0,
			  20.0, 100.0,
			  150.0);
	makeTestRectangle(-20.0, -100.0,
			  20.0, 100.0,
			  500.0);
	makeTestRectangle(-20.0, -100.0,
			  20.0, 100.0,
			  1500.0);
	
	makeTestRectangle(20.0, 100.0,
			  -20.0, -100.0,
			  150.0);
	makeTestRectangle(20.0, 100.0,
			  -20.0, -100.0,
			  500.0);
	makeTestRectangle(20.0, 100.0,
			  -20.0, -100.0,
			  1500.0);

	makeTestRectangle(-20.0, -100.0,
			  -20.0, -100.0,
			  150.0);
	makeTestRectangle(-20.0, -100.0,
			  -20.0, -100.0,
			  500.0);
	makeTestRectangle(-20.0, -100.0,
			  -20.0, -100.0,
			  1500.0);
	cam.updateScene(lines);
	cam.showScene();
    }
    
    private void makeTestRectangle(double x, double deltaX,
				   double y, double deltaY,
				   double z) throws Exception{
	System.out.printf("*** makeTestRectangle ***\n");
	
	Polygon3D polygon3d = new Polygon3D();

	polygon3d.addPoint(new Point3D(x, y, z, Color.white));
	polygon3d.addPoint(new Point3D(x + deltaX, y, z, Color.red));
	polygon3d.addPoint(new Point3D(x + deltaX, y + deltaY, z, Color.white));
	polygon3d.addPoint(new Point3D(x, y + deltaY, z, Color.red));
	
	lines.addAll(polygon3d.toLine3dList());
    }

    private void doTestPolygonN() throws Exception{
        cam = new Camera(10.0,
			 70.0,
			 600.0,
			 400.0,
			 1600,
			 1000);

	Polygon3D polygon3d1;
	
	double dx, dy, z, dz = 0.0;

	dx = 0.0;
	dy = 0.0;
	dz = 0.0;
	z = 11;
	
	polygon3d1 = new Polygon3D();
	polygon3d1.addPoint(new Point3D(-22.0 + dx, 0.0 + dy, z + dz, Color.red));
	polygon3d1.addPoint(new Point3D(-10.0 + dx, -20.0 + dy, z + dz, Color.yellow));
	polygon3d1.addPoint(new Point3D(10.0 + dx, -20.0 + dy, z + dz, Color.green));
	polygon3d1.addPoint(new Point3D(22.0 + dx, 0.0 + dy, z + dz, Color.cyan));
	polygon3d1.addPoint(new Point3D(10.0 + dx, 20.0 + dy, z + dz, Color.blue));
	polygon3d1.addPoint(new Point3D(-10.0 + dx, 20.0 + dy, z + dz, Color.magenta));

	cam.updateScene(polygon3d1);
	cam.showScene();
    }

    private void doTestTriangle() throws Exception{
	System.out.printf("*** doTestTriangle ***\n");
	
        cam = new Camera(10.0,
			 70.0,
			 600.0,
			 400.0,
			 1600,
			 1000);

	double dx1, dy1, z1, dz1 = 0.0;

	dx1 = 0.0;
	dy1 = 0.0;
	dz1 = 0.0;
	z1 = 11;

	Polygon3D polygon3d1 = new Polygon3D();

	polygon3d1.addPoint(new Point3D(-22.0 + dx1, 0.0 + dy1, z1, Color.blue));
	polygon3d1.addPoint(new Point3D(-10.0 + dx1, 20.0 + dy1, z1, Color.darkGray));
	polygon3d1.addPoint(new Point3D(10.0 + dx1, -2.0 + dy1, z1 + dz1, Color.white));

	System.out.printf("*** Line lengths ***\n");

	Line3D polygonLine;
	ArrayList<Line3D> lines = polygon3d1.toLine3dList();
	Iterator<Line3D> lineIterator = lines.iterator();

	while (lineIterator.hasNext()){
	    polygonLine = lineIterator.next();
	    System.out.println("polygonLine: " + polygonLine.toString());
	    System.out.println("Line length = " + polygonLine.length());
	}

	cam.updateScene(polygon3d1);
	cam.showScene();
    }

    private void doTestPolygonLinesCut() throws Exception{
        cam = new Camera(40.0,
			 70.0,
			 600.0,
			 400.0,
			 1600,
			 1000);

	Polygon3D polygon3d = new Polygon3D();

	polygon3d.addPoint(new Point3D(20.0, -30.0, 100.0, Color.blue));
	polygon3d.addPoint(new Point3D(50.0, -30.0, 50.0, Color.cyan));
	polygon3d.addPoint(new Point3D(50.0, 30.0, 30.0, Color.green));
	polygon3d.addPoint(new Point3D(20.0, 30.0, 100.0, Color.magenta));

	lines = polygon3d.toLine3dList();
	cam.updateScene(lines);
	cam.showScene();
    }

    private void makeTestStarBlink() throws Exception{
	System.out.printf("*** makeTestStarBlink ***\n");

        cam = new Camera(40.0,
			 70.0,
			 600.0,
			 400.0,
			 1600,
			 1000);

	for (int i = 0; i < 1000; i++){
	    makeTestStar(true);
	    cam.updateScene(lines);
	    cam.showScene();
	    Thread.sleep(1000);
	    makeTestStar(false);
	    cam.updateScene(lines);
	    cam.showScene();
	    Thread.sleep(1000);
	}
    }
    
    private void makeTestStar(boolean beamOutwards){
	double r = 150.0;
	for (double theta = 0.0; theta <= 2.0 * Math.PI; theta = theta + 0.05){
	    if (beamOutwards){
		line = new Line3D(new Point3D(0.0, 0.0, 150.0, Color.white),
				  new Point3D(r * Math.cos(theta), r * Math.sin(theta),
					      150.0, Color.red));
		lines.add(line);
	    } else {
		line = new Line3D(new Point3D(r * Math.cos(theta), r * Math.sin(theta),
					      150.0, Color.white),
				  new Point3D(0.0, 0.0, 150.0, Color.red));
		lines.add(line);
	    }
	}
    }

    private void makeTestLine() throws Exception{
	System.out.printf("*** makeTestLine ***\n");

        cam = new Camera(40.0,
			 70.0,
			 600.0,
			 400.0,
			 1600,
			 1000);

	line = new Line3D(new Point3D(20.0, 20.0, 150.0, Color.white),
			  new Point3D(120.0, 20.0, 150.0, Color.red));
	lines.add(line);
	cam.updateScene(lines);
	cam.showScene();
    }

    private void makeTestLineCut() throws Exception{
	System.out.printf("*** makeTestLineCut ***\n");

        cam = new Camera(40.0,
			 70.0,
			 600.0,
			 400.0,
			 1600,
			 1000);

	line = new Line3D(new Point3D(30.0, 20.0, 500.0, Color.white),
			  new Point3D(30.0, 20.0, 40.0, Color.red));
	lines.add(line);
			  
	line = new Line3D(new Point3D(-30.0, 20.0, 500.0, Color.white),
			  new Point3D(-30.0, 20.0, -1050.0, Color.red));
	lines.add(line);
	
	cam.updateScene(lines);
	cam.showScene();
    }

    private void doTestDegenerateTriangle()throws Exception{
	System.out.printf("*** doTestDegenerateTriangle ***\n");
	
        cam = new Camera(10.0,
			 70.0,
			 600.0,
			 400.0,
			 1600,
			 1000);

	Polygon3D polygon3d = new Polygon3D();

	polygon3d.addPoint(new Point3D(-55.15151515151516, 10.606060606060609, 70.0, Color.blue));
	polygon3d.addPoint(new Point3D(-89.0909090909091, -31.818181818181817, 70.0, Color.blue));
	polygon3d.addPoint(new Point3D(12.72727272727272, 95.45454545454545, 70.0, Color.blue));

	System.out.printf("*** Line lengths ***\n");

	Line3D polygonLine;
	ArrayList<Line3D> lines = polygon3d.toLine3dList();
	Iterator<Line3D> lineIterator = lines.iterator();

	while (lineIterator.hasNext()){
	    polygonLine = lineIterator.next();
	    System.out.println("Line length = " + polygonLine.length());
	}

	cam.updateScene(polygon3d);
	cam.showScene();
    }

    public static void main(String[] args) throws Exception{
	SceneTest sceneTest = new SceneTest();
    }
}

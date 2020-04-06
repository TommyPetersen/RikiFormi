package RikiFormi.Tests;

import java.util.*;
import RikiFormi.*;
import RikiFormi.Utils.*;

public class CameraTest extends Camera{

    CameraTest() throws Exception{
        super(5.0, 50.0, 600.0, 400.0, 600, 400); //FrontClipPlaneValue,
	                                          //ProjectionPlaneValue,
	                                          //ProjectionWindowWidth,
	                                          //ProjectionWindowHeight,
	                                          //W,
	                                          //H

	//test_valueForLine2DCrossLine2D();
	//test_clipLine();
	//test_transformGeom();
	test_projectLine();
    }

    private void test_valueForLine2DCrossLine2D(){
	System.out.printf("*** Testing valueForLine2DCrossLine2D ***\n");
	
	ProjectionPlaneValue = 50.0; ProjectionWindowWidth = 600.0; ProjectionWindowHeight = 400.0;
	
	Line2D constant1 = new Line2D(new Point2D(0.0, 1.0), new Point2D(1.0, 1.0));
	double newX;
	System.out.printf("TEST1: newX is %.2f\t[%s]\n",
			  newX = Util3d.valueForLine2DCrossLine2D(new Line2D(new Point2D(10.0, 20.0), new Point2D(15.0, 5.0)), constant1),
			  (newX > 15.0 ? "Passed" : "Failed")
			  );

	System.out.printf("TEST2: newX is %.2f\t[%s]\n",
			  newX = Util3d.valueForLine2DCrossLine2D(new Line2D(new Point2D(10.0, 20.0), new Point2D(15.0, 10.0)), constant1),
			  (newX > 15.0 ? "Passed" : "Failed")
			 );

	System.out.printf("TEST3: newX is %.2f\t[%s]\n",
			  newX = Util3d.valueForLine2DCrossLine2D(new Line2D(new Point2D(10.0, 5.0), new Point2D(15.0, 20.0)), constant1),
			  (newX < 10.0 ? "Passed" : "Failed")
			 );

	System.out.printf("TEST4: newX is %.2f\t[%s]\n",
			  newX = Util3d.valueForLine2DCrossLine2D(new Line2D(new Point2D(10.0, 5.0), new Point2D(15.0, 10.0)), constant1),
			  (newX < 10.0 ? "Passed" : "Failed")
			 );

	System.out.printf("TEST5: newX is %.2f\t[%s]\n",
			  newX = Util3d.valueForLine2DCrossLine2D(new Line2D(new Point2D(10.0, 5.0), new Point2D(10.0, 20.0)), constant1),
			  (newX == 10.0 ? "Passed" : "Failed")
			 );

	System.out.printf("TEST6: newX is %.2f\t[%s]\n",
			  newX = Util3d.valueForLine2DCrossLine2D(new Line2D(new Point2D(10.0, 2.0), new Point2D(10.0, 2.0)), constant1),
			  (Double.isNaN(newX) ? "Passed" : "Failed")
			 );
    }

    private void test_clipLine() throws Exception{
	System.out.printf("*** Testing clipLine ***\n");

	ProjectionPlaneValue = 50.0; ProjectionWindowWidth = 600.0; ProjectionWindowHeight = 400.0;

	Line3D lineToClip;
	Line3D clippedLine;
	boolean passed = false;
	Line2D constant2 = new Line2D(new Point2D(0.0, 2.0), new Point2D(1.0, 2.0));
	Line2D constant7 = new Line2D(new Point2D(0.0, 7.0), new Point2D(1.0, 7.0));
	Line2D constant70 = new Line2D(new Point2D(0.0, 70.0), new Point2D(1.0, 70.0));

	// Test1a:
	lineToClip = new Line3D(new Point3D(15.0, 5.0, 15.0),
			        new Point3D(15.0, 5.0, 5.0));

	clippedLine = Util3d.clipLine(lineToClip, ClipPlaneName.FRONT, constant2);
	
	System.out.printf("Test1a: clippedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\t[%s]\n",
			  clippedLine.P0.x, clippedLine.P0.y, clippedLine.P0.z,
			  clippedLine.P1.x, clippedLine.P1.y, clippedLine.P1.z,
			  ((clippedLine.P0.x == lineToClip.P0.x)
			   && (clippedLine.P0.y == lineToClip.P0.y)
			   && (clippedLine.P0.z == lineToClip.P0.z)
			   && (clippedLine.P1.x == lineToClip.P1.x)
			   && (clippedLine.P1.y == lineToClip.P1.y)
			   && (clippedLine.P1.z == lineToClip.P1.z)) ? "Passed" : "Failed");

	// Test1b:
	lineToClip = new Line3D(new Point3D(15.0, 5.0, 5.0),
				new Point3D(15.0, 5.0, 15.0));

	clippedLine = Util3d.clipLine(lineToClip, ClipPlaneName.FRONT, constant2);
	
	System.out.printf("Test1b: clippedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\t[%s]\n",
			  clippedLine.P0.x, clippedLine.P0.y, clippedLine.P0.z,
			  clippedLine.P1.x, clippedLine.P1.y, clippedLine.P1.z,
			  ((clippedLine.P0.x == lineToClip.P0.x)
			   && (clippedLine.P0.y == lineToClip.P0.y)
			   && (clippedLine.P0.z == lineToClip.P0.z)
			   && (clippedLine.P1.x == lineToClip.P1.x)
			   && (clippedLine.P1.y == lineToClip.P1.y)
			   && (clippedLine.P1.z == lineToClip.P1.z)) ? "Passed" : "Failed");

	// Test2a:
	lineToClip = new Line3D(new Point3D(15.0, 5.0, 15.0),
			        new Point3D(15.0, 5.0, 5.0));

	clippedLine = Util3d.clipLine(lineToClip, ClipPlaneName.FRONT, constant7);
	
	System.out.printf("Test2a: clippedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\t[%s]\n",
			  clippedLine.P0.x, clippedLine.P0.y, clippedLine.P0.z,
			  clippedLine.P1.x, clippedLine.P1.y, clippedLine.P1.z,
			  ((clippedLine.P0.x == lineToClip.P0.x)
			   && (clippedLine.P0.y == lineToClip.P0.y)
			   && (clippedLine.P0.z == lineToClip.P0.z)
			   && (clippedLine.P1.x == lineToClip.P1.x)
			   && (clippedLine.P1.y == lineToClip.P1.y)
			   && (clippedLine.P1.z == 7.0)) ? "Passed" : "Failed");
	
	// Test2b:
	lineToClip = new Line3D(new Point3D(15.0, 5.0, 5.0),
				new Point3D(15.0, 5.0, 15.0));

	clippedLine = Util3d.clipLine(lineToClip, ClipPlaneName.FRONT, constant7);
	
	System.out.printf("Test2b: clippedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\t[%s]\n",
			  clippedLine.P0.x, clippedLine.P0.y, clippedLine.P0.z,
			  clippedLine.P1.x, clippedLine.P1.y, clippedLine.P1.z,
			  ((clippedLine.P0.x == lineToClip.P0.x)
			   && (clippedLine.P0.y == lineToClip.P0.y)
			   && (clippedLine.P0.z == 7.0)
			   && (clippedLine.P1.x == lineToClip.P1.x)
			   && (clippedLine.P1.y == lineToClip.P1.y)
			   && (clippedLine.P1.z == lineToClip.P1.z)) ? "Passed" : "Failed");
	
	// Test3a:
	lineToClip = new Line3D(new Point3D(15.0, 5.0, 15.0),
			        new Point3D(15.0, 5.0, 5.0));

	clippedLine = Util3d.clipLine(lineToClip, ClipPlaneName.FRONT, constant70);
	
	System.out.printf("Test3a: clippedLine is " + clippedLine + "\t\t\t\t\t\t\t[%s]\n",
			  (clippedLine == null) ? "Passed" : "Failed");
	
	// Test3b:
	lineToClip = new Line3D(new Point3D(15.0, 5.0, 5.0),
				new Point3D(15.0, 5.0, 15.0));

	clippedLine = Util3d.clipLine(lineToClip, ClipPlaneName.FRONT, constant70);
	
	System.out.printf("Test3b: clippedLine is " + clippedLine + "\t\t\t\t\t\t\t[%s]\n",
			  (clippedLine == null) ? "Passed" : "Failed");

	// Test4a:
	lineToClip = new Line3D(new Point3D(15.0, 5.0, 15.0),
			        new Point3D(25.0, 5.0, 5.0));

	clippedLine = Util3d.clipLine(lineToClip, ClipPlaneName.FRONT, constant7);
	
	System.out.printf("Test4a: clippedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\t[%s]\n",
			  clippedLine.P0.x, clippedLine.P0.y, clippedLine.P0.z,
			  clippedLine.P1.x, clippedLine.P1.y, clippedLine.P1.z,
			  ((clippedLine.P0.x == lineToClip.P0.x)
			   && (clippedLine.P0.y == lineToClip.P0.y)
			   && (clippedLine.P0.z == lineToClip.P0.z)
			   && (clippedLine.P1.x > 15.0 && clippedLine.P1.x < 25.0)
			   && (clippedLine.P1.y == lineToClip.P1.y)
			   && (clippedLine.P1.z == 7.0)) ? "Passed" : "Failed");
	
	// Test4b:
	lineToClip = new Line3D(new Point3D(25.0, 5.0, 5.0),
				new Point3D(15.0, 5.0, 15.0));

	clippedLine = Util3d.clipLine(lineToClip, ClipPlaneName.FRONT, constant7);
	
	System.out.printf("Test4b: clippedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\t[%s]\n",
			  clippedLine.P0.x, clippedLine.P0.y, clippedLine.P0.z,
			  clippedLine.P1.x, clippedLine.P1.y, clippedLine.P1.z,
			  ((clippedLine.P0.x > 15.0 && clippedLine.P0.x < 25.0)
			   && (clippedLine.P0.y == lineToClip.P0.y)
			   && (clippedLine.P0.z == 7.0))
			   && (clippedLine.P1.x == lineToClip.P1.x)
			   && (clippedLine.P1.y == lineToClip.P1.y)
			   && (clippedLine.P1.z == lineToClip.P1.z) ? "Passed" : "Failed");

	// Test5a:
	lineToClip = new Line3D(new Point3D(15.0, 5.0, 15.0),
			        new Point3D(15.0, 15.0, 5.0));

	clippedLine = Util3d.clipLine(lineToClip, ClipPlaneName.FRONT, constant7);
	
	System.out.printf("Test5a: clippedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\t[%s]\n",
			  clippedLine.P0.x, clippedLine.P0.y, clippedLine.P0.z,
			  clippedLine.P1.x, clippedLine.P1.y, clippedLine.P1.z,
			  ((clippedLine.P0.x == lineToClip.P0.x)
			   && (clippedLine.P0.y == lineToClip.P0.y)
			   && (clippedLine.P0.z == lineToClip.P0.z)
			   && (clippedLine.P1.x == lineToClip.P1.x)
			   && (clippedLine.P1.y > 5.0 && clippedLine.P1.y < 15.0)
			   && (clippedLine.P1.z == 7.0)) ? "Passed" : "Failed");
	
	// Test5b:
	lineToClip = new Line3D(new Point3D(15.0, 15.0, 5.0),
				new Point3D(15.0, 5.0, 15.0));

	clippedLine = Util3d.clipLine(lineToClip, ClipPlaneName.FRONT, constant7);
	
	System.out.printf("Test5b: clippedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\t[%s]\n",
			  clippedLine.P0.x, clippedLine.P0.y, clippedLine.P0.z,
			  clippedLine.P1.x, clippedLine.P1.y, clippedLine.P1.z,
			  ((clippedLine.P0.x == lineToClip.P0.x)
			   && (clippedLine.P0.y > 5.0 && clippedLine.P0.y < 15.0)
			   && (clippedLine.P0.z == 7.0))
			   && (clippedLine.P1.x == lineToClip.P1.x)
			   && (clippedLine.P1.y == lineToClip.P1.y)
			   && (clippedLine.P1.z == lineToClip.P1.z) ? "Passed" : "Failed");
	
	// Test6a:
	lineToClip = new Line3D(new Point3D(15.0, 5.0, 15.0),
			        new Point3D(25.0, 15.0, 5.0));

	clippedLine = Util3d.clipLine(lineToClip, ClipPlaneName.FRONT, constant7);
	
	System.out.printf("Test6a: clippedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\t[%s]\n",
			  clippedLine.P0.x, clippedLine.P0.y, clippedLine.P0.z,
			  clippedLine.P1.x, clippedLine.P1.y, clippedLine.P1.z,
			  ((clippedLine.P0.x == lineToClip.P0.x)
			   && (clippedLine.P0.y == lineToClip.P0.y)
			   && (clippedLine.P0.z == lineToClip.P0.z)
			   && (clippedLine.P1.x > 15.0 && clippedLine.P1.x < 25.0)
			   && (clippedLine.P1.y > 5.0 && clippedLine.P1.y < 15.0)
			   && (clippedLine.P1.z == 7.0)) ? "Passed" : "Failed");

	// Test6b:
	lineToClip = new Line3D(new Point3D(25.0, 15.0, 5.0),
				new Point3D(15.0, 5.0, 15.0));

	clippedLine = Util3d.clipLine(lineToClip, ClipPlaneName.FRONT, constant7);
	
	System.out.printf("Test6b: clippedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\t[%s]\n",
			  clippedLine.P0.x, clippedLine.P0.y, clippedLine.P0.z,
			  clippedLine.P1.x, clippedLine.P1.y, clippedLine.P1.z,
			  ((clippedLine.P0.x > 15.0 && clippedLine.P0.x < 25.0)
			   && (clippedLine.P0.y > 5.0 && clippedLine.P0.y < 15.0)
			   && (clippedLine.P0.z == 7.0)
			   && (clippedLine.P1.x == lineToClip.P1.x)
			   && (clippedLine.P1.y == lineToClip.P1.y)
			   && (clippedLine.P1.z == lineToClip.P1.z)) ? "Passed" : "Failed");
    }
    
    private void test_transformGeom() throws Exception{
	System.out.printf("*** Testing transformGeom ***\n");

	ProjectionPlaneValue = 50.0; ProjectionWindowWidth = 600.0; ProjectionWindowHeight = 400.0;

	Line3D lineToTransform, transformedLine;

	// *** Test when no camera movement ***

	// Test1:
	lineToTransform = new Line3D(new Point3D(10.0, 20.0, 15.0),
				     new Point3D(20.0, 0.0, 50.0));

	transformationReset();

	transformedLine = Util3d.transformGeom(lineToTransform, TransformationMatrix);

	System.out.printf("Test1: transformedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Length of transformedLine: %.4f, length of lineToTransform: %.4f" +
			  "\t\t\t[%s]\n",
			  transformedLine.P0.x, transformedLine.P0.y, transformedLine.P0.z,
			  transformedLine.P1.x, transformedLine.P1.y, transformedLine.P1.z,
			  transformedLine.length(), lineToTransform.length(),
			  ((Math.round(transformedLine.length() * 10000.0) / 10000.0 ==
			    Math.round(lineToTransform.length() * 10000.0) / 10000.0)
			   && (transformedLine.P0.x == lineToTransform.P0.x)
			   && (transformedLine.P0.y == lineToTransform.P0.y)
			   && (transformedLine.P0.z == lineToTransform.P0.z)
			   && (transformedLine.P1.x == lineToTransform.P1.x)
			   && (transformedLine.P1.y == lineToTransform.P1.y)
			   && (transformedLine.P1.z == lineToTransform.P1.z)) ? "Passed" : "Failed");

	// *** Test when camera translation ***

	// Test2a:
	lineToTransform = new Line3D(new Point3D(10.0, 20.0, 15.0),
				     new Point3D(20.0, 0.0, 50.0));

	transformationReset();
	updateTransformationByTranslation(new Point3D(0.0, 0.0, 10.0));
	
	transformedLine = Util3d.transformGeom(lineToTransform, TransformationMatrix);

	System.out.printf("Test2a: transformedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Length of transformedLine: %.4f, length of lineToTransform: %.4f" +
			  "\t\t\t[%s]\n",
			  transformedLine.P0.x, transformedLine.P0.y, transformedLine.P0.z,
			  transformedLine.P1.x, transformedLine.P1.y, transformedLine.P1.z,
			  transformedLine.length(), lineToTransform.length(),
			  ((Math.round(transformedLine.length() * 10000.0) / 10000.0 ==
			    Math.round(lineToTransform.length() * 10000.0) / 10000.0)
			   && (transformedLine.P0.x == lineToTransform.P0.x)
			   && (transformedLine.P0.y == lineToTransform.P0.y)
			   && (transformedLine.P0.z < lineToTransform.P0.z)
			   && (transformedLine.P1.x == lineToTransform.P1.x)
			   && (transformedLine.P1.y == lineToTransform.P1.y)
			   && (transformedLine.P1.z < lineToTransform.P1.z)) ? "Passed" : "Failed");

	// Test2b:
	lineToTransform = new Line3D(new Point3D(10.0, 20.0, 15.0),
				     new Point3D(20.0, 0.0, 50.0));

	transformationReset();
	updateTransformationByTranslation(new Point3D(0.0, 0.0, -10.0));
	
	transformedLine = Util3d.transformGeom(lineToTransform, TransformationMatrix);

	System.out.printf("Test2b: transformedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Length of transformedLine: %.4f, length of lineToTransform: %.4f" +
			  "\t\t\t[%s]\n",
			  transformedLine.P0.x, transformedLine.P0.y, transformedLine.P0.z,
			  transformedLine.P1.x, transformedLine.P1.y, transformedLine.P1.z,
			  transformedLine.length(), lineToTransform.length(),
			  ((Math.round(transformedLine.length() * 10000.0) / 10000.0 ==
			    Math.round(lineToTransform.length() * 10000.0) / 10000.0)
			   && (transformedLine.P0.x == lineToTransform.P0.x)
			   && (transformedLine.P0.y == lineToTransform.P0.y)
			   && (transformedLine.P0.z > lineToTransform.P0.z)
			   && (transformedLine.P1.x == lineToTransform.P1.x)
			   && (transformedLine.P1.y == lineToTransform.P1.y)
			   && (transformedLine.P1.z > lineToTransform.P1.z)) ? "Passed" : "Failed");

	// Test3a:
	lineToTransform = new Line3D(new Point3D(10.0, 20.0, 15.0),
				     new Point3D(20.0, 0.0, 50.0));

	transformationReset();
	updateTransformationByTranslation(new Point3D(0.0, 10.0, 0.0));
	
	transformedLine = Util3d.transformGeom(lineToTransform, TransformationMatrix);

	System.out.printf("Test3a: transformedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Length of transformedLine: %.4f, length of lineToTransform: %.4f" +
			  "\t\t\t[%s]\n",
			  transformedLine.P0.x, transformedLine.P0.y, transformedLine.P0.z,
			  transformedLine.P1.x, transformedLine.P1.y, transformedLine.P1.z,
			  transformedLine.length(), lineToTransform.length(),
			  ((Math.round(transformedLine.length() * 10000.0) / 10000.0 ==
			    Math.round(lineToTransform.length() * 10000.0) / 10000.0)
			   && (transformedLine.P0.x == lineToTransform.P0.x)
			   && (transformedLine.P0.y < lineToTransform.P0.y)
			   && (transformedLine.P0.z == lineToTransform.P0.z)
			   && (transformedLine.P1.x == lineToTransform.P1.x)
			   && (transformedLine.P1.y < lineToTransform.P1.y)
			   && (transformedLine.P1.z == lineToTransform.P1.z)) ? "Passed" : "Failed");

	// Test3b:
	lineToTransform = new Line3D(new Point3D(10.0, 20.0, 15.0),
				     new Point3D(20.0, 0.0, 50.0));

	transformationReset();
	updateTransformationByTranslation(new Point3D(0.0, -10.0, 0.0));
	
	transformedLine = Util3d.transformGeom(lineToTransform, TransformationMatrix);

	System.out.printf("Test3b: transformedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Length of transformedLine: %.4f, length of lineToTransform: %.4f" +
			  "\t\t\t[%s]\n",
			  transformedLine.P0.x, transformedLine.P0.y, transformedLine.P0.z,
			  transformedLine.P1.x, transformedLine.P1.y, transformedLine.P1.z,
			  transformedLine.length(), lineToTransform.length(),
			  ((Math.round(transformedLine.length() * 10000.0) / 10000.0 ==
			    Math.round(lineToTransform.length() * 10000.0) / 10000.0)
			   && (transformedLine.P0.x == lineToTransform.P0.x)
			   && (transformedLine.P0.y > lineToTransform.P0.y)
			   && (transformedLine.P0.z == lineToTransform.P0.z)
			   && (transformedLine.P1.x == lineToTransform.P1.x)
			   && (transformedLine.P1.y > lineToTransform.P1.y)
			   && (transformedLine.P1.z == lineToTransform.P1.z)) ? "Passed" : "Failed");

	// Test4a:
	lineToTransform = new Line3D(new Point3D(10.0, 20.0, 15.0),
				     new Point3D(20.0, 0.0, 50.0));

	transformationReset();
	updateTransformationByTranslation(new Point3D(10.0, 0.0, 0.0));
	
	transformedLine = Util3d.transformGeom(lineToTransform, TransformationMatrix);

	System.out.printf("Test4a: transformedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Length of transformedLine: %.4f, length of lineToTransform: %.4f" +
			  "\t\t\t[%s]\n",
			  transformedLine.P0.x, transformedLine.P0.y, transformedLine.P0.z,
			  transformedLine.P1.x, transformedLine.P1.y, transformedLine.P1.z,
			  transformedLine.length(), lineToTransform.length(),
			  ((Math.round(transformedLine.length() * 10000.0) / 10000.0 ==
			    Math.round(lineToTransform.length() * 10000.0) / 10000.0)
			   && (transformedLine.P0.x < lineToTransform.P0.x)
			   && (transformedLine.P0.y == lineToTransform.P0.y)
			   && (transformedLine.P0.z == lineToTransform.P0.z)
			   && (transformedLine.P1.x < lineToTransform.P1.x)
			   && (transformedLine.P1.y == lineToTransform.P1.y)
			   && (transformedLine.P1.z == lineToTransform.P1.z)) ? "Passed" : "Failed");

	// Test4b:
	lineToTransform = new Line3D(new Point3D(10.0, 20.0, 15.0),
				     new Point3D(20.0, 0.0, 50.0));

	transformationReset();
	updateTransformationByTranslation(new Point3D(-10.0, 0.0, 0.0));
	
	transformedLine = Util3d.transformGeom(lineToTransform, TransformationMatrix);

	System.out.printf("Test4b: transformedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Length of transformedLine: %.4f, length of lineToTransform: %.4f" +
			  "\t\t\t[%s]\n",
			  transformedLine.P0.x, transformedLine.P0.y, transformedLine.P0.z,
			  transformedLine.P1.x, transformedLine.P1.y, transformedLine.P1.z,
			  transformedLine.length(), lineToTransform.length(),
			  ((Math.round(transformedLine.length() * 10000.0) / 10000.0 ==
			    Math.round(lineToTransform.length() * 10000.0) / 10000.0)
			   && (transformedLine.P0.x > lineToTransform.P0.x)
			   && (transformedLine.P0.y == lineToTransform.P0.y)
			   && (transformedLine.P0.z == lineToTransform.P0.z)
			   && (transformedLine.P1.x > lineToTransform.P1.x)
			   && (transformedLine.P1.y == lineToTransform.P1.y)
			   && (transformedLine.P1.z == lineToTransform.P1.z)) ? "Passed" : "Failed");

	// *** Test when camera X rotation ***
	
	// Test5a:
	lineToTransform = new Line3D(new Point3D(0.0, 0.0, 0.0),
				     new Point3D(0.0, 0.0, 30.0));

	transformationReset();
	updateTransformationByXRotation(Math.PI / 4.0); // Positive rotation (counter clockwise)
	
	transformedLine = Util3d.transformGeom(lineToTransform, TransformationMatrix);

	System.out.printf("Test5a: transformedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Length of transformedLine: %.4f, length of lineToTransform: %.4f" +
			  "\t\t\t[%s]\n",
			  transformedLine.P0.x, transformedLine.P0.y, transformedLine.P0.z,
			  transformedLine.P1.x, transformedLine.P1.y, transformedLine.P1.z,
			  transformedLine.length(), lineToTransform.length(),
			  ((Math.round(transformedLine.length() * 10000.0) / 10000.0 ==
			    Math.round(lineToTransform.length() * 10000.0) / 10000.0)
			   && (transformedLine.P0.x == lineToTransform.P0.x)
			   && (transformedLine.P0.y == lineToTransform.P0.y)
			   && (transformedLine.P0.z == lineToTransform.P0.z)
			   && (transformedLine.P1.x == lineToTransform.P1.x)
			   && (transformedLine.P1.y < lineToTransform.P1.y)
			   && (transformedLine.P1.z < lineToTransform.P1.z)) ? "Passed" : "Failed");

	// Test5b:
	lineToTransform = new Line3D(new Point3D(0.0, 0.0, 0.0),
				     new Point3D(0.0, 0.0, 30.0));

	transformationReset();
	updateTransformationByXRotation(-Math.PI / 4.0); // Negative rotation (clockwise)
	
	transformedLine = Util3d.transformGeom(lineToTransform, TransformationMatrix);

	System.out.printf("Test5b: transformedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Length of transformedLine: %.4f, length of lineToTransform: %.4f" +
			  "\t\t\t[%s]\n",
			  transformedLine.P0.x, transformedLine.P0.y, transformedLine.P0.z,
			  transformedLine.P1.x, transformedLine.P1.y, transformedLine.P1.z,
			  transformedLine.length(), lineToTransform.length(),
			  ((Math.round(transformedLine.length() * 10000.0) / 10000.0 ==
			    Math.round(lineToTransform.length() * 10000.0) / 10000.0)
			   && (transformedLine.P0.x == lineToTransform.P0.x)
			   && (transformedLine.P0.y == lineToTransform.P0.y)
			   && (transformedLine.P0.z == lineToTransform.P0.z)
			   && (transformedLine.P1.x == lineToTransform.P1.x)
			   && (transformedLine.P1.y > lineToTransform.P1.y)
			   && (transformedLine.P1.z < lineToTransform.P1.z)) ? "Passed" : "Failed");

	// *** Test when camera Y rotation ***
	
	// Test6a:
	lineToTransform = new Line3D(new Point3D(0.0, 0.0, 0.0),
				     new Point3D(30.0, 0.0, 0.0));

	transformationReset();
	updateTransformationByYRotation(Math.PI / 4.0); // Positive rotation (counter clockwise)
	
	transformedLine = Util3d.transformGeom(lineToTransform, TransformationMatrix);

	System.out.printf("Test6a: transformedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Length of transformedLine: %.4f, length of lineToTransform: %.4f" +
			  "\t\t\t[%s]\n",
			  transformedLine.P0.x, transformedLine.P0.y, transformedLine.P0.z,
			  transformedLine.P1.x, transformedLine.P1.y, transformedLine.P1.z,
			  transformedLine.length(), lineToTransform.length(),
			  ((Math.round(transformedLine.length() * 10000.0) / 10000.0 ==
			    Math.round(lineToTransform.length() * 10000.0) / 10000.0)
			   && (transformedLine.P0.x == lineToTransform.P0.x)
			   && (transformedLine.P0.y == lineToTransform.P0.y)
			   && (transformedLine.P0.z == lineToTransform.P0.z)
			   && (transformedLine.P1.x < lineToTransform.P1.x)
			   && (transformedLine.P1.y == lineToTransform.P1.y)
			   && (transformedLine.P1.z < lineToTransform.P1.z)) ? "Passed" : "Failed");

	// Test6b:
	lineToTransform = new Line3D(new Point3D(0.0, 0.0, 0.0),
				     new Point3D(30.0, 0.0, 0.0));

	transformationReset();
	updateTransformationByYRotation(-Math.PI / 4.0); // Negative rotation (clockwise)
	
	transformedLine = Util3d.transformGeom(lineToTransform, TransformationMatrix);

	System.out.printf("Test6b: transformedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Length of transformedLine: %.4f, length of lineToTransform: %.4f" +
			  "\t\t\t[%s]\n",
			  transformedLine.P0.x, transformedLine.P0.y, transformedLine.P0.z,
			  transformedLine.P1.x, transformedLine.P1.y, transformedLine.P1.z,
			  transformedLine.length(), lineToTransform.length(),
			  ((Math.round(transformedLine.length() * 10000.0) / 10000.0 ==
			    Math.round(lineToTransform.length() * 10000.0) / 10000.0)
			   && (transformedLine.P0.x == lineToTransform.P0.x)
			   && (transformedLine.P0.y == lineToTransform.P0.y)
			   && (transformedLine.P0.z == lineToTransform.P0.z)
			   && (transformedLine.P1.x < lineToTransform.P1.x)
			   && (transformedLine.P1.y == lineToTransform.P1.y)
			   && (transformedLine.P1.z > lineToTransform.P1.z)) ? "Passed" : "Failed");

	// *** Test when camera Z rotation ***
	
	// Test7a:
	lineToTransform = new Line3D(new Point3D(0.0, 0.0, 0.0),
				     new Point3D(30.0, 0.0, 0.0));

	transformationReset();
	updateTransformationByZRotation(Math.PI / 4.0); // Positive rotation (counter clockwise)
	
	transformedLine = Util3d.transformGeom(lineToTransform, TransformationMatrix);

	System.out.printf("Test7a: transformedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Length of transformedLine: %.4f, length of lineToTransform: %.4f" +
			  "\t\t\t[%s]\n",
			  transformedLine.P0.x, transformedLine.P0.y, transformedLine.P0.z,
			  transformedLine.P1.x, transformedLine.P1.y, transformedLine.P1.z,
			  transformedLine.length(), lineToTransform.length(),
			  ((Math.round(transformedLine.length() * 10000.0) / 10000.0 ==
			    Math.round(lineToTransform.length() * 10000.0) / 10000.0)
			   && (transformedLine.P0.x == lineToTransform.P0.x)
			   && (transformedLine.P0.y == lineToTransform.P0.y)
			   && (transformedLine.P0.z == lineToTransform.P0.z)
			   && (transformedLine.P1.x < lineToTransform.P1.x)
			   && (transformedLine.P1.y < lineToTransform.P1.y)
			   && (transformedLine.P1.z == lineToTransform.P1.z)) ? "Passed" : "Failed");

	// Test7b:
	lineToTransform = new Line3D(new Point3D(0.0, 0.0, 0.0),
				     new Point3D(30.0, 0.0, 0.0));

	transformationReset();
	updateTransformationByZRotation(-Math.PI / 4.0); // Negative rotation (clockwise)
	
	transformedLine = Util3d.transformGeom(lineToTransform, TransformationMatrix);

	System.out.printf("Test7b: transformedLine is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Length of transformedLine: %.4f, length of lineToTransform: %.4f" +
			  "\t\t\t[%s]\n",
			  transformedLine.P0.x, transformedLine.P0.y, transformedLine.P0.z,
			  transformedLine.P1.x, transformedLine.P1.y, transformedLine.P1.z,
			  transformedLine.length(), lineToTransform.length(),
			  ((Math.round(transformedLine.length() * 10000.0) / 10000.0 ==
			    Math.round(lineToTransform.length() * 10000.0) / 10000.0)
			   && (transformedLine.P0.x == lineToTransform.P0.x)
			   && (transformedLine.P0.y == lineToTransform.P0.y)
			   && (transformedLine.P0.z == lineToTransform.P0.z)
			   && (transformedLine.P1.x < lineToTransform.P1.x)
			   && (transformedLine.P1.y > lineToTransform.P1.y)
			   && (transformedLine.P1.z == lineToTransform.P1.z)) ? "Passed" : "Failed");
    }

    private void test_projectLine() throws Exception{
	System.out.printf("*** Testing projectLine ***\n");

	ProjectionPlaneValue = 50.0; ProjectionWindowWidth = 600.0; ProjectionWindowHeight = 400.0;

	Line3D rail1, rail2;
	Line3D projectedRail1, projectedRail2;
	double railDistance, projectedRailDistance1, projectedRailDistance2;

	// *** Test "railroad" in the plane Y = 0 ***

	// Test1:
	rail1 = new Line3D(new Point3D(-20.0, 0.0, 100.0),
			   new Point3D(-20.0, 0.0, 1000.0));
	rail2 = new Line3D(new Point3D(20.0, 0.0, 100.0),
			   new Point3D(20.0, 0.0, 1000.0));
	railDistance = new Line3D(rail1.P0,
				  rail2.P0).length();

	projectedRail1 = Util3d.projectLine(rail1, ProjectionPlaneValue);
	projectedRail2 = Util3d.projectLine(rail2, ProjectionPlaneValue);
	projectedRailDistance1 = new Line3D(projectedRail1.P0,
					    projectedRail2.P0).length();
	projectedRailDistance2 = new Line3D(projectedRail1.P1,
					    projectedRail2.P1).length();

	System.out.printf("Test1: projectedRail1 is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "ProjectedRail2 is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Distance between projected P0s: %.4f, Distance between projected P1s: %.4f" +
			  "\t\t\t[%s]\n",
			  projectedRail1.P0.x, projectedRail1.P0.y, projectedRail1.P0.z,
			  projectedRail1.P1.x, projectedRail1.P1.y, projectedRail1.P1.z,
			  projectedRail2.P0.x, projectedRail2.P0.y, projectedRail2.P0.z,
			  projectedRail2.P1.x, projectedRail2.P1.y, projectedRail2.P1.z,
			  projectedRailDistance1, projectedRailDistance2,
			  ((Math.round(projectedRailDistance1 * 10000.0) / 10000.0 >
			    Math.round(projectedRailDistance2 * 10000.0) / 10000.0)
			   && (projectedRail1.P0.x < projectedRail1.P1.x)
			   && (projectedRail2.P0.x > projectedRail2.P1.x)) ? "Passed" : "Failed");

	// *** Test "railroad" in the plane X = 0 ***

	// Test2:
	rail1 = new Line3D(new Point3D(0.0, -20.0, 100.0),
			   new Point3D(0.0, -20.0, 1000.0));
	rail2 = new Line3D(new Point3D(0.0, 20.0, 100.0),
			   new Point3D(0.0, 20.0, 1000.0));
	railDistance = new Line3D(rail1.P0,
				  rail2.P0).length();

	projectedRail1 = Util3d.projectLine(rail1, ProjectionPlaneValue);
	projectedRail2 = Util3d.projectLine(rail2, ProjectionPlaneValue);
	projectedRailDistance1 = new Line3D(projectedRail1.P0,
					    projectedRail2.P0).length();
	projectedRailDistance2 = new Line3D(projectedRail1.P1,
					    projectedRail2.P1).length();

	System.out.printf("Test2: projectedRail1 is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "ProjectedRail2 is p0 = (%.2f, %.2f, %.2f)" +
			  " and p1 = (%.2f, %.2f, %.2f)\n" +
			  "Distance between projected P0s: %.4f, Distance between projected P1s: %.4f" +
			  "\t\t\t[%s]\n",
			  projectedRail1.P0.x, projectedRail1.P0.y, projectedRail1.P0.z,
			  projectedRail1.P1.x, projectedRail1.P1.y, projectedRail1.P1.z,
			  projectedRail2.P0.x, projectedRail2.P0.y, projectedRail2.P0.z,
			  projectedRail2.P1.x, projectedRail2.P1.y, projectedRail2.P1.z,
			  projectedRailDistance1, projectedRailDistance2,
			  ((Math.round(projectedRailDistance1 * 10000.0) / 10000.0 >
			    Math.round(projectedRailDistance2 * 10000.0) / 10000.0)
			   && (projectedRail1.P0.y < projectedRail1.P1.y)
			   && (projectedRail2.P0.y > projectedRail2.P1.y)) ? "Passed" : "Failed");
    }
    
    public static void main(String[] args) throws Exception{
	CameraTest cameraTest = new CameraTest();
    }
}

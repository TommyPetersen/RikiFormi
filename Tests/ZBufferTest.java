package RikiFormi.Tests;

import java.util.*;
import java.awt.*;
import RikiFormi.*;

public class ZBufferTest extends ZBuffer{

    ZBufferTest() throws Exception{
	super(600, 400, Double.MAX_VALUE, Color.black, 1500.0);

	test_sortRasterPoints();
    }

    private void test_sortRasterPoints() throws Exception{
	System.out.printf("*** Testing sortRasterPoints ***\n");

	RasterPoint rasterPoint0, rasterPoint1, rasterPoint2;
	OrderedTriple<RasterPoint> rasterPoints;

	int a0;
	int a1;
	int a2;

	// *** Test1 ***
	rasterPoint0 = new RasterPoint(10, 20, 100.0, Color.blue);
	rasterPoint1 = new RasterPoint(15, 40, 100.0, Color.blue);
	rasterPoint2 = new RasterPoint(20, 10, 100.0, Color.blue);

	rasterPoints = sortRasterPointsBySecondCoordinate(rasterPoint0, rasterPoint1, rasterPoint2);

	a0 = rasterPoints.getFirst().a;
	a1 = rasterPoints.getSecond().a;
	a2 = rasterPoints.getThird().a;

	System.out.printf("TEST1:\n" +
			  "p0 is %d\t[%s]\n" +
			  "p1 is %d\t[%s]\n" +
			  "p2 is %d\t[%s]\n",
			  a0, (a0 == 10 ? "Passed" : "Failed"),
			  a1, (a1 == 15 ? "Passed" : "Failed"),
			  a2, (a2 == 20 ? "Passed" : "Failed")
			 );
	
	// *** Test2 ***
	rasterPoint0 = new RasterPoint(15, 40, 100.0, Color.blue);
	rasterPoint1 = new RasterPoint(20, 10, 100.0, Color.blue);
	rasterPoint2 = new RasterPoint(10, 20, 100.0, Color.blue);

	rasterPoints = sortRasterPointsBySecondCoordinate(rasterPoint0, rasterPoint1, rasterPoint2);

	a0 = rasterPoints.getFirst().a;
	a1 = rasterPoints.getSecond().a;
	a2 = rasterPoints.getThird().a;

	System.out.printf("TEST2:\n" +
			  "p0 is %d\t[%s]\n" +
			  "p1 is %d\t[%s]\n" +
			  "p2 is %d\t[%s]\n",
			  a0, (a0 == 10 ? "Passed" : "Failed"),
			  a1, (a1 == 15 ? "Passed" : "Failed"),
			  a2, (a2 == 20 ? "Passed" : "Failed")
			 );

	// *** Test3 ***
	rasterPoint0 = new RasterPoint(20, 10, 100.0, Color.blue);
	rasterPoint1 = new RasterPoint(10, 20, 100.0, Color.blue);
	rasterPoint2 = new RasterPoint(15, 40, 100.0, Color.blue);

	rasterPoints = sortRasterPointsBySecondCoordinate(rasterPoint0, rasterPoint1, rasterPoint2);

	a0 = rasterPoints.getFirst().a;
	a1 = rasterPoints.getSecond().a;
	a2 = rasterPoints.getThird().a;

	System.out.printf("TEST2:\n" +
			  "p0 is %d\t[%s]\n" +
			  "p1 is %d\t[%s]\n" +
			  "p2 is %d\t[%s]\n",
			  a0, (a0 == 10 ? "Passed" : "Failed"),
			  a1, (a1 == 15 ? "Passed" : "Failed"),
			  a2, (a2 == 20 ? "Passed" : "Failed")
			 );
    }

    public static void main(String[] args) throws Exception{
	ZBufferTest zBufferTest = new ZBufferTest();
    }
}

package RikiFormi.Tests;

import RikiFormi.*;
import RikiFormi.Utils.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class UtilTest{

    UtilTest() throws Exception{
	testLine2DCrossLine2D();
    }

    public void testLine2DCrossLine2D() throws Exception{
	Line2D l1, l2;

	//Test1:
	l1 = new Line2D(new Point2D(10.0, 20.0), new Point2D(100.0, 20.0));
	l2 = new Line2D(new Point2D(10.0, 40.0), new Point2D(100.0, 40.0));
	System.out.printf("Test1: [%s]\n", (Double.isNaN(Util3d.valueForLine2DCrossLine2D(l1, l2)) ? "Passed" : "Failed"));

	//Test2:
	l1 = new Line2D(new Point2D(0.0, 0.0), new Point2D(100.0, 100.0));
	l2 = new Line2D(new Point2D(30.0, 50.0), new Point2D(70.0, 50.0));
	System.out.printf("Test2: [%s]\n", (Util3d.valueForLine2DCrossLine2D(l1, l2) == 50.00 ? "Passed" : "Failed"));

	//Test3:
	l1 = new Line2D(new Point2D(0.0, 0.0), new Point2D(100.0, 100.0));
	l2 = new Line2D(new Point2D(-30.0, -50.0), new Point2D(-70.0, -50.0));
	System.out.printf("Test3: [%s]\n", (Util3d.valueForLine2DCrossLine2D(l1, l2) == -50.00 ? "Passed" : "Failed"));

	//Test4:
	l1 = new Line2D(new Point2D(10.0, 0.0), new Point2D(10.0, 100.0));
	l2 = new Line2D(new Point2D(30.0, 50.0), new Point2D(70.0, 50.0));
	System.out.printf("Test4: [%s]\n", (Util3d.valueForLine2DCrossLine2D(l1, l2) == 10.00 ? "Passed" : "Failed"));
    }

    public static void main(String[] args) throws Exception{
	UtilTest utilTest = new UtilTest();
    }
}

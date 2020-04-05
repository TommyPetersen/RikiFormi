package RikiFormi;

import java.util.*;
import java.awt.*;

public class Scene{

    Camera cam;
    
    public Scene() throws Exception{
	ArrayList<Line3D> lines;

        cam = new Camera(5.0,
			 150.0,
			 600.0,
			 400.0,
			 600,
			 400);

	//lines = initSceneObject("testLine");
	//lines = initSceneObject("testLines");
	lines = initSceneObject("testRectangles");
	
	cam.updateScene(lines);
    }

    public ArrayList<Line3D> initSceneObject(String name) throws Exception{

	ArrayList<Line3D> lines = new ArrayList<Line3D>();
	Line3D line = null;

	switch (name){
	case "testRectangles":
	    double z = 500.0;
	    line = new Line3D(new Point3D(20.0, 20.0, z, Color.white),
			      new Point3D(120.0, 20.0, z, Color.red));
	    lines.add(line);
	    line = new Line3D(new Point3D(120.0, 20.0, z, Color.red),
			      new Point3D(120.0, 120.0, z, Color.white));
	    lines.add(line);
	    line = new Line3D(new Point3D(120.0, 120.0, z, Color.white),
			      new Point3D(20.0, 120.0, z, Color.red));
	    lines.add(line);
	    line = new Line3D(new Point3D(20.0, 120.0, z, Color.red),
			      new Point3D(20.0, 20.0, z, Color.white));
	    lines.add(line);
	    
	    z = 150.0;
	    line = new Line3D(new Point3D(20.0, 20.0, z, Color.white),
			      new Point3D(120.0, 20.0, z, Color.red));
	    lines.add(line);
	    line = new Line3D(new Point3D(120.0, 20.0, z, Color.red),
			      new Point3D(120.0, 120.0, z, Color.white));
	    lines.add(line);
	    line = new Line3D(new Point3D(120.0, 120.0, z, Color.white),
			      new Point3D(20.0, 120.0, z, Color.red));
	    lines.add(line);
	    line = new Line3D(new Point3D(20.0, 120.0, z, Color.red),
			      new Point3D(20.0, 20.0, z, Color.white));
	    lines.add(line);
	    
	    z = 500.0;
	    line = new Line3D(new Point3D(-20.0, 20.0, z, Color.white),
			      new Point3D(-120.0, 20.0, z, Color.red));
	    lines.add(line);
	    line = new Line3D(new Point3D(-120.0, 20.0, z, Color.red),
			      new Point3D(-120.0, 120.0, z, Color.white));
	    lines.add(line);
	    line = new Line3D(new Point3D(-120.0, 120.0, z, Color.white),
			      new Point3D(-20.0, 120.0, z, Color.red));
	    lines.add(line);
	    line = new Line3D(new Point3D(-20.0, 120.0, z, Color.red),
			      new Point3D(-20.0, 20.0, z, Color.white));
	    lines.add(line);
	    
	    z = 150.0;
	    line = new Line3D(new Point3D(-20.0, 20.0, z, Color.white),
			      new Point3D(-120.0, 20.0, z, Color.red));
	    lines.add(line);
	    line = new Line3D(new Point3D(-120.0, 20.0, z, Color.red),
			      new Point3D(-120.0, 120.0, z, Color.white));
	    lines.add(line);
	    line = new Line3D(new Point3D(-120.0, 120.0, z, Color.white),
			      new Point3D(-20.0, 120.0, z, Color.red));
	    lines.add(line);
	    line = new Line3D(new Point3D(-20.0, 120.0, z, Color.red),
			      new Point3D(-20.0, 20.0, z, Color.white));
	    lines.add(line);
	    break;
	default:
	    break;
	}
	
	return lines;
    }

    public static void main(String[] arg) throws Exception{
	Scene scene = new Scene();
    }
}

package RikiFormi.Tests;

import RikiFormi.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class SerializableTest{

    SerializableTest() throws Exception{
	testSerializablePoint3D();
	testSerializableTriangle3D();
	testSerializableListOfTriangle3D();
    }

    public void testSerializablePoint3D() throws Exception{
	// *** Object before ***
	Point3D point3dBefore = new Point3D(0.0, 30.0, 20.0, Color.blue);
	
	// *** Serialization ***
	serializeObject(point3dBefore, "point3d.ser");
	
	// *** Deserialization / Object after ***
	Point3D point3dAfter = (Point3D)deserializeObject("point3d.ser");
	
	// *** Equality test ***
	System.out.printf("EqualityTest: [%s]\n", (point3dAfter.equals(point3dBefore)));
    }

    public void testSerializableTriangle3D() throws Exception{
	// *** Object before ***
	Triangle3D triangle3dBefore = new Triangle3D(
						     new Point3D(0.0, 30.0, 20.0, Color.blue),
						     new Point3D(10.0, 40.0, 30.0, Color.yellow),
						     new Point3D(20.0, 50.0, 40.0, Color.red)
						     );
	// *** Serialization ***
	serializeObject(triangle3dBefore, "triangle3d.ser");
	
	// *** Deserialization / Object after ***
	Triangle3D triangle3dAfter = (Triangle3D)deserializeObject("triangle3d.ser");

	// *** Equality test ***
	System.out.printf("EqualityTest: [%s]\n", (triangle3dAfter.equals(triangle3dBefore)));
    }

    @SuppressWarnings("unchecked") // Suppress warnings on cast from deserialization.
    public void testSerializableListOfTriangle3D() throws Exception{
	// *** Object before ***
	Triangle3D triangle3dBefore1 = new Triangle3D(
						      new Point3D(0.0, 30.0, 20.0, Color.blue),
						      new Point3D(10.0, 40.0, 30.0, Color.yellow),
						      new Point3D(20.0, 50.0, 40.0, Color.red)
						      );
	Triangle3D triangle3dBefore2 = new Triangle3D(
						      new Point3D(5.0, 30.0, 20.0, Color.white),
						      new Point3D(15.0, 40.0, 30.0, Color.blue),
						      new Point3D(25.0, 50.0, 40.0, Color.gray)
						      );
	
	// *** Serialization ***
	ArrayList<Triangle3D> trianglesBefore = new ArrayList<Triangle3D>();
	trianglesBefore.add(triangle3dBefore1);
	trianglesBefore.add(triangle3dBefore2);

	serializeObject(trianglesBefore, "triangles.ser");
	
	// *** Deserialization ***
	ArrayList<Triangle3D> trianglesAfter = (ArrayList<Triangle3D>)deserializeObject("triangles.ser");

	Triangle3D triangle3dAfter1 = (Triangle3D)trianglesAfter.get(0);
	Triangle3D triangle3dAfter2 = (Triangle3D)trianglesAfter.get(1);

	// *** Equality test ***
	System.out.printf("EqualityTest: [%s]\n",
			  (trianglesAfter.size() == 2 &&
			   trianglesAfter.size() == trianglesBefore.size() &&
			   triangle3dAfter1.equals(triangle3dBefore1) &&
			   triangle3dAfter2.equals(triangle3dBefore2)));
    }
    
    private void serializeObject(Object objectToSerialize, String fileName) throws Exception{
	FileOutputStream fileOut = new FileOutputStream(fileName);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
	outputStream.writeObject(objectToSerialize);
        outputStream.close();
        fileOut.close();
    }

    private Object deserializeObject(String fileName) throws Exception{
	FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream inStream = new ObjectInputStream(fileIn);
	Object deserializedObject = inStream.readObject();
        inStream.close();
        fileIn.close();
	return deserializedObject;
    }

    public static void main(String[] args) throws Exception{
	SerializableTest serializableTest = new SerializableTest();
    }
}

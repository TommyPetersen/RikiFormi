package RikiFormi;

public class Matrix4x1{

    private double[] a = null;

    public Matrix4x1(double[] b){
	a = new double[4];

	for (int i = 0; i < 4; i++){
	    a[i] = b[i];
	}
    }

    public double[] getTable(){
	return a;
    }

    public void print(){
	System.out.println("");
	System.out.println("[" + a[0] + "\t" + a[1] + "\t" + a[2] + "\t" + a[3] + "]");

    }
}

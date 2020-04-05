package RikiFormi;

public class Matrix3x1{

    private double[] a = null;

    Matrix3x1(double[] b){
	a = new double[3];

	for (int i = 0; i < 3; i++){
	    a[i] = b[i];
	}
    }

    public double[] getTable(){
	return a;
    }

    public void print(){
	System.out.println("");
	System.out.println("[" + a[0] + "\t" + a[1] + "\t" + a[2] + "]");

    }
}

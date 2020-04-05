package RikiFormi;

public class Matrix3x3{

    private double[][] a = null;

    Matrix3x3(double[][] b){
	a = new double[3][3];

	for (int i = 0; i < 3; i++){
	    for (int j = 0; j < 3; j++){
		a[i][j] = b[i][j];
	    }
	}
    }

    public double[][] getTable(){
	return a;
    }

    public Matrix3x3 multiply3x3(Matrix3x3 n){
	double[][] b = n.getTable();
	double[][] c = new double[3][3];

	for (int i = 0; i < 3; i++){
	    for (int k = 0; k < 3; k++){
		c[i][k] = a[i][0] * b[0][k] + a[i][1] * b[1][k] + a[i][2] * b[2][k];
	    }
	}

	return new Matrix3x3(c);
    }

    public Matrix3x1 multiply3x1(Matrix3x1 v){
	double[] b = v.getTable();
	double[] c = new double[3];

	for (int i = 0; i < 3; i++){
	    c[i] = a[i][0] * b[0] + a[i][1] * b[1] + a[i][2] * b[2];
	}

	return new Matrix3x1(c);
    }

    public void print(){
	System.out.println("");
	System.out.println("[" + a[0][0] + "\t" + a[0][1] + "\t" + a[0][2] + "]");
	System.out.println("[" + a[1][0] + "\t" + a[1][1] + "\t" + a[1][2] + "]");
	System.out.println("[" + a[2][0] + "\t" + a[2][1] + "\t" + a[2][2] + "]");
    }

    public static void main(String[] args){
	double[][] a33 = new double[3][3];

	a33[0][0] = 1.0; a33[0][1] = 0.0; a33[0][2] = 0.0;
	a33[1][0] = 0.0; a33[1][1] = 1.0; a33[1][2] = 0.0;
	a33[2][0] = 0.0; a33[2][1] = 0.0; a33[2][2] = 1.0;

	Matrix3x3 m33 = new Matrix3x3(a33);
	m33.print();

	double[][] b33 = new double[3][3];

	b33[0][0] = 4.3; b33[0][1] = 1.2; b33[0][2] = -5.9;
	b33[1][0] = -3.2; b33[1][1] = -4.0; b33[1][2] = 5.3;
	b33[2][0] = -10.3; b33[2][1] = 8.7; b33[2][2] = 9.8;

	Matrix3x3 n33 = new Matrix3x3(b33);
	n33.print();

	double[][] c33 = new double[3][3];

	c33[0][0] = 3.5; c33[0][1] = -4.8; c33[0][2] = -8.9;
	c33[1][0] = 1.9; c33[1][1] = -8.3; c33[1][2] = 4.3;
	c33[2][0] = 3.3; c33[2][1] = 4.9; c33[2][2] = -5.1;

	Matrix3x3 o33 = new Matrix3x3(c33);

	Matrix3x3 p33 = n33.multiply3x3(o33);
	p33.print();

	double[] a31 = new double[3];

	a31[0] = 4.2; a31[1] = 9.8; a31[2] = 4.9;
	
	Matrix3x1 v31 = new Matrix3x1(a31);

	v31.print();

	Matrix3x1 w31 = n33.multiply3x1(v31);

	w31.print();
    }
}

package RikiFormi;

public class Matrix4x4{

    private double[][] a = null;

    Matrix4x4(double[][] b){
	a = new double[4][4];

	for (int i = 0; i < 4; i++){
	    for (int j = 0; j < 4; j++){
		a[i][j] = b[i][j];
	    }
	}
    }

    public double[][] getTable(){
	return a;
    }

    public Matrix4x4 multiply4x4(Matrix4x4 B){
	double[][] b = B.getTable();
	double[][] c = new double[4][4];

	for (int i = 0; i < 4; i++){
	    for (int j = 0; j < 4; j++){
		c[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j] + a[i][2] * b[2][j] + a[i][3] * b[3][j];
	    }
	}

	return new Matrix4x4(c);
    }

    public Matrix4x1 multiply4x1(Matrix4x1 V){
	double[] b = V.getTable();
	double[] c = new double[4];

	for (int i = 0; i < 4; i++){
	    c[i] = a[i][0] * b[0] + a[i][1] * b[1] + a[i][2] * b[2] + a[i][3] * b[3];
	}

	return new Matrix4x1(c);
    }

    public void print(){
	System.out.println("");
	System.out.println("[" + a[0][0] + "\t" + a[0][1] + "\t" + a[0][2] + "\t" + a[0][3] + "]");
	System.out.println("[" + a[1][0] + "\t" + a[1][1] + "\t" + a[1][2] + "\t" + a[1][3] + "]");
	System.out.println("[" + a[2][0] + "\t" + a[2][1] + "\t" + a[2][2] + "\t" + a[2][3] + "]");
	System.out.println("[" + a[3][0] + "\t" + a[3][1] + "\t" + a[3][2] + "\t" + a[3][3] + "]");
    }

    public static void main(String[] args){
	double[][] a44 = new double[4][4];

	a44[0][0] = 1.0; a44[0][1] = 0.0; a44[0][2] = 0.0; a44[0][3] = 0.0;
	a44[1][0] = 0.0; a44[1][1] = 1.0; a44[1][2] = 0.0; a44[1][3] = 0.0;
	a44[2][0] = 0.0; a44[2][1] = 0.0; a44[2][2] = 1.0; a44[2][3] = 0.0;
	a44[3][0] = 0.0; a44[3][1] = 0.0; a44[3][2] = 1.0; a44[3][3] = 1.0;

	Matrix4x4 m44 = new Matrix4x4(a44);
	//	m44.print();

	double[][] b44 = new double[4][4];

	b44[0][0] =  4.4;  b44[0][1] =  1.2; b44[0][2] = -5.9; b44[0][3] = -5.9;
	b44[1][0] = -4.2;  b44[1][1] = -4.0; b44[1][2] =  5.4; b44[1][3] = -5.9;
	b44[2][0] = -10.4; b44[2][1] =  8.7; b44[2][2] =  9.8; b44[2][3] = -5.9;
	b44[3][0] = -10.4; b44[3][1] =  8.7; b44[3][2] =  9.8; b44[3][3] = -5.9;

	Matrix4x4 n44 = new Matrix4x4(b44);
	n44.print();

	double[][] c44 = new double[4][4];

	c44[0][0] = 4.5; c44[0][1] = -4.8; c44[0][2] = -8.9; c44[0][3] = -8.9;
	c44[1][0] = 1.9; c44[1][1] = -8.4; c44[1][2] =  4.4; c44[1][3] = -8.9;
	c44[2][0] = 4.4; c44[2][1] =  4.9; c44[2][2] = -5.1; c44[2][3] = -8.9;
	c44[3][0] = 4.4; c44[3][1] =  4.9; c44[3][2] = -5.1; c44[3][3] = -8.9;

	Matrix4x4 o44 = new Matrix4x4(c44);
	//	o44.print();

	Matrix4x4 p44 = n44.multiply4x4(o44);
	//	p44.print();

	double[] a41 = new double[4];

	a41[0] = 4.2; a41[1] = 9.8; a41[2] = 4.9; a41[3] = 4.9;
	
	Matrix4x1 v41 = new Matrix4x1(a41);

	v41.print();

	Matrix4x1 w41 = n44.multiply4x1(v41);

	w41.print();
    }
}

package linalg;

/*** A class that represents a two dimensional real-valued (double) matrix
 *   and supports various matrix computations required in linear algebra.
 *   
 *   Class and method comments are in JavaDoc: https://en.wikipedia.org/wiki/Javadoc
 *
 */
public class Matrix {

	private int _nRows; // Number of rows in this matrix; nomenclature: _ for data member, n for integer
	private int _nCols; // Number of columns in this matrix; nomenclature: _ for data member, n for integer
	// TODO: add your own data member to represent the matrix content
	//       you could use a 2D array, or an array of Vectors (e.g., for each row)
/*	private double[] _adRows;
	private double[] _adCols;*/
	private double[][] _adRCs;
	
	/** Allocates a new matrix of the given row and column dimensions
	 * 
	 * @param rows
	 * @param cols
	 * @throws LinAlgException if either rows or cols is <= 0
	 */
	public Matrix(int rows, int cols) throws LinAlgException {
		// TODO: hint: see the corresponding Vector constructor
		if (rows <= 0 || cols <= 0) {
			throw new LinAlgException("Both dimensions (" + rows + "," + cols + ") must be greater than 0");
		}
		_nRows = rows;
		_nCols = cols;
		_adRCs = new double[_nRows][_nCols]; // Entries will be automatically initialized to 0.0
	}
	
	/** Copy constructor: makes a new copy of an existing Matrix m
	 *                    (note: this explicitly allocates new memory and copies over content)
	 * 
	 * @param m
	 */
	public Matrix(Matrix m) {
		// TODO: hint: see the corresponding Vector "copy constructor" for an example
		_nRows = m._nRows;
		_nCols = m._nCols;
		_adRCs = new double[_nRows][_nCols]; // This allocates a matrix of size _nRows*_nCols
		for (int i = 0; i < _nRows; i++) {
			for (int j = 0; j < _nCols; j++) {
				_adRCs[i][j] = m._adRCs[i][j];
			}
		}
	}

	/** Constructs a String representation of this Matrix
	 * 
	 */
	public String toString() {
		// TODO: hint: see Vector.toString() for an example
		// We could just repeatedly append to an existing String, but that copies the String each
		// time, whereas a StringBuilder simply appends new characters to the end of the String
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < _nRows; i++) {
			sb.append("[");
			for (int j = 0; j < _nCols; j++) {
					sb.append(String.format(" %6.3f ", _adRCs[i][j])); // Append each vector value in order
			}
			sb.append(" ]" + "\n");
		}
		return sb.toString(); 
	}

	/** Tests whether another Object o (most often a matrix) is a equal to *this*
	 *  (i.e., are the dimensions the same and all elements equal each other?)
	 * 
	 * @param o the object to compare to
	 */
	public boolean equals(Object o) {
		// TODO: hint: see Vector.equals(), you can also use Vector.equals() for checking equality 
		//             of row vectors if you store your matrix as an array of Vectors for rows
		
		// TODO: this should not always return false!
		if (o instanceof Matrix) {
			Matrix m = (Matrix)o; // This is called a cast (or downcast)... we can do it since we
			                      // know from the if statement that o is actually of subtype Matrix
			                      // Makes o Matrix type that has the content of Matrix v
			if (_nRows != m._nRows || _nCols != m._nCols) {
				return false; // Two Matrices cannot be equal if they don't have the same dimension
			}
			for (int i = 0; i < _nRows; i++) {
				for (int j = 0; j < _nCols; j++) {
					if (_adRCs[i][j] != m._adRCs[i][j]) {
						return false; // If two Matrices mismatch at any index, they are not equal
					}
				}
			}
			return true; // Everything matched... objects are equal!
		} 
		else { // if we get here "(o instanceof Matrix)" was false
			return false; // Two objects cannot be equal if they don't have the same class type	
		}
	}
	
	/** Return the number of rows in this matrix
	 *   
	 * @return 
	 */
	public int getNumRows() {
		// TODO (this should not return -1!)
		return _nRows;
	}

	/** Return the number of columns in this matrix
	 *   
	 * @return 
	 */
	public int getNumCols() {
		// TODO (this should not return -1!)
		return _nCols;
	}

	/** Return the scalar value at the given row and column of the matrix
	 * 
	 * @param row
	 * @param col
	 * @return
	 * @throws LinAlgException if row or col indices are out of bounds
	 */
	public double get(int row, int col) throws LinAlgException {
		// TODO (this should not return -1!)
		if (row < 0 || col < 0) {
			throw new LinAlgException("One or both indices (" + row + ", " + col + ") are out of bounds ([0, " + _nRows + "],[0, " + _nCols + "])");
		}
		if (row > (_nRows -1) || col > (_nCols -1)) { // if _nCols is 3, then the array is [0,1,2]
			throw new LinAlgException("One or both indices (" + row + ", " + col + ") are out of bounds ([0, " + _nRows + "],[0, " + _nCols + "])");
		}
		return _adRCs[row][col];
	}
	
	/** Return the Vector of numbers corresponding to the provided row index
	 * 
	 * @param row
	 * @return
	 * @throws LinAlgException if row is out of bounds
	 */
	public Vector getRow(int row) throws LinAlgException {
		// TODO (this should not return null!)
		if (row < 0 || row > (_nRows -1)) { 
			throw new LinAlgException("Row index (" + row + ") out of bounds [0, " + _nRows + "])");
		}
		Vector v = new Vector(_nCols);
		for (int i = 0; i < _nCols; i++) {
			v.set(i, _adRCs[row][i]); //use method from Vector since _adVal is invisible
		}
		return v;
	}

	/** Set the row and col of this matrix to the provided val
	 * 
	 * @param row
	 * @param col
	 * @param val
	 * @throws LinAlgException if row or col indices are out of bounds
	 */
	public void set(int row, int col, double val) throws LinAlgException {
		// TODO
		if (row < 0 || col < 0) {
			throw new LinAlgException("One or both indices (" + row + ", " + col + ") are out of bounds ([0, " + _nRows + "],[0, " + _nCols + "])");
		}
		if (row > (_nRows -1) || col > (_nCols -1)) {
			throw new LinAlgException("One or both indices (" + row + ", " + col + ") are out of bounds ([0, " + _nRows + "],[0, " + _nCols + "])");
		}
		_adRCs[row][col] = val;
	}
	
	/** Return a new Matrix that is the transpose of *this*, i.e., if "transpose"
	 *  is the transpose of Matrix m then for all row, col: transpose[row,col] = m[col,row]
	 *  (should not modify *this*)
	 * 
	 * @return
	 * @throws LinAlgException
	 */
	public Matrix transpose() throws LinAlgException {
		Matrix transpose = new Matrix(_nCols, _nRows);
		for (int row = 0; row < _nRows; row++) {
			for (int col = 0; col < _nCols; col++) {
				transpose.set(col, row, get(row,col)); // get value at the original matrix (1,2); make it the value at (2,1)
			}
		}
		return transpose;
	}

	/** Return a new Matrix that is the square identity matrix (1's on diagonal, 0's elsewhere) 
	 *  with the number of rows, cols given by dim.  E.g., if dim = 3 then the returned matrix
	 *  would be the following:
	 *  
	 *  [ 1 0 0 ]
	 *  [ 0 1 0 ]
	 *  [ 0 0 1 ]
	 * 
	 * @param dim
	 * @return
	 * @throws LinAlgException if the dim is <= 0
	 */
	public static Matrix GetIdentity(int dim) throws LinAlgException {
		// TODO: this should not return null!
		if (dim <= 0) {
			throw new LinAlgException("Size " + dim + " must be greater than 0");
		}
		Matrix newMatrix = new Matrix(dim, dim);
		for (int row = 0; row < newMatrix._nRows; row++) {
			for (int col = 0; col < newMatrix._nCols; col++) {
				if (row == col) {
					newMatrix._adRCs[row][col] = 1;
				}
			}
		}
		return newMatrix;
	}
	
	/** Returns the Matrix result of multiplying Matrix m1 and m2
	 *  (look up the definition of matrix multiply if you don't remember it)
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 * @throws LinAlgException if m1 columns do not match the size of m2 rows
	 */
	public static Matrix Multiply(Matrix m1, Matrix m2) throws LinAlgException {
		// TODO: this should not return null!
		if (m2._nRows != m1._nCols) {
			throw new LinAlgException("Cannot multiply matrix m1 having " + m1._nCols + " columns with matrix m2 having " + m2._nRows + " rows");
		}
		Matrix C = new Matrix(m1._nRows, m2._nCols);
        for (int i = 0; i < C._nRows; i++) {
            for (int j = 0; j < C._nCols; j++) {
                for (int k = 0; k < m1._nCols; k++) { //serve as a counter for both matrices
                    C._adRCs[i][j] += (m1._adRCs[i][k] * m2._adRCs[k][j]);
                }
            }
        }
        return C;
	}
		
	/** Returns the Vector result of multiplying Matrix m by Vector v (assuming v is a column vector)
	 * 
	 * @param m
	 * @param v
	 * @return
	 * @throws LinAlgException if m columns do match the size of v
	 */
	public static Vector Multiply(Matrix m, Vector v) throws LinAlgException {
		// TODO: this should not return null!
		Vector vec = new Vector(m._nRows);
		if (m._nCols != v.getDim()) { //use method from Vector since _nDim is invisible
			throw new LinAlgException("Cannot multiply matrix with " + m._nCols + " columns with a vector of dimension " + v.getDim());
		} 
		for (int row = 0; row < m._nRows; row++) {
			double sum = 0;
			for (int column = 0; column < m._nCols; column++) {
				sum += m._adRCs[row][column] * v.get(column);
		        vec.set(row,sum);
			}
		}
		return vec;
	}
}		

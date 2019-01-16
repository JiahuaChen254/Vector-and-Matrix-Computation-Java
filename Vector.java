package linalg;

/*** A class that represents a multidimensional real-valued (double) vector
 *   and supports various vector computations required in linear algebra.
 *   
 *   Class and method comments are in JavaDoc: https://en.wikipedia.org/wiki/Javadoc
 *
 */
public class Vector {

	private int _nDim;       // Dimension of the Vector; nomenclature: _ for data member, n for integer
	private double[] _adVal; // Contents of the Vector; nomenclature: _ for data member, a for array, d for double

	/** Constructor: allocates space for a new vector of dimension dim
	 * 
	 * @param dim
	 * @throws LinAlgException if vector dimension is < 1
	 */
	public Vector(int dim) throws LinAlgException {
		if (dim <= 0)
			throw new LinAlgException("Vector dimension " + dim + " cannot be less than 1");
		_nDim = dim;
		_adVal = new double[dim]; // Entries will be automatically initialized to 0.0
	}
	
	/** Copy constructor: makes a new copy of an existing Vector v
	 *                    (note: this explicitly allocates new memory and copies over content)
	 * 
	 * @param v
	 */
	public Vector(Vector v) {
		_nDim = v._nDim;
		_adVal = new double[_nDim]; // This allocates an array of size _nDim
		for (int index = 0; index < _nDim; index++)
			_adVal[index] = v._adVal[index];
	}

	/** Constructor: creates a new Vector with dimension and values given by init
	 * 
	 * @param init: a String formatted like "[ -1.2 2.0 3.1 5.8 ]" (must start with [ and end with ])
	 * @throws LinAlgException if init is not properly formatted (missing [ or ], or improperly formatted number)
	 */
	public Vector(String init) throws LinAlgException {
		
		// The following says split init on whitespace (\\s) into an array of Strings
		String[] split = init.split("\\s");  
		// Uncomment the following to see what split produces
		// for (int i = 0; i < split.length; i++)
		// 		System.out.println(i + ". " + split[i]);

		if (!split[0].equals("[") || !split[split.length-1].equals("]"))
			throw new LinAlgException("Malformed vector initialization: missing [ or ] in " + init);

		// We don't count the [ and ] in the dimensionality
		_nDim = split.length - 2;
		_adVal = new double[_nDim];
		
		// Parse each number from init and add it to the Vector in order (note the +1 offset to account for [)
		for (int index = 0; index < _nDim; index++) {
			try {
				set(index, Double.parseDouble(split[index + 1])); // refers to the above set function 
			} catch (NumberFormatException e) {
				throw new LinAlgException("Malformed vector initialization: could not parse " + split[index + 1] + " in " + init);
			}
		}
	}

	/** Overrides method toString() on Object: converts the class to a human readable String
	 * 
	 *  Note 1: this is invoked *automatically* when the object is listed where a String is expected,
	 *          e.g., "System.out.println(v);" is actually equivalent to "System.out.println(v.toString());"       
	 *          
	 *  Note 2: for debugging purposes, you should always define a toString() method on a class you define
	 */
	@Override // optional annotation to tell Java we expect this overrides a parent method -- compiler will warn if not
	public String toString() {
		// We could just repeatedly append to an existing String, but that copies the String each
		// time, whereas a StringBuilder simply appends new characters to the end of the String
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < _nDim; i++)
			sb.append(String.format(" %6.3f ", _adVal[i])); // Append each vector value in order
		sb.append(" ]");
		return sb.toString(); // return sth like an array of string content
	}

	/** Overrides address equality check on Object: allows semantic equality testing of vectors,
	 *  i.e., here we say two objects are equal iff they have the same dimensions and values
	 *        match at all indices
	 * 
	 * Note: you should almost always define equals() since the default equals() on Object simply
	 *       tests that two objects occupy the same space in memory (are actually the same instance), 
	 *       but does not test that two objects may be different instances but have the same content
	 *       
	 * @param o the object to compare to
	 */
	@Override // optional annotation to tell Java we expect this overrides a parent method -- compiler will warn if not
	public boolean equals(Object o) {
		if (o instanceof Vector) {
			Vector v = (Vector)o; // This is called a cast (or downcast)... we can do it since we
			                      // know from the if statement that o is actually of subtype Vector
			                      // Makes o Vector type that has the content of Vector v
			if (_nDim != v._nDim)
				return false; // Two Vectors cannot be equal if they don't have the same dimension
			for (int index = 0; index < _nDim; index++)
				if (_adVal[index] != v._adVal[index])
					return false; // If two Vectors mismatch at any index, they are not equal
			return true; // Everything matched... objects are equal!
		} else // if we get here "(o instanceof Vector)" was false
			return false; // Two objects cannot be equal if they don't have the same class type
	}
	
	/** Get the dimension of this vector
	 * 
	 * @return: the dimensionality of this Vector
	 */
	public int getDim() {
		// TODO (this should not return -1!)
		return _nDim;
	}

	/** Returns the value of this vector at the given index (remember: array indices start at 0)
	 * 
	 * @param index
	 * @return
	 * @throws LinAlgException if array index is out of bounds (see throw examples above)
	 */
	public double get(int index) throws LinAlgException {
		// TODO (this should not return -1.0!)
		if (index < 0 || index >= _nDim) {
			throw new LinAlgException("Index " + index + " is out of bounds [0, " + _nDim + "]");
		}
		return _adVal[index];
	}

	/** Set the value val of the vector at the given index (remember: array indices start at 0)
	 * 
	 * @param index
	 * @param val
	 * @throws LinAlgException if array index is out of bounds (see throw examples above)
	 */
	public void set(int index, double val) throws LinAlgException {
		// TODO
		if (index < 0 || index >= _nDim) {
			throw new LinAlgException("Index " + index + " is out of bounds [0, " + _nDim + "]");
		}
		_adVal[index] = val;
	}
	
	/** Change the dimension of this Vector by *reallocating array storage* and copying content over
	 *  ... if new dim is larger than current dim then the additional indices take value 0.0
	 *  ... if new dim is smaller than current dim then any indices in current vector beyond current
	 *      dim are simply lost
	 * 
	 * @param new_dim
	 * @throws LinAlgException if vector dimension is < 1
	 */
	public void changeDim(int new_dim) throws LinAlgException {
		// TODO
		if (new_dim < 1) {
			throw new LinAlgException("Vector dimension " + new_dim + " cannot be less than 1");
		}
		if (new_dim > _nDim) {
			int old_nDim = _nDim;
			_nDim = new_dim;
			double[] old_adVal = _adVal;
			_adVal = new double[new_dim];
			for (int i = 0; i < old_nDim; i++) {
				_adVal[i] = old_adVal[i];
				for (int j = old_nDim; j < new_dim; j++) {
					_adVal[j] = 0.0;
				}
			}
		}
		if (new_dim <= _nDim) {
			_nDim = new_dim;
			double[] old_adVal = _adVal;
			_adVal = new double[new_dim];
			for (int i = 0; i < new_dim; i++) {
				_adVal[i] = old_adVal[i];
			}
		}
	}
	
	/** This adds a scalar d to all elements of *this* Vector
	 *  (should modify *this*)
	 * 
	 * @param d
	 */
	public void scalarAddInPlace(double d) {
		for (int index = 0; index < _nDim; index++)
			_adVal[index] += d;
	}
	
	/** This creates a new Vector, adds a scalar d to it, and returns it
	 *  (should not modify *this*)
	 * 
	 * @param d
	 * @return new Vector after scalar addition
	 */
	public Vector scalarAdd(double d) {
		// TODO (this should not return null!)
		Vector newVector = new Vector(this);
		for (int index = 0; index < _nDim; index++) {
			newVector._adVal[index] = newVector._adVal[index] + d;
		}
//		can call on newVector.scalarAddInPlace(d);
		return newVector;
	}
	
	/** This multiplies a scalar d by all elements of *this* Vector
	 *  (should modify *this*)
	 * 
	 * @param d
	 */
	public void scalarMultInPlace(double d) {
		// TODO
		for (int index = 0; index < _nDim; index++) {
			_adVal[index] *= d;	
		}
	}
	
	/** This creates a new Vector, multiplies it by a scalar d, and returns it
	 *  (should not modify *this*)
	 * 
	 * @param d
	 * @return new Vector after scalar addition
	 */
	public Vector scalarMult(double d) {
		// TODO (this should not return null!)
		Vector newVector = new Vector(this);
		for (int index = 0; index < _nDim; index++) {
			newVector._adVal[index] *= d;
		}
		return newVector;
	}

	/** Performs an elementwise addition of v to *this*, modifies *this*
	 * 
	 * @param v
	 * @throws LinAlgException if dimensions of the two operand vectors do not match
	 */
	public void elementwiseAddInPlace(Vector v) throws LinAlgException {
		// TODO
		// has the same LinAlgException printed as elementwiseAdd
		if (v._nDim != _nDim) {
			throw new LinAlgException("Cannot elementWiseAdd vectors of different dimensions " + _nDim + " and " + v._nDim);
		}
		for (int index = 0; index < _nDim; index++) {
			_adVal[index] += v._adVal[index];
		}
	}

	/** Performs an elementwise addition of *this* and v and returns a new Vector with result
	 * 
	 * @param v
	 * @return
	 * @throws LinAlgException if dimensions of the two operand vectors do not match
	 */
	public Vector elementwiseAdd(Vector v) throws LinAlgException {
		// TODO (this should not return null!)
		// has the same LinAlgException printed as elementwiseAddInPlace
		Vector newVector = new Vector(this);
		if (v._nDim != _nDim) {
			throw new LinAlgException("Cannot elementWiseAdd vectors of different dimensions " + _nDim + " and " + v._nDim);
		}
		for (int index = 0; index < _nDim; index++) {
			newVector._adVal[index] = v._adVal[index] + _adVal[index];
		}
		return newVector;
	}
	
	/** Performs an elementwise multiplication of v and *this*, modifies *this*
	 * 
	 * @param v
	 * @throws LinAlgException if dimensions of the two operand vectors do not match
	 */
	public void elementwiseMultInPlace(Vector v) throws LinAlgException {
		// TODO
		// has the same LinAlgException printed as elementwiseMult
		if (v._nDim != _nDim) {
			throw new LinAlgException("Cannot elementWiseMult vectors of different dimensions " + _nDim + " and " + v._nDim);
		}
		for (int index = 0; index < _nDim; index++) {
			_adVal[index] *= v._adVal[index];	
		}
	}

	/** Performs an elementwise multiplication of *this* and v and returns a new Vector with result
	 * 
	 * @param v
	 * @return
	 * @throws LinAlgException if dimensions of the two operand vectors do not match
	 */
	public Vector elementwiseMult(Vector v) throws LinAlgException {
		// TODO (this should not return null!)
		// has the same LinAlgException printed as elementwiseMultInPlace
		Vector newVector = new Vector(this);
		if (v._nDim != _nDim) {
			throw new LinAlgException("Cannot elementWiseMult vectors of different dimensions " + _nDim + " and " + v._nDim);
		}
		for (int index = 0; index < _nDim; index++) {
			newVector._adVal[index] = v._adVal[index] * _adVal[index];
		}
		return newVector;
	}

	/** Performs an inner product of Vectors v1 and v2 and returns the scalar result
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @throws LinAlgException
	 */
	public static double InnerProd(Vector v1, Vector v2) throws LinAlgException {
		// TODO (this should not return -1.0!)
		// static: use method in the class, but does not operate on the class,
		// so when called, append class name in front of method instead of appending variable name
		if (v1._nDim != v2._nDim) {
			throw new LinAlgException("Cannot innerProd vectors of different dimensions " + v1._nDim + " and " + v2._nDim);
		}
		double innerproduct = 0.0;
		for (int index = 0; index < v1._nDim; index++) {
			innerproduct += v1._adVal[index] * v2._adVal[index];	
		}
		return innerproduct;
	}
}

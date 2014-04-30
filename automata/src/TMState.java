public class TMState extends State{
	
	private TMState zero;// Transition for 0
	private TMState one; // Transition for 1
	private TMState blank;
	private TMState two;
	private TMState extra;
	
	private char writeZero;
	private char writeOne;
	private char writeBlank;
	private char writeTwo;
	private char writeExtra;
	
	private char moveZero;
	private char moveOne;
	private char moveBlank;
	private char moveTwo;
	private char moveExtra;

	
	public TMState(String name) {
		super(name);	
	}
	
	/**
	 * @return the two
	 */
	public TMState getTwo() {
		return two;
	}

	/**
	 * @param two the two to set
	 */
	public void setTwo(TMState two) {
		this.two = two;
	}

	/**
	 * @return the extra
	 */
	public TMState getExtra() {
		return extra;
	}

	/**
	 * @param extra the extra to set
	 */
	public void setExtra(TMState extra) {
		this.extra = extra;
	}

	/**
	 * @return the writeTwo
	 */
	public char getWriteTwo() {
		return writeTwo;
	}

	/**
	 * @param writeTwo the writeTwo to set
	 */
	public void setWriteTwo(char writeTwo) {
		this.writeTwo = writeTwo;
	}

	/**
	 * @return the writeExtra
	 */
	public char getWriteExtra() {
		return writeExtra;
	}

	/**
	 * @param writeExtra the writeExtra to set
	 */
	public void setWriteExtra(char writeExtra) {
		this.writeExtra = writeExtra;
	}

	/**
	 * @return the moveTwo
	 */
	public char getMoveTwo() {
		return moveTwo;
	}

	/**
	 * @param moveTwo the moveTwo to set
	 */
	public void setMoveTwo(char moveTwo) {
		this.moveTwo = moveTwo;
	}

	/**
	 * @return the moveExtra
	 */
	public char getMoveExtra() {
		return moveExtra;
	}

	/**
	 * @param moveExtra the moveExtra to set
	 */
	public void setMoveExtra(char moveExtra) {
		this.moveExtra = moveExtra;
	}

	

	/**
	 * @return the zero
	 */
	@Override
	public TMState getZero() {
		return zero;
	}

	/**
	 * @param zero the zero to set
	 */
	
	public void setZero(TMState zero) {
		this.zero = zero;
	}

	/**
	 * @return the one
	 */
	@Override
	public TMState getOne() {
		return one;
	}

	/**
	 * @param one the one to set
	 */
	
	public void setOne(TMState one) {
		this.one = one;
	}

	/**
	 * @return the blank
	 */
	public TMState getBlank() {
		return blank;
	}

	/**
	 * @param blank the blank to set
	 */
	public void setBlank(TMState blank) {
		this.blank = blank;
	}

	/**
	 * @return the writeZero
	 */
	public char getWriteZero() {
		return writeZero;
	}

	/**
	 * @param writeZero the writeZero to set
	 */
	public void setWriteZero(char writeZero) {
		this.writeZero = writeZero;
	}

	/**
	 * @return the writeOne
	 */
	public char getWriteOne() {
		return writeOne;
	}

	/**
	 * @param writeOne the writeOne to set
	 */
	public void setWriteOne(char writeOne) {
		this.writeOne = writeOne;
	}

	/**
	 * @return the writeBlank
	 */
	public char getWriteBlank() {
		return writeBlank;
	}

	/**
	 * @param writeBlank the writeBlank to set
	 */
	public void setWriteBlank(char writeBlank) {
		this.writeBlank = writeBlank;
	}

	/**
	 * @return the moveZero
	 */
	public char getMoveZero() {
		return moveZero;
	}

	/**
	 * @param moveZero the moveZero to set
	 */
	public void setMoveZero(char moveZero) {
		this.moveZero = moveZero;
	}

	/**
	 * @return the moveOne
	 */
	public char getMoveOne() {
		return moveOne;
	}

	/**
	 * @param moveOne the moveOne to set
	 */
	public void setMoveOne(char moveOne) {
		this.moveOne = moveOne;
	}

	/**
	 * @return the moveBlank
	 */
	public char getMoveBlank() {
		return moveBlank;
	}

	/**
	 * @param moveBlank the moveBlank to set
	 */
	public void setMoveBlank(char moveBlank) {
		this.moveBlank = moveBlank;
	}


}

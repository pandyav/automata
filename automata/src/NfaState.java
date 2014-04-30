import java.util.LinkedList;

/**
 * 
 */

/**
 * 
 * 
 */
public class NfaState extends State {

	private LinkedList<NfaState> zero;// list to hold all of 0 transitions
	private LinkedList<NfaState> one;// list to hold all of 1 transitions
	private LinkedList<NfaState> ebs;// list to hold all of ebsilon transitions

	/**
	 * constructor takes the name parameter for the super class
	 */
	public NfaState(String name) {
		super(name);
		zero = new LinkedList<NfaState>();
		one = new LinkedList<NfaState>();
		ebs = new LinkedList<NfaState>();
	}

	/**
	 * @return index x from zero
	 */
	public NfaState getZero(int x) {
		return zero.get(x);
	}

	/**
	 * @return adds a state to the alphabet 0
	 */
	public void setZero(NfaState zero) {

		this.zero.add(zero);
	}

	/**
	 * @return index x from one
	 */
	public NfaState getOne(int x) {
		return one.get(x);
	}

	/**
	 * @return adds a state to the alphabet 1
	 */
	public void setOne(NfaState one) {
		this.one.add(one);
	}

	/**
	 * @return index x from ebs
	 */
	public NfaState getEbs(int x) {
		return ebs.get(x);
	}

	/**
	 * @return adds a state to the alphabet ebsilon
	 */
	public void setEbs(NfaState ebs) {
		this.ebs.add(ebs);
	}

	/**
	 * @return size of one
	 */
	public int getOneSize() {
		return one.size();
	}

	/**
	 * @return size of zero
	 */
	public int getZeroSize() {
		return zero.size();
	}

	/**
	 * @return size of ebs
	 */
	public int getEbsSize() {
		return ebs.size();
	}
}

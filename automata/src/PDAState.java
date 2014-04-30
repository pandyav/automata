
public class PDAState extends State{
	
	private PDAState zero;// Transition for 0
	private PDAState one; // Transition for 1
	private PDAState ebs;
	
	private boolean pop=false;
	private boolean push=false;
	

	public PDAState(String name) {
		super(name);
	}


	/**
	 * @return the zero
	 *
	 */
	@Override
	public PDAState getZero() {
		return zero;
	}


	/**
	 * @param zero the zero to set
	 */
	public void setZero(PDAState zero) {
		this.zero = zero;
	}


	/**
	 * @return the one
	 */
	@Override
	public PDAState getOne() {
		return one;
	}


	/**
	 * @param one the one to set
	 */
	public void setOne(PDAState one) {
		this.one = one;
	}


	/**
	 * @return the ebs
	 */
	public PDAState getEbs() {
		return ebs;
	}


	/**
	 * @param ebs the ebs to set
	 */
	public void setEbs(PDAState ebs) {
		this.ebs = ebs;
	}


	/**
	 * @return the pop
	 */
	public boolean isPop() {
		return pop;
	}


	/**
	 * @param pop the pop to set
	 */
	public void setPop(boolean pop) {
		this.pop = pop;
	}


	/**
	 * @return the push
	 */
	public boolean isPush() {
		return push;
	}


	/**
	 * @param push the push to set
	 */
	public void setPush(boolean push) {
		this.push = push;
	}

	
}

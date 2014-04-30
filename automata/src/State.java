/**
 * 
 * 
 */

public class State {

	protected String stateName;// state name
	protected boolean accept = false;// Is this state an accept state?
	protected boolean start = false;// Is this state a start state?
	private State zero;// Transition for 0
	private State one; // Transition for 1

	/**
	 * constructor takes the name parameter
	 */
	public State(String stateName) {
		this.stateName = stateName;

	}

	/**
	 * @return the state name
	 */
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return true if the state is accept
	 */
	public boolean isAccept() {
		return accept;
	}

	/**
	 * set the accept state
	 */
	public void setAccept(boolean accept) {
		this.accept = accept;
	}

	/**
	 * @return true if the state is start state
	 */
	public boolean isStart() {
		return start;
	}

	/**
	 * set the start state
	 */
	public void setStart(boolean start) {
		this.start = start;
	}

	/**
	 * @return state pointed by transition 0
	 */
	public State getZero() {
		return zero;
	}

	/**
	 * make 0 point to this state
	 */
	public void setZero(State zero) {

		this.zero = zero;
	}

	/**
	 * @return state pointed by transition 1
	 */
	public State getOne() {
		return one;
	}

	/**
	 * make 1 point to this state
	 */
	public void setOne(State one) {
		this.one = one;
	}

}

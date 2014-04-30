import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JTextArea;

public class NFA {

	LinkedList<NfaState> stateList;
	ListIterator<NfaState> lst;
	JTextArea ja;

	public NFA(JTextArea sampleTextArea) {
		stateList = new LinkedList<NfaState>();
		ja = sampleTextArea;
	}
	//create all of the state objects from input file
	public void createStates(Scanner sc) {
		String[] s;
		while (sc.nextLine().startsWith("c")) {
		}

		if (sc.next().equalsIgnoreCase("States:")) {

			s = sc.nextLine().trim().split("\\s+");
			if (s[0].equals(""))
				throw new InputFileException("There has to be a set of states");
			for (String c : s) {
				stateList.add(new NfaState(c));

			}
		} else {
			throw new InputFileException(
					"Set of states line has to begin with 'States:'");
		}

	}
	//Initialize the start state
	public void createStartState(Scanner sc) {
		String startState;
		if (sc.next().equalsIgnoreCase("Start:"))
			startState = sc.next();
		else
			throw new InputFileException(
					"Start state line has to begin with 'Start:'");

		lst = stateList.listIterator();
		while (lst.hasNext()) {

			if (lst.next().getStateName().equals(startState)) {
				lst.previous().setStart(true);
				break;
			}
		}

	}
	//mark all of the accept states
	public void createAcceptState(Scanner sc) {
		String[] acceptState;
		if (sc.next().equalsIgnoreCase("Accept:"))
			acceptState = sc.nextLine().trim().split("\\s+");
		else
			throw new InputFileException(
					"Accept state(s) line has to begin with 'Accept:'");
		if (acceptState[0].equals(""))
			throw new InputFileException("There has to be an accept state");

		for (String s : acceptState) {

			lst = stateList.listIterator();

			while (lst.hasNext()) {

				if (lst.next().getStateName().equals(s)) {
					lst.previous().setAccept(true);
					break;
				}
			}

		}

	}
	//create the transition function based on input file
	public void createTransitionFunction(Scanner sc) {

		lst = stateList.listIterator();
		ListIterator<NfaState> ls2 = stateList.listIterator();
		String[] zero = null, one = null, ebs = null;
		sc.nextLine();
		if (!sc.nextLine().trim().startsWith("Transition"))
			throw new InputFileException("Couldn't find the word 'Transition'");
		String pattern = "State:.*";

		sc.nextLine();

		while (lst.hasNext()) {
			zero = null;
			one = null;
			ebs = null;
			NfaState temp = lst.next();

			if (sc.hasNext("State:" + temp.getStateName())) {

				sc.nextLine();
				if (sc.hasNext(pattern)||!sc.hasNext())
					throw new InputFileException("There was an error in the transition function declaration");
					
				String n2 = sc.next();
				if (n2.equalsIgnoreCase("0:")) {
					zero = sc.nextLine().trim().split("\\s+");
					if (!sc.hasNext(pattern) && sc.hasNext())
						n2 = sc.next();
				}

				if (n2.equalsIgnoreCase("1:")) {
					one = sc.nextLine().trim().split("\\s+");
					if (!sc.hasNext(pattern) && sc.hasNext())
						n2 = sc.next();

				}

				if (n2.equalsIgnoreCase("e:")) {
					ebs = sc.nextLine().trim().split("\\s+");

				}

				ls2 = stateList.listIterator();

				while (ls2.hasNext()) {
					NfaState temp2 = ls2.next();
					if (zero != null) {
						for (String s : zero) {
							if (temp2.getStateName().equals(s))
								temp.setZero(temp2);
						}
					}
					if (one != null) {
						for (String s : one) {
							if (temp2.getStateName().equals(s))
								temp.setOne(temp2);

						}
					}
					if (ebs != null) {
						for (String s : ebs) {
							if (temp2.getStateName().equals(s))
								temp.setEbs(temp2);
						}
					}

				}
			}

		}
		ja.append("The NFA has been constructed");
	}
	//read the input string
	public boolean readString(String input) {

		char[] s = input.toCharArray();
		LinkedList<NfaState> st = new LinkedList<NfaState>();
		st.add(getStartState());

		for (char c : s) {

			checkEbs(st);

			if (c == 'e' || c == 'E')
				return checkEmptyString(st);
			else if (c == '1') {
				int m = st.size();
				int q = 0;
				while (q < m) {
					int x = st.getFirst().getOneSize();
					int y = 0;
					NfaState t = st.remove();
					while (y < x) {
						st.add(t.getOne(y));
						y++;
					}
					q++;
				}

				checkEbs(st);

			} else if (c == '0') {
				int m = st.size();
				int q = 0;
				while (q < m) {
					int x = st.getFirst().getZeroSize();
					int y = 0;
					NfaState t = st.remove();
					while (y < x) {
						st.add(t.getZero(y));
						y++;
					}
					q++;
				}

				checkEbs(st);
			} else
				throw new IllegalArgumentException(
						"Invalid character in the string: " + c);
		}
		return checkEmptyString(st);

	}
	//check if there are any ebsilons for current state(s)
	private void checkEbs(LinkedList<NfaState> st) {
		int m = st.size();
		int q = 0;
		while (q < m) {
			int x = st.get(q).getEbsSize();
			int y = 0;
			NfaState t = st.get(q);
			while (y < x) {

				st.add(t.getEbs(y));

				y++;
			}

			q++;
		}
	}
	//get sample strings
	public void getSampleStrings() {
		Random rn = new Random();
		ArrayList<String> acc = new ArrayList<String>();
		ArrayList<String> rej = new ArrayList<String>();

		int num;
		int y = 0;
		while (acc.size() < 3 || rej.size() < 3) {
			num = rn.nextInt(100);
			if (readString(Integer.toBinaryString(num)))
				acc.add(Integer.toBinaryString(num));
			else
				rej.add(Integer.toBinaryString(num));

			y++;
			if (y > 100)
				break;
		}
		ja.append("\n-----------------------------------\nSome of the strings that this NFA accepts/rejects");
		if (acc.size() > 0)
			ja.append("\n------------\nAccepted:");
		for (int x = 0; x < acc.size(); x++) {
			ja.append("\n" + acc.get(x));
			if (x >= 3)
				break;
		}

		if (rej.size() > 0)
			ja.append("\n------------\nRejected:");
		for (int x = 0; x < rej.size(); x++) {
			ja.append("\n" + rej.get(x));
			if (x >= 3)
				break;
		}
		ja.append("\n----------------------------------------\nThis machine only works with binary strings\nenter e to test empty string");

	}
	//check if the current state(s) is accept
	private boolean checkEmptyString(LinkedList<NfaState> st) {
		ListIterator<NfaState> lp;
		lp = st.listIterator();
		while (lp.hasNext()) {
			if (lp.next().isAccept())
				return true;
		}
		return false;
	}

	private NfaState getStartState() {
		lst = stateList.listIterator();

		while (lst.hasNext()) {
			if (lst.next().isStart())
				return lst.previous();
		}

		return null;
	}

}

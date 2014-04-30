import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JTextArea;

public class DFA {

	LinkedList<State> stateList;
	ListIterator<State> lst;

	JTextArea ja;

	public DFA(JTextArea sampleTextArea) {
		stateList = new LinkedList<State>();
		ja = sampleTextArea;

	}
	//creates the states based on inpu file
	public void createStates(Scanner sc) {
		String[] s;
		while (sc.nextLine().startsWith("c")) {
		}

		if (sc.next().equalsIgnoreCase("States:")) {

			s = sc.nextLine().trim().split("\\s+");
			if (s[0].equals(""))
				throw new InputFileException("There has to be a set of states");
			for (String c : s) {
				stateList.add(new State(c));

			}
		} else {
			throw new InputFileException(
					"Set of states line has to begin with 'States:'");
		}
	}
	//declares the starts state as 'start'
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
	//marks all of the accept states
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
	//create the transition function and connect the states
	public void createTransitionFunction(Scanner sc) {

		lst = stateList.listIterator();
		ListIterator<State> ls2 = stateList.listIterator();
		String zero, one;
		sc.nextLine();
		if (!sc.nextLine().trim().startsWith("Transition"))
			throw new InputFileException("Couldn't find the word 'Transition'");

		while (lst.hasNext()) {
			sc.nextLine();
			int x = 0;
			State temp = lst.next();

			if (sc.nextLine().trim()
					.equalsIgnoreCase("State: " + temp.getStateName())) {
				if (sc.next().equalsIgnoreCase("0:"))
					zero = sc.next();
				else
					throw new InputFileException(
							"Could not find the line '0:' for state "
									+ temp.getStateName());

				if (sc.next().equalsIgnoreCase("1:"))
					one = sc.next();
				else
					throw new InputFileException(
							"Could not find the line '1:' for state "
									+ temp.getStateName());
			} else
				throw new InputFileException("Could not find the line 'State: "
						+ temp.getStateName() + "'");

			ls2 = stateList.listIterator();

			while (ls2.hasNext()) {
				State temp2 = ls2.next();

				if (temp2.getStateName().equals(zero)) {
					temp.setZero(temp2);
					x++;

				}
				if (temp2.getStateName().equals(one)) {
					temp.setOne(temp2);
					x++;
				}
				if (x == 2)
					break;

			}

		}
		ja.append("The DFA has been constructed");

	}
	//read the input string and accept or reject it
	public boolean readString(String input) {

		char[] s = input.toCharArray();
		State st = getStartState();
		for (char c : s) {
			if (c == 'e' || c == 'E')
				return getStartState().isAccept();
			else if (c == '1') {
				st = st.getOne();
			} else if (c == '0')
				st = st.getZero();
			else
				throw new IllegalArgumentException(
						"Invalid character in the string: " + c);
		}

		return st.isAccept();
	}
	//gets some sample random strings for this DFA
	public void getSampleStrings() {
		Random rn = new Random();
		ArrayList<String> acc = new ArrayList<String>();
		ArrayList<String> rej = new ArrayList<String>();

		int num;
		int y = 0;
		while (acc.size() < 2 || rej.size() < 2) {
			num = rn.nextInt(100);
			if (readString(Integer.toBinaryString(num)))
				acc.add(Integer.toBinaryString(num));
			else
				rej.add(Integer.toBinaryString(num));
			y++;
			if (y > 100)
				break;
		}
		ja.append("\n-----------------------------------\nSome of the strings that this DFA accepts/rejects");
		if (acc.size() > 0)
			ja.append("\n------------\nAccepted:");
		for (int x = 0; x < acc.size(); x++) {
			ja.append("\n" + acc.get(x));
			if (x >= 2)
				break;
		}

		if (rej.size() > 0)
			ja.append("\n------------\nRejected:");
		for (int x = 0; x < rej.size(); x++) {
			ja.append("\n" + rej.get(x));
			if (x >= 2)
				break;
		}

		ja.append("\n----------------------------------------\nThis machine only works with binary strings\nenter e to test empty string");

	}
	//helper method to fetch the start state
	private State getStartState() {
		lst = stateList.listIterator();

		while (lst.hasNext()) {
			if (lst.next().isStart())
				return lst.previous();
		}

		return null;
	}

}

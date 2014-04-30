import java.awt.Color;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class PDA {

	LinkedList<PDAState> stateList;
	ListIterator<PDAState> lst;
	JTextArea j;

	public PDA(JTextArea sampleTextArea) {
		stateList = new LinkedList<PDAState>();
		j = sampleTextArea;

	}

	public void createStates(Scanner sc) {
		String[] s;
		while (sc.nextLine().startsWith("c")) {
		}

		if (sc.next().equalsIgnoreCase("States:")) {

			s = sc.nextLine().trim().split("\\s+");
			if (s[0].equals(""))
				throw new InputFileException("There has to be a set of states");
			for (String c : s) {
				stateList.add(new PDAState(c));

			}
		} else {
			throw new InputFileException(
					"Set of states line has to begin with 'States:'");
		}

	}

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

	public void createAcceptState(Scanner sc) {
		String acceptState;
		if (sc.next().equalsIgnoreCase("Accept:"))
			acceptState = sc.nextLine().trim();
		else
			throw new InputFileException(
					"Accept state(s) line has to begin with 'Accept:'");
		if (acceptState.equals(""))
			throw new InputFileException("There has to be an accept state");

		lst = stateList.listIterator();

		while (lst.hasNext()) {

			if (lst.next().getStateName().equals(acceptState)) {
				lst.previous().setAccept(true);
				break;
			}
		}

	}

	public void createTransitionFunction(Scanner sc) {
		lst = stateList.listIterator();
		ListIterator<PDAState> ls2 = stateList.listIterator();
		String zero = null, one = null, ebs = null;
		sc.nextLine();
		if (!sc.nextLine().trim().startsWith("Transition"))
			throw new InputFileException("Couldn't find the word 'Transition'");
		String pattern = "State:.*";

		sc.nextLine();

		while (lst.hasNext()) {
			zero = null;
			one = null;
			ebs = null;

			PDAState temp = lst.next();

			if (sc.hasNext("State:" + temp.getStateName())) {

				sc.nextLine();
				String n2 = sc.next();
				if (n2.equalsIgnoreCase("0:") || n2.equalsIgnoreCase("(:")) {
					zero = sc.next().trim();
					String t1 = sc.next();
					if (t1.equalsIgnoreCase("push"))
						temp.setPush(true);
					else if (t1.equalsIgnoreCase("pop"))
						temp.setPop(true);
					else
						throw new InputFileException(
								"Couldnt find 'push' or 'pop'");

					sc.nextLine();

					if (!sc.hasNext(pattern) && sc.hasNext())
						n2 = sc.next();
				}

				if (n2.equalsIgnoreCase("1:") || n2.equalsIgnoreCase("):")) {
					one = sc.next().trim();
					String t1 = sc.next();
					if (t1.equalsIgnoreCase("push"))
						temp.setPush(true);
					else if (t1.equalsIgnoreCase("pop"))
						temp.setPop(true);
					else
						throw new InputFileException(
								"Couldnt find 'push' or 'pop'");
					sc.nextLine();
					if (!sc.hasNext(pattern) && sc.hasNext())
						n2 = sc.next();

				}

				if (n2.equalsIgnoreCase("e:")) {

					ebs = sc.next().trim();
					sc.next();

					if (sc.hasNext())
						sc.nextLine();
				}

				ls2 = stateList.listIterator();

				while (ls2.hasNext()) {
					PDAState temp2 = ls2.next();
					if (zero != null) {

						if (temp2.getStateName().equals(zero))
							temp.setZero(temp2);

					}
					if (one != null) {

						if (temp2.getStateName().equals(one))
							temp.setOne(temp2);

					}
					if (ebs != null) {

						if (temp2.getStateName().equals(ebs))
							temp.setEbs(temp2);

					}

				}
			}

		}

		j.append("The PDA has "
				+ "been constructed\n--------------------------------------\nInput "
				+ "can be in the form of {0,1,(,)} for testing\n&=delta\nFinal stack will "
				+ "be shown\nexample stack: [&,x,x,x]<--top of stack");

	}

	public boolean readString(String ex, JLabel t) {
		Stack<Character> stack = new Stack<Character>();
		stack.push('&');

		lst = stateList.listIterator();

		char[] s = ex.toCharArray();
		PDAState currentState = getStartState().getEbs();
		try {
			for (char c : s) {
				if (c == '0' || c == '(') {
					if (currentState == null) {
						t.setText("Final Stack: " + stack.toString());
						t.setForeground(Color.cyan);
						return false;
					}
					if (currentState.isPush())
						stack.push('x');

					currentState = currentState.getZero();

				} else if (c == '1' || c == ')') {
					if (currentState == null) {
						t.setText("Final Stack: " + stack.toString());
						t.setForeground(Color.cyan);
						return false;
					}
					if (currentState.isPop())
						stack.pop();

					currentState = currentState.getOne();
				} else
					throw new IllegalArgumentException("Invalid Symbol: " + c);
			}
			if (stack.peek().equals('&')) {
				stack.pop();
				t.setText("Final Stack: " + stack.toString());
				t.setForeground(Color.cyan);
				return true;
			} else {
				t.setText("Final Stack: " + stack.toString());
				t.setForeground(Color.cyan);
				return false;
			}
		} catch (EmptyStackException e) {
			t.setText("Stack is already empty!Final Stack: " + stack.toString());
			t.setForeground(Color.cyan);
			return false;
		}

	}

	private PDAState getStartState() {
		lst = stateList.listIterator();

		while (lst.hasNext()) {
			if (lst.next().isStart())
				return lst.previous();
		}

		return null;
	}

}

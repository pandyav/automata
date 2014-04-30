import java.awt.Color;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class TuringMachine {

	LinkedList<TMState> stateList;
	ListIterator<TMState> lst;
	JTextArea j;

	public TuringMachine(JTextArea sampleTextArea) {
		stateList = new LinkedList<TMState>();
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
				stateList.add(new TMState(c));

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

	public void createTransitionFunction(Scanner sc) {
		lst = stateList.listIterator();
		ListIterator<TMState> ls2 = stateList.listIterator();
		String zero = null, one = null, blank = null, two = null, extra = null;
		sc.nextLine();
		if (!sc.nextLine().trim().startsWith("Transition"))
			throw new InputFileException("Couldn't find the word 'Transition'");
		String pattern = "State:.*";

		sc.nextLine();

		while (lst.hasNext()) {
			zero = null;
			one = null;
			blank = null;
			two = null;
			extra = null;
			TMState temp = lst.next();

			if (sc.hasNext("State:" + temp.getStateName())) {

				sc.nextLine();
				String n2 = sc.next();
				if (n2.equalsIgnoreCase("0:") || n2.equalsIgnoreCase("(:")) {
					zero = sc.next().trim();
					temp.setWriteZero(sc.next().charAt(0));
					temp.setMoveZero(sc.next().charAt(0));
					sc.nextLine();

					if (!sc.hasNext(pattern) && sc.hasNext())
						n2 = sc.next();
				}

				if (n2.equalsIgnoreCase("1:") || n2.equalsIgnoreCase("):")) {
					one = sc.next().trim();
					temp.setWriteOne(sc.next().charAt(0));
					temp.setMoveOne(sc.next().charAt(0));
					sc.nextLine();
					if (!sc.hasNext(pattern) && sc.hasNext())
						n2 = sc.next();

				}

				if (n2.equalsIgnoreCase("2:")) {
					two = sc.next().trim();
					temp.setWriteTwo(sc.next().charAt(0));
					temp.setMoveTwo(sc.next().charAt(0));
					sc.nextLine();
					if (!sc.hasNext(pattern) && sc.hasNext())
						n2 = sc.next();

				}
				if (n2.equalsIgnoreCase("x:")) {
					extra = sc.next().trim();
					temp.setWriteExtra(sc.next().charAt(0));
					temp.setMoveExtra(sc.next().charAt(0));
					sc.nextLine();
					if (!sc.hasNext(pattern) && sc.hasNext())
						n2 = sc.next();

				}

				if (n2.equalsIgnoreCase("&:")) {

					blank = sc.next().trim();
					temp.setWriteBlank(sc.next().charAt(0));
					temp.setMoveBlank(sc.next().charAt(0));
					if (sc.hasNext())
						sc.nextLine();
				}

				ls2 = stateList.listIterator();

				while (ls2.hasNext()) {
					TMState temp2 = ls2.next();
					if (zero != null) {

						if (temp2.getStateName().equals(zero))
							temp.setZero(temp2);

					}
					if (one != null) {

						if (temp2.getStateName().equals(one))
							temp.setOne(temp2);

					}
					if (blank != null) {

						if (temp2.getStateName().equals(blank))
							temp.setBlank(temp2);

					}
					if (two != null) {

						if (temp2.getStateName().equals(two))
							temp.setTwo(temp2);

					}
					if (extra != null) {

						if (temp2.getStateName().equals(extra))
							temp.setExtra(temp2);

					}

				}
			}

		}

		j.append("The TM has "
				+ "been constructed\n--------------------------------------\nInput "
				+ "can be in the form of {0,1,2,(,),&(empty string)} for testing\n&=blank symbol\nIinitial and Final tape will "
				+ "be shown\nexample tape: [&,&,0,0,1,&,&]");
	}

	public boolean readString(String ex, JLabel t1, JLabel t2) {
		LinkedList<Character> tape = new LinkedList<Character>();

		lst = stateList.listIterator();
		TMState currentState = getStartState();
		char[] s = ex.toCharArray();
		for (char c : s)
			tape.add(c);

		tape.addFirst('&');
		tape.addFirst('&');
		tape.addLast('&');
		tape.addLast('&');

		t1.setText("Initial Tape: " + tape.toString());
		t1.setForeground(Color.cyan);

		char temp;
		int x = 2;
		while (true) {
			temp = tape.get(x);

			if (temp == '0' || temp == '(') {
				if (currentState.getZero() == null) {
					t2.setText("Final Tape: " + tape.toString());
					t2.setForeground(Color.cyan);
					return false;
				}
				tape.set(x, currentState.getWriteZero());
				checkEnoughBlank(tape);

				if (Character.toUpperCase(currentState.getMoveZero()) == Character
						.toUpperCase('R'))
					x++;
				else if (Character.toUpperCase(currentState.getMoveZero()) == Character
						.toUpperCase('L'))
					x--;

				currentState = currentState.getZero();
				if (isAccept(currentState, tape, t2))
					return true;
			} else if (temp == '1' || temp == ')') {
				if (currentState.getOne() == null) {
					t2.setText("Final Tape: " + tape.toString());
					t2.setForeground(Color.cyan);
					return false;
				}
				tape.set(x, currentState.getWriteOne());
				checkEnoughBlank(tape);

				if (Character.toUpperCase(currentState.getMoveOne()) == Character
						.toUpperCase('R'))
					x++;
				else if (Character.toUpperCase(currentState.getMoveOne()) == Character
						.toUpperCase('L'))
					x--;

				currentState = currentState.getOne();
				if (isAccept(currentState, tape, t2))
					return true;
			} else if (temp == '2') {
				if (currentState.getTwo() == null) {
					t2.setText("Final Tape: " + tape.toString());
					t2.setForeground(Color.cyan);
					return false;
				}
				tape.set(x, currentState.getWriteTwo());
				checkEnoughBlank(tape);

				if (Character.toUpperCase(currentState.getMoveTwo()) == Character
						.toUpperCase('R'))
					x++;
				else if (Character.toUpperCase(currentState.getMoveTwo()) == Character
						.toUpperCase('L'))
					x--;

				currentState = currentState.getTwo();
				if (isAccept(currentState, tape, t2))
					return true;
			} else if (temp == 'x') {
				if (currentState.getExtra() == null) {
					t2.setText("Final Tape: " + tape.toString());
					t2.setForeground(Color.cyan);
					return false;
				}
				tape.set(x, currentState.getWriteExtra());
				checkEnoughBlank(tape);

				if (Character.toUpperCase(currentState.getMoveExtra()) == Character
						.toUpperCase('R'))
					x++;
				else if (Character.toUpperCase(currentState.getMoveExtra()) == Character
						.toUpperCase('L'))
					x--;

				currentState = currentState.getExtra();
				if (isAccept(currentState, tape, t2))
					return true;
			} else if (temp == '&') {
				if (currentState.getBlank() == null) {
					t2.setText("Final Tape: " + tape.toString());
					t2.setForeground(Color.cyan);
					return false;
				}
				tape.set(x, currentState.getWriteBlank());
				checkEnoughBlank(tape);

				if (Character.toUpperCase(currentState.getMoveBlank()) == Character
						.toUpperCase('R'))
					x++;
				else if (Character.toUpperCase(currentState.getMoveBlank()) == Character
						.toUpperCase('L'))
					x--;

				currentState = currentState.getBlank();
				if (isAccept(currentState, tape, t2))
					return true;
			} else
				throw new IllegalArgumentException("Invalid Symbol: " + temp);
		}

	}

	private boolean isAccept(TMState currentState, LinkedList<Character> tape,
			JLabel t2) {
		if (currentState.isAccept()) {
			t2.setText("Final Tape: " + tape.toString());
			t2.setForeground(Color.cyan);
			return true;
		}

		return false;
	}

	private TMState getStartState() {
		lst = stateList.listIterator();

		while (lst.hasNext()) {
			if (lst.next().isStart())
				return lst.previous();
		}

		return null;
	}

	private void checkEnoughBlank(LinkedList<Character> tape) {
		if (tape.getFirst() != '&' || tape.getLast() != '&') {
			tape.addFirst('&');
			tape.addLast('&');
		}
	}

}

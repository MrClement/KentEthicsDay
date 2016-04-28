package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class SeminarList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, Seminar> seminars;
	private int numSeminars;

	public SeminarList() {
		setSeminars(new HashMap<Integer, Seminar>());
		numSeminars = 0;
	}

	public SeminarList(SeminarList other) {
		numSeminars = other.numSeminars();
		seminars = new HashMap<Integer, Seminar>();
		for (Entry<Integer, Seminar> e : other.getSeminars().entrySet()) {
			seminars.put(new Integer(e.getKey()), new Seminar(e.getValue()));
		}
	}

	public void add(Seminar s) {
		getSeminars().put(s.getId(), s);
		numSeminars++;
	}

	public HashMap<Integer, Seminar> getSeminars() {
		return seminars;
	}

	public void setSeminars(HashMap<Integer, Seminar> seminars) {
		this.seminars = seminars;
	}

	public int numSeminars() {
		return numSeminars;
	}

	public ArrayList<Integer> getMSOnlySeminars() {
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		for (Entry<Integer, Seminar> e : seminars.entrySet()) {
			if (e.getValue().isAgeLimited().equals("MS")) {
				toReturn.add(e.getKey());
			}
		}
		return toReturn;
	}

	public ArrayList<Integer> getUSOnlySeminars() {
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		for (Entry<Integer, Seminar> e : seminars.entrySet()) {
			if (e.getValue().isAgeLimited().equals("US")) {
				toReturn.add(e.getKey());
			}
		}
		return toReturn;

	}

}

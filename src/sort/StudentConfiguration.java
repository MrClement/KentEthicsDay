package sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import data.Student;

public class StudentConfiguration implements Comparable<StudentConfiguration>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8798229221676840461L;
	private int value;
	private HashMap<Integer, ArrayList<Student>> config;
	private int[] capacities;
	private SortWeighting weighting;
	private int[] maxSeminarSize;
	private ArrayList<Integer> allowedDupilcates;
	private ArrayList<Integer> prevent;

	public StudentConfiguration(SortWeighting weighting, int[] maxSeminarSize, ArrayList<Integer> prevent,
			ArrayList<Integer> doubleSeminars) {
		this.maxSeminarSize = maxSeminarSize;
		this.prevent = prevent;
		value = 0;
		config = new HashMap<Integer, ArrayList<Student>>();
		capacities = new int[100];
		this.weighting = weighting;
		allowedDupilcates = new ArrayList<Integer>();
		for (int i = 0; i < doubleSeminars.size(); i++) {
			allowedDupilcates.add(doubleSeminars.get(i));
		}
	}

	public boolean addStudent(int seminarID, Student s) {
		if (!config.containsKey(seminarID)) {
			config.put(seminarID, new ArrayList<Student>());
		}
		if (capacities[seminarID] >= maxSeminarSize[seminarID]) {
			return false;
		} else if (studentCanBeAssigned(seminarID, s)) {

			config.get(seminarID).add(s);
			capacities[seminarID]++;
			updateValue(seminarID, s);

			s.getAssignedSeminars().add(seminarID);

			return true;
		} else {
			return false;
		}
	}

	private boolean studentCanBeAssigned(int seminarID, Student s) {
		ArrayList<Integer> assignedSeminars = s.getAssignedSeminars();
		if (assignedSeminars.size() >= 2 || prevent.contains(seminarID)) {
			return false;
		} else {
			for (int i = 0; i < assignedSeminars.size(); i++) {
				if (assignedSeminars.get(i) == seminarID && !allowedDupilcates.contains(seminarID))
					return false;
			}
			return true;
		}

	}

	private void updateValue(int seminarID, Student s) {
		ArrayList<Integer> seminarPreferences = s.getSeminarPreferences();
		boolean found = false;
		for (int i = 0; i < seminarPreferences.size(); i++) {
			if (seminarID == seminarPreferences.get(i)) {
				value += weighting.getPrefWeighting()[i];
				found = true;
			}
		}
		if (!found && s.didRegister()) {
			value += weighting.getPrefWeighting()[5];
		}

	}

	public void adjustValue() {
		if (weighting.getSixthWeighting() != 0) {
			for (Entry<Integer, ArrayList<Student>> e : config.entrySet()) {
				int numSixth = 0;
				ArrayList<Student> list = e.getValue();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getGrade() == 6) {
						numSixth++;
					}
				}
				if (numSixth == 1) {
					value += weighting.getSixthWeighting();
					break;
				}
			}

		}
		if (weighting.getGenderWeighting() != 0) {
			for (Entry<Integer, ArrayList<Student>> e : config.entrySet()) {
				double male = 0;
				double female = 0;
				ArrayList<Student> list = e.getValue();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).isGender() == Student.MALE) {
						male++;
					} else {
						female++;
					}
				}
				double ratio = Math.abs(1 - (male / female));
				value -= (int) (ratio * weighting.getGenderWeighting());
			}
		}
	}

	@Override
	public int compareTo(StudentConfiguration o) {
		if (getValue() > o.getValue()) {
			return -1;
		} else if (getValue() < o.getValue()) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "StudentConfiguration [value=" + value // +", config=" + config
				+ ", capacities=" + Arrays.toString(capacities) + ", weighting=" + weighting + "]";
	}

	public int getValue() {
		return value;
	}

	public HashMap<Integer, ArrayList<Student>> getConfig() {
		return config;
	}

}

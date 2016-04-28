package sort;

import java.io.Serializable;
import java.util.ArrayList;

import data.SeminarList;
import data.StudentList;

public class FullConfiguration implements Comparable<FullConfiguration>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -300012580663480825L;
	private StudentConfiguration first;
	private StudentConfiguration second;

	private StudentList unregistedStudents;
	private StudentList students;
	private SeminarList seminars;

	public FullConfiguration(SortWeighting weighting, int[] maxSeminarSize, ArrayList<Integer> firstPrevent,
			ArrayList<Integer> secondPrevent, ArrayList<Integer> doubleSeminars) {
		setFirst(new StudentConfiguration(weighting, maxSeminarSize, firstPrevent, doubleSeminars));
		setSecond(new StudentConfiguration(weighting, maxSeminarSize, secondPrevent, doubleSeminars));
	}

	public StudentConfiguration getFirst() {
		return first;
	}

	public void setFirst(StudentConfiguration first) {
		this.first = first;
	}

	public StudentConfiguration getSecond() {
		return second;
	}

	public void setSecond(StudentConfiguration second) {
		this.second = second;
	}

	public StudentList getUnregistedStudents() {
		return unregistedStudents;
	}

	public StudentList getStudents() {
		return students;
	}

	public SeminarList getSeminars() {
		return seminars;
	}

	public void setUnregistedStudents(StudentList unregistedStudents) {
		this.unregistedStudents = unregistedStudents;
	}

	public void setStudents(StudentList students) {
		this.students = students;
	}

	public void setSeminars(SeminarList seminars) {
		this.seminars = seminars;
	}

	public int getValue() {
		return first.getValue() + second.getValue();
	}

	public void adjustValues() {
		first.adjustValue();
		second.adjustValue();
	}

	@Override
	public int compareTo(FullConfiguration o) {
		if (getValue() > o.getValue()) {
			return 1;
		} else if (getValue() < o.getValue()) {
			return -1;
		} else {
			return 0;
		}
	}

	public String toString() {
		return "Value: " + getValue();
	}

}

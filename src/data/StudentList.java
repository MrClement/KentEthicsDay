package data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

public class StudentList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6834271998881192578L;
	private HashMap<Integer, Student> students;
	private int i;
	private int numStudents;

	public StudentList() {
		students = new HashMap<Integer, Student>();
		i = 1;
		numStudents = 0;
	}

	public StudentList(StudentList other) {
		i = other.numStudents() + 1;
		numStudents = other.numStudents();
		students = new HashMap<Integer, Student>();
		for (Entry<Integer, Student> e : other.getStudents().entrySet()) {
			students.put(new Integer(e.getKey()), new Student(e.getValue()));
		}
	}

	public void add(Student s) {
		students.put(i++, s);
		numStudents++;
	}

	public HashMap<Integer, Student> getStudents() {
		return students;
	}

	public void setStudents(HashMap<Integer, Student> students) {
		this.students = students;
	}

	public int numStudents() {
		return numStudents;
	}

	public void printList() {
		for (Entry<Integer, Student> e : students.entrySet()) {
			System.out.println(e.getValue());
		}
	}

}

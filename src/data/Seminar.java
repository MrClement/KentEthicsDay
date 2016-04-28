package data;

import java.io.Serializable;
import java.util.ArrayList;

public class Seminar implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int capacity;
	private double genderRatio;
	private int id;
	private String ageLimited;
	private String roomAssignment;
	private String title;
	private ArrayList<Student> studentRoster;
	private String instructorName;

	public Seminar(int id) {
		this.id = id;
		studentRoster = new ArrayList<Student>();
	}

	public Seminar(Seminar other) {
		capacity = other.getCapacity();
		id = other.getId();
		genderRatio = other.getGenderRatio();
		ageLimited = other.isAgeLimited();
		roomAssignment = other.getRoomAssignment();
		title = other.getTitle();
		instructorName = other.getInstructorName();
		studentRoster = new ArrayList<Student>();
		ArrayList<Student> studentRoster2 = other.getStudentRoster();
		for (int i = 0; i < studentRoster2.size(); i++) {
			studentRoster.add(new Student(studentRoster2.get(i)));
		}
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @return the genderRatio
	 * 
	 */
	public double getGenderRatio() {
		return genderRatio;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the roomAssignment
	 */
	public String getRoomAssignment() {
		return roomAssignment;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the studentRoster
	 */
	public ArrayList<Student> getStudentRoster() {
		return studentRoster;
	}

	/**
	 * @param capacity
	 *            the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * @param genderRatio
	 *            the genderRatio to set
	 */
	public void setGenderRatio(double genderRatio) {
		this.genderRatio = genderRatio;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param roomAssignment
	 *            the roomAssignment to set
	 */
	public void setRoomAssignment(String roomAssignment) {
		this.roomAssignment = roomAssignment;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param studentRoster
	 *            the studentRoster to set
	 */
	public void setStudentRoster(ArrayList<Student> studentRoster) {
		this.studentRoster = studentRoster;
	}

	/**
	 * @return the name of the seminar instructor
	 */
	public String getInstructorName() {
		return instructorName;
	}

	/**
	 * @param instructorName
	 */
	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	/**
	 * @return ageLimited (false = no)
	 */
	public String isAgeLimited() {
		return ageLimited;
	}

	/**
	 * @param sets
	 *            whether all grades can take part in the seminar true = no,
	 *            false = yes
	 * 
	 */
	public void setAgeLimited(String ageLimited) {
		this.ageLimited = ageLimited;
	}

	@Override
	public String toString() {
		return "Seminar [capacity=" + capacity + ", genderRatio=" + genderRatio + ", id=" + id + ", ageLimited="
				+ ageLimited + ", roomAssignment=" + roomAssignment + ", title=" + title + ", studentRoster="
				+ studentRoster + ", instructorName=" + instructorName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seminar other = (Seminar) obj;
		if (id != other.id)
			return false;
		return true;
	}

}

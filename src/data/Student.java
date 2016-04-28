package data;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final boolean MALE = false;
	public static final boolean FEMALE = true;

	private int grade;
	private boolean gender;
	private String firstName;
	private String lastName;
	private String fullName;
	private String email;
	private boolean registered;

	private ArrayList<Integer> assignedSeminars;
	private ArrayList<Integer> seminarPreferences;

	/**
	 * Constructor for a student object.
	 * 
	 * @param grade
	 *            The grade level of the student
	 * @param gender
	 *            The gender of the student
	 */
	public Student(int grade, boolean gender, String firstName, String lastName, String email) {

		this.grade = grade;
		this.gender = gender;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.fullName = firstName + " " + lastName;
		assignedSeminars = new ArrayList<Integer>();
		seminarPreferences = new ArrayList<Integer>();
		setDidRegister(true);
	}

	public Student(Student other) {
		this.grade = other.getGrade();
		this.gender = other.isGender();
		this.firstName = other.getFirstName();
		this.lastName = other.getLastName();
		this.email = other.getEmail();
		this.fullName = firstName + " " + lastName;
		this.setDidRegister(other.didRegister());
		assignedSeminars = new ArrayList<Integer>(other.getAssignedSeminars());
		seminarPreferences = new ArrayList<Integer>(other.getSeminarPreferences());
	}

	/**
	 * @return the student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the student's full name
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @return the student's email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the grade level of the student
	 * 
	 * @return the grade level of the student
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * @return the gender of the student
	 */
	public boolean isGender() {
		return gender;
	}

	/**
	 * Gets the ArrayList of seminars id numbers that the student has been
	 * assigned to. Index 0 is the first seminar while index 1 is the second
	 * 
	 * @return the assignedSeminars
	 */
	public ArrayList<Integer> getAssignedSeminars() {
		return assignedSeminars;
	}

	/**
	 * Gets The ArrayList of seminar id numbers that the student has indicated
	 * they wish to take. Lowest index is highest preference
	 * 
	 * @return the seminarPreferences
	 */
	public ArrayList<Integer> getSeminarPreferences() {
		return seminarPreferences;
	}

	/**
	 * 
	 * 
	 * @param grade
	 *            the grade level to set
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(boolean gender) {
		this.gender = gender;
	}

	/**
	 * @param assignedSeminars
	 *            the assignedSeminars ArrayList to set
	 */
	public void setAssignedSeminars(ArrayList<Integer> assignedSeminars) {
		this.assignedSeminars = assignedSeminars;
	}

	/**
	 * @param seminarPreferences
	 *            the seminarPreferences ArrayList to set
	 */
	public void setSeminarPreferences(ArrayList<Integer> seminarPreferences) {
		this.seminarPreferences = seminarPreferences;
	}

	@Override
	public String toString() {
		return "Student [grade=" + grade + ", gender=" + gender + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", fullName=" + fullName + ", email=" + email + ", assignedSeminars=" + assignedSeminars
				+ ", seminarPreferences=" + seminarPreferences + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
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
		Student other = (Student) obj;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		return true;
	}

	public boolean didRegister() {
		return registered;
	}

	public void setDidRegister(boolean registered) {
		this.registered = registered;
	}

}

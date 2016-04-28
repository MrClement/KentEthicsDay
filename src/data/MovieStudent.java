package data;

import java.io.Serializable;

public class MovieStudent implements Serializable {

	public static final boolean MALE = false;
	public static final boolean FEMALE = true;

	private int grade;
	private boolean gender;
	private String firstName;
	private String lastName;
	private String fullName;
	private String email;

	private int moviePreference;
	private String roomAssignment;

	@Override
	public String toString() {
		return "MovieStudent [grade=" + grade + ", gender=" + gender + ", fullName=" + fullName + ", email=" + email
				+ ", moviePreference=" + moviePreference + ", roomAssignment=" + roomAssignment + "]";
	}

	public MovieStudent(boolean gender, String firstName, String lastName, String email, int moviePref) {
		this.gender = gender;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.grade = calcGrade(email);
		this.fullName = firstName + " " + lastName;
		this.moviePreference = moviePref;
	}

	public MovieStudent(MovieStudent other) {
		this.gender = other.isGender();
		this.firstName = other.getFirstName();
		this.lastName = other.getLastName();
		this.email = other.getEmail();
		this.grade = calcGrade(email);
		this.fullName = firstName + " " + lastName;
		this.moviePreference = other.getMoviePreference();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + (gender ? 1231 : 1237);
		result = prime * result + grade;
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
		MovieStudent other = (MovieStudent) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (gender != other.gender)
			return false;
		if (grade != other.grade)
			return false;
		return true;
	}

	private int calcGrade(String email) {

		String[] split = email.split("@");
		String year = split[0].substring(split[0].length() - 2);
		switch (year) {
			case "16":
				return 12;
			case "17":
				return 11;
			case "18":
				return 10;
			case "19":
				return 9;
			case "20":
				return 8;
			case "21":
				return 7;
			case "22":
				return 6;
			default:
				return -1;
		}

	}

	public int getGrade() {
		return grade;
	}

	public boolean isGender() {
		return gender;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public int getMoviePreference() {
		return moviePreference;
	}

	public String getRoomAssignment() {
		return roomAssignment;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMoviePreference(int moviePreference) {
		this.moviePreference = moviePreference;
	}

	public void setRoomAssignment(String roomAssignment) {
		this.roomAssignment = roomAssignment;
	}

}

package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

public class CSVParser {

	private String studentInFile;
	private String seminarInFile;
	private String unregisteredStuFile;
	private StudentList students;
	private SeminarList seminars;
	private StudentList unregisteredStudents;

	private HashMap<String, Integer> seminarInfoLocations;
	private HashMap<String, Integer> studentInfoLocations;

	@SuppressWarnings("resource")
	public CSVParser(String studentInFile, String seminarInFile, String unregisteredStuFile) {

		seminarInfoLocations = new HashMap<String, Integer>();
		studentInfoLocations = new HashMap<String, Integer>();

		this.studentInFile = studentInFile;
		this.seminarInFile = seminarInFile;
		this.unregisteredStuFile = unregisteredStuFile;
		students = new StudentList();
		seminars = new SeminarList();
		unregisteredStudents = new StudentList();
		Scanner s;
		s = new Scanner(CSVParser.class.getClassLoader().getResourceAsStream(this.seminarInFile));
		findSeminarInfo(s.nextLine());
		while (s.hasNextLine()) {
			parseSeminarData(s.nextLine());

		}
		s = new Scanner(CSVParser.class.getClassLoader().getResourceAsStream(this.studentInFile));
		findStudentInfo(s.nextLine());
		while (s.hasNextLine()) {
			parseStudentData(s.nextLine());

		}

		s = new Scanner(CSVParser.class.getClassLoader().getResourceAsStream(this.unregisteredStuFile));
		s.nextLine();
		while (s.hasNextLine()) {
			parseUnregisteredStudentData(s.nextLine());

		}

		removeDuplicateSeminarPreferences(seminars);

	}

	private void findSeminarInfo(String firstLine) {
		String[] split = firstLine.split("\t");
		for (int i = 0; i < split.length; i++) {
			seminarInfoLocations.put(split[i], i);
		}
	}

	private void findStudentInfo(String firstLine) {
		String[] split = firstLine.split(",");
		for (int i = 0; i < split.length; i++) {
			studentInfoLocations.put(split[i], i);
		}

	}

	private void parseSeminarData(String nextLine) {

		String title;
		String roomAssignment;
		int id;
		String instructorName;
		String ageLimited;

		String[] strings = nextLine.split("\t");

		title = strings[seminarInfoLocations.get("Title")];
		instructorName = "test";// strings[seminarInfoLocations.get("Faculty Sponsor")];
		ageLimited = strings[seminarInfoLocations.get("Limited to")];

		id = Integer.parseInt(strings[seminarInfoLocations.get("ID")]);
		int capacity = 15;
		roomAssignment = "test";// strings[seminarInfoLocations.get("Room")];

		Seminar s = new Seminar(id);
		s.setAgeLimited(ageLimited);
		s.setCapacity(capacity);
		s.setInstructorName(instructorName);
		s.setTitle(title);
		s.setRoomAssignment(roomAssignment);

		seminars.add(s);

	}

	/**
	 * parseStudentData takes a line from a csv file and interprets it as a
	 * student
	 * 
	 * @param nextLine
	 *            this is the next line from the .csv file
	 */
	private void parseStudentData(String nextLine) {
		int grade;
		boolean gender;
		String firstName;
		String lastName;
		String email;

		String[] strings = nextLine.split(",");

		email = strings[studentInfoLocations.get("Email")];
		firstName = strings[studentInfoLocations.get("First Name")];
		lastName = strings[studentInfoLocations.get("Last Name")];
		gender = strings[studentInfoLocations.get("Gender")].equals("F");
		grade = getGrade(email);
		ArrayList<Integer> semPrefs = new ArrayList<Integer>();
		int startIndex = studentInfoLocations.get("First Choice");
		for (int i = 0; i < 5; i++) {
			semPrefs.add(Integer.parseInt(strings[startIndex + i]));
		}

		Student s = new Student(grade, gender, firstName, lastName, email);
		s.setSeminarPreferences(semPrefs);
		students.add(s);

	}

	/**
	 * parseUnregisteredStudentData takes a line from a csv file and interprets
	 * it as a student
	 * 
	 * @param nextLine
	 *            this is the next line from the .csv file
	 */

	private void parseUnregisteredStudentData(String nextLine) {
		int grade;
		boolean gender;
		String firstName;
		String lastName;
		String email;

		String[] strings = nextLine.split(",");

		email = strings[studentInfoLocations.get("Email")];
		firstName = strings[studentInfoLocations.get("First Name")];
		lastName = strings[studentInfoLocations.get("Last Name")];
		gender = strings[studentInfoLocations.get("Gender")].equals("F");
		grade = getGrade(email);
		ArrayList<Integer> semPrefs = new ArrayList<Integer>();
		int startIndex = studentInfoLocations.get("First Choice");
		for (int i = 0; i < 5; i++) {
			semPrefs.add(Integer.parseInt(strings[startIndex + i]));
		}

		Student s = new Student(grade, gender, firstName, lastName, email);
		s.setSeminarPreferences(semPrefs);
		s.setDidRegister(false);
		unregisteredStudents.add(s);
	}

	private void removeDuplicateSeminarPreferences(SeminarList sems) {

		for (Entry<Integer, Student> e : students.getStudents().entrySet()) {
			Student s = e.getValue();
			ArrayList<Integer> seminarPreferences = s.getSeminarPreferences();
			boolean[] storage = new boolean[100];
			ArrayList<Integer> duplicateIndex = new ArrayList<Integer>();
			for (int i = 0; i < seminarPreferences.size(); i++) {
				Integer semID = seminarPreferences.get(i);
				if (!storage[semID]) {
					storage[semID] = true;
				} else {
					duplicateIndex.add(i);
				}
			}
			for (int i = 0; i < duplicateIndex.size(); i++) {
				seminarPreferences.set(duplicateIndex.get(i), getValidSeminar(s, sems));
			}
		}
	}

	public Integer getValidSeminar(Student s, SeminarList sems) {
		Random r = new Random();
		ArrayList<Integer> invalidSems = new ArrayList<Integer>();
		invalidSems.addAll(s.getSeminarPreferences());
		if (s.getGrade() > 8) {
			invalidSems.addAll(sems.getMSOnlySeminars());
		} else {
			invalidSems.addAll(sems.getUSOnlySeminars());
		}
		Integer newID = r.nextInt(sems.numSeminars()) + 1;
		while (invalidSems.contains(newID)) {
			newID = r.nextInt(sems.numSeminars()) + 1;
		}
		return newID;
	}

	private int getGrade(String email) {

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

	public StudentList getStudents() {
		return students;
	}

	public SeminarList getSeminars() {
		return seminars;
	}

	public StudentList getUnregistered() {
		return unregisteredStudents;
	}

	public void listStudents() {
		for (Entry<Integer, Student> e : students.getStudents().entrySet()) {
			System.out.println(e.getValue());
		}

	}

	public void listSeminars() {
		for (Entry<Integer, Seminar> e : seminars.getSeminars().entrySet()) {
			System.out.println(e.getValue());
		}

	}

	public void listUnregisteredStudents() {
		for (Entry<Integer, Student> e : unregisteredStudents.getStudents().entrySet()) {
			System.out.println(e.getValue());
		}
	}

}

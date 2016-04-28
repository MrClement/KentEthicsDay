package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import sort.FullConfiguration;

public class DataOutput {

	// Student name, pref 1 (id and title), pref rank, pref 2 ,pref rank,
	// registered?
	// Seminar name, student username, student pref, 1, 2, 3, 4, 5

	private int counter;
	private String studentOutputFile;
	private String seminarOutputFile;
	private BufferedWriter out;
	private FullConfiguration config;
	private StudentList students;
	private SeminarList seminars;

	public DataOutput(String studentOutputFile, String seminarOutputFile, FullConfiguration config,
			StudentList students, SeminarList seminars) {
		this.studentOutputFile = studentOutputFile;
		this.seminarOutputFile = seminarOutputFile;
		this.config = config;
		this.students = students;
		this.seminars = seminars;

	}

	public void generateStudentFile() {
		File old = new File(studentOutputFile);
		old.delete();
		try {
			out = new BufferedWriter(new FileWriter(studentOutputFile, true));
			out.write("First Name\tLast Name\tUsername\tRegistered?\tPreference 1\tRank\tPreference 2\tRank\tOtherChoices");
			out.newLine();
			HashMap<Integer, Student> students2 = students.getStudents();
			counter = 0;
			for (Entry<Integer, Student> e : students2.entrySet()) {
				Student s = e.getValue();
				writeStudentToFile(s);
			}
			System.out.println(counter);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeStudentToFile(Student s) throws IOException {
		String toWrite = s.getFirstName() + "\t" + s.getLastName() + "\t" + s.getEmail().split("@")[0] + "\t"
				+ s.didRegister() + "\t";
		ArrayList<Integer> assignedSeminars = s.getAssignedSeminars();
		if (assignedSeminars.size() < 2) {
			counter++;
			System.out.println(s);
		} else {
			for (int i = 0; i < 2; i++) {
				toWrite += assignedSeminars.get(i) + "\t" + getRank(s, assignedSeminars.get(i));
				toWrite += "\t";
			}
			toWrite += s.getSeminarPreferences().toString();
			out.write(toWrite);
			out.newLine();
		}
	}

	private int getRank(Student s, Integer pref) {
		if (!s.didRegister()) {
			return 0;
		}
		ArrayList<Integer> seminarPreferences = s.getSeminarPreferences();
		for (int i = 0; i < seminarPreferences.size(); i++) {
			if (pref.equals(seminarPreferences.get(i))) {
				return i + 1;
			}
		}
		return -1;
	}

	public void generateSeminarFile() {
		File old = new File(seminarOutputFile);
		old.delete();
		try {
			out = new BufferedWriter(new FileWriter(seminarOutputFile, true));
			out.write("Seminar ID\tTitle\tFaculty Sponsor\tFirst Session Registered Students\tFirst Total\tFirst Ranks\tGender Ratio\tSecond Session RegisteredStudents\tSecond Total\tSecond Ranks\tGender Ratio");
			out.newLine();
			HashMap<Integer, ArrayList<Student>> first = config.getFirst().getConfig();
			for (Entry<Integer, ArrayList<Student>> e : first.entrySet()) {
				writeSeminarToFile(e.getKey(), e.getValue());
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeSeminarToFile(Integer key, ArrayList<Student> value) throws IOException {
		String toWrite = key + "\t" + seminars.getSeminars().get(key).getTitle() + "\t"
				+ seminars.getSeminars().get(key).getInstructorName() + "\t";
		int[] ranks = new int[6];
		// toWrite += "\"";

		for (int i = 0; i < value.size(); i++) {
			Student student = value.get(i);
			int rank = getRank(student, key);
			if (rank > 0) {
				ranks[rank - 1]++;
			} else {
				ranks[5]++;
			}
			String stringTemp = student.getEmail().split("@")[0];
			toWrite += stringTemp.substring(0, stringTemp.length() - 2) + " " + student.getGrade()
					+ (!student.isGender() ? "m" : "f") + " " + " (" + rank + "), ";
		}
		// toWrite += "\"";
		toWrite += "\t" + value.size();
		toWrite += writeRanks(ranks);
		toWrite += genderRatio(value);
		ranks = new int[6];
		ArrayList<Student> secondList = config.getSecond().getConfig().get(key);
		// toWrite += "\"";
		for (int i = 0; i < secondList.size(); i++) {
			Student student = secondList.get(i);
			int rank = getRank(student, key);
			if (rank > 0) {
				ranks[rank - 1]++;
			} else {
				ranks[5]++;
			}
			String stringTemp = student.getEmail().split("@")[0];
			toWrite += stringTemp.substring(0, stringTemp.length() - 2) + " " + student.getGrade()
					+ (!student.isGender() ? "m" : "f") + " " + " (" + rank + "), ";
		}
		// toWrite += "\"";
		toWrite += "\t" + secondList.size();
		toWrite += writeRanks(ranks);
		toWrite += genderRatio(secondList);
		out.write(toWrite);
		out.newLine();
	}

	private String genderRatio(ArrayList<Student> secondList) {
		String toReturn = "\t";
		int sum = 0;
		for (int i = 0; i < secondList.size(); i++) {
			if (secondList.get(i).isGender())
				sum++;
		}
		toReturn += "" + (1.0 * sum / secondList.size()) + "\t";
		return toReturn;
	}

	private String writeRanks(int[] ranks) throws IOException {
		String toReturn = "\t";
		toReturn += ranks[0];
		for (int i = 1; i < ranks.length; i++) {
			toReturn += " " + ranks[i];
		}
		return toReturn;
	}
}

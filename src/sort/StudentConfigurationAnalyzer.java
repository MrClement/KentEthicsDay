package sort;

import java.util.ArrayList;
import java.util.Map.Entry;

import data.Student;

public class StudentConfigurationAnalyzer {

	private StudentConfiguration toTest;

	public StudentConfigurationAnalyzer(StudentConfiguration toTest) {
		this.toTest = toTest;
	}

	public String getSeminarPreferenceInfo() {
		String toReturn = "ID\t 1 \t 2 \t 3 \t 4 \t 5 \t -1 \t Total \t Male/Female\n";
		for (Entry<Integer, ArrayList<Student>> e : toTest.getConfig().entrySet()) {
			ArrayList<Student> value = e.getValue();
			int[] prefs = new int[5];
			for (int i = 0; i < value.size(); i++) {
				ArrayList<Integer> seminarPreferences = value.get(i).getSeminarPreferences();
				for (int j = 0; j < seminarPreferences.size(); j++) {
					if (e.getKey() == seminarPreferences.get(j)) {
						prefs[j]++;
					}
				}
			}
			int total = 0;
			toReturn += "" + e.getKey();
			for (int i = 0; i < prefs.length; i++) {
				int now = prefs[i];
				total += now;
				toReturn += "\t" + now;
			}
			toReturn += "\t" + (value.size() - total) + "\t" + value.size() + "\t" + getSeminarGenderRatio(e.getKey());
			toReturn += "\n";
		}
		return toReturn;

	}

	public String getSeminarGenderRatio(int semID) {
		ArrayList<Student> students = toTest.getConfig().get(semID);
		int male = 0;
		int female = 0;
		for (Student student : students) {
			if (student.isGender() == Student.MALE) {
				male++;
			} else {
				female++;
			}

		}
		return "M: " + male + " F: " + female;
	}

	public ArrayList<Integer> hasSoloSixthGraders() {
		ArrayList<Integer> soloSems = new ArrayList<Integer>();
		for (Entry<Integer, ArrayList<Student>> e : toTest.getConfig().entrySet()) {
			int numSixth = 0;
			ArrayList<Student> list = e.getValue();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getGrade() == 6) {
					numSixth++;
				}
			}
			if (numSixth == 1) {
				soloSems.add(e.getKey());
			}
		}
		return soloSems;
	}

	public int numStudents() {
		int sum = 0;
		for (Entry<Integer, ArrayList<Student>> e : toTest.getConfig().entrySet()) {
			sum += e.getValue().size();
		}
		return sum;
	}

}

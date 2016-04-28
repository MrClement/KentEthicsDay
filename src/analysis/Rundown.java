package analysis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Map.Entry;

import sort.FullConfiguration;
import sort.StudentConfigurationAnalyzer;
import data.Student;

public class Rundown {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		InputStream file = new FileInputStream("OldSorts/Sort_Sun_Feb_21_13-58-49_MST_2016.ser");
		ObjectInputStream in = new ObjectInputStream(file);
		FullConfiguration c = (FullConfiguration) in.readObject();
		in.close();
		StudentConfigurationAnalyzer analyzerFirst = new StudentConfigurationAnalyzer(c.getFirst());
		StudentConfigurationAnalyzer analyzerSecond = new StudentConfigurationAnalyzer(c.getSecond());
		System.out.println("First:");
		ArrayList<Integer> soloFirst = analyzerFirst.hasSoloSixthGraders();
		System.out.println("Has solo sixth? " + (soloFirst.size() != 0) + " " + soloFirst.toString());
		System.out.println(analyzerFirst.numStudents());
		System.out.println(analyzerFirst.getSeminarPreferenceInfo());
		System.out.println("Second:");
		ArrayList<Integer> soloSecond = analyzerSecond.hasSoloSixthGraders();
		System.out.println("Has solo sixth? " + (soloSecond.size() != 0) + " " + soloSecond.toString());
		System.out.println(analyzerSecond.numStudents());
		System.out.println(analyzerSecond.getSeminarPreferenceInfo());

		System.out.println();
		System.out.println("Bad Sort Outcomes:");
		ArrayList<Student> badSortOutcomes = badSortOutcomes(c);
		for (int i = 0; i < args.length; i++) {
			System.out.println(badSortOutcomes.get(i));
		}

	}

	private static ArrayList<Student> badSortOutcomes(FullConfiguration c) {
		ArrayList<Student> output = new ArrayList<Student>();
		for (Entry<Integer, Student> e : c.getStudents().getStudents().entrySet()) {
			Student s = e.getValue();
			boolean gotASem = false;
			if (s.didRegister()) {
				ArrayList<Integer> sems = s.getAssignedSeminars();
				ArrayList<Integer> seminarPreferences = s.getSeminarPreferences();
				for (int i = 0; i < sems.size(); i++) {
					for (int j = 0; j < seminarPreferences.size(); j++) {
						if (sems.get(i) == seminarPreferences.get(j)) {
							gotASem = true;
						}
					}
				}
			}
			if (!gotASem) {
				output.add(s);
			}
		}
		return output;
	}
}

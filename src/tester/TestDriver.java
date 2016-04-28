package tester;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map.Entry;

import sort.FullConfiguration;
import sort.SortWeighting;
import sort.Sorter;
import sort.StudentConfigurationAnalyzer;
import data.CSVParser;
import data.DataOutput;
import data.Student;
import data.StudentList;

public class TestDriver {

	public static void main(String[] args) throws IOException {
		CSVParser c = new CSVParser("Students.csv", "Seminars.tsv", "UnregisteredStudents.csv");
		// System.out.println("Students: ");
		// c.listStudents();
		// System.out.println();
		// System.out.println("Seminars: ");
		// c.listSeminars();

		// c.listUnregisteredStudents();
		SortWeighting weighting = new SortWeighting(new int[] { 5, 5, 5, 1, 1, -15 }, 0, 0);
		int[] seminarSizes = new int[c.getSeminars().numSeminars()];
		for (int i = 0; i < seminarSizes.length; i++) {
			seminarSizes[i] = 15;
		}
		Sorter s = new Sorter(c.getStudents(), c.getSeminars(), c.getUnregistered(), weighting, seminarSizes,
				Sorter.FILL_FROM_TOP, c, 10000, new int[] { 1, 2, 3, 4, 5, 6, 37 }, new int[0], new int[0]);
		// s.removeSeminarsFromFirstSession();
		// s.removeSeminarsFromSecondSession();
		FullConfiguration bestSort = s.getBestSort();
		System.out.println(c.getStudents().numStudents());
		System.out.println(c.getUnregistered().numStudents());
		StudentConfigurationAnalyzer analyzerFirst = new StudentConfigurationAnalyzer(bestSort.getFirst());
		StudentConfigurationAnalyzer analyzerSecond = new StudentConfigurationAnalyzer(bestSort.getSecond());
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
		StudentList allStudents = makeAllStudents(bestSort);
		DataOutput d = new DataOutput("StudentSeminarSelection.tsv", "FilledSeminars.tsv", bestSort, allStudents,
				c.getSeminars());
		d.generateStudentFile();
		d.generateSeminarFile();
		writeConfigurationToFile(bestSort);
	}

	public static StudentList makeAllStudents(FullConfiguration c) {
		StudentList toReturn = new StudentList();
		StudentList students = c.getStudents();
		StudentList unregistered = c.getUnregistedStudents();
		for (Entry<Integer, Student> e : students.getStudents().entrySet()) {
			toReturn.add(e.getValue());
		}
		for (Entry<Integer, Student> e : unregistered.getStudents().entrySet()) {
			toReturn.add(e.getValue());
		}
		return toReturn;
	}

	private static void writeConfigurationToFile(FullConfiguration c) throws IOException {
		GregorianCalendar now = new GregorianCalendar(new Locale("Denver"));
		OutputStream file = new FileOutputStream(new File("OldSorts/Sort_"
				+ now.getTime().toString().replace(' ', '_').replace(':', '-') + ".ser"));
		ObjectOutputStream out = new ObjectOutputStream(file);
		out.writeObject(c);

	}
}

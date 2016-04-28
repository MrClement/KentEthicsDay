package driver;

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

public class RunSort {

	public static void main(String[] args) throws IOException {
		CSVParser c = new CSVParser("Students.csv", "Seminars.tsv", "UnregisteredStudents.csv");
		// System.out.println("Students: ");
		// c.listStudents();
		// System.out.println();
		// System.out.println("Seminars: ");
		// c.listSeminars();

		// c.listUnregisteredStudents();
		int[] seminarSizes = new int[c.getSeminars().numSeminars() + 1];
		for (int i = 1; i < seminarSizes.length; i++) {
			seminarSizes[i] = 16;
		}
		seminarSizes[1] = 8;
		seminarSizes[2] = 13;
		seminarSizes[3] = 13;
		seminarSizes[4] = 13;
		seminarSizes[5] = 13;
		seminarSizes[6] = 13;
		seminarSizes[7] = 13;
		seminarSizes[8] = 0;
		SortWeighting weighting = new SortWeighting(new int[] { 5, 5, 5, 1, 1, -15 }, 0, 0);
		Sorter s = new Sorter(c.getStudents(), c.getSeminars(), c.getUnregistered(), weighting, seminarSizes,
				Sorter.FILL_FROM_TOP, c, 100000, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 }, new int[] { 8, 10,
						30, 44, 23 }, new int[] { 8, 10, 30, 44, 31, 38 });
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

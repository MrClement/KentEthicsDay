package tester;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import sort.MovieConfiguration;
import data.MovieCSVParser;
import data.MovieStudent;
import data.Room;

public class MovieSorterDriver {

	public static void main(String[] args) throws IOException {
		MovieCSVParser m = new MovieCSVParser("MovieStudents.csv", "MovieRooms.csv");

		HashMap<Integer, Room> rooms = m.getRooms();
		HashMap<Integer, MovieStudent> students = m.getStudents();

		MovieConfiguration test = sortOnce(rooms, students);
		ArrayList<MovieStudent> students2 = test.getStudents();
		for (Entry<Integer, MovieStudent> movieStudent : students.entrySet()) {
			if (!students2.contains(movieStudent.getValue())) {
				System.out.println(movieStudent.getValue());
			}
		}
		while (!isValid(test)) {
			test = sortOnce(rooms, students);
			// test.printRooms();
		}
		test.printRooms();
		writeToFile(test);
		writeConfigurationToFile(test);
		// System.out.println(rooms);

	}

	private static void writeToFile(MovieConfiguration test) {

		BufferedWriter out;
		File old = new File("Movie Output.csv");
		old.delete();
		try {
			out = new BufferedWriter(new FileWriter("Movie Output.csv", true));
			ArrayList<MovieStudent> students = test.getStudents();
			for (MovieStudent movieStudent : students) {
				out.write(movieStudent.getFirstName() + "," + movieStudent.getLastName() + ","
						+ movieStudent.getEmail() + "," + movieStudent.getMoviePreference() + ","
						+ movieStudent.getRoomAssignment());
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static MovieConfiguration sortOnce(HashMap<Integer, Room> rooms, HashMap<Integer, MovieStudent> students) {
		MovieConfiguration toReturn = new MovieConfiguration();
		toReturn.generateConfiguration(rooms, students);
		return toReturn;
	}

	private static boolean isValid(MovieConfiguration toTest) {
		HashMap<Integer, ArrayList<Room>> rooms = toTest.getRooms();
		for (Entry<Integer, ArrayList<Room>> e : rooms.entrySet()) {
			for (Room r : e.getValue()) {
				if (!isRoomValid(r)) {
					return false;
				}
			}
		}
		return true;
	}

	private static void writeConfigurationToFile(MovieConfiguration c) throws IOException {
		GregorianCalendar now = new GregorianCalendar(new Locale("Denver"));
		OutputStream file = new FileOutputStream(new File("MovieSorts/Sort_"
				+ now.getTime().toString().replace(' ', '_').replace(':', '-') + ".ser"));
		ObjectOutputStream out = new ObjectOutputStream(file);
		out.writeObject(c);

	}

	public static boolean isRoomValid(Room r) {
		if (r.getAssignedStudents().size() > 0) {
			int[] grades = new int[7];
			int females = 0;
			int totalStudents = r.getAssignedStudents().size();
			for (MovieStudent m : r.getAssignedStudents()) {
				if (m.isGender()) {
					females++;
				}
				grades[m.getGrade() - 6]++;
			}
			double genderRatio = 1.0 * females / totalStudents;
			boolean genderCheck = females >= 2 && (totalStudents - females >= 2);
			boolean msCheck = (grades[0] == 0 || grades[0] > 1) && (grades[1] == 0 || grades[1] > 1)
					&& (grades[2] == 0 || grades[2] > 1);
			boolean hsCheck = (grades[3] == 0 || grades[3] > 1) && (grades[4] == 0 || grades[4] > 1)
					&& (grades[5] == 0 || grades[5] > 1) && (grades[6] == 0 || grades[6] > 1);
			boolean gradeCheck = hsCheck && msCheck;
			// System.out.println(r.getRoomName() + ": " + genderRatio + " " +
			// gradesToString(grades));
			return genderCheck;
		} else {
			return true;
		}

	}

	public static String gradesToString(int[] grades) {

		String toReturn = "[ ";
		toReturn += grades[0];
		for (int i = 1; i < grades.length; i++) {
			toReturn += ", " + grades[i];
		}
		toReturn += " ]";
		return toReturn;
	}

	public static String gradesToString(Room r) {
		int[] grades = new int[7];
		for (MovieStudent m : r.getAssignedStudents()) {
			grades[m.getGrade() - 6]++;
		}
		String toReturn = "[ ";
		toReturn += grades[0];
		for (int i = 1; i < grades.length; i++) {
			toReturn += ", " + grades[i];
		}
		toReturn += " ]";
		return toReturn;
	}

	public static double getGenderRatio(Room r) {
		int females = 0;
		int totalStudents = r.getAssignedStudents().size();
		for (MovieStudent m : r.getAssignedStudents()) {
			if (m.isGender()) {
				females++;
			}
		}
		return 1.0 * females / totalStudents;
	}
}

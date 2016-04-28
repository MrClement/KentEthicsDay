package sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import tester.MovieSorterDriver;
import data.MovieStudent;
import data.Room;

public class MovieConfiguration implements Serializable {

	private HashMap<Integer, ArrayList<Room>> rooms;
	private ArrayList<MovieStudent> students;

	private int numMovies = 5;

	public MovieConfiguration() {
		rooms = new HashMap<Integer, ArrayList<Room>>();
		students = new ArrayList<MovieStudent>();
	}

	public void generateConfiguration(HashMap<Integer, Room> rooms2, HashMap<Integer, MovieStudent> students2) {

		HashMap<Integer, HashMap<Integer, ArrayList<MovieStudent>>> studentsByType = new HashMap<Integer, HashMap<Integer, ArrayList<MovieStudent>>>();

		fillStudentsByType(studentsByType, students2);

		// System.out.println(studentsByType.get(1).get(6).size());

		HashMap<Integer, ArrayList<Room>> unFilledRooms = new HashMap<Integer, ArrayList<Room>>();

		for (Entry<Integer, Room> e : rooms2.entrySet()) {
			Room value = e.getValue();
			int filmNumber = value.getFilmNumber();

			if (unFilledRooms.get(filmNumber) == null) {
				unFilledRooms.put(filmNumber, new ArrayList<Room>());
			}
			unFilledRooms.get(filmNumber).add(new Room(value));
		}

		for (int i = 1; i <= numMovies; i++) {
			HashMap<Integer, ArrayList<MovieStudent>> currentMovieList = studentsByType.get(i);
			for (int j = 6; j <= 12; j++) {
				ArrayList<MovieStudent> studentsByGradeAndMovie = currentMovieList.get(j);
				addTwoToEachRoom(unFilledRooms.get(i), studentsByGradeAndMovie);
			}
			for (int j = 6; j <= 12; j++) {
				ArrayList<MovieStudent> studentsByGradeAndMovie = currentMovieList.get(j);
				fillWithRemainingStudents(unFilledRooms.get(i), studentsByGradeAndMovie);
			}
		}

		for (Entry<Integer, ArrayList<Room>> e : unFilledRooms.entrySet()) {
			int num = e.getKey();
			ArrayList<Room> value = e.getValue();
			for (Room room : value) {
				if (rooms.get(num) == null) {
					rooms.put(num, new ArrayList<Room>());
				}
				rooms.get(num).add(room);
			}
		}
		// printRooms();

	}

	private void fillWithRemainingStudents(ArrayList<Room> rooms, ArrayList<MovieStudent> studentsByGradeAndMovie) {
		for (Room r : rooms) {
			while (studentsByGradeAndMovie.size() > 0 && (r.getAssignedStudents().size() < r.getRoomCapacity())) {
				MovieStudent remove = studentsByGradeAndMovie.remove(0);
				remove.setRoomAssignment(r.getRoomName());
				students.add(remove);
				r.getAssignedStudents().add(remove);
			}
		}
	}

	private void addTwoToEachRoom(ArrayList<Room> rooms, ArrayList<MovieStudent> studentsByGradeAndMovie) {

		for (Room r : rooms) {
			if (studentsByGradeAndMovie.size() >= 4) {
				MovieStudent remove = studentsByGradeAndMovie.remove(0);
				remove.setRoomAssignment(r.getRoomName());
				students.add(remove);
				r.getAssignedStudents().add(remove);
				remove = studentsByGradeAndMovie.remove(0);
				remove.setRoomAssignment(r.getRoomName());
				students.add(remove);
				r.getAssignedStudents().add(remove);
			} else {
				for (int i = 0; i < studentsByGradeAndMovie.size(); i++) {
					MovieStudent remove = studentsByGradeAndMovie.remove(i--);
					remove.setRoomAssignment(r.getRoomName());
					students.add(remove);
					r.getAssignedStudents().add(remove);
				}
			}
		}

	}

	private void fillStudentsByType(HashMap<Integer, HashMap<Integer, ArrayList<MovieStudent>>> studentsByType,
			HashMap<Integer, MovieStudent> students2) {
		for (int j = 1; j <= numMovies; j++) {
			studentsByType.put(j, generateStudentList(j, students2));
		}

	}

	private HashMap<Integer, ArrayList<MovieStudent>> generateStudentList(int i,
			HashMap<Integer, MovieStudent> students2) {
		HashMap<Integer, ArrayList<MovieStudent>> toReturn = new HashMap<Integer, ArrayList<MovieStudent>>();
		for (int i2 = 4; i2 <= 12; i2++) {
			ArrayList<MovieStudent> temp = new ArrayList<MovieStudent>();
			if (i2 > 5) {
				for (Entry<Integer, MovieStudent> e : students2.entrySet()) {
					MovieStudent value = e.getValue();
					if (value.getGrade() == i2 && i == value.getMoviePreference()) {
						temp.add(new MovieStudent(value));
					}
				}
			} else {
				if (i2 == 4) {
					for (Entry<Integer, MovieStudent> e : students2.entrySet()) {
						MovieStudent value = e.getValue();
						if (value.isGender() && i == value.getMoviePreference()) {
							temp.add(new MovieStudent(value));
						}
					}
				} else {
					for (Entry<Integer, MovieStudent> e : students2.entrySet()) {
						MovieStudent value = e.getValue();
						if (!value.isGender() && i == value.getMoviePreference()) {
							temp.add(new MovieStudent(value));
						}
					}
				}
			}
			Collections.shuffle(temp);
			toReturn.put(i2, temp);
		}
		return toReturn;
	}

	public void printRooms() {
		for (Entry<Integer, ArrayList<Room>> e : rooms.entrySet()) {
			for (Room r : e.getValue()) {

				System.out.println("Name: " + r.getRoomName() + " Capacity: " + r.getRoomCapacity() + " Fill: "
						+ r.getAssignedStudents().size() + " Valid?: " + MovieSorterDriver.isRoomValid(r)
						+ " Gender Ratio: " + MovieSorterDriver.getGenderRatio(r) + " "
						+ MovieSorterDriver.gradesToString(r));
			}
		}
	}

	public void printStudents() {

	}

	public HashMap<Integer, ArrayList<Room>> getRooms() {
		return rooms;
	}

	public ArrayList<MovieStudent> getStudents() {
		return students;
	}

	public void setRooms(HashMap<Integer, ArrayList<Room>> rooms) {
		this.rooms = rooms;
	}

	public void setStudents(ArrayList<MovieStudent> students) {
		this.students = students;
	}

}

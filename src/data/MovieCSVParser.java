package data;

import java.util.HashMap;
import java.util.Scanner;

public class MovieCSVParser {

	private HashMap<Integer, MovieStudent> students;
	private HashMap<Integer, Room> rooms;

	private int studentCounter;
	private int roomCounter;

	@SuppressWarnings("resource")
	public MovieCSVParser(String studentFile, String roomFile) {
		studentCounter = 0;
		roomCounter = 0;
		students = new HashMap<Integer, MovieStudent>();
		rooms = new HashMap<Integer, Room>();
		Scanner s;
		s = new Scanner(MovieCSVParser.class.getClassLoader().getResourceAsStream(studentFile));
		s.nextLine();
		while (s.hasNextLine()) {
			parseStudent(s.nextLine());

		}
		s = new Scanner(MovieCSVParser.class.getClassLoader().getResourceAsStream(roomFile));
		s.nextLine();
		while (s.hasNextLine()) {
			parseRoom(s.nextLine());

		}
		System.out.println(students);
		System.out.println(rooms);
	}

	private void parseStudent(String nextLine) {

		String[] pieces = nextLine.split(",");
		String email = pieces[0];
		String firstName = pieces[1];
		String lastName = pieces[2];
		boolean gender = pieces[3].equals("F");
		int moviePref = Integer.parseInt(pieces[5]);
		MovieStudent s = new MovieStudent(gender, firstName, lastName, email, moviePref);
		students.put(studentCounter++, s);

	}

	private void parseRoom(String nextLine) {
		String[] pieces = nextLine.split(",");
		String name = pieces[0];
		int capacity = Integer.parseInt(pieces[1]);
		String filmName = pieces[2];
		int filmNumber = Integer.parseInt(pieces[3]);
		Room r = new Room(name, capacity, filmNumber, filmName);
		rooms.put(roomCounter++, r);

	}

	public HashMap<Integer, MovieStudent> getStudents() {
		return students;
	}

	public void setStudents(HashMap<Integer, MovieStudent> students) {
		this.students = students;
	}

	public HashMap<Integer, Room> getRooms() {
		return rooms;
	}

	public void setRooms(HashMap<Integer, Room> rooms) {
		this.rooms = rooms;
	}
}

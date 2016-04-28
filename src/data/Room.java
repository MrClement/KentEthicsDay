package data;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {

	private String roomName;
	private int roomCapacity;
	private int filmNumber;
	private String filmName;
	private ArrayList<MovieStudent> assignedStudents;

	public Room(String roomName, int roomCapacity, int filmNumber, String filmName) {
		this.roomName = roomName;
		this.roomCapacity = roomCapacity;
		this.filmName = filmName;
		this.filmNumber = filmNumber;
		assignedStudents = new ArrayList<MovieStudent>();
	}

	public Room(Room other) {
		this.roomName = other.getRoomName();
		this.roomCapacity = other.getRoomCapacity();
		this.filmName = other.getFilmName();
		this.filmNumber = other.getFilmNumber();
		ArrayList<MovieStudent> assignedStudents2 = other.getAssignedStudents();
		assignedStudents = new ArrayList<MovieStudent>();
		for (int i = 0; i < assignedStudents2.size(); i++) {
			assignedStudents.add(new MovieStudent(assignedStudents2.get(i)));
		}

	}

	@Override
	public String toString() {
		return "Room [roomName=" + roomName + ", roomCapacity=" + roomCapacity + ", filmNumber=" + filmNumber
				+ ", filmName=" + filmName + ", assignedStudents=" + assignedStudents + "]";
	}

	public String getRoomName() {
		return roomName;
	}

	public int getRoomCapacity() {
		return roomCapacity;
	}

	public int getFilmNumber() {
		return filmNumber;
	}

	public String getFilmName() {
		return filmName;
	}

	public ArrayList<MovieStudent> getAssignedStudents() {
		return assignedStudents;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void setRoomCapacity(int roomCapacity) {
		this.roomCapacity = roomCapacity;
	}

	public void setFilmNumber(int filmNumber) {
		this.filmNumber = filmNumber;
	}

	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}

	public void setAssignedStudents(ArrayList<MovieStudent> assignedStudents) {
		this.assignedStudents = assignedStudents;
	}

}

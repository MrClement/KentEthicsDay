package sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Random;

import data.CSVParser;
import data.SeminarList;
import data.Student;
import data.StudentList;

public class Sorter {

	private StudentList students;
	private SeminarList seminars;
	private StudentList unregistered;
	private PriorityQueue<FullConfiguration> attempts;
	private FullConfiguration best;
	private ArrayList<Student> randomStudentList;
	private SortWeighting weighting;
	private int[] maxSeminarSize;
	private int numSeminars;

	public static final int FILL_FROM_BOTTOM = 1;
	public static final int FILL_FROM_TOP = 2;
	public static final int FILL_RANDOM = 3;
	private CSVParser parser;

	private StudentList originalStudents;
	private StudentList originalUnRegStudents;
	private SeminarList originalSeminars;
	private ArrayList<Integer> doubleSeminars;
	private ArrayList<Integer> secondPrevent;
	private ArrayList<Integer> firstPrevent;

	public Sorter(StudentList students, SeminarList seminars, StudentList unregisted, SortWeighting weighting,
			int[] maxSeminarSize, int fillType, CSVParser c, int runs, int[] isDouble, int[] firstPrevent,
			int[] secondPrevent) {

		this.originalStudents = new StudentList(students);
		this.originalUnRegStudents = new StudentList(unregisted);
		this.originalSeminars = new SeminarList(seminars);

		this.students = new StudentList(students);
		this.seminars = new SeminarList(seminars);
		this.unregistered = new StudentList(unregisted);
		this.maxSeminarSize = maxSeminarSize;
		this.parser = c;
		attempts = new PriorityQueue<FullConfiguration>();
		randomStudentList = new ArrayList<Student>();
		for (Entry<Integer, Student> e : students.getStudents().entrySet()) {
			randomStudentList.add(e.getValue());
		}
		Collections.shuffle(randomStudentList);
		this.weighting = weighting;
		setDoubleSeminars(isDouble);
		setFirstPrevent(firstPrevent);
		setSecondPrevent(secondPrevent);
		makeAttempts(runs, fillType);

	}

	private void setSecondPrevent(int[] secondPrevent) {
		this.secondPrevent = new ArrayList<Integer>();
		for (int i = 0; i < secondPrevent.length; i++) {
			this.secondPrevent.add(secondPrevent[i]);
		}
	}

	private void setFirstPrevent(int[] firstPrevent) {
		this.firstPrevent = new ArrayList<Integer>();
		for (int i = 0; i < firstPrevent.length; i++) {
			this.firstPrevent.add(firstPrevent[i]);
		}
	}

	private void makeAttempts(int i, int fillType) {
		for (int j = 0; j < i; j++) {
			randomSort(fillType);
		}
	}

	private void randomSort(int fillType) {
		this.students = new StudentList(originalStudents);
		this.seminars = new SeminarList(originalSeminars);
		this.unregistered = new StudentList(originalUnRegStudents);
		randomStudentList = new ArrayList<Student>();
		for (Entry<Integer, Student> e : students.getStudents().entrySet()) {
			randomStudentList.add(e.getValue());
		}
		Collections.shuffle(randomStudentList);
		FullConfiguration both = new FullConfiguration(weighting, maxSeminarSize, firstPrevent, secondPrevent,
				doubleSeminars);
		both.setSeminars(seminars);
		both.setStudents(students);
		both.setUnregistedStudents(unregistered);
		StudentConfiguration first = both.getFirst();
		Collections.shuffle(randomStudentList);
		HashMap<Student, Integer> twiceStudents = new HashMap<Student, Integer>();
		for (Student s : randomStudentList) {
			for (int i = 0; i < s.getSeminarPreferences().size(); i++) {
				int semId = s.getSeminarPreferences().get(i);
				if (first.addStudent(semId, s)) {
					if (doubleSeminars.contains(semId)) {
						twiceStudents.put(s, semId);
					}
					break;
				}
			}
			if (s.getAssignedSeminars().size() == 0) {
				assignToRandom(s, first);
			}
		}

		StudentConfiguration second = both.getSecond();
		for (Entry<Student, Integer> e : twiceStudents.entrySet()) {
			second.addStudent(e.getValue(), e.getKey());
		}
		Collections.shuffle(randomStudentList);
		for (Student s : randomStudentList) {
			for (int i = 0; i < s.getSeminarPreferences().size(); i++) {
				int semID = s.getSeminarPreferences().get(i);
				if (!doubleSeminars.contains(semID) && second.addStudent(semID, s)) {
					break;
				}
			}
			if (s.getAssignedSeminars().size() == 1) {
				assignToRandom(s, second);
			}
		}
		addUnregistered(fillType, both);
		both.adjustValues();
		if (best == null) {
			best = both;
		} else if (both.compareTo(best) > 0) {
			best = both;
		}
	}

	private void assignToRandom(Student s, StudentConfiguration first) {
		while (true) {
			if (first.addStudent(parser.getValidSeminar(s, seminars), s)) {
				break;
			}
		}

	}

	private void addUnregistered(int fillType, FullConfiguration both) {
		switch (fillType) {
			case FILL_FROM_BOTTOM:
				fillFromBottom(both);
				break;
			case FILL_FROM_TOP:
				fillFromTop(both);
				break;
			case FILL_RANDOM:
				fillRandom(both);
				break;
			default:
				break;
		}
	}

	private void fillRandom(FullConfiguration both) {
		Random r = new Random();
		ArrayList<Student> randomChoices = new ArrayList<Student>();
		for (Entry<Integer, Student> e : unregistered.getStudents().entrySet()) {
			Student temp = e.getValue();
			ArrayList<Integer> seminarPreferences = temp.getSeminarPreferences();
			for (int i = 0; i < seminarPreferences.size(); i++) {
				seminarPreferences.set(i, parser.getValidSeminar(temp, seminars));
			}
			randomChoices.add(temp);
		}
		StudentConfiguration first = both.getFirst();
		Collections.shuffle(randomChoices);
		for (Student s : randomChoices) {
			for (int i = 0; i < s.getSeminarPreferences().size(); i++) {
				if (first.addStudent(s.getSeminarPreferences().get(i), s)) {
					break;
				}
			}
			if (s.getAssignedSeminars().size() == 0) {
				assignToRandom(s, first);
			}
		}

		StudentConfiguration second = both.getSecond();
		Collections.shuffle(randomChoices);
		for (Student s : randomChoices) {
			for (int i = 0; i < s.getSeminarPreferences().size(); i++) {
				if (second.addStudent(s.getSeminarPreferences().get(i), s)) {
					break;
				}
			}
			if (s.getAssignedSeminars().size() == 1) {
				assignToRandom(s, second);
			}
		}
	}

	private void fillFromTop(FullConfiguration both) {
		StudentConfiguration first = both.getFirst();
		HashMap<Student, Integer> preclude = new HashMap<Student, Integer>();
		fillFromTopHelper(first, preclude);
		StudentConfiguration second = both.getSecond();
		fillFromTopHelper(second, preclude);

	}

	private void fillFromTopHelper(StudentConfiguration first, HashMap<Student, Integer> preclude) {
		ArrayList<Integer> seminarsToFill = new ArrayList<Integer>();

		for (Entry<Integer, ArrayList<Student>> e : first.getConfig().entrySet()) {
			ArrayList<Student> s = e.getValue();
			int numStudents = s.size();
			int index = -1;
			for (int i = 0; i < seminarsToFill.size(); i++) {
				int otherNum = first.getConfig().get(seminarsToFill.get(i)).size();
				if (numStudents >= otherNum) {
					index = i;
					break;
				}
			}
			if (index == -1) {
				seminarsToFill.add(e.getKey());
			} else {
				seminarsToFill.add(index, e.getKey());
			}
		}
		ArrayList<Student> randomChoices = new ArrayList<Student>();
		for (Entry<Integer, Student> e : unregistered.getStudents().entrySet()) {
			randomChoices.add(e.getValue());
		}
		Collections.shuffle(randomChoices);
		if (preclude.size() > 0) {
			for (Entry<Student, Integer> e : preclude.entrySet()) {
				first.addStudent(e.getValue(), e.getKey());
			}
		}
		for (int i = 0; i < randomChoices.size(); i++) {
			for (int j = 0; j < seminarsToFill.size(); j++) {
				int semID = seminarsToFill.get(j);
				if (first.getConfig().get(semID).size() == maxSeminarSize[semID]) {
					seminarsToFill.remove(j);
					j--;
				} else {
					if (first.addStudent(semID, randomChoices.get(i))) {
						if (doubleSeminars.contains(semID)) {
							preclude.put(randomChoices.get(i), semID);
						}
						break;
					}
				}
			}
		}
	}

	private void fillFromBottom(FullConfiguration both) {
		StudentConfiguration first = both.getFirst();
		fillFromBottomHelper(first);
		StudentConfiguration second = both.getSecond();
		fillFromBottomHelper(second);
	}

	private void fillFromBottomHelper(StudentConfiguration first) {
		ArrayList<Integer> seminarsToFill = new ArrayList<Integer>();

		for (Entry<Integer, ArrayList<Student>> e : first.getConfig().entrySet()) {
			ArrayList<Student> s = e.getValue();
			int numStudents = s.size();
			int index = -1;
			for (int i = 0; i < seminarsToFill.size(); i++) {
				int otherNum = first.getConfig().get(seminarsToFill.get(i)).size();
				if (numStudents <= otherNum) {
					index = i;
					break;
				}
			}
			if (index == -1) {
				seminarsToFill.add(e.getKey());
			} else {
				seminarsToFill.add(index, e.getKey());
			}
		}
		ArrayList<Student> randomChoices = new ArrayList<Student>();
		for (Entry<Integer, Student> e : unregistered.getStudents().entrySet()) {
			randomChoices.add(e.getValue());
		}
		Collections.shuffle(randomChoices);
		for (int i = 0; i < randomChoices.size(); i++) {
			for (int j = 0; j < seminarsToFill.size(); j++) {
				int semID = seminarsToFill.get(j);
				if (first.getConfig().get(semID).size() == maxSeminarSize[semID]) {
					seminarsToFill.remove(j);
					j--;
				} else {
					if (first.addStudent(semID, randomChoices.get(i))) {
						break;
					}
				}
			}
		}
	}

	public FullConfiguration getBestSort() {
		return best;
	}

	public void setDoubleSeminars(int[] is) {
		this.doubleSeminars = new ArrayList<Integer>();
		for (int i = 0; i < is.length; i++) {
			doubleSeminars.add(is[i]);
		}

	}
}

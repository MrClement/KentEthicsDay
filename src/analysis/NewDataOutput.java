package analysis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import sort.FullConfiguration;
import tester.TestDriver;
import data.CSVParser;
import data.DataOutput;
import data.StudentList;

public class NewDataOutput {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		CSVParser c = new CSVParser("Students.csv", "Seminars.tsv", "UnregisteredStudents.csv");
		InputStream file = new FileInputStream("OldSorts/Sort_Thu_Feb_19_15-27-50_MST_2015.ser");
		ObjectInputStream in = new ObjectInputStream(file);
		FullConfiguration bestSort = (FullConfiguration) in.readObject();
		in.close();
		StudentList allStudents = TestDriver.makeAllStudents(bestSort);
		DataOutput d = new DataOutput("StudentSeminarSelection.tsv", "FilledSeminars.tsv", bestSort, allStudents,
				c.getSeminars());
		d.generateStudentFile();
		// d.generateSeminarFile();
	}

}

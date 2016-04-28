package analysis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import sort.MovieConfiguration;

public class MovieAnalysis {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		InputStream file = new FileInputStream("MovieSorts/Sort_Tue_Mar_03_09-55-35_MST_2015.ser");
		ObjectInputStream in = new ObjectInputStream(file);
		MovieConfiguration c = (MovieConfiguration) in.readObject();
		in.close();
		c.printRooms();
		c.printStudents();

	}

}

package sort;

import java.io.Serializable;
import java.util.Arrays;

public class SortWeighting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] prefWeighting;
	private int sixthWeighting;
	private int genderWeighting;

	public SortWeighting(int[] prefWeighting, int sixthWeighting, int genderWeighting) {
		this.prefWeighting = prefWeighting;
		this.sixthWeighting = sixthWeighting;
		this.genderWeighting = genderWeighting;
	}

	@Override
	public String toString() {
		return "SortWeighting [prefWeighting=" + Arrays.toString(prefWeighting) + ", sixthWeighting=" + sixthWeighting
				+ ", genderWeighting=" + genderWeighting + "]";
	}

	public int[] getPrefWeighting() {
		return prefWeighting;
	}

	public int getSixthWeighting() {
		return sixthWeighting;
	}

	public int getGenderWeighting() {
		return genderWeighting;
	}

	public void setPrefWeighting(int[] prefWeighting) {
		this.prefWeighting = prefWeighting;
	}

	public void setSixthWeighting(int sixthWeighting) {
		this.sixthWeighting = sixthWeighting;
	}

	public void setGenderWeighting(int genderWeighting) {
		this.genderWeighting = genderWeighting;
	}

}

package pl.edu.agh.mwo.invoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvoicesList {
	// tu jako lista. W rzeczywistości z pliku.
	private List<Integer> numbers = new ArrayList<Integer>(Arrays.asList(0));
	
	public int getNewNumber() {
		int newNumber = this.numbers.get(this.numbers.size() - 1) + 1;
		this.numbers.add(newNumber);
		return newNumber;
	}

}

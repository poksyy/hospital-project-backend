package main.hospital;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NurseServiceImpl  {

	private List<Nurse> nurses = new ArrayList<>();

	// Constructor that initializes instances of Nurse to the ArrayList nurses.
	public NurseServiceImpl() {
		// // We need to pass the same parameters that are expected by the Nurse
		// constructor.
		nurses.add(new Nurse("Pau", "pl2024769", "pau1234"));
		nurses.add(new Nurse("Dylan", "pl2024379", "dylan1234"));
		nurses.add(new Nurse("Cristian", "pl2024768", "cristian1234"));

	}
}

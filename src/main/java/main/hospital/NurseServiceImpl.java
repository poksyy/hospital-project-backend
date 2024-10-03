package main.hospital;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NurseServiceImpl {

	private List<Nurse> nurses = new ArrayList<>();

	// Constructor that initializes instances of Nurse to the ArrayList nurses.
	public NurseServiceImpl() {
		// // We need to pass the same parameters that are expected by the Nurse
		// constructor.
		nurses.add(new Nurse("Pau", "pl2024769", "pau1234"));
		nurses.add(new Nurse("Dylan", "pl2024379", "dylan1234"));
		nurses.add(new Nurse("Cristian", "pl2024768", "cristian1234"));

	}
	
	public boolean LoginAuthentication(String username, String password) {

		// Loop to iterate the List 'nurses'.
		for (int i = 0; i < nurses.size(); i++) {
			// This allows us to access the variables and methods from Nurse class, and
			// access us to do operations on each Nurse object in the list.
			Nurse nurse = nurses.get(i);

			// Check if credentials are correct.
			if (nurse.getUsername().equals(username) && nurse.getPassword().equals(password)) {
				System.out.println("Welcome to the application!.");
				return true;
			}
		}
		
		System.err.println("Incorrect login.");
		return false;
	}
	
    public List<Nurse> getNursesInformation() {
        return nurses;
    }
}

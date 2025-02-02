package main.hospital;

import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class NurseValidator {

	// Validate password when registering a Nurse.
	public boolean isValidPassword(String password) {
		if (password == null || password.length() < 8) {
			return false;
		}
		if (!password.matches(".*[A-Z].*")) {
			return false;
		}
		if (!password.matches(".*[0-9].*")) {
			return false;
		}
		if (!password.matches(".*[!@#$%^&*()-_+=<>?].*")) {
			return false;
		}
		return true;
	}

	// Validate password when updating password.
	public boolean isValidPassword(Nurse nurse, NurseRepository nurseRepository) {
		if (nurse.getPassword() == null || nurse.getPassword().length() < 8) {
			return false;
		}
		if (!nurse.getPassword().matches(".*[A-Z].*")) {
			return false;
		}
		if (!nurse.getPassword().matches(".*[0-9].*")) {
			return false;
		}
		if (!nurse.getPassword().matches(".*[!@#$%^&*()-_+=<>?].*")) {
			return false;
		}
		return true;
	}
	
	// Used when updating Nurse Name and
	public boolean isValidProfileChanges(Nurse nurse, NurseRepository nurseRepository) {

		if (nurse.getUser() == null || nurse.getName() == null || nurse.getUser().trim().isEmpty()
				|| nurse.getName().trim().isEmpty()) {
			return false;
		}
		Optional<Nurse> currentNurse = nurseRepository.findById(nurse.getId());
		if (currentNurse.isPresent() && !currentNurse.get().getUser().equals(nurse.getUser())) {
			return isUserAvailable(nurse.getUser(), nurseRepository);
		}
		return true;
	}

	// Default validation for Nurse update
	public boolean isValidNurseRegister(Nurse nurse) {
	    System.out.println("Validating nurse registration: " + 
	            "name: " + nurse.getName() + 
	            ", user: " + nurse.getUser() + 
	            ", password: " + nurse.getPassword());
		return nurse.getName() != null && !nurse.getName().trim().isEmpty() && nurse.getUser() != null
				&& !nurse.getUser().trim().isEmpty() && nurse.getPassword() != null
				&& !nurse.getPassword().trim().isEmpty();
	}

	// Validates if the Username already exists on the database.
	public boolean isUserAvailable(String user, NurseRepository nurseRepository) {
		return !nurseRepository.existsByUser(user);
	}
}

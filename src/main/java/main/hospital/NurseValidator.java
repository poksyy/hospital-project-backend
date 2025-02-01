package main.hospital;

import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class NurseValidator {

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
	
	// Used when updating Nurse Name and 
	public boolean isValidProfileChanges(Nurse nurse, NurseRepository nurseRepository) {
		if (nurse.getUser() == null || nurse.getName() == null || nurse.getUser().trim().isEmpty()
				|| nurse.getName().trim().isEmpty()) {
			return false;
		}
		Optional<Nurse> currentNurse = nurseRepository.findById(nurse.getId());
		if (currentNurse.isPresent() && !currentNurse.get().getUser().equals(nurse.getUser())) {
			return isUsernameTaken(nurse.getUser(), nurseRepository);
		}
		return true;
	}

	// Default validation for Nurse update
	public boolean isValidNurseRegister(Nurse nurse) {
	    return nurse.getName() != null && !nurse.getName().trim().isEmpty() &&
	           nurse.getUser() != null && !nurse.getUser().trim().isEmpty() &&
	           nurse.getPassword() != null && !nurse.getPassword().trim().isEmpty();
	}

	// Validates if the Username already exists on the database.
	public boolean isUsernameTaken(String user, NurseRepository nurseRepository) {
	    return nurseRepository.existsByUser(user);
	}
}

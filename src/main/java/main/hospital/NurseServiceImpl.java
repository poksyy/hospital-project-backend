package main.hospital;

import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NurseServiceImpl implements NurseService {

	private final NurseRepository nurseRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final NurseValidator nurseValidator;

	@Autowired
	public NurseServiceImpl(NurseRepository nurseRepository, NurseValidator nurseValidator) {
		this.nurseRepository = nurseRepository;
		this.nurseValidator = nurseValidator;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	public Optional<Nurse> findByUserAndPassword(String user, String password) {
		return nurseRepository.findByUserAndPassword(user, password);
	}

	@Override
	public Iterable<Nurse> findAllNurses() {
		return nurseRepository.findAll();
	}

	@Override
	public Optional<Nurse> findByName(String name) {
		return nurseRepository.findByName(name);
	}

	@Override
	public Nurse registerNurse(Nurse nurse) {
		try {
			if (!nurseValidator.isValidPassword(nurse.getPassword())) {
				throw new IllegalArgumentException("Password requirements are incorrect.");
			}

			if (!nurseValidator.isValidNurseRegister(nurse)) {
				throw new IllegalArgumentException("Fields cannot be empty.");
			}

			if (nurseValidator.isUsernameTaken(nurse.getUser(), nurseRepository)) {
				throw new IllegalArgumentException("Username is already taken.");
			}

			nurse.setPassword(passwordEncoder.encode(nurse.getPassword()));

			return nurseRepository.save(nurse);
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
		}
	}

	// Used when updating Nurse name and username on ProfileScreen.
	@Override
	public Nurse updateNameAndUsername(Nurse nurse) {
		
		if (!nurseValidator.isValidProfileChanges(nurse, nurseRepository)) {
			   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
			            "Invalid profile changes. Username may be already taken or fields are empty.");
		  }
	    return nurseRepository.save(nurse);
	}

	// Default update method for updating Nurses.
	@Override
    public Nurse updateNurseInformation(Integer id, Nurse updatedNurse) {
        Optional<Nurse> existingNurse = nurseRepository.findById(id);
        
        if (!existingNurse.isPresent()) {
            throw new IllegalArgumentException("Nurse not found.");
        }

        Nurse nurse = existingNurse.get();

        if (!nurseValidator.isValidPassword(updatedNurse.getPassword())) {
            throw new IllegalArgumentException("Password does not meet the required criteria.");
        }

        nurse.setName(updatedNurse.getName());
        nurse.setUser(updatedNurse.getUser());
        nurse.setPassword(updatedNurse.getPassword());

        return nurseRepository.save(nurse);
    }

	@Override
	public Optional<Nurse> findById(Integer id) {
		return nurseRepository.findById(id);
	}

	@Override
	public Optional<Nurse> updateNurse(Integer id, Nurse updatedNurse) {
		return nurseRepository.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		nurseRepository.deleteById(id);
	}

	@Override
	public Nurse saveProfileImage(Integer id, byte[] image) {
		Optional<Nurse> optionalNurse = nurseRepository.findById(id);
		if (optionalNurse.isPresent()) {
			Nurse nurse = optionalNurse.get();
			nurse.setProfileImage(image);
			return nurseRepository.save(nurse);
		}
		return null;
	}

	@Override
	public byte[] getProfileImage(Integer id) {
		Nurse nurse = nurseRepository.findById(id).orElse(null);
		return (nurse != null) ? nurse.getProfileImage() : null;
	}
}

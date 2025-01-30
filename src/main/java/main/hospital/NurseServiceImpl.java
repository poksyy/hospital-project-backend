package main.hospital;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NurseServiceImpl implements NurseService {

	private final NurseRepository nurseRepository;
    private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public NurseServiceImpl(NurseRepository nurseRepository) {
		this.nurseRepository = nurseRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	public Optional<Nurse> findByUserAndPassword(String user, String password) {
		return nurseRepository.findByUserAndPassword(user, password);
	}
	
	@Override
	public Iterable<Nurse> findAll() {
		return nurseRepository.findAll();
	}
	
	@Override
	public Optional<Nurse> findByName(String name) {
		return nurseRepository.findByName(name);
	}

	// CRUD
	@Override
	public Nurse save(Nurse nurse) {
		
        if (!isValidPassword(nurse.getPassword())) {
            throw new IllegalArgumentException("Password requeriments are incorrect.");
        }

        nurse.setPassword(passwordEncoder.encode(nurse.getPassword()));
        
        return nurseRepository.save(nurse);
	}
	
	@Override
	public Nurse updateProfileChanges(Nurse nurse) {
		
	    if (!isValidProfileChanges(nurse)) {
	        throw new IllegalArgumentException("Invalid profile changes. Username may be already taken or fields are empty.");
	    }
	    
	    return nurseRepository.save(nurse);
	}
	
	// Check the password requirements.
    private boolean isValidPassword(String password) {
    	
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
    
    // Check the profile user and name requirements.
    private boolean isValidProfileChanges(Nurse nurse) {
    	
        // Validates that the fields are not empty or null.
        if (nurse.getUser() == null || nurse.getName() == null || 
            nurse.getUser().trim().isEmpty() || nurse.getName().trim().isEmpty()) {
            return false;
        }
        
        // Get the current Nurse ID.
        Optional<Nurse> currentNurse = nurseRepository.findById(nurse.getId());
        
        // Verifies that the username is not in use by another Nurse.
        if (currentNurse.isPresent() && !currentNurse.get().getUser().equals(nurse.getUser())) {
        	
            // Throws exception if the username is unavailable.
            return checkUserAvailability(nurse.getUser());
        }
        
        return true;
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
	
    // ProfileImage
    @Override
    public Nurse saveProfileImage(Integer id, byte[] image) {
    	// Search the nurse by ID.
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
    	// Search the nurse by ID.
        Nurse nurse = nurseRepository.findById(id).orElse(null);

        if (nurse == null) {
            return null;
        }
        // Returns the image in byte format.
        return nurse.getProfileImage(); 
    }
    
    public boolean checkUserAvailability(String user) {
        return !nurseRepository.existsByUser(user);
    }
}

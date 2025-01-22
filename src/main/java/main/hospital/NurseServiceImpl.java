package main.hospital;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NurseServiceImpl implements NurseService {

	private final NurseRepository nurseRepository;

	@Autowired
	public NurseServiceImpl(NurseRepository nurseRepository) {
		this.nurseRepository = nurseRepository;
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
}

package main.hospital;

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
	public Optional<Nurse> findByName(String name) {
		return nurseRepository.findByName(name);
	}

	@Override
	public Optional<Nurse> findByUserAndPassword(String user, String password) {
		return nurseRepository.findByUserAndPassword(user, password);
	}

	// CRUD
	@Override
	public Nurse createNurse(Nurse nurse) {
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
}

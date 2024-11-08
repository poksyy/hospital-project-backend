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

	// Implements the business logic to get a nurse by ID.
	// Calls the repository to access the database.
	@Override
	public Optional<Nurse> findById(Integer id) {
		// TODO Auto-generated method stub
		return nurseRepository.findById(id);
	}
}

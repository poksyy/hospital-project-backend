package main.hospital;

import java.util.Optional;

public interface NurseService {
	// NurseService belongs to the Service Layer.
	// It contains business logic and orchestrates operations between the controller
	// and the repository.
	// It uses NurseRepository to interact with the database and adds additional
	// rules or processes
	// like authentication, validation, or any complex logic that the repository
	// shouldn't handle directly.
	public Optional<Nurse> findByUserAndPassword(String user, String password);

	public Optional<Nurse> findByName(String name);
	
	// CRUD
	public Nurse createNurse(Nurse nurse);
	
	public Optional<Nurse> findById(Integer id);

	public Optional<Nurse> updateNurse(Integer id, Nurse updatedNurse);
	
	public void deleteById(Integer id);
	
}

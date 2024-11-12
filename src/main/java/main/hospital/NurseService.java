package main.hospital;

import java.util.Optional;

public interface NurseService {
	// NurseService belongs to the Service Layer.
	// It contains business logic and orchestrates operations between the controller
	// and the repository.
	// It uses NurseRepository to interact with the database and adds additional
	// rules or processes like authentication, validation, or any complex logic 
	// that the repository shouldn't handle directly.
	
	// Retrieves a nurse by their username and password.
	public Optional<Nurse> findByUserAndPassword(String user, String password);

	// Retrieves a nurse from the database by their name.
	public Optional<Nurse> findByName(String name);
	
	// Creates a new nurse in the database.
	public Nurse createNurse(Nurse nurse);
	
	// Retrieves a nurse by their ID.
	public Optional<Nurse> findById(Integer id);

	// Updates a nurse's information based on the provided ID.
	public Optional<Nurse> updateNurse(Integer id, Nurse updatedNurse);
	
	// Deletes a nurse by their ID.
	public void deleteById(Integer id);
}

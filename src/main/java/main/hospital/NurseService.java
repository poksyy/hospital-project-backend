package main.hospital;

import java.util.Optional;

public interface NurseService {
	// NurseService belongs to the Service Layer.
	// It contains business logic and orchestrates operations between the controller
	// and the repository.
	// It uses NurseRepository to interact with the database and adds additional
	// rules or processes like authentication, validation, or any complex logic 
	// that the repository shouldn't handle directly.
	
	// This method retrieves a nurse by their username and password.
	public Optional<Nurse> findByUserAndPassword(String user, String password);

	// This method retrieves a nurse from the database by their name.
	public Optional<Nurse> findByName(String name);
	
	// This method creates a new nurse in the database.
	public Nurse createNurse(Nurse nurse);
	
	// This method retrieves a nurse by their ID.
	public Optional<Nurse> findById(Integer id);

	// This method updates a nurse's information based on the provided ID.
	public Optional<Nurse> updateNurse(Integer id, Nurse updatedNurse);
	
	// This method deletes a nurse by their ID.
	public void deleteById(Integer id);
}

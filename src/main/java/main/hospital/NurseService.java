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

	// Find all nurses information.
	public Iterable<Nurse> findAllNurses();
	
	// Retrieves a nurse from the database by their name.
	public Optional<Nurse> findByName(String name);
	
	// Creates a new nurse in the database.
	public Nurse registerNurse(Nurse nurse);
	
	// Updates the all the Nurse information.
    public Nurse updateNurseInformation(Integer id, Nurse updatedNurse);
	
	// Updates the Nurse user and name in the database.
	public Nurse updateNameAndUsername (Nurse nurse);
	
	// Updates the Nurse password in the database.
	public Nurse updatePassword (Nurse nurse);
	
	// Retrieves a nurse by their ID.
	public Optional<Nurse> findById(Integer id);

	// Updates a nurse's information based on the provided ID.
	public Optional<Nurse> updateNurse(Integer id, Nurse updatedNurse);
	
	// Deletes a nurse by their ID.
	public void deleteById(Integer id);
	
    // Save a nurse profile image
    public Nurse saveProfileImage(Integer id, byte[] image);

    // Retrieve a nurse profile image
    public byte[] getProfileImage(Integer id);
    
    // Retrieve name availability
    public boolean isUserAvailable(String user);
}

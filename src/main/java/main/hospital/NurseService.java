package main.hospital;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

public interface NurseService extends CrudRepository<Nurse, Integer> {
    // NurseService belongs to the Service Layer.
    // It contains business logic and orchestrates operations between the controller and the repository.
    // It uses NurseRepository to interact with the database and adds additional rules or processes
    // like authentication, validation, or any complex logic that the repository shouldn't handle directly.
    
	public boolean LoginAuthentication(String user, String password);
		
	public ResponseEntity<Nurse> findByName(String name);
	
	Iterable<Nurse> findAll();
}

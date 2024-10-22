package main.hospital;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

public interface NurseService extends CrudRepository<Nurse, Integer> {
    
	public boolean LoginAuthentication(String user, String password);
		
	public ResponseEntity<Nurse> findByName(String name);
	
	Iterable<Nurse> findAll();
}

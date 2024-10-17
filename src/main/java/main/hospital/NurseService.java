package main.hospital;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

public interface NurseService extends CrudRepository<Nurse, Integer> {
    
	public boolean LoginAuthentication(String username, String password);
	
	public List<Nurse> getNursesInformation();
	
	public ResponseEntity<Nurse> findByName(String name);
}


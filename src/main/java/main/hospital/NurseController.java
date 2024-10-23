package main.hospital;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/nurse")
public class NurseController {
    
    private final NurseService nurseService;

    @Autowired
    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Nurse nurseLogin) {
		boolean isAuthenticated = nurseService.LoginAuthentication(nurseLogin.getUser(), nurseLogin.getPassword());
		return isAuthenticated ? ResponseEntity.ok("Welcome to the application!")
				: ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect login.");
	}

	// This method is mapped to the HTTP GET request at the "/index" endpoint.
	// It retrieves a list of all nurses from the database.
    @GetMapping("/index")
    public Iterable<Nurse> getAll() {
        return nurseService.findAll();
    }

	@GetMapping("/name/{name}")	
	public ResponseEntity<Nurse> findByName(@PathVariable String name) {
		return nurseService.findByName(name);
	}
}
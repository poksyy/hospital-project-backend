package main.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nurse")
public class NurseController {

	private NurseServiceImpl nurseService;

	@Autowired
	public NurseController(NurseServiceImpl nurseService) {
		this.nurseService = nurseService;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Nurse nurseLogin) {
		boolean isAuthenticated = nurseService.LoginAuthentication(nurseLogin.getUsername(), nurseLogin.getPassword());
		return isAuthenticated ? ResponseEntity.ok("Welcome to the application!")
				: ResponseEntity.status(401).body("Incorrect login.");
	}
}
package main.hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/nurse")
public class NurseController {
	private final NurseRepository nurseRepository;
	private final NurseService nurseService;

	@Autowired
	public NurseController(NurseService nurseService, NurseRepository nurseRepository) {
		this.nurseRepository = nurseRepository;
		this.nurseService = nurseService;
	}

	// Handles user authentication by verifying the provided username and password.
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Nurse loginRequest) {
		Optional<Nurse> nurse = nurseService.findByUserAndPassword(loginRequest.getUser(), loginRequest.getPassword());
		if (nurse.isPresent()) {
			return ResponseEntity.ok("Welcome to the application!");
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect login.");
		}
	}

	// Handles HTTP GET request at the "/index" endpoint.
	// It retrieves a list of all nurses from the database.
	@GetMapping("/index")
	public ResponseEntity<List<Nurse>> getAll() {
		List<Nurse> nurses = new ArrayList<>();
		nurseRepository.findAll().forEach(nurses::add);
		return ResponseEntity.ok(nurses);
	}

	// Handles HTTP GET request at "/name/{name}" endpoint.
	// It retrieves a nurse from the database by their name.
	@GetMapping("/name/{name}")
	public ResponseEntity<Nurse> findByName(@PathVariable String name) {
		Optional<Nurse> nurse = nurseService.findByName(name);
		if (nurse.isPresent()) {
			return ResponseEntity.ok(nurse.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	// Handles HTTP POST request at "/create" endpoint.
	// It creates a new nurse in the database.
	@PostMapping("/create")
	public ResponseEntity<Nurse> createNurse(@RequestBody Nurse nurse) {
		nurse.setId(null); // Ensure the ID is null for new entries.
		Nurse savedNurse = nurseRepository.save(nurse);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedNurse);
	}

	// Handles HTTP GET request at "/find/{id}" endpoint.
	// It retrieves a nurse by ID.
	@GetMapping("/find/{id}")
	public ResponseEntity<Nurse> readNurse(@PathVariable Integer id) {
		Optional<Nurse> nurseAvailable = nurseService.findById(id);
		if (nurseAvailable.isPresent()) {
			return ResponseEntity.ok(nurseAvailable.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Hndles HTTP POST request at "/update/{id}" endpoint.
	// It updates a nurse's information based on provided ID.
	@PostMapping("/update/{id}")
	public ResponseEntity<Nurse> updateNurse(@PathVariable Integer id, @RequestBody Nurse updatedNurse) {
		Optional<Nurse> existingNurse = nurseService.findById(id);
		if (existingNurse.isPresent()) {
			Nurse nurse = existingNurse.get();
			// Update only fields that are provided and non-null.
			if (updatedNurse.getName() != null) {
				nurse.setName(updatedNurse.getName());
			}
			if (updatedNurse.getUser() != null) {
				nurse.setUser(updatedNurse.getUser());
			}
			if (updatedNurse.getPassword() != null) {
				nurse.setPassword(updatedNurse.getPassword());
			}
			// Save the updated nurse to the database.
			Nurse savedNurse = nurseRepository.save(nurse);
			return ResponseEntity.ok(savedNurse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	// Handles HTTP DELETE request at "/delete/{id}" endpoint.
	// It deletes a nurse by ID.
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteNurse(@PathVariable Integer id) {
		if (nurseRepository.existsById(id)) {
			nurseService.deleteById(id);
			return ResponseEntity.ok("Nurse deleted successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nurse not found");
		}
	}
}

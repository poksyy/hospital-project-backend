package main.hospital;

import org.springframework.http.MediaType;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/hospital")
public class NurseController {

	private final NurseRepository nurseRepository;
	private final NurseService nurseService;

	@Autowired
	public NurseController(NurseService nurseService, NurseRepository nurseRepository) {

		this.nurseRepository = nurseRepository;
		this.nurseService = nurseService;
	}

	// Handles HTTP POST request at the "/login" endpoint.
	// It provides a login to the Nurse account.
	@PostMapping("/login")
	public ResponseEntity<Nurse> login(@RequestBody Nurse loginRequest) {

		Optional<Nurse> nurse = nurseService.findByUserAndPassword(loginRequest.getUser(), loginRequest.getPassword());

		if (nurse.isPresent()) {

			return ResponseEntity.ok(nurse.get());

		} else {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	// Handles HTTP POST request at "/register" endpoint.
	// It creates a new nurse in the database.
	@PostMapping("/register")
	public ResponseEntity<Object> createNurse(@RequestBody Nurse nurse) {

		try {

			nurse.setId(null);
			Nurse newRegisteredNurse = nurseService.registerNurse(nurse);

			return ResponseEntity.status(HttpStatus.CREATED).body(newRegisteredNurse);

		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
		}
	}

	// Handles HTTP GET request at the "/nurses" endpoint.
	// It retrieves a list of all nurses from the database.
	@GetMapping("/nurses")

	public ResponseEntity<List<Nurse>> getAllNurses() {

		List<Nurse> nurses = new ArrayList<>();

		nurseService.findAllNurses().forEach(nurses::add);

		return ResponseEntity.ok(nurses);
	}

	// Handles HTTP GET request at "/nurses{name}" endpoint.
	// It retrieves a nurse from the database by their name.
	@GetMapping("/nurses/{name}/name")
	public ResponseEntity<Nurse> getNurseByName(@PathVariable String name) {

		Optional<Nurse> nurse = nurseService.findByName(name);

		if (nurse.isPresent()) {

			return ResponseEntity.ok(nurse.get());

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	// Handles HTTP GET request at "/profile/{id}" endpoint.
	// It retrieves a nurse by ID.
	@GetMapping("/nurses/{id}")
	public ResponseEntity<Nurse> getNurseById(@PathVariable Integer id) {

		Optional<Nurse> nurseAvailable = nurseService.findById(id);

		if (nurseAvailable.isPresent()) {
			return ResponseEntity.ok(nurseAvailable.get());

		} else {

			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/nurses/{id}")
	public ResponseEntity<Object> updateNurse(@PathVariable Integer id, @RequestBody Nurse updatedNurse) {

		try {

			Nurse updatedNurseData = nurseService.updateNurseInformation(id, updatedNurse);
			return ResponseEntity.ok(updatedNurseData);

		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
		}
	}

	// Handles HTTP POST request at "nurses/{id}/profile" endpoint.
	// It updates a nurse's information based on provided ID.
	@PutMapping("/nurses/{id}/profile")
	public ResponseEntity<Nurse> updateNurseProfile(@PathVariable Integer id, @RequestBody Nurse updatedNurse) {

		try {

			Optional<Nurse> existingNurse = nurseService.findById(id);
			if (existingNurse.isPresent()) {
				Nurse nurse = existingNurse.get();

				// Update the fields
				nurse.setName(updatedNurse.getName());
				nurse.setUser(updatedNurse.getUser());

				// Save the updated nurse to the database
				Nurse savedNurse = nurseService.updateNameAndUsername(nurse);
				return ResponseEntity.ok(savedNurse);

			} else {
				
				// HTTP 404 NOT FOUND
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

		} catch (DataIntegrityViolationException e) {

			// Manage username duplication.
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException
					&& e.getMessage().contains("nurse.user")) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}

			// Manage HTTP BadRequest.
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	// Handles HTTP POST request at "nurses/{id}/password" endpoint.
	// It updates a nurse's password based on provided ID.
	@PutMapping("/nurses/{id}/password")
	public ResponseEntity<Object> updateNursePassword(@PathVariable Integer id, @RequestBody Nurse updatedNurse) {

		try {

			Nurse updatedNurseData = nurseService.updateNurseInformation(id, updatedNurse);
			return ResponseEntity.ok(updatedNurseData);

		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
		}
	}

	// Handles HTTP DELETE request at "/nurses/{id}" endpoint.
	// It deletes a nurse by ID.
	@DeleteMapping("/nurses/{id}")
	public ResponseEntity<String> deleteNurse(@PathVariable Integer id) {

		if (nurseRepository.existsById(id)) {
			nurseService.deleteById(id);
			return ResponseEntity.ok("Nurse deleted successfully");

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nurse not found");
		}
	}

	// Handles HTTP POST request at "/nurses/{id}/upload-image" endpoint.
	// It uploads a nurse image by ID.
	@PostMapping("/nurses/{id}/upload-image")
	public ResponseEntity<String> uploadNurseImage(@PathVariable Integer id,
			@RequestParam("image") MultipartFile imageFile) {

		try {

			byte[] imageBytes = imageFile.getBytes();
			String contentType = getContentType(imageBytes);

			// Validate image type (jpeg/png).
			if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
				return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
						.body("Only PNG and JPEG images are allowed.");
			}

			Nurse updatedNurse = nurseService.saveProfileImage(id, imageBytes);

			if (updatedNurse != null) {
				return ResponseEntity.ok("Image uploaded successfully.");

			} else {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nurse not found.");
			}

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
		}
	}

	// Handles HTTP GET request at "/nurses/{id}/image" endpoint.
	// It retrieves a nurse image by ID.
	@GetMapping("/nurses/{id}/image")
	public ResponseEntity<byte[]> getNurseImage(@PathVariable Integer id) {

		byte[] image = nurseService.getProfileImage(id);

		// Return 404 if image not found
		if (image == null)
			return ResponseEntity.notFound().build();

		// Determine content type based on image bytes.
		String contentType = getContentType(image);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(image);
	}

	// Method to check if the image is PNG or JPEG based on bytes.
	private String getContentType(byte[] image) {

		// Validation for the JPEG format (starts with 0xFF 0xD8 and ends with 0xFF
		// 0xD9).
		if (image[0] == (byte) 0xFF && image[1] == (byte) 0xD8) {
			return "image/jpeg";
		}
		// Validation for the PNG format (starts with 0x89 0x50 0x4E 0x47).
		else if (image[0] == (byte) 0x89 && image[1] == (byte) 0x50 && image[2] == (byte) 0x4E
				&& image[3] == (byte) 0x47) {
			
			return "image/png";
		}
		
		return "application/octet-stream";
	}
}
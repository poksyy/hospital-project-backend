package main.hospital;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NurseController {
	
	private final NurseServiceImpl nurseService;

    public NurseController(NurseServiceImpl nurseService) {
        this.nurseService = nurseService;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Nurse> findByName(@PathVariable String name) {
        return nurseService.findByName(name);
    }
	
}

package com.hospital.hospital;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/nurse") 
public class NurseController {
    
    private final NurseServiceImpl nurseService;

    @Autowired
    public NurseController(NurseServiceImpl nurseService) {
        this.nurseService = nurseService;
    }

    @GetMapping("/index")
    public List<Nurse> getAll() {
        return nurseService.getNursesInformation();
    }
}

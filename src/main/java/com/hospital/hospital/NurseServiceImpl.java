package com.hospital.hospital;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class NurseServiceImpl {

    private List<Nurse> nurses = new ArrayList<>();

    public NurseServiceImpl() {
        nurses.add(new Nurse("Pau", "pl2024769", "pau1234"));
        nurses.add(new Nurse("Dylan", "pl2024379", "dylan1234"));
        nurses.add(new Nurse("Cristian", "pl2024768", "cristian1234"));
    }

    public List<Nurse> getNursesInformation() {
        return nurses;
    }
}

package com.hospital.hospital;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import main.hospital.Nurse;
import main.hospital.NurseController;
import main.hospital.NurseRepository;
import main.hospital.NurseService;
			
class NurseControllerTMock {

	
	
	@Mock
	NurseRepository nurseRepository;
	@Mock
	NurseService nurseService;
	
	@InjectMocks
    private NurseController nurseController;
	

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
    void testLogin_Success() {
        Nurse nurse = new Nurse("testUser", "testPassword");
        when(nurseService.findByUserAndPassword("testUser", "testPassword")).thenReturn(Optional.of(nurse));

        ResponseEntity<Nurse> response = nurseController.login(nurse);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(nurse, response.getBody()); 
    }

    @Test
    void testLogin_Failure() {
        Nurse nurse = new Nurse("wrongUser", "wrongPassword");
        when(nurseService.findByUserAndPassword("wrongUser", "wrongPassword")).thenReturn(Optional.empty());

        ResponseEntity<Nurse> response = nurseController.login(nurse);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetAllNurses() {
        Nurse nurse1 = new Nurse("nurse1", "password1");
        Nurse nurse2 = new Nurse("nurse2", "password2");
        List<Nurse> nurseList = Arrays.asList(nurse1, nurse2);
        when(nurseService.findAllNurses()).thenReturn(nurseList);

        ResponseEntity<List<Nurse>> response = nurseController.getAllNurses();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(nurseList, response.getBody());
    }

    @Test
    void testFindByName_Success() {
        Nurse nurse = new Nurse("nurseName", "password");
        when(nurseService.findByName("nurseName")).thenReturn(Optional.of(nurse));

        ResponseEntity<Nurse> response = nurseController.getNurseByName("nurseName");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(nurse, response.getBody());
    }

    @Test
    void testFindByName_NotFound() {
        when(nurseService.findByName("unknownName")).thenReturn(Optional.empty());

        ResponseEntity<Nurse> response = nurseController.getNurseByName("unknownName");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateNurse() {
        Nurse nurse = new Nurse("newNurse", "newPassword");
        when(nurseService.registerNurse(nurse)).thenReturn(nurse);

        ResponseEntity<Nurse> response = nurseController.createNurse(nurse);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(nurse, response.getBody());
    }
    
    @Test
    void testCreateNurse_Failure() {
        Nurse nurse = new Nurse("newNurse", "newPassword");

        when(nurseService.registerNurse(nurse)).thenThrow(new RuntimeException("Error creating Nurse"));

        ResponseEntity<Nurse> response = nurseController.createNurse(nurse);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateNurse_Success() {
        Nurse existingNurse = new Nurse("existingName", "existingPassword");
        existingNurse.setId(1);
        Nurse updatedNurse = new Nurse("updatedName", "updatedPassword");

        when(nurseService.findById(1)).thenReturn(Optional.of(existingNurse));
        when(nurseService.registerNurse(existingNurse)).thenReturn(existingNurse);

        ResponseEntity<Nurse> response = nurseController.updateNurse(1, updatedNurse);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingNurse, response.getBody());
    }

    @Test
    void testUpdateNurse_NotFound() {
        Nurse updatedNurse = new Nurse("updatedName", "updatedPassword");

        when(nurseRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Nurse> response = nurseController.updateNurse(1, updatedNurse);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testReadNurse_Success() {
        Nurse nurse = new Nurse("nurseName", "nursePassword");
        when(nurseService.findById(1)).thenReturn(Optional.of(nurse));

        ResponseEntity<Nurse> response = nurseController.getNurseById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(nurse, response.getBody());
    }

    @Test
    void testReadNurse_NotFound() {
        when(nurseService.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Nurse> response = nurseController.getNurseById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteNurse_Success() {
        when(nurseRepository.existsById(1)).thenReturn(true);

        ResponseEntity<String> response = nurseController.deleteNurse(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nurse deleted successfully", response.getBody());
    }

    @Test
    void testDeleteNurse_NotFound() {
        when(nurseRepository.existsById(1)).thenReturn(false);

        ResponseEntity<String> response = nurseController.deleteNurse(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Nurse not found", response.getBody());
    }

}

package main.hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NurseServiceImpl implements NurseService {
	
	private NurseService nurseService;

	private List<Nurse> nurses = new ArrayList<>();

	// Constructor that initializes instances of Nurse to the ArrayList nurses.
//	public NurseServiceImpl() {
//		// // We need to pass the same parameters that are expected by the Nurse
//		// constructor.
//		nurses.add(new Nurse("Pau", "pl2024769", "pau1234"));
//		nurses.add(new Nurse("Dylan", "pl2024379", "dylan1234"));
//		nurses.add(new Nurse("Cristian", "pl2024768", "cristian1234"));
//
//	}

	public boolean LoginAuthentication(String username, String password) {

		// Loop to iterate the List 'nurses'.
		for (int i = 0; i < nurses.size(); i++) {
			// This allows us to access the variables and methods from Nurse class, and
			// access us to do operations on each Nurse object in the list.
			Nurse nurse = nurses.get(i);

			// Check if credentials are correct.
			if (nurse.getUsername().equals(username) && nurse.getPassword().equals(password)) {
				System.out.println("Welcome to the application!.");
				return true;
			}
		}

		System.err.println("Incorrect login.");
		return false;
	}

	public List<Nurse> getNursesInformation() {
		return nurses;
	}

	public ResponseEntity<Nurse> findByName(String name) {
		for (Nurse nurse : nurses) {
			System.out.println(nurse);
			if (name.equals(nurse.getName())) {
				return ResponseEntity.ok(nurse);
			}
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public <S extends Nurse> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Nurse> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Nurse> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Nurse> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Nurse> findAllById(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Nurse entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Nurse> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}

package main.hospital;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NurseRepository extends CrudRepository<Nurse, Integer> {

	Optional<Nurse> findByUserAndPassword(String user, String password);

    Optional<Nurse> findByName(String name);
    
    boolean existsByUser(String user);
    }
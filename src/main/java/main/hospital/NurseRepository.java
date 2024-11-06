package main.hospital;

import org.springframework.data.repository.CrudRepository;

public interface NurseRepository extends CrudRepository<Nurse, Integer> {
    // NurseRepository belongs to the Data Access Layer (DAO).
    // Its primary responsibility is to interact directly with the database to perform CRUD operations.
    // It does not contain business logic, just methods that allow fetching, saving, updating, or deleting entities.
}


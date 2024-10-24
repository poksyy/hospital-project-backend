package main.hospital;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NurseServiceImpl implements NurseService {

	// Instance that will be used to create sessions and interact with the database. 	
	private SessionFactory sessionFactory;

	// Autowired instance for the CRUD.
	@Autowired
	private NurseRepository nurseRepository;

	public NurseServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean LoginAuthentication(String user, String password) {
		
		// We are getting a session to interact with the database.
		Session session = sessionFactory.openSession();
		// We start a transaction instance to guarantee security of the database operations.
		Transaction transaction = null;
		// We create an instance of Nurse.
		Nurse nurse = null;

		try {
			// Start transaction.
			transaction = session.beginTransaction();
			// HQL QUERY to search with user and name.
			String hql = "FROM Nurse WHERE user = :user AND password = :password";
			// We create the query waiting for a Nurse object response.
			Query<Nurse> query = session.createQuery(hql, Nurse.class);
			// Value of the query  for the user.
			query.setParameter("user", user);
			// Value of the query for the password.
			query.setParameter("password", password);

			// Nurse object has only one(1) unique result.
			nurse = query.uniqueResult();
			// Transaction starts if everything is ok.
			transaction.commit();
		} catch (Exception TransactionError) {
			if (transaction != null) {
				transaction.rollback();
			}
			TransactionError.printStackTrace();
		// The session always ends regardless of whether the try or catch was executed.
		} finally {
			session.close();
		}
		// Query matched.
		if (nurse != null) {
			System.out.println("Welcome to the application!.");
			return true;
		// Query did not match.
		} else {
			System.out.println("Invalid credentials.");
			return false;
		}
	}

	public List<Nurse> getNursesInformation() {
		return null;
	}

	public ResponseEntity<Nurse> findByName(String name) {
		// Open a Hibernate session.
	    Session session = sessionFactory.openSession();
	    Transaction transaction = null;
	    // Save the nurse selected to this variable.
	    Nurse nurse = null;
	    
	    try {
	    	// Start transaction.
	        transaction = session.beginTransaction();
	        // Create an HQL query to search nurse by name.
	        String hql = "FROM Nurse WHERE name = :name";
	        Query<Nurse> query = session.createQuery(hql, Nurse.class);
	        query.setParameter("name", name);
	        // Return only 1 result. If it returns more than 1 result it will launch an exception.
	        nurse = query.uniqueResult();
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	        // If there's an error do a rollback.
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }

	    // If nurse is found.
	    if (nurse != null) {
	        System.out.println(nurse);
	        return ResponseEntity.ok(nurse);
	    } else { 
	    // If nurse is NOT found.
	        return ResponseEntity.notFound().build();
	    }
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

	// Overrides findAll() from NurseService.
	// Retrieves all nurse records by calling nurseRepository's findAll(), 
	// which extends CrudRepository.
	@Override
	public Iterable<Nurse> findAll() {
		return nurseRepository.findAll();
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
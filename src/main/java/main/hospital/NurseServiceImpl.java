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

	 private SessionFactory sessionFactory;
   
   @Autowired
   private NurseRepository nurseRepository; 

    public NurseServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean LoginAuthentication(String user, String password) { 
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Nurse nurse = null;

        try {
            transaction = session.beginTransaction();
            String hql = "FROM Nurse WHERE user = :user AND password = :password"; 
            Query<Nurse> query = session.createQuery(hql, Nurse.class);
            query.setParameter("user", user); 
            query.setParameter("password", password);

            nurse = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        if (nurse != null) {
            System.out.println("Welcome to the application!.");
            return true;
        } else {
            System.out.println("Invalid credentials.");
            return false;
        }
    }

	public List<Nurse> getNursesInformation() {
		return null;
	}

	public ResponseEntity<Nurse> findByName(String name) {
	    Session session = sessionFactory.openSession(); // Open a Hibernate session.
	    Transaction transaction = null;
	    Nurse nurse = null; // Save the nurse selected to this variable.

	    try {
	    	// Start transaction.
	        transaction = session.beginTransaction();
	        String hql = "FROM Nurse WHERE name = :name"; // Create an HQL query to search nurse by name.
	        Query<Nurse> query = session.createQuery(hql, Nurse.class);
	        query.setParameter("name", name);

	        nurse = query.uniqueResult(); // Return only 1 result. If it returns more than 1 result it will launch an exception.
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback(); // If there's an error do a rollback.
	        }
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }

	    
	    if (nurse != null) { // If nurse is found.
	        System.out.println(nurse);
	        return ResponseEntity.ok(nurse);
	    } else { // If nurse is NOT found.
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

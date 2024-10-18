package main.hospital;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Nurse {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	private String user;
	private String password;

	// Constructor.
	public Nurse(String name, String user, String password) {
		this.name = name;
		this.user = user;
		this.password = password;
	}
	
	// This constructor is needed for the deserialization (convert the JSON to a Java Object).
	public Nurse() {
	    
	}
	
	public Nurse (String user, String password) {
		this.user = user;
		this.password = password;
	}
	
	// Getters and setters.
	public String getName() {
		return name;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

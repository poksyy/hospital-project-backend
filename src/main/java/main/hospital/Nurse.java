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
	private String username;
	private String password;

	// Constructor.
	public Nurse(String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
	}
	
	// This constructor is needed for the deserialization (convert the JSON to a Java Object).
	public Nurse() {
	    
	}
	
	public Nurse (String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	// Getters and setters.
	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

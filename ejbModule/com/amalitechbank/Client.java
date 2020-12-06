package com.amalitechbank;

import javax.enterprise.inject.Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Model
@Entity
@Table(name="Client")
public class Client {

	@Id
	@Column(name="clientID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int clientID;
	
	@NotBlank
	@Column(name="contact")
	private String contact;
	
	@Email
	@Column(name="email")
	private String email;
	
	@NotBlank
	@Column(name="firstname")
	private String firstname;
	
	@NotBlank
	@Column(name="lastname")
	private String lastname;
	
	public Client() {
		
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	
}

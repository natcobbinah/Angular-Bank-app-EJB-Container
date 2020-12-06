package com.amalitechbank;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Model
@Entity
@Table(name = "Bankaccount")
public class Bankaccount {

	@Id
	@Column(name = "clientID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clientID; // relationship btwn bank and client

	@Column(name = "accountStatus")
	private String accountStatus;

	@Column(name = "balance")
	private BigDecimal balance;

	@Temporal(TemporalType.DATE)
	private Date createdAt;

	//@Column(name = "accountNumber")
	//private String accountNumber;

	// bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name = "accountNumber")
	private Client client;

	// bi-directional many-to-one association to Operation
	@OneToMany(mappedBy = "bankaccount")
	private List<Operation> operations;
	// --------------------------------------------------

	public Bankaccount() {

	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

//	public String getAccountNumber() {
//		return accountNumber;
//	}
//
//	public void setAccountNumber(String accountNumber) {
//		this.accountNumber = accountNumber;
//	}

	// code stub between bankaccount and transactional operations
	public List<Operation> getOperations() {
		return this.operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public Operation addOperation(Operation operation) {
		getOperations().add(operation);
		operation.setBankaccount(this);

		return operation;
	}

	public Operation removeOperation(Operation operation) {
		getOperations().remove(operation);
		operation.setBankaccount(null);

		return operation;
	}
}

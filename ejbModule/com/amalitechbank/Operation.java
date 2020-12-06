package com.amalitechbank;

import java.math.BigDecimal;
import java.util.Date;

import javax.enterprise.inject.Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Model
@Entity
@Table(name = "operations")
public class Operation {

	@Id
	@Column(name = "operationid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int operationid;

	@Temporal(TemporalType.DATE)
	@Column(name = "doneAt")
	private Date doneAt;

	@Column(name = "operationAmount")
	private BigDecimal operationAmount;

	@Column(name = "operationType")
	private OperationType operationType;

	// bi-directional many-to-one association to Bankaccount
	@ManyToOne
	@JoinColumn(name = "accountNumber", referencedColumnName = "accountNumber")
	private Bankaccount bankaccount;

	public Operation() {

	}

	public int getOperationid() {
		return operationid;
	}

	public void setOperationid(int operationid) {
		this.operationid = operationid;
	}

	public Date getDoneAt() {
		return doneAt;
	}

	public void setDoneAt(Date doneAt) {
		this.doneAt = doneAt;
	}

	public BigDecimal getOperationAmount() {
		return operationAmount;
	}

	public void setOperationAmount(BigDecimal operationAmount) {
		this.operationAmount = operationAmount;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	
	//operation and bank relationship
	public Bankaccount getBankaccount() {
		return this.bankaccount;
	}

	public void setBankaccount(Bankaccount bankaccount) {
		this.bankaccount = bankaccount;
	}


}

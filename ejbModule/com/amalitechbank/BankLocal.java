package com.amalitechbank;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;


@Local
public interface BankLocal {
	
	List<Bankaccount> getBankAccounts();
	
	void addBankAccount(Bankaccount bankaccount);
	
	List<Client> getClients();
	
	void addClient(Client client);
	
	List<Operation> getOperations();
	
	void addOperation(Operation operation);
	
	List<Operation> getAllTransactions(String type,Date datefrom, Date dateto);
	
	List<Bankaccount> listAllAccounts(Long accountNumber);
	
	boolean closeAccount(Long number);
	 
	boolean deposit(BigDecimal amount, long accountNumber);
	
	boolean withdraw(BigDecimal amount, long accountNumber);
	
	boolean transfer(BigDecimal amount,long accountNumber1, long accountNumber2);

}

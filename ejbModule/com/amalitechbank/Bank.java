package com.amalitechbank;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Session Bean implementation class Bank
 */
@Singleton
@LocalBean
public class Bank implements BankLocal {

	@PersistenceContext
	private EntityManager entityManager;

	private static BigDecimal MINIMUM_ACCOUNT_BALANCE = new BigDecimal(100);
	
	/**
	 * Default constructor.
	 */
	public Bank() {

	}

	@Override
	public List<Bankaccount> getBankAccounts() {
		return this.entityManager.createQuery("select b from Bankaccount b", Bankaccount.class).getResultList();
	}

	@Override
	public void addBankAccount(Bankaccount bankaccount) {
		this.entityManager.persist(bankaccount);

	}

	@Override
	public List<Client> getClients() {
		return this.entityManager.createQuery("select c from Client c", Client.class).getResultList();
	}

	@Override
	public void addClient(Client client) {
		this.entityManager.persist(client);
	}

	@Override
	public List<Operation> getOperations() {
		return this.entityManager.createQuery("select p from Operation", Operation.class).getResultList();
	}

	@Override
	public void addOperation(Operation operation) {
		this.entityManager.persist(operation);
	}

	@Override
	public List<Operation> getAllTransactions(String type, Date datefrom, Date dateto) {
		return this.entityManager
				.createQuery("select o from Operation o where o.doneAt between :datefrom and :dateto", Operation.class)
				.getResultList();
	}

	@Override
	public List<Bankaccount> listAllAccounts(Long accountNumber) {
		return this.entityManager
				.createQuery("select b from Bankaccount b where b.accountNumber =:accountNumber", Bankaccount.class)
				.getResultList();
	}

	@Override
	public boolean closeAccount(Long number) {
		String queryString = "Select count(b.accountNumber) from Bankaccount b where b.accountNumber =:number";
		TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
		boolean result = false;
		query.setParameter("accountNumber", Long.toString(number));
		query.setParameter("accountStatus", "CLOSED");

		try {
			long accountId = query.getSingleResult();
			if (accountId > 0) {
				return true;
			}
		} catch (Exception e) {
			result = false;
		} finally {
			entityManager.close();
		}
		return result;
	}

	@Override
	public boolean deposit(BigDecimal amount, long accountNumber) {
		String queryString = "select b from Bankaccount b where b.accountNumber =: accountNumber";

		TypedQuery<Bankaccount> bankaccount = entityManager.createQuery(queryString, Bankaccount.class);
		List<Bankaccount> accounts = null;
		accounts = bankaccount.getResultList();
		BigDecimal balance = null;
		for (Bankaccount account : accounts) {
			balance = account.getBalance();
			break;
		}

		BigDecimal newBalance = balance.add(amount);
		bankaccount.setParameter("balance", newBalance);

		return true;
	}

	@Override
	public boolean withdraw(BigDecimal amount, long accountNumber) {
		String queryString = "select b from Bankaccount b where b.accountNumber =: accountNumber";
		
		TypedQuery<Bankaccount> bankaccount = entityManager.createQuery(queryString,Bankaccount.class);
		List<Bankaccount> accounts = null;
		accounts = bankaccount.getResultList();
		
		BigDecimal balance = null;
		for(Bankaccount account: accounts) {
			 balance = account.getBalance();
			break;
		}
		if(balance.doubleValue() < MINIMUM_ACCOUNT_BALANCE.doubleValue()) {
			return  false;
		}else {
			BigDecimal remainingBalance = balance.subtract(amount);
			bankaccount.setParameter("balance", remainingBalance);
		}
		return true;
	}

	@Override
	public boolean transfer(BigDecimal amount, long accountNumber1, long accountNumber2) {
		String fromqueryString = "select b from Bankaccount b where b.accountNumber =: accountNumber1";
		String toqueryString = "select b from Bankaccount b where b.accountNumber =: accountNumber2";
		
		TypedQuery<Bankaccount> frombankaccount = entityManager.createQuery(fromqueryString,Bankaccount.class);
		TypedQuery<Bankaccount> tobankaccount = entityManager.createQuery(toqueryString,Bankaccount.class);
		
		List<Bankaccount> fromaccount = null;
		fromaccount = frombankaccount.getResultList();
		BigDecimal frombalance = null;
		for(Bankaccount account: fromaccount) {
			 frombalance = account.getBalance();
			break;
		}
		
		List<Bankaccount> toaccount = null;
		toaccount = tobankaccount.getResultList();
		BigDecimal tobalance = null;
		for(Bankaccount account: toaccount) {
			 tobalance = account.getBalance();
			break;
		}
		
		if( (frombalance.doubleValue() - amount.doubleValue()) < MINIMUM_ACCOUNT_BALANCE.doubleValue()) {
			return false;
		}else {
			tobalance = tobalance.add(amount);
			frombalance = frombalance.subtract(amount);
			
			frombankaccount.setParameter("balance", frombalance);
			tobankaccount.setParameter("balance", tobalance);
		}
		return true;
	}

}

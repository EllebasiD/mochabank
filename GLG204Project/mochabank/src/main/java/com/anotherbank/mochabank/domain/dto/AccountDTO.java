package com.anotherbank.mochabank.domain.dto;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
* This class follows the Data Transfer Object design pattern.
* It is a client view of an Account. This class only
* transfers data from a distant service to a client.
* 
*  @author Isabelle Deligniere
*/

@SuppressWarnings("serial")
public final class AccountDTO implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
	private Long id;
	private String accountName;
	private String bankId;
	private String bankName;
	private long balance;
	private String username;
    //private Set<CustomerDTO> customersList = new LinkedHashSet <CustomerDTO>();
    private Set<TransactionDTO> transactionsList = new LinkedHashSet <TransactionDTO>();
    // ======================================
    // =            Constructors            =
    // ======================================
    public AccountDTO () {}
    
    public AccountDTO(final String accountName, final String bankId, final String bankName, final long balance) {
    	setAccountName(accountName);
    	setBankId(bankId);
		setBankName(bankName);
		setBalance(balance);
		this.transactionsList = new LinkedHashSet <TransactionDTO>();
	}
    
    public AccountDTO(final String accountName, final String bankId, final String bankName, final long balance, final String username) {
    	setAccountName(accountName);
    	setBankId(bankId);
		setBankName(bankName);
		setBalance(balance);
		setCustomerId(username);
		this.transactionsList = new LinkedHashSet <TransactionDTO>();
	}
    // ======================================
    // =         Getters and Setters        =
    // ======================================
    
    /**
     * This method sets the name of an account.
     * @param accountName	the name of an account.
     */
    public void setAccountName(final String accountName) {
    	this.accountName = accountName;
    }
    /**
     * This method gets the name of an account.
     * @return accountName the name of an account.
     */
    public String getAccountName() {
    	return accountName;
    }
    /**
     * This method sets the identifier of an Account.
     * @param id	the identifier of an Account.
     */
    public void setId(final Long id) {
    	this.id = id;
    }
    /**
     * This method gets the identifier of an Account.
     * @return id the identifier of an Account.
     */
    public Long getId() {
    	return id;
    }
    /**
     * This method sets the identifier of the acount's bank.
     * @param bankId	the identifier of the acount's bank.
     */
    public void setBankId(final String bankId) {
    	this.bankId = bankId;
    }
    /**
     * This method gets the identifier of the acount's bank.
     * @return bankId the identifier of the account's bank.
     */
    public String getBankId() {
    	return bankId;
    }
    /**
     * This method sets the name of the account's bank.
     * @param bankName	the name of the account's bank.
     */
    public void setBankName(final String bankName) {
    	this.bankName = bankName;
    }
    /**
     * This method gets the name of the acount's bank.
     * @return bankName the name of the account's bank.
     */
    public String getBankName() {
    	return bankName;
    }
    /**
     * This method sets the balance of an account.
     * @param balance	the balance of an account.
     */
    public void setBalance(final long balance) {
    	this.balance = balance;
    }
    /**
     * This method gets the balance of an account.
     * @return balance the balance of the account.
     */
    public long getBalance() {
    	return balance;
    }
    /**
     * This method sets the identifier of the customer of an account.
     * @param username	the identifier of the customer of an account.
     */
    public void setCustomerId(final String username) {
    	this.username = username;
    }
    /**
     * This method gets the identifier of the customer of an account.
     * @return username the identifier of the customer of an account.
     */
    public String getCustomerId() {
    	return username;
    }
	
    /**
     * This method sets the list of transactions of an account.
     * @param transactionsList	the list of transactions of an account.
     */
    public void setTransactionsList(final Set<TransactionDTO> transactionsList) {
    	this.transactionsList = transactionsList;
    }
    /**
     * This method gets the transactions list of an account.
     * @return transactionsList the transactions list of an account.
     */
    public Set<TransactionDTO> getTransactionsList() {
    	return transactionsList;
    }
    /**
	 * This method displays an account.
	 * @return the account.
	 */
	public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tAccount {");
        buf.append("\n\t\tId=").append(getId());
        buf.append("\\n\\t\\tAccount name=").append(getAccountName());
        buf.append("\n\t\tBank id=").append(getBankId());
        buf.append("\\n\\t\\tBank name=").append(getBankName());
        buf.append("\n\t\tCustomer Id=").append(getCustomerId());
        buf.append(",Balance=").append(getBalance());
        buf.append("\n\t}");
        return buf.toString();
    }
       
}


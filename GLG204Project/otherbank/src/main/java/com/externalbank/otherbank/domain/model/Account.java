package com.externalbank.otherbank.domain.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.externalbank.otherbank.authentication.domain.model.Customer;
import com.externalbank.otherbank.exception.CheckException;

/**
* This class represents an account for the Other Bank company.
* 
* @author Isabelle Deligniere
*/

@SuppressWarnings("serial")
@Entity
@Table(name = "T_ACCOUNT") 
public final  class Account implements Serializable{
	// ======================================
    // =             Attributes             =
    // ======================================
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator="order_seq_gen")
	private Long id;
	private String accountName;
	private String bankId;
	private String bankName;
	private long balance;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="customer_fk")
	private Customer customer;
	//@ManyToMany
    //private Set<Customer> customersList;
	@OneToMany(targetEntity=Transaction.class, mappedBy="transactionDate", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Transaction> transactionsList = new LinkedHashSet <Transaction>();
	
	// ======================================
    // =            Constructors            =
    // ======================================
	public Account() {
		this.transactionsList = new LinkedHashSet <Transaction>();
	}
	
	public Account(final String accountName, final String bankId, final String bankName, final long balance) {
		setAccountName(accountName);
		setBankId(bankId);
		setBankName(bankName);
		setBalance(balance);
		this.transactionsList = new LinkedHashSet <Transaction>();
	}
	
	public Account(final String accountName, final String bankId, final String bankName, final long balance, final Customer customer) {
		setAccountName(accountName);
		setBankId(bankId);
		setBankName(bankName);
		setBalance(balance);
		setCustomer(customer);
		this.transactionsList = new LinkedHashSet <Transaction>();
	}
	
	// ======================================
    // =           Business methods         =
    // ======================================
	/**
     * This method checks the integrity of the object data.
     * @throws CheckException if data is invalid
     */
    public void checkData() throws CheckException {
    	if(accountName == null || "".equals(accountName))
    		throw new CheckException("Invalid account name");
    	if (bankName == null || "".equals(bankName))
            throw new CheckException("Invalid bank name");
    	if (customer == null)
            throw new CheckException("Invalid customers list ");
    }
    /**
     * This method credits the balance of the account.
     * @param amount	the amount to credit.
     */
    public boolean credit(final long amount) {
    		this.balance = this.balance + amount;    	
    	return true;
    }
    /**
     * This method debits the balance of the account.
     * @param amount	the amount to debit.
     */
    public boolean debit(final long amount) {
    		this.balance = this.balance - amount;
    	return true;
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
     * This method sets the identifier of an account.
     * @param id	the identifier of an account.
     */
    public void setId(final long id) {
    	this.id = id;
    }
    /**
     * This method gets the identifier of an account.
     * @return id the identifier of an account.
     */
    public long getId() {
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
     * This method sets the customer of an account.
     * @param customer	the customer of an account.
     */
    public void setCustomer(final Customer customer) {
    	this.customer = customer;
    }
    /**
     * This method gets the customer of an account.
     * @return customer the customer of an account.
     */
    public Customer getCustomer() {
    	return customer;
    }
    /**
     * This method sets the list of transactions of an account.
     * @param transactionsList	the list of transactions of an account.
     */
    public void setTransactionsList(final Set<Transaction> transactionsList) {
    	this.transactionsList = transactionsList;
    }
    /**
     * This method gets the transactions list of an account.
     * @return transactionsList the transactions list of an account.
     */
    public Set<Transaction> getTransactionsList() {
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
        buf.append("\n\t\tCustomer=").append(getCustomer());
        buf.append(",Balance=").append(getBalance());
        buf.append("\n\t}");
        return buf.toString();
    }

   
}

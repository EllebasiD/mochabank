package com.externalbank.otherbank.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.externalbank.otherbank.exception.CheckException;

/**
* This class represents a transaction for the Other Bank company.
* 
* @author Isabelle Deligniere
*/

@SuppressWarnings("serial")
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Transaction implements Serializable{
	// ======================================
    // =             Attributes             =
    // ======================================
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator="order_seq_gen")
	private Long id;
	private LocalDateTime transactionDate;
	private String description;
	private String type;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="accountA_fk", nullable = false)
	private Account accountA;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="accountB_fk")
	private Account accountB;
	private long amount;
	private boolean result;	
	// ======================================
    // =            Constructors            =
    // ======================================
	public Transaction() {
		this.transactionDate = LocalDateTime.now();
	}
	
	public Transaction(final String description, final String type,
			final Account accountA, final long amount, final boolean result) {
		this.transactionDate = LocalDateTime.now();
		setDescription(description);
		setType(type);
		setAccountA(accountA);
		setAmount(amount);
		setResult(result);
	}
	
	public Transaction(final String description, final String type,
			final Account accountA, final Account accountB, final long amount, final boolean result) {
		this.transactionDate = LocalDateTime.now();
		setDescription(description);
		setType(type);
		setAccountA(accountA);
		setAccountB(accountB);
		setAmount(amount);
		setResult(result);
	}
	// ======================================
    // =           Business methods         =
    // ======================================
	/**
     * This method checks the integrity of the object data.
     * @throws CheckException if data is invalid
     */
    public void checkData() throws CheckException {
    	if (description == null || "".equals(description))
            throw new CheckException("Invalid transaction's description");
    	if (type == null || "".equals(type))
            throw new CheckException("Invalid transaction's type");
    	if (accountA == null)
            throw new CheckException("Invalid transaction's account A");
    }
		// ======================================
    // =         Getters and Setters        =
    // ======================================
    /**
     * This method sets the identifier of a transaction.
     * @param id	the identifier of a transaction.
     */
    public void setId(final Long id) {
    	this.id = id;
    }
    /**
     * This method gets the identifier of a transaction.
     * @return id the identifier of a transaction.
     */
    public Long getId() {
    	return id;
    }
    /**
     * This method sets the identifier of a transaction.
     * @param transactionDate	the date of a transaction.
     */
    public void setTransactionDate(final LocalDateTime transactionDate) {
    	this.transactionDate = transactionDate;
    }
    /**
     * This method gets the date of a transaction.
     * @return transactionDate the date of a transaction.
     */
    public LocalDateTime getTransactionDate() {
    	return transactionDate;
    }
    /**
     * This method sets the description of a transaction.
     * @param description	the description of a transaction.
     */
    public void setDescription(final String description) {
    	this.description = description;
    }
    /**
     * This method gets the description of a transaction.
     * @return description the description of a transaction.
     */
    public String getDescription() {
    	return description;
    }
    /**
     * This method sets the type of a transaction.
     * @param type	the type of a transaction.
     */
    public void setType(final String type) {
    	this.type = type;
    }
    /**
     * This method gets the type of a transaction.
     * @return type the type of a transaction.
     */
    public String getType() {
    	return type;
    }
    /**
     * This method sets the accountA of a transaction.
     * @param accountA	the accountA of a transaction.
     */
    public void setAccountA(final Account accountA) {
    	this.accountA = accountA;
    }
    /**
     * This method gets the accountA of a transaction.
     * @return accountA the accountA of a transaction.
     */
    public Account getAccountA() {
    	return accountA;
    }
    /**
     * This method sets the accountB of a transaction.
     * @param accountB	the accountB of a transaction.
     */
    public void setAccountB(final Account accountB) {
    	this.accountB = accountB;
    }
    /**
     * This method gets the accountB of a transaction.
     * @return accountB the accountB of a transaction.
     */
    public Account getAccountB() {
    	return accountB;
    }
    /**
     * This method sets the amount of a transaction.
     * @param amount	the amount of a transaction.
     */
    public void setAmount(final long amount) {
    	this.amount = amount;
    }
    /**
     * This method gets the amount of a transaction.
     * @return amount the amount of a transaction.
     */
    public long getAmount() {
    	return amount;
    }
    /**
     * This method sets the result of a transaction.
     * @param result	the result of a transaction.
     */
    public void setResult(final boolean result) {
    	this.result = result;
    }
    /**
     * This method gets the result of a transaction.
     * @return id the result of a transaction.
     */
    public boolean getResult() {
    	return result;
    }    
    /**
	 * This method displays a transaction.
	 * @return the transaction.
	 */
	public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tTransaction {");
        buf.append("\n\t\tId=").append(getId());
        buf.append("\n\t\tDate=").append(getTransactionDate());
        buf.append("\\n\\t\\tDescription=").append(getDescription());
        buf.append("\\n\\t\\tType=").append(getType());
        buf.append("\n\t\tAccountA=").append(getAccountA());
        if(getAccountB() != null) {
        	buf.append("\n\t\tAccountB=").append(getAccountB());
        }
        buf.append("\n\t\tAmount=").append(getAmount());
        buf.append(",Result=").append(getResult());
        buf.append("\n\t}");
        return buf.toString();
    }
	
}

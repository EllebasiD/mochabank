package com.externalbank.otherbank.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.externalbank.otherbank.exception.CheckException;

/**
* This class represents an operation for the Other Bank company.
* 
* @author Isabelle Deligniere
*/

@SuppressWarnings("serial")
@Entity
@Table(name = "T_OPERATION")
public final class Operation extends Transaction implements Serializable{
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
	public Operation() {
		this.transactionDate = LocalDateTime.now();
	}

	public Operation(final String description, final String type,
			final Account accountA, final long amount, final boolean result) {
		this.transactionDate = LocalDateTime.now();
		setDescription(description);
		setType(type);
		setAccountA(accountA);
		setAmount(amount);
		setResult(result);
	}
	
	public Operation(final String description, final String type,
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
            throw new CheckException("Invalid operation's description");
    	if (type == null || "".equals(type))
            throw new CheckException("Invalid operation's type");
    	if (accountA == null)
            throw new CheckException("Invalid operation's account A");
    }
		// ======================================
    // =         Getters and Setters        =
    // ======================================
    /**
     * This method sets the identifier of an operation.
     * @param id	the identifier of an operation.
     */
    public void setId(final Long id) {
    	this.id = id;
    }
    /**
     * This method gets the identifier of an operation.
     * @return id the identifier of an operation.
     */
    public Long getId() {
    	return id;
    }
    /**
     * This method sets the identifier of an operation.
     * @param transactionDate	the date of an operation.
     */
    public void setTransactionDate(final LocalDateTime transactionDate) {
    	this.transactionDate = transactionDate;
    }
    /**
     * This method gets the date of an operation.
     * @return transactionDate the date of an operation.
     */
    public LocalDateTime getTransactionDate() {
    	return transactionDate;
    }
    /**
     * This method sets the description of an operation.
     * @param description	the description of an operation.
     */
    public void setDescription(final String description) {
    	this.description = description;
    }
    /**
     * This method gets the description of an operation.
     * @return description the description of an operation.
     */
    public String getDescription() {
    	return description;
    }
    /**
     * This method sets the type of an operation.
     * @param type	the type of an operation.
     */
    public void setType(final String type) {
    	this.type = type;
    }
    /**
     * This method gets the type of an operation.
     * @return type the type of an operation.
     */
    public String getType() {
    	return type;
    }
    /**
     * This method sets the accountA of an operation.
     * @param accountA	the accountA of an operation.
     */
    public void setAccountA(final Account accountA) {
    	this.accountA = accountA;
    }
    /**
     * This method gets the accountA of an operation.
     * @return accountA the accountA of an operation.
     */
    public Account getAccountA() {
    	return accountA;
    }
    /**
     * This method sets the accountB of an operation.
     * @param accountB	the accountB of an operation.
     */
    public void setAccountB(final Account accountB) {
    	this.accountB = accountB;
    }
    /**
     * This method gets the accountB of an operation.
     * @return accountB the accountB of an operation.
     */
    public Account getAccountB() {
    	return accountB;
    }
    /**
     * This method sets the amount of an operation.
     * @param amount	the amount of an operation.
     */
    public void setAmount(final long amount) {
    	this.amount = amount;
    }
    /**
     * This method gets the amount of an operation.
     * @return amount the amount of an operation.
     */
    public long getAmount() {
    	return amount;
    }
    /**
     * This method sets the result of an operation.
     * @param result	the result of an operation.
     */
    public void setResult(final boolean result) {
    	this.result = result;
    }
    /**
     * This method gets the result of an operation.
     * @return id the result of an operation.
     */
    public boolean getResult() {
    	return result;
    }    
    /**
	 * This method displays an operation.
	 * @return the operation.
	 */
	public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tOperation {");
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

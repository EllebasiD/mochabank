package com.anotherbank.mochabank.domain.model;

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

import com.anotherbank.mochabank.exception.CheckException;

/**
* This class represents an operation for the Mocha Bank company.
* 
* @author Isabelle Deligniere
*/

@SuppressWarnings("serial")
@Entity
@Table(name = "T_SCHEDULED_OPERATION")
public final class ScheduledOperation extends Transaction implements Serializable{
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
	private String cronExpression;
	// ======================================
    // =            Constructors            =
    // ======================================
	public ScheduledOperation() {
		this.transactionDate = LocalDateTime.now();
	}
	
	public ScheduledOperation(final String description, final String type,
			final Account accountA, final long amount, final boolean result) {
		this.transactionDate = LocalDateTime.now();
		setDescription(description);
		setType(type);
		setAccountA(accountA);
		setAmount(amount);
		setResult(result);
	}
	
	public ScheduledOperation(final String description, final String type,
			final Account accountA, final Account accountB, final long amount, final String cronExpression, final boolean result) {
		this.transactionDate = LocalDateTime.now();
		setDescription(description);
		setType(type);
		setAccountA(accountA);
		setAccountB(accountB);
		setAmount(amount);
		setCronExpression(cronExpression);
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
            throw new CheckException("Invalid scheduled operation's description");
    	if (type == null || "".equals(type))
            throw new CheckException("Invalid scheduled operation's type");
    	if (accountA == null)
            throw new CheckException("Invalid scheduled operation's account A");
    	if (accountB == null)
            throw new CheckException("Invalid scheduled operation's account B");
    	if (cronExpression == null || "".equals(cronExpression))
            throw new CheckException("Invalid scheduled operation's cronExpression");
    }
		// ======================================
    // =         Getters and Setters        =
    // ======================================
    /**
     * This method sets the identifier of a scheduled operation.
     * @param id	the identifier of a scheduled operation.
     */
    public void setId(final Long id) {
    	this.id = id;
    }
    /**
     * This method gets the identifier of a scheduled operation.
     * @return id the identifier of a scheduled operation.
     */
    public Long getId() {
    	return id;
    }
    /**
     * This method sets the identifier of a scheduled operation.
     * @param transactionDate	the date of a scheduled operation.
     */
    public void setTransactionDate(final LocalDateTime transactionDate) {
    	this.transactionDate = transactionDate;
    }
    /**
     * This method gets the date of a scheduled operation.
     * @return transactionDate the date of a scheduled operation.
     */
    public LocalDateTime getTransactionDate() {
    	return transactionDate;
    }
    /**
     * This method sets the description of a scheduled operation.
     * @param description	the description of a scheduled operation.
     */
    public void setDescription(final String description) {
    	this.description = description;
    }
    /**
     * This method gets the description of a scheduled operation.
     * @return description the description of a scheduled operation.
     */
    public String getDescription() {
    	return description;
    }
    /**
     * This method sets the type of a scheduled operation.
     * @param type	the type of a scheduled operation.
     */
    public void setType(final String type) {
    	this.type = type;
    }
    /**
     * This method gets the type of a scheduled operation.
     * @return type the type of a scheduled operation.
     */
    public String getType() {
    	return type;
    }
    /**
     * This method sets the accountA of a scheduled operation.
     * @param accountA	the accountA of a scheduled operation.
     */
    public void setAccountA(final Account accountA) {
    	this.accountA = accountA;
    }
    /**
     * This method gets the accountA of a scheduled operation.
     * @return accountA the accountA of a scheduled operation.
     */
    public Account getAccountA() {
    	return accountA;
    }
    /**
     * This method sets the accountB of a scheduled operation.
     * @param accountB	the accountB of a scheduled operation.
     */
    public void setAccountB(final Account accountB) {
    	this.accountB = accountB;
    }
    /**
     * This method gets the accountB of a scheduled operation.
     * @return accountB the accountB of a scheduled operation.
     */
    public Account getAccountB() {
    	return accountB;
    }
    /**
     * This method sets the amount of a scheduled operation.
     * @param amount	the amount of a scheduled operation.
     */
    public void setAmount(final long amount) {
    	this.amount = amount;
    }
    /**
     * This method gets the amount of a scheduled operation.
     * @return amount the amount of a scheduled operation.
     */
    public long getAmount() {
    	return amount;
    }
    /**
     * This method sets the result of a scheduled operation.
     * @param result	the result of a scheduled operation.
     */
    public void setResult(final boolean result) {
    	this.result = result;
    }
    /**
     * This method gets the result of a scheduled operation.
     * @return id the result of a scheduled operation.
     */
    public boolean getResult() {
    	return result;
    }     
    
    /**
     * This method sets the cron expression of a scheduled operation.
     * @param cronExpression	the cron expression of a scheduled operation.
     */
    public void setCronExpression(final String cronExpression) {
    	this.cronExpression = cronExpression;
    }
    /**
     * This method gets the cron expression of a scheduled operation.
     * @return cronExpression the cron expression of a scheduled operation.
     */
    public String getCronExpression() {
    	return cronExpression;
    }  
     
    /**
	 * This method displays a scheduled operation.
	 * @return the scheduled operation.
	 */
	public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tScheduled operation {");
        buf.append("\n\t\tId=").append(getId());
        buf.append("\n\t\tDate=").append(getTransactionDate());
        buf.append("\n\t\tDescription=").append(getDescription());
        buf.append("\n\t\tType=").append(getType());
        buf.append("\n\t\tAccountA=").append(getAccountA());
        if(getAccountB() != null) {
        	buf.append("\n\t\tAccountB=").append(getAccountB());
        }
        buf.append("\n\t\tAmount=").append(getAmount());
        buf.append("\n\t\tCron expression=").append(getCronExpression());
        buf.append("\n\t\tResult=").append(getResult());
        buf.append("\n\t}");
        return buf.toString();
    }
	
}

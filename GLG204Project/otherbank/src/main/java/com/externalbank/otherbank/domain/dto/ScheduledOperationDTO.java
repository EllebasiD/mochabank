package com.externalbank.otherbank.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* This class follows the Data Transfer Object design pattern.
* It is a client view of a ScheduledOperation. This class only
* transfers data from a distant service to a client.
* 
*  @author Isabelle Deligniere
*/

@SuppressWarnings("serial")
public class ScheduledOperationDTO extends TransactionDTO implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
	private long id;
	private LocalDateTime transactionDate;
	private String description;
	private String type;
	private Long accountAId;
	private Long accountBId;
	private long amount;
	private boolean result;	
	private String cronExpression;
	// ======================================
    // =            Constructors            =
    // ======================================
	public ScheduledOperationDTO() {
		this.transactionDate = LocalDateTime.now();
	}

	public ScheduledOperationDTO(final String description, final String type,
			final Long accountAId, final Long amount, final boolean result) {
		this.transactionDate = LocalDateTime.now();
		setDescription(description);
		setType(type);
		setAccountAId(accountAId);
		setAmount(amount);
		setResult(result);
	}
	
	public ScheduledOperationDTO(final String description, final String type,
			final Long accountAId, final Long accountBId, final long amount, final String cronExpression, final boolean result) {
		this.transactionDate = LocalDateTime.now();
		setDescription(description);
		setType(type);
		setAccountAId(accountAId);
		setAccountBId(accountBId);
		setAmount(amount);
		setCronExpression(cronExpression);
		setResult(result);
	}

	// ======================================
    // =         Getters and Setters        =
    // ======================================
    /**
     * This method sets the identifier of a scheduled operation.
     * @param id	the identifier of a scheduled operation.
     */
    public void setId(final long id) {
    	this.id = id;
    }
    /**
     * This method gets the identifier of a scheduled operation.
     * @return id the identifier of a scheduled operation.
     */
    public long getId() {
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
     * This method sets the identifier of accountA of a scheduled operation.
     * @param accountAId	the identifier of accountA of a scheduled operation.
     */
    public void setAccountAId(final Long accountAId) {
    	this.accountAId = accountAId;
    }
    /**
     * This method gets the identifier of accountA of a scheduled operation.
     * @return accountAId the identifier of accountA of a scheduled operation.
     */
    public Long getAccountAId() {
    	return accountAId;
    }
    /**
     * This method sets the identifier of accountB of a scheduled operation.
     * @param accountBId	the identifier of accountB of a scheduled operation.
     */
    public void setAccountBId(final Long accountBId) {
    	this.accountBId = accountBId;
    }
    /**
     * This method gets the identifier of accountB of a scheduled operation.
     * @return accountBId the identifier of accountB of a scheduled operation.
     */
    public Long getAccountBId() {
    	return accountBId;
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
        buf.append("\n\t\tAccountA Id=").append(getAccountAId());
        if(getAccountBId() != null) {
        	buf.append("\n\t\tAccountB Id=").append(getAccountBId());
        }
        buf.append("\n\t\tAmount=").append(getAmount());
        buf.append("\n\t\tCron expression=").append(getCronExpression());
        buf.append("\n\t\tResult=").append(getResult());
        buf.append("\n\t}");
        return buf.toString();
    }
	
	
}

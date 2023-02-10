package com.externalbank.otherbank.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* This class follows the Data Transfer Object design pattern.
* It is a client view of an Operation. This class only
* transfers data from a distant service to a client.
* 
*  @author Isabelle Deligniere
*/

@SuppressWarnings("serial")
public class OperationDTO extends TransactionDTO implements Serializable {

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
	// ======================================
    // =            Constructors            =
    // ======================================
	public OperationDTO() {
		this.transactionDate = LocalDateTime.now();
	}
	
	public OperationDTO(final String description, final String type,
			final Long accountAId, final long amount, final boolean result) {
		this.transactionDate = LocalDateTime.now();
		setDescription(description);
		setType(type);
		setAccountAId(accountAId);
		setAmount(amount);
		setResult(result);
	}
	
	public OperationDTO(final String description, final String type,
			final Long accountAId, final Long accountBId, final long amount, final boolean result) {
		this.transactionDate = LocalDateTime.now();
		setDescription(description);
		setType(type);
		setAccountAId(accountAId);
		setAccountBId(accountBId);
		setAmount(amount);
		setResult(result);
	}

		// ======================================
    // =         Getters and Setters        =
    // ======================================
    /**
     * This method sets the identifier of an operation.
     * @param id	the identifier of an operation.
     */
    public void setId(final long id) {
    	this.id = id;
    }
    /**
     * This method gets the identifier of an operation.
     * @return id the identifier of an operation.
     */
    public long getId() {
    	return id;
    }
    /**
     * This method sets the identifier of an operation.
     * @param operationDate	the date of an operation.
     */
    public void setTransactionDate(final LocalDateTime operationDate) {
    	this.transactionDate = operationDate;
    }
    /**
     * This method gets the date of an operation.
     * @return operationDate the date of an operation.
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
     * This method sets the identifier of accountA of an operation.
     * @param accountAId	the identifier of accountA of an operation.
     */
    public void setAccountAId(final Long accountAId) {
    	this.accountAId = accountAId;
    }
    /**
     * This method gets the identifier of accountA of an operation.
     * @return accountAId the identifier of accountA of an operation.
     */
    public Long getAccountAId() {
    	return accountAId;
    }

    /**
     * This method sets the identifier of accountB of an operation.
     * @param accountBId	the identifier of accountB of an operation.
     */
    public void setAccountBId(final Long accountBId) {
    	this.accountBId = accountBId;
    }
    /**
     * This method gets the identifier of accountB of an operation.
     * @return accountBId the identifier of accountB of an operation.
     */
    public Long getAccountBId() {
    	return accountBId;
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
        buf.append("\n\t\tAccountA Id=").append(getAccountAId());
        if(getAccountBId() != null) {
        	buf.append("\n\t\tAccountB Id=").append(getAccountBId());
        }
        buf.append("\n\t\tAmount=").append(getAmount());
        buf.append(",Result=").append(getResult());
        buf.append("\n\t}");
        return buf.toString();
    }

}

package com.externalbank.otherbank.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* This class follows the Data Transfer Object design pattern.
* It is a client view of a Transaction. This class only
* transfers data from a distant service to a client.
* 
*  @author Isabelle Deligniere
*/

@SuppressWarnings("serial")
public class TransactionDTO implements Serializable {

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
	public TransactionDTO() {
		this.transactionDate = LocalDateTime.now();
	}
	
	public TransactionDTO(final String description, final String type,
			final Long accountAId, final long amount, final boolean result) {
		this.transactionDate = LocalDateTime.now();
		setDescription(description);
		setType(type);
		setAccountAId(accountAId);
		setAmount(amount);
		setResult(result);
	}
	
	public TransactionDTO(final String description, final String type,
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
     * This method sets the identifier of a transaction.
     * @param id	the identifier of a transaction.
     */
    public void setId(final long id) {
    	this.id = id;
    }
    /**
     * This method gets the identifier of a transaction.
     * @return id the identifier of a transaction.
     */
    public long getId() {
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
     * This method sets the identifier of accountA of a transaction.
     * @param accountAId	the identifier of accountA of a transaction.
     */
    public void setAccountAId(final Long accountAId) {
    	this.accountAId = accountAId;
    }
    /**
     * This method gets the identifier of accountA of a transaction.
     * @return accountAId the identifier of accountA of a transaction.
     */
    public Long getAccountAId() {
    	return accountAId;
    }
    /**
     * This method sets the identifier of accountB of a transaction.
     * @param accountBId	the identifier of accountB of a transaction.
     */
    public void setAccountBId(final Long accountBId) {
    	this.accountBId = accountBId;
    }
    /**
     * This method gets the identifier of accountB of a transaction.
     * @return accountBId the identifier of accountB of a transaction.
     */
    public Long getAccountBId() {
    	return accountBId;
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
	
	
	


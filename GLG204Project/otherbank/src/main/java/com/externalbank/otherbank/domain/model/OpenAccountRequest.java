package com.externalbank.otherbank.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.externalbank.otherbank.authentication.domain.model.ProspectCustomer;
import com.externalbank.otherbank.exception.CheckException;

/**
* This class represents an open account request for the Other Bank company.
* 
* @author Isabelle Deligniere
*/

@SuppressWarnings("serial")
@Entity
@Table(name = "T_OPENACCOUNTREQUEST") 
public final class OpenAccountRequest implements Serializable{
	// ======================================
    // =             Attributes             =
    // ======================================
	@Id
	@Column(name="id")
	private String id;
	private Date openAccountRequestDate = new Date();
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="prospect_customer_fk", nullable = false)
	private ProspectCustomer prospectCustomer;
	private String accountOffer;
    private boolean accepted;
    // ======================================
    // =            Constructors            =
    // ======================================
    public OpenAccountRequest () {}
    
    public OpenAccountRequest (final String id) {
    	setId(id);
    }
    
    public OpenAccountRequest (final String id, final ProspectCustomer prospectCustomer, String accountOffer) {
    	setId(id);
    	setProspectCustomer(prospectCustomer);
    	setAccountOffer(accountOffer);
    }
    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method checks the integrity of the object data.
     * @throws CheckException if data is invalid
     */
    public void checkData() throws CheckException {
        if (prospectCustomer == null)
            throw new CheckException("Invalid prospect customer");
        if (accountOffer == null || "".equals(accountOffer))
            throw new CheckException("Invalid account offer");
    }
    
    // ======================================
    // =         Getters and Setters        =
    // ======================================
    /**
     * This method sets the identifier of an open account request.
     * @param id	the identifier of an open account request.
     */
    public void setId(final String id) {
    	this.id = id;
    }
    /**
     * This method gets the identifier of an open account request.
     * @return id the identifier of an open account request.
     */
    public String getId() {
    	return id;
    }
    /**
     * This method sets the date of the open account request.
     * @param openAccountRequestDate	the date of the open account request.
     */
    public void setOpenAccountRequestDate(final Date openAccountRequestDate) {
    	this.openAccountRequestDate = openAccountRequestDate;
    }
    /**
     * This method gets the date of the open account request.
     * @return date	the date of the open account request.
     */
    public Date getOpenAccountRequestDate() {
    	return openAccountRequestDate;
    }       
    /**
     * This method sets the prospect customer of an open account request.
     * @param prospectCustomer	the prospect customer of an open account request.
     */
    public void setProspectCustomer(final ProspectCustomer prospectCustomer) {
    	this.prospectCustomer = prospectCustomer;
    }
    /**
     * This method gets the prospect customer of an open account request.
     * @return prospectCustomer the prospect customer of an open account request.
     */
    public ProspectCustomer getProspectCustomer() {
    	return prospectCustomer;
    }
    /**
     * This method sets the account offer of an open account request.
     * @param accountOffer	the account offer of an open account request.
     */
    public void setAccountOffer(final String accountOffer) {
    	this.accountOffer = accountOffer;
    }
    /**
     * This method gets the account offer of an open account request.
     * @return accountOffer	the account offer of an open account request.
     */
    public String getAccountOffer() {
    	return accountOffer;
    }
    /**
     * This method sets the status of an open account request.
     * @param accepted	the open account request.
     */
    public void setAccepted(boolean accepted) {
    	this.accepted = accepted;
    }
    /**
     * This method gets the status of an open account request.
     * @return accepted	the open account request.
     */
    public boolean getAccepted() {
    	return accepted;
    }
    /**
	 * This method displays an open account request.
	 * @return the open account request.
	 */
	public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tOpenAccountRequest {");
        buf.append("\n\t\tId=").append(getId());
        buf.append("\n\t\tDate=").append(getOpenAccountRequestDate());
        buf.append("\n\t\tProspect Customer=").append(getProspectCustomer());
        buf.append(",Statut=").append(getAccepted());
        buf.append("\n\t}");
        return buf.toString();
    }
    
    
    
    
    
}


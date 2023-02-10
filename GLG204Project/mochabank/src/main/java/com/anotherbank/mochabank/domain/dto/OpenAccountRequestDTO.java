package com.anotherbank.mochabank.domain.dto;

import java.io.Serializable;
import java.util.Date;

import com.anotherbank.mochabank.authentication.domain.dto.ProspectCustomerDTO;

/**
* This class follows the Data Transfer Object design pattern.
* It is a client view of an OpenAccountRequest. This class only
* transfers data from a distant service to a client.
* 
*  @author Isabelle Deligniere
*/

@SuppressWarnings("serial")
public final class OpenAccountRequestDTO implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
	private String id;
	private Date openAccountRequestDate = new Date();
    private ProspectCustomerDTO prospectCustomer = new ProspectCustomerDTO();
    private String accountOffer;
    private boolean accepted;
    // ======================================
    // =            Constructors            =
    // ======================================
    public OpenAccountRequestDTO () {}
    
    public OpenAccountRequestDTO (final String id) {
    	setId(id);
    }
    
    public OpenAccountRequestDTO (final String id, final ProspectCustomerDTO prospectCustomer, final String accountOffer) {
    	setId(id);
    	setProspectCustomer(prospectCustomer);
    	setAccountOffer(accountOffer);
    }
 // ======================================
    // =         Getters and Setters        =
    // ======================================
    /**
     * This method sets the identifier of an OpenAccountRequest.
     * @param id	the identifier of an OpenAccountRequest.
     */
    public void setId(final String id) {
    	this.id = id;
    }
    /**
     * This method gets the identifier of an OpenAccountRequest.
     * @return id the identifier of an OpenAccountRequest.
     */
    public String getId() {
    	return id;
    }
    /**
     * This method sets the date of the openAccountRequest.
     * @param openAccountRequestDate	the date of the openAccountRequest.
     */
    public void setOpenAccountRequestDate(final Date openAccountRequestDate) {
    	this.openAccountRequestDate = openAccountRequestDate;
    }
    /**
     * This method gets the date of the openAccountRequest.
     * @return openAccountRequestDate	the date of the openAccountRequest.
     */
    public Date getOpenAccountRequestDate() {
    	return openAccountRequestDate;
    }
    /**
     * This method sets the prospect customer of an OpenAccountRequest.
     * @param prospectCustomer	the prospect customer of an OpenAccountRequest.
     */
    public void setProspectCustomer(final ProspectCustomerDTO prospectCustomer) {
    	this.prospectCustomer = prospectCustomer;
    }
    /**
     * This method gets the prospect customer of an OpenAccountRequest.
     * @return prospectCustomer	the prospect customer of an OpenAccountRequest.
     */
	public ProspectCustomerDTO getProspectCustomer() {
        return prospectCustomer;
    }
	/**
	 * This method sets the account offer of an OpenAccountRequest.
	 * @param accountOffer	the account offer of an OpenAccountRequest.
	 */
	public void setAccountOffer(final String accountOffer) {
    	this.accountOffer = accountOffer;
    }
	/**
	 * This method gets the account offer of an OpenAccountRequest.
	 * @return accountOffer	the account offer of an OpenAccountRequest.
	 */
    public String getAccountOffer() {
        return accountOffer;
    }	
    /**
     * This method sets the status of an OpenAccountRequest.
     * @param accepted	the OpenAccountRequest.
     */
    public void setAccepted(boolean accepted) {
    	this.accepted = accepted;
    }
    /**
     * This method gets the status of an OpenAccountRequest.
     * @return accepted	the OpenAccountRequest.
     */
    public boolean getAccepted() {
    	return accepted;
    }
    /**
	 * This method displays an OpenAccountRequest.
	 * @return the OpenAccountRequest.
	 */
	public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tOpenAccountRequest {");
        buf.append("\n\t\tProspect customer=").append(getProspectCustomer());
        buf.append("\n\t\tDate=").append(getOpenAccountRequestDate());
        buf.append("\n\t\tAccount offer=").append(getAccountOffer());
        buf.append(",Statut=").append(getAccepted());
        buf.append("\n\t}");
        return buf.toString();
    }
    
       
}

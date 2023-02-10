package com.anotherbank.mochabank.authentication.domain.dto;

import java.io.Serializable;
import java.util.Date;

import com.anotherbank.mochabank.domain.dto.AddressDTO;

/**
 * This class follows the Data Transfert Object design pattern.
 * It is a client view of a ProspectCustomer. This class only
 * transfers data from a distant service to a client.
 * 
 * @author Isabelle Deligniere
 */
@SuppressWarnings("serial")
public final class ProspectCustomerDTO extends UserDTO implements Serializable {
	// ======================================
    // =             Attributes             =
    // ======================================
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String phoneNumber;
    private String email;
    private Date birthdate;
    private String company;
    private final AddressDTO address = new AddressDTO();
    private Integer roleId;
	private String roleName;

    // ======================================
    // =            Constructors            =
    // ======================================
    public ProspectCustomerDTO() {
    }
    
    public ProspectCustomerDTO(final String username) {
    	setUsername(username);
    }
    
    public ProspectCustomerDTO(final String username, final String firstname, final String lastname) {
        setUsername(username);
        setFirstname(firstname);
        setLastname(lastname);
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    /**
     * This method sets the user name of a prospect customer.
     * @param username	the user name of a prospect customer.
     */
    public void setUsername(final String username) {
    	this.username = username;
    }
    /**
     * This method gets the user name of a prospect customer.
     * @return username	 the user name of a prospect customer.
     */
    public String getUsername() {
        return username;
    }
    /**
     * This method sets the first name of a prospect customer.
     * @param firstname	the first name of a prospect customer.
     */
    public void setFirstname(final String firstname) {
    	this.firstname = firstname;
    }
    /**
     * This method gets the first name of a prospect customer.
     * @return firstname	the first name of a prospect customer.
     */
    public String getFirstname() {
        return firstname;
    }
    /**
	 * This method sets the last name of a prospect customer.
	 * @param lastname	the last name of a prospect customer.
	 */
    public void setLastname(final String lastname) {
    	this.lastname = lastname;
    }
    /**
	 * This method gets the last name of a prospect customer.
	 * @return lastname	the last name of a prospect customer.
	 */
    public String getLastname() {
        return lastname;
    }
    /**
	 * This method sets the phone number of a prospect customer.
	 * @param phoneNumber	the phone number of a prospect customer.
	 */
    public void setPhoneNumber(final String phoneNumber) {
    	this.phoneNumber = phoneNumber;
    }
    /**
	 * This method gets the phone number of a prospect customer.
	 * @return phoneNumber	the phone number of a prospect customer.
	 */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * This method sets the email of a prospect customer.
     * @param email	the email of a prospect customer.
     */
    public void setEmail(final String email) {
    	this.email = email;
    }
    /**
     * This method gets the email of a prospect customer.
     * @return email	the email of a prospect customer.
     */
    public String getEmail() {
        return email;
    }
    /**
	 * This method sets the birth date of a prospect customer.
	 * @param birthdate	the birth date of a prospect customer.
	 */
	public void setBirthdate(final Date birthdate) {
    	this.birthdate = birthdate;
    }
	/**
	 * 	This method gets the birth date of a prospect customer.
	 * @return birthdate	the birth date of a prospect customer.
	 */
	public Date getBirthdate() {
        return birthdate;
    }
	/**
	 * This method sets the company of a prospect customer.
	 * @param company	the company of a prospect customer.
	 */
	public void setCompany(final String company) {
    	this.company = company;
    }
	/**
	 * 	This method gets the company of a prospect customer.
	 * @return company	the company of a prospect customer.
	 */
	public String getCompany() {
        return company;
    }
	/**
	 * This method sets the street of an address (number included).
	 * @param street1	the street of an address.
	 */
    public void setStreet1(final String street1) {
        address.setStreet1(street1);
    }
    /**
	 * This method gets the street1 of an address (number included).
	 * @return street1	the street1 of an address.
	 */
    public String getStreet1() {
        return address.getStreet1();
    }
    /**
	 * This method sets the complement of the street of an address (if street1 need a complement).
	 * @param street2	the complement of the street of an address.
	 */
    public void setStreet2(final String street2) {
        address.setStreet2(street2);
    }
    /**
	 * This method gets the complement of the street of an address.
	 * @return street2	the complement of the street of an address.
	 */
    public String getStreet2() {
        return address.getStreet2();
    }
    /**
	 * This method sets the city of an address.
	 * @param city	the city of an address.
	 */
    public void setCity(final String city) {
        address.setCity(city);
    }
    /**
	 * This method gets the city of an address.
	 * @return city	the city of an address.
	 */
    public String getCity() {
        return address.getCity();
    }
    /**
	 * This method sets the zip code of an address.
	 * @param zipcode	the zip code of an address.
	 */
    public void setZipcode(final String zipcode) {
        address.setZipcode(zipcode);
    }
    /**
	 * This method gets the zip code of an address.
	 * @return zipcode	the zip code of an address.
	 */
    public String getZipcode() {
        return address.getZipcode();
    }
    /**
     * This method sets the password of a prospect customer.
     * @param password	the password of a prospect customer.
     */
    public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * This method gets the password of a prospect customer.
	 * @return password	the password of a prospect customer.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * This method sets the role name of a user role.
	 * 
	 * @param roleId		the role identifier of the user role.
	 */
	public void setRoleId(final Integer roleId) {
		this.roleId = roleId;
	}
	/**
	 * This method returns the role identifier of a user role.
	 * 
	 * @return roleId		the role identifier of the user role.
	 */
	public Integer getRoleId() {

		return roleId;
	}
	/**
	 * This method sets the role name of a prospect customer role.
	 * 
	 * @param roleName		the role name of the prospect customer role.
	 */
	public void setRoleName(final String roleName) {
		this.roleName = roleName;
	}
	/**
	 * This method returns the role name of a prospect customer role.
	 * 
	 * @return roleName		the role name of the prospect customer role.
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * This method displays a prospect customer.
	 * @return the customer.
	 */
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("ProspectCustomerDTO{");
        buf.append("username=").append(getUsername());
        buf.append(",firstname=").append(getFirstname());
        buf.append(",lastname=").append(getLastname());
        buf.append(",password=").append(getPassword());
        buf.append("\n\t\tBirth date=").append(getBirthdate().toString());
        buf.append("\n\t\tCompany=").append(getCompany());
        buf.append(",email=").append(getEmail());
        buf.append(",Phone number=").append(getPhoneNumber());
        buf.append(",street1=").append(getStreet1());
        buf.append(",street2=").append(getStreet2());
        buf.append(",city=").append(getCity());
        buf.append(",zipcode=").append(getZipcode());
		buf.append(",Role Name=").append(getRoleName());
        buf.append('}');
        return buf.toString();
    }
}

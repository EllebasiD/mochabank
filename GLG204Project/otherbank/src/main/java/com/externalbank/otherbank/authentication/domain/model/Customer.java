package com.externalbank.otherbank.authentication.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.externalbank.otherbank.domain.model.Account;
import com.externalbank.otherbank.domain.model.Address;
import com.externalbank.otherbank.exception.CheckException;

/**
 * This class represents a customer for the Other Bank company.
 * 
 * @author Isabelle Deligniere
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CUSTOMER") 
public final class Customer extends User implements Serializable {
	// ======================================
    // =             Attributes             =
    // ======================================
	@Id
	@Column(name="id")
	private String username;
    private String firstname;
    private String lastname;
    @Email(message = "Email should be valid")
    private String email;
    private Date birthdate;    
    private int phoneNumber;
    private String company;
	@NotEmpty
	private String password;
    @Embedded
    private Address address = new Address();
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_fk", referencedColumnName="id")
	private Role role;
    @OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="account_fk")
    private Account account;
    //@OneToMany(targetEntity=Account.class, mappedBy="accountName", fetch=FetchType.EAGER)
	//private Set<Account> accountsList = new HashSet<Account>();
    // private HashMap<K,V> listTransferDestinationAccount;
    
	// ======================================
    // =            Constructors            =
    // ======================================
    
    public Customer() {}

    public Customer(final String username) {
    	setUsername(username);
    }

    public Customer(final String username, final String firstname, final String lastname) {
    	setUsername(username);
    	setFirstname(firstname);
    	setLastname(lastname);
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method checks the integrity of the object data.
     * @throws CheckException if data is invalid
     */
    public void checkData() throws CheckException {
    	if (firstname == null || "".equals(firstname))
            throw new CheckException("Invalid Customer first name");
        if (lastname == null || "".equals(lastname))
            throw new CheckException("Invalid Customer last name");
    }
    /**
     * This method checks the age from the object birthdate.
     * @throws CheckException if birth date is invalid
     */
    public void checkBirthDate() throws CheckException{
    	//Today's date
    	LocalDate today = LocalDate.now();
    	//Birth date
    	LocalDate localBirthdate = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;  
    	//Age  
    	Period period = Period.between(localBirthdate, today);
    	if (period.getYears() < 18)
            throw new CheckException("Invalid Customer birth date, Customer must be 18 to open an account");    	
    }    
    
    // ======================================
    // =         Getters and Setters        =
    // ======================================
    /**
     * This method sets the user name of a customer.
     * @param username	the user name of a customer.
     */
    public void setUsername(final String username) {
		this.username = username;
	}
    /**
     * This method gets the user name of a customer.
     * @return username	 the user name of a customer.
     */
    public String getUsername() {
		return username;
	}
    /**
     * This method sets the first name of a customer.
     * @param firstname	the first name of a customer.
     */
    public void setFirstname(final String firstname) {
    	this.firstname = firstname;
    }
    /**
     * This method gets the first name of a customer.
     * @return firstname	the first name of a customer.
     */
	public String getFirstname() {
        return firstname;
    }
	/**
	 * This method sets the last name of a customer.
	 * @param lastname	the last name of a customer.
	 */
	public void setLastname(final String lastname) {
    	this.lastname = lastname;
    }
	/**
	 * This method gets the last name of a customer.
	 * @return lastname	the last name of a customer.
	 */
    public String getLastname() {
        return lastname;
    }
    /**
     * This method sets the email of a customer.
     * @param email	the email of a customer.
     */
    public void setEmail(final String email) {
		this.email = email;
	}
    /**
     * This method gets the email of a customer.
     * @return email	the email of a customer.
     */
    public String getEmail() {
		return email;
	}
    /**
     * This method sets the password of a customer.
     * @param password	the password of a customer.
     */
    public void setPassword(final String password) {
		this.password = password;
	}
	/**
	 * This method gets the password of a customer.
	 * @return password	the password of a customer.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * This method sets the birth date of a customer.
	 * @param birthdate	the birth date of a customer.
	 */
	public void setBirthdate(final Date birthdate) {
    	this.birthdate = birthdate;
    }
	/**
	 * 	This method gets the birth date of a customer.
	 * @return birthdate	the birth date of a customer.
	 */
	public Date getBirthdate() {
        return birthdate;
    }
	/**
	 * This method sets the company of a customer.
	 * @param company	the company of a customer.
	 */
	public void setCompany(final String company) {
    	this.company = company;
    }
	/**
	 * 	This method gets the company of a customer.
	 * @return company	the company of a customer.
	 */
	public String getCompany() {
        return company;
    }
	/**
	 * This method sets the phone number of a customer.
	 * @param phoneNumber	the phone number of a customer.
	 */
	public void setPhoneNumber(final int phoneNumber) {
    	this.phoneNumber = phoneNumber;
    }
	/**
	 * This method gets the phone number of a customer.
	 * @return phoneNumber	the phone number of a customer.
	 */
	public int getPhoneNumber() {
        return phoneNumber;
    }
	/**
     * This method sets the address of a customer.
     * @param address	the address of a customer.
     */
    public void setAddress(Address address) {
    	this.address = address;
	}
	/**
	 * This method gets the address of a customer.
	 * @return address	the address of a customer.
	 */
	public Address getAddress() {
		return address;
	}
	/**
	 * This method sets the street1 of an address (number included).
	 * @param street1	the street1 of an address.
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
	 * This method sets the role of a customer.
	 * @param role	the role of an a customer.
	 */
	public void setRole(final Role role) {
		this.role = role;
	}
	/**
	 * This method gets the role of a customer.
	 * @return role	the role of a customer.
	 */
	public Role getRole() {
		return role;
	}
	/**
     * This method sets the account of a customer.
     * @param account	the account of a customer.
     */
    public void setAccount(final Account account) {
    	this.account = account;
    }
    /**
     * This method gets the account of a customer.
     * @return account the account of a customer.
     */
    public Account getAccount() {
    	return account;
    }
	/**
     * This method sets the list of accounts of a customer.
     * @param accountsList	the list of accounts of a customer.
     */
    //public void setAccountsList(final Set<Account> accountsList) {
    //	this.accountsList = accountsList;
    //}
    /**
     * This method gets the accounts list of a customer.
     * @return accountsList the accounts list of a customer.
     */
    //public Set<Account> getAccountsList() {
    //	return accountsList;
    //}
	/**
	 * This method displays a customer.
	 * @return the customer.
	 */
	public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tCustomer {");
        buf.append("\n\t\tId=").append(getUsername());
        buf.append("\n\t\tFirst Name=").append(getFirstname());
        buf.append("\n\t\tLast Name=").append(getLastname());
        buf.append(",password=").append(getPassword());
        buf.append("\n\t\tBirth date=").append(getBirthdate().toString());
        buf.append("\n\t\tCompany=").append(getCompany());
        buf.append("\n\t\temail=").append(getEmail());
        buf.append("\n\t\tPhone number=").append(getPhoneNumber());
        buf.append(",street1=").append(getStreet1());
        buf.append(",street2=").append(getStreet2());
        buf.append(",city=").append(getCity());
        buf.append(",zipcode=").append(getZipcode());
        buf.append(",Role Name=").append(getRole().getName());
        buf.append("\n\t}");
        return buf.toString();
    }
}

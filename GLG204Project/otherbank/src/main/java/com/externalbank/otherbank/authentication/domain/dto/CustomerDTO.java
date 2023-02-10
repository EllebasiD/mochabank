package com.externalbank.otherbank.authentication.domain.dto;

import java.io.Serializable;
import java.util.Date;
import com.externalbank.otherbank.domain.dto.AccountDTO;
import com.externalbank.otherbank.domain.dto.AddressDTO;

/**
 * This class follows the Data Transfert Object design pattern.
 * It is a client view of a Customer. This class only
 * transfers data from a distant service to a client.
 * 
 * @author Isabelle Deligniere
 */
@SuppressWarnings("serial")
public final class CustomerDTO extends UserDTO implements Serializable {
	// ======================================
    // =             Attributes             =
    // ======================================
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private int phoneNumber;
    private String email;
    private Date birthdate;
    private String company;
    private AddressDTO address = new AddressDTO();
    private Integer roleId;
    private String roleName;
    private AccountDTO account = new AccountDTO();
    //private Set<Account> accountsList = new HashSet<Account>();

    // ======================================
    // =            Constructors            =
    // ======================================
    public CustomerDTO() {
    }
    
    public CustomerDTO(final String username) {
    	setUsername(username);
    }
    
    public CustomerDTO(final String username, final String firstname, final String lastname) {
        setUsername(username);
        setFirstname(firstname);
        setLastname(lastname);
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
     * This method sets the address of a customer.
     * @param address	the address of a customer.
     */
    public void setAddress(AddressDTO address) {
    	this.address = address;
	}
	/**
	 * This method gets the address of a customer.
	 * @return address	the address of a customer.
	 */
	public AddressDTO getAddress() {
		return address;
	}
    /**
     * This method sets the password of a customer.
     * @param password	the password of a customer.
     */
    public void setPassword(String password) {
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
	 * This method sets the role name of a customer role.
	 * 
	 * @param roleName		the role name of the customer role.
	 */
	public void setRoleName(final String roleName) {
		this.roleName = roleName;
	}
	/**
	 * This method returns the role name of a customer role.
	 * 
	 * @return roleName		the role name of the customer role.
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
     * This method sets the account of a customer.
     * @param account	the account of a customer.
     */
    public void setAccount(final AccountDTO account) {
    	this.account = account;
    }
    /**
     * This method gets the account of a customer.
     * @return account the account of a customer.
     */
    public AccountDTO getAccount() {
    	return account;
    }
    /**
     * This method sets the name of an account.
     * @param accountName	the name of an account.
     */
    public void setAccountName(final String accountName) {
    	account.setAccountName(accountName);
    }
    /**
     * This method gets the name of an account.
     * @return accountName the name of an account.
     */
    public String getAccountName() {
    	return account.getAccountName();
    }
    /**
     * This method sets the identifier of an Account.
     * @param id	the identifier of an Account.
     */
    public void setAccountId(final long id) {
    	account.setId(id);
    }
    /**
     * This method gets the identifier of an Account.
     * @return id the identifier of an Account.
     */
    public long getAccountId() {
    	return account.getId();
    }
    /**
     * This method sets the identifier of the acount's bank.
     * @param bankId	the identifier of the acount's bank.
     */
    public void setBankId(final String bankId) {
    	account.setBankId(bankId);
    }
    /**
     * This method gets the identifier of the acount's bank.
     * @return bankId the identifier of the account's bank.
     */
    public String getBankId() {
    	return account.getBankId();
    }
    /**
     * This method sets the name of the account's bank.
     * @param bankName	the name of the account's bank.
     */
    public void setBankName(final String bankName) {
    	account.setBankName(bankName);
    }
    /**
     * This method gets the name of the acount's bank.
     * @return bankName the name of the account's bank.
     */
    public String getBankName() {
    	return account.getBankName();
    }
    /**
     * This method sets the balance of an account.
     * @param balance	the balance of an account.
     */
    public void setBalance(final long balance) {
    	account.setBalance(balance);
    }
    /**
     * This method gets the balance of an account.
     * @return balance the balance of the account.
     */
    public long getBalance() {
    	return account.getBalance();
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
        buf.append("CustomerDTO{");
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

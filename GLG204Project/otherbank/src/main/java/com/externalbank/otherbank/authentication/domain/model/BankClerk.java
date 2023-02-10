package com.externalbank.otherbank.authentication.domain.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.externalbank.otherbank.exception.CheckException;

/**
 * This class represents a bank clerk for the Other Bank company.
 * 
 * @author Isabelle Deligniere
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_BANKCLERK") 
public final class BankClerk extends User implements Serializable {
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
	@NotEmpty
	private String password;    
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_fk", referencedColumnName="id")
	private Role role;
	
	// ======================================
    // =            Constructors            =
    // ======================================
    
    public BankClerk() {}

    public BankClerk(final String username) {
    	setUsername(username);
    }

    public BankClerk(final String username, final String firstname, final String lastname) {
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
            throw new CheckException("Invalid bank clerk first name");
        if (lastname == null || "".equals(lastname))
            throw new CheckException("Invalid bank clerk last name");
    }
    
    // ======================================
    // =         Getters and Setters        =
    // ======================================
    /**
     * This method sets the user name of a bank clerk.
     * @param username	the user name of a bank clerk.
     */
    public void setUsername(final String username) {
		this.username = username;
	}
    /**
     * This method gets the user name of a bank clerk.
     * @return username	 the user name of a bank clerk.
     */
    public String getUsername() {
		return username;
	}
    /**
     * This method sets the first name of a bank clerk.
     * @param firstname	the first name of a bank clerk.
     */
    public void setFirstname(final String firstname) {
    	this.firstname = firstname;
    }
    /**
     * This method gets the first name of a bank clerk.
     * @return firstname	the first name of a bank clerk.
     */
	public String getFirstname() {
        return firstname;
    }
	/**
	 * This method sets the last name of a bank clerk.
	 * @param lastname	the last name of a bank clerk.
	 */
	public void setLastname(final String lastname) {
    	this.lastname = lastname;
    }
	/**
	 * This method gets the last name of a bank clerk.
	 * @return lastname	the last name of a bank clerk.
	 */
    public String getLastname() {
        return lastname;
    }
    /**
     * This method sets the email of a bank clerk.
     * @param email	the email of a bank clerk.
     */
    public void setEmail(final String email) {
		this.email = email;
	}
    /**
     * This method gets the email of a bank clerk.
     * @return email	the email of a bank clerk.
     */
    public String getEmail() {
		return email;
	}
    /**
     * This method sets the password of a bank clerk.
     * @param password	the password of a bank clerk.
     */
    public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * This method gets the password of a bank clerk.
	 * @return password	the password of a bank clerk.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * This method sets the role of a bank clerk.
	 * @param role	the role of a bank clerk.
	 */
	public void setRole(final Role role) {
		this.role = role;
	}
	/**
	 * This method gets the role of a bank clerk.
	 * @return role	the role of a bank clerk.
	 */
	public Role getRole() {
		return role;
	}
	/**
	 * This method displays a bank clerk.
	 * @return the bank clerk.
	 */
	public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tBank clerk {");
        buf.append("\n\t\tId=").append(getUsername());
        buf.append("\n\t\tFirst Name=").append(getFirstname());
        buf.append("\n\t\tLast Name=").append(getLastname());
        buf.append(",password=").append(getPassword());
        buf.append("\n\t\temail=").append(getEmail());
        buf.append(",Role Name=").append(getRole().getName());
        buf.append("\n\t}");
        return buf.toString();
    }
}

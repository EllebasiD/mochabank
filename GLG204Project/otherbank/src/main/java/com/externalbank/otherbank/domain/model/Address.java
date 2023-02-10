package com.externalbank.otherbank.domain.model;

import javax.persistence.Embeddable;

import com.externalbank.otherbank.exception.CheckException;

/**
 * This class represents an address for the Other Bank company.
 * 
 * @author Isabelle Deligniere
 */
@Embeddable
public class Address {
	// ======================================
    // =             Attributes             =
    // ======================================
	private String street1;
	private String street2;
	private String city;
	private String zipcode;

	
	public Address() {}

	// ======================================
    // =           Business methods         =
    // ======================================
    public void checkData() throws CheckException {
        if (street1 == null || "".equals(street1))
            throw new CheckException("Invalid street");
        if (city == null || "".equals(city))
            throw new CheckException("Invalid city");
        if (zipcode == null || "".equals(city))
            throw new CheckException("Invalid zipcode");
        
    }
	// ======================================
    // =         Getters and Setters        =
    // ======================================
	/**
	 * This method sets the street of an address (number included).
	 * @param street1	the street of an address.
	 */
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	/**
	 * This method gets the street of an address (number included).
	 * @return street1	the street of an address.
	 */
	public String getStreet1() {
		return street1;
	}
	/**
	 * This method sets the complement of the street of an address (if street1 need a complement).
	 * @param street2	the complement of the street of an address.
	 */
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	/**
	 * This method gets the complement of the street of an address.
	 * @return street2	the complement of the street of an address.
	 */
	public String getStreet2() {
		return street2;
	}
	/**
	 * This method sets the city of an address.
	 * @param city	the city of an address.
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * This method gets the city of an address.
	 * @return city	the city of an address.
	 */
	public String getCity() {
		return city;
	}
	/**
	 * This method sets the zip code of an address.
	 * @param zipcode	the zip code of an address.
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	/**
	 * This method gets the zip code of an address.
	 * @return zipcode	the zip code of an address.
	 */
	public String getZipcode() {
		return zipcode;
	}	
}

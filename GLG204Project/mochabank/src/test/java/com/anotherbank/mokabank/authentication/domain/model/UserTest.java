package com.anotherbank.mokabank.authentication.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.anotherbank.mochabank.authentication.domain.model.BankClerk;
import com.anotherbank.mochabank.authentication.domain.model.Customer;
import com.anotherbank.mochabank.authentication.domain.model.ProspectCustomer;
import com.anotherbank.mochabank.exception.CheckException;


/**
 * This class tests the Customer class, ProspectCustomer and the  and the BankClerk class.
 */
public final class UserTest {

    //==================================
    //=            Test cases          =
    //==================================

    /**
     * This test tries to create a Customer with valid values.
     */
	@Test
    public void testCreateValidCustomer() {

        // Creates a valid customer
        try {
        	final Customer customer = new Customer("Ada000", "Ada", "Lovelace");
        	assertEquals("Ada", customer.getFirstname());
        	assertEquals("Lovelace", customer.getLastname());
        	customer.checkData();
        } catch (CheckException e) {
            fail("Customer data is OK!");
        }
    }
	/**
     * This test tries to create a ProspectCustomer with valid values.
     */
	@Test
    public void testCreateValidProspectCustomer() {

        // Creates a valid customer
        try {
        	final ProspectCustomer prospectCustomer = new ProspectCustomer("Ada000", "Ada", "Lovelace");
        	assertEquals("Ada", prospectCustomer.getFirstname());
        	assertEquals("Lovelace", prospectCustomer.getLastname());
        	prospectCustomer.checkData();
        } catch (CheckException e) {
            fail("Prospect customer data is OK!");
        }
    }
	/**
     * This test tries to create a BankClerk with valid values.
     */
	@Test
    public void testCreateValidBankClerk() {

        // Creates a valid customer
        try {
        	final BankClerk bankClerk = new BankClerk("Ada000", "Ada", "Lovelace");
        	assertEquals("Ada", bankClerk.getFirstname());
        	assertEquals("Lovelace", bankClerk.getLastname());
        	bankClerk.checkData();
        } catch (CheckException e) {
            fail("Bank clerk data is OK!");
        }
    }
    /**
     * This test tries to create a Customer with invalid values.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testCreateCustomerWithInvalidValues() throws Exception {

        // Creates objects with empty values
        try {
            final Customer customer = new Customer("1234", "", "Beck");
        	customer.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid customer first name", e.getMessage());
        }
        try {
            final Customer customer = new Customer("1234", "Kent", "");
        	customer.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid customer last name", e.getMessage());
        }

        // Creates objects with null values
        try {
            final Customer customer = new Customer("1234", null, "Beck");
        	customer.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid customer first name", e.getMessage());
        }
        try {
            final Customer customer = new Customer("1234", "Kent", null);
        	customer.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid customer last name", e.getMessage());
        }
    }
	/**
     * This test tries to create a ProspectCustomer with invalid values.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testCreateProspectCustomerWithInvalidValues() throws Exception {

        // Creates objects with empty values
        try {
            final ProspectCustomer prospectCustomer = new ProspectCustomer("Grace000", "", "Hopper");
            prospectCustomer.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid prospect customer first name", e.getMessage());
        }
        try {
            final ProspectCustomer prospectCustomer = new ProspectCustomer("Grace000", "Grace", "");
            prospectCustomer.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid prospect customer last name", e.getMessage());
        }

        // Creates objects with null values
        try {
            final ProspectCustomer prospectCustomer = new ProspectCustomer("Grace000", null, "Hopper");
            prospectCustomer.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid prospect customer first name", e.getMessage());
        }
        try {
            final ProspectCustomer prospectCustomer = new ProspectCustomer("Grace000", "Grace", null);
            prospectCustomer.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid prospect customer last name", e.getMessage());
        }
    }

	/**
     * This test tries to create a BankClerk with invalid values.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testCreateBankClerkWithInvalidValues() throws Exception {

        // Creates objects with empty values
        try {
            final BankClerk bankClerk = new BankClerk("Erich000", "", "Gamma");
            bankClerk.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid bank clerk first name", e.getMessage());
        }
        try {
            final BankClerk bankClerk = new BankClerk("Erich000", "Erich", "");
            bankClerk.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid bank clerk last name", e.getMessage());
        }

        // Creates objects with null values
        try {
            final BankClerk bankClerk = new BankClerk("Erich000", null, "Gamma");
            bankClerk.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid bank clerk first name", e.getMessage());
        }
        try {
            final BankClerk bankClerk = new BankClerk("Erich000", "Erich", null);
            bankClerk.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid bank clerk last name", e.getMessage());
        }
    }


}

package com.anotherbank.mokabank.authentication.domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.anotherbank.mochabank.MochabankApplication;
import com.anotherbank.mochabank.authentication.domain.dao.CustomerRepository;
import com.anotherbank.mochabank.authentication.domain.model.Customer;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.DuplicateKeyException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.ObjectNotFoundException;
import com.anotherbank.mokabank.configuration.TestConfig;

/**
 * This class tests the CustomerDAO class.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(classes = MochabankApplication.class)
public final class CustomerDAOTest {
	
	@Autowired
	private CustomerRepository customerRepository;
	
    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testDomainFindCustomerWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final String id = "555";
        try {
        	findCustomer(id);
            fail("Object with unknonw id should not be found");
        } catch (NoSuchElementException e) {
        }

        // Finds an object with a null identifier
        try {
            customerRepository.findById(null).get();
            fail("Object with null id should not be found");
        } catch (Exception e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testDomainFindAllCustomers() throws Exception {
        final String id = "333";

        // First findAll
        int firstSize = findAllCustomers();
        
        // Create an object
        createCustomer(id);

        // Ensures that the object exists
        try {
        	findCustomer(id);
        } catch (NoSuchElementException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        int secondSize = findAllCustomers();
        
        // Cleans the test environment
        removeCustomer(id);
        
        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");  

        try {
        	findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (NoSuchElementException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testDomainCreateCustomer() throws Exception {
        final String id = "3";
        Customer customer = null;

        // Ensures that the object doesn't exist
        try {
            customer = findCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (NoSuchElementException e) {
        }

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            customer = findCustomer(id);
        } catch (NoSuchElementException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customer, id);

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (NoSuchElementException e) {
        }

    }

    /**
     * This test make sure that updating an object success.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testDomainUpdateCustomer() throws Exception {
        final String id = "3";

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        Customer customer = null;
        try {
            customer = findCustomer(id);
        } catch (NoSuchElementException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customer, id);

        // Updates the object with new values
        updateCustomer(customer, id + 1);

        // Ensures that the object still exists
        Customer customerUpdated = null;
        try {
            customerUpdated = findCustomer(id);
        } catch (NoSuchElementException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkCustomer(customerUpdated, id + 1);

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (NoSuchElementException e) {
        }
    }

    /**
     * This test ensures that the system cannot remove an unknown object.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testDomainDeleteUnknownCustomer() throws Exception {
        // Removes an unknown object
        try {
            removeCustomer("custo1000");
            fail("Deleting an unknown object should break");
        } catch (EmptyResultDataAccessException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Customer findCustomer(final String id) throws NoSuchElementException {
        final Customer customer = customerRepository.findById("custo" + id).get();
        return customer;
    }

    private int findAllCustomers() throws FinderException {
        try {        	
            return ((Collection<Customer>) customerRepository.findAll()).size();
        } catch (Exception e) {
            return 0;
        }
    }

    private void createCustomer(final String id) throws CreateException, CheckException {
        final Customer customer = new Customer("custo" + id, "firstname" + id, "lastname" + id);
        customer.setCity("city" + id);
        customer.setStreet1("street1" + id);
        customer.setStreet2("street2" + id);
        customer.setPassword("password" + id);
        customer.setPhoneNumber("phone" + id);
        customer.setCompany("company" + id);      
        customer.setEmail("email" + id + "@java.fr");
        customer.setZipcode("zip" + id); 
        customerRepository.save(customer);
    }

    private void updateCustomer(final Customer customer, final String id) 
    throws ObjectNotFoundException, DuplicateKeyException, CheckException {
        customer.setFirstname("firstname" + id);
        customer.setLastname("lastname" + id);
        customer.setCity("city" + id);
        customer.setStreet1("street1" + id);
        customer.setStreet2("street2" + id);
        customer.setPassword("password" + id);
        customer.setPhoneNumber("phone" + id);
        customer.setCompany("company" + id);
        customer.setEmail("email" + id + "@java.fr");
        customer.setZipcode("zip" + id);
  		customerRepository.save(customer);
    }

    private void removeCustomer(final String id) throws EmptyResultDataAccessException {
        final String sid = "custo" + id;		
        customerRepository.deleteById(sid);
    }

    private void checkCustomer(final Customer customer, final String id) {
        assertEquals("firstname", "firstname" + id, customer.getFirstname());
        assertEquals("lastname", "lastname" + id, customer.getLastname());
        assertEquals("city", "city" + id, customer.getCity());
        assertEquals("street1", "street1" + id, customer.getStreet1());
        assertEquals("street2", "street2" + id, customer.getStreet2());
        assertEquals("telephone", "phone" + id, customer.getPhoneNumber());
        assertEquals("email", "email" + id + "@java.fr", customer.getEmail());
        assertEquals("password", "password" + id, customer.getPassword());
        assertEquals("zipcode", "zip" + id, customer.getZipcode());
     }
 
}


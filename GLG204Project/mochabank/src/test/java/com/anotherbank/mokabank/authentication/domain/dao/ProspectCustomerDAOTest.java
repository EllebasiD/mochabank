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
import com.anotherbank.mochabank.authentication.domain.dao.ProspectCustomerRepository;
import com.anotherbank.mochabank.authentication.domain.model.ProspectCustomer;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.DuplicateKeyException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.ObjectNotFoundException;
import com.anotherbank.mokabank.configuration.TestConfig;

/**
 * This class tests the ProspectCustomerDAO class.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(classes = MochabankApplication.class)
public class ProspectCustomerDAOTest {
	
	@Autowired
	private ProspectCustomerRepository prospectCustomerRepository;

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	 @Test
    public void testDomainFindProspectCustomerWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final String id = "Susan333";
        try {
        	findProspectCustomer(id);
            fail("Object with unknonw id should not be found");
        } catch (NoSuchElementException e) {
        }

        // Finds an object with a null identifier
        try {
        	prospectCustomerRepository.findById(null).get();
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
    public void testDomainFindAllProspectCustomers() throws Exception {
        final String id = "Susan333";

        // First findAll
        int firstSize = findAllProspectCustomers();

        // Create an object
        createProspectCustomer(id);

        // Ensures that the object exists
        try {
        	findProspectCustomer(id);
        } catch (NoSuchElementException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        int secondSize = findAllProspectCustomers();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeProspectCustomer(id);

        try {
        	findProspectCustomer(id);
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
    public void testDomainCreateProspectCustomer() throws Exception {
        final String id = "Susan333";
        ProspectCustomer prospectCustomer = null;

        // Ensures that the object doesn't exist
        try {
        	prospectCustomer = findProspectCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (NoSuchElementException e) {
        }

        // Creates an object
        createProspectCustomer(id);

        // Ensures that the object exists
        try {
        	prospectCustomer = findProspectCustomer(id);
        } catch (NoSuchElementException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkProspectCustomer(prospectCustomer, id);

        // Cleans the test environment
        removeProspectCustomer(id);

        try {
            findProspectCustomer(id);
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
    public void testDomainUpdateProspectCustomer() throws Exception {
        final String id = "Susan333";

        // Creates an object
        createProspectCustomer(id);

        // Ensures that the object exists
        ProspectCustomer prospectCustomer = null;
        try {
        	prospectCustomer = findProspectCustomer(id);
        } catch (NoSuchElementException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkProspectCustomer(prospectCustomer, id);

        // Updates the object with new values
        updateProspectCustomer(prospectCustomer, id + 1);

        // Ensures that the object still exists
        ProspectCustomer prospectCustomerUpdated = null;
        try {
        	prospectCustomerUpdated = findProspectCustomer(id);
        } catch (NoSuchElementException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkProspectCustomer(prospectCustomerUpdated, id + 1);

        // Cleans the test environment
        removeProspectCustomer(id);

        try {
            findProspectCustomer(id);
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
    public void testDomainDeleteUnknownProspectCustomer() throws Exception {
        // Removes an unknown object
        try {
            removeProspectCustomer("Susan333");
            fail("Deleting an unknown object should break");
        } catch (EmptyResultDataAccessException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private ProspectCustomer findProspectCustomer(final String id) throws NoSuchElementException {
        final ProspectCustomer prospectCustomer = prospectCustomerRepository.findById("custo" + id).get();
        return prospectCustomer;
    }

    private int findAllProspectCustomers() throws FinderException {
        try {        	
            return ((Collection<ProspectCustomer>) prospectCustomerRepository.findAll()).size();
        } catch (Exception e) {
            return 0;
        }
    }

    private void createProspectCustomer(final String id) throws CreateException, CheckException {
        final ProspectCustomer prospectCustomer = new ProspectCustomer("custo" + id, "firstname" + id, "lastname" + id);
        prospectCustomer.setCity("city" + id);
        prospectCustomer.setStreet1("street1" + id);
        prospectCustomer.setStreet2("street2" + id);
        prospectCustomer.setPhoneNumber("phone" + id);
        prospectCustomer.setCompany("company" + id);
        prospectCustomer.setPassword("password"+id);
        prospectCustomer.setEmail("email" + id + "@java.fr");
        prospectCustomer.setZipcode("zip" + id);
        prospectCustomerRepository.save(prospectCustomer);
    }

    private void updateProspectCustomer(final ProspectCustomer prospectCustomer, final String id) 
    throws ObjectNotFoundException, DuplicateKeyException, CheckException {
    	prospectCustomer.setFirstname("firstname" + id);
    	prospectCustomer.setLastname("lastname" + id);
    	prospectCustomer.setCity("city" + id);
    	prospectCustomer.setStreet1("street1" + id);
    	prospectCustomer.setStreet2("street2" + id);
    	prospectCustomer.setPhoneNumber("phone" + id);
    	prospectCustomer.setCompany("company" + id);
    	prospectCustomer.setEmail("email" + id + "@java.fr");
    	prospectCustomer.setZipcode("zip" + id);
        prospectCustomerRepository.save(prospectCustomer);
    }

    private void removeProspectCustomer(final String id) throws EmptyResultDataAccessException {
        final String sid = "custo" + id;
        prospectCustomerRepository.deleteById(sid);
    }

    private void checkProspectCustomer(final ProspectCustomer prospectCustomer, final String id) {
        assertEquals("firstname", "firstname" + id, prospectCustomer.getFirstname());
        assertEquals("lastname", "lastname" + id, prospectCustomer.getLastname());
        assertEquals("city", "city" + id, prospectCustomer.getCity());
        assertEquals("street1", "street1" + id, prospectCustomer.getStreet1());
        assertEquals("street2", "street2" + id, prospectCustomer.getStreet2());
        assertEquals("telephone", "phone" + id, prospectCustomer.getPhoneNumber());
        assertEquals("email", "email" + id + "@java.fr", prospectCustomer.getEmail());
        assertEquals("password", "password" + id, prospectCustomer.getPassword());
        assertEquals("zipcode", "zip" + id, prospectCustomer.getZipcode());
     }
}

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
import com.anotherbank.mochabank.authentication.domain.dao.BankClerkRepository;
import com.anotherbank.mochabank.authentication.domain.model.BankClerk;
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
public final class BankClerkDAOTest {
		 
	@Autowired
	private BankClerkRepository bankClerkRepository;

	//==================================
	//=            Test cases          =
	//==================================
	/**
	 * This test tries to find an object with a invalid identifier.
	 * 
	 * @throws Exception	is thrown if an error occurs during the test.
	 */
	@Test
	public void testDomainFindBankClerkWithInvalidValues() throws Exception {

		// Finds an object with a unknown identifier
	    final String id = "333";
	    try {
	      	findBankClerk(id);
	        fail("Object with unknown id should not be found");
	    } catch (NoSuchElementException e) {
	    }

	    // Finds an object with a null identifier
	    try {
	    	bankClerkRepository.findById(null).get();
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
	public void testDomainFindAllBankClerks() throws Exception {
	    final String id = "333";

	    // First findAll
	    int firstSize = findAllBankClerks();

	    // Create an object
	    createBankClerk(id);

	    // Ensures that the object exists
	    try {
	        findBankClerk(id);
	    } catch (NoSuchElementException e) {
	        fail("Object has been created it should be found");
	    }

	    // Second findAll
	    int secondSize = findAllBankClerks();

	    // Checks that the collection size has increase of one
	    if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

	    // Cleans the test environment
	    removeBankClerk(id);

	    try {
	        findBankClerk(id);
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
	public void testDomainCreateBankClerk() throws Exception {
	    final String id = "333";
	    BankClerk bankClerk = null;

	    // Ensures that the object doesn't exist
	    try {
	    	bankClerk = findBankClerk(id);
	        fail("Object has not been created yet it shouldn't be found");
	    } catch (NoSuchElementException e) {
	    }

	    // Creates an object
	    createBankClerk(id);

	    // Ensures that the object exists
	    try {
	    	bankClerk = findBankClerk(id);
	    } catch (NoSuchElementException e) {
	        fail("Object has been created it should be found");
	    }

	    // Checks that it's the right object
	    checkBankClerk(bankClerk, id);

	    // Cleans the test environment
	    removeBankClerk(id);

	    try {
	        findBankClerk(id);
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
	public void testDomainUpdateBankClerk() throws Exception {
	    final String id = "333";

	    // Creates an object
	    createBankClerk(id);

	    // Ensures that the object exists
	    BankClerk bankClerk = null;
	    try {
	    	bankClerk = findBankClerk(id);
	    } catch (NoSuchElementException e) {
	        fail("Object has been created it should be found");
	    }

	    // Checks that it's the right object
	    checkBankClerk(bankClerk, id);

	    // Updates the object with new values
	    updateBankClerk(bankClerk, id + 1);

	    // Ensures that the object still exists
	    BankClerk bankClerkUpdated = null;
	    try {
	    	bankClerkUpdated = findBankClerk(id);
	    } catch (NoSuchElementException e) {
	        fail("Object should be found");
	    }

	    // Checks that the object values have been updated
	    checkBankClerk(bankClerkUpdated, id + 1);

	    // Cleans the test environment
	    removeBankClerk(id);

	    try {
	        findBankClerk(id);
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
	public void testDomainDeleteUnknownBankClerk() throws Exception {
	    // Removes an unknown object
	    try {
	        removeBankClerk("333");
	        fail("Deleting an unknown object should break");
	    } catch (EmptyResultDataAccessException e) {
	    }
	}

	    //==================================
	    //=         Private Methods        =
	    //==================================
	    private BankClerk findBankClerk(final String id) throws NoSuchElementException {
	        final BankClerk bankClerk = bankClerkRepository.findById("clerk" + id).get();
	        return bankClerk;
	    }

	    private int findAllBankClerks() throws FinderException {
	        try {        	
	            return ((Collection<BankClerk>) bankClerkRepository.findAll()).size();
	        } catch (Exception e) {
	            return 0;
	        }
	    }

	    private void createBankClerk(final String id) throws CreateException, CheckException {
	        final BankClerk bankClerk = new BankClerk("clerk" + id, "firstname" + id, "lastname" + id);
	        bankClerk.setPassword("password" + id);
	        bankClerk.setEmail("email" + id + "@java.fr");
	        bankClerkRepository.save(bankClerk);
	    }

	    private void updateBankClerk(final BankClerk bankClerk, final String id) 
	    throws ObjectNotFoundException, DuplicateKeyException, CheckException {
	    	bankClerk.setFirstname("firstname" + id);
	    	bankClerk.setLastname("lastname" + id);
	    	bankClerk.setPassword("password" + id);
	    	bankClerk.setEmail("email" + id + "@java.fr");
	    	bankClerkRepository.save(bankClerk);
	    }

	    private void removeBankClerk(final String id) throws EmptyResultDataAccessException {
	        final String sid = "clerk" + id;
	        bankClerkRepository.deleteById(sid);
	    }

	    private void checkBankClerk(final BankClerk bankClerk, final String id) {
	        assertEquals("firstname", "firstname" + id, bankClerk.getFirstname());
	        assertEquals("lastname", "lastname" + id, bankClerk.getLastname());
	        assertEquals("email", "email" + id + "@java.fr", bankClerk.getEmail());
	        assertEquals("password", "password" + id, bankClerk.getPassword());
	     }
	 
}

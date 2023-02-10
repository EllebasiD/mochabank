package com.anotherbank.mokabank.authentication.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.anotherbank.mochabank.MochabankApplication;
import com.anotherbank.mochabank.authentication.domain.dto.BankClerkDTO;
import com.anotherbank.mochabank.authentication.domain.service.BankClerkService;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.DuplicateKeyException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.ObjectNotFoundException;
import com.anotherbank.mochabank.exception.RemoveException;
import com.anotherbank.mochabank.exception.UpdateException;
import com.anotherbank.mokabank.configuration.TestConfig;

/**
 * This class tests the BankClerkService class.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(classes = MochabankApplication.class)
public class BankClerkServiceTest {
	
	private static Logger logger = LogManager.getLogger(BankClerkServiceTest.class);
	
	@Autowired
	private BankClerkService bankClerkService;
	
	//==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testServiceFindBankClerkWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final String id = "333";
        try {
        	bankClerkService.findBankClerk(id);
            fail("Object with unknonw id should not be found");
        } catch (FinderException e) {
        }

        // Finds an object with an empty identifier
        try {
        	bankClerkService.findBankClerk(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
        	bankClerkService.findBankClerk(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testServiceFindAllBankClerks() throws Exception {
        final String id = "333";

        // First findAll
        final int firstSize = findAllBankClerks();
        logger.info("firstSize is ... "+firstSize);
        // Creates an object
        createBankClerk(id);

        // Ensures that the object exists
        try {
            findBankClerk(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllBankClerks();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteBankClerk(id);

        try {
            findBankClerk(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (FinderException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testServiceCreateBankClerk() throws Exception {
        final String id = "333";
        BankClerkDTO bankClerkDTO = null;

        // Ensures that the object doesn't exist
        try {
            findBankClerk(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (FinderException e) {
        }

        // Creates an object
        createBankClerk(id);

        // Ensures that the object exists
        try {
        	bankClerkDTO = findBankClerk(id);
        } catch (FinderException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkBankClerk(bankClerkDTO, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createBankClerk(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        deleteBankClerk(id);

        try {
            findBankClerk(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (FinderException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
    @Test
    public void testServiceCreateBankClerkWithInvalidValues() throws Exception {
    	BankClerkDTO bankClerkDTO;

        // Creates an object with a null parameter
        try {
        	bankClerkService.createBankClerk(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }
        
     // Creates an object with an invalid pwd
        try {
        	bankClerkDTO = new BankClerkDTO("username", "firstname", "lastname");
        	bankClerkDTO.setRoleName("ROLE_BANKCLERK");
        	bankClerkDTO.setPassword("abc");
        	bankClerkService.createBankClerk(bankClerkDTO);
            fail("Object with short password should not be created");
        } catch (CreateException e) {
        }

        // Creates an object with empty values
        try {
        	bankClerkDTO = new BankClerkDTO(new String(), new String(), new String());
        	bankClerkDTO.setPassword("validPwd");
        	bankClerkService.createBankClerk(bankClerkDTO);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
        	bankClerkDTO = new BankClerkDTO(null, null, null);
        	bankClerkDTO.setPassword("validPwd");
        	bankClerkService.createBankClerk(bankClerkDTO);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test make sure that updating an object success.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
    @Test
    public void testServiceUpdateBankClerk() throws Exception {
        final String id = "333";
        final String updatedId = "334";

        // Creates an object
        createBankClerk(id);

        // Ensures that the object exists
        BankClerkDTO bankClerkDTO = null;
        try {
        	bankClerkDTO = findBankClerk(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkBankClerk(bankClerkDTO, id);

        // Updates the object with new values
        updateBankClerk(bankClerkDTO, updatedId);

        // Ensures that the object still exists
        BankClerkDTO bankClerkUpdated = null;
        try {
        	bankClerkUpdated = findBankClerk(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkBankClerk(bankClerkUpdated, updatedId);

        // Cleans the test environment
        deleteBankClerk(id);

        try {
            findBankClerk(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (FinderException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
    @Test
    public void testServiceUpdateBankClerkWithInvalidValues() throws Exception {
    	BankClerkDTO bankClerkDTO;

        // Updates an object with a null parameter
        try {
        	bankClerkService.updateBankClerk(null);
            fail("Object with null parameter should not be updated");
        } catch (UpdateException e) {
        }

        // Updates an object with empty values
        try {
        	bankClerkDTO = new BankClerkDTO(new String(), new String(), new String());
        	bankClerkService.updateBankClerk(bankClerkDTO);
            fail("Object with empty values should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with null values
        try {
        	bankClerkDTO = new BankClerkDTO(null, null, null);
        	bankClerkService.updateBankClerk(bankClerkDTO);
            fail("Object with null values should not be updated");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the system cannot remove an unknown object.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
    @Test
    public void testServiceDeleteUnknownBankClerk() throws Exception {
        final String id = "333";

        // Ensures that the object doesn't exist
        try {
            findBankClerk(id);
            fail("Object has not been created it shouldn't be found");
        } catch (FinderException e) {
        }

        // Delete the unknown object
        try {
            deleteBankClerk(id);
            fail("Deleting an unknown object should break");
        } catch (RemoveException e) {
        }
    }

    //==================================
    //=          Private Methods       =
    //==================================

    private BankClerkDTO findBankClerk(final String id) throws FinderException, CheckException {
        final BankClerkDTO bankClerkDTO = bankClerkService.findBankClerk("clerk" + id);
        return bankClerkDTO;
    }

    private int findAllBankClerks() throws FinderException, RemoteException {
        try {
            return ((Collection<BankClerkDTO>) bankClerkService.findBankClerks()).size();
        } catch (ObjectNotFoundException e) {
        	logger.warn("exception is ... "+e.getMessage());
            return 0;
        }
    }

    private void createBankClerk(final String id) throws CreateException, CheckException, RemoteException {
        final BankClerkDTO bankClerkDTO = new BankClerkDTO("clerk" + id, "firstname" + id, "lastname" + id);
        bankClerkDTO.setEmail("email" + id + "@java.fr");
        bankClerkDTO.setPassword("pwd" + id);
        bankClerkDTO.setRoleName("ROLE_BANKCLERK");
        bankClerkService.createBankClerk(bankClerkDTO);
    }

    private void updateBankClerk(final BankClerkDTO bankClerkDTO, final String id) throws UpdateException, CheckException, RemoteException {
    	bankClerkDTO.setFirstname("firstname" + id);
        bankClerkDTO.setLastname("lastname" + id);
        bankClerkDTO.setEmail("email" + id + "@java.fr");
        bankClerkService.updateBankClerk(bankClerkDTO);
    }

    private void deleteBankClerk(final String id) throws RemoveException, CheckException, RemoteException {
    	bankClerkService.deleteBankClerk("clerk" + id);
    }

    private void checkBankClerk(final BankClerkDTO bankClerkDTO, final String id) {
        assertEquals("firstname", "firstname" + id, bankClerkDTO.getFirstname());
        assertEquals("lastname", "lastname" + id, bankClerkDTO.getLastname());
        assertEquals("email", "email" + id + "@java.fr", bankClerkDTO.getEmail());
    }
	
}

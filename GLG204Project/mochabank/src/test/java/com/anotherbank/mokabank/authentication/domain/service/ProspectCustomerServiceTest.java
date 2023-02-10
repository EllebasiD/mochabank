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
import com.anotherbank.mochabank.authentication.domain.dto.ProspectCustomerDTO;
import com.anotherbank.mochabank.authentication.domain.service.ProspectCustomerService;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.DuplicateKeyException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.ObjectNotFoundException;
import com.anotherbank.mochabank.exception.RemoveException;
import com.anotherbank.mochabank.exception.UpdateException;
import com.anotherbank.mokabank.configuration.TestConfig;

/**
 * This class tests the ProspectCustomerService class.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(classes = MochabankApplication.class)
public class ProspectCustomerServiceTest {
private static Logger logger = LogManager.getLogger(ProspectCustomerServiceTest.class);
	
	@Autowired
	private ProspectCustomerService prospectCustomerService;
	
	//==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testServiceFindProspectCustomerWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final String id = "333";
        try {
        	prospectCustomerService.findProspectCustomer(id);
            fail("Object with unknonw id should not be found");
        } catch (FinderException e) {
        }

        // Finds an object with an empty identifier
        try {
        	prospectCustomerService.findProspectCustomer(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
        	prospectCustomerService.findProspectCustomer(null);
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
    public void testServiceFindAllProspectCustomers() throws Exception {
        final String id = "333";

        // First findAll
        final int firstSize = findAllProspectCustomers();
        logger.info("firstSize is ... "+firstSize);
        // Creates an object
        createProspectCustomer(id);

        // Ensures that the object exists
        try {
            findProspectCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllProspectCustomers();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteProspectCustomer(id);

        try {
            findProspectCustomer(id);
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
    public void testServiceCreateProspectCustomer() throws Exception {
        final String id = "333";
        ProspectCustomerDTO prospectCustomerDTO = null;

        // Ensures that the object doesn't exist
        try {
            findProspectCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (FinderException e) {
        }

        // Creates an object
        createProspectCustomer(id);

        // Ensures that the object exists
        try {
        	prospectCustomerDTO = findProspectCustomer(id);
        } catch (FinderException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkProspectCustomer(prospectCustomerDTO, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createProspectCustomer(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        deleteProspectCustomer(id);

        try {
            findProspectCustomer(id);
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
    public void testServiceCreateProspectCustomerWithInvalidValues() throws Exception {
        ProspectCustomerDTO prospectCustomerDTO;

        // Creates an object with a null parameter
        try {
        	prospectCustomerService.createProspectCustomer(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }
        
     // Creates an object with an invalid pwd
        try {
        	prospectCustomerDTO = new ProspectCustomerDTO("username", "firstname", "lastname");
        	prospectCustomerDTO.setPassword("abc");
            prospectCustomerService.createProspectCustomer(prospectCustomerDTO);
            fail("Object with short password should not be created");
        } catch (CreateException e) {
        }

        // Creates an object with empty values
        try {
        	prospectCustomerDTO = new ProspectCustomerDTO(new String(), new String(), new String());
            prospectCustomerDTO.setPassword("validPwd");
            prospectCustomerService.createProspectCustomer(prospectCustomerDTO);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
        	prospectCustomerDTO = new ProspectCustomerDTO(null, null, null);
            prospectCustomerDTO.setPassword("validPwd");
            prospectCustomerService.createProspectCustomer(prospectCustomerDTO);
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
    public void testServiceUpdateProspectCustomer() throws Exception {
        final String id = "333";
        final String updatedId = "334";

        // Creates an object
        createProspectCustomer(id);

        // Ensures that the object exists
        ProspectCustomerDTO prospectCustomerDTO = null;
        try {
        	prospectCustomerDTO = findProspectCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkProspectCustomer(prospectCustomerDTO, id);

        // Updates the object with new values
        updateProspectCustomer(prospectCustomerDTO, updatedId);

        // Ensures that the object still exists
        ProspectCustomerDTO prospectCustomerUpdated = null;
        try {
        	prospectCustomerUpdated = findProspectCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkProspectCustomer(prospectCustomerUpdated, updatedId);

        // Cleans the test environment
        deleteProspectCustomer(id);

        try {
            findProspectCustomer(id);
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
    public void testServiceUpdateProspectCustomerWithInvalidValues() throws Exception {
    	ProspectCustomerDTO prospectCustomerDTO;

        // Updates an object with a null parameter
        try {
        	prospectCustomerService.updateProspectCustomer(null);
            fail("Object with null parameter should not be updated");
        } catch (UpdateException e) {
        }

        // Updates an object with empty values
        try {
        	prospectCustomerDTO = new ProspectCustomerDTO(new String(), new String(), new String());
        	prospectCustomerService.updateProspectCustomer(prospectCustomerDTO);
            fail("Object with empty values should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with null values
        try {
        	prospectCustomerDTO = new ProspectCustomerDTO(null, null, null);
            prospectCustomerService.updateProspectCustomer(prospectCustomerDTO);
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
    public void testServiceDeleteUnknownProspectCustomer() throws Exception {
        final String id = "333";

        // Ensures that the object doesn't exist
        try {
            findProspectCustomer(id);
            fail("Object has not been created it shouldn't be found");
        } catch (FinderException e) {
        }

        // Delete the unknown object
        try {
            deleteProspectCustomer(id);
            fail("Deleting an unknown object should break");
        } catch (RemoveException e) {
        }
    }

    //==================================
    //=          Private Methods       =
    //==================================

    private ProspectCustomerDTO findProspectCustomer(final String id) throws FinderException, CheckException {
        final ProspectCustomerDTO prospectCustomerDTO = prospectCustomerService.findProspectCustomer("prospect" + id);
        return prospectCustomerDTO;
    }

    private int findAllProspectCustomers() throws FinderException, RemoteException {
        try {
            return ((Collection<ProspectCustomerDTO>) prospectCustomerService.findProspectCustomers()).size();
        } catch (ObjectNotFoundException e) {
        	logger.warn("exception is ... "+e.getMessage());
            return 0;
        }
    }

    private void createProspectCustomer(final String id) throws CreateException, CheckException, RemoteException {
        final ProspectCustomerDTO prospectCustomerDTO = new ProspectCustomerDTO("prospect" + id, "firstname" + id, "lastname" + id);
        prospectCustomerDTO.setCity("city" + id);
        prospectCustomerDTO.setStreet1("street1" + id);
        prospectCustomerDTO.setStreet2("street2" + id);
        prospectCustomerDTO.setPhoneNumber("phone" + id);
        prospectCustomerDTO.setEmail("email" + id + "@java.fr");
        prospectCustomerDTO.setPassword("pwd" + id);
        prospectCustomerDTO.setZipcode("zip" + id);
        prospectCustomerService.createProspectCustomer(prospectCustomerDTO);
    }

    private void updateProspectCustomer(final ProspectCustomerDTO prospectCustomerDTO, final String id) throws UpdateException, CheckException, RemoteException {
    	prospectCustomerDTO.setFirstname("firstname" + id);
        prospectCustomerDTO.setLastname("lastname" + id);
        prospectCustomerDTO.setCity("city" + id);
        prospectCustomerDTO.setStreet1("street1" + id);
        prospectCustomerDTO.setStreet2("street2" + id);
        prospectCustomerDTO.setPhoneNumber("phone" + id);
        prospectCustomerDTO.setEmail("email" + id + "@java.fr");
        prospectCustomerDTO.setZipcode("zip" + id);
        prospectCustomerService.updateProspectCustomer(prospectCustomerDTO);
    }

    private void deleteProspectCustomer(final String id) throws RemoveException, CheckException, RemoteException {
    	prospectCustomerService.deleteProspectCustomer("prospect" + id);
    }

    private void checkProspectCustomer(final ProspectCustomerDTO prospectCustomerDTO, final String id) {
        assertEquals("firstname", "firstname" + id, prospectCustomerDTO.getFirstname());
        assertEquals("lastname", "lastname" + id, prospectCustomerDTO.getLastname());
        assertEquals("city", "city" + id, prospectCustomerDTO.getCity());
        assertEquals("street1", "street1" + id, prospectCustomerDTO.getStreet1());
        assertEquals("street2", "street2" + id, prospectCustomerDTO.getStreet2());
        assertEquals("phoneNumber", "phone" + id, prospectCustomerDTO.getPhoneNumber());
        assertEquals("email", "email" + id + "@java.fr", prospectCustomerDTO.getEmail());
        assertEquals("zipcode", "zip" + id, prospectCustomerDTO.getZipcode());
    }
}

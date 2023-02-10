package com.anotherbank.mokabank.authentication.domain.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.anotherbank.mochabank.MochabankApplication;
import com.anotherbank.mochabank.authentication.domain.dto.CustomerDTO;
import com.anotherbank.mochabank.authentication.domain.service.CustomerService;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.DuplicateKeyException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.ObjectNotFoundException;
import com.anotherbank.mochabank.exception.RemoveException;
import com.anotherbank.mochabank.exception.UpdateException;
import com.anotherbank.mokabank.configuration.TestConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class tests the CustomerService class.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(classes = MochabankApplication.class)
public class CustomerServiceTest {
	
	private static Logger logger = LogManager.getLogger(CustomerServiceTest.class);
	
	@Autowired
	private CustomerService customerService;
	
	//==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testServiceFindCustomerWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final String id = "333";
        try {
            customerService.findCustomer(id);
            fail("Object with unknonw id should not be found");
        } catch (FinderException e) {
        }

        // Finds an object with an empty identifier
        try {
            customerService.findCustomer(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            customerService.findCustomer(null);
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
    public void testServiceFindAllCustomers() throws Exception {
        final String id = "333";

        // First findAll
        final int firstSize = findAllCustomers();
        logger.info("firstSize is ... "+firstSize);
        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllCustomers();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteCustomer(id);

        try {
            findCustomer(id);
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
    public void testServiceCreateCustomer() throws Exception {
        final String id = "333";
        CustomerDTO customerDTO = null;

        // Ensures that the object doesn't exist
        try {
            findCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (FinderException e) {
        }

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            customerDTO = findCustomer(id);
        } catch (FinderException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customerDTO, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createCustomer(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        deleteCustomer(id);

        try {
            findCustomer(id);
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
    public void testServiceCreateCustomerWithInvalidValues() throws Exception {
        CustomerDTO customerDTO;

        // Creates an object with a null parameter
        try {
            customerService.createCustomer(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }
        
     // Creates an object with an invalid pwd
        try {
        	customerDTO = new CustomerDTO("username", "firstname", "lastname");
        	customerDTO.setRoleName("ROLE_CUSTOMER");
            customerDTO.setPassword("abc");
            customerService.createCustomer(customerDTO);
            fail("Object with short password should not be created");
        } catch (CreateException e) {
        }

        // Creates an object with empty values
        try {
            customerDTO = new CustomerDTO(new String(), new String(), new String());
            customerDTO.setPassword("validPwd");
            customerService.createCustomer(customerDTO);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            customerDTO = new CustomerDTO(null, null, null);
            customerDTO.setPassword("validPwd");
            customerService.createCustomer(customerDTO);
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
    public void testServiceUpdateCustomer() throws Exception {
        final String id = "333";
        final String updatedId = "334";

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        CustomerDTO customerDTO = null;
        try {
            customerDTO = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customerDTO, id);

        // Updates the object with new values
        updateCustomer(customerDTO, updatedId);

        // Ensures that the object still exists
        CustomerDTO customerUpdated = null;
        try {
            customerUpdated = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkCustomer(customerUpdated, updatedId);

        // Cleans the test environment
        deleteCustomer(id);

        try {
            findCustomer(id);
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
    public void testServiceUpdateCustomerWithInvalidValues() throws Exception {
        CustomerDTO customerDTO;

        // Updates an object with a null parameter
        try {
            customerService.updateCustomer(null);
            fail("Object with null parameter should not be updated");
        } catch (UpdateException e) {
        }

        // Updates an object with empty values
        try {
            customerDTO = new CustomerDTO(new String(), new String(), new String());
            customerService.updateCustomer(customerDTO);
            fail("Object with empty values should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with null values
        try {
            customerDTO = new CustomerDTO(null, null, null);
            customerService.updateCustomer(customerDTO);
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
    public void testServiceDeleteUnknownCustomer() throws Exception {
        final String id = "333";

        // Ensures that the object doesn't exist
        try {
            findCustomer(id);
            fail("Object has not been created it shouldn't be found");
        } catch (FinderException e) {
        }

        // Delete the unknown object
        try {
            deleteCustomer(id);
            fail("Deleting an unknown object should break");
        } catch (RemoveException e) {
        }
    }

    //==================================
    //=          Private Methods       =
    //==================================

    private CustomerDTO findCustomer(final String id) throws FinderException, CheckException {
        final CustomerDTO customerDTO = customerService.findCustomer("custo" + id);
        return customerDTO;
    }

    private int findAllCustomers() throws FinderException, RemoteException {
        try {
            return ((Collection<CustomerDTO>) customerService.findCustomers()).size();
        } catch (ObjectNotFoundException e) {
        	logger.warn("exception is ... "+e.getMessage());
            return 0;
        }
    }

    private void createCustomer(final String id) throws CreateException, CheckException, RemoteException {
        final CustomerDTO customerDTO = new CustomerDTO("custo" + id, "firstname" + id, "lastname" + id);
        customerDTO.setCity("city" + id);
        customerDTO.setStreet1("street1" + id);
        customerDTO.setStreet2("street2" + id);
        customerDTO.setPhoneNumber("phone" + id);
        customerDTO.setEmail("email" + id + "@java.fr");
        customerDTO.setPassword("pwd" + id);
        customerDTO.setZipcode("zip" + id);
        customerDTO.setRoleName("ROLE_CUSTOMER");
        customerService.createCustomer(customerDTO);
    }

    private void updateCustomer(final CustomerDTO customerDTO, final String id) throws UpdateException, CheckException, RemoteException {
        customerDTO.setFirstname("firstname" + id);
        customerDTO.setLastname("lastname" + id);
        customerDTO.setCity("city" + id);
        customerDTO.setStreet1("street1" + id);
        customerDTO.setStreet2("street2" + id);
        customerDTO.setPhoneNumber("phone" + id);
        customerDTO.setEmail("email" + id + "@java.fr");
        customerDTO.setZipcode("zip" + id);
        customerService.updateCustomer(customerDTO);
    }

    private void deleteCustomer(final String id) throws RemoveException, CheckException, RemoteException {
        customerService.deleteCustomer("custo" + id);
    }

    private void checkCustomer(final CustomerDTO customerDTO, final String id) {
        assertEquals("firstname", "firstname" + id, customerDTO.getFirstname());
        assertEquals("lastname", "lastname" + id, customerDTO.getLastname());
        assertEquals("city", "city" + id, customerDTO.getCity());
        assertEquals("street1", "street1" + id, customerDTO.getStreet1());
        assertEquals("street2", "street2" + id, customerDTO.getStreet2());
        assertEquals("phoneNumber", "phone" + id, customerDTO.getPhoneNumber());
        assertEquals("email", "email" + id + "@java.fr", customerDTO.getEmail());
        assertEquals("zipcode", "zip" + id, customerDTO.getZipcode());
    }
	
}

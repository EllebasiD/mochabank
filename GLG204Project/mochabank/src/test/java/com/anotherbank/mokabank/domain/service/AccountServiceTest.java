package com.anotherbank.mokabank.domain.service;

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
import com.anotherbank.mochabank.domain.dto.AccountDTO;
import com.anotherbank.mochabank.domain.service.AccountService;
//import com.anotherbank.mochabank.domain.service.CounterService;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.ObjectNotFoundException;
import com.anotherbank.mochabank.exception.RemoveException;
import com.anotherbank.mokabank.configuration.TestConfig;

/**
 * This class tests the AccountService class.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(classes = MochabankApplication.class)
public class AccountServiceTest {
	
//	private static final String COUNTER_NAME = "Account";
	
	private static Logger logger = LogManager.getLogger(AccountServiceTest.class);
	
	@Autowired
	private AccountService accountService;
	
//	@Autowired
//    private CounterService counterService;
	
	//==================================
	//=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testServiceFindAccountWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final long id = 345L;
		try {
        	accountService.findAccount(id);
            fail("Object with unknonw id should not be found");
        } catch (FinderException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testServiceFindAllAccounts() throws Exception {
		//final Long id = Long.parseLong(counterService.getUniqueId(COUNTER_NAME));
        // First findAll
        final int firstSize = findAllAccounts();
        logger.info("firstSize is ... "+firstSize);
        // Creates an object
        AccountDTO accountDTO = createAccount();

        // Ensures that the object exists
        try {
            findAccount(accountDTO.getId());
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllAccounts();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteAccount(accountDTO.getId());

        try {
            findAccount(accountDTO.getId());
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
    public void testServiceCreateAccount() throws Exception {
		//final Long id = Long.parseLong(counterService.getUniqueId(COUNTER_NAME));
        // Creates an object
		AccountDTO accountDTO = createAccount();
		
        // Ensures that the object exists
		//AccountDTO accountDTO = null;
        try {
        	accountDTO = findAccount(accountDTO.getId());
        } catch (FinderException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkAccount(accountDTO, accountDTO.getId());

        // Cleans the test environment
        deleteAccount(accountDTO.getId());

        try {
            findAccount(accountDTO.getId());
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
    public void testServiceCreateAccountWithInvalidValues() throws Exception {
    	AccountDTO accountDTO;

        // Creates an object with a null parameter
        try {
        	accountService.createAccount(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }
        

        // Creates an object with empty values
        try {
        	accountDTO = new AccountDTO(new String(), new String(), new String(), 30L);
        	accountService.createAccount(accountDTO);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        } catch (NullPointerException e) {        	
        }

        // Creates an object with null values
        try {
        	accountDTO = new AccountDTO(null, null, null, 30L);
        	accountService.createAccount(accountDTO);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }catch (NullPointerException e) {        	
        }
    }

    /**
     * This test ensures that the system cannot remove an unknown object.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
    @Test
    public void testServiceDeleteUnknownAccount() throws Exception {
    	//final Long id = Long.parseLong(counterService.getUniqueId(COUNTER_NAME));
    	final long id = 4598L;
        // Ensures that the object doesn't exist
        try {
            findAccount(id);
            fail("Object has not been created it shouldn't be found");
        } catch (FinderException e) {
        }

        // Delete the unknown object
        try {
            deleteAccount(id);
            fail("Deleting an unknown object should break");
        } catch (RemoveException e) {
        }
    }

    //==================================
    //=          Private Methods       =
    //==================================

    private AccountDTO findAccount(final Long id) throws FinderException, CheckException {
        final AccountDTO accountDTO = accountService.findAccount(id);
        return accountDTO;
    }

    private int findAllAccounts() throws FinderException, RemoteException {
        try {
            return ((Collection<AccountDTO>) accountService.findAccounts()).size();
        } catch (ObjectNotFoundException e) {
        	logger.warn("exception is ... "+e.getMessage());
            return 0;
        }
    }

    private AccountDTO createAccount() throws CreateException, CheckException, RemoteException {
    	//final Long id = Long.parseLong(counterService.getUniqueId(COUNTER_NAME));
    	//final AccountDTO accountDTO = new AccountDTO("accountName", "bankId", "bankName", 20L);
    	final AccountDTO accountDTO = new AccountDTO();
    	accountDTO.setAccountName("accountName");
    	accountDTO.setBankId("bankId");
    	accountDTO.setBankName("bankName");
    	accountDTO.setBalance(20L);
    	//accountDTO.setId(id);
        accountService.createAccount(accountDTO);
        return accountDTO;
    }
    
    

    private void deleteAccount(final Long id) throws RemoveException, CheckException, RemoteException {
    	accountService.deleteAccount(id);
    }

    private void checkAccount(final AccountDTO accountDTO, final Long id) {
    	assertEquals("accountName", "accountName" + id, accountDTO.getAccountName());
        assertEquals("bankId", "bankId" + id, accountDTO.getBankId());
        assertEquals("bankName", "bankName" + id, accountDTO.getBankName());
        assertEquals("username", "username" + id, accountDTO.getCustomerId());
    }
    
}

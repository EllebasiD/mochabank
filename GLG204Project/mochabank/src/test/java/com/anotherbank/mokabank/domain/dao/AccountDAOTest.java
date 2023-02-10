package com.anotherbank.mokabank.domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.anotherbank.mochabank.MochabankApplication;
import com.anotherbank.mochabank.domain.dao.AccountRepository;
import com.anotherbank.mochabank.domain.model.Account;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.DuplicateKeyException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mokabank.configuration.TestConfig;

/**
 * This class tests the AccountDAO class.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(classes = MochabankApplication.class)
public class AccountDAOTest {
	
	@Autowired
	private AccountRepository accountRepository;

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	 @Test
    public void testDomainFindAccountWithInvalidValues() throws Exception {
        // Finds an object with a unknown identifier
        final Long id = 3335L;
        try {
        	findAccount(id);
            fail("Object with unknonw id should not be found");
        } catch (NoSuchElementException e) {
        }

        // Finds an object with a null identifier
        try {
        	accountRepository.findById(null).get();
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
    public void testDomainFindAllAccounts() throws Exception {
        // First findAll
        int firstSize = findAllAccounts();

        // Create an object
        Account account = createAccount();

        // Ensures that the object exists
        try {
        	findAccount(account.getId());
        } catch (NoSuchElementException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        int secondSize = findAllAccounts();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeAccount(account.getId());

        try {
        	findAccount(account.getId());
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
    public void testDomainCreateAccount() throws Exception {
        Account account = null;

        // Creates an object
        account = createAccount();

        // Ensures that the object exists
        try {
        	findAccount(account.getId());
        } catch (NoSuchElementException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkAccount(account);

        // Cleans the test environment
        removeAccount(account.getId());

        try {
            findAccount(account.getId());
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
    public void testDomainUpdateAccount() throws Exception {
        // Creates an object
        Account account = createAccount();

        // Ensures that the object exists
        try {
        	findAccount(account.getId());
        } catch (NoSuchElementException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkAccount(account);

        // Updates the object with new values
        updateAccount(account, "id");

        // Ensures that the object still exists
        Account accountUpdated = null;
        try {
        	accountUpdated = findAccount(account.getId());
        } catch (NoSuchElementException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkUpdatedAccount(accountUpdated);

        // Cleans the test environment
        removeAccount(accountUpdated.getId());

        try {
            findAccount(accountUpdated.getId());
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
    public void testDomainDeleteUnknownAccount() throws Exception {
        // Removes an unknown object
        try {
            removeAccount(90000L);
            fail("Deleting an unknown object should break");
        } catch (EmptyResultDataAccessException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Account findAccount(final Long id) throws NoSuchElementException {
        final Account account = accountRepository.findById(id).get();
        return account;
    }

    private int findAllAccounts() throws FinderException {
        try {        	
            return ((Collection<Account>) accountRepository.findAll()).size();
        } catch (Exception e) {
            return 0;
        }
    }

    private Account createAccount() throws CreateException, CheckException {
        final Account account = new Account("accountName", "bankId", "bankName", 20L);
        accountRepository.save(account);
        return account;
    }

    private void updateAccount(final Account account, final String id) 
    throws ObjectNotFoundException, DuplicateKeyException, CheckException {
    	account.setAccountName("accountName" + id);
    	account.setBankId("bankId" + id);
    	account.setBankName("bankName" + id);
    	account.setBalance(20L);
    	accountRepository.save(account);
    }

    private void removeAccount(final Long id) throws EmptyResultDataAccessException {
        accountRepository.deleteById(id);
    }

    private void checkAccount(final Account account) {
        assertEquals("accountName", "accountName", account.getAccountName());
        assertEquals("bankId", "bankId", account.getBankId());
        assertEquals("bankName", "bankName", account.getBankName());
    }
    
    private void checkUpdatedAccount(final Account account) {
        assertEquals("accountName", "accountName" + "id", account.getAccountName());
        assertEquals("bankId", "bankId" + "id", account.getBankId());
        assertEquals("bankName", "bankName" + "id", account.getBankName());
    }
}

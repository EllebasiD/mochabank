package com.anotherbank.mochabank.domain.service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anotherbank.mochabank.authentication.domain.dao.CustomerRepository;
import com.anotherbank.mochabank.authentication.domain.model.Customer;
import com.anotherbank.mochabank.domain.dao.AccountRepository;
import com.anotherbank.mochabank.domain.dao.TransactionRepository;
import com.anotherbank.mochabank.domain.dto.AccountDTO;
import com.anotherbank.mochabank.domain.dto.OperationDTO;
import com.anotherbank.mochabank.domain.dto.ScheduledOperationDTO;
import com.anotherbank.mochabank.domain.dto.TransactionDTO;
import com.anotherbank.mochabank.domain.model.Account;
import com.anotherbank.mochabank.domain.model.Operation;
import com.anotherbank.mochabank.domain.model.Transaction;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.DuplicateKeyException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.OperationException;
import com.anotherbank.mochabank.exception.RemoveException;
import com.anotherbank.mochabank.exception.TransferException;
import com.anotherbank.mochabank.logging.Trace;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
* This class is a facade for all Account services.
* 
* @author Isabelle Deligniere
*
*/

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	// ======================================
    // =             Attributes             =
    // ======================================
    
	// Used for logging
    protected final transient String _cname = this.getClass().getName();
    
    @Autowired
    private AccountRepository accountRepository;    
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private OperationService operationService;

    @Autowired
    private ScheduledOperationService scheduledOperationService;

    // ======================================
    // =            Constructors            =
    // ======================================
    public AccountServiceImpl() {}

	// ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method creates an Account.
     * 
     * @param accountDTO 	the account to create.
     * @return accountDTO	the created account.
     * 
     * @throws CreateException is thrown if an error occurs during creation.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	@Transactional
	public AccountDTO createAccount(AccountDTO accountDTO) throws CreateException, CheckException {
	      final String mname = "createAccount";
	        Trace.entering(_cname, mname, accountDTO);

	        if (accountDTO == null)
	            throw new CreateException("Account object is null");
	        
	        try {
				if(findAccount(accountDTO.getId())!=null)
					 throw new DuplicateKeyException();
			} catch (FinderException | CheckException  e) {}
	        
	        //Find the customer
	        Customer customer = null;
	        Account account = null;
	        if(accountDTO.getCustomerId() == null) {
	        	account = new Account(accountDTO.getAccountName(), accountDTO.getBankId(), accountDTO.getBankName(), accountDTO.getBalance());
//	        	account.setAccountName(accountDTO.getAccountName());		
//	        	account.setBankId(accountDTO.getBankId());
//	        	account.setBankName(accountDTO.getBankName());
//	        	account.setBalance(accountDTO.getBalance());
	        	account.setId(accountDTO.getId());
	        }else {
	        	if(customerRepository.findById(accountDTO.getCustomerId()).isPresent()) {
		        	customer = customerRepository.findById(accountDTO.getCustomerId()).get();
		        	account = new Account(accountDTO.getAccountName(), accountDTO.getBankId(), accountDTO.getBankName(), accountDTO.getBalance(), customer);
		        	account.setId(accountDTO.getId());
		        	account.setCustomer(customer);
	        	}	
	        }	        
	        
	        account.checkData();
	    	
	        // Creates the object
	        accountRepository.save(account);
	        // Transforms domain object into DTO
	        final AccountDTO result = transformAccount2DTO(account);

	        Trace.exiting(_cname, mname, result);
	 
	        return result;
	}
	/**
     * This method finds an account by its identifier.
     * 
     * @param accountId		the identifier of the account.
     * @return accountDTO	the searched account.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws CheckException is thrown if an error occurs during checking.
     * @return accountDTO		the account.
     */
	@Override
	@Transactional(readOnly=true)
	public AccountDTO findAccount(long accountId) throws FinderException, CheckException {
		 final String mname = "findAccount";
	        Trace.entering(_cname, mname, accountId);

	    	checkId(accountId);
	    	
	    	// Finds the object
	    	Account account = null;
	        if( ! accountRepository.findById(accountId).isPresent())
	        	throw new FinderException("Account must exist to be found");
	        else 
	        	account = accountRepository.findById(accountId).get();

	        // Transforms domain object into DTO
	        final AccountDTO accountDTO = transformAccount2DTO(account);

	        Trace.exiting(_cname, mname, accountDTO);
	        
	        return accountDTO;
	}
	
	/**
     * This method deletes an Account giving its identifier.
     * 
     * @param accountId	the identifier of the account.
     * 
     * @throws RemoveException is thrown if an error occurs during removing.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	@Transactional
	public void deleteAccount(long accountId) throws RemoveException, CheckException {
		final String mname = "deleteAccount";
        Trace.entering(_cname, mname, accountId);

    	checkId(accountId);
    	
    	Account account;
        // Checks if the object exists
        if( ! accountRepository.findById(accountId).isPresent())
        	throw new RemoveException("Account must exist to be deleted");
        else 
        	account=accountRepository.findById(accountId).get();
        // Deletes the object
        accountRepository.delete(account);
            	
	}
	/**
     * This method finds all the accounts.
     * 
     * @return accountsDTO	all the accounts.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     */
	@Override
	@Transactional(readOnly=true)
	public Iterable<AccountDTO> findAccounts() throws FinderException {
		final String mname = "findAccounts";
        Trace.entering(_cname, mname);

        // Finds all the objects
        final Iterable<Account> accounts = accountRepository.findAll();

        // Transforms domain objects into DTOs
        Iterable<AccountDTO> accountsDTO = transformAccounts2DTOs(accounts);
        
        Integer integer = Integer.valueOf(((Collection<AccountDTO>) accountsDTO).size());
        Trace.exiting(_cname, mname, integer);
        return accountsDTO;
	}	
	/**
     * This method finds an account by its customer identifier.
     * 
     * @param username		the identifier of the customer account.
     * @return accountDTO	the searched account.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     * @return accountDTO		the account.
     */
	public AccountDTO findAccountByCustomerId(final String username) throws FinderException {
		final String mname = "findAccountByCustomerId";
        Trace.entering(_cname, mname, username);
    	
    	// Finds the object
        Customer customer = null;
        if( ! customerRepository.findById(username).isPresent())
        	throw new FinderException("Customer must exist to be found");
        else 
        	customer = customerRepository.findById(username).get();
        
    	Account account = accountRepository.findByCustomer(customer);

        // Transforms domain object into DTO
        final AccountDTO accountDTO = transformAccount2DTO(account);

        Trace.exiting(_cname, mname, accountDTO);
        
        return accountDTO;
	}

	/**
     * This method checks the transactions of an account.
     * 
     * @param accountId the identifier of the account.
     * @return operations	all the operations.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     * @return transactions all the transactions of the account.
     */
	@Override
	@Transactional(readOnly=true)
	public Iterable<Operation> checkTransactions(long accountId) throws FinderException {
		final String mname = "checkTransactions";
        Trace.entering(_cname, mname, accountId);
        
        try {
			checkId(accountId);
		} catch (CheckException e) {
			e.printStackTrace();
		}        
        
        Set<Transaction> transactions = null;
        Account account = null;
        // Find the account
        account = accountRepository.findById(accountId).get();
        // Find all the objects
        if( ! accountRepository.findById(accountId).isPresent()) {
        	throw new FinderException("Account must exist to be found");
        }else {
        	transactions = transactionRepository.findAllByAccountA(account);
        }
        // Keep only Operations        
        List<Operation> operationsList = new ArrayList<Operation>();
        for(Transaction transaction : transactions) {
        	if(transaction instanceof Operation) {
        		operationsList.add((Operation) transaction);
        	}
        }
        // Sort by transaction date
        Collections.sort(operationsList, new Comparator<Operation>() {  
            public int compare(Operation a, Operation b) {
                return a.getTransactionDate().compareTo(b.getTransactionDate());
            }
        });
        
        Set<Operation> operations = new LinkedHashSet<Operation>(operationsList);
        
		Integer integer = Integer.valueOf((transactions).size());
        Trace.exiting(_cname, mname, integer);
        
		return operations;	
	}
	/**
     * This method makes a deposit on the account.
     *
     * @param accountId	The identifier of the account receiving the deposit. 
     * @param amount	The amount to be deposited into the account.
     * @return result	the result of the operation.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws OperationException is thrown if an error occurs during the operation.
     */
	@Override
	@Transactional
	public boolean makeDeposit(final long accountId, final long amount) throws FinderException, OperationException {
		final String mname = "makeDeposit";
        Trace.entering(_cname, mname, accountId);
        boolean result = false;
        
    	try {
			checkId(accountId);
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	
    	// Finds the object
    	Account account = null;
        if( ! accountRepository.findById(accountId).isPresent())
        	throw new FinderException("Account must exist to be found");
        else 
        	account = accountRepository.findById(accountId).get();
        
        // Credit the balance of the Account
        if(amount <= 0) {
        		throw new OperationException("Amount must be > 0");
        }else {
			result = account.credit(amount);
			// Get the transactions list of account
            Set<TransactionDTO> transactions = null;
			try {
				transactions = findAccountByCustomerId(account.getCustomer().getUsername()).getTransactionsList();
			}catch (FinderException e) {
				e.getMessage();
			} catch (Exception e) {
				e.getMessage();
			} 	
			 // Create Transaction    		
    		OperationDTO operationDTO = new OperationDTO();
    		operationDTO.setDescription("Dépôt");
    		operationDTO.setType("Crédit");
    		operationDTO.setAccountAId(accountId);
    		operationDTO.setAmount(amount);
    		operationDTO.setResult(result);
			try {
				operationService.createOperation(operationDTO);	    		
			}catch (CreateException | CheckException e) {
				e.getMessage();
			} catch (Exception e) {
				e.getMessage();
			}  
			// Add transaction to the transactions list of Account
	        transactions.add(operationDTO);     
	        
	    }
            
        
        Trace.exiting(_cname, mname, result);
        
        return result;
	}	
	
	
	/**
     * This method makes an internal transfer from the customer account to a recipient account.
     * 
     * @param accountIdA	The identifier of the account issuing the deposit. 
     * @param accountIdB	The identifier of the account receiving the deposit. 
     * @param amount	The amount to be deposited into the account.
     * @return result	the result of the operation.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws OperationException is thrown if an error occurs during the operation.
     * @throws TransferException is thrown if an error occurs during the transfer.
     */
	@Override
	@Transactional
	public boolean orderInternalBankTransfer(final long accountIdA, final long accountIdB, long amount) throws FinderException, OperationException, TransferException {
		final String mname = "orderInternalTransfer";
        Trace.entering(_cname, mname);
        boolean resultA = false;
        boolean resultB = false;
     
    	try {
			checkId(accountIdA);
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	
    	try {
			checkId(accountIdB);
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	
    	// Find the account A
    	Account accountA = null;
        if( ! accountRepository.findById(accountIdA).isPresent()){
			throw new FinderException("Account must exist to be found");
		}else {
			// Get account A
			accountA = accountRepository.findById(accountIdA).get();
		}       	
        
        // Debit the balance of the Account A
        // Case without authorized overdraft 
        if(amount <= 0){
			throw new OperationException("Amount must be > 0");
		}else if(accountA.getBalance() < amount) {
			throw new TransferException("Insufficient Balance");
		}else {
			// Debit account A
			resultA = accountA.debit(amount);
			OperationDTO operationDTOAD = new OperationDTO();
			// Get the transactions list of account A
            Set<TransactionDTO> transactionsA = null;
			try {
				transactionsA = findAccountByCustomerId(accountA.getCustomer().getUsername()).getTransactionsList();
			}catch (FinderException e) {
				e.getMessage();
			} catch (Exception e) {
				e.getMessage();
			} 						
			// Find the object B
			Account accountB = null;
			// Case Account B not found
			if( ! accountRepository.findById(accountIdB).isPresent()) {
				// If Account B isn't found, credit of Account B will also fail, so recredit Account A        	
				// Create debit Transaction for account A without Account B				
				operationDTOAD.setDescription("Virement");
				operationDTOAD.setType("Débit");
				operationDTOAD.setAccountAId(accountIdA);
				operationDTOAD.setAmount(amount);
				operationDTOAD.setResult(true);					
				try {
					operationService.createOperation(operationDTOAD);	    		
				}catch (CreateException | CheckException e) {
					e.getMessage();
				} catch (Exception e) {
					e.getMessage();
				}  
				// Add debit transaction to the transactions list of Account A				
				transactionsA.add(operationDTOAD);
				// Recredit Account A
				boolean resultR = accountA.credit(amount);				
				// Create recredit Transaction for account A   				
				OperationDTO operationDTOAR = new OperationDTO();
				operationDTOAR.setDescription("Recrédit échec virement");
				operationDTOAR.setType("Crédit");
				operationDTOAR.setAccountAId(accountIdA);
				operationDTOAR.setAmount(amount);
				operationDTOAR.setResult(resultR);
				try {			
					operationService.createOperation(operationDTOAR);	    													
				}catch (CreateException | CheckException e) {
					e.getMessage();
				} catch (Exception e) {
					e.getMessage();
				}
				// Add recredit transaction to the transactions list of Account A
				transactionsA.add(operationDTOAR);
				// Create transfer exception
				throw new TransferException("Unknown recipient account");
			}else {		
				// Get account B
				accountB = accountRepository.findById(accountIdB).get();
				// Get the transactions list of account B
				Set<TransactionDTO> transactionsB = null;
				try {
					transactionsB = findAccountByCustomerId(accountB.getCustomer().getUsername()).getTransactionsList();
				}catch (FinderException e) {
					e.getMessage();
				} catch (Exception e) {
					e.getMessage();
				} 
				// Create debit Transaction for account A with Account B
				//OperationDTO operationDTOA = new OperationDTO();
				operationDTOAD.setDescription("Virement");
				operationDTOAD.setType("Débit");
				operationDTOAD.setAccountAId(accountIdA);
				operationDTOAD.setAccountBId(accountIdB);
				operationDTOAD.setAmount(amount);
				operationDTOAD.setResult(resultA);													
				try {			
					operationService.createOperation(operationDTOAD);	    								
				}catch (CreateException | CheckException e) {
					e.getMessage();
				} catch (Exception e) {
					e.getMessage();
				}
				// Add transaction to the transactions list of Account A
				transactionsA.add(operationDTOAD);								
				// Credit the balance of the Account B
				resultB = accountB.credit(amount);				
				// Create credit Transaction for account B 
				OperationDTO operationDTOB = new OperationDTO();
				operationDTOB.setDescription("Virement");
				operationDTOB.setType("Crédit");
				operationDTOB.setAccountAId(accountIdB);
				operationDTOB.setAccountBId(accountIdA);
				operationDTOB.setAmount(amount);
				operationDTOB.setResult(resultB);
				
				try {			
					operationService.createOperation(operationDTOB);	    		
						
				}catch (CreateException | CheckException e) {
					e.getMessage();
				} catch (Exception e) {
					e.getMessage();
				}
				// Add transaction to the transactions list of Account B
				transactionsB.add(operationDTOB);
			}			
        }      
		boolean result = resultA & resultB;
    	
		Trace.exiting(_cname, mname, result);
        
        return result;
	}
	
	
	/**
     * This method makes an external transfer from the customer account to a recipient account.
     * 
     * @param accountIdA	The identifier of the account issuing the deposit. 
     * @param accountIdB	The identifier of the account receiving the deposit. 
     * @param amount	The amount to be deposited into the account.
     * @return result	the result of the operation.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws OperationException is thrown if an error occurs during the operation.
     * @throws TransferException is thrown if an error occurs during the transfer.
     */
	@Override
	@Transactional
	public boolean orderExternalBankTransfer(final long accountIdA, final long accountIdB, long amount) throws FinderException, OperationException, TransferException {
		final String mname = "orderExternalTransfer";
        Trace.entering(_cname, mname);
        boolean resultA = false;
        boolean resultB = false;

    	try {
			checkId(accountIdA);
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	
    	try {
			checkId(accountIdB);
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	
    	// Find the account A
    	Account accountA = null;
        if( ! accountRepository.findById(accountIdA).isPresent()){
			throw new FinderException("Account must exist to be found");
		}else {
			// Get account A
			accountA = accountRepository.findById(accountIdA).get();
		}       	
        
        // Debit the balance of the Account A
        // Case without authorized overdraft 
        if(amount <= 0){
			throw new OperationException("Amount must be > 0");
		}else if(accountA.getBalance() < amount) {
			throw new TransferException("Insufficient Balance");
		}else {
			// Debit account A
			resultA = accountA.debit(amount);
			OperationDTO operationDTOAD = new OperationDTO();
			// Get the transactions list of account A
            Set<TransactionDTO> transactionsA = null;
			try {
				transactionsA = findAccountByCustomerId(accountA.getCustomer().getUsername()).getTransactionsList();
			}catch (FinderException e) {
				e.getMessage();
			} catch (Exception e) {
				e.getMessage();
			} 							
			// Send transfer to Other Bank
			ObjectMapper mapper = new ObjectMapper();
			try {
				String accountIdBJSON = mapper.writeValueAsString(accountIdB); 
				String amountJSON = mapper.writeValueAsString(amount); 
				resultB = HTTPSender.send(accountIdBJSON, amountJSON);				
			}catch(Exception e) {
		    	 e.getMessage();
		    }
			
			if(resultB == true) {
				// Create debit Transaction for account A with Account B
				operationDTOAD.setDescription("Virement externe");
				operationDTOAD.setType("Débit");
				operationDTOAD.setAccountAId(accountIdA);
				operationDTOAD.setAccountBId(accountIdB);
				operationDTOAD.setAmount(amount);
				operationDTOAD.setResult(true);	
				try {
					operationService.createOperation(operationDTOAD);	    		
				}catch (CreateException | CheckException e) {
					e.getMessage();
				} catch (Exception e) {
					e.getMessage();
				}
				// Add debit transaction to the transactions list of Account A				
				transactionsA.add(operationDTOAD);
			}else {
				// Create debit Transaction for account A without Account B				
				operationDTOAD.setDescription("Virement externe");
				operationDTOAD.setType("Débit");
				operationDTOAD.setAccountAId(accountIdA);
				operationDTOAD.setAmount(amount);
				operationDTOAD.setResult(true);					
				try {
					operationService.createOperation(operationDTOAD);	    		
				}catch (CreateException | CheckException e) {
					e.getMessage();
				} catch (Exception e) {
					e.getMessage();
				}  
				// Add debit transaction to the transactions list of Account A				
				transactionsA.add(operationDTOAD);
				// Recredit Account A
				boolean resultR = accountA.credit(amount);				
				// Create recredit Transaction for account A   				
				OperationDTO operationDTOAR = new OperationDTO();
				operationDTOAR.setDescription("Recrédit échec virement");
				operationDTOAR.setType("Crédit");
				operationDTOAR.setAccountAId(accountIdA);
				operationDTOAR.setAmount(amount);
				operationDTOAR.setResult(resultR);
				try {			
					operationService.createOperation(operationDTOAR);	    													
				}catch (CreateException | CheckException e) {
					e.getMessage();
				} catch (Exception e) {
					e.getMessage();
				}
				// Add recredit transaction to the transactions list of Account A
				transactionsA.add(operationDTOAR);
				// Create transfer exception
				throw new TransferException("Transfer failed");
			}
						
        }      
		boolean result = resultA & resultB;
    	
		Trace.exiting(_cname, mname, result);
        
        return result;
	}
	
	
	/**
     * This method receives an external transfer and makes the deposit on the account.
     *
     * @param accountId	The identifier of the account receiving the external transfer. 
     * @param amount	The amount to be deposited into the account.
     * @return result	the result of the operation.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws OperationException is thrown if an error occurs during the operation.
     */
	@Override
	@Transactional
	public boolean receiveExternalTransfer(final long accountId, final long amount) throws FinderException, OperationException {
		final String mname = "receiveExternalTransfer";
        Trace.entering(_cname, mname, accountId);
        boolean result = false;
        
    	try {
			checkId(accountId);
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	
    	// Finds the object
    	Account account = null;
        if( ! accountRepository.findById(accountId).isPresent())
        	throw new FinderException("Account must exist to be found");
        else 
        	account = accountRepository.findById(accountId).get();
        
        // Credit the balance of the Account
        if(amount <= 0) {
        		throw new OperationException();
        }else {
			result = account.credit(amount);
			// Get the transactions list of account
            Set<TransactionDTO> transactions = null;
			try {
				transactions = findAccountByCustomerId(account.getCustomer().getUsername()).getTransactionsList();
			}catch (FinderException e) {
				e.getMessage();
			} catch (Exception e) {
				e.getMessage();
			} 	
			 // Create Transaction    		
    		OperationDTO operationDTO = new OperationDTO();
    		operationDTO.setDescription("Virement externe");
    		operationDTO.setType("Crédit");
    		operationDTO.setAccountAId(accountId);
    		operationDTO.setAmount(amount);
    		operationDTO.setResult(result);
			try {
				operationService.createOperation(operationDTO);	    		
			}catch (CreateException | CheckException e) {
				e.getMessage();
			} catch (Exception e) {
				e.getMessage();
			}  
			// Add transaction to the transactions list of Account
	        transactions.add(operationDTO);     
	        
	    }
            
        
        Trace.exiting(_cname, mname, result);
        
        return result;
	}	
	
	
	
	/**
     * This method schedules an internal transfer from the customer account to a recipient account.
     * 
     * @param accountIdA	The identifier of the account issuing the deposit. 
     * @param accountIdB	The identifier of the account receiving the deposit. 
     * @param amount	The amount to be deposited into the account.
     * @param minute	The minute wanted to schedule the transfer.
     * @param frequency	The frequency to schedule the transfer.
     * @return result	the result of the operation.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws OperationException is thrown if an error occurs during the operation. 
     */
	@Override
	@Transactional	
	public boolean scheduleAutoBankTransfer(final long accountIdA, final long accountIdB, final long amount,  final String minute, final String frequency) throws FinderException, OperationException {
		final String mname = "scheduleAutoBankTransfer";
        Trace.entering(_cname, mname);
        boolean result = false;
     
    	try {
			checkId(accountIdA);
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	
    	try {
			checkId(accountIdB);
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	
    	// Finds the object A
    	Account accountA = null;
        if( ! accountRepository.findById(accountIdA).isPresent())
        	throw new FinderException("Account must exist to be found");
        else 
        	accountA = accountRepository.findById(accountIdA).get();
            	
        // Finds the object B
    	Account accountB = null;
        if( ! accountRepository.findById(accountIdB).isPresent()) {
        	throw new FinderException("Account must exist to be found");
		}else {			
        	accountB = accountRepository.findById(accountIdB).get();
		}        
        // Check amount
        if(amount <= 0) {
    		throw new OperationException("Amount must be > 0");
        }
        // Create cron expression
    	String cronExpression = null;
    	// Check minute
    	if(minute == null) {
    		throw new OperationException("Minute null");
    	}else if (minute.equals("0") || minute.contains("-")){
    		throw new OperationException("Minute must be > 0");
    	// Check frequency
    	}else if(frequency == null) {
    		throw new OperationException("Frequence null");    	 
    	}else if(frequency.equals("unique")) {    		
    		// One time at x minute 
    		if(Year.now().getValue() == 2021) {
    			LocalDateTime localDate = LocalDateTime.now();
    			String hour = Integer.toString(localDate.getHour());
    			String today = Integer.toString(localDate.getDayOfMonth());		
    			String month = Integer.toString(localDate.getMonthValue());    			
    			cronExpression = "0 " + minute + " " + hour + " " + today + " " + month +" ?";
    		}	    	
	    }else if(frequency.equals("recurrent") && minute.equals("1")){
	    	// top of every minute of every day 
	    	cronExpression = "0 * * * * *";	    	
	    }else {
	    	// Every x minute 
	    	cronExpression = "0 */"+ minute + " * * * *";
	    }  	
                
        // Create scheduled operation     
        ScheduledOperationDTO scheduledOperationDTO = new ScheduledOperationDTO();
        scheduledOperationDTO.setAccountAId(accountA.getId());
        scheduledOperationDTO.setAccountBId(accountB.getId());
        scheduledOperationDTO.setAmount(amount);
        scheduledOperationDTO.setCronExpression(cronExpression);
        scheduledOperationDTO.setDescription("Virement automatique");
        scheduledOperationDTO.setType("Virement");

		try {
			scheduledOperationService.createScheduledOperation(scheduledOperationDTO);
			result = true;
		} catch (CreateException | CheckException e) {
			e.printStackTrace();
		}   	
    	
    	Trace.exiting(_cname, mname, result);
        
        return result;
	}
		

	// ======================================
    // =          Private Methods           =
    // ======================================
    /**
     * This method transforms an Account object to an AccountDTO object.
     * 
     * @param account		the Account object.
     * @return accountDTO	the AccountDTO object.
     */
	private AccountDTO transformAccount2DTO(Account account) {
		final AccountDTO accountDTO = new AccountDTO();		
		accountDTO.setAccountName(account.getAccountName());
		accountDTO.setId(account.getId());
		accountDTO.setBankId(account.getBankId());
		accountDTO.setBankName(account.getBankName());
		accountDTO.setBalance(account.getBalance());
		if(account.getCustomer() != null) {
			accountDTO.setCustomerId(account.getCustomer().getUsername());			
		}        
        return accountDTO;
	}
	/**
     * This method transforms an iterable collection of Account objects to an iterable collection of AccountDTO objects.
     * 
     * @param accounts 		the iterable collection of Account objects.
     * @return accountsDTO		the iterable collection of AccountDTO objects
     */
	private Iterable<AccountDTO> transformAccounts2DTOs(Iterable<Account> accounts) {
		final Collection<AccountDTO> accountsDTO = new ArrayList<>();
        for (Iterator<Account> iterator = accounts.iterator(); iterator.hasNext();) {
            final Account account = (Account) iterator.next();
            accountsDTO.add(transformAccount2DTO(account));
        }
		
        return accountsDTO;
	}
	/**
     * This method checks the validity of the identifier. 
     * 
     * @param id	the identifier.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	private void checkId(final long id) throws CheckException {
    	if ( id == 0)
    		throw new CheckException("Id should not be 0");    	
    }
	

}

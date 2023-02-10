package com.anotherbank.mochabank.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.anotherbank.mochabank.domain.dto.AccountDTO;
import com.anotherbank.mochabank.domain.model.Operation;
import com.anotherbank.mochabank.domain.model.Transaction;
import com.anotherbank.mochabank.domain.service.AccountService;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.OperationException;
import com.anotherbank.mochabank.exception.TransferException;
import com.anotherbank.mochabank.logging.Trace;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AccountController {
	// Used for logging
	private final transient String _cname = this.getClass().getName();

	protected String getCname() {
		return _cname;
	}

	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(AccountController.class);
				
	@Autowired
	private AccountService accountService;
		
		
	/** Check balance **/
	@GetMapping("/check-balance")
    public String checkBalance(Model model) {
		final AccountDTO accountDTO;
    	Long accountId;
	    String username;
	    try {
	    	// Find the accountId of current logged Customer
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Object principal = auth.getPrincipal();
	    	
	    	if(principal instanceof UserDetails) {
	    		username = ((UserDetails)principal).getUsername();
	    	}else {
	    		username = principal.toString();
	    	}
	    	accountId = accountService.findAccountByCustomerId(username).getId();
	    	// Find account 
	    	accountDTO = accountService.findAccount(accountId);
	    	model.addAttribute("accountDTO", accountDTO);
	    	model.addAttribute("accountBalance", accountDTO.getBalance());
	    } catch (FinderException e) {
        	model.addAttribute("exception", "Account not found");
            return "error";
	    } catch (Exception e) {
	        model.addAttribute("exception", "Cannot display the account name and account bank name :" + e.getMessage());
	        return "error";
	    }      		    	
    	return "check-balance";
    }
		
		
	/** Check transactions **/
	@GetMapping("/check-transactions")
    public String checkTransactions(Model model) {
		final String mname = "checkTransactions";
	    Trace.entering(getCname(), mname);
		Long accountId;
		String username;
	    try {
	    	// Find the accountId of current logged Customer
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Object principal = auth.getPrincipal();
	    	
	    	if(principal instanceof UserDetails) {
	    		username = ((UserDetails)principal).getUsername();
	    	}else {
	    		username = principal.toString();
	    	}
	    	accountId = accountService.findAccountByCustomerId(username).getId();
		    	
	    	// Find account
	    	AccountDTO accountDTO = accountService.findAccount(accountId);
	    	model.addAttribute("accountDTO", accountDTO);
	    	Set<Operation> transactionsList = null;
	    	transactionsList = (Set<Operation>) accountService.checkTransactions(accountId);
		    	
	    	// Sort by transaction date
	    	List<Transaction> transactions = new ArrayList<Transaction>(transactionsList);
	    	Collections.reverse(transactions);
	    	model.addAttribute("transactions", transactions);
	    } catch (FinderException e) {
        	model.addAttribute("exception", "Account not found");
            return "error";
	    } catch (Exception e) {
	    	Trace.throwing(getCname(), mname, e);
	        model.addAttribute("exception", "Cannot display the account name and account bank name :" + e.getMessage());
	        return "error";
	    }   		 
		return "check-transactions";			
	}
		
		
		
	/** Make Deposit **/
	@GetMapping(path="/make-deposit")
	public String makeDeposit(Model model) {
		Long accountId;
		String username;
	    try {
	    	// Find the accountId of current logged Customer
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Object principal = auth.getPrincipal();
	    	
	    	if(principal instanceof UserDetails) {
	    		username = ((UserDetails)principal).getUsername();
	    	}else {
	    		username = principal.toString();
	    	}
	    	accountId = accountService.findAccountByCustomerId(username).getId();
		    	
	    	// Find account
	    	AccountDTO accountDTO = accountService.findAccount(accountId);
	    	model.addAttribute("accountDTO", accountDTO);
	    } catch (FinderException e) {
        	model.addAttribute("exception", "Account not found");
            return "error";
	    } catch (Exception e) {
	        model.addAttribute("exception", "Cannot display the account name and account bank name :" + e.getMessage());
	        return "error";
	    }  
		return "make-deposit";	
  	}

	@PostMapping(path= "/make-deposit")
	public String makeDeposit(Model model, @RequestParam(value = "amount") long amount, Authentication authentication)  {
		final String mname = "makeDeposit";
	    Trace.entering(getCname(), mname);
		    
	    long accountId;
	    boolean result = false;
	    String username;
	    try {
	    	//Find the accountId of current logged Customer
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Object principal = auth.getPrincipal();
		    	
	    	if(principal instanceof UserDetails) {
	    		username = ((UserDetails)principal).getUsername();
	    	}else {
	    		username = principal.toString();
	    	}
	    	accountId = accountService.findAccountByCustomerId(username).getId();
	    } catch (FinderException e) {
        	model.addAttribute("exception", "Account not found");
            return "error";
	    } catch (Exception e) {
	        Trace.throwing(getCname(), mname, e);
	        model.addAttribute("exception", "Cannot make the deposit :" + e.getMessage());
	        return "error";
	    } 
		    	
	    try {	
	    	// Make the deposit
	    	result = accountService.makeDeposit(accountId, amount);
		    	
	    	// Success message
            model.addAttribute("depositDone", result);
	    } catch (FinderException e) {
        	model.addAttribute("exception", "Account not found");
            return "error";	
	    } catch (OperationException e) {
	    	model.addAttribute("exception", e.getMessage());
            return "error";
	    } catch (Exception e) {
            Trace.throwing(getCname(), mname, e);
            model.addAttribute("exception", "Cannot make the deposit :" + e.getMessage());
            return "error";
        }

        return "make-deposit";
  	}
		
		
		
	/** Order transfer **/
	@GetMapping(path="/order-transfer")
	public String orderTransfer(Model model) {
		Long accountId;
		String username;
	    try {
	    	// Find the accountId of current logged Customer
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Object principal = auth.getPrincipal();
		    	
	    	if(principal instanceof UserDetails) {
	    		username = ((UserDetails)principal).getUsername();
	    	}else {
	    		username = principal.toString();
	    	}
	    	accountId = accountService.findAccountByCustomerId(username).getId();
		    	
	    	// Find account
	    	AccountDTO accountDTO = accountService.findAccount(accountId);
	    	model.addAttribute("accountDTO", accountDTO);
	    } catch (FinderException e) {
        	model.addAttribute("exception", "Account not found");
            return "error";
	    } catch (Exception e) {
	        model.addAttribute("exception", "Cannot display the account name and account bank name :" + e.getMessage());
	        return "error";
	    }  
		return "order-transfer";	
  	}

	@PostMapping(path= "/order-transfer")
	public String orderTransfer(Model model, @RequestParam(value = "accountIdB") long accountIdB, @RequestParam(value = "bankNameB") String bankNameB,  @RequestParam(value = "amount") long amount, Authentication authentication)  {
		final String mname = "orderInternalTransfer";
    	Trace.entering(getCname(), mname);
		    
	    long accountIdA;
	    boolean result = false;
	    String username;
		    
	    //Find the accountId of current logged Customer
	    try {		    	
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Object principal = auth.getPrincipal();
		    	
	    	if(principal instanceof UserDetails) {
	    		username = ((UserDetails)principal).getUsername();
	    	}else {
	    		username = principal.toString();
	    	}
	    	accountIdA = accountService.findAccountByCustomerId(username).getId();	    	
	    } catch (FinderException e) {
        	model.addAttribute("exception", "Transfer failed : account not found");
            return "error";
	    } catch (Exception e) {
	        Trace.throwing(getCname(), mname, e);
	        model.addAttribute("exception", "Cannot make the transfer :" + e.getMessage());
	        return "error";
	    } 
		    
	    // Check if Account B is a Mocha Bank account 
	    AccountDTO accountDTOB = null;
	    try {
			accountDTOB = accountService.findAccount(accountIdB);
		} catch (FinderException | CheckException e1) {
			e1.getMessage();
		}
	    if(accountDTOB.getBankId().equals("1234") && bankNameB.toUpperCase().contains("MOCHA") && accountDTOB.getBankName().toUpperCase().contains("MOCHA")) {
	    	// Make the internal transfer	
		    try {			    	
		    	result = accountService.orderInternalBankTransfer(accountIdA, accountIdB, amount);    			    	
		    	if(result == true)
			    	// Success message
		            model.addAttribute("orderTransferDone", true);
		    } catch (FinderException e) {
	        	model.addAttribute("exception", "Transfer failed : account not found");
	            return "error";
		    } catch (OperationException e) {
		    	model.addAttribute("exception", "Transfer failed : " + e.getMessage());
	            return "error";
		    } catch(TransferException e) {
		    	model.addAttribute("exception", "Transfer failed : " + e.getMessage());
	            return "error";
		    } catch (Exception e) {
	            Trace.throwing(getCname(), mname, e);
	            model.addAttribute("exception", "Cannot make the transfer :" + e.getMessage());
	            return "error";
	        }
	    }else if(accountDTOB.getBankId().equals("5678") && bankNameB.toUpperCase().contains("OTHER") && accountDTOB.getBankName().toUpperCase().contains("OTHER")) {
	    	// Make the external transfer	
		    try {		
		    	result = accountService.orderExternalBankTransfer(accountIdA, accountIdB, amount);    			    	
		    	if(result == true)
			    	// Success message
		            model.addAttribute("orderTransferDone", true);
		    } catch (FinderException e) {
	        	model.addAttribute("exception", "Transfer failed : account not found");
	            return "error";
		    } catch (OperationException e) {
		    	model.addAttribute("exception", "Transfer failed : " + e.getMessage());
	            return "error";
		    } catch(TransferException e) {
		    	model.addAttribute("exception", "Transfer failed : " + e.getMessage());
	            return "error";
		    } catch (Exception e) {
	            Trace.throwing(getCname(), mname, e);
	            model.addAttribute("exception", "Cannot make the transfer :" + e.getMessage());
	            return "error";
	        }		    	
	    }else {
	    	model.addAttribute("exception", "Transfer failed : account not found");
            return "error";
	    }
		    		    		    
        return "order-transfer";
  	}
		
		
	/** Receive external transfer **/
	@GetMapping(path="/receive-transfer")
	public void externalTransfer(@RequestParam(value = "param1") String param1, @RequestParam(value = "param2") String param2, HttpServletResponse response) throws IOException {
		final String mname = "receiveExternalTransfer";
		Trace.entering(getCname(), mname);
	     
		try {			
			ObjectMapper mapper = new ObjectMapper();			
			String result = null;
			Long accountId;
			Long amount;				
			// Get accountId
			accountId = Long.valueOf(mapper.readValue(URLDecoder.decode(param1, "UTF-8"), String.class));
			// Get amount
			amount = Long.valueOf(mapper.readValue(URLDecoder.decode(param2, "UTF-8"), String.class));
			try {
				// Find accountId
				if(accountService.findAccount(accountId) != null) {
					// Receive the transfer
					result = String.valueOf(accountService.receiveExternalTransfer(accountId, amount));
				}
			}catch(FinderException e){
				e.getMessage();
			}catch(OperationException e) {
				e.getMessage();
			}catch(Exception e) {
				e.getMessage();
			}			
			// Set the response
			response.setContentType("text/html");
			final PrintWriter out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			 e.getMessage();
		}
							
	}
		
		
		
		
	/** Program transfer **/
	@GetMapping(path="/program-transfer")
	public String programTransfer(Model model) {
		Long accountId;
		String username;
	    try {
	    	// Find the accountId of current logged Customer
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Object principal = auth.getPrincipal();
		    	
	    	if(principal instanceof UserDetails) {
	    		username = ((UserDetails)principal).getUsername();
	    	}else {
	    		username = principal.toString();
	    	}
	    	accountId = accountService.findAccountByCustomerId(username).getId();
		    	
	    	// Find account
	    	AccountDTO accountDTO = accountService.findAccount(accountId);
	    	model.addAttribute("accountDTO", accountDTO);
		    			    	
	    } catch (FinderException e) {
        	model.addAttribute("exception", "Account not found");
            return "error";
	    } catch (Exception e) {
	        model.addAttribute("exception", "Cannot display the account name and account bank name :" + e.getMessage());
	        return "error";
	    }  
		return "program-transfer";	
  	}
		
	@PostMapping(path= "/program-transfer")
	public String createProgramedTransfer(Model model, @RequestParam(value = "accountIdB") long accountIdB, @RequestParam(value = "amount") long amount, @RequestParam(value = "minute") String minute, @RequestParam(value = "frequency") String frequency, Authentication authentication)  {
		final String mname = "scheduleAutoBankTransfer";
    	Trace.entering(getCname(), mname);
		    
	    long accountIdA;
	    boolean result = false;
	    String username;
		    
	    //Find the accountId of current logged Customer
	    try {		    	
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Object principal = auth.getPrincipal();
		    	
	    	if(principal instanceof UserDetails) {
	    		username = ((UserDetails)principal).getUsername();
	    	}else {
	    		username = principal.toString();
	    	}
	    	accountIdA = accountService.findAccountByCustomerId(username).getId();	    	
	    } catch (FinderException e) {
        	model.addAttribute("exception", "Automatic transfer resquest failed : account not found");
            return "error";
	    } catch (Exception e) {
	        Trace.throwing(getCname(), mname, e);
	        model.addAttribute("exception", "Cannot make the transfer :" + e.getMessage());
	        return "error";
	    } 
		   
	    // Create the schedule operation	
	    try {	
		    result = accountService.scheduleAutoBankTransfer(accountIdA, accountIdB, amount, minute, frequency);    			    	
	    	// Add result message
	    	if(result == true) {
	    		model.addAttribute("programTransferDone", true);
	    	}else {
	    		model.addAttribute("programTransferFail", true);
	    	}
	    } catch (FinderException e) {
        	model.addAttribute("exception", "Automatic transfer resquest failed : account not found");
            return "error";
	    } catch (OperationException e) {
	    	model.addAttribute("exception", "Automatic transfer resquest failed : " + e.getMessage());
            return "error";
	    } catch (Exception e) {
            Trace.throwing(getCname(), mname, e);
            model.addAttribute("exception", "Cannot create the transfer request :" + e.getMessage());
            return "error";
        }
	   
        return "program-transfer";
  	}		
				
}

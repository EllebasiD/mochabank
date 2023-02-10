package com.anotherbank.mochabank.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.anotherbank.mochabank.domain.dto.AccountDTO;
import com.anotherbank.mochabank.domain.dto.ScheduledOperationDTO;
import com.anotherbank.mochabank.domain.service.AccountService;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.OperationException;
import com.anotherbank.mochabank.exception.TransferException;

/**
* This class executes the scheduled operations.
* 
* @author Isabelle Deligniere
*
*/
public class RunnableTask implements Runnable{
    
	private ScheduledOperationDTO scheduledOperationDTO;
	private String cronExpression;
	
	@Autowired
	private AccountService accountService;
	
    public RunnableTask(ScheduledOperationDTO scheduledOperationDTO){
        this.scheduledOperationDTO = scheduledOperationDTO;
		this.cronExpression = scheduledOperationDTO.getCronExpression();
    }
	
	public String getCronExpression(){
		return cronExpression;
	}
    
    @Override  
    public void run() {
    	// Find account A
        AccountDTO accountDTOA = null;
		try {
			accountDTOA = accountService.findAccount(scheduledOperationDTO.getAccountAId());
		} catch (FinderException | CheckException e) {
			e.getMessage();
		}		
		// Find account B
		AccountDTO accountDTOB = null;
		try {
			accountDTOB = accountService.findAccount(scheduledOperationDTO.getAccountBId());
		} catch (FinderException | CheckException e) {
			e.getMessage();
		}
		// If account B is a Mocha Bank account so it's an internal transfer			
		if(accountDTOB.getBankId().equals("1234") && accountDTOB.getBankName().toUpperCase().contains("MOCHA")){
			try{
				accountService.orderInternalBankTransfer(accountDTOA.getId(), accountDTOB.getId(), scheduledOperationDTO.getAmount());												
			}catch (FinderException e) {
				e.getMessage();		
			}catch (OperationException e) {
				e.getMessage();
			} catch(TransferException e) {
				e.getMessage();
			} catch (Exception e) {
				e.getMessage();
			}	
		// If account B is an Other Bank account so it's an external transfer	
		}else if(accountDTOB.getBankId().equals("5678") && accountDTOB.getBankName().toUpperCase().contains("OTHER")){
			try{
				accountService.orderExternalBankTransfer(accountDTOA.getId(), accountDTOB.getId(), scheduledOperationDTO.getAmount());												
			}catch (FinderException e) {
				e.getMessage();		
			}catch (OperationException e) {
				e.getMessage();
			} catch(TransferException e) {
				e.getMessage();
			} catch (Exception e) {
				e.getMessage();
			}
		}
    }	
    
}


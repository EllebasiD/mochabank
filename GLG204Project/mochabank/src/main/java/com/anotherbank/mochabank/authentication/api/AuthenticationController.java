package com.anotherbank.mochabank.authentication.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.anotherbank.mochabank.authentication.domain.dto.BankClerkDTO;
import com.anotherbank.mochabank.authentication.domain.dto.CustomerDTO;
import com.anotherbank.mochabank.authentication.domain.dto.UserDTO;
import com.anotherbank.mochabank.authentication.domain.service.BankClerkService;
import com.anotherbank.mochabank.authentication.domain.service.CustomerService;
import com.anotherbank.mochabank.authentication.domain.service.UserService;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.UpdateException;
import com.anotherbank.mochabank.logging.Trace;



@Controller
public class AuthenticationController {
	
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(AuthenticationController.class);
	
	// Used for logging
    private final transient String _cname = this.getClass().getName();
    
    protected String getCname() {
        return _cname;
    }

	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private BankClerkService bankClerkService;

	@GetMapping(path = "/login")
	public String login(Authentication authentication) {		// A ce niveau, re-router si d�j� authentifi� 
		if(authentication != null )
			return "index";
		else
			return "login";
	}
	
	@GetMapping(path = "/about")
	public String about(Authentication authentication) {		
			return "about";
	}
	
	
	@GetMapping(path = "/update-account/{username}")
	public String showAccount(Model model, @PathVariable String username ) {
		UserDTO userDTO;
		try {
			userDTO = userService.findUser(username);
		} catch (FinderException | CheckException e) {
			model.addAttribute("exception", e.getClass().getName());
			return "error";
		}
		model.addAttribute("userDTO", userDTO);
		return "update-account";
	}

	@PostMapping(path = "/update-account")
	public String updateAccount(@ModelAttribute UserDTO userDTO, Model model) {
		final String mname = "updateAccount";
        Trace.entering(getCname(), mname);
        
		try {
			if (userDTO instanceof BankClerkDTO) {
				bankClerkService.updateBankClerk((BankClerkDTO)userDTO);
				model.addAttribute("bankClerkUpdated",true);				
			}else if (userDTO instanceof CustomerDTO) {
				customerService.updateCustomer((CustomerDTO)userDTO);
				model.addAttribute("customerUpdated",true);				
			}
			return "index";
		} catch (UpdateException | CheckException e) {
			model.addAttribute("exception", e.getMessage());
			return "error";
		} catch(Exception exc) {
			model.addAttribute("exception", exc.getMessage());
			return "error";
		}

	}
}

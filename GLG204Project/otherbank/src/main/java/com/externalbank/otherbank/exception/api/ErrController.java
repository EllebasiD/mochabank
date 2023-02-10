package com.externalbank.otherbank.exception.api;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.externalbank.otherbank.logging.Trace;


/**
 * This servlet takes the exception message from the HttpServletRequest and displays it
 * creating dynamicaly a HTML page
 * 
 * @author Teachers of GLG203 Unit
 */
@Controller
public final class ErrController implements ErrorController {

    // ======================================
    // =             Attributes             =
    // ======================================

    // Used for logging
    private final transient String _cname = this.getClass().getName();

    // ======================================
    // =         Entry point method         =
    // ======================================
    
    @GetMapping("/error")
    public String handleError(Model model, @RequestParam String exception)  {
        final String mname = "handleError";
        Trace.entering(_cname, mname);
        model.addAttribute("exception", exception);
       return "error";
    }

	//@Override
	public String getErrorPath() {
		 return "/error";
	}
	
}


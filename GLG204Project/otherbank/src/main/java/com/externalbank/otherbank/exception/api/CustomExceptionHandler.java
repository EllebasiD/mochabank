package com.externalbank.otherbank.exception.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * These servlets returns the selected exception.
 * 
 * @author Teachers of GLG203 Unit
 */
@ControllerAdvice
public class CustomExceptionHandler {
	
	private static Logger logger = LogManager.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleAllExc(HttpServletRequest req, Exception ex, Model model) {
    	logger.error("Request: " + req.getRequestURL() + " raised " + ex);
    	logger.error(ex.getMessage());
    	model.addAttribute("exception", "Request: " + req.getRequestURL() + " raised " + ex.getClass().getName()+" ***** Veuillez Cliquer sur Accueil");
    	return "error";
    }
    
    @ExceptionHandler(RuntimeException.class)
    public String handleAllRuntimeExc(HttpServletRequest req, Exception ex, Model model) {
    	logger.error("Request: " + req.getRequestURL() + " raised " + ex);
    	logger.error(ex.getMessage());
    	model.addAttribute("exception", "Request: " + req.getRequestURL() + " raised " + ex.getClass().getName()+" ***** Veuillez Cliquer sur Accueil");
    	return "error";
    }

}

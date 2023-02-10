package com.anotherbank.mokabank.web;

import static org.junit.Assert.fail;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.anotherbank.mochabank.MochabankApplication;
import com.anotherbank.mokabank.configuration.TestConfig;

/**
 * This class tests the HTML Pages and controllers
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(classes = MochabankApplication.class)
@AutoConfigureMockMvc
public class WebTestMockMvc {
	@Autowired
    private MockMvc mockMvc;

    /**
     * Checks that all pages are deployed
     */
    @Test
    public void testWebCheckPages() {
        try {
        	this.mockMvc.perform(get("/dummy.html")).andExpect(status().is3xxRedirection());
        } catch (Exception e) {
        }

        try {
        	this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
        		.andExpect(content().string(containsString("The Mochabank Demo for Spring Boot is a fictional sample application")));
        } catch (Exception e) {
            fail("Root / hasn't been found");
        }
    }
    /**
     * Checks that all servlets are deployed
     */
    @Test
    @WithMockUser(username = "Anne001", password = "cnam", roles = "3")
    public void testWebCheckServlets() {
    	
        try {
        	this.mockMvc.perform(get("/check-balance")).andDo(print()).andExpect(status().isOk())
        		.andExpect(content().string(containsString("Consulter le solde du compte")));
        } catch (Exception e) {
            fail("The check-balance Controller hasn't been found");
        }

        try {
        	this.mockMvc.perform(get("/check-transactions")).andDo(print()).andExpect(status().isOk())
        		.andExpect(content().string(containsString("Historique des opérations du compte")));
        } catch (Exception e) {
            fail("The check-transactions Controller hasn't been found");
        }
        
        try {
        	this.mockMvc.perform(get("/make-deposit")).andDo(print()).andExpect(status().isOk())
        		.andExpect(content().string(containsString("Faire un dépôt")));
        } catch (Exception e) {
            fail("The make-deposit Controller hasn't been found");
        }
        
        try {
        	this.mockMvc.perform(get("/order-transfer")).andDo(print()).andExpect(status().isOk())
        		.andExpect(content().string(containsString("Effectuer un virement")));
        } catch (Exception e) {
            fail("The order-transfer Controller hasn't been found");
        }
        
        try {
        	this.mockMvc.perform(get("/program-transfer")).andDo(print()).andExpect(status().isOk())
        		.andExpect(content().string(containsString("Mettre en place un virement automatique")));
        } catch (Exception e) {
            fail("The program-transfer Controller hasn't been found");
        }
        
        try {
        	this.mockMvc.perform(get("/check-balance")).andDo(print()).andExpect(status().isOk())
        		.andExpect(content().string(containsString("Consulter le solde du compte")));
        } catch (Exception e) {
            fail("The check-balance Controller hasn't been found");
        }
    }
   
}

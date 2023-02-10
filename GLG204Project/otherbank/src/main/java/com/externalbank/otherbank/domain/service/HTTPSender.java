package com.externalbank.otherbank.domain.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.externalbank.otherbank.exception.TransferException;
import com.externalbank.otherbank.logging.Trace;

/**
 * This class sends a HTTP request to a servlet.
 * 
 * @author Isabelle Deligniere
 */
public class HTTPSender {
	// ======================================
    // =             Attributes             =
    // ======================================
    // Used for logging
    private final static String _cname = HTTPSender.class.getName();

    private static final String SERVLET_PARAMETER1 = "param1";
    private static final String SERVLET_PARAMETER2 = "param2";
    private static final String URL_SERVLET_CREDITCARD = "http://localhost:8080/mochabank/receive-transfer";

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method takes data as a parameter and send it to a servlet. The response
     * of the servlet is returned back.
     *
     * @param accountIdBJSON JSON representation of data to send.
     * @param amountJSON JSON representation of data to send.
     * @return the response of the remote servlet after execution
     * @throws TransferException thrown if there's a communication or parsing problem
     */
    public static boolean send(String accountIdBJSON, String amountJSON) throws TransferException {
        String mname = "send";
        Trace.entering(_cname, mname);
        boolean result = false;

        try {
            // Encodes the data to send
            String JSONEncoded1 = URLEncoder.encode(accountIdBJSON, "UTF-8");
            String JSONEncoded2 = URLEncoder.encode(amountJSON, "UTF-8");
            
            // Create the URL with name of the servlet and the data as a parameter
            URL url = new URL(URL_SERVLET_CREDITCARD + "?" + SERVLET_PARAMETER1 + "=" + JSONEncoded1 + "&" + SERVLET_PARAMETER2 + "=" + JSONEncoded2);
            Trace.finest(_cname, mname, "URL=" + url);

            // Opens the connection with the servlet
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            // Gets the result from the servlet
            InputStream is = conn.getInputStream();
            BufferedReader msgStream = new BufferedReader(new InputStreamReader(is));
            String response = msgStream.readLine();
            if(response.contains("true")) {
            	result = true;
            }
            msgStream.close();
            is.close();
        } catch (Exception e) {
            throw new TransferException("Transfer failed");
        }

        Trace.exiting(_cname, mname);
        return result;
    }
}


package com.externalbank.otherbank.api;

import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListenerController implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
		try {
			com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
		} catch (Throwable t) {
		}
		// This manually deregisters JDBC driver, which prevents Tomcat 7 from
		// complaining about memory leaks
		Enumeration<java.sql.Driver> drivers = java.sql.DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			java.sql.Driver driver = drivers.nextElement();
			System.out.println("ServletContextListener deregisterDriver " + driver);
			try {
				java.sql.DriverManager.deregisterDriver(driver);
			} catch (Throwable t) {
			}
		}
		try {
			Thread.sleep(2000L);
		} catch (Exception e) {
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// do nothing!
		System.out.println("ServletContextListener started");
	}
}

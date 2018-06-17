package listeners;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.taglibs.standard.lang.jstl.ELException;
import org.apache.taglibs.standard.lang.jstl.Logger;

@WebListener
public class ServletListener implements ServletContextListener{
	private static final Logger LOGGER = new Logger(System.out);
	
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		try {
			Driver mysql = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(mysql);
			LOGGER.logWarning(String.format("Driver %s registered", mysql));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ELException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	    Enumeration<Driver> drivers = DriverManager.getDrivers();
	    Driver d = null;
	    while (drivers.hasMoreElements()) {
	        try {
	            d = drivers.nextElement();
	            DriverManager.deregisterDriver(d);
	            LOGGER.logWarning(String.format("Driver %s deregistered", d));
	        } catch (SQLException ex) {
	            try {
					LOGGER.logWarning(String.format("Error deregistering driver %s", d), ex);
				} catch (ELException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
}

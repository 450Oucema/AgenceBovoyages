package org.bovoyage.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public final class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        
        System.out.println("Application BoVoyage déployé");
        
    }

    public void contextDestroyed(ServletContextEvent event) {
    }
}

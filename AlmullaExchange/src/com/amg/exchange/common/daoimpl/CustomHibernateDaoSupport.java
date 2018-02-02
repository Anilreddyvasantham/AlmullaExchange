package com.amg.exchange.common.daoimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomHibernateDaoSupport  {
	
	//private static final Logger LOG = Logger.getLogger(CustomHibernateDaoSupport.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
    	//LOG.info("Entering into getSession method");
        return sessionFactory.getCurrentSession();
    }
    
}

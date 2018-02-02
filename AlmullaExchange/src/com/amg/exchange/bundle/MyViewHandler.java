package com.amg.exchange.bundle;

import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.sun.faces.application.view.MultiViewHandler;

public class MyViewHandler extends MultiViewHandler {
    public Locale calculateLocale(FacesContext context) {
        HttpSession session = (HttpSession) context.getExternalContext()
            .getSession(false);
        if (session != null) {
            //Return the locale saved by the managed bean earlier
            Locale locale = (Locale) session.getAttribute("locale");
            if (locale != null) {
                return locale;
            }
        }
       return super.calculateLocale(context);
    }
}
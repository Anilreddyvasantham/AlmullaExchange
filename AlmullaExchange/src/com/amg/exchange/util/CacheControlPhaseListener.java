package com.amg.exchange.util;

import java.io.IOException;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class CacheControlPhaseListener implements PhaseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	public void afterPhase(PhaseEvent event) {
	}

	public void beforePhase(PhaseEvent event) {

		FacesContext facesContext = event.getFacesContext();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache");
		// Stronger according to blog comment below that references HTTP spec
		response.addHeader("Cache-Control", "no-store");
		response.addHeader("Cache-Control", "must-revalidate");
		// some date in the past
		response.addHeader("Expires", new Date().toString());

		/*
		 * Cookie cookie = new Cookie("Almula", ""); cookie.setMaxAge(0);
		 * response.addCookie(cookie);
		 */
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		String requestPath = request.getRequestURI().substring(request.getContextPath().length());

		if (SessionCacheManager.is(requestPath, SessionCacheManager.UrlCheck.BYPASS)) {
			// Do Nothing
			// System.out.println(requestPath + " : Path Does not need check");
		} else {
			if (!SessionCacheManager.getInstance().isValid(requestPath)) {
				facesContext.getExternalContext().invalidateSession();
				try {
					facesContext.getExternalContext().redirect("../login/login.xhtml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}

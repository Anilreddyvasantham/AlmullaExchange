package com.amg.exchange.util;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.amg.exchange.common.model.UserSession;
import com.amg.exchange.registration.service.ILoginService;

public class SessionCacheManager implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -2133170415176824330L;

	// @SuppressWarnings("rawtypes")
	// ILoginService loginService = null;

	public static final String KEY_USERNAME = "_username";
	public static final String KEY_TOKEN = "_token";
	public static final String KEY_LASTCHECK = "_lastcheck";

	private static SessionCacheManager instance = null;
	private static Object monitor = new Object();
	private Map<String, Object> cache = new ConcurrentHashMap<String, Object>();
	private static long tokenTimeOut = 60000L;

	public static enum UrlCheck {
		BYPASS, MUST, OK
	};

	public static final Map<String, UrlCheck> xURLs = new HashMap<String, UrlCheck>();
	static {
		xURLs.put("/almullagroup/almullagroup.xhtml", UrlCheck.BYPASS);
		xURLs.put("/login/login.xhtml", UrlCheck.BYPASS);
		xURLs.put("/common/error.xhtml", UrlCheck.BYPASS);

		xURLs.put("/remittance/", UrlCheck.MUST);
		xURLs.put("/registration/", UrlCheck.MUST);
		xURLs.put("/foreigncurrency/", UrlCheck.MUST);
		xURLs.put("/beneficary/", UrlCheck.MUST);
		xURLs.put("/wuh2h/", UrlCheck.MUST);
	}

	public static boolean startsWith(String requestPath, UrlCheck type) {

		for (Map.Entry<String, UrlCheck> entry : xURLs.entrySet()) {
			if (requestPath != null && requestPath.startsWith(entry.getKey()) && entry.getValue() == type) {
				return true;
			}
		}
		return false;
	}

	public static boolean is(String requestPath, UrlCheck type) {
		return SessionCacheManager.xURLs.containsKey(requestPath) && SessionCacheManager.xURLs.get(requestPath) == type;
	}

	private SessionCacheManager() {
	}

	public void put(String cacheKey, Object value) {
		cache.put(cacheKey, value);
	}

	public Object get(String cacheKey) {
		return cache.get(cacheKey);
	}

	public void clear(String cacheKey) {
		cache.put(cacheKey, null);
	}

	public void clear() {
		cache.clear();
	}

	@SuppressWarnings({ "rawtypes" })
	public ILoginService getLoginService() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		// if (loginService == null) {
		ILoginService loginService = (ILoginService) FacesContext.getCurrentInstance().getApplication().getELResolver()
				.getValue(elContext, null, "loginServiceImpl");
		// }
		return loginService;
	}

	public static SessionCacheManager getInstance() {
		if (instance == null) {
			synchronized (monitor) {
				if (instance == null) {
					instance = new SessionCacheManager();
				}
			}
		}
		return instance;
	}

	public void setValid(String username, String ipaddress) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		// loginService =loginService;
		Long token = System.currentTimeMillis();
		session.setAttribute(KEY_USERNAME, username);
		session.setAttribute(KEY_TOKEN, token);
		session.setAttribute("ipaddress", ipaddress);
		session.setAttribute(KEY_LASTCHECK, System.currentTimeMillis());
		dbSaveToken(username, token, ipaddress);
		put(username, token);
	}

	public boolean isValid(String requestPath) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		if (session != null) {

			Long token = (session.getAttribute(KEY_TOKEN) == null ? 0L : (Long) session.getAttribute(KEY_TOKEN));
			Long lastcheck = (session.getAttribute(KEY_LASTCHECK) == null ? 0L
					: (Long) session.getAttribute(KEY_LASTCHECK));
			String username = (session.getAttribute(KEY_USERNAME) == null ? null
					: (String) session.getAttribute(KEY_USERNAME));
			String ipaddress = (session.getAttribute("ipaddress") == null ? null
					: (String) session.getAttribute("ipaddress"));

			if (token != 0L && username != null) {

				Long tokenAge = System.currentTimeMillis() - lastcheck;

				if (cache.containsKey(username)) {
					Long cacheToken = (Long) get(username);
					/**
					 * Check Token Age if token is older than 10 Seconds then reload it from DB
					 */
					if (tokenAge > tokenTimeOut || startsWith(requestPath, UrlCheck.MUST)) {
						UserSession cacheUser = dbGetToken(username, cacheToken, ipaddress);
						put(username, cacheUser.getUserToken());
						session.setAttribute(KEY_LASTCHECK, System.currentTimeMillis());
					}
					return cacheToken != 0L && (cacheToken.longValue() == token.longValue());

				}
			}
		}
		return false;
	}

	public void dbSaveToken(String username, Long token,String ipaddress) {

		UserSession userSession = new UserSession();
		userSession.setUserName(username);
		userSession.setUserToken(token);
		userSession.setIpaddress(ipaddress);
		userSession.setDate(new Date());
		getLoginService().dbSaveToken(userSession);

	}

	public UserSession dbGetToken(String username, Long cacheToken,String ipaddress) {
		// get Token from DB is return it, instead of cacheToken
		// Note :- IGNORE cacheToken
		List<UserSession> dbCacheToken = getLoginService().getDBToken(username,ipaddress);
		UserSession usersession = null;
		if(dbCacheToken != null && dbCacheToken.size() != 0){
			usersession = dbCacheToken.get(0);
		}
		
		return usersession;
	}

}

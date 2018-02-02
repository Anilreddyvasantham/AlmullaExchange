package com.amg.exchange.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.LoginLogoutHistory;

@Component("loginLogoutHistoryUtil")
@Scope("session")
public class LoginLogoutHistoryUtil<T> implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	IGeneralService<T> iGeneralService;
		
	public  void saveLoginLogoutDetails(BigDecimal countryId,String loginType, String userName,String visitedPage)
	{
		Date currentDate = iGeneralService.getSysDateTimestamp(countryId);
		Timestamp currentTime = DateUtil.getLoginCountryCurrentDate(currentDate);
		
		LoginLogoutHistory loginLogoutHistory = new LoginLogoutHistory();
		loginLogoutHistory.setLoginTime(currentTime);
		loginLogoutHistory.setUserName(userName);
		loginLogoutHistory.setLoginType(loginType);
		loginLogoutHistory.setVisitedPage(visitedPage);
		iGeneralService.saveOrUpdateLoginLogoutHistory(loginLogoutHistory);
	}
}

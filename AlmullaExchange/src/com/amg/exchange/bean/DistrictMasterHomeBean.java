/**
 * 
 */
package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Subramaniam
 * 
 */
@Component("districtMasterHomeBean")
@Scope("session")
public class DistrictMasterHomeBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * 
	 */
	public DistrictMasterHomeBean() {
		// TODO Auto-generated constructor stub
	}

	private Integer selectType;
	
	private Boolean booDistrictDetails = false;
	private Boolean booDistrictDetailsapproval = false;
	
	private SessionStateManage session = new SessionStateManage();



	public Boolean isBooDistrictDetails() {
		return booDistrictDetails;
	}

	public void setBooDistrictDetails(Boolean booDistrictDetails) {
		this.booDistrictDetails = booDistrictDetails;
	}
	
	

	public Boolean isBooDistrictDetailsapproval() {
		return booDistrictDetailsapproval;
	}

	public void setBooDistrictDetailsapproval(Boolean booDistrictDetailsapproval) {
		this.booDistrictDetailsapproval = booDistrictDetailsapproval;
	}


	public Integer getSelectType() {
		return selectType;
	}

	public void setSelectType(Integer selectType) {
		this.selectType = selectType;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "districtmasterhome.xhtml");
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/districtmasterhome.xhtml");

		} catch (Exception e) {
			// LOGGER.info("Problem to redirect:" + e);
		}

	}
	
	public void pageNavigationMode(){
		try {
			System.out.println("selectType ========== > "+selectType);
			
			if(getSelectType()==2){
				System.out.println("selectType ========== > "+selectType);				
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/districtmasterfileupload.xhtml");
			}else if(getSelectType()==1){
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/districtmaster.xhtml");
				
				setBooDistrictDetails(null);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}

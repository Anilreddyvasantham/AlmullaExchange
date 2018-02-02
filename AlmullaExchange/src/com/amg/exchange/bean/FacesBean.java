package com.amg.exchange.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

/*@ManagedBean(name="facesBean")
@SessionScoped*/

@SuppressWarnings("unused")
@Component("facesBean")
@Scope("session")
public class FacesBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private String align="ltr"  ; 
	private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	private String header="../common/header.xhtml"; 
	private String footer = "../common/footer.xhtml"; 
	private String footerwithoutRuleEngine = "../common/footerwithoutRuleEngine.xhtml"; 
	private String menu = "../common/menu.xhtml"; 
	//private String common_menu = "../common/common_menu.xhtml"; 
	private String fxdeal_menu = "../common/fxdeal_menu.xhtml"; 
	private String stylemenu="";

	/* BigDecimal applicationId ;*/

	private Boolean visible;



	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	/*public void facesBeanMethod() {
		if (applicationId == null) {
			applicationId = generalService.getApplicationIdBasedApplicationCode(Constants.RULE_ENGINE_APPLICATION_CODE_KUWAIT);
		}
		if (applicationId != null) {
			new SessionStateManage().setSessionValue("applicationId", applicationId.toString());
		}
	}*/
	@Autowired
	LoginBean loginBean;
	@Autowired
	IGeneralService<T> generalService;

	public String getDirection() {
		if (getLanguage() == "ar") {
			align = "rtl";
		} else {
			align = "ltr";
		}
		return align;
	}

	public String getHeader(){
		/*facesBeanMethod();*/

		header = "../common/header.xhtml";  

		return header;
	}

	public String getFooter(){
		if (getLanguage() == "ar") {
			footer = "../common/arabicfooter.xhtml";
		} else {

			footer = "../common/footer.xhtml";

		}
		return footer;
	}

	public String getMenu(){
		if (getLanguage() == "ar") {
			menu = "../common/arabicmenu.xhtml";
		} else {
			menu = "../common/menu.xhtml";
		}
		return menu;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getDirectionArabic(ActionEvent e){

		setLanguage("ar");

		// Added by Justin Vincent , For -> Add language code into session , Date -> 2014-apr-16
		SessionStateManage sessionStateManage = new SessionStateManage();
		sessionStateManage.setSessionValue("languageCode", "ar");
		sessionStateManage.setSessionValue("languageId", "2");
		align = "rtl";
		loginBean.fetchdata();
		return align;
	}

	public String getDirectionEnglish(ActionEvent e){
		setLanguage("en"); 

		// Added by Justin Vincent , For -> Add language code into session , Date -> 2014-apr-16
		SessionStateManage sessionStateManage = new SessionStateManage();
		sessionStateManage.setSessionValue("languageCode", "en");
		sessionStateManage.setSessionValue("languageId", "1");
		align = "ltr";
		loginBean.fetchdata();
		return align;
	}

	public Locale getLocale() {
		return locale;
	}

	public String getLanguage() {
		return locale.getLanguage();
	}

	public void setLanguage(String language) {
		this.locale = new Locale(language);
	}

	public String getStylemenu() {

		return stylemenu;
	}

	public void setStylemenu(String stylemenu) {
		if(align.equals("rtl"))
		{
			stylemenu ="<style type=\"text/css/\"> " +
					".ui-menubar{      float: right !important;     margin-left: 0px !important; }.ui-menu-list {  right: 0; } .ui-menuitem-text {   float: right !important; }</style>";
		}
		else
		{
			stylemenu ="<style type=\"text/css/\"> " +
					".ui-menubar{      float: left !important;     margin-left: 0px !important; }.ui-menu-list {  left: 0; } .ui-menuitem-text {   float: left !important; }</style>";
		}
		this.stylemenu = stylemenu;
	}

	public String getFxdeal_menu() {
		SessionStateManage sessinstate = new SessionStateManage();
		if (sessinstate.getUserName().equals("root")) {
			RequestContext.getCurrentInstance().execute("expired.show();");
			setVisible(true);
		} else {
			setVisible(null);
			List<RoleMaster> rolList = generalService.getRoleList(new BigDecimal(sessinstate.getRoleId()));
			if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
				fxdeal_menu = "../common/fxdeal_menu.xhtml";
			}else if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_SALES)) {
				fxdeal_menu = "../common/salesmenu.xhtml";
			}else if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_TREASURY)) {
				fxdeal_menu = "../common/treasurymenu.xhtml";
			} else if(rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_REMITTANCE)){
				fxdeal_menu = "../common/remittance_menu.xhtml";
			} else if(rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_CORPORATE)){
				fxdeal_menu = "../common/corporate_menu.xhtml";
			}else if(rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_BRANCH_MANAGER)){
				fxdeal_menu = "../common/branchmanager_menu.xhtml";
			}else if(rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_AML)){
				fxdeal_menu = "../common/aml_menu.xhtml";
			}else if(rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_MARKETING)){
				fxdeal_menu = "../common/marketing_menu.xhtml";
			}else if(rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_HELP_DESK)){
				fxdeal_menu = "../common/helpdesk_menu.xhtml";
			}else if(rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_FCSTAFF)){
				fxdeal_menu = "../common/fcstaff_menu.xhtml";
			}else if (rolList == null || rolList.isEmpty()) {
				try {
					ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
					context.redirect("../almullagroup/almullagroup.xhtml");
					// setCountry(null);
				} catch (Exception e) {
					// LOG.error("Exception occured in LoginBean ::cancel method ",
					// e);
				}
			} else {
				fxdeal_menu = "../common/common_menu.xhtml";
			}

			// For Dynamic Menu
			// fxdeal_menu = "../menu/amgmenu.xhtml";
		}
		return fxdeal_menu;
	}



	public void setFxdeal_menu(String fxdeal_menu) {
		this.fxdeal_menu = fxdeal_menu;
	}

	public String getFooterwithoutRuleEngine() {
		return footerwithoutRuleEngine;
	}

	public void setFooterwithoutRuleEngine(String footerwithoutRuleEngine) {
		this.footerwithoutRuleEngine = footerwithoutRuleEngine;
	}

	public void userSessionCheck(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String countryId = null;
		setVisible(null);
		if(fc.getExternalContext().getSessionMap().get("countryId") != null){
			countryId = fc.getExternalContext().getSessionMap().get("countryId").toString();
		}

		if(countryId!=null && !countryId.isEmpty()){
			// if session is available
		}else{ 
			try {
				fc.getExternalContext().redirect("../almullagroup/almullagroup.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
	}	
}

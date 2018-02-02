package com.amg.exchange.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.MarketingData;
import com.amg.exchange.common.service.IAlmullagroupService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.LoginLogoutHistory;
import com.amg.exchange.registration.service.ILoginService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.DateUtil;
import com.amg.exchange.util.SessionStateManage;

@Component(value = "almullaGroupMain")
@Scope("request")
public class AlmullagroupMain<T> implements Serializable {
	
	private static final Logger LOG = Logger.getLogger(AlmullagroupMain.class);

	private static final long serialVersionUID = 1L;
	
	private BigDecimal pid;
	private String loginHdrTitle;
	private StreamedContent loginHdrlogo;
	private String loginHdrTxt;
	private String loginArbTitle;
	private StreamedContent loginArblog;
	private String loginArbTxt;
	private String innerHdrTitle;
	private StreamedContent innerHdrlog;
	private String innerHdrTxt;
	private String innerHdrArbtitle;
	private StreamedContent innerHdrArbBlog;
	private String innerHdrArbTxt;
	private String loginfooterTxt;
	private String innerfooterTxt;
	private StreamedContent logBnrImg;
	private String loginNewsEvent;
	private BigDecimal country;
	private Boolean disableLink=false;
	
	
	private List<MarketingData> data = new ArrayList<MarketingData>();

	@Autowired
	LoginBean loginBean;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IAlmullagroupService<T> ialmullagroupService;
	@Autowired
	ILoginService<T> loginService;

	//SessionStateManage sessionStateManage = new SessionStateManage();
	public void logout() {
		LOG.info("Entering into logout method");
		try {
			LoginLogoutHistory loginLogoutHistory = new LoginLogoutHistory();
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			BigDecimal loginid = (BigDecimal) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginid");
			List<LoginLogoutHistory> loginDetailList = (List<LoginLogoutHistory>) loginService.getLoginLogoutDetails(loginid);			
			//Timestamp logoutTime = new Timestamp(new Date().getTime());
			
			Object obj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("countryId");
			
			//BigDecimal countryId = (BigDecimal)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("countryId");
			BigDecimal countryId = new BigDecimal(obj.toString());
			
			Date currentDate = generalService.getSysDateTimestamp(countryId);
			Timestamp currentTime = DateUtil.getLoginCountryCurrentDate(currentDate);
			if(loginDetailList.size()>0){
				loginLogoutHistory.setLoginTime(loginDetailList.get(0).getLoginTime());
				loginLogoutHistory.setLoginType(loginDetailList.get(0).getLoginType());
				loginLogoutHistory.setUserName(loginDetailList.get(0).getUserName());
				loginLogoutHistory.setLoginLogoutId(loginid);
				loginLogoutHistory.setLogoutTime(currentTime);
				loginService.saveLoginLogoutDetails(loginLogoutHistory);
			}
			context.redirect("../almullagroup/almullagroup.xhtml");
			// getLoginService().updateSessionReset(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userName").toString());
			context.invalidateSession();
			setCountry(null);
		} catch (Exception e) {
			System.out.println("Exception  logout:"+e.getMessage());
			LOG.error("Exception occured in AlmullagroupMain ::logout method ", e);
		}
		LOG.info("Exit into logout method");
	}

	public void redirectLogin() {
		LOG.info("Entering into redirectLogin method");
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../almullagroup/almullagroup.xhtml");
			context.invalidateSession();
			setCountry(null);
		} catch (Exception e) {
			LOG.error("Exception occured in AlmullagroupMain ::redirectLogin method ", e);
		}
		LOG.info("Exit into redirectLogin method");
	}

	public void fetchdata() {
		LOG.info("Entering into fetchdata method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		data = ialmullagroupService.getCountryList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"), getCountry());
		setPid(data.get(0).getMarkdataId());
		setLoginHdrTitle(data.get(0).getLoginHeaderTitle());
		setLoginHdrTxt(data.get(0).getLoginHeaderText());
		setLoginArbTitle(data.get(0).getLoginHeaderArabicTitle());
		setLoginArbTxt(data.get(0).getLoginHeaderArabicText());
		setInnerHdrTitle(data.get(0).getInnerHeaderTitle());
		setInnerHdrTxt(data.get(0).getInnerHeaderText());
		setInnerHdrArbtitle(data.get(0).getInnerHeaderArabicTitle());
		setInnerHdrArbTxt(data.get(0).getInnerHeaderArabicText());
		setLoginfooterTxt(data.get(0).getLoginFooterText());
		setInnerfooterTxt(data.get(0).getInnerFooterText());
		setLoginNewsEvent(data.get(0).getLoginNewsEvent());
		try {
			InputStream stream = null;
			Blob blob = null;
			blob = data.get(0).getLoginHeaderlogo();
			int blobLength = (int) blob.length();
			byte[] blobAsBytes = blob.getBytes(1, blobLength);
			stream = new ByteArrayInputStream(blobAsBytes);
			loginHdrlogo = new DefaultStreamedContent(stream, "image/jpg");
			setLoginHdrlogo(loginHdrlogo);
			InputStream stream2 = null;
			Blob blob2 = null;
			blob2 = data.get(0).getLoginHeaderArabiclogo();
			int blobLength2 = (int) blob2.length();
			byte[] blobAsBytes2 = blob2.getBytes(1, blobLength2);
			stream2 = new ByteArrayInputStream(blobAsBytes2);
			loginArblog = new DefaultStreamedContent(stream2, "image/jpg");
			setLoginArblog(loginArblog);
			InputStream stream3 = null;
			Blob blob3 = null;
			blob3 = data.get(0).getInnerHeaderlogo();
			int blobLength3 = (int) blob3.length();
			byte[] blobAsBytes3 = blob3.getBytes(1, blobLength3);
			stream3 = new ByteArrayInputStream(blobAsBytes3);
			innerHdrlog = new DefaultStreamedContent(stream3, "image/jpg");
			setInnerHdrlog(innerHdrlog);
			InputStream stream4 = null;
			Blob blob4 = null;
			blob4 = data.get(0).getInnerHeaderArabiclogo();
			int blobLength4 = (int) blob4.length();
			byte[] blobAsBytes4 = blob4.getBytes(1, blobLength4);
			stream4 = new ByteArrayInputStream(blobAsBytes4);
			innerHdrArbBlog = new DefaultStreamedContent(stream4, "image/jpg");
			setInnerHdrArbBlog(innerHdrArbBlog);
			InputStream stream5 = null;
			Blob blob5 = null;
			blob5 = data.get(0).getLoginBannerImage();
			int blobLength5 = (int) blob5.length();
			byte[] blobAsBytes5 = blob5.getBytes(1, blobLength5);
			stream5 = new ByteArrayInputStream(blobAsBytes5);
			logBnrImg = new DefaultStreamedContent(stream5, "image/jpg");
			setLogBnrImg(logBnrImg);
		} catch (Exception e) {
			LOG.error("Exception occured in AlmullagroupMain ::fetchdata method ", e);
		}
		
		LOG.info("Exit into fetchdata method");
	}
	
	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}
	
	private List<CountryMasterDesc> lstofbussinesscountry = new ArrayList<CountryMasterDesc>();

	public List<CountryMasterDesc> getLstofbussinesscountry() {
		return lstofbussinesscountry;
	}

	public void setLstofbussinesscountry(
			List<CountryMasterDesc> lstofbussinesscountry) {
		this.lstofbussinesscountry = lstofbussinesscountry;
	}

	/*
	 * method to get the country Name name and country code from dataBase
	 */
	public List<CountryMasterDesc> getCountryNameList() {
		LOG.info("Entering into getCountryNameList method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		lstofbussinesscountry = getGeneralService().getBusinessCountryList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		LOG.info("Exit into getCountryNameList method");
		return lstofbussinesscountry;
	}

	public void changeCountry() {
		LOG.info("Entering into changeCountry method");

		BigDecimal applicationId = generalService.getApplicationIdBasedApplicationCode(Constants.RULE_ENGINE_APPLICATION_CODE_KUWAIT);
		new SessionStateManage().setSessionValue("applicationId", applicationId.toString());
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		if (lstofbussinesscountry.size() != 0) {
			for (CountryMasterDesc busscountry : lstofbussinesscountry) {
				if (busscountry.getFsCountryMaster().getCountryId().compareTo(getCountry()) == 0) {
					try {

						// logo for Kuwait and Oman
						if(busscountry.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
							loginBean.setBooRenderloginHdrlogoKW(true);
							loginBean.setBooRenderloginHdrlogoOM(false);
						}else if(busscountry.getCountryName().equalsIgnoreCase(Constants.OMAN)){
							loginBean.setBooRenderloginHdrlogoKW(false);
							loginBean.setBooRenderloginHdrlogoOM(true);
						}else{
							// Other Country
							loginBean.setBooRenderloginHdrlogoKW(true);
							loginBean.setBooRenderloginHdrlogoOM(false);
						}

						// fetchdata();
						/* Start */
						SessionStateManage sessionStateManage = new SessionStateManage();
						sessionStateManage.setSessionValue("countryId", getCountry().toPlainString());
						sessionStateManage.setSessionValue("countryName", busscountry.getCountryName());
						/* End add by Ramakrishna 09-02-2015 */
						loginBean.setApplicationCountryId(getCountry());
						loginBean.setApplicationCountryName(busscountry.getCountryName());
						loginBean.pageNavigation();
						
					} catch (Exception e) {
						LOG.error("Exception occured in AlmullagroupMain ::changeCountry method ", e);
					}
				}
			}
		} else {
			LOG.info("No Countries Found");
		}
		LOG.info("Entering into changeCountry method");

	}	
	
	
	public void home() throws IOException {
		LOG.info("Entering into home method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		/*if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else if (sessionStateManage.getRoleId().equalsIgnoreCase("2")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}else{
			FacesContext.getCurrentInstance() .getExternalContext().redirect("../common/saleshome.xhtml");
		}*/
		FacesContext.getCurrentInstance() .getExternalContext().redirect(sessionStateManage.getMenuPage());
		LOG.info("Exit into home method");
		
		
		return;
	}
	
	public BigDecimal getCountry() {
		return country;
	}

	public void setCountry(BigDecimal country) {
		this.country = country;
	}
	
	public List<MarketingData> getData() {
		

		return data;
	}

	public void setData(List<MarketingData> data) {
		this.data = data;
	}

	public BigDecimal getPid() {
		return pid;
	}

	public void setPid(BigDecimal pid) {
		this.pid = pid;
	}

	public String getLoginHdrTitle() {
		return loginHdrTitle;
	}

	public void setLoginHdrTitle(String loginHdrTitle) {
		this.loginHdrTitle = loginHdrTitle;
	}

	public StreamedContent getLoginHdrlogo() {
		return loginHdrlogo;
	}

	public void setLoginHdrlogo(StreamedContent loginHdrlogo) {
		this.loginHdrlogo = loginHdrlogo;
	}

	public String getLoginHdrTxt() {
		return loginHdrTxt;
	}

	public void setLoginHdrTxt(String loginHdrTxt) {
		this.loginHdrTxt = loginHdrTxt;
	}

	public String getLoginArbTitle() {
		return loginArbTitle;
	}

	public void setLoginArbTitle(String loginArbTitle) {
		this.loginArbTitle = loginArbTitle;
	}

	public StreamedContent getLoginArblog() {
		return loginArblog;
	}

	public void setLoginArblog(StreamedContent loginArblog) {
		this.loginArblog = loginArblog;
	}

	public String getLoginArbTxt() {
		return loginArbTxt;
	}

	public void setLoginArbTxt(String loginArbTxt) {
		this.loginArbTxt = loginArbTxt;
	}

	public String getInnerHdrTitle() {
		return innerHdrTitle;
	}

	public void setInnerHdrTitle(String innerHdrTitle) {
		this.innerHdrTitle = innerHdrTitle;
	}

	public StreamedContent getInnerHdrlog() {
		return innerHdrlog;
	}

	public void setInnerHdrlog(StreamedContent innerHdrlog) {
		this.innerHdrlog = innerHdrlog;
	}

	public String getInnerHdrTxt() {
		return innerHdrTxt;
	}

	public void setInnerHdrTxt(String innerHdrTxt) {
		this.innerHdrTxt = innerHdrTxt;
	}

	public String getInnerHdrArbtitle() {
		return innerHdrArbtitle;
	}

	public void setInnerHdrArbtitle(String innerHdrArbtitle) {
		this.innerHdrArbtitle = innerHdrArbtitle;
	}

	public StreamedContent getInnerHdrArbBlog() {
		return innerHdrArbBlog;
	}

	public void setInnerHdrArbBlog(StreamedContent innerHdrArbBlog) {
		this.innerHdrArbBlog = innerHdrArbBlog;
	}

	public String getInnerHdrArbTxt() {
		return innerHdrArbTxt;
	}

	public void setInnerHdrArbTxt(String innerHdrArbTxt) {
		this.innerHdrArbTxt = innerHdrArbTxt;
	}

	public String getLoginfooterTxt() {
		return loginfooterTxt;
	}

	public void setLoginfooterTxt(String loginfooterTxt) {
		this.loginfooterTxt = loginfooterTxt;
	}

	public String getInnerfooterTxt() {
		return innerfooterTxt;
	}

	public void setInnerfooterTxt(String innerfooterTxt) {
		this.innerfooterTxt = innerfooterTxt;
	}

	public StreamedContent getLogBnrImg() {
		return logBnrImg;
	}

	public void setLogBnrImg(StreamedContent logBnrImg) {
		this.logBnrImg = logBnrImg;
	}

	public String getLoginNewsEvent() {
		return loginNewsEvent;
	}

	public void setLoginNewsEvent(String loginNewsEvent) {
		this.loginNewsEvent = loginNewsEvent;
	}

	public IAlmullagroupService<T> getIalmullagroupService() {

		return ialmullagroupService;
	}

	public void setIalmullagroupService(IAlmullagroupService<T> ialmullagroupService) {
		this.ialmullagroupService = ialmullagroupService;
	}
	
	public Boolean getDisableLink() {
		return disableLink;
	}
	
	public void setDisableLink(Boolean disableLink) {
		this.disableLink = disableLink;
	}

	

}

package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.SwiftMaster;
import com.amg.exchange.remittance.service.ISwiftMasterService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("swiftMasterEnquiryBean")
@Scope("session")
public class SwiftMasterEnquiryBean<T> implements Serializable {

	private static Logger log = Logger.getLogger(SwiftMasterEnquiryBean.class);
	private static final long serialVersionUID = -1979424207354300862L;

	private  String swiftCountryId;
	private  String bankId;
	private BigDecimal branchId;
	private BigDecimal swiftId;
	private String bankCode;
	private String countryCode;
	
	private String swiftCountryName;
	private String swiftBank;
	private String swiftLocation;
	private String branchName;
	private String errorMessage;
	
	private Boolean renderDataTablePanel = false;

	@Autowired
	ISwiftMasterService iSwiftService;
	@Autowired
	IGeneralService<T> igeneralService;

	SessionStateManage session = new SessionStateManage();

	private List<SwiftMasterMaintenanceDataTableBean> swiftMasterEnqryList = new ArrayList<>();
	private Map<String, String> swiftCountryMap = new HashMap<String, String>();
	private List<CountryMasterDesc> allCountryList = new ArrayList<CountryMasterDesc>();
	private List<SwiftMaster> swiftMasterList = new ArrayList<>();

	public List<CountryMasterDesc> getAllCountryList() {
		allCountryList = igeneralService
				.getCountryList(session.getLanguageId());
		for (CountryMasterDesc countryMasterDesc : allCountryList) {
			swiftCountryMap.put(countryMasterDesc.getFsCountryMaster()
					.getCountryAlpha2Code(), countryMasterDesc.getCountryName());
		}
		return allCountryList;
	}

	public void setAllCountryList(List<CountryMasterDesc> allCountryList) {
		this.allCountryList = allCountryList;
	}

	public List<BankMaster> getBankListFromDB() {
		return igeneralService.getAllBankListFromBankMaster();
	}

	public List<SwiftMaster> getSwiftMasterList() {
		return swiftMasterList;
	}

	public void setSwiftMasterList(List<SwiftMaster> swiftMasterList) {
		this.swiftMasterList = swiftMasterList;
	}

	public String getSwiftCountryId() {
		return swiftCountryId;
	}

	public void setSwiftCountryId(String swiftCountryId) {
		this.swiftCountryId = swiftCountryId;
	}

	public String getSwiftCountryName() {
		return swiftCountryName;
	}

	public void setSwiftCountryName(String swiftCountryName) {
		this.swiftCountryName = swiftCountryName;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public BigDecimal getSwiftId() {
		return swiftId;
	}

	public void setSwiftId(BigDecimal swiftId) {
		this.swiftId = swiftId;
	}

	public String getSwiftBank() {
		return swiftBank;
	}

	public void setSwiftBank(String swiftBank) {
		this.swiftBank = swiftBank;
	}

	public String getSwiftLocation() {
		return swiftLocation;
	}

	public void setSwiftLocation(String swiftLocation) {
		this.swiftLocation = swiftLocation;
	}

	public Boolean getRenderDataTablePanel() {
		return renderDataTablePanel;
	}

	public void setRenderDataTablePanel(Boolean renderDataTablePanel) {
		this.renderDataTablePanel = renderDataTablePanel;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public ISwiftMasterService getiSwiftService() {
		return iSwiftService;
	}

	public void setiSwiftService(ISwiftMasterService iSwiftService) {
		this.iSwiftService = iSwiftService;
	}

	public List<SwiftMasterMaintenanceDataTableBean> getSwiftMasterEnqryList() {
		return swiftMasterEnqryList;
	}

	public void setSwiftMasterEnqryList(
			List<SwiftMasterMaintenanceDataTableBean> swiftMasterEnqryList) {
		this.swiftMasterEnqryList = swiftMasterEnqryList;
	}

	public void swiftEnquiry() {
	try{
		log.info("Entered into swiftEnquiry() method");
		swiftMasterEnqryList.clear();
		// clear();
if(getSwiftCountryId()!=null&&getBankId()!=null){
		swiftMasterList = iSwiftService.getSwiftEnquiry(getSwiftCountryId(),getBankId());
		log.info( "=============================swift list======"+swiftMasterList.size());
		log.info( "======================="+getCountryCode()+""+getBankCode());
		log.info("size is=================="+getBankId()+"data"+getSwiftCountryId());
		if (swiftMasterList.size() > 0) {

			setRenderDataTablePanel(true);
			for (SwiftMaster swiftEnqr : swiftMasterList) {
				SwiftMasterMaintenanceDataTableBean swiftDT = new SwiftMasterMaintenanceDataTableBean();
				
								swiftDT.setSwiftId(swiftEnqr.getSwiftId());
	 
								//swiftDT.setBranchName(iSwiftService.getBranchName(swiftEnqr.getSwiftBranchCode() ) );
									//swiftDT.setBankId(swiftEnqr.getBankId().getBankId());
			//if(NullCheckHandler.isObjNull( swiftEnqr.getBankId().getBankId() )){

				swiftDT.setSwiftBankName(iSwiftService.getBankFullNameBasedOnCode(swiftEnqr.getBankCode() ));
			//	swiftDT.setSwiftBankId(swiftEnqr.getSwiftBankId());

				//swiftDT.setBranchId(swiftEnqr.getSwiftBranchId()
					//	.getBankBranchId());

				swiftDT.setBranchName( iSwiftService.getBranchName( swiftEnqr.getSwiftBranchCode()));

				//swiftDT.setSwiftCountryId(swiftEnqr.getSwiftCountryId()
						//.getCountryId());
				swiftDT.setSwiftCountryName( iSwiftService.getCountryNameBasedOnCountryAlphaCode(getSwiftCountryId())); 

				swiftDT.setSwiftLocation(swiftEnqr.getSwiftLocation());
				swiftDT.setBankName(swiftEnqr.getBankName());
				swiftDT.setCityName(swiftEnqr.getCityName());
				swiftDT.setFirstAddress(swiftEnqr.getFirstAddress());
				swiftDT.setSecondAddress(swiftEnqr.getSecondAddress());
				swiftDT.setThirdAddress(swiftEnqr.getThirdAddress());
				swiftDT.setChipsUID(swiftEnqr.getChipsUID());
				swiftDT.setAcronymID(swiftEnqr.getAcronymId());
				swiftDT.setFedwireID(swiftEnqr.getFedwireId());
				swiftDT.setRegion(swiftEnqr.getRegion());
				swiftDT.setSwiftBIC(swiftEnqr.getSwiftBIC());
				swiftDT.setAbaNumber(swiftEnqr.getAbaId());
				swiftDT.setCreatedBy(swiftEnqr.getCreatedBy());
				swiftDT.setCreatedDate(swiftEnqr.getCreatedDate());
				swiftDT.setApprovedBy(swiftEnqr.getApprovedBy());
				swiftDT.setApprovedDate(swiftEnqr.getApprovedDate());
				if (swiftEnqr.getIsActive().equalsIgnoreCase(Constants.U)) {
					swiftDT.setIsActive( Constants.DEACTIVATE);
				} else if (swiftEnqr.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					swiftDT.setIsActive(Constants.ACTIVATE);
				} else if (swiftEnqr.getIsActive().equalsIgnoreCase(Constants.D)) {
					swiftDT.setIsActive(Constants.DELETE);
				}
				swiftMasterEnqryList.add(swiftDT);

			}

		} else {
			log.info("No Records Found for this combination");
			swiftMasterEnqryList.clear();
			RequestContext.getCurrentInstance().execute("mandatorydia.show();");
			return ;
		}
		log.info( "Exited from swiftEnquiry() Method");
}else{
	RequestContext.getCurrentInstance().execute("succ.show();");
}
	}catch(NullPointerException  e){
		setErrorMessage("Method Name: swiftEnquiry"+e.getMessage());
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}
	catch(Exception e){
		setErrorMessage(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
	}
	}

	public void clear() {
		log.info("Entered into clear() method");
		setSwiftCountryId(null);
		setBankId(null);
		setCountryCode(null);
		setBankCode(null);
		swiftMasterEnqryList.clear();
	}
	
	
/*private Map<BigDecimal, String> swiftBanks = new HashMap<BigDecimal, String>();

	public void bankList(){
		iSwiftService.getBankMasterListBasedOnCode();
 }*/
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void swiftEnquiryPageNavigatetion() {
		

		
		log.info("Entered into swiftEnquiryPageNavigatetion() method");
		try {
			clear();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "SwiftEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/SwiftEnquiry.xhtml");
		} catch (Exception e) {
			log.error("This is Navigation problem in SWIFT ENQUIRY");
		}
	}

	public void exit() {
		log.info("Entered into exit() method");
		try {
			if (session.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/branchhome.xhtml");
			}
		} catch (Exception e) {
			log.error("This is Navigation problem in SWIFT ENQUIRY" + e);
		}

	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

 

}

package com.amg.exchange.promotion.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.promotion.model.PromotionDetails;
import com.amg.exchange.promotion.model.PromotionMaster;
import com.amg.exchange.promotion.model.PromotionPrize;
import com.amg.exchange.promotion.service.IPromotionService;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("promotionMasterMBean")
@Scope("session")
public class PromotionMasterMBean<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final Logger log = Logger.getLogger(PromotionMasterMBean.class);
	
	private BigDecimal companyId;
	private String errorMessage;
	private BigDecimal finaceYear;
	private BigDecimal financialYearId;
	private BigDecimal promotionDocumentNo;
	private Date fromDate;
	private Date toDate;
	private String description;	
	private BigDecimal documentId;
	private BigDecimal promoNum;
	private BigDecimal exPromoHdSeq;
	
	private Boolean proNumDispaly = false;
	private Boolean editButton = true;
	
	private SessionStateManage sessionStateManage=new SessionStateManage();
	private Date weekFromDt = new Date();
	private Date weekToDt = new Date();
	
	private List<PopulateData> docNumbers = null;
	private List<PromotionMasterDataTable> lstPromotionMasterDataTable = null;
	private BigDecimal promoMasterSeq;
	
	@Autowired
	IGeneralService<T> igeneralService;
	
	@Autowired
	IPromotionService iPromotionService;
	
	
	public void pageNavigation() {
		try {			
			clear();
			getCompanyListFromDB();
			getFinaceYearFromDb();
			getDocNumberPromotion();
			getDocumentIdFromDb();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../promotion/promotionMaster.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getCompanyListFromDB() {
		List<CompanyMasterDesc> data=null;
		try{
			data=igeneralService.getCompanyList(sessionStateManage.getCompanyId(),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
			setCompanyId(data.get(0).getFsCompanyMaster().getCompanyId());

		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;       
		}
		return data.get(0).getCompanyName();
	}
	

	public void getFinaceYearFromDb() {

		try{
			BigDecimal finaceYear= BigDecimal.ZERO;
			BigDecimal financialYearId= BigDecimal.ZERO;
			List<UserFinancialYear> financialYearList = igeneralService.getDealYear(new Date());
			log.info("financialYearList :"+financialYearList.size());
			if(financialYearList!=null && financialYearList.size()>0){
				if(financialYearList.get(0).getFinancialYear()!=null){
					finaceYear = financialYearList.get(0).getFinancialYear();
				}
				financialYearId=financialYearList.get(0).getFinancialYearID();
				setFinancialYearId(financialYearId);
				setFinaceYear(finaceYear);
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}
	
	
	public BigDecimal getDocumentSerialIdNumber(String processIn) {

		String documentSerialId="0";
		try {
			HashMap<String, String> outPutValues= igeneralService.getNextDocumentRefNumber(sessionStateManage.getCountryId().intValue() ,sessionStateManage.getCompanyId().intValue(),Integer.parseInt(Constants.DOCUMENT_CODE_FOR_PROMOTION), finaceYear.intValue(),processIn,sessionStateManage.getCountryBranchCode());
			String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
			if(proceErrorMsg!=null)
			{
				setErrorMessage(proceErrorMsg);
				RequestContext.getCurrentInstance().execute("error.show();");
				return BigDecimal.ZERO;
			}else if(outPutValues.get("DOCNO") !=null && new BigDecimal(outPutValues.get("DOCNO")).compareTo(BigDecimal.ZERO)==0){

				RequestContext.getCurrentInstance().execute("docZero.show();");
				return BigDecimal.ZERO;

			}else
			{
				documentSerialId=outPutValues.get("DOCNO");
			}
		} catch (NumberFormatException | AMGException e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return BigDecimal.ZERO;
		}
		return new BigDecimal(documentSerialId);
	}
	
	
	public void getDocumentIdFromDb() {
		try{
			List<Document> documentDesc=igeneralService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PROMOTION),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
			for(Document des:documentDesc)
			{
				setDocumentId(des.getDocumentID());
				
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			      
		}		
	}
	
	
	private void getDocNumberPromotion()
	{
		BigDecimal docRefNumber=getDocumentSerialIdNumber(Constants.Yes);
		setPromotionDocumentNo(docRefNumber);
	}
	
	
	
	public void proNumSelected(){
		setEditButton(false);
		setProNumDispaly(true);
		setFromDate(null);
		setToDate(null);
		setDescription(null);
		setLstPromotionMasterDataTable(null);
		setPromoMasterSeq(null);
		if(getPromoNum().compareTo(BigDecimal.ZERO)==0){
		RequestContext.getCurrentInstance().execute("zeroDocNo.show();");
		return;
		}
		List<PromotionMaster> lstPromotionMaster = iPromotionService.lstPromotionMaster(getCompanyId(), getDocumentId(), getFinaceYear(),getPromoNum());
		if(lstPromotionMaster!=null && lstPromotionMaster.size() > 0){
			PromotionMaster promotionMaster = lstPromotionMaster.get(0);
			setFinaceYear(promotionMaster.getDocumentFinancialYear());
			setFromDate(promotionMaster.getFromDate());
			setToDate(promotionMaster.getToDate());
			setDescription(promotionMaster.getPromotionDescription());
			setPromoMasterSeq(promotionMaster.getPromotionMasterId());
			
			List<PromotionPrize> lstPromotionPrize = iPromotionService.lstPromotionPrize(promotionMaster.getPromotionMasterId());
			if(lstPromotionPrize!=null && lstPromotionPrize.size() > 0){
				List<PromotionMasterDataTable> promoMasterTable = new ArrayList<PromotionMasterDataTable>();
				for(PromotionPrize promotionPrize : lstPromotionPrize){
					PromotionMasterDataTable promotionMasterDataTable = new PromotionMasterDataTable();
					promotionMasterDataTable.setPromoMasterSeq(promotionMaster.getPromotionMasterId());
					promotionMasterDataTable.setPromoPrizeSeq(promotionPrize.getPromotionPrizeId());
					promotionMasterDataTable.setWeekFrDt(promotionPrize.getFromDate());
					promotionMasterDataTable.setWeekToDt(promotionPrize.getToDate());
					promotionMasterDataTable.setPrizesNo(promotionPrize.getPrizeNo());
					promotionMasterDataTable.setAmount(promotionPrize.getPrizeAmount());
					
					promoMasterTable.add(promotionMasterDataTable);
				}	
				
				setLstPromotionMasterDataTable(promoMasterTable);
			}
		}		
	}	
	
	
	public void editSelected(){
		if(getLstPromotionMasterDataTable()!=null && getLstPromotionMasterDataTable().size() > 0){
			RequestContext.getCurrentInstance().execute("someDataPresent.show();");
		}else{
			setProNumDispaly(true);
			setEditButton(false);
			setFromDate(null);
			setToDate(null);
			setDescription(null);
			setLstPromotionMasterDataTable(null);
			List<PopulateData> documentNumbers = iPromotionService.getPromotionDocumentNo(getCompanyId(), getDocumentId(), getFinaceYear());
			if(documentNumbers!=null && documentNumbers.size() > 0){
				setDocNumbers(documentNumbers);
			}
		}
	}	
	
	public void clickOnYes(){
		setProNumDispaly(true);
		setEditButton(false);
		setLstPromotionMasterDataTable(null);
		List<PopulateData> documentNumbers = iPromotionService.getPromotionDocumentNo(getCompanyId(), getDocumentId(), getFinaceYear());
		if(documentNumbers!=null && documentNumbers.size() > 0){
			setDocNumbers(documentNumbers);
		}
	}
	
	public void addNewRecord(){
		if(getFinaceYear()==null){
			RequestContext.getCurrentInstance().execute("promotionYear.show();");
		} else if(getPromotionDocumentNo()==null){
			RequestContext.getCurrentInstance().execute("promotionNumber.show();");
		} else if(getFromDate()==null){
			RequestContext.getCurrentInstance().execute("fromDate.show();");
		} else if(getToDate()==null){
			RequestContext.getCurrentInstance().execute("toDate.show();");
		} else if(getDescription()==null){
			RequestContext.getCurrentInstance().execute("description.show();");
		} else{
			List<PromotionMasterDataTable> lstExisting = getLstPromotionMasterDataTable();
			List<PromotionMasterDataTable> dataTable = new ArrayList<PromotionMasterDataTable>();
			if(lstExisting!=null && lstExisting.size()>0){
				for(PromotionMasterDataTable promotionTable : getLstPromotionMasterDataTable()){
					boolean checkMandatory=mandatoryCheck(promotionTable);
					if(checkMandatory)
					{
						return;
					}
				}
			}
			
			if(lstExisting!=null){
				dataTable.addAll(lstExisting);
			}
			
			PromotionMasterDataTable promotionMasterDataTable = new PromotionMasterDataTable();
			dataTable.add(promotionMasterDataTable);			
					
			setLstPromotionMasterDataTable(dataTable);
		}		
	}
	
	private boolean mandatoryCheck(PromotionMasterDataTable promotionMasterDataTable){
		boolean checkMandatory=false;
		if(promotionMasterDataTable!=null){
			if(promotionMasterDataTable.getWeekFrDt()==null){
				checkMandatory = true;
				RequestContext.getCurrentInstance().execute("weekFromDt.show();");
				return checkMandatory;
			}
			if(promotionMasterDataTable.getWeekToDt()==null){
				checkMandatory = true;
				RequestContext.getCurrentInstance().execute("weekToDt.show();");
				return checkMandatory;
			}
			if(promotionMasterDataTable.getPrizesNo()==null){
				checkMandatory = true;
				RequestContext.getCurrentInstance().execute("enterPrizeNum.show();");
				return checkMandatory;
			}
			if(promotionMasterDataTable.getAmount()==null){
				checkMandatory = true;
				RequestContext.getCurrentInstance().execute("enterAmount.show();");
				return checkMandatory;
			}
		}		
		return checkMandatory;
	}
	
	
	
	public void deleteSelected(PromotionMasterDataTable promotionMasterDataTable){
		try{
			if(promotionMasterDataTable.getPromoPrizeSeq()!=null){
				List<PromotionDetails> list = iPromotionService.promotionDetailsList(promotionMasterDataTable.getPromoPrizeSeq());
				
				if(list!=null && list.size() > 0){
					RequestContext.getCurrentInstance().execute("alreadyUtilised.show();");
				}else{
					iPromotionService.delete(promotionMasterDataTable.getPromoPrizeSeq());
					proNumSelected();
					RequestContext.getCurrentInstance().execute("recordDeleted.show();");
				}				
			}
			
		}catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");	
		}		
	}
	
	public void remove(PromotionMasterDataTable promotionMasterDataTable)
	 {		 
		getLstPromotionMasterDataTable().remove(promotionMasterDataTable);
		
	 }
	
	
	
	public void saveDataTable(){
		try{
			if(getFinaceYear()==null){
				RequestContext.getCurrentInstance().execute("promotionYear.show();");
			} else if(getPromotionDocumentNo()==null){
				RequestContext.getCurrentInstance().execute("promotionNumber.show();");
			} else if(getFromDate()==null){
				RequestContext.getCurrentInstance().execute("fromDate.show();");
			} else if(getToDate()==null){
				RequestContext.getCurrentInstance().execute("toDate.show();");
			} else if(getDescription()==null){
				RequestContext.getCurrentInstance().execute("description.show();");
			} else if(getLstPromotionMasterDataTable()==null || getLstPromotionMasterDataTable().size()==0){
				RequestContext.getCurrentInstance().execute("noRecordsInTable.show();");
			} else{
				BigDecimal docRefNumber=getDocumentSerialIdNumber(Constants.U);
				if(docRefNumber.compareTo(BigDecimal.ZERO)==0)
				{
					RequestContext.getCurrentInstance().execute("zeroDocNo.show();");
					return;
				}
				//Taken update on 12 06 2017 
				/* Start Here */
				for(PromotionMasterDataTable promotionTable : getLstPromotionMasterDataTable()){
					boolean checkMandatory=mandatoryCheck(promotionTable);
					if(checkMandatory)
					{
						return;
					}
				}
				
				/* End Here */
				List<PromotionMasterDataTable> proMasterDataTable = getLstPromotionMasterDataTable();
				
				PromotionMaster promotionMaster = new PromotionMaster();
				
				CountryMaster couMaster = new CountryMaster();
				couMaster.setCountryId(sessionStateManage.getCountryId());
				promotionMaster.setApplicationCountry(couMaster);
				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(sessionStateManage.getCompanyId());
				promotionMaster.setCompanyMaster(companyMaster);
				Document doc = new Document();
				doc.setDocumentID(getDocumentId());
				promotionMaster.setDocumentMaster(doc);
				promotionMaster.setDocumentFinancialYear(getFinaceYear());
				promotionMaster.setDocumentNo(docRefNumber);
				promotionMaster.setFromDate(getFromDate());
				promotionMaster.setToDate(getToDate());
				promotionMaster.setPromotionDescription(getDescription());
				promotionMaster.setIsActive(Constants.Y);
				promotionMaster.setCreatedBy(sessionStateManage.getUserName());
				promotionMaster.setCreatedDate(new Date());
				List<PromotionPrize> lstPromotionPrize= new ArrayList<PromotionPrize>();
				if(proMasterDataTable!=null && proMasterDataTable.size() > 0){
					for(PromotionMasterDataTable dataTable : proMasterDataTable){						
						
						PromotionPrize promotionPrize = new PromotionPrize();
						CountryMaster countryMaster = new CountryMaster();
						countryMaster.setCountryId(sessionStateManage.getCountryId());
						promotionPrize.setApplicationCountry(countryMaster);				
						Document document = new Document();
						document.setDocumentID(getDocumentId());
						promotionPrize.setDocumentMaster(document);
						promotionPrize.setFromDate(dataTable.getWeekFrDt());
						promotionPrize.setToDate(dataTable.getWeekToDt());
						promotionPrize.setPrizeNo(dataTable.getPrizesNo());
						promotionPrize.setPrizeAmount(dataTable.getAmount());				
						
						lstPromotionPrize.add(promotionPrize);
						
					}			
				}
				iPromotionService.savePromotionMaster(promotionMaster, lstPromotionPrize);
				pageNavigation();
			}				
		}catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");	
		}			
	}
	
	
	public void update(){
		try{
			List<PromotionMasterDataTable> proMasterDataTable = getLstPromotionMasterDataTable();
			List<PromotionPrize> lstPromotionPrize= new ArrayList<PromotionPrize>();
			if(proMasterDataTable!=null && proMasterDataTable.size() > 0){
				for(PromotionMasterDataTable dataTable : proMasterDataTable){					
					
					PromotionPrize promotionPrize = new PromotionPrize();
					promotionPrize.setPromotionPrizeId(dataTable.getPromoPrizeSeq());
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(sessionStateManage.getCountryId());
					promotionPrize.setApplicationCountry(countryMaster);				
					Document document = new Document();
					document.setDocumentID(getDocumentId());
					promotionPrize.setDocumentMaster(document);
					promotionPrize.setFromDate(dataTable.getWeekFrDt());
					promotionPrize.setToDate(dataTable.getWeekToDt());
					promotionPrize.setPrizeNo(dataTable.getPrizesNo());
					promotionPrize.setPrizeAmount(dataTable.getAmount());				
					
					lstPromotionPrize.add(promotionPrize);					
				}
				iPromotionService.updatePromotionPrize(lstPromotionPrize,getPromoMasterSeq(),sessionStateManage.getUserName());
				pageNavigation();
			}
		}catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");	
		}	
	}	
	
	
	public void clear(){
		setProNumDispaly(false);
		setEditButton(true);
		setFromDate(null);
		setToDate(null);
		setDescription(null);
		setPromoNum(null);
		setLstPromotionMasterDataTable(null);
	}
	
	
	public void prizeNumber(PromotionMasterDataTable promotionMasterDataTable){
		
		if(promotionMasterDataTable.getWeekFrDt()==null){
			if(getProNumDispaly()==true){
				proNumSelected();
			}else{
				promotionMasterDataTable.setPrizesNo(null);
			}			
			RequestContext.getCurrentInstance().execute("selectWeekFrmDt.show();");
		}else if(promotionMasterDataTable.getWeekToDt()==null){
			if(getProNumDispaly()==true){
				proNumSelected();
			}else{
				promotionMasterDataTable.setPrizesNo(null);
			}			
			RequestContext.getCurrentInstance().execute("selectWeekToDt.show();");
		}else{
			if(promotionMasterDataTable.getPrizesNo().compareTo(BigDecimal.ZERO)==0){
				if(getProNumDispaly()==true){
					proNumSelected();
				}else{
					promotionMasterDataTable.setPrizesNo(null);
				}
				RequestContext.getCurrentInstance().execute("prizeNum.show();");
			}else if(promotionMasterDataTable.getPrizesNo().compareTo(BigDecimal.ZERO) < 0){
				if(getProNumDispaly()==true){
					proNumSelected();
				}else{
					promotionMasterDataTable.setPrizesNo(null);
				}
				RequestContext.getCurrentInstance().execute("prizeNumLessThan.show();");
			}else if(getLstPromotionMasterDataTable()!=null && getLstPromotionMasterDataTable().size() > 0){					
				int count = 0;
				for(PromotionMasterDataTable promotionMasterTable : getLstPromotionMasterDataTable()){
					if(promotionMasterTable.getWeekFrDt().compareTo(promotionMasterDataTable.getWeekFrDt())==0 && promotionMasterTable.getPrizesNo().compareTo(promotionMasterDataTable.getPrizesNo())==0){
						count = count + 1;
						if (count > 1) {
							break;
						}
					}
				}			
				if (count > 1) {
					if(getProNumDispaly()==true){
						proNumSelected();
					}else{
						promotionMasterDataTable.setPrizesNo(null);
					}
					RequestContext.getCurrentInstance().execute("dupFromDtAndPrizeNum.show();");
					return;
				}
			}
		}		
	}
	
	
	public void amount(PromotionMasterDataTable promotionMasterDataTable){
		if(promotionMasterDataTable.getWeekFrDt()==null){
			if(getProNumDispaly()==true){
				proNumSelected();
			}else{
				promotionMasterDataTable.setAmount(null);
			}			
			RequestContext.getCurrentInstance().execute("selectWeekFrmDt.show();");
		}else if(promotionMasterDataTable.getWeekToDt()==null){
			if(getProNumDispaly()==true){
				proNumSelected();
			}else{
				promotionMasterDataTable.setAmount(null);
			}			
			RequestContext.getCurrentInstance().execute("selectWeekToDt.show();");
		}else if(promotionMasterDataTable.getPrizesNo()==null){
			if(getProNumDispaly()==true){
				proNumSelected();
			}else{
				promotionMasterDataTable.setAmount(null);
			}			
			RequestContext.getCurrentInstance().execute("selectPrizeNum.show();");
		}else{
			if(promotionMasterDataTable.getAmount().compareTo(BigDecimal.ZERO)==0){
				if(getProNumDispaly()==true){
					proNumSelected();
				}else{
					promotionMasterDataTable.setAmount(null);
				}
				RequestContext.getCurrentInstance().execute("amount.show();");
			}else if(promotionMasterDataTable.getAmount().compareTo(BigDecimal.ZERO) < 0){
				if(getProNumDispaly()==true){
					proNumSelected();
				}else{
					promotionMasterDataTable.setAmount(null);
				}
				RequestContext.getCurrentInstance().execute("amountLessThan.show();");
			}
		}			
	}
	
	
	public void headerFromDtSelected(){
		setToDate(null);
	}
	
	
	
	//DATE RANGE COMPARISIONS.
	
	public void headerToDt(){
		if(getFromDate()==null){
			setToDate(null);
			RequestContext.getCurrentInstance().execute("fromDate.show();");
		}else if(getToDate().compareTo(getFromDate())<0){
			setToDate(null);
			RequestContext.getCurrentInstance().execute("toDtLessThanFromDt.show();");
		}else if(getToDate().compareTo(getFromDate())==0){
			setToDate(null);
			RequestContext.getCurrentInstance().execute("toDtEqualFromDt.show();");
		}
	}
	
	public void checkFromDateRange(PromotionMasterDataTable promotionMasterDataTable){
		
		Date cDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(cDate);
		c.add(Calendar.DATE, -1);
		Date newDate = c.getTime();
		
		boolean range = false;
		
		range = promotionMasterDataTable.getWeekFrDt().compareTo(getFromDate()) >=0 && promotionMasterDataTable.getWeekFrDt().compareTo(getToDate()) <0;
		if(range == false){
			if(getProNumDispaly()==true){
				proNumSelected();
			}else{
				promotionMasterDataTable.setWeekFrDt(null);
			}
			RequestContext.getCurrentInstance().execute("fromDtRange.show();");
		} else if(promotionMasterDataTable.getWeekFrDt().compareTo(newDate)>=0){
			if(getProNumDispaly()==true){
				proNumSelected();
			}else{
				promotionMasterDataTable.setWeekFrDt(null);
			}
			RequestContext.getCurrentInstance().execute("weekFromDtValue.show();");
		}
	}
	
	public void checkToDateRange(PromotionMasterDataTable promotionMasterDataTable){
		
		if(promotionMasterDataTable.getWeekFrDt()==null){
			if(getProNumDispaly()==true){
				proNumSelected();
			}else{
				promotionMasterDataTable.setWeekToDt(null);
			}			
			RequestContext.getCurrentInstance().execute("selectWeekFrmDt.show();");
		} else{
			boolean range = false;
			range = promotionMasterDataTable.getWeekToDt().compareTo(getFromDate()) >=0 && promotionMasterDataTable.getWeekToDt().compareTo(getToDate()) <=0;
			if(range == false){
				if(getProNumDispaly()==true){
					proNumSelected();
				}else{
					promotionMasterDataTable.setWeekToDt(null);
				}
				RequestContext.getCurrentInstance().execute("toDtRange.show();");
			}else if(promotionMasterDataTable.getWeekFrDt().compareTo(promotionMasterDataTable.getWeekToDt())>0){
				if(getProNumDispaly()==true){
					proNumSelected();
				}else{
					promotionMasterDataTable.setWeekToDt(null);
				}
				RequestContext.getCurrentInstance().execute("weekToDtLessThanWeekFromDt.show();");
			}else if(promotionMasterDataTable.getWeekFrDt().compareTo(promotionMasterDataTable.getWeekToDt())==0){
				if(getProNumDispaly()==true){
					proNumSelected();
				}else{
					promotionMasterDataTable.setWeekToDt(null);
				}
				RequestContext.getCurrentInstance().execute("weekToDtAndWeekFromDtEquals.show();");
			}
		}		
	}
	
	
	public void exit(){		
		try {	
			clear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	//Getters and setters.

	
	public Date getFromDate() {
		return fromDate;
	}
	
	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getFinaceYear() {
		return finaceYear;
	}

	public void setFinaceYear(BigDecimal finaceYear) {
		this.finaceYear = finaceYear;
	}

	public BigDecimal getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(BigDecimal financialYearId) {
		this.financialYearId = financialYearId;
	}

	public BigDecimal getPromotionDocumentNo() {
		return promotionDocumentNo;
	}

	public void setPromotionDocumentNo(BigDecimal promotionDocumentNo) {
		this.promotionDocumentNo = promotionDocumentNo;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getProNumDispaly() {
		return proNumDispaly;
	}

	public void setProNumDispaly(Boolean proNumDispaly) {
		this.proNumDispaly = proNumDispaly;
	}

	public Boolean getEditButton() {
		return editButton;
	}

	public void setEditButton(Boolean editButton) {
		this.editButton = editButton;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SessionStateManage getSessionStateManage() {
		return sessionStateManage;
	}

	public void setSessionStateManage(SessionStateManage sessionStateManage) {
		this.sessionStateManage = sessionStateManage;
	}

	public List<PromotionMasterDataTable> getLstPromotionMasterDataTable() {
		return lstPromotionMasterDataTable;
	}

	public void setLstPromotionMasterDataTable(
			List<PromotionMasterDataTable> lstPromotionMasterDataTable) {
		this.lstPromotionMasterDataTable = lstPromotionMasterDataTable;
	}

	public IGeneralService<T> getIgeneralService() {
		return igeneralService;
	}

	public void setIgeneralService(IGeneralService<T> igeneralService) {
		this.igeneralService = igeneralService;
	}

	public static Logger getLog() {
		return log;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public IPromotionService getiPromotionService() {
		return iPromotionService;
	}

	public void setiPromotionService(IPromotionService iPromotionService) {
		this.iPromotionService = iPromotionService;
	}

	public BigDecimal getPromoNum() {
		return promoNum;
	}

	public void setPromoNum(BigDecimal promoNum) {
		this.promoNum = promoNum;
	}

	public List<PopulateData> getDocNumbers() {
		return docNumbers;
	}

	public void setDocNumbers(List<PopulateData> docNumbers) {
		this.docNumbers = docNumbers;
	}

	public BigDecimal getExPromoHdSeq() {
		return exPromoHdSeq;
	}

	public void setExPromoHdSeq(BigDecimal exPromoHdSeq) {
		this.exPromoHdSeq = exPromoHdSeq;
	}


	public Date getWeekFromDt() {
		return weekFromDt;
	}


	public void setWeekFromDt(Date weekFromDt) {
		this.weekFromDt = weekFromDt;
	}


	public Date getWeekToDt() {
		return weekToDt;
	}


	public void setWeekToDt(Date weekToDt) {
		this.weekToDt = weekToDt;
	}

	public BigDecimal getPromoMasterSeq() {
		return promoMasterSeq;
	}

	public void setPromoMasterSeq(BigDecimal promoMasterSeq) {
		this.promoMasterSeq = promoMasterSeq;
	}	
	
}

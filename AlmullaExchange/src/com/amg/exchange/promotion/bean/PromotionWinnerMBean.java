package com.amg.exchange.promotion.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cancelreissue.model.ViewRemittanceDocument;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.promotion.model.PromotionDetails;
import com.amg.exchange.promotion.model.PromotionMaster;
import com.amg.exchange.promotion.model.PromotionPrize;
import com.amg.exchange.promotion.service.IPromotionService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("promotionWinnerMBean")
@Scope("session")
public class PromotionWinnerMBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(PromotionWinnerMBean.class);

	private SessionStateManage sessionStateManage=new SessionStateManage();

	private BigDecimal companyId;
	private String errorMessage;
	private BigDecimal finaceYear;
	private BigDecimal financialYearId;
	private BigDecimal promotionDocumentNo;
	private List<PromotionWinnerDataTable> lstPromotionWinnerDataTable;
	private BigDecimal documentId;
	private List<PopulateData> lstPromotionDocNo;
	
	private String fromHDate;
	private String toDate;
	private String promotionDescription;
	private BigDecimal promotionMasterid;
	private Date drawDate;
	
	
	
	public Date getDrawDate() {
		return drawDate;
	}

	public void setDrawDate(Date drawDate) {
		this.drawDate = drawDate;
	}

	public BigDecimal getPromotionMasterid() {
		return promotionMasterid;
	}

	public void setPromotionMasterid(BigDecimal promotionMasterid) {
		this.promotionMasterid = promotionMasterid;
	}

	/*public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}*/
	public String getFromHDate() {
		return fromHDate;
	}

	public void setFromHDate(String fromHDate) {
		this.fromHDate = fromHDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getPromotionDescription() {
		return promotionDescription;
	}

	public void setPromotionDescription(String promotionDescription) {
		this.promotionDescription = promotionDescription;
	}

	public List<PopulateData> getLstPromotionDocNo() {
		return lstPromotionDocNo;
	}

	public void setLstPromotionDocNo(List<PopulateData> lstPromotionDocNo) {
		this.lstPromotionDocNo = lstPromotionDocNo;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}


	public BigDecimal getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(BigDecimal financialYearId) {
		this.financialYearId = financialYearId;
	}


	public BigDecimal getFinaceYear() {
		return finaceYear;
	}

	public void setFinaceYear(BigDecimal finaceYear) {
		this.finaceYear = finaceYear;
	}

	public BigDecimal getPromotionDocumentNo() {
		return promotionDocumentNo;
	}

	public void setPromotionDocumentNo(BigDecimal promotionDocumentNo) {
		this.promotionDocumentNo = promotionDocumentNo;
	}
	

	public List<PromotionWinnerDataTable> getLstPromotionWinnerDataTable() {
		return lstPromotionWinnerDataTable;
	}

	public void setLstPromotionWinnerDataTable(
			List<PromotionWinnerDataTable> lstPromotionWinnerDataTable) {
		this.lstPromotionWinnerDataTable = lstPromotionWinnerDataTable;
	}


	@Autowired
	IGeneralService<T> igeneralService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	IPromotionService iPromotionService;
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	
	public void pageNavigation() {
		try {
			mainClear();
			getCompanyListFromDB();
			getFinaceYearFromDb();
			getDocumentIdFromDb();
			getDocNumberPromotion();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../promotion/promotionWinner.xhtml");
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
			HashMap<String, String> outPutValues= igeneralService.getNextDocumentRefNumber(sessionStateManage.getCountryId().intValue() ,sessionStateManage.getCompanyId().intValue(),Integer.parseInt(Constants.DOCUMENT_CODE_FOR_BANK_TRANSFER), finaceYear.intValue(),processIn,sessionStateManage.getCountryBranchCode());
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
	
	private void getDocNumberPromotion()
	{
		
		List<PopulateData> lstPopulateData=iPromotionService.getPromotionDocumentNo(getCompanyId(), getDocumentId(), getFinaceYear());
		setLstPromotionDocNo(lstPopulateData);
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
	
	public void addEmptyRow()
	{
		try{
			
			if(getDrawDate()!=null)
			{
				boolean drawDateCheck=drawDateCheckForSave();
				if(drawDateCheck)
				{
					return;
				}
			}
			
			boolean checkHeaderMandatory=mandatoryHeaderCheck();
			if(checkHeaderMandatory)
			{
				return;
			}
			BigDecimal insertIndex= BigDecimal.ZERO;
			List<PromotionWinnerDataTable> lstExistPromotionWinnerDataTable  =getLstPromotionWinnerDataTable();
			//List<PromotionWinnerDataTable> lstPromotionWinnerDataTable = new ArrayList<PromotionWinnerDataTable>();
			if(lstExistPromotionWinnerDataTable!=null && lstExistPromotionWinnerDataTable.size()>0)
			{
				
				for(PromotionWinnerDataTable promotionWinnerDataTable :lstExistPromotionWinnerDataTable)
				{
					boolean checkMandatory=mandatoryCheck(promotionWinnerDataTable);
					if(checkMandatory)
					{
						return;
					}
				}
				PromotionWinnerDataTable existPromotionWinnerDataTable =lstExistPromotionWinnerDataTable.get(lstExistPromotionWinnerDataTable.size()-1);
				insertIndex=existPromotionWinnerDataTable.getLineIndex();
				
				insertIndex=insertIndex.add(BigDecimal.ONE);
				
				//lstPromotionWinnerDataTable.addAll(lstExistPromotionWinnerDataTable);
			}else
			{
				lstExistPromotionWinnerDataTable= new ArrayList<PromotionWinnerDataTable>();
				
			}
			
			
			Map<BigDecimal ,BigDecimal> mapPrizeAmount = new HashMap<BigDecimal ,BigDecimal>();
			Map<BigDecimal ,BigDecimal> mapPrizeIdPrizeNo = new HashMap<BigDecimal ,BigDecimal>();
			Map<String ,String> mapDateMatch = new HashMap<String ,String>();
			List<BigDecimal> lstPrizeNo = new ArrayList<BigDecimal>();
			List<BigDecimal> lstPrizeAmount = new ArrayList<BigDecimal>();
			Set<String> setFromDate= new HashSet<String>();
			Set<String> setToDate= new HashSet<String>();
			
			List<PromotionMaster> lstPromotionMaster=iPromotionService.getPromotionMasterDetails(getCompanyId(), getDocumentId(), getFinaceYear(), getPromotionDocumentNo());
			if(lstPromotionMaster!=null && lstPromotionMaster.size()>0)
			{
				
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				for(PromotionMaster promotionMaster :lstPromotionMaster)
				{
					HashMap<String, List<PromotionPrize>> rtnMap=iPromotionService.getPromotionPrizeDetailsForWinner(sessionStateManage.getCountryId(), getDocumentId(), promotionMaster.getPromotionMasterId());
					
					List<PromotionPrize> lstDeclaredPromotionPrize =rtnMap.get("DeclaredPromotionPrize");
					List<PromotionPrize> lstNotDeclaredPromotionPrize =rtnMap.get("NotDeclaredPromotionPrize");
					if(lstDeclaredPromotionPrize!=null && lstNotDeclaredPromotionPrize!=null)
					{
						if(lstDeclaredPromotionPrize.size()>0 && lstNotDeclaredPromotionPrize.size()==0)
						{
							RequestContext.getCurrentInstance().execute("noPrize.show();");
							return;
						}
					}
					
					//List<PromotionPrize> lstPromotionPrize=iPromotionService.getPromotionPrizeDetails(sessionStateManage.getCountryId(), getDocumentId(), promotionMaster.getPromotionMasterId());
					if(lstNotDeclaredPromotionPrize!=null && lstNotDeclaredPromotionPrize.size()>0)
					{
						for(PromotionPrize promotionPrize :lstNotDeclaredPromotionPrize)
						{
							
							setFromDate.add(format.format(promotionPrize.getFromDate()));
							setToDate.add(format.format(promotionPrize.getToDate()));
							lstPrizeNo.add(promotionPrize.getPrizeNo());
							lstPrizeAmount.add(promotionPrize.getPrizeAmount());
							mapPrizeAmount.put(promotionPrize.getPrizeNo(), promotionPrize.getPrizeAmount());
							mapDateMatch.put(format.format(promotionPrize.getFromDate()), format.format(promotionPrize.getToDate()));
							mapPrizeIdPrizeNo.put(promotionPrize.getPrizeNo(),promotionPrize.getPromotionPrizeId());
							
						}
						List<String>  lstFromDate1= new ArrayList<String>(setFromDate);
						List<Date>  lstFromDateFormat= new ArrayList<Date>();
						
						if(lstFromDateFormat!=null)
						{
							for(String date:lstFromDate1)
							{
								lstFromDateFormat.add(format.parse(date));
							}
						}
						
						 Collections.sort(lstFromDateFormat, new Comparator<Date>(){
							 
					            @Override
					            public int compare(Date o1, Date o2) {
					                return o1.compareTo(o2);
					            }
					        });
						 
						 List<String>  lstFromDate= new ArrayList<String>();
						 
						 if(lstFromDate!=null)
						 {
							 for(Date date:lstFromDateFormat)
							 {
								 lstFromDate.add(format.format(date));
							 }
						 }
						/*for(PromotionPrize promotionPrize :lstPromotionPrize)
						{
							PromotionWinnerDataTable promotionWinnerDataTable= new PromotionWinnerDataTable();
							promotionWinnerDataTable.setPromotionMasterId(promotionMaster.getPromotionMasterId());
							promotionWinnerDataTable.setPromotionPrizeId(promotionPrize.getPromotionPrizeId());
							promotionWinnerDataTable.setTempPrizeAmount(promotionPrize.getPrizeAmount());
							promotionWinnerDataTable.setTempToDate(format.format(promotionPrize.getToDate()));
							
							List<String>  lstFromDate= new ArrayList<String>(setFromDate);
							List<String>  lstToate= new ArrayList<String>(setToDate);
							
							promotionWinnerDataTable.setLstFromDate(lstFromDate);
							promotionWinnerDataTable.setLstToDate(lstToate);
							promotionWinnerDataTable.setLstPrizeNo(lstPrizeNo);
							promotionWinnerDataTable.setLstPrizeAmount(lstPrizeAmount);
							lstPromotionWinnerDataTable.add(promotionWinnerDataTable);
							
						}*/
						
						PromotionWinnerDataTable promotionWinnerDataTable= new PromotionWinnerDataTable();
						promotionWinnerDataTable.setPromotionMasterId(promotionMaster.getPromotionMasterId());
						promotionWinnerDataTable.setLineIndex(insertIndex);
						//promotionWinnerDataTable.setPromotionPrizeId(promotionPrize.getPromotionPrizeId());
						//promotionWinnerDataTable.setTempPrizeAmount(promotionPrize.getPrizeAmount());
						//promotionWinnerDataTable.setTempToDate(format.format(promotionPrize.getToDate()));
						
						//List<String>  lstFromDate= new ArrayList<String>(setFromDate);
						List<String>  lstToate= new ArrayList<String>(setToDate);
						
						promotionWinnerDataTable.setLstFromDate(lstFromDate);
						promotionWinnerDataTable.setLstToDate(lstToate);
						//promotionWinnerDataTable.setLstPrizeNo(lstPrizeNo);
						promotionWinnerDataTable.setLstPrizeAmount(lstPrizeAmount);
						promotionWinnerDataTable.setMapPrizeAmount(mapPrizeAmount);
						promotionWinnerDataTable.setMapDateMatch(mapDateMatch);
						promotionWinnerDataTable.setMapPrizeIdPrizeNo(mapPrizeIdPrizeNo);
						
						
						lstExistPromotionWinnerDataTable.add(insertIndex.intValue(),promotionWinnerDataTable);
					}
					
					
				}
				
			}
						
			setLstPromotionWinnerDataTable(lstExistPromotionWinnerDataTable);
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			      
		}
		
		
	}
	public void getPrizeAmount(PromotionWinnerDataTable promotionWinnerDataTable)
	{
		try{
			boolean duplicatePrizeNo=checkDuplicatePrizeNo(promotionWinnerDataTable);
			if(duplicatePrizeNo)
			{
				return;
			}
			if(promotionWinnerDataTable!=null && promotionWinnerDataTable.getPrizeNo().compareTo(BigDecimal.ZERO)!=0)
			{
				/*List<PromotionWinnerDataTable> lstExistPromotionWinnerDataTable  =getLstPromotionWinnerDataTable();
				for(PromotionWinnerDataTable lpromotionWinnerDataTable :lstExistPromotionWinnerDataTable)
				{
					if(promotionWinnerDataTable.getPromotionPrizeId().compareTo(lpromotionWinnerDataTable.getPromotionPrizeId())==0)
					{
						promotionWinnerDataTable.setPrizeAmount(lpromotionWinnerDataTable.getTempPrizeAmount());
					}
				}*/
				
				Map<BigDecimal ,BigDecimal> mapPrizeAmount=promotionWinnerDataTable.getMapPrizeAmount();
				Map<BigDecimal ,BigDecimal> mapPrizeIdPrizeNo =promotionWinnerDataTable.getMapPrizeIdPrizeNo();
				
				BigDecimal matchPrizeAmt=  mapPrizeAmount.get(promotionWinnerDataTable.getPrizeNo());
				BigDecimal promotionPrizeId= mapPrizeIdPrizeNo.get(promotionWinnerDataTable.getPrizeNo());
				promotionWinnerDataTable.setPrizeAmount(matchPrizeAmt);
				promotionWinnerDataTable.setPromotionPrizeId(promotionPrizeId);
				
				
			}else
			{
				promotionWinnerDataTable.setPrizeNo(null);
				promotionWinnerDataTable.setPrizeAmount(null);
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			      
		}
		
	}
	
	private boolean checkDuplicatePrizeNo(PromotionWinnerDataTable promotionWinnerDataTable)
	{
		boolean duplicatePrizeNo=false;
		try{

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date fromDate=format.parse(promotionWinnerDataTable.getFromDate());
			List<PromotionWinnerDataTable> lstExistPromotionWinnerDataTable  =getLstPromotionWinnerDataTable();
			if(lstExistPromotionWinnerDataTable!=null && lstExistPromotionWinnerDataTable.size()>0)
			{
				for(PromotionWinnerDataTable lpromotionWinnerDataTable :lstExistPromotionWinnerDataTable)
				{
					if(lpromotionWinnerDataTable.getLineIndex().compareTo(promotionWinnerDataTable.getLineIndex())!=0)
					{
						Date lfromDate=format.parse(lpromotionWinnerDataTable.getFromDate());
						if(fromDate.compareTo(lfromDate)==0)
						{
							if(lpromotionWinnerDataTable.getPrizeNo().compareTo(promotionWinnerDataTable.getPrizeNo())==0)
							{
								duplicatePrizeNo=true;
								promotionWinnerDataTable.setPrizeNo(null);
								promotionWinnerDataTable.setPrizeAmount(null);
								RequestContext.getCurrentInstance().execute("duplicatePrizeNo.show();");
								break;
							}
						}

					}
				}
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");

		}

		return duplicatePrizeNo;
	}
	public void getToDate(PromotionWinnerDataTable promotionWinnerDataTable)
	{
		try{
			if(promotionWinnerDataTable!=null && !promotionWinnerDataTable.getFromDate().equalsIgnoreCase("0"))
			{
				/*List<PromotionWinnerDataTable> lstExistPromotionWinnerDataTable  =getLstPromotionWinnerDataTable();
				for(PromotionWinnerDataTable lpromotionWinnerDataTable :lstExistPromotionWinnerDataTable)
				{
					if(promotionWinnerDataTable.getPromotionPrizeId().compareTo(lpromotionWinnerDataTable.getPromotionPrizeId())==0)
					{
						promotionWinnerDataTable.setToDate(lpromotionWinnerDataTable.getTempToDate());
					}
				}*/
				Map<BigDecimal ,BigDecimal> mapPrizeAmount = new HashMap<BigDecimal ,BigDecimal>();
				List<BigDecimal> lstPrizeNo = new ArrayList<BigDecimal>();
				Map<BigDecimal ,BigDecimal> mapPrizeIdPrizeNo = new HashMap<BigDecimal ,BigDecimal>();
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				List<PromotionPrize> lstNotDeclaredPromotionPrize=iPromotionService.getPrizeNoFromDate(promotionWinnerDataTable.getPromotionMasterId(), format.parse(promotionWinnerDataTable.getFromDate()));
				
				if(lstNotDeclaredPromotionPrize!=null && lstNotDeclaredPromotionPrize.size()>0)
				{
					for(PromotionPrize promotionPrize:lstNotDeclaredPromotionPrize)
					{
						lstPrizeNo.add(promotionPrize.getPrizeNo());
						mapPrizeAmount.put(promotionPrize.getPrizeNo(), promotionPrize.getPrizeAmount());
						mapPrizeIdPrizeNo.put(promotionPrize.getPrizeNo(),promotionPrize.getPromotionPrizeId());
					}
				}
				
				Map<String ,String> mapDateMatch =promotionWinnerDataTable.getMapDateMatch();
				String toDate= mapDateMatch.get(promotionWinnerDataTable.getFromDate());
				promotionWinnerDataTable.setToDate(toDate);
				promotionWinnerDataTable.setLstPrizeNo(lstPrizeNo);
				promotionWinnerDataTable.setMapPrizeAmount(mapPrizeAmount);
				promotionWinnerDataTable.setMapPrizeIdPrizeNo(mapPrizeIdPrizeNo);
				promotionWinnerDataTable.setPrizeNo(null);
				promotionWinnerDataTable.setPrizeAmount(null);
				
			}else
			{
				promotionWinnerDataTable.setFromDate(null);
				promotionWinnerDataTable.setToDate(null);
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			      
		}
		
	}
	private void checkDupicateFromDate(PromotionWinnerDataTable promotionWinnerDataTable)
	{
		boolean checkDuplicateFromDt=false;
		List<PromotionWinnerDataTable> lstExistPromotionWinnerDataTable  =getLstPromotionWinnerDataTable();
		if(lstExistPromotionWinnerDataTable!=null && lstExistPromotionWinnerDataTable.size()>0)
		{
			for(PromotionWinnerDataTable lpromotionWinnerDataTable :lstExistPromotionWinnerDataTable)
			{
				if(lpromotionWinnerDataTable.getLineIndex().compareTo(promotionWinnerDataTable.getLineIndex())!=0)
				{
					if(lpromotionWinnerDataTable.getFromDate().equalsIgnoreCase(promotionWinnerDataTable.getFromDate()))
					{
						checkDuplicateFromDt=true;
						promotionWinnerDataTable.setFromDate(null);
						promotionWinnerDataTable.setToDate(null);
						RequestContext.getCurrentInstance().execute("duplicateFromDt.show();");
						break;
					}
					
				}
			}
		}
	}
	public void getCustName(PromotionWinnerDataTable promotionWinnerDataTable)
	{
		try{
			String civilId=promotionWinnerDataTable.getCivilId();
			if(civilId!=null)
			{
				if(civilId.equalsIgnoreCase(""))
				{
					promotionWinnerDataTable.setCustomerName(null);
					promotionWinnerDataTable.setCustMobileNo(null);
					promotionWinnerDataTable.setCivilId(null);
					promotionWinnerDataTable.setCustomerId(null);
					return;
				}
				List<CustomerIdProof> customerDetailsList = iPersonalRemittanceService.getCustomerDetailsThroughCusReg(civilId);
				
				if(customerDetailsList!=null && customerDetailsList.size()>0)
				{
					StringBuffer engNameConcat = new StringBuffer();
					CustomerIdProof customerIdProof=customerDetailsList.get(0);
					Customer customer= customerIdProof.getFsCustomer();
					
					if (customer.getFirstName() != null) {
						engNameConcat.append(customer.getFirstName().trim()+" ");
					}
					
					if (customer.getMiddleName() != null) {
						engNameConcat.append(customer.getMiddleName().trim()+" ");
					}
					/*if (customer.getShortName() != null) {
						engNameConcat.append(customer.getShortName().trim()+" ");
					}*/
					if (customer.getLastName() != null) {
						engNameConcat.append(customer.getLastName().trim());
					}
					promotionWinnerDataTable.setCustomerName(engNameConcat.toString());
					promotionWinnerDataTable.setCustMobileNo(customer.getMobile());
					promotionWinnerDataTable.setCustomerId(customer.getCustomerId());
					verifyRemittance(promotionWinnerDataTable);
				}else
				{
					promotionWinnerDataTable.setCustomerName(null);
					promotionWinnerDataTable.setCustMobileNo(null);
					promotionWinnerDataTable.setCivilId(null);
					promotionWinnerDataTable.setCustomerId(null);
					RequestContext.getCurrentInstance().execute("noCustomer.show();");
				}
			}else
			{
				promotionWinnerDataTable.setCustomerName(null);
				promotionWinnerDataTable.setCustMobileNo(null);
				promotionWinnerDataTable.setCivilId(null);
				//RequestContext.getCurrentInstance().execute("noCustomer.show();");
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			      
		}
		
	
	}
	private boolean mandatoryHeaderCheck()
	{
		boolean checkMandatory=false;
		if(getFinaceYear()==null)
		{
			checkMandatory=true;
			RequestContext.getCurrentInstance().execute("promotionYear.show();");
			return checkMandatory;
		}
		if(getPromotionDocumentNo()==null)
		{
			checkMandatory=true;
			RequestContext.getCurrentInstance().execute("promotionNo.show();");
			return checkMandatory;
		}
		
		if(getFromHDate()==null)
		{
			checkMandatory=true;
			RequestContext.getCurrentInstance().execute("fromDate.show();");
			return checkMandatory;
		}
		if(getToDate()== null)
		{
			checkMandatory=true;
			RequestContext.getCurrentInstance().execute("toDate.show();");
			return checkMandatory;
		}
		
		if(getPromotionDescription()== null)
		{
			checkMandatory=true;
			RequestContext.getCurrentInstance().execute("promotionDescription.show();");
			return checkMandatory;
		}
		
		if(getDrawDate()== null)
		{
			checkMandatory=true;
			RequestContext.getCurrentInstance().execute("drawDateiid.show();");
			return checkMandatory;
		}
		return checkMandatory;
	}
	private boolean mandatoryCheck(PromotionWinnerDataTable promotionWinnerDataTable)
	{
		boolean checkMandatory=false;
		if(promotionWinnerDataTable!=null)
		{
			if(promotionWinnerDataTable.getFromDate()== null)
			{
				checkMandatory=true;
				RequestContext.getCurrentInstance().execute("fromDate.show();");
				return checkMandatory;
			}
			if(promotionWinnerDataTable.getToDate()== null)
			{
				checkMandatory=true;
				RequestContext.getCurrentInstance().execute("toDate.show();");
				return checkMandatory;
			}
			if(promotionWinnerDataTable.getCivilId()== null)
			{
				checkMandatory=true;
				RequestContext.getCurrentInstance().execute("civilId.show();");
				return checkMandatory;
			}
			
			if(promotionWinnerDataTable.getPrizeNo()== null)
			{
				checkMandatory=true;
				RequestContext.getCurrentInstance().execute("prizeNo.show();");
				return checkMandatory;
			}else if(promotionWinnerDataTable.getPrizeNo().compareTo(BigDecimal.ZERO)==0)
			{
				checkMandatory=true;
				RequestContext.getCurrentInstance().execute("prizeNo.show();");
				return checkMandatory;
			}
			
			if(promotionWinnerDataTable.getPrizeAmount()== null)
			{
				checkMandatory=true;
				RequestContext.getCurrentInstance().execute("prizeAmount.show();");
				return checkMandatory;
			}
			if(promotionWinnerDataTable.getTransactionNo()== null)
			{
				checkMandatory=true;
				RequestContext.getCurrentInstance().execute("transactionNo.show();");
				return checkMandatory;
			}
			if(promotionWinnerDataTable.getTransactionYear()== null)
			{
				checkMandatory=true;
				RequestContext.getCurrentInstance().execute("transactionYear.show();");
				return checkMandatory;
			}
			
			if(promotionWinnerDataTable.getRemittancTransactionId()== null)
			{
				checkMandatory=true;
				RequestContext.getCurrentInstance().execute("noRemittance.show();");
				return checkMandatory;
			}
			/*if(promotionWinnerDataTable.getValidUpTo()== null)
			{
				checkMandatory=true;
				RequestContext.getCurrentInstance().execute("validUpTo.show();");
				return checkMandatory;
			}*/
			
		}else
		{
			checkMandatory=true;
			RequestContext.getCurrentInstance().execute("mandatoryCheck.show();");
			return checkMandatory;
		}
		return checkMandatory;
	}
	
	public void getPromotionMaster()
	{
		try{
			clearPromoMaster();
			if(getPromotionDocumentNo()!=null && getPromotionDocumentNo().compareTo(BigDecimal.ZERO)==0)
			{
				setPromotionDocumentNo(null);
				RequestContext.getCurrentInstance().execute("documentNoZero.show();");
				return;
			}
			List<PromotionMaster> lstPromotionMaster=iPromotionService.getPromotionMasterDetails(getCompanyId(), getDocumentId(), getFinaceYear(), getPromotionDocumentNo());
			if(lstPromotionMaster!=null && lstPromotionMaster.size()>0)
			{
				Map<BigDecimal ,BigDecimal> mapPrizeAmount = new HashMap<BigDecimal ,BigDecimal>();
				Map<BigDecimal ,BigDecimal> mapPrizeIdPrizeNo = new HashMap<BigDecimal ,BigDecimal>();
				Map<String ,String> mapDateMatch = new HashMap<String ,String>();
				List<BigDecimal> lstPrizeNo = new ArrayList<BigDecimal>();
				List<BigDecimal> lstPrizeAmount = new ArrayList<BigDecimal>();
				Set<String> setFromDate= new HashSet<String>();
				Set<String> setToDate= new HashSet<String>();
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				for(PromotionMaster promotionMaster :lstPromotionMaster)
				{
					
					setPromotionMasterid(promotionMaster.getPromotionMasterId());
					setFromHDate(format.format(promotionMaster.getFromDate()));
					setToDate(format.format(promotionMaster.getToDate()));
					setPromotionDescription(promotionMaster.getPromotionDescription());
					
					List<PromotionWinnerDataTable> lstPromotionWinnerDataTable = new ArrayList<PromotionWinnerDataTable>();
					List<PromotionDetails> lstPromotionDetails=iPromotionService.getPromotionDetails(promotionMaster.getPromotionMasterId());
					if(lstPromotionDetails!=null && lstPromotionDetails.size()>0)
					{
						List<PromotionPrize> lstPromotionPrize=iPromotionService.getPromotionPrizeDetails(sessionStateManage.getCountryId(), getDocumentId(), promotionMaster.getPromotionMasterId());
						if(lstPromotionPrize!=null && lstPromotionPrize.size()>0)
						{
							for(PromotionPrize promotionPrize :lstPromotionPrize)
							{
								
								setFromDate.add(format.format(promotionPrize.getFromDate()));
								setToDate.add(format.format(promotionPrize.getToDate()));
								lstPrizeNo.add(promotionPrize.getPrizeNo());
								lstPrizeAmount.add(promotionPrize.getPrizeAmount());
								mapPrizeAmount.put(promotionPrize.getPrizeNo(), promotionPrize.getPrizeAmount());
								mapDateMatch.put(format.format(promotionPrize.getFromDate()), format.format(promotionPrize.getToDate()));
								mapPrizeIdPrizeNo.put(promotionPrize.getPrizeNo(),promotionPrize.getPromotionPrizeId());
								
							}
						}
						if(lstPromotionDetails!=null && lstPromotionDetails.size()>0)
						{
							BigDecimal insertIndex= BigDecimal.ZERO;
							for(PromotionDetails promotionDetails :lstPromotionDetails)
							{
								setDrawDate(promotionDetails.getDrawDate());
								PromotionWinnerDataTable promotionWinnerDataTable= new PromotionWinnerDataTable();
								promotionWinnerDataTable.setPromotionMasterId(promotionMaster.getPromotionMasterId());
								promotionWinnerDataTable.setPromotionPrizeId(promotionDetails.getPromotionPrize().getPromotionPrizeId());
								promotionWinnerDataTable.setPrizeAmount(promotionDetails.getPromotionPrize().getPrizeAmount());
								promotionWinnerDataTable.setPrizeNo(promotionDetails.getPromotionPrize().getPrizeNo());
								promotionWinnerDataTable.setFromDate(format.format(promotionDetails.getPromotionPrize().getFromDate()));
								promotionWinnerDataTable.setToDate(format.format(promotionDetails.getPromotionPrize().getToDate()));
								promotionWinnerDataTable.setPromotionDetailsId(promotionDetails.getPromotionDetailsId());
								promotionWinnerDataTable.setCreatedBy(promotionDetails.getCreatedBy());
								promotionWinnerDataTable.setCreatedDate(promotionDetails.getCreatedDate());
								promotionWinnerDataTable.setModifiedBy(promotionDetails.getModifiedBy());
								promotionWinnerDataTable.setModifiedDate(promotionDetails.getModifiedDate());
								List<Customer> customerList = icustomerRegistrationService.getCustomerInfo(promotionDetails.getCustomerId());
								if(customerList!=null && customerList.size()>0)
								{
									StringBuffer engNameConcat = new StringBuffer();
									
									Customer customer= customerList.get(0);
									
									if (customer.getFirstName() != null) {
										engNameConcat.append(customer.getFirstName().trim()+" ");
									}
									
									if (customer.getMiddleName() != null) {
										engNameConcat.append(customer.getMiddleName().trim()+" ");
									}
									/*if (customer.getShortName() != null) {
										engNameConcat.append(customer.getShortName().trim());
									}*/
									if (customer.getLastName() != null) {
										engNameConcat.append(customer.getLastName().trim());
									}
									promotionWinnerDataTable.setCustomerName(engNameConcat.toString());
									promotionWinnerDataTable.setCustMobileNo(customer.getMobile());
									promotionWinnerDataTable.setCustomerId(customer.getCustomerId());
									promotionWinnerDataTable.setCivilId(customer.getCivilId());
								}
								List<ViewRemittanceDocument> lstViewRemittanceDocument=iPromotionService.verifyRemittanceBasedOnTransId(sessionStateManage.getCountryId() ,getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE), promotionDetails.getRemittanceTrasactionId());
								if(lstViewRemittanceDocument!=null && lstViewRemittanceDocument.size()>0)
								{
									ViewRemittanceDocument viewRemittanceDocument=lstViewRemittanceDocument.get(0);
									promotionWinnerDataTable.setTransactionYear(viewRemittanceDocument.getDocumentFinanceYear());
									promotionWinnerDataTable.setTransactionNo(viewRemittanceDocument.getDocumentNo());
									promotionWinnerDataTable.setRemittancTransactionId(promotionDetails.getRemittanceTrasactionId());
								}
								
								
								//promotionWinnerDataTable.set
								List<String>  lstFromDate1= new ArrayList<String>(setFromDate);
								List<Date>  lstFromDateFormat= new ArrayList<Date>();
								
								if(lstFromDateFormat!=null)
								{
									for(String date:lstFromDate1)
									{
										lstFromDateFormat.add(format.parse(date));
									}
								}
								
								 Collections.sort(lstFromDateFormat, new Comparator<Date>(){
									 
							            @Override
							            public int compare(Date o1, Date o2) {
							                return o1.compareTo(o2);
							            }
							        });
								 
								 List<String>  lstFromDate= new ArrayList<String>();
								 
								 if(lstFromDate!=null)
								 {
									 for(Date date:lstFromDateFormat)
									 {
										 lstFromDate.add(format.format(date));
									 }
								 }
								
								 List<BigDecimal> lstPrizeNo1 = new ArrayList<BigDecimal>();
								 List<PromotionPrize> lstNotDeclaredPromotionPrize=iPromotionService.getPrizeNoFromDate(promotionWinnerDataTable.getPromotionMasterId(), format.parse(promotionWinnerDataTable.getFromDate()));
									
									if(lstNotDeclaredPromotionPrize!=null && lstNotDeclaredPromotionPrize.size()>0)
									{
										for(PromotionPrize promotionPrize:lstNotDeclaredPromotionPrize)
										{
											lstPrizeNo1.add(promotionPrize.getPrizeNo());
											mapPrizeAmount.put(promotionPrize.getPrizeNo(), promotionPrize.getPrizeAmount());
										}
									}	
									
								List<String>  lstToate= new ArrayList<String>(setToDate);
								
								promotionWinnerDataTable.setLstFromDate(lstFromDate);
								promotionWinnerDataTable.setLstToDate(lstToate);
								promotionWinnerDataTable.setLstPrizeNo(lstPrizeNo1);
								promotionWinnerDataTable.setLstPrizeAmount(lstPrizeAmount);
								promotionWinnerDataTable.setMapPrizeAmount(mapPrizeAmount);
								promotionWinnerDataTable.setMapDateMatch(mapDateMatch);
								promotionWinnerDataTable.setMapPrizeIdPrizeNo(mapPrizeIdPrizeNo);
								promotionWinnerDataTable.setLineIndex(insertIndex);
								lstPromotionWinnerDataTable.add(promotionWinnerDataTable);
								insertIndex=insertIndex.add(BigDecimal.ONE);
							}
							setLstPromotionWinnerDataTable(lstPromotionWinnerDataTable);
						}
						
					}else
					{
						setLstPromotionWinnerDataTable(null);
					}
				
				}
			}else
			{
				RequestContext.getCurrentInstance().execute("noPromoMaster.show();");
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		

	}
	
	private void clearPromoMaster()
	{
		setPromotionMasterid(null);
		setFromHDate(null);
		setToDate(null);
		setPromotionDescription(null);
		setDrawDate(null);
	}
	
	public void mainClear()
	{
		
		setPromotionDocumentNo(null);
		setPromotionMasterid(null);
		setFromHDate(null);
		setToDate(null);
		setPromotionDescription(null);
		setDrawDate(null);
		setLstPromotionWinnerDataTable(null);
	}
	
	public void savePromotionalDetails()
	{
		try{
			if(getDrawDate()!=null)
			{
				boolean drawDateCheck=drawDateCheckForSave();
				if(drawDateCheck)
				{
					return;
				}
			}
			
			
			boolean checkHeaderMandatory=mandatoryHeaderCheck();
			if(checkHeaderMandatory)
			{
				return;
			}
			
			List<PromotionWinnerDataTable> lstExistPromotionWinnerDataTable  =getLstPromotionWinnerDataTable();
			List<PromotionDetails> lstPromotionDetails= new ArrayList<PromotionDetails>();

			if(lstExistPromotionWinnerDataTable!=null && lstExistPromotionWinnerDataTable.size()>0)
			{
				for(PromotionWinnerDataTable promotionWinnerDataTable :lstExistPromotionWinnerDataTable)
				{
					boolean checkMandatory=mandatoryCheck(promotionWinnerDataTable);
					if(checkMandatory)
					{
						return;
					}
				}
				// Date currentDatePlusOne=addOneYear();a
				for(PromotionWinnerDataTable promotionWinnerDataTable :lstExistPromotionWinnerDataTable)
				{
					PromotionDetails  promotionDetails =new PromotionDetails();

					promotionDetails.setPromotionDetailsId(promotionWinnerDataTable.getPromotionDetailsId());
					
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(sessionStateManage.getCountryId());
					promotionDetails.setApplicationCountry(countryMaster);

					promotionDetails.setCustomerId(promotionWinnerDataTable.getCustomerId());

					promotionDetails.setDrawDate(getDrawDate());
					promotionDetails.setPrizeNo(promotionWinnerDataTable.getPrizeNo());

					PromotionMaster promotionMaster = new PromotionMaster();
					promotionMaster.setPromotionMasterId(promotionWinnerDataTable.getPromotionMasterId());
					promotionDetails.setPromotionMaster(promotionMaster);

					PromotionPrize promotionPrize = new PromotionPrize();
					promotionPrize.setPromotionPrizeId(promotionWinnerDataTable.getPromotionPrizeId());
					promotionDetails.setPromotionPrize(promotionPrize);
					
					promotionDetails.setRemittanceTrasactionId(promotionWinnerDataTable.getRemittancTransactionId());
					
					if(promotionWinnerDataTable.getPromotionDetailsId()!=null)
					{
						promotionDetails.setCreatedBy(promotionWinnerDataTable.getCreatedBy());
						promotionDetails.setCreatedDate(promotionWinnerDataTable.getCreatedDate());
						promotionDetails.setModifiedBy(sessionStateManage.getUserName());
						promotionDetails.setModifiedDate(new Date());
					}else
					{
						promotionDetails.setCreatedBy(sessionStateManage.getUserName());
						promotionDetails.setCreatedDate(new Date());
						
					}
					
			        
					//promotionDetails.setValidUpto(currentDatePlusOne);
					lstPromotionDetails.add(promotionDetails);

				}
				iPromotionService.savePromotionDetails(lstPromotionDetails);
				RequestContext.getCurrentInstance().execute("success.show();");
			}else
			{
				RequestContext.getCurrentInstance().execute("noPromotionPrize.show();");
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}
	
	public void okSelected(){
		mainClear();
	}
	
	private Date addOneYear()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date currentDate = new Date();
       

        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        // manipulate date
        c.add(Calendar.YEAR, 1);
       /* c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, 1); //same with c.add(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.HOUR, 1);
        c.add(Calendar.MINUTE, 1);
        c.add(Calendar.SECOND, 1);*/

        // convert calendar to date
        Date currentDatePlusOne = c.getTime();
        return currentDatePlusOne;
	}
	public boolean verifyRemittance(PromotionWinnerDataTable promotionWinnerDataTable)
	{
		boolean checkRemittance=false;
		try{
			if(promotionWinnerDataTable!=null && promotionWinnerDataTable.getTransactionNo()!=null && promotionWinnerDataTable.getTransactionYear()!=null)
			{
				boolean duplicateTransNo=checkDuplicateTransactionNo(promotionWinnerDataTable);
				if(duplicateTransNo)
				{
					checkRemittance=true;
					return checkRemittance;
				}
				
				BigDecimal transactionYear= promotionWinnerDataTable.getTransactionYear();
				BigDecimal transactionNo=promotionWinnerDataTable.getTransactionNo();
				BigDecimal customerId=promotionWinnerDataTable.getCustomerId();
				
				List<ViewRemittanceDocument> lstViewRemittanceDocument=iPromotionService.verifyRemittanceDetailsWithRemitTransaction(sessionStateManage.getCountryId() ,getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE), transactionYear , transactionNo,customerId);
				
				if(lstViewRemittanceDocument!=null && lstViewRemittanceDocument.size()>0)
				{
					ViewRemittanceDocument viewRemittanceDocument =lstViewRemittanceDocument.get(0);
					promotionWinnerDataTable.setRemittancTransactionId(viewRemittanceDocument.getRemittanceTransactionId());
				}else
				{
					checkRemittance=true;
					promotionWinnerDataTable.setTransactionNo(null);
					RequestContext.getCurrentInstance().execute("noRemittance.show();");
				}
			}
			
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		return checkRemittance;
	}
	public void deletePromotionDetails(PromotionWinnerDataTable promotionWinnerDataTable)
	{
		try{
			if(promotionWinnerDataTable!=null)
			{
				/*List<PromotionWinnerDataTable> lstExistPromotionWinnerDataTable  =getLstPromotionWinnerDataTable();
				
				if(lstExistPromotionWinnerDataTable!=null && lstExistPromotionWinnerDataTable.size()>0)
				{
					for(PromotionWinnerDataTable lpromotionWinnerDataTable :lstExistPromotionWinnerDataTable)
					{
						if(lpromotionWinnerDataTable.getLineIndex().compareTo(promotionWinnerDataTable.getLineIndex())==0)
						{
							lstExistPromotionWinnerDataTable.remove(promotionWinnerDataTable.getLineIndex().intValue());
							break;
						}
					}
				}*/
				
				if(promotionWinnerDataTable.getPromotionDetailsId()!=null)
				{
					iPromotionService.deletePromotionDetails(promotionWinnerDataTable.getPromotionDetailsId());
				}
				reArrangeDataTable(promotionWinnerDataTable);
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		
	}
	public void confirmExit()
	{
		try {
			mainExit();
		} catch (IOException exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}
	
	public void mainExit() throws IOException {
		if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	
	public void reArrangeDataTable(PromotionWinnerDataTable lpromotionWinnerDataTable)
	{
		try{
			List<PromotionWinnerDataTable> lstExistPromotionWinnerDataTable  =getLstPromotionWinnerDataTable();

			 List<PromotionWinnerDataTable> lstPromotionWinnerDataTable= new ArrayList<PromotionWinnerDataTable>();
			
			int insertIndex=0;
			
			BigDecimal selectedIndex= lpromotionWinnerDataTable.getLineIndex();
			
			if(selectedIndex.compareTo(BigDecimal.ZERO)==0)
			{
				for(int i =1; i<lstExistPromotionWinnerDataTable.size(); i++)
				{
					
					PromotionWinnerDataTable frmPromotionWinnerDataTable =lstExistPromotionWinnerDataTable.get(i);
					
					PromotionWinnerDataTable promotionWinnerDataTable= new PromotionWinnerDataTable();
					
					promotionWinnerDataTable.setPromotionMasterId(frmPromotionWinnerDataTable.getPromotionMasterId());
					promotionWinnerDataTable.setPromotionPrizeId(frmPromotionWinnerDataTable.getPromotionPrizeId());
					promotionWinnerDataTable.setPrizeAmount(frmPromotionWinnerDataTable.getPrizeAmount());
					promotionWinnerDataTable.setPrizeNo(frmPromotionWinnerDataTable.getPrizeNo());
					promotionWinnerDataTable.setFromDate(frmPromotionWinnerDataTable.getFromDate());
					promotionWinnerDataTable.setToDate(frmPromotionWinnerDataTable.getToDate());
					promotionWinnerDataTable.setPromotionDetailsId(frmPromotionWinnerDataTable.getPromotionDetailsId());
					promotionWinnerDataTable.setCustomerName(frmPromotionWinnerDataTable.getCustomerName());
					promotionWinnerDataTable.setCustMobileNo(frmPromotionWinnerDataTable.getCustMobileNo());
					promotionWinnerDataTable.setCustomerId(frmPromotionWinnerDataTable.getCustomerId());
					promotionWinnerDataTable.setCivilId(frmPromotionWinnerDataTable.getCivilId());
					promotionWinnerDataTable.setTransactionYear(frmPromotionWinnerDataTable.getTransactionYear());
					promotionWinnerDataTable.setTransactionNo(frmPromotionWinnerDataTable.getTransactionNo());
					promotionWinnerDataTable.setLstFromDate(frmPromotionWinnerDataTable.getLstFromDate());
					promotionWinnerDataTable.setLstToDate(frmPromotionWinnerDataTable.getLstToDate());
					promotionWinnerDataTable.setLstPrizeNo(frmPromotionWinnerDataTable.getLstPrizeNo());
					promotionWinnerDataTable.setLstPrizeAmount(frmPromotionWinnerDataTable.getLstPrizeAmount());
					promotionWinnerDataTable.setMapPrizeAmount(frmPromotionWinnerDataTable.getMapPrizeAmount());
					promotionWinnerDataTable.setMapDateMatch(frmPromotionWinnerDataTable.getMapDateMatch());
					promotionWinnerDataTable.setMapPrizeIdPrizeNo(frmPromotionWinnerDataTable.getMapPrizeIdPrizeNo());
					promotionWinnerDataTable.setRemittancTransactionId(frmPromotionWinnerDataTable.getRemittancTransactionId());
					
					promotionWinnerDataTable.setCreatedBy(frmPromotionWinnerDataTable.getCreatedBy());
					promotionWinnerDataTable.setCreatedDate(frmPromotionWinnerDataTable.getCreatedDate());
					promotionWinnerDataTable.setModifiedBy(frmPromotionWinnerDataTable.getModifiedBy());
					promotionWinnerDataTable.setModifiedDate(frmPromotionWinnerDataTable.getModifiedDate());

					
					promotionWinnerDataTable.setLineIndex(new BigDecimal(insertIndex));

										
					lstPromotionWinnerDataTable.add(insertIndex, promotionWinnerDataTable);
					insertIndex=insertIndex+1;
					
					
				
				}
				setLstPromotionWinnerDataTable(lstPromotionWinnerDataTable);
			}else
			{
				if(lstExistPromotionWinnerDataTable!=null && lstExistPromotionWinnerDataTable.size()!=0)
				{
					
					for(int i =0; i<=selectedIndex.intValue()-1;i++)
					{

						PromotionWinnerDataTable frmPromotionWinnerDataTable =lstExistPromotionWinnerDataTable.get(i);
					
						
						PromotionWinnerDataTable promotionWinnerDataTable= new PromotionWinnerDataTable();
						
						promotionWinnerDataTable.setPromotionMasterId(frmPromotionWinnerDataTable.getPromotionMasterId());
						promotionWinnerDataTable.setPromotionPrizeId(frmPromotionWinnerDataTable.getPromotionPrizeId());
						promotionWinnerDataTable.setPrizeAmount(frmPromotionWinnerDataTable.getPrizeAmount());
						promotionWinnerDataTable.setPrizeNo(frmPromotionWinnerDataTable.getPrizeNo());
						promotionWinnerDataTable.setFromDate(frmPromotionWinnerDataTable.getFromDate());
						promotionWinnerDataTable.setToDate(frmPromotionWinnerDataTable.getToDate());
						promotionWinnerDataTable.setPromotionDetailsId(frmPromotionWinnerDataTable.getPromotionDetailsId());
						promotionWinnerDataTable.setCustomerName(frmPromotionWinnerDataTable.getCustomerName());
						promotionWinnerDataTable.setCustMobileNo(frmPromotionWinnerDataTable.getCustMobileNo());
						promotionWinnerDataTable.setCustomerId(frmPromotionWinnerDataTable.getCustomerId());
						promotionWinnerDataTable.setCivilId(frmPromotionWinnerDataTable.getCivilId());
						promotionWinnerDataTable.setTransactionYear(frmPromotionWinnerDataTable.getTransactionYear());
						promotionWinnerDataTable.setTransactionNo(frmPromotionWinnerDataTable.getTransactionNo());
						promotionWinnerDataTable.setLstFromDate(frmPromotionWinnerDataTable.getLstFromDate());
						promotionWinnerDataTable.setLstToDate(frmPromotionWinnerDataTable.getLstToDate());
						promotionWinnerDataTable.setLstPrizeNo(frmPromotionWinnerDataTable.getLstPrizeNo());
						promotionWinnerDataTable.setLstPrizeAmount(frmPromotionWinnerDataTable.getLstPrizeAmount());
						promotionWinnerDataTable.setMapPrizeAmount(frmPromotionWinnerDataTable.getMapPrizeAmount());
						promotionWinnerDataTable.setMapDateMatch(frmPromotionWinnerDataTable.getMapDateMatch());
						promotionWinnerDataTable.setMapPrizeIdPrizeNo(frmPromotionWinnerDataTable.getMapPrizeIdPrizeNo());
						promotionWinnerDataTable.setRemittancTransactionId(frmPromotionWinnerDataTable.getRemittancTransactionId());
						
						promotionWinnerDataTable.setCreatedBy(frmPromotionWinnerDataTable.getCreatedBy());
						promotionWinnerDataTable.setCreatedDate(frmPromotionWinnerDataTable.getCreatedDate());
						promotionWinnerDataTable.setModifiedBy(frmPromotionWinnerDataTable.getModifiedBy());
						promotionWinnerDataTable.setModifiedDate(frmPromotionWinnerDataTable.getModifiedDate());

						
						promotionWinnerDataTable.setLineIndex(new BigDecimal(i));
						lstPromotionWinnerDataTable.add(i, promotionWinnerDataTable);
						insertIndex=i;
					
					}
					
					
					int remainIndex=selectedIndex.intValue()+1;
					for(int i =remainIndex; i<lstExistPromotionWinnerDataTable.size();i++)
					{
						insertIndex=insertIndex+1;
						
						PromotionWinnerDataTable frmPromotionWinnerDataTable=lstExistPromotionWinnerDataTable.get(i);
						
						PromotionWinnerDataTable promotionWinnerDataTable= new PromotionWinnerDataTable();
						
						promotionWinnerDataTable.setPromotionMasterId(frmPromotionWinnerDataTable.getPromotionMasterId());
						promotionWinnerDataTable.setPromotionPrizeId(frmPromotionWinnerDataTable.getPromotionPrizeId());
						promotionWinnerDataTable.setPrizeAmount(frmPromotionWinnerDataTable.getPrizeAmount());
						promotionWinnerDataTable.setPrizeNo(frmPromotionWinnerDataTable.getPrizeNo());
						promotionWinnerDataTable.setFromDate(frmPromotionWinnerDataTable.getFromDate());
						promotionWinnerDataTable.setToDate(frmPromotionWinnerDataTable.getToDate());
						promotionWinnerDataTable.setPromotionDetailsId(frmPromotionWinnerDataTable.getPromotionDetailsId());
						promotionWinnerDataTable.setCustomerName(frmPromotionWinnerDataTable.getCustomerName());
						promotionWinnerDataTable.setCustMobileNo(frmPromotionWinnerDataTable.getCustMobileNo());
						promotionWinnerDataTable.setCustomerId(frmPromotionWinnerDataTable.getCustomerId());
						promotionWinnerDataTable.setCivilId(frmPromotionWinnerDataTable.getCivilId());
						promotionWinnerDataTable.setTransactionYear(frmPromotionWinnerDataTable.getTransactionYear());
						promotionWinnerDataTable.setTransactionNo(frmPromotionWinnerDataTable.getTransactionNo());
						promotionWinnerDataTable.setLstFromDate(frmPromotionWinnerDataTable.getLstFromDate());
						promotionWinnerDataTable.setLstToDate(frmPromotionWinnerDataTable.getLstToDate());
						promotionWinnerDataTable.setLstPrizeNo(frmPromotionWinnerDataTable.getLstPrizeNo());
						promotionWinnerDataTable.setLstPrizeAmount(frmPromotionWinnerDataTable.getLstPrizeAmount());
						promotionWinnerDataTable.setMapPrizeAmount(frmPromotionWinnerDataTable.getMapPrizeAmount());
						promotionWinnerDataTable.setMapDateMatch(frmPromotionWinnerDataTable.getMapDateMatch());
						promotionWinnerDataTable.setMapPrizeIdPrizeNo(frmPromotionWinnerDataTable.getMapPrizeIdPrizeNo());
						promotionWinnerDataTable.setRemittancTransactionId(frmPromotionWinnerDataTable.getRemittancTransactionId());
						
						promotionWinnerDataTable.setCreatedBy(frmPromotionWinnerDataTable.getCreatedBy());
						promotionWinnerDataTable.setCreatedDate(frmPromotionWinnerDataTable.getCreatedDate());
						promotionWinnerDataTable.setModifiedBy(frmPromotionWinnerDataTable.getModifiedBy());
						promotionWinnerDataTable.setModifiedDate(frmPromotionWinnerDataTable.getModifiedDate());

						
						promotionWinnerDataTable.setLineIndex(new BigDecimal(insertIndex));

						lstPromotionWinnerDataTable.add(insertIndex, promotionWinnerDataTable);
						
						
					}
					setLstPromotionWinnerDataTable(lstPromotionWinnerDataTable);
				}
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		
		
	}
	
	public void checkDrawDate(){
		
		try {
			
			String fromHdate=getFromHDate();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date fromDateToCheck = format.parse(fromHdate);
			if(getDrawDate().compareTo(fromDateToCheck)!=1 )
			{
				RequestContext.getCurrentInstance().execute("drawDateCheck.show();");
			}
		} catch (ParseException e) {
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		
	}
	
	private boolean drawDateCheckForSave()
	{
		boolean drawDateCheck=false;
		try {

			String fromHdate=getFromHDate();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date fromDateToCheck = format.parse(fromHdate);
			if(getDrawDate().compareTo(fromDateToCheck)!=1 )
			{
				drawDateCheck=true;
				RequestContext.getCurrentInstance().execute("drawDateCheck.show();");
			}
		} catch (ParseException e) {
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		return drawDateCheck;
	}
	private boolean checkDuplicateTransactionNo(PromotionWinnerDataTable promotionWinnerDataTable)
	{
		boolean duplicateTransNo=false;
		List<PromotionWinnerDataTable> lstExistPromotionWinnerDataTable  =getLstPromotionWinnerDataTable();
		if(lstExistPromotionWinnerDataTable!=null && lstExistPromotionWinnerDataTable.size()>0)
		{
			for(PromotionWinnerDataTable lpromotionWinnerDataTable :lstExistPromotionWinnerDataTable)
			{
				if(lpromotionWinnerDataTable.getLineIndex().compareTo(promotionWinnerDataTable.getLineIndex())!=0)
				{
					if((lpromotionWinnerDataTable.getTransactionNo()!=null && promotionWinnerDataTable.getTransactionNo()!=null) && (lpromotionWinnerDataTable.getTransactionNo().compareTo(promotionWinnerDataTable.getTransactionNo())==0))
					{
						duplicateTransNo=true;
						promotionWinnerDataTable.setTransactionNo(null);
						RequestContext.getCurrentInstance().execute("duplicateTransactionNo.show();");
						break;
					}
					
				}
			}
		}
		return duplicateTransNo;
	}
}

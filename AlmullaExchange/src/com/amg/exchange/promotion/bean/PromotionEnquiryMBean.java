package com.amg.exchange.promotion.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.promotion.model.PromotionDetails;
import com.amg.exchange.promotion.model.PromotionMaster;
import com.amg.exchange.promotion.model.PromotionPrize;
import com.amg.exchange.promotion.service.IPromotionService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("promotionEnquiryMBean")
@Scope("session")
public class PromotionEnquiryMBean<T> implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Logger log = Logger.getLogger(PromotionDeclarationMBean.class);

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
	private String digitalSignature;
	private Boolean signatureCaptureRender = false;
	private PromotionWinnerDataTable selectedPromotionWinnerDataTable;
	
	
	
	public PromotionWinnerDataTable getSelectedPromotionWinnerDataTable() {
		return selectedPromotionWinnerDataTable;
	}
	public void setSelectedPromotionWinnerDataTable(
			PromotionWinnerDataTable selectedPromotionWinnerDataTable) {
		this.selectedPromotionWinnerDataTable = selectedPromotionWinnerDataTable;
	}
	public Boolean getSignatureCaptureRender() {
		return signatureCaptureRender;
	}
	public void setSignatureCaptureRender(Boolean signatureCaptureRender) {
		this.signatureCaptureRender = signatureCaptureRender;
	}
	public String getDigitalSignature() {
		return digitalSignature;
	}
	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
	}
	public SessionStateManage getSessionStateManage() {
		return sessionStateManage;
	}
	public void setSessionStateManage(SessionStateManage sessionStateManage) {
		this.sessionStateManage = sessionStateManage;
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
	public List<PromotionWinnerDataTable> getLstPromotionWinnerDataTable() {
		return lstPromotionWinnerDataTable;
	}
	public void setLstPromotionWinnerDataTable(
			List<PromotionWinnerDataTable> lstPromotionWinnerDataTable) {
		this.lstPromotionWinnerDataTable = lstPromotionWinnerDataTable;
	}
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	public List<PopulateData> getLstPromotionDocNo() {
		return lstPromotionDocNo;
	}
	public void setLstPromotionDocNo(List<PopulateData> lstPromotionDocNo) {
		this.lstPromotionDocNo = lstPromotionDocNo;
	}
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
	public BigDecimal getPromotionMasterid() {
		return promotionMasterid;
	}
	public void setPromotionMasterid(BigDecimal promotionMasterid) {
		this.promotionMasterid = promotionMasterid;
	}
	
	public void pageNavigation() {
		try {
			mainClear();
			getCompanyListFromDB();
			getFinaceYearFromDb();
			getDocumentIdFromDb();
			getDocNumberPromotion();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../promotion/promotionEnquiry.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Autowired
	IGeneralService<T> igeneralService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	IPromotionService iPromotionService;
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	
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
		
		List<PopulateData> lstPopulateData=iPromotionService.getPromotionDocumentNo(getCompanyId(), getDocumentId(), getFinaceYear());
		setLstPromotionDocNo(lstPopulateData);
	}
	
	public void getPromotionMaster()
	{
		try{
			clearPromoMaster();
			setLstPromotionWinnerDataTable(null);
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
					
					List<PromotionDetails> lstPromotionDetails=iPromotionService.getPromotionEnquiryDetails(promotionMaster.getPromotionMasterId());
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
								
								PromotionWinnerDataTable promotionWinnerDataTable= new PromotionWinnerDataTable();
								promotionWinnerDataTable.setPromotionMasterId(promotionMaster.getPromotionMasterId());
								promotionWinnerDataTable.setPromotionPrizeId(promotionDetails.getPromotionPrize().getPromotionPrizeId());
								promotionWinnerDataTable.setPrizeAmount(promotionDetails.getPromotionPrize().getPrizeAmount());
								promotionWinnerDataTable.setPrizeNo(promotionDetails.getPromotionPrize().getPrizeNo());
								promotionWinnerDataTable.setFromDate(format.format(promotionDetails.getPromotionPrize().getFromDate()));
								promotionWinnerDataTable.setToDate(format.format(promotionDetails.getPromotionPrize().getToDate()));
								promotionWinnerDataTable.setPromotionDetailsId(promotionDetails.getPromotionDetailsId());
								if(promotionDetails.getPromotionDetailsId()!=null)
								{
									BigDecimal promoUsedAmt= iPromotionService.getPromoUsedAmount(promotionDetails.getPromotionDetailsId());
									promotionWinnerDataTable.setPromoUsedAmt(promoUsedAmt);
								}
								
								promotionWinnerDataTable.setDrawDate(format.format(promotionDetails.getDrawDate()));
								if(promotionDetails.getValidUpto()!=null)
								{
									promotionWinnerDataTable.setValidUpTo(format.format(promotionDetails.getValidUpto()));
								}
								
								promotionWinnerDataTable.setDeclarationDate(promotionDetails.getDeclarationDate());
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
								List<ViewRemiitanceInfo> remittViewList=iPromotionService.verifyRemittanceDetailsBasedOnTransId(sessionStateManage.getCountryId() ,getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE), promotionDetails.getRemittanceTrasactionId());
								if(remittViewList!=null && remittViewList.size()>0)
								{
									ViewRemiitanceInfo viewRemiitanceInfo=remittViewList.get(0);
									promotionWinnerDataTable.setTransactionYear(viewRemiitanceInfo.getDocumentFinYear());
									promotionWinnerDataTable.setTransactionNo(viewRemiitanceInfo.getDocumentNo());
									promotionWinnerDataTable.setRemittancTransactionId(promotionDetails.getRemittanceTrasactionId());
								}
								
								
								//promotionWinnerDataTable.set
								List<String>  lstFromDate= new ArrayList<String>(setFromDate);
								List<String>  lstToate= new ArrayList<String>(setToDate);
								
								promotionWinnerDataTable.setLstFromDate(lstFromDate);
								promotionWinnerDataTable.setLstToDate(lstToate);
								promotionWinnerDataTable.setLstPrizeNo(lstPrizeNo);
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
						
					}else{
						clearPromoMaster();
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
		setSignatureCaptureRender(false);
		setPromotionMasterid(null);
		setFromHDate(null);
		setToDate(null);
		setPromotionDescription(null);
		setSelectedPromotionWinnerDataTable(null);
		
	}
	
	public void mainClear()
	{
		setSignatureCaptureRender(false);
		setPromotionDocumentNo(null);
		setPromotionMasterid(null);
		setFromHDate(null);
		setToDate(null);
		setPromotionDescription(null);
		setSelectedPromotionWinnerDataTable(null);
		setLstPromotionWinnerDataTable(null);
		
	}
	
	public void exit(){		
		try {	
			mainClear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<PromotionDeclarationReport> lstPromotionWinnerDataTableReport;
	
	
	
	public List<PromotionDeclarationReport> getLstPromotionWinnerDataTableReport() {
		return lstPromotionWinnerDataTableReport;
	}
	public void setLstPromotionWinnerDataTableReport(
			List<PromotionDeclarationReport> lstPromotionWinnerDataTableReport) {
		this.lstPromotionWinnerDataTableReport = lstPromotionWinnerDataTableReport;
	}
	
	private JasperPrint jasperPrint;
	public void genaratePromoDeclarationReport(PromotionWinnerDataTable promotionWinnerDataTable)
	{
		ServletOutputStream servletOutputStream =null;
		try {
			fetchPromotionDeclarationReport(promotionWinnerDataTable);
			promotionDeclarationReportInit();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition","attachment; filename=promotion_declaration.pdf");

			servletOutputStream= httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {

			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}finally
		{
			if(servletOutputStream!=null)
			{
				try {
					servletOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private void fetchPromotionDeclarationReport(PromotionWinnerDataTable promotionWinnerDataTable) {
		
		//PromotionWinnerDataTable promotionWinnerDataTable=getSelectedPromotionWinnerDataTable();
		List<PromotionDeclarationReport> lstPromotionWinnerDataTableReport= new ArrayList<PromotionDeclarationReport>();
		PromotionDetails promotionDetails =iPromotionService.getPromotionDetailsReport(promotionWinnerDataTable.getPromotionDetailsId());
		
		
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		String waterMark =  realPath+Constants.REPORT_WATERMARK_LOGO;
		
		PromotionDeclarationReport promotionDeclarationReport = new PromotionDeclarationReport();
		promotionDeclarationReport.setCivilId(promotionWinnerDataTable.getCivilId());
		promotionDeclarationReport.setDateofIssue(promotionDetails.getDeclarationDate().toString());
		promotionDeclarationReport.setName(promotionWinnerDataTable.getCustomerName());
		promotionDeclarationReport.setPrizeAmount(promotionWinnerDataTable.getPrizeAmount().toString());
		//promotionDeclarationReport.setSignature(promotionDetails.getSignatureSpecimenClob().toString());
		promotionDeclarationReport.setLogoPath(logoPath);
		promotionDeclarationReport.setWaterMarkLogoPath(waterMark);
		promotionDeclarationReport.setWaterMarkCheck(false);
		
		try {
			if (promotionDetails.getSignatureSpecimenClob() != null) {
				promotionDeclarationReport.setSignature(promotionDetails.getSignatureSpecimenClob().getSubString(1, (int) promotionDetails.getSignatureSpecimenClob().length()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		lstPromotionWinnerDataTableReport.add(promotionDeclarationReport);
		
		setLstPromotionWinnerDataTableReport(lstPromotionWinnerDataTableReport);
	}
	
	public void promotionDeclarationReportInit() throws JRException {
		List<PromotionDeclarationReport> lstPromotionWinnerDataTableReport=getLstPromotionWinnerDataTableReport();
		
		
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(lstPromotionWinnerDataTableReport);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/proRamdan_Report.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
	}
	

}

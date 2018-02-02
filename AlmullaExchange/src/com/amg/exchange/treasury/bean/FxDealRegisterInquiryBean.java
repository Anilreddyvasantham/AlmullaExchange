package com.amg.exchange.treasury.bean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.TreasuryDealRegisterView;
import com.amg.exchange.treasury.service.IFXDetailInformationService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("fxDealRegisterInquiryBean")
@Scope("session")
public class FxDealRegisterInquiryBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SessionStateManage sessionManage = new SessionStateManage();
	Logger log = Logger.getLogger(FxDealRegisterInquiryBean.class);

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IFXDetailInformationService<T> fXDetailInformationService;

	// lists
	private List<CompanyMasterDesc> lstCompany = new ArrayList<CompanyMasterDesc>();
	private List<BankApplicability> lstAllBankApplicabity = new ArrayList<BankApplicability>();
	private List<BankAccountDetails> lstPurchaseCurrency = new ArrayList<BankAccountDetails>();
	private List<BankAccountDetails> lstSaleCurrency = new ArrayList<BankAccountDetails>();
	private List<fxDealRegisterInquiryDataTable> lstTreasuryDealRegister = new ArrayList<fxDealRegisterInquiryDataTable>();

	// variables
	private BigDecimal companyId;
	private BigDecimal bankId;
	private BigDecimal pdCurrencyId;
	private BigDecimal sdCurrencyId;
	private Date fromDocDate;
	private Date toDocDate;
	private BigDecimal selectedSearchValueId;

	// extra vaiables
	private Date effectiveMaxDate = new Date();
	private String exceptionMessage;


	// all company details
	public void fetchAllCompany() {
		try{  
			List<CompanyMasterDesc> companyDataLst = generalService.getCompanyList(sessionManage.getCompanyId(), sessionManage.getLanguageId());
			if(companyDataLst.size() != 0){
				setCompanyId(sessionManage.getCompanyId());
				setLstCompany(companyDataLst);
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setExceptionMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("dataexception.show();");
			return;       
		}

	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;

	// page navigation
	public void clearCache(){
		try {
			clear();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "fxdealRegisterInquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/fxdealRegisterInquiry.xhtml");
			fetchAllCompany();
			fetchAllbankFromBankApplicability();
		} catch (IOException exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setExceptionMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("dataexception.show();");
			return;
		}
	}

	//CR get Bank list from Bank Applicability local Banks , Funding Banks and Corresponding Bank 
	public void fetchAllbankFromBankApplicability() {
		try{
			if(lstAllBankApplicabity !=null && lstAllBankApplicabity.size()!=0 ){
				lstAllBankApplicabity.clear();
			}

			List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

			List<BankApplicability> lstBankApplicabity = fXDetailInformationService.getCorrespondingLocalFundingBanks(sessionManage.getCountryId());

			for (BankApplicability bankApplicability : lstBankApplicabity) {
				if(!duplicateCheck.contains(bankApplicability.getBankMaster().getBankId())){
					duplicateCheck.add(bankApplicability.getBankMaster().getBankId());
					lstAllBankApplicabity.add(bankApplicability);
				}
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setExceptionMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("dataexception.show();");
			return;
		}
	}

	//CR to get Currency In Multiple and Single Panel 
	public void populateCurrencyBasedOnBankForPurchase() {
		try{
			if(lstPurchaseCurrency !=null && lstPurchaseCurrency.size()!=0 ){
				lstPurchaseCurrency.clear();
			}

			if(lstSaleCurrency !=null && lstSaleCurrency.size()!=0 ){
				lstSaleCurrency.clear();
			}

			List<BigDecimal> duplicatesCheck = new ArrayList<BigDecimal>();

			//removed Duplicates from Bank Currency 
			List<BankAccountDetails> lstAllpurchaseCurrency = fXDetailInformationService.getCurrencyBasedOnBankCountry(getBankId());
			for (BankAccountDetails bankAccountDetails : lstAllpurchaseCurrency) {
				if(!duplicatesCheck.contains(bankAccountDetails.getFsCurrencyMaster().getCurrencyId())){
					duplicatesCheck.add(bankAccountDetails.getFsCurrencyMaster().getCurrencyId());
					lstPurchaseCurrency.add(bankAccountDetails);
					lstSaleCurrency.add(bankAccountDetails);
				}
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setExceptionMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("dataexception.show();");
			return;
		}
	}

	// event listener from date
	public void onDateSelectFrom(SelectEvent event) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			setFromDocDate(format.parse(format.format(event.getObject())));
		}catch(ParseException exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setExceptionMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("dataexception.show();");
			return;
		}
	}

	// event listener to date
	public void onDateSelectTo(SelectEvent event) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			setToDocDate(format.parse(format.format(event.getObject())));
		} catch (ParseException exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setExceptionMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("dataexception.show();");
			return;
		}
	}

	public Date getEffectiveMaxDate() {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(Constants.FX_DEAL_WITH_SUPPLIER_DEAL_DATE_ALLOW));
		Date tomorrow = cal.getTime();
		effectiveMaxDate=tomorrow;
		return effectiveMaxDate;
	}

	public void setEffectiveMaxDate(Date effectiveMaxDate) {
		this.effectiveMaxDate = effectiveMaxDate;
	}



	// view All based on Status
	public void viewAll(){

		if(getFromDocDate().compareTo(getToDocDate())<=0){

			log.info("COMPANY_ID : "+getCompanyId());
			log.info("BANK_ID : "+getBankId());
			log.info("PD_CURRENCY : "+getPdCurrencyId());
			log.info("SD_CURRENCY"+getSdCurrencyId());
			log.info("FROM_DOC_DATE"+getFromDocDate());
			log.info("TO_DOC_DATE"+getToDocDate());
			log.info("STATUS"+getSelectedSearchValueId());

			List<fxDealRegisterInquiryDataTable> lstTreasuryDealRegisterlocal = new ArrayList<fxDealRegisterInquiryDataTable>();

			HashMap<String, Object> lstTreasuryDealRegisterInPut = new HashMap<String, Object>();
			lstTreasuryDealRegisterInPut.put("COMPANY_ID", getCompanyId());
			lstTreasuryDealRegisterInPut.put("BANK_ID", getBankId());
			lstTreasuryDealRegisterInPut.put("PD_CURRENCY", getPdCurrencyId());
			lstTreasuryDealRegisterInPut.put("SD_CURRENCY", getSdCurrencyId());
			lstTreasuryDealRegisterInPut.put("FROM_DOC_DATE", getFromDocDate());
			lstTreasuryDealRegisterInPut.put("TO_DOC_DATE", getToDocDate());
			lstTreasuryDealRegisterInPut.put("STATUS", getSelectedSearchValueId());

			try {

				// from date converted to Calendar 
				Calendar fromCalendar = Calendar.getInstance();
				fromCalendar.setTime(getFromDocDate());
				fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
				fromCalendar.set(Calendar.MINUTE, 0);
				fromCalendar.set(Calendar.SECOND, 0);
				Date fromDate = fromCalendar.getTime();
				System.out.println("fromDate : "+fromDate);
				setFromDocDate(fromDate);

				// to date converted to Calendar 
				Calendar toCalendar = Calendar.getInstance();
				toCalendar.setTime(getToDocDate());
				toCalendar.set(Calendar.HOUR_OF_DAY, 23);
				toCalendar.set(Calendar.MINUTE, 59);
				toCalendar.set(Calendar.SECOND, 59);
				Date toDate = toCalendar.getTime();
				System.out.println("toDate : "+toDate);
				setToDocDate(toDate);

				List<TreasuryDealRegisterView> lstTreasuryDealRegisterOutPut = fXDetailInformationService.fetchTreasuryDealRegisterFromView(lstTreasuryDealRegisterInPut);

				if(lstTreasuryDealRegisterOutPut.size() != 0){
					for (TreasuryDealRegisterView treasuryDealRegisterView : lstTreasuryDealRegisterOutPut) {

						fxDealRegisterInquiryDataTable lstFxDealRegisterInquiry = new fxDealRegisterInquiryDataTable();

						lstFxDealRegisterInquiry.setApplicationCountryId(treasuryDealRegisterView.getApplicationCountryId());
						lstFxDealRegisterInquiry.setCompanyId(treasuryDealRegisterView.getCompanyId());
						lstFxDealRegisterInquiry.setDealWithType(treasuryDealRegisterView.getDealWithType());
						lstFxDealRegisterInquiry.setDocumentDate(treasuryDealRegisterView.getDocumentDate());
						lstFxDealRegisterInquiry.setDocumentFinanceYear(treasuryDealRegisterView.getDocumentFinanceYear());
						lstFxDealRegisterInquiry.setDocumentId(treasuryDealRegisterView.getDocumentId());
						lstFxDealRegisterInquiry.setDocumentNumber(treasuryDealRegisterView.getDocumentNumber());
						lstFxDealRegisterInquiry.setIsActive(treasuryDealRegisterView.getIsActive());
						lstFxDealRegisterInquiry.setPdBankAcctDetId(treasuryDealRegisterView.getPdBankAcctDetId());
						lstFxDealRegisterInquiry.setPdBankCode(treasuryDealRegisterView.getPdBankCode());
						lstFxDealRegisterInquiry.setPdBankFullName(treasuryDealRegisterView.getPdBankFullName());
						lstFxDealRegisterInquiry.setPdBankId(treasuryDealRegisterView.getPdBankId());
						lstFxDealRegisterInquiry.setPdCurrencyId(treasuryDealRegisterView.getPdCurrencyId());
						lstFxDealRegisterInquiry.setPdExchangeRate(treasuryDealRegisterView.getPdExchangeRate());
						lstFxDealRegisterInquiry.setPdFCAmount(treasuryDealRegisterView.getPdFCAmount());
						lstFxDealRegisterInquiry.setPdLineType(treasuryDealRegisterView.getPdLineType());
						lstFxDealRegisterInquiry.setPdLocalAmount(treasuryDealRegisterView.getPdLocalAmount());
						lstFxDealRegisterInquiry.setPdLocalExchangeRate(treasuryDealRegisterView.getPdLocalExchangeRate());
						lstFxDealRegisterInquiry.setPdQuoteName(treasuryDealRegisterView.getPdQuoteName());
						lstFxDealRegisterInquiry.setPurchaseExchageRate(treasuryDealRegisterView.getPurchaseExchageRate());
						lstFxDealRegisterInquiry.setSaleAmount(treasuryDealRegisterView.getSaleAmount());
						lstFxDealRegisterInquiry.setSdBankAcctDetId(treasuryDealRegisterView.getSdBankAcctDetId());
						lstFxDealRegisterInquiry.setSdBankCode(treasuryDealRegisterView.getSdBankCode());
						lstFxDealRegisterInquiry.setSdBankFullName(treasuryDealRegisterView.getSdBankFullName());
						lstFxDealRegisterInquiry.setSdBankId(treasuryDealRegisterView.getSdBankId());
						lstFxDealRegisterInquiry.setSdCurrencyId(treasuryDealRegisterView.getSdCurrencyId());
						lstFxDealRegisterInquiry.setSdExchangeRate(treasuryDealRegisterView.getSdExchangeRate());
						lstFxDealRegisterInquiry.setSdFCAmount(treasuryDealRegisterView.getSdFCAmount());
						lstFxDealRegisterInquiry.setSdLineType(treasuryDealRegisterView.getSdLineType());
						lstFxDealRegisterInquiry.setSdLocalAmount(treasuryDealRegisterView.getSdLocalAmount());
						lstFxDealRegisterInquiry.setSdLocalExchangeRate(treasuryDealRegisterView.getSdLocalExchangeRate());
						lstFxDealRegisterInquiry.setSdQuoteName(treasuryDealRegisterView.getSdQuoteName());
						lstFxDealRegisterInquiry.setTotalPurchaseFCAmount(treasuryDealRegisterView.getTotalPurchaseFCAmount());
						lstFxDealRegisterInquiry.setTreasuryDealHeaderId(treasuryDealRegisterView.getTreasuryDealHeaderId());
						lstFxDealRegisterInquiry.setValueDate(treasuryDealRegisterView.getValueDate());

						lstTreasuryDealRegisterlocal.add(lstFxDealRegisterInquiry);
					}
					if(lstTreasuryDealRegisterlocal != null && lstTreasuryDealRegisterlocal.size()!=0){
						setLstTreasuryDealRegister(lstTreasuryDealRegisterlocal);
					}else{
						setLstTreasuryDealRegister(null);
						RequestContext.getCurrentInstance().execute("exist.show();");
					}
				}else{
					setLstTreasuryDealRegister(null);
					RequestContext.getCurrentInstance().execute("exist.show();");
				}
			} catch (Exception e) {
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("dataexception.show();");
			}

		}else{
			setLstTreasuryDealRegister(null);
			RequestContext.getCurrentInstance().execute("fromToDateCheck.show();");
		}

	}

	// Clear All 
	public void clear(){
		setBankId(null);
		setPdCurrencyId(null);
		setSdCurrencyId(null);
		setFromDocDate(null);
		setToDocDate(null);
		setSelectedSearchValueId(null);
		if(lstTreasuryDealRegister != null && !lstTreasuryDealRegister.isEmpty()){
			lstTreasuryDealRegister.clear();
		}
	}

	// Exit
	public void exit(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}
	}

	// getter and setters
	public List<CompanyMasterDesc> getLstCompany() {
		return lstCompany;
	}
	public void setLstCompany(List<CompanyMasterDesc> lstCompany) {
		this.lstCompany = lstCompany;
	}

	public List<BankApplicability> getLstAllBankApplicabity() {
		return lstAllBankApplicabity;
	}
	public void setLstAllBankApplicabity(List<BankApplicability> lstAllBankApplicabity) {
		this.lstAllBankApplicabity = lstAllBankApplicabity;
	}

	public List<BankAccountDetails> getLstPurchaseCurrency() {
		return lstPurchaseCurrency;
	}
	public void setLstPurchaseCurrency(List<BankAccountDetails> lstPurchaseCurrency) {
		this.lstPurchaseCurrency = lstPurchaseCurrency;
	}

	public List<BankAccountDetails> getLstSaleCurrency() {
		return lstSaleCurrency;
	}
	public void setLstSaleCurrency(List<BankAccountDetails> lstSaleCurrency) {
		this.lstSaleCurrency = lstSaleCurrency;
	}

	public List<fxDealRegisterInquiryDataTable> getLstTreasuryDealRegister() {
		return lstTreasuryDealRegister;
	}
	public void setLstTreasuryDealRegister(List<fxDealRegisterInquiryDataTable> lstTreasuryDealRegister) {
		this.lstTreasuryDealRegister = lstTreasuryDealRegister;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getPdCurrencyId() {
		return pdCurrencyId;
	}
	public void setPdCurrencyId(BigDecimal pdCurrencyId) {
		this.pdCurrencyId = pdCurrencyId;
	}

	public BigDecimal getSdCurrencyId() {
		return sdCurrencyId;
	}
	public void setSdCurrencyId(BigDecimal sdCurrencyId) {
		this.sdCurrencyId = sdCurrencyId;
	}

	public Date getFromDocDate() {
		return fromDocDate;
	}
	public void setFromDocDate(Date fromDocDate) {
		this.fromDocDate = fromDocDate;
	}

	public Date getToDocDate() {
		return toDocDate;
	}
	public void setToDocDate(Date toDocDate) {
		this.toDocDate = toDocDate;
	}

	public BigDecimal getSelectedSearchValueId() {
		return selectedSearchValueId;
	}
	public void setSelectedSearchValueId(BigDecimal selectedSearchValueId) {
		this.selectedSearchValueId = selectedSearchValueId;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}



	//Anil (01/15/2018)
	
	public void download(){
		downloadList();
		generateFxDealRegisterReport();
	}
	
	//Download As Excel Sheet.
	public void downloadList(){
		try{
			if(getLstTreasuryDealRegister()!=null && getLstTreasuryDealRegister().size() > 0){
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("Fx Deal Register Enquiry");				

				sheet.addMergedRegion(CellRangeAddress.valueOf("A1:A2"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("B1:B2"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("C1:G1"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("C2:D2"));			
				sheet.addMergedRegion(CellRangeAddress.valueOf("H1:L1"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("H2:I2"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("M1:M2"));

				Object[][] datatypes = {
						{ "Deal Year / Deal Number", "Document Date", "Purchase","","", "","","Sale","","","","", "Exchange Rate" },
						{ "","","Bank","","Currency","Fc Amount","Value date","Bank","","Currency","Fc Amount","Value date",""}
				};

				int rowNum = 0;
				System.out.println("Creating excel");				

				for (Object[] datatype : datatypes) {
					Row row = sheet.createRow(rowNum++);
					int colNum = 0;
					for (Object field : datatype) {
						Cell cell = row.createCell(colNum++);

						if (rowNum == 1 || rowNum == 2) {
							CellStyle style = workbook.createCellStyle();
							style.setBorderBottom(CellStyle.BORDER_THIN);
							style.setBorderLeft(CellStyle.BORDER_THIN);
							style.setBorderRight(CellStyle.BORDER_THIN);
							style.setBorderTop(CellStyle.BORDER_THIN);
							
							XSSFFont font= workbook.createFont();
						    font.setFontHeightInPoints((short)10);
						    font.setFontName("Arial");
						    font.setColor(IndexedColors.WHITE.getIndex());
						    font.setBold(true);
						    font.setItalic(false);

						    //style=row.getRowStyle();
						    style.setFillBackgroundColor(IndexedColors.DARK_BLUE.getIndex());
						    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
						    style.setAlignment(CellStyle.ALIGN_CENTER);
						    style.setFont(font);

							style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
							style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
							style.setRightBorderColor(IndexedColors.WHITE.getIndex());
							style.setTopBorderColor(IndexedColors.WHITE.getIndex());

							cell.setCellStyle(style);					  
						}						
						
						
						if (field instanceof String) {
							cell.setCellValue((String) field);
						} else if (field instanceof Integer) {
							cell.setCellValue((Integer) field);
						}
					}
				}
				rowNum=2;
				int colNum = 0;
				int count=0;
				int listSize = getLstTreasuryDealRegister().size();

				while(count<listSize){
					fxDealRegisterInquiryDataTable lst = lstTreasuryDealRegister.get(count);
					Row row = sheet.createRow(rowNum++);
					
					CellStyle style = workbook.createCellStyle();
					style.setBorderBottom(CellStyle.BORDER_THIN);
					style.setBorderLeft(CellStyle.BORDER_THIN);
					style.setBorderRight(CellStyle.BORDER_THIN);
					style.setBorderTop(CellStyle.BORDER_THIN);

					style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
					style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
					style.setRightBorderColor(IndexedColors.BLACK.getIndex());
					style.setTopBorderColor(IndexedColors.BLACK.getIndex());

					Cell cell1 = row.createCell(colNum++);
					cell1.setCellValue(lst.getDocumentFinanceYear()+"/"+lst.getDocumentNumber());
					cell1.setCellStyle(style);

					Cell cell2 = row.createCell(colNum++);
					Date date = lst.getDocumentDate();  
				    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
				    String docDate= formatter.format(date);
					cell2.setCellValue(docDate);
					cell2.setCellStyle(style);

					Cell cell3 = row.createCell(colNum++);
					cell3.setCellValue(lst.getPdBankCode());
					cell3.setCellStyle(style);

					Cell cell4 = row.createCell(colNum++);
					cell4.setCellValue(lst.getPdBankFullName());
					cell4.setCellStyle(style);

					Cell cell5 = row.createCell(colNum++);
					cell5.setCellValue(lst.getPdQuoteName());
					cell5.setCellStyle(style);

					Cell cell6 = row.createCell(colNum++);
					if(lst.getPdFCAmount()!=null){
						cell6.setCellValue(lst.getPdFCAmount().toString());
						cell6.setCellStyle(style);
					}else{
						cell6.setCellValue("");
						cell6.setCellStyle(style);
					}				

					Cell cell7 = row.createCell(colNum++);
					Date date1 = lst.getValueDate();  
				    SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");  
				    String valDate= formatter1.format(date1);
					cell7.setCellValue(valDate);
					cell7.setCellStyle(style);

					Cell cell8 = row.createCell(colNum++);
					cell8.setCellValue(lst.getSdBankCode());
					cell8.setCellStyle(style);

					Cell cell9 = row.createCell(colNum++);
					cell9.setCellValue(lst.getSdBankFullName());
					cell9.setCellStyle(style);

					Cell cell10 = row.createCell(colNum++);
					cell10.setCellValue(lst.getSdQuoteName());
					cell10.setCellStyle(style);

					Cell cell11 = row.createCell(colNum++);
					if(lst.getSdFCAmount()!=null){
						cell11.setCellValue(lst.getSdFCAmount().toString());
						cell11.setCellStyle(style);
					}else{
						cell11.setCellValue("");
						cell11.setCellStyle(style);
					}				

					Cell cell12 = row.createCell(colNum++);
					Date date2 = lst.getValueDate();  
				    SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");  
				    String valDate1= formatter2.format(date2);
					cell12.setCellValue(valDate1);
					cell12.setCellStyle(style);

					Cell cell13 = row.createCell(colNum++);
					if(lst.getPdExchangeRate()!=null){
						cell13.setCellValue(lst.getPdExchangeRate().toString());
						cell13.setCellStyle(style);
					}else{
						cell13.setCellValue("");
						cell13.setCellStyle(style);
					}

					colNum = 0;
					count=count+1;
				}

				try {
					FacesContext fc = FacesContext.getCurrentInstance();

					HttpServletResponse httpServletResponse = (HttpServletResponse) fc.getExternalContext().getResponse();
					httpServletResponse.reset();

					httpServletResponse.setHeader("Cache-Control", "cache, must-revalidate");
					httpServletResponse.setContentType("application/vnd.ms-excel");
					httpServletResponse.addHeader("Content-disposition", "attachment; filename=FxDealRegisterEnquiry.xlsx");

					workbook.write(httpServletResponse.getOutputStream());

					fc.responseComplete();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Done");
			}else{
				RequestContext.getCurrentInstance().execute("noRecordsInDataTable.show();");
			}
		}catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}
	}
	
	
	
	//Download As PDF.
	private JasperPrint jasperPrint;	
	private List<FxDealRegisterInquiryReport> lstFxDealInquiryReport = null;	
	

	public void generateFxDealRegisterReport(){
		ServletOutputStream servletOutputStream =null;
		try{
			fetchFxDealRegisterInquiryRecords();
			fxDealRegisterInquiryInit();
			
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition","attachment; filename=fxDealRegisterInquiry.pdf");

			servletOutputStream= httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
			
		}catch (Exception e) {
			setExceptionMessage(e.getMessage()); 
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
	
	
	private void fetchFxDealRegisterInquiryRecords(){
		//List<fxDealRegisterInquiryDataTable> fxDealRegisterInquiryTable = getLstTreasuryDealRegister();
		List<FxDealRegisterInquiryReport> reportList = new ArrayList<FxDealRegisterInquiryReport>();		
		FxDealRegisterInquiryReport fxDealReport = new FxDealRegisterInquiryReport();
		
		//Company
		if(getCompanyId()!=null){
			List<CompanyMasterDesc> companyName = generalService.getCompanyList(getCompanyId(),sessionManage.getLanguageId());
			if(companyName!=null && companyName.size() > 0){
				fxDealReport.setCompany(companyName.get(0).getCompanyName());
			}else{
				fxDealReport.setCompany("");
			}
		}else{
			fxDealReport.setCompany("");
		}
		
		
		//Bank
		if(getBankId()!=null){
			List<BankMaster> bankName = fXDetailInformationService.getBankName(getBankId());
			if(bankName!=null && bankName.size() > 0){
				fxDealReport.setBank(bankName.get(0).getBankFullName());
			}else{
				fxDealReport.setBank("");
			}
		}else{
			fxDealReport.setBank("");
		}
		
		
		//Purchase Currency
		if(getPdCurrencyId()!=null){
			String purCurr = generalService.getCurrencyName(getPdCurrencyId());
			if(purCurr!=null){
				fxDealReport.setPurchaseCurrency(purCurr);
			}else{
				fxDealReport.setPurchaseCurrency("");
			}
		}else{
			fxDealReport.setPurchaseCurrency("");
		}
		
		
		//Sale Currency
		if(getSdCurrencyId()!=null){
			String saleCurr = generalService.getCurrencyName(getSdCurrencyId());
			if(saleCurr!=null){
				fxDealReport.setSaleCurrency(saleCurr);
			}else{
				fxDealReport.setSaleCurrency("");
			}
		}else{
			fxDealReport.setSaleCurrency("");
		}			
		
		fxDealReport.setFromDocDate(getFromDocDate());
		fxDealReport.setToDocDate(getToDocDate());		
		
		List<FxDealRegisterInquiryReport> list = new ArrayList<FxDealRegisterInquiryReport>();
		if(getLstTreasuryDealRegister()!=null && getLstTreasuryDealRegister().size() > 0){
			for(fxDealRegisterInquiryDataTable view : lstTreasuryDealRegister){
				FxDealRegisterInquiryReport lst = new FxDealRegisterInquiryReport();
				lst.setDocumentFinanceYear(view.getDocumentFinanceYear());		        
				lst.setDocumentNumber(view.getDocumentNumber());
				lst.setDocumentDate(view.getDocumentDate());
				lst.setPdBankCode(view.getPdBankCode());
				lst.setPdBankFullName(view.getPdBankFullName());
				lst.setPdQuoteName(view.getPdQuoteName());
				lst.setPdFCAmount(view.getPdFCAmount());
				lst.setValueDate(view.getValueDate());
				lst.setSdBankCode(view.getSdBankCode());
				lst.setSdBankFullName(view.getSdBankFullName());
				lst.setSdQuoteName(view.getSdQuoteName());
				lst.setSdFCAmount(view.getSdFCAmount());
				lst.setPdExchangeRate(view.getPdExchangeRate());
				
				list.add(lst);				
			}	
			fxDealReport.setListFxDealRegisterInquiryReport(list);
			reportList.add(fxDealReport);
			setLstFxDealInquiryReport(reportList);
		}		
		
	}
	
	
	public void fxDealRegisterInquiryInit() throws JRException {
		List<FxDealRegisterInquiryReport> lst = getLstFxDealInquiryReport();
		
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(lst);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/fxDealRegisterInquiry.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
		
	}
	
	
	
	//Getters and setters.
	
	public IFXDetailInformationService<T> getfXDetailInformationService() {
		return fXDetailInformationService;
	}

	public void setfXDetailInformationService(
			IFXDetailInformationService<T> fXDetailInformationService) {
		this.fXDetailInformationService = fXDetailInformationService;
	}

	public List<FxDealRegisterInquiryReport> getLstFxDealInquiryReport() {
		return lstFxDealInquiryReport;
	}

	public void setLstFxDealInquiryReport(
			List<FxDealRegisterInquiryReport> lstFxDealInquiryReport) {
		this.lstFxDealInquiryReport = lstFxDealInquiryReport;
	}
	
	
}

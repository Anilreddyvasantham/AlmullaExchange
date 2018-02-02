package com.amg.exchange.cancelreissue.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;
import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.cancelreissue.service.ICancelReissueService;
import com.amg.exchange.common.dto.BankMasterDTO;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.RemitApplAml;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("cancelAndReIssueFileUploadBean")
@Scope("session")
public class CancelAndReIssueFileUploadBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log=Logger.getLogger(CancelAndReIssueFileUploadBean.class);
	private SessionStateManage session=new SessionStateManage();

	private BigDecimal bankId;
	private String customerName;
	private String errorMessage;

	private BigDecimal trnxAmount;
	private BigDecimal transactionNo;
	private BigDecimal transactionYear;
	private String applicationYear;
	private BigDecimal applicationRef;
	private String processIn=Constants.Yes;
	private BigDecimal remittanceAppId;
	private BigDecimal applicationYearId;

	private BigDecimal pkRemittanceApplicationId =null;
	private BigDecimal pkRemittanceAppBenificiaryId = null;
	private BigDecimal pkRemitApplAmlId =null;
	private BigDecimal pkAdditionalInstructionDataId=null;
	private String errorMessageException;
	private Boolean saveOk=false;



	private Boolean booProcedureDialog=false;
	private String errMsg;




	@Autowired 
	IGeneralService  generalService;

	@Autowired
	ICancelReissueService<T> icancelReissueService;

	@Autowired
	IForeignCurrencyPurchaseService  iforeignCurrencyPurchaseService;

	public Boolean getBooProcedureDialog() {
		return booProcedureDialog;
	}

	public void setBooProcedureDialog(Boolean booProcedureDialog) {
		this.booProcedureDialog = booProcedureDialog;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}


	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<BankMasterDTO> getBankList() {

		return bankList;
	}

	public void setBankList(List<BankMasterDTO> bankList) {
		this.bankList = bankList;
	}

	public BigDecimal getBankId() {
		return bankId;
	}


	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public BigDecimal getTrnxAmount() {
		return trnxAmount;
	}

	public void setTrnxAmount(BigDecimal trnxAmount) {
		this.trnxAmount = trnxAmount;
	}

	public BigDecimal getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(BigDecimal transactionNo) {
		this.transactionNo = transactionNo;
	}


	public BigDecimal getTransactionYear() {
		return transactionYear;
	}

	public void setTransactionYear(BigDecimal transactionYear) {
		this.transactionYear = transactionYear;
	}
	public BigDecimal getRemittanceAppId() {
		return remittanceAppId;
	}

	public void setRemittanceAppId(BigDecimal remittanceAppId) {
		this.remittanceAppId = remittanceAppId;
	}

	public BigDecimal getPkRemittanceApplicationId() {
		return pkRemittanceApplicationId;
	}

	public void setPkRemittanceApplicationId(BigDecimal pkRemittanceApplicationId) {
		this.pkRemittanceApplicationId = pkRemittanceApplicationId;
	}

	public BigDecimal getPkRemittanceAppBenificiaryId() {
		return pkRemittanceAppBenificiaryId;
	}

	public void setPkRemittanceAppBenificiaryId(
			BigDecimal pkRemittanceAppBenificiaryId) {
		this.pkRemittanceAppBenificiaryId = pkRemittanceAppBenificiaryId;
	}

	public BigDecimal getPkRemitApplAmlId() {
		return pkRemitApplAmlId;
	}

	public void setPkRemitApplAmlId(BigDecimal pkRemitApplAmlId) {
		this.pkRemitApplAmlId = pkRemitApplAmlId;
	}

	public BigDecimal getPkAdditionalInstructionDataId() {
		return pkAdditionalInstructionDataId;
	}

	public void setPkAdditionalInstructionDataId(
			BigDecimal pkAdditionalInstructionDataId) {
		this.pkAdditionalInstructionDataId = pkAdditionalInstructionDataId;
	}

	public String getErrorMessageException() {
		return errorMessageException;
	}

	public void setErrorMessageException(String errorMessageException) {
		this.errorMessageException = errorMessageException;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;

	public void pageNavigationToCancelAndReIssueFileUpload(){
		setSaveOk( false);
		clearAll();
		getApplicationYearFromdb();
		setBooProcedureDialog(false);
		List<BankMasterDTO>  banksList= generalService.getCoresBankListForApplicationCountry(session.getCountryId());
		setBankList(banksList);

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "cancelandreissuefileupload.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../cancelreissue/cancelandreissuefileupload.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to cancelandreissue");
		}

	}


	public void clearAll(){

		cancelReIssueDTList.clear();
		cancelReIssueFinalList.clear();
		setSaveOk( false);
		setBankId(null);
		setTransactionNo( null);
		setTransactionYear(null);
		//setErrorMessage( null);
		setTrnxAmount( null);
		setUploadedFile(null);
		setCheckFile( false);
		setCustomerName( null);
		setApplicationYear( null);
		//setErrorMessageException(null);


	}


	public void exit(){

		log.info("::::::::::::::::::::::::::::::::::exit method called:::::::::::::::::::::");	
		try {
			if (session.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../registration/branchhome.xhtml");
			}
		} catch (Exception e) {
			log.error(":::::::::::::::::::Problem Ocuured in Exit Button::::::::::::::::");
		}

	}

	public void nextApplicationNo() {
		String docNo=getDocumentSerialID(processIn);
		if(docNo.equalsIgnoreCase("")){

		}else{
			setApplicationRef(new BigDecimal(docNo));
		}
		//setApplicationRef(new BigDecimal(getDocumentSerialID(processIn)));
	}



	public String getDocumentSerialID(String processIn){

		try{
			HashMap<String, String> outPutValues = generalService.getNextDocumentRefNumber(session.getCountryId().intValue() , session.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_CANCELLATION_REISSUE) , new BigDecimal(getApplicationYear()).intValue(),processIn,session.getCountryBranchCode());

			String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
			if(proceErrorMsg!=null)
			{
				setBooProcedureDialog(true);
				setErrMsg(proceErrorMsg);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
				return "";

			}else{
				setBooProcedureDialog(false);
				String documentSerialID=outPutValues.get("DOCNO");
				return documentSerialID;

			}



		}catch(NumberFormatException | AMGException e){
			setErrMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
			return "";
		}

		/*	String documentSerialID = generalService.getNextDocumentReferenceNumber(session.getCountryId().intValue() , session.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_CANCELLATION_REISSUE) , new BigDecimal(getApplicationYear()).intValue(),processIn,session.getCountryBranchCode());
		return documentSerialID;*/

	}


	public void getApplicationYearFromdb() {

		try{
			List<UserFinancialYear> applicationYearList = generalService.getDealYear(new Date());
			if(applicationYearList.size()>0){
				setApplicationYear(applicationYearList.get(0).getFinancialYear().toString());
				setApplicationYearId(applicationYearList.get(0).getFinancialYearID());
			}
		}catch(Exception e){
			e.printStackTrace();	
		}
	}


	//  ile upload code STARTED HERE

	private Boolean renderFileuploadDatatable=false;
	private Boolean renderFileuploadSubmit=false;
	private Boolean  checkFile=false;
	private UploadedFile uploadedFile;
	private List<BankMasterDTO> bankList ;
	private List<CancelReIssueDataTableBean> cancelReIssueDTList=new  CopyOnWriteArrayList<CancelReIssueDataTableBean>();
	private List<CancelReIssueDataTableBean>  cancelReIssueFinalList=new CopyOnWriteArrayList<CancelReIssueDataTableBean>();

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public List<CancelReIssueDataTableBean> getCancelReIssueDTList() {
		return cancelReIssueDTList;
	}

	public void setCancelReIssueDTList(List<CancelReIssueDataTableBean> cancelReIssueDTList) {
		this.cancelReIssueDTList = cancelReIssueDTList;
	}

	public Boolean getRenderFileuploadDatatable() {
		return renderFileuploadDatatable;
	}
	public void setRenderFileuploadDatatable(Boolean renderFileuploadDatatable) {
		this.renderFileuploadDatatable = renderFileuploadDatatable;
	}

	public Boolean getRenderFileuploadSubmit() {
		return renderFileuploadSubmit;
	}
	public void setRenderFileuploadSubmit(Boolean renderFileuploadSubmit) {
		this.renderFileuploadSubmit = renderFileuploadSubmit;
	}
	public Boolean getCheckFile() {
		return checkFile;
	}
	public void setCheckFile(Boolean checkFile) {
		this.checkFile = checkFile;
	}
	public List<CancelReIssueDataTableBean> getCancelReIssueFinalList() {
		return cancelReIssueFinalList;
	}

	public void setCancelReIssueFinalList(List<CancelReIssueDataTableBean> cancelReIssueFinalList) {
		this.cancelReIssueFinalList = cancelReIssueFinalList;
	}

	public String getApplicationYear() {
		return applicationYear;
	}
	public void setApplicationYear(String applicationYear) {
		this.applicationYear = applicationYear;
	}
	public BigDecimal getApplicationRef() {
		return applicationRef;
	}


	public void setApplicationRef(BigDecimal applicationRef) {
		this.applicationRef = applicationRef;
	}
	public BigDecimal getApplicationYearId() {
		return applicationYearId;
	}

	public void setApplicationYearId(BigDecimal applicationYearId) {
		this.applicationYearId = applicationYearId;
	}



	//when we upload a file this method called and and read the file name
	public void exportExcel(FileUploadEvent event) throws IOException, SerialException, SQLException {
		uploadedFile = event.getFile();
		uploadedFile.getFileName();
		log.info("File name is ::::::::::::::::::::::::::::::"+uploadedFile.getFileName());
		setCheckFile( true);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("uploadfile.show();");
	}


	public void uploadtoDatatable() {

		if(getCheckFile() && getBankId() != null){

			setRenderFileuploadDatatable(true);
			setRenderFileuploadSubmit(true);
			cancelReIssueDTList.clear();
			cancelReIssueFinalList.clear();
			try {

				Workbook workbook = null;
				
				if(uploadedFile.getFileName().endsWith(".xls")) {
					workbook = new HSSFWorkbook(uploadedFile.getInputstream());
				} else if(uploadedFile.getFileName().endsWith(".xlsx")) {
					workbook = new XSSFWorkbook(uploadedFile.getInputstream());
				}


				//Get the number of sheets in the xlsx file
				int numberOfSheets = workbook.getNumberOfSheets();

				//loop through each of the sheets
				for(int i=0; i < numberOfSheets; i++){

					//Get the nth sheet from the workbook
					Sheet sheet = workbook.getSheetAt(i);

					//every sheet has rows, iterate over them
					Iterator<Row> rowIterator = sheet.iterator();
					int rowNumber =1;
					while (rowIterator.hasNext()) 
					{


						Row row = rowIterator.next();
						if(rowNumber==1)
						{
							rowNumber++;
							continue;
						}

						//Every row has columns, get the column iterator and iterate over them
						Iterator<Cell> cellIterator = row.cellIterator();

						CancelReIssueDataTableBean cancelandReissueDT=new CancelReIssueDataTableBean();

						while (cellIterator.hasNext()) 
						{
							//Get the Cell object
							Cell cell = cellIterator.next();

							//check the cell type and process accordingly
							switch(cell.getCellType()){
							case Cell.CELL_TYPE_STRING:
								if(cell.getColumnIndex()==0){
									String countryCode= cell.getStringCellValue().trim().replaceAll(" +", " ");
									log.info("countryCode="+countryCode);
									cancelandReissueDT.setApplicationCountryCode(countryCode);
									cancelandReissueDT.setCountryId(generalService.getCountriIdFromCode(countryCode));
								} 
								break;
							case Cell.CELL_TYPE_NUMERIC:							

								if(cell.getColumnIndex()==1){
									Double d = cell.getNumericCellValue();
									BigDecimal companyCode = new BigDecimal(d);
									log.info("companyCode ="+companyCode);
									cancelandReissueDT.setCompanyCode(companyCode);
									cancelandReissueDT.setCompanyId(generalService.getCompanyIdFromCode(companyCode));
								}
								if(cell.getColumnIndex()==2){
									Double d = cell.getNumericCellValue();
									log.info("DocumentYear ="+d.intValue());
									cancelandReissueDT.setTransactionYear( new BigDecimal(d));
								}
								if(cell.getColumnIndex()==3){
									Double d = cell.getNumericCellValue();
									log.info(" Document No="+d.intValue());
									cancelandReissueDT.setTransactionNo( new BigDecimal(d));
									log.info("===================================================");
								}

								break;

							case Cell.CELL_TYPE_BLANK:

							}

						}
						cancelReIssueDTList.add( cancelandReissueDT);

					} //end of rows iterator

				} //end of sheets for loop

				if(cancelReIssueDTList==null || cancelReIssueDTList.size()==0){
					RequestContext.getCurrentInstance().execute("noRecordsInExcel.show();");
				}else{
					for(CancelReIssueDataTableBean canceReIssueObj:cancelReIssueDTList){
						
						if(getBankId()==null){
							RequestContext.getCurrentInstance().execute("selectBank.show();");
						}else if(canceReIssueObj.getCountryId()==null){
							RequestContext.getCurrentInstance().execute("countryCodeInvalid.show();");
						}else if(canceReIssueObj.getCompanyId()==null){
							RequestContext.getCurrentInstance().execute("companyCodeInvalid.show();");
						}else if(canceReIssueObj.getTransactionYear()==null){
							RequestContext.getCurrentInstance().execute("transYrInvalid.show();");
						}else if(canceReIssueObj.getTransactionNo()==null){
							RequestContext.getCurrentInstance().execute("transNoInvalid.show();");
						}else {
							if(getBankId()!=null&&cancelReIssueDTList.size()>0){
								
								List<ViewRemiitanceInfo> remittViewList=icancelReissueService.fetchRemittanceDetailsFileUpload(canceReIssueObj.getCountryId() ,canceReIssueObj.getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE), canceReIssueObj.getTransactionYear() , canceReIssueObj.getTransactionNo(), getBankId());
								log.info("BankId========="+getBankId());
								log.info( "TransactionNo=============="+canceReIssueObj.getTransactionNo());
								log.info("VIEW  SIZE ==============="+remittViewList.size());
								if(remittViewList!=null && remittViewList.size()>0){
									for(ViewRemiitanceInfo remittanceViewObj:remittViewList){

										CancelReIssueDataTableBean cancelReissueDT=new CancelReIssueDataTableBean();
										cancelReissueDT.setTransactionNo( remittanceViewObj.getDocumentNo());
										cancelReissueDT.setTransactionYear(remittanceViewObj.getDocumentFinYear());
										cancelReissueDT.setCustomerId( remittanceViewObj.getCustomerId());
										cancelReissueDT.setCustomerName(generalService.getCustomerNameCustomerId(remittanceViewObj.getCustomerId()));
										cancelReissueDT.setTranxAmount(remittanceViewObj.getForeignTrxAmount());
										cancelReissueDT.setCompanyId(remittanceViewObj.getCompanyId());
										cancelReissueDT.setCountryId(remittanceViewObj.getApplicationCountryId());
										cancelReissueDT.setBooEmptyRecord(true);
										cancelReissueDT.setTransactionId(remittanceViewObj.getRemittanceTransactionId());
										log.info( "trnxid============="+remittanceViewObj.getRemittanceTransactionId());
										cancelReissueDT.setOldRemittanceCompanyId( remittanceViewObj.getCompanyId());
										cancelReissueDT.setOldRemittanceDocNo(remittanceViewObj.getDocumentNo());
										cancelReissueDT.setOldRemittanceDocumentId(remittanceViewObj.getDocumentId());
										cancelReissueDT.setOldRemittanceFinYearId(remittanceViewObj.getDocumentFinYearId());
										cancelReissueDT.setApplicationDocumentNo(remittanceViewObj.getApplicationDocumentNo());
										cancelReissueDT.setApplicationDocumentYear(remittanceViewObj.getApplicationFinYear());
										cancelReIssueFinalList.add( cancelReissueDT);

									}
								}else{
									CancelReIssueDataTableBean cancelReissueDT2=new CancelReIssueDataTableBean();
									cancelReissueDT2.setBooEmptyRecord(false);
									cancelReissueDT2.setTransactionNo( canceReIssueObj.getTransactionNo());
									cancelReissueDT2.setTransactionYear(canceReIssueObj.getTransactionYear());
									cancelReissueDT2.setErrorMessage("Record Not Existed");
									cancelReIssueFinalList.add(cancelReissueDT2);
								}
							}
						}
					}
				}				
			} catch (IOException e) {
				log.error( "::::::::::::::::::::::::Problem Occured in Excel File UPLOAD ::::::::::::::::::::::");
				setErrorMessageException(e.getMessage());
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("procedureError.show();");
				return;
			}

		}   else{
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("upload.show();");
		}
		cancelReIssueDTList.clear();
		setBankId(null);
		setUploadedFile( null);
		setCheckFile( false);
	}


	//when ever click on accept button 
	public void acceptButton() throws AMGException,Exception{

		if(cancelReIssueFinalList.size()>0){
			for(CancelReIssueDataTableBean cancelReIssueDTObj :  cancelReIssueFinalList){
				if(cancelReIssueDTObj.getBooEmptyRecord()){
					//int applicationno=getNextApplicationNo();
					nextApplicationNo();
					log.info("Trasactionid=========="+cancelReIssueDTObj.getTransactionId());
					saveRemitTansaction(cancelReIssueDTObj.getTransactionId(),cancelReIssueDTObj);
		}
				
			}
			if(getSaveOk()){
				cancelReIssueFinalList.clear();
				setSaveOk( false);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("complete.show();");
				}
		}else{
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("nodata.show();");

		}
	}



	public void saveRemitTansaction(BigDecimal transactionId,CancelReIssueDataTableBean cancelReIssueDTObj){
		log.info( "saveRemitTransaction() method calledCalled=================");
		try{
		if(cancelReIssueDTObj.getOldRemittanceDocNo()!=null&&cancelReIssueDTObj.getTransactionYear()!=null&&cancelReIssueDTObj.getOldRemittanceDocumentId()!=null){
		HashMap<String, String> mapProcedureData = new HashMap<String,String>();
		 	
		
		mapProcedureData.put("ApplicationCountryID", session.getCountryId().toString());
		mapProcedureData.put("OldRemittanceCompanyID", cancelReIssueDTObj.getOldRemittanceCompanyId().toString());
		mapProcedureData.put("OldRemittanceDocumentID", cancelReIssueDTObj.getOldRemittanceDocumentId().toString());
		mapProcedureData.put("OldRemittanceDocumentFinancialYear", cancelReIssueDTObj.getTransactionYear().toString());
		mapProcedureData.put("OldRemittanceDocumentNo", cancelReIssueDTObj.getOldRemittanceDocNo().toString());
		mapProcedureData.put("CountryBranchID",  session.getBranchId());
		mapProcedureData.put("loginUser",  session.getUserName());
		BigDecimal docNum=icancelReissueService.procedureCallForSave(mapProcedureData);
		setSaveOk(true);
		
			}	
			

 }catch(NullPointerException ne){
				log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::fetchRemiitanceView"); 
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return; 

			}catch(Exception e){
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("savingError.show();");
				log.error( "Saving the APPL_TRNX Problem Occured",e);
				return;
				//setErrorMessage( e.getMessage()==null?"":e.getMessage()+""+getErrMsg());

			}
 


	}


	public AdditionalInstructionData saveRemitAdditionalData(BigDecimal applDocumentNo){
		AdditionalInstructionData additionalData = new AdditionalInstructionData();
		List<AdditionalInstructionData> additionalList = icancelReissueService.remittanceAppAdditionalDataList(applDocumentNo);
		if(additionalList.size()>0){
			try{

				AdditionalInstructionData getData = additionalList.get(0);
				additionalData.setAdditionalBankFieldsId(getData.getAdditionalBankFieldsId());
				//additionalData.setAdditionalInstructionDataId(getData.getAdditionalInstructionDataId());
				additionalData.setApprovedBy(session.getUserName());
				additionalData.setApprovedDate(new Date());
				additionalData.setCreatedBy(session.getUserName());
				additionalData.setCreatedDate(new Date());
				additionalData.setDocumentFinanceYear(getData.getDocumentFinanceYear());
				additionalData.setDocumentNo(getApplicationRef());
				additionalData.setIsactive(getData.getIsactive());
				additionalData.setAmiecCode(getData.getAmiecCode() );
				
				if(getData.getExDocument()!=null){
					Document document = new Document();
					document.setDocumentID(getData.getExDocument().getDocumentID());
					additionalData.setExDocument(document);
				}

				RemittanceApplication remiApp = new RemittanceApplication();
				remiApp.setRemittanceApplicationId(getRemittanceAppId());
				additionalData.setExRemittanceApplication(remiApp);
				if(getData.getExUserFinancialYear()!=null){
					UserFinancialYear userFinyear = new UserFinancialYear();
					userFinyear.setFinancialYearID(getData.getExUserFinancialYear().getFinancialYearID());
					additionalData.setExUserFinancialYear(userFinyear);
				}
				additionalData.setFlexField(getData.getFlexField());
				additionalData.setFlexFieldValue(getData.getFlexFieldValue());
				additionalData.setIsactive(getData.getIsactive());
				if(getData.getFsCompanyMaster()!=null){
					CompanyMaster compMaster = new CompanyMaster();
					compMaster.setCompanyId(getData.getFsCompanyMaster().getCompanyId());
					additionalData.setFsCompanyMaster(compMaster);
				}
				if(getData.getFsCountryMaster()!=null){
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(getData.getFsCountryMaster().getCountryId());
					additionalData.setFsCountryMaster(countryMaster);
				}


			}catch(Exception e){	log.error( "Saving the  APPL_ADDL_DATA Problem Occured");}
		}
		return additionalData;
	}


	public RemitApplAml saveRemitApplAml(BigDecimal  applicationDocumentNo){
		RemitApplAml remitAml = new RemitApplAml();
		List<RemitApplAml> listRemitAml = icancelReissueService.remittanceAppAmlList(applicationDocumentNo);
		if(listRemitAml.size()>0){
			try{

				RemitApplAml getAmlData = listRemitAml.get(0);
				remitAml.setBlackClearedUser(getAmlData.getBlackClearedUser());
				remitAml.setBlackListClear(getAmlData.getBlackListClear());
				remitAml.setBlackListDate(getAmlData.getBlackListDate());
				remitAml.setBlackListReason(getAmlData.getBlackListReason());
				remitAml.setBlackListRemarks(getAmlData.getBlackListRemarks());
				remitAml.setBlackListUser(getAmlData.getBlackListUser());
				remitAml.setCreatedBy(session.getUserName());
				remitAml.setCreatedDate(new Date());

				remitAml.setCustomerSignature(getAmlData.getCustomerSignature());
				remitAml.setIsactive(getAmlData.getIsactive());
				RemittanceApplication remitApplication = new RemittanceApplication();
				remitApplication.setRemittanceApplicationId(getRemittanceAppId());
				remitAml.setExRemittanceAppfromAml(remitApplication);
				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(session.getCompanyId());
				remitAml.setFsCompanyMaster(companyMaster);
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(getAmlData.getFsCountryMaster().getCountryId());
				remitAml.setFsCountryMaster(countryMaster);
				//Added by Nazish on 16-JAN-2016
				remitAml.setAuthorizedBy(getAmlData.getAuthorizedBy());
				remitAml.setAuthType(getAmlData.getAuthType());


			}catch(Exception e){log.error( "saving EX_APPL_AML  Problem occured");}
		}
		return remitAml;
	}



	public RemittanceAppBenificiary saveRemittanceAppBenificiary(BigDecimal  applicationDocumentNo){
		RemittanceAppBenificiary remiBene = new RemittanceAppBenificiary();
		List<RemittanceAppBenificiary> listRemitAml = icancelReissueService.remittanceApplicationBeneficiaryList(applicationDocumentNo);
		if(listRemitAml.size()>0){

			RemittanceAppBenificiary getBeneData = listRemitAml.get(0);
			remiBene.setBeneficiaryAccountNo(getBeneData.getBeneficiaryAccountNo());
			remiBene.setBeneficiaryBank(getBeneData.getBeneficiaryBank());
			remiBene.setBeneficiaryBranch(getBeneData.getBeneficiaryBranch());
			remiBene.setBeneficiaryFirstName(getBeneData.getBeneficiaryFirstName());
			remiBene.setBeneficiaryFourthName(getBeneData.getBeneficiaryFourthName());
			remiBene.setBeneficiarySwiftAddr1(getBeneData.getBeneficiarySwiftAddr1());
			remiBene.setBeneficiarySwiftAddr2(getBeneData.getBeneficiarySwiftAddr2());
			remiBene.setBeneficiaryName(getBeneData.getBeneficiaryName());
			remiBene.setBeneficiarySecondName(getBeneData.getBeneficiarySecondName());
			remiBene.setBeneficiarySwiftBank1(getBeneData.getBeneficiarySwiftBank1());
			remiBene.setBeneficiarySwiftBank2(getBeneData.getBeneficiarySwiftBank2());
			remiBene.setBeneficiaryThirdName(getBeneData.getBeneficiaryThirdName());
			remiBene.setCompanyCode(getBeneData.getCompanyCode());
			remiBene.setCreatedBy(session.getUserName());
			remiBene.setCreatedDate(new Date());

			remiBene.setIsactive(getBeneData.getIsactive() );
			remiBene.setDocumentCode(getBeneData.getDocumentCode());
			remiBene.setBeneficiaryId( getBeneData.getBeneficiaryId());

			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(session.getCompanyId());
			remiBene.setFsCompanyMaster(companyMaster);

			remiBene.setBeneficiaryAccountSeqId(getBeneData.getBeneficiaryAccountSeqId());
			remiBene.setBeneficiaryRelationShipSeqId(getBeneData.getBeneficiaryRelationShipSeqId());
			remiBene.setBeneficiaryBankCountryId(getBeneData.getBeneficiaryBankCountryId());
			
			remiBene.setBeneficiaryBankId(getBeneData.getBeneficiaryBankId());
			remiBene.setBeneficiaryBankBranchId(getBeneData.getBeneficiaryBankBranchId());
			remiBene.setBeneficiaryBranchStateId(getBeneData.getBeneficiaryBranchStateId());
			remiBene.setBeneficiaryBranchDistrictId(getBeneData.getBeneficiaryBranchDistrictId());
			remiBene.setBeneficiaryBranchCityId(getBeneData.getBeneficiaryBranchCityId());
			remiBene.setBeneficiaryBankSwift(getBeneData.getBeneficiaryBankSwift());
			remiBene.setBeneficiaryFifthName(getBeneData.getBeneficiaryFifthName());
			remiBene.setBeneficiarySwiftBank1Id(getBeneData.getBeneficiarySwiftBank1Id());
			remiBene.setBeneficiarySwiftBank2Id(getBeneData.getBeneficiarySwiftBank2Id());
			remiBene.setDocumentNo( getApplicationRef());
			remiBene.setBeneficiaryTelephoneNumber( getBeneData.getBeneficiaryTelephoneNumber());
			
			if(getBeneData.getExDocument()!=null){
				Document document = new Document();
				document.setDocumentID(getBeneData.getExDocument().getDocumentID());
				remiBene.setExDocument(document);
			}

			RemittanceApplication remiApp = new RemittanceApplication();
			remiApp.setRemittanceApplicationId(getRemittanceAppId());
			remiBene.setExRemittanceAppfromBenfi(remiApp);
			if(getBeneData.getExUserFinancialYear()!=null){
				UserFinancialYear userFinyear = new UserFinancialYear();
				userFinyear.setFinancialYearID(getBeneData.getExUserFinancialYear().getFinancialYearID());
				remiBene.setExUserFinancialYear(userFinyear);
			}


		}
		return remiBene;
	}

	public Boolean getSaveOk() {
		return saveOk;
	}

	public void setSaveOk(Boolean saveOk) {
		this.saveOk = saveOk;
	}


























}

package com.amg.exchange.remittance.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javassist.bytecode.stackmap.BasicBlock.Catch;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CutomerDetailsView;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.common.service.ISourceOfIncomeService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.mail.ApllicationMailer1;
import com.amg.exchange.miscellaneous.service.IMiscellaneousReceiptPaymentService;
import com.amg.exchange.registration.bean.CustomerDebitCardDatatable;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerInfoView;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.IEncrptionDescriptionService;
import com.amg.exchange.remittance.model.CollectionDetailView;
import com.amg.exchange.remittance.model.CollectionPaymentDetailsView;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.CustomerDBCardDetailsView;
import com.amg.exchange.remittance.model.KnetDownload;
import com.amg.exchange.remittance.model.PurposeOfRemittanceView;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.remittance.model.ViewKnetTrnxRelease;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.remittance.service.IKnetUploadFileService;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IBankMasterService;
import com.amg.exchange.treasury.service.IBeneCountryServices;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;
import com.amg.exchange.wu.service.IWesternUnionService;

@Component("knetTranxFileUploadBean")
@Scope("session")
public class KnetFileUploadBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(KnetFileUploadBean.class);

	// panel 1 beneficiary and customer details
	private UploadedFile file;

	private BigDecimal customerId = null;
	private BigDecimal customerReference = null;
	private Date trnxDate = null;
	private String trnxDateStr = null;

	private String cardNumber = null;
	private String merchantTranckId = null;
	private String customerName = null;
	private String approvalNumber;
	private BigDecimal amount;
	private String status = null;
	private String errorMessage = null;
	private String authorizationCode = null;
	private String resultCode = null;
	private String referenceId = null;
	private String transactionId = null;
	private String orderId = null;
	private String userField1;
	private String userField2;
	private String userField3;
	private String userField4;
	private String userField5;
	private int totalTobeProcessed;
	private int totalAuthAndProcessed;
	private int totalRejected;
	private int totalTagError;
	private String trnxStatus;

	private String dCardNo1;
	private String dCardNo2;
	private String dCardNo3;
	private String dCardNo4;

	private String reTypeDCardNo1;
	private String reTypeDCardNo2;
	private String reTypeDCardNo3;
	private String reTypeDCardNo4;
	private BigDecimal collDocNo;
	private BigDecimal collDocCode;
	private BigDecimal collDocFyr;
	private String toEmail;

	private String colBankCode;

	private String exception = null;
	private BigDecimal financialYear;

	private UploadedFile uploadedFile;

	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	IWesternUnionService westernUnionService;
	@Autowired
	IBeneCountryServices<T> ibeneCountryServices;
	@Autowired
	ISourceOfIncomeService sourceofIncomeservice;

	@Autowired
	BeneficiaryEditBean beneficiaryEditBean;
	@Autowired
	IServiceGroupMasterService serviceGroupMasterService;
	@Autowired
	IBeneficaryCreation beneficaryCreation;
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;
	@Autowired
	ApplicationContext context;
	@Autowired
	ServiceCodeMasterService serviceMasterService;
	@Autowired
	IPaymentService ipaymentService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	@Autowired
	ICustomerBankService icustomerBankService;

	@Autowired
	ApllicationMailer1 mailService;

	@Autowired
	IKnetUploadFileService knetUploadFileService;

	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;

	@Autowired
	IMiscellaneousReceiptPaymentService<T> miscellaneousReceiptPaymentService;

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;

	@Autowired
	IEncrptionDescriptionService<T> encryptionDescriptionService;

	SessionStateManage sessionStateManage = new SessionStateManage();
	private Map<BigDecimal, String> mapcomIdentityType = new HashMap<BigDecimal, String>();
	private List<CustomerIdProof> customerDetailsList = new ArrayList<CustomerIdProof>();

	private List<KnetTranxFileUploadDatatable> lstknetTranxFileUploadDatatable = new ArrayList<KnetTranxFileUploadDatatable>();

	private List<KnetTranxFileUploadDatatable> lstknetTranxFileUploadDatatableCopy = new CopyOnWriteArrayList<KnetTranxFileUploadDatatable>();
	private KnetTranxFileUploadDatatable lstSelectedKnetranxFileUpload = new KnetTranxFileUploadDatatable();
	List<ViewKnetTrnxRelease> listOnHoldKnetTrnx = new ArrayList<ViewKnetTrnxRelease>();

	private List<RemittanceReceiptSubreport> remittanceReceiptSubreportList;
	// Jasper Report generation
	private JasperPrint jasperPrint;
	private List<CollectionDetailView> collectionViewList = new CopyOnWriteArrayList<CollectionDetailView>();

	private List<ViewBankDetails> bankMasterList = new CopyOnWriteArrayList<ViewBankDetails>();
	private List<ViewBankDetails> localbankList = new ArrayList<ViewBankDetails>();

	public void KnetFileUploadBean() {

		setCardType(null);
		setSelectCardType(0);
		setIdNumber(null);

	}

	// page navigation
	public void pageNavigation() {
		try {
			clearDetails();
			fetchUserFinancialYear();
			try {
				loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "knetTranxfileupload.xhtml");
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/knetTranxfileupload.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
	}

	public void pageNavigationToKnetRelease() {

		try {
			clearDetails();
			fetchUserFinancialYear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/KnetReleaseTrnx.xhtml");
		} catch (IOException e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}

	}

	public void readCSVfile(FileUploadEvent event) {

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		uploadedFile = event.getFile();
		uploadedFile.getFileName();
		lstknetTranxFileUploadDatatable.clear();
		totalTobeProcessed = 0;
		totalAuthAndProcessed = 0;
		totalRejected = 0;
		totalTagError = 0;

		try {
			int lineNoCount = 0;

			Workbook workbook = null;

			workbook = new HSSFWorkbook(uploadedFile.getInputstream());

			// Get the number of sheets in the xlsx file
			int numberOfSheets = workbook.getNumberOfSheets();

			// loop through each of the sheets
			for (int i = 0; i < numberOfSheets; i++) {

				// Get the nth sheet from the workbook
				Sheet sheet = workbook.getSheetAt(i);

				// every sheet has rows, iterate over them
				Iterator<Row> rowIterator = sheet.iterator();
				int rowNumber = 1;
				while (rowIterator.hasNext()) {

					Row row = rowIterator.next();
					if (rowNumber == 1) {
						rowNumber++;
						continue;
					}

					// Every row has columns, get the column iterator and
					// iterate over them
					Iterator<Cell> cellIterator = row.cellIterator();
					// CityMasterDataTable cityMasterData = new
					// CityMasterDataTable();
					// CurrencyDataTable currencyDT=new CurrencyDataTable();

					KnetTranxFileUploadDatatable knetTranxFileUploadDatatable = new KnetTranxFileUploadDatatable();

					while (cellIterator.hasNext()) {
						// Get the Cell object
						Cell cell = cellIterator.next();

						// check the cell type and process accordingly
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:

							if (cell.getColumnIndex() == 4) {
								String resultCode = cell.getStringCellValue().trim();
								log.info("resultCode Code =" + resultCode);
								knetTranxFileUploadDatatable.setResultCode(resultCode);

							}

							if (cell.getColumnIndex() == 1) {
								String trnxDateTime = cell.getStringCellValue().trim();
								log.info("trnxDateTime =" + trnxDateTime + "\t Formated Date :" + formatDateddMMYyyy(trnxDateTime));
								knetTranxFileUploadDatatable.setTrnxDate(formatDateddMMYyyy(trnxDateTime));
							}
							if (cell.getColumnIndex() == 2) {
								String quoteName = cell.getStringCellValue().trim();
								log.info(" Quote Name=" + quoteName);
								// currencyDT.setQuoteName(quoteName );
							}

							if (cell.getColumnIndex() == 5) {
								String merTrackId = cell.getStringCellValue().trim();
								log.info("merTrackId=" + merTrackId.replaceAll("'", ""));
								knetTranxFileUploadDatatable.setCustomerReference(merTrackId.replaceAll("'", ""));
							}

							if (cell.getColumnIndex() == 6) {
								String refeId = cell.getStringCellValue().trim();
								log.info(" refeId=" + refeId.replaceAll("'", ""));
								knetTranxFileUploadDatatable.setReferenceId(refeId.replaceAll("'", ""));
							}
							if (cell.getColumnIndex() == 7) {
								String tranId = cell.getStringCellValue().trim();
								log.info("  tranId=" + tranId.replaceAll("'", ""));
								knetTranxFileUploadDatatable.setTransactionId(tranId.replaceAll("'", ""));
							}
							if (cell.getColumnIndex() == 8) {
								String orderId = cell.getStringCellValue().trim();
								log.info("  orderId=" + orderId.replaceAll("'", ""));
								knetTranxFileUploadDatatable.setOrderId(orderId.replaceAll("'", ""));
							}

							if (cell.getColumnIndex() == 9) {
								String userField1 = cell.getStringCellValue().trim();
								log.info("  userField1=" + userField1);
								knetTranxFileUploadDatatable.setUserField1(userField1 == null ? "" : userField1.replaceAll("'", ""));
							}
							if (cell.getColumnIndex() == 10) {
								String userField2 = cell.getStringCellValue().trim();
								log.info("  userField12=" + userField2);
								knetTranxFileUploadDatatable.setUserField1(userField2 == null ? "" : userField2.replaceAll("'", ""));
							}
							if (cell.getColumnIndex() == 11) {
								String userField3 = cell.getStringCellValue().trim();
								log.info("  userField3=" + userField3);
								knetTranxFileUploadDatatable.setUserField1(userField3 == null ? "" : userField3.replaceAll("'", ""));
							}
							if (cell.getColumnIndex() == 12) {
								String userField4 = cell.getStringCellValue().trim();
								log.info("  userField3=" + userField4);
								knetTranxFileUploadDatatable.setUserField1(userField4 == null ? "" : userField4.replaceAll("'", ""));
							}

							if (cell.getColumnIndex() == 13) {
								String userField4 = cell.getStringCellValue().trim();
								log.info("  userField3=" + userField5);
								knetTranxFileUploadDatatable.setUserField1(userField5 == null ? "" : userField5.replaceAll("'", ""));
							}

							if (cell.getColumnIndex() == 14) {
								String card = cell.getStringCellValue().trim();
								log.info("  card=" + card);
								if (card != null && card.length() == 16) {
									knetTranxFileUploadDatatable.setCardNumber(card);
								} else {
									setErrorMessage("Line No :" + knetTranxFileUploadDatatable.getSrNo()
											+ " The debit card should be 16 digit, correct the card number in excel file and upload again :" + card);
									RequestContext.getCurrentInstance().execute("errorPage.show();");
									return;
								}
							}

							break;
						case Cell.CELL_TYPE_NUMERIC:

							if (cell.getColumnIndex() == 0) {
								Double d = cell.getNumericCellValue();
								BigDecimal count = new BigDecimal(d);
								log.info("count =" + count);
								knetTranxFileUploadDatatable.setSrNo(count);

							}

							if (cell.getColumnIndex() == 2) {
								Double d = cell.getNumericCellValue();
								BigDecimal authcode = new BigDecimal(d);
								log.info("authcode =" + authcode);
								knetTranxFileUploadDatatable.setAuthorizationCode(authcode);

							}
							if (cell.getColumnIndex() == 3) {
								Double d = cell.getNumericCellValue();
								BigDecimal trnxAmount = new BigDecimal(d);
								log.info("trnxAmount =" + trnxAmount);
								knetTranxFileUploadDatatable.setAmount(GetRound.roundBigDecimal(trnxAmount, 3));
							}

							break;

						case Cell.CELL_TYPE_BLANK:

						}

					}

					System.out.println("Cusref : " + knetTranxFileUploadDatatable.getCustomerReference());
					knetTranxFileUploadDatatable.setFinYear(getFinancialYear());

					if (knetTranxFileUploadDatatable.getCardNumber() != null ) { //&& (!knetTranxFileUploadDatatable.getCustomerReference().substring(0, 4).equalsIgnoreCase(getFinancialYear().toString())

						List<CustomerDBCardDetailsView> list = knetUploadFileService.customerBanksView(new BigDecimal(knetTranxFileUploadDatatable.getCustomerReference()),knetTranxFileUploadDatatable.getCardNumber(),knetTranxFileUploadDatatable);

						if (list.isEmpty()) {
							knetTranxFileUploadDatatable.setTextColor("brown");
							knetTranxFileUploadDatatable.setStatus("ON HOLD");
							knetTranxFileUploadDatatable.setErrorMessage("DEBIT CARD NOT MATCHING");
							/*
							 * setErrorMessage("Line No :" +
							 * knetTranxFileUploadDatatable.getSrNo()+
							 * " The debit card is not matched with customer, Kindly check :"
							 * +knetTranxFileUploadDatatable.getCardNumber());
							 * RequestContext
							 * .getCurrentInstance().execute("errorPage.show();"
							 * ); return;
							 */
						}/*
						 * else if(isEmpty() ){
						 * 
						 * }
						 */

					}

					List<CustomerInfoView> custList = miscellaneousReceiptPaymentService.getCustomerDetailsBasedOnRef(new BigDecimal(knetTranxFileUploadDatatable.getCustomerReference()));

				
					List<KnetDownload> listDownload = knetUploadFileService.checkDupliateKnetTrnx(knetTranxFileUploadDatatable);
					int count = knetUploadFileService.checkCustomerReference(knetTranxFileUploadDatatable);

					if (listDownload != null && listDownload.size() > 0) {
						knetTranxFileUploadDatatable.setTextColor("brown");
						knetTranxFileUploadDatatable.setStatus("ALREADY UPLOADED BEFORE");
					}

					if (!custList.isEmpty() && custList.size() == 1) {
						knetTranxFileUploadDatatable.setCustomerName(custList.get(0).getFirstName() + " " + custList.get(0).getMiddleName() + " " + custList.get(0).getLastName());
						knetTranxFileUploadDatatable.setToEmailId(custList.get(0).getEmail());
					}

					if (listDownload.isEmpty() && count == 0) {
						knetTranxFileUploadDatatable.setTextColor("red");
						knetTranxFileUploadDatatable.setStatus("ERROR");
						knetTranxFileUploadDatatable.setErrorMessage("UNABLE TO GET CUSTOMER REFERENCE");
						totalTagError++;
					}

					if (listDownload.isEmpty() && count == 1 && knetTranxFileUploadDatatable.getStatus() == null) {
						totalAuthAndProcessed++;
						knetTranxFileUploadDatatable.setTextColor("green");
						//knetTranxFileUploadDatatable.setStatus("PROCESSED");
						knetTranxFileUploadDatatable.setStatus("");
					}

					lstknetTranxFileUploadDatatable.add(knetTranxFileUploadDatatable);
					setLstknetTranxFileUploadDatatable(lstknetTranxFileUploadDatatable);
					if (lstknetTranxFileUploadDatatable != null) {
						totalTobeProcessed = lstknetTranxFileUploadDatatable.size();
					}

				} // end of rows iterator
			}

			System.out.println("SIZE :" + lstknetTranxFileUploadDatatable.size());
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					setErrorMessage(e.getMessage());
					RequestContext.getCurrentInstance().execute("errorPage.show();");
				}
			}
		}

	}

	public void uploadKnetFile() throws Exception {

		try {
			if (totalTagError > 0) {
				setErrorMessage("FILE HAS ERRORS.PLEASE CORRECT AND RE-UPLOAD");
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;
			} else {

				for (KnetTranxFileUploadDatatable knetDataTable : lstknetTranxFileUploadDatatable) {

					// Calling procedure

					BigDecimal collDocumentNumber = null;
					BigDecimal collDocFyr = null;
					String email = null;
					String status = null;
					String proErrMsg = null;
					BigDecimal compCode = new BigDecimal("0");
					BigDecimal locCode = null;

					HashMap<String, Object> hmapOutValues = knetUploadFileService.callKnetTransferProcedureForUpdate(knetDataTable);

					collDocumentNumber = (BigDecimal) hmapOutValues.get("P_COL_DOCNO");
					collDocFyr = (BigDecimal) hmapOutValues.get("P_COL_DOCFYR");
					status = hmapOutValues.get("P_STATUS") == null ? null : hmapOutValues.get("P_STATUS").toString();
					proErrMsg = hmapOutValues.get("P_ERROR_MESSAGE") == null ? null : hmapOutValues.get("P_ERROR_MESSAGE").toString();
					compCode = (BigDecimal) hmapOutValues.get("P_COMPANY_CODE");
					locCode = (BigDecimal) hmapOutValues.get("P_LOCCOD");
					System.out.println("collDocFyr collDocumentNumber :" + collDocFyr + "--" + collDocumentNumber + "\t locCode :" + locCode + "\t Status : " + status);

					if (status.equalsIgnoreCase("ERROR")) {
						setErrorMessage(proErrMsg);
						RequestContext.getCurrentInstance().execute("errorPage.show();");
					} else {

						// insert into EX_KNET_DOWNLOAD table
						KnetDownload knetDoanLoad = new KnetDownload();
						knetDoanLoad.setSrNo(knetDataTable.getSrNo());
						knetDoanLoad.setApprovalNumber(knetDataTable.getAuthorizationCode());
						knetDoanLoad.setCardNumber(knetDataTable.getCardNumber());
						knetDoanLoad.setColComCode(compCode);
						knetDoanLoad.setColDocCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION));
						knetDoanLoad.setColdocFyr(collDocFyr);
						knetDoanLoad.setColDocNo(collDocumentNumber);
						knetDoanLoad.setCreator(sessionStateManage.getUserName());
						knetDoanLoad.setCrtDate(new Date());
						knetDoanLoad.setCustomerReference(new BigDecimal(knetDataTable.getCustomerReference()));
						knetDoanLoad.setErrorMessage(knetDataTable.getErrorMessage());
						knetDoanLoad.setMerchantTranckId(knetDataTable.getCustomerReference());
						knetDoanLoad.setModifier(sessionStateManage.getUserName());
						knetDoanLoad.setOrderId(knetDataTable.getOrderId());
						knetDoanLoad.setReferenceId(knetDataTable.getReferenceId());
						knetDoanLoad.setTransactionId(knetDataTable.getTransactionId());
						knetDoanLoad.setResultCode(knetDataTable.getResultCode());
						knetDoanLoad.setStatus(status);
						knetDoanLoad.setTrnxAmount(knetDataTable.getAmount());
						// knetDoanLoad.setTrnxComCod(trnxComCod);
						knetDoanLoad.setTrnxDate(new Date(knetDataTable.getTrnxDate()));
						// knetDoanLoad.setTrnxDocCod(trnxDocCod);
						// knetDoanLoad.setTrnxdocFyr(trnxdocFyr);
						// knetDoanLoad.setTrnxDocNo(trnxDocNo);
						// knetDoanLoad.setUpdateIndicator(updateIndicator);

						knetDoanLoad.setUserField1(knetDataTable.getUserField1());
						knetDoanLoad.setUserField2(knetDataTable.getUserField2());
						knetDoanLoad.setUserField3(knetDataTable.getUserField3());
						knetDoanLoad.setUserField4(knetDataTable.getUserField4());
						knetDoanLoad.setUserField5(knetDataTable.getUserField5());

						knetUploadFileService.saveKnetDownLoadFileDetails(knetDoanLoad);

						/*
						 * if(status.equalsIgnoreCase("ERROR")){
						 * setErrorMessage(proErrMsg);
						 * RequestContext.getCurrentInstance
						 * ().execute("errorPage.show();"); }else
						 */
						if (status != null && status.equalsIgnoreCase("PROCESSED")) {

							// Send Email if there is no error
							System.out.println("collDocumentNumber " + collDocumentNumber);
							System.out.println("collDocFyr " + collDocFyr);
							System.out.println("Email ID  :" + knetDataTable.getToEmailId() + "\t Name :" + knetDataTable.getCustomerName());
							// collDocumentNumber =new BigDecimal("43000266");
							// collDocFyr = new BigDecimal("2016");

							if (knetDataTable.getToEmailId() == null || knetDataTable.getToEmailId().equals("")) {
								setErrorMessage("Email id is not exist.Update your email id");
								RequestContext.getCurrentInstance().execute("errorPage.show();");
							} else {
								generateReceiptReport(collDocumentNumber, collDocFyr, knetDataTable.getToEmailId(), knetDataTable.getCustomerName());
							}
						} else if (status != null && status.equalsIgnoreCase("ON HOLD") && locCode.compareTo(new BigDecimal("90")) == 0) {

							if (knetDataTable.getToEmailId() == null || knetDataTable.getToEmailId().equals("")) {
								setErrorMessage("Email id is not exist.Update your email id");
								RequestContext.getCurrentInstance().execute("errorPage.show();");
							} else {
								sendEmailForRefund(knetDataTable.getToEmailId(), knetDataTable.getCustomerName(), collDocFyr, collDocumentNumber);
							}
						} else if (status != null && status.equalsIgnoreCase("ON HOLD")) { // Reject
							System.out.println("callKnetTransferProcedureForRefund Called----:" + status);
							knetUploadFileService.callKnetTransferProcedureForRefund(sessionStateManage.getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION), collDocFyr,
									collDocumentNumber, "I", sessionStateManage.getUserName());

						}

					}

				}
				clearDetails();
				setErrorMessage("UPLOADED SUCCESSFULLY");
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;

			}
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}

	}

	public String formatDateddMMYyyy(String knetDateStr) {

		String strDate = null;
		DateFormat readFormat = new SimpleDateFormat("MMMM dd, yyyy hh:mm:ss a zzz");
		DateFormat writeFormat = new SimpleDateFormat("dd/MM/yyyy");

		Date date = null;
		try {
			date = readFormat.parse(knetDateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (date != null) {
			strDate = writeFormat.format(date);
		}

		return strDate;

	}

	// int i;
	public void generateReceiptReport(BigDecimal documentNo, BigDecimal collDocFyr, String toEmail, String customerName) throws AMGException {

		ServletOutputStream servletOutputStream = null;
		try {
			fetchRemittanceReceiptReportData(documentNo, collDocFyr, Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION);

			remittanceReceiptReportInit();
			byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);
			// email sending to customer

			// if(toEmail != null && !toEmail.equals("")){
			sendEmail(pdfasbytes, toEmail, customerName, collDocFyr, documentNo);
			// }

		} catch (Exception e) {

		} finally {
			if (servletOutputStream != null) {
				try {
					servletOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void sendEmail(byte[] pdfasbytes, String toEmail, String customerName, BigDecimal collDocFyr, BigDecimal documentNo) throws com.lowagie.text.DocumentException, IOException,
			AddressException, javax.mail.MessagingException {

		String subject = "Online-Receipt-" + collDocFyr + "/" + documentNo;
		try {
			List<ApplicationSetup> lstApplicationSetup = iPersonalRemittanceService.getEmailFromAppSetup(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId());

			// if(toEmail!=null && !toEmail.equals("")){
			//mailService.sendMailToCustomerWithAttachmentForKnetUpload(lstApplicationSetup, toEmail, subject, pdfasbytes, customerName);
			// }
		} catch (AMGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendEmailForRefund(String toEmail, String customerName, BigDecimal docFyr, BigDecimal colDocNo) throws com.lowagie.text.DocumentException, IOException, AddressException,
			javax.mail.MessagingException {
		String subject = "ONLINE TRANSACTION REFERENCE NO " + docFyr + "/" + colDocNo + " ON HOLD";
		try {
			List<ApplicationSetup> lstApplicationSetup = iPersonalRemittanceService.getEmailFromAppSetup(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId());
			//mailService.sendMailToCustomerForRefundKnetTrnx(lstApplicationSetup, toEmail, subject, customerName);
		} catch (AMGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void remittanceReceiptReportInit() throws JRException {

		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remittanceReceiptSubreportList);
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		// String reportPath = realPath
		// +"\\reports\\design\\RemittanceReceiptNewReport.jasper"; //for Window
		String reportPath = realPath + "//reports//design//RemittanceReceiptNewReport.jasper";
		System.out.println(reportPath);

		// String reportPath =
		// FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RemittanceReceiptNewReport.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);

	}

	private void fetchRemittanceReceiptReportData(BigDecimal documentNumber, BigDecimal finaceYear, String documentCode) throws Exception {

		collectionViewList.clear();
		remittanceReceiptSubreportList = new CopyOnWriteArrayList<RemittanceReceiptSubreport>();

		List<RemittanceApplicationView> remittanceApplicationList = new ArrayList<RemittanceApplicationView>();

		List<RemittanceApplicationView> fcsaleList = new ArrayList<RemittanceApplicationView>();

		List<RemittanceReportBean> remittanceApplList = new ArrayList<RemittanceReportBean>();

		List<RemittanceReportBean> fcsaleAppList = new ArrayList<RemittanceReportBean>();

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		String waterMark = realPath + Constants.REPORT_WATERMARK_LOGO;

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		int noOfTransactions = 0;

		String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));

		List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(sessionStateManage.getUserName());

		List<RemittanceApplicationView> remittanceViewlist = iPersonalRemittanceService.getRecordsForRemittanceReceiptReport(documentNumber, finaceYear, documentCode);

		if (remittanceViewlist.size() > 0) {

			for (RemittanceApplicationView remittanceAppview : remittanceViewlist) {

				if (remittanceAppview.getApplicationTypeDesc().equalsIgnoreCase("REMITTANCE")) {
					remittanceApplicationList.add(remittanceAppview);
					noOfTransactions = noOfTransactions + 1;
				} else if (remittanceAppview.getApplicationTypeDesc().equalsIgnoreCase("FOREIGN CURRENCY SALE")) {
					fcsaleList.add(remittanceAppview);
					noOfTransactions = noOfTransactions + 1;
				}
			}
			// remittance List
			for (RemittanceApplicationView view : remittanceApplicationList) {

				RemittanceReportBean obj = new RemittanceReportBean();

				List<PurposeOfRemittanceView> purposeOfRemittanceList = iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(), view.getDocumentFinancialYear());

				List<PurposeOfRemittanceReportBean> purposeOfRemitTrList1 = new ArrayList<PurposeOfRemittanceReportBean>();
				for (PurposeOfRemittanceView purposeObj : purposeOfRemittanceList) {
					PurposeOfRemittanceReportBean beanObj = new PurposeOfRemittanceReportBean();
					beanObj.setPurposeOfTrField(purposeObj.getFlexfieldName());
					beanObj.setPurposeOfTrfieldArabic(null);
					beanObj.setPurposeOfTrValue(purposeObj.getFlexiFieldValue());
					purposeOfRemitTrList1.add(beanObj);
				}

				if (purposeOfRemitTrList1.size() > 0) {
					obj.setPurposeOfRemitTrList(purposeOfRemitTrList1);
				}
				// setting customer reference
				StringBuffer customerReff = new StringBuffer();

				if (view.getCustomerReference() != null) {
					customerReff.append(view.getCustomerReference());
					customerReff.append(" / ");
				}

				StringBuffer customerName = new StringBuffer();

				if (view.getFirstName() != null) {
					customerName.append(" ");
					customerName.append(view.getFirstName());
					customerReff.append(" ");
					customerReff.append(view.getFirstName());
				}
				if (view.getMiddleName() != null) {
					customerName.append(" ");
					customerName.append(view.getMiddleName());
					customerReff.append(" ");
					customerReff.append(view.getMiddleName());
				}
				if (view.getLastName() != null) {
					customerName.append(" ");
					customerName.append(view.getLastName());
					customerReff.append(" ");
					customerReff.append(view.getLastName());
				}

				obj.setFirstName(customerReff.toString());
				setCustomerNameForReport(customerName.toString());

				if (view.getContactNumber() != null && !view.getContactNumber().trim().equalsIgnoreCase("")) {
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}

				obj.setCivilId(view.getIdentityInt());

				Date sysdate = view.getIdentityExpiryDate();

				if (sysdate != null) {
					obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyy").format(sysdate));
				}

				obj.setLocation(sessionStateManage.getLocation());

				// setting receipt Number
				StringBuffer receiptNo = new StringBuffer();

				if (view.getDocumentFinancialYear() != null) {
					receiptNo.append(view.getDocumentFinancialYear());
					receiptNo.append(" / ");
				}
				if (view.getCollectionDocumentNo() != null) {
					receiptNo.append(view.getCollectionDocumentNo());
				}
				obj.setReceiptNo(receiptNo.toString());

				// setting transaction Number
				StringBuffer transactionNo = new StringBuffer();
				if (view.getDocumentFinancialYear() != null) {
					transactionNo.append(view.getDocumentFinancialYear());
					transactionNo.append(" / ");
				}
				if (view.getDocumentNo() != null) {
					transactionNo.append(view.getDocumentNo());
				}
				obj.setTransactionNo(transactionNo.toString());

				obj.setDate(currentDate);
				obj.setBeneficiaryName(view.getBeneficiaryName());
				obj.setBenefeciaryBankName(view.getBeneficiaryBank());
				obj.setBenefeciaryBranchName(view.getBenefeciaryBranch());
				obj.setBenefeciaryAccountNumber(view.getBenefeciaryAccountNo());
				obj.setNoOfTransaction(new BigDecimal(noOfTransactions));
				obj.setPhoneNumber(view.getPhoneNumber());
				obj.setUserName(sessionStateManage.getUserName());
				obj.setPinNo(view.getPinNo());

				HashMap<String, String> loyaltiPoints = iPersonalRemittanceService.getloyalityPointsFromProcedure(view.getCustomerReference(), view.getDocumentDate());

				String prLtyStr1 = loyaltiPoints.get("P_LTY_STR1");
				String prLtyStr2 = loyaltiPoints.get("P_LTY_STR2");
				String prInsStr1 = loyaltiPoints.get("P_INS_STR1");
				String prInsStr2 = loyaltiPoints.get("P_INS_STR2");
				String prInsStrAr1 = loyaltiPoints.get("P_INS_STR_AR1");
				String prInsStrAr2 = loyaltiPoints.get("P_INS_STR_AR2");

				StringBuffer loyaltyPoint = new StringBuffer();
				if (!prLtyStr1.trim().equals("")) {
					loyaltyPoint.append(prLtyStr1);
				}
				if (!prLtyStr2.trim().equals("")) {
					loyaltyPoint.append("\n");
					loyaltyPoint.append(prLtyStr2);
				}
				obj.setLoyalityPointExpiring(loyaltyPoint.toString());

				StringBuffer insurence1 = new StringBuffer();
				if (!prInsStr1.trim().equals("")) {
					insurence1.append(prInsStr1);
				}
				if (!prInsStrAr1.trim().equals("")) {
					insurence1.append("\n");
					insurence1.append(prInsStrAr1);
				}
				obj.setInsurence1(insurence1.toString());

				StringBuffer insurence2 = new StringBuffer();
				if (!prInsStr2.trim().equals("")) {
					insurence2.append(prInsStr2);
				}
				if (!prInsStrAr2.trim().equals("")) {
					insurence2.append("\n");
					insurence2.append(prInsStrAr2);
				}
				obj.setInsurence2(insurence2.toString());

				// setting beneficiary Address
				StringBuffer address = new StringBuffer();
				if (view.getBeneStateName() != null) {
					address.append(view.getBeneStateName());
					address.append(",  ");
				}
				if (view.getBeneCityName() != null) {
					address.append(view.getBeneCityName());
					address.append(",  ");
				}
				if (view.getBeneDistrictName() != null) {
					address.append(view.getBeneDistrictName());
				}
				obj.setAddress(address.toString());

				// setting payment channel
				StringBuffer paymentChannel = new StringBuffer();
				if (view.getRemittanceDescription() != null) {
					paymentChannel.append(view.getRemittanceDescription());
					paymentChannel.append(" - ");
				}
				if (view.getDeliveryDescription() != null) {
					paymentChannel.append(view.getDeliveryDescription());
				}
				obj.setPaymentChannel(paymentChannel.toString());

				String currencyAndAmount = null;
				BigDecimal foreignTransationAmount = GetRound.roundBigDecimal((view.getForeignTransactionAmount()),
						foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));
				if (view.getCurrencyQuoteName() != null && foreignTransationAmount != null) {
					currencyAndAmount = view.getCurrencyQuoteName() + "     ******" + foreignTransationAmount;
				}
				obj.setCurrencyAndAmount(currencyAndAmount);

				if (view.getCurrencyQuoteName() != null && currencyQuoteName != null && view.getExchangeRateApplied() != null) {
					obj.setExchangeRate(view.getCurrencyQuoteName() + " / " + currencyQuoteName + "     " + view.getExchangeRateApplied().toString());
				}

				if (view.getLocalTransactionAmount() != null && view.getLocalTransactionCurrencyId() != null) {
					BigDecimal transationAmount = GetRound.roundBigDecimal((view.getLocalTransactionAmount()),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLocalTransactionAmount(currencyQuoteName + "     ******" + transationAmount.toString());
				}

				if (view.getLocalCommissionAmount() != null && view.getLocalTransactionCurrencyId() != null) {
					BigDecimal localCommitionAmount = GetRound.roundBigDecimal((view.getLocalCommissionAmount()),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setCommision(currencyQuoteName + "     ******" + localCommitionAmount.toString());
				}

				if (view.getLocalChargeAmount() != null && view.getLocalTransactionCurrencyId() != null) {
					BigDecimal localChargeAmount = GetRound.roundBigDecimal((view.getLocalChargeAmount()),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setOtherCharges(currencyQuoteName + "     ******" + localChargeAmount.toString());
				}

				if (view.getLocalNetTransactionAmount() != null && view.getLocalTransactionCurrencyId() != null) {
					BigDecimal netAmount = GetRound.roundBigDecimal((view.getLocalNetTransactionAmount()),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setTotalAmount(currencyQuoteName + "     ******" + netAmount.toString());
				}

				obj.setFutherInstructions(view.getInstructions());
				obj.setSourceOfIncome(view.getSourceOfIncomeDesc());
				obj.setIntermediataryBank(view.getBenefeciaryInterBank1());

				List<CollectionDetailView> collectionDetailList1 = iPersonalRemittanceService.getCollectionDetailForRemittanceReceipt(view.getCompanyId(), view.getCollectionDocumentNo(),
						view.getCollectionDocFinanceYear(), view.getCollectionDocCode());

				CollectionDetailView collectionDetailView = collectionDetailList1.get(0);

				if (collectionDetailView.getNetAmount() != null && view.getLocalTransactionCurrencyId() != null) {
					BigDecimal collectNetAmount = GetRound.roundBigDecimal((collectionDetailView.getNetAmount()),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setNetAmount(currencyQuoteName + "     ******" + collectNetAmount);
				}

				if (collectionDetailView.getPaidAmount() != null && view.getLocalTransactionCurrencyId() != null) {
					BigDecimal collectPaidAmount = GetRound.roundBigDecimal((collectionDetailView.getPaidAmount()),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setPaidAmount(currencyQuoteName + "     ******" + collectPaidAmount);
				}

				if (collectionDetailView.getRefundedAmount() != null && view.getLocalTransactionCurrencyId() != null) {
					BigDecimal collectRefundAmount = GetRound.roundBigDecimal((collectionDetailView.getRefundedAmount()),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setRefundedAmount(currencyQuoteName + "     ******" + collectRefundAmount);
				}

				obj.setSubReport(subReportPath);
				obj.setCollectionDetailList(calculateCollectionMode(view));

				// addedd new column
				BigDecimal lessLoyaltyEncash = BigDecimal.ZERO;
				BigDecimal amountPayable = BigDecimal.ZERO;
				List<CollectionPaymentDetailsView> collectionPmtDetailList = iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(view.getCompanyId(),
						view.getCollectionDocumentNo(), view.getCollectionDocFinanceYear(), view.getCollectionDocCode());
				for (CollectionPaymentDetailsView collPaymentDetailsView : collectionPmtDetailList) {
					if (collPaymentDetailsView.getCollectionMode().equalsIgnoreCase(Constants.VOCHERCODE)) {
						lessLoyaltyEncash = collPaymentDetailsView.getCollectAmount();
						amountPayable = amountPayable.add(collPaymentDetailsView.getCollectAmount());
					} else {
						amountPayable = amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}
				}
				if (lessLoyaltyEncash.compareTo(BigDecimal.ZERO) == 0) {
					obj.setLessLoyaltyEncasement(null);
				} else {
					BigDecimal loyaltyAmount = GetRound.roundBigDecimal((lessLoyaltyEncash), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLessLoyaltyEncasement(currencyQuoteName + "     ******" + loyaltyAmount);
				}

				if (amountPayable != null && currencyQuoteName != null && view.getLocalTransactionCurrencyId() != null) {
					BigDecimal payable = GetRound.roundBigDecimal((amountPayable), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setAmountPayable(currencyQuoteName + "     ******" + payable);
				}

				// obj.setSignature(view.getCustomerSignature());
				// Rabil

				// Added by Rabil
				try {
					if (view.getCustomerSignatureClob() != null) {
						obj.setSignature(view.getCustomerSignatureClob().getSubString(1, (int) view.getCustomerSignatureClob().length()));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (employeeList != null && employeeList.size() > 0) {
					try {
						Employee employee = employeeList.get(0);
						if (employee.getSignatureSpecimenClob() != null) {
							obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1, (int) employee.getSignatureSpecimenClob().length()));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				// For Company information ADDED BY VISWA --START
				List<CompanyMaster> companyMaster = iPersonalRemittanceService.getCompanyMaster(sessionStateManage.getCompanyId());
				StringBuffer engCompanyInfo = null;
				StringBuffer arabicCompanyInfo = null;
				if (companyMaster.size() > 0) {
					engCompanyInfo = new StringBuffer();
					if (companyMaster.get(0).getAddress1() != null && companyMaster.get(0).getAddress1().length() > 0) {
						engCompanyInfo = engCompanyInfo.append(companyMaster.get(0).getAddress1() + ",");
					}
					if (companyMaster.get(0).getAddress2() != null && companyMaster.get(0).getAddress2().length() > 0) {
						engCompanyInfo = engCompanyInfo.append(companyMaster.get(0).getAddress2() + ",");
					}
					if (companyMaster.get(0).getAddress3() != null && companyMaster.get(0).getAddress3().length() > 0) {
						engCompanyInfo = engCompanyInfo.append(companyMaster.get(0).getAddress3() + ",");
					}
					if (companyMaster.get(0).getRegistrationNumber() != null && companyMaster.get(0).getRegistrationNumber().length() > 0) {
						engCompanyInfo = engCompanyInfo.append("C.R. " + companyMaster.get(0).getRegistrationNumber() + ",");
					}
					if (companyMaster.get(0).getCapitalAmount() != null && companyMaster.get(0).getCapitalAmount().length() > 0) {
						engCompanyInfo = engCompanyInfo.append("Share Capital-" + companyMaster.get(0).getCapitalAmount());
					}
					obj.setEngCompanyInfo(engCompanyInfo.toString());

					arabicCompanyInfo = new StringBuffer();

					if (companyMaster.get(0).getArabicAddress1() != null && companyMaster.get(0).getArabicAddress1().length() > 0) {
						arabicCompanyInfo = arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress1());
					}
					if (companyMaster.get(0).getArabicAddress2() != null && companyMaster.get(0).getArabicAddress2().length() > 0) {
						arabicCompanyInfo = arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress2() + ",");
					}
					if (companyMaster.get(0).getArabicAddress3() != null && companyMaster.get(0).getArabicAddress3().length() > 0) {
						arabicCompanyInfo = arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress3() + ",");
					}
					if (companyMaster.get(0).getRegistrationNumber() != null && companyMaster.get(0).getRegistrationNumber().length() > 0) {
						arabicCompanyInfo = arabicCompanyInfo.append(Constants.CR + " " + companyMaster.get(0).getRegistrationNumber() + ",");
					}
					if (companyMaster.get(0).getCapitalAmount() != null && companyMaster.get(0).getCapitalAmount().length() > 0) {
						arabicCompanyInfo = arabicCompanyInfo.append(Constants.Share_Capital + " " + companyMaster.get(0).getCapitalAmount());
					}
					obj.setArabicCompanyInfo(arabicCompanyInfo.toString());
				}
				// For Company information ADDED BY VISWA --END

				if (obj.getFirstName() == null || obj.getFirstName().isEmpty()) {
					List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

					if (customerList.size() > 0) {
						CutomerDetailsView cust = customerList.get(0);
						obj.setFirstName(cust.getCustomerName());
						if (cust.getContactNumber() != null && !cust.getContactNumber().trim().equalsIgnoreCase("")) {
							obj.setMobileNo(new BigDecimal(cust.getContactNumber()));
						}
						obj.setCivilId(cust.getIdNumber());
						Date sysdate1 = cust.getIdExp();
						if (sysdate1 != null) {
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate1));
						}
					}
				}

				remittanceApplList.add(obj);
			}

			// for foreign currency Sale report
			for (RemittanceApplicationView view : fcsaleList) {

				RemittanceReportBean obj = new RemittanceReportBean();

				// For Company information ADDED BY VISWA --START
				List<CompanyMaster> companyMaster = iPersonalRemittanceService.getCompanyMaster(sessionStateManage.getCompanyId());
				StringBuffer engCompanyInfo = null;
				StringBuffer arabicCompanyInfo = null;
				if (companyMaster.size() > 0) {
					engCompanyInfo = new StringBuffer();
					if (companyMaster.get(0).getAddress1() != null && companyMaster.get(0).getAddress1().length() > 0) {
						engCompanyInfo = engCompanyInfo.append(companyMaster.get(0).getAddress1() + ",");
					}
					if (companyMaster.get(0).getAddress2() != null && companyMaster.get(0).getAddress2().length() > 0) {
						engCompanyInfo = engCompanyInfo.append(companyMaster.get(0).getAddress2() + ",");
					}
					if (companyMaster.get(0).getAddress3() != null && companyMaster.get(0).getAddress3().length() > 0) {
						engCompanyInfo = engCompanyInfo.append(companyMaster.get(0).getAddress3() + ",");
					}
					if (companyMaster.get(0).getRegistrationNumber() != null && companyMaster.get(0).getRegistrationNumber().length() > 0) {
						engCompanyInfo = engCompanyInfo.append("C.R. " + companyMaster.get(0).getRegistrationNumber() + ",");
					}
					if (companyMaster.get(0).getCapitalAmount() != null && companyMaster.get(0).getCapitalAmount().length() > 0) {
						engCompanyInfo = engCompanyInfo.append("Share Capital-" + companyMaster.get(0).getCapitalAmount());
					}
					obj.setEngCompanyInfo(engCompanyInfo.toString());

					arabicCompanyInfo = new StringBuffer();

					if (companyMaster.get(0).getArabicAddress1() != null && companyMaster.get(0).getArabicAddress1().length() > 0) {
						arabicCompanyInfo = arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress1());
					}
					if (companyMaster.get(0).getArabicAddress2() != null && companyMaster.get(0).getArabicAddress2().length() > 0) {
						arabicCompanyInfo = arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress2() + ",");
					}
					if (companyMaster.get(0).getArabicAddress3() != null && companyMaster.get(0).getArabicAddress3().length() > 0) {
						arabicCompanyInfo = arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress3() + ",");
					}
					if (companyMaster.get(0).getRegistrationNumber() != null && companyMaster.get(0).getRegistrationNumber().length() > 0) {
						arabicCompanyInfo = arabicCompanyInfo.append(Constants.CR + " " + companyMaster.get(0).getRegistrationNumber() + ",");
					}
					if (companyMaster.get(0).getCapitalAmount() != null && companyMaster.get(0).getCapitalAmount().length() > 0) {
						arabicCompanyInfo = arabicCompanyInfo.append(Constants.Share_Capital + " " + companyMaster.get(0).getCapitalAmount());
					}
					obj.setArabicCompanyInfo(arabicCompanyInfo.toString());
				}
				// For Company information ADDED BY VISWA --END

				// setting customer reference
				StringBuffer customerReff = new StringBuffer();

				if (view.getCustomerReference() != null) {
					customerReff.append(view.getCustomerReference());
					customerReff.append(" / ");
				}

				StringBuffer customerName = new StringBuffer();

				if (view.getFirstName() != null) {
					customerName.append(" ");
					customerName.append(view.getFirstName());
					customerReff.append(" ");
					customerReff.append(view.getFirstName());
				}
				if (view.getMiddleName() != null) {
					customerName.append(" ");
					customerName.append(view.getMiddleName());
					customerReff.append(" ");
					customerReff.append(view.getMiddleName());
				}
				if (view.getLastName() != null) {
					customerName.append(" ");
					customerName.append(view.getLastName());
					customerReff.append(" ");
					customerReff.append(view.getLastName());
				}

				obj.setFirstName(customerReff.toString());
				setCustomerNameForReport(customerName.toString());

				if (view.getContactNumber() != null && !view.getContactNumber().trim().equalsIgnoreCase("")) {
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}
				obj.setCivilId(view.getIdentityInt());

				Date sysdate = view.getIdentityExpiryDate();
				if (sysdate != null) {
					obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate));
				}

				HashMap<String, String> loyaltiPoints = iPersonalRemittanceService.getloyalityPointsFromProcedure(view.getCustomerReference(), view.getDocumentDate());

				String prLtyStr1 = loyaltiPoints.get("P_LTY_STR1");
				String prLtyStr2 = loyaltiPoints.get("P_LTY_STR2");
				String prInsStr1 = loyaltiPoints.get("P_INS_STR1");
				String prInsStr2 = loyaltiPoints.get("P_INS_STR2");
				String prInsStrAr1 = loyaltiPoints.get("P_INS_STR_AR1");
				String prInsStrAr2 = loyaltiPoints.get("P_INS_STR_AR2");

				StringBuffer loyaltyPoint = new StringBuffer();

				if (!prLtyStr1.trim().equals("")) {
					loyaltyPoint.append(prLtyStr1);
				}
				if (!prLtyStr2.trim().equals("")) {
					loyaltyPoint.append("\n");
					loyaltyPoint.append(prLtyStr2);
				}
				obj.setLoyalityPointExpiring(loyaltyPoint.toString());

				StringBuffer insurence = new StringBuffer();

				if (!prInsStr1.trim().equals("")) {
					insurence.append(prInsStr1);
				}
				if (prInsStrAr1.trim().equals("")) {
					insurence.append("\n");
					insurence.append(prInsStrAr1);
				}

				if (!prInsStr2.trim().equals("")) {
					insurence.append("\n");
					insurence.append(prInsStr2);
				}
				if (!prInsStrAr2.trim().equals("")) {
					insurence.append("\n");
					insurence.append(prInsStrAr2);
				}
				obj.setInsurence1(insurence.toString());

				obj.setLocation(view.getCountryBranchName());
				obj.setPhoneNumber(view.getPhoneNumber());
				obj.setDate(currentDate);
				obj.setUserName(sessionStateManage.getUserName());

				// setting receipt Number
				StringBuffer receiptNo = new StringBuffer();
				if (view.getDocumentFinancialYear() != null) {
					receiptNo.append(view.getDocumentFinancialYear());
					receiptNo.append(" / ");
				}
				if (view.getCollectionDocumentNo() != null) {
					receiptNo.append(view.getCollectionDocumentNo());
				}
				obj.setReceiptNo(receiptNo.toString());

				// setting transaction Number
				StringBuffer transactionNo = new StringBuffer();
				if (view.getDocumentFinancialYear() != null) {
					transactionNo.append(view.getDocumentFinancialYear());
					transactionNo.append(" / ");
				}
				if (view.getDocumentNo() != null) {
					transactionNo.append(view.getDocumentNo());
				}
				obj.setTransactionNo(transactionNo.toString());

				if (view.getForeignCurrencyId() != null) {
					String saleCurrency = specialCustomerDealRequestService.getCurrencyForUpdate(view.getForeignCurrencyId());
					obj.setCurrencyQuoteName(saleCurrency);
				}
				String saleCurrencyCode = null;

				if (view.getForeignCurrencyId() != null) {
					saleCurrencyCode = generalService.getCurrencyQuote(view.getForeignCurrencyId());
				}

				if (view.getForeignTransactionAmount() != null && saleCurrencyCode != null) {
					BigDecimal foreignTransationAmount = GetRound.roundBigDecimal((view.getForeignTransactionAmount()),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));
					obj.setSaleAmount(saleCurrencyCode + "     ******" + foreignTransationAmount.toString());
				}

				if (view.getLocalTransactionCurrencyId() != null && currencyQuoteName != null) {
					BigDecimal transationAmount = GetRound.roundBigDecimal((view.getLocalTransactionAmount()),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setPurchageAmount(currencyQuoteName + "     ******" + transationAmount.toString());
				}

				if (saleCurrencyCode != null && currencyQuoteName != null && view.getExchangeRateApplied() != null) {
					obj.setExchangeRate(saleCurrencyCode + " / " + currencyQuoteName + "     " + view.getExchangeRateApplied().toString());
				}

				if (view.getLocalTransactionAmount() != null && view.getLocalTransactionCurrencyId() != null && currencyQuoteName != null) {
					BigDecimal transationAmount = GetRound.roundBigDecimal((view.getLocalTransactionAmount()),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLocalTransactionAmount(currencyQuoteName + "     ******" + transationAmount.toString());
				}

				if (view.getLocalCommissionAmount() != null && view.getLocalTransactionCurrencyId() != null && currencyQuoteName != null) {
					BigDecimal localCommitionAmount = GetRound.roundBigDecimal((view.getLocalCommissionAmount()),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setCommision(currencyQuoteName + "     ******" + localCommitionAmount.toString());
				}

				/*
				 * List<PurposeOfRemittanceView> purposeOfRemittanceList =
				 * iPersonalRemittanceService
				 * .getPurposeOfRemittance(view.getDocumentNo
				 * (),view.getDocumentFinancialYear());
				 * 
				 * if(purposeOfRemittanceList!=null &&
				 * purposeOfRemittanceList.size()>0){
				 * obj.setPerposeOfRemittance(
				 * purposeOfRemittanceList.get(0).getAmiecDescription()); }
				 */
				obj.setPerposeOfRemittance(view.getPurposeOfTransaction());
				obj.setSourceOfIncome(view.getSourceOfIncomeDesc());

				List<CollectionDetailView> collectionDetailList1 = iPersonalRemittanceService.getCollectionDetailForRemittanceReceipt(view.getCompanyId(), view.getCollectionDocumentNo(),
						view.getCollectionDocFinanceYear(), view.getCollectionDocCode());
				if (collectionDetailList1.size() > 0) {
					CollectionDetailView collectionDetailView = collectionDetailList1.get(0);
					if (collectionDetailView.getNetAmount() != null && currencyQuoteName != null && view.getLocalTransactionCurrencyId() != null) {
						BigDecimal collectNetAmount = GetRound.roundBigDecimal((collectionDetailView.getNetAmount()),
								foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setNetAmount(currencyQuoteName + "     ******" + collectNetAmount);
					}
					if (collectionDetailView.getPaidAmount() != null && currencyQuoteName != null && view.getLocalTransactionCurrencyId() != null) {
						BigDecimal collectPaidAmount = GetRound.roundBigDecimal((collectionDetailView.getPaidAmount()),
								foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setPaidAmount(currencyQuoteName + "     ******" + collectPaidAmount);
					}
					if (collectionDetailView.getRefundedAmount() != null && currencyQuoteName != null && view.getLocalTransactionCurrencyId() != null) {
						BigDecimal collectRefundAmount = GetRound.roundBigDecimal((collectionDetailView.getRefundedAmount()),
								foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setRefundedAmount(currencyQuoteName + "     ******" + collectRefundAmount);
					}
					obj.setCollectionDetailList(calculateCollectionMode(view));
				}

				obj.setSubReport(subReportPath);
				obj.setUserName(sessionStateManage.getUserName());

				// addedd new column
				BigDecimal lessLoyaltyEncash = BigDecimal.ZERO;
				BigDecimal amountPayable = BigDecimal.ZERO;
				List<CollectionPaymentDetailsView> collectionPmtDetailList = iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(view.getCompanyId(),
						view.getCollectionDocumentNo(), view.getCollectionDocFinanceYear(), view.getCollectionDocCode());
				for (CollectionPaymentDetailsView collPaymentDetailsView : collectionPmtDetailList) {
					if (collPaymentDetailsView.getCollectionMode().equalsIgnoreCase(Constants.VOCHERCODE)) {
						lessLoyaltyEncash = collPaymentDetailsView.getCollectAmount();
						amountPayable = amountPayable.add(collPaymentDetailsView.getCollectAmount());
					} else {
						amountPayable = amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}
				}
				if (lessLoyaltyEncash.compareTo(BigDecimal.ZERO) == 0) {
					obj.setLessLoyaltyEncasement(null);
				} else {
					BigDecimal loyaltyAmount = GetRound.roundBigDecimal((lessLoyaltyEncash), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLessLoyaltyEncasement(currencyQuoteName + "     ******" + loyaltyAmount);
				}

				if (amountPayable != null && currencyQuoteName != null && view.getLocalTransactionCurrencyId() != null) {
					BigDecimal payable = GetRound.roundBigDecimal((amountPayable), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setAmountPayable(currencyQuoteName + "     ******" + payable);
				}

				// obj.setSignature(view.getCustomerSignature());

				try {
					if (view.getCustomerSignatureClob() != null) {
						obj.setSignature(view.getCustomerSignatureClob().getSubString(1, (int) view.getCustomerSignatureClob().length()));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (employeeList != null && employeeList.size() > 0) {
					try {
						Employee employee = employeeList.get(0);
						if (employee.getSignatureSpecimenClob() != null) {
							obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1, (int) employee.getSignatureSpecimenClob().length()));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

				if (obj.getFirstName() == null || obj.getFirstName().isEmpty()) {
					List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

					if (customerList.size() > 0) {
						CutomerDetailsView cust = customerList.get(0);
						obj.setFirstName(cust.getCustomerName());
						if (cust.getContactNumber() != null && !cust.getContactNumber().trim().equalsIgnoreCase("")) {
							obj.setMobileNo(new BigDecimal(cust.getContactNumber()));
						}
						obj.setCivilId(cust.getIdNumber());
						Date sysdate1 = cust.getIdExp();
						if (sysdate1 != null) {
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate1));
						}
					}
				}

				fcsaleAppList.add(obj);

			}

			// for Main Remittance Receipt report (Remittance Receipt and Fc
			// Sale Application)
			RemittanceReceiptSubreport remittanceObj = new RemittanceReceiptSubreport();

			remittanceObj.setWaterMarkLogoPath(waterMark);
			remittanceObj.setWaterMarkCheck(false);
			remittanceObj.setFcsaleAppList(fcsaleAppList);
			remittanceObj.setRemittanceApplList(remittanceApplList);
			remittanceObj.setSubReport(subReportPath);

			if (fcsaleAppList != null && fcsaleAppList.size() > 0) {
				remittanceObj.setFcsaleApplicationCheck(true);
			} /*
			 * else { remittanceObj.setFcsaleApplicationCheck(false); }
			 */
			if (remittanceApplList != null && remittanceApplList.size() > 0) {
				remittanceObj.setRemittanceReceiptCheck(true);
			}/*
			 * else{ remittanceObj.setRemittanceReceiptCheck(false); }
			 */

			remittanceReceiptSubreportList.add(remittanceObj);

		} else {
			RequestContext.getCurrentInstance().execute("noDataForReport.show();");
			return;
		}

	}

	public List<RemittanceReportBean> calculateCollectionMode(RemittanceApplicationView viewCollectionObj) {
		List<RemittanceReportBean> collectionDetailList = new ArrayList<RemittanceReportBean>();
		List<CollectionPaymentDetailsView> collectionPaymentDetailList = iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(viewCollectionObj.getCompanyId(),
				viewCollectionObj.getCollectionDocumentNo(), viewCollectionObj.getCollectionDocFinanceYear(), viewCollectionObj.getCollectionDocCode());

		int size = collectionPaymentDetailList.size();
		for (CollectionPaymentDetailsView viewObj : collectionPaymentDetailList) {
			RemittanceReportBean obj = new RemittanceReportBean();
			if (viewObj.getCollectionMode() != null && viewObj.getCollectionMode().equalsIgnoreCase("K")) {
				obj.setCollectionMode(viewObj.getCollectionModeDesc());
				obj.setApprovalNo(viewObj.getApprovalNo());
				obj.setTransactionId(viewObj.getTransactionId());
				obj.setKnetreceiptDateTime(viewObj.getKnetReceiptDatenTime());
				obj.setKnetBooleanCheck(true);
				if (viewObj.getCollectAmount() != null && viewCollectionObj.getLocalTransactionCurrencyId() != null) {
					BigDecimal collectAmount = GetRound.roundBigDecimal((viewObj.getCollectAmount()),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewCollectionObj.getLocalTransactionCurrencyId()));
					obj.setCollectAmount(collectAmount);
				}
			} else {
				obj.setCollectionMode(viewObj.getCollectionModeDesc());
				obj.setKnetBooleanCheck(false);
				if (viewObj.getCollectAmount() != null && viewCollectionObj.getLocalTransactionCurrencyId() != null) {
					BigDecimal collectAmount = GetRound.roundBigDecimal((viewObj.getCollectAmount()),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewCollectionObj.getLocalTransactionCurrencyId()));
					obj.setCollectAmount(collectAmount);
				}
			}
			if (size > 1) {
				obj.setDrawLine(true);
			} else {
				obj.setDrawLine(false);
			}
			collectionDetailList.add(obj);
			size = size - 1;
		}
		return collectionDetailList;
	}

	private String customerNameForReport;

	public String getCustomerNameForReport() {
		return customerNameForReport;
	}

	public void setCustomerNameForReport(String customerNameForReport) {
		this.customerNameForReport = customerNameForReport;
	}

	public void clearDetails() {
		lstknetTranxFileUploadDatatable.clear();
		totalTobeProcessed = 0;
		totalAuthAndProcessed = 0;
		totalRejected = 0;
		totalTagError = 0;
		setCardType(null);
		setSelectCardType(0);
		setIdNumber(null);
		setMainPanelRender(true);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderOldSmartCardPanel(false);

		setdCardNo1(null);
		setdCardNo2(null);
		setdCardNo3(null);
		setdCardNo4(null);
		setReTypeDCardNo1(null);
		setReTypeDCardNo2(null);
		setReTypeDCardNo3(null);
		setReTypeDCardNo4(null);
		setColBankCode(null);
		setTrnxDate(null);
		setCollDocCode(null);
		setCollDocNo(null);
		setCollDocFyr(null);

	}

	// clear customer details
	public void clearData() {

	}

	// CR Deal Year Changing from User Financial year Table
	public void fetchUserFinancialYear() {
		try {
			List<UserFinancialYear> finYearList = generalService.getDealYear(new Date());
			if (finYearList != null) {
				setFinancialYear(finYearList.get(0).getFinancialYear());
			}
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage(ne.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return;
		}
	}

	public void exitFromKnetUpload() {
		try {
			String roleNameDesc = iPersonalRemittanceService.toFetchRoleName(new BigDecimal(sessionStateManage.getRoleId()));
			String roleName = roleNameDesc.trim();
			if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCHSTAFF)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			} else if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCH_MANAGER) || roleName.equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			} else if (roleName.equalsIgnoreCase(Constants.USER_ROLE_CORPORATE)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/corporatehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return For Release trnx
	 */

	// first panel Variables
	private BigDecimal selectCard;
	private int selectCardType;
	private boolean booRenderOldSmartCardPanel = false;
	private boolean booRenderBenificaryFirstPanel = false;
	private boolean booRenderBenificarySearchPanel = false;
	private boolean mainPanelRender = false;

	private BigDecimal cardType;
	private String idNumber;
	private boolean visible;
	private String errmsg;
	private String warningMessage;

	@PostConstruct
	public void loadIdType() {
		mapcomIdentityType = icustomerRegistrationService.getAllComponentComboDataForCustomer(sessionStateManage.getLanguageId(), "I", "Identity Type");
	}

	public void showCardTypePanel() throws Exception {

		int typecard = getSelectCardType();
		if (typecard == 2) {
			setBooRenderOldSmartCardPanel(true);
			setBooRenderBenificaryFirstPanel(true);
			BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
			if (idtypeCivilIdnew != null) {
				setSelectCard(idtypeCivilIdnew);
			}
		} else if (typecard == 1) {
			fetchSmartCardIdNumber();
			if (getIdNumber() != null && getSelectCard() != null) {
				/*
				 * if(coustomerBeneficaryDTList != null &&
				 * !coustomerBeneficaryDTList.isEmpty()){
				 * coustomerBeneficaryDTList.clear(); }
				 * goFromOldSmartCardpanel();
				 */
				setBooRenderBenificaryFirstPanel(false);
				setBooRenderOldSmartCardPanel(false);
			} else {
				setSelectCardType(0);
				setBooRenderOldSmartCardPanel(false);
				RequestContext.getCurrentInstance().execute("dldInserCard.show();");
			}
		}
	}

	public void addNewCard(ViewKnetTrnxRelease viewtrnxObject) {
		System.out.println("viewtrnxObjest :" + viewtrnxObject.getCustomerRefernce());

		getLocalBankListforIndicator();
		setdCardNo1(null);
		setdCardNo2(null);
		setdCardNo3(null);
		setdCardNo4(null);
		setReTypeDCardNo1(null);
		setReTypeDCardNo2(null);
		setReTypeDCardNo3(null);
		setReTypeDCardNo4(null);
		setColBankCode(null);
		setTrnxDate(null);
		setCollDocCode(null);
		setCollDocNo(null);
		setCollDocFyr(null);

		// setTrnxDate(viewtrnxObject.getTransactionDate());
		setTrnxDateStr(viewtrnxObject.getTransactionDate());
		setCollDocCode(viewtrnxObject.getCollDocCode());
		setCollDocNo(viewtrnxObject.getCollDocNo());
		setCollDocFyr(viewtrnxObject.getCollDocfyr());

		RequestContext context1 = RequestContext.getCurrentInstance();
		context1.execute("showrecord.show();");
	}

	public void updateCardDetail() throws AMGException {
		try {
			String fullDebitCard = null;
			String reTypefullDebitCard = null;

			System.out.println("D1 : " + getdCardNo1() + "\t D2 :" + getdCardNo2() + "\t D3 :" + getdCardNo3() + "\t D4 :" + getdCardNo4());
			System.out.println("RED1 :" + getReTypeDCardNo1() + "\t RED12 :" + getReTypeDCardNo2() + "\t RD3 :" + getReTypeDCardNo3() + "\t RD4 :" + getReTypeDCardNo4());

			if (getdCardNo1() != null && getdCardNo2() != null && getdCardNo3() != null && getdCardNo4() != null) {
				fullDebitCard = getdCardNo1() + getdCardNo2() + getdCardNo3() + getdCardNo4();
			}
			if (getReTypeDCardNo1() != null && getReTypeDCardNo2() != null && getReTypeDCardNo3() != null && getReTypeDCardNo4() != null) {
				reTypefullDebitCard = getReTypeDCardNo1() + getReTypeDCardNo2() + getReTypeDCardNo3() + getReTypeDCardNo4();
			}

			System.out.println("fullDebitCard :" + fullDebitCard);
			System.out.println("reTypefullDebitCard :" + reTypefullDebitCard);
			System.out.println("Bank Code  :" + getColBankCode() + "\t customerrefno :" + customerrefno);
			if (fullDebitCard != null && reTypefullDebitCard != null && fullDebitCard.length() == 16 && reTypefullDebitCard.length() == 16 && fullDebitCard.equals(reTypefullDebitCard)) {

				List<CustomerDBCardDetailsView> list = knetUploadFileService.duplicateCustomerBanksViewCheck(customerrefno, fullDebitCard, getColBankCode());
				if (list.isEmpty()) {
					System.out.println("Insert Code here :");

					CustomerBank customerBank = new CustomerBank();

					customerBank.setCustomerReference(customerrefno);
					customerBank.setBankCode(getColBankCode());
					customerBank.setCollectionMode(Constants.COLLECTIONMODE);
					customerBank.setAuthorizedBy(sessionStateManage.getUserName());
					customerBank.setAuthorizedDate(new Date());
					customerBank.setCreatedBy(sessionStateManage.getUserName());
					customerBank.setCreatedDate(new Date());
					customerBank.setIsActive(Constants.Yes);

					if (getCustomerNo() != null) {
						Customer customer = new Customer();
						customer.setCustomerId(getCustomerNo());
						customerBank.setFsCustomer(customer);
					}

					customerBank.setDebitCard(encryptionDescriptionService.getENCrypted(Constants.EncriptionKey, fullDebitCard));
					customerBank.setDebitCardName(customerFullName);

					customerBank.setBankPrefix(fullDebitCard.substring(0, 6));
					customerBank.setBankSuffix(fullDebitCard.substring(12));

					List<ViewBankDetails> lstViewBankDetails = icustomerBankService.getChequeBnakIdFromView(getColBankCode());
					if (lstViewBankDetails != null && lstViewBankDetails.size() > 0) {
						ViewBankDetails viewBankDetails = lstViewBankDetails.get(0);
						BankMaster bankMaster = new BankMaster();
						bankMaster.setBankId(viewBankDetails.getChequeBankId());
						customerBank.setFsBankMaster(bankMaster);
					}

					System.out.println("collDocNo :" + collDocNo + "\t trnxDate :" + trnxDate + "\t collDocCode :" + collDocCode + "\t collDocNo :" + collDocNo + "\t Email :" + toEmail);
					// String str = trnxDate.toString().substring(0, 10);
					// System.out.println("str :"+trnxDate);

					// DateFormat formatter = new
					// SimpleDateFormat("dd/MM/yyyy");
					// / String today = formatter.format(trnxDate);
					// System.out.println("Today : " +
					// today+"\t trnxDateStr :"+trnxDateStr);

					icustomerBankService.save(customerBank);
					String errMsg = knetUploadFileService.callKnetReleasePro(trnxDateStr, collDocCode, collDocFyr, collDocNo, sessionStateManage.getUserName());

					if (errMsg == null) {

						generateReceiptReport(collDocNo, collDocFyr, getToEmail(), customerFullName);
						RequestContext.getCurrentInstance().execute("save.show();");

					} else {
						setErrorMessage(errMsg);
						RequestContext.getCurrentInstance().execute("errorPage.show();");
						return;
					}

				} else {
					setErrorMessage("The debit card is already registered before");
					RequestContext.getCurrentInstance().execute("errorPage.show();");
					return;
				}

			} else {
				setErrorMessage("Please enter correct card number and length of the card number should be 16 digit");
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return;
		}
	}

	public void exit() {
		clearDetails();

	}

	/** TO STORE THE NEW BENIFICARY CUSTOMER DETAILS TO CUSTOMER BANK **/
	/*
	 * public void saveCustomerDetailsToCustomerBank() {
	 * 
	 * List<CustomerDebitCardDatatable> custDebitDataTableList
	 * =getCustDebitDataTableList(); if(custDebitDataTableList!=null &&
	 * custDebitDataTableList.size()!=0) {
	 * 
	 * // SAVE ALL LIST List<CustomerBank> lstCustBank = new
	 * ArrayList<CustomerBank>();
	 * 
	 * for(CustomerDebitCardDatatable customerDebitCardDatatable
	 * :custDebitDataTableList) { CustomerBank customerBank = new
	 * CustomerBank();
	 * 
	 * customerBank.setCustomerBankId(customerDebitCardDatatable.getCustomerBankPkId
	 * ());
	 * 
	 * customerBank.setCollectionMode(Constants.COLLECTIONMODE);
	 * 
	 * BankMaster bankMaster = new BankMaster();
	 * bankMaster.setBankId(customerDebitCardDatatable.getBankId());
	 * customerBank.setFsBankMaster(bankMaster);
	 * 
	 * customerBank.setBankCode(customerDebitCardDatatable.getColBankCode()); //
	 * this is fixed//generalService.getBankCode(getColBankid()));
	 * 
	 * if(getPkCustomerId() != null){ Customer customer = new Customer();
	 * customer.setCustomerId(getPkCustomerId());
	 * customerBank.setFsCustomer(customer); }
	 * 
	 * if(customerDebitCardDatatable.getColAuthorizedby() != null){
	 * customerBank.
	 * setAuthorizedBy(customerDebitCardDatatable.getColAuthorizedby());
	 * customerBank.setAuthorizedDate(new Date());
	 * customerBank.setPassword(getCyberPassword()); }
	 * 
	 * customerBank.setDebitCard(encryptionDescriptionService.getENCrypted(Constants
	 * .EncriptionKey, customerDebitCardDatatable.getColCardNo().toString()));
	 * customerBank
	 * .setDebitCardName(customerDebitCardDatatable.getColNameofCard());
	 * customerBank.setIsActive(Constants.Yes);
	 * 
	 * if(customerDebitCardDatatable.getCustomerBankPkId() != null){
	 * customerBank.setCreatedBy(customerDebitCardDatatable.getCreatedBy());
	 * customerBank.setCreatedDate(customerDebitCardDatatable.getCreatedDate());
	 * customerBank.setModifiedBy(sessionStateManage.getUserName());
	 * customerBank.setModifiedDate(new Date()); }else{
	 * customerBank.setCreatedBy(sessionStateManage.getUserName());
	 * customerBank.setCreatedDate(new Date()); }
	 * 
	 * customerBank.setBankPrefix(customerDebitCardDatatable.getBankPrefix());
	 * customerBank.setBankSuffix(customerDebitCardDatatable.getBankSuffix());
	 * 
	 * customerBank.setCustomerReference(iglTransactionForADocument.
	 * getCustomeReference(getPkCustomerId())); //
	 * customerBank.setModifiedBy(null); // customerBank.setModifiedDate(null);
	 * log.info("PK CUSTOMER BANK " +
	 * customerDebitCardDatatable.getCustomerBankPkId());
	 * 
	 * lstCustBank.add(customerBank);
	 * //icustomerBankService.saveOrUpdate(customerBank); //
	 * RequestContext.getCurrentInstance().execute("locbankid.show();"); }
	 * 
	 * icustomerBankService.saveAllDebitCardsBanks(lstCustBank); }
	 * 
	 * }
	 */

	// to get the local bank list or customer bank list
	public void getLocalBankListforIndicator() {
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<ViewBankDetails> lstofBank = new ArrayList<ViewBankDetails>();
		bankMasterList.clear();
		localbankList = generalService.getLocalBankListFromView(sessionStateManage.getCountryId());
		if (localbankList.size() > 0) {
			bankMasterList.addAll(localbankList);
		}
		if (bankMasterList.size() != 0) {
			for (ViewBankDetails lstBank : bankMasterList) {
				if (!duplicateCheck.contains(lstBank.getChequeBankId())) {
					duplicateCheck.add(lstBank.getChequeBankId());
					lstofBank.add(lstBank);
				}
			}
		}
		bankMasterList.clear();
		bankMasterList.addAll(lstofBank);

	}

	// on click of go button
	public void onClickGoButton() throws Exception {
		if (customerDetailsList != null || !customerDetailsList.isEmpty()) {
			customerDetailsList.clear();
		}

		goFromOldSmartCardpanel();

	}

	// first method after go clicked to fetch all customer details
	public void goFromOldSmartCardpanel() throws ParseException {

		log.info("Entering into goFromOldSmartCardpanel method");

		if (getIdNumber() != null && !getIdNumber().equalsIgnoreCase("")) {

			if (getSelectCard() != null) {
				customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber().toUpperCase(), getSelectCard());
				if (customerDetailsList != null && !customerDetailsList.isEmpty()) {
					getCustomerDetails();
					if (getCustomerrefno() != null) {
						System.out.println("getCustomerReference :" + getCustomerrefno());
						setListOnHoldKnetTrnx(knetUploadFileService.getKnetTrnxOnHoldListfromView(getCustomerrefno()));
					}

					setBooRenderBenificarySearchPanel(true);
				}
			}

		} else {
			RequestContext.getCurrentInstance().execute("idNumbernotenter.show();");
		}

		log.info("Exit into goFromOldSmartCardpanel method");
	}

	// to fetch all Customer details
	public void getCustomerDetails() {
		clearCustomerDetails();

		try {
			if (customerDetailsList.size() != 0) {

				CustomerIdProof customerDetails = customerDetailsList.get(0);

				setSelectCard(customerDetails.getFsBizComponentDataByIdentityTypeId().getComponentDataId());
				setIdNumber(customerDetails.getIdentityInt());
				setCustomerName(customerDetails.getFsCustomer().getFirstName());
				setCustomerCrNumber(customerDetails.getFsCustomer().getCrNo() == null ? "" : customerDetails.getFsCustomer().getCrNo());
				setCustomerNo(customerDetails.getFsCustomer().getCustomerId());
				setCustomerrefno(customerDetails.getFsCustomer().getCustomerReference());
				setFirstName(customerDetails.getFsCustomer().getFirstName());
				setSecondName(customerDetails.getFsCustomer().getMiddleName());
				setThirdName(customerDetails.getFsCustomer().getLastName());
				setCustomerFullName(nullCheck(getFirstName()) + " " + nullCheck(getSecondName()) + " " + nullCheck(getThirdName()));
				setCustomerLocalFullName(nullCheck(customerDetails.getFsCustomer().getFirstNameLocal()) + " " + nullCheck(customerDetails.getFsCustomer().getMiddleNameLocal()) + " "
						+ nullCheck(customerDetails.getFsCustomer().getLastNameLocal()));
				setLoyaltyPoints(iPersonalRemittanceService.getLoyaltyPointFromFunction(sessionStateManage.getCountryId(), getCustomerrefno()));
				System.out.println("Loyalty Points :" + customerDetails.getFsCustomer().getLoyaltyPoints());
				setToEmail(customerDetails.getFsCustomer().getEmail());
			}
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

		log.info("Exit into getCustomerDetails method ");
	}

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	// clear All Customer Details
	public void clearCustomerDetails() {
		setCustomerName(null);
		setCustomerCrNumber(null);
		setCustomerNo(null);
		setCustomerrefno(null);
		setFirstName(null);
		setSecondName(null);
		setThirdName(null);
		setCustomerFullName(null);
		setCustomerLocalFullName(null);
		setCustomerIsActive(null);
		setCustomerExpDate(null);
		setCustomerTypeId(null);
		setCustomerType(null);
		setCustomerExpireDateMsg(null);
		setNationality(null);
		setNationalityName(null);
		setDateOfBrith(null);
		setCountryCode(null);
		setMcountryCode(null);
		setOccupation(null);

		setLoyaltyPoints(null);
	}

	// All Variables Based on Panels
	// customer details variables

	private String customerCrNumber;
	private BigDecimal customerNo;
	private BigDecimal customerrefno;
	private String firstName;
	private String secondName;
	private String thirdName;
	private String customerFullName;
	private String customerLocalFullName;
	private String customerIsActive;
	private Date customerExpDate;
	private String customerExpireDateMsg;
	private String customerType;
	private BigDecimal customerTypeId;
	private BigDecimal nationality;
	private BigDecimal nationalityName;
	private Date dateOfBrith;
	private String birthdate;
	private String countryCode;
	private String mcountryCode;
	private String occupation;
	// Added by Rabil on 11/04/2016
	private BigDecimal loyaltyPoints;

	public void fetchSmartCardIdNumber() throws ParseException {
		if (sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			smartCardDisplay(ipAddress, "8085", "M", "test");
		}
	}

	public String smartCardDisplay(String host, String prdPort, String requestType, String env) throws ParseException {
		StringBuffer sb = new StringBuffer();
		StringBuffer urlBuffer = new StringBuffer();
		String appender = "?";
		String ampersand = "&";
		String equals = "=";
		String colon = ":";
		String rootContext = "/KwtSmartCard/SmartCartServ"; // KwtSmartCard/smartcard
		if (env.equalsIgnoreCase("test")) {
			urlBuffer.append("http://").append(host).append(colon).append(prdPort).append(rootContext).append(appender);
		} else if (env.equalsIgnoreCase("live")) {
			urlBuffer.append("https://").append(host);
			if (prdPort != null && prdPort.length() > 0) {
				urlBuffer.append(colon).append(prdPort);
			}
			urlBuffer.append(rootContext).append(appender);
		}
		urlBuffer.append("type").append(equals).append("M").append(ampersand);
		HttpURLConnection testyc = null;
		HttpsURLConnection prdyc = null;
		try {
			URL knetRequest = new URL(urlBuffer.toString());

			BufferedReader in = null;
			if (env.equalsIgnoreCase("test")) {
				testyc = (HttpURLConnection) knetRequest.openConnection();
				in = new BufferedReader(new InputStreamReader(testyc.getInputStream()));
			} else if (env.equalsIgnoreCase("live")) {
				prdyc = (HttpsURLConnection) knetRequest.openConnection();
				in = new BufferedReader(new InputStreamReader(prdyc.getInputStream()));
			}
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine + "##");
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (testyc != null) {
				testyc.disconnect();
			}
			if (prdyc != null) {
				testyc.disconnect();
			}
		}
		String[] str = sb.toString().split("#");
		if (str.length > 1) {
			for (int i = 0; i < str.length; i++) {
				String string = str[i];
				if (i == 8) {
					System.out.println("Civil Id ");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setIdNumber(part2);
					setSelectCard(generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
				}
			}
		} else {
			setIdNumber(null);
			setSelectCard(null);
			RequestContext.getCurrentInstance().execute("dldInserCard.show();");
		}
		return sb.toString();
	}

	/*
	 * // first method after go clicked to fetch all customer details public
	 * void goFromOldSmartCardpanel() throws ParseException {
	 * 
	 * log.info("Entering into goFromOldSmartCardpanel method");
	 * 
	 * if(getIdNumber() != null && !getIdNumber().equalsIgnoreCase("")){ //
	 * clearing data table resetFilters("form1:dataTable");
	 * 
	 * if(coustomerBeneficaryDTList != null ||
	 * !coustomerBeneficaryDTList.isEmpty()){ coustomerBeneficaryDTList.clear();
	 * }
	 * 
	 * if(getSelectCard() != null){
	 * 
	 * //customerDetailsList =
	 * iPersonalRemittanceService.getCustomerDetails(getIdNumber
	 * ().toUpperCase(), getSelectCard());
	 * 
	 * customerDetailsList =
	 * iPersonalRemittanceService.getCustomerDetailsActiveRec
	 * (getIdNumber().toUpperCase(), getSelectCard());
	 * 
	 * if(customerDetailsList.size() != 0){ if(getBooRenderCorporateBack()){
	 * CustomerIdProof customerDetails = customerDetailsList.get(0);
	 * getCustomerDetails(); populateBeneFiciaryCountry();
	 * backFromBenificaryStatusPanel(); clearData(); }else{ CustomerIdProof
	 * customerDetails = customerDetailsList.get(0);
	 * fetchCustomerBeneficiaryDetails(customerDetails); } }else{ // comparing
	 * with civil id BigDecimal identityTpeIds =
	 * generalService.getComponentId(Constants.CIVILID,
	 * sessionStateManage.getLanguageId
	 * ()).getFsBizComponentData().getComponentDataId();
	 * 
	 * if(getSelectCard().compareTo(identityTpeIds)!=0){ //customerDetailsList =
	 * iPersonalRemittanceService.getCustomerDetails(getIdNumber(),
	 * identityTpeIds); customerDetailsList =
	 * iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(),
	 * identityTpeIds); if(customerDetailsList.size() != 0){ CustomerIdProof
	 * customerDetails = customerDetailsList.get(0);
	 * fetchCustomerBeneficiaryDetails(customerDetails); }else{
	 * if(getBooRenderCorporateBack()){ BigDecimal
	 * idNumber=generalService.getComponentId
	 * (Constants.COMPANY_REGISTRATION_DOC,
	 * sessionStateManage.getLanguageId()).getFsBizComponentData
	 * ().getComponentDataId(); //customerDetailsList =
	 * iPersonalRemittanceService.getCustomerDetails(getIdNumber(), idNumber);
	 * customerDetailsList =
	 * iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(),
	 * idNumber); if(customerDetailsList.size() != 0){ CustomerIdProof
	 * customerDetails = customerDetailsList.get(0); getCustomerDetails();
	 * populateBeneFiciaryCountry(); backFromBenificaryStatusPanel();
	 * clearData(); }else{ // failed all conditions setCardType(null);
	 * setBooRenderBenificaryFirstPanel(true);
	 * RequestContext.getCurrentInstance().execute("idNotFound.show();"); }
	 * }else{ // comparing with civil id new BigDecimal idtypeCivilIdnew =
	 * generalService.getComponentId(Constants.CIVIL_ID_NEW,
	 * sessionStateManage.getLanguageId
	 * ()).getFsBizComponentData().getComponentDataId(); //customerDetailsList =
	 * iPersonalRemittanceService.getCustomerDetails(getIdNumber(),
	 * idtypeCivilIdnew); customerDetailsList =
	 * iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(),
	 * idtypeCivilIdnew); if(customerDetailsList.size() != 0){ CustomerIdProof
	 * customerDetails = customerDetailsList.get(0);
	 * fetchCustomerBeneficiaryDetails(customerDetails); }else{ // failed all
	 * conditions setCardType(null); //setIdNumber(null);
	 * setBooRenderBenificaryFirstPanel(true);
	 * RequestContext.getCurrentInstance().execute("idNotFound.show();"); } } }
	 * }else{ // comparing with civil id new BigDecimal idtypeCivilIdnew =
	 * generalService.getComponentId(Constants.CIVIL_ID_NEW,
	 * sessionStateManage.getLanguageId
	 * ()).getFsBizComponentData().getComponentDataId(); //customerDetailsList =
	 * iPersonalRemittanceService.getCustomerDetails(getIdNumber(),
	 * idtypeCivilIdnew); customerDetailsList =
	 * iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(),
	 * idtypeCivilIdnew); if(customerDetailsList.size() != 0){ CustomerIdProof
	 * customerDetails = customerDetailsList.get(0);
	 * fetchCustomerBeneficiaryDetails(customerDetails); }else{ // failed all
	 * conditions setCardType(null); //setIdNumber(null);
	 * setBooRenderBenificaryFirstPanel(true);
	 * RequestContext.getCurrentInstance().execute("idNotFound.show();"); }
	 * 
	 * } }
	 * 
	 * }
	 * 
	 * }else{
	 * RequestContext.getCurrentInstance().execute("idNumbernotenter.show();");
	 * }
	 * 
	 * log.info("Exit into goFromOldSmartCardpanel method"); }
	 */

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	public Date getTrnxDate() {
		return trnxDate;
	}

	public void setTrnxDate(Date trnxDate) {
		this.trnxDate = trnxDate;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getMerchantTranckId() {
		return merchantTranckId;
	}

	public void setMerchantTranckId(String merchantTranckId) {
		this.merchantTranckId = merchantTranckId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public ICustomerRegistrationBranchService<T> getIcustomerRegistrationService() {
		return icustomerRegistrationService;
	}

	public void setIcustomerRegistrationService(ICustomerRegistrationBranchService<T> icustomerRegistrationService) {
		this.icustomerRegistrationService = icustomerRegistrationService;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public IPersonalRemittanceService getiPersonalRemittanceService() {
		return iPersonalRemittanceService;
	}

	public void setiPersonalRemittanceService(IPersonalRemittanceService iPersonalRemittanceService) {
		this.iPersonalRemittanceService = iPersonalRemittanceService;
	}

	public IWesternUnionService getWesternUnionService() {
		return westernUnionService;
	}

	public void setWesternUnionService(IWesternUnionService westernUnionService) {
		this.westernUnionService = westernUnionService;
	}

	public IBeneCountryServices<T> getIbeneCountryServices() {
		return ibeneCountryServices;
	}

	public void setIbeneCountryServices(IBeneCountryServices<T> ibeneCountryServices) {
		this.ibeneCountryServices = ibeneCountryServices;
	}

	public ISourceOfIncomeService getSourceofIncomeservice() {
		return sourceofIncomeservice;
	}

	public void setSourceofIncomeservice(ISourceOfIncomeService sourceofIncomeservice) {
		this.sourceofIncomeservice = sourceofIncomeservice;
	}

	public BeneficiaryEditBean getBeneficiaryEditBean() {
		return beneficiaryEditBean;
	}

	public void setBeneficiaryEditBean(BeneficiaryEditBean beneficiaryEditBean) {
		this.beneficiaryEditBean = beneficiaryEditBean;
	}

	public IServiceGroupMasterService getServiceGroupMasterService() {
		return serviceGroupMasterService;
	}

	public void setServiceGroupMasterService(IServiceGroupMasterService serviceGroupMasterService) {
		this.serviceGroupMasterService = serviceGroupMasterService;
	}

	public IBeneficaryCreation getBeneficaryCreation() {
		return beneficaryCreation;
	}

	public void setBeneficaryCreation(IBeneficaryCreation beneficaryCreation) {
		this.beneficaryCreation = beneficaryCreation;
	}

	public IForeignCurrencyPurchaseService<T> getForeignCurrencyPurchaseService() {
		return foreignCurrencyPurchaseService;
	}

	public void setForeignCurrencyPurchaseService(IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService) {
		this.foreignCurrencyPurchaseService = foreignCurrencyPurchaseService;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public ServiceCodeMasterService getServiceMasterService() {
		return serviceMasterService;
	}

	public void setServiceMasterService(ServiceCodeMasterService serviceMasterService) {
		this.serviceMasterService = serviceMasterService;
	}

	public IPaymentService getIpaymentService() {
		return ipaymentService;
	}

	public void setIpaymentService(IPaymentService ipaymentService) {
		this.ipaymentService = ipaymentService;
	}

	public ForeignLocalCurrencyDenominationService<T> getForeignLocalCurrencyDenominationService() {
		return foreignLocalCurrencyDenominationService;
	}

	public void setForeignLocalCurrencyDenominationService(ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService) {
		this.foreignLocalCurrencyDenominationService = foreignLocalCurrencyDenominationService;
	}

	public SessionStateManage getSessionStateManage() {
		return sessionStateManage;
	}

	public void setSessionStateManage(SessionStateManage sessionStateManage) {
		this.sessionStateManage = sessionStateManage;
	}

	public Map<BigDecimal, String> getMapcomIdentityType() {
		return mapcomIdentityType;
	}

	public void setMapcomIdentityType(Map<BigDecimal, String> mapcomIdentityType) {
		this.mapcomIdentityType = mapcomIdentityType;
	}

	public List<CustomerIdProof> getCustomerDetailsList() {
		return customerDetailsList;
	}

	public void setCustomerDetailsList(List<CustomerIdProof> customerDetailsList) {
		this.customerDetailsList = customerDetailsList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(BigDecimal financialYear) {
		this.financialYear = financialYear;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserField1() {
		return userField1;
	}

	public void setUserField1(String userField1) {
		this.userField1 = userField1;
	}

	public String getUserField2() {
		return userField2;
	}

	public void setUserField2(String userField2) {
		this.userField2 = userField2;
	}

	public String getUserField3() {
		return userField3;
	}

	public void setUserField3(String userField3) {
		this.userField3 = userField3;
	}

	public String getUserField4() {
		return userField4;
	}

	public void setUserField4(String userField4) {
		this.userField4 = userField4;
	}

	public String getUserField5() {
		return userField5;
	}

	public void setUserField5(String userField5) {
		this.userField5 = userField5;
	}

	public List<KnetTranxFileUploadDatatable> getLstknetTranxFileUploadDatatable() {
		return lstknetTranxFileUploadDatatable;
	}

	public void setLstknetTranxFileUploadDatatable(List<KnetTranxFileUploadDatatable> lstknetTranxFileUploadDatatable) {
		this.lstknetTranxFileUploadDatatable = lstknetTranxFileUploadDatatable;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public KnetTranxFileUploadDatatable getLstSelectedKnetranxFileUpload() {
		return lstSelectedKnetranxFileUpload;
	}

	public void setLstSelectedKnetranxFileUpload(KnetTranxFileUploadDatatable lstSelectedKnetranxFileUpload) {
		this.lstSelectedKnetranxFileUpload = lstSelectedKnetranxFileUpload;
	}

	public int getTotalTobeProcessed() {
		return totalTobeProcessed;
	}

	public void setTotalTobeProcessed(int totalTobeProcessed) {
		this.totalTobeProcessed = totalTobeProcessed;
	}

	public int getTotalAuthAndProcessed() {
		return totalAuthAndProcessed;
	}

	public void setTotalAuthAndProcessed(int totalAuthAndProcessed) {
		this.totalAuthAndProcessed = totalAuthAndProcessed;
	}

	public int getTotalRejected() {
		return totalRejected;
	}

	public void setTotalRejected(int totalRejected) {
		this.totalRejected = totalRejected;
	}

	public int getTotalTagError() {
		return totalTagError;
	}

	public void setTotalTagError(int totalTagError) {
		this.totalTagError = totalTagError;
	}

	public IKnetUploadFileService getKnetUploadFileService() {
		return knetUploadFileService;
	}

	public void setKnetUploadFileService(IKnetUploadFileService knetUploadFileService) {
		this.knetUploadFileService = knetUploadFileService;
	}

	public String getTrnxStatus() {
		return trnxStatus;
	}

	public void setTrnxStatus(String trnxStatus) {
		this.trnxStatus = trnxStatus;
	}

	public BigDecimal getSelectCard() {
		return selectCard;
	}

	public void setSelectCard(BigDecimal selectCard) {
		this.selectCard = selectCard;
	}

	public int getSelectCardType() {
		return selectCardType;
	}

	public void setSelectCardType(int selectCardType) {
		this.selectCardType = selectCardType;
	}

	public BigDecimal getCardType() {
		return cardType;
	}

	public void setCardType(BigDecimal cardType) {
		this.cardType = cardType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

	public boolean isBooRenderOldSmartCardPanel() {
		return booRenderOldSmartCardPanel;
	}

	public void setBooRenderOldSmartCardPanel(boolean booRenderOldSmartCardPanel) {
		this.booRenderOldSmartCardPanel = booRenderOldSmartCardPanel;
	}

	public boolean isMainPanelRender() {
		return mainPanelRender;
	}

	public void setMainPanelRender(boolean mainPanelRender) {
		this.mainPanelRender = mainPanelRender;
	}

	public boolean isBooRenderBenificaryFirstPanel() {
		return booRenderBenificaryFirstPanel;
	}

	public void setBooRenderBenificaryFirstPanel(boolean booRenderBenificaryFirstPanel) {
		this.booRenderBenificaryFirstPanel = booRenderBenificaryFirstPanel;
	}

	public boolean isBooRenderBenificarySearchPanel() {
		return booRenderBenificarySearchPanel;
	}

	public void setBooRenderBenificarySearchPanel(boolean booRenderBenificarySearchPanel) {
		this.booRenderBenificarySearchPanel = booRenderBenificarySearchPanel;
	}

	public ApllicationMailer1 getMailService() {
		return mailService;
	}

	public void setMailService(ApllicationMailer1 mailService) {
		this.mailService = mailService;
	}

	public ISpecialCustomerDealRequestService<T> getSpecialCustomerDealRequestService() {
		return specialCustomerDealRequestService;
	}

	public void setSpecialCustomerDealRequestService(ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService) {
		this.specialCustomerDealRequestService = specialCustomerDealRequestService;
	}

	public IMiscellaneousReceiptPaymentService<T> getMiscellaneousReceiptPaymentService() {
		return miscellaneousReceiptPaymentService;
	}

	public void setMiscellaneousReceiptPaymentService(IMiscellaneousReceiptPaymentService<T> miscellaneousReceiptPaymentService) {
		this.miscellaneousReceiptPaymentService = miscellaneousReceiptPaymentService;
	}

	public LoginLogoutHistoryUtil<T> getLoginLogoutHistoryUtil() {
		return loginLogoutHistoryUtil;
	}

	public void setLoginLogoutHistoryUtil(LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil) {
		this.loginLogoutHistoryUtil = loginLogoutHistoryUtil;
	}

	public List<KnetTranxFileUploadDatatable> getLstknetTranxFileUploadDatatableCopy() {
		return lstknetTranxFileUploadDatatableCopy;
	}

	public void setLstknetTranxFileUploadDatatableCopy(List<KnetTranxFileUploadDatatable> lstknetTranxFileUploadDatatableCopy) {
		this.lstknetTranxFileUploadDatatableCopy = lstknetTranxFileUploadDatatableCopy;
	}

	public List<RemittanceReceiptSubreport> getRemittanceReceiptSubreportList() {
		return remittanceReceiptSubreportList;
	}

	public void setRemittanceReceiptSubreportList(List<RemittanceReceiptSubreport> remittanceReceiptSubreportList) {
		this.remittanceReceiptSubreportList = remittanceReceiptSubreportList;
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}

	public List<CollectionDetailView> getCollectionViewList() {
		return collectionViewList;
	}

	public void setCollectionViewList(List<CollectionDetailView> collectionViewList) {
		this.collectionViewList = collectionViewList;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getCustomerCrNumber() {
		return customerCrNumber;
	}

	public void setCustomerCrNumber(String customerCrNumber) {
		this.customerCrNumber = customerCrNumber;
	}

	public BigDecimal getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(BigDecimal customerNo) {
		this.customerNo = customerNo;
	}

	public BigDecimal getCustomerrefno() {
		return customerrefno;
	}

	public void setCustomerrefno(BigDecimal customerrefno) {
		this.customerrefno = customerrefno;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public String getCustomerLocalFullName() {
		return customerLocalFullName;
	}

	public void setCustomerLocalFullName(String customerLocalFullName) {
		this.customerLocalFullName = customerLocalFullName;
	}

	public String getCustomerIsActive() {
		return customerIsActive;
	}

	public void setCustomerIsActive(String customerIsActive) {
		this.customerIsActive = customerIsActive;
	}

	public Date getCustomerExpDate() {
		return customerExpDate;
	}

	public void setCustomerExpDate(Date customerExpDate) {
		this.customerExpDate = customerExpDate;
	}

	public String getCustomerExpireDateMsg() {
		return customerExpireDateMsg;
	}

	public void setCustomerExpireDateMsg(String customerExpireDateMsg) {
		this.customerExpireDateMsg = customerExpireDateMsg;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public BigDecimal getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(BigDecimal customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public BigDecimal getNationality() {
		return nationality;
	}

	public void setNationality(BigDecimal nationality) {
		this.nationality = nationality;
	}

	public BigDecimal getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(BigDecimal nationalityName) {
		this.nationalityName = nationalityName;
	}

	public Date getDateOfBrith() {
		return dateOfBrith;
	}

	public void setDateOfBrith(Date dateOfBrith) {
		this.dateOfBrith = dateOfBrith;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getMcountryCode() {
		return mcountryCode;
	}

	public void setMcountryCode(String mcountryCode) {
		this.mcountryCode = mcountryCode;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public BigDecimal getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public List<ViewKnetTrnxRelease> getListOnHoldKnetTrnx() {
		return listOnHoldKnetTrnx;
	}

	public void setListOnHoldKnetTrnx(List<ViewKnetTrnxRelease> listOnHoldKnetTrnx) {
		this.listOnHoldKnetTrnx = listOnHoldKnetTrnx;
	}

	public String getColBankCode() {
		return colBankCode;
	}

	public void setColBankCode(String colBankCode) {
		this.colBankCode = colBankCode;
	}

	public List<ViewBankDetails> getBankMasterList() {
		return bankMasterList;
	}

	public void setBankMasterList(List<ViewBankDetails> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	public String getdCardNo1() {
		return dCardNo1;
	}

	public void setdCardNo1(String dCardNo1) {
		this.dCardNo1 = dCardNo1;
	}

	public String getdCardNo2() {
		return dCardNo2;
	}

	public void setdCardNo2(String dCardNo2) {
		this.dCardNo2 = dCardNo2;
	}

	public String getdCardNo3() {
		return dCardNo3;
	}

	public void setdCardNo3(String dCardNo3) {
		this.dCardNo3 = dCardNo3;
	}

	public String getdCardNo4() {
		return dCardNo4;
	}

	public void setdCardNo4(String dCardNo4) {
		this.dCardNo4 = dCardNo4;
	}

	public String getReTypeDCardNo1() {
		return reTypeDCardNo1;
	}

	public void setReTypeDCardNo1(String reTypeDCardNo1) {
		this.reTypeDCardNo1 = reTypeDCardNo1;
	}

	public String getReTypeDCardNo2() {
		return reTypeDCardNo2;
	}

	public void setReTypeDCardNo2(String reTypeDCardNo2) {
		this.reTypeDCardNo2 = reTypeDCardNo2;
	}

	public String getReTypeDCardNo3() {
		return reTypeDCardNo3;
	}

	public void setReTypeDCardNo3(String reTypeDCardNo3) {
		this.reTypeDCardNo3 = reTypeDCardNo3;
	}

	public String getReTypeDCardNo4() {
		return reTypeDCardNo4;
	}

	public void setReTypeDCardNo4(String reTypeDCardNo4) {
		this.reTypeDCardNo4 = reTypeDCardNo4;
	}

	public BigDecimal getCollDocNo() {
		return collDocNo;
	}

	public void setCollDocNo(BigDecimal collDocNo) {
		this.collDocNo = collDocNo;
	}

	public BigDecimal getCollDocCode() {
		return collDocCode;
	}

	public void setCollDocCode(BigDecimal collDocCode) {
		this.collDocCode = collDocCode;
	}

	public BigDecimal getCollDocFyr() {
		return collDocFyr;
	}

	public void setCollDocFyr(BigDecimal collDocFyr) {
		this.collDocFyr = collDocFyr;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getTrnxDateStr() {
		return trnxDateStr;
	}

	public void setTrnxDateStr(String trnxDateStr) {
		this.trnxDateStr = trnxDateStr;
	}

	// getters and setters

}

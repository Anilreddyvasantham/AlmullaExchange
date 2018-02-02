package com.amg.exchange.util;

import java.math.BigDecimal;
import java.util.HashMap;

public final class Constants {

	// For Masters
	public static String Yes;
	public static String No;
	public static String U;
	public static String D;
	public static String A;
	public static String ALL;
	public static String SPECIFIC;
	public static String ACTIVE;
	public static String IN_ACTIVE;
	public static String DEACTIVATE;
	public static String ACTIVATE;
	public static String DELETE;
	public static String REMOVE;
	public static String DOCUMENT_NUMBER;
	public static String FILE_UPLOAD;
	public static String DUPLICATE;
	public static String PENDING_FOR_APPROVE;
	public static String TRUE;
	public static String FALSE;
	public static String KUWAIT;
	public static String OMAN;
	// For Jv Process
	public static String JV_REASON;
	
	public static String PARTIAL;
	public static String ARMS;
	public static String KIOSK;
	

	// for Country
	public static  String BAHRAIN_ALPHA_TWO_CODE;
	public static  String KUWAIT_ALPHA_TWO_CODE;
	public static  String OMAN_ALPHA_TWO_CODE;
	public static  String KUWAIT_QUOTE_NAME; 


	// For Personal Remittance
	public static String Individual;
	public static String NonIndividual;
	public static String Corporate;
	public static String Dependant;
	public static String DEPENDANT;
	
	public static String IndividualFullName;
	public static String NonIndividualFullName;
	public static String CorporateFullName;
	
	public static String COLLECTIONMODE;
	public static String RP;
	public static String FC_SALE_REMIT;
	public static String SELF;
	public static String OTHERS;
	public static String Remittance;
	public static String FCSale;
	public static String KNET;

	public static String UP;
	public static String DOWN;
	public static String USER_TYPE_BRANCH;

	public static String BENEFICIARY_SWIFT_BANK1;
	public static String BENEFICIARY_SWIFT_BANK2;
	public static String INSTRUCTION;
	public static String AMIEC_CODE;
	public static String INDIC1;
	public static String INDIC2;
	public static String INDIC3;
	public static String INDIC4;
	public static String INDIC5;

	public static String DenominationAvailable;
	public static String DenominationNotAvailable;
	public static String Save;
	public static String Next;

	public static String IND_CODE;
	
	// parameter AUTH view Constants
	public static String Percentage_authorization_Type;
	public static String LimitBeneficiaryPerDay_authorization_Type;
	public static String WU_Percentage_authorization_Type;
	public static String LimitBeneficiaryPerDay_PlaceOrder;
	public static String DeclarationReportforCash;
	public static String DeclarationReportTotalAmt;
	

	// For Routing Set UP page in Remittance
	public static String CASHNAME;
	public static String DDNAME;
	public static String EFTNAME;
	public static String EFTCode;
	public static String EFTFULLNAME;
	public static String TTNAME;
	public static String TTCodeForService;
	public static String TTCodeForRemittance;
	public static String CHEQUENAME;
	public static String BankingChannelNAME;
	public static String New_Record;
	public static String ICASHAGENTCODE;


	// For FX_Deal Bank
	public static String PD;
	public static String SD;
	public static String Line_Number;
	public static String Fx_BankDealType;

	// For Bank Transfer
	public static String BF;
	public static String BT;

	// For Bank Supplier
	public static String FX_DEAL_WITH_SUPPLIER_DEAL_DATE_ALLOW;
	public static String FX_DEAL_WITH_SUPPLIER_DEAL_DECIMAL_PLACES;
	public static String PY;
	public static String Fx_SupplierDealType;

	// For Bank Indicator
	public static final String BANK_INDICATOR_CORR_BANK;
	public static final String BANK_INDICATOR_FUND_BANK;
	public static final String BANK_INDICATOR_BENI_BANK;
	public static final String BANK_INDICATOR_LOCAL_BANK;
	public static final String BANK_INDICATOR_SERVICEPRO_BANK;
	public static final String BANK_INDICATOR_AGENT_BANK;

	// Title Constants
	public static String TITLE_FOR_MR_NAME;
	public static String TITLE_FOR_MRS_NAME;
	public static String GENDER_MALE;
	public static String GENDER_FEMALE;

	// Language ID
	public static String ENGLISH_LANGUAGE_ID;
	public static String ARABIC_LANGUAGE_ID;

	//Foreign Currency Purchase
	public static String DOCUMENT_CODE_FOR_FCPURCHAGE;
	public static String PURCHAGE_AMOUNT_FOR_FC;
	public static String FC_PURCHAGE;
	public static String P;
	public static String O;
	public static String R;

	//Fund Estimation 
	public static String FUND_ESTIMATION_USD_CURRENCY_CODE;
	public static String PREVIOUS_MOTH_VALUE1;
	public static String PREVIOUS_MOTH_VALUE2;
	public static String PREVIOUS_MOTH_VALUE3;
	public static String PREVIOUS_MOTH_WEEK_VALUE1;
	public static String PREVIOUS_MOTH_WEEK_VALUE2;
	public static String PREVIOUS_MOTH_WEEK_VALUE3;
	public static String FUND_ESTIMATION_IKON_RATE;
	public static String IKON;
	public static String USD;

	// Document Code For Total Application
	public static String DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK;
	public static String DOCUMENT_CODE_FOR_BANK_TRANSFER;
	public static String DOCUMENT_CODE_FOR_SPL_CUSTOMER_DEAL_REQUEST;
	public static String FX_DEAL_WITH_DAY_BOOK;
	public static String DOCUMENT_CODE_FOR_FCSALE;
	public static String DOCUMENT_CODE_FOR_FCSALE_REMITTANCE_APPLICATION;
	public static String DOCUMENT_CODE_FOR_REFUND_REQUEST;
	public static String DOCUMENT_CODE_FOR_REMITTANCE_APPLICATION;
	public static String DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION;
	public static String DOCUMENT_CODE_FOR_COLLECT_TRANSACTION;
	public static String DOCUMENT_CODE_FOR_CUSTOMER;
	public static String DOCUMENT_CODE_FOR_COMPLAINTCREATION;
	public static String DOCUMENT_CODE_FOR_CANCELLATION_REISSUE;
	public static String DOCUMENT_CODE_FOR_CANCEL_REISSUE;
	public static String DOCUMENT_CODE_FOR_STOCK_ADJUST;
	public static String DOCUMENT_CODE_FOR_JVPROCESS;
	public static String DOCUMENT_CODE_FOR_PAYMENT;
	public static String ReceiptType_FOR_PAYMENT;
	public static String DOCUMENT_CODE_FOR_RECEIVE;
	public static String ReceiptType_FOR_RECEIVE;
	public static String STOCK_ADJUST;
	public static String DOCUMENT_CODE_FOR_WU;
	// for FC Sale Constants
	public static String FC_SALE;
	public static String C;
	public static String F;
	public static String S;
	public static String SOURCE_ID_FOR_FC;
	public static String PURPUSE_ID_FOR_FC;

	//DEAL TRACKER
	public static String DEAL_TRACKER_KW_CONBY;
	public static String DEAL_TRACKER_OM_CONBY;

	// For Report Constants
	public static String LOGO_PATH;
	public static String LOGO_PATH_OM;
	public static String SUB_REPORT_PATH;
	public static String REPORT_WATERMARK_LOGO;
	public static String WU_LOGO;

	// Login Info Constants
	public static String USER_TYPE_CUSTOMER;
	public static String USER_TYPE_EMPLOYEE;
	public static String USER_TYPE_CUSTOMERID;
	public static String USER_TYPE_EMPLOYEEID;
	public static String USER_ROLE_MANAGER;
	public static String USER_ROLE_BRANCH_MANAGER;
	public static String USER_ROLE_GENERALMANAGER;
	public static String USER_ROLE_BRANCHSTAFF;
	public static String USER_ROLE_CASHIER;
	public static String USER_ROLE_CHIEF_CASHIER;
	public static String USER_TYPE_ONLINE_CUSTOMERID;
	public static String USER_ROLE_SALES;
	public static String USER_ROLE_TREASURY;
	public static String USER_ROLE_REMITTANCE;
	public static String USER_ROLE_CORPORATE;
	public static String USER_ROLE_AML;
	public static String USER_ROLE_MARKETING;
	public static String USER_ROLE_HELP_DESK;
	
	public static String USER_ROLE_FCSTAFF;
	
	// For Customer
	public static String AGE_LIMIT;
	public static String CUSTOMERTYPE_INDU;
	public static String CIVILID;
	public static String GROUPID;
	public static String EMPLOYMENTTYPE;
	public static String METHODTYPE;
	public static String NATIONAL_ID;
	public static String RESIDENCE;
	public static String PERMANENT;
	public static String CHANGE_DOB;
	public static String IDENTITYFOR;
	public static String MOBILE_CONTACT;
	public static String RESIDENCE_CONTACT;
	public static String PASSWORD;
	public static String WUPASSWORD;

	public static String MOBILE_NUMBER_STRATWITH_FOR_KUWAIT;
	public static String MOBILE_NUMBER_STRATWITH_FOR_OMAN ;
	public static String MOBILE_NUMBER_STRATWITH_FOR_BAHARAIN;

	public static String RESIDENCE_STRATWITH_FOR_KUWAIT;
	public static String RESIDENCE_STRATWITH_FOR_OMAN ;
	public static String RESIDENCE_STRATWITH_FOR_BAHARAIN;

	public static String MESSAGE_FOR_MOBILE_NO_START_WITH_KUWAIT;
	public static String MESSAGE_FOR_RESITANCE_NO_START_WITH_KUWAIT;

	public static String MESSAGE_FOR_MOBILE_NO_START_WITH_OMAN;
	public static String MESSAGE_FOR_RESITANCE_NO_START_WITH_OMAN;

	public static String MESSAGE_FOR_MOBILE_NO_START_WITH_BAHARAIN;
	public static String MESSAGE_FOR_RESITANCE_NO_START_WITH_BAHARAIN;
	public static String BRANCH_CODE_ONLINE;

	public static String CUST_ACTIVE_INDICATOR;
	public static String CUST_INACTIVE_INDICATOR;
	public static String Dependent;


	//public static  String TITLE_FOR_MR;
	//public static  String TITLE_FOR_MRS;

	public static String LOCAL_TITLE_FOR_MR;
	public static String LOCAL_TITLE_FOR_MRS;

	public static String GENDER_MALE_ARABIC;
	public static String GENDER_FEMALE_ARABIC;
	public static String MANUAL;

	//public static String MR_ARABIC;
	//public static String MRS_ARABIC;

	//public static String COMPANY_REGISTRATION;
	public static String COMPANY_REGISTRATION_DOC;
	public static String TITLE_CORPORATE_MS;
	// For Corporate 
	public static String CUSTOMERTYPE_CORP; 
	public static String CUSTOMERTYPEFORPARTNER;
	public static String CUSTOMERTYPEFOROWNER;
	public static String COMPANYIDTYPE;


	//Special Customer Deal Request 
	public static String INDIVIDUAL_COMPONENT_ID;
	public static String CORPORATE_COMPONENT_ID;
	public static String SPL_CUSTOMER_HIGH_VALUE_TRNX; 


	// CashTransfer Constant Values
	
	public static BigDecimal ONE_LAKH;
	public static BigDecimal FIFTY_THOUSAND;
	public static BigDecimal TWENTY_THOUSAND;
	public static BigDecimal TEN_THOUSAND;
	public static BigDecimal FIVE_THOUSAND;
	public static BigDecimal TWO_THOUSAND;
	
	public static BigDecimal THOUSAND;
	public static BigDecimal FIVE_HUNDRED;
	public static BigDecimal TWO_HUNDRED;
	public static BigDecimal HUNDRED;
	public static BigDecimal FIFTY;
	public static BigDecimal TWENTY;
	public static BigDecimal TEN;
	public static BigDecimal FIVE;
	public static BigDecimal ONE;
	public static BigDecimal POINT_FIVE;
	public static BigDecimal POINT_TWO_FIVE;
	public static BigDecimal POINT_ONE;
	public static BigDecimal POINT_ZERO_FIVE;
	
	public static BigDecimal POINT_ZERO_TWO;
	public static BigDecimal POINT_ZERO_ONE;
	public static BigDecimal POINT_ZERO_ZERO_FIVE;
	public static BigDecimal POINT_ZERO_ZERO_ONE;
	
	public static String SAFE;
	public static String CT;
	public static String CASH_TRANSFER_L_TO_L;
	public static String CASH_TRANSFER_C_TO_C;
	public static String DOCUMENT_CODE_FOR_CASH_TRANSFER;
	public static String DOCUMENT_CODE_FOR_CASH_TRANSFER_B_B;
	public static String DOCUMENT_CODE_FOR_PLACEORDER;
	
	public static String T; // Transfer - T
	public static String Receive; // Approve Receive - R

	// for Standing Instructions
	public static String Daily;
	public static String Weekly;
	public static String Monthly;
	public static String Quaterly;
	public static String HalfYearly;
	public static String Annually;
	public static String STANDING_INSTRUCTION;

	public static String DAILY;
	public static String WEEKLY;
	public static String TWO_WEEK;
	public static String MONTHLY;
	public static String QUARTERLY;
	public static String HALFYEARLY;
	public static String ANNUALLY;


	//Bank Service Rule Master
	public static  String CUSTOMERTYPE_INDIVIDUAL;
	public static  String CUSTOMERTYPE_CORPORATE;
	public static  String BOTH;
	public static  String COMISSION;
	public static  String OVERSEASE_CHARGE; 
	public static  String VALIDATE;
	public static  String YES;
	public static  String NO;
	public static  String BOTHFOR_BANKSERVICE_COMPONENT_ID;
	public static  String TWO;
	public static  String THREE;

	//Refund Request
	public static String  TRANSACTION_STATUS_FOR_CASH_CODE;
	public static String  TRANSACTION_STATUS_FOR_STOP_PAYMENT_CODE;
	public static String  TRANSACTION_STATUS_FOR_STOP_REFUND_OVER_CODE;
	public static String  TRANSACTION_STATUS_FOR_WESTERN_UNION;

	// BCO Module Constants
	public static String FINSCAN_STATUS_PENDING;	
	public static String FINSCAN_STATUS_PASS;
	public static String FINSCAN_STATUS_ERROR ;	
	public static String FINSCAN_STATUS_FAIL;
	public static String AML_STATUS_COMP;	
	public static String AML_STATUS_BCO ;	
	public static String AML_STATUS_ESCALATE;	
	public static String AML_STATUS_PASS ;
	public static String AML_STATUS_FAIL;


	// Report constants
	public static String CUSTOMER_LOG_DIFF_RPT_FILE;
	public static String CUSTOMER_REG_RPT_FILE ;
	public static String RPT_IMAGE_PATH ;
	public static String RPT_PATH ;

	// Report constants
	public static BigDecimal DOCUMENT_CODE_FOR_STOPPAYMENT;
	public static String CASH_PRODUCT;

	//STOP PAYMENT CONSTANTS
	public static String STOPR_PAYMENT_PROG;

	public static final int DOCUMENT_CODE_FOR_FC_SALE_APP = 1;

	public static final int DOCUMENT_CODE_FOR_REMMITANCE_COMPLIANT = 3;
	//Modified by Rabil on 05102015
	public static final int DOCUMENT_CODE_FOR_REMMITANCE = 2;

	public static final String INDIVIDUAL = "Individual";

	// APPLICATION CODE

	public static final String RULE_ENGINE_APPLICATION_CODE_KUWAIT ;
	public static final String V ;
	//Added by Rabil
	public static final String AMTBC ;

	//CUSTOMER FOR NEW ID TYPE
	public static String CIVIL_ID_NEW;
	public static String CIVIL_ID_VALUE;

	//Online Customer Login CR- 22-06-2015
	public static String USER;
	public static String ADMIN;
	//Customer Login on 07-09-2015
	public static String STATUS_ACTIVE;
	public static String STATUS_LOCK;

	//knet message properties
	public static String knetsuccess;
	public static String knetsuccesspage;
	public static String kneterror;
	public static String entercardnumbermsg;
	public static String carderrormsg;
	public static String knetTransactionerror;
	public static String knetTransactionContact;
	//Complaint Action
	public static String OPEN;
	public static String CLOSE;
	public static String PENDING;
	public static String BANK;
	public static String E;

	public static String COMPLAINT_ACTION_GROUP_OPEN;


	public static String ASSIGNED_CUSTOMER_HELP_DESK;
	public static String ASSIGNED_CUSTOMER_SUPPORT_SERVICE;
	public static String ASSIGNED_BRANCH;

	public static String ASSIGNED_TO_CENTRAL_HELP_DESK;
	public static String ASSIGNED_TO_CUSTOMER_SUPPORT_SERVICES;
	public static String ASSIGNED_TO_BRANCH;
	public static String ASSIGNED_TO_BANK;
	public static String ASSIGNED_TO_AGENT;

	//Test Key Master and Value
	public static String SERIAL_INDICATOR_VALUE_DAILY ;
	public static String SERIAL_INDICATOR_VALUE_WEEKLY ;
	public static String SERIAL_INDICATOR_VALUE_MONTHLY ;
	public static String SERIAL_INDICATOR_VALUE_CONTINUE ;
	public static String END_OF_SERIAL_VALUE_NEW;
	public static String END_OF_SERIAL_VALUE_REUSE;
	public static String INDICATOR_SEND;
	public static String INDICATOR_RECEIVE;

	// Is Active Status for Enquiry
	public static String APPROVED;
	public static String UNAPPROVED;
	public static String NOTAPPROVED;
	public static String NEGOTIATED;

	//KIOSK XML CONSTANTS
	public static String RESPONSE_ID;
	public static String HOST_NAME;
	public static String PORT_NO;
	public static String FEEDCASH_STATUS;

	public static String DAILYLIMIT;
	public static String RECEIPT_TYPE;
	public static String BIZ_COMPONENT_IDENTITY_TYPE;

	//payment mode
	public static String CashCode;
	public static String KNETCode;
	public static String ChequeCode;
	public static String BankTransferCode;
	public static String NameCheckAlertMsg;

	//Scan image location for customer DB scan
	public static String IMG_LOCATION;
	public static String ScanInd_N;
	public static String ScanInd_D;
	public static String ScanInd_A;
	public static String Scan_Req_Y;


	//fcsale inquiry
	public static String CASH;
	public static String SALE;
	public static String PURCHASE;

	// Employee Active or Inactive
	public static String ACTIVEEMPLOYEE;
	public static String INACTIVEEMPLOYEE;
	public static String ITIPAddress;


	//Western Union Constants
	public static String TERMINAL_ID;
	public static String JAVA_TRANSACTION_YES;
	public static String WU_INOUT_SEND;
	public static String WU_INOUT_RECEIVE;
	public static String WU_COLLECT_DOCUMENTNO;	
	public static String WU_BANK_CODE;
	public static String MONEYGRAM_BANK_CODE;
	public static String HOMESEND_BANK_CODE;
	public static String WU_APP_DELAY_TIME;
	public static String WU_APP_DELAY_SLEEP_INTERVAL;

	// Canecl reissue
	public static String APPLICATION_STATUS;

	// Arcmate Scanning
	public static String CHECK;
	public static String SCAN;
	public static String VIEW;
	public static String MODIFY;
	public static String OCR;
	public static String NON_OCR;
	public static String BOTH_VIEW;
	public static String BEDOUIN;
	public static String PASSPORT;
	public static String GCC_NATIONAL_ID;
	public static String LICENSE_NO;
	public static String CHECK_DOCUMENT;
	public static String CHECK_FILE;
	public static String VOCHERCODE;
	public static String VOUCHER;
	public static String BANKTRANSFER;
	
	public static String GL_LEVEL;
	public static String COUNTRY_CURRENCY_LEVEL;
	
	public static String ACCEPT;
	public static String REJECT;
	
	public static String ACCEPT_VALUE;
	public static String REJECT_VALUE;
	public static String PENDING_VALUE;
	
	// COUNTRYBranch
	public static String INDICATOR_0;
	public static String INDICATOR_1;
	
	public   static String BANKCHANNEL;
	public   static String  CASHFORONLINE;
	public static String SPOT;
	public static String TOM;
	public static String BAHRAIN;
	
	public static String CUSTOMER_URL;
	
	// ICASH PRODUCT
	public static String AgentStatusICash;
	public static String ModeofTransferEFT;
	public static String ModeofTransferTT;
	
	//country code
	public static String COUNTRY_CODE_1;
	
	
	public static String I;
	public static String W;
	public static String Y;
	
	public static String WesternUnionPassword;
	public static String LoginPassword;
	public static String Old_Record;
	
	public static String INSERT;
	public static String UPDATE;
	
	public static String BNFADDR3;
	
	public static String IDTYPE_CIVILID;
	public static String IDTYPE_CIVILID_NEW;
	public static String IDTYPE_PASSPORT;
	
	public static String EncriptionKey;
	
	public static String StopPaymentForm;
	public static String RefundForm;
	public static String MiscelleaneousPaymentForm;
	public static String MiscelleaneousReceiptForm;
	
	public static String CR;
	public static String Share_Capital;
	
	public static String OMSMARTCARDENV;
	public static String OM_SMART_CARD_APPLICATION_TYPE;
	
	public static String WU_FILE_UPLOAD_PATH;
	public static String WU_FILE_NAME;
	
	public static String COMPANY_NAME_KW;
	public static String COMPANY_NAME_OM;
	public static String COMPANY_NAME_BH;
	
	public static String localConstactTypeId;
	public static String homeConstactTypeId;
	
	// tele marketing
	public static String TeleFollowUpCodeClose;
	public static String TeleFollowUpCodeDate;
	public static String GO_GREEN_REMIT_AMT_LIMIT;
	public static String EMAILVERFICATIONAPP;
	public static BigDecimal ASSO_ID;
	public static String DOCUMENT_CODE_FOR_PROMOTION;
	
	public static String StopPaymentLetterBody1;
	public static String StopPaymentLetterBody2;
	public static String StopPaymentLetterBody3;
	
	public static String PepNoteBody1;
	public static String PepDeclarationBody;
	public static String PepDeclarationBody1;
	
	public static String GSM;
	
	public static String  CORPORATE_ID_TYPE; 
	
	public static String WUH2H_SELECT_DECLARATION;
	public static String WUH2H_UNSELECT_DECLARATION;
	
	static {
		HashMap<String, String> hashmap = ReadApplicationProp.getPropertiesValue();

		DOCUMENT_NUMBER = hashmap.get("DOCUMENT_NUMBER");
		VOUCHER=hashmap.get("VOUCHER");
  		VOCHERCODE=hashmap.get("VOCHERCODE");
  		BANKTRANSFER=hashmap.get("BANKTRANSFER");
		Yes = hashmap.get("Yes");
		No = hashmap.get("No");
		U = hashmap.get("U");
		A = hashmap.get("A");
		D = hashmap.get("D");
		ALL = hashmap.get("ALL");
		ACTIVE = hashmap.get("ACTIVE");
		IN_ACTIVE = hashmap.get("IN_ACTIVE");
		SPECIFIC = hashmap.get("SPECIFIC");
		DEACTIVATE = hashmap.get("DEACTIVATE");
		ACTIVATE = hashmap.get("ACTIVATE");
		DELETE = hashmap.get("DELETE");
		REMOVE = hashmap.get("REMOVE");
		FILE_UPLOAD= hashmap.get("FILE_UPLOAD");
		DUPLICATE= hashmap.get("DUPLICATE");
		PENDING_FOR_APPROVE= hashmap.get("PENDING_FOR_APPROVE");
		
		INSERT=hashmap.get("INSERT");
		UPDATE =hashmap.get("UPDATE");


		// Title Constants
		TITLE_FOR_MR_NAME = hashmap.get("TITLE_FOR_MR_NAME");
		TITLE_FOR_MRS_NAME = hashmap.get("TITLE_FOR_MRS_NAME");
		GENDER_MALE = hashmap.get("GENDER_MALE");
		GENDER_FEMALE = hashmap.get("GENDER_FEMALE");

		// Standard Constants for FX_Deal Bank
		PD = hashmap.get("PD");
		SD = hashmap.get("SD");
		Line_Number = hashmap.get("Line_Number");
		Fx_BankDealType = hashmap.get("Fx_BankDealType");

		// For Bank Transfer
		BF = hashmap.get("BF");
		BT = hashmap.get("BT");

		// For Routing Set UP page in Remittance
		CASHNAME = hashmap.get("CASHNAME");
		DDNAME = hashmap.get("DDNAME");
		EFTNAME = hashmap.get("EFTNAME");
		EFTCode = hashmap.get("EFTCode");
		EFTFULLNAME =  hashmap.get("EFTFULLNAME");
		TTNAME = hashmap.get("TTNAME");
		TTCodeForService = hashmap.get("TTCodeForService");
		TTCodeForRemittance = hashmap.get("TTCodeForRemittance");
		CHEQUENAME = hashmap.get("CHEQUENAME");
		BankingChannelNAME = hashmap.get("BankingChannelNAME");
		New_Record= hashmap.get("New_Record");
		ICASHAGENTCODE= hashmap.get("ICASHAGENTCODE");

		// For Personal Remittance
		Individual = hashmap.get("Individual");
		NonIndividual = hashmap.get("NonIndividual");
		Dependant = hashmap.get("Dependant");
		DEPENDANT= hashmap.get("DEPENDANT");
		Corporate = hashmap.get("Corporate");
		COLLECTIONMODE= hashmap.get("COLLECTIONMODE");
		RP = hashmap.get("RP");
		FC_SALE_REMIT = hashmap.get("FC_SALE_REMIT");
		SELF = hashmap.get("SELF");
		OTHERS = hashmap.get("OTHERS");
		Remittance = hashmap.get("Remittance");
		FCSale = hashmap.get("FCSale");
		KNET = hashmap.get("KNET");
		
		IndividualFullName  = hashmap.get("IndividualFullName");
		NonIndividualFullName  = hashmap.get("NonIndividualFullName");
		CorporateFullName  = hashmap.get("CorporateFullName");

		UP  = hashmap.get("UP");
		DOWN  = hashmap.get("DOWN");
		USER_TYPE_BRANCH = hashmap.get("USER_TYPE_BRANCH");

		BENEFICIARY_SWIFT_BANK1  = hashmap.get("BENEFICIARY_SWIFT_BANK1");
		BENEFICIARY_SWIFT_BANK2  = hashmap.get("BENEFICIARY_SWIFT_BANK2");
		INSTRUCTION  = hashmap.get("INSTRUCTION");
		AMIEC_CODE  = hashmap.get("AMIEC_CODE");
		INDIC1  = hashmap.get("INDIC1");
		INDIC2  = hashmap.get("INDIC2");
		INDIC3  = hashmap.get("INDIC3");
		INDIC4  = hashmap.get("INDIC4");
		INDIC5  = hashmap.get("INDIC5");

		DenominationAvailable = hashmap.get("DenominationAvailable");
		DenominationNotAvailable = hashmap.get("DenominationNotAvailable");
		Save = hashmap.get("Save");
		Next = hashmap.get("Next");
		IND_CODE = hashmap.get("IND_CODE");
		
		Percentage_authorization_Type = hashmap.get("Percentage_authorization_Type");
		LimitBeneficiaryPerDay_authorization_Type = hashmap.get("LimitBeneficiaryPerDay_authorization_Type");
		WU_Percentage_authorization_Type = hashmap.get("WU_Percentage_authorization_Type");
		LimitBeneficiaryPerDay_PlaceOrder = hashmap.get("LimitBeneficiaryPerDay_PlaceOrder"); 

		// Bank Indicator
		BANK_INDICATOR_CORR_BANK = hashmap.get("BANK_INDICATOR_CORR_BANK");
		BANK_INDICATOR_FUND_BANK = hashmap.get("BANK_INDICATOR_FUND_BANK");
		BANK_INDICATOR_BENI_BANK = hashmap.get("BANK_INDICATOR_BENI_BANK");
		BANK_INDICATOR_LOCAL_BANK = hashmap.get("BANK_INDICATOR_LOCAL_BANK");
		BANK_INDICATOR_SERVICEPRO_BANK = hashmap.get("BANK_INDICATOR_SERVICEPRO_BANK");
		BANK_INDICATOR_AGENT_BANK = hashmap.get("BANK_INDICATOR_AGENT_BANK");

		// Document Seriality
		DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK = hashmap.get("DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK");
		DOCUMENT_CODE_FOR_BANK_TRANSFER = hashmap.get("DOCUMENT_CODE_FOR_BANK_TRANSFER");
		DOCUMENT_CODE_FOR_SPL_CUSTOMER_DEAL_REQUEST = hashmap.get("DOCUMENT_CODE_FOR_SPL_CUSTOMER_DEAL_REQUEST");
		FX_DEAL_WITH_DAY_BOOK = hashmap.get("FX_DEAL_WITH_DAY_BOOK");
		DOCUMENT_CODE_FOR_FCSALE = hashmap.get("DOCUMENT_CODE_FOR_FCSALE");
		DOCUMENT_CODE_FOR_FCSALE_REMITTANCE_APPLICATION = hashmap.get("DOCUMENT_CODE_FOR_FCSALE_REMITTANCE_APPLICATION");
		DOCUMENT_CODE_FOR_REFUND_REQUEST = hashmap.get("DOCUMENT_CODE_FOR_REFUND_REQUEST");
		DOCUMENT_CODE_FOR_REMITTANCE_APPLICATION = hashmap.get("DOCUMENT_CODE_FOR_REMITTANCE_APPLICATION");
		DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION = hashmap.get("DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION");
		DOCUMENT_CODE_FOR_COLLECT_TRANSACTION = hashmap.get("DOCUMENT_CODE_FOR_COLLECT_TRANSACTION");
		DOCUMENT_CODE_FOR_CUSTOMER = hashmap.get("DOCUMENT_CODE_FOR_CUSTOMER");
		DOCUMENT_CODE_FOR_COMPLAINTCREATION = hashmap.get("DOCUMENT_CODE_FOR_COMPLAINTCREATION");
		DOCUMENT_CODE_FOR_CANCELLATION_REISSUE = hashmap.get("DOCUMENT_CODE_FOR_CANCELLATION_REISSUE");
		DOCUMENT_CODE_FOR_CANCEL_REISSUE=hashmap.get("DOCUMENT_CODE_FOR_CANCEL_REISSUE");
		DOCUMENT_CODE_FOR_STOCK_ADJUST=hashmap.get("DOCUMENT_CODE_FOR_STOCK_ADJUST");
		DOCUMENT_CODE_FOR_JVPROCESS = hashmap.get("DOCUMENT_CODE_FOR_JVPROCESS");
		DOCUMENT_CODE_FOR_PAYMENT = hashmap.get("DOCUMENT_CODE_FOR_PAYMENT");
		ReceiptType_FOR_PAYMENT = hashmap.get("ReceiptType_FOR_PAYMENT");
		DOCUMENT_CODE_FOR_RECEIVE = hashmap.get("DOCUMENT_CODE_FOR_RECEIVE");
		ReceiptType_FOR_RECEIVE = hashmap.get("ReceiptType_FOR_RECEIVE");
		STOCK_ADJUST = hashmap.get("STOCK_ADJUST");
		DOCUMENT_CODE_FOR_WU = hashmap.get("DOCUMENT_CODE_FOR_WU");
		// Language Id
		ENGLISH_LANGUAGE_ID = hashmap.get("ENGLISH_LANGUAGE_ID");

		ARABIC_LANGUAGE_ID = hashmap.get("ARABIC_LANGUAGE_ID");

		// For FC Sale
		FC_SALE = hashmap.get("FC_SALE");
		C = hashmap.get("C");
		F = hashmap.get("F");
		S = hashmap.get("S");
		SOURCE_ID_FOR_FC = hashmap.get("SOURCE_ID_FOR_FC");
		PURPUSE_ID_FOR_FC = hashmap.get("PURPUSE_ID_FOR_FC");


		// For Report Constants
		LOGO_PATH = hashmap.get("LOGO_PATH");
		LOGO_PATH_OM = hashmap.get("LOGO_PATH_OM");
		SUB_REPORT_PATH = hashmap.get("SUB_REPORT_PATH");
		REPORT_WATERMARK_LOGO = hashmap.get("REPORT_WATERMARK_LOGO");
		WU_LOGO = hashmap.get("WU_LOGO");

		// Login Info Constants
		USER_ROLE_MANAGER = hashmap.get("USER_ROLE_MANAGER");
		USER_ROLE_BRANCH_MANAGER = hashmap.get("USER_ROLE_BRANCH_MANAGER");
		USER_ROLE_GENERALMANAGER = hashmap.get("USER_ROLE_GENERALMANAGER");
		USER_ROLE_CASHIER = hashmap.get("USER_ROLE_CASHIER");
		USER_ROLE_CHIEF_CASHIER = hashmap.get("USER_ROLE_CHIEF_CASHIER");
		USER_TYPE_CUSTOMER = hashmap.get("USER_TYPE_CUSTOMER");
		USER_TYPE_EMPLOYEE = hashmap.get("USER_TYPE_EMPLOYEE");
		USER_TYPE_CUSTOMERID = hashmap.get("USER_TYPE_CUSTOMERID");
		USER_TYPE_EMPLOYEEID = hashmap.get("USER_TYPE_EMPLOYEEID");
		USER_TYPE_ONLINE_CUSTOMERID = hashmap.get("USER_TYPE_ONLINE_CUSTOMERID");
		USER_ROLE_SALES = hashmap.get("USER_ROLE_SALES");
		USER_ROLE_TREASURY = hashmap.get("USER_ROLE_TREASURY");
		USER_ROLE_REMITTANCE = hashmap.get("USER_ROLE_REMITTANCE");
		USER_ROLE_BRANCHSTAFF = hashmap.get("USER_ROLE_BRANCHSTAFF");
		USER_ROLE_CORPORATE = hashmap.get("USER_ROLE_CORPORATE");
		USER_ROLE_AML= hashmap.get("USER_ROLE_AML");
		USER_ROLE_MARKETING = hashmap.get("USER_ROLE_MARKETING");
		USER_ROLE_HELP_DESK = hashmap.get("USER_ROLE_HELP_DESK");
		
		USER_ROLE_FCSTAFF = hashmap.get("USER_ROLE_FCSTAFF");
		
		// For Customer
		AGE_LIMIT = hashmap.get("AGE_LIMIT");
		CUSTOMERTYPE_INDU = hashmap.get("CUSTOMERTYPE_INDU");
		CIVILID = hashmap.get("CIVILID");
		GROUPID = hashmap.get("GROUPID");
		EMPLOYMENTTYPE = hashmap.get("EMPLOYMENTTYPE");
		METHODTYPE = hashmap.get("METHODTYPE");
		NATIONAL_ID = hashmap.get("NATIONAL_ID");
		RESIDENCE = hashmap.get("RESIDENCE");
		PERMANENT = hashmap.get("PERMANENT");
		CHANGE_DOB = hashmap.get("CHANGE_DOB");
		IDENTITYFOR = hashmap.get("IDENTITYFOR");
		//	TITLE_FOR_MR = hashmap.get("TITLE_FOR_MR");
		//    TITLE_FOR_MRS = hashmap.get("TITLE_FOR_MRS");
		LOCAL_TITLE_FOR_MR = hashmap.get("LOCAL_TITLE_FOR_MR");
		LOCAL_TITLE_FOR_MRS = hashmap.get("LOCAL_TITLE_FOR_MRS");
		GENDER_MALE_ARABIC = hashmap.get("GENDER_MALE_ARABIC");
		GENDER_FEMALE_ARABIC = hashmap.get("GENDER_FEMALE_ARABIC");
		MANUAL = hashmap.get("MANUAL");
		//MR_ARABIC = hashmap.get("MR_ARABIC");
		// MRS_ARABIC = hashmap.get("MRS_ARABIC");

		CUST_ACTIVE_INDICATOR = hashmap.get("CUST_ACTIVE_INDICATOR");
		CUST_INACTIVE_INDICATOR = hashmap.get("CUST_INACTIVE_INDICATOR");
		Dependent = hashmap.get("Dependent");


		// For Corporate 
		CUSTOMERTYPE_CORP = hashmap.get("CUSTOMERTYPE_CORP");
		CUSTOMERTYPEFORPARTNER = hashmap.get("CUSTOMERTYPEFORPARTNER");
		CUSTOMERTYPEFOROWNER = hashmap.get("CUSTOMERTYPEFOROWNER");
		COMPANYIDTYPE = hashmap.get("COMPANYIDTYPE");
		//COMPANY_REGISTRATION = hashmap.get("COMPANY_REGISTRATION");
		COMPANY_REGISTRATION_DOC = hashmap.get("COMPANY_REGISTRATION_DOC");
		TITLE_CORPORATE_MS = hashmap.get("TITLE_CORPORATE_MS");

		// For Country
		BAHRAIN_ALPHA_TWO_CODE = hashmap.get("BAHRAIN_ALPHA_TWO_CODE");
		KUWAIT_ALPHA_TWO_CODE = hashmap.get("KUWAIT_ALPHA_TWO_CODE");
		OMAN_ALPHA_TWO_CODE = hashmap.get("OMAN_ALPHA_TWO_CODE");
		KUWAIT_QUOTE_NAME = hashmap.get("KUWAIT_QUOTE_NAME");

		//Special Customer Deal Request 
		INDIVIDUAL_COMPONENT_ID = hashmap.get("INDIVIDUAL_COMPONENT_ID");  
		CORPORATE_COMPONENT_ID = hashmap.get("CORPORATE_COMPONENT_ID");
		SPL_CUSTOMER_HIGH_VALUE_TRNX = hashmap.get("SPL_CUSTOMER_HIGH_VALUE_TRNX"); 

		// For Fx Deal Supplier
		FX_DEAL_WITH_SUPPLIER_DEAL_DATE_ALLOW = hashmap.get("FX_DEAL_WITH_SUPPLIER_DEAL_DATE_ALLOW");
		FX_DEAL_WITH_SUPPLIER_DEAL_DECIMAL_PLACES = hashmap.get("FX_DEAL_WITH_SUPPLIER_DEAL_DECIMAL_PLACES");
		PY = hashmap.get("PY");
		Fx_SupplierDealType = hashmap.get("Fx_SupplierDealType");

		//Foreign Currency Purchage
		DOCUMENT_CODE_FOR_FCPURCHAGE = hashmap.get("DOCUMENT_CODE_FOR_FCPURCHAGE");
		PURCHAGE_AMOUNT_FOR_FC = hashmap.get("PURCHAGE_AMOUNT_FOR_FC");
		FC_PURCHAGE = hashmap.get("FC_PURCHAGE");
		P = hashmap.get("P");
		O = hashmap.get("O");
		R = hashmap.get("R");


		//Fund Estimation
		FUND_ESTIMATION_USD_CURRENCY_CODE = hashmap.get("FUND_ESTIMATION_USD_CURRENCY_CODE");
		PREVIOUS_MOTH_VALUE1 = hashmap.get("PREVIOUS_MOTH_VALUE1");
		PREVIOUS_MOTH_VALUE2 = hashmap.get("PREVIOUS_MOTH_VALUE2");
		PREVIOUS_MOTH_VALUE3 = hashmap.get("PREVIOUS_MOTH_VALUE3");
		PREVIOUS_MOTH_WEEK_VALUE1 = hashmap.get("PREVIOUS_MOTH_WEEK_VALUE1");
		PREVIOUS_MOTH_WEEK_VALUE2 = hashmap.get("PREVIOUS_MOTH_WEEK_VALUE2");
		PREVIOUS_MOTH_WEEK_VALUE3 = hashmap.get("PREVIOUS_MOTH_WEEK_VALUE3");
		FUND_ESTIMATION_IKON_RATE = hashmap.get("FUND_ESTIMATION_IKON_RATE");

		//CashTransfer Constant Values
		
		String onelakh =hashmap.get("ONE_LAKH");
		ONE_LAKH= new BigDecimal(onelakh);
		String fiftythousand =hashmap.get("FIFTY_THOUSAND");
		FIFTY_THOUSAND= new BigDecimal(fiftythousand);
		String twentythousand =hashmap.get("TWENTY_THOUSAND");
		TWENTY_THOUSAND= new BigDecimal(twentythousand);
		String tenthousand =hashmap.get("TEN_THOUSAND");
		TEN_THOUSAND= new BigDecimal(tenthousand);
		String fivethousand =hashmap.get("FIVE_THOUSAND");
		FIVE_THOUSAND= new BigDecimal(fivethousand);
		String twothousand =hashmap.get("TWO_THOUSAND");
		TWO_THOUSAND= new BigDecimal(twothousand);
		
		String thousand =hashmap.get("THOUSAND");
		THOUSAND= new BigDecimal(thousand);
		String fiveHundred =hashmap.get("FIVE_HUNDRED");
		FIVE_HUNDRED= new BigDecimal(fiveHundred);
		String twoHundred =hashmap.get("TWO_HUNDRED");
		TWO_HUNDRED= new BigDecimal(twoHundred);
		String hundred =hashmap.get("HUNDRED");
		HUNDRED= new BigDecimal(hundred);
		String fifty =hashmap.get("FIFTY");
		FIFTY= new BigDecimal(fifty);
		String twenty =hashmap.get("TWENTY");
		TWENTY= new BigDecimal(twenty);
		String ten =hashmap.get("TEN");
		TEN= new BigDecimal(ten);
		String five =hashmap.get("FIVE");
		FIVE= new BigDecimal(five);
		String one =hashmap.get("ONE");
		ONE= new BigDecimal(one);
		String pointFive =hashmap.get("POINT_FIVE");
		POINT_FIVE= new BigDecimal(pointFive);
		String pointTwoFive =hashmap.get("POINT_TWO_FIVE");
		POINT_TWO_FIVE= new BigDecimal(pointTwoFive);
		String pointOne =hashmap.get("POINT_ONE");
		POINT_ONE= new BigDecimal(pointOne);
		String pointZeroFive = hashmap.get("POINT_ZERO_FIVE");
		POINT_ZERO_FIVE= new BigDecimal(pointZeroFive);
		
		String pointzerotwo =hashmap.get("POINT_ZERO_TWO");
		POINT_ZERO_TWO= new BigDecimal(pointzerotwo);
		String pointzeroone =hashmap.get("POINT_ZERO_ONE");
		POINT_ZERO_ONE= new BigDecimal(pointzeroone);
		String pointzerozerofive =hashmap.get("POINT_ZERO_ZERO_FIVE");
		POINT_ZERO_ZERO_FIVE= new BigDecimal(pointzerozerofive);
		String pointzerozeroone =hashmap.get("POINT_ZERO_ZERO_ONE");
		POINT_ZERO_ZERO_ONE= new BigDecimal(pointzerozeroone);
		
		
		SAFE = hashmap.get("SAFE");
		CASH_TRANSFER_L_TO_L = hashmap.get("CASH_TRANSFER_L_TO_L");
		CASH_TRANSFER_C_TO_C = hashmap.get("CASH_TRANSFER_C_TO_C");
		CT = hashmap.get("CT");
		T = hashmap.get("T");
		Receive = hashmap.get("Receive");
		DOCUMENT_CODE_FOR_CASH_TRANSFER = hashmap.get("DOCUMENT_CODE_FOR_CASH_TRANSFER");
		DOCUMENT_CODE_FOR_CASH_TRANSFER_B_B = hashmap.get("DOCUMENT_CODE_FOR_CASH_TRANSFER_B_B");
		

		//Standing Instructions---- Needs  to remove
		Daily = hashmap.get("Daily");
		Weekly = hashmap.get("Weekly");
		Monthly = hashmap.get("Monthly");
		Quaterly = hashmap.get("Quaterly");
		HalfYearly = hashmap.get("HalfYearly");
		Annually = hashmap.get("Annually");
		STANDING_INSTRUCTION =hashmap.get("STANDING_INSTRUCTION");
		DAILY = hashmap.get("DAILY");
		WEEKLY = hashmap.get("WEEKLY");
		TWO_WEEK = hashmap.get("TWO_WEEK");
		MONTHLY = hashmap.get("MONTHLY");
		QUARTERLY = hashmap.get("QUARTERLY");
		HALFYEARLY = hashmap.get("HALFYEARLY");
		ANNUALLY = hashmap.get("ANNUALLY");


		//Bank Service Rule Master
		CUSTOMERTYPE_INDIVIDUAL = hashmap.get("CUSTOMERTYPE_INDIVIDUAL");
		CUSTOMERTYPE_CORPORATE = hashmap.get("CUSTOMERTYPE_CORPORATE");
		BOTH = hashmap.get("BOTH");
		COMISSION = hashmap.get("COMISSION");
		OVERSEASE_CHARGE = hashmap.get("OVERSEASE_CHARGE");
		VALIDATE = hashmap.get("VALIDATE");
		YES = hashmap.get("YES");
		NO = hashmap.get("NO");
		BOTHFOR_BANKSERVICE_COMPONENT_ID = hashmap.get("BOTHFOR_BANKSERVICE_COMPONENT_ID");
		TWO = hashmap.get("TWO");
		THREE = hashmap.get("THREE");

		//Refund Request
		TRANSACTION_STATUS_FOR_CASH_CODE =hashmap.get("TRANSACTION_STATUS_FOR_CASH_CODE");
		TRANSACTION_STATUS_FOR_STOP_PAYMENT_CODE =hashmap.get("TRANSACTION_STATUS_FOR_STOP_PAYMENT_CODE");
		TRANSACTION_STATUS_FOR_STOP_REFUND_OVER_CODE =hashmap.get("TRANSACTION_STATUS_FOR_STOP_REFUND_OVER_CODE");
		TRANSACTION_STATUS_FOR_WESTERN_UNION =hashmap.get("TRANSACTION_STATUS_FOR_WESTERN_UNION");

		//BCO
		FINSCAN_STATUS_PENDING = hashmap.get("FINSCAN_STATUS_PENDING");
		FINSCAN_STATUS_PASS = hashmap.get("FINSCAN_STATUS_PASS");
		FINSCAN_STATUS_ERROR =hashmap.get("FINSCAN_STATUS_ERROR");
		FINSCAN_STATUS_FAIL = hashmap.get("FINSCAN_STATUS_FAIL");

		AML_STATUS_COMP = hashmap.get("AML_STATUS_COMP");
		AML_STATUS_BCO =hashmap.get("AML_STATUS_BCO");
		AML_STATUS_ESCALATE = hashmap.get("AML_STATUS_ESCALATE");
		AML_STATUS_PASS =hashmap.get("AML_STATUS_PASS");
		AML_STATUS_FAIL = hashmap.get("AML_STATUS_FAIL");

		// Report constants
		CUSTOMER_LOG_DIFF_RPT_FILE = hashmap.get("CUSTOMER_LOG_DIFF_RPT_FILE");
		CUSTOMER_REG_RPT_FILE = hashmap.get("CUSTOMER_REG_RPT_FILE");
		RPT_IMAGE_PATH = hashmap.get("RPT_IMAGE_PATH");
		RPT_PATH = hashmap.get("RPT_PATH");

		//STOP PAYMENT DOCUMENT CODE KEY/VALUE
		String documentForStopPayment = hashmap.get("DOCUMENT_CODE_FOR_STOPPAYMENT");
		DOCUMENT_CODE_FOR_STOPPAYMENT = new BigDecimal(documentForStopPayment);
		CASH_PRODUCT = hashmap.get("CASH_PRODUCT");
		STOPR_PAYMENT_PROG = hashmap.get("STOPR_PAYMENT_PROG");

		RULE_ENGINE_APPLICATION_CODE_KUWAIT = hashmap.get("RULE_ENGINE_APPLICATION_CODE_KUWAIT");

		MOBILE_NUMBER_STRATWITH_FOR_KUWAIT= hashmap.get("MOBILE_NUMBER_STRATWITH_FOR_KUWAIT");
		MOBILE_NUMBER_STRATWITH_FOR_OMAN = hashmap.get("MOBILE_NUMBER_STRATWITH_FOR_OMAN");
		MOBILE_NUMBER_STRATWITH_FOR_BAHARAIN = hashmap.get("MOBILE_NUMBER_STRATWITH_FOR_BAHARAIN");//Not yet Used

		RESIDENCE_STRATWITH_FOR_KUWAIT = hashmap.get("RESIDENCE_STRATWITH_FOR_KUWAIT");
		RESIDENCE_STRATWITH_FOR_OMAN = hashmap.get("RESIDENCE_STRATWITH_FOR_OMAN");
		RESIDENCE_STRATWITH_FOR_BAHARAIN = hashmap.get("RESIDENCE_STRATWITH_FOR_BAHARAIN");//Not Yet Used

		MESSAGE_FOR_MOBILE_NO_START_WITH_KUWAIT = hashmap.get("MESSAGE_FOR_MOBILE_NO_START_WITH_KUWAIT");
		MESSAGE_FOR_RESITANCE_NO_START_WITH_KUWAIT = hashmap.get("MESSAGE_FOR_RESITANCE_NO_START_WITH_KUWAIT");

		MESSAGE_FOR_MOBILE_NO_START_WITH_OMAN= hashmap.get("MESSAGE_FOR_MOBILE_NO_START_WITH_OMAN");
		MESSAGE_FOR_RESITANCE_NO_START_WITH_OMAN =  hashmap.get("MESSAGE_FOR_RESITANCE_NO_START_WITH_OMAN");

		MESSAGE_FOR_MOBILE_NO_START_WITH_BAHARAIN = hashmap.get("MESSAGE_FOR_MOBILE_NO_START_WITH_BAHARAIN");
		MESSAGE_FOR_RESITANCE_NO_START_WITH_BAHARAIN = hashmap.get("MESSAGE_FOR_RESITANCE_NO_START_WITH_BAHARAIN");
		BRANCH_CODE_ONLINE = hashmap.get("BRANCH_CODE_ONLINE");
		MOBILE_CONTACT = hashmap.get("MOBILE_CONTACT");
		RESIDENCE_CONTACT = hashmap.get("RESIDENCE_CONTACT");
		PASSWORD = hashmap.get("PASSWORD");
		WUPASSWORD =  hashmap.get("WUPASSWORD");

		DEAL_TRACKER_KW_CONBY=hashmap.get("DEAL_TRACKER_KW_CONBY");
		DEAL_TRACKER_OM_CONBY=hashmap.get("DEAL_TRACKER_OM_CONBY");

		//CUSTOMER FOR NEW ID TYPE
		CIVIL_ID_NEW=hashmap.get("CIVIL_ID_NEW");
		CIVIL_ID_VALUE=hashmap.get("CIVIL_ID_VALUE");

		//CUSTOMER Online Login
		USER=hashmap.get("USER");
		ADMIN=hashmap.get("ADMIN");
		STATUS_ACTIVE=hashmap.get("STATUS_ACTIVE");
		STATUS_LOCK=hashmap.get("STATUS_LOCK");

		//knet message properties
		knetsuccess = hashmap.get("knetsuccess");
		knetsuccesspage = hashmap.get("knetsuccesspage");
		kneterror = hashmap.get("kneterror");
		entercardnumbermsg = hashmap.get("entercardnumbermsg");
		carderrormsg = hashmap.get("carderrormsg");
		knetTransactionerror = hashmap.get("knetTransactionerror");
		knetTransactionContact = hashmap.get("knetTransactionContact");
		//complaint action
		OPEN= hashmap.get("OPEN");
		CLOSE= hashmap.get("CLOSE");
		PENDING= hashmap.get("PENDING");
		BANK= hashmap.get("BANK");
		E= hashmap.get("E");

		COMPLAINT_ACTION_GROUP_OPEN = hashmap.get("COMPLAINT_ACTION_GROUP_OPEN");

		ASSIGNED_CUSTOMER_HELP_DESK = hashmap.get("ASSIGNED_CUSTOMER_HELP_DESK");
		ASSIGNED_CUSTOMER_SUPPORT_SERVICE = hashmap.get("ASSIGNED_CUSTOMER_SUPPORT_SERVICE");
		ASSIGNED_BRANCH = hashmap.get("ASSIGNED_BRANCH");

		//Assigned To Code constants        
		ASSIGNED_TO_CENTRAL_HELP_DESK = hashmap.get("ASSIGNED_TO_CENTRAL_HELP_DESK");
		ASSIGNED_TO_CUSTOMER_SUPPORT_SERVICES = hashmap.get("ASSIGNED_TO_CUSTOMER_SUPPORT_SERVICES");
		ASSIGNED_TO_BRANCH = hashmap.get("ASSIGNED_TO_BRANCH");
		ASSIGNED_TO_BANK = hashmap.get("ASSIGNED_TO_BANK");
		ASSIGNED_TO_AGENT = hashmap.get("ASSIGNED_TO_AGENT");

		//Test Key Master and Value
		SERIAL_INDICATOR_VALUE_DAILY   = hashmap.get("SERIAL_INDICATOR_VALUE_DAILY");
		SERIAL_INDICATOR_VALUE_WEEKLY  = hashmap.get("SERIAL_INDICATOR_VALUE_WEEKLY");
		SERIAL_INDICATOR_VALUE_MONTHLY = hashmap.get("SERIAL_INDICATOR_VALUE_MONTHLY");
		SERIAL_INDICATOR_VALUE_CONTINUE = hashmap.get("SERIAL_INDICATOR_VALUE_CONTINUE");

		END_OF_SERIAL_VALUE_NEW = hashmap.get("END_OF_SERIAL_VALUE_NEW");
		END_OF_SERIAL_VALUE_REUSE= hashmap.get("END_OF_SERIAL_VALUE_REUSE");
		INDICATOR_SEND = hashmap.get("INDICATOR_SEND");
		INDICATOR_RECEIVE = hashmap.get("INDICATOR_RECEIVE");

		JV_REASON = hashmap.get("JV_REASON");

		//KIOSK XML CONSTANTS
		RESPONSE_ID=hashmap.get("RESPONSE_ID");
		HOST_NAME=hashmap.get("HOST_NAME"); 
		PORT_NO=hashmap.get("PORT_NO");
		FEEDCASH_STATUS=hashmap.get("FEEDCASH_STATUS");
		FALSE=hashmap.get("FALSE");
		TRUE=hashmap.get("TRUE");
		RECEIPT_TYPE=hashmap.get("RECEIPT_TYPE");

		DAILYLIMIT = hashmap.get("DAILYLIMIT");

		APPROVED=hashmap.get("APPROVED");
		UNAPPROVED=hashmap.get("UNAPPROVED");
		NOTAPPROVED=hashmap.get("NOTAPPROVED");
		NEGOTIATED = hashmap.get("NEGOTIATED");
		BIZ_COMPONENT_IDENTITY_TYPE = hashmap.get("BIZ_COMPONENT_IDENTITY_TYPE");

		CashCode=hashmap.get("CashCode");
		KNETCode=hashmap.get("KNETCode");
		ChequeCode=hashmap.get("ChequeCode");
		BankTransferCode=hashmap.get("BankTransferCode");
		NameCheckAlertMsg=hashmap.get("NameCheckAlertMsg");


		IMG_LOCATION=hashmap.get("IMG_LOCATION");
		ScanInd_N=hashmap.get("ScanInd_N");
		ScanInd_D=hashmap.get("ScanInd_D");
		ScanInd_A=hashmap.get("ScanInd_A");
		Scan_Req_Y=hashmap.get("Scan_Req_Y");


		CASH=hashmap.get( "CASH");
		SALE=	hashmap.get( "SALE");
		PURCHASE=	hashmap.get( "PURCHASE");

		KUWAIT=hashmap.get( "KUWAIT");
		OMAN=	hashmap.get( "OMAN");

		ACTIVEEMPLOYEE=hashmap.get( "ACTIVEEMPLOYEE");
		INACTIVEEMPLOYEE=	hashmap.get( "INACTIVEEMPLOYEE");
		ITIPAddress = hashmap.get("ITIPAddress");

		//western union 
		WU_APP_DELAY_TIME = hashmap.get("WU_APP_DELAY_TIME");
		WU_APP_DELAY_SLEEP_INTERVAL = hashmap.get("WU_APP_DELAY_SLEEP_INTERVAL");
		WU_BANK_CODE=hashmap.get("WU_BANK_CODE");
		MONEYGRAM_BANK_CODE = hashmap.get("MONEYGRAM_BANK_CODE");
		HOMESEND_BANK_CODE = hashmap.get("HOMESEND_BANK_CODE");
		TERMINAL_ID = hashmap.get("TERMINAL_ID");
		JAVA_TRANSACTION_YES = hashmap.get("JAVA_TRANSACTION_YES");
		WU_INOUT_SEND = hashmap.get("WU_INOUT_SEND");
		WU_INOUT_RECEIVE = hashmap.get("WU_INOUT_RECEIVE");
		WU_COLLECT_DOCUMENTNO = hashmap.get("WU_COLLECT_DOCUMENTNO");
		APPLICATION_STATUS = hashmap.get("APPLICATION_STATUS");
		V = hashmap.get("V");
		IKON = hashmap.get("IKON");
		USD = hashmap.get("USD");

		// ARCMATE SCANNING

		CHECK = hashmap.get("CHECK");
		SCAN = hashmap.get("SCAN");
		VIEW = hashmap.get("VIEW");
		MODIFY = hashmap.get("MODIFY");
		OCR = hashmap.get("OCR");
		NON_OCR = hashmap.get("NON_OCR");
		BOTH_VIEW = hashmap.get("BOTH_VIEW");
		BEDOUIN = hashmap.get("BEDOUIN");
		PASSPORT = hashmap.get("PASSPORT");
		GCC_NATIONAL_ID = hashmap.get("GCC_NATIONAL_ID");
		LICENSE_NO = hashmap.get("LICENSE_NO");
		CHECK_DOCUMENT = hashmap.get("CHECK_DOCUMENT");
		CHECK_FILE = hashmap.get("CHECK_FILE");
		
		GL_LEVEL=hashmap.get("GL_LEVEL");
		COUNTRY_CURRENCY_LEVEL=hashmap.get("COUNTRY_CURRENCY_LEVEL");
		
		ACCEPT=hashmap.get("ACCEPT");
		REJECT=hashmap.get("REJECT");
		ACCEPT_VALUE=hashmap.get("ACCEPT_VALUE");
		REJECT_VALUE=hashmap.get("REJECT_VALUE");
		PENDING_VALUE=hashmap.get("PENDING_VALUE");
		
		INDICATOR_0=hashmap.get("INDICATOR_0");
		INDICATOR_1=hashmap.get("INDICATOR_1");
		BANKCHANNEL= hashmap.get("BANKCHANNEL");
		CASHFORONLINE= hashmap.get("CASHFORONLINE");
		SPOT= hashmap.get("SPOT");
		TOM= hashmap.get("TOM");
		BAHRAIN= hashmap.get("BAHRAIN");
		CUSTOMER_URL=hashmap.get("CUSTOMER_URL");
		DOCUMENT_CODE_FOR_PLACEORDER=hashmap.get("DOCUMENT_CODE_FOR_PLACEORDER");
		
		// ICASH PRODUCT
		AgentStatusICash=hashmap.get("AgentStatusICash");
		ModeofTransferEFT=hashmap.get("ModeofTransferEFT");
		ModeofTransferTT=hashmap.get("ModeofTransferTT");
		COUNTRY_CODE_1=hashmap.get("COUNTRY_CODE_1");
		
		
		Y=hashmap.get("Y");
		W=hashmap.get("W");
		I=hashmap.get("I");
		
		WesternUnionPassword = hashmap.get("WesternUnionPassword");
		LoginPassword = hashmap.get("LoginPassword");
		Old_Record= hashmap.get("Old_Record");
		BNFADDR3= hashmap.get("BNFADDR3");
		
		IDTYPE_CIVILID =  hashmap.get("IDTYPE_CIVILID");
		IDTYPE_CIVILID_NEW =  hashmap.get("IDTYPE_CIVILID_NEW");
		IDTYPE_PASSPORT =  hashmap.get("IDTYPE_PASSPORT");
		
		
		PARTIAL = hashmap.get("PARTIAL");
		ARMS =  hashmap.get("ARMS");
		KIOSK = hashmap.get("KIOSK");
		
		StopPaymentForm =  hashmap.get("StopPaymentForm");
		RefundForm =  hashmap.get("RefundForm");
		MiscelleaneousPaymentForm =  hashmap.get("MiscelleaneousPaymentForm");
		MiscelleaneousReceiptForm =  hashmap.get("MiscelleaneousReceiptForm");
		
		EncriptionKey = hashmap.get("EncriptionKey");
		
		CR = hashmap.get("CR");
		Share_Capital = hashmap.get("Share_Capital");
		//For Oman
		OMSMARTCARDENV=hashmap.get("OMSMARTCARDENV");
		OM_SMART_CARD_APPLICATION_TYPE=hashmap.get("OM_SMART_CARD_APPLICATION_TYPE");
		WU_FILE_UPLOAD_PATH=hashmap.get("WU_FILE_UPLOAD_PATH");
		WU_FILE_NAME=hashmap.get("WU_FILE_NAME");
		COMPANY_NAME_KW = hashmap.get("COMPANY_NAME_KW");
		COMPANY_NAME_OM = hashmap.get("COMPANY_NAME_OM");
		COMPANY_NAME_BH = hashmap.get("COMPANY_NAME_BH");
		
		localConstactTypeId = hashmap.get("localConstactTypeId");
		homeConstactTypeId = hashmap.get("homeConstactTypeId");
		
		TeleFollowUpCodeClose = hashmap.get("TeleFollowUpCodeClose");
		TeleFollowUpCodeDate =  hashmap.get("TeleFollowUpCodeDate");
		//FOR AMTB COUPON
		AMTBC =hashmap.get("A");
		GO_GREEN_REMIT_AMT_LIMIT =  hashmap.get("GO_GREEN_REMIT_AMT_LIMIT");
		EMAILVERFICATIONAPP=hashmap.get("EMAILVERFICATIONAPP");
		
		String ASSOIdStr =hashmap.get("ASSO_ID");
		ASSO_ID= new BigDecimal(ASSOIdStr);
		DOCUMENT_CODE_FOR_PROMOTION= hashmap.get("DOCUMENT_CODE_FOR_PROMOTION");
		
		StopPaymentLetterBody1 = hashmap.get("StopPaymentLetterBody1");
		StopPaymentLetterBody2 = hashmap.get("StopPaymentLetterBody2");
		StopPaymentLetterBody3  = hashmap.get("StopPaymentLetterBody3");
		
		GSM =  hashmap.get("GSM");
		
		DeclarationReportforCash =  hashmap.get("DeclarationReportforCash");
		DeclarationReportTotalAmt =  hashmap.get("DeclarationReportTotalAmt");
		
		PepNoteBody1 = hashmap.get("PepNoteBody1");
		PepDeclarationBody = hashmap.get("PepDeclarationBody");
		PepDeclarationBody1 = hashmap.get("PepDeclarationBody1");
		CORPORATE_ID_TYPE = hashmap.get("CORPORATE_ID_TYPE");
		
		
		WUH2H_SELECT_DECLARATION  = hashmap.get("WUH2H_SELECT_DECLARATION");
		WUH2H_UNSELECT_DECLARATION  = hashmap.get("WUH2H_UNSELECT_DECLARATION");
		
	}



}

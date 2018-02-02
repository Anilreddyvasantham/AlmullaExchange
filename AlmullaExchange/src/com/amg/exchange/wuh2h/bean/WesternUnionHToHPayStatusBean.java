package com.amg.exchange.wuh2h.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.wuh2h.service.IWUH2HService;

@SuppressWarnings({ "unused" })
@Component("wuh2hpay")
@Scope("session")
public class WesternUnionHToHPayStatusBean<T> implements Serializable {/*
	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger log = Logger.getLogger(WesternUnionHToHPayStatusBean.class);

	
	 * Autowire Configuration
	 
	
	@Autowired
	IWUH2HService iwuh2hService;
	
	@Autowired
	IGeneralService<T> generalService;
	
	public WesternUnionHToHPayStatusBean() {
	}

	
	 * Western Union Page Navigation
	 
	public void pageNavigation() {
		try {
			clear();
			setMtcn(null);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hpaystatus.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	List<PaymentTransaction> paymentTransactionList = new ArrayList<PaymentTransaction>();
	
	public void wuh2hPayStatus() {

		PayStatusInquiryRequestData request = null;
		PayStatusInquiryReply response = null;
		String referenceno = null;
		try {

			clear();
			paymentTransactionList.clear();
			if(getWuTransReferenceNo()!=null){
				setWuTransReferenceNo(null);
			}
			
			referenceno = iwuh2hService.getNextReferenceNo();
			setWuTransReferenceNo(referenceno);
			
			response = new PayStatusInquiryReply();
			Certification.keystoreSetup();
			request = fetchWUH2HPayStatusRequest();

			convertedString = XMLGenerator.generateXML(request);
			xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			xmlstring = xmlhead + "\n" + convertedString;
			writeToFile("paystatusrequest", xmlstring);

			PayStatusInquiryReplyHelper helper = null;
			helper = PayStatusBO.payStatus(request);

			if (helper.getErrorReply() == null) {
				response = helper.getPayStatusInquiryReply();

				convertedString = XMLGenerator.generateXML(response);
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead + "\n" + convertedString;
				writeToFile("paystatusreply", xmlstring);
				System.out.println(xmlstring);

				paymentTransactionList = new ArrayList<PaymentTransaction>();
				paymentTransactionList = response.getPaymentTransactions()
						.getPaymentTransaction();

				if (paymentTransactionList.size() > 0) {
					for (PaymentTransaction paymentTransaction : paymentTransactionList) {

						setSenderFirstName(paymentTransaction.getSender()
								.getName().getFirstName());
						setSenderLastName(paymentTransaction.getSender()
								.getName().getLastName());
						setReceiverFirstName(paymentTransaction.getReceiver()
								.getName().getFirstName());
						setReceiverLastName(paymentTransaction.getReceiver()
								.getName().getLastName());
						setMoneyTransferKey(paymentTransaction
								.getMoneyTransferKey());
						setOriginPrincipleAmount(BigDecimal
								.valueOf(paymentTransaction.getFinancials()
										.getOriginatorsPrincipalAmount()));

						setOriginPrincipleAmount(convertLongToBigDecimal(
								paymentTransaction.getFinancials()
										.getOriginatorsPrincipalAmount(),
								paymentTransaction.getPaymentDetails()
										.getOriginatingCountryCurrency()
										.getIsoCode().getCurrencyCode()));
						
						BigDecimal countryid = iwuh2hService.getCountryIDFromCode(paymentTransaction.getPaymentDetails().getOriginatingCountryCurrency().getIsoCode().getCountryCode());
						String countryname = 	iwuh2hService.getNameDescription(countryid,sessionStateManage.getLanguageId(), "country");
						setOriginISOCountry(countryname);
						BigDecimal currencyId= generalService.getCurrencyIDFromQuote(paymentTransaction
								.getPaymentDetails()
								.getOriginatingCountryCurrency().getIsoCode()
								.getCurrencyCode());						
						String currencyname = 	generalService.getCurrencyName(currencyId);
						
						setOriginISOCurrency(currencyname);
						setPayStatus(paymentTransaction
								.getPayStatusDescription());
						setFillDate(paymentTransaction.getFilingDate());
						setFillTime(paymentTransaction.getFilingTime());
					}
				}
			} else {
				
				setErrorMessage(helper.getErrorReply().getError());
				RequestContext.getCurrentInstance().execute("error.show();");

				convertedString = XMLGenerator.generateXML(helper
						.getErrorReply());
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead + "\n" + convertedString;
				writeToFile("paystatusreply", xmlstring);
				System.out.println(xmlstring);
				clear();
				return;
			}

		} catch (NullPointerException ne) {
			log.info("Null Pointer" + ne.getMessage());
			setErrorMessage("Method Name::wuh2hPayStatus()");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public PayStatusInquiryRequestData fetchWUH2HPayStatusRequest() {
		
		PayStatusInquiryRequestData request = null;
		ForeignRemoteSystem foreignRemoteSystem = null;
		Channel channel = null;
		PaymentTransaction paymentTransaction = null;

		try {

			request = new PayStatusInquiryRequestData();

			channel = new Channel();
			channel.setType(ChannelType.H_2_H);
			channel.setName(channelName);
			channel.setVersion(channelVersion);
			request.setChannel(channel);

			// Foreign remote system info
			foreignRemoteSystem = new ForeignRemoteSystem();
			foreignRemoteSystem.setReferenceNo(getWuTransReferenceNo());
			foreignRemoteSystem.setCounterId(remoteCounterId);
			foreignRemoteSystem.setIdentifier(remoteIdentifier);
			request.setForeignRemoteSystem(foreignRemoteSystem);
			request.setMtcn(getMtcn());

		} catch (Exception e) {
			return null;
		}
		return request;
	}

	
	 * Load WUH2H properties
	 
	private static String propertyFilePath = "/wuh2h.properties";
	static Properties prop = null;
	static InputStream input = null;
	static String channelName = null;
	static String channelVersion = null;
	static String remoteRefno = null;
	static String remoteCounterId = null;
	static String remoteIdentifier = null;
	static String xmlStorePath = null;

	static {

		prop = new Properties();
		input = WesternUnionHToHBean.class.getClassLoader()
				.getResourceAsStream(propertyFilePath);
		try {
			prop.load(input);
			channelName = prop.getProperty("WUH2H.CHANNEL_NAME");

			remoteRefno = prop.getProperty("WUH2H.FOREIGN_REMOTE_REFNO");
			remoteCounterId = prop
					.getProperty("WUH2H.FOREIGN_REMOTE_COUNTERID");
			remoteIdentifier = prop
					.getProperty("WUH2H.FOREIGN_REMOTE_IDENTIFIER");
			xmlStorePath = prop.getProperty("WUH2H.XML_STORE_PATH");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		setSenderFirstName(null);
		setSenderLastName(null);
		setReceiverFirstName(null);
		setReceiverLastName(null);
		setMoneyTransferKey(null);
		setOriginPrincipleAmount(null);
		setOriginISOCountry(null);
		setOriginISOCurrency(null);
		setPayStatus(null);
		//setMtcn(null);
		setWuTransReferenceNo(null);
		paymentTransactionList.clear();
	}

	private String convertedString = null;
	private String xmlhead = null;
	private String xmlstring = null;

	public void writeToFile(String filename, String content) {

		String suffix = new SimpleDateFormat("yyyyMMddhhmmss")
				.format(new Date());
		filename = filename + "_" + suffix + ".xml";
		File file = new File(xmlStorePath + filename);

		try (FileOutputStream fop = new FileOutputStream(file)) {

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	 * Required properties
	 
	private String mtcn;
	private String senderFirstName;
	private String senderLastName;
	private String receiverFirstName;
	private String receiverLastName;
	private String payStatus;
	private String moneyTransferKey;
	private String originISOCountry;
	private String originISOCurrency;
	private BigDecimal originPrincipleAmount;
	private String errorMessage;

	public String getMtcn() {
		return mtcn;
	}

	public void setMtcn(String mtcn) {
		this.mtcn = mtcn;
	}

	public String getSenderFirstName() {
		return senderFirstName;
	}

	public void setSenderFirstName(String senderFirstName) {
		this.senderFirstName = senderFirstName;
	}

	public String getSenderLastName() {
		return senderLastName;
	}

	public void setSenderLastName(String senderLastName) {
		this.senderLastName = senderLastName;
	}

	public String getReceiverFirstName() {
		return receiverFirstName;
	}

	public void setReceiverFirstName(String receiverFirstName) {
		this.receiverFirstName = receiverFirstName;
	}

	public String getReceiverLastName() {
		return receiverLastName;
	}

	public void setReceiverLastName(String receiverLastName) {
		this.receiverLastName = receiverLastName;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getMoneyTransferKey() {
		return moneyTransferKey;
	}

	public void setMoneyTransferKey(String moneyTransferKey) {
		this.moneyTransferKey = moneyTransferKey;
	}

	public String getOriginISOCountry() {
		return originISOCountry;
	}

	public void setOriginISOCountry(String originISOCountry) {
		this.originISOCountry = originISOCountry;
	}

	public String getOriginISOCurrency() {
		return originISOCurrency;
	}

	public void setOriginISOCurrency(String originISOCurrency) {
		this.originISOCurrency = originISOCurrency;
	}

	public BigDecimal getOriginPrincipleAmount() {
		return originPrincipleAmount;
	}

	public void setOriginPrincipleAmount(BigDecimal originPrincipleAmount) {
		this.originPrincipleAmount = originPrincipleAmount;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal convertLongToBigDecimal(Long value, String currency) {
		BigDecimal convertedValue = new BigDecimal(0);
		Long hundred = new Long("100");
		int decimalcount;
		if (currency.equals("KWD")) {
			decimalcount = 3;
		} else {
			decimalcount = 2;
		}
		// convertedValue = BigDecimal.valueOf(value==(Long)null ? new
		// Long(0):(double)(value/hundred)).setScale(decimalcount,
		// BigDecimal.ROUND_UP);

		BigDecimal bd = BigDecimal.valueOf(value == null ? new Long(0) : value);

		BigDecimal bd1 = bd.movePointLeft(2);
		convertedValue = bd1.setScale(decimalcount);
		return convertedValue;
	}
	
	private String fillTime;
	private String fillDate;

	public String getFillTime() {
		return fillTime;
	}

	public void setFillTime(String fillTime) {
		this.fillTime = fillTime;
	}

	public String getFillDate() {
		return fillDate;
	}

	public void setFillDate(String fillDate) {
		this.fillDate = fillDate;
	}
	
	private String wuTransReferenceNo;

	public String getWuTransReferenceNo() {
		return wuTransReferenceNo;
	}

	public void setWuTransReferenceNo(String wuTransReferenceNo) {
		this.wuTransReferenceNo = wuTransReferenceNo;
	}
	

*/}

package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.bean.ForeignLocalCurrencyDataTable;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.TempCollectDetail;
import com.amg.exchange.foreigncurrency.model.TempCollection;
import com.amg.exchange.foreigncurrency.model.TempForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.model.ShoppingCartDetails;
import com.amg.exchange.remittance.model.ViewStock;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;


@Component("remittanceShoppingCart")
@Scope("session")
public class RemittanceShoppingCart  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(RemittanceShoppingCart.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	@Autowired
	ApplicationContext appContext;
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;

	private String customerIdentityInt;
	private BigDecimal customerId;
	private String customerFullName;
	private String customerLocalFullName;
	private String exceptionMessage;
	public String paidbyCustMach;
	private String denominationchecking;
	private BigDecimal collectedRefundAmount;
	private String userName;
	private BigDecimal paidAmount;
	private BigDecimal refundAmount;
	private BigDecimal netAmount;
	private BigDecimal loyalityAmount;
	private boolean booRenderRefund = false;
	private boolean booSaveButton = false;
	private BigDecimal tempCollectId;
	private BigDecimal documentNo;
	private String createdBy;

	private List<ShoppingCartDataTableBean> shoppingcartDTList = new ArrayList<ShoppingCartDataTableBean>();
	private CopyOnWriteArrayList<ShoppingCartDataTableBean> lstselectedrecord = new CopyOnWriteArrayList<ShoppingCartDataTableBean>();
	private ArrayList<ForeignLocalCurrencyDataTable> lstRefundData = new ArrayList<ForeignLocalCurrencyDataTable>();
	private TempCollection tempCollect = new TempCollection();



	// to fetch shopping cart records remittance
	public void getShoppingCartDetails(BigDecimal customerId) {
		setPaidbyCustMach(null);
		shoppingcartDTList.clear();
		List<ShoppingCartDetails> shoppingCartList = new ArrayList<ShoppingCartDetails>();
		try {
			shoppingCartList = iPersonalRemittanceService.getShoppingCartDetails(customerId);
			if (shoppingCartList.size() > 0) {
				for (ShoppingCartDetails shoppingCartDetails : shoppingCartList) {
					ShoppingCartDataTableBean shoppingCartDataTableBean = new ShoppingCartDataTableBean();
					if (shoppingCartDetails.getRemittanceApplicationId() != null)
						shoppingCartDataTableBean.setRemittanceApplicationId(shoppingCartDetails.getRemittanceApplicationId());
					if (shoppingCartDetails.getApplicationType() != null)
						shoppingCartDataTableBean.setApplicationType(shoppingCartDetails.getApplicationType());
					if (shoppingCartDetails.getApplicationTypeDesc() != null)
						shoppingCartDataTableBean.setApplicationTypeDesc(shoppingCartDetails.getApplicationTypeDesc());
					if (shoppingCartDetails.getBeneficiaryAccountNo() != null)
						shoppingCartDataTableBean.setBeneficiaryAccountNo(shoppingCartDetails.getBeneficiaryAccountNo());
					if (shoppingCartDetails.getBeneficiaryBank() != null)
						shoppingCartDataTableBean.setBeneficiaryBank(shoppingCartDetails.getBeneficiaryBank());
					if (shoppingCartDetails.getBeneficiaryBranch() != null)
						shoppingCartDataTableBean.setBeneficiaryBranch(shoppingCartDetails.getBeneficiaryBranch());
					if (shoppingCartDetails.getBeneficiaryFirstName() != null)
						shoppingCartDataTableBean.setBeneficiaryFirstName(shoppingCartDetails.getBeneficiaryFirstName());
					if (shoppingCartDetails.getBeneficiarySecondName() != null)
						shoppingCartDataTableBean.setBeneficiarySecondName(shoppingCartDetails.getBeneficiarySecondName());
					if (shoppingCartDetails.getBeneficiaryThirdName() != null)
						shoppingCartDataTableBean.setBeneficiaryThirdName(shoppingCartDetails.getBeneficiaryThirdName());
					if (shoppingCartDetails.getBeneficiaryFourthName() != null)
						shoppingCartDataTableBean.setBeneficiaryFourthName(shoppingCartDetails.getBeneficiaryFourthName());
					if (shoppingCartDetails.getBeneficiaryId() != null)
						shoppingCartDataTableBean.setBeneficiaryId(shoppingCartDetails.getBeneficiaryId());
					if (shoppingCartDetails.getBeneficiarySwiftAddrOne() != null)
						shoppingCartDataTableBean.setBeneficiaryInterBankOne(shoppingCartDetails.getBeneficiarySwiftAddrOne());
					if (shoppingCartDetails.getBeneficiarySwiftAddrTwo() != null)
						shoppingCartDataTableBean.setBeneficiaryInterBankTwo(shoppingCartDetails.getBeneficiarySwiftAddrTwo());
					if (shoppingCartDetails.getBeneficiarySwiftBankOne() != null)
						shoppingCartDataTableBean.setBeneficiarySwiftBankOne(shoppingCartDetails.getBeneficiarySwiftBankOne());
					if (shoppingCartDetails.getBeneficiarySwiftBankTwo() != null)
						shoppingCartDataTableBean.setBeneficiarySwiftBankTwo(shoppingCartDetails.getBeneficiarySwiftBankTwo());
					if (shoppingCartDetails.getBeneficiaryName() != null)
						shoppingCartDataTableBean.setBeneficiaryName(shoppingCartDetails.getBeneficiaryName());
					if (shoppingCartDetails.getCompanyId() != null)
						shoppingCartDataTableBean.setCompanyId(shoppingCartDetails.getCompanyId());
					if (shoppingCartDetails.getDocumentFinanceYear() != null)
						shoppingCartDataTableBean.setDocumentFinanceYear(shoppingCartDetails.getDocumentFinanceYear());
					if (shoppingCartDetails.getDocumentId() != null)
						shoppingCartDataTableBean.setDocumentId(shoppingCartDetails.getDocumentId());
					if (shoppingCartDetails.getForeignTranxAmount() != null)
						shoppingCartDataTableBean.setForeignTranxAmount(GetRound.roundBigDecimal(shoppingCartDetails.getForeignTranxAmount(),
								foreignLocalCurrencyDenominationService.getDecimalPerCurrency(shoppingCartDetails.getForeignCurrency())));
					if (shoppingCartDetails.getLocalTranxAmount() != null)
						shoppingCartDataTableBean.setLocalTranxAmount(GetRound.roundBigDecimal(shoppingCartDetails.getLocalTranxAmount(),
								foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					if (shoppingCartDetails.getLocalChargeAmount() != null)
						shoppingCartDataTableBean.setLocalChargeAmount(GetRound.roundBigDecimal(shoppingCartDetails.getLocalChargeAmount(),
								foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					if (shoppingCartDetails.getLocalCommisionAmount() != null)
						shoppingCartDataTableBean.setLocalCommisionAmount(GetRound.roundBigDecimal(shoppingCartDetails.getLocalCommisionAmount(),
								foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					if (shoppingCartDetails.getLocalDeliveryAmount() != null)
						shoppingCartDataTableBean.setLocalDeliveryAmount(shoppingCartDetails.getLocalDeliveryAmount());
					if (shoppingCartDetails.getIsActive() != null)
						shoppingCartDataTableBean.setIsActive(shoppingCartDetails.getIsActive());
					if (shoppingCartDetails.getLocalNextTranxAmount() != null)
						shoppingCartDataTableBean.setLocalNextTranxAmount(GetRound.roundBigDecimal(shoppingCartDetails.getLocalNextTranxAmount(),
								foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					if (shoppingCartDetails.getCustomerId() != null)
						shoppingCartDataTableBean.setCustomerId(shoppingCartDetails.getCustomerId());
					if (shoppingCartDetails.getExchangeRateApplied() != null)
						shoppingCartDataTableBean.setExchangeRateApplied(shoppingCartDetails.getExchangeRateApplied());
					if (shoppingCartDetails.getApplicationId() != null)
						shoppingCartDataTableBean.setApplicationDetailsId(shoppingCartDetails.getApplicationId());
					if (shoppingCartDetails.getDocumentNo() != null)
						shoppingCartDataTableBean.setDocumentNo(shoppingCartDetails.getDocumentNo());
					if (shoppingCartDetails.getForeignCurrency() != null)
						shoppingCartDataTableBean.setForeigncurrency(shoppingCartDetails.getForeignCurrency());
					if (shoppingCartDetails.getForeignCurrencyDesc() != null)
						shoppingCartDataTableBean.setForeignCurrencyDesc(shoppingCartDetails.getForeignCurrencyDesc());
					if (shoppingCartDetails.getLocalCurrency() != null)
						shoppingCartDataTableBean.setLocalcurrency(shoppingCartDetails.getLocalCurrency());
					if (shoppingCartDetails.getSpldeal() != null) {
						shoppingCartDataTableBean.setSpldeal(shoppingCartDetails.getSpldeal());
						shoppingCartDataTableBean.setSpldealStatus(Constants.YES);
					} else {
						shoppingCartDataTableBean.setSpldealStatus(Constants.NO);
					}
					if (shoppingCartDetails.getLoyaltsPointencahsed() != null)
						shoppingCartDataTableBean.setLoyaltsPointencahsed(shoppingCartDetails.getLoyaltsPointencahsed());
					if(shoppingCartDetails.getLoyaltsPointIndicator() != null){
						shoppingCartDataTableBean.setLoyaltsPointIndicator(shoppingCartDetails.getLoyaltsPointIndicator());
					}

					shoppingCartDataTableBean.setSelectedrecord(Boolean.FALSE);
					shoppingCartDataTableBean.setDeleteStatus(Boolean.TRUE);
					shoppingCartDataTableBean.setBooReportEligible(true);

					shoppingcartDTList.add(shoppingCartDataTableBean);

				}
			}
		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// selected in remittance to make save in remittance
	public void selectAnyOneRecToProcess(ShoppingCartDataTableBean shoppingCartDataTableBean) {
		setPaidbyCustMach(null);
		setBooRenderRefund(false);
		setBooSaveButton(false);
		if (shoppingCartDataTableBean.getSelectedrecord()) {
			for (ShoppingCartDataTableBean lstShpCrt : shoppingcartDTList) {
				if(lstselectedrecord != null && !lstselectedrecord.isEmpty() && lstShpCrt.getApplicationDetailsId().compareTo(lstselectedrecord.get(0).getApplicationDetailsId())==0){
					lstShpCrt.setSelectedrecord(false);
					break;
				}
			}
			lstselectedrecord.clear();
			lstselectedrecord.add(shoppingCartDataTableBean);
			setDocumentNo(shoppingCartDataTableBean.getDocumentNo());
		}else{
			setDocumentNo(null);
			shoppingCartDataTableBean.setSelectedrecord(false);
			lstselectedrecord.clear();
		}
	}

	// collect the records from temp collection and collection
	public void fetchTempCollectDetails(){

		HashMap<String,Object> lstTempCollect = iPersonalRemittanceService.fetchTempCollection(new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION),getCustomerId(),lstselectedrecord.get(0).getLocalNextTranxAmount());

		if(lstTempCollect != null && lstTempCollect.size() != 0){
			
			log.info("Temp Object Size : "+lstTempCollect.toString());

			List<TempCollection> tempColl = (List<TempCollection>) lstTempCollect.get("TempCollect");

			if(tempColl != null && tempColl.size() != 0){
				
				log.info("Temp Collect Size : "+tempColl.size());

				List<TempCollectDetail> tempCollDt = (List<TempCollectDetail>) lstTempCollect.get("TempCollectDT");
				
				if(tempCollDt != null && tempCollDt.size() != 0){
					
					log.info("Temp Collect Dt Size : "+tempCollDt.size());
					
					List<TempForeignCurrencyAdjust> tempForeCurrAdj = (List<TempForeignCurrencyAdjust>) lstTempCollect.get("TempForeCurrAdj");
					
					if(tempForeCurrAdj != null && tempForeCurrAdj.size() != 0){
						
						log.info("Temp Currency Adj Size : "+tempForeCurrAdj.size());

						if(tempColl.size() == 1){

							TempCollection tempCollection = tempColl.get(0);

							setNetAmount(tempCollection.getNetAmount());
							setPaidAmount(tempCollection.getPaidAmount());
							setRefundAmount(tempCollection.getRefoundAmount());
							if(getUserName() == null){
								setUserName(tempCollection.getCreatedBy());
							}
							setCreatedBy(tempCollection.getCreatedBy());
							setTempCollectId(tempCollection.getCollectionId());
							setTempCollect(tempCollection);
							setLoyalityAmount(BigDecimal.ZERO);

							log.info("Paid Amount : "+getPaidAmount()+ " : Net Amount :" + getNetAmount()+ " : Refund Amount :" + getRefundAmount());

							BigDecimal collAmt = BigDecimal.ZERO;
							for (TempCollectDetail tempCollectDetail : tempCollDt) {
								collAmt = collAmt.add(tempCollectDetail.getCollAmt());
								if(tempCollectDetail.getCollectionMode().equals(Constants.V)){
									setLoyalityAmount(tempCollectDetail.getCollAmt());
								}
							}

							log.info("only Procedure Call : Collect Details Amount : "+collAmt);

							if(getPaidAmount().compareTo(collAmt)==0){
								if(getRefundAmount().compareTo(BigDecimal.ZERO)==0){
									// no refund
									BigDecimal currencyAdjAmount = BigDecimal.ZERO;
									for (TempForeignCurrencyAdjust tempForeignCurrencyAdjust : tempForeCurrAdj) {
										if(tempForeignCurrencyAdjust.getTransactionType().equalsIgnoreCase(Constants.C)){
											currencyAdjAmount = currencyAdjAmount.add(tempForeignCurrencyAdjust.getAdjustmentAmount());
										}
									}
									
									if(getLoyalityAmount() != null && getLoyalityAmount().compareTo(BigDecimal.ZERO) != 0){
										currencyAdjAmount = currencyAdjAmount.add(getLoyalityAmount());
									}

									if(getPaidAmount().compareTo(currencyAdjAmount)==0){
										// only Procedure Call
										log.info("only Procedure Call : Paid Amount : "+getPaidAmount()+ " : Currency Adj Amount :" + currencyAdjAmount);
										setBooSaveButton(true);
									}else{
										// denomination problem
										setPaidbyCustMach(null);
										log.info("Denomination problem");
										setExceptionMessage("Denomination problem");
										RequestContext.getCurrentInstance().execute("alertmsg.show();");
									}

								}else{
									// with refund
									BigDecimal currencyAdjAmount = BigDecimal.ZERO;
									for (TempForeignCurrencyAdjust tempForeignCurrencyAdjust : tempForeCurrAdj) {
										if(tempForeignCurrencyAdjust.getTransactionType().equalsIgnoreCase(Constants.C)){
											currencyAdjAmount = currencyAdjAmount.add(tempForeignCurrencyAdjust.getAdjustmentAmount());
										}
									}
									
									if(getLoyalityAmount() != null && getLoyalityAmount().compareTo(BigDecimal.ZERO) != 0){
										currencyAdjAmount = currencyAdjAmount.add(getLoyalityAmount());
									}

									if(getPaidAmount().compareTo(currencyAdjAmount)==0){
										// check refund
										BigDecimal currencyAdjAmount1 = BigDecimal.ZERO;
										for (TempForeignCurrencyAdjust tempForeignCurrencyAdjust : tempForeCurrAdj) {
											if(tempForeignCurrencyAdjust.getTransactionType().equalsIgnoreCase(Constants.F)){
												currencyAdjAmount1 = currencyAdjAmount1.add(tempForeignCurrencyAdjust.getAdjustmentAmount());
											}
										}

										if(getRefundAmount().compareTo(currencyAdjAmount1)==0){
											// only Procedure Call
											log.info("only Procedure Call");
											setBooSaveButton(true);
										}else{
											// denomination need to save from screen
											refundDenominationData();
											setBooRenderRefund(true);
											setBooSaveButton(true);
										}
									}else{
										// denomination problem
										setPaidbyCustMach(null);
										log.info("Denomination problem");
										setExceptionMessage("Denomination problem");
										RequestContext.getCurrentInstance().execute("alertmsg.show();");
									}
								}
							}else{
								// denomination problem
								setPaidbyCustMach(null);
								log.info("Denomination problem");
								setExceptionMessage("Denomination problem");
								RequestContext.getCurrentInstance().execute("alertmsg.show();");
							}
						}else if(tempColl.size() > 1){
							// Multiple Rec Found
							setPaidbyCustMach(null);
							log.info(" Multiple Records Found for this Application");
							setExceptionMessage(" Multiple Records Found for this Application");
							RequestContext.getCurrentInstance().execute("alertmsg.show();");
						}else{
							// no Rec Found
							setPaidbyCustMach(null);
							log.info("no Rec Found");
							setExceptionMessage("No Records Found for this application");
							RequestContext.getCurrentInstance().execute("alertmsg.show();");
						}
						
					}else{
						// no records found for this application
						setPaidbyCustMach(null);
						log.info(" no records found for this application");
						setExceptionMessage("No Records Found for this application");
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
					}
					
				}else{
					// no records found for this application
					setPaidbyCustMach(null);
					log.info(" no records found for this application");
					setExceptionMessage("No Records Found for this application");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}

			}else{
				// no records found for this application
				setPaidbyCustMach(null);
				log.info(" no records found for this application");
				setExceptionMessage("No Records Found for this application");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}


		}else{
			// fetching temp fail
			setPaidbyCustMach(null);
			log.info("Fetching Temp Tables Fail");
			setExceptionMessage("Fetching Temp Tables Fail");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

	}


	// pay by cashier or machine
	public void selectAnyOneRecPayBy(){

		setBooRenderRefund(false);
		setBooSaveButton(false);
		if(lstselectedrecord.size() != 0){
			System.out.println(getPaidbyCustMach());

			if(getPaidbyCustMach() != null && getPaidbyCustMach().equalsIgnoreCase(Constants.C)){
				setUserName(sessionStateManage.getUserName());
			}else if(getPaidbyCustMach() != null && getPaidbyCustMach().equalsIgnoreCase(Constants.A)){
				setUserName(null);
			}else{
				setUserName(sessionStateManage.getUserName());
			}

			setDenominationchecking(Constants.DenominationNotAvailable + " FOR " + getUserName());
			fetchTempCollectDetails();
		}else{
			setPaidbyCustMach(null);
			setExceptionMessage("Please Select the Application Which is Pending");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	private void refundDenominationData() {
		lstRefundData.clear();
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		localLstData.clear();
		if (localLstData.size() == 0) {
			List<ViewStock> stockList = iPersonalRemittanceService.toCheckStockForView(
					sessionStateManage.getCountryId(),
					getUserName(),
					sessionStateManage.getBranchId(),
					sessionStateManage.getCompanyId(),
					sessionStateManage.getCurrencyId());

			int serial = 1;
			for(ViewStock viewStockObj:stockList){
				ForeignLocalCurrencyDataTable forLocalCurrencyDtObj = new ForeignLocalCurrencyDataTable();
				forLocalCurrencyDtObj.setSerial(serial);
				forLocalCurrencyDtObj.setItem(viewStockObj.getDenominationAmount());
				forLocalCurrencyDtObj.setQty("");
				forLocalCurrencyDtObj.setPrice("");
				if(viewStockObj.getCurrentStock()!=null){
					forLocalCurrencyDtObj.setStock(viewStockObj.getCurrentStock().intValue());
				}else{
					forLocalCurrencyDtObj.setStock(0);
				}
				forLocalCurrencyDtObj.setDenominationId(viewStockObj.getDenominationId());
				forLocalCurrencyDtObj.setCurrencyId(viewStockObj.getCurrencyId());
				forLocalCurrencyDtObj.setDenominationDesc(viewStockObj.getDenominationDEsc());
				forLocalCurrencyDtObj.setDenominationAmount(viewStockObj.getDenominationAmount());

				localLstData.add(forLocalCurrencyDtObj);
				serial++;
			}

		}

		/* Responsible to keep sum of total amount of cash entered */
		int totalSum = 0;
		/* Responsible to calculate sum of entered cash amount */
		for (ForeignLocalCurrencyDataTable element : localLstData) {
			if (element.getPrice().length() != 0) {
				totalSum = totalSum + Integer.parseInt(element.getPrice());
			}
		}
		setLstRefundData(localLstData);
		if(localLstData.size() != 0){
			setDenominationchecking(Constants.DenominationAvailable + " FOR " + getUserName());
		}else{
			setDenominationchecking(Constants.DenominationNotAvailable + " FOR " + getUserName());
		}

	}

	public void onRefundCellEdit(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {
		int quantity = 0;
		if((foreignLocalCurrencyDataTable.getQty()=="") || (foreignLocalCurrencyDataTable.getQty() != null && foreignLocalCurrencyDataTable.getQty().equalsIgnoreCase("0")))
		{
			quantity = 0;
			BigDecimal totalSum = BigDecimal.ZERO;
			foreignLocalCurrencyDataTable.setQty("");
			foreignLocalCurrencyDataTable.setPrice("");
			for (ForeignLocalCurrencyDataTable element : lstRefundData) {
				if (element.getPrice().length() != 0) {
					totalSum = totalSum.add(new BigDecimal(element.getPrice()));
				}
			}
			setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
		}else{
			try
			{
				quantity = Integer.parseInt(foreignLocalCurrencyDataTable.getQty());

				if(foreignLocalCurrencyDataTable.getStock()>=quantity && quantity!=0){
					BigDecimal totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(new BigDecimal(foreignLocalCurrencyDataTable.getQty() == "" ? "0" : foreignLocalCurrencyDataTable.getQty()));
					if (foreignLocalCurrencyDataTable.getQty().equals("")) {
						foreignLocalCurrencyDataTable.setQty("");
						//added by  rabil for clear if blank
						foreignLocalCurrencyDataTable.setPrice("");
					}
					if(totalcashAmount!=null && totalcashAmount.doubleValue()!=0){
						foreignLocalCurrencyDataTable.setPrice(GetRound.roundBigDecimal(totalcashAmount,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))).toPlainString());
					}else{
						foreignLocalCurrencyDataTable.setPrice("");	
					}
					BigDecimal totalSum = BigDecimal.ZERO;
					/* Responsible to calculate sum of entered cash amount */
					for (ForeignLocalCurrencyDataTable element : lstRefundData) {
						if (element.getPrice().length() != 0) {
							totalSum = totalSum.add(new BigDecimal(element.getPrice()));
						}
					}
					//BigDecimal totalDenoCash = getDenomtotalCash();
					if (getRefundAmount() != null && getRefundAmount().compareTo(totalSum) < 0) {
						totalSum = BigDecimal.ZERO;
						foreignLocalCurrencyDataTable.setQty("");
						foreignLocalCurrencyDataTable.setPrice("");
						for (ForeignLocalCurrencyDataTable element : lstRefundData) {
							if (element.getPrice().length() != 0) {
								totalSum = totalSum.add(new BigDecimal(element.getPrice()));
							}
						}
						setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
						//RequestContext.getCurrentInstance().execute("clearrefunddenominationexceed.show();");
						setExceptionMessage(WarningHandler.showWarningMessage("lbl.totalRefundAmountReceivedExcedThanRefundAmount", sessionStateManage.getLanguageId()));
						RequestContext.getCurrentInstance().execute("alertmsg.show();");

						return;
					} else {
						setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					}
				}else{
					BigDecimal totalSum = BigDecimal.ZERO;
					foreignLocalCurrencyDataTable.setQty("");
					foreignLocalCurrencyDataTable.setPrice("");
					for (ForeignLocalCurrencyDataTable element : lstRefundData) {
						if (element.getPrice().length() != 0) {
							totalSum = totalSum.add(new BigDecimal(element.getPrice()));
						}
					}
					setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));

					setExceptionMessage(WarningHandler.showWarningMessage("lbl.stockNotAvailable", sessionStateManage.getLanguageId()));
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}
			}
			catch(Exception e)
			{
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				quantity =0;
			}
		}
	}

	// exit to personalRemittance
	public void exitFromBeneficaryCreation() {
		try {
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("exitBeneficary", "yes");
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(null);

			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// to get the accoMMYY value
	@Deprecated
	public String getCurrentDateWithFormat() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				data.put(i, "0" + String.valueOf(i + 1));
			} else {
				data.put(i, String.valueOf(i + 1));
			}
		}

		//String year = String.valueOf(new Date().getYear()).substring(1, 3);
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		String year =String.valueOf(calendar.get(Calendar.YEAR));

		System.out.println(Calendar.getInstance().get(Calendar.MONTH));
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH)) + "/" + year;
	}

	// on click of save - Accept
	public void save(){
		try {
			if(isBooRenderRefund()){
				
				if(getCollectedRefundAmount() != null && getRefundAmount() != null && getRefundAmount().compareTo(getCollectedRefundAmount())==0){
					List<TempForeignCurrencyAdjust> tempRefundAdjustList = saveRefundForeignCurrencyAdjust();
					log.info("tempRefundAdjustList size : " +tempRefundAdjustList.size());
					if(tempRefundAdjustList != null && tempRefundAdjustList.size() != 0){
						iPersonalRemittanceService.saveRefundTempCurrencyAdjust(tempRefundAdjustList);
						if(getTempCollect() != null && getTempCollectId() != null){
							saveCollectbyProcedure(getTempCollect(),getTempCollectId());
							saveProcedureCall();
						}else{
							log.info("No Temp Collect and Collect ID");
							setExceptionMessage("No Temp Collect and Collect ID");
							RequestContext.getCurrentInstance().execute("alertmsg.show();");
						}
					}else{
						log.info("Refund List is Empty");
						setExceptionMessage("Please Enter The Denomination");
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
					}
				}else{
					setExceptionMessage("Refund Amount is Not Matching");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}
				
			}else{
				log.info("Only Procedure Call");
				saveProcedureCall();
			}
		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// call the procedure
	public void saveProcedureCall(){
		try {
			if(getTempCollect() != null && getTempCollectId() != null){

				// updating Status "S" in different Tables and checking application status null or not if not null then 
				boolean checkStatus = updadateRecordsNotNull(Constants.S);
				if (!checkStatus) {
					setExceptionMessage("Shopping cart Application already processed / changed.");
					log.info("Exception occured while Checking Shopping cart Application status.");
					RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
					return;
				}

				saveCollectbyProcedure(getTempCollect(),getTempCollectId());
			}else{
				log.info("No Temp Collect and Collect ID");
				setExceptionMessage("No Temp Collect and Collect ID");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// call procedure to push the trnx
	public void saveCollectbyProcedure(TempCollection tempCollection,BigDecimal collectionId) throws AMGException{

		HashMap<String, Object> returnResult = new HashMap<String, Object>();
		BigDecimal noofTrnx =  BigDecimal.ONE;
		String errormsg = null;
		BigDecimal cFinYear = null;
		BigDecimal collectionNumber = null;

		log.info("Parameter 1 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCountryId(): "+sessionStateManage.getCountryId());
		log.info("Parameter 2 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCompanyId(): "+sessionStateManage.getCompanyId());
		log.info("Parameter 3 saveRemittance EX_INSERT_REMITTANCE_TRANX getCustomerNo(): "+getCustomerId());
		log.info("Parameter 4 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionmanage.getUserName(): "+getUserName());
		log.info("Parameter 5 saveRemittance EX_INSERT_REMITTANCE_TRANX getColremittanceNo() : 1 ");
		log.info("Parameter 6 saveRemittance EX_INSERT_REMITTANCE_TRANX tempCollection.getDocumentCode(): "+tempCollection.getDocumentCode());
		log.info("Parameter 7 saveRemittance EX_INSERT_REMITTANCE_TRANX collectionId: "+collectionId);

		try {

			returnResult.put("CountryId",sessionStateManage.getCountryId());
			returnResult.put("CompanyId",sessionStateManage.getCompanyId());
			returnResult.put("CustomerId",getCustomerId());
			returnResult.put("UserName",getUserName());
			returnResult.put("NoofTrnx",noofTrnx);
			returnResult.put("TempDocCode",tempCollection.getDocumentCode());
			returnResult.put("TempCollectionId",collectionId);

			HashMap<String, Object> outRecord = iPersonalRemittanceService.saveAllRemittanceTransaction(returnResult);

			collectionNumber = (BigDecimal) outRecord.get("CollectionDocNo");
			cFinYear = (BigDecimal) outRecord.get("CollectionFinYear");
			errormsg = (String) outRecord.get("ErrorMsg");

			log.info("=====================CALLED SAVE ALL");

			log.info("Out1 -----> cFinYear : "+cFinYear);
			log.info("Out2------>collectionNumber : "+collectionNumber);
			log.info("Out3------->errormsg : "+errormsg);

			if(cFinYear==null || collectionNumber==null)
			{
				String status = null;
				// updating Status "null" in different Tables
				updadateRecords(status);

				log.info("Exception occured while executing procedure");
				throw new SQLException("Exception occured while executing procedure");
			}

			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX getCustomerNo():"+getCustomerId());
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX sessionmanage.getUserName():"+getUserName());
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX  getColremittanceNo(): 1 ");
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX collectionNumber:"+collectionNumber);
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX cFinYear:"+cFinYear);
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX errormsg:"+errormsg);

			if (errormsg==null || errormsg.equalsIgnoreCase("")) {

				//for report generation
				setDocumentNo(collectionNumber);

				// calling EX_INSERT_EMOS_TRANSFER_LIVE procedure changes - 17/12/2015 
				if (tempCollection != null) {
					try {
						String output = iPersonalRemittanceService.insertEMOSLIVETransfer(tempCollection.getApplicationCountryId(),sessionStateManage.getCompanyId(), tempCollection.getDocumentCode(), cFinYear, collectionNumber);
						log.info("EX_INSERT_EMOS_TRANSFER_LIVE : " + output);
					} catch (AMGException e) {
						log.info("Exception occurs while moving data to Old Emos:" + cFinYear + " - " + collectionNumber);
					} catch (Exception e1) {
						log.info("Exception occurs while moving data to Old Emos:" + cFinYear + " - " + collectionNumber);
					}
				}

				try {
					//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("exitBeneficary", "yes");
					PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
					objectPersonalRemittance.setDocumentNo(getDocumentNo().toPlainString());
					objectPersonalRemittance.setBooReportDisplay(true);
					
					FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/personalRemittanceSuccessPage.xhtml");
				} catch (IOException e) {
					log.info("Exception while Redirect");
				}

			} else {
				String status = null;
				// updating Status "null" in different Tables
				updadateRecords(status);
				log.info(" Records Not Saved : " + errormsg);
				setExceptionMessage(errormsg);
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		} catch (SQLException e) {
			String status = null;
			// updating Status "null" in different Tables
			updadateRecords(status);
			log.info(" Records Not Saved : " + errormsg);
			setExceptionMessage(errormsg);
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}
	
	public BigDecimal fetchFinaceYearId() {
		BigDecimal finaceYearId = null;
		try {
			List<UserFinancialYear> financialYearList = foreignCurrencyPurchaseService.getUserFinancialYear(new Date());
			log.info("financialYearList :" + financialYearList.size());
			if (financialYearList != null){
				finaceYearId = financialYearList.get(0).getFinancialYearID();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finaceYearId;
	}

	// update the record status if any fail in procedure
	public boolean updadateRecordsNotNull(String status) {
		BigDecimal financialYearId = fetchFinaceYearId();
		for (ShoppingCartDataTableBean shoppingCartDataTableObj : lstselectedrecord) {
			if (shoppingCartDataTableObj.getSelectedrecord()) {
				 if (shoppingCartDataTableObj.getApplicationType().equalsIgnoreCase(Constants.Remittance)) {
					if (shoppingCartDataTableObj.getSelectedrecord()) {
						//BigDecimal remittanceAppPk = iPersonalRemittanceService.getRemittanceApplicationPk(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo());
						BigDecimal remittanceAppPk = iPersonalRemittanceService.getRemittanceApplicationPk(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo(),shoppingCartDataTableObj.getDocumentId(),financialYearId,shoppingCartDataTableObj.getCompanyId());
						if (remittanceAppPk != null) {
							iPersonalRemittanceService.updateRemittanceApplicationTableData(remittanceAppPk, status);
						}
					}
				}
			}
		}
		return true;
	}
	
	public void updadateRecords(String status) {
		BigDecimal financialYearId = fetchFinaceYearId();
		for (ShoppingCartDataTableBean shoppingCartDataTableObj : lstselectedrecord) {
			if (shoppingCartDataTableObj.getSelectedrecord()) {
				if (shoppingCartDataTableObj.getApplicationType().equalsIgnoreCase(Constants.Remittance)) {
					if(shoppingCartDataTableObj.getSelectedrecord()){
						//BigDecimal remittanceAppPk = iPersonalRemittanceService.getRemittanceApplicationPk(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo());
						BigDecimal remittanceAppPk = iPersonalRemittanceService.getRemittanceApplicationPk(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo(),shoppingCartDataTableObj.getDocumentId(),financialYearId,shoppingCartDataTableObj.getCompanyId());
						if (remittanceAppPk != null) {
							iPersonalRemittanceService.updateRemittanceApplicationTableData(remittanceAppPk, status);
						}
					}
				}
			}
		}
	}

	// save Refund denomination
	public List<TempForeignCurrencyAdjust> saveRefundForeignCurrencyAdjust(){

		log.info("Enter into saveRefundForeignCurrencyAdjust method");

		List<TempForeignCurrencyAdjust> lstdenominationRecords = new ArrayList<TempForeignCurrencyAdjust>();
		try {
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
			Date acc_Month = null;
			try {
				acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			ShoppingCartDataTableBean shoppingCartDetails = null;
			if (lstselectedrecord != null && lstselectedrecord.size() != 0) {
				shoppingCartDetails = lstselectedrecord.get(0);
			}

			List<CurrencyWiseDenomination> lstcurrencyDenomination = foreignLocalCurrencyDenominationService.getLocalCurrencyDenominationStockWithoutUser(sessionStateManage.getCountryId(), sessionStateManage.getCurrencyId());

			int j = 0;
			for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstRefundData) {
				log.info("foreignLocalCurrencyDataTable.getQty() ^^^^^^^^^^^^^^^^^" + foreignLocalCurrencyDataTable.getQty());
				if (!foreignLocalCurrencyDataTable.getPrice().equals("") && foreignLocalCurrencyDataTable.getPrice() != null && !foreignLocalCurrencyDataTable.getPrice().equals("0")) {
					TempForeignCurrencyAdjust foreignCurrencyAdjust = new TempForeignCurrencyAdjust();
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
					foreignCurrencyAdjust.setFsCountryMaster(countryMaster);

					Customer customer = new Customer();
					customer.setCustomerId(getCustomerId());
					foreignCurrencyAdjust.setFsCustomer(customer);

					foreignCurrencyAdjust.setDocumentDate(new Date());

					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));

					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));

					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(denominationMaster);

					foreignCurrencyAdjust.setExchangeRate(shoppingCartDetails.getExchangeRateApplied());

					foreignCurrencyAdjust.setDenaminationAmount(foreignLocalCurrencyDataTable.getDenominationAmount());
					try {
						foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMMITANCE));
						foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++j));
						foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
						CountryBranch countryBranch = new CountryBranch();
						countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
						foreignCurrencyAdjust.setCountryBranch(countryBranch);
						foreignCurrencyAdjust.setProgNumber(Constants.FC_SALE_REMIT);
						foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
						foreignCurrencyAdjust.setTransactionType(Constants.F);
					} catch (Exception e) {
						e.printStackTrace();
					}
					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getUserName());
					if (getTempCollectId() != null) {
						TempCollection collect = new TempCollection();
						collect.setCollectionId(getTempCollectId());
						foreignCurrencyAdjust.setTempCollection(collect);
					}

					lstdenominationRecords.add(foreignCurrencyAdjust);
				} else {
					log.info("Number of notes is 0");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("Exit saveRefundForeignCurrencyAdjust method");

		return lstdenominationRecords;
	}


	public String getCustomerIdentityInt() {
		return customerIdentityInt;
	}
	public void setCustomerIdentityInt(String customerIdentityInt) {
		this.customerIdentityInt = customerIdentityInt;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
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

	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getPaidbyCustMach() {
		return paidbyCustMach;
	}
	public void setPaidbyCustMach(String paidbyCustMach) {
		this.paidbyCustMach = paidbyCustMach;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getDenominationchecking() {
		return denominationchecking;
	}
	public void setDenominationchecking(String denominationchecking) {
		this.denominationchecking = denominationchecking;
	}

	public BigDecimal getCollectedRefundAmount() {
		return collectedRefundAmount;
	}
	public void setCollectedRefundAmount(BigDecimal collectedRefundAmount) {
		this.collectedRefundAmount = collectedRefundAmount;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getLoyalityAmount() {
		return loyalityAmount;
	}
	public void setLoyalityAmount(BigDecimal loyalityAmount) {
		this.loyalityAmount = loyalityAmount;
	}

	public boolean isBooRenderRefund() {
		return booRenderRefund;
	}
	public void setBooRenderRefund(boolean booRenderRefund) {
		this.booRenderRefund = booRenderRefund;
	}

	public boolean isBooSaveButton() {
		return booSaveButton;
	}
	public void setBooSaveButton(boolean booSaveButton) {
		this.booSaveButton = booSaveButton;
	}

	public BigDecimal getTempCollectId() {
		return tempCollectId;
	}
	public void setTempCollectId(BigDecimal tempCollectId) {
		this.tempCollectId = tempCollectId;
	}

	public List<ShoppingCartDataTableBean> getShoppingcartDTList() {
		return shoppingcartDTList;
	}
	public void setShoppingcartDTList(List<ShoppingCartDataTableBean> shoppingcartDTList) {
		this.shoppingcartDTList = shoppingcartDTList;
	}

	public CopyOnWriteArrayList<ShoppingCartDataTableBean> getLstselectedrecord() {
		return lstselectedrecord;
	}
	public void setLstselectedrecord(CopyOnWriteArrayList<ShoppingCartDataTableBean> lstselectedrecord) {
		this.lstselectedrecord = lstselectedrecord;
	}

	public ArrayList<ForeignLocalCurrencyDataTable> getLstRefundData() {
		return lstRefundData;
	}
	public void setLstRefundData(ArrayList<ForeignLocalCurrencyDataTable> lstRefundData) {
		this.lstRefundData = lstRefundData;
	}

	public TempCollection getTempCollect() {
		return tempCollect;
	}
	public void setTempCollect(TempCollection tempCollect) {
		this.tempCollect = tempCollect;
	}

}

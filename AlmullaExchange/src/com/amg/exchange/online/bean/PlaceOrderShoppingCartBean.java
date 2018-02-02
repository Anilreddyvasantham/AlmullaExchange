package com.amg.exchange.online.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CutomerDetailsView;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.remittance.bean.RemittanceReportBean;
import com.amg.exchange.remittance.bean.ShoppingCartDataTableBean;
import com.amg.exchange.remittance.model.RemittanceApplicationPurpose;
import com.amg.exchange.remittance.model.ShoppingCartDetails;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.SessionStateManage;


@Component("placeOrderShoppingCartBean")
@Scope("session")
public class PlaceOrderShoppingCartBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();

	private List<ShoppingCartDataTableBean> shoppingcartDTList;
	private List<RemittanceReportBean> remittanceApplicationReportList = new CopyOnWriteArrayList<RemittanceReportBean>();
	private String exceptionMessage;
	private JasperPrint jasperPrint;
	private Boolean visableExceptionDailogForApplication=false;
	private Boolean renderPaymentpanel;
	private Boolean renderShoppingCartpanel;
	private Boolean booRenderColDebitCard;
	private String colBankCode;
	

	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;

	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	@Autowired
	IGeneralService<T> generalService;

	public List<ShoppingCartDataTableBean> getShoppingcartDTList() {
		return shoppingcartDTList;
	}

	public void setShoppingcartDTList(
			List<ShoppingCartDataTableBean> shoppingcartDTList) {
		this.shoppingcartDTList = shoppingcartDTList;
	}


	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	
	public List<RemittanceReportBean> getRemittanceApplicationReportList() {
		return remittanceApplicationReportList;
	}

	public void setRemittanceApplicationReportList(
			List<RemittanceReportBean> remittanceApplicationReportList) {
		this.remittanceApplicationReportList = remittanceApplicationReportList;
	}

	
	public Boolean getVisableExceptionDailogForApplication() {
		return visableExceptionDailogForApplication;
	}

	public void setVisableExceptionDailogForApplication(
			Boolean visableExceptionDailogForApplication) {
		this.visableExceptionDailogForApplication = visableExceptionDailogForApplication;
	}
	
	public Boolean getRenderPaymentpanel() {
		return renderPaymentpanel;
	}

	public void setRenderPaymentpanel(Boolean renderPaymentpanel) {
		this.renderPaymentpanel = renderPaymentpanel;
	}

	public Boolean getRenderShoppingCartpanel() {
		return renderShoppingCartpanel;
	}

	public void setRenderShoppingCartpanel(Boolean renderShoppingCartpanel) {
		this.renderShoppingCartpanel = renderShoppingCartpanel;
	}

	// to fetch shopping cart records bot remittance and fcsale
	public void getShoppingCartDetails(BigDecimal customerNo) {
		setRenderShoppingCartpanel(true);
		setRenderPaymentpanel(false);
		setShoppingcartDTList(null);
		List<ShoppingCartDataTableBean> shoppingcartDTList = new ArrayList<ShoppingCartDataTableBean>();
		
		List<ShoppingCartDetails> shoppingCartList = new ArrayList<ShoppingCartDetails>();
		try{
			shoppingCartList = iPersonalRemittanceService.getShoppingCartDetails(customerNo);
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
						shoppingCartDataTableBean.setForeignTranxAmount(GetRound.roundBigDecimal(shoppingCartDetails.getForeignTranxAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(shoppingCartDetails.getForeignCurrency())));
					if (shoppingCartDetails.getLocalTranxAmount() != null)
						shoppingCartDataTableBean.setLocalTranxAmount(GetRound.roundBigDecimal(shoppingCartDetails.getLocalTranxAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					if (shoppingCartDetails.getLocalChargeAmount() != null)
						shoppingCartDataTableBean.setLocalChargeAmount(GetRound.roundBigDecimal(shoppingCartDetails.getLocalChargeAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					if (shoppingCartDetails.getLocalCommisionAmount() != null)
						shoppingCartDataTableBean.setLocalCommisionAmount(GetRound.roundBigDecimal(shoppingCartDetails.getLocalCommisionAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					if (shoppingCartDetails.getLocalDeliveryAmount() != null)
						shoppingCartDataTableBean.setLocalDeliveryAmount(shoppingCartDetails.getLocalDeliveryAmount());
					if (shoppingCartDetails.getIsActive() != null)
						shoppingCartDataTableBean.setIsActive(shoppingCartDetails.getIsActive());
					if (shoppingCartDetails.getLocalNextTranxAmount() != null)
						shoppingCartDataTableBean.setLocalNextTranxAmount(GetRound.roundBigDecimal(shoppingCartDetails.getLocalNextTranxAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
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

					shoppingCartDataTableBean.setSelectedrecord(Boolean.FALSE);
					shoppingCartDataTableBean.setDeleteStatus(Boolean.TRUE);

					shoppingCartDataTableBean.setBooReportEligible(true);

					if(shoppingCartDetails.getApplicationType()!=null && shoppingCartDetails.getApplicationType().equals(Constants.FCSale))
					{
						shoppingCartDataTableBean.setBooReportEligible(false);
					}


					if(shoppingCartDetails.getLoyaltsPointIndicator()!=null)
					{
						shoppingCartDataTableBean.setLoyaltsPointIndicator(shoppingCartDetails.getLoyaltsPointIndicator());
					}

					if(shoppingCartDetails.getLoyaltsPointencahsed()!=null)
					{
						shoppingCartDataTableBean.setLoyaltsPointencahsed(shoppingCartDetails.getLoyaltsPointencahsed());
					}

					shoppingcartDTList.add(shoppingCartDataTableBean);


				}
				
				if(shoppingcartDTList.size()>0)
				{
					setShoppingcartDTList(shoppingcartDTList);
				}else
				{
					setShoppingcartDTList(null);
				}
			}
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	public void generatePersonalRemittanceApplicationReport(ShoppingCartDataTableBean shoppingCartDataTableBean){
		setVisableExceptionDailogForApplication(false);
		try{
			if(shoppingCartDataTableBean.getApplicationType().equalsIgnoreCase(Constants.Remittance)){
				fetchApplicationRemittanceReportData(shoppingCartDataTableBean.getDocumentNo());
				remittanceApplicationReportInit();
				HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				httpServletResponse.addHeader("Content-disposition","attachment; filename=RemittanceApplicationReport.pdf");
				ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
				FacesContext.getCurrentInstance().responseComplete();
			}/*else if(shoppingCartDataTableBean.getApplicationType().equalsIgnoreCase(Constants.FCSale)){
				generateNewUpdatedReports(shoppingCartDataTableBean.getCustomerId(),shoppingCartDataTableBean.getDocumentNo().toString());
			}*/
		}catch(Exception e){
			setExceptionMessage(null);
			setVisableExceptionDailogForApplication(true);
			if(e.getMessage()!=null){
				setExceptionMessage(e.getMessage());	
			}else{
				setExceptionMessage("Exception  :"+e);
			}
			RequestContext.getCurrentInstance().execute("exceptioninApplication.show();");
		}

	}

	private List<RemittanceReportBean> fetchApplicationRemittanceReportData(BigDecimal documentNumber)throws Exception {


		remittanceApplicationReportList.clear();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		List<ShoppingCartDetails> remittanceViewlist = iPersonalRemittanceService.getRecordsRemittanceApplication(documentNumber);
		String purposeOfRemittance = "";
		String currencyQuoteName ="";
		if(generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()))!=null){
			currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));
		}


		if (remittanceViewlist.size() > 0) {
			for (ShoppingCartDetails view : remittanceViewlist) {
				RemittanceReportBean obj = new RemittanceReportBean();
				StringBuffer applino =new StringBuffer();
				if(view.getDocumentFinanceYear()!=null){
					applino.append(view.getDocumentFinanceYear());
				}
				if(view.getDocumentNo()!=null){
					applino.append(" / ");
					applino.append(view.getDocumentNo());
				}
				obj.setApplicationNo(applino.toString());

				obj.setDate(currentDate);
				String foreignCurrencyQuoteName = "";

				if(generalService.getCurrencyQuote(view.getForeignCurrency())!=null){
					foreignCurrencyQuoteName = generalService.getCurrencyQuote(view.getForeignCurrency());
				}

				if(view.getForeignTranxAmount()!=null){
					BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTranxAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrency()));
					obj.setProposedCurrencyAndAmount(foreignCurrencyQuoteName+"     ******"+foreignTransationAmount.toString());
				}
				if(view.getExchangeRateApplied()!=null){
					obj.setProposedExchangeRate(foreignCurrencyQuoteName+" / "+currencyQuoteName+"     "+view.getExchangeRateApplied().toString());
				}
				if(view.getLocalTranxAmount()!=null){
					BigDecimal localTranxAmount=GetRound.roundBigDecimal((view.getLocalTranxAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrency()));
					obj.setProposedKWDAmount(currencyQuoteName+"     ******"+localTranxAmount.toString());
				}
				if(view.getLocalCommisionAmount()!=null){
					BigDecimal localCommisionAmount=GetRound.roundBigDecimal((view.getLocalCommisionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrency()));
					obj.setProposedCommission(currencyQuoteName+"     ******"+localCommisionAmount.toString());
				}
				if(view.getLocalChargeAmount()!=null){
					BigDecimal localChargeAmount=GetRound.roundBigDecimal((view.getLocalChargeAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrency()));
					obj.setProposedOtherCharges(currencyQuoteName+"     ******"+localChargeAmount.toString());
				}
				if(view.getLocalNextTranxAmount()!=null){
					BigDecimal localNextTranxAmount=GetRound.roundBigDecimal((view.getLocalNextTranxAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrency()));
					obj.setTotalKWDAmount(currencyQuoteName+"     ******"+localNextTranxAmount.toString());
				}
				obj.setFutherInstructions(view.getInstruction());

				obj.setBeneSwiftBank1(view.getBeneficiarySwiftBankOne());
				obj.setBeneSwiftBank2(view.getBeneficiarySwiftBankTwo());
				obj.setBeneSwiftAddr1(view.getBeneficiarySwiftAddrOne());
				obj.setBeneSwiftAddr2(view.getBeneficiarySwiftAddrTwo());
				obj.setBenefeciaryBranchName(view.getBeneficiaryBranch() );
				StringBuffer beneficieryName = new StringBuffer();
				if(view.getBeneficiaryFirstName()!= null){
					beneficieryName.append(view.getBeneficiaryFirstName());
				}
				if(view.getBeneficiarySecondName()!= null){
					beneficieryName.append(" ");
					beneficieryName.append(view.getBeneficiarySecondName());
				}
				if(view.getBeneficiaryThirdName()!=null){
					beneficieryName.append(" ");
					beneficieryName.append(view.getBeneficiaryThirdName());
				}
				if(view.getBeneficiaryFourthName()!=null){
					beneficieryName.append(" ");
					beneficieryName.append(view.getBeneficiaryFourthName());
				}
				
				obj.setBeneficiaryName(view.getBeneficiaryName());

				StringBuffer benfPayMode = new StringBuffer();
				if(view.getRemittanceDescription()!=null){
					benfPayMode.append(view.getRemittanceDescription());
				}
				if(view.getDeliveryDescription()!= null){
					benfPayMode.append(" - ");
					benfPayMode.append(view.getDeliveryDescription());
				}
				obj.setBenefPaymentMode(benfPayMode.toString());

				obj.setBenefeciaryBankName(view.getBeneficiaryBank());
				obj.setBenefeciaryAccountNumber(view.getBeneficiaryAccountNo());

				List<RemittanceApplicationPurpose>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittanceForApplication(sessionStateManage.getCountryId(),view.getDocumentFinanceYear(),view.getDocumentId(),view.getDocumentNo());

				for(RemittanceApplicationPurpose purposeObj :purposeOfRemittanceList){
					if(purposeOfRemittance.equals("")){
						purposeOfRemittance  = purposeObj.getAmiecDescription();
					}else{
						purposeOfRemittance  = purposeOfRemittance+", "+purposeObj.getAmiecDescription();
					}
				}

				obj.setPurposeOfRemittance(purposeOfRemittance);

				StringBuffer address = new StringBuffer();
				if(view.getBeneCityName()!= null){
					address.append(view.getBeneCityName());
					address.append(", ");
				}
				if(view.getBeneDistrictName()!= null){
					address.append(view.getBeneDistrictName());
					address.append(", ");
				}
				if(view.getBeneStateName()!= null){
					address.append(view.getBeneStateName());
				}
				obj.setAddress(address.toString());
				obj.setIncomeSource(view.getSourceOfIncomeDesc());
				try {
					if (view.getCustomerSignatureClob() != null) {
						obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

				if (customerList.size() > 0) {
					obj.setSenderName(customerList.get(0).getCustomerName());
					obj.setContactNumber(customerList.get(0).getContactNumber());
					obj.setCivilId(customerList.get(0).getIdNumber());
					Date sysdate = customerList.get(0).getIdExp();
					obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate));
				}

				remittanceApplicationReportList.add(obj);
			}
		} else {
			RequestContext.getCurrentInstance().execute("noDataForReport.show();");
		}
		return remittanceApplicationReportList;

	}

	public void remittanceApplicationReportInit() throws JRException {


		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remittanceApplicationReportList);
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String reportPath = realPath +"\\reports\\design\\RemittanceApplication.jasper";

		//	String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RemittanceApplication.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);

	}
	
	public void pageNavigation(BigDecimal customerNo)
	{
		try {
			getShoppingCartDetails(customerNo);
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../onlinespecialrate/PlaceOrderShoppingCart.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void pageNavigationCustomerOnlineTrnx(BigDecimal customerNo)
	{
		try {
			getShoppingCartDetails(customerNo);
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../onlinespecialrate/CustomerOnlineShoppingCart.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exitFromPlaceOrder()
	{
		try {
			setShoppingcartDTList(null);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}catch(Exception e){
			//setErrorMsg( e.getMessage());
			//RequestContext.getCurrentInstance().execute("error.show();");
		}  
		
	}
	
	public void saveKnet(){
		RequestContext.getCurrentInstance().execute("complete.show();");
		return;
	}
	
	public void clickOnOKKnetPage(){
		
		try {
			
			setShoppingcartDTList(null);
			//setBooRenderExitButtonOnline(false);
			//setBooRenderExitButtonBranch(false);
			FacesContext.getCurrentInstance() .getExternalContext().redirect(sessionStateManage.getMenuPage());
		}catch(Exception e){
			/*setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");*/
		}  
		
	}
}

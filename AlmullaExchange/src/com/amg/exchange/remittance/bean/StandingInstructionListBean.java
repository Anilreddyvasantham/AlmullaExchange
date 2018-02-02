package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;


import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.model.RateAlertFrequency;
import com.amg.exchange.model.StandingInstruction;
import com.amg.exchange.remittance.service.IStandingInstructionService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.SessionStateManage;

@Component("standingInstructionListBean")
@Scope("session")
public class StandingInstructionListBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean selectedRecords;
	
	// variables declaration - All Beneficiary Selected Details (First Panel)
	private String customerBeneName;
	private BigDecimal customerAccNum;
	private BigDecimal customerCurrencyId;
	private String customerCurrencyName;
	private BigDecimal customerCountryId;
	private String customerCountryName;
	private BigDecimal customerBankId;
	private String customerBankName;
	private BigDecimal customerBranchId;
	private String customerBranchName;
	private String customerCity;
	private String customerSInstrnCreatedFor;
	private Boolean booRenderBeneDtSISetUp;
	private Boolean booRenderSIRecords;
	
	// Variable Declaration - For Standing Instruction Update (Second Panel)
	private String sIStartdate;
	private String sIRepeatNOOFTimes;
	private BigDecimal sIFrequencyId;
	private String sIFrequencyName;
	private BigDecimal newfrequencyId;
	private Date newStartdate;
	private BigDecimal newRepeatNOOFTimes;
	
	SessionStateManage sessionmanage = new SessionStateManage();
	
	public Boolean getSelectedRecords() {
		return selectedRecords;
	}

	public void setSelectedRecords(Boolean selectedRecords) {
		this.selectedRecords = selectedRecords;
	}
	
	public String getCustomerBeneName() {
		return customerBeneName;
	}

	public void setCustomerBeneName(String customerBeneName) {
		this.customerBeneName = customerBeneName;
	}

	public BigDecimal getCustomerAccNum() {
		return customerAccNum;
	}

	public void setCustomerAccNum(BigDecimal customerAccNum) {
		this.customerAccNum = customerAccNum;
	}

	public BigDecimal getCustomerCurrencyId() {
		return customerCurrencyId;
	}

	public void setCustomerCurrencyId(BigDecimal customerCurrencyId) {
		this.customerCurrencyId = customerCurrencyId;
	}

	public String getCustomerCurrencyName() {
		return customerCurrencyName;
	}

	public void setCustomerCurrencyName(String customerCurrencyName) {
		this.customerCurrencyName = customerCurrencyName;
	}

	public BigDecimal getCustomerCountryId() {
		return customerCountryId;
	}

	public void setCustomerCountryId(BigDecimal customerCountryId) {
		this.customerCountryId = customerCountryId;
	}

	public String getCustomerCountryName() {
		return customerCountryName;
	}

	public void setCustomerCountryName(String customerCountryName) {
		this.customerCountryName = customerCountryName;
	}

	public BigDecimal getCustomerBankId() {
		return customerBankId;
	}

	public void setCustomerBankId(BigDecimal customerBankId) {
		this.customerBankId = customerBankId;
	}

	public String getCustomerBankName() {
		return customerBankName;
	}

	public void setCustomerBankName(String customerBankName) {
		this.customerBankName = customerBankName;
	}

	public BigDecimal getCustomerBranchId() {
		return customerBranchId;
	}

	public void setCustomerBranchId(BigDecimal customerBranchId) {
		this.customerBranchId = customerBranchId;
	}

	public String getCustomerBranchName() {
		return customerBranchName;
	}

	public void setCustomerBranchName(String customerBranchName) {
		this.customerBranchName = customerBranchName;
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustomerSInstrnCreatedFor() {
		return customerSInstrnCreatedFor;
	}

	public void setCustomerSInstrnCreatedFor(String customerSInstrnCreatedFor) {
		this.customerSInstrnCreatedFor = customerSInstrnCreatedFor;
	}

	public Boolean getBooRenderBeneDtSISetUp() {
		return booRenderBeneDtSISetUp;
	}

	public void setBooRenderBeneDtSISetUp(Boolean booRenderBeneDtSISetUp) {
		this.booRenderBeneDtSISetUp = booRenderBeneDtSISetUp;
	}

	public Boolean getBooRenderSIRecords() {
		return booRenderSIRecords;
	}

	public void setBooRenderSIRecords(Boolean booRenderSIRecords) {
		this.booRenderSIRecords = booRenderSIRecords;
	}

	public String getsIStartdate() {
		return sIStartdate;
	}

	public void setsIStartdate(String sIStartdate) {
		this.sIStartdate = sIStartdate;
	}

	public String getsIRepeatNOOFTimes() {
		return sIRepeatNOOFTimes;
	}

	public void setsIRepeatNOOFTimes(String sIRepeatNOOFTimes) {
		this.sIRepeatNOOFTimes = sIRepeatNOOFTimes;
	}

	public BigDecimal getsIFrequencyId() {
		return sIFrequencyId;
	}

	public void setsIFrequencyId(BigDecimal sIFrequencyId) {
		this.sIFrequencyId = sIFrequencyId;
	}

	public String getsIFrequencyName() {
		return sIFrequencyName;
	}

	public void setsIFrequencyName(String sIFrequencyName) {
		this.sIFrequencyName = sIFrequencyName;
	}

	public BigDecimal getNewfrequencyId() {
		return newfrequencyId;
	}

	public void setNewfrequencyId(BigDecimal newfrequencyId) {
		this.newfrequencyId = newfrequencyId;
	}

	public Date getNewStartdate() {
		return newStartdate;
	}

	public void setNewStartdate(Date newStartdate) {
		this.newStartdate = newStartdate;
	}

	public BigDecimal getNewRepeatNOOFTimes() {
		return newRepeatNOOFTimes;
	}

	public void setNewRepeatNOOFTimes(BigDecimal newRepeatNOOFTimes) {
		this.newRepeatNOOFTimes = newRepeatNOOFTimes;
	}

	private List<StandingInstruction> lstStandingInstrn = new ArrayList<StandingInstruction>();
	private List<StandingInstruction> lstStandingInstrnforSave = new ArrayList<StandingInstruction>();
	private List<StandingInstruction> lstStandingInstrnforEdit = new ArrayList<StandingInstruction>();
	private List<StandingInstructionList> lstStndingInstrnDetails = new ArrayList<StandingInstructionList>();
	private CopyOnWriteArrayList<StandingInstructionList> lstSelectedRecords = new CopyOnWriteArrayList<StandingInstructionList>();
	private List<RateAlertFrequency> lstFrequencyDetails = new ArrayList<RateAlertFrequency>();
	
	@Autowired
	IStandingInstructionService<T> standingInstructionService;
	
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	
	public List<StandingInstruction> getLstStandingInstrn() {
		return lstStandingInstrn;
	}

	public void setLstStandingInstrn(List<StandingInstruction> lstStandingInstrn) {
		this.lstStandingInstrn = lstStandingInstrn;
	}

	public List<StandingInstructionList> getLstStndingInstrnDetails() {
		return lstStndingInstrnDetails;
	}

	public void setLstStndingInstrnDetails(
			List<StandingInstructionList> lstStndingInstrnDetails) {
		this.lstStndingInstrnDetails = lstStndingInstrnDetails;
	}

	public CopyOnWriteArrayList<StandingInstructionList> getLstSelectedRecords() {
		return lstSelectedRecords;
	}

	public void setLstSelectedRecords(
			CopyOnWriteArrayList<StandingInstructionList> lstSelectedRecords) {
		this.lstSelectedRecords = lstSelectedRecords;
	}

	public List<StandingInstruction> getLstStandingInstrnforSave() {
		return lstStandingInstrnforSave;
	}

	public void setLstStandingInstrnforSave(
			List<StandingInstruction> lstStandingInstrnforSave) {
		this.lstStandingInstrnforSave = lstStandingInstrnforSave;
	}

	public List<StandingInstruction> getLstStandingInstrnforEdit() {
		return lstStandingInstrnforEdit;
	}

	public void setLstStandingInstrnforEdit(
			List<StandingInstruction> lstStandingInstrnforEdit) {
		this.lstStandingInstrnforEdit = lstStandingInstrnforEdit;
	}

	public List<RateAlertFrequency> getLstFrequencyDetails() {
		return lstFrequencyDetails;
	}

	public void setLstFrequencyDetails(List<RateAlertFrequency> lstFrequencyDetails) {
		this.lstFrequencyDetails = lstFrequencyDetails;
	}

	public void FetchAllListbyCustomerNo(){
		
		lstStndingInstrnDetails.clear();
		
        lstStandingInstrn = standingInstructionService.standingInstrnAllDetailsbyCustomer(sessionmanage.getCustomerId());
		
		for(StandingInstruction lstStrnInstrn : lstStandingInstrn){
			
			StandingInstructionList stndingInstrn = new StandingInstructionList();
			
			stndingInstrn.setBeneficiaryAccount(lstStrnInstrn.getDebitAccountNo());
			stndingInstrn.setBeneficiaryAccountDtId(lstStrnInstrn.getExbeneficaryAccountSeqId().getBeneficaryAccountSeqId());
			stndingInstrn.setBeneficiaryBankBranchId(lstStrnInstrn.getExBankBranchId().getBankBranchId());
			stndingInstrn.setBeneficiaryBankBranchName(lstStrnInstrn.getExBankBranchId().getBranchFullName());
			stndingInstrn.setBeneficiaryBankId(lstStrnInstrn.getExBankMaster().getBankId());
			stndingInstrn.setBeneficiaryBankName(lstStrnInstrn.getExBankMaster().getBankFullName());
			stndingInstrn.setBeneficiaryCityName(lstStrnInstrn.getExbeneficaryMasterSeqId().getCityName());
			stndingInstrn.setBeneficiaryCurrencyId(lstStrnInstrn.getExCurrencyMasterByForeignCurrencyId().getCurrencyId());
			stndingInstrn.setBeneficiaryCurrencyName(lstStrnInstrn.getExCurrencyMasterByForeignCurrencyId().getCurrencyName());
			stndingInstrn.setBeneficiaryCurrenyShort(lstStrnInstrn.getExCurrencyMasterByForeignCurrencyId().getQuoteName());
			stndingInstrn.setBeneficiaryDueDate(new SimpleDateFormat("dd/MM/yyyy").format(lstStrnInstrn.getEffectiveFromDate()));
			stndingInstrn.setBeneficiaryMasterId(lstStrnInstrn.getExbeneficaryMasterSeqId().getBeneficaryMasterSeqId());
			if(lstStrnInstrn.getIsactive().equalsIgnoreCase(Constants.Yes)){
				stndingInstrn.setBeneficiaryStatus(Constants.ACTIVE);
			}else{
				stndingInstrn.setBeneficiaryStatus(Constants.IN_ACTIVE);
			}
			stndingInstrn.setStandingInstnID(lstStrnInstrn.getStandingInstructionSeqId());
			stndingInstrn.setBeneficiaryUserName(lstStrnInstrn.getExbeneficaryMasterSeqId().getFirstName());
			stndingInstrn.setBeneficiaryCustomerNo(lstStrnInstrn.getFsCustomer().getCustomerId());
			stndingInstrn.setStandingAmount(lstStrnInstrn.getAmount());
			
			lstStndingInstrnDetails.add(stndingInstrn);
		}
		
	}
	
	// Navigation to Standing Instruction All List
	public void standingInstrnAllListNavigation() {

		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../remittance/StandingInstructionListing.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		standingInstructionAllRecords();

		// to fetch the records of customer standing Instruction 
		FetchAllListbyCustomerNo();
	}
		
	//selecting records to make Active/DeActive
	public void changeStatus(StandingInstructionList lstselected){
		
		if(lstselected.getSelectedRecord()){
			for(StandingInstruction lstStrnInstrn : lstStandingInstrn){
				if(lstStrnInstrn.getStandingInstructionSeqId().compareTo(lstselected.getStandingInstnID())==0){
					lstStandingInstrnforSave.add(lstStrnInstrn);
				}
			}
			
		}else{
			for(int i=0;i<=lstStandingInstrn.size();i++){
				StandingInstruction removingObj =  lstStandingInstrnforSave.get(i);
				if(removingObj.getStandingInstructionSeqId().compareTo(lstselected.getStandingInstnID())==0){
					lstStandingInstrnforSave.remove(i);
				}
			}
		}
	}
	
	//changing status based on selected records 
	public void updatingStatusToDB(){
		for(StandingInstruction lstStrnInstrn : lstStandingInstrn){

			for(StandingInstruction lstselected : lstStandingInstrnforSave){

				if(lstStrnInstrn.getStandingInstructionSeqId().compareTo(lstselected.getStandingInstructionSeqId())==0){
					if(lstStrnInstrn.getIsactive().equalsIgnoreCase(Constants.Yes)){
						lstselected.setIsactive(Constants.No);
					}else{
						lstselected.setIsactive(Constants.Yes);
					}	
				}
				standingInstructionService.saveStandingInstruction(lstselected);
			}
		}
		lstStandingInstrnforSave.clear();
		// to fetch the records of customer standing Instruction 
		FetchAllListbyCustomerNo();
	}
	
	//edit button functionality
	public void editStandingInstruction(StandingInstructionList lstselected) throws ParseException{
		
		lstStandingInstrnforEdit.clear();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		beneficiaryDtEditSI();
		
		String standInstrnfor = null;
		for(StandingInstruction lstStrnInstrn : lstStandingInstrn){
			if(lstStrnInstrn.getStandingInstructionSeqId().compareTo(lstselected.getStandingInstnID())==0){
				lstStandingInstrnforEdit.add(lstStrnInstrn);
				// setting selected Beneficiary Details on Edit
				standInstrnfor = lstStandingInstrnforEdit.get(0).getExCurrencyMasterByForeignCurrencyId().getQuoteName().concat(GetRound.roundBigDecimal(lstStandingInstrnforEdit.get(0).getAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(lstStandingInstrnforEdit.get(0).getExCurrencyMasterByForeignCurrencyId().getCurrencyId())).toPlainString());
			}
		}
		
		if(lstStandingInstrnforEdit.size()!=0){
			setCustomerAccNum(lstStandingInstrnforEdit.get(0).getDebitAccountNo());
			setCustomerBankId(lstStandingInstrnforEdit.get(0).getExBankMaster().getBankId());
			setCustomerBankName(lstStandingInstrnforEdit.get(0).getExBankMaster().getBankFullName());
			setCustomerBeneName(lstStandingInstrnforEdit.get(0).getFsCustomer().getFirstName());
			setCustomerBranchId(lstStandingInstrnforEdit.get(0).getExBankBranchId().getBankBranchId());
			setCustomerBranchName(lstStandingInstrnforEdit.get(0).getExBankBranchId().getBranchFullName());
			setCustomerCity(lstStandingInstrnforEdit.get(0).getExbeneficaryMasterSeqId().getCityName());
			setCustomerCountryId(lstStandingInstrnforEdit.get(0).getExbeneficaryMasterSeqId().getFsCountryMaster().getCountryId());
			setCustomerCountryName(generalService.getCountryName(sessionmanage.getLanguageId(),getCustomerCountryId()));
			setCustomerCurrencyId(lstStandingInstrnforEdit.get(0).getExCurrencyMasterByForeignCurrencyId().getCurrencyId());
			setCustomerCurrencyName(lstStandingInstrnforEdit.get(0).getExCurrencyMasterByForeignCurrencyId().getCurrencyName());
			setCustomerSInstrnCreatedFor(standInstrnfor);
			setsIFrequencyId(lstStandingInstrnforEdit.get(0).getFrequency().getOnlineRateAlertId());
			setsIFrequencyName(lstStandingInstrnforEdit.get(0).getFrequency().getFrequencyDescription());
			setsIStartdate(new SimpleDateFormat("dd/MM/YYYY").format(lstStandingInstrnforEdit.get(0).getEffectiveFromDate()));
			setsIRepeatNOOFTimes("Repeat for "+lstStandingInstrnforEdit.get(0).getRepeatNoofTimes()+" Times");
			//fetch frequency Records
			frequencyDetails();
		}
	}
	
	//rendering first panel
	public void standingInstructionAllRecords(){
		setBooRenderBeneDtSISetUp(false);
		setBooRenderSIRecords(true);
	}
	
	//rendering first panel
	public void beneficiaryDtEditSI(){
		setBooRenderBeneDtSISetUp(true);
		setBooRenderSIRecords(false);
	}
	
	// setting Start Date in Second Panel
	public void onDateSelect(SelectEvent event) throws ParseException {
	       
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        setNewStartdate(format.parse(format.format(event.getObject())));
        
    }
	
	// third Panel Frequency fetching and rendering Panel
	public void frequencyDetails() {

		// fetching frequency details from DB
		List<RateAlertFrequency> lstFrqDetails = generalService.frequencyDetailsLst(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));

		if (lstFrqDetails.size() != 0) {
			setLstFrequencyDetails(lstFrqDetails);
			try {
				SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
				Calendar cal = Calendar.getInstance();
				setNewStartdate(myFormat.parse(new SimpleDateFormat("dd/MM/YYYY").format(cal.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
		
	// calculating the Repeat No of Times For Default 3 Years
	public void calculationRepeatTimes() {

		int days = 0, weeks = 0, months = 0, years = 0;

		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 3);

		// Calendar cal1 = Calendar.getInstance();

		try {
			// Date date1 = myFormat.parse(new
			// SimpleDateFormat("dd/MM/YYYY").format(cal1.getTime()));
			Date date1 = getNewStartdate();
			Date date2 = myFormat.parse(new SimpleDateFormat("dd/MM/YYYY").format(cal.getTime()));
			days = Days.daysBetween(new DateTime(date1), new DateTime(date2)).getDays();
			weeks = Weeks.weeksBetween(new DateTime(date1), new DateTime(date2)).getWeeks();
			months = Months.monthsBetween(new DateTime(date1),new DateTime(date2)).getMonths();
			years = Years.yearsBetween(new DateTime(date1), new DateTime(date2)).getYears();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (getNewfrequencyId().compareTo(new BigDecimal(Constants.Daily)) == 0) {
			setNewRepeatNOOFTimes(new BigDecimal(days));
		} else if (getNewfrequencyId().compareTo(new BigDecimal(Constants.Weekly)) == 0) {
			setNewRepeatNOOFTimes(new BigDecimal(weeks));
		} else if (getNewfrequencyId().compareTo(new BigDecimal(Constants.Monthly)) == 0) {
			setNewRepeatNOOFTimes(new BigDecimal(months));
		} else if (getNewfrequencyId().compareTo(new BigDecimal(Constants.Quaterly)) == 0) {
			setNewRepeatNOOFTimes(GetRound.roundBigDecimal(new BigDecimal(months).divide(new BigDecimal(4), 4,BigDecimal.ROUND_HALF_UP), 0));
		} else if (getNewfrequencyId().compareTo(new BigDecimal(Constants.HalfYearly)) == 0) {
			setNewRepeatNOOFTimes(GetRound.roundBigDecimal(new BigDecimal(months).divide(new BigDecimal(6), 6,BigDecimal.ROUND_HALF_UP), 0));
		} else if (getNewfrequencyId().compareTo(new BigDecimal(Constants.Annually)) == 0) {
			setNewRepeatNOOFTimes(new BigDecimal(years));
		} else {
			setNewRepeatNOOFTimes(null);
		}

	}
	
	// Frequency min Date - Starting From Today
	private Date effectiveMinDate = new Date();

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}
	
	//changing status based on selected records 
	public void updatingSIRecords(){
		for(StandingInstruction lstStrnInstrn : lstStandingInstrn){

			for(StandingInstruction lstselected : lstStandingInstrnforEdit){

				if(lstStrnInstrn.getStandingInstructionSeqId().compareTo(lstselected.getStandingInstructionSeqId())==0){
					
					RateAlertFrequency ratefreq = new RateAlertFrequency();
					ratefreq.setOnlineRateAlertId(getNewfrequencyId());
					lstselected.setFrequency(ratefreq);
					
					lstselected.setEffectiveFromDate(getNewStartdate());
					
					lstselected.setRepeatNoofTimes(getNewRepeatNOOFTimes());
				}
				standingInstructionService.saveStandingInstruction(lstselected);
			}
		}
		lstStandingInstrnforEdit.clear();
		// to fetch the records of customer standing Instruction 
		standingInstrnAllListNavigation();
	}


}

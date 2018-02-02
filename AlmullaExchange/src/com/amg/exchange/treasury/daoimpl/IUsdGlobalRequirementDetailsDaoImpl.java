package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.treasury.bean.BankCountryPopulationBean;
import com.amg.exchange.treasury.bean.UsdGlobalRequirementDetails;
import com.amg.exchange.treasury.dao.IUsdGlobalRequirementDetailsDao;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.FundEstimationDetails;

@Repository
public class IUsdGlobalRequirementDetailsDaoImpl<T> extends CommonDaoImpl<T>  implements IUsdGlobalRequirementDetailsDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountDetails> getCurrencyOfBank(BigDecimal bankId) {
		
		List<BankAccountDetails> finalData = new ArrayList<BankAccountDetails>(); 
		List<BigDecimal> lstCurrency = new ArrayList<BigDecimal>();
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		
		dCriteria.setFetchMode("bankAccountDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId)); 
		
		dCriteria.setFetchMode("bankAccountDetails.fsCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);
		/** NAG APPLY ASCENDING  04/02/2015 **/
		dCriteria.addOrder(Order.asc("fsCurrencyMaster.currencyName"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankAccountDetails> data = (List<BankAccountDetails>) findAll(dCriteria);
		
		for (BankAccountDetails bankAccountDetails : data) {
			if(!lstCurrency.contains(bankAccountDetails.getFsCurrencyMaster().getCurrencyId())) {
				finalData.add(bankAccountDetails);
				lstCurrency.add(bankAccountDetails.getFsCurrencyMaster().getCurrencyId());
			}
		}
		
		return finalData;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getBankAccordingToBankCountry(BigDecimal applicationCountryId,  BigDecimal countryId) {
		
		DetachedCriteria dCriteria =DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		/*18/10/2014  we removed FS_Application_CountryID from Bank Master Table
		dCriteria.setFetchMode("bankMaster.fsApplicationCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.fsApplicationCountryMaster", "fsApplicationCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsApplicationCountryMaster.countryId", applicationCountryId));*/
		
		dCriteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return dCriteria.getExecutableCriteria(getSession()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FundEstimationDetails> search(BigDecimal bankId, BigDecimal currencyId, BigDecimal bankCountry, BigDecimal detailsId) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(FundEstimationDetails.class, "fundEstimationDetails");
		
		dCriteria.setFetchMode("fundEstimationDetails.exFundEstimtaionDeatilsForBankCountry", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.exFundEstimtaionDeatilsForBankCountry", "exFundEstimtaionDeatilsForBankCountry", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exFundEstimtaionDeatilsForBankCountry.countryId", bankCountry));
		
		/*dCriteria.setFetchMode("exFundEstimtaionDeatilsForBankCountry.fsCountryMasterDescs", FetchMode.JOIN);
		dCriteria.createAlias("exFundEstimtaionDeatilsForBankCountry.fsCountryMasterDescs", "fsCountryMasterDescs", JoinType.INNER_JOIN);*/
		
		dCriteria.setFetchMode("fundEstimationDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		
		if(bankId!=null && bankId.intValue()>0) {
			dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		}
		
		dCriteria.setFetchMode("fundEstimationDetails.exCurrenyMaster", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.exCurrenyMaster", "exCurrenyMaster", JoinType.INNER_JOIN);
		if(currencyId!=null && currencyId.intValue()>0) {
			dCriteria.add(Restrictions.eq("exCurrenyMaster.currencyId", currencyId));
		}
		
		dCriteria.setFetchMode("fundEstimationDetails.fundEstimtaionId", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.fundEstimtaionId", "fundEstimtaionId", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("fundEstimationDetails.exFundEstimationDetails", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.exFundEstimationDetails", "exFundEstimationDetails", JoinType.INNER_JOIN);
        if(detailsId!=null && detailsId.intValue()>0) {
        	dCriteria.add(Restrictions.eq("exFundEstimationDetails.bankAcctDetId", detailsId));
        }
        
        dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
        
		
        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::Bank Country"+bankCountry+"Currency ::"+currencyId+"Bank Id ::: "+bankId+"detailsId::"+detailsId );
		List<FundEstimationDetails> data = (List<FundEstimationDetails>)findAll(dCriteria);
		System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::"+data.size());
		
		List<FundEstimationDetails> finalData = new ArrayList<FundEstimationDetails>();
		
		for (FundEstimationDetails fundEstimationDetails : data) {
			if(new SimpleDateFormat("dd/MMM/yy").format(fundEstimationDetails.getProjectionDate()).equalsIgnoreCase(new SimpleDateFormat("dd/MMM/yy").format(new Date()))) {
				finalData.add(fundEstimationDetails);
			}
		}
		
		System.out.println("Size of finla data ::: "+finalData.size());
		
	 	return finalData;
	}

	@Override
	public String getCountryName(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		
		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster",  JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		
		return ((CountryMasterDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCountryName();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankCountryPopulationBean> getBankContry() {
		
		BankCountryPopulationBean bankCountryPopulationBean = null;
		List<BankCountryPopulationBean> finalData = new ArrayList<BankCountryPopulationBean>();
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		
		dCriteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("fsCountryMaster.fsCountryMasterDescs", FetchMode.JOIN);
		dCriteria.createAlias("fsCountryMaster.fsCountryMasterDescs", "fsCountryMasterDescs", JoinType.INNER_JOIN);
		
		dCriteria.addOrder(Order.asc("fsCountryMasterDescs.countryName"));	/** NAG APPLY ASCENDING 04/02/2015**/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<BankMaster> lstBankmaster =  dCriteria.getExecutableCriteria(getSession()).list();
		
		for (BankMaster bankMaster : lstBankmaster) {
			if(!duplicateCheck.contains(bankMaster.getFsCountryMaster().getCountryId())) {
				duplicateCheck.add(bankMaster.getFsCountryMaster().getCountryId());
					bankCountryPopulationBean = new BankCountryPopulationBean(bankMaster.getFsCountryMaster().getCountryId(), bankMaster.getFsCountryMaster().getFsCountryMasterDescs().get(0).getCountryName(),bankMaster.getFsCountryMaster().getCountryCode());
					finalData.add(bankCountryPopulationBean);
				}
		}
		return finalData;
	}


	@SuppressWarnings("unchecked")
	@Override
	public String getBankName(BigDecimal countryId) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "fsCountryMasterDesc"); 
		
		dCriteria.setFetchMode("fsCountryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("fsCountryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		
		List<CountryMasterDesc> data = (List<CountryMasterDesc>) findAll(dCriteria); 
		
		return data.get(0).getCountryName();
	}

}

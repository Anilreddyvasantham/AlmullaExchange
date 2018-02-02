package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.treasury.dao.ILocalRequirementDao;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.FundEstimation;
import com.amg.exchange.treasury.model.FundEstimationDetails;

@Repository
public class LocalRequirementDaoImpl<T> extends CommonDaoImpl<T> implements ILocalRequirementDao<T> {

	@Override
	public List<FundEstimationDetails> getFundEstimationDetailsList(BigDecimal fundEstimationId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(FundEstimationDetails.class,"fundEstimationDetails");
		dCriteria.setFetchMode("fundEstimationDetails.fundEstimtaionId", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.fundEstimtaionId", "fundEstimtaionId", JoinType.INNER_JOIN);
		
        dCriteria.add(Restrictions.eq("fundEstimtaionId.fundEstimtaionId", fundEstimationId));
        
        dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

        
		return (List<FundEstimationDetails>)findAll(dCriteria);
	}

	@Override
	public List<FundEstimation> getFundEstimationList(BigDecimal countryId) {
	DetachedCriteria dCriteria = DetachedCriteria.forClass(FundEstimation.class,"fundEstimation");
		dCriteria.setFetchMode("fundEstimation.exFundEstimationForApplicationCountry", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimation.exFundEstimationForApplicationCountry", "exFundEstimationForApplicationCountry", JoinType.INNER_JOIN);
		
        dCriteria.add(Restrictions.eq("exFundEstimationForApplicationCountry.countryId", countryId));
        
        dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
        
		return (List<FundEstimation>)findAll(dCriteria);
	}

	@Override
	public List<FundEstimation> getFundEstimationListBasedOnCurrency(BigDecimal bankCountry) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(FundEstimation.class,"fundEstimation");
		dCriteria.setFetchMode("fundEstimation.exFundEstimationForBankCountry", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimation.exFundEstimationForBankCountry", "exFundEstimationForBankCountry", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exFundEstimationForBankCountry.countryId", bankCountry));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<FundEstimation>)findAll(dCriteria);
	}

	@Override
	public List<FundEstimationDetails> getFundEstimationDetailsListBasedOnCountry(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(FundEstimationDetails.class,"fundEstimationDetails");
		dCriteria.setFetchMode("fundEstimationDetails.exFundEstimtaionDeatilsForBankCountry", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.exFundEstimtaionDeatilsForBankCountry", "exFundEstimtaionDeatilsForBankCountry", JoinType.INNER_JOIN);
		
        dCriteria.add(Restrictions.eq("exFundEstimtaionDeatilsForBankCountry.countryId", countryId));
        
        
        dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
        
		return (List<FundEstimationDetails>)findAll(dCriteria);
	}

}

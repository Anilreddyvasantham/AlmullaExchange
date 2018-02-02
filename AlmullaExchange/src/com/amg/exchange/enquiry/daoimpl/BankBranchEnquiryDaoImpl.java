package com.amg.exchange.enquiry.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.enquiry.dao.IBankBranchEnquiryDao;
import com.amg.exchange.treasury.model.BankBranch;

@Repository
public class BankBranchEnquiryDaoImpl extends CommonDaoImpl implements IBankBranchEnquiryDao{

	@Override
	public List<BankBranch> getBranchDetails(BigDecimal bankCountry,BigDecimal bankId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(BankBranch.class,"bankBranch");
		
		criteria.setFetchMode("bankBranch.fsDistrictMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("bankBranch.fsStateMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("bankBranch.fsCityMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.fsCityMaster", "fsCityMaster", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("bankBranch.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", bankCountry));
		
		criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findAll(criteria);
	}

}

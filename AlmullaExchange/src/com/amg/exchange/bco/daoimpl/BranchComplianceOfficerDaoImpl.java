package com.amg.exchange.bco.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.bco.dao.IBranchComplianceOfficerDao;
import com.amg.exchange.bco.model.BranchComplianceOfficer;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.model.IdentityTypeMaster;
import com.amg.exchange.util.Constants;

@SuppressWarnings("unchecked")
@Repository
public class BranchComplianceOfficerDaoImpl<T> extends CommonDaoImpl<T>
		implements IBranchComplianceOfficerDao {

	@Override
	public List<BranchComplianceOfficer> getBranchComplianceList(
			BigDecimal countryBranchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(
				BranchComplianceOfficer.class, "bco");

		criteria.setFetchMode("bco.customerId", FetchMode.JOIN);
		criteria.createAlias("bco.customerId", "customerId",
				JoinType.INNER_JOIN);

		// criteria.setFetchMode("bco.countryBranchId", FetchMode.JOIN);
		// criteria.createAlias("bco.countryBranchId", "countryBranchId",
		// JoinType.INNER_JOIN);

		criteria.setFetchMode("bco.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("bco.applicationCountryId",
				"applicationCountryId", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("bco.amlCode", Constants.AML_STATUS_BCO));
		criteria.add(Restrictions.eq("bco.countryBranchId.countryBranchId",
				countryBranchId));
		criteria.add(Restrictions.isNull("bco.amlClearBy"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BranchComplianceOfficer>) findAll(criteria);
	}

	@Override
	public List<BranchComplianceOfficer> getHeadOfficeComplianceList(
			BigDecimal countryBranchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(
				BranchComplianceOfficer.class, "bco");

		criteria.setFetchMode("bco.customerId", FetchMode.JOIN);
		criteria.createAlias("bco.customerId", "customerId",
				JoinType.INNER_JOIN);

		// criteria.setFetchMode("bco.countryBranchId", FetchMode.JOIN);
		// criteria.createAlias("bco.countryBranchId", "countryBranchId",
		// JoinType.INNER_JOIN);

		criteria.setFetchMode("bco.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("bco.applicationCountryId",
				"applicationCountryId", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("bco.amlCode", Constants.AML_STATUS_COMP));
		criteria.add(Restrictions.eq("bco.countryBranchId.countryBranchId",
				countryBranchId));
		criteria.add(Restrictions.isNotNull("bco.amlClearBy"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BranchComplianceOfficer>) findAll(criteria);
	}

	@Override
	public String getNationality(BigDecimal languageId, BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				CountryMasterDesc.class, "countryMasterDesc");

		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.add(Restrictions.eq(
				"fsCountryMasterDescs.languageType.languageId", languageId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return ((CountryMasterDesc) dCriteria
				.getExecutableCriteria(getSession()).list().get(0))
				.getNationality();
	}

	@Override
	public List<IdentityTypeMaster> getIdentityList(BigDecimal identityid) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				IdentityTypeMaster.class, "identityTypeMaster");

		dCriteria.add(Restrictions.eq("IdentityTypeMaster.identityTypeId",
				identityid));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<IdentityTypeMaster>) findAll(dCriteria);
	}

	@Override
	public void saveBranchCompliance(
			BranchComplianceOfficer branchComplianceOfficer) {
		getSession().saveOrUpdate(branchComplianceOfficer);

	}

	@Override
	public void saveHeadOfficeCompliance(
			BranchComplianceOfficer branchComplianceOfficer) {
		getSession().saveOrUpdate(branchComplianceOfficer);

	}

}

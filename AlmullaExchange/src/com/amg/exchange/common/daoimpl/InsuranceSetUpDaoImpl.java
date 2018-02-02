package com.amg.exchange.common.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.dao.IInsuranceDao;
import com.amg.exchange.common.model.InsuranceMaster;
import com.amg.exchange.common.model.InsuranceMasterDescription;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class InsuranceSetUpDaoImpl extends CommonDaoImpl implements IInsuranceDao {

	@Override
	public void saveOrUpadate(InsuranceMaster insuranceSetUp) {
		 getSession().saveOrUpdate(insuranceSetUp);
		
	}

	@Override
	public void saveOrUpdate(
			InsuranceMasterDescription insuranceMasterDescription) {
		 getSession().saveOrUpdate(insuranceMasterDescription);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InsuranceMasterDescription> getAllUnApprovedRecords() {
		DetachedCriteria criteria = DetachedCriteria.forClass(InsuranceMasterDescription.class, "insuranceMasterDescription");
		criteria.setFetchMode("insuranceMasterDescription.insuranceMasterId", FetchMode.JOIN);
		criteria.createAlias("insuranceMasterDescription.insuranceMasterId", "insuranceMasterId",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("insuranceMasterId.isActive",Constants.U));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<InsuranceMasterDescription>) findAll(criteria);
	 
	}

	@Override
	public String approveRecordRecord(BigDecimal InsuranceSetupPk,
			String userName) {
		String approveMsg;
		InsuranceMaster insuranceMaster=(InsuranceMaster) getSession().get(InsuranceMaster.class, InsuranceSetupPk);
		String approvedUser= insuranceMaster.getApprovedBy();
		if(approvedUser==null)
		{
			insuranceMaster.setApprovedBy(userName );
			insuranceMaster.setApprovedDate(new Date());
			insuranceMaster.setIsActive(Constants.Yes);
			getSession().saveOrUpdate(insuranceMaster);
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InsuranceMasterDescription> medicalInsuranceInquiry(Date fromDate, Date toDate) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(InsuranceMasterDescription.class, "insuranceMasterDescription");
		criteria.setFetchMode("insuranceMasterDescription.insuranceMasterId", FetchMode.JOIN);
		criteria.createAlias("insuranceMasterDescription.insuranceMasterId", "insuranceMasterId",JoinType.INNER_JOIN);
	 
		//criteria.add(Restrictions.le("insuranceMasterId.fromDate",fromDate));
		//criteria.add(Restrictions.ge("insuranceMasterId.toDate",toDate));
		if(fromDate!=null){
		criteria.add(Restrictions.eq("insuranceMasterId.fromDate",fromDate));
		}
		if(toDate!=null){
		criteria.add(Restrictions.eq("insuranceMasterId.toDate",toDate));
		}
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<InsuranceMasterDescription>) findAll(criteria);
	}

	@Override
	public void deleteRecordFromParent(BigDecimal insuranceSetupMasterPk) {
		InsuranceMaster insuranceMaster=(InsuranceMaster) getSession().get(InsuranceMaster.class, insuranceSetupMasterPk);
		getSession().delete(insuranceMaster);
		
	}

	@Override
	public void deleteRecordFromChild(BigDecimal insuranceSetupDescPk) {
		InsuranceMasterDescription insuranceMasterDesc=(InsuranceMasterDescription) getSession().get(InsuranceMasterDescription.class, insuranceSetupDescPk);
		 getSession().delete(insuranceMasterDesc);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InsuranceMasterDescription> getAllRecordsFromDB() {
		DetachedCriteria criteria = DetachedCriteria.forClass(InsuranceMasterDescription.class, "insuranceMasterDescription");
		criteria.setFetchMode("insuranceMasterDescription.insuranceMasterId", FetchMode.JOIN);
		criteria.createAlias("insuranceMasterDescription.insuranceMasterId", "insuranceMasterId",JoinType.INNER_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<InsuranceMasterDescription>) findAll(criteria);
	}

	
}

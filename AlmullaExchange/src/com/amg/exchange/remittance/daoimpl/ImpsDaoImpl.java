package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.complaint.model.ComplaintAction;
import com.amg.exchange.remittance.dao.IImpsDao;
import com.amg.exchange.remittance.model.Imps;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.Constants;
@SuppressWarnings("rawtypes")
@Repository
public class ImpsDaoImpl extends CommonDaoImpl  implements IImpsDao {

	private static final Logger log=Logger.getLogger(ImpsDaoImpl.class);

	@Override
	public void saveOrUpdate(Imps impsObj) {
	log.info( "==============saveOrUpdate called=============");
	 getSession().saveOrUpdate(impsObj);
		
	}

	@Override
	public List<Imps> getImpsRecordsBasedOnBankIds( BigDecimal corrspBankId,
			BigDecimal beneBankId) {
		log.info( "============== getImpsRecordsBasedOnBankIds() called=============");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Imps.class,"imps");
		
		dCriteria.setFetchMode("imps.correspondingBankId", FetchMode.JOIN);
		dCriteria.createAlias("imps.correspondingBankId", "correspondingBankId",  JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("imps.beneBankId", FetchMode.JOIN);
		dCriteria.createAlias("imps.beneBankId", "beneBankId",  JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("correspondingBankId.bankId", corrspBankId));
		dCriteria.add(Restrictions.eq("beneBankId.bankId", beneBankId));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<Imps>)findAll(dCriteria);
	 
	}

	@Override
	public List<Imps> getAllRecordsFromDB() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Imps.class,"imps");
		
		dCriteria.setFetchMode("imps.correspondingBankId", FetchMode.JOIN);
		dCriteria.createAlias("imps.correspondingBankId", "correspondingBankId",  JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("imps.beneBankId", FetchMode.JOIN);
		dCriteria.createAlias("imps.beneBankId", "beneBankId",  JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("imps.applicationCountryId", FetchMode.JOIN);
		dCriteria.createAlias("imps.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<Imps>)findAll(dCriteria);
		
	}

	@Override
	public void delete(BigDecimal impsPk) {
		Imps imps=(Imps)getSession().get(Imps.class, impsPk);
		getSession().delete(imps); 
		
	}

	@Override
	public void activateRecord(BigDecimal impsPk, String userName) {
		Imps imps=(Imps)getSession().get(Imps.class, impsPk);
		imps.setIsActive(Constants.U);
		imps.setModifiedBy(userName);
		imps.setModifiedDate(new Date());
		imps.setApprovedBy(null);
		imps.setApprovedDate(null);
		imps.setRemarks(null);
		getSession().update(imps);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Imps> getAllUnApprovedRecords() {
	
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Imps.class,"imps");
		
		dCriteria.setFetchMode("imps.correspondingBankId", FetchMode.JOIN);
		dCriteria.createAlias("imps.correspondingBankId", "correspondingBankId",  JoinType.INNER_JOIN);
		dCriteria.setFetchMode("imps.beneBankId", FetchMode.JOIN);
		dCriteria.createAlias("imps.beneBankId", "beneBankId",  JoinType.INNER_JOIN);
		dCriteria.setFetchMode("imps.applicationCountryId", FetchMode.JOIN);
		dCriteria.createAlias("imps.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("imps.isActive", Constants.U));
		dCriteria.add(Restrictions.isNull("imps.approvedBy"));
		dCriteria.add(Restrictions.isNull("imps.approvedDate"));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<Imps>)findAll(dCriteria);
	  
	}

	@Override
	public String approveRecord(BigDecimal  impsPk, String userName) {
		String approveMsg;
		Imps impsObj=(Imps) getSession().get(Imps.class, impsPk);
		String approvedUser=impsObj.getApprovedBy();
		if(approvedUser==null)
		{
			impsObj.setApprovedBy(userName );
			impsObj.setApprovedDate(new Date());
			impsObj.setIsActive(Constants.Yes);
			getSession().saveOrUpdate(impsObj);
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;
		 
	}
	
	
	
}

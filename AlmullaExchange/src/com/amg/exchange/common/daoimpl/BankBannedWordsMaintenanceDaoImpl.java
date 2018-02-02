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

import com.amg.exchange.common.dao.IBankBannedWordsMaintenanceDao;
import com.amg.exchange.common.model.BankBannedWordsMaintenance;
import com.amg.exchange.treasury.model.StandardInstructionDetails;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class BankBannedWordsMaintenanceDaoImpl extends CommonDaoImpl implements
		IBankBannedWordsMaintenanceDao {

	@Override
	public void saveOrUpdate(
			BankBannedWordsMaintenance bankBannedWordMaintenanceObj) {
		getSession().saveOrUpdate(bankBannedWordMaintenanceObj );
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankBannedWordsMaintenance> getSpecificReords(
			BigDecimal bankId, BigDecimal countryId, String selectionMode) {
		DetachedCriteria dcriteria = DetachedCriteria.forClass(BankBannedWordsMaintenance.class, "bankBannedWordsMaintenance");
		
		dcriteria.setFetchMode("bankBannedWordsMaintenance.countryId",FetchMode.JOIN);
		dcriteria.createAlias("bankBannedWordsMaintenance.countryId", "countryId",JoinType.INNER_JOIN);
		
		dcriteria.setFetchMode("bankBannedWordsMaintenance.bankId",FetchMode.JOIN);
		dcriteria.createAlias("bankBannedWordsMaintenance.bankId", "bankId",JoinType.INNER_JOIN);
		
		dcriteria.add(Restrictions.eq( "bankBannedWordsMaintenance.bankId.bankId",bankId ));
		dcriteria.add(Restrictions.eq( "bankBannedWordsMaintenance.selectionMode",selectionMode));
		dcriteria.add(Restrictions.eq( "bankBannedWordsMaintenance.countryId.countryId",countryId ));
		//dcriteria.add(Restrictions.eq( "bankBannedWordsMaintenance.isActive", Constants.Yes ));
		dcriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	
		return (List<BankBannedWordsMaintenance>)findAll(dcriteria);
 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankBannedWordsMaintenance> getAllBannedWordsList(
			String selectionMode) {
		DetachedCriteria dcriteria = DetachedCriteria.forClass(BankBannedWordsMaintenance.class, "bankBannedWordsMaintenance");
		
		/*dcriteria.setFetchMode("bankBannedWordsMaintenance.countryId",FetchMode.JOIN);
		dcriteria.createAlias("bankBannedWordsMaintenance.countryId", "countryId",JoinType.INNER_JOIN);
		
		dcriteria.setFetchMode("bankBannedWordsMaintenance.bankId",FetchMode.JOIN);
		dcriteria.createAlias("bankBannedWordsMaintenance.bankId", "bankId",JoinType.INNER_JOIN);*/
		dcriteria.add(Restrictions.eq( "bankBannedWordsMaintenance.selectionMode",selectionMode));
		//dcriteria.add(Restrictions.eq( "bankBannedWordsMaintenance.isActive", Constants.Yes));
		dcriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return  (List<BankBannedWordsMaintenance>)findAll(dcriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankBannedWordsMaintenance> duplicateWordCheck(String bannedName) {
	DetachedCriteria dcriteria = DetachedCriteria.forClass(BankBannedWordsMaintenance.class, "bankBannedWordsMaintenance");
		
		dcriteria.setFetchMode("bankBannedWordsMaintenance.countryId",FetchMode.JOIN);
		dcriteria.createAlias("bankBannedWordsMaintenance.countryId", "countryId",JoinType.INNER_JOIN);
		
		dcriteria.setFetchMode("bankBannedWordsMaintenance.bankId",FetchMode.JOIN);
		dcriteria.createAlias("bankBannedWordsMaintenance.bankId", "bankId",JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq( "bankBannedWordsMaintenance.bannedName",bannedName));
		return  (List<BankBannedWordsMaintenance>)findAll(dcriteria);
	}

	@Override
	public void deleteRecord(BigDecimal bankBannedId, String userName) {
		
		BankBannedWordsMaintenance  bankBannedWord = (BankBannedWordsMaintenance) getSession().get(BankBannedWordsMaintenance.class, bankBannedId);
		bankBannedWord.setIsActive(Constants.U);
		bankBannedWord.setModifiedBy(userName);
		bankBannedWord.setModifiedDate(new Date());
		getSession().update(bankBannedWord);
		
	}

	@Override
	public void activateWord(BigDecimal bankBannedId, String userName) {
		BankBannedWordsMaintenance  bankBannedWord = (BankBannedWordsMaintenance) getSession().get(BankBannedWordsMaintenance.class, bankBannedId);
		bankBannedWord.setIsActive(Constants.Yes);
		bankBannedWord.setModifiedBy(userName);
		bankBannedWord.setModifiedDate(new Date());
		getSession().update(bankBannedWord);
		
	}
	

}

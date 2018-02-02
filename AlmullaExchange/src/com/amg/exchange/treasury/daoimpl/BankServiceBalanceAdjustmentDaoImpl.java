package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.treasury.dao.IBankServiceBalanceAdjustmentDao;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryTransfer;
import com.amg.exchange.treasury.viewModel.ViewAccountBalance;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.SessionStateManage;
@SuppressWarnings("rawtypes")
@Repository
public class BankServiceBalanceAdjustmentDaoImpl extends CommonDaoImpl  implements
		IBankServiceBalanceAdjustmentDao {

	SessionStateManage session=new SessionStateManage();
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewAccountBalance> getAllRecordsFrmAccountBalance(
			BigDecimal countryId, BigDecimal currencyId, BigDecimal bankId) {
		
		List<ViewAccountBalance> noBankList=new ArrayList<ViewAccountBalance>();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass( ViewAccountBalance.class, "viewAccountBalance");
		
		if(countryId!=null){
			detachedCriteria.add(Restrictions.eq("viewAccountBalance.countryId", countryId));
		}
		if(currencyId!=null){
			detachedCriteria.add(Restrictions.eq("viewAccountBalance.currencyId", currencyId));
		}
		if(bankId!=null){
			detachedCriteria.add(Restrictions.eq("viewAccountBalance.bankId", bankId));
		}
	
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewAccountBalance> bankList = (List<ViewAccountBalance>)findAll(detachedCriteria);
	  if(bankList.size()>0){
		return bankList;
	  }	 else{
		  return  noBankList;
	  }
	}

	@Override
	public BigDecimal getAccountBalanceIdBasedOnGlno(String glslNumber) {
		BigDecimal accountBalId=null;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass( AccountBalance.class, "accountBalance");
		detachedCriteria.add(Restrictions.eq("accountBalance.glSlNumber", glslNumber));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AccountBalance> bankAccBalList = (List<AccountBalance>)findAll(detachedCriteria);
		if(bankAccBalList.size()>0){
				return bankAccBalList.get(0).getAccountId();
	}
		else{
			return accountBalId;
		}
	}

	@Override
	public void update(BigDecimal accBalPk,BigDecimal eft,BigDecimal tt,BigDecimal cash) {
		
	 AccountBalance accountBalance = (AccountBalance) getSession().get(AccountBalance.class, accBalPk);
	 accountBalance.setEftCurrentBalance(eft);
	 accountBalance.setTtCurrentBalance(tt);
	 accountBalance.setCashCurrencyBalance(cash);
	 accountBalance.setModifiedBy( session.getUserName() );
	 accountBalance.setModifiedDate(new Date());
	 getSession().saveOrUpdate(accountBalance);
		
	}

	@Override
	public void saveTreasuryHeader(TreasuryDealHeader tresuryHeader) {
		getSession().save(tresuryHeader);
		
	}

	@Override
	public void saveTreasuryDetails(TreasuryDealDetail tresuryDealDetail) {
	 getSession().save( tresuryDealDetail);
		
	}

	@Override
	public void saveTreasuryTransfer(TreasuryTransfer treasuryTransfer) throws AMGException {
		try {
			getSession().save(treasuryTransfer);
		} catch (Exception e) {
			new AMGException(e.getMessage());
		}
		
	}
		 
 

}

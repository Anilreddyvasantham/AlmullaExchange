package com.amg.exchange.enquiry.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.enquiry.dao.IPlEnquiryDao;
import com.amg.exchange.enquiry.model.PlEnquiryView;
import com.amg.exchange.treasury.model.BankMaster;

@SuppressWarnings("serial")
@Repository
public class PlEnquiryDaoImpl<T> extends CommonDaoImpl<T> implements IPlEnquiryDao<T>,Serializable {

	@SuppressWarnings("unchecked")
	@Override
	public List<PlEnquiryView> viewRecord(BigDecimal currencyId,BigDecimal bankId) {
      DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PlEnquiryView.class, "plEnquiryView");
		
		detachedCriteria.add(Restrictions.eq("currencyId", currencyId));
		if(bankId !=null){
		detachedCriteria.add(Restrictions.eq("bankId", bankId));
		}
		detachedCriteria.addOrder(Order.asc("plEnquiryView.bankCode"));
		return (List<PlEnquiryView>) findAll(detachedCriteria);
	}

	@Override
	public  List<BankMaster> getBanksList(BigDecimal currencyId) {

				String hqlQuery="select distinct a.exBankMaster.bankId  from BankAccountDetails a where a.fsCurrencyMaster.currencyId =  :currencyId  ";
				Query query = getSession().createQuery(hqlQuery);
				query.setParameter("currencyId", currencyId );
				List<BigDecimal>     bankIdList=query.list();
				 List<BankMaster> finalList=	getBankMasterList(bankIdList);
			return finalList;
	 
		}

		
	public List<BankMaster> getBankMasterList(List<BigDecimal> lstBankId) {

			DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
			
			dCriteria.add(Restrictions.in("bankMaster.bankId", lstBankId));
			dCriteria.addOrder(Order.asc("bankMaster.bankCode"));
					
			dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<BankMaster> lstBankMaster=(List<BankMaster>) findAll(dCriteria);
	 
			return lstBankMaster;
	 
		}
}

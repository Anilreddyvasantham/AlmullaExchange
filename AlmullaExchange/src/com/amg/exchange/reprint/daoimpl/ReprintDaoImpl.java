package com.amg.exchange.reprint.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.reprint.dao.IReprintDao;

@Repository
@SuppressWarnings("unchecked")
public class ReprintDaoImpl<T> extends CommonDaoImpl<T> implements IReprintDao {

	
	@Override
	public List<RemittanceApplicationView> getRecordsRemittanceReceipt(BigDecimal documentNumber, BigDecimal documentYear,BigDecimal applCountryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplicationView.class, "remittanceApplicationView");
		criteria.add(Restrictions.eq("remittanceApplicationView.documentNo", documentNumber));
		criteria.add(Restrictions.eq("remittanceApplicationView.documentFinancialYear", documentNumber));
		criteria.add(Restrictions.eq("remittanceApplicationView.applicationCountryId", applCountryId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RemittanceApplicationView>) findAll(criteria);
	}

	@Override
	public List<RemittanceApplicationView> getRemittancefromCollectionNumber(BigDecimal transactionNo, BigDecimal rEMITTANCE_DOCUMENT_CODE, BigDecimal documentYear,BigDecimal applCountryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplicationView.class, "remittanceApplicationView");
		criteria.add(Restrictions.eq("remittanceApplicationView.collectionDocumentNo", transactionNo));
		criteria.add(Restrictions.eq("remittanceApplicationView.collectionDocmentFinacnceYear", documentYear));
		criteria.add(Restrictions.eq("remittanceApplicationView.collectionDocumentId", documentYear));
		criteria.add(Restrictions.eq("remittanceApplicationView.applicationCountryId", applCountryId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RemittanceApplicationView>) findAll(criteria);
	}

}

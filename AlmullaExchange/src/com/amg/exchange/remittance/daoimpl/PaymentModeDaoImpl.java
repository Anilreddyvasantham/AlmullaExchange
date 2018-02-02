package com.amg.exchange.remittance.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.remittance.dao.PaymentModeDao;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.util.Constants;

@Repository
public class PaymentModeDaoImpl<T> extends CommonDaoImpl<T>
		implements PaymentModeDao, Serializable {

	private static final long serialVersionUID = 2315791709068216697L;
	
	@Override
	public void save(PaymentMode paymentMode) {
		 getSession().saveOrUpdate(paymentMode);

	}

	@Override
	public void saveRecord(PaymentModeDesc  paymentModeDesc) {
       getSession().saveOrUpdate(paymentModeDesc);

	}

	@Override
	public List<PaymentMode> searchRecord(String paymentcode) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PaymentMode.class, "paymentMode");
		
		dCriteria.add(Restrictions.eq("paymentMode.paymentCode", paymentcode));
		
		return ((List<PaymentMode>)findAll(dCriteria));
	}
	
	
	@Override
	public List<PaymentModeDesc> paymentDescRec(BigDecimal paymentId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
		
		dCriteria.setFetchMode("paymentModeDesc.paymentMode",FetchMode.SELECT);
		dCriteria.createAlias("paymentModeDesc.paymentMode", "paymentMode", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("paymentMode.paymentModeId", paymentId));

		return ((List<PaymentModeDesc>)findAll(dCriteria));
	}

	@Override
	public List<String> getPaymntcodelist(String query) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentMode.class, "payment");
		criteria.add(Restrictions.like("payment.paymentCode", query, MatchMode.ANYWHERE).ignoreCase());
		
		criteria.setProjection(Projections.property("payment.paymentCode"));
		criteria.addOrder(Order.asc("payment.paymentCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<String>)findAll(criteria);
	}

	@Override
	public List<PaymentMode> getPayment() {
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentMode.class, "payment");
		criteria.add(Restrictions.eq("payment.isActive", Constants.U));
		criteria.add(Restrictions.isNull("payment.approvBy"));
		criteria.add(Restrictions.isNull("payment.approvDate"));
		
		return (List<PaymentMode>)findAll(criteria);
	}

	@Override
	public String getPaymentDesc(BigDecimal arbId) {
	DetachedCriteria criteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
		
	  criteria.setFetchMode("paymentModeDesc.paymentMode",FetchMode.SELECT);
      criteria.createAlias("paymentModeDesc.paymentMode", "PaymentMode", JoinType.INNER_JOIN);
      criteria.add(Restrictions.eq("paymentMode.paymentModeId", arbId));
     
     criteria.setFetchMode("paymentModeDesc.languageType",FetchMode.SELECT);
     criteria.createAlias("paymentModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
     criteria.add(Restrictions.eq("languageType.languageId", new BigDecimal(Constants.ARABIC_LANGUAGE_ID)));
     
     criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return ((PaymentModeDesc)criteria.getExecutableCriteria(getSession()).list().get(0)).getLocalPaymentName();

		
	}

	@Override
	public String getCreatedBy() {
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentMode.class, "PaymentMode");
		
		return ((PaymentMode)criteria.getExecutableCriteria(getSession()).list().get(0)).getCreatedBy();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentModeDesc> viewRecord() {
	
        DetachedCriteria dCriteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
		
		dCriteria.setFetchMode("paymentModeDesc.paymentMode",FetchMode.JOIN);
		dCriteria.createAlias("paymentModeDesc.paymentMode", "paymentMode", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("paymentModeDesc.languageType",FetchMode.JOIN);
		dCriteria.createAlias("paymentModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return ((List<PaymentModeDesc>)findAll(dCriteria));
	}

	@Override
	public BigDecimal getPaymentPk(String englang) {
		 
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
				
		criteria.add(Restrictions.eq("paymentModeDesc.localPaymentName", englang));
				
		return ((PaymentModeDesc)criteria.getExecutableCriteria(getSession()).list().get(0)).getPaymentModeDescId();
			
	}


	@Override
	public BigDecimal getPaymentarbPk(String arblang) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
		
	    criteria.add(Restrictions.eq("paymentModeDesc.localPaymentName", arblang));

		return ((PaymentModeDesc)criteria.getExecutableCriteria(getSession()).list().get(0)).getPaymentModeDescId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentMode> getPaymentMode() {
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentMode.class, "payment");
	
	    criteria.add(Restrictions.eq("isActive", Constants.Yes));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<PaymentMode>)findAll(criteria);
	}

	@Override
	public String getArbPaymnetName(BigDecimal paymentModeId) {
	  DetachedCriteria criteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentDesc");
		    
	  criteria.setFetchMode("paymentDesc.paymentMode",FetchMode.SELECT);
      criteria.createAlias("paymentDesc.paymentMode", "PaymentMode", JoinType.INNER_JOIN);
      criteria.add(Restrictions.eq("paymentMode.paymentModeId", paymentModeId));
      
      criteria.setFetchMode("paymentDesc.languageType",FetchMode.SELECT);
      criteria.createAlias("paymentDesc.languageType", "languageType", JoinType.INNER_JOIN);
      criteria.add(Restrictions.eq("languageType.languageId", new BigDecimal(Constants.ARABIC_LANGUAGE_ID)));
      
      criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return ((PaymentModeDesc)criteria.getExecutableCriteria(getSession()).list().get(0)).getLocalPaymentName();
	}

	@Override
	public List<PaymentModeDesc> getListOfPayment() {
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
		criteria.setFetchMode("paymentModeDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("paymentModeDesc.languageType", "languageType",JoinType.INNER_JOIN);
		criteria.setFetchMode("paymentModeDesc.paymentMode", FetchMode.JOIN);
		criteria.createAlias("paymentModeDesc.paymentMode", "paymentMode",JoinType.INNER_JOIN);
		//criteria.add(Restrictions.ne("paymentMode.isActive","U"));
		
		return (List<PaymentModeDesc>)findAll(criteria);
	}

	@Override
	public List<PaymentMode> getPaymentCheck(String paymentcode) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentMode.class, "paymentMode");
		
		criteria.add(Restrictions.eq("paymentMode.paymentCode", paymentcode).ignoreCase());
		
		return (List<PaymentMode>)findAll(criteria);
	
	}

	@Override
	public String getPaymentDisc(BigDecimal engid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentDesc");
		

	      
	    criteria.setFetchMode("paymentDesc.paymentMode",FetchMode.SELECT);
      criteria.createAlias("paymentDesc.paymentMode", "PaymentMode", JoinType.INNER_JOIN);
      criteria.add(Restrictions.eq("paymentMode.paymentModeId", engid));
      
      criteria.setFetchMode("paymentDesc.languageType",FetchMode.SELECT);
      criteria.createAlias("paymentDesc.languageType", "languageType", JoinType.INNER_JOIN);
      criteria.add(Restrictions.eq("languageType.languageId", new BigDecimal(Constants.ENGLISH_LANGUAGE_ID)));
      
      criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return ((PaymentModeDesc)criteria.getExecutableCriteria(getSession()).list().get(0)).getLocalPaymentName();
	}

	@Override
	public void delete(PaymentMode paymentMode) {
		getSession().delete(paymentMode );
		
	}

	@Override
	public void delete(PaymentModeDesc paymentModeDesc) {
		 getSession().delete(paymentModeDesc);
		
	}

	@Override
	public List<PaymentModeDesc> fetchPaymodeDesc(BigDecimal langId,String isActive) {
	
        DetachedCriteria dCriteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
		
		dCriteria.setFetchMode("paymentModeDesc.paymentMode",FetchMode.JOIN);
		dCriteria.createAlias("paymentModeDesc.paymentMode", "paymentMode", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("paymentModeDesc.languageType",FetchMode.JOIN);
		dCriteria.createAlias("paymentModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", langId));
		
	     dCriteria.add(Restrictions.eq("paymentMode.isActive", isActive));
		       
        
  
        
        
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return ((List<PaymentModeDesc>)findAll(dCriteria));
	}

	@Override
	public BigDecimal fetchPaymodeMasterId(String paymentModeDesc,BigDecimal langId) {
		 
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
		
		criteria.setFetchMode("paymentModeDesc.paymentMode", FetchMode.JOIN);
		criteria.createAlias("paymentModeDesc.paymentMode", "paymentMode",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("paymentModeDesc.languageType",FetchMode.SELECT);
	    criteria.createAlias("paymentModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
	    criteria.add(Restrictions.eq("languageType.languageId", langId));
	    
		criteria.add(Restrictions.eq("paymentModeDesc.localPaymentName", paymentModeDesc));
				
		return ((PaymentModeDesc)criteria.getExecutableCriteria(getSession()).list().get(0)).getPaymentMode().getPaymentModeId();
			
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentModeDesc> getRecordsFromDb(BigDecimal paymentModeId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
		criteria.setFetchMode("paymentModeDesc.paymentMode", FetchMode.JOIN);
		criteria.createAlias("paymentModeDesc.paymentMode", "paymentMode",JoinType.INNER_JOIN);
		criteria.setFetchMode("paymentModeDesc.languageType",FetchMode.SELECT);
	      criteria.createAlias("paymentModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("paymentMode.paymentModeId",paymentModeId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<PaymentModeDesc>) findAll(criteria);
		
	}

	@Override
	public void deleteRecordPermrntely(BigDecimal dtpymPk,BigDecimal dtpymDescpken, BigDecimal dtpymDescpkarb) {
		PaymentMode paymentMode=(PaymentMode) getSession().get(PaymentMode.class, dtpymPk);
		PaymentModeDesc paymentModeDesEng=(PaymentModeDesc) getSession().get(PaymentModeDesc.class, dtpymDescpken);
		PaymentModeDesc paymentModeDesarb=(PaymentModeDesc) getSession().get(PaymentModeDesc.class, dtpymDescpkarb);
		getSession().delete(paymentModeDesEng);
		getSession().delete(paymentModeDesarb);
		getSession().delete(paymentMode);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentModeDesc> getPaymentDescLangList(BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
		criteria.setFetchMode("paymentModeDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("paymentModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.setFetchMode("paymentModeDesc.paymentMode", FetchMode.JOIN);
		criteria.createAlias("paymentModeDesc.paymentMode", "paymentMode", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("paymentMode.isActive", Constants.Yes));
		return (List<PaymentModeDesc>) findAll(criteria);
	}

	@Override
	public String approveRecord(BigDecimal paymentModePk, String userName) {
		 
		String approveMsg;
		PaymentMode paymentMode = (PaymentMode) getSession().get(PaymentMode.class, paymentModePk);
		String approvedUser=paymentMode.getApprovBy();
		if(approvedUser==null)
		{
			paymentMode.setIsActive(Constants.Yes);
			paymentMode.setApprovBy(userName);
			paymentMode.setApprovDate(new Date());
			getSession().update(paymentMode);
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String paymentModeDescription(BigDecimal paymentId, BigDecimal langId) {
		  String paymentDesc = null;
		  DetachedCriteria criteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
		  criteria.setFetchMode("paymentModeDesc.languageType", FetchMode.JOIN);
		  criteria.createAlias("paymentModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		  criteria.add(Restrictions.eq("languageType.languageId", langId));
		  criteria.setFetchMode("paymentModeDesc.paymentMode", FetchMode.JOIN);
		  criteria.createAlias("paymentModeDesc.paymentMode", "paymentMode", JoinType.INNER_JOIN);
		  criteria.add(Restrictions.eq("paymentMode.paymentModeId", paymentId));
		  criteria.add(Restrictions.eq("paymentMode.isActive", Constants.Yes));
		  
		  List<PaymentModeDesc> lstPaymentMode = (List<PaymentModeDesc>) findAll(criteria);
		  
		  if(lstPaymentMode.size() != 0){
			    paymentDesc = lstPaymentMode.get(0).getLocalPaymentName();
		  }
		  
		  return paymentDesc;
	}

	@Override
	public List<PaymentMode> getAllRecordsFrmPaymentMode() {
	DetachedCriteria criteria = DetachedCriteria.forClass(PaymentMode.class, "paymentMode");
	return (List<PaymentMode>)findAll(criteria); 
	 
	}

	@Override
	public List<PaymentModeDesc> getAllRecordsBasedOnPaymentModeId(
			BigDecimal paymentModeId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PaymentModeDesc.class,"paymentModeDesc" );
		dCriteria.setFetchMode("paymentModeDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("paymentModeDesc.languageType", "languageType",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq( "paymentModeDesc.paymentMode.paymentModeId", paymentModeId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<PaymentModeDesc>)findAll(dCriteria);
	}

	
	@Override
	public List<PaymentModeDesc> fetchPaymodeDescForStopPayment(BigDecimal langId,String isActive) {
	
        DetachedCriteria dCriteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
		
		dCriteria.setFetchMode("paymentModeDesc.paymentMode",FetchMode.JOIN);
		dCriteria.createAlias("paymentModeDesc.paymentMode", "paymentMode", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("paymentModeDesc.languageType",FetchMode.JOIN);
		dCriteria.createAlias("paymentModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", langId));
	    dCriteria.add(Restrictions.eq("paymentMode.isActive", isActive));
		       
       // Criterion criteria1=Restrictions.eq("paymentMode.paymentCode" , Constants.CashCode);
       // Criterion criteria2=Restrictions.eq("paymentMode.paymentCode",Constants.KNETCode);
       // LogicalExpression orExp = Restrictions. (criteria1, criteria2);
      //  dCriteria.add( orExp );
        
        
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return ((List<PaymentModeDesc>)findAll(dCriteria));
	}

	@Override
	public List<PaymentModeDesc> searchRecordByCode(String paymentcode) {
		
		BigDecimal paymentId = null;
		List<PaymentModeDesc> lstPaymentMode = null;
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PaymentMode.class, "paymentMode");
		
		dCriteria.add(Restrictions.eq("paymentMode.paymentCode", paymentcode));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<PaymentMode> lstPayment = (List<PaymentMode>)findAll(dCriteria);
		
		if(lstPayment != null && lstPayment.size() != 0){
			PaymentMode paymentMode = lstPayment.get(0);
			paymentId = paymentMode.getPaymentModeId();

			DetachedCriteria criteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
			criteria.setFetchMode("paymentModeDesc.languageType", FetchMode.JOIN);
			criteria.createAlias("paymentModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("languageType.languageId", BigDecimal.ONE));
			criteria.setFetchMode("paymentModeDesc.paymentMode", FetchMode.JOIN);
			criteria.createAlias("paymentModeDesc.paymentMode", "paymentMode", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("paymentMode.paymentModeId", paymentId));
			criteria.add(Restrictions.eq("paymentMode.isActive", Constants.Yes));

			dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			lstPaymentMode = (List<PaymentModeDesc>) findAll(criteria);
		}
		
		return lstPaymentMode;
	}
	
	
	
	/*@Override
	@SuppressWarnings("unchecked")
	public List<String> getAllBusinessComponent(String query) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BusinessComponent.class, "bussComponentComboData");
		criteria.add(Restrictions.like("bussComponentComboData.componentName", query, MatchMode.ANYWHERE).ignoreCase());
		
		criteria.setProjection(Projections.property("bussComponentComboData.componentName"));
		criteria.addOrder(Order.asc("bussComponentComboData.componentName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<String>)findAll(criteria);
	}*/
	

}

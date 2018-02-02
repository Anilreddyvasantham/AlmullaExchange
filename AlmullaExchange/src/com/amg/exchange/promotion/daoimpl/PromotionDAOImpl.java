package com.amg.exchange.promotion.daoimpl;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.cancelreissue.model.ViewRemittanceDocument;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.promotion.dao.IPromotionDAO;
import com.amg.exchange.promotion.model.PromotionDetails;
import com.amg.exchange.promotion.model.PromotionMaster;
import com.amg.exchange.promotion.model.PromotionPrize;
import com.amg.exchange.remittance.bean.PopulateData;

@Repository
@SuppressWarnings("unchecked")
public class PromotionDAOImpl extends CommonDaoImpl implements IPromotionDAO {

	@Override
	public void savePromotionMaster(PromotionMaster promotionMaster,List<PromotionPrize> lstPromotionPrize){
		
		getSession().saveOrUpdate(promotionMaster);
		if(lstPromotionPrize!=null && lstPromotionPrize.size()>0)
		{
			for(PromotionPrize promotionPrize :lstPromotionPrize)
			{
				promotionPrize.setPromotionMaster(promotionMaster);
				getSession().saveOrUpdate(promotionPrize);
			}
		}		
	}
	
	@Override
	public void updatePromotionPrize(List<PromotionPrize> lstPromotionPrize,BigDecimal promotionMasterId,String userName){
		
		PromotionMaster promotionMaster=(PromotionMaster)getSession().get(PromotionMaster.class, promotionMasterId);
		promotionMaster.setModifiedBy(userName);
		promotionMaster.setModifiedDate(new Date());
		getSession().saveOrUpdate(promotionMaster);
		
		for(PromotionPrize promotionPrize : lstPromotionPrize){
			
			promotionPrize.setPromotionMaster(promotionMaster);
			getSession().saveOrUpdate(promotionPrize);
			
		}					
	}
	
	@Override
	public List<PromotionMaster> docNumbers(){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PromotionMaster.class,"promotionMaster");	
		
		dCriteria.addOrder(Order.asc("promotionMaster.documentNo"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<PromotionMaster>)findAll(dCriteria);	
	}
	
	@Override
	public List<PromotionMaster> lstPromotionMaster(BigDecimal companyId, BigDecimal documentId,BigDecimal applicationYear,BigDecimal docNum){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PromotionMaster.class,"promotionMaster");
		
		dCriteria.setFetchMode("promotionMaster.documentMaster", FetchMode.JOIN);
		dCriteria.createAlias("promotionMaster.documentMaster", "documentMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("documentMaster.documentID", documentId));
		
		
		dCriteria.setFetchMode("promotionMaster.companyMaster", FetchMode.JOIN);
		dCriteria.createAlias("promotionMaster.companyMaster", "companyMaster", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("companyMaster.companyId", companyId));
		
		dCriteria.add(Restrictions.eq("promotionMaster.documentFinancialYear", applicationYear));
		
		dCriteria.add(Restrictions.eq("promotionMaster.documentNo", docNum));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<PromotionMaster>)findAll(dCriteria);	
	}
	
	@Override
	public List<PromotionPrize> lstPromotionPrize(BigDecimal exPromoHdSeq){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PromotionPrize.class,"promotionPrize");
		
		dCriteria.setFetchMode("promotionPrize.promotionMaster", FetchMode.JOIN);
		dCriteria.createAlias("promotionPrize.promotionMaster", "promotionMaster",JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("promotionMaster.promotionMasterId", exPromoHdSeq));
		dCriteria.addOrder(Order.asc("promotionPrize.fromDate"));
		dCriteria.addOrder(Order.asc("promotionPrize.prizeNo"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<PromotionPrize>)findAll(dCriteria);	
	}
	
	@Override
	public void delete(BigDecimal promoPrizeSeq) {
		PromotionPrize promotionPrize=(PromotionPrize)getSession().get(PromotionPrize.class, promoPrizeSeq);
		getSession().delete(promotionPrize); 		
	}
	
	@Override
	public List<PopulateData> getPromotionDocumentNo(BigDecimal companyId,
			BigDecimal documentId, BigDecimal applicationYear) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(PromotionMaster.class, "promotionMaster");
		
		criteria.setFetchMode("promotionMaster.documentMaster", FetchMode.JOIN);
		criteria.createAlias("promotionMaster.documentMaster", "documentMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("documentMaster.documentID", documentId));
		
		
		criteria.setFetchMode("promotionMaster.companyMaster", FetchMode.JOIN);
		criteria.createAlias("promotionMaster.companyMaster", "companyMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("companyMaster.companyId", companyId));
		
		criteria.add(Restrictions.eq("promotionMaster.documentFinancialYear", applicationYear));
		
		
		ProjectionList columns = Projections.projectionList();

		criteria.addOrder(Order.asc("promotionMaster.documentNo"));
		columns.add(Projections.property("promotionMaster.documentNo"), "populateId");		
		columns.add(Projections.property("promotionMaster.promotionDescription"),"populateCode");
		//columns.add(Projections.property("serviceGroupDesc"), "populateName");
		criteria.setProjection(columns);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(PopulateData.class));
		
		List<PopulateData> lstPopulateData= (List<PopulateData>) findAll(criteria);
		return lstPopulateData;
	}

	@Override
	public List<PromotionMaster> getPromotionMasterDetails(
			BigDecimal companyId, BigDecimal documentId,
			BigDecimal applicationYear, BigDecimal documentNo) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(PromotionMaster.class, "promotionMaster");
		
		criteria.setFetchMode("promotionMaster.documentMaster", FetchMode.JOIN);
		criteria.createAlias("promotionMaster.documentMaster", "documentMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("documentMaster.documentID", documentId));
		
		
		criteria.setFetchMode("promotionMaster.companyMaster", FetchMode.JOIN);
		criteria.createAlias("promotionMaster.companyMaster", "companyMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("companyMaster.companyId", companyId));
		
		criteria.add(Restrictions.eq("promotionMaster.documentFinancialYear", applicationYear));
		
		criteria.add(Restrictions.eq("promotionMaster.documentNo", documentNo));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<PromotionMaster> lstPromotionMaster= (List<PromotionMaster>) findAll(criteria);
		
		return lstPromotionMaster;
	}

	@Override
	public List<PromotionPrize> getPromotionPrizeDetails(
			BigDecimal appicationCountryId, BigDecimal documentId,
			BigDecimal promotionMasterId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(PromotionPrize.class, "promotionPrize");
		
		criteria.setFetchMode("promotionPrize.promotionMaster", FetchMode.JOIN);
		criteria.createAlias("promotionPrize.promotionMaster", "promotionMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("promotionMaster.promotionMasterId", promotionMasterId));
		
		criteria.addOrder(Order.asc("promotionPrize.prizeNo"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<PromotionPrize> lstPromotionPrize= (List<PromotionPrize>) findAll(criteria);
		
		return lstPromotionPrize;
	}

	@Override
	public List<PromotionDetails> getPromotionDetails(
			BigDecimal promotionMasterId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(PromotionDetails.class, "promotionDetails");
		
		criteria.setFetchMode("promotionDetails.promotionMaster", FetchMode.JOIN);
		criteria.createAlias("promotionDetails.promotionMaster", "promotionMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("promotionMaster.promotionMasterId", promotionMasterId));
		
		criteria.setFetchMode("promotionDetails.promotionPrize", FetchMode.JOIN);
		criteria.createAlias("promotionDetails.promotionPrize", "promotionPrize", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.isNull("promotionDetails.declarationDate"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<PromotionDetails> lstPromotionDetails= (List<PromotionDetails>) findAll(criteria);
		
		return lstPromotionDetails;
	}
	
	@Override
	public List<PromotionDetails> getPromotionEnquiryDetails(BigDecimal promotionMasterId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(PromotionDetails.class, "promotionDetails");
		
		criteria.setFetchMode("promotionDetails.promotionMaster", FetchMode.JOIN);
		criteria.createAlias("promotionDetails.promotionMaster", "promotionMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("promotionMaster.promotionMasterId", promotionMasterId));
		
		criteria.setFetchMode("promotionDetails.promotionPrize", FetchMode.JOIN);
		criteria.createAlias("promotionDetails.promotionPrize", "promotionPrize", JoinType.INNER_JOIN);
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<PromotionDetails> lstPromotionDetails= (List<PromotionDetails>) findAll(criteria);
		
		return lstPromotionDetails;
	}

	@Override
	public void savePromotionDetails(List<PromotionDetails> lstPromotionDetails) {
		
		if(lstPromotionDetails!=null && lstPromotionDetails.size()>0)
		{
			for(PromotionDetails promotionDetails :lstPromotionDetails)
			{
				getSession().saveOrUpdate(promotionDetails);
			}
		}
	}

	@Override
	public List<ViewRemiitanceInfo> verifyRemittanceDetails(
			BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,
			BigDecimal documentFinanceYear, BigDecimal documentNo ,BigDecimal customerId) {
		
		DetachedCriteria critiria = DetachedCriteria.forClass(ViewRemiitanceInfo.class,"remittanceView");

		critiria.add(Restrictions.eq("remittanceView.applicationCountryId", appCountryId));
		critiria.add(Restrictions.eq("remittanceView.companyId", companyId));
		critiria.add(Restrictions.eq("remittanceView.documentCode", documentCode));
		critiria.add(Restrictions.eq("remittanceView.documentFinYear", documentFinanceYear));
		critiria.add(Restrictions.eq("remittanceView.documentNo", documentNo));
		critiria.add(Restrictions.eq("remittanceView.customerId", customerId));
		
		critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewRemiitanceInfo> remittanceList = (List<ViewRemiitanceInfo>) findAll(critiria);
		return remittanceList;

	}
	
	@Override
	public List<ViewRemittanceDocument> verifyRemittanceDetailsWithRemitTransaction(
			BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,
			BigDecimal documentFinanceYear, BigDecimal documentNo ,BigDecimal customerId) {
		
		DetachedCriteria critiria = DetachedCriteria.forClass(ViewRemittanceDocument.class,"viewRemittanceDocument");		

		critiria.add(Restrictions.eq("viewRemittanceDocument.applicationCountryId", appCountryId));
		critiria.add(Restrictions.eq("viewRemittanceDocument.companyId", companyId));
		critiria.add(Restrictions.eq("viewRemittanceDocument.documanetCode", documentCode));
		critiria.add(Restrictions.eq("viewRemittanceDocument.documentFinanceYear", documentFinanceYear));
		critiria.add(Restrictions.eq("viewRemittanceDocument.documentNo", documentNo));
		critiria.add(Restrictions.eq("viewRemittanceDocument.customerId", customerId));
		
		critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewRemittanceDocument> remittanceList = (List<ViewRemittanceDocument>) findAll(critiria);
		return remittanceList;

	}
	
	
	public List<ViewRemiitanceInfo> verifyRemittanceDetailsBasedOnTransId(BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,BigDecimal transActionId)
	{
		DetachedCriteria critiria = DetachedCriteria.forClass(ViewRemiitanceInfo.class,"remittanceView");

		critiria.add(Restrictions.eq("remittanceView.applicationCountryId", appCountryId));
		critiria.add(Restrictions.eq("remittanceView.companyId", companyId));
		critiria.add(Restrictions.eq("remittanceView.documentCode", documentCode));
		critiria.add(Restrictions.eq("remittanceView.remittanceTransactionId", transActionId));
		//critiria.add(Restrictions.eq("remittanceView.documentFinYear", documentFinanceYear));
		//critiria.add(Restrictions.eq("remittanceView.documentNo", documentNo));
		
		critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewRemiitanceInfo> remittanceList = (List<ViewRemiitanceInfo>) findAll(critiria);
		
		return remittanceList;

	}

	@Override
	public void deletePromotionDetails(BigDecimal promotionDetailsId) {
		PromotionDetails promotionDetails=(PromotionDetails) getSession().get(PromotionDetails.class, promotionDetailsId);
		getSession().delete(promotionDetails);
	}

	@Override
	public void savePromotionDeclaration(BigDecimal promotionDeclartionId,
			Clob custSignature, String userName) {
		Date currentDatePlusOne=addOneYear();
		PromotionDetails promotionDetails=(PromotionDetails) getSession().get(PromotionDetails.class, promotionDeclartionId);
		promotionDetails.setSignatureSpecimenClob(custSignature);
		promotionDetails.setDeclarationDate(new Date());
		promotionDetails.setModifiedBy(userName);
		promotionDetails.setModifiedDate(new Date());
		promotionDetails.setValidUpto(currentDatePlusOne);
		getSession().saveOrUpdate(promotionDetails);
		
	}

	@Override
	public PromotionDetails getPromotionDetailsReport(
			BigDecimal promotionDetailsId) {
		
		PromotionDetails promotionDetails=(PromotionDetails) getSession().get(PromotionDetails.class, promotionDetailsId);
		
		return promotionDetails;
	}
	
	public HashMap<String, List<PromotionPrize>> getPromotionPrizeDetailsForWinner(
			BigDecimal appicationCountryId, BigDecimal documentId,
			BigDecimal promotionMasterId) {
		List<PromotionPrize> lstDeclaredPromotionPrize= new ArrayList<PromotionPrize>();
		List<PromotionPrize> lstNotDeclaredPromotionPrize= new ArrayList<PromotionPrize>();
		HashMap<String, List<PromotionPrize>> rtnMap= new HashMap<String, List<PromotionPrize>>();
				
		DetachedCriteria criteria = DetachedCriteria.forClass(PromotionPrize.class, "promotionPrize");
		
		criteria.setFetchMode("promotionPrize.promotionMaster", FetchMode.JOIN);
		criteria.createAlias("promotionPrize.promotionMaster", "promotionMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("promotionMaster.promotionMasterId", promotionMasterId));
		criteria.addOrder(Order.asc("promotionPrize.prizeNo"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<PromotionPrize> lstPromotionPrize= (List<PromotionPrize>) findAll(criteria);
		
		if(lstPromotionPrize!=null && lstPromotionPrize.size()>0)
		{
			for(PromotionPrize promotionPrize :lstPromotionPrize)
			{
				List<PromotionDetails> lstPromotionDetails=getDeclaredPromotionDetails(promotionMasterId, promotionPrize.getPromotionPrizeId());
				if(lstPromotionDetails!=null && lstPromotionDetails.size()>0)
				{
					PromotionDetails promotionDetails =lstPromotionDetails.get(0);
					if(promotionDetails.getDeclarationDate()!=null)
					{
						lstDeclaredPromotionPrize.add(promotionPrize);
					}else
					{
						lstNotDeclaredPromotionPrize.add(promotionPrize);
					}
					
				}else
				{
					lstNotDeclaredPromotionPrize.add(promotionPrize);
				}
			}
		}
		rtnMap.put("DeclaredPromotionPrize", lstDeclaredPromotionPrize);
		rtnMap.put("NotDeclaredPromotionPrize", lstNotDeclaredPromotionPrize);
		
		return rtnMap;
	}
	private List<PromotionDetails> getDeclaredPromotionDetails(
			BigDecimal promotionMasterId,BigDecimal promotionPrizeId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(PromotionDetails.class, "promotionDetails");
		
		criteria.setFetchMode("promotionDetails.promotionMaster", FetchMode.JOIN);
		criteria.createAlias("promotionDetails.promotionMaster", "promotionMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("promotionMaster.promotionMasterId", promotionMasterId));
		
		criteria.setFetchMode("promotionDetails.promotionPrize", FetchMode.JOIN);
		criteria.createAlias("promotionDetails.promotionPrize", "promotionPrize", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("promotionPrize.promotionPrizeId", promotionPrizeId));
		
		//criteria.add(Restrictions.isNull("promotionDetails.declarationDate"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<PromotionDetails> lstPromotionDetails= (List<PromotionDetails>) findAll(criteria);
		
		return lstPromotionDetails;
	}
	
	@Override
	public List<PromotionDetails> promotionDetailsList(BigDecimal promotionPrizeSeq){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PromotionDetails.class,"promotionDetails");
		
		dCriteria.setFetchMode("promotionDetails.promotionPrize", FetchMode.JOIN);
		dCriteria.createAlias("promotionDetails.promotionPrize", "promotionPrize",JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("promotionPrize.promotionPrizeId", promotionPrizeSeq));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<PromotionDetails>)findAll(dCriteria);
	}

	@Override
	public BigDecimal getPromoUsedAmount(
			BigDecimal promotionDetailsId) {
		
		String queryString = "SELECT USED_PROM_AMT FROM EX_PROMO_DETAIL WHERE  EX_PROMO_DT_SEQ =?" ;
		Query query = getSession().createSQLQuery(queryString);
		query.setBigDecimal(0, promotionDetailsId);
		
		BigDecimal dbUsedAmount = (BigDecimal)query.uniqueResult();
		return dbUsedAmount;
	}
	
	@Override
	public List<ViewRemittanceDocument> verifyRemittanceBasedOnTransId(BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,BigDecimal transActionId)
	{
		DetachedCriteria critiria = DetachedCriteria.forClass(ViewRemittanceDocument.class,"viewRemittanceDocument");		
		
		critiria.add(Restrictions.eq("viewRemittanceDocument.remittanceTransactionId", transActionId));		
		
		critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewRemittanceDocument> remittanceList = (List<ViewRemittanceDocument>) findAll(critiria);
		
		return remittanceList;

	}
	
	
	public List<PromotionPrize>  getPrizeNoFromDate(BigDecimal promotionMasterId,Date fromDate)
	{
		List<PromotionPrize> lstDeclaredPromotionPrize= new ArrayList<PromotionPrize>();
		List<PromotionPrize> lstNotDeclaredPromotionPrize= new ArrayList<PromotionPrize>();
		List<BigDecimal> lstPrizeNo= new ArrayList<BigDecimal>();
		HashMap<String, List<PromotionPrize>> rtnMap= new HashMap<String, List<PromotionPrize>>();
				
		DetachedCriteria criteria = DetachedCriteria.forClass(PromotionPrize.class, "promotionPrize");
		
		criteria.setFetchMode("promotionPrize.promotionMaster", FetchMode.JOIN);
		criteria.createAlias("promotionPrize.promotionMaster", "promotionMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("promotionMaster.promotionMasterId", promotionMasterId));
		
		criteria.add(Restrictions.eq("promotionPrize.fromDate", fromDate));
		
		criteria.addOrder(Order.asc("promotionPrize.prizeNo"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<PromotionPrize> lstPromotionPrize= (List<PromotionPrize>) findAll(criteria);
		
		if(lstPromotionPrize!=null && lstPromotionPrize.size()>0)
		{
			for(PromotionPrize promotionPrize :lstPromotionPrize)
			{
				List<PromotionDetails> lstPromotionDetails=getDeclaredPromotionDetails(promotionMasterId, promotionPrize.getPromotionPrizeId());
				if(lstPromotionDetails!=null && lstPromotionDetails.size()>0)
				{
					PromotionDetails promotionDetails =lstPromotionDetails.get(0);
					if(promotionDetails.getDeclarationDate()!=null)
					{
						lstDeclaredPromotionPrize.add(promotionPrize);
					}else
					{
						lstNotDeclaredPromotionPrize.add(promotionPrize);
					}
					
				}else
				{
					lstNotDeclaredPromotionPrize.add(promotionPrize);
				}
			}
		}
		
		/*if(lstNotDeclaredPromotionPrize!=null && lstNotDeclaredPromotionPrize.size()>0)
		{
			for(PromotionPrize promotionPrize:lstNotDeclaredPromotionPrize)
			{
				lstPrizeNo.add(promotionPrize.getPrizeNo());
			}
		}*/
		return lstNotDeclaredPromotionPrize;
	}
	private Date addOneYear()
	{
		
		Date currentDate = new Date();
       

        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        // manipulate date
        c.add(Calendar.YEAR, 1);
       /* c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, 1); //same with c.add(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.HOUR, 1);
        c.add(Calendar.MINUTE, 1);
        c.add(Calendar.SECOND, 1);*/

        // convert calendar to date
        Date currentDatePlusOne = c.getTime();
        return currentDatePlusOne;
	}
}

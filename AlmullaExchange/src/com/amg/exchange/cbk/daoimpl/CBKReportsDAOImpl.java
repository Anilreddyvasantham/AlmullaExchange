package com.amg.exchange.cbk.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.cbk.dao.CBKReportsDAO;
import com.amg.exchange.cbk.model.CBKDetails;
import com.amg.exchange.cbk.model.CBKHeader;
import com.amg.exchange.cbk.model.CBKLines;
import com.amg.exchange.cbk.model.CBKTotals;
import com.amg.exchange.cbk.model.ViewExUnmappedGLS;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.jvprocess.model.ViewAccountCategory;
import com.amg.exchange.jvprocess.model.ViewAccountClass;
import com.amg.exchange.jvprocess.model.ViewActivitycenterAcnt;
import com.amg.exchange.jvprocess.model.ViewBusinessoperationAlev2;
import com.amg.exchange.jvprocess.model.ViewFranchiseAlev3;
import com.amg.exchange.jvprocess.model.ViewProductAlev4;
import com.amg.exchange.jvprocess.model.ViewmainActivityALev1;
import com.amg.exchange.util.Constants;

@Repository
@SuppressWarnings("unchecked")
public class CBKReportsDAOImpl<T> extends CommonDaoImpl<T> implements CBKReportsDAO {
	
	
	@Override
	public List<ViewmainActivityALev1> getViewAlev1List() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewmainActivityALev1.class,"viewmainActivityALev1");		
		
		criteria.addOrder(Order.asc("viewmainActivityALev1.al1Des"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<ViewmainActivityALev1>) findAll(criteria);
	}
	
	@Override
	public List<ViewBusinessoperationAlev2> getViewAlev2List(String alev1Code) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewBusinessoperationAlev2.class,"viewBusinessoperationAlev2");
		
		criteria.add(Restrictions.eq("viewBusinessoperationAlev2.al1Cod", alev1Code));
		criteria.addOrder(Order.asc("viewBusinessoperationAlev2.al2Des"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewBusinessoperationAlev2> lstViewBusinessoperationAlev2=(List<ViewBusinessoperationAlev2>) findAll(criteria);
		
		/*String hqlQuery="select distinct a.al2Cod from ViewBusinessoperationAlev2 a where a.al2Cod like ?";


		Query query = getSession().createQuery(hqlQuery);
		query.setParameter(0, "%"+alev1Code+"%");
		List<String> rtnList =	query.list();
		
		String hqlQuery="from ViewBusinessoperationAlev2 a   where a.al1Cod =:pal1Cod ";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("pal1Cod", alev1Code);
		

		List<ViewBusinessoperationAlev2> lstViewBusinessoperationAlev2=(List<ViewBusinessoperationAlev2>)query.list();*/

		
		return lstViewBusinessoperationAlev2;
	}
	
	@Override
	public List<ViewFranchiseAlev3> getViewAlev3List(String alev2Code) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewFranchiseAlev3.class,"viewFranchiseAlev3");
		
		criteria.add(Restrictions.eq("viewFranchiseAlev3.al2Com", alev2Code));
		criteria.addOrder(Order.asc("viewFranchiseAlev3.al3Des"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<ViewFranchiseAlev3>) findAll(criteria);
	}
	
	@Override
	public List<ViewProductAlev4> getViewAlev4List(String alev3Code) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewProductAlev4.class,"viewProductAlev4");
		
		criteria.add(Restrictions.eq("viewProductAlev4.al3Com", alev3Code));
		criteria.addOrder(Order.asc("viewProductAlev4.al4Des"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<ViewProductAlev4>) findAll(criteria);
	}
	
	@Override
	public List<ViewActivitycenterAcnt> getViewActivitycenterAcntList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewActivitycenterAcnt.class,"viewActivitycenterAcnt");		
		
		criteria.addOrder(Order.asc("viewActivitycenterAcnt.fudesc"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<ViewActivitycenterAcnt>) findAll(criteria);
	}

	@Override
	public HashMap<String, BigDecimal> saveCBKHeaderAndLines(
			CBKHeader cBKHeader, CBKLines cBKLines) {
		
		getSession().saveOrUpdate(cBKHeader);
		
		getSession().saveOrUpdate(cBKLines);
		
		HashMap<String, BigDecimal> rtnMap= new HashMap<String, BigDecimal>();
		rtnMap.put("CBKHRDID", cBKHeader.getCbkId());
		rtnMap.put("CBKLINEID", cBKLines.getCbkLineId());
		return rtnMap;
	}

	@Override
	public HashMap<String, BigDecimal> saveMainCBKHeaderAndLines(
			CBKHeader cBKHeader, List<CBKLines> lstCBKLines) {
		
		getSession().saveOrUpdate(cBKHeader);
		
		if(lstCBKLines!=null && lstCBKLines.size()>0)
		{
			for(CBKLines cBKLines :lstCBKLines)
			{
				getSession().saveOrUpdate(cBKLines);
			}
		}
		
		HashMap<String, BigDecimal> rtnMap= new HashMap<String, BigDecimal>();
		rtnMap.put("CBKHRDID", cBKHeader.getCbkId());
		//rtnMap.put("CBKLINEID", cBKLines.getCbkLineId());
		
		return rtnMap;
	}
	
	@Override
	public List<CBKLines> getcbkHdrLinesDetails(String reportNo, BigDecimal applicationCountryId)
	{
		List<CBKLines> lstCBKLines = new ArrayList<CBKLines>();
		DetachedCriteria criteria = DetachedCriteria.forClass(CBKHeader.class, "cBKHeader");
		criteria.add(Restrictions.eq("cBKHeader.reportNo", reportNo));
		criteria.add(Restrictions.eq("cBKHeader.applicationCountryId", applicationCountryId));
		criteria.add(Restrictions.eq("cBKHeader.isactive", Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CBKHeader> lstCBKHeader =(List<CBKHeader>) findAll(criteria);
		
		if(lstCBKHeader!=null && lstCBKHeader.size()>0)
		{
			lstCBKLines=getCbkLines(lstCBKHeader.get(0).getCbkId());
		}
		return lstCBKLines;
	}

	private List<CBKLines> getCbkLines(BigDecimal cbkReportSeqId)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CBKLines.class, "cBKLines");
		
		criteria.setFetchMode("cBKLines.cbkId", FetchMode.JOIN);
		criteria.createAlias("cBKLines.cbkId", "cbkId", JoinType.INNER_JOIN);
		criteria.addOrder(Order.asc("cBKLines.lineNo"));
		criteria.add(Restrictions.eq("cbkId.cbkId", cbkReportSeqId));	
		
		criteria.add(Restrictions.eq("cBKLines.isActive", Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CBKLines> lstCBKLines =(List<CBKLines>) findAll(criteria);
		
		return lstCBKLines;
	}
	@Override
	public String checkCbkDetailsAndTotalCount(BigDecimal cbkHdrSeqiId,BigDecimal cbkLineSeqId,String lineType,String userName)
	{
		String recordExist= null;
		
		
		String hqlQuery="select count(*) from CBKTotals a inner join a.cbkId b inner join  a.cbkLineId c  where b.cbkId =:pcbkHdrSeqiId  and c.cbkLineId=:pcbkLineSeqId and  a.isActive='Y'";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("pcbkHdrSeqiId", cbkHdrSeqiId);
		query.setParameter("pcbkLineSeqId", cbkLineSeqId);

		Long cbkTotalCount = (Long)query.uniqueResult();
		if(cbkTotalCount!=null && cbkTotalCount.compareTo(Long.valueOf("0"))==0)
		{
			
			hqlQuery="select count(*) from CBKTotals a inner join a.cbkId b inner join  a.cbkLineId c  where b.cbkId =:pcbkHdrSeqiId  and a.totalLineSeqId=:pcbkLineSeqId and  a.isActive='Y'";
			query = getSession().createQuery(hqlQuery);
			query.setParameter("pcbkHdrSeqiId", cbkHdrSeqiId);
			query.setParameter("pcbkLineSeqId", cbkLineSeqId);
			Long cbkTotalCount1 = (Long)query.uniqueResult();
			if(cbkTotalCount1!=null && cbkTotalCount1.compareTo(Long.valueOf("0"))==0)
			{
				recordExist=Constants.No;
			}else
			{
				recordExist=Constants.Yes;
			}
			
			//deactivateCBKLines(cbkHdrSeqiId, cbkLineSeqId, lineType, userName);
		}else
		{
			recordExist=Constants.Yes;
		}
		
		if(recordExist!=null && recordExist.equalsIgnoreCase(Constants.No))
		{
			hqlQuery="select count(*) from CBKDetails a inner join a.cbkId b inner join  a.cbkLineId c  where b.cbkId =:pcbkHdrSeqiId  and c.cbkLineId=:pcbkLineSeqId and  a.isActive='Y'";
			query = getSession().createQuery(hqlQuery);
			query.setParameter("pcbkHdrSeqiId", cbkHdrSeqiId);
			query.setParameter("pcbkLineSeqId", cbkLineSeqId);

			Long cbkDetailsCount = (Long)query.uniqueResult();
			
			if(cbkDetailsCount!=null && cbkDetailsCount.compareTo(Long.valueOf("0"))==0)
			{
				recordExist=Constants.No;
				//deactivateCBKLines(cbkHdrSeqiId, cbkLineSeqId, lineType, userName);
			}else
			{
				recordExist=Constants.Yes;
			}
		}
		if(recordExist!=null && recordExist.equalsIgnoreCase(Constants.No))
		{
			deactivateCBKLines(cbkHdrSeqiId, cbkLineSeqId, lineType, userName);
		}
		/*if(lineType.equalsIgnoreCase("TOT"))
		{
			hqlQuery="select count(*) from CBKTotals a inner join a.cbkId b inner join  a.cbkLineId c  where b.cbkId =:pcbkHdrSeqiId  and c.cbkLineId=:pcbkLineSeqId";
			query = getSession().createQuery(hqlQuery);
			query.setParameter("pcbkHdrSeqiId", cbkHdrSeqiId);
			query.setParameter("pcbkLineSeqId", cbkLineSeqId);

			Long cbkTotalCount = (Long)query.uniqueResult();
			if(cbkTotalCount!=null && cbkTotalCount.compareTo(Long.valueOf("0"))==0)
			{
				recordExist=Constants.No;
				deactivateCBKLines(cbkHdrSeqiId, cbkLineSeqId, lineType, userName);
			}else
			{
				recordExist=Constants.Yes;
			}

		}else if(recordExist.equalsIgnoreCase(Constants.No))
		{
			String hqlQuery="select count(*) from CBKDetails a inner join a.cbkId b inner join  a.cbkLineId c  where b.cbkId =:pcbkHdrSeqiId  and c.cbkLineId=:pcbkLineSeqId";
			Query query = getSession().createQuery(hqlQuery);
			query.setParameter("pcbkHdrSeqiId", cbkHdrSeqiId);
			query.setParameter("pcbkLineSeqId", cbkLineSeqId);

			Long cbkDetailsCount = (Long)query.uniqueResult();
			
			if(cbkDetailsCount!=null && cbkDetailsCount.compareTo(Long.valueOf("0"))==0)
			{
				recordExist=Constants.No;
				deactivateCBKLines(cbkHdrSeqiId, cbkLineSeqId, lineType, userName);
			}else
			{
				recordExist=Constants.Yes;
			}
		}else
		{
			deactivateCBKLines(cbkHdrSeqiId, cbkLineSeqId, lineType, userName);
		}*/
		
		return recordExist;
	}
	
	public void deactivateCBKLines(BigDecimal cbkHdrSeqiId,BigDecimal cbkLineSeqId,String lineType,String userName)
	{
		
		CBKLines cBKLines=(CBKLines) getSession().get(CBKLines.class, cbkLineSeqId);
		
		if(cBKLines!=null)
		{
			cBKLines.setIsActive(Constants.D);
			cBKLines.setModifiedBy(userName);
			cBKLines.setModifiedDate(new Date());
			
			getSession().saveOrUpdate(cBKLines);
		}
		
		
	}

	@Override
	public List<ViewAccountClass> getViewAccountClassList() {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewAccountClass.class,"viewAccountClass");		
		
		criteria.addOrder(Order.asc("viewAccountClass.aclsdes"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewAccountClass> lstViewAccountClass= (List<ViewAccountClass>) findAll(criteria);
		return lstViewAccountClass;
	}

	@Override
	public List<ViewAccountCategory> getViewAccountCategoryList() {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewAccountCategory.class,"viewAccountCategory");		
		
		criteria.addOrder(Order.asc("viewAccountCategory.acades"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewAccountCategory> lstViewAccountCategory= (List<ViewAccountCategory>) findAll(criteria);
		return lstViewAccountCategory;
	}

	@Override
	public void saveCbkDetails(List<CBKDetails> lstCBKDetails) {

		if(lstCBKDetails!=null && lstCBKDetails.size()>0)
		{
			for(CBKDetails cBKDetails:lstCBKDetails)
			{
				getSession().saveOrUpdate(cBKDetails);
			}
		}
	}

	@Override
	public List<CBKDetails> getCbkDetails(BigDecimal cbkHdrSeqId,BigDecimal cbkLineSeqId) {
		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CBKDetails.class, "cBKDetails");
		
		criteria.setFetchMode("cBKDetails.cbkId", FetchMode.JOIN);
		criteria.createAlias("cBKDetails.cbkId", "cbkId", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("cbkId.cbkId", cbkHdrSeqId));
		
		criteria.setFetchMode("cBKDetails.cbkLineId", FetchMode.JOIN);
		criteria.createAlias("cBKDetails.cbkLineId", "cbkLineId", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("cbkLineId.cbkLineId", cbkLineSeqId));	
		
		criteria.add(Restrictions.eq("cBKDetails.isActive", Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CBKDetails> lstCBKDetails =(List<CBKDetails>) findAll(criteria);
		
		return lstCBKDetails;
	}
		
	public boolean deleteCbkDetails(BigDecimal cbkDetailsId,String userName)
	{
		boolean deleteSts=false;
		CBKDetails cBKDetails=(CBKDetails) getSession().get(CBKDetails.class, cbkDetailsId);
		
		if(cBKDetails!=null)
		{
			cBKDetails.setIsActive(Constants.D);
			cBKDetails.setModifiedBy(userName);
			cBKDetails.setModifiedDate(new Date());
			
			getSession().saveOrUpdate(cBKDetails);
			deleteSts=true;
		}
		return deleteSts;
	}
	
	@Override
	public void saveCbkTotals(List<CBKTotals> lstCBKTotals) {

		if(lstCBKTotals!=null && lstCBKTotals.size()>0)
		{
			for(CBKTotals cBKTotals:lstCBKTotals)
			{
				getSession().saveOrUpdate(cBKTotals);
			}
		}
	}
	@Override
	public List<CBKTotals> getCBKTotals(BigDecimal cbkHdrSeqId,BigDecimal cbkLineSeqId)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CBKTotals.class, "cBKTotals");
		
		criteria.setFetchMode("cBKTotals.cbkId", FetchMode.JOIN);
		criteria.createAlias("cBKTotals.cbkId", "cbkId", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("cbkId.cbkId", cbkHdrSeqId));
		
		criteria.setFetchMode("cBKTotals.cbkLineId", FetchMode.JOIN);
		criteria.createAlias("cBKTotals.cbkLineId", "cbkLineId", JoinType.INNER_JOIN);
		
		//criteria.add(Restrictions.eq("cbkLineId.cbkLineId", cbkLineSeqId));
		criteria.add(Restrictions.eq("cBKTotals.totalLineSeqId", cbkLineSeqId));
		
		criteria.add(Restrictions.eq("cBKTotals.isActive", Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CBKTotals> lstCBKTotals =(List<CBKTotals>) findAll(criteria);
		return lstCBKTotals;
				
	}
	@Override
	public boolean deleteCbkTotals(BigDecimal cbkTotalSeqId,String userName)
	{
		boolean deleteSts=false;
		CBKTotals cBKTotals=(CBKTotals) getSession().get(CBKTotals.class, cbkTotalSeqId);
		
		if(cBKTotals!=null)
		{
			cBKTotals.setIsActive(Constants.D);
			cBKTotals.setModifiedBy(userName);
			cBKTotals.setModifiedDate(new Date());
			
			getSession().saveOrUpdate(cBKTotals);
			deleteSts=true;
		}
		return deleteSts;
	}
	
	@Override
	public String getCBKReportProcedure(String reportFrequency,
			Date weekFromDate, Date weekToDate, Date monthFromDate,
			Date monthToDate, Date quarFromDate, Date quarToDate,
			String loginUser) {
		String out = "";
		Connection connection = null;
		try {
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { call EX40E01(?,?,?,?) } ";
			cs = connection.prepareCall(call);
			cs.setString(1, reportFrequency);
			if (reportFrequency.equalsIgnoreCase("W")) {
				cs.setDate(2, new java.sql.Date(weekFromDate.getTime()));
				cs.setDate(3, new java.sql.Date(weekToDate.getTime()));
			} else if (reportFrequency.equalsIgnoreCase("M")) {
				cs.setDate(2, new java.sql.Date(monthFromDate.getTime()));
				cs.setDate(3, new java.sql.Date(monthToDate.getTime()));
			} else if (reportFrequency.equalsIgnoreCase("Q")) {
				cs.setDate(2, new java.sql.Date(quarFromDate.getTime()));
				cs.setDate(3, new java.sql.Date(quarToDate.getTime()));
			}
			cs.setString(4, loginUser);
			cs.executeUpdate();
			//out = cs.getString(1);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	
	@Override
	public List<ViewExUnmappedGLS> getViewExUnmappedGLSList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewExUnmappedGLS.class,"viewExUnmappedGLS");		
		
		criteria.addOrder(Order.asc("viewExUnmappedGLS.aclsDesc"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<ViewExUnmappedGLS>) findAll(criteria);
	}
	
	public void deleteConfirmation(BigDecimal cbkHdrSeqiId,BigDecimal cbkLineSeqId,String userName)
	{
		String hqlQuery="select count(*) from CBKTotals a inner join a.cbkId b inner join  a.cbkLineId c  where b.cbkId =:pcbkHdrSeqiId  and c.cbkLineId=:pcbkLineSeqId and a.isActive='Y' ";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("pcbkHdrSeqiId", cbkHdrSeqiId);
		query.setParameter("pcbkLineSeqId", cbkLineSeqId);

		Long cbkTotalCount = (Long)query.uniqueResult();
		if(cbkTotalCount!=null && cbkTotalCount.compareTo(Long.valueOf("0"))==0)
		{
			
			hqlQuery="select count(*) from CBKTotals a inner join a.cbkId b inner join  a.cbkLineId c  where b.cbkId =:pcbkHdrSeqiId  and a.totalLineSeqId=:pcbkLineSeqId and a.isActive ='Y' ";
			query = getSession().createQuery(hqlQuery);
			query.setParameter("pcbkHdrSeqiId", cbkHdrSeqiId);
			query.setParameter("pcbkLineSeqId", cbkLineSeqId);
			Long cbkTotalCount1 = (Long)query.uniqueResult();
			if(cbkTotalCount1!=null && cbkTotalCount1.compareTo(Long.valueOf("0"))==0)
			{
				
			}else
			{
				hqlQuery="select a.cbkTotalsId from CBKTotals a inner join a.cbkId b inner join  a.cbkLineId c  where b.cbkId =:pcbkHdrSeqiId  and a.totalLineSeqId=:pcbkLineSeqId and  a.isActive='Y'";
				query = getSession().createQuery(hqlQuery);
				query.setParameter("pcbkHdrSeqiId", cbkHdrSeqiId);
				query.setParameter("pcbkLineSeqId", cbkLineSeqId);

				List<BigDecimal> lstcbkTotalsId =query.list();
				
				if(lstcbkTotalsId!=null && lstcbkTotalsId.size()>0)
				{
					for(BigDecimal cbkTotalsId :lstcbkTotalsId)
					{
						deleteCbkTotals(cbkTotalsId, userName);
						deactivateCBKLines(cbkHdrSeqiId, cbkLineSeqId, "", userName);
					}
					
				}
			}
				
			
		}else
		{
			hqlQuery="select a.cbkTotalsId from CBKTotals a inner join a.cbkId b inner join  a.cbkLineId c  where b.cbkId =:pcbkHdrSeqiId  and c.cbkLineId=:pcbkLineSeqId and  a.isActive='Y' ";
			query = getSession().createQuery(hqlQuery);
			query.setParameter("pcbkHdrSeqiId", cbkHdrSeqiId);
			query.setParameter("pcbkLineSeqId", cbkLineSeqId);

			List<BigDecimal> lstcbkTotalsId =query.list();
			
			if(lstcbkTotalsId!=null && lstcbkTotalsId.size()>0)
			{
				for(BigDecimal cbkTotalsId :lstcbkTotalsId)
				{
					deleteCbkTotals(cbkTotalsId, userName);
					deactivateCBKLines(cbkHdrSeqiId, cbkLineSeqId, "", userName);
				}
				
			}
		}
		
		hqlQuery="select count(*) from CBKDetails a inner join a.cbkId b inner join  a.cbkLineId c  where b.cbkId =:pcbkHdrSeqiId  and c.cbkLineId=:pcbkLineSeqId and  a.isActive='Y'";
		query = getSession().createQuery(hqlQuery);
		query.setParameter("pcbkHdrSeqiId", cbkHdrSeqiId);
		query.setParameter("pcbkLineSeqId", cbkLineSeqId);

		Long cbkDetailsCount = (Long)query.uniqueResult();
		
		if(cbkDetailsCount!=null && cbkDetailsCount.compareTo(Long.valueOf("0"))==0)
		{
			
		}else
		{
			hqlQuery="select a.cbkDetailsId from CBKDetails a inner join a.cbkId b inner join  a.cbkLineId c  where b.cbkId =:pcbkHdrSeqiId  and c.cbkLineId=:pcbkLineSeqId and  a.isActive='Y' ";
			query = getSession().createQuery(hqlQuery);
			query.setParameter("pcbkHdrSeqiId", cbkHdrSeqiId);
			query.setParameter("pcbkLineSeqId", cbkLineSeqId);
			
			List<BigDecimal> lstcbkDetailsId =query.list();
			
			if(lstcbkDetailsId!=null && lstcbkDetailsId.size()>0)
			{
				for(BigDecimal cbkDetailsId :lstcbkDetailsId)
				{
					deleteCbkDetails(cbkDetailsId, userName);
					deactivateCBKLines(cbkHdrSeqiId, cbkLineSeqId, "", userName);
				}
				
				
			}
		}
		
	}
	@Override
	public List<CBKHeader> getcbkHdrDetails(String reportNo, BigDecimal applicationCountryId)
	{
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CBKHeader.class, "cBKHeader");
		criteria.add(Restrictions.eq("cBKHeader.reportNo", reportNo));
		criteria.add(Restrictions.eq("cBKHeader.applicationCountryId", applicationCountryId));
		criteria.add(Restrictions.eq("cBKHeader.isactive", Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CBKHeader> lstCBKHeader =(List<CBKHeader>) findAll(criteria);
		
		
		return lstCBKHeader;
	}
	
}

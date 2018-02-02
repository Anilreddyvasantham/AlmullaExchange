package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.ViewPIPSType;
import com.amg.exchange.treasury.dao.IPipsMasterDao;
import com.amg.exchange.treasury.deal.supplier.model.TreasuryCustomerSupplier;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.PipsMaster;
import com.amg.exchange.util.Constants;

@Repository
public class PipsMasterDaoImpl<T> extends CommonDaoImpl<T> implements IPipsMasterDao{

	@Override
	public void saveRecord(PipsMaster pipsMasterObj) {
		getSession().saveOrUpdate(pipsMasterObj );;
		
	}
	
	public List<PipsMaster> pipsListFrmDB(BigDecimal countryId,BigDecimal currencyId,BigDecimal countryBranchId,BigDecimal serviceId,BigDecimal bankId){
        DetachedCriteria dCriteria =DetachedCriteria.forClass(PipsMaster.class,"pipsMaster");
		
		dCriteria.setFetchMode("pipsMaster.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("pipsMaster.bankMaster", "bankMaster",  JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("pipsMaster.countryMaster", FetchMode.JOIN);
		dCriteria.createAlias("pipsMaster.countryMaster", "countryMaster",  JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("pipsMaster.countryBranch", FetchMode.JOIN);
		dCriteria.createAlias("pipsMaster.countryBranch", "countryBranch",  JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("pipsMaster.currencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("pipsMaster.currencyMaster", "currencyMaster",  JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("pipsMaster.serviceMaster", FetchMode.JOIN);
		dCriteria.createAlias("pipsMaster.serviceMaster", "serviceMaster",  JoinType.INNER_JOIN);
		
		if(countryId!=null)
		{
			dCriteria.add( Restrictions.eq("countryMaster.countryId", countryId));
		}
		if(currencyId!=null)
		{
			dCriteria.add( Restrictions.eq("currencyMaster.currencyId" , currencyId));
		}
        
        if(countryBranchId!=null)
        {
        	dCriteria.add( Restrictions.eq("countryBranch.countryBranchId" , countryBranchId));
        }
        if(serviceId!=null)
        {
        	dCriteria.add( Restrictions.eq("serviceMaster.serviceId" , serviceId));
        }
        if(bankId!=null)
        {
        	dCriteria.add( Restrictions.eq("bankMaster.bankId" , bankId));
        }
        
		
        return (List<PipsMaster>)findAll(dCriteria);
	}
	
	
		
	public void update(PipsMaster pipsMasterObj){
		getSession().saveOrUpdate(pipsMasterObj);
	}
	
	@SuppressWarnings("unchecked")
	public List<PipsMaster>  getAllPipsList(){
		 DetachedCriteria dCriteria =DetachedCriteria.forClass(PipsMaster.class,"pipsMaster");
		 
		 dCriteria.add( Restrictions.eq("pipsMaster.isActive" ,Constants.Yes));
		return (List<PipsMaster>)findAll(dCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<PipsMaster> getAllPipsApprove() {
		
		DetachedCriteria dCriteria =DetachedCriteria.forClass(PipsMaster.class,"pipsMaster");
		 
		 dCriteria.add( Restrictions.eq("pipsMaster.isActive" ,Constants.U));
		 dCriteria.add(Restrictions.isNull("pipsMaster.approvedBy"));
		 dCriteria.add(Restrictions.isNull("pipsMaster.approvedDate"));
			return (List<PipsMaster>)findAll(dCriteria);
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BeneCountryService> getCurrencyMaster(BigDecimal countryId) {

		DetachedCriteria dCriteria =DetachedCriteria.forClass(BeneCountryService.class,"beneCountryService");
			
		dCriteria.setFetchMode("beneCountryService.beneCountryId", FetchMode.JOIN);
		dCriteria.createAlias("beneCountryService.beneCountryId", "beneCountryId",  JoinType.INNER_JOIN);
		dCriteria.add( Restrictions.eq("beneCountryId.countryId", countryId));
		
		
		dCriteria.setFetchMode("beneCountryService.currencyId", FetchMode.JOIN);
		dCriteria.createAlias("beneCountryService.currencyId", "currencyId",  JoinType.INNER_JOIN);
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		//dCriteria.setProjection(Projections.distinct(Projections.distinct(Projections.projectionList())));
		
		List<BeneCountryService> lstBeneCountryService=(List<BeneCountryService>)findAll(dCriteria);
		
		//List<BeneCountryService> lstReturnBeneCountryService = new ArrayList<BeneCountryService>();
		
		CopyOnWriteArrayList<BeneCountryService> lstReturnBeneCountryService=new CopyOnWriteArrayList<BeneCountryService>();
		boolean recordExist=false;
		for(BeneCountryService beneCountryService :lstBeneCountryService)
		{
			if(lstReturnBeneCountryService.size()>0)
			{
				for(BeneCountryService rtnbeneCountryService : lstReturnBeneCountryService)
				{
					if(beneCountryService.getCurrencyId().getCurrencyId().compareTo(rtnbeneCountryService.getCurrencyId().getCurrencyId())==0)
					{
						recordExist=false;
						break;
					}else
					{
						recordExist=true;
					}
					
				}
				if(recordExist)
				{
					lstReturnBeneCountryService.add(beneCountryService);
				}
					
			}else
			{
				lstReturnBeneCountryService.add(beneCountryService);
			}
		
		}
			
		
		return lstReturnBeneCountryService;
	}

	@Override
	public List<PipsMaster> populatePipsListFrmDB(BigDecimal countryId,BigDecimal currencyId, BigDecimal bankId) {
		
		DetachedCriteria dCriteria =DetachedCriteria.forClass(PipsMaster.class,"pipsMaster");

		

		dCriteria.setFetchMode("pipsMaster.countryMaster", FetchMode.JOIN);
		dCriteria.createAlias("pipsMaster.countryMaster", "countryMaster",  JoinType.INNER_JOIN);

		/*dCriteria.setFetchMode("pipsMaster.countryBranch", FetchMode.JOIN);
		dCriteria.createAlias("pipsMaster.countryBranch", "countryBranch",  JoinType.INNER_JOIN);*/

		dCriteria.setFetchMode("pipsMaster.currencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("pipsMaster.currencyMaster", "currencyMaster",  JoinType.INNER_JOIN);

		/*dCriteria.setFetchMode("pipsMaster.serviceMaster", FetchMode.JOIN);
		dCriteria.createAlias("pipsMaster.serviceMaster", "serviceMaster",  JoinType.INNER_JOIN);*/

		if(countryId!=null)
		{
			dCriteria.add( Restrictions.eq("countryMaster.countryId", countryId));
		}
		if(currencyId!=null)
		{
			dCriteria.add( Restrictions.eq("currencyMaster.currencyId" , currencyId));
		}
		dCriteria.add( Restrictions.eq("pipsMaster.isActive" ,Constants.Yes));
		/*if(bankId!=null)
		{
			dCriteria.setFetchMode("pipsMaster.bankMaster", FetchMode.JOIN);
			dCriteria.createAlias("pipsMaster.bankMaster", "bankMaster",  JoinType.INNER_JOIN);
			dCriteria.add( Restrictions.eq("bankMaster.bankId" , bankId));
		}*/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<PipsMaster>)findAll(dCriteria);
	}
	
	@Override
	public List<PipsMaster> pipsListForEnquiry(BigDecimal countryId,BigDecimal currencyId,BigDecimal countryBranchId,BigDecimal serviceId,BigDecimal bankId){
        DetachedCriteria dCriteria =DetachedCriteria.forClass(PipsMaster.class,"pipsMaster");
		
		dCriteria.setFetchMode("pipsMaster.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("pipsMaster.bankMaster", "bankMaster",  JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("pipsMaster.countryMaster", FetchMode.JOIN);
		dCriteria.createAlias("pipsMaster.countryMaster", "countryMaster",  JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("pipsMaster.countryBranch", FetchMode.JOIN);
		dCriteria.createAlias("pipsMaster.countryBranch", "countryBranch",  JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("pipsMaster.currencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("pipsMaster.currencyMaster", "currencyMaster",  JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("pipsMaster.serviceMaster", FetchMode.JOIN);
		dCriteria.createAlias("pipsMaster.serviceMaster", "serviceMaster",  JoinType.INNER_JOIN);
		
		if(countryId!=null)
		{System.out.println("country not null");
			dCriteria.add( Restrictions.eq("countryMaster.countryId", countryId));
		}
		if(currencyId!=null)
		{System.out.println("currency not null");
			dCriteria.add( Restrictions.eq("currencyMaster.currencyId" , currencyId));
		}
        
        if(countryBranchId!=null)
        {System.out.println("country branch not null");
        	dCriteria.add( Restrictions.eq("countryBranch.countryBranchId" , countryBranchId));
        }
        if(serviceId!=null)
        {System.out.println("service not null");
        	dCriteria.add( Restrictions.eq("serviceMaster.serviceId" , serviceId));
        }
        if(bankId!=null)
        {System.out.println("bank not null");
        	dCriteria.add( Restrictions.eq("bankMaster.bankId" , bankId));
        }
        
        dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
        return (List<PipsMaster>)findAll(dCriteria);
	}

	@Override
	public String approveRecord(BigDecimal pipsMasterPk, String userName) {
		String approveMsg;
		PipsMaster pipsMaster = (PipsMaster) getSession().get(PipsMaster.class, pipsMasterPk);
		String approvedUser=pipsMaster.getApprovedBy();
		if(approvedUser==null)
		{
			pipsMaster.setIsActive(Constants.Yes);
			pipsMaster.setApprovedBy(userName);
			pipsMaster.setApprovedDate(new Date());
			getSession().update(pipsMaster);
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;
	}

	@Override
	public List<ViewPIPSType> toFetchAllPipsIndParamView() {
		DetachedCriteria dCriteria =DetachedCriteria.forClass(ViewPIPSType.class,"viewPIPSType");
		//dCriteria.add( Restrictions.eq("pipsMaster.isActive" ,Constants.Yes));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewPIPSType> lstViewPIPSTypes=(List<ViewPIPSType>)findAll(dCriteria);
		return lstViewPIPSTypes;
	}

	@Override
	public String toFetchFullNameTypeCode(String pipsTypeCode) {
		String pipsFullName=null;
		DetachedCriteria dCriteria =DetachedCriteria.forClass(ViewPIPSType.class,"viewPIPSType");
		dCriteria.add( Restrictions.eq("viewPIPSType.pipsTypeCode" ,pipsTypeCode));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewPIPSType> lstViewPIPSTypes=(List<ViewPIPSType>)findAll(dCriteria);
		if (lstViewPIPSTypes.size()>0) {
			pipsFullName=lstViewPIPSTypes.get(0).getFullDesc();
		}
		return pipsFullName;
	}
	

}

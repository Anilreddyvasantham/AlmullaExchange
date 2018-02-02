package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.treasury.dao.ITotalUsdRequirementDao;
import com.amg.exchange.treasury.model.FundEstimation;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.viewModel.TotalUsdRequirementView;

@Repository
public class TotalUsdRequirementDaoImpl<T> extends CommonDaoImpl<T> implements ITotalUsdRequirementDao<T> {

	@SuppressWarnings("unchecked")
	@Override
	public List<FundEstimationDetails> getSaleProjectionValues(BigDecimal countryId,String roleId,Date projectionDate) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(FundEstimationDetails.class, "fundEstimationDetails");
	dCriteria.setFetchMode("fundEstimationDetails.exFundEstimtaionDetailsForApplicationCountry", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.exFundEstimtaionDetailsForApplicationCountry", "exFundEstimtaionDetailsForApplicationCountry", JoinType.INNER_JOIN);
		
	if(!roleId.equalsIgnoreCase("1")){
			
			System.out.println("======DAO Role DAOIMPL====");
			
			dCriteria.add(Restrictions.eq("exFundEstimtaionDetailsForApplicationCountry.countryId", countryId));
		}
		
		
		dCriteria.setFetchMode("fundEstimationDetails.fundEstimtaionId", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.fundEstimtaionId", "fundEstimtaionId", JoinType.INNER_JOIN);
		
        List<FundEstimationDetails> data = (List<FundEstimationDetails>)findAll(dCriteria);
		
		List<FundEstimationDetails> finalData = new ArrayList<FundEstimationDetails>();
		
		for (FundEstimationDetails fundEstimationDetails : data) {
			if(new SimpleDateFormat("dd/MMM/yy").format(fundEstimationDetails.getProjectionDate()).equalsIgnoreCase(new SimpleDateFormat("dd/MMM/yy").format(new Date()))) {
				
				//fundEstimationDetails.get(0).getExFundEstimtaionDetailsForApplicationCountry().getCountryId()
				finalData.add(fundEstimationDetails);
			}
		}
		
		//dCriteria.add(Restrictions.eq("fundEstimationDetails.projectionDate",  projectionDate));
    
	 	return finalData;

		
		//return (List<FundEstimationDetails>)findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FundEstimation> getTotalUsd(BigDecimal countryId,String roleId,Date projectionDate){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(FundEstimation.class, "fundEstimation");
		dCriteria.setFetchMode("fundEstimation.exFundEstimationForApplicationCountry", FetchMode.JOIN);
			dCriteria.createAlias("fundEstimation.exFundEstimationForApplicationCountry", "exFundEstimationForApplicationCountry", JoinType.INNER_JOIN);
			
			System.out.println("************************"+countryId);
			if(!roleId.equals("1")){
				dCriteria.add(Restrictions.eq("exFundEstimationForApplicationCountry.countryId", countryId));
			}
			
			
			  List<FundEstimation> data = (List<FundEstimation>)findAll(dCriteria);
				
				List<FundEstimation> finalData = new ArrayList<FundEstimation>();
				
				for (FundEstimation fundEstimation : data) {
					if(new SimpleDateFormat("dd/MMM/yy").format(fundEstimation.getProjectionDate()).equalsIgnoreCase(new SimpleDateFormat("dd/MMM/yy").format(new Date()))) {
						
						finalData.add(fundEstimation);
					}
				}
				
				//dCriteria.add(Restrictions.eq("fundEstimationDetails.projectionDate",  projectionDate));
				
				
		    
			 	return finalData;
			//return (List<FundEstimation>)findAll(dCriteria);
				
	}
	
	
	/*
	 * This Method is added for Treasury – Sales Projections CR Point -9 begin
	 */
	
	
		@SuppressWarnings("unchecked")
		@Override
		public List<TotalUsdRequirementView> getSaleProjectionValuesChief() {
			
			String sql ="select * from V_TOT_INTRREQ";
			SQLQuery query = getSession().createSQLQuery(sql);
	        query.addEntity(TotalUsdRequirementView.class);
	        List <TotalUsdRequirementView> totalUsdRequirementView= query.list();
	        	         
	         return totalUsdRequirementView;
		}

		
		/*
		 * Above Method is added for Treasury – Sales Projections CR Point -9 End
		 */
		
		
				}

	

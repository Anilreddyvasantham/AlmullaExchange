/**
 * 
 */
package com.amg.exchange.common.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.dao.IDistrictMasterDao;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.util.Constants;

/**
 * @author Subramaniam
 *
 */

@SuppressWarnings("serial")
@Repository
public class DistrictMasterDaoImpl<T> extends CommonDaoImpl<T> implements IDistrictMasterDao<T>,Serializable {

	/**
	 * 
	 */
	public DistrictMasterDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void saveRecord(DistrictMaster districtMaster) {
		 
		
		getSession().saveOrUpdate(districtMaster);
		 
	}
	@Override
	public void save(DistrictMasterDesc districtMasterDesc) {
		 
		getSession().saveOrUpdate(districtMasterDesc);
		
	}
	
	@Override
	public String getStateName(BigDecimal languageId,BigDecimal stateId) {
          DetachedCriteria dCriteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		
		dCriteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		dCriteria.createAlias("stateMasterDesc.fsStateMaster", "fsStateMaster",  JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsStateMaster.stateId", stateId));
		
		dCriteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("stateMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		
		List<StateMasterDesc> data = dCriteria.getExecutableCriteria(getSession()).list();
		if (data.isEmpty())
			return null;
		
		//return ((StateMasterDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getStateName();
		return data.get(0).getStateName();
	}
	@Override
	public List<StateMaster> getCountryName(BigDecimal languageId, BigDecimal stateId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StateMaster.class, "stateMaster");

		dCriteria.add(Restrictions.eq("stateMaster.stateId", stateId));
		
	
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<StateMaster> data = (List<StateMaster>) findAll(dCriteria);
		if (data.isEmpty())
			return null;

		return data;
	}
	
	@Override	
	public List<DistrictMasterDesc> getDistrictList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(DistrictMasterDesc.class, "districtMasterDesc");
		criteria.setFetchMode("districtMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("districtMasterDesc.fsDistrictMaster", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);
		
		//criteria.setFetchMode("districtMasterDesc.fsDistrictMaster", FetchMode.JOIN);
		/*criteria.createAlias("fsDistrictMaster.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		
		criteria.createAlias("fsStateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);*/
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		List<DistrictMasterDesc> data = (List<DistrictMasterDesc>) findAll(criteria);
		
		System.out.println("data.size() ============ > "+data.size());
		
		if (data.isEmpty())
			return null;
		
		return data;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void approveRecord(BigDecimal districtMasterId, String userName){
		System.out.println("Approve Record ==== > "+districtMasterId);
		DistrictMaster districtMaster = (DistrictMaster) getSession().get(DistrictMaster.class,districtMasterId);
		
		districtMaster.setDistrictActive("Y");
		districtMaster.setApprovedBy(userName);
		districtMaster.setApprovedDate(new Date());
		getSession().update(districtMaster);
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public void modifyRecord(BigDecimal districtMasterId, String userName,String remarks){
		System.out.println("Approve Record ==== > "+districtMasterId);
		DistrictMaster districtMaster = (DistrictMaster) getSession().get(DistrictMaster.class,districtMasterId);
		
		districtMaster.setDistrictActive("D");
		districtMaster.setUpdatedBy(userName);
		districtMaster.setLastUpdated(new Date());
		districtMaster.setApprovedBy(null);
		districtMaster.setApprovedDate(null);
		districtMaster.setRemarks(remarks);
		getSession().update(districtMaster);
		
	}
	@Override
	public void deleteRecordDesc(DistrictMasterDesc districtMasterDesc) {
		getSession().delete(districtMasterDesc);

	}

	@Override
	public void deleteRecord(DistrictMaster districtMaster) {
		getSession().delete(districtMaster);

	}
	
	@Override	
	public List<DistrictMasterDesc> getDistrictList(BigDecimal districtId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DistrictMasterDesc.class, "districtMasterDesc");
		criteria.setFetchMode("districtMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.setFetchMode("districtMasterDesc.fsDistrictMaster", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsDistrictMaster.districtId", districtId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		return (List<DistrictMasterDesc>) findAll(criteria);
	}
	@Override	
	public List<DistrictMasterDesc> getDistrictListBasedOnDistrictCode(String districtCode,String stateCode,String districtName) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(DistrictMasterDesc.class, "districtMasterDesc");
		
		criteria.setFetchMode("districtMasterDesc.fsDistrictMaster", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);	
		
		criteria.add(Restrictions.eq("fsDistrictMaster.stateCode", stateCode));
		
		
		Criterion district = Restrictions.eq("fsDistrictMaster.districtCode", districtCode);
	//	Criterion state = Restrictions.eq("fsDistrictMaster.stateCode", stateCode);
		
		Criterion name = Restrictions.eq("districtMasterDesc.district", districtName);

		LogicalExpression orExp = Restrictions.or(district, name);
		criteria.add( orExp );
		/*// To get records matching with OR condistions
		LogicalExpression orExp = Restrictions.or(salary, name);
		cr.add( orExp );
		
		criteria.add(Restrictions.eq("fsDistrictMaster.districtCode", districtCode));
		
		
		criteria.add(Restrictions.eq("districtMasterDesc.district", districtName));*/
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		return (List<DistrictMasterDesc>) findAll(criteria);
	}
	
	@Override	
	public List<DistrictMasterDesc> checkLocalDescription(String localDistrictName,BigDecimal districtId ){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(DistrictMasterDesc.class, "districtMasterDesc");
		
		criteria.setFetchMode("districtMasterDesc.fsDistrictMaster", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);	
		
		criteria.add(Restrictions.eq("fsDistrictMaster.districtId", districtId));
		
		criteria.add(Restrictions.eq("districtMasterDesc.district", localDistrictName));
		
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
List<DistrictMasterDesc> objList = (List<DistrictMasterDesc>) findAll(criteria);
		
		if(objList.isEmpty())
			return null;
		
		return objList;
	}
	
	@Override	
	public List<DistrictMasterDesc> checkEnglishDescription(String englishDistrictName,BigDecimal districtId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(DistrictMasterDesc.class, "districtMasterDesc");
		
		criteria.setFetchMode("districtMasterDesc.fsDistrictMaster", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);	
		
		criteria.add(Restrictions.eq("fsDistrictMaster.districtId", districtId));
		
		criteria.add(Restrictions.eq("districtMasterDesc.district", englishDistrictName));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		
List<DistrictMasterDesc> objList = (List<DistrictMasterDesc>) findAll(criteria);
		
		if(objList.isEmpty())
			return null;
		
		return objList;
	}
	
	@Override
	public List<DistrictMaster> getDistrictBasedOnDistrictCode(String districtCode, BigDecimal stateId) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(DistrictMaster.class, "districtMaster");
		
		criteria.setFetchMode("districtMaster.fsStateMaster", FetchMode.JOIN);
		criteria.createAlias("districtMaster.fsStateMaster", "fsStateMaster",  JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsStateMaster.stateId", stateId));
		
		criteria.add(Restrictions.eq("districtMaster.districtCode", districtCode));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		List<DistrictMaster> objList = (List<DistrictMaster>) findAll(criteria);
		
		if(objList.isEmpty())
			return null;
		
		return objList;
	}
	
	
	@Override
	public List<StateMasterDesc> getStateName(String stateCode) {
          DetachedCriteria dCriteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		
		dCriteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		dCriteria.createAlias("stateMasterDesc.fsStateMaster", "fsStateMaster",  JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsStateMaster.stateCode", stateCode));
		
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		List<StateMasterDesc> data = (List<StateMasterDesc>) findAll(dCriteria);
		if (data.isEmpty())
			return null;
		
		return data;
	}

	
	@Override	
	public Boolean isDistrictListBasedOnDistrictCode(String districtCode) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(DistrictMasterDesc.class, "districtMasterDesc");
		
		criteria.setFetchMode("districtMasterDesc.fsDistrictMaster", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);	
		
		criteria.add(Restrictions.eq("fsDistrictMaster.districtCode", districtCode));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		List<DistrictMasterDesc> data = (List<DistrictMasterDesc>) findAll(criteria);
		
		if (data.isEmpty())
			return false;
		
		return true;
	}
	
	@Override
	public List<StateMasterDesc> getStateList(String stateId) {
          DetachedCriteria dCriteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		
		dCriteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		dCriteria.createAlias("stateMasterDesc.fsStateMaster", "fsStateMaster",  JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsStateMaster.stateCode", stateId));
		
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		List<StateMasterDesc> data = (List<StateMasterDesc>) findAll(dCriteria);
		if (data.isEmpty())
			return null;
		
		return data;
	}
	
	@Override
	public List<StateMaster> getCountryFromState(BigDecimal stateId) {
          DetachedCriteria dCriteria = DetachedCriteria.forClass(StateMaster.class, "stateMaster");
		
          dCriteria.setFetchMode("stateMaster.fsCountryMaster", FetchMode.JOIN);
  		dCriteria.createAlias("stateMaster.fsCountryMaster", "fsCountryMaster",  JoinType.INNER_JOIN);
  		
		dCriteria.add(Restrictions.eq("stateMaster.stateId", stateId));
		
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		List<StateMaster> data = (List<StateMaster>) findAll(dCriteria);
		if (data.isEmpty())
			return null;
		
		return data;
	}
	
	@Override
	public void delete(BigDecimal districtId, BigDecimal districtDescId, BigDecimal districtArabicDescId) {
		DistrictMaster districtMaster=(DistrictMaster) getSession().get(DistrictMaster.class, districtId);
		DistrictMasterDesc districtEngDescId=(DistrictMasterDesc) getSession().get(DistrictMasterDesc.class, districtDescId);		
			DistrictMasterDesc districtLocalDescId=(DistrictMasterDesc) getSession().get(DistrictMasterDesc.class, districtArabicDescId);
			getSession().delete(districtMaster);
			
		getSession().delete(districtLocalDescId);
		
		getSession().delete(districtEngDescId);
		
		
		
	}
	
	@Override
	public List<StateMaster> getStateMasterId(BigDecimal countryId,BigDecimal stateId){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StateMaster.class, "stateMaster");
		
        dCriteria.setFetchMode("stateMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("stateMaster.fsCountryMaster", "fsCountryMaster",  JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		
		dCriteria.add(Restrictions.eq("stateMaster.stateId", stateId));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.getExecutableCriteria(getSession()).setCacheable(true);

		List<StateMaster> data = (List<StateMaster>) findAll(dCriteria);
		if (data.isEmpty())
			return null;
		
		return data;
	}
	
	@Override
	public List<DistrictMaster> viewMasterRecords(BigDecimal stateId) {

		

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DistrictMaster.class, "districtMaster");
		detachedCriteria.setFetchMode("districtMaster.fsStateMaster", FetchMode.SELECT);
		detachedCriteria.createAlias("districtMaster.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);

		
				detachedCriteria.add(Restrictions.eq("fsStateMaster.stateId", stateId));

			

		detachedCriteria.addOrder(Order.asc("districtMaster.districtCode"));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<DistrictMaster> data = (List<DistrictMaster>) findAll(detachedCriteria);
		if (data.isEmpty())
			return null;
		
		return data;
	}
	
	@Override
	public List<DistrictMaster> approveViewMasterRecords(BigDecimal stateId,String actvice) {
		
		

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DistrictMaster.class, "districtMaster");
		detachedCriteria.setFetchMode("districtMaster.fsStateMaster", FetchMode.SELECT);
		detachedCriteria.createAlias("districtMaster.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);

		
				detachedCriteria.add(Restrictions.eq("fsStateMaster.stateId", stateId));

				detachedCriteria.add(Restrictions.eq("districtMaster.districtActive", actvice));

				

		detachedCriteria.addOrder(Order.asc("districtMaster.districtCode"));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<DistrictMaster> data = (List<DistrictMaster>) findAll(detachedCriteria);
		if (data.isEmpty())
			return null;
		
		return data;
	}
	
	@Override
	public List<DistrictMasterDesc> viewById(BigDecimal districtId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DistrictMasterDesc.class, "districtMasterDesc");
		criteria.setFetchMode("districtMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.setFetchMode("districtMasterDesc.fsDistrictMaster", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsDistrictMaster.districtId", districtId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		return (List<DistrictMasterDesc>) findAll(criteria);
	}
	
	@Override
	public void activateRecord(BigDecimal districtMaster, String userName) {
		DistrictMaster district = (DistrictMaster) getSession().get(DistrictMaster.class, districtMaster);
		district.setDistrictActive(Constants.U);
		district.setUpdatedBy(userName);
		district.setLastUpdated(new Date());
		district.setApprovedBy(null);
		district.setApprovedDate(null);
		district.setRemarks(null);
		getSession().update(district);

	}
	

	@Override
	public List<DistrictMaster> viewMasterRecords() {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DistrictMaster.class, "districtMaster");
		detachedCriteria.setFetchMode("districtMaster.fsStateMaster", FetchMode.SELECT);
		detachedCriteria.createAlias("districtMaster.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);

		detachedCriteria.addOrder(Order.asc("districtMaster.districtCode"));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<DistrictMaster>) findAll(detachedCriteria);
	}
	@Override
	public List<StateMaster> getStateListBasedOnCountryId(BigDecimal countryId, String stateCode){
	DetachedCriteria dCriteria = DetachedCriteria.forClass(StateMaster.class, "stateMaster");
		
        dCriteria.setFetchMode("stateMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("stateMaster.fsCountryMaster", "fsCountryMaster",  JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		
		dCriteria.add(Restrictions.eq("stateMaster.stateCode", stateCode));	
		
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.getExecutableCriteria(getSession()).setCacheable(true);

		List<StateMaster> data = (List<StateMaster>) findAll(dCriteria);
		if (data.isEmpty())
			return null;
		
		return data;
	}
	
	@Override
	public List<StateMasterDesc> getStateNameBasedOnId(BigDecimal stateId) {
          DetachedCriteria dCriteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		
		dCriteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		dCriteria.createAlias("stateMasterDesc.fsStateMaster", "fsStateMaster",  JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsStateMaster.stateId", stateId));
		
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		List<StateMasterDesc> data = (List<StateMasterDesc>) findAll(dCriteria);
		if (data.isEmpty())
			return null;
		
		return data;
	}
	
	@Override
	public String approveRecord(List<BigDecimal> districtId,String userName) {
		
		
		
		String list = null;
		int i=0;
		for (BigDecimal districtMasterId : districtId) {
			DistrictMaster districtMaster = (DistrictMaster) getSession().get(DistrictMaster.class,districtMasterId);
			String approvedUser=districtMaster.getApprovedBy();
			if(approvedUser==null)
			{
				districtMaster.setApprovedBy(userName);
				districtMaster.setApprovedDate(new Date());
				districtMaster.setDistrictActive(Constants.Yes);
				getSession().update(districtMaster);
				i++;
			}
		}
		if(i != 0){
			list="Success";
		}else{
			list="Fail";
		}
		return list;
	}

	@Override
	public String getDistrictName(BigDecimal languageId, BigDecimal districtId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DistrictMasterDesc.class, "districtMasterDesc");

		dCriteria.setFetchMode("districtMasterDesc.fsDistrictMaster",FetchMode.JOIN);
		dCriteria.createAlias("districtMasterDesc.fsDistrictMaster","fsDistrictMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsDistrictMaster.districtId", districtId));

		dCriteria.setFetchMode("districtMasterDesc.fsLanguageType",FetchMode.JOIN);
		dCriteria.createAlias("districtMasterDesc.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<DistrictMasterDesc> lstDistrictMasterDesc = (List<DistrictMasterDesc>) findAll(dCriteria);
		String districtName = null;
		if (lstDistrictMasterDesc.size() > 0) {
			districtName = lstDistrictMasterDesc.get(0).getDistrict();
		}
		return districtName;
	}

	@Override
	public String getCityName(BigDecimal languageId, BigDecimal cityId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CityMasterDesc.class, "cityMasterDesc");

		dCriteria.setFetchMode("cityMasterDesc.fsCityMaster", FetchMode.JOIN);
		dCriteria.createAlias("cityMasterDesc.fsCityMaster", "fsCityMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCityMaster.cityId", cityId));
		
		dCriteria.setFetchMode("cityMasterDesc.fsLanguageType",FetchMode.JOIN);
		dCriteria.createAlias("cityMasterDesc.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CityMasterDesc> lstCityMasterDesc = (List<CityMasterDesc>) findAll(dCriteria);
		
		String cityName = null;
		if (lstCityMasterDesc.size() > 0) {
			cityName = lstCityMasterDesc.get(0).getCityName();
		}

		return cityName;
	}
	
	
}

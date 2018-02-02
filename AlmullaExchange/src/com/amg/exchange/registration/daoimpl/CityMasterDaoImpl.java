package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.registration.dao.ICityMasterDao;
import com.amg.exchange.util.Constants;

@SuppressWarnings({ "unchecked", "serial" })
@Repository
public class CityMasterDaoImpl<T> extends CommonDaoImpl<T> implements ICityMasterDao<T>, Serializable {
	  private static final Logger log = Logger.getLogger(CityMasterDaoImpl.class);
	  @Override
	  public List<CityMasterDesc> view() {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CityMasterDesc.class, "cityMasterDesc");

		    detachedCriteria.setFetchMode("cityMasterDesc.fsLanguageType", FetchMode.JOIN);
		    detachedCriteria.createAlias("cityMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);

		    detachedCriteria.setFetchMode("cityMasterDesc.fsCityMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("cityMasterDesc.fsCityMaster", "fsCityMaster", JoinType.INNER_JOIN);

		    detachedCriteria.setFetchMode("fsCityMaster.fsDistrictMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("fsCityMaster.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);

		    /*
		     * detachedCriteria.setFetchMode("fsCityMaster.fsStateMaster"
		     * , FetchMode.JOIN);
		     * detachedCriteria.createAlias("fsCityMaster.fsStateMaster"
		     * , "fsStateMaster", JoinType.INNER_JOIN);
		     * 
		     * detachedCriteria.setFetchMode("fsCityMaster.fsCountryMaster"
		     * , FetchMode.JOIN);
		     * detachedCriteria.createAlias("fsCityMaster.fsCountryMaster"
		     * , "fsCountryMaster", JoinType.INNER_JOIN);
		     */
		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		    return (List<CityMasterDesc>) findAll(detachedCriteria);
	  }

	  @Override
	  public List<CityMasterDesc> getIdentityStatus(BigDecimal countryId, BigDecimal businessComponentId) {
		    // TODO Auto-generated method stub
		    return null;
	  }

	  @Override
	  public List<CityMasterDesc> getUnApprovedList() {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CityMasterDesc.class, "cityMaster");
		    detachedCriteria.add(Restrictions.eq("cityMaster.isActive", "U"));
		    return (List<CityMasterDesc>) findAll(detachedCriteria);
	  }

	  @Override
	  public void approve(BigDecimal cityMasterPk, String userName, Date currentDate) {
		    CityMaster cityMaster = (CityMaster) getSession().get(CityMaster.class, cityMasterPk);
		    cityMaster.setIsActive("Y");
		    cityMaster.setApprovedBy(userName);
		    cityMaster.setApprovedDate(currentDate);
		    cityMaster.setRemarks(null);
		    getSession().update(cityMaster);

	  }

	  @Override
	  public void delete(BigDecimal cityMasterPk, BigDecimal cityDescEnPk, BigDecimal cityDescLocalPk) {
		    CityMaster cityMaster = (CityMaster) getSession().get(CityMaster.class, cityMasterPk);
		    CityMasterDesc cityMasterDescEng = (CityMasterDesc) getSession().get(CityMasterDesc.class, cityDescEnPk);
		    CityMasterDesc cityMasterDescLocal = (CityMasterDesc) getSession().get(CityMasterDesc.class, cityDescLocalPk);
		    getSession().delete(cityMaster);
		    getSession().delete(cityMasterDescEng);
		    getSession().delete(cityMasterDescLocal);

	  }

	  @Override
	  public void softDelete(BigDecimal cityMasterPk, String userName, Date currentDate, String remarks) {
		    CityMaster cityMaster = (CityMaster) getSession().get(CityMaster.class, cityMasterPk);
		    cityMaster.setIsActive("D");
		    cityMaster.setApprovedBy(null);
		    cityMaster.setApprovedDate(null);
		    cityMaster.setModifiedBy(userName);
		    cityMaster.setModifiedDate(currentDate);
		    cityMaster.setRemarks(remarks);
		    getSession().update(cityMaster);

	  }

	  @Override
	  public void activate(BigDecimal cityMasterPk, String userName, Date currentDate) {

		    CityMaster cityMaster = (CityMaster) getSession().get(CityMaster.class, cityMasterPk);
		    cityMaster.setIsActive(Constants.U);
		    cityMaster.setApprovedBy(null);
		    cityMaster.setApprovedDate(null);
		    cityMaster.setModifiedBy(userName);
		    cityMaster.setModifiedDate(currentDate);
		    cityMaster.setRemarks(null);
		    getSession().update(cityMaster);

	  }

	  @Override
	  public List<CityMaster> viewAllRecord(String countryCode, String stateCode, String districtCode) {

		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CityMaster.class, "cityMaster");

		    if (countryCode != null) {

			      detachedCriteria.add(Restrictions.eq("cityMaster.countryCode", countryCode));

			      /*
			       * detachedCriteria.setFetchMode(
			       * "cityMaster.fsCountryMaster", FetchMode.JOIN);
			       * detachedCriteria
			       * .createAlias("cityMaster.fsCountryMaster",
			       * "fsCountryMaster", JoinType.INNER_JOIN);
			       */

		    }

		    if (stateCode != null) {

			      detachedCriteria.add(Restrictions.eq("cityMaster.stateCode", stateCode));

			      /*
			       * detachedCriteria.setFetchMode(
			       * "cityMaster.fsStateMaster", FetchMode.JOIN);
			       * detachedCriteria
			       * .createAlias("cityMaster.fsStateMaster",
			       * "fsStateMaster", JoinType.INNER_JOIN);
			       */

		    }

 

			      detachedCriteria.add(Restrictions.eq("cityMaster.districtCode", districtCode));

			      /*
			       * detachedCriteria.setFetchMode(
			       * "cityMaster.fsDistrictMaster", FetchMode.JOIN);
			       * detachedCriteria
			       * .createAlias("cityMaster.fsDistrictMaster",
			       * "fsDistrictMaster", JoinType.INNER_JOIN);
			       */

	 

		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		    return (List<CityMaster>) findAll(detachedCriteria);
	  }

	  @Override
	  public List<CityMasterDesc> viewDescRecord(BigDecimal cityIdPk) {

		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CityMasterDesc.class, "cityMasterDesc");

		    detachedCriteria.setFetchMode("cityMasterDesc.fsLanguageType", FetchMode.JOIN);
		    detachedCriteria.createAlias("cityMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);

		    detachedCriteria.setFetchMode("cityMasterDesc.fsCityMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("cityMasterDesc.fsCityMaster", "fsCityMaster", JoinType.INNER_JOIN);

		    detachedCriteria.add(Restrictions.eq("fsCityMaster.cityId", cityIdPk));
		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		    return (List<CityMasterDesc>) findAll(detachedCriteria);
	  }

	  @Override
	  public List<CityMaster> viewUnApproveRecord() {
		    DetachedCriteria criteria = DetachedCriteria.forClass(CityMaster.class, "cityMaster");
		    criteria.add(Restrictions.eq("cityMaster.isActive", "U"));
		    criteria.add(Restrictions.isNull("cityMaster.approvedBy"));
		    criteria.add(Restrictions.isNull("cityMaster.approvedDate"));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    return (List<CityMaster>) findAll(criteria);
	  }

	  @Override
	  public List<CityMaster> viewMatchingRecord() {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CityMaster.class, "cityMaster");

		    detachedCriteria.setFetchMode("cityMaster.fsDistrictMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("cityMaster.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);

		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		    return (List<CityMaster>) findAll(detachedCriteria);
	  }

	  @Override
	  public List<CityMaster> viewRecordDetails(String countryCode, String stateCode, BigDecimal districtId) {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CityMaster.class, "cityMaster");

		    detachedCriteria.setFetchMode("cityMaster.fsDistrictMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("cityMaster.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);
		    detachedCriteria.add(Restrictions.eq("fsDistrictMaster.districtId", districtId));
		    detachedCriteria.add(Restrictions.eq("countryCode", countryCode));
		    detachedCriteria.add(Restrictions.eq("stateCode", stateCode));

		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		    return (List<CityMaster>) findAll(detachedCriteria);
	  }

	  @Override
	  public List<CountryMaster> viewCountryId(String countryCode) {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");

		    detachedCriteria.add(Restrictions.eq("countryCode", countryCode));

		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		    return (List<CountryMaster>) findAll(detachedCriteria);
	  }

	  @Override
	  public List<StateMaster> viewStateIdId(BigDecimal countryId, String stateCode) {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StateMaster.class, "stateMaster");

		    detachedCriteria.setFetchMode("stateMaster.fsCountryMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("stateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		    detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		    detachedCriteria.add(Restrictions.eq("stateCode", stateCode));

		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		    return (List<StateMaster>) findAll(detachedCriteria);
	  }

	  @Override
	  public List<StateMasterDesc> getStateList(BigDecimal languageId) {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		    // Join FS_LANGUAGE_TYPE table
		    detachedCriteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		    detachedCriteria.createAlias("stateMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		    // Add FS_LANGUAGE_TYPE Condition
		    detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		    // Join FS_STATE_MASTER table
		    detachedCriteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("stateMasterDesc.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		    // Join FS_COUNTRY_MASTER table
		    detachedCriteria.setFetchMode("fsStateMaster.fsCountryMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("fsStateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    // Add FS_COUNTRY_MASTER Filter Condition
		    detachedCriteria.addOrder(Order.asc("stateMasterDesc.stateName"));
		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    detachedCriteria.getExecutableCriteria(getSession()).setCacheable(true);
		    return (List<StateMasterDesc>) findAll(detachedCriteria);
	  }

	  @Override
	  public List<CountryMasterDesc> getCountryList(BigDecimal languageId) {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		    // Join Language Type table
		    detachedCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		    detachedCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		    // Add Language Condition
		    detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		    // Join Country Master Table
		    detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryActive", "Y"));
		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    detachedCriteria.getExecutableCriteria(getSession()).setCacheable(true);
		    return (List<CountryMasterDesc>) findAll(detachedCriteria);
	  }

	  @Override
	  public String getCountryName(BigDecimal languageId, String countryCode) {
		    // TODO Auto-generated method stub
		    return null;
	  }

	  @Override
	  public String getStateName(BigDecimal languageId, String stateCode) {
		    // TODO Auto-generated method stub
		    return null;
	  }

	  @Override
	  public String getDistrictName(BigDecimal languageId, String districtCode) {
		    // TODO Auto-generated method stub
		    return null;
	  }

	  @Override
	  public List<String> getCityListCode(String countryCode, String stateCode, String districtCode, String query) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(CityMaster.class, "cityMaster");

		    criteria.add(Restrictions.eq("cityMaster.countryCode", countryCode));

		    criteria.add(Restrictions.eq("cityMaster.stateCode", stateCode));

		    criteria.add(Restrictions.eq("cityMaster.districtCode", districtCode));

		    criteria.add(Restrictions.like("cityMaster.cityCode", query, MatchMode.START).ignoreCase());
		    criteria.setProjection(Projections.property("cityMaster.cityCode"));

		    criteria.addOrder(Order.asc("cityMaster.cityCode"));

		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		    return (List<String>) findAll(criteria);
	  }

	  @Override
	  public List<CityMasterDesc> fetchDBBasedonCityCode(String countryCode, String stateCode, String districtCode, String cityCode) {

		    List<CityMasterDesc> lstData = new ArrayList<CityMasterDesc>();

		    DetachedCriteria criteria = DetachedCriteria.forClass(CityMaster.class, "cityMaster");

		    criteria.add(Restrictions.eq("cityMaster.countryCode", countryCode));

		    criteria.add(Restrictions.eq("cityMaster.stateCode", stateCode));

		    criteria.add(Restrictions.eq("cityMaster.districtCode", districtCode));

		    criteria.add(Restrictions.eq("cityMaster.cityCode", cityCode));

		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		    List<CityMaster> lstrecord = (List<CityMaster>) findAll(criteria);

		    if (lstrecord.size() != 0) {

			      CityMaster cityId = lstrecord.get(0);

			      DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CityMasterDesc.class, "cityMasterDesc");

			      detachedCriteria.setFetchMode("cityMasterDesc.fsCityMaster", FetchMode.JOIN);
			      detachedCriteria.createAlias("cityMasterDesc.fsCityMaster", "fsCityMaster", JoinType.INNER_JOIN);
			      detachedCriteria.add(Restrictions.eq("fsCityMaster.cityId", cityId.getCityId()));

			      detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

			      List<CityMasterDesc> lstCityRec = (List<CityMasterDesc>) findAll(detachedCriteria);

			      if (lstCityRec.size() != 0) {
					lstData.addAll(lstCityRec);
			      }

		    }

		    return lstData;
	  }

	  @Override
	  public void deleteRecordPermentelyFromDb(BigDecimal cityIdPk, BigDecimal cityDescIdEnglishPk, BigDecimal cityDescIdLocalPk) {
		    try {
			      CityMaster cityMaster = (CityMaster) getSession().get(CityMaster.class, cityIdPk);
			      if(cityDescIdEnglishPk != null){
			      CityMasterDesc cityMasterDesc = (CityMasterDesc) getSession().get(CityMasterDesc.class, cityDescIdEnglishPk);
			      getSession().delete(cityMasterDesc);
			      }
			     if(cityDescIdLocalPk != null){
			      CityMasterDesc cityMasterDesc2 = (CityMasterDesc) getSession().get(CityMasterDesc.class, cityDescIdLocalPk);
			      getSession().delete(cityMasterDesc2);
			     }
			     getSession().delete(cityMaster);
			      
			      
		    } catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::" + exception.getMessage());
		    }

	  }

	  @Override
	  public List<CityMaster> toFetchRecordsForApproval(String countryCode, String stateCode, String districtCode) {

		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CityMaster.class, "cityMaster");

		    if (countryCode != null) {

			      detachedCriteria.add(Restrictions.eq("cityMaster.countryCode", countryCode));

		    }

		    if (stateCode != null) {

			      detachedCriteria.add(Restrictions.eq("cityMaster.stateCode", stateCode));


		    }

		    if (districtCode != null) {

			      detachedCriteria.add(Restrictions.eq("cityMaster.districtCode", districtCode));

		    }
		    detachedCriteria.add(Restrictions.eq("cityMaster.isActive", Constants.U));
		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		    return (List<CityMaster>) findAll(detachedCriteria);
	  }

	  @Override
	  public List<CityMaster> toCheckBasedOnCountryBasedOnStateBasedOnDistBasedOnCityCode(String countryCode, String stateCode, BigDecimal districtId, String cityCode) {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CityMaster.class, "cityMaster");
		    detachedCriteria.setFetchMode("cityMaster.fsDistrictMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("cityMaster.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);
		    detachedCriteria.add(Restrictions.eq("cityMaster.countryCode", countryCode));
		    detachedCriteria.add(Restrictions.eq("cityMaster.stateCode", stateCode));
		    detachedCriteria.add(Restrictions.eq("fsDistrictMaster.districtId", districtId));
		    detachedCriteria.add(Restrictions.eq("cityMaster.cityCode", cityCode));
		    List<CityMaster> lstCityMasters=(List<CityMaster>) findAll(detachedCriteria);
		    return lstCityMasters;
	  }

	  @Override
	  public List<CityMasterDesc> toFetchCityDesc(String cityName, String countryCode, String stateCode, String districtCode) {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CityMasterDesc.class, "cityMasterDesc");
		    detachedCriteria.setFetchMode("cityMasterDesc.fsLanguageType", FetchMode.JOIN);
		    detachedCriteria.createAlias("cityMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		    detachedCriteria.setFetchMode("cityMasterDesc.fsCityMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("cityMasterDesc.fsCityMaster", "fsCityMaster", JoinType.INNER_JOIN);
		    detachedCriteria.add(Restrictions.eq("cityMasterDesc.cityName", cityName));
		    detachedCriteria.add(Restrictions.eq("fsCityMaster.countryCode", countryCode));
		    detachedCriteria.add(Restrictions.eq("fsCityMaster.stateCode", stateCode));
		    detachedCriteria.add(Restrictions.eq("fsCityMaster.districtCode", districtCode));
		    List<CityMasterDesc> lstCityMasterDesc=(List<CityMasterDesc>) findAll(detachedCriteria);
		    return lstCityMasterDesc;
	  }

	  @Override
	  public String toFetchDestrictCodeBasedOnDistrictId(BigDecimal districtId, String stateCode) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DistrictMaster.class, "districtMaster");
			
			
			// Join FS_STATE_MASTER table
			detachedCriteria.setFetchMode("districtMaster.fsStateMaster", FetchMode.JOIN);
			detachedCriteria.createAlias("districtMaster.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
			// Join FS_COUNTRY_MASTER table
			/*detachedCriteria.setFetchMode("fsStateMaster.fsCountryMaster", FetchMode.JOIN);
			detachedCriteria.createAlias("fsStateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
			*/
			detachedCriteria.add(Restrictions.eq("districtMaster.districtId", districtId));
			detachedCriteria.add(Restrictions.eq("fsStateMaster.stateCode", stateCode));
			//detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryCode", countryCode));
			detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<DistrictMaster> lstDistrictMasters=(List<DistrictMaster>) findAll(detachedCriteria);
			 String districtCode = null;
			if(lstDistrictMasters !=null && lstDistrictMasters.size() !=0){
				  districtCode=lstDistrictMasters.get(0).getDistrictCode();	  
			}
			return districtCode;
	  }

	  @Override
	  public List<CityMaster> viewRecordDetailsForCheck(String cityCode, String countryCode, String stateCode, BigDecimal districtId) {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CityMaster.class, "cityMaster");
		    detachedCriteria.setFetchMode("cityMaster.fsDistrictMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("cityMaster.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);
		    detachedCriteria.add(Restrictions.eq("cityMaster.countryCode", countryCode));
		    detachedCriteria.add(Restrictions.eq("cityMaster.stateCode", stateCode));
		    detachedCriteria.add(Restrictions.eq("cityMaster.cityCode", cityCode));
		    detachedCriteria.add(Restrictions.eq("fsDistrictMaster.districtId", districtId));
		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<CityMaster> lstCityMaster=(List<CityMaster>) findAll(detachedCriteria);
		   if(lstCityMaster != null && lstCityMaster.size() !=0){
			     return lstCityMaster;
		   }
		   return lstCityMaster;
	  }

	  @Override
	  public void saveAllRecorsToDB(CityMaster cityMaster, CityMasterDesc desc, CityMasterDesc desc1) {
		      getSession().saveOrUpdate(cityMaster);
		      getSession().saveOrUpdate(desc);
		      getSession().saveOrUpdate(desc1);    
		    
	  }

}

package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.treasury.dao.IRemittanceDao;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.util.Constants;

@Repository
public class RemittanceDaoImpl<T> extends CommonDaoImpl<T> implements IRemittanceDao{

	@Override
	public void saveRecord(RemittanceModeMaster remittanceModeMaster) {

		getSession().saveOrUpdate(remittanceModeMaster);
	}

	@Override
	public void saveRecordForDesc(RemittanceModeDescription remittanceModeDescription) {

		getSession().saveOrUpdate(remittanceModeDescription);
	}

	@Override
	public List<RemittanceModeMaster> getRemittanceCheck(String remittance) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeMaster.class, "remittanceModeMaster");
		criteria.add(Restrictions.eq("remittanceModeMaster.remittance", remittance).ignoreCase());

		return (List<RemittanceModeMaster>)findAll(criteria);
	}

	@Override
	public List<String> getremittancecodelist(String query,BigDecimal serviceId){

		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeMaster.class, "remittanceModeMaster");
		criteria.add(Restrictions.like("remittanceModeMaster.remittance", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.setProjection(Projections.property("remittanceModeMaster.remittance"));
		criteria.setFetchMode("remittanceModeMaster.serviceMaster",FetchMode.JOIN);
		criteria.createAlias("remittanceModeMaster.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.serviceId", serviceId));
		criteria.addOrder(Order.asc("remittanceModeMaster.remittance"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<String>)findAll(criteria);
	}

	@Override
	public List<RemittanceModeMaster> getremittanceDesc(String remiId,BigDecimal serviceId) {

		System.out.println("String is comingssssssssssssssssssssssssssssssss"+remiId);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeMaster.class, "remittanceModeMaster");

		criteria.add(Restrictions.eq("remittanceModeMaster.remittance", remiId));

		criteria.setFetchMode("remittanceModeMaster.serviceMaster",FetchMode.JOIN);
		criteria.createAlias("remittanceModeMaster.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.serviceId", serviceId));



		return (List<RemittanceModeMaster>)findAll(criteria);
	}

	@Override
	public List<RemittanceModeDescription> getRemittancDesc(BigDecimal remiId) {


		System.out.println("IDDDDDDDDDDDDDDDDDDDDDddddd"+remiId);

		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "RemittanceModeDescription");

		criteria.setFetchMode("RemittanceModeDescription.remittanceModeMaster",FetchMode.SELECT);
		criteria.createAlias("RemittanceModeDescription.remittanceModeMaster", "remittanceModeMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId", remiId));

		// TODO Auto-generated method stub
		return (List<RemittanceModeDescription>)findAll(criteria);
	}


	@Override
	public List<RemittanceModeDescription> getListRemittance() {
		/*DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeMaster.class, "RemittanceModeMaster");

		 criteria.setFetchMode("RemittanceModeMaster.serviceMaster",FetchMode.SELECT);
         criteria.createAlias("RemittanceModeMaster.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
         criteria.add(Restrictions.eq("RemittanceModeMaster.isActive", "Y"));*/
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");
		criteria.setFetchMode("remittanceModeDescription.languageType", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.languageType", "languageType",JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceModeDescription.remittanceModeMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.remittanceModeMaster", "remittanceModeMaster",JoinType.INNER_JOIN);
		//criteria.add(Restrictions.ne("remittanceModeMaster.isActive",Constants.U));
		criteria.setFetchMode("remittanceModeMaster.serviceMaster",FetchMode.SELECT);
		criteria.createAlias("remittanceModeMaster.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		return (List<RemittanceModeDescription>)findAll(criteria);
	}

	@Override
	public String getRemittanceDesc(BigDecimal remitId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");

		criteria.setFetchMode("remittanceModeDescription.remittanceModeMaster",FetchMode.SELECT);
		criteria.createAlias("remittanceModeDescription.remittanceModeMaster", "remittanceModeMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId", remitId));

		criteria.setFetchMode("remittanceModeDescription.languageType",FetchMode.SELECT);
		criteria.createAlias("remittanceModeDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", new BigDecimal(Constants.ENGLISH_LANGUAGE_ID)));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List<RemittanceModeDescription> list = (List<RemittanceModeDescription>) criteria.getExecutableCriteria(getSession()).list();
		if (list != null && !list.isEmpty()) {
			return list.get(0).getRemittanceDescription();
		}
		return null;
	}

	@Override
	public String getRemittanceArbDesc(BigDecimal remitId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");

		criteria.setFetchMode("remittanceModeDescription.remittanceModeMaster",FetchMode.SELECT);
		criteria.createAlias("remittanceModeDescription.remittanceModeMaster", "remittanceModeMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId", remitId));

		criteria.setFetchMode("remittanceModeDescription.languageType",FetchMode.SELECT);
		criteria.createAlias("remittanceModeDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", new BigDecimal(Constants.ARABIC_LANGUAGE_ID)));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		return ((RemittanceModeDescription)criteria.getExecutableCriteria(getSession()).list().get(0)).getRemittanceDescription();

		//return ((CountryMasterDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCountryName();
	}

	@Override
	public List<Object> getlistRemittance() {
		List<Object> objList=new ArrayList<Object>();
		String queryString="select c.service_master_id, c.remittance_mode_id, a.remittance_desc_english, b.remittance_desc_local, c.remittance_code, c.created_by, c.created_date, c.remittance_mode_id, b.remittance_mode_desc_id from  (select remittance_mode_id, remittance_mode_desc_id,remittance_desc_english,remittance_mode_desc_id from ex_remittance_mode_desc where language_id=1) a, (select remittance_mode_id, remittance_desc_local,remittance_mode_desc_id from ex_remittance_mode_desc where language_id=2) b, ex_remittance_mode c where c.isactive ='Y' and a.remittance_mode_id = b.remittance_mode_id and a.remittance_mode_id = c.remittance_mode_id and b.remittance_mode_id = c.remittance_mode_id ";

		objList =  getSession().createSQLQuery(queryString).list();

		return objList;
	}

	@Override
	public BigDecimal getRemittanceDescArbPk(String localArb) {

		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");

		criteria.add(Restrictions.eq("remittanceModeDescription.remittanceDescription", localArb));
		return ((RemittanceModeDescription)criteria.getExecutableCriteria(getSession()).list().get(0)).getRemittanceModeDescId();
	}

	@Override
	public BigDecimal getRemittanceDescEngPk(String localEng) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");

		criteria.add(Restrictions.eq("remittanceModeDescription.remittanceDescription", localEng));
		return ((RemittanceModeDescription)criteria.getExecutableCriteria(getSession()).list().get(0)).getRemittanceModeDescId();
	}

	@Override
	public List<RemittanceModeMaster> getRemittanceModeApproval() {
		DetachedCriteria criteria = DetachedCriteria.forClass( RemittanceModeMaster.class, "remittanceModeMaster");

		criteria.setFetchMode("remittanceModeMaster.serviceMaster",FetchMode.SELECT);
		criteria.createAlias("remittanceModeMaster.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("remittanceModeMaster.isActive", Constants.U));
		criteria.add(Restrictions.isNull("remittanceModeMaster.approvedBy"));
		criteria.add(Restrictions.isNull("remittanceModeMaster.approvedDate"));

		return (List<RemittanceModeMaster>)findAll(criteria);
	}

	@Override
	public String getEngRemitDes(BigDecimal engId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");

		System.out.println(engId+"to get Relation Id%%%%%%%%%%%%%%%%%%");

		criteria.setFetchMode("remittanceModeDescription.remittanceModeMaster",FetchMode.SELECT);
		criteria.createAlias("remittanceModeDescription.remittanceModeMaster", "remittanceModeMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId", engId));

		criteria.setFetchMode("remittanceModeDescription.languageType",FetchMode.SELECT);
		criteria.createAlias("remittanceModeDescription.languageType", "languageType", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("languageType.languageId",new BigDecimal( Constants.ENGLISH_LANGUAGE_ID)));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<RemittanceModeDescription> remittanceDescriptionlist=(List<RemittanceModeDescription>) findAll(criteria);

		String remittanceDescription=remittanceDescriptionlist.get(0).getRemittanceDescription();
		if(remittanceDescription!=null){
			return remittanceDescription;
		}else{
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public String getArbRemiDes(BigDecimal araId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");

		criteria.setFetchMode("remittanceModeDescription.remittanceModeMaster",FetchMode.SELECT);
		criteria.createAlias("remittanceModeDescription.remittanceModeMaster", "remittanceModeMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId", araId));

		criteria.setFetchMode("remittanceModeDescription.languageType",FetchMode.SELECT);
		criteria.createAlias("remittanceModeDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", new BigDecimal( Constants.ARABIC_LANGUAGE_ID)));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List<RemittanceModeDescription> remittanceDescriptionlist=(List<RemittanceModeDescription>) findAll(criteria);
		
		String remittanceDescription=remittanceDescriptionlist.get(0).getRemittanceDescription();
		if(remittanceDescription!=null){
			return remittanceDescription;
		}else{
			return null;
		}

	}

	@Override
	public String getCreatedBy() {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeMaster.class, "remittanceModeMaster");

		return ((RemittanceModeMaster)criteria.getExecutableCriteria(getSession()).list().get(0)).getCreatedBy();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemittanceModeMaster> getRemitCheck(String remitance) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeMaster.class, "remittanceModeMaster");

		criteria.add(Restrictions.eq("remittanceModeMaster.remittance", remitance).ignoreCase());

		return (List<RemittanceModeMaster>)findAll(criteria);

	}



	@Override
	public String approveRecord(BigDecimal remittancePk, String userName) {
		String approveMsg;
		RemittanceModeMaster remittance = (RemittanceModeMaster) getSession().get(RemittanceModeMaster.class, remittancePk);
		String approvedUser=remittance.getApprovedBy();
		if(approvedUser==null)
		{
			remittance.setIsActive(Constants.Yes);
			remittance.setApprovedBy(userName);
			remittance.setApprovedDate(new Date());
			getSession().update(remittance);
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;

		/*RemittanceModeMaster remittance=(RemittanceModeMaster) getSession().get(RemittanceModeMaster.class, remittancePk);
		remittance.setIsActive(Constants.Yes);
		remittance.setApprovedBy(userName);
		remittance.setApprovedDate(new Date());
		getSession().update(remittance);*/
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceMasterDesc> getServiceMastersActivateList(BigDecimal languageId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ServiceMasterDesc.class, "serviceMasterDesc");
		criteria.setFetchMode("serviceMasterDesc.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.serviceMaster", "serviceMaster",  JoinType.INNER_JOIN);
		criteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.languageType", "languageType",  JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId",  languageId));
		criteria.add(Restrictions.eq("serviceMaster.isActive",  Constants.Yes));
		criteria.addOrder(Order.asc("serviceMasterDesc.localServiceDescription"));/** NAG CODE APPLY ASCENDING ORDER 05/02/2015**/
		return (List<ServiceMasterDesc>) findAll(criteria);
	}

	@Override
	public void delete(RemittanceModeMaster remittanceModeMaster) {
		getSession().delete(remittanceModeMaster );
	}

	@Override
	public void delete(RemittanceModeDescription remittanceModeDescription) {
		getSession().delete(remittanceModeDescription );

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemittanceModeDescription> getCheckRemittCode(String remittanceCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");
		criteria.setFetchMode("remittanceModeDescription.languageType", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.languageType", "languageType",JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceModeDescription.remittanceModeMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.remittanceModeMaster", "remittanceModeMaster",JoinType.INNER_JOIN);
		//criteria.add(Restrictions.ne("remittanceModeMaster.isActive",Constants.U));
		criteria.setFetchMode("remittanceModeMaster.serviceMaster",FetchMode.SELECT);
		criteria.createAlias("remittanceModeMaster.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeMaster.remittance", remittanceCode));
		return (List<RemittanceModeDescription>)findAll(criteria);

	}

	@SuppressWarnings("unchecked")
	@Override
	public String getRemittanceDesc(BigDecimal remitId, BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");
		criteria.setFetchMode("remittanceModeDescription.languageType", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.languageType", "languageType",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId",  languageId));
		
		criteria.setFetchMode("remittanceModeDescription.remittanceModeMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.remittanceModeMaster", "remittanceModeMaster",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId", remitId));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return ((List<RemittanceModeDescription>)findAll(criteria)).get(0).getRemittanceDescription();
	}

	// Method for view record by subramanian
	@Override
	public List<RemittanceModeMaster> viewAllRemittanceMode() {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeMaster.class, "remittanceModeMaster");

		criteria.setFetchMode("remittanceModeMaster.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceModeMaster.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		
		List<RemittanceModeMaster> objList = (List<RemittanceModeMaster>) findAll(criteria);
		
		if(objList.isEmpty())
			return null;

		return objList;
	}
	
	@Override
	public List<RemittanceModeDescription> viewRemittanceDescBasedOnRemittanceId(BigDecimal remittanceId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");

		criteria.setFetchMode("remittanceModeDescription.remittanceModeMaster", FetchMode.SELECT);
		criteria.createAlias("remittanceModeDescription.remittanceModeMaster", "remittanceModeMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId", remittanceId));

		List<RemittanceModeDescription> objList = (List<RemittanceModeDescription>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;
	}

	//End by subramanian
}

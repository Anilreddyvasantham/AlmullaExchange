package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.treasury.dao.IZoneMasterDao;
import com.amg.exchange.treasury.model.Zone;
import com.amg.exchange.treasury.model.ZoneMasterDesc;
import com.amg.exchange.util.Constants;

@SuppressWarnings(value = { "unchecked" })
@Repository
public class ZoneMasterDaoImple<T> extends CommonDaoImpl<T> implements IZoneMasterDao {

	@Override
	public void saveRecord(Zone zones) {
		getSession().saveOrUpdate(zones);
	}

	@Override
	public void saveRecordDesc(ZoneMasterDesc zoneMasterDesc) {
		getSession().saveOrUpdate(zoneMasterDesc);
	}

	@Override
	public List<String> getZoneListCode(String query) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Zone.class, "zone");
		criteria.add(Restrictions.like("zone.zoneCode", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.setProjection(Projections.property("zone.zoneCode"));
		criteria.addOrder(Order.asc("zone.zoneCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<String> lstZone = (List<String>) findAll(criteria);
		return lstZone;
	}

	@Override
	public List<Zone> getZoneList(String zoneCode) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Zone.class, "zone");
		detachedCriteria.add(Restrictions.eq("zone.zoneCode", zoneCode));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<Zone>) findAll(detachedCriteria);
	}

	@Override
	public List<ZoneMasterDesc> getZoneDescriptionList(BigDecimal zoneId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ZoneMasterDesc.class, "zoneMasterDesc");
		detachedCriteria.setFetchMode("zoneMasterDesc.zone", FetchMode.JOIN);
		detachedCriteria.createAlias("zoneMasterDesc.zone", "zone", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("zone.zoneId", zoneId));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ZoneMasterDesc>) findAll(detachedCriteria);

	}

	@Override
	public List<ZoneMasterDesc> getAllZoneList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ZoneMasterDesc.class, "zoneMasterDesc");
		criteria.setFetchMode("zoneMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("zoneMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.setFetchMode("zoneMasterDesc.zone", FetchMode.JOIN);
		criteria.createAlias("zoneMasterDesc.zone", "zone", JoinType.INNER_JOIN);
		return (List<ZoneMasterDesc>) findAll(criteria);
	}

	@Override
	public List<ZoneMasterDesc> getZoneMasterApprovel() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ZoneMasterDesc.class, "zoneMasterDesc");
		criteria.setFetchMode("zoneMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("zoneMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.setFetchMode("zoneMasterDesc.zone", FetchMode.JOIN);
		criteria.createAlias("zoneMasterDesc.zone", "zone", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("zone.isactive", Constants.U));
		return (List<ZoneMasterDesc>) findAll(criteria);
	}

	@Override
	public  String approveRecord(BigDecimal zoneIdpk, String userName) {
		String approveMsg;
		Zone zone = (Zone) getSession().get(Zone.class, zoneIdpk);
		String approvedUser=zone.getApprovedBy();
		if(approvedUser==null)
		{
			zone.setIsactive(Constants.Yes);
			zone.setApprovedBy(userName);
			zone.setApprovedDate(new Date());
			getSession().update(zone);
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;
		 
	}

	@Override
	public void deleteRecordDesc(ZoneMasterDesc zoneMasterDesc) {
		getSession().delete(zoneMasterDesc);

	}

	@Override
	public void deleteRecord(Zone zone) {
		getSession().delete(zone);

	}

	@Override
	public List<ZoneMasterDesc> getZoneMasterList(BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ZoneMasterDesc.class, "zoneMasterDesc");
		criteria.setFetchMode("zoneMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("zoneMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.setFetchMode("zoneMasterDesc.zone", FetchMode.JOIN);
		criteria.createAlias("zoneMasterDesc.zone", "zone", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("zone.isactive", Constants.Yes));
		return (List<ZoneMasterDesc>) findAll(criteria);
	}

	@Override
	public List<Zone> getAllZoneListOnly() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Zone.class, "zone");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<Zone>) findAll(criteria);
	}

}

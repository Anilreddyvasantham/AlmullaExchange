package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.dao.IRelationsDao;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class RelationsDaoImpl extends CommonDaoImpl implements IRelationsDao {
	private static final Logger log = Logger.getLogger(RelationsDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public void saveRecord(Relations relations) {
		saveOrUpdate(relations);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveRecordForDesc(RelationsDescription relationsDescription) {
		saveOrUpdate(relationsDescription);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Relations> getRealtion(String relationid) {
		log.info("Entered into getRealtion() method");
		log.info("relationid=" + relationid);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Relations.class, "relations");
		detachedCriteria.add(Restrictions.eq("relations.relationsCode", relationid).ignoreCase());
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		log.info("Exited from getRealtion() method");
		return (List<Relations>) findAll(detachedCriteria);
	}

	/* added to populate the DB Values added @Koti 20/02/2015* */
	@SuppressWarnings("unchecked")
	@Override
	public List<Relations> getRelationList(String relationsCode) {
		log.info("Entered into getRelationList() method::::::relationsCode=" + relationsCode);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Relations.class, "relations");
		detachedCriteria.add(Restrictions.eq("relations.relationsCode", relationsCode));
		return (List<Relations>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RelationsDescription> getRelationsDescriptionList(BigDecimal relationsId) {
		log.info("Entered into getRelationsDescriptionList() method::::::relationsId=" + relationsId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RelationsDescription.class, "relationsDescription");
		detachedCriteria.setFetchMode("relationsDescription.relations", FetchMode.JOIN);
		detachedCriteria.createAlias("relationsDescription.relations", "relations", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("relations.relationsId", relationsId));
		log.info("Exited from  getRelationsDescriptionList() method");
		return (List<RelationsDescription>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Relations> getAllComponent(String relationsCode) {
		log.info("Entered into getAllComponent() method");
		log.info("relationsCode=" + relationsCode);
		DetachedCriteria criteria = DetachedCriteria.forClass(Relations.class, "relations");
		criteria.add(Restrictions.like("relations.relationsCode", relationsCode, MatchMode.ANYWHERE).ignoreCase());
		criteria.addOrder(Order.asc("relations.relationsCode"));
		// criteria.add(Restrictions.eq("relations.isActive", "Y"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		log.info("Exited from  getAllComponent() method");
		return (List<Relations>) findAll(criteria);
	}

	/* Ended to populate the DB Values added @Koti 20/02/2015* */
	@SuppressWarnings("unchecked")
	@Override
	public List<RelationsDescription> getAllRelationList() {
		log.info("Entered into getAllRelationList() method");
		DetachedCriteria criteria = DetachedCriteria.forClass(RelationsDescription.class, "relationsDescription");
		criteria.setFetchMode("relationsDescription.languageType", FetchMode.JOIN);
		criteria.createAlias("relationsDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.setFetchMode("relationsDescription.relations", FetchMode.JOIN);
		criteria.createAlias("relationsDescription.relations", "relations", JoinType.INNER_JOIN);
		// RM commentted on 14 May 2015
		/*
		 * List<String> activeStatuslist = new ArrayList<String>();
		 * activeStatuslist.add("Y"); activeStatuslist.add("D");
		 * activeStatuslist.add("U"); //21/04/2015 NAG ADDED THIS LINE
		 * criteria.add(Restrictions.in("relations.isActive",
		 * activeStatuslist));
		 */
		// criteria.add(Restrictions.ne("relations.isActive" , "U"));
		log.info("Exited from getAllRelationList() method");
		return (List<RelationsDescription>) findAll(criteria);
	}

	@Override
	public String getEngRelation(BigDecimal relationsId) {
		log.info("Entered into getEngRelation() method relationsId=" + relationsId);
		DetachedCriteria criteria = DetachedCriteria.forClass(RelationsDescription.class, "relationsDescription");
		criteria.setFetchMode("relationsDescription.relations", FetchMode.SELECT);
		criteria.createAlias("relationsDescription.relations", "relations", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("relations.relationsId", relationsId));
		criteria.setFetchMode("relationsDescription.languageType", FetchMode.SELECT);
		criteria.createAlias("relationsDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", new BigDecimal(1)));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		log.info("Exited from getEngRelation() method");
		return ((RelationsDescription) criteria.getExecutableCriteria(getSession()).list().get(0)).getLocalRelationsDescription();
	}

	@Override
	public String getArabicRelation(BigDecimal arabicId) {
		log.info("Entered into getArabicRelation() method arabicId=" + arabicId);
		DetachedCriteria criteria = DetachedCriteria.forClass(RelationsDescription.class, "relationsDescription");
		criteria.setFetchMode("relationsDescription.relations", FetchMode.SELECT);
		criteria.createAlias("relationsDescription.relations", "relations", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("relations.relationsId", arabicId));
		criteria.setFetchMode("relationsDescription.languageType", FetchMode.SELECT);
		criteria.createAlias("relationsDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", new BigDecimal(2)));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		log.info("Exited from getArabicRelation() method");
		return ((RelationsDescription) criteria.getExecutableCriteria(getSession()).list().get(0)).getLocalRelationsDescription();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Relations> getRelationListApproval() {
		log.info("Entered into getRelationListApproval() method");
		DetachedCriteria criteria = DetachedCriteria.forClass(Relations.class, "relations");
		criteria.add(Restrictions.eq("relations.isActive", Constants.U));
		criteria.add(Restrictions.isNull("relations.approvedBy"));
		criteria.add(Restrictions.isNull("relations.approvedDate"));
		log.info("Exited from getRelationListApproval() method");
		return (List<Relations>) findAll(criteria);
	}

	@Override
	public String getCreatedBy() {
		log.info("Entered into getCreatedBy() method");
		DetachedCriteria criteria = DetachedCriteria.forClass(Relations.class, "relations");
		return ((Relations) criteria.getExecutableCriteria(getSession()).list().get(0)).getCreatedBy();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RelationsDescription> getAllActiveInActive(String englishDesc) {
		log.info("Entered into getAllActiveInActive() method");
		DetachedCriteria criteria = DetachedCriteria.forClass(RelationsDescription.class, "relationsDescription");
		criteria.setFetchMode("relationsDescription.languageType", FetchMode.JOIN);
		criteria.createAlias("relationsDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.setFetchMode("relationsDescription.relations", FetchMode.JOIN);
		criteria.createAlias("relationsDescription.relations", "relations", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("relationsDescription.localRelationsDescription", englishDesc));
		log.info("Exited from  getAllActiveInActive() method");
		return (List<RelationsDescription>) findAll(criteria);
	}

	public void delete(Relations relation) {
		getSession().delete(relation);
	}

	public void delete(RelationsDescription relationsdesc) {
		getSession().delete(relationsdesc);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RelationsDescription> getCheckRelationCode(String relationsCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RelationsDescription.class, "relationsDescription");
		criteria.setFetchMode("relationsDescription.languageType", FetchMode.JOIN);
		criteria.createAlias("relationsDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.setFetchMode("relationsDescription.relations", FetchMode.JOIN);
		criteria.createAlias("relationsDescription.relations", "relations", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("relations.relationsCode", relationsCode));
		return (List<RelationsDescription>) findAll(criteria);
	}

	@Override
	public String approveRecord(BigDecimal relationPk, String userName) {
		String approveMsg;
		Relations relations = (Relations) getSession().get(Relations.class, relationPk);
		String approvedUser = relations.getApprovedBy();
		if (approvedUser == null) {
			relations.setIsActive(Constants.Yes);
			relations.setApprovedBy(userName);
			relations.setApprovedDate(new Date());
			getSession().update(relations);
			approveMsg = "Success";
		} else {
			approveMsg = "Fail";
		}
		return approveMsg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Relations> getAllRecords() {
		
		

		log.info("Entered into getAllRecords() method");
		DetachedCriteria criteria = DetachedCriteria.forClass(Relations.class, "relations");
		log.info("Exited from getAllRelationList() method");
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<Relations>) findAll(criteria);
	
	}
}

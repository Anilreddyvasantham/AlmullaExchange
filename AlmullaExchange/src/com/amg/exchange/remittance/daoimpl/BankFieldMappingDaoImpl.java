package com.amg.exchange.remittance.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.AvailableTableColumns;
import com.amg.exchange.remittance.dao.IBankFieldMappingDao;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.BankFieldMapping;
import com.amg.exchange.util.Constants;

@SuppressWarnings({ "unchecked" })
@Repository
public class BankFieldMappingDaoImpl<T> extends CommonDaoImpl<T> implements IBankFieldMappingDao<T>, Serializable {
	  private static final long serialVersionUID = 1L;
	  private static final Logger log = Logger.getLogger(BankFieldMappingDaoImpl.class);

	  @Override
	  public List<AdditionalBankRuleMap> toFetchAdditionalData() {
		    DetachedCriteria dCriteria = DetachedCriteria.forClass(AdditionalBankRuleMap.class, "additionalBankRuleMap");
		    dCriteria.addOrder(Order.asc("additionalBankRuleMap.fieldName"));
		    dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<AdditionalBankRuleMap> lstAdditionalBankRuleMaps = (List<AdditionalBankRuleMap>) findAll(dCriteria);
		    return lstAdditionalBankRuleMaps;
	  }

	  @Override
	  public void saveAllBankFieldMapping(BankFieldMapping bankFieldMapping) {
		    try {
			      getSession().saveOrUpdate(bankFieldMapping);
		    } catch (Exception exception) {
			      log.info("exception.getMessage()::::::::::::::::::::::::::::::::::::::::::::::" + exception.getMessage());
		    }
	  }

	  @Override
	  public void deleteRecordPermentelyFromDb(BigDecimal bankFieldMappingId) {
		    try {
			      BankFieldMapping bankFieldMapping = (BankFieldMapping) getSession().get(BankFieldMapping.class, bankFieldMappingId);
			      getSession().delete(bankFieldMapping);
		    } catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::" + exception.getMessage());
		    }
	  }

	  @Override
	  public void upDateActiveRecordtoDeActive(BigDecimal bankFieldMappingId, String remarks, String userName) {
		    try {
			      BankFieldMapping bankFieldMapping = (BankFieldMapping) getSession().get(BankFieldMapping.class, bankFieldMappingId);
			      bankFieldMapping.setRemarks(remarks);
			      bankFieldMapping.setModifiedBy(userName);
			      bankFieldMapping.setModifiedDate(new Date());
			      bankFieldMapping.setApprovedBy(null);
			      bankFieldMapping.setApprovedDate(null);
			      bankFieldMapping.setIsActive(Constants.D);
			      getSession().update(bankFieldMapping);
		    } catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::" + exception.getMessage());
		    }

	  }

	  @Override
	  public void DeActiveRecordToPendingForApprovalBankFieldMapping(BigDecimal bankFieldMappingId, String userName) {
		    try {
			      BankFieldMapping bankFieldMapping = (BankFieldMapping) getSession().get(BankFieldMapping.class, bankFieldMappingId);
			      bankFieldMapping.setModifiedBy(userName);
			      bankFieldMapping.setModifiedDate(new Date());
			      bankFieldMapping.setApprovedBy(null);
			      bankFieldMapping.setApprovedDate(null);
			      bankFieldMapping.setIsActive(Constants.U);
			      bankFieldMapping.setRemarks(null);
			      getSession().update(bankFieldMapping);
		    } catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::" + exception.getMessage());
		    }
	  }

	  @Override
	  public List<BankFieldMapping> toFetchAllViewDetailsOfBankFieldMapping(BigDecimal countryId) {
		    DetachedCriteria dCriteria = DetachedCriteria.forClass(BankFieldMapping.class, "bankFieldMapping");
		    dCriteria.setFetchMode("bankFieldMapping.bankId", FetchMode.JOIN);
		    dCriteria.createAlias("bankFieldMapping.bankId", "bankId", JoinType.INNER_JOIN);
		    dCriteria.setFetchMode("bankFieldMapping.exbankAdditinalFields", FetchMode.JOIN);
		    dCriteria.createAlias("bankFieldMapping.exbankAdditinalFields", "exbankAdditinalFields", JoinType.INNER_JOIN);
		    dCriteria.add(Restrictions.eq("bankFieldMapping.applicationCountry", countryId));
		    dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<BankFieldMapping> lstBankFieldMapping = (List<BankFieldMapping>) findAll(dCriteria);
		    return lstBankFieldMapping;
	  }

	  @Override
	  public String toFtechCoumnIdBasedOnColumnName(String fieldName,String tableName) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(AvailableTableColumns.class, "availableTableColumns");
		    criteria.add(Restrictions.eq("availableTableColumns.columnName", fieldName));
		    criteria.add(Restrictions.eq("availableTableColumns.tableName", tableName));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<AvailableTableColumns> lstAvailableTableColumns = (List<AvailableTableColumns>) findAll(criteria);
		    String columnId=null;
		    if(lstAvailableTableColumns.size() !=0){
			      columnId=lstAvailableTableColumns.get(0).getColumnId();
		    }
		    return columnId;
	  }

	  @Override
	  public List<BankFieldMapping> toFetchAllApprovalDetailsOfBankFieldMapping(BigDecimal countryId) {
		    DetachedCriteria dCriteria = DetachedCriteria.forClass(BankFieldMapping.class, "bankFieldMapping");
		    dCriteria.setFetchMode("bankFieldMapping.bankId", FetchMode.JOIN);
		    dCriteria.createAlias("bankFieldMapping.bankId", "bankId", JoinType.INNER_JOIN);
		    dCriteria.setFetchMode("bankFieldMapping.exbankAdditinalFields", FetchMode.JOIN);
		    dCriteria.createAlias("bankFieldMapping.exbankAdditinalFields", "exbankAdditinalFields", JoinType.INNER_JOIN);
		    dCriteria.add(Restrictions.eq("bankFieldMapping.applicationCountry", countryId));
		    dCriteria.add(Restrictions.eq("bankFieldMapping.isActive", Constants.U));
		    dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<BankFieldMapping> lstBankFieldMapping = (List<BankFieldMapping>) findAll(dCriteria);
		    return lstBankFieldMapping;
	  }

	  @Override
	  public String checkBankFieldMappingMultiUser(BigDecimal bankFieldMappingId, String userName) {
		    String approvalMsg;
		    BankFieldMapping bankFieldMapping = (BankFieldMapping) getSession().get(BankFieldMapping.class, bankFieldMappingId);
		    String approvalUser = bankFieldMapping.getApprovedBy();
		    if (approvalUser == null) {
			      bankFieldMapping.setIsActive(Constants.Yes);
			      bankFieldMapping.setApprovedBy(userName);
			      bankFieldMapping.setApprovedDate(new Date());
			      getSession().update(bankFieldMapping);
			      approvalMsg = "Success";
		    } else {
			      approvalMsg = "Fail";
		    }
		    return approvalMsg;
	  }

	  @Override
	  public List<BankFieldMapping> toCheckDuplicateData(String tableName, String fieldName, String fieldvalue) {
		    DetachedCriteria dCriteria = DetachedCriteria.forClass(BankFieldMapping.class, "bankFieldMapping");
		    dCriteria.add(Restrictions.eq("bankFieldMapping.tableName", tableName));
		    dCriteria.add(Restrictions.eq("bankFieldMapping.fieldName", fieldName));
		    dCriteria.add(Restrictions.eq("bankFieldMapping.fieldValue", fieldvalue));
		    dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<BankFieldMapping> lstBankFieldMapping = (List<BankFieldMapping>) findAll(dCriteria);
		    return lstBankFieldMapping;
	  }

}

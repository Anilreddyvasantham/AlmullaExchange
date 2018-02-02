package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.dao.IBankExternalReferenceDao;
import com.amg.exchange.remittance.model.BankExternalReferenceDetail;
import com.amg.exchange.remittance.model.BankExternalReferenceHead;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BankExternalReferenceDaoImpl extends CommonDaoImpl implements IBankExternalReferenceDao {
	private static final Logger LOGGER = Logger.getLogger(BankExternalReferenceDaoImpl.class);


	@Override
	public void saveHeaderData(BankExternalReferenceHead bankExternalReferenceHead) {
		LOGGER.info("Entering into saveHeaderData method");
		saveOrUpdate(bankExternalReferenceHead);
		LOGGER.info("Exit into saveHeaderData method");
	}

	@Override
	public void saveDetailData(BankExternalReferenceDetail bankExternalReferenceDetail) {
		LOGGER.info("Entering into saveDetailData method");
		saveOrUpdate(bankExternalReferenceDetail);
		LOGGER.info("Exit into saveHeaderData method");
	}

	@Override
	public List<BankExternalReferenceHead> viewBean(BigDecimal countryId, BigDecimal bankId,BigDecimal appContryId,BigDecimal beneBankId) {
		LOGGER.info("Entering into viewBean method");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankExternalReferenceHead.class, "bankExternalReferenceHead");
		
		dCriteria.setFetchMode("bankExternalReferenceHead.bank", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceHead.bank", "bank", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bank.bankId", bankId));
		
		dCriteria.setFetchMode("bankExternalReferenceHead.applicationCountry", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceHead.applicationCountry", "applicationCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("applicationCountryId.countryId", appContryId));
		
		dCriteria.setFetchMode("bankExternalReferenceHead.bankCountry", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceHead.bankCountry", "bankCountry", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankCountry.countryId", countryId));
		
		if(beneBankId!=null)
		{
			dCriteria.setFetchMode("bankExternalReferenceHead.beneficaryBank", FetchMode.JOIN);
			dCriteria.createAlias("bankExternalReferenceHead.beneficaryBank", "beneficaryBank", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("beneficaryBank.bankId", beneBankId));
		}
		
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewBean method");
		return (List<BankExternalReferenceHead>) findAll(dCriteria);
	}

	@Override
	public BankExternalReferenceHead findHeaderById(BigDecimal bankExtRefSeqId) {
		LOGGER.info("Entering into findHeaderById method");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankExternalReferenceHead.class, "bankExternalReferenceHead");
		dCriteria.add(Restrictions.eq("bankExternalReferenceHead.bankExtRefSeqId", bankExtRefSeqId));
		List<BankExternalReferenceHead> list = (List<BankExternalReferenceHead>) findAll(dCriteria);
		LOGGER.info("Exit into findHeaderById method");
		return list.get(0);
	}

	@Override
	public List<BankExternalReferenceDetail> findDetailById(BigDecimal bankExtRefDetailSeqId) {
		LOGGER.info("Entering into findDetailById method");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankExternalReferenceDetail.class, "bankExternalReferenceDetail");
		dCriteria.setFetchMode("bankExternalReferenceDetail.bankExternalReferenceHead", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceDetail.bankExternalReferenceHead", "bankExternalReferenceHead", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankExternalReferenceHead.bankExtRefSeqId", bankExtRefDetailSeqId));
		dCriteria.setFetchMode("bankExternalReferenceDetail.bank", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceDetail.bank", "bank", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("bankExternalReferenceDetail.countryId", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceDetail.countryId", "country", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("bankExternalReferenceDetail.beneficaryBranch", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceDetail.beneficaryBranch", "beneficaryBranch", JoinType.INNER_JOIN);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into findDetailById method");
		return (List<BankExternalReferenceDetail>) findAll(dCriteria);
	}

	@Override
	public List<BankExternalReferenceDetail> getAllRecords(BigDecimal appCountryId, BigDecimal countryId, BigDecimal bankId, BigDecimal beneaBankId, String bankExternalId) {
		LOGGER.info("Entering into getAllRecords method");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankExternalReferenceDetail.class, "bankExternalReferenceDetail");
		System.out.println("countryId " + countryId);
		System.out.println("bankId " + bankId);
		System.out.println("acountryId " + appCountryId);
		System.out.println("beneacountryId " + beneaBankId);
		dCriteria.setFetchMode("bankExternalReferenceDetail.bank", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceDetail.bank", "bank", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bank.bankId", bankId));
		dCriteria.setFetchMode("bankExternalReferenceDetail.applicationCountry", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceDetail.applicationCountry", "applicationCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("applicationCountryId.countryId", appCountryId));
		dCriteria.setFetchMode("bankExternalReferenceDetail.bankExternalReferenceHead", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceDetail.bankExternalReferenceHead", "bankExternalReferenceHead", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("bankExternalReferenceDetail.beneficaryBank", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceDetail.beneficaryBank", "beneficaryBank", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("beneficaryBank.bankId", beneaBankId));
		dCriteria.setFetchMode("bankExternalReferenceDetail.countryId", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceDetail.countryId", "country", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("country.countryId", countryId));
		dCriteria.add(Restrictions.eq("bankExternalReferenceHead.bankExternalId", bankExternalId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into getAllRecords method");
		return (List<BankExternalReferenceDetail>) findAll(dCriteria);
	}

	@Override
	public List<BankExternalReferenceHead> getAllRecordsfromHead(BigDecimal appCountryId, BigDecimal bankCountryId, BigDecimal bankId, BigDecimal benfiBankId, String externlaId) {
		LOGGER.info("Entering into getAllRecordsfromHead method");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankExternalReferenceHead.class, "bankExternalReferenceHead");
		dCriteria.setFetchMode("bankExternalReferenceHead.bank", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceHead.bank", "bank", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bank.bankId", bankId));
		dCriteria.setFetchMode("bankExternalReferenceHead.applicationCountry", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceHead.applicationCountry", "applicationCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("applicationCountryId.countryId", appCountryId));
		dCriteria.setFetchMode("bankExternalReferenceHead.beneficaryBank", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceHead.beneficaryBank", "beneficaryBank", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("beneficaryBank.bankId", benfiBankId));
		dCriteria.setFetchMode("bankExternalReferenceHead.bankCountry", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceHead.bankCountry", "bankCountry", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankCountry.countryId", bankCountryId));
		dCriteria.add(Restrictions.eq("bankExternalReferenceHead.bankExternalId", externlaId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into getAllRecordsfromHead method");
		return (List<BankExternalReferenceHead>) findAll(dCriteria);
	}

	@Override
	public List<BankExternalReferenceDetail> getAllRecords() {
		LOGGER.info("Entering into getAllRecords method");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankExternalReferenceDetail.class, "bankExternalReferenceDetail");
		dCriteria.setFetchMode("bankExternalReferenceDetail.bank", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceDetail.bank", "bank", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("bankExternalReferenceDetail.applicationCountry", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceDetail.applicationCountry", "applicationCountryId", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("bankExternalReferenceDetail.bankExternalReferenceHead", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceDetail.bankExternalReferenceHead", "bankExternalReferenceHead", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankExternalReferenceHead.isActive", Constants.U));
		dCriteria.setFetchMode("bankExternalReferenceDetail.beneficaryBank", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceDetail.beneficaryBank", "beneficaryBank", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("bankExternalReferenceDetail.countryId", FetchMode.JOIN);
		dCriteria.createAlias("bankExternalReferenceDetail.countryId", "country", JoinType.INNER_JOIN);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into getAllRecords method");
		return (List<BankExternalReferenceDetail>) findAll(dCriteria);
	}

	@Override
	public List<BankExternalReferenceHead> getAllRecordsfromHead(boolean fetchAll, BigDecimal bankCountryId, BigDecimal bankId) {
		LOGGER.info("Entering into getAllRecordsfromHead method");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankExternalReferenceHead.class, "bankExternalReferenceHead");
		if (bankCountryId != null) {
			dCriteria.setFetchMode("bankExternalReferenceHead.bankCountry", FetchMode.JOIN);
			dCriteria.createAlias("bankExternalReferenceHead.bankCountry", "bankCountry", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("bankCountry.countryId", bankCountryId));
		}
		if (bankId != null) {
			dCriteria.setFetchMode("bankExternalReferenceHead.bank", FetchMode.JOIN);
			dCriteria.createAlias("bankExternalReferenceHead.bank", "bank", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("bank.bankId", bankId));
		}
		if (!fetchAll) {
			dCriteria.add(Restrictions.eq("bankExternalReferenceHead.isActive", Constants.U));
		}
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into getAllRecordsfromHead method");
		return (List<BankExternalReferenceHead>) findAll(dCriteria);
	}

	@Override
	public List<BankApplicability> getBankListbyIndicators(BigDecimal bankCountryId, BigDecimal correspondingbankindicator, BigDecimal serviceproviderbankindicator) {
		LOGGER.info("Entering into getBankListbyIndicators method");
		/* BankApplicability bankApplicabilitybean = null; */
		List<BankApplicability> finalData = new ArrayList<BankApplicability>();
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId", bankCountryId));
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));
		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.disjunction().add(Restrictions.eq("bankInd.bankIndicatorId", correspondingbankindicator)).add(Restrictions.eq("bankInd.bankIndicatorId", serviceproviderbankindicator)));
		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));
		/** NAG CODE APPLY ASCENDING ORDER 05/02/2015 **/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankApplicability> data = (List<BankApplicability>) findAll(dCriteria);
		for (BankApplicability bankApplicability : data) {
			System.out.println(bankApplicability.getBankMaster().getBankId());
			if (!duplicateCheck.contains(bankApplicability.getBankMaster().getBankId())) {
				duplicateCheck.add(bankApplicability.getBankMaster().getBankId());
				finalData.add(bankApplicability);
			}
		}
		System.out.println(duplicateCheck.size());
		System.out.println(duplicateCheck);
		LOGGER.info("Exit into getBankListbyIndicators method");
		return finalData;
	}

	@Override
	public void delete(BankExternalReferenceHead bankExternalReferenceHead, List<BankExternalReferenceDetail> list) {
		LOGGER.info("Entering into delete method");
		for (BankExternalReferenceDetail bankExternalReferenceDetail : list) {
			getSession().delete(bankExternalReferenceDetail);
		}
		getSession().delete(bankExternalReferenceHead);
		LOGGER.info("Exit into delete method");
	}

	@Override
	public void delele(BankExternalReferenceHead bankExternalReferenceHead) {
		LOGGER.info("Entering into delete method");
		getSession().delete(bankExternalReferenceHead);
		LOGGER.info("Exit into delete method");
	}

	@Override
	public String approveRecord(BigDecimal bankExtRefSeqId, String userName) {
		String approveMsg;
		BankExternalReferenceHead external = (BankExternalReferenceHead) getSession().get(BankExternalReferenceHead.class, bankExtRefSeqId);
        String approvedUser=external.getApprovedBy();
        String active=external.getIsActive();
        if(approvedUser==null)
        {
        	external.setIsActive(Constants.Yes);
        	external.setApprovedBy(userName);
        	external.setApprovedDate(new Date());
            getSession().update(external);
            approveMsg="Success";
        }
        else{
        		if(active.equals( Constants.U)){
        			external.setIsActive(Constants.Yes);
                	external.setApprovedBy(userName);
                	external.setApprovedDate(new Date());
                    approveMsg="Success";
        		}
        		    	          
        		else{
        			 approveMsg="Fail";
        		}
        }
        return  approveMsg;
	}

	@Override
	public void approveDetailsRecord(BigDecimal bankExtRefSeqId, String userName) {
		BankExternalReferenceDetail detail = (BankExternalReferenceDetail) getSession().get(BankExternalReferenceDetail.class, bankExtRefSeqId);
		detail.setIsActive(Constants.Yes);
		detail.setApprovedBy(userName);
		detail.setApprovedDate(new Date());
        getSession().update(detail);
	}

        @Override
        public boolean saveAllDetails(HashMap<String, Object> saveMapInfo) {
            try {
        	      BankExternalReferenceHead external = (BankExternalReferenceHead) saveMapInfo.get("BankExternalReferenceHead");
        	      BankExternalReferenceDetail detail = (BankExternalReferenceDetail) saveMapInfo.get("BankExternalReferenceDetail");
        	      getSession().saveOrUpdate(external);
        	      getSession().saveOrUpdate(detail);
            } catch (Exception e) {
        	      throw e;
            }
            return false;
        }

		@Override
		public List<BankBranch> getBankBranchListFromCountryBank(
				BigDecimal countryId, BigDecimal bankId) {

			DetachedCriteria dCriteria = DetachedCriteria.forClass(
					BankBranch.class, "bankBranch");
			dCriteria.setFetchMode("bankBranch.fsCountryMaster", FetchMode.JOIN);
			dCriteria.createAlias("bankBranch.fsCountryMaster", "fsCountryMaster",
					JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
			dCriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
			dCriteria.createAlias("bankBranch.exBankMaster", "exBankMaster",
					JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
			dCriteria.addOrder(Order.asc("bankBranch.branchCode"));
			dCriteria.add(Restrictions.eq("bankBranch.isactive", Constants.Yes));
			dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			
			List<BankBranch>lstBankBranch=(List<BankBranch>) findAll(dCriteria);
			
			return lstBankBranch;
		}
		
		@Override
		public void deactiveRecordToPendingForApproval(BigDecimal bankExtranlDetlId, String userName) {
			BankExternalReferenceDetail bankExternalReferenceDetail = (BankExternalReferenceDetail) getSession().get(BankExternalReferenceDetail.class, bankExtranlDetlId);
			bankExternalReferenceDetail.setModifiedBy(userName);
			bankExternalReferenceDetail.setModifiedDate(new Date());
			bankExternalReferenceDetail.setApprovedBy(null);
			bankExternalReferenceDetail.setApprovedDate(null);
			bankExternalReferenceDetail.setRemarks(null);
			bankExternalReferenceDetail.setIsActive(Constants.U);
			getSession().update(bankExternalReferenceDetail);
			
			BankExternalReferenceHead bankExternalReferenceHead = (BankExternalReferenceHead) getSession().get(BankExternalReferenceHead.class, bankExternalReferenceDetail.getBankExternalReferenceHead().getBankExtRefSeqId());
			bankExternalReferenceHead.setModifiedBy(userName);
			bankExternalReferenceHead.setModifiedDate(new Date());
			bankExternalReferenceHead.setApprovedBy(null);
			bankExternalReferenceHead.setApprovedDate(null);
			bankExternalReferenceHead.setRemarks(null);
			bankExternalReferenceHead.setIsActive(Constants.U);
			getSession().update(bankExternalReferenceHead);
			
		}
		@Override
		public void upDateActiveRecordtoDeActive(BigDecimal bankExtranlDetlId, String remarks, String userName) {
			BankExternalReferenceDetail bankExternalReferenceDetail = (BankExternalReferenceDetail) getSession().get(BankExternalReferenceDetail.class, bankExtranlDetlId);
			bankExternalReferenceDetail.setRemarks(remarks);
			bankExternalReferenceDetail.setModifiedBy(userName);
			bankExternalReferenceDetail.setModifiedDate(new Date());
			bankExternalReferenceDetail.setApprovedBy(null);
			bankExternalReferenceDetail.setApprovedDate(null);
			bankExternalReferenceDetail.setIsActive(Constants.D);
			getSession().update(bankExternalReferenceDetail);
			
		}

		@Override
		public void deleteBranchRecord(BigDecimal branchDetailsPk) {
			BankExternalReferenceDetail   bankExternalDeleteObj=(BankExternalReferenceDetail) getSession().get(BankExternalReferenceDetail.class, branchDetailsPk);
			getSession().delete(bankExternalDeleteObj);	
		 
			
		}

		@Override
		public List<BankExternalReferenceHead> getDuplicateCheckList(
				BigDecimal countryId, BigDecimal bankId,
				BigDecimal appContryId, BigDecimal beneBankId) {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(BankExternalReferenceHead.class, "bankExternalReferenceHead");
			
			dCriteria.setFetchMode("bankExternalReferenceHead.bank", FetchMode.JOIN);
			dCriteria.createAlias("bankExternalReferenceHead.bank", "bank", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("bank.bankId", bankId));
			
			dCriteria.setFetchMode("bankExternalReferenceHead.applicationCountry", FetchMode.JOIN);
			dCriteria.createAlias("bankExternalReferenceHead.applicationCountry", "applicationCountryId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("applicationCountryId.countryId", appContryId));
			
			dCriteria.setFetchMode("bankExternalReferenceHead.bankCountry", FetchMode.JOIN);
			dCriteria.createAlias("bankExternalReferenceHead.bankCountry", "bankCountry", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("bankCountry.countryId", countryId));
			
		 
			dCriteria.setFetchMode("bankExternalReferenceHead.beneficaryBank", FetchMode.JOIN);
			dCriteria.createAlias("bankExternalReferenceHead.beneficaryBank", "beneficaryBank", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("beneficaryBank.bankId", beneBankId));
 
			
			dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			LOGGER.info("Exit into viewBean method");
			return (List<BankExternalReferenceHead>) findAll(dCriteria);
 
		}
		
		@Override
		public HashMap<String, String> callPopulateBankExternalRef(HashMap<String, String> approveRecord)  throws AMGException{
			HashMap<String, String> outputValues = new HashMap<String, String>();
			Connection connection = null;
			String errString = null;
			
			try {
				connection = getDataSourceFromHibernateSession();
				CallableStatement cs;
				String call = " { call EX_BANK_EXTERNAL_REF (?, ?,?,? ) } ";
				cs = connection.prepareCall(call);
				cs.setBigDecimal(1, new BigDecimal(approveRecord.get("APPL_COUNTRY_ID")));
				cs.setBigDecimal(2, new BigDecimal(approveRecord.get("BANK_ID")));
				cs.setBigDecimal(3, new BigDecimal(approveRecord.get("BENE_BANK_ID")));
				cs.registerOutParameter(4, java.sql.Types.VARCHAR);
				cs.execute();
				errString = cs.getString(4);
	 			outputValues.put("P_ERROR_MESSAGE", errString);
	 			
			} catch (SQLException e) {
				String erromsg = "EX_BANK_EXTERNAL_REF" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					String erromsg = "EX_BANK_EXTERNAL_REF" + " : " + e.getMessage();
					throw new AMGException(erromsg);
				}
			}
			
			return outputValues;
		}

		@Override
		public void procErrorToUnApprove(BigDecimal bankExtRefSeqId,List<BigDecimal> list) {
			BankExternalReferenceHead bankExternalReferenceHead=(BankExternalReferenceHead) getSession().get(BankExternalReferenceHead.class, bankExtRefSeqId);
			bankExternalReferenceHead.setIsActive(Constants.U);
			bankExternalReferenceHead.setApprovedBy(null);
			bankExternalReferenceHead.setApprovedDate(null);
			getSession().saveOrUpdate(bankExternalReferenceHead);
			
			for (BigDecimal bankExtRefDetailSeqId : list) {
				BankExternalReferenceDetail   bankExternalDeleteObj=(BankExternalReferenceDetail) getSession().get(BankExternalReferenceDetail.class, bankExtRefDetailSeqId);
				bankExternalDeleteObj.setIsActive(Constants.U);
				bankExternalDeleteObj.setApprovedBy(null);
				bankExternalDeleteObj.setApprovedDate(null);
				getSession().delete(bankExternalDeleteObj);
			}
				
			
		}
		
			@Override
		public List<BankExternalReferenceDetail> findDetailByIdUnApproved(BigDecimal bankExtRefSeqId) {
			LOGGER.info("Entering into findDetailById method");
			DetachedCriteria dCriteria = DetachedCriteria.forClass(BankExternalReferenceDetail.class, "bankExternalReferenceDetail");
			dCriteria.setFetchMode("bankExternalReferenceDetail.bankExternalReferenceHead", FetchMode.JOIN);
			dCriteria.createAlias("bankExternalReferenceDetail.bankExternalReferenceHead", "bankExternalReferenceHead", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("bankExternalReferenceHead.bankExtRefSeqId", bankExtRefSeqId));
			dCriteria.setFetchMode("bankExternalReferenceDetail.bank", FetchMode.JOIN);
			dCriteria.createAlias("bankExternalReferenceDetail.bank", "bank", JoinType.INNER_JOIN);
			dCriteria.setFetchMode("bankExternalReferenceDetail.countryId", FetchMode.JOIN);
			dCriteria.createAlias("bankExternalReferenceDetail.countryId", "country", JoinType.INNER_JOIN);
			dCriteria.setFetchMode("bankExternalReferenceDetail.beneficaryBranch", FetchMode.JOIN);
			dCriteria.createAlias("bankExternalReferenceDetail.beneficaryBranch", "beneficaryBranch", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("bankExternalReferenceDetail.isActive", Constants.U));
			dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			LOGGER.info("Exit into findDetailById method");
			return (List<BankExternalReferenceDetail>) findAll(dCriteria);
		}
}
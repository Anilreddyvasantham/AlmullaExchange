package com.amg.exchange.wu.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CollectDetailWU;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjustWU;
import com.amg.exchange.foreigncurrency.model.TempForeignCurrencyAdjust;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.wu.bean.WUTranxFileUploadDatatable;
import com.amg.exchange.wu.bean.WesternUnionTransferBean;
import com.amg.exchange.wu.dao.IWesternUnionDao;
import com.amg.exchange.wu.model.VoyagerExceptionModel;
import com.amg.exchange.wu.model.WesternUnionTransfer;

@SuppressWarnings("unchecked")
@Repository
public class WesternUnionDaoImpl<T> extends CommonDaoImpl<T> implements IWesternUnionDao {
	Logger LOGGER = Logger.getLogger(WesternUnionTransferBean.class);
	SessionStateManage session = new SessionStateManage();

	@Override
	public String getNextToken() {
		SQLQuery sqlQuery = super.getSession().createSQLQuery("SELECT WU_TOKEN_SEQ.NEXTVAL FROM DUAL");
		return sqlQuery.uniqueResult().toString();
	}

	@Override
	public void saveWesternUnionTransfer(WesternUnionTransfer westernUnionTransfer) {
		getSession().saveOrUpdate(westernUnionTransfer);
	}

	@Override
	public String updateWUTransfer(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo) {
		Connection connection = null;
		String erromsg = null;
		CallableStatement cs;
		LOGGER.info("EX_WU_UPDATE ompanyCode  " + companyCode);
		LOGGER.info("EX_WU_UPDATE documentCode  " + documentCode);
		LOGGER.info("EX_WU_UPDATE documentFinanceYr  " + documentFinanceYr);
		LOGGER.info("EX_WU_UPDATE documentNo  " + documentNo);
		
		
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_WU_UPDATE (?, ?, ?, ?) } ";
			
			// testing purpose.
			//String call = " { call EX_WU_UPDATE_TJ (?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, companyCode);
			cs.setBigDecimal(2, documentCode);
			cs.setBigDecimal(3, documentFinanceYr);
			cs.setBigDecimal(4, documentNo);
			cs.executeUpdate();// teUpdate();
			System.out.println("Success" + cs);
		} catch (SQLException e) {
			erromsg = "EX_WU_UPDATE" + " : " + e.getMessage();
			LOGGER.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_WU_UPDATE" + " : " + e.getMessage();
				try {
					throw new AMGException(erromsg);
				} catch (AMGException e1) {
					e1.printStackTrace();
				}
			}
		}
		return erromsg;
	}

	@Override
	public List<WesternUnionTransfer> getWUData(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal tokenNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(WesternUnionTransfer.class, "wuTransfer");
		criteria.add(Restrictions.eq("wuTransfer.companyCode", companyCode));
		criteria.add(Restrictions.eq("wuTransfer.documentCode", documentCode));
		criteria.add(Restrictions.eq("wuTransfer.documentFinanceYear", documentFinanceYr));
		criteria.add(Restrictions.eq("wuTransfer.documentNo", tokenNo));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<WesternUnionTransfer>) findAll(criteria);
	}

	@Override
	public List<CollectDetail> getCollectionData(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal tokenNo) {
		List<String> list = new ArrayList<String>();
		list.add("CASH");
		list.add("C");
		list.add("Cash");
		list.add("cash");
		DetachedCriteria criteria = DetachedCriteria.forClass(CollectDetail.class, "collectDetail");
		criteria.setFetchMode("collectDetail.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("collectDetail.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("collectDetail.exCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("collectDetail.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCompanyMaster.companyCode", companyCode));
		criteria.add(Restrictions.eq("collectDetail.documentCode", documentCode));
		criteria.add(Restrictions.eq("collectDetail.documentFinanceYear", documentFinanceYr));
		criteria.add(Restrictions.eq("collectDetail.documentNo", tokenNo));
		criteria.add(Restrictions.in("collectDetail.collectionMode", list));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CollectDetail>) findAll(criteria);
	}

	@Override
	public List<CollectDetailWU> getCollectionDataWU(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal tokenNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CollectDetailWU.class, "collectDetail");
		criteria.add(Restrictions.eq("collectDetail.companyCode", companyCode));
		criteria.add(Restrictions.eq("collectDetail.documentCode", documentCode));
		criteria.add(Restrictions.eq("collectDetail.documentFinanceYear", documentFinanceYr));
		criteria.add(Restrictions.eq("collectDetail.documentNo", tokenNo));
		// criteria.add(Restrictions.in("collectDetail.collectionMode", list));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CollectDetailWU>) findAll(criteria);
	}

	@Override
	public String transferToOldSystem(BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo) {
		Connection connection = null;
		String erromsg = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_TRF_WU_CURRENCY_ADJUST (?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, companyId);
			cs.setBigDecimal(2, documentCode);
			cs.setBigDecimal(3, documentFinanceYr);
			cs.setBigDecimal(4, documentNo);
			cs.execute();
		} catch (SQLException e) {
			erromsg = "EX_P_TRF_WU_CURRENCY_ADJUST" + " : " + e.getMessage();
			LOGGER.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_P_TRF_WU_CURRENCY_ADJUST" + " : " + e.getMessage();
				try {
					throw new AMGException(erromsg);
				} catch (AMGException e1) {
					e1.printStackTrace();
				}
			}
		}
		return erromsg;
	}

	@Override
	public List<WesternUnionTransfer> getWUDataByMtcNo(String mtcNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(WesternUnionTransfer.class, "wuTransfer");
		criteria.add(Restrictions.eq("wuTransfer.mtcNo", mtcNo));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<WesternUnionTransfer>) findAll(criteria);
	}

	@Override
	public List<RemittanceTransaction> getRemittanceDataByMtcNo(String mtcNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");
		criteria.add(Restrictions.eq("remittanceTransaction.westernUnionMtcno", mtcNo));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RemittanceTransaction>) findAll(criteria);
	}

	@Override
	public void saveForeignCurrencyAdjust(List<ForeignCurrencyAdjustWU> foreignCurrencyAdjust) {
		
		for (ForeignCurrencyAdjustWU foreignCurrencyAdjustWU : foreignCurrencyAdjust) {
			getSession().save(foreignCurrencyAdjustWU);
		}
				
	}

	@Override
	public List<BeneficaryRelationship> getBeniRelationship(BigDecimal relationId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");
		criteria.add(Restrictions.eq("beneficaryRelationship.beneficaryRelationshipId", relationId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BeneficaryRelationship>) findAll(criteria);
	}

	@Override
	public List<WesternUnionTransfer> getWUDataReceive(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, String mtcno,String trnxType) {
		String[] sendOrReceive={"S","R"};
		DetachedCriteria criteria = DetachedCriteria.forClass(WesternUnionTransfer.class, "wuTransfer");
		criteria.add(Restrictions.eq("wuTransfer.companyCode", companyCode));
		criteria.add(Restrictions.eq("wuTransfer.documentCode", documentCode));
		criteria.add(Restrictions.eq("wuTransfer.documentFinanceYear", documentFinanceYr));
		criteria.add(Restrictions.eq("wuTransfer.mtcNo", mtcno));
		criteria.add(Restrictions.eq("wuTransfer.inorOut", trnxType));
		//criteria.add(Restrictions.in("wuTransfer.inorOut", sendOrReceive));
		criteria.add(Restrictions.eq("wuTransfer.javaTransaction", "Y"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<WesternUnionTransfer>) findAll(criteria);
	}

	@Override
	public BigDecimal getCollectionAmount(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo) {
		Connection connection = null;
		String erromsg = null;
		BigDecimal out = null;
		CallableStatement cs;
		try {
			
			LOGGER.info("EX_F_WU_NET_AMOUNT   companyCode " + companyCode);
			LOGGER.info("EX_F_WU_NET_AMOUNT  documentCode " + documentCode);
			LOGGER.info("EX_F_WU_NET_AMOUNT  documentFinanceYr " + documentFinanceYr);
			LOGGER.info("EX_F_WU_NET_AMOUNT  documentNo " + documentNo);
			
			
			
			connection = getDataSourceFromHibernateSession();
			String call = " { ? = call EX_F_WU_NET_AMOUNT (?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.registerOutParameter(1, Types.FLOAT);
			cs.setBigDecimal(2, companyCode);
			cs.setBigDecimal(3, documentCode);
			cs.setBigDecimal(4, documentFinanceYr);
			cs.setBigDecimal(5, documentNo);
			cs.execute();
			out = cs.getBigDecimal(1);
			System.out.println("SUCCESS==========" + out);
		} catch (SQLException e) {
			erromsg = "EX_F_WU_NET_AMOUNT" + " : " + e.getMessage();
			LOGGER.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_F_WU_NET_AMOUNT" + " : " + e.getMessage();
				try {
					throw new AMGException(erromsg);
				} catch (AMGException e1) {
					e1.printStackTrace();
				}
			}
		}
		return out;
	}

	@Override
	public List<BenificiaryListView> getBeneficaryList(BigDecimal customerNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BenificiaryListView.class, "benificiaryListView");
		criteria.add(Restrictions.eq("benificiaryListView.customerId", customerNo));
		criteria.add(Restrictions.eq("benificiaryListView.bankCode", Constants.WU_BANK_CODE));
		criteria.addOrder(Order.desc("benificiaryListView.createdDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BenificiaryListView>) findAll(criteria);
	}

	@Override
	public BankMaster getWUBank(String bankCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		criteria.add(Restrictions.eq("bankMaster.bankCode", bankCode));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<BankMaster> bankMaster = (List<BankMaster>) findAll(criteria);
		if (null != bankMaster) {
			if (!bankMaster.isEmpty()) {
				return bankMaster.get(0);
			}
		}
		return null;
	}

	@Override
	public void saveBeneficary(BeneficaryAccount beneficaryAccount, BeneficaryRelationship relationship) {
		LOGGER.info("Entered into saveBeneficary Method");
		if (beneficaryAccount != null) {
			getSession().save(beneficaryAccount);
		}
		if (relationship != null) {
			if (beneficaryAccount != null) {
				relationship.setBeneficaryAccount(beneficaryAccount);
			}
			getSession().saveOrUpdate(relationship);
		}
		LOGGER.info("EXIT into saveBeneficary Method");
	}

	@Override
	public List<BenificiaryListView> fetchAllBeneficiaryCountrys() {
		DetachedCriteria criteria = DetachedCriteria.forClass(BenificiaryListView.class, "benificiaryListView");
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<BenificiaryListView> benificiaryList = (List<BenificiaryListView>) findAll(criteria);
		return benificiaryList;
	}

	@Override
	public List<BeneficaryAccount> getBeneficaryAccountDetails(BigDecimal beneficiaryMasterSeqId,String bankCode,BigDecimal bankCountryId,BigDecimal currencyId) {
		LOGGER.info("Entered into getBeneficaryAccountDetails(BigDecimal beneficiaryMasterSeqId) Method ");
		LOGGER.info("beneficiaryMasterSeqId ="+beneficiaryMasterSeqId); 
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		// masterSeqId
		criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficiaryMasterSeqId));
		// bankCode
		criteria.add(Restrictions.eq("beneficaryAccount.bankCode", bankCode));
		// bank Country Id
		criteria.setFetchMode("beneficaryAccount.beneficaryCountry", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryCountry", "beneficaryCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryCountry.countryId", bankCountryId));
		// bank Currency Id
		criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.currencyId", "currencyId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("currencyId.currencyId", currencyId));
		
		criteria.add(Restrictions.eq("beneficaryAccount.isActive", Constants.Yes));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryAccount> acclist = (List<BeneficaryAccount>) findAll(criteria);
		LOGGER.info("Exited from the getBeneficaryAccountDetails(BigDecimal beneficiaryMasterSeqId) Method");
		return acclist;
	}

	@Override
	public List<BeneficaryRelationship> fetchBeneficiaryRelationShip(BigDecimal beneficiaryMasterSeqId,BigDecimal beneficiaryAccSeqId,BigDecimal customerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");

		criteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficiaryMasterSeqId));

		criteria.setFetchMode("beneficaryRelationship.customerId", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.customerId", "customer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("customer.customerId", customerId));

		criteria.setFetchMode("beneficaryRelationship.beneficaryAccount", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryAccount", "beneficaryAccount", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryAccount.beneficaryAccountSeqId", beneficiaryAccSeqId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BeneficaryRelationship>) findAll(criteria);
	}

	@Override
	public Boolean checkBeneficaryAccountDetailsForWUnion(BigDecimal bankCountry, BigDecimal bankId, BigDecimal bankBranchId,BigDecimal currency, BigDecimal beneMasterSeqId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");

		criteria.setFetchMode("beneficaryAccount.beneficaryCountry", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryCountry", "beneficaryCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryCountry.countryId", bankCountry));

		criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.bank", "exBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		criteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.bankBranch", "exBankBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankBranch.bankBranchId", bankBranchId));

		criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.currencyId", "currency", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("currency.currencyId", currency));

		criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneMasterSeqId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryAccount> accountNumberList = (List<BeneficaryAccount>) findAll(criteria);

		if(accountNumberList.size() != 0){
			return true;
		}else{
			return false;
		}


	}
	@Override
	public List<WesternUnionTransfer> getWUPendingTransactionList(
			Date createDate, BigDecimal branchId, String userName) {

		/*DetachedCriteria criteria = DetachedCriteria.forClass(WesternUnionTransfer.class, "westernUnionTransfer");


		criteria.add(Restrictions.eq("westernUnionTransfer.locationCode", branchId));
		if(userName!=null){
			  criteria.add(Restrictions.eq("westernUnionTransfer.createdBy", userName));
		}

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);*/
		String locationCode=branchId.toPlainString();

		String hql = "from WesternUnionTransfer as westernUnionTransfer where trunc(westernUnionTransfer.createdDate) = trunc(:pcreateDate) "
				+ " and westernUnionTransfer.locationCode = :plocationCode"
				+ " and westernUnionTransfer.createdBy= :puserName";
		Query query = getSession().createQuery(hql); 

		query.setParameter("pcreateDate", createDate);
		query.setParameter("plocationCode", locationCode); 
		query.setParameter("puserName", userName);
		List<WesternUnionTransfer> lstWesternUnionTransfer = query.list();


		return lstWesternUnionTransfer;
	}
	@Override
	public List<WesternUnionTransfer> getWUTransactionWithOutDenomination(String username,String location,BigDecimal customerRef)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(WesternUnionTransfer.class, "westernUnionTransfer");

		// created by and modified by can be changed 
		criteria.add(Restrictions.eq("westernUnionTransfer.createdBy", username)); // login Username
		
		//criteria.add(Restrictions.eq("westernUnionTransfer.wuId", session.getWUUsername())); // western Username
		criteria.add(Restrictions.eq("westernUnionTransfer.locationCode", location));
		criteria.add(Restrictions.isNull("westernUnionTransfer.exDocumentNo"));
		criteria.add(Restrictions.isNotNull("westernUnionTransfer.wuMTCNo"));
		criteria.add(Restrictions.eq("westernUnionTransfer.javaTransaction", Constants.Yes));
		criteria.add(Restrictions.isNotNull("westernUnionTransfer.sendAmount"));

		//criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<WesternUnionTransfer> lstWesternUnionTransfer = (List<WesternUnionTransfer>) findAll(criteria);

		return lstWesternUnionTransfer;

	}

	public void getFindWUTransxWithCash()
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CollectDetailWU.class, "collectDetailWU");

		criteria.add(Restrictions.isNull("collectDetailWU.exDocumentNo"));
		criteria.add(Restrictions.isNull("collectDetailWU.exDocumentNo"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CollectDetailWU> lstCollectDetailWU=(List<CollectDetailWU>) findAll(criteria);
	}



	@Override
	public List<WesternUnionTransfer> getWUTransactionWithOutDenominationlist()
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(WesternUnionTransfer.class, "westernUnionTransfer");

		criteria.add(Restrictions.isNull("westernUnionTransfer.exDocumentNo"));

		criteria.add(Restrictions.isNotNull("westernUnionTransfer.wuMTCNo"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<WesternUnionTransfer> lstWesternUnionTransfer=(List<WesternUnionTransfer>) findAll(criteria);

		return lstWesternUnionTransfer;

	}

	@Override
	public List<Customer> getCustomerDetail(BigDecimal customerId){

		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class,
				"fscustomer");

		criteria.add(Restrictions.eq("fscustomer.customerId", customerId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Customer> customerList = (List<Customer>) findAll(criteria);

		if (null != customerList) {
			if (!customerList.isEmpty()) {
				return customerList;
			}
		}
		return null;

	}

	@Override
	public List<BeneficaryMaster> fetchBeneMasterRecForWU(Map<String, Object> mapBeneMasterCheck) {

		String firstName = (String) mapBeneMasterCheck.get("FirstName");
		String secondName = (String) mapBeneMasterCheck.get("SecondName");
		String thirdName = (String) mapBeneMasterCheck.get("ThirdName");
		String fourthName = (String) mapBeneMasterCheck.get("FourthName");
		String fifthName = (String) mapBeneMasterCheck.get("FifthName");

		String firstLName = (String) mapBeneMasterCheck.get("FirstLName");
		String secondLName = (String) mapBeneMasterCheck.get("SecondLName");
		String thirdLName = (String) mapBeneMasterCheck.get("ThirdLName");
		String fourthLName = (String) mapBeneMasterCheck.get("FourthLName");
		BigDecimal beneCountryId = (BigDecimal) mapBeneMasterCheck.get("BeneCountryId");
		
		BigDecimal customerId = (BigDecimal) mapBeneMasterCheck.get("customerId");
		

		List<BeneficaryMaster> beneMaster =null;
		
		//DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryMaster.class, "beneficaryMaster");
		
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");
		
		

		criteria.setFetchMode("beneficaryRelationship.customerId", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.customerId", "customer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("customer.customerId", customerId));
		
		criteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		//criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficiaryMasterSeqId));

		if(firstName != null && !firstName.equalsIgnoreCase("")){
			criteria.add(Restrictions.eq("beneficaryMaster.firstName", firstName));
		}
		if(secondName != null && !secondName.equalsIgnoreCase("")){
			criteria.add(Restrictions.eq("beneficaryMaster.secondName", secondName));
		}
		if(thirdName != null && !thirdName.equalsIgnoreCase("")){
			criteria.add(Restrictions.eq("beneficaryMaster.thirdName", thirdName));
		}
		if(fourthName != null && !fourthName.equalsIgnoreCase("")){
			criteria.add(Restrictions.eq("beneficaryMaster.fourthName", fourthName));
		}
		if(fifthName != null && !fifthName.equalsIgnoreCase("")){
			criteria.add(Restrictions.eq("beneficaryMaster.fifthName", fifthName));
		}
		if(firstLName != null && !firstLName.equalsIgnoreCase("")){
			criteria.add(Restrictions.eq("beneficaryMaster.localFirstName", firstLName));
		}
		if(secondLName != null && !secondLName.equalsIgnoreCase("")){
			criteria.add(Restrictions.eq("beneficaryMaster.localSecondName", secondLName));
		}
		if(thirdLName != null && !thirdLName.equalsIgnoreCase("")){
			criteria.add(Restrictions.eq("beneficaryMaster.localThirdName", thirdLName));
		}
		if(fourthLName != null && !fourthLName.equalsIgnoreCase("")){
			criteria.add(Restrictions.eq("beneficaryMaster.localFourthName", fourthLName));
		}
		if(beneCountryId != null){
			criteria.setFetchMode("beneficaryMaster.fsCountryMaster", FetchMode.JOIN);
			criteria.createAlias("beneficaryMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("fsCountryMaster.countryId", beneCountryId));
		}
			
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryRelationship> beneRelationMaster = (List<BeneficaryRelationship>) findAll(criteria);
		
		if(beneRelationMaster!=null && !beneRelationMaster.isEmpty()){
			
			DetachedCriteria criteria1 = DetachedCriteria.forClass(BeneficaryMaster.class, "beneficaryMaster");
			criteria1.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneRelationMaster.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId()));
			
			criteria1.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			beneMaster = (List<BeneficaryMaster>) findAll(criteria1);
		}
		
				

		

		return beneMaster;
	}

	@Override
	public List<WesternUnionTransfer> fetchWesternUnionTransfer(Map<String, String> lstWuTransfer) {

		BigDecimal companyCode = new BigDecimal(lstWuTransfer.get("Company_Code"));
		BigDecimal documentCode = new BigDecimal(lstWuTransfer.get("Documnent_Code"));
		BigDecimal documentYear = new BigDecimal(lstWuTransfer.get("Documnent_Year"));
		String mtcNumber = lstWuTransfer.get("MTC_Number");
		String inorOut = lstWuTransfer.get("INorOUT");
		
		DetachedCriteria criteria = DetachedCriteria.forClass(WesternUnionTransfer.class, "westernUnionTransfer");
		
		criteria.add(Restrictions.eq("westernUnionTransfer.companyCode", companyCode));
		
		criteria.add(Restrictions.eq("westernUnionTransfer.documentCode", documentCode));
		
		criteria.add(Restrictions.eq("westernUnionTransfer.documentFinanceYear", documentYear));
		
		criteria.add(Restrictions.eq("westernUnionTransfer.wuMTCNo", mtcNumber));
		
		criteria.add(Restrictions.eq("westernUnionTransfer.inorOut", inorOut));
		
		criteria.addOrder(Order.desc("westernUnionTransfer.createdDate"));
		
		criteria.add(Restrictions.eq("westernUnionTransfer.javaTransaction", Constants.Yes));
		
		// criteria.add(Restrictions.isNotNull("westernUnionTransfer.sendAmount"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<WesternUnionTransfer> lstWesternUnionTransfer = (List<WesternUnionTransfer>) findAll(criteria);
		
		return lstWesternUnionTransfer;
	}

	@Override
	public List<WesternUnionTransfer> getAllWUException(Date createDate,String branchCode, String userName) {

		String hql = "Select * from EX_WU_MONEYTRANSFER WHERE EX_DOCNO IS NULL AND (COMCOD,DOCCOD,DOCFYR,DOCNO) IN (SELECT COMCOD,DOCCOD,DOCFYR,DOCNO FROM EX_VOYAGER_EXCEPTION) Order by DOCNO asc";
		Query query = getSession().createSQLQuery(hql).addEntity(WesternUnionTransfer.class);
		List<WesternUnionTransfer> lstWesternUnionTransfer = query.list();
		
		return lstWesternUnionTransfer;
	}

	@Override
	public List<VoyagerExceptionModel> getAllWUExceptionErrorMsg(WesternUnionTransfer lstWURec) {

		WesternUnionTransfer lstWURecDetails = lstWURec;

		List<VoyagerExceptionModel> lstAllVoyagerExceptionModel = new ArrayList<VoyagerExceptionModel>();

		String hql = "SELECT * FROM EX_VOYAGER_EXCEPTION where Comcod = "+lstWURecDetails.getCompanyCode()+" "
				+ " and Doccod= "+lstWURecDetails.getDocumentCode()+" and Docfyr = "+lstWURecDetails.getDocumentFinanceYear()+" "
				+ " and Docno ="+lstWURecDetails.getDocumentNo()+" Order by CRTDAT desc";
		Query query = getSession().createSQLQuery(hql);
		List<VoyagerExceptionModel> lstVoyagerExceptionModel = query.list();

		if(lstVoyagerExceptionModel != null && !lstVoyagerExceptionModel.isEmpty()){
			lstAllVoyagerExceptionModel.addAll(lstVoyagerExceptionModel);
		}
		
		return lstAllVoyagerExceptionModel;
	}

	@Override
	public String updateWUTransferForApprovalException(BigDecimal companyCode,BigDecimal documentCode, BigDecimal documentFinanceYr,BigDecimal documentNo) {
		Connection connection = null;
		String erromsg = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_WU_UPDATE_NEW (?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, companyCode);
			cs.setBigDecimal(2, documentCode);
			cs.setBigDecimal(3, documentFinanceYr);
			cs.setBigDecimal(4, documentNo);
			cs.executeUpdate();// teUpdate();
			System.out.println("Success" + cs);
		} catch (SQLException e) {
			erromsg = "EX_WU_UPDATE_NEW" + " : " + e.getMessage();
			LOGGER.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_WU_UPDATE_NEW" + " : " + e.getMessage();
				try {
					throw new AMGException(erromsg);
				} catch (AMGException e1) {
					e1.printStackTrace();
				}
			}
		}
		return erromsg;
	}

	@Override
	public void deleteFromExTempCurrencyAdjust(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo,
			String mtcNo) {
		
		LOGGER.info("deleteFromExTempCurrencyAdjust  calling  companyCode" + companyCode);
		LOGGER.info("deleteFromExTempCurrencyAdjust  calling  documentCode" + documentCode);
		LOGGER.info("deleteFromExTempCurrencyAdjust  calling  documentFinanceYr" + documentFinanceYr);
		LOGGER.info("deleteFromExTempCurrencyAdjust  calling  documentNo" + documentNo);
		LOGGER.info("deleteFromExTempCurrencyAdjust  calling  mtcNo" + mtcNo);
		
	    DetachedCriteria criteria = DetachedCriteria.forClass(TempForeignCurrencyAdjust.class, "tempCurrencyAdjust");
		//criteria.add(Restrictions.eq("tempCurrencyAdjust.companyCode", companyCode));
		criteria.add(Restrictions.eq("tempCurrencyAdjust.documentCode", documentCode));
		criteria.add(Restrictions.eq("tempCurrencyAdjust.documentFinanceYear", documentFinanceYr));
		criteria.add(Restrictions.eq("tempCurrencyAdjust.westernUnionMicNo", mtcNo));
		//Added by Rabil on 13112016
		criteria.setFetchMode("tempCurrencyAdjust.tempCollection", FetchMode.JOIN);
		criteria.createAlias("tempCurrencyAdjust.tempCollection", "tempCollection", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("tempCollection.collectionId", documentNo));
		
		List<TempForeignCurrencyAdjust> deleteList = (List<TempForeignCurrencyAdjust>) findAll(criteria);
		
		for (TempForeignCurrencyAdjust tempForeignCurrencyAdjust : deleteList) {
			getSession().delete(tempForeignCurrencyAdjust);
		}
		
		
	}

	@Override
	public List<BeneficaryRelationship> fetchBeneficiaryRelationShipForWU(BigDecimal beneficiaryMasterSeqId, String bankCode,BigDecimal customerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");

		criteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficiaryMasterSeqId));

		criteria.setFetchMode("beneficaryRelationship.customerId", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.customerId", "customer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("customer.customerId", customerId));

		criteria.setFetchMode("beneficaryRelationship.beneficaryAccount", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryAccount", "beneficaryAccount", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryAccount.bankCode", bankCode));
		
		criteria.add(Restrictions.eq("beneficaryRelationship.isActive", Constants.Yes));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BeneficaryRelationship>) findAll(criteria);
	}

	@Override
	public List<WesternUnionTransfer> getWUTransactionDenomination(String location, BigDecimal operationId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(WesternUnionTransfer.class, "westernUnionTransfer");

		// created by and modified by can be changed
		criteria.add(Restrictions.eq("westernUnionTransfer.locationCode", location));
		//criteria.add(Restrictions.eq("westernUnionTransfer.wuId", operationId));
		//criteria.add(Restrictions.isNull("westernUnionTransfer.exDocumentNo"));
		criteria.add(Restrictions.isNotNull("westernUnionTransfer.wuMTCNo"));
		criteria.add(Restrictions.eq("westernUnionTransfer.javaTransaction", Constants.Yes));
		criteria.add(Restrictions.isNotNull("westernUnionTransfer.sendAmount"));

		//criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<WesternUnionTransfer> lstWesternUnionTransfer = (List<WesternUnionTransfer>) findAll(criteria);

		return lstWesternUnionTransfer;
	}

	@Override
	public List<WUTranxFileUploadDatatable> checkWuMoneyTransferMTCNO(List<WUTranxFileUploadDatatable> lstWUTranxFileUploadData,BigDecimal docyear) {
		
		List<WUTranxFileUploadDatatable> lstfinalData = new ArrayList<WUTranxFileUploadDatatable>();
		String sendOrReceivep;
		
		if(lstWUTranxFileUploadData != null && lstWUTranxFileUploadData.size() != 0){
			
			for (WUTranxFileUploadDatatable wuTranxFileUploadDatatable : lstWUTranxFileUploadData) {
				
				if(wuTranxFileUploadDatatable.getMtcNo() != null){
					
					DetachedCriteria criteria = DetachedCriteria.forClass(WesternUnionTransfer.class, "wuTransfer");
					criteria.add(Restrictions.eq("wuTransfer.mtcNo", wuTranxFileUploadDatatable.getMtcNo()));
					criteria.add(Restrictions.eq("wuTransfer.inorOut", wuTranxFileUploadDatatable.getInorOut()));
					criteria.add(Restrictions.eq("wuTransfer.documentFinanceYear", docyear));
					criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
					
					List<WesternUnionTransfer> lstWesternUnionTransfer = (List<WesternUnionTransfer>) findAll(criteria);
					
					if(lstWesternUnionTransfer.size() != 0){
						// no need to add
					}else{
						lstfinalData.add(wuTranxFileUploadDatatable);
					}
				}
			}
		}
		
		return lstfinalData;
	}

	@Override
	public List<WesternUnionTransfer> getWUDataByMtcNo(BigDecimal documentFinanceYr, String mtcNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(WesternUnionTransfer.class, "wuTransfer");
		criteria.add(Restrictions.eq("wuTransfer.documentFinanceYear", documentFinanceYr));
		criteria.add(Restrictions.eq("wuTransfer.mtcNo", mtcNo));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<WesternUnionTransfer>) findAll(criteria);
	}

}

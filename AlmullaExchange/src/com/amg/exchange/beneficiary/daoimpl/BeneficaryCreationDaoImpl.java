package com.amg.exchange.beneficiary.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.beneficiary.dao.BeneficaryCreationDao;
import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.beneficiary.model.BanksView;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.remittance.model.AccountTypeFromView;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryContact;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.RoutingDetails;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Repository
@SuppressWarnings("unchecked")
public class BeneficaryCreationDaoImpl<T> extends CommonDaoImpl<T> implements BeneficaryCreationDao {

	private static final Logger LOG = Logger.getLogger(BeneficaryCreationDaoImpl.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Override
	public List<CountryMasterDesc> checkCountryCode(String countryTelCode) {
		LOG.info("Entering into checkCountryCode method");
		LOG.info("countryTelCode" + countryTelCode);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
		detachedCriteria.add(Restrictions.eq("countryMaster.countryTelCode", countryTelCode));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getCountryList method");
		return (List<CountryMasterDesc>) findAll(detachedCriteria);
	}

	@Override
	public List<BeneficaryRelationship> isBenificaryRelationExist(BigDecimal beneficaryMasterSeqId, BigDecimal relationId) {
		LOG.info("Entering into isBenificaryRelationExist method");
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");
		criteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficaryMasterSeqId));
		criteria.setFetchMode("beneficaryRelationship.beneficaryAccount", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.relations", "relations", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("relations.relationsId", relationId));
		criteria.add(Restrictions.eq("beneficaryMaster.isActive", Constants.Yes));
		List<BeneficaryRelationship> beneficaryRship = (List<BeneficaryRelationship>) findAll(criteria);
		System.out.println("coming ------------- > success");
		return beneficaryRship;
	}

	@Override
	public BigDecimal checkMasterSequenceExist(BigDecimal customerNo, BigDecimal relationId) {
		LOG.info("Entering into isBenificaryRelationExist method");
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");
		criteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryRelationship.customerId", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.customerId", "customerId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("customerId.customerId", customerNo));
		criteria.setFetchMode("beneficaryRelationship.beneficaryAccount", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.relations", "relations", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("relations.relationsId", relationId));
		criteria.add(Restrictions.eq("beneficaryMaster.isActive", Constants.Yes));
		List<BeneficaryRelationship> beneficaryRship = (List<BeneficaryRelationship>) findAll(criteria);
		if (beneficaryRship != null && !beneficaryRship.isEmpty()) {
			return beneficaryRship.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId();
		}
		return null;
	}

	@Override
	public List<BenificiaryListView> getBeneficaryList(BigDecimal customerNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BenificiaryListView.class, "benificiaryListView");
		criteria.add(Restrictions.eq("benificiaryListView.customerId", customerNo));
		criteria.addOrder(Order.desc("benificiaryListView.createdDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BenificiaryListView>) findAll(criteria);
	}

	@Override
	public BigDecimal getTelePhoneNumber(BigDecimal beneficaryMasterSeqId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "beneficaryContact");
		criteria.setFetchMode("beneficaryContact.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryContact.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficaryMasterSeqId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryContact> beneficaryContact = (List<BeneficaryContact>) findAll(criteria);
		if (beneficaryContact != null && !beneficaryContact.isEmpty()) {
			if (beneficaryContact.get(0).getTelephoneNumber() != null) {
				return new BigDecimal(beneficaryContact.get(0).getTelephoneNumber());
			} else if (beneficaryContact.get(0).getMobileNumber() != null) {
				return beneficaryContact.get(0).getMobileNumber();
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public Boolean checkSelfAlreadyExistorNot(BigDecimal customerNo, BigDecimal relationsId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");
		criteria.setFetchMode("beneficaryRelationship.customerId", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.customerId", "cus", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("cus.customerId", customerNo));
		criteria.setFetchMode("beneficaryRelationship.relations", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.relations", "res", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("res.relationsId", relationsId));
		List<BeneficaryRelationship> relatioList = (List<BeneficaryRelationship>) findAll(criteria);
		if (relatioList != null && !relatioList.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public List<BeneficaryAccount> isBankAccountNumberExist(String bankAccountNumber, BigDecimal benifisBankId, BigDecimal benifisCurrencyId, BigDecimal benifisCountryId, BigDecimal servicebankBranchId) {
		LOG.info("Entering into isBankAccountNumberExist method ");
		LOG.info("bankAccountNumber " + bankAccountNumber);
		LOG.info("benifisCurrencyId " + benifisCurrencyId);
		LOG.info("benifisCountryId " + benifisCountryId);
		LOG.info("servicebankBranchId " + servicebankBranchId);
		LOG.info("benifisBankId " + benifisBankId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		criteria.add(Restrictions.eq("beneficaryAccount.bankAccountNumber", bankAccountNumber));
		criteria.setFetchMode("beneficaryAccount.beneficaryCountry", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryCountry", "beneficaryCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryCountry.countryId", benifisCountryId));
		criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.bank", "exBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", benifisBankId));
		criteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.bankBranch", "exBankBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankBranch.bankBranchId", servicebankBranchId));
		criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.currencyId", "currency", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("currency.currencyId", benifisCurrencyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryAccount> accountNumberList = (List<BeneficaryAccount>) findAll(criteria);
		return accountNumberList;
	}

	@Override
	public List<BeneficaryAccount> isBankAccountNumberExist(BigDecimal benificaryMasterId, String accountNo, String type) {
		LOG.info("Entering into getCustomerBeneficaryDetailswithAll method");
		LOG.info("masterseqId " + benificaryMasterId);
		LOG.info("type " + type);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", benificaryMasterId));
		criteria.add(Restrictions.isNull("beneficaryAccount.bankAccountNumber"));
		criteria.setFetchMode("beneficaryAccount.servicegropupId", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.servicegropupId", "exServiceId", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.currencyId", "currencyId", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.beneApplicationCountry", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneApplicationCountry", "appcountry", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.beneficaryCountry", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryCountry", "benecountry", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.bank", "bank", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.bankBranch", "bankBranch", JoinType.INNER_JOIN);
		if (!type.equals("") && type.equals("cash")) {
			criteria.setFetchMode("beneficaryAccount.serviceProvider", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.serviceProvider", "serviceProvider", JoinType.INNER_JOIN);
		}
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryAccount> acclist = (List<BeneficaryAccount>) findAll(criteria);
		LOG.info("Exit into getCustomerBeneficaryDetailswithAll method");
		return acclist;
	}

	@Override
	public List<BankAccountTypeDesc> getBankAccountType(BigDecimal languageId) {
		LOG.info("Entering into getBankAccountType method");
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountTypeDesc.class, "bankAccountTypeDesc");
		criteria.setFetchMode("bankAccountTypeDesc.bankAccountTypeId", FetchMode.JOIN);
		criteria.createAlias("bankAccountTypeDesc.bankAccountTypeId", "bankAccountTypeId", JoinType.INNER_JOIN);
		criteria.setFetchMode("bankAccountTypeDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("bankAccountTypeDesc.languageId", "language", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("language.languageId", languageId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBankAccountType method");
		return (List<BankAccountTypeDesc>) findAll(criteria);
	}

	@Override
	public List<BankBranch> getBranchList(BigDecimal benifisBankId) {


		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		criteria.add(Restrictions.eq("exBankMaster.bankId", benifisBankId));
		criteria.addOrder(Order.asc("bankBranch.branchFullName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankBranch>) findAll(criteria);

	}

	@Override
	public BankBranch getBranchDetails(BigDecimal bankBranchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		criteria.add(Restrictions.eq("bankBranch.bankBranchId", bankBranchId));
		criteria.setFetchMode("bankBranch.fsStateMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.fsStateMaster", "state", JoinType.INNER_JOIN);
		criteria.setFetchMode("bankBranch.fsCityMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.fsCityMaster", "city", JoinType.INNER_JOIN);
		criteria.setFetchMode("bankBranch.fsDistrictMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.fsDistrictMaster", "district", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankBranch> list = (List<BankBranch>) findAll(criteria);

		if(!list.isEmpty())
		{
			return list.get(0);
		}

		return null;
	}

	@Override
	public List<BeneficaryAccount> getCustomerBeneficaryDetailswithAccountNO(BigDecimal benificaryMasterId, String accountNo, String type) {
		LOG.info("Entering into getCustomerBeneficaryDetailswithAccountNO method");
		LOG.info("masterseqId " + benificaryMasterId);
		LOG.info("type " + type);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", benificaryMasterId));
		if(accountNo != null){
			criteria.add(Restrictions.eq("beneficaryAccount.bankAccountNumber", accountNo));
		}
		criteria.setFetchMode("beneficaryAccount.servicegropupId", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.servicegropupId", "exServiceId", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.currencyId", "currencyId", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.beneApplicationCountry", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneApplicationCountry", "appcountry", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.beneficaryCountry", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryCountry", "benecountry", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.bank", "bank", JoinType.INNER_JOIN);
		//criteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
		//criteria.createAlias("beneficaryAccount.bankBranch", "bankBranch", JoinType.INNER_JOIN);
		/*if (!type.equals("") && type.equals("cash")) {
			criteria.setFetchMode("beneficaryAccount.serviceProvider", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.serviceProvider", "serviceProvider", JoinType.INNER_JOIN);
		}*/
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryAccount> acclist = (List<BeneficaryAccount>) findAll(criteria);
		LOG.info("Exit into getCustomerBeneficaryDetailswithAll method");
		return acclist;
	}

	@Override
	public String getBankBranchName(BigDecimal bankId, BigDecimal bankBranchId,BigDecimal countryId) {

		LOG.info("Entering into getBankBranchName method");
		LOG.info("bankId " + bankId);
		LOG.info("bankBranchId " + bankBranchId);

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		dCriteria.setFetchMode("bankBranch.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		dCriteria.add(Restrictions.eq("bankBranch.bankBranchId", bankBranchId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankBranch> acclist = (List<BankBranch>) findAll(dCriteria);

		if(acclist !=null &&!acclist.isEmpty())
		{
			return acclist.get(0).getBranchFullName();
		}

		LOG.info("Exit into getBankBranchName method");
		return null;
	}

	/*@Override
	public List<BankBranchView> getBranchListfromView(BigDecimal benifisBankId) {
		LOG.info("Entering into getBranchListfromView method");
		LOG.info("benifisBankId " + benifisBankId);

		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranchView.class, "bankBranchView");
		criteria.add(Restrictions.eq("bankBranchView.bankId", benifisBankId));
		criteria.addOrder(Order.asc("bankBranchView.branchFullName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBranchListfromView method");
		return (List<BankBranchView>) findAll(criteria);
	}*/


	@Override
	public BankBranchView getBranchListfromView(BigDecimal benifisBankId, BigDecimal bankBranchId) {
		LOG.info("Entering into getBranchListfromView method");
		LOG.info("benifisBankId " + benifisBankId);
		LOG.info("bankBranchId " + bankBranchId);
		BankBranchView branchLocation = null;

		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranchView.class, "bankBranchView");
		criteria.add(Restrictions.eq("bankBranchView.bankId", benifisBankId));
		criteria.add(Restrictions.eq("bankBranchView.bankBranchId", bankBranchId));
		criteria.addOrder(Order.asc("bankBranchView.branchFullName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBranchListfromView method");
		List<BankBranchView> bankBranchView = (List<BankBranchView>) findAll(criteria);
		if(bankBranchView.size() != 0){
			branchLocation = bankBranchView.get(0);
		}
		return branchLocation;
	}

	@Override
	public BankBranch getBankBranch(BigDecimal bankId, BigDecimal bankBranchId, BigDecimal countryId) {

		LOG.info("Entering into getBankBranchName method");
		LOG.info("bankId " + bankId);
		LOG.info("bankBranchId " + bankBranchId);

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		dCriteria.setFetchMode("bankBranch.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		dCriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		//dCriteria.setFetchMode("bankBranch.fsStateMaster", FetchMode.JOIN);
		//dCriteria.createAlias("bankBranch.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);


		//dCriteria.setFetchMode("bankBranch.fsDistrictMaster", FetchMode.JOIN);
		//dCriteria.createAlias("bankBranch.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);

		//dCriteria.setFetchMode("bankBranch.fsCityMaster", FetchMode.JOIN);
		//dCriteria.createAlias("bankBranch.fsCityMaster", "fsCityMaster", JoinType.INNER_JOIN);


		dCriteria.add(Restrictions.eq("bankBranch.bankBranchId", bankBranchId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankBranch> acclist = (List<BankBranch>) findAll(dCriteria);

		if(acclist !=null &&!acclist.isEmpty())
		{
			return acclist.get(0);
		}

		LOG.info("Exit into getBankBranchName method");
		return null;
	}

	@Override
	public List<AccountTypeFromView> getAccountTypeFromView(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(AccountTypeFromView.class, "accountType");
		dCriteria.add(Restrictions.eq("accountType.countryId", countryId));
		dCriteria.addOrder(Order.asc("accountType.amiecCode"));
		List<AccountTypeFromView> accTypelist = (List<AccountTypeFromView>) findAll(dCriteria);
		return accTypelist;
	}

	@Override
	public List<BanksView> getBankListFromView(BigDecimal appCountryId,BigDecimal bankCountryId, BigDecimal serviceGroupId) {
		LOG.info("Entering into getBankListFromView method");
		LOG.info("appCountryId " + appCountryId);
		LOG.info("bankCountryId " + bankCountryId);
		LOG.info("serviceGroupId " + serviceGroupId);

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BanksView.class, "banksView");

		dCriteria.add(Restrictions.eq("banksView.applicationCountryId", appCountryId));
		dCriteria.add(Restrictions.eq("banksView.bankCountryId", bankCountryId));
		if(serviceGroupId!=null){
			dCriteria.add(Restrictions.eq("banksView.serviceGroupId", serviceGroupId));
		}

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BanksView> bankList = (List<BanksView>) findAll(dCriteria);

		LOG.info("Exit into getBankListFromView method");
		return bankList;
	}

	@Override
	public String getBeneDetailProce(BigDecimal benificaryMasterId, BigDecimal benifisBankId, BigDecimal beneBankBranchId,BigDecimal beneAccountSeqId ,
			BigDecimal benifisCurrencyId, BigDecimal customerId) {
		LOG.info("Entering into getBeneDetailProce EX_POPULATE_BENE_DT method");
		LOG.info("benificaryMasterId " + benificaryMasterId);
		LOG.info("benifisBankId " + benifisBankId);
		LOG.info("beneBankBranchId " + beneBankBranchId);
		LOG.info("benifisCurrencyId " + benifisCurrencyId);
		LOG.info("customerId " + customerId);

		Connection connection = null;
		String errorMessage=null;
		try {

			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;

			String call = " { call EX_POPULATE_BENE_DT (?,?,?,?,?,?,?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, benificaryMasterId);
			cs.setBigDecimal(2, benifisBankId);
			cs.setBigDecimal(3, beneBankBranchId);
			cs.setBigDecimal(4, beneAccountSeqId);
			cs.setBigDecimal(5, benifisCurrencyId);
			cs.setBigDecimal(6, customerId);
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			cs.execute();//
			errorMessage = cs.getString(7) == null ? null : cs.getString(7);
			LOG.info("getBeneDetailProce out parameter :"+errorMessage);
			System.out.println("getBeneDetailProce out parameter :"+errorMessage);
			return errorMessage;

		} catch (Exception e) {
			LOG.info( "Error While getting connection "+e.getMessage());
			e.printStackTrace();
			errorMessage = "EX_POPULATE_BENE_DT  "+e.getMessage();
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error( "Problem While Procedure calling "+e.getMessage());
				String erromsg = "EX_POPULATE_BENE_DT" + " : " + e.getMessage();

			}
		}
		return errorMessage;
	}

	@Override
	public Map<String, Object> checkTelephoneExist(String telephoneNumber, String countryCode, String string) {

		LOG.info("Entering into checkTelephoneExistWithCustId method");
		LOG.info("telephoneNo " + telephoneNumber);
		LOG.info("teleCountryID " + countryCode);
		LOG.info("teleCountryID " + string);
		Map<String, Object> mapTeleExist = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "beneficaryTelaphone");
		if (string.equals("telephone")) {
			criteria.add(Restrictions.eq("beneficaryTelaphone.telephoneNumber", telephoneNumber));
		} else if (string.equals("mobile")) {
			criteria.add(Restrictions.eq("beneficaryTelaphone.mobileNumber", telephoneNumber));
		}
		criteria.setFetchMode("beneficaryTelaphone.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryTelaphone.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryTelaphone.countryTelCode", countryCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryContact> lstBeneficaryTelaphone = (List<BeneficaryContact>) findAll(criteria);
		if (lstBeneficaryTelaphone.size() > 0) {
			BeneficaryContact beneficaryTelaphone = lstBeneficaryTelaphone.get(0);
			BigDecimal beneMatsterSeqId = beneficaryTelaphone.getBeneficaryMaster().getBeneficaryMasterSeqId();
			DetachedCriteria beneCriteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");
			beneCriteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
			beneCriteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
			beneCriteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneMatsterSeqId));
			List<BeneficaryRelationship> lstBeneficaryRelationship = (List<BeneficaryRelationship>) findAll(beneCriteria);
			if (lstBeneficaryRelationship.size() > 0) {
				BeneficaryRelationship beneficaryRelationship = lstBeneficaryRelationship.get(0);
				BigDecimal beneAccountSeqId = beneficaryRelationship.getBeneficaryAccount().getBeneficaryAccountSeqId();
				DetachedCriteria beneAccountCriteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
				beneAccountCriteria.add(Restrictions.eq("beneficaryAccount.beneficaryAccountSeqId", beneAccountSeqId));
				beneAccountCriteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryAccount.bank", "bank", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryAccount.bankBranch", "bankBranch", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryMaster.fsStateMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryMaster.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryMaster.fsCityMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryMaster.fsCityMaster", "fsCityMaster", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryMaster.fsDistrictMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryMaster.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);
				beneAccountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
				List<BeneficaryAccount> lstBeneficaryAccount = (List<BeneficaryAccount>) findAll(beneAccountCriteria);
				LOG.info("lstBeneficaryAccount ========== " + lstBeneficaryAccount.size());
				if (lstBeneficaryAccount.size() > 0) {
					mapTeleExist.put("TeleExist", new Boolean(true));
					mapTeleExist.put("LstTeleExist", lstBeneficaryTelaphone);
					mapTeleExist.put("RelationExist", new Boolean(true));
					mapTeleExist.put("AccountExist", new Boolean(true));
					mapTeleExist.put("LstAccountExist", lstBeneficaryAccount);
				} else {
					mapTeleExist.put("TeleExist", new Boolean(true));
					mapTeleExist.put("LstTeleExist", lstBeneficaryTelaphone);
					mapTeleExist.put("RelationExist", new Boolean(true));
					mapTeleExist.put("AccountExist", new Boolean(false));
				}
			} else {
				mapTeleExist.put("TeleExist", new Boolean(true));
				mapTeleExist.put("LstTeleExist", lstBeneficaryTelaphone);
				mapTeleExist.put("RelationExist", new Boolean(false));
				mapTeleExist.put("AccountExist", new Boolean(false));
			}
		} else {
			mapTeleExist.put("TeleExist", new Boolean(false));
			mapTeleExist.put("RelationExist", new Boolean(false));
			mapTeleExist.put("AccountExist", new Boolean(false));
		}
		return mapTeleExist;

	}

	@Override
	public Map<String, Object> checkTelephoneExistWithCustIdwithPhone(String telephoneNumber, BigDecimal customerNo, String countryCode, String type) {
		LOG.info("Entered into checkTelephoneExistWithCustIdwithPhone(BigDecimal telephoneNo, BigDecimal customerID, String teleCountryID, String type) Method");
		LOG.info("telephoneNumber  = "+telephoneNumber);
		LOG.info("customerNo = "+customerNo);
		LOG.info("countryCode ="+countryCode);
		LOG.info("type =" +type);
		Map<String, Object> mapTeleExist = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "beneficaryTelaphone");
		if (type.equals("telephone")) {
			criteria.add(Restrictions.eq("beneficaryTelaphone.telephoneNumber", telephoneNumber));
		} else if (type.equals("mobile")) {
			criteria.add(Restrictions.eq("beneficaryTelaphone.mobileNumber", telephoneNumber));
		}
		criteria.add(Restrictions.eq("beneficaryTelaphone.countryTelCode", countryCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryContact> lstBeneficaryTelaphone = (List<BeneficaryContact>) findAll(criteria);
		if (lstBeneficaryTelaphone.size() > 0) {
			BeneficaryContact beneficaryTelaphone = lstBeneficaryTelaphone.get(0);
			BigDecimal beneMatsterSeqId = beneficaryTelaphone.getBeneficaryMaster().getBeneficaryMasterSeqId();
			DetachedCriteria beneCriteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");
			beneCriteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
			beneCriteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
			beneCriteria.setFetchMode("beneficaryRelationship.customerId", FetchMode.JOIN);
			beneCriteria.createAlias("beneficaryRelationship.customerId", "customerId", JoinType.INNER_JOIN);
			beneCriteria.add(Restrictions.eq("customerId.customerId", customerNo));
			beneCriteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneMatsterSeqId));
			List<BeneficaryRelationship> lstBeneficaryRelationship = (List<BeneficaryRelationship>) findAll(beneCriteria);
			if (lstBeneficaryRelationship.size() > 0) {
				BeneficaryRelationship beneficaryRelationship = lstBeneficaryRelationship.get(0);
				BigDecimal beneAccountSeqId = beneficaryRelationship.getBeneficaryAccount().getBeneficaryAccountSeqId();
				DetachedCriteria beneAccountCriteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
				beneAccountCriteria.add(Restrictions.eq("beneficaryAccount.beneficaryAccountSeqId", beneAccountSeqId));
				beneAccountCriteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryAccount.bank", "bank", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryAccount.bankBranch", "bankBranch", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryMaster.fsStateMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryMaster.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryMaster.fsCityMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryMaster.fsCityMaster", "fsCityMaster", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryMaster.fsDistrictMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryMaster.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);
				beneAccountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
				List<BeneficaryAccount> lstBeneficaryAccount = (List<BeneficaryAccount>) findAll(beneAccountCriteria);
				LOG.info("lstBeneficaryAccount ========== " + lstBeneficaryAccount.size());
				if (lstBeneficaryAccount.size() > 0) {
					mapTeleExist.put("TeleExist", new Boolean(true));
					mapTeleExist.put("LstTeleExist", lstBeneficaryTelaphone);
					mapTeleExist.put("RelationExist", new Boolean(true));
					mapTeleExist.put("AccountExist", new Boolean(true));
					mapTeleExist.put("LstAccountExist", lstBeneficaryAccount);
				} else {
					mapTeleExist.put("TeleExist", new Boolean(true));
					mapTeleExist.put("LstTeleExist", lstBeneficaryTelaphone);
					mapTeleExist.put("RelationExist", new Boolean(true));
					mapTeleExist.put("AccountExist", new Boolean(false));
				}
			} else {
				mapTeleExist.put("TeleExist", new Boolean(true));
				mapTeleExist.put("LstTeleExist", lstBeneficaryTelaphone);
				mapTeleExist.put("RelationExist", new Boolean(false));
				mapTeleExist.put("AccountExist", new Boolean(false));
			}
		} else {
			mapTeleExist.put("TeleExist", new Boolean(false));
			mapTeleExist.put("RelationExist", new Boolean(false));
			mapTeleExist.put("AccountExist", new Boolean(false));
		}
		LOG.info(  "Exited from the checkTelephoneExistWithCustIdwithPhone method ");
		return mapTeleExist;
	}

	@Override
	public List<BeneficaryContact> isCoustomerTelephoneExistInDB(String telephoneNumber) {
		LOG.info("Entered into isCoustomerTelephoneExistInDB(BigDecimal telephoneNumber) Method");
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "beneficaryTelaphone");
		criteria.add(Restrictions.eq("beneficaryTelaphone.countryTelephoneNumber", telephoneNumber));
		List<BeneficaryContact> beneficaryTelaphone = (List<BeneficaryContact>) findAll(criteria);
		LOG.info("beneficaryTelaphone =" + beneficaryTelaphone.size());
		LOG.info("Exited from  isCoustomerTelephoneExistInDB(BigDecimal telephoneNumber) Method");
		return beneficaryTelaphone;
	}

	@Override
	public String getTelePhoneNumberString(BigDecimal beneficaryMasterSeqId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "beneficaryContact");
		criteria.setFetchMode("beneficaryContact.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryContact.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficaryMasterSeqId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryContact> beneficaryContact = (List<BeneficaryContact>) findAll(criteria);
		if (beneficaryContact != null && !beneficaryContact.isEmpty()) {
			if (beneficaryContact.get(0).getTelephoneNumber() != null) {
				return beneficaryContact.get(0).getTelephoneNumber();
			}
		}
		return null;

	}

	@Override
	public BigDecimal getMobileNumber(BigDecimal beneficaryMasterSeqId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "beneficaryContact");
		criteria.setFetchMode("beneficaryContact.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryContact.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficaryMasterSeqId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryContact> beneficaryContact = (List<BeneficaryContact>) findAll(criteria);
		if (beneficaryContact != null && !beneficaryContact.isEmpty()) {
			if (beneficaryContact.get(0).getMobileNumber() != null) {
				return beneficaryContact.get(0).getMobileNumber();
			}
		}
		return null;

	}

	@Override
	public List<BankBranchView> getBranchListfromViewfromState(BigDecimal benifisStateId,BigDecimal benifisBankId) {
		LOG.info("Entering into getBranchListfromViewfromState method");
		LOG.info("benifisStateId " + benifisStateId);
		LOG.info("benifisBankId " + benifisBankId);

		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranchView.class, "bankBranchView");
		criteria.add(Restrictions.eq("bankBranchView.stateId", benifisStateId));
		criteria.add(Restrictions.eq("bankBranchView.bankId", benifisBankId));
		criteria.addOrder(Order.asc("bankBranchView.branchFullName"));
		//	criteria.addOrder(Order.asc("bankBranchView.branchFullName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBranchListfromViewfromState method");
		return (List<BankBranchView>) findAll(criteria);
	}

	@Override
	public List<StateMaster> getStatefromBranch(BigDecimal benifisBankId) {
		LOG.info("Entering into getStatefromBranch method");
		LOG.info("benifisBankId " + benifisBankId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "BankBranch");
		criteria.setFetchMode("BankBranch.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("BankBranch.exBankMaster", "bankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankMaster.bankId", benifisBankId));
		criteria.setProjection(Projections.distinct(Projections.property("BankBranch.fsStateMaster")));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getStatefromBranch method");
		return (List<StateMaster>) findAll(criteria);
	}

	@Override
	public List<StateMasterDesc> getBankStateList(List<BigDecimal> stateIdList,BigDecimal languageId) {
		LOG.info("Entering into getBankStateList method");
		DetachedCriteria criteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		criteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		criteria.createAlias("stateMasterDesc.fsStateMaster", "stateMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("stateMasterDesc.fsLanguageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.add(Restrictions.in("stateMaster.stateId", stateIdList));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBankStateList method");
		return (List<StateMasterDesc>) findAll(criteria);
	}

	@Override
	public void deActivateMultipleMobileNumbers(List<BeneficaryContact> lstContactDetails) {
		for (BeneficaryContact contact : lstContactDetails) {
			contact.setIsActive(Constants.D);
			getSession().saveOrUpdate(contact);
		}
	}

	@Override
	public List<BanksView> getBankListFromView(BigDecimal countryId, BigDecimal benifisCountryId, BigDecimal serviceGroupId, ArrayList<String> bankIndList) {
		LOG.info("Entering into getBankListFromView method");
		LOG.info("countryId " + countryId);
		LOG.info("benifisCountryId " + benifisCountryId);
		LOG.info("serviceGroupId " + serviceGroupId);
		LOG.info("bankIndList " + bankIndList);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BanksView.class, "banksView");
		dCriteria.add(Restrictions.eq("banksView.applicationCountryId", countryId));
		dCriteria.add(Restrictions.eq("banksView.bankCountryId", benifisCountryId));
		dCriteria.add(Restrictions.eq("banksView.serviceGroupId", serviceGroupId));
		dCriteria.add(Restrictions.in("banksView.bankInd", bankIndList));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BanksView> bankList = (List<BanksView>) findAll(dCriteria);
		LOG.info("Exit into getBankListFromView method");
		return bankList;
	}

	@Override
	public List<RoutingHeader> getRountingBankId(BigDecimal benifisCountryId, BigDecimal currency, BigDecimal serviceGroupId, BigDecimal serviceProvider) {
		LOG.info("Entering into getRountingBankId method");
		LOG.info("benifisCountryId " + benifisCountryId);
		LOG.info("currency " + currency);
		LOG.info("serviceGroupId " + serviceGroupId);
		LOG.info("serviceProvider " + serviceProvider);
		DetachedCriteria criteria = DetachedCriteria.forClass(RoutingHeader.class, "routingHeader");
		criteria.setFetchMode("routingHeader.exRoutingCountryId", FetchMode.JOIN);
		criteria.createAlias("routingHeader.exRoutingCountryId", "routingCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("routingCountry.countryId", benifisCountryId));
		criteria.setFetchMode("routingHeader.exCurrenyId", FetchMode.JOIN);
		criteria.createAlias("routingHeader.exCurrenyId", "currency", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("currency.currencyId", currency));
		criteria.setFetchMode("routingHeader.exServiceId", FetchMode.JOIN);
		criteria.createAlias("routingHeader.exServiceId", "service", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("service.serviceId", serviceGroupId));
		criteria.setFetchMode("routingHeader.exRoutingBankId", FetchMode.JOIN);
		criteria.createAlias("routingHeader.exRoutingBankId", "routingBank", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("routingBank.bankId", serviceProvider));
		List<RoutingHeader> list = (List<RoutingHeader>) findAll(criteria);
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public int checkBrnachiAvailble(BigDecimal benifisBankId) {
		LOG.info("Entering into checkBrnachiAvailble method");
		LOG.info("benifisBankId " + benifisBankId);

		String hql = "select count(*) " + "from " + "BankBranch as bankBranch where bankBranch.exBankMaster.bankId = :benifisBankId ";
		LOG.info("hql " + hql);
		Query query = getSession().createQuery(hql);
		query.setParameter("benifisBankId", benifisBankId);
		Long count = (Long) query.uniqueResult();
		LOG.info("count " + count);
		if (count > 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public List<RoutingDetails> getRountingBankBranch(BigDecimal benifisCountryId, BigDecimal currency,	BigDecimal serviceGroupId, BigDecimal agentServicePBank,BigDecimal agentMaster) {

		LOG.info("Entering into getRountingBankId method");
		LOG.info("benifisCountryId " + benifisCountryId);
		LOG.info("currency " + currency);
		LOG.info("serviceGroupId " + serviceGroupId);
		LOG.info("serviceProvider " + agentServicePBank);

		DetachedCriteria criteria = DetachedCriteria.forClass(RoutingDetails.class, "routingDetails");

		criteria.setFetchMode("routingDetails.exRoutingCountryId", FetchMode.JOIN);
		criteria.createAlias("routingDetails.exRoutingCountryId", "routingCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("routingCountry.countryId", benifisCountryId));

		criteria.setFetchMode("routingDetails.exCurrenyId", FetchMode.JOIN);
		criteria.createAlias("routingDetails.exCurrenyId", "currency", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("currency.currencyId", currency));

		criteria.setFetchMode("routingDetails.exServiceId", FetchMode.JOIN);
		criteria.createAlias("routingDetails.exServiceId", "service", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("service.serviceId", serviceGroupId));

		if(agentServicePBank.compareTo(agentMaster)==0){
			criteria.setFetchMode("routingDetails.exRoutingBankId", FetchMode.JOIN);
			criteria.createAlias("routingDetails.exRoutingBankId", "routingBank", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("routingBank.bankId", agentServicePBank));
		}else{
			criteria.add(Restrictions.eq("routingDetails.agentBankId", agentMaster));
		}

		criteria.setFetchMode("routingDetails.exBankBranchId", FetchMode.JOIN);
		criteria.createAlias("routingDetails.exBankBranchId", "routingBankBranch", JoinType.INNER_JOIN);

		List<RoutingDetails> list = (List<RoutingDetails>) findAll(criteria);

		return list;
	}

	@Override
	public boolean doneSingleTransaction(BigDecimal benificaryAccountId) {
		LOG.info("Entering into doneSingleTransaction method");
		LOG.info("benificaryAccountId " + benificaryAccountId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		criteria.add(Restrictions.eq("beneficaryAccount.beneficaryAccountSeqId", benificaryAccountId));
		List<BeneficaryAccount> list = (List<BeneficaryAccount>) findAll(criteria);
		if (list != null && !list.isEmpty()) {
			if (list.get(0).getLastJavaRemittence() == null) {
				return false;
			}
			return true;
		}
		LOG.info("Exit into doneSingleTransaction method");
		return false;
	}

	@Override
	public BigDecimal getbeneAccountSeqId(BigDecimal benificaryMasterId,BigDecimal bankNameId, BigDecimal branchNameId,	String accountNumber,BigDecimal currencyId) {

		LOG.info("Entering into getCustomerBeneficaryDetailswithAll method");
		LOG.info("masterseqId " + benificaryMasterId);

		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		if(benificaryMasterId != null){
			criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", benificaryMasterId));
		}

		if(bankNameId != null){
			criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.bank", "bank", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("bank.bankId", bankNameId));
		}

		/*if(branchNameId != null){
			criteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.bankBranch", "bankBranch", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("bankBranch.bankBranchId", branchNameId));
		}*/

		if(currencyId != null){
			criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.currencyId", "currencyId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("currencyId.currencyId", currencyId));
		}

		if(accountNumber != null){
			criteria.add(Restrictions.eq("beneficaryAccount.bankAccountNumber", accountNumber));
		}else{
			criteria.add(Restrictions.isNull("beneficaryAccount.bankAccountNumber"));
		}
		
		criteria.add(Restrictions.eq("beneficaryAccount.isActive", Constants.Yes));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryAccount> acclist = (List<BeneficaryAccount>) findAll(criteria);
		LOG.info("Exit into getCustomerBeneficaryDetailswithAll method");

		if(acclist!=null && !acclist.isEmpty())
		{
			return acclist.get(0).getBeneficaryAccountSeqId();
		}

		return null;
	}

	@Override
	public List<BankBranchView> getBranchListfromViewwithStateMissing(
			BigDecimal benifisBankId) {

		LOG.info("Entering into getBranchListfromView method");
		LOG.info("benifisBankId " + benifisBankId);

		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranchView.class, "bankBranchView");
		criteria.add(Restrictions.eq("bankBranchView.bankId", benifisBankId));

		Criterion criteria1 = Restrictions.eq("bankBranchView.stateId", null);
		Criterion criteria2 = Restrictions.isNull("bankBranchView.stateId");
		LogicalExpression orExp = Restrictions.or(criteria1, criteria2);
		criteria.add(orExp);

		criteria.addOrder(Order.asc("bankBranchView.branchFullName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBranchListfromView method");
		return (List<BankBranchView>) findAll(criteria);

	}

	@Override
	public List<BeneficaryContact> getMobileDetails(BigDecimal benificaryMasterId) {
		LOG.info("Entered into  getMobileDetails(BigDecimal benificaryMasterId) Method");
		LOG.info("masterId = " + benificaryMasterId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "benificiaryTelephone");
		criteria.setFetchMode("benificiaryTelephone.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("benificiaryTelephone.beneficaryMaster", "beneficaryMasterbeneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", benificaryMasterId));
		criteria.add(Restrictions.eq("benificiaryTelephone.isActive", Constants.Yes));
		criteria.add(Restrictions.isNotNull("benificiaryTelephone.mobileNumber"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exited from the  getMobileDetails(BigDecimal masterId) Method");
		return (List<BeneficaryContact>) findAll(criteria);
	}

	@Override
	public List<BeneficaryContact> getTelephoneDetails(BigDecimal masterId) {
		LOG.info( "Entered into  getTelephoneDetails(BigDecimal masterId) Method");
		LOG.info("masterId = "+masterId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "benificiaryTelephone");
		criteria.setFetchMode("benificiaryTelephone.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("benificiaryTelephone.beneficaryMaster", "beneficaryMasterbeneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", masterId));
		criteria.add(Restrictions.eq("benificiaryTelephone.isActive", Constants.Yes));
		//criteria.add(Restrictions.isNotNull("benificiaryTelephone.telephoneNumber"));
		criteria.addOrder(Order.desc("benificiaryTelephone.createdDate"));
		
		criteria.setFetchMode("benificiaryTelephone.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("benificiaryTelephone.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info( "Exited from the  getTelephoneDetails(BigDecimal masterId) Method");
		return (List<BeneficaryContact>) findAll(criteria);

	}

	@Override
	public Map<String, Object> checkTelephoneExistWithCustIdwithPhone(BigDecimal telephoneNo, BigDecimal customerID, String teleCountryID, String type) {
		LOG.info("Entered into checkTelephoneExistWithCustIdwithPhone(BigDecimal telephoneNo, BigDecimal customerID, String teleCountryID, String type) Method");
		LOG.info("telephoneNo ="+telephoneNo);
		LOG.info("customerID ="+customerID);
		LOG.info("teleCountryID ="+teleCountryID);
		LOG.info("type ="+type);
		Map<String, Object> mapTeleExist = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "beneficaryTelaphone");
		if (type.equals("telephone")) {
			criteria.add(Restrictions.eq("beneficaryTelaphone.telephoneNumber", telephoneNo.toPlainString()));
		} else if (type.equals("mobile")) {
			criteria.add(Restrictions.eq("beneficaryTelaphone.mobileNumber", telephoneNo));
		}
		criteria.add(Restrictions.eq("beneficaryTelaphone.countryTelCode", teleCountryID));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryContact> lstBeneficaryTelaphone = (List<BeneficaryContact>) findAll(criteria);
		if (lstBeneficaryTelaphone.size() > 0) {
			BeneficaryContact beneficaryTelaphone = lstBeneficaryTelaphone.get(0);
			BigDecimal beneMatsterSeqId = beneficaryTelaphone.getBeneficaryMaster().getBeneficaryMasterSeqId();
			DetachedCriteria beneCriteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");
			beneCriteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
			beneCriteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
			beneCriteria.setFetchMode("beneficaryRelationship.customerId", FetchMode.JOIN);
			beneCriteria.createAlias("beneficaryRelationship.customerId", "customerId", JoinType.INNER_JOIN);
			beneCriteria.add(Restrictions.eq("customerId.customerId", customerID));
			beneCriteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneMatsterSeqId));
			List<BeneficaryRelationship> lstBeneficaryRelationship = (List<BeneficaryRelationship>) findAll(beneCriteria);
			if (lstBeneficaryRelationship.size() > 0) {
				BeneficaryRelationship beneficaryRelationship = lstBeneficaryRelationship.get(0);
				BigDecimal beneAccountSeqId = beneficaryRelationship.getBeneficaryAccount().getBeneficaryAccountSeqId();
				DetachedCriteria beneAccountCriteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
				beneAccountCriteria.add(Restrictions.eq("beneficaryAccount.beneficaryAccountSeqId", beneAccountSeqId));
				beneAccountCriteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryAccount.bank", "bank", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryAccount.bankBranch", "bankBranch", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryMaster.fsStateMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryMaster.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
				/*			beneAccountCriteria.setFetchMode("beneficaryMaster.fsCityMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryMaster.fsCityMaster", "fsCityMaster", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryMaster.fsDistrictMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryMaster.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);*/
				beneAccountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
				List<BeneficaryAccount> lstBeneficaryAccount = (List<BeneficaryAccount>) findAll(beneAccountCriteria);
				LOG.info("lstBeneficaryAccount ========== " + lstBeneficaryAccount.size());
				if (lstBeneficaryAccount.size() > 0) {
					mapTeleExist.put("TeleExist", new Boolean(true));
					mapTeleExist.put("LstTeleExist", lstBeneficaryTelaphone);
					mapTeleExist.put("RelationExist", new Boolean(true));
					mapTeleExist.put("AccountExist", new Boolean(true));
					mapTeleExist.put("LstAccountExist", lstBeneficaryAccount);
				} else {
					mapTeleExist.put("TeleExist", new Boolean(true));
					mapTeleExist.put("LstTeleExist", lstBeneficaryTelaphone);
					mapTeleExist.put("RelationExist", new Boolean(true));
					mapTeleExist.put("AccountExist", new Boolean(false));
				}
			} else {
				mapTeleExist.put("TeleExist", new Boolean(true));
				mapTeleExist.put("LstTeleExist", lstBeneficaryTelaphone);
				mapTeleExist.put("RelationExist", new Boolean(false));
				mapTeleExist.put("AccountExist", new Boolean(false));
			}
		} else {
			mapTeleExist.put("TeleExist", new Boolean(false));
			mapTeleExist.put("RelationExist", new Boolean(false));
			mapTeleExist.put("AccountExist", new Boolean(false));
		}
		LOG.info(  "Exited from the checkTelephoneExistWithCustIdwithPhone method ");
		return mapTeleExist;
	}

	@Override
	public List<BenificiaryListView> fetchBeneficiaryNewRecord(BigDecimal customerId, BigDecimal beneMasterId,BigDecimal beneAccountId,Boolean editBene,BigDecimal currencyId,BigDecimal servicegroupid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BenificiaryListView.class, "benificiaryListView");
		if(customerId != null){
			criteria.add(Restrictions.eq("benificiaryListView.customerId", customerId));
		}
		if(beneMasterId != null){
			criteria.add(Restrictions.eq("benificiaryListView.beneficaryMasterSeqId", beneMasterId));
		}
		if(beneAccountId != null){
			criteria.add(Restrictions.eq("benificiaryListView.beneficiaryAccountSeqId", beneAccountId));
		}
		if(!editBene){
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date fromDate = calendar.getTime();
			System.out.println(fromDate);

			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			Date toDate = calendar.getTime();
			System.out.println(toDate);

			criteria.add(Restrictions.between("benificiaryListView.createdDate", fromDate, toDate));
		}

		if(currencyId != null){
			criteria.add(Restrictions.eq("benificiaryListView.currencyId", currencyId));
		}

		if(servicegroupid != null){
			criteria.add(Restrictions.eq("benificiaryListView.serviceGroupId", servicegroupid));
		}

		criteria.addOrder(Order.desc("benificiaryListView.createdDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BenificiaryListView>) findAll(criteria);
	}

	@Override
	public void concurrentSaveBeneficaryDetails(BeneficaryMaster beneficaryMaster, BeneficaryContact contact, BeneficaryAccount beneficaryAccount, BeneficaryRelationship relationship) {
		LOG.info("Entered into checoncurrentSaveBeneficaryDetails Method");
		if(beneficaryMaster!=null) {
			beneficaryMaster.setIsActive(Constants.Yes);
			getSession().saveOrUpdate(beneficaryMaster);
		}
		if (contact != null) {
			if(beneficaryMaster!=null) {
				contact.setBeneficaryMaster(beneficaryMaster);
			}
			contact.setIsActive(Constants.Yes);
			getSession().saveOrUpdate(contact);
		}
		if (beneficaryAccount != null) {
			if(beneficaryMaster!=null) {
				beneficaryAccount.setBeneficaryMaster(beneficaryMaster);
			}
			beneficaryAccount.setIsActive(Constants.Yes);
			getSession().saveOrUpdate(beneficaryAccount);
		}
		if (relationship != null) {
			if(beneficaryMaster!=null) {
				relationship.setBeneficaryMaster(beneficaryMaster);
			}
			if (beneficaryAccount != null) {
				relationship.setBeneficaryAccount(beneficaryAccount);
			}
			relationship.setIsActive(Constants.Yes);
			getSession().saveOrUpdate(relationship);
		}
		LOG.info("EXIT into checoncurrentSaveBeneficaryDetails Method");
	}

	@Override
	public List<BankBranchView> serachBranch(BigDecimal bankid,String searchBranchName, String searchIFSC, String searchSwift, String searchState) {
		LOG.info("Entering into getBranchListfromView method");
		LOG.info("searchBranchName " + searchBranchName);
		LOG.info("searchState " + searchState);
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranchView.class, "bankBranchView");
		criteria.add(Restrictions.eq("bankBranchView.bankId", bankid));

		if (searchBranchName != null && searchBranchName.length() != 0) {
			criteria.add(Restrictions.like("bankBranchView.branchFullName", searchBranchName, MatchMode.ANYWHERE).ignoreCase());
		}
		if (searchIFSC != null && searchIFSC.length() != 0) {
			criteria.add(Restrictions.like("bankBranchView.ifscCode", searchIFSC, MatchMode.ANYWHERE).ignoreCase());
		}
		if (searchSwift != null && searchSwift.length() != 0) {
			criteria.add(Restrictions.like("bankBranchView.swift", searchSwift, MatchMode.ANYWHERE).ignoreCase());
		}
		if (searchState != null && searchState.length() != 0) {
			criteria.add(Restrictions.eq("bankBranchView.stateId", new BigDecimal(searchState)));
		}

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBranchListfromView method");
		return (List<BankBranchView>) findAll(criteria);
	}

	@Override
	public Boolean checkAccountTypeMigrated(BigDecimal beneficiaryAccountSeqId) {

		LOG.info("Entering into doneSingleTransaction method");
		LOG.info("benificaryAccountId " + beneficiaryAccountSeqId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		criteria.add(Restrictions.eq("beneficaryAccount.beneficaryAccountSeqId", beneficiaryAccountSeqId));
		List<BeneficaryAccount> list = (List<BeneficaryAccount>) findAll(criteria);
		if (list != null && !list.isEmpty()) {
			if (list.get(0).getRecordStaus()!=null && list.get(0).getRecordStaus().equalsIgnoreCase("T")) {
				return false;
			}
			return true;
		}
		LOG.info("Exit into doneSingleTransaction method");
		return false;

	}

	@Override
	public List<BenificiaryListView> fetchBeneficiaryNewRecordUpdated(BigDecimal customerId,BigDecimal beneMasterId,BigDecimal beneAccountId,Boolean editBene,BigDecimal currencyId,BigDecimal servicegroupid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BenificiaryListView.class, "benificiaryListView");
		if(customerId != null){
			criteria.add(Restrictions.eq("benificiaryListView.customerId", customerId));
		}
		if(beneMasterId != null){
			criteria.add(Restrictions.eq("benificiaryListView.beneficaryMasterSeqId", beneMasterId));
		}
		if(beneAccountId != null){
			criteria.add(Restrictions.eq("benificiaryListView.beneficiaryAccountSeqId", beneAccountId));
		}
		if(!editBene){
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date fromDate = calendar.getTime();
			System.out.println(fromDate);

			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			Date toDate = calendar.getTime();
			System.out.println(toDate);

			criteria.add(Restrictions.between("benificiaryListView.modifiedDate", fromDate, toDate));
		}

		if(currencyId != null){
			criteria.add(Restrictions.eq("benificiaryListView.currencyId", currencyId));
		}

		if(servicegroupid != null){
			criteria.add(Restrictions.eq("benificiaryListView.serviceGroupId", servicegroupid));
		}

		criteria.addOrder(Order.desc("benificiaryListView.modifiedDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BenificiaryListView>) findAll(criteria);
	}

	@Override
	public List<CountryMasterDesc> fetchCountryContactCode(BigDecimal countryID) {
		LOG.info("Entering into fetchCountryContactCode method");
		LOG.info("countryID" + countryID);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryID));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getCountryList method");
		return (List<CountryMasterDesc>) findAll(detachedCriteria);
	}

	@Override
	public BeneficaryMaster getMasterDetails(BigDecimal beneficaryMasterSeqId) {
		LOG.info("Entering into getMasterDetails method");
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryMaster.class, "beneficaryMaster");
		criteria.setFetchMode("beneficaryMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryMaster.fsCountryMaster", "countryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficaryMasterSeqId));
		criteria.setFetchMode("beneficaryMaster.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("beneficaryMaster.applicationCountryId", "applicationCountry", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryMaster.fsStateMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryMaster.fsStateMaster", "stateMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryMaster.beneficaryStatus", FetchMode.JOIN);
		criteria.createAlias("beneficaryMaster.beneficaryStatus", "beneficaryStatus", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryMaster> listMaster = (List<BeneficaryMaster>) findAll(criteria);
		if (listMaster != null && !listMaster.isEmpty()) {
			if (listMaster.get(0).getFsDistrictMaster() != null) {
				DetachedCriteria criteria1 = DetachedCriteria.forClass(BeneficaryMaster.class, "beneficaryMaster");
				criteria1.setFetchMode("beneficaryMaster.fsCountryMaster", FetchMode.JOIN);
				criteria1.createAlias("beneficaryMaster.fsCountryMaster", "countryMaster", JoinType.INNER_JOIN);
				criteria1.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficaryMasterSeqId));
				criteria1.setFetchMode("beneficaryMaster.applicationCountryId", FetchMode.JOIN);
				criteria1.createAlias("beneficaryMaster.applicationCountryId", "applicationCountry", JoinType.INNER_JOIN);
				if (listMaster.get(0).getFsStateMaster() != null) {
					criteria1.setFetchMode("beneficaryMaster.fsStateMaster", FetchMode.JOIN);
					criteria1.createAlias("beneficaryMaster.fsStateMaster", "stateMaster", JoinType.INNER_JOIN);
				}
				if (listMaster.get(0).getFsDistrictMaster() != null) {
					criteria.setFetchMode("beneficaryMaster.fsDistrictMaster", FetchMode.JOIN);
					criteria.createAlias("beneficaryMaster.fsDistrictMaster", "distMaster", JoinType.INNER_JOIN);
				}
				if (listMaster.get(0).getFsCityMaster() != null) {
					criteria.setFetchMode("beneficaryMaster.fsCityMaster", FetchMode.JOIN);
					criteria.createAlias("beneficaryMaster.fsCityMaster", "cityMaster", JoinType.INNER_JOIN);
				}
				if (listMaster.get(0).getBeneficaryStatus() != null) {
					criteria1.setFetchMode("beneficaryMaster.beneficaryStatus", FetchMode.JOIN);
					criteria1.createAlias("beneficaryMaster.beneficaryStatus", "beneficaryStatus", JoinType.INNER_JOIN);
				}
				criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
				List<BeneficaryMaster> templistMaster = (List<BeneficaryMaster>) findAll(criteria);
				if (templistMaster != null && !templistMaster.isEmpty()) {
					return templistMaster.get(0);
				} else {
					return listMaster.get(0);
				}
			}
			return listMaster.get(0);
		}
		return null;
	}

	@Override
	public String saveUpdatedBeneTelePhoneNumber(HashMap<String, Object> migrateBeneTelephone) {

		String errorMessage = null;

		try{

			BigDecimal beneMasterId = (BigDecimal) migrateBeneTelephone.get("BeneMasterSeqId");
			BigDecimal beneBankId = (BigDecimal) migrateBeneTelephone.get("BeneBankId");
			BigDecimal beneBankBranchId = (BigDecimal) migrateBeneTelephone.get("BeneBankBranchId");
			BigDecimal beneAccountId = (BigDecimal) migrateBeneTelephone.get("BeneAccountSeqId");
			BigDecimal beneCurrencyId = (BigDecimal) migrateBeneTelephone.get("BeneCurrencyId");
			BigDecimal beneCustomerId = (BigDecimal) migrateBeneTelephone.get("BeneCustomerId");
			BigDecimal beneContactId = (BigDecimal) migrateBeneTelephone.get("BeneContactSeqId");
			String telephoneNumber = (String) migrateBeneTelephone.get("BeneContactTelephone");

			// saving in ex_beneficiary_contact_details
			if(beneContactId != null && telephoneNumber != null){
				BeneficaryContact customerContactDetails = (BeneficaryContact) getSession().get(BeneficaryContact.class, beneContactId);
				if(customerContactDetails.getTelephoneNumber() != null){
					customerContactDetails.setTelephoneNumber(telephoneNumber);
				}else if(customerContactDetails.getMobileNumber() != null){
					customerContactDetails.setMobileNumber(new BigDecimal(telephoneNumber));
				}
				customerContactDetails.setModifiedBy(sessionStateManage.getUserName());
				customerContactDetails.setModifiedDate(new Date());

				getSession().update(customerContactDetails);
			}

			// if beneficiary mobile number then calling EX_POPULATE_BENE_DT
			errorMessage = getBeneDetailProce(beneMasterId, beneBankId, beneBankBranchId, beneAccountId, beneCurrencyId, beneCustomerId);

		}catch(Exception e){
			LOG.info("saveUpdatedBeneTelePhoneNumber method"+e.getMessage());
			e.printStackTrace();
			errorMessage = "Saving Contact and Calling - EX_POPULATE_BENE_DT - "+e.getMessage();
		}

		return errorMessage;
	}

	@Override
	public String checkBeneDetailsValidation(HashMap<String, Object> checkBeneDetails) throws AMGException {
		
		LOG.info("Entering into checkBeneDetailsValidation method");
		LOG.info("Input Values : "+checkBeneDetails.toString());

		String errorMessage = null;
		Connection connection = null;

		try{
			
			BigDecimal BeneBankCountryId = (BigDecimal) checkBeneDetails.get("BeneBankCountryId");
			BigDecimal BeneServiceGroupId = (BigDecimal) checkBeneDetails.get("BeneServiceGroupId");
			BigDecimal BeneBankId = (BigDecimal) checkBeneDetails.get("BeneBankId");
			BigDecimal BeneBankBranchId = (BigDecimal) checkBeneDetails.get("BeneBankBranchId");
			BigDecimal BeneCurrencyId = (BigDecimal) checkBeneDetails.get("BeneCurrencyId");
			String BeneAccountNumber = (String) checkBeneDetails.get("BeneAccountNumber");
			BigDecimal BeneBankServiceProvider = (BigDecimal) checkBeneDetails.get("BeneBankServiceProvider");
			String BeneFirstName = (String) checkBeneDetails.get("BeneFirstName");
			String BeneSecondName = (String) checkBeneDetails.get("BeneSecondName");
			String BeneThirdName = (String) checkBeneDetails.get("BeneThirdName");
			String BeneFourthName = (String) checkBeneDetails.get("BeneFourthName");
			String BeneFifthName = (String) checkBeneDetails.get("BeneFifthName");
			String BeneArabicFirstName = (String) checkBeneDetails.get("BeneArabicFirstName");
			String BeneArabicSecondName = (String) checkBeneDetails.get("BeneArabicSecondName");
			String BeneArabicThirdName = (String) checkBeneDetails.get("BeneArabicThirdName");
			String BeneArabicFourthName = (String) checkBeneDetails.get("BeneArabicFourthName");
			String BeneTelephone = (String) checkBeneDetails.get("BeneTelephone");
			BigDecimal BeneMobileNumber = (BigDecimal) checkBeneDetails.get("BeneMobileNumber");
			BigDecimal BeneCountryId = (BigDecimal) checkBeneDetails.get("BeneCountryId");
			BigDecimal BeneStateId = (BigDecimal) checkBeneDetails.get("BeneStateId");
			BigDecimal BeneDistrictId = (BigDecimal) checkBeneDetails.get("BeneDistrictId");
			BigDecimal BeneCityId = (BigDecimal) checkBeneDetails.get("BeneCityId");
			
			CallableStatement cs =null;
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_CHK_BENEFICIARY (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, BeneBankCountryId);
			cs.setBigDecimal(2, BeneServiceGroupId);
			cs.setBigDecimal(3, BeneBankId);
			cs.setBigDecimal(4, BeneBankBranchId);
			cs.setBigDecimal(5, BeneCurrencyId);
			cs.setString(6, BeneAccountNumber);
			cs.setBigDecimal(7, BeneBankServiceProvider);
			cs.setString(8, BeneFirstName);
			cs.setString(9, BeneSecondName);
			cs.setString(10, BeneThirdName);
			cs.setString(11, BeneFourthName);
			cs.setString(12, BeneFifthName);
			cs.setString(13, BeneArabicFirstName);
			cs.setString(14, BeneArabicSecondName);
			cs.setString(15, BeneArabicThirdName);
			cs.setString(16, BeneArabicFourthName);
			cs.setString(17, BeneTelephone);
			cs.setBigDecimal(18, BeneMobileNumber);
			cs.setBigDecimal(19, BeneCountryId);
			cs.setBigDecimal(20, BeneStateId);
			cs.setBigDecimal(21, BeneDistrictId);
			cs.setBigDecimal(22, BeneCityId);
			cs.registerOutParameter(23, java.sql.Types.VARCHAR);
			cs.execute();
			errorMessage = cs.getString(23);
			
			LOG.info("OutPut Values : "+errorMessage);

		} catch (SQLException e) {
			String erromsg = "EX_CHK_BENEFICIARY" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				String erromsg = "EX_CHK_BENEFICIARY" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}

		return errorMessage;
	}

	@Override
	public List<BeneficaryAccount> getbeneAccountDetailsWithAccNo(BigDecimal benificaryMasterId, BigDecimal bankId,	BigDecimal branchId, String accountNumber, BigDecimal currencyId,BigDecimal benificaryAccSeqId) throws AMGException {


		LOG.info("Entering into getbeneAccountDetailsWithAccNo method");
		LOG.info("masterseqId " + benificaryMasterId);
		List<BeneficaryAccount> acclist = null;

		
		try{
			DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
			
			criteria.add(Restrictions.eq("beneficaryAccount.beneficaryAccountSeqId", benificaryAccSeqId));
			
			if(benificaryMasterId != null){
				criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
				criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
				criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", benificaryMasterId));
			}

			if(bankId != null){
				criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
				criteria.createAlias("beneficaryAccount.bank", "bank", JoinType.INNER_JOIN);
				criteria.add(Restrictions.eq("bank.bankId", bankId));
			}

			if(branchId != null){
				criteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
				criteria.createAlias("beneficaryAccount.bankBranch", "bankBranch", JoinType.INNER_JOIN);
				criteria.add(Restrictions.eq("bankBranch.bankBranchId", branchId));
			}

			if(currencyId != null){
				criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
				criteria.createAlias("beneficaryAccount.currencyId", "currencyId", JoinType.INNER_JOIN);
				criteria.add(Restrictions.eq("currencyId.currencyId", currencyId));
			}

			if(accountNumber != null){
				criteria.add(Restrictions.eq("beneficaryAccount.bankAccountNumber", accountNumber));
			}else{
				criteria.add(Restrictions.isNull("beneficaryAccount.bankAccountNumber"));
			}

			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			acclist = (List<BeneficaryAccount>) findAll(criteria);
			LOG.info("Exit into getbeneAccountDetailsWithAccNo method");
			
		}catch (Exception e) {
			String erromsg = "EX_CHK_BENEFICIARY" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		}
		
		return acclist;
	}

	@Override
	public List<BeneficaryAccount> getbeneAccountDetailsForCashWU(BigDecimal benificaryMasterId, BigDecimal bankId,	BigDecimal branchId, BigDecimal currencyId,	BigDecimal benificaryAccSeqId, BigDecimal serviceProvider) throws AMGException {


		LOG.info("Entering into getbeneAccountDetailsWithAccNo method");
		LOG.info("masterseqId " + benificaryMasterId);
		List<BeneficaryAccount> acclist = null;

		
		try{
			DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
			
			criteria.add(Restrictions.eq("beneficaryAccount.beneficaryAccountSeqId", benificaryAccSeqId));
			
			if(benificaryMasterId != null){
				criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
				criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
				criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", benificaryMasterId));
			}

			if(bankId != null){
				criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
				criteria.createAlias("beneficaryAccount.bank", "bank", JoinType.INNER_JOIN);
				criteria.add(Restrictions.eq("bank.bankId", bankId));
			}

			if(branchId != null){
				criteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
				criteria.createAlias("beneficaryAccount.bankBranch", "bankBranch", JoinType.INNER_JOIN);
				criteria.add(Restrictions.eq("bankBranch.bankBranchId", branchId));
			}

			if(currencyId != null){
				criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
				criteria.createAlias("beneficaryAccount.currencyId", "currencyId", JoinType.INNER_JOIN);
				criteria.add(Restrictions.eq("currencyId.currencyId", currencyId));
			}

			if(serviceProvider != null){
				criteria.setFetchMode("beneficaryAccount.serviceProvider", FetchMode.JOIN);
				criteria.createAlias("beneficaryAccount.serviceProvider", "serviceProvider", JoinType.INNER_JOIN);
				criteria.add(Restrictions.eq("serviceProvider.bankId", serviceProvider));
			}

			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			acclist = (List<BeneficaryAccount>) findAll(criteria);
			LOG.info("Exit into getbeneAccountDetailsWithAccNo method");

		}catch (Exception e) {
			String erromsg = "EX_CHK_BENEFICIARY" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		}

		return acclist;
	}

	@Override
	public void deleteBeneRelationRecord(BigDecimal beneficiaryRelationShipSeqId, String status) {
		if (beneficiaryRelationShipSeqId != null) {
			BeneficaryRelationship beneRelation = (BeneficaryRelationship) getSession().get(BeneficaryRelationship.class, beneficiaryRelationShipSeqId);
			beneRelation.setIsActive(status);
			beneRelation.setModifiedBy(sessionStateManage.getUserName());
			beneRelation.setModifiedDate(new Date());
			getSession().update(beneRelation);
		}
	}

	@Override
	public void updateBeneficaryDataforWU(BeneficaryMaster beneficaryMaster,BeneficaryAccount account ,BeneficaryRelationship beneficaryRelationship, BeneficaryContact contact) {
		
		getSession().saveOrUpdate(beneficaryMaster);
		account.setBeneficaryMaster(beneficaryMaster);
		getSession().saveOrUpdate(account);
		beneficaryRelationship.setBeneficaryMaster(beneficaryMaster);
		getSession().saveOrUpdate(beneficaryRelationship);
		contact.setBeneficaryMaster(beneficaryMaster);
		getSession().saveOrUpdate(contact);
		
	}

	@Override
	public List<BeneficaryAccount> getBeneAccountDetails(BigDecimal benificaryMasterId, BigDecimal currencyId,String bankCode) {
		LOG.info("Entering into isBankAccountNumberExist method ");
		LOG.info("benificaryMasterId " + benificaryMasterId);
		LOG.info("currencyId " + currencyId);
		LOG.info("bankCode " + bankCode);

		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		
		criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", benificaryMasterId));
		
		criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.currencyId", "currency", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("currency.currencyId", currencyId));
		
		criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.bank", "exBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankCode", bankCode));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryAccount> accountNumberList = (List<BeneficaryAccount>) findAll(criteria);
		
		return accountNumberList;
	}

	@Override
	public List<BankBranchView> getBranchListfromViewByCountryBank(BigDecimal countryId, BigDecimal bankId) {
		LOG.info("Entering into getBranchListfromViewByCountryBank method");
		LOG.info("countryId " + countryId);
		LOG.info("bankId " + bankId);

		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranchView.class, "bankBranchView");
		criteria.add(Restrictions.eq("bankBranchView.countryId", countryId));
		criteria.add(Restrictions.eq("bankBranchView.bankId", bankId));
		criteria.addOrder(Order.asc("bankBranchView.branchFullName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBranchListfromView method");
		
		return (List<BankBranchView>) findAll(criteria);
	}

	@Override
	public BigDecimal getbeneAccountSeqIdForCash(BigDecimal benificaryMasterId,
			BigDecimal bankNameId, BigDecimal branchNameId,
			String accountNumber, BigDecimal currencyId) {

		LOG.info("Entering into getCustomerBeneficaryDetailswithAll method");
		LOG.info("masterseqId " + benificaryMasterId);

		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		if(benificaryMasterId != null){
			criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", benificaryMasterId));
		}

		if(bankNameId != null){
			criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.bank", "bank", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("bank.bankId", bankNameId));
		}

		if(branchNameId != null){
			criteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.bankBranch", "bankBranch", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("bankBranch.bankBranchId", branchNameId));
		}

		if(currencyId != null){
			criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.currencyId", "currencyId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("currencyId.currencyId", currencyId));
		}

		if(accountNumber != null){
			criteria.add(Restrictions.eq("beneficaryAccount.bankAccountNumber", accountNumber));
		}else{
			criteria.add(Restrictions.isNull("beneficaryAccount.bankAccountNumber"));
		}
		
		criteria.add(Restrictions.eq("beneficaryAccount.isActive", Constants.Yes));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryAccount> acclist = (List<BeneficaryAccount>) findAll(criteria);
		LOG.info("Exit into getCustomerBeneficaryDetailswithAll method");

		if(acclist!=null && !acclist.isEmpty())
		{
			return acclist.get(0).getBeneficaryAccountSeqId();
		}

		return null;
	}

}

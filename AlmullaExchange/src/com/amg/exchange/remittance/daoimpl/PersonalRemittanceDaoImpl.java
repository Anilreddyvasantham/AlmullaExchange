package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.beneficiary.model.BanksView;
import com.amg.exchange.beneficiary.model.CustomerRemittanceTransactionView;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.CutomerDetailsView;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjustApp;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.ReceiptPaymentApp;
import com.amg.exchange.foreigncurrency.model.TempCollectDetail;
import com.amg.exchange.foreigncurrency.model.TempCollection;
import com.amg.exchange.foreigncurrency.model.TempForeignCurrencyAdjust;
import com.amg.exchange.intercompany.model.IntraTrnxModel;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.model.CustomerMobileLogModel;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.remittance.bean.AddAdditionalBankData;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.bean.ShoppingCartDataTableBean;
import com.amg.exchange.remittance.dao.IPersonalRemittanceDao;
import com.amg.exchange.remittance.model.AdditionalBankDetailsView;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.AdditionalDataDisplayView;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.AllBeneficiaryView;
import com.amg.exchange.remittance.model.AmlLimit;
import com.amg.exchange.remittance.model.AmtbCoupon;
import com.amg.exchange.remittance.model.AuthicationLimitCheckView;
import com.amg.exchange.remittance.model.AuthorizedLog;
import com.amg.exchange.remittance.model.BankServiceRule;
import com.amg.exchange.remittance.model.BeneServiceExceptionSetup;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryContact;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.BeneficaryStatus;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.CollectionDetailView;
import com.amg.exchange.remittance.model.CollectionPaymentDetailsView;
import com.amg.exchange.remittance.model.CustomerDeclerationView;
import com.amg.exchange.remittance.model.CustomerSpecialDealAppl;
import com.amg.exchange.remittance.model.DebitAutendicationView;
import com.amg.exchange.remittance.model.KnetLog;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.PendingTransferRequestEnquiryView;
import com.amg.exchange.remittance.model.ProductGroup;
import com.amg.exchange.remittance.model.PurposeOfRemittanceView;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.remittance.model.RemitApplAml;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.RemittanceApplicationPurpose;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.remittance.model.RemittanceBranchWiseEnquiryView;
import com.amg.exchange.remittance.model.RoutingDetails;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.remittance.model.ServiceApplicabilityRule;
import com.amg.exchange.remittance.model.ShoppingCartDetails;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.remittance.model.SwiftMaster;
import com.amg.exchange.remittance.model.SwiftMasterView;
import com.amg.exchange.remittance.model.ViewAmtbCoupon;
import com.amg.exchange.remittance.model.ViewBeneServiceCurrency;
import com.amg.exchange.remittance.model.ViewDeliveryModeMap;
import com.amg.exchange.remittance.model.ViewHODirectEFT;
import com.amg.exchange.remittance.model.ViewHODirectInDirect;
import com.amg.exchange.remittance.model.ViewRoutingAgentLocations;
import com.amg.exchange.remittance.model.ViewRoutingAgents;
import com.amg.exchange.remittance.model.ViewServiceRemittance;
import com.amg.exchange.remittance.model.ViewStatesForICASH;
import com.amg.exchange.remittance.model.ViewStock;
import com.amg.exchange.remittance.model.ViewSubAgent;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankIndicatorDescription;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.viewModel.CurrencyMasterView;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository
public class PersonalRemittanceDaoImpl extends CommonDaoImpl implements IPersonalRemittanceDao {
	Logger LOGGER = Logger.getLogger(PersonalRemittanceDaoImpl.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Override
	public CustomerIdProof getCustomerList(String identityType) {
		LOGGER.info("Entering into getCustomerList(String identityType) Method  STARTED ");
		LOGGER.info(" identityType = " + identityType);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		detachedCriteria.add(Restrictions.eq("customerIdProof.identityInt", "485852360725"));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit from  getCustomerList(String identityType) method");
		return ((CustomerIdProof) detachedCriteria.getExecutableCriteria(getSession()).list().get(0));
	}

	@Override
	public List<BeneficaryStatus> getBeneficaryStatusList() {
		LOGGER.info("Entering into getBeneficaryStatusList method");
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryStatus.class, "beneficaryStatus");
		criteria.add(Restrictions.eq("beneficaryStatus.isActive", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from getBeneficaryStatusList method");
		return (List<BeneficaryStatus>) findAll(criteria);
	}

	@Override
	public List<CustomerIdProof> getCustomerDetails(String customerId, BigDecimal cardType) {
		LOGGER.info("Entered into getCustomerDetails(String customerId, BigDecimal cardType) method ");
		LOGGER.info("CustomerId =" + customerId);
		LOGGER.info("cardType =" + cardType);
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		criteria.add(Restrictions.eq("customerIdProof.identityInt", customerId));
		criteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId", cardType));
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.addOrder(Order.desc("customerIdProof.creationDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CustomerIdProof> lstCustomerIdPrrof = (List<CustomerIdProof>) findAll(criteria);
		LOGGER.info("Exited from getCustomerDetails(String customerId, BigDecimal cardType) method");
		return lstCustomerIdPrrof;
	}

	// Added by kani
	@Override
	public List<Relations> getRelationsList() {
		LOGGER.info("Entered into getRelationsList() Method ");
		DetachedCriteria criteria = DetachedCriteria.forClass(Relations.class, "relations");
		criteria.add(Restrictions.eq("relations.isActive", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info(" Exited from   getRelationsList() Method");
		return (List<Relations>) findAll(criteria);
	}

	@Override
	public List<BankMaster> getbanklist(BigDecimal currencyId) {
		LOGGER.info("Entered into getbanklist(BigDecimal currencyId) Method");
		LOGGER.info(" currencyId =" + currencyId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		criteria.setFetchMode("bankMaster.currencyMaster", FetchMode.JOIN);
		criteria.createAlias("bankMaster.currencyMaster", "currencyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info(" Exited from getbanklist(BigDecimal currencyId) Method");
		return (List<BankMaster>) findAll(criteria);
	}

	@Override
	public List<BankBranch> getBankbranchlist(BigDecimal bankId) {
		LOGGER.info("Entered into getBankbranchlist(BigDecimal bankId) Method");
		LOGGER.info("bankId =" + bankId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		criteria.addOrder(Order.asc("bankBranch.branchFullName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited From the getBankbranchlist(BigDecimal bankId) Method ");
		return (List<BankBranch>) findAll(criteria);
	}

	@Override
	public List<CountryMasterDesc> getCountryList(BigDecimal languageId) {
		LOGGER.info("Entered into getCountryList() Method");
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");

		criteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CountryMasterDesc> lstCountry = (List<CountryMasterDesc>) findAll(criteria);

		LOGGER.info("Exited From the getCountryList() Method");
		return lstCountry;
	}

	@Override
	public List<CurrencyMaster> getCurrencyList(BigDecimal countryId) {
		LOGGER.info("Entered into getCurrencyList(BigDecimal countryId) Method");
		LOGGER.info("countryId =" + countryId);
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		criteria.setFetchMode("currencyMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("currencyMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited From the getCurrencyList(BigDecimal countryId) Method");
		return (List<CurrencyMaster>) findAll(criteria);
	}

	// Remittance Save by subramanian
	@Override
	public void saveBeneficaryMaster(BeneficaryMaster beneficaryMaster) {
		getSession().saveOrUpdate(beneficaryMaster);
	}

	@Override
	public void saveBeneficaryRelationship(BeneficaryRelationship beneficaryRelationship) {
		getSession().saveOrUpdate(beneficaryRelationship);
	}

	@Override
	public void saveBeneficaryTelephone(BeneficaryContact beneficaryTelaphone) {
		getSession().saveOrUpdate(beneficaryTelaphone);
	}

	@Override
	public void savebeneficaryAccount(BeneficaryAccount beneficaryAccount) {
		getSession().saveOrUpdate(beneficaryAccount);
	}

	@Override
	public List<BeneficaryAccount> getCustomerBeneficaryDetails(BigDecimal masterseqId) {
		LOGGER.info("Entered into getCustomerBeneficaryDetails(BigDecimal masterseqId) Method");
		LOGGER.info("masterseqId = " + masterseqId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", masterseqId));
		criteria.setFetchMode("beneficaryAccount.servicegropupId", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.servicegropupId", "exServiceId", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.currencyId", "currencyId", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited From the getCustomerBeneficaryDetails(BigDecimal masterseqId) Method");
		List<BeneficaryAccount> acclist = (List<BeneficaryAccount>) findAll(criteria);
		return acclist;
	}

	/*
	 * public BigDecimal getBeneficaryMasterId(BigDecimal customerId) {
	 * DetachedCriteria criteria = DetachedCriteria.forClass(
	 * BeneficaryRelationship.class, "beneficaryRelationship");
	 * criteria.setFetchMode("beneficaryRelationship.customerId",
	 * FetchMode.JOIN);
	 * criteria.createAlias("beneficaryRelationship.customerId", "customerId",
	 * JoinType.INNER_JOIN);
	 * criteria.add(Restrictions.eq("customerId.customerId",customerId ));
	 * 
	 * List<BeneficaryRelationship> beneficaryRship = findAll(criteria);
	 * 
	 * return
	 * beneficaryRship.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId();
	 * 
	 * }
	 */
	@Override
	public List<BeneficaryRelationship> isCoustomerExistInDB(BigDecimal customerId) {
		LOGGER.info("Entered into isCoustomerExistInDB(BigDecimal customerId) Method");
		LOGGER.info("CustomerId = " + customerId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");
		criteria.setFetchMode("beneficaryRelationship.customerId", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.customerId", "customerId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("customerId.customerId", customerId));
		criteria.add(Restrictions.eq("beneficaryMaster.isActive", Constants.Yes));
		criteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryRelationship> beneficaryRship = (List<BeneficaryRelationship>) findAll(criteria);
		LOGGER.info("Relation ship size = " + beneficaryRship.size());
		LOGGER.info("Exited from the isCoustomerExistInDB(BigDecimal customerId) Method ");
		return beneficaryRship;
	}

	@Override
	public List<CustomerIdProof> getCustomerDetails(String customerId) {
		return null;
	}

	@Override
	public List<BeneficaryContact> getTelephoneDetails(BigDecimal masterId) {
		LOGGER.info("Entered into  getTelephoneDetails(BigDecimal masterId) Method");
		LOGGER.info("masterId = " + masterId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "benificiaryTelephone");
		criteria.setFetchMode("benificiaryTelephone.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("benificiaryTelephone.beneficaryMaster", "beneficaryMasterbeneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", masterId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the  getTelephoneDetails(BigDecimal masterId) Method");
		return (List<BeneficaryContact>) findAll(criteria);

	}

	@Override
	public List<BeneficaryMaster> getAllMasterValues(BigDecimal masterId) {
		LOGGER.info("Entered into getAllMasterValues(BigDecimal masterId) Method");
		LOGGER.info("masterId = " + masterId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryMaster.class, "beneficaryMaster");
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", masterId));
		// criteria.setFetchMode("beneficaryMaster.beneficaryStatus",
		// FetchMode.JOIN);
		// criteria.createAlias("beneficaryMaster.beneficaryStatus",
		// "beneficaryStatus", JoinType.INNER_JOIN);

		criteria.setFetchMode("beneficaryMaster.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("beneficaryMaster.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);

		criteria.setFetchMode("beneficaryMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the getAllMasterValues(BigDecimal masterId) Method");
		return (List<BeneficaryMaster>) findAll(criteria);
	}

	// Modifies to show only Active records for the bug fix by kani begin
	@Override
	public List<PaymentModeDesc> getPaymentModeDetails(BigDecimal languageId) {
		LOGGER.info("Entered into  getPaymentModeDetails(BigDecimal languageId) Method");
		LOGGER.info("languageId = " + languageId);
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
		criteria.setFetchMode("paymentModeDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("paymentModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.createAlias("paymentModeDesc.paymentMode", "paymentMode", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("paymentMode.isActive", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<PaymentModeDesc> size = (List<PaymentModeDesc>) findAll(criteria);
		LOGGER.info("Exited from the getPaymentModeDetails(BigDecimal languageId) Method");
		return size;
	}

	// Modifies to show only Active records for the bug fix by kani End
	@Override
	public List<BankBranch> getALLListBankBranch(BigDecimal countryid, BigDecimal Bankid) {
		LOGGER.info("Entered into  getALLListBankBranch(BigDecimal countryid, BigDecimal Bankid) Method");
		LOGGER.info("countryid = " + countryid);
		LOGGER.info("Bankid = " + Bankid);
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<BankBranch> lstBankBranch = new ArrayList<BankBranch>();
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		criteria.setFetchMode("bankBranch.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryid));
		criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", Bankid));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankBranch> branchsize = (List<BankBranch>) findAll(criteria);
		for (BankBranch bankBranch : branchsize) {
			if (!duplicateCheck.contains(bankBranch.getBankBranchId())) {
				duplicateCheck.add(bankBranch.getBankBranchId());
				lstBankBranch.add(bankBranch);
			}
		}
		LOGGER.info("Exited from the getALLListBankBranch(BigDecimal countryid, BigDecimal Bankid) Method");
		return lstBankBranch;
	}

	@Override
	public List<RoutingDetails> getSpecificListBankBranch(BigDecimal countryid, BigDecimal Bankid) {
		LOGGER.info("Entered into getSpecificListBankBranch(BigDecimal countryid, BigDecimal Bankid) Method");
		LOGGER.info("countryid = " + countryid);
		LOGGER.info("Bankid = " + Bankid);
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<RoutingDetails> lstBankBranch = new ArrayList<RoutingDetails>();
		DetachedCriteria criteria = DetachedCriteria.forClass(RoutingDetails.class, "routingDetails");
		criteria.setFetchMode("routingDetails.exRoutingCountryId", FetchMode.JOIN);
		criteria.createAlias("routingDetails.exRoutingCountryId", "exRoutingCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exRoutingCountryId.countryId", countryid));
		criteria.setFetchMode("routingDetails.exRoutingBankId", FetchMode.JOIN);
		criteria.createAlias("routingDetails.exRoutingBankId", "exRoutingBankId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exRoutingBankId.bankId", Bankid));
		criteria.setFetchMode("routingDetails.exBankBranchId", FetchMode.JOIN);
		criteria.createAlias("routingDetails.exBankBranchId", "exBankBranchId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("routingDetails.isActive", Constants.Yes)); // if
		// Active
		// only
		// View
		// Branch
		// from
		// Routing
		// Details
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RoutingDetails> branchsize = (List<RoutingDetails>) findAll(criteria);
		for (RoutingDetails routingDetails : branchsize) {
			if (!duplicateCheck.contains(routingDetails.getExBankBranchId().getBankBranchId())) {
				duplicateCheck.add(routingDetails.getExBankBranchId().getBankBranchId());
				lstBankBranch.add(routingDetails);
			}
		}
		LOGGER.info("Exited From the getSpecificListBankBranch(BigDecimal countryid, BigDecimal Bankid) Method");
		return lstBankBranch;
	}

	@Override
	public List<BigDecimal> getExchangeRateAllValues(BigDecimal appcountryId, BigDecimal routingcountry, BigDecimal branchId, BigDecimal companyId,
			BigDecimal routingbankId, BigDecimal serviceId, BigDecimal deliveryId, BigDecimal remitId, BigDecimal foreigncurrencyId,
			BigDecimal currencyId, BigDecimal customerId, String customertypeId, String loyaltypt, String spldeal, String oversea,
			BigDecimal fcamount, String spotrate, String cashround) throws AMGException {
		LOGGER.info("Entered into getExchangeRateAllValues()  Method Called ");
		LOGGER.info("Procedure Name =  EX_GET_EXCHANGE_RATE ");
		LOGGER.info("getExchangeRateAllValues() Method Called ");
		LOGGER.info("!!!!!!appcountryId!!!!!!!!!" + appcountryId);
		LOGGER.info("!!!!!!routingcountry!!!!!!!!!" + routingcountry);
		LOGGER.info("!!!!!!branchId!!!!!!!!!" + branchId);
		LOGGER.info("!!!!!!companyId!!!!!!!!!" + companyId);
		LOGGER.info("!!!!!!routingbankId!!!!!!!!!" + routingbankId);
		LOGGER.info("!!!!!!serviceId!!!!!!!!!" + serviceId);
		LOGGER.info("!!!!!!deliveryId!!!!!!!!!" + deliveryId);
		LOGGER.info("!!!!!!remitId!!!!!!!!!" + remitId);
		LOGGER.info("!!!!!!foreigncurrencyId!!!!!!!!!" + foreigncurrencyId);
		LOGGER.info("!!!!!!selectedcurrencyId!!!!!!!!!" + currencyId);
		LOGGER.info("!!!!!!customerId!!!!!!!!!" + customerId);
		LOGGER.info("!!!!!!customertypeId!!!!!!!!!" + customertypeId);
		LOGGER.info("!!!!!!loyaltypt!!!!!!!!!" + loyaltypt);
		LOGGER.info("!!!!!!spldeal!!!!!!!!!" + spldeal);
		LOGGER.info("!!!!!!oversea!!!!!!!!!" + oversea);
		LOGGER.info("!!!!!!fcamount!!!!!!!!!" + fcamount);
		LOGGER.info("!!!!!!spotrate!!!!!!!!!" + spotrate);
		LOGGER.info("!!!!!!cashround!!!!!!!!!" + cashround);
		List<BigDecimal> outLst = new ArrayList<BigDecimal>();
		Connection connection = null;
		try {
			// connection = DBConnection.getdataconnection();
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { call EX_GET_EXCHANGE_RATE_P (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, appcountryId);
			cs.setBigDecimal(2, routingcountry);
			cs.setBigDecimal(3, branchId);
			cs.setBigDecimal(4, companyId);
			cs.setBigDecimal(5, routingbankId);
			cs.setBigDecimal(6, serviceId);
			cs.setBigDecimal(7, deliveryId);
			cs.setBigDecimal(8, remitId);
			cs.setBigDecimal(9, foreigncurrencyId);
			cs.setBigDecimal(10, currencyId);
			cs.setBigDecimal(11, customerId);
			cs.setString(12, customertypeId);
			cs.setString(13, loyaltypt);
			cs.setString(14, spldeal);
			cs.setString(15, oversea);
			cs.setBigDecimal(16, fcamount);
			cs.setString(17, spotrate);
			cs.setString(18, cashround);
			cs.registerOutParameter(19, java.sql.Types.INTEGER);
			cs.registerOutParameter(20, java.sql.Types.INTEGER);
			cs.registerOutParameter(21, java.sql.Types.INTEGER);
			cs.registerOutParameter(22, java.sql.Types.INTEGER);
			cs.registerOutParameter(23, java.sql.Types.INTEGER);
			cs.registerOutParameter(24, java.sql.Types.INTEGER);
			cs.registerOutParameter(25, java.sql.Types.INTEGER);
			cs.execute();// teUpdate();
			BigDecimal out1 = cs.getBigDecimal(19);
			BigDecimal out2 = cs.getBigDecimal(20);
			BigDecimal out3 = cs.getBigDecimal(21);
			BigDecimal out4 = cs.getBigDecimal(22);
			BigDecimal out5 = cs.getBigDecimal(23);
			BigDecimal out6 = cs.getBigDecimal(24);
			BigDecimal out7 = cs.getBigDecimal(25);
			outLst.add(out1);
			outLst.add(out2);
			outLst.add(out3);
			outLst.add(out4);
			outLst.add(out5);
			outLst.add(out6);
			outLst.add(out7);
		} catch (SQLException e) {
			LOGGER.info("Problem Occured When Procedure Calling=" + e.getMessage());
			String erromsg = "EX_GET_EXCHANGE_RATE" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Problem Occured When Procedure Calling=" + e.getMessage());
				String erromsg = "EX_GET_EXCHANGE_RATE" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		LOGGER.info("!!!!!!outLst out!!!!!!!!!" + outLst);
		LOGGER.info("Exited from the getExchangeRateAllValues()  Method ");
		return outLst;
	}

	@Override
	public void saveSpecialRateRequest(SpecialRateRequest specialRateRequest) {
		getSession().saveOrUpdate(specialRateRequest);
	}

	@Override
	public Map<String, ServiceApplicabilityRule> getComponentBehavior(List<String> componentList, BigDecimal applicationCountryId,
			BigDecimal currencyId, BigDecimal remittanceModeId, BigDecimal deliveryModeId) {
		LOGGER.info("Entered into getComponentBehavior(List<String> componentList, BigDecimal applicationCountryId, BigDecimal currencyId, BigDecimal remittanceModeId, BigDecimal deliveryModeId) Method");
		LOGGER.info("componentList =" + componentList.size());
		LOGGER.info("applicationCountryId =" + applicationCountryId);
		LOGGER.info("currencyId =" + currencyId);
		LOGGER.info("remittanceModeId =" + remittanceModeId);
		LOGGER.info("deliveryModeId =" + deliveryModeId);
		String alias = "fsbusiness1_";
		Map<String, ServiceApplicabilityRule> mapReturn = new HashMap<String, ServiceApplicabilityRule>();
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceApplicabilityRule.class, "serviceApplicabilityRule");
		criteria.setFetchMode("serviceApplicabilityRule.fsBusinessComponentConf", FetchMode.JOIN);
		criteria.createAlias("serviceApplicabilityRule.fsBusinessComponentConf", "fsBusinessComponentConf", JoinType.INNER_JOIN);
		// Left join Country Master
		criteria.setFetchMode("serviceApplicabilityRule.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("serviceApplicabilityRule.applicationCountryId", "applicationCountryId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("countryId", applicationCountryId));
		// Left join page master
		criteria.setFetchMode("serviceApplicabilityRule.currencyId", FetchMode.JOIN);
		criteria.createAlias("serviceApplicabilityRule.currencyId", "currencyId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("currencyId", currencyId));
		criteria.setFetchMode("serviceApplicabilityRule.remittanceModeId", FetchMode.JOIN);
		criteria.createAlias("serviceApplicabilityRule.remittanceModeId", "remittanceModeId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeId.remittanceModeId", remittanceModeId));
		criteria.setFetchMode("serviceApplicabilityRule.deliveryModeId", FetchMode.JOIN);
		criteria.createAlias("serviceApplicabilityRule.deliveryModeId", "remittanceModeId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("deliveryModeId.deliveryModeId", deliveryModeId));
		LOGGER.info("Exited from  getComponentBehavior(List<String> componentList, BigDecimal applicationCountryId, BigDecimal currencyId, BigDecimal remittanceModeId, BigDecimal deliveryModeId) Method");
		return mapReturn;
	}

	@Override
	public List<ServiceApplicabilityRule> getDynamicLevel(BigDecimal applicationCountryId, BigDecimal countryId, BigDecimal currencyId,
			BigDecimal remittanceModeId, BigDecimal deliveryModeId) {
		LOGGER.info("Entered into getDynamicLevel(BigDecimal applicationCountryId, BigDecimal countryId, BigDecimal currencyId, BigDecimal remittanceModeId, BigDecimal deliveryModeId) Method");
		LOGGER.info("applicationCountryId = " + applicationCountryId);
		LOGGER.info("countryId = " + countryId);
		LOGGER.info("currencyId = " + currencyId);
		LOGGER.info("remittanceModeId = " + remittanceModeId);
		LOGGER.info("deliveryModeId = " + deliveryModeId);

		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceApplicabilityRule.class, "serviceApplicabilityRule");
		// Left join Country Master
		criteria.setFetchMode("serviceApplicabilityRule.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("serviceApplicabilityRule.applicationCountryId", "applicationCountryId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("applicationCountryId.countryId", applicationCountryId));
		criteria.setFetchMode("serviceApplicabilityRule.countryId", FetchMode.JOIN);
		criteria.createAlias("serviceApplicabilityRule.countryId", "countryId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId", countryId));
		criteria.add(Restrictions.eq("isActive", Constants.Yes));
		// Left join page master
		/*
		 * criteria.setFetchMode("serviceApplicabilityRule.currencyId",
		 * FetchMode.JOIN);
		 * criteria.createAlias("serviceApplicabilityRule.currencyId",
		 * "currencyId", JoinType.LEFT_OUTER_JOIN);
		 * criteria.add(Restrictions.eq("currencyId.currencyId", currencyId));
		 */
		criteria.setFetchMode("serviceApplicabilityRule.remittanceModeId", FetchMode.JOIN);
		criteria.createAlias("serviceApplicabilityRule.remittanceModeId", "remittanceModeId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeId.remittanceModeId", remittanceModeId));
		criteria.setFetchMode("serviceApplicabilityRule.deliveryModeId", FetchMode.JOIN);
		criteria.createAlias("serviceApplicabilityRule.deliveryModeId", "deliveryModeId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("deliveryModeId.deliveryModeId", deliveryModeId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the getDynamicLevel(BigDecimal applicationCountryId, BigDecimal countryId, BigDecimal currencyId, BigDecimal remittanceModeId, BigDecimal deliveryModeId) Method");
		return (List<ServiceApplicabilityRule>) findAll(criteria);
	}

	@Override
	public List<CustomerSpecialDealRequest> getSpecialRequestFcAmountCalList(BigDecimal customerId, BigDecimal applicationCountryId,
			BigDecimal bankId, BigDecimal bankCountryId) {
		LOGGER.info("Entered into getSpecialRequestFcAmountCalList(BigDecimal customerId, BigDecimal applicationCountryId, BigDecimal bankId, BigDecimal bankCountryId) Method  ");
		LOGGER.info("customerId = " + customerId);
		LOGGER.info("applicationCountryId =" + applicationCountryId);
		LOGGER.info("bankId = " + bankId);
		LOGGER.info("bankCountryId =" + bankCountryId);
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerSpecialDealRequest.class, "customerSpecialDealRequest");
		criteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCustomer", FetchMode.JOIN);
		criteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCustomer", "customerSpeacialDealReqCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("customerSpeacialDealReqCustomer.customerId", customerId));
		criteria.setFetchMode("customerSpecialDealRequest.applicationCountryCountryMaster", FetchMode.JOIN);
		criteria.createAlias("customerSpecialDealRequest.applicationCountryCountryMaster", "applicationCountryCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("applicationCountryCountryMaster.countryId", applicationCountryId));
		criteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqBankMaster", FetchMode.JOIN);
		criteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqBankMaster", "customerSpeacialDealReqBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("customerSpeacialDealReqBankMaster.bankId", bankId));
		criteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCountryMaster", FetchMode.JOIN);
		criteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCountryMaster", "customerSpeacialDealReqCountryMaster",
				JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("customerSpeacialDealReqCountryMaster.countryId", bankCountryId));
		criteria.setFetchMode("customerSpecialDealRequest.documentFinancialYear", FetchMode.JOIN);
		criteria.createAlias("customerSpecialDealRequest.documentFinancialYear", "documentFinancialYear", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("customerSpecialDealRequest.fundingOption", "S"));

		criteria.add(Restrictions.isNotNull("customerSpecialDealRequest.dealCompanyId"));
		criteria.add(Restrictions.isNotNull("customerSpecialDealRequest.dealId"));
		criteria.add(Restrictions.isNotNull("customerSpecialDealRequest.dealFinanceYear"));
		criteria.add(Restrictions.isNotNull("customerSpecialDealRequest.dealApplicationNumber"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CustomerSpecialDealRequest> lstdata = (List<CustomerSpecialDealRequest>) findAll(criteria);
		LOGGER.info("Exited from the getSpecialRequestFcAmountCalList(BigDecimal customerId, BigDecimal applicationCountryId, BigDecimal bankId, BigDecimal bankCountryId) Method ");
		return lstdata;
	}

	@Override
	public void calculateRemittanceAmount(BigDecimal specialCustomerPk, BigDecimal utilizedAmount) {
		CustomerSpecialDealRequest cusSpDeal = (CustomerSpecialDealRequest) getSession().get(CustomerSpecialDealRequest.class, specialCustomerPk);
		cusSpDeal.setUtilizedAmount(utilizedAmount);
		getSession().update(cusSpDeal);
	}

	@Override
	public List<BeneficaryAccount> getBeneficaryAccountDetails(BigDecimal beneficiaryMasterSeqId) {
		LOGGER.info("Entered into getBeneficaryAccountDetails(BigDecimal beneficiaryMasterSeqId) Method ");
		LOGGER.info("beneficiaryMasterSeqId =" + beneficiaryMasterSeqId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficiaryMasterSeqId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryAccount> acclist = (List<BeneficaryAccount>) findAll(criteria);
		LOGGER.info("Exited from the getBeneficaryAccountDetails(BigDecimal beneficiaryMasterSeqId) Method");
		return acclist;
	}

	@Override
	public List<BeneficaryContact> isCoustomerTelephoneExistInDB(BigDecimal telephoneNumber) {
		LOGGER.info("Entered into isCoustomerTelephoneExistInDB(BigDecimal telephoneNumber) Method");
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "beneficaryTelaphone");
		criteria.add(Restrictions.eq("beneficaryTelaphone.countryTelephoneNumber", telephoneNumber.toPlainString()));
		List<BeneficaryContact> beneficaryTelaphone = (List<BeneficaryContact>) findAll(criteria);
		LOGGER.info("beneficaryTelaphone =" + beneficaryTelaphone.size());
		LOGGER.info("Exited from  isCoustomerTelephoneExistInDB(BigDecimal telephoneNumber) Method");
		return beneficaryTelaphone;
	}

	@Override
	public List<BeneficaryMaster> getBeneficaryMasterDetails(BigDecimal beneficiaryMasterSeqId) {
		LOGGER.info("Entered into  getBeneficaryMasterDetails(BigDecimal beneficiaryMasterSeqId) Method");
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryMaster.class, "beneficaryMaster");
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficiaryMasterSeqId));
		List<BeneficaryMaster> acclist = (List<BeneficaryMaster>) findAll(criteria);
		LOGGER.info("Exited from the getBeneficaryMasterDetails(BigDecimal beneficiaryMasterSeqId) Method");
		return acclist;
	}

	@Override
	public List<BeneficaryRelationship> isBenificaryRelationExistInDB(BigDecimal beneficaryMasterId) {
		LOGGER.info("Entered into isBenificaryRelationExistInDB(BigDecimal beneficaryMasterId) Method ");
		LOGGER.info("beneficaryMasterId = " + beneficaryMasterId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");
		criteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficaryMasterId));
		criteria.add(Restrictions.eq("beneficaryMaster.isActive", Constants.Yes));
		// criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryRelationship> beneficaryRship = (List<BeneficaryRelationship>) findAll(criteria);
		LOGGER.info("Exited from isBenificaryRelationExistInDB(BigDecimal beneficaryMasterId) Method ");
		return beneficaryRship;
	}

	@Override
	public List<BeneficaryContact> isTelephoneExistInDB(BigDecimal telephoneNumber) {
		LOGGER.info("Entered into isTelephoneExistInDB(BigDecimal telephoneNumber) Method ");
		LOGGER.info("telephoneNumber = " + telephoneNumber);
		String telephoneNo = telephoneNumber.toPlainString();
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "beneficaryTelaphone");
		criteria.add(Restrictions.eq("beneficaryTelaphone.countryTelephoneNumber", telephoneNo));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryContact> beneficaryTelaphone = (List<BeneficaryContact>) findAll(criteria);
		LOGGER.info("size = " + beneficaryTelaphone.size());
		LOGGER.info("Exited from the isTelephoneExistInDB(BigDecimal telephoneNumber) Method");
		return beneficaryTelaphone;
	}

	@Override
	public void saveRemittanceApplication(RemittanceApplication remittanceApplication) {
		getSession().saveOrUpdate(remittanceApplication);
	}

	@Override
	public List<RemittanceApplication> getRemittanceApplicationAllDetails(BigDecimal customerid) {
		// String telephoneNo = telephoneNumber.toPlainString();
		LOGGER.info("Entered into getRemittanceApplicationAllDetails(BigDecimal customerid) Method");
		LOGGER.info("customerid = " + customerid);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplication.class, "remittanceApplication");
		criteria.setFetchMode("remittanceApplication.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerid));
		criteria.setFetchMode("remittanceApplication.fsCountryMasterByBankCountryId", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCountryMasterByBankCountryId", "fsCountryMasterByBankCountryId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceApplication.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceApplication.exCurrencyMasterByForeignCurrencyId", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.exCurrencyMasterByForeignCurrencyId", "exCurrencyMasterByForeignCurrencyId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceApplication.exRemittanceAppBenificiary", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.exRemittanceAppBenificiary", "exRemittanceAppBenificiary", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceApplication> remitapp = (List<RemittanceApplication>) findAll(criteria);
		LOGGER.info(" remitapp::::::::::Size=" + remitapp.size());
		return remitapp;
	}

	@Override
	public List<BeneficaryAccount> isBankAccountNumberExistInDb(String bankAccountNumber, BigDecimal countryId, BigDecimal BankId,
			BigDecimal bankBranchId) {
		LOGGER.info("Entered into isBankAccountNumberExistInDb(String bankAccountNumber, BigDecimal countryId, BigDecimal BankId, BigDecimal bankBranchId) Method ");
		LOGGER.info("bankAccountNumber = " + bankAccountNumber);
		LOGGER.info("countryId = " + countryId);
		LOGGER.info("BankId = " + BankId);
		LOGGER.info("bankBranchId = " + bankBranchId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		if (bankAccountNumber != null) {
			criteria.add(Restrictions.eq("beneficaryAccount.bankAccountNumber", bankAccountNumber));
		}
		if (countryId != null) {
			criteria.setFetchMode("beneficaryAccount.beneficaryCountry", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.beneficaryCountry", "beneficaryCountry", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("beneficaryCountry.countryId", countryId));
		}
		if (BankId != null) {
			criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.bank", "exBankMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("exBankMaster.bankId", BankId));
		}
		if (bankBranchId != null) {
			criteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.bankBranch", "exBankBranch", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("exBankBranch.bankBranchId", bankBranchId));
		}

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryAccount> accountNumberList = (List<BeneficaryAccount>) findAll(criteria);
		LOGGER.info("Exited from the isBankAccountNumberExistInDb(String bankAccountNumber, BigDecimal countryId, BigDecimal BankId, BigDecimal bankBranchId) Method ");
		return accountNumberList;
	}

	@Override
	public List<BeneficaryAccount> isBeneficaryAccountExistInDb(BigDecimal beneficaryMasterSeqId) {
		LOGGER.info("Entered into isBeneficaryAccountExistInDb(BigDecimal beneficaryMasterSeqId) Method");
		LOGGER.info("beneficaryMasterSeqId=" + beneficaryMasterSeqId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryAccountDetail", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryAccountDetail.beneficaryMasterSeqId", beneficaryMasterSeqId));
		List<BeneficaryAccount> accountDetailList = (List<BeneficaryAccount>) findAll(criteria);
		LOGGER.info("Exited from the  isBeneficaryAccountExistInDb(BigDecimal beneficaryMasterSeqId) Method");
		return accountDetailList;
	}

	@Override
	public List<RemittanceModeDescription> listRemittanceDesc(BigDecimal langId) {
		LOGGER.info("Entered into the  listRemittanceDesc(BigDecimal langId) Method ");
		LOGGER.info("languageId = " + langId);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");
		criteria.setFetchMode("remittanceModeDescription.languageType", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", langId));
		criteria.setFetchMode("remittanceModeDescription.remittanceModeMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.remittanceModeMaster", "remittanceModeMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeMaster.isActive", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the listRemittanceDesc(BigDecimal langId) Method ");
		return (List<RemittanceModeDescription>) findAll(criteria);
	}

	@Override
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal langId) {
		LOGGER.info("Entered into the  lstDeliveryMode(BigDecimal langId) Method  ");
		LOGGER.info("LanguageId= " + langId);
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliverymodedesc");
		criteria.setFetchMode("deliverymodedesc.languageType", FetchMode.JOIN);
		criteria.createAlias("deliverymodedesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", langId));
		criteria.setFetchMode("deliverymodedesc.deliveryMode", FetchMode.JOIN);
		criteria.createAlias("deliverymodedesc.deliveryMode", "deliveryMode", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("deliveryMode.isActive", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the  lstDeliveryMode(BigDecimal langId) Method");
		return (List<DeliveryModeDesc>) findAll(criteria);
	}

	@Override
	public BigDecimal getUtilizedAmount(BigDecimal customerId, BigDecimal applicationCountryId, BigDecimal bankId, BigDecimal bankCountryId) {
		LOGGER.info("Entered into getUtilizedAmount(BigDecimal customerId, BigDecimal applicationCountryId, BigDecimal bankId, BigDecimal bankCountryId)  Method ");
		LOGGER.info("customerId =" + customerId);
		LOGGER.info("applicationCountryId = " + applicationCountryId);
		LOGGER.info("bankId = " + bankId);
		LOGGER.info("bankCountryId = " + bankCountryId);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplication.class, "remittanceApplication");
		criteria.setFetchMode("remittanceApplication.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.setFetchMode("remittanceApplication.fsCountryMasterByApplicationCountryId", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCountryMasterByApplicationCountryId", "fsCountryMasterByApplicationCountryId",
				JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMasterByApplicationCountryId.countryId", applicationCountryId));
		criteria.setFetchMode("remittanceApplication.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		criteria.setFetchMode("remittanceApplication.fsCountryMasterByBankCountryId", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCountryMasterByBankCountryId", "fsCountryMasterByBankCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMasterByBankCountryId.countryId", bankCountryId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceApplication> remittanceApplicationlist = findAll(criteria);
		int utilizedAmount = 0;
		for (RemittanceApplication remittanceApplication : remittanceApplicationlist) {
			// utilizedAmount = utilizedAmount +
			// (remittanceApplication.getUtilizedAmount()==null ? new
			// BigDecimal(0) :
			// remittanceApplication.getUtilizedAmount()).intValue();
		}
		return new BigDecimal(utilizedAmount);
	}

	@Override
	public void saveRemittanceAppBeneficiary(RemittanceAppBenificiary remittanceAppBen) {
		getSession().saveOrUpdate(remittanceAppBen);
	}

	@Override
	public void saveAdditionalInsData(AdditionalInstructionData additionalInsData) {
		getSession().saveOrUpdate(additionalInsData);
	}

	@Override
	public void saveRemitAppAml(RemitApplAml remitAppAml) {
		getSession().saveOrUpdate(remitAppAml);
	}

	@Override
	public List<AdditionalBankRuleMap> getDynamicLevelMatch(BigDecimal countryId, String flexiField) {
		LOGGER.info("Entered into getDynamicLevelMatch(BigDecimal countryId, String flexiField) Method");
		LOGGER.info("countryId = " + countryId);
		LOGGER.info("flexiField = " + flexiField);
		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalBankRuleMap.class, "additionalMap");
		criteria.setFetchMode("additionalMap.countryId", FetchMode.JOIN);
		criteria.createAlias("additionalMap.countryId", "countryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId", countryId));
		criteria.add(Restrictions.eq("flexField", flexiField));
		criteria.add(Restrictions.eq("isActive", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the getDynamicLevelMatch(BigDecimal countryId, String flexiField) Method");
		return (List<AdditionalBankRuleMap>) findAll(criteria);
	}

	/*
	 * @Override public List<BeneficaryRelationship>
	 * isBeneficaryAccountCustomerExistInDb(BigDecimal
	 * beneficaryMasterSeqId,BigDecimal customerId){
	 * 
	 * DetachedCriteria criteria =
	 * DetachedCriteria.forClass(BeneficaryRelationship
	 * .class,"beneficaryRelationship");
	 * 
	 * criteria.setFetchMode("beneficaryRelationship.customerId",FetchMode.JOIN);
	 * criteria.createAlias("beneficaryRelationship.customerId",
	 * "customerId",JoinType.INNER_JOIN);
	 * criteria.add(Restrictions.eq("customerId.customerId", customerId));
	 * 
	 * criteria.setFetchMode("beneficaryRelationship.beneficaryMaster",FetchMode.
	 * JOIN); criteria.createAlias("beneficaryRelationship.beneficaryMaster",
	 * "beneficaryRelationship", JoinType.INNER_JOIN);
	 * 
	 * criteria.add(Restrictions.eq("beneficaryAccountDetail.beneficaryMasterSeqId"
	 * , beneficaryMasterSeqId));
	 * 
	 * List<BeneficaryRelationship> accountDetailList =
	 * (List<BeneficaryRelationship>) findAll(criteria);
	 * 
	 * return accountDetailList;
	 * 
	 * }
	 */
	// get shopping cart details by subramanian
	@Override
	public List<ShoppingCartDetails> getShoppingCartDetails(BigDecimal customerNo) {
		LOGGER.info("Entered into getShoppingCartDetails(BigDecimal customerNo) Method");
		LOGGER.info("customerNo =" + customerNo);
		DetachedCriteria criteria = DetachedCriteria.forClass(ShoppingCartDetails.class, "shoppingCart");
		criteria.add(Restrictions.eq("shoppingCart.customerId", customerNo));
		criteria.addOrder(Order.asc("shoppingCart.applicationId"));
		List<ShoppingCartDetails> shoppingCartDetails = (List<ShoppingCartDetails>) findAll(criteria);
		LOGGER.info("Exited from the getShoppingCartDetails(BigDecimal customerNo) ");
		return shoppingCartDetails;
	}

	@Override
	public void saveCollectTableData(Collect collect) {
		getSession().saveOrUpdate(collect);
	}

	@Override
	public void saveCollectDetailTableData(CollectDetail collectDetail) {
		getSession().saveOrUpdate(collectDetail);
	}

	@Override
	public List<SpecialRateRequest> getDocumentSeriality() {
		LOGGER.info("Entered into getDocumentSeriality()  Method ");
		DetachedCriteria criteria = DetachedCriteria.forClass(SpecialRateRequest.class, "specialRateRequest");
		return findAll(criteria);
	}

	@Override
	public void updateReceiptPaymentTableData(BigDecimal receiptPaymentPk, String status) {
		ReceiptPaymentApp receiptPaymentApp = (ReceiptPaymentApp) getSession().get(ReceiptPaymentApp.class, receiptPaymentPk);
		receiptPaymentApp.setApplicationStatus(status);
		getSession().update(receiptPaymentApp);
	}

	@Override
	public void updateRemittanceApplicationTableData(BigDecimal remitanceAppPk, String status) {
		RemittanceApplication remittanceApplication = (RemittanceApplication) getSession().get(RemittanceApplication.class, remitanceAppPk);
		remittanceApplication.setApplicaitonStatus(status);
		if (status == null) {
			remittanceApplication.setTransactionFinancialyear(null);
			remittanceApplication.setTransactionDocumentNo(null);
			remittanceApplication.setBlackListIndicator(null);
		}

		getSession().update(remittanceApplication);
	}

	@Override
	public void updateForeignCurrencyAdjustAppTableData(BigDecimal foreignCurrencyAdjustAppPk, String status) {
		ForeignCurrencyAdjustApp foreignCurrencyAdjustApp = (ForeignCurrencyAdjustApp) getSession().get(ForeignCurrencyAdjustApp.class,
				foreignCurrencyAdjustAppPk);
		foreignCurrencyAdjustApp.setApplicationStatus(status);
		getSession().update(foreignCurrencyAdjustApp);
	}

	@Override
	public BigDecimal getReceiptPaymentTablePk(BigDecimal customerId, BigDecimal documentNo) {
		LOGGER.info("Entered into getReceiptPaymentTablePk(BigDecimal customerId, BigDecimal documentNo) Method");
		LOGGER.info("customerId =" + customerId);
		LOGGER.info("documentNo =" + documentNo);
		DetachedCriteria criteria = DetachedCriteria.forClass(ReceiptPaymentApp.class, "receiptPaymentApp");
		/*
		 * criteria.setFetchMode("receiptPaymentApp.fsCustomer",
		 * FetchMode.JOIN); criteria.createAlias("receiptPaymentApp.fsCustomer",
		 * "fsCustomer", JoinType.INNER_JOIN);
		 */
		criteria.add(Restrictions.eq("receiptPaymentApp.customerId", customerId));
		criteria.add(Restrictions.eq("receiptPaymentApp.documentNo", documentNo));
		List<ReceiptPaymentApp> receiptPaymentList = findAll(criteria);
		LOGGER.info("Exited from getReceiptPaymentTablePk(BigDecimal customerId, BigDecimal documentNo) Method");
		return receiptPaymentList.get(0).getReceiptId();
	}

	/*@Override
	public BigDecimal getRemittanceApplicationPk(BigDecimal customerId, BigDecimal documentNo) {
		LOGGER.info("Entered into getRemittanceApplicationPk(BigDecimal customerId, BigDecimal documentNo) Method ");
		LOGGER.info("customerId = " + customerId);
		LOGGER.info("documentNo = " + documentNo);

		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplication.class, "remittanceApplication");
		criteria.setFetchMode("remittanceApplication.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.add(Restrictions.eq("remittanceApplication.documentNo", documentNo));
		List<RemittanceApplication> remittanceAppList = findAll(criteria);
		LOGGER.info("Exited getRemittanceApplicationPk(BigDecimal customerId, BigDecimal documentNo) Method ");
		return remittanceAppList.get(0).getRemittanceApplicationId();
	}*/

	@Override
	public void saveForeignCurrencyAdjust(ForeignCurrencyAdjust foreignCurrencyAdjust) {
		getSession().saveOrUpdate(foreignCurrencyAdjust);
	}

	@Override
	public List<ForeignCurrencyAdjustApp> getForeignCurrencyAdjustAppPk(BigDecimal customerId, BigDecimal documentNo) {
		LOGGER.info("Entered into  getForeignCurrencyAdjustAppPk(BigDecimal customerId, BigDecimal documentNo) Method");
		LOGGER.info("customerId=" + customerId);
		LOGGER.info("documentNo=" + documentNo);
		DetachedCriteria criteria = DetachedCriteria.forClass(ForeignCurrencyAdjustApp.class, "foreignCurrencyAdjustApp");
		criteria.setFetchMode("foreignCurrencyAdjustApp.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjustApp.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.add(Restrictions.eq("foreignCurrencyAdjustApp.documentNo", documentNo.intValue()));
		List<ForeignCurrencyAdjustApp> foreignCurrencyAdjustAppList = findAll(criteria);
		LOGGER.info("Exit From getForeignCurrencyAdjustAppPk(BigDecimal customerId, BigDecimal documentNo) Method");
		return foreignCurrencyAdjustAppList;
	}

	// Start by subramanian 02/02/2015
	@Override
	public List<Object> isAmlTranxAmountCheck(BigDecimal appcountryId, BigDecimal beneCountryId, BigDecimal customerId, BigDecimal benificiarySeqId,
			BigDecimal fcamount) throws AMGException {
		LOGGER.info("Entered into the isAmlTranxAmountCheck(BigDecimal appcountryId, BigDecimal beneCountryId, BigDecimal customerId, BigDecimal benificiarySeqId, BigDecimal fcamount) Method");
		LOGGER.info("Procedure Name = EX_CUSTOMER_AML_TRNX_CHECK");
		LOGGER.info("Customer Id ---------- > " + customerId);
		LOGGER.info("AppCountry Id ------------> " + appcountryId);
		LOGGER.info("fc amount -------------- > " + fcamount);
		int customer_id = customerId.intValue();
		int country_id = appcountryId.intValue();
		int fc_amount = fcamount.intValue();
		int beneCountry = beneCountryId.intValue();
		int beneseqId = benificiarySeqId.intValue();
		int isFlag;
		LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK appcountryId :" + appcountryId);
		LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK customerId :" + customerId);
		LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK beneCountryId :" + beneCountryId);
		LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK benificiarySeqId :" + benificiarySeqId);
		LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK fcamount :" + fcamount);
		@SuppressWarnings("rawtypes")
		List list = new ArrayList();
		Connection connection = null;
		try {
			// connection = DBConnection.getdataconnection();
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.info("Problem Occured " + e.getMessage());
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { call EX_CUSTOMER_AML_TRNX_CHECK (?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?, "
					+ "?, ?, ?,?, ?," + " ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setInt(1, country_id);
			cs.setInt(2, beneCountry);
			cs.setInt(3, customer_id);
			cs.setInt(4, beneseqId);
			cs.setInt(5, fc_amount);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.registerOutParameter(9, java.sql.Types.VARCHAR);
			cs.registerOutParameter(10, java.sql.Types.VARCHAR);
			cs.registerOutParameter(11, java.sql.Types.VARCHAR);
			cs.registerOutParameter(12, java.sql.Types.INTEGER);
			cs.registerOutParameter(13, java.sql.Types.INTEGER);
			cs.registerOutParameter(14, java.sql.Types.INTEGER);
			cs.registerOutParameter(15, java.sql.Types.INTEGER);
			cs.registerOutParameter(16, java.sql.Types.INTEGER);
			cs.registerOutParameter(17, java.sql.Types.INTEGER);
			cs.registerOutParameter(18, java.sql.Types.INTEGER);
			cs.registerOutParameter(19, java.sql.Types.INTEGER);
			cs.registerOutParameter(20, java.sql.Types.INTEGER);
			cs.registerOutParameter(21, java.sql.Types.INTEGER);
			cs.registerOutParameter(22, java.sql.Types.INTEGER);
			cs.registerOutParameter(23, java.sql.Types.INTEGER);
			cs.registerOutParameter(24, java.sql.Types.INTEGER);
			cs.registerOutParameter(25, java.sql.Types.INTEGER);
			cs.registerOutParameter(26, java.sql.Types.INTEGER);
			cs.registerOutParameter(27, java.sql.Types.INTEGER);
			cs.registerOutParameter(28, java.sql.Types.INTEGER);
			cs.registerOutParameter(29, java.sql.Types.INTEGER);
			// cs.registerOutParameter(30, java.sql.Types.INTEGER);
			cs.execute();
			// isFlag = cs.getInt(8);
			String out1 = cs.getString(6);
			String out2 = cs.getString(7);
			String out3 = cs.getString(8);
			String out4 = cs.getString(9);
			String out5 = cs.getString(10);
			String out6 = cs.getString(11);
			BigDecimal out7 = cs.getBigDecimal(12);
			BigDecimal out8 = cs.getBigDecimal(13);
			BigDecimal out9 = cs.getBigDecimal(14);
			BigDecimal out10 = cs.getBigDecimal(15);
			BigDecimal out11 = cs.getBigDecimal(16);
			BigDecimal out12 = cs.getBigDecimal(17);
			BigDecimal out13 = cs.getBigDecimal(18);
			BigDecimal out14 = cs.getBigDecimal(19);
			BigDecimal out15 = cs.getBigDecimal(20);
			BigDecimal out16 = cs.getBigDecimal(21);
			BigDecimal out17 = cs.getBigDecimal(22);
			BigDecimal out18 = cs.getBigDecimal(23);
			list.add(out1);
			list.add(out2);
			list.add(out3);
			list.add(out4);
			list.add(out5);
			list.add(out6);
			list.add(out7);
			list.add(out8);
			list.add(out9);
			list.add(out10);
			list.add(out11);
			list.add(out12);
			list.add(out13);
			list.add(out14);
			list.add(out15);
			list.add(out16);
			list.add(out17);
			list.add(out18);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out1 :" + out1);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out2 :" + out2);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out3 :" + out3);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out4 :" + out4);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out5 :" + out5);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out6 :" + out6);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out7 :" + out7);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out8 :" + out8);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out9 :" + out9);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out10 :" + out10);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out11 :" + out11);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out12 :" + out12);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out13 :" + out13);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out14 :" + out14);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out15 :" + out15);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out16 :" + out16);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out17 :" + out17);
		} catch (SQLException e) {
			LOGGER.info("Procedure calling time Problem Occured" + e.getMessage());
			String erromsg = "EX_CUSTOMER_AML_TRNX_CHECK" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Procedure calling time Problem Occured" + e.getMessage());
				String erromsg = "EX_CUSTOMER_AML_TRNX_CHECK" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		LOGGER.info("Exited from the  isAmlTranxAmountCheck(BigDecimal appcountryId, BigDecimal beneCountryId, BigDecimal customerId, BigDecimal benificiarySeqId, BigDecimal fcamount) Method");
		return list;
	}

	@Override
	public List<AmlLimit> getAmlLimitCheckList() {
		LOGGER.info("Entered into getAmlLimitCheckList() Method ");
		DetachedCriteria criteria = DetachedCriteria.forClass(AmlLimit.class, "amlLimit");
		List<AmlLimit> objList = findAll(criteria);
		LOGGER.info("Exited from the getAmlLimitCheckList() Method ");
		return objList;
	}

	// end by subramanian 02/02/2015
	@Override
	public List<CurrencyMaster> getCurrencyMasterList(BigDecimal countryId, BigDecimal currencyId) {
		LOGGER.info("Entered into getCurrencyMasterList(BigDecimal countryId, BigDecimal currencyId) Method ");
		LOGGER.info("countryId  =" + countryId);
		LOGGER.info("currencyId = " + currencyId);
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		criteria.setFetchMode("currencyMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("currencyMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.or(Restrictions.eq("fsCountryMaster.countryId", countryId),
				Restrictions.eq("currencyMaster.currencyId", currencyId)));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyMaster> size = findAll(criteria);
		LOGGER.info("Exited from the  getCurrencyMasterList(BigDecimal countryId, BigDecimal currencyId) Method ");
		return size;
	}

	@Override
	public List<AdditionalBankRuleAddData> getAdditionalBankData(BigDecimal bankId, BigDecimal additionalBankFieldId) {
		LOGGER.info("Entered into getAdditionalBankData(BigDecimal bankId, BigDecimal additionalBankFieldId) Method");
		LOGGER.info("bankId =" + bankId);
		LOGGER.info("additionalBankFieldId =" + additionalBankFieldId);

		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalBankRuleAddData.class, "additionalBankRule");
		criteria.setFetchMode("additionalBankRule.additionalBankFieldId", FetchMode.JOIN);
		criteria.createAlias("additionalBankRule.additionalBankFieldId", "additionalBankFieldId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("additionalBankFieldId.additionalBankRuleId", additionalBankFieldId));
		criteria.setFetchMode("additionalBankRule.bankId", FetchMode.JOIN);
		criteria.createAlias("additionalBankRule.bankId", "bankId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankId.bankId", bankId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("additionalBankRule.additionalDescription"));
		LOGGER.info("Exited from the getAdditionalBankData() Method");
		return (List<AdditionalBankRuleAddData>) findAll(criteria);
	}

	@Override
	public Map<String, Object> checkTelephoneExistWithCustId(BigDecimal telephoneNo, BigDecimal customerID, String teleCountryID) {
		LOGGER.info("Entered into checkTelephoneExistWithCustId(BigDecimal telephoneNo, BigDecimal customerID, String teleCountryID) Method ");
		LOGGER.info("telephoneNo" + telephoneNo);
		LOGGER.info("customerID" + customerID);
		LOGGER.info("teleCountryID" + teleCountryID);
		Map<String, Object> mapTeleExist = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "beneficaryTelaphone");
		criteria.add(Restrictions.eq("beneficaryTelaphone.telephoneNumber", telephoneNo.toPlainString()));
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
				beneAccountCriteria.setFetchMode("beneficaryMaster.fsCityMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryMaster.fsCityMaster", "fsCityMaster", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryMaster.fsDistrictMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryMaster.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);
				beneAccountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
				List<BeneficaryAccount> lstBeneficaryAccount = (List<BeneficaryAccount>) findAll(beneAccountCriteria);
				LOGGER.info("lstBeneficaryAccount ========== " + lstBeneficaryAccount.size());
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
		LOGGER.info("Exited from checkTelephoneExistWithCustId() Method ");
		return mapTeleExist;
	}

	@Override
	public List<BeneficaryRelationship> checkRelationExistWithCustId(BigDecimal relationId, BigDecimal customerID) {
		LOGGER.info("Entered into checkRelationExistWithCustId(BigDecimal relationId, BigDecimal customerID) Method ");
		LOGGER.info("relationId" + relationId);
		LOGGER.info("customerID" + customerID);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");
		criteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryRelationship.relations", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.relations", "relations", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("relations.relationsId", relationId));
		/*
		 * criteria.setFetchMode("beneficaryMaster.fsCustomer",FetchMode.JOIN);
		 * criteria.createAlias("beneficaryMaster.fsCustomer",
		 * "fsCustomer",JoinType.INNER_JOIN);
		 */
		criteria.setFetchMode("beneficaryRelationship.customerId", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.customerId", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerID));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		/*
		 * List<BeneficaryRelationship> beneficaryRelation =
		 * (List<BeneficaryRelationship>)findAll(criteria);
		 */
		LOGGER.info("Exited from checkRelationExistWithCustId() Method");
		return (List<BeneficaryRelationship>) findAll(criteria);
	}

	@Override
	public BigDecimal populateTelephoneCountry(BigDecimal countryId) {
		return null;
	}

	@Override
	public List<BeneCountryService> getBeneCountryAllServiceList(BigDecimal countryId, BigDecimal languageId, BigDecimal appCountryId) {
		LOGGER.info("Entered into getBeneCountryAllServiceList(BigDecimal countryId, BigDecimal languageId, BigDecimal appCountryId) Method");
		LOGGER.info("countryId =" + countryId);
		LOGGER.info("languageId =" + languageId);
		LOGGER.info("appCountryId =" + appCountryId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneCountryService.class, "beneCountryService");
		criteria.setFetchMode("beneCountryService.beneCountryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.beneCountryId", "beneCntryService", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneCntryService.countryId", countryId));
		criteria.setFetchMode("beneCountryService.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.applicationCountryId", "beneCuntryService", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneCuntryService.countryId", appCountryId));
		criteria.setFetchMode("beneCountryService.remitanceId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.remitanceId", "remitanceId", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneCountryService.deliveryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.deliveryId", "deliveryId", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneCountryService> objList = (List<BeneCountryService>) findAll(criteria);
		LOGGER.info("Controll come inside method ===================== > " + objList.size());
		if (objList.isEmpty())
			return null;
		return objList;
	}

	@Override
	public List<BeneCountryService> getBeneCountryServiceList(BigDecimal countryId) {
		LOGGER.info("Entered into getBeneCountryServiceList(BigDecimal countryId) Method ");
		LOGGER.info("countryId =" + countryId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneCountryService.class, "beneCountryService");
		criteria.setFetchMode("beneCountryService.beneCountryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.beneCountryId", "beneCntryService", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneCntryService.countryId", countryId));
		criteria.setFetchMode("beneCountryService.serviceId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.serviceId", "serviceId", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneCountryService.remitanceId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.remitanceId", "remitanceId", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneCountryService.deliveryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.deliveryId", "deliveryId", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneCountryService> objList = (List<BeneCountryService>) findAll(criteria);
		LOGGER.info("Controll come inside method ===================== > " + objList.size());
		if (objList.isEmpty())
			return null;
		return objList;
	}

	@Override
	public List<BankBranch> getBankBranchList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode, BigDecimal toBranchCode) {
		LOGGER.info("Entered into getBankBranchList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode, BigDecimal toBranchCode) Method ");
		LOGGER.info("countryId =" + countryId);
		LOGGER.info("BankId =" + BankId);
		LOGGER.info("formBranchCode =" + formBranchCode);
		LOGGER.info("toBranchCode =" + toBranchCode);
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		if (formBranchCode == null && toBranchCode == null) {
			criteria.setFetchMode("bankBranch.fsCountryMaster", FetchMode.JOIN);
			criteria.createAlias("bankBranch.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
			criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
			criteria.createAlias("bankBranch.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("exBankMaster.bankId", BankId));
		} else {
			criteria.setFetchMode("bankBranch.fsCountryMaster", FetchMode.JOIN);
			criteria.createAlias("bankBranch.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
			criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
			criteria.createAlias("bankBranch.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("exBankMaster.bankId", BankId));
			criteria.add(Restrictions.between("branchCode", formBranchCode, toBranchCode));
		}
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankBranch> objList = (List<BankBranch>) findAll(criteria);
		LOGGER.info("Controll come inside method ===================== > " + objList.size());
		/*
		 * if(objList.size()==0) return null;
		 */
		LOGGER.info("Exited From the getBankBranchList Method");
		return objList;
	}

	@Override
	public List<BeneServiceExceptionSetup> getBeneServiceExceptionSetupList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode,
			BigDecimal toBranchCode) {
		LOGGER.info(" Entered into getBeneServiceExceptionSetupList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode, BigDecimal toBranchCode) Method ");
		LOGGER.info("getCountry() ==== > daoimpl" + countryId);
		LOGGER.info("getBank()daoimpl" + BankId);
		LOGGER.info("getFromBankBranch()daoimpl" + formBranchCode);
		LOGGER.info(" getToBankBranch()daoimpl" + toBranchCode);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneServiceExceptionSetup.class, "beneServiceExceptionSetup");
		String isActive = Constants.U;
		// List<BeneServiceExceptionSetup> objList = null;
		if (formBranchCode == null && toBranchCode == null) {
			LOGGER.info("Controll come inside method ===================== >if ");
			criteria.setFetchMode("beneServiceExceptionSetup.countryId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.countryId", "countryId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("countryId.countryId", countryId));
			criteria.setFetchMode("beneServiceExceptionSetup.bankId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankId", "bankId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("bankId.bankId", BankId));
			criteria.setFetchMode("beneServiceExceptionSetup.bankBranchId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankBranchId", "bankBranchId", JoinType.LEFT_OUTER_JOIN);
			criteria.add(Restrictions.eq("beneServiceExceptionSetup.isActive", isActive));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			// objList = (List<BeneServiceExceptionSetup>) findAll(criteria);
		} else {
			LOGGER.info("Controll come inside method ===================== >else ");
			criteria.setFetchMode("beneServiceExceptionSetup.countryId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.countryId", "countryId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("countryId.countryId", countryId));
			criteria.setFetchMode("beneServiceExceptionSetup.bankId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankId", "bankId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("bankId.bankId", BankId));
			criteria.setFetchMode("beneServiceExceptionSetup.bankBranchId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankBranchId", "bankBranchId", JoinType.LEFT_OUTER_JOIN);
			// criteria.add(Restrictions.between("beneServiceExceptionSetup.branchCodeNo",
			// formBranchCode,toBranchCode));
			criteria.add(Restrictions.between("beneServiceExceptionSetup.branchCodeNo", formBranchCode.toString(), toBranchCode.toString()));
			criteria.add(Restrictions.eq("beneServiceExceptionSetup.isActive", isActive));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			// objList = (List<BeneServiceExceptionSetup>) findAll(criteria);
		}
		List<BeneServiceExceptionSetup> objList = (List<BeneServiceExceptionSetup>) findAll(criteria);
		LOGGER.info("Exited from  getBeneServiceExceptionSetupList() Method Size=" + objList.size());
		/*
		 * if (objList.isEmpty()) return null;
		 */
		return objList;
	}

	@Override
	public List<BeneServiceExceptionSetup> getBeneDeleteServiceExceptionSetupList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode,
			BigDecimal toBranchCode) {
		LOGGER.info("Entered into getBeneDeleteServiceExceptionSetupList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode, BigDecimal toBranchCode) method ");
		LOGGER.info("countryId =" + countryId);
		LOGGER.info("BankId =" + BankId);
		LOGGER.info("formBranchCode =" + formBranchCode);
		LOGGER.info("toBranchCode =" + toBranchCode);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneServiceExceptionSetup.class, "beneServiceExceptionSetup");
		String isActive = Constants.D;
		List<BeneServiceExceptionSetup> objList = null;
		if (formBranchCode == null && toBranchCode == null) {
			LOGGER.info("Controll come inside method ===================== >if ");
			criteria.setFetchMode("beneServiceExceptionSetup.countryId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.countryId", "countryId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("countryId.countryId", countryId));
			criteria.setFetchMode("beneServiceExceptionSetup.bankId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankId", "bankId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("bankId.bankId", BankId));
			criteria.setFetchMode("beneServiceExceptionSetup.bankBranchId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankBranchId", "bankBranchId", JoinType.LEFT_OUTER_JOIN);
			criteria.add(Restrictions.eq("beneServiceExceptionSetup.isActive", isActive));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			objList = (List<BeneServiceExceptionSetup>) findAll(criteria);
		} else {
			LOGGER.info("Controll come inside method ===================== >else ");
			criteria.setFetchMode("beneServiceExceptionSetup.countryId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.countryId", "countryId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("countryId.countryId", countryId));
			criteria.setFetchMode("beneServiceExceptionSetup.bankId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankId", "bankId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("bankId.bankId", BankId));
			criteria.setFetchMode("beneServiceExceptionSetup.bankBranchId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankBranchId", "bankBranchId", JoinType.LEFT_OUTER_JOIN);
			// criteria.add(Restrictions.between("beneServiceExceptionSetup.branchCodeNo",
			// formBranchCode,toBranchCode));
			criteria.add(Restrictions.between("beneServiceExceptionSetup.branchCodeNo", formBranchCode.toString(), toBranchCode.toString()));
			criteria.add(Restrictions.eq("beneServiceExceptionSetup.isActive", isActive));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			objList = (List<BeneServiceExceptionSetup>) findAll(criteria);
		}
		// List<BeneServiceExceptionSetup> objList =
		// (List<BeneServiceExceptionSetup>) findAll(criteria);
		LOGGER.info(" Exited from getBeneDeleteServiceExceptionSetupList()  size  is ===================== > " + objList.size());
		/*
		 * if (objList.isEmpty()) return null;
		 */
		return objList;
	}

	@Override
	public List<RemittanceModeDescription> getRemittancDesc(BigDecimal remiId) {
		LOGGER.info("Entered into getRemittancDesc() Method ");
		LOGGER.info("remiId" + remiId);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "RemittanceModeDescription");
		criteria.setFetchMode("RemittanceModeDescription.remittanceModeMaster", FetchMode.JOIN);
		criteria.createAlias("RemittanceModeDescription.remittanceModeMaster", "remittanceModeMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId", remiId));
		LOGGER.info("Exited from the getRemittancDesc() Method");
		return (List<RemittanceModeDescription>) findAll(criteria);
	}

	@Override
	public List<DeliveryModeDesc> getDeliveryModeDesc(BigDecimal deliveryId) {

		LOGGER.info("Entered into getDeliveryModeDesc(BigDecimal deliveryId) Method ");
		LOGGER.info("deliveryId =" + deliveryId);
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");
		criteria.setFetchMode("deliveryModeDesc.deliveryMode", FetchMode.JOIN);
		criteria.createAlias("deliveryModeDesc.deliveryMode", "deliveryMode", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("deliveryMode.deliveryModeId", deliveryId));
		LOGGER.info("Exited From the  getDeliveryModeDesc(BigDecimal deliveryId)  Method");
		return (List<DeliveryModeDesc>) findAll(criteria);
	}

	@Override
	public List<Object> getCountryServiceList(BigDecimal appCountryId, BigDecimal languageId, BigDecimal countryId) {
		LOGGER.info("Entered into getCountryServiceList(BigDecimal appCountryId, BigDecimal languageId, BigDecimal countryId) Method");
		LOGGER.info("appCountryId =" + appCountryId);
		LOGGER.info("languageId =" + languageId);
		LOGGER.info("countryId =" + countryId);
		List<Object> objList = new ArrayList<Object>();
		String queryString = "SELECT distinct BCS.REMITTANCE_MODE_ID,BCS.DELIVERY_MODE_ID,RMD.REMITTANCE_DESCRIPTION,RM.REMITTANCE_CODE,DMD.DELIVERY_DESCRIPTION ,"
				+ "DM.DELIVERY_CODE,BCS.CURRENCY_ID,BCS.BENE_COUNTRY_ID,BCS.APPLICATION_COUNTRY_ID FROM EX_BENE_COUNTRY_SERVICE BCS JOIN EX_DELIVERY_MODE DM ON BCS.DELIVERY_MODE_ID = DM.DELIVERY_MODE_ID "
				+ "JOIN EX_REMITTANCE_MODE RM ON BCS.REMITTANCE_MODE_ID =  RM.REMITTANCE_MODE_ID JOIN EX_REMITTANCE_MODE_DESC RMD on BCS.REMITTANCE_MODE_ID = RMD.REMITTANCE_MODE_ID"
				+ "  JOIN EX_DELIVERY_MODE_DESC DMD on BCS.DELIVERY_MODE_ID = DMD.DELIVERY_MODE_ID where BCS.BENE_COUNTRY_ID=?  and BCS.APPLICATION_COUNTRY_ID=? "
				+ "and RMD.LANGUAGE_ID=? and DMD.LANGUAGE_ID=?";
		objList = getSession().createSQLQuery(queryString).setBigDecimal(0, countryId).setBigDecimal(1, appCountryId).setBigDecimal(2, languageId)
				.setBigDecimal(3, languageId).list();
		LOGGER.info("Exited from the getCountryServiceList Method ");
		return objList;
	}

	@Override
	public void saveBeneServiceExceptionSetup(BeneServiceExceptionSetup beneServiceException) {
		getSession().saveOrUpdate(beneServiceException);
	}

	@Override
	public boolean getBeneServiceExceptionSetup(BigDecimal countryId, BigDecimal branchId, BigDecimal deliveryId, BigDecimal remittanceId) {
		LOGGER.info("Entered into getBeneServiceExceptionSetup(BigDecimal countryId, BigDecimal branchId, BigDecimal deliveryId, BigDecimal remittanceId) Method");
		LOGGER.info("countryId =" + countryId);
		LOGGER.info("branchId =" + branchId);
		LOGGER.info("deliveryId =" + deliveryId);
		LOGGER.info("remittanceId =" + remittanceId);
		List<Object> objList = new ArrayList<Object>();
		String queryString = "select COUNTRY_ID from EX_BENE_SERVICE_EXCEP_SETUP where COUNTRY_ID =? and BANK_BRANCH_ID=? and REMITTANCE_MODE_ID=? and DELIVERY_MODE_ID=? and ISACTIVE='U'";
		objList = getSession().createSQLQuery(queryString).setBigDecimal(0, countryId).setBigDecimal(1, branchId).setBigDecimal(2, deliveryId)
				.setBigDecimal(3, remittanceId).list();
		if (objList.isEmpty())
			return false;
		return true;
	}

	@Override
	public List<Object> getBeneExceptionSetupList(BigDecimal countryId, BigDecimal appCountryId, BigDecimal languageId) {
		LOGGER.info("Entered into getBeneExceptionSetupList() Method ");
		List<Object> objList = new ArrayList<Object>();
		String queryString = "SELECT distinct BSES.REMITTANCE_MODE_ID,BSES.DELIVERY_MODE_ID,RMD.REMITTANCE_DESCRIPTION,RM.REMITTANCE_CODE,DMD.DELIVERY_DESCRIPTION ,"
				+ "DM.DELIVERY_CODE,BSES.CURRENCY_ID,BSES.COUNTRY_ID,BSES.APPLICATION_COUNTRY_ID,BSES.ISACTIVE FROM EX_BENE_SERVICE_EXCEP_SETUP BSES "
				+ "JOIN EX_DELIVERY_MODE DM ON BSES.DELIVERY_MODE_ID = DM.DELIVERY_MODE_ID JOIN EX_REMITTANCE_MODE RM ON BSES.REMITTANCE_MODE_ID =  RM.REMITTANCE_MODE_ID "
				+ "JOIN EX_REMITTANCE_MODE_DESC RMD on BSES.REMITTANCE_MODE_ID = RMD.REMITTANCE_MODE_ID  JOIN EX_DELIVERY_MODE_DESC DMD on BSES.DELIVERY_MODE_ID = DMD.DELIVERY_MODE_ID "
				+ "where BSES.COUNTRY_ID=?  and BSES.APPLICATION_COUNTRY_ID=? and RMD.LANGUAGE_ID=? and DMD.LANGUAGE_ID=? "
				+ "union "
				+ "SELECT distinct BCS.REMITTANCE_MODE_ID,BCS.DELIVERY_MODE_ID,RMD.REMITTANCE_DESCRIPTION,RM.REMITTANCE_CODE,DMD.DELIVERY_DESCRIPTION ,DM.DELIVERY_CODE,BCS.CURRENCY_ID,"
				+ "BCS.BENE_COUNTRY_ID,BCS.APPLICATION_COUNTRY_ID,null  FROM EX_BENE_COUNTRY_SERVICE BCS JOIN EX_DELIVERY_MODE DM ON BCS.DELIVERY_MODE_ID = DM.DELIVERY_MODE_ID "
				+ "JOIN EX_REMITTANCE_MODE RM ON BCS.REMITTANCE_MODE_ID =  RM.REMITTANCE_MODE_ID JOIN EX_REMITTANCE_MODE_DESC RMD on BCS.REMITTANCE_MODE_ID = RMD.REMITTANCE_MODE_ID  "
				+ "JOIN EX_DELIVERY_MODE_DESC DMD on BCS.DELIVERY_MODE_ID = DMD.DELIVERY_MODE_ID where BCS.BENE_COUNTRY_ID=? and BCS.APPLICATION_COUNTRY_ID=? and RMD.LANGUAGE_ID=? "
				+ "and DMD.LANGUAGE_ID=?";
		objList = getSession().createSQLQuery(queryString).setBigDecimal(0, countryId).setBigDecimal(1, appCountryId).setBigDecimal(2, languageId)
				.setBigDecimal(3, languageId).setBigDecimal(4, countryId).setBigDecimal(5, appCountryId).setBigDecimal(6, languageId)
				.setBigDecimal(7, languageId).list();
		LOGGER.info("Exited from  getBeneExceptionSetupList() Method ");
		return objList;
	}

	@Override
	public List<BeneServiceExceptionSetup> getBeneExceptionSetupAllList() {
		LOGGER.info("Enterd into getBeneExceptionSetupAllList() method ");
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneServiceExceptionSetup.class, "beneServiceExceptionSetup");
		criteria.add(Restrictions.eq("beneServiceExceptionSetup.isActive", Constants.U));
		List<BeneServiceExceptionSetup> objList = (List<BeneServiceExceptionSetup>) findAll(criteria);
		LOGGER.info("Controll come inside method ===================== > " + objList.size());
		LOGGER.info("Exited from the getBeneExceptionSetupAllList() Method ");
		return objList;
	}

	@Override
	public List<RemittanceModeDescription> listRemittanceDesc(BigDecimal remittanceId, BigDecimal langId) {
		LOGGER.info("Entered into listRemittanceDesc(BigDecimal remittanceId, BigDecimal langId) Method ");
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");
		criteria.setFetchMode("remittanceModeDescription.languageType", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", langId));
		criteria.setFetchMode("remittanceModeDescription.remittanceModeMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.remittanceModeMaster", "remittanceModeMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId", remittanceId));
		criteria.add(Restrictions.eq("remittanceModeMaster.isActive", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the listRemittanceDesc() Method ");
		return (List<RemittanceModeDescription>) findAll(criteria);
	}

	@Override
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal deliveryId, BigDecimal langId) {
		LOGGER.info("Entered into lstDeliveryMode(BigDecimal deliveryId, BigDecimal langId) Method ");
		LOGGER.info("deliveryId =" + deliveryId);
		LOGGER.info("langId =" + langId);
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliverymodedesc");
		criteria.setFetchMode("deliverymodedesc.languageType", FetchMode.JOIN);
		criteria.createAlias("deliverymodedesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", langId));
		criteria.setFetchMode("deliverymodedesc.deliveryMode", FetchMode.JOIN);
		criteria.createAlias("deliverymodedesc.deliveryMode", "deliveryMode", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("deliveryMode.deliveryModeId", deliveryId));
		criteria.add(Restrictions.eq("deliveryMode.isActive", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the lstDeliveryMode() Method ");
		return (List<DeliveryModeDesc>) findAll(criteria);
	}

	@Override
	public List<BeneServiceExceptionSetup> getBeneExceptionSetupList(BigDecimal exceptionSetupId) {
		LOGGER.info("Entered into getBeneExceptionSetupList(BigDecimal exceptionSetupId)");
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneServiceExceptionSetup.class, "beneServiceExceptionSetup");
		criteria.add(Restrictions.eq("beneServiceExceptionSetup.beneServiceExcepSetup", exceptionSetupId));
		List<BeneServiceExceptionSetup> objList = (List<BeneServiceExceptionSetup>) findAll(criteria);
		LOGGER.info("Controll come inside method ===================== > " + objList.size());
		LOGGER.info("Exited from the getBeneExceptionSetupList Method ");
		return objList;
	}

	@Override
	public List<BeneServiceExceptionSetup> getExceptionSetupListForActiveInactive() {
		LOGGER.info("Entered into getExceptionSetupListForActiveInactive() Method ");
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneServiceExceptionSetup.class, "beneServiceExceptionSetup");
		List<String> activeStatuslist = new ArrayList<String>();
		activeStatuslist.add(Constants.Yes);
		activeStatuslist.add(Constants.D);
		// criteria.add(Restrictions.isNotNull("bankMaster.approvedBy"));
		// criteria.add(Restrictions.isNotNull("bankMaster.approvedDate"));
		criteria.add(Restrictions.in("beneServiceExceptionSetup.isActive", activeStatuslist));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from  getExceptionSetupListForActiveInactive() Method ");
		return (List<BeneServiceExceptionSetup>) findAll(criteria);
	}

	// get transaction details by subramanian.
	@Override
	public List<RemittanceApplication> getRemittanceTransactionList(BigDecimal transferNo) {
		LOGGER.info("Entered into getRemittanceTransactionList(BigDecimal transferNo) Method ");
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplication.class, "remittanceApplication");
		criteria.add(Restrictions.eq("documentNo", transferNo));
		criteria.setFetchMode("remittanceApplication.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCustomer", "fsCustomer", JoinType.LEFT_OUTER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		/*
		 * criteria.setFetchMode("remittanceApplication.fsCustomer",FetchMode.JOIN
		 * ); criteria.createAlias("remittanceApplication.fsCustomer",
		 * "fsCustomer",JoinType.INNER_JOIN);
		 * 
		 * criteria.add(Restrictions.eq("remittanceApplication.documentNo",
		 * transferNo));
		 */
		// List<RemittanceApplication> remittanceTransactionList = new
		// ArrayList<RemittanceApplication>();
		LOGGER.info("Exited from the getRemittanceTransactionList Method ");
		return (List<RemittanceApplication>) findAll(criteria);
	}

	@Override
	public String getRemittanceServiceRuleName(BigDecimal countryId, BigDecimal currencyId, BigDecimal bankId, BigDecimal remittanceId,
			BigDecimal deliveryId) {
		LOGGER.info("Entered into getRemittanceServiceRuleName() Method ");
		LOGGER.info("countryId" + countryId);
		LOGGER.info("currencyId" + currencyId);
		LOGGER.info("bankId" + bankId);
		LOGGER.info("remittanceId" + remittanceId);
		LOGGER.info("deliveryId" + deliveryId);

		DetachedCriteria criteria = DetachedCriteria.forClass(BankServiceRule.class, "bankServiceRule");
		criteria.setFetchMode("bankServiceRule.countryId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.countryId", "countryId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId", countryId));
		criteria.setFetchMode("bankServiceRule.currencyId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.currencyId", "currencyId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("currencyId.currencyId", currencyId));
		criteria.setFetchMode("bankServiceRule.bankId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.bankId", "bankId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("bankId.bankId", bankId));
		criteria.setFetchMode("bankServiceRule.remittanceModeId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.remittanceModeId", "remittanceModeId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeId.remittanceModeId", remittanceId));
		criteria.setFetchMode("bankServiceRule.deliveryModeId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.deliveryModeId", "deliveryModeId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("deliveryModeId.deliveryModeId", deliveryId));
		if (((List<BankServiceRule>) findAll(criteria)).isEmpty())
			return null;
		return ((List<BankServiceRule>) findAll(criteria)).get(0).getFullname();
	}

	@Override
	public List<BankAccountLength> getBankAccountLengthByBank(BigDecimal bankId) {
		LOGGER.info("Entered into getBankAccountLengthByBank(BigDecimal bankId) Method ");
		LOGGER.info("Bank Id= " + bankId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountLength.class, "bankAccountLength");
		criteria.setFetchMode("bankAccountLength.bankMaster", FetchMode.JOIN);
		criteria.createAlias("bankAccountLength.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		criteria.add(Restrictions.eq("bankAccountLength.recordStatus", Constants.Yes));
		LOGGER.info("Exited from the getBankAccountLengthByBank() Method");
		return (List<BankAccountLength>) findAll(criteria);
	}

	@Override
	public boolean getBanKBranchFromBeneExceptionSetup(BigDecimal countryId, BigDecimal bankId, BigDecimal branchId) {
		LOGGER.info("Entered into getBanKBranchFromBeneExceptionSetup(BigDecimal countryId, BigDecimal bankId, BigDecimal branchId) Method ");
		LOGGER.info("countryId =" + countryId);
		LOGGER.info("bankId =" + bankId);
		LOGGER.info("branchId =" + branchId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneServiceExceptionSetup.class, "beneServiceExceptionSetup");
		criteria.setFetchMode("beneServiceExceptionSetup.countryId", FetchMode.JOIN);
		criteria.createAlias("beneServiceExceptionSetup.countryId", "countryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId", countryId));
		criteria.setFetchMode("beneServiceExceptionSetup.bankId", FetchMode.JOIN);
		criteria.createAlias("beneServiceExceptionSetup.bankId", "bankId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankId.bankId", bankId));
		criteria.setFetchMode("beneServiceExceptionSetup.bankBranchId", FetchMode.JOIN);
		criteria.createAlias("beneServiceExceptionSetup.bankBranchId", "bankBranchId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("bankBranchId.bankBranchId", branchId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneServiceExceptionSetup> objList = (List<BeneServiceExceptionSetup>) findAll(criteria);
		if (objList.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public List<BeneServiceExceptionSetup> getExceptionSetupListForEnquiry(BigDecimal countryId, BigDecimal bankId, BigDecimal currencyId) {
		LOGGER.info("Entered into getExceptionSetupListForEnquiry(BigDecimal countryId, BigDecimal bankId, BigDecimal currencyId) Method");
		LOGGER.info("countryId =" + countryId);
		LOGGER.info("bankId =" + bankId);
		LOGGER.info("currencyId =" + currencyId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneServiceExceptionSetup.class, "beneServiceExceptionSetup");
		criteria.setFetchMode("beneServiceExceptionSetup.bankId", FetchMode.JOIN);
		criteria.createAlias("beneServiceExceptionSetup.bankId", "bankId", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneServiceExceptionSetup.countryId", FetchMode.JOIN);
		criteria.createAlias("beneServiceExceptionSetup.countryId", "countryId", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneServiceExceptionSetup.currency", FetchMode.JOIN);
		criteria.createAlias("beneServiceExceptionSetup.currency", "currency", JoinType.INNER_JOIN);
		if (countryId != null) {
			criteria.add(Restrictions.eq("countryId.countryId", countryId));
		}
		if (bankId != null) {
			criteria.add(Restrictions.eq("bankId.bankId", bankId));
		}
		if (currencyId != null) {
			criteria.add(Restrictions.eq("currency.currencyId", currencyId));
		}
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the getExceptionSetupListForEnquiry Method ");
		return (List<BeneServiceExceptionSetup>) findAll(criteria);
	}

	@Override
	public List<ViewBeneServiceCurrency> getViewBeneCurrency(BigDecimal benifisCountryId) {
		LOGGER.info("Entered into getViewBeneCurrency(BigDecimal benifisCountryId) Method");
		LOGGER.info("benifisCountryId =" + benifisCountryId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewBeneServiceCurrency.class, "viewBeneServiceCurrency");
		criteria.add(Restrictions.eq("viewBeneServiceCurrency.beneCountryId", benifisCountryId));
		criteria.addOrder(Order.asc("currencyCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from getViewBeneCurrency() Method");
		return (List<ViewBeneServiceCurrency>) findAll(criteria);
	}

	@Override
	public String getBankBranchName(BigDecimal bankBranchId) {
		LOGGER.info("Entered into getBankBranchName(BigDecimal bankBranchId) Method");
		LOGGER.info("bankBranchId =" + bankBranchId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		dCriteria.add(Restrictions.eq("countryBranch.countryBranchId", bankBranchId));
		LOGGER.info("Exited from getBankBranchName Method");
		return ((CountryBranch) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getBranchName();
	}

	@Override
	public String getBranchName(BigDecimal countryId, BigDecimal bankId, BigDecimal bankBranchId) {
		LOGGER.info("Entered into getBranchName(BigDecimal countryId, BigDecimal bankId, BigDecimal bankBranchId) Method ");
		LOGGER.info("countryId =" + countryId);
		LOGGER.info("bankId =" + bankId);
		LOGGER.info("bankBranchId =" + bankBranchId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		criteria.setFetchMode("bankBranch.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.fsCountryMaster", "fsCountryMaster", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.exBankMaster", "exBankMaster", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		criteria.add(Restrictions.eq("bankBranch.bankBranchId", bankBranchId));
		LOGGER.info("Exited from the getBranchName() Method");
		return ((BankBranch) criteria.getExecutableCriteria(getSession()).list().get(0)).getBranchFullName();
	}

	@Override
	public List<ServiceMasterDesc> fetchCashServiceId(String serviceDesc, BigDecimal langId) {
		LOGGER.info("Entered into fetchCashServiceId(String serviceDesc, BigDecimal langId) Method ");
		LOGGER.info("serviceDesc =" + serviceDesc);
		LOGGER.info("langId =" + langId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceMasterDesc.class, "serviceMasterDesc");
		criteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", langId));
		criteria.add(Restrictions.eq("serviceMasterDesc.localServiceDescription", serviceDesc));
		LOGGER.info("Exited from fetchCashServiceId(String serviceDesc, BigDecimal langId) Method ");
		return (List<ServiceMasterDesc>) findAll(criteria);
	}

	@Override
	public List<PopulateData> lstOfRemittance(BigDecimal routingCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId,
			BigDecimal routingBankId) {
		LOGGER.info("Entered into lstOfRemittance(BigDecimal routingCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId, BigDecimal routingBankId) Method ");
		LOGGER.info("routingCountryId =" + routingCountryId);
		LOGGER.info("beneficaryCurrencyId =" + beneficaryCurrencyId);
		LOGGER.info("serviceId =" + serviceId);
		LOGGER.info("routingBankId =" + routingBankId);
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		String sql = "select DISTINCT * from  EX_BENE_COUNTRY_SERVICE a ,  EX_ROUTING_HEADER b " + "  where   b.COUNTRY_ID  =   a.BENE_COUNTRY_ID  "
				+ " and   b.CURRENCY_ID   =  a.CURRENCY_ID  " + " and  b.SERVICE_MASTER_ID    =  a.SERVICE_MASTER_ID "
				+ "  and  b.DELIVERY_MODE_ID    =  a.DELIVERY_MODE_ID " + "  and  b.ISACTIVE = a.ISACTIVE";

		LOGGER.info("lstOfRemittance query=" + sql);
		SQLQuery query = getSession().createSQLQuery(sql).addEntity(RoutingHeader.class);
		List<PopulateData> lstremit = new ArrayList<PopulateData>();
		List<RoutingHeader> beneficaryCountryList = query.list();
		for (RoutingHeader routinghead : beneficaryCountryList) {
			if ((routinghead.getExRoutingCountryId().getCountryId().compareTo(routingCountryId) == 0)
					&& (routinghead.getExCurrenyId().getCurrencyId().compareTo(beneficaryCurrencyId) == 0)
					&& (routinghead.getExServiceId().getServiceId().compareTo(serviceId) == 0)
					&& (routinghead.getExRoutingBankId().getBankId().compareTo(routingBankId) == 0)) {
				if (!duplicateCheck.contains(routinghead.getExRemittanceModeId().getRemittanceModeId())) {
					duplicateCheck.add(routinghead.getExRemittanceModeId().getRemittanceModeId());
					PopulateData routingCountry = new PopulateData(routinghead.getExRemittanceModeId().getRemittanceModeId(), routinghead
							.getExRemittanceModeId().getRemittanceModeDescription().get(0).getRemittanceDescription());
					lstremit.add(routingCountry);
				}
			}
		}
		LOGGER.info("Exited from lstOfRemittance Method");
		return lstremit;
	}

	@Override
	public List<PopulateData> lstOfDelivery(BigDecimal routingCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId,
			BigDecimal routingBankId) {
		LOGGER.info("Entered into lstOfDelivery(BigDecimal routingCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId, BigDecimal routingBankId) Method");
		LOGGER.info("routingCountryId =" + routingCountryId);
		LOGGER.info("beneficaryCurrencyId =" + beneficaryCurrencyId);
		LOGGER.info("serviceId =" + serviceId);
		LOGGER.info("routingBankId =" + routingBankId);
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		String sql = "select DISTINCT * from  EX_BENE_COUNTRY_SERVICE a ,  EX_ROUTING_HEADER b " + "  where   b.COUNTRY_ID  =   a.BENE_COUNTRY_ID  "
				+ " and   b.CURRENCY_ID   =  a.CURRENCY_ID  " + " and  b.SERVICE_MASTER_ID    =  a.SERVICE_MASTER_ID "
				+ "  and  b.DELIVERY_MODE_ID    =  a.DELIVERY_MODE_ID " + "  and  b.ISACTIVE = a.ISACTIVE";
		LOGGER.info("lstOfDelivery query=" + sql);
		SQLQuery query = getSession().createSQLQuery(sql).addEntity(RoutingHeader.class);
		List<PopulateData> lstremit = new ArrayList<PopulateData>();
		List<RoutingHeader> beneficaryCountryList = query.list();
		for (RoutingHeader routinghead : beneficaryCountryList) {
			if ((routinghead.getExRoutingCountryId().getCountryId().compareTo(routingCountryId) == 0)
					&& (routinghead.getExCurrenyId().getCurrencyId().compareTo(beneficaryCurrencyId) == 0)
					&& (routinghead.getExServiceId().getServiceId().compareTo(serviceId) == 0)
					&& (routinghead.getExRoutingBankId().getBankId().compareTo(routingBankId) == 0)) {
				if (!duplicateCheck.contains(routinghead.getExDeliveryModeId().getDeliveryModeId())) {
					duplicateCheck.add(routinghead.getExDeliveryModeId().getDeliveryModeId());
					PopulateData routingCountry = new PopulateData(routinghead.getExDeliveryModeId().getDeliveryModeId(), routinghead
							.getExDeliveryModeId().getDeliveryModeList().get(0).getEnglishDeliveryName());
					lstremit.add(routingCountry);
				}
			}
		}
		LOGGER.info("Exited from the lstOfDelivery() Method ");
		return lstremit;
	}

	@Override
	public void updateRemitTranxTable(Collect collect, List<ShoppingCartDataTableBean> lstToUpdate) {
		LOGGER.info("Entered into updateRemitTranxTable(Collect collect, List<ShoppingCartDataTableBean> lstToUpdate) Method ");
		LOGGER.info("collect =" + collect);
		LOGGER.info("lstToUpdate  size=" + lstToUpdate.size());

		List<RemittanceTransaction> objupdateList = new ArrayList<RemittanceTransaction>();
		List<CustomerSpecialDealRequest> lstcustSplDetails = new ArrayList<CustomerSpecialDealRequest>();
		List<ReceiptPayment> lstReceiptPaymentDetails = new ArrayList<ReceiptPayment>();
		List<BigDecimal> lstduplicate = new ArrayList<BigDecimal>();
		HashMap<BigDecimal, BigDecimal> mapcustSplID = new HashMap<BigDecimal, BigDecimal>();
		lstcustSplDetails.clear();
		objupdateList.clear();
		lstReceiptPaymentDetails.clear();
		lstduplicate.clear();
		mapcustSplID.clear();
		for (ShoppingCartDataTableBean shoppingCartDataTableBean : lstToUpdate) {
			BigDecimal totalUtilizedAmount = BigDecimal.ZERO;
			if (shoppingCartDataTableBean.getSpldealStatus().equalsIgnoreCase(Constants.YES)) {
				// Special Customer Deal Request Record Fetching
				DetachedCriteria criteria1 = DetachedCriteria.forClass(CustomerSpecialDealRequest.class, "customerSpecialDealRequest");
				criteria1.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCompanyMaster", FetchMode.JOIN);
				criteria1.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCompanyMaster", "customerSpeacialDealReqCompanyMaster",
						JoinType.INNER_JOIN);
				// criteria1.add(Restrictions.eq("customerSpeacialDealReqCompanyMaster.companyId",
				// shoppingCartDataTableBean.getSpldealcompanyId()));
				criteria1.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqDocument", FetchMode.JOIN);
				criteria1.createAlias("customerSpecialDealRequest.customerSpeacialDealReqDocument", "customerSpeacialDealReqDocument",
						JoinType.INNER_JOIN);
				// criteria1.add(Restrictions.eq("customerSpeacialDealReqDocument.documentID",
				// shoppingCartDataTableBean.getSpldealdocumentcode()));
				// criteria1.add(Restrictions.eq("customerSpecialDealRequest.documentNumber",
				// shoppingCartDataTableBean.getSpldealdocumentnumber()));
				criteria1.setFetchMode("customerSpecialDealRequest.documentFinancialYear", FetchMode.JOIN);
				criteria1.createAlias("customerSpecialDealRequest.documentFinancialYear", "documentFinancialYear", JoinType.INNER_JOIN);
				// criteria1.add(Restrictions.eq("documentFinancialYear.financialYear",
				// shoppingCartDataTableBean.getSpldealdocumentyear()));
				criteria1.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
				List<CustomerSpecialDealRequest> lstSplCustDeal = (List<CustomerSpecialDealRequest>) findAll(criteria1);
				if (lstSplCustDeal.size() != 0) {
					for (CustomerSpecialDealRequest customerSpecialDealRequest : lstSplCustDeal) {
						if (!lstduplicate.contains(customerSpecialDealRequest.getCustomerSpecialDealReqId())) {
							lstduplicate.add(customerSpecialDealRequest.getCustomerSpecialDealReqId());
							totalUtilizedAmount = totalUtilizedAmount.add(shoppingCartDataTableBean.getForeignTranxAmount());
							mapcustSplID.put(customerSpecialDealRequest.getCustomerSpecialDealReqId(), (customerSpecialDealRequest
									.getUtilizedAmount() == null ? BigDecimal.ZERO : customerSpecialDealRequest.getUtilizedAmount())
									.add(totalUtilizedAmount));
							lstcustSplDetails.addAll(lstSplCustDeal);
						} else {
							BigDecimal utilizedValue = mapcustSplID.get(customerSpecialDealRequest.getCustomerSpecialDealReqId());
							totalUtilizedAmount = totalUtilizedAmount.add(shoppingCartDataTableBean.getForeignTranxAmount());
							totalUtilizedAmount = totalUtilizedAmount.add(utilizedValue);
							mapcustSplID.put(customerSpecialDealRequest.getCustomerSpecialDealReqId(), totalUtilizedAmount);
						}
					}
				}
			}
			if (shoppingCartDataTableBean.getApplicationType().equalsIgnoreCase(Constants.Remittance)) {
				DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");
				criteria.add(Restrictions.eq("remittanceTransaction.applicationFinanceYear", shoppingCartDataTableBean.getDocumentFinanceYear()));
				criteria.add(Restrictions.eq("remittanceTransaction.applicationDocumentNo", shoppingCartDataTableBean.getDocumentNo()));
				criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
				List<RemittanceTransaction> objList = (List<RemittanceTransaction>) findAll(criteria);
				if (objList.size() != 0) {
					objupdateList.addAll(objList);
				}
			} else if (shoppingCartDataTableBean.getApplicationType().equalsIgnoreCase(Constants.FCSale)) {
				DetachedCriteria criteria = DetachedCriteria.forClass(ReceiptPayment.class, "receiptPayment");
				criteria.add(Restrictions.eq("receiptPayment.applicationFinanceYear", shoppingCartDataTableBean.getDocumentFinanceYear()));
				criteria.add(Restrictions.eq("receiptPayment.applicationDocumentNo", shoppingCartDataTableBean.getDocumentNo()));
				criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
				List<ReceiptPayment> lstReceiptPayment = (List<ReceiptPayment>) findAll(criteria);
				if (lstReceiptPayment.size() != 0) {
					lstReceiptPaymentDetails.addAll(lstReceiptPayment);
				}
			} else {
				LOGGER.info("Not Matching");
			}
		}
		for (RemittanceTransaction remittanceTransaction : objupdateList) {
			RemittanceTransaction remitTrnx = (RemittanceTransaction) getSession().get(RemittanceTransaction.class,
					remittanceTransaction.getRemittanceTransactionId());
			remitTrnx.setCollectionDocId(collect.getDocumentCode());
			remitTrnx.setCollectionDocCode(collect.getDocumentCode());
			remitTrnx.setCollectionDocFinanceYear(collect.getDocumentFinanceYear());
			remitTrnx.setCollectionDocumentNo(collect.getDocumentNo());
			getSession().update(remitTrnx);
		}
		for (ReceiptPayment lstRecPay : lstReceiptPaymentDetails) {
			ReceiptPayment receiptPay = (ReceiptPayment) getSession().get(ReceiptPayment.class, lstRecPay.getReceiptId());
			receiptPay.setColDocCode(collect.getDocumentCode());
			receiptPay.setColDocFyr(collect.getDocumentFinanceYear());
			receiptPay.setColDocNo(collect.getDocumentNo());
			getSession().update(receiptPay);
		}
		/*
		 * for (CustomerSpecialDealRequest lstSplCust : lstcustSplDetails) {
		 * CustomerSpecialDealRequest custSplId = (CustomerSpecialDealRequest)
		 * getSession().get(CustomerSpecialDealRequest.class,
		 * lstSplCust.getCustomerSpecialDealReqId()); BigDecimal
		 * finalUtilizedAmount =
		 * mapcustSplID.get(lstSplCust.getCustomerSpecialDealReqId());
		 * custSplId.setUtilizedAmount(finalUtilizedAmount);
		 * getSession().update(custSplId); }
		 */
	}

	@Override
	public void saveUpdateCustSplDealUtilizedAmount(BigDecimal custSplDealId, BigDecimal utilizedAmount) {
		CustomerSpecialDealRequest custSplId = (CustomerSpecialDealRequest) getSession().get(CustomerSpecialDealRequest.class, custSplDealId);
		custSplId.setUtilizedAmount(utilizedAmount);
		getSession().update(custSplId);
	}

	@Override
	public void deleteShoppingCartByApplId(BigDecimal remittanceApplicationId) {
		LOGGER.info("Entered into deleteShoppingCartByApplId() Method ");
		LOGGER.info("remittanceApplicationId =" + remittanceApplicationId);
		RemittanceApplication remitApplId = (RemittanceApplication) getSession().get(RemittanceApplication.class, remittanceApplicationId);
		remitApplId.setIsactive(Constants.D);
		remitApplId.setModifiedBy(sessionStateManage.getUserName());
		remitApplId.setModifiedDate(new Date());
		getSession().update(remitApplId);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemitApplAml.class, "remitApplAml");
		criteria.setFetchMode("remitApplAml.exRemittanceAppfromAml", FetchMode.JOIN);
		criteria.createAlias("remitApplAml.exRemittanceAppfromAml", "exRemittanceAppfromAml", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exRemittanceAppfromAml.remittanceApplicationId", remittanceApplicationId));
		List<RemitApplAml> remitAppAml = (List<RemitApplAml>) findAll(criteria);
		for (RemitApplAml remitApplAml : remitAppAml) {
			remitApplAml.setIsactive(Constants.D);
			remitApplAml.setModifiedBy(sessionStateManage.getUserName());
			remitApplAml.setModifiedDate(new Date());
			getSession().update(remitApplAml);
		}
		DetachedCriteria criteria1 = DetachedCriteria.forClass(RemittanceAppBenificiary.class, "remittanceAppBenificiary");
		criteria1.setFetchMode("remittanceAppBenificiary.exRemittanceAppfromBenfi", FetchMode.JOIN);
		criteria1.createAlias("remittanceAppBenificiary.exRemittanceAppfromBenfi", "exRemittanceAppfromBenfi", JoinType.INNER_JOIN);
		criteria1.add(Restrictions.eq("exRemittanceAppfromBenfi.remittanceApplicationId", remittanceApplicationId));
		List<RemittanceAppBenificiary> remitAppBeneList = (List<RemittanceAppBenificiary>) findAll(criteria1);
		for (RemittanceAppBenificiary remitBene : remitAppBeneList) {
			remitBene.setIsactive(Constants.D);
			remitBene.setModifiedBy(sessionStateManage.getUserName());
			remitBene.setModifiedDate(new Date());
			getSession().update(remitBene);
		}
		DetachedCriteria criteria2 = DetachedCriteria.forClass(AdditionalInstructionData.class, "additionalInstructionData");
		criteria2.setFetchMode("additionalInstructionData.exRemittanceApplication", FetchMode.JOIN);
		criteria2.createAlias("additionalInstructionData.exRemittanceApplication", "exRemittanceApplication", JoinType.INNER_JOIN);
		criteria2.add(Restrictions.eq("exRemittanceApplication.remittanceApplicationId", remittanceApplicationId));
		List<AdditionalInstructionData> remitAddInst = (List<AdditionalInstructionData>) findAll(criteria2);
		for (AdditionalInstructionData additionalInstructionData : remitAddInst) {
			additionalInstructionData.setIsactive(Constants.D);
			additionalInstructionData.setModifiedBy(sessionStateManage.getUserName());
			additionalInstructionData.setModifiedDate(new Date());
			getSession().update(additionalInstructionData);
		}
		
		System.out.println("remitApplId.getDocumentNo() :"+remitApplId.getDocumentNo());
		if(remitApplId.getDocumentNo()!=null){
			String sqlQry = "DELETE from EX_AMTB_COUPON where APPL_DOCUMENT_NO="+remitApplId.getDocumentNo();
			int ui = getSession().createSQLQuery(sqlQry).executeUpdate();
		}
	}

	@Override
	public HashMap<String, Object> findUserCustomerOrEmployee(String userName, String customertype) {
		LOGGER.info("Entered into findUserCustomerOrEmployee(String userName, String customertype) Method ");
		LOGGER.info("userName =" + userName);
		LOGGER.info("customertype =" + customertype);

		HashMap<String, Object> rtnMapUser = new HashMap<String, Object>();
		if (customertype.equalsIgnoreCase(Constants.USER_TYPE_ONLINE_CUSTOMERID)) {
			DetachedCriteria criteria = DetachedCriteria.forClass(CustomerLogin.class, "customerLogin");
			criteria.add(Restrictions.eq("customerLogin.userName", userName));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<CustomerLogin> lstCustomerLogin = (List<CustomerLogin>) findAll(criteria);
			if (lstCustomerLogin.size() > 0) {
				rtnMapUser.put("CustomerLogin", Boolean.TRUE);
			} else {
				rtnMapUser.put("CustomerLogin", Boolean.FALSE);
			}
		} else if (customertype.equalsIgnoreCase(Constants.USER_TYPE_EMPLOYEEID)) {
			DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
			criteria.add(Restrictions.eq("employee.userName", userName));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<Employee> lstEmployee = (List<Employee>) findAll(criteria);
			if (lstEmployee.size() > 0) {
				rtnMapUser.put("Employee", Boolean.TRUE);
			} else {
				rtnMapUser.put("Employee", Boolean.FALSE);
			}
		} else {
			LOGGER.info("non value");
		}
		LOGGER.info("Exited from findUserCustomerOrEmployee Method ");
		return rtnMapUser;
	}

	@Override
	public List<RelationsDescription> getRelationsDescriptionList(BigDecimal languageId) {
		LOGGER.info("Entered into getRelationsDescriptionList(BigDecimal languageId) Method ");
		LOGGER.info("languageId=" + languageId);
		DetachedCriteria criteria = DetachedCriteria.forClass(RelationsDescription.class, "relationsDescription");
		criteria.setFetchMode("relationsDescription.relations", FetchMode.JOIN);
		criteria.createAlias("relationsDescription.relations", "relations", JoinType.INNER_JOIN);
		criteria.setFetchMode("relationsDescription.languageType", FetchMode.JOIN);
		criteria.createAlias("relationsDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("relations.isActive", Constants.Yes));
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the getRelationsDescriptionList Method ");
		return (List<RelationsDescription>) findAll(criteria);
	}

	@Override
	public void updatePayIdPayTokenInAppTrnx(List<ShoppingCartDataTableBean> lstSelected, String payToken, String payId) {
		LOGGER.info("Entered into updatePayIdPayTokenInAppTrnx() Method");
		LOGGER.info("lstSelected =" + lstSelected.size());
		LOGGER.info("payToken =" + payToken);
		LOGGER.info("payId =" + payId);
		List<ShoppingCartDataTableBean> lstSelectedRecords = lstSelected;
		for (ShoppingCartDataTableBean shoppingCartDataTableBean : lstSelectedRecords) {
			if (shoppingCartDataTableBean.getRemittanceApplicationId() != null) {
				RemittanceApplication applTrnx = (RemittanceApplication) getSession().get(RemittanceApplication.class,
						shoppingCartDataTableBean.getRemittanceApplicationId());
				applTrnx.setPaymentId(payId);
				applTrnx.setPayToken(payToken);
				getSession().update(applTrnx);
			}
		}
	}

	@Override
	public List<ShoppingCartDetails> getRecordsRemittanceApplication(BigDecimal documentNo) {
		LOGGER.info("Entered into getRecordsRemittanceApplication(BigDecimal documentNo) Method ");
		LOGGER.info("documentNo =" + documentNo);
		DetachedCriteria criteria = DetachedCriteria.forClass(ShoppingCartDetails.class, "shoppingCartDetails");
		criteria.add(Restrictions.eq("shoppingCartDetails.documentNo", documentNo));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the getRecordsRemittanceApplication Method ");
		return (List<ShoppingCartDetails>) findAll(criteria);
	}

	@Override
	public List<RemittanceApplicationView> getRecordsRemittanceReceipt(BigDecimal documentNo) {
		LOGGER.info("Entered into getRecordsRemittanceReceipt(BigDecimal documentNo) Method");
		LOGGER.info("documentNo =" + documentNo);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplicationView.class, "remittanceApplicationView");
		criteria.add(Restrictions.eq("remittanceApplicationView.collectionDocumentNo", documentNo));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from getRecordsRemittanceReceipt Method ");
		return (List<RemittanceApplicationView>) findAll(criteria);
	}

	@Override
	public List<RemittanceApplicationView> getRecordsForRemittanceReceiptReport(BigDecimal documentNo, BigDecimal financeYear, String documentCode) {
		LOGGER.info("Entered into getRecordsRemittanceReceipt(BigDecimal documentNo) Method");
		LOGGER.info("documentNo =" + documentNo);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplicationView.class, "remittanceApplicationView");
		criteria.add(Restrictions.eq("remittanceApplicationView.collectionDocumentNo", documentNo));
		criteria.add(Restrictions.eq("remittanceApplicationView.collectionDocFinanceYear", financeYear));
		criteria.add(Restrictions.eq("remittanceApplicationView.collectionDocCode", new BigDecimal(documentCode)));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from getRecordsRemittanceReceipt Method ");
		return (List<RemittanceApplicationView>) findAll(criteria);
	}

	@Override
	public List<PurposeOfRemittanceView> getPurposeOfRemittance(BigDecimal documentNo, BigDecimal documentYear) {
		LOGGER.info("Entered into getPurposeOfRemittance(BigDecimal documentNo) Method");
		LOGGER.info("documentNo =" + documentNo);
		DetachedCriteria criteria = DetachedCriteria.forClass(PurposeOfRemittanceView.class, "purposeOfRemittanceView");
		criteria.add(Restrictions.eq("purposeOfRemittanceView.documentNumber", documentNo));
		criteria.add(Restrictions.eq("purposeOfRemittanceView.documentFinancialYear", documentYear));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from getPurposeOfRemittance Method");
		return (List<PurposeOfRemittanceView>) findAll(criteria);
	}

	@Override
	public List<RemittanceApplicationPurpose> getPurposeOfRemittanceForApplication(BigDecimal applicationCountryId, BigDecimal documentFinancialYear,
			BigDecimal documentId, BigDecimal documentNo) {
		LOGGER.info("Entered into getPurposeOfRemittanceForApplication(BigDecimal applicationCountryId,BigDecimal documentFinancialYear,BigDecimal documentId,BigDecimal documentNo) Method");
		LOGGER.info("applicationCountryId =" + applicationCountryId);
		LOGGER.info("documentFinancialYear =" + documentFinancialYear);
		LOGGER.info("documentId =" + documentId);
		LOGGER.info("documentNo =" + documentNo);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplicationPurpose.class, "remittanceApplicationPurpose");
		// criteria.add(Restrictions.eq("remittanceApplicationPurpose.applicationCountryId",
		// applicationCountryId));
		criteria.add(Restrictions.eq("remittanceApplicationPurpose.documentFinanceYear", documentFinancialYear));
		// criteria.add(Restrictions.eq("remittanceApplicationPurpose.documentId",
		// documentId));
		criteria.add(Restrictions.eq("remittanceApplicationPurpose.documentNo", documentNo));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit from the getPurposeOfRemittanceForApplication() Method ");
		return (List<RemittanceApplicationPurpose>) findAll(criteria);
	}

	public List<CollectionDetailView> getCollectionDetailForRemittanceReceipt(BigDecimal companyId, BigDecimal documentNumber,
			BigDecimal financeYear, BigDecimal documentCode) {
		LOGGER.info("Entered into getCollectionDetailForRemittanceReceipt(BigDecimal documentNo)  Method ");
		LOGGER.info("DocumentNo =" + documentNumber);
		DetachedCriteria criteria = DetachedCriteria.forClass(CollectionDetailView.class, "collectionDetailView");
		criteria.add(Restrictions.eq("collectionDetailView.companyId", companyId));
		criteria.add(Restrictions.eq("collectionDetailView.documentNo", documentNumber));
		criteria.add(Restrictions.eq("collectionDetailView.documentFinancialYear", financeYear));
		criteria.add(Restrictions.eq("collectionDetailView.documentCode", documentCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the getCollectionDetailForRemittanceReceipt Method");
		return (List<CollectionDetailView>) findAll(criteria);
	}

	public List<CollectionPaymentDetailsView> getCollectionPaymentDetailForRemittanceReceipt(BigDecimal companyId, BigDecimal documentNumber,
			BigDecimal financeYear, BigDecimal documentCode) {
		LOGGER.info("Entered into getCollectionPaymentDetailForRemittanceReceipt(BigDecimal documentNo) Method");
		LOGGER.info("DocumentNo =" + documentNumber);
		DetachedCriteria criteria = DetachedCriteria.forClass(CollectionPaymentDetailsView.class, "collectionPaymentDetailsView");
		criteria.add(Restrictions.eq("collectionPaymentDetailsView.companyId", companyId));
		criteria.add(Restrictions.eq("collectionPaymentDetailsView.documentNo", documentNumber));
		criteria.add(Restrictions.eq("collectionPaymentDetailsView.documentFinancialYear", financeYear));
		criteria.add(Restrictions.eq("collectionPaymentDetailsView.documentCode", documentCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the getCollectionPaymentDetailForRemittanceReceipt Method ");

		return (List<CollectionPaymentDetailsView>) findAll(criteria);
	}

	@Override
	public List<ServiceGroupMasterDesc> fetchCashServiceGorupId(String cASHNAME, BigDecimal languageId) {
		LOGGER.info("Entered into fetchCashServiceGorupId(String cASHNAME, BigDecimal languageId) Method ");
		LOGGER.info("cASHNAME =" + cASHNAME);
		LOGGER.info("languageId =" + languageId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceGroupMasterDesc.class, "serviceGroupMasterDesc");
		criteria.setFetchMode("serviceGroupMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceGroupMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.add(Restrictions.eq("serviceGroupMasterDesc.serviceGroupDesc", cASHNAME));
		LOGGER.info("Exited from the fetchCashServiceGorupId Method");
		return (List<ServiceGroupMasterDesc>) findAll(criteria);
	}

	@Override
	public List<ServiceGroupMasterDesc> getAllServiceGroupDesc(BigDecimal languageId) {
		LOGGER.info("Entered into getAllServiceGroupDesc(BigDecimal languageId)  Method ");
		LOGGER.info("languageId =" + languageId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceGroupMasterDesc.class, "serviceGroupMasterDesc");
		criteria.setFetchMode("serviceGroupMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceGroupMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.setFetchMode("serviceGroupMasterDesc.serviceGroupMasterId", FetchMode.JOIN);
		criteria.createAlias("serviceGroupMasterDesc.serviceGroupMasterId", "serviceMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.isActive", Constants.Yes));
		criteria.addOrder(Order.asc("serviceGroupMasterDesc.serviceGroupDesc"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the  getAllServiceGroupDesc Method");
		return (List<ServiceGroupMasterDesc>) findAll(criteria);
	}

	@Override
	public boolean bankIndcheckingbank(String bankInd, BigDecimal bankId) {
		LOGGER.info("Entered into bankIndcheckingbank(String bankInd, BigDecimal bankId)  Method ");
		LOGGER.info("bankInd =" + bankInd);
		LOGGER.info("bankId =" + bankId);
		Boolean statusofBank = false;
		BigDecimal corresBankIndicatorId = fetchLocalBankIndicator(bankInd);
		BigDecimal BankIndicatorId = bankFetchBankIndicator(bankId);
		if (corresBankIndicatorId.compareTo(BankIndicatorId) == 0) {
			statusofBank = true;
		} else {
			statusofBank = false;
		}
		LOGGER.info("Exited from the bankIndcheckingbank() Method");
		return statusofBank;
	}

	public BigDecimal bankFetchBankIndicator(BigDecimal bankId) {
		LOGGER.info(" Entered into bankFetchBankIndicator(BigDecimal bankId) Method ");
		LOGGER.info("bankId" + bankId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankApplicability> lstBankIndId = (List<BankApplicability>) findAll(dCriteria);
		if (lstBankIndId.size() != 0) {
			LOGGER.info("Exited from bankFetchBankIndicator method");
			return ((List<BankApplicability>) findAll(dCriteria)).get(0).getBankInd().getBankIndicatorId();
		}
		return null;
	}

	public BigDecimal fetchLocalBankIndicator(String bankIndicatorCode) {
		LOGGER.info("Entered into fetchLocalBankIndicator(String bankIndicatorCode) Method");
		LOGGER.info("bankIndicatorCode = " + bankIndicatorCode);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankIndicator.class, "bankIndicator");
		dCriteria.add(Restrictions.eq("bankIndicator.bankIndicatorCode", bankIndicatorCode));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankIndicator> lstBankIndId = (List<BankIndicator>) findAll(dCriteria);
		if (lstBankIndId.size() != 0) {
			LOGGER.info("Exit from fetchLocalBankIndicator");
			return ((List<BankIndicator>) findAll(dCriteria)).get(0).getBankIndicatorId();
		}
		return null;
	}

	@Override
	public List<BeneCountryService> getServiceIdBeneCountrySer(BigDecimal beneficaryCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId,
			BigDecimal bankId) {
		LOGGER.info("Entered into getServiceIdBeneCountrySer(BigDecimal beneficaryCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId, BigDecimal bankId)");
		LOGGER.info("beneficaryCountryId =" + beneficaryCountryId);
		LOGGER.info("beneficaryCurrencyId =" + beneficaryCurrencyId);
		LOGGER.info("serviceId =" + serviceId);
		LOGGER.info("bankId =" + bankId);
		List<BigDecimal> lstServiceId = new ArrayList<BigDecimal>();
		String sql = "select DISTINCT  * from  EX_BENE_COUNTRY_SERVICE a " + "  where   a.BENE_COUNTRY_ID  =   " + beneficaryCountryId + " "
				+ " and  a.CURRENCY_ID   =  " + beneficaryCurrencyId + " "
				+ " and  a.SERVICE_MASTER_ID  IN ( SELECT DISTINCT SERVICE_MASTER_ID FROM EX_SERVICE_MASTER WHERE SERVICE_GROUP_ID = " + serviceId
				+ " )" + " and  a.ISACTIVE = '" + Constants.Yes + "'";

		LOGGER.info("getServiceIdBeneCountrySer query=" + sql);
		SQLQuery query = getSession().createSQLQuery(sql).addEntity(BeneCountryService.class);
		List<BeneCountryService> beneficaryCountryList = query.list();
		LOGGER.info("$$$$$$$routingCountrylist routingCountrylist$$$$$$$$" + beneficaryCountryList.size());
		return beneficaryCountryList;
	}

	public List<CutomerDetailsView> getCustomerDetails(BigDecimal customerId) {
		LOGGER.info("Entered into getCustomerDetails(BigDecimal customerId) method");
		LOGGER.info("customerId =" + customerId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CutomerDetailsView.class, "cutomerDetailsView");
		dCriteria.add(Restrictions.eq("cutomerDetailsView.customerId", customerId));
		dCriteria.add(Restrictions.ge("cutomerDetailsView.idExp", new Date()));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CutomerDetailsView> lstBankIndId = (List<CutomerDetailsView>) findAll(dCriteria);
		LOGGER.info("Exited from the getCustomerDetails() method ");
		return lstBankIndId;
	}

	@Override
	public List<BeneficaryRelationship> getCustomerBeneficaryDetailswithRelations(BigDecimal beneficaryMasterSeqId) {
		LOGGER.info("Entered into getCustomerBeneficaryDetailswithRelations(BigDecimal beneficaryMasterSeqId) Method ");
		LOGGER.info("beneficaryMasterSeqId =" + beneficaryMasterSeqId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");
		criteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficaryMasterSeqId));
		criteria.setFetchMode("beneficaryRelationship.beneficaryAccount", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryAccount", "beneficaryAccount", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.beneficaryCountry", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryCountry", "beneficaryCountry", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.servicegropupId", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.servicegropupId", "servicegropupId", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.currencyId", "currencyId", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.bank", "bank", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.bankBranch", "bankBranch", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryRelationship.customerId", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.customerId", "customerId", JoinType.INNER_JOIN);
		criteria.setFetchMode("beneficaryRelationship.relations", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.relations", "relations", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryRelationship> acclist = (List<BeneficaryRelationship>) findAll(criteria);
		LOGGER.info("Exited from the getCustomerBeneficaryDetailswithRelations method ");
		return acclist;
	}

	@Override
	public List<BeneficaryRelationship> isBenificaryRelationExist(BigDecimal beneficaryMasterSeqId, BigDecimal beneficaryAccountSeqId) {
		LOGGER.info("Entered into isBenificaryRelationExist(BigDecimal beneficaryMasterSeqId, BigDecimal beneficaryAccountSeqId) Method ");
		LOGGER.info("beneficaryMasterSeqId =" + beneficaryMasterSeqId);
		LOGGER.info("beneficaryAccountSeqId =" + beneficaryAccountSeqId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");

		criteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficaryMasterSeqId));

		criteria.setFetchMode("beneficaryRelationship.customerId", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.customerId", "customerId", JoinType.INNER_JOIN);

		criteria.setFetchMode("beneficaryRelationship.beneficaryAccount", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryAccount", "beneficaryAccount", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryAccount.beneficaryAccountSeqId", beneficaryAccountSeqId));

		criteria.add(Restrictions.eq("beneficaryRelationship.isActive", Constants.Yes));

		criteria.add(Restrictions.eq("beneficaryMaster.isActive", Constants.Yes));

		List<BeneficaryRelationship> beneficaryRship = (List<BeneficaryRelationship>) findAll(criteria);

		LOGGER.info("Exit from the isBenificaryRelationExist Method ");
		return beneficaryRship;
	}

	@Override
	public Map<String, Object> checkTelephoneExist(BigDecimal telephoneNumber, String countryCode, String string) {
		LOGGER.info("Entering into checkTelephoneExistWithCustId method");
		LOGGER.info("telephoneNo " + telephoneNumber);
		LOGGER.info("teleCountryID " + countryCode);
		LOGGER.info("teleCountryID " + string);
		Map<String, Object> mapTeleExist = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "beneficaryTelaphone");
		if (string.equals("telephone")) {
			criteria.add(Restrictions.eq("beneficaryTelaphone.telephoneNumber", telephoneNumber.toPlainString()));
		} else if (string.equals("mobile")) {
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
				LOGGER.info("lstBeneficaryAccount ========== " + lstBeneficaryAccount.size());
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
	public List<BeneficaryAccount> isBankAccountNumberExist(BigDecimal bankAccountNumber, BigDecimal benifisBankId, BigDecimal benifisCurrencyId,
			BigDecimal benifisCountryId, BigDecimal servicebankBranchId) {
		LOGGER.info("Entering into isBankAccountNumberExist method ");
		LOGGER.info("bankAccountNumber " + bankAccountNumber);
		LOGGER.info("benifisCurrencyId " + benifisCurrencyId);
		LOGGER.info("benifisCountryId " + benifisCountryId);
		LOGGER.info("servicebankBranchId " + servicebankBranchId);
		LOGGER.info("benifisBankId " + benifisBankId);
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
		LOGGER.info("Exited from the isBankAccountNumberExist Method ");
		return accountNumberList;
	}

	@Override
	public void updateBeneficaryData(BeneficaryMaster beneficaryMaster, BeneficaryAccount account, BeneficaryRelationship beneficaryRelationship,
			BeneficaryContact contact) {
		getSession().saveOrUpdate(beneficaryMaster);
		account.setBeneficaryMaster(beneficaryMaster);
		getSession().saveOrUpdate(account);
		beneficaryRelationship.setBeneficaryMaster(beneficaryMaster);
		getSession().saveOrUpdate(beneficaryRelationship);
		contact.setBeneficaryMaster(beneficaryMaster);
		getSession().saveOrUpdate(contact);
	}

	@Override
	public List<BeneficaryAccount> getCustomerBeneficaryDetailswithAll(BigDecimal masterseqId, String type) {
		LOGGER.info("Entering into getCustomerBeneficaryDetailswithAll method");
		LOGGER.info("masterseqId " + masterseqId);
		LOGGER.info("type " + type);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", masterseqId));
		// criteria.add(Restrictions.isNotNull("beneficaryAccount.bankAccountNumber"));
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
		// criteria.setFetchMode("beneficaryAccount.bankBranch",
		// FetchMode.JOIN);
		// criteria.createAlias("beneficaryAccount.bankBranch", "bankBranch",
		// JoinType.INNER_JOIN);
		if (!type.equals("") && type.equals("cash")) {
			criteria.setFetchMode("beneficaryAccount.serviceProvider", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.serviceProvider", "serviceProvider", JoinType.INNER_JOIN);
		}
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryAccount> acclist = (List<BeneficaryAccount>) findAll(criteria);
		LOGGER.info("Exit from getCustomerBeneficaryDetailswithAll method");
		return acclist;
	}

	@Override
	public BigDecimal getserviceGroupforCash(BigDecimal languageId) {
		LOGGER.info("Entered into getserviceGroupforCash(BigDecimal languageId) Method ");
		LOGGER.info("LanguageId =" + languageId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceGroupMasterDesc.class, "serviceGroupMasterDesc");
		criteria.setFetchMode("serviceGroupMasterDesc.serviceGroupMasterId", FetchMode.JOIN);
		criteria.createAlias("serviceGroupMasterDesc.serviceGroupMasterId", "beneficaryCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceGroupMasterDesc.serviceGroupDesc", Constants.CASHNAME));
		criteria.setFetchMode("serviceGroupMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceGroupMasterDesc.languageType", "langauage", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("langauage.languageId", languageId));
		List<ServiceGroupMasterDesc> descList = (List<ServiceGroupMasterDesc>) findAll(criteria);
		if (descList != null && !descList.isEmpty()) {
			LOGGER.info("Exited from the getserviceGroupforCash method");
			return descList.get(0).getServiceGroupMasterDescId();
		}
		LOGGER.info("Exited from the getserviceGroupforCash method");
		return null;
	}

	@Override
	public Map<String, Object> checkTelephoneExistWithCustIdwithPhone(BigDecimal telephoneNo, BigDecimal customerID, String teleCountryID, String type) {
		LOGGER.info("Entered into checkTelephoneExistWithCustIdwithPhone(BigDecimal telephoneNo, BigDecimal customerID, String teleCountryID, String type) Method");
		LOGGER.info("telephoneNo =" + telephoneNo);
		LOGGER.info("customerID =" + customerID);
		LOGGER.info("teleCountryID =" + teleCountryID);
		LOGGER.info("type =" + type);
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
				beneAccountCriteria.setFetchMode("beneficaryMaster.fsCityMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryMaster.fsCityMaster", "fsCityMaster", JoinType.INNER_JOIN);
				beneAccountCriteria.setFetchMode("beneficaryMaster.fsDistrictMaster", FetchMode.JOIN);
				beneAccountCriteria.createAlias("beneficaryMaster.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);
				beneAccountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
				List<BeneficaryAccount> lstBeneficaryAccount = (List<BeneficaryAccount>) findAll(beneAccountCriteria);
				LOGGER.info("lstBeneficaryAccount ========== " + lstBeneficaryAccount.size());
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
		LOGGER.info("Exited from the checkTelephoneExistWithCustIdwithPhone method ");
		return mapTeleExist;
	}

	@Override
	public List<BeneficaryRelationship> getRelationshipRecord(BigDecimal benificaryMasterId, BigDecimal beneficaryAccountSeqId) {
		return null;
	}

	@Override
	public List<BeneficaryAccount> getCustomerBeneficaryDetailswithAccountNO(BigDecimal benificaryMasterId, String accountNo, String type) {
		LOGGER.info("Entering into getCustomerBeneficaryDetailswithAll method");
		LOGGER.info("masterseqId " + benificaryMasterId);
		LOGGER.info("type " + type);
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
		LOGGER.info("Exit into getCustomerBeneficaryDetailswithAll method");
		return acclist;
	}

	@Override
	public List<BeneficaryAccount> isCashAccountExist(BigDecimal benifisBankId, BigDecimal benifisCurrencyId, BigDecimal benifisCountryId,
			BigDecimal servicebankBranchId) {
		LOGGER.info("Entering into isBankAccountNumberExist method ");
		LOGGER.info("benifisCurrencyId " + benifisCurrencyId);
		LOGGER.info("benifisCountryId " + benifisCountryId);
		LOGGER.info("servicebankBranchId " + servicebankBranchId);
		LOGGER.info("benifisBankId " + benifisBankId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		criteria.add(Restrictions.isNull("beneficaryAccount.bankAccountNumber"));
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
		LOGGER.info("Exited from the isCashAccountExist() method ");
		return accountNumberList;
	}

	@Override
	public BigDecimal getbankIndicator(String bankIndicatorAgentBank, BigDecimal languageId) {
		LOGGER.info("Entered into getbankIndicator(String bankIndicatorAgentBank, BigDecimal languageId) Method ");
		LOGGER.info("bankIndicatorAgentBank =" + bankIndicatorAgentBank);
		LOGGER.info("languageId =" + languageId);

		DetachedCriteria criteria = DetachedCriteria.forClass(BankIndicatorDescription.class, "bankIndicatorDescription");
		criteria.setFetchMode("bankIndicatorDescription.languageType", FetchMode.JOIN);
		criteria.createAlias("bankIndicatorDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.setFetchMode("bankIndicatorDescription.bankIndicator", FetchMode.JOIN);
		criteria.createAlias("bankIndicatorDescription.bankIndicator", "bankIndicator", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankIndicator.bankIndicatorCode", bankIndicatorAgentBank));
		/*
		 * criteria.add(Restrictions.eq(
		 * "bankIndicatorDescription.bankIndicatorDescription",
		 * bankIndicatorAgentBank));
		 */
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankIndicatorDescription> list = (List<BankIndicatorDescription>) findAll(criteria);
		if (list != null && !list.isEmpty()) {
			LOGGER.info("Exited from the getbankIndicator(String bankIndicatorAgentBank, BigDecimal languageId) Method  ");
			return list.get(0).getBankIndicator().getBankIndicatorId();
		}
		LOGGER.info("Exited from the getbankIndicator(String bankIndicatorAgentBank, BigDecimal languageId) Method  ");
		return null;
	}

	@Override
	public BigDecimal roundingTotalNetAmountbyFunction(BigDecimal applcountryId, BigDecimal totalNetAmount, String roundstatus) throws AMGException {
		// TODO Auto-generated method stub
		LOGGER.info("Entered into roundingTotalNetAmountbyFunction(BigDecimal applcountryId, BigDecimal totalNetAmount, String roundstatus) Method");
		LOGGER.info("Procedure Name =  EX_FN_EXCH_AMOUNT_ROUND");
		LOGGER.info("applcountryId =" + applcountryId);
		LOGGER.info("totalNetAmount =" + totalNetAmount);
		LOGGER.info("roundstatus =" + roundstatus);
		BigDecimal roundvalue = null;
		Connection connection = null;
		try {
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.error("Error Occured in EX_FN_EXCH_AMOUNT_ROUND Procedure call");
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { ? = call EX_FN_EXCH_AMOUNT_ROUND (?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.registerOutParameter(1, java.sql.Types.INTEGER);
			cs.setBigDecimal(2, applcountryId);
			cs.setBigDecimal(3, totalNetAmount);
			cs.setString(4, roundstatus);
			cs.setString(5, "B");
			cs.execute();
			roundvalue = cs.getBigDecimal(1);
			LOGGER.info("roundingTotalNetAmountbyFunction applcountryId:" + applcountryId);
			LOGGER.info("roundingTotalNetAmountbyFunction totalNetAmount:" + totalNetAmount);
			LOGGER.info("roundingTotalNetAmountbyFunction roundstatus:" + roundstatus);
			LOGGER.info("roundingTotalNetAmountbyFunction roundvalue:" + roundvalue);
		} catch (SQLException e) {
			LOGGER.info("Error while calling EX_FN_EXCH_AMOUNT_ROUND procedure ");
			String erromsg = "EX_FN_EXCH_AMOUNT_ROUND" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Error while calling EX_FN_EXCH_AMOUNT_ROUND procedure ");
				String erromsg = "EX_FN_EXCH_AMOUNT_ROUND" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		LOGGER.info("Exited from the roundingTotalNetAmountbyFunction method");
		return roundvalue;
	}

	@Override
	public void saveApplicationTransactionModifiedRecordByRound(ShoppingCartDataTableBean lstShoppingCart) {
		// TODO Auto-generated method stub
		RemittanceApplication applTrnx = (RemittanceApplication) getSession().get(RemittanceApplication.class,
				lstShoppingCart.getRemittanceApplicationId());
		applTrnx.setLocalTranxAmount(lstShoppingCart.getLocalTranxAmount());
		applTrnx.setLocalNetTranxAmount(lstShoppingCart.getLocalNextTranxAmount());
		applTrnx.setForeignTranxAmount(lstShoppingCart.getForeignTranxAmount());
		getSession().update(applTrnx);
	}

	@Override
	public void updateApplTrnxError(List<RemittanceApplication> lstShoppingCart, String status) {
		// TODO Auto-generated method stub
		for (RemittanceApplication remittanceShoppingCart : lstShoppingCart) {
			RemittanceApplication remittanceApplication = (RemittanceApplication) getSession().get(RemittanceApplication.class,
					remittanceShoppingCart.getRemittanceApplicationId());
			remittanceApplication.setPayToken(null);
			remittanceApplication.setResultCode(status);
			getSession().update(remittanceApplication);
		}
	}

	@Override
	public List<RemittanceApplication> fetchRemitApplTrnxRecordsByPayId(String paymentId) {
		LOGGER.info("Entered into fetchRemitApplTrnxRecordsByPayId(String paymentId) Method ");
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplication.class, "remittanceApplication");
		criteria.add(Restrictions.eq("remittanceApplication.paymentId", paymentId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceApplication> lstremitapp = (List<RemittanceApplication>) findAll(criteria);
		LOGGER.info("Exited from the fetchRemitApplTrnxRecordsByPayId(String paymentId) ");
		return lstremitapp;
	}

	@Override
	public void updatePayTokenNull(List<RemittanceApplication> lstPayIdDetails, String status) {
		// TODO Auto-generated method stub
		for (RemittanceApplication shoppingCartDataTableBean : lstPayIdDetails) {
			RemittanceApplication remittanceApplication = (RemittanceApplication) getSession().get(RemittanceApplication.class,
					shoppingCartDataTableBean.getRemittanceApplicationId());
			remittanceApplication.setPayToken(null);
			remittanceApplication.setResultCode(status);
			getSession().update(remittanceApplication);
		}
	}

	@Override
	public void callProcedureToCancelRecordsinApplTrnx(String paymentId, String status) throws AMGException {
		// TODO Auto-generated method stub
		LOGGER.info("Entered into callProcedureToCancelRecordsinApplTrnx(String paymentId, String status) Method ");
		LOGGER.info("Procedure Name = EX_UPDATE_ONLINE_APPL");
		LOGGER.info("paymentId =" + paymentId);
		LOGGER.info("status =" + status);
		Connection connection = null;
		try {
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.info("Problem Occured when getting the Connection" + e.getMessage());
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { call EX_UPDATE_ONLINE_APPL (?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setString(1, paymentId);
			cs.setString(2, status);
			cs.execute();
			LOGGER.info("callProcedureToCancelRecordsinApplTrnx paymentId:" + paymentId);
			LOGGER.info("callProcedureToCancelRecordsinApplTrnx status:" + status);
		} catch (SQLException e) {
			LOGGER.info("Problem Occured  While Procedure calling  " + e.getMessage());
			String erromsg = "EX_UPDATE_ONLINE_APPL" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Problem Occured  While Procedure calling  " + e.getMessage());
				String erromsg = "EX_UPDATE_ONLINE_APPL" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
	}

	@Override
	public List<AdditionalBankDetailsView> fetchAdditionalDetails(BigDecimal additionalBankRuleId) {
		LOGGER.info("Entered into fetchAdditionalDetails(BigDecimal additionalBankRuleId) Method");
		LOGGER.info("additionalBankRuleId =" + additionalBankRuleId);
		DetachedCriteria critiria = DetachedCriteria.forClass(AdditionalBankDetailsView.class, "additionalBankDetailsView");
		critiria.add(Restrictions.eq("additionalBankDetailsView.additionalBankRuleId", additionalBankRuleId));
		critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<AdditionalBankDetailsView> additionalList = (List<AdditionalBankDetailsView>) findAll(critiria);
		LOGGER.info("Exited from the fetchAdditionalDetails() Mehtod");
		return additionalList;
	}

	@Override
	public String getCustomerType(BigDecimal componentId) {
		LOGGER.info("Entered into getCustomerType(BigDecimal componentId) Method");
		LOGGER.info("componentId =" + componentId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentData.class, "bizComponentData");
		criteria.add(Restrictions.eq("bizComponentData.componentDataId", componentId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		LOGGER.info("Exited from the  getCustomerType method");
		return ((BizComponentData) criteria.getExecutableCriteria(getSession()).list().get(0)).getComponentCode();
	}

	@Override
	public void saveAuthorizedLog(AuthorizedLog authorizedLog) {
		getSession().save(authorizedLog);
	}

	@Override
	public HashMap<String, String> getRoutingBankSetupDetails(HashMap<String, String> inputValue) throws AMGException {
		LOGGER.info("Entered into getRoutingBankSetupDetails(HashMap<String, String> inputValue) Method ");
		LOGGER.info("Procedure Name = EX_GET_ROUTING_SET_UP");
		Connection connection = null;
		CallableStatement cs = null;
		HashMap<String, String> rtnValues = new HashMap<String, String>();
		LOGGER.info("getRoutingBankSetupDetails :" + inputValue.toString());
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_GET_ROUTING_SET_UP (?,?,?,?,?,?,?,?,?,?,?,?,?,?) } ";
			cs = connection.prepareCall(call);
			// In Parameters
			cs.setBigDecimal(1, new BigDecimal(inputValue.get("P_APPLICATION_COUNTRY_ID")));
			// Added by Rabil on 08/12/2015
			cs.setString(2, inputValue.get("P_USER_TYPE"));
			// Added by Rabil on 08/12/2015
			cs.setBigDecimal(3, new BigDecimal(inputValue.get("P_BENE_COUNTRY_ID")));
			cs.setBigDecimal(4, new BigDecimal(inputValue.get("P_BENE_BANK_ID")));
			cs.setBigDecimal(5, new BigDecimal(inputValue.get("P_BENE_BANK_BRANCH_ID")));
			// cs.setBigDecimal(5, inputValue.get("P_BANK_COUNTRY_ID"));
			cs.setString(6, inputValue.get("P_SERVICE_GROUP_CODE"));
			cs.setBigDecimal(7, new BigDecimal(inputValue.get("P_CURRENCY_ID")));
			// Out Parameters
			cs.registerOutParameter(8, java.sql.Types.INTEGER);
			cs.registerOutParameter(9, java.sql.Types.INTEGER);
			cs.registerOutParameter(10, java.sql.Types.INTEGER);
			cs.registerOutParameter(11, java.sql.Types.INTEGER);
			cs.registerOutParameter(12, java.sql.Types.INTEGER);
			cs.registerOutParameter(13, java.sql.Types.INTEGER);
			cs.registerOutParameter(14, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			BigDecimal pServiceMasterId = cs.getBigDecimal(8);
			BigDecimal pRoutingCountryId = cs.getBigDecimal(9);
			BigDecimal pRoutingBankId = cs.getBigDecimal(10);
			BigDecimal pRoutingBranchId = cs.getBigDecimal(11);
			BigDecimal pRemittanceModeId = cs.getBigDecimal(12);
			BigDecimal pDeliveryModeId = cs.getBigDecimal(13);
			String pErrorMesg = cs.getString(14);
			System.out.println("EX_GET_ROUTING_SET_UP pServiceMasterId :" + pServiceMasterId);

			System.out.println("EX_GET_ROUTING_SET_UP pErrorMesg :" + pErrorMesg);

			rtnValues.put("P_SERVICE_MASTER_ID", pServiceMasterId == null ? "0" : pServiceMasterId.toPlainString());
			rtnValues.put("P_ROUTING_COUNTRY_ID", pRoutingCountryId == null ? "0" : pRoutingCountryId.toPlainString());
			rtnValues.put("P_ROUTING_BANK_ID", pRoutingBankId == null ? "0" : pRoutingBankId.toPlainString());
			rtnValues.put("P_ROUTING_BANK_BRANCH_ID", pRoutingBranchId == null ? "0" : pRoutingBranchId.toPlainString());
			rtnValues.put("P_REMITTANCE_MODE_ID", pRemittanceModeId == null ? "0" : pRemittanceModeId.toPlainString());
			rtnValues.put("P_DELIVERY_MODE_ID", pDeliveryModeId == null ? "0" : pDeliveryModeId.toPlainString());
			rtnValues.put("P_ERROR_MESSAGE", pErrorMesg);
		} catch (SQLException e) {
			String erromsg = "EX_GET_ROUTING_SET_UP" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				cs.close();
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Problem Occured While Procedure calling " + e.getMessage());
				String erromsg = "EX_GET_ROUTING_SET_UP" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		LOGGER.info("Exited from the getRoutingBankSetupDetails method  calling ");
		return rtnValues;
	}

	@Override
	public List<PopulateData> getRoutingCountryList(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId,
			BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal languageId) {
		/*
		 * List<ViewRoutingDetails> finalData = new
		 * ArrayList<ViewRoutingDetails>(); List<PopulateData> lstCountry = new
		 * ArrayList<PopulateData>(); List<BigDecimal> duplicateCheck = new
		 * ArrayList<BigDecimal>(); DetachedCriteria critiria =
		 * DetachedCriteria.
		 * forClass(ViewRoutingDetails.class,"viewRoutingDetails");
		 * 
		 * critiria.add(Restrictions.eq("viewRoutingDetails.applicationCountryId"
		 * , appLicationCountryId));
		 * critiria.add(Restrictions.eq("viewRoutingDetails.countryId",
		 * countryId));
		 * critiria.add(Restrictions.eq("viewRoutingDetails.currencyId",
		 * beneCurrencyId));
		 * critiria.add(Restrictions.eq("viewRoutingDetails.serviceMasterId",
		 * serviceMasterId)); // if ServiceMaster is EFT beneBankId should be
		 * Routing Bank if(serviceMasterId.compareTo(new BigDecimal(101))==0){
		 * critiria.add(Restrictions.eq("viewRoutingDetails.routingBankId",
		 * beneBankId)); }
		 * 
		 * critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY
		 * ); List<ViewRoutingDetails> lstViewRoutingDetails =
		 * (List<ViewRoutingDetails>) findAll(critiria);
		 * 
		 * for (ViewRoutingDetails viewRoutingDetails2 : lstViewRoutingDetails)
		 * {
		 * if(!duplicateCheck.contains(viewRoutingDetails2.getRoutingCountryId(
		 * ))) { duplicateCheck.add(viewRoutingDetails2.getRoutingCountryId());
		 * finalData.add(viewRoutingDetails2); } }
		 * 
		 * for (ViewRoutingDetails viewRoutingDetails : finalData) {
		 * PopulateData lstRBankData = new PopulateData();
		 * lstRBankData.setPopulateId(viewRoutingDetails.getRoutingCountryId());
		 * List<CountryMasterDesc> lstCountryName =
		 * getCountryListExceptAppCountry(languageId ,
		 * viewRoutingDetails.getRoutingCountryId()); if(lstCountryName.size()
		 * != 0){ CountryMasterDesc countryName = lstCountryName.get(0);
		 * lstRBankData.setPopulateName(countryName.getCountryName()); }
		 * 
		 * lstCountry.add(lstRBankData); }
		 */
		List<PopulateData> lstCountry = new ArrayList<PopulateData>();
		LOGGER.info("======Start getRoutingCountryList ========");
		LOGGER.info("appLicationCountryId :" + appLicationCountryId);
		LOGGER.info("beneBankId :" + beneBankId);
		LOGGER.info("beneBankBranchId :" + beneBankBranchId);
		LOGGER.info("countryId :" + countryId);
		LOGGER.info("beneCurrencyId :" + beneCurrencyId);
		LOGGER.info("serviceMasterId :" + serviceMasterId);
		LOGGER.info("======End getRoutingCountryList ========");
		String sql = "SELECT ROUTING_COUNTRY_ID FROM ( " + " SELECT DISTINCT F.ROUTING_COUNTRY_ID  " + " FROM   V_EX_ROUTING_DETAILS F "
				+ " WHERE  F.APPLICATION_COUNTRY_ID= " + appLicationCountryId + " AND    F.BENE_BANK_ID =  " + beneBankId
				+ " AND    F.BENE_BANK_BRANCH_ID= " + beneBankBranchId + " AND    F.COUNTRY_ID = " + countryId + " AND    F.CURRENCY_ID  =  "
				+ beneCurrencyId + " AND    F.SERVICE_MASTER_ID  = " + serviceMasterId + " AND    F.ROUTING_BANK_ID   =  DECODE(" + serviceMasterId
				+ ",101," + beneBankId + ",F.ROUTING_BANK_ID))";

		LOGGER.info("=====getRoutingCountryList ========query :" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		List<BigDecimal> rows = query.list();
		for (BigDecimal viewRoutingDetails : rows) {
			PopulateData lstRBankData = new PopulateData();
			lstRBankData.setPopulateId(viewRoutingDetails);
			List<CountryMasterDesc> lstCountryName = getCountryListExceptAppCountry(languageId, viewRoutingDetails);
			if (lstCountryName.size() != 0) {
				CountryMasterDesc countryName = lstCountryName.get(0);
				lstRBankData.setPopulateName(countryName.getCountryName());
			}
			lstCountry.add(lstRBankData);
		}
		return lstCountry;
	}

	public List<CountryMasterDesc> getCountryListExceptAppCountry(BigDecimal languageId, BigDecimal countryId) {

		LOGGER.info("Entered into getCountryListExceptAppCountry(BigDecimal languageId, BigDecimal countryId)  method");
		LOGGER.info("languageId =" + languageId);
		LOGGER.info("countryId =" + countryId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		// Join Language Type table
		detachedCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		// Add Language Condition
		detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		// Join Country Master Table
		detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		detachedCriteria.addOrder(Order.asc("fsCountryMaster.fsCountryMasterDescs"));
		// detachedCriteria.add(Restrictions.not(Restrictions.eq("fsCountryMaster.countryId",
		// countryId)));
		detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the getCountryListExceptAppCountry() Method ");
		return (List<CountryMasterDesc>) findAll(detachedCriteria);
	}

	@Override
	public List<PopulateData> getRoutingBankList(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId,
			BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, Boolean ttCheck) {
		LOGGER.info("Entered into getRoutingBankList(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, Boolean ttCheck) Method ");
		LOGGER.info("beneBankId :" + beneBankId);
		LOGGER.info("ttCheck :" + ttCheck);
		LOGGER.info("======Start getRoutingBankList ========");
		LOGGER.info("appLicationCountryId :" + appLicationCountryId);
		LOGGER.info("beneBankId :" + beneBankId);
		LOGGER.info("beneBankBranchId :" + beneBankBranchId);
		LOGGER.info("countryId :" + countryId);
		LOGGER.info("beneCurrencyId :" + beneCurrencyId);
		LOGGER.info("serviceMasterId :" + serviceMasterId);
		LOGGER.info("routingCountryId :" + routingCountryId);
		LOGGER.info("======End getRoutingBankList ========");
		List<PopulateData> lstBank = new ArrayList<PopulateData>();
		String sql = "SELECT ROUTING_BANK_ID FROM ( " + "SELECT DISTINCT F.ROUTING_BANK_ID " + " FROM   V_EX_ROUTING_DETAILS F "
				+ " WHERE  F.APPLICATION_COUNTRY_ID= " + appLicationCountryId + " AND    F.BENE_BANK_ID =  " + beneBankId
				+ " AND    F.BENE_BANK_BRANCH_ID= " + beneBankBranchId + " AND    F.COUNTRY_ID = " + countryId + " AND    F.CURRENCY_ID  =  "
				+ beneCurrencyId + " AND    F.SERVICE_MASTER_ID  = " + serviceMasterId + " AND    F.ROUTING_BANK_ID   =  DECODE(" + serviceMasterId
				+ ",101," + beneBankId + ",F.ROUTING_BANK_ID)" + " AND    F.ROUTING_COUNTRY_ID = " + routingCountryId + ")";
		LOGGER.info("====== getRoutingBankList ========query :" + sql);

		SQLQuery query = getSession().createSQLQuery(sql);
		List<BigDecimal> rows = query.list();
		for (BigDecimal viewRoutingDetails : rows) {
			PopulateData lstRBankData = new PopulateData();
			lstRBankData.setPopulateId(viewRoutingDetails);
			lstRBankData.setPopulateName(getBankName(viewRoutingDetails));
			lstRBankData.setPopulateCode(getRoutingBankCode(viewRoutingDetails));
			lstBank.add(lstRBankData);
		}
		return lstBank;
	}

	private String getRoutingBankCode(BigDecimal bankId) {
		String rouingBankCode = null;
		LOGGER.info(" Entered into getBankName(BigDecimal bankId) Method ");
		LOGGER.info("bankid =" + bankId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		LOGGER.info(" Exited from the getBankName() method");
		List<BankMaster> lstbankMaster = (List<BankMaster>) findAll(dCriteria);
		if (lstbankMaster.size() > 0) {
			rouingBankCode = lstbankMaster.get(0).getBankCode();
		}
		return rouingBankCode;
	}

	public String getBankName(BigDecimal bankId) {
		LOGGER.info(" Entered into getBankName(BigDecimal bankId) Method ");
		LOGGER.info("bankid =" + bankId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		LOGGER.info(" Exited from the getBankName() method");
		return ((BankMaster) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getBankFullName();
	}

	@Override
	public List<PopulateData> getRoutingBranchList(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId,
			BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingbankId,
			BigDecimal remitId, BigDecimal deliveryId) {
		LOGGER.info(" Entered into getRoutingBranchList(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingbankId, BigDecimal remitId,BigDecimal deliveryId) Method ");

		List<PopulateData> lstBankBranch = new ArrayList<PopulateData>();
		LOGGER.info("======Start getRoutingBranchList ========");
		LOGGER.info("appLicationCountryId :" + appLicationCountryId);
		LOGGER.info("beneBankId :" + beneBankId);
		LOGGER.info("beneBankBranchId :" + beneBankBranchId);
		LOGGER.info("countryId :" + countryId);
		LOGGER.info("beneCurrencyId :" + beneCurrencyId);
		LOGGER.info("serviceMasterId :" + serviceMasterId);
		LOGGER.info("routingCountryId :" + routingCountryId);
		LOGGER.info("routingbankId :" + routingbankId);
		LOGGER.info("remitId :" + remitId);
		LOGGER.info("deliveryId :" + deliveryId);
		LOGGER.info("======End getRoutingBranchList ========");
		String sql = "SELECT BANK_BRANCH_ID FROM ( " + "SELECT DISTINCT F.BANK_BRANCH_ID " + " FROM   V_EX_ROUTING_DETAILS F "
				+ " WHERE  F.APPLICATION_COUNTRY_ID= "
				+ appLicationCountryId
				+ " AND    F.BENE_BANK_ID =  "
				+ beneBankId
				+ " AND    F.BENE_BANK_BRANCH_ID= "
				+ beneBankBranchId
				+ " AND    F.COUNTRY_ID = "
				+ countryId
				+ " AND    F.CURRENCY_ID  =  "
				+ beneCurrencyId
				+ " AND    F.SERVICE_MASTER_ID  = "
				+ serviceMasterId
				+ " AND    F.ROUTING_BANK_ID   =  DECODE("
				+ serviceMasterId
				+ ",101,"
				+ beneBankId
				+ ",F.ROUTING_BANK_ID)"
				+ " AND    F.ROUTING_COUNTRY_ID = "
				+ routingCountryId
				+ " AND    F.ROUTING_BANK_ID = "
				+ routingbankId
				+ " AND    F.REMITTANCE_MODE_ID = " + remitId + " AND    F.DELIVERY_MODE_ID = " + deliveryId + " )";
		LOGGER.info("======query  getRoutingBranchList ========:" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		List<BigDecimal> rows = query.list();
		for (BigDecimal viewRoutingDetails : rows) {
			PopulateData lstRBankData = new PopulateData();
			lstRBankData.setPopulateId(viewRoutingDetails);
			List<BankBranch> lstBranchName = getBankBranchByBranchID(viewRoutingDetails);
			if (lstBranchName.size() != 0) {
				BankBranch branchName = lstBranchName.get(0);
				lstRBankData.setPopulateName(branchName.getBranchFullName());
			}
			lstBankBranch.add(lstRBankData);
		}
		return lstBankBranch;
	}

	public List<BankBranch> getBankBranchByBranchID(BigDecimal branchID) {
		LOGGER.info("Entered into getBankBranchByBranchID(BigDecimal branchID) Method");
		LOGGER.info("branchID =" + branchID);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankBranch.class, "BankBranch");
		detachedCriteria.add(Restrictions.eq("bankBranchId", branchID));
		/*
		 * detachedCriteria.setFetchMode("BankBranch.fsCountryMaster",
		 * FetchMode.JOIN);
		 * detachedCriteria.createAlias("BankBranch.fsCountryMaster",
		 * "fsCountryMaster", JoinType.INNER_JOIN);
		 * 
		 * detachedCriteria.setFetchMode("BankBranch.fsStateMaster",
		 * FetchMode.JOIN);
		 * detachedCriteria.createAlias("BankBranch.fsStateMaster",
		 * "fsStateMaster", JoinType.INNER_JOIN);
		 * 
		 * detachedCriteria.setFetchMode("BankBranch.fsDistrictMaster",
		 * FetchMode.JOIN);
		 * detachedCriteria.createAlias("BankBranch.fsDistrictMaster",
		 * "fsDistrictMaster", JoinType.INNER_JOIN);
		 * 
		 * detachedCriteria.setFetchMode("BankBranch.fsCityMaster",
		 * FetchMode.JOIN);
		 * detachedCriteria.createAlias("BankBranch.fsCityMaster",
		 * "fsCityMaster",JoinType.LEFT_OUTER_JOIN);
		 */
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the getBankBranchByBranchID Method");
		return (List<BankBranch>) findAll(detachedCriteria);
	}

	@Override
	public List<PopulateData> getRemittanceListByCountryBank(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId,
			BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingbankId,
			BigDecimal languageId) {

		List<PopulateData> lstRemittanceDetails = new ArrayList<PopulateData>();
		LOGGER.info("======Start getRemittanceListByCountryBank ========");
		LOGGER.info("appLicationCountryId :" + appLicationCountryId);
		LOGGER.info("beneBankId :" + beneBankId);
		LOGGER.info("beneBankBranchId :" + beneBankBranchId);
		LOGGER.info("countryId :" + countryId);
		LOGGER.info("beneCurrencyId :" + beneCurrencyId);
		LOGGER.info("serviceMasterId :" + serviceMasterId);
		LOGGER.info("routingCountryId :" + routingCountryId);
		LOGGER.info("routingbankId :" + routingbankId);
		LOGGER.info("======End getRemittanceListByCountryBank ========");
		String sql = "SELECT REMITTANCE_MODE_ID FROM ( " + "SELECT DISTINCT F.REMITTANCE_MODE_ID " + " FROM   V_EX_ROUTING_DETAILS F "
				+ " WHERE  F.APPLICATION_COUNTRY_ID= " + appLicationCountryId + " AND    F.BENE_BANK_ID =  " + beneBankId
				+ " AND    F.BENE_BANK_BRANCH_ID= " + beneBankBranchId + " AND    F.COUNTRY_ID = " + countryId + " AND    F.CURRENCY_ID  =  "
				+ beneCurrencyId + " AND    F.SERVICE_MASTER_ID  = " + serviceMasterId + " AND    F.ROUTING_BANK_ID   =  DECODE(" + serviceMasterId
				+ ",101," + beneBankId + ",F.ROUTING_BANK_ID)" + " AND    F.ROUTING_COUNTRY_ID = " + routingCountryId
				+ " AND    F.ROUTING_BANK_ID = " + routingbankId + " ORDER BY F.REMITTANCE_MODE_ID )";

		LOGGER.info("getRemittanceListByCountryBank :" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		List<BigDecimal> rows = query.list();
		for (BigDecimal viewRoutingDetails : rows) {
			PopulateData lstRBankData = new PopulateData();
			// String con = viewRoutingDetails.toString();
			lstRBankData.setPopulateId(viewRoutingDetails);
			String lstRemittanceName = getRemittanceDesc(viewRoutingDetails, languageId);
			if (lstRemittanceName != null) {
				lstRBankData.setPopulateName(lstRemittanceName);
			}
			lstRemittanceDetails.add(lstRBankData);
		}
		return lstRemittanceDetails;
	}

	public String getRemittanceDesc(BigDecimal remitId, BigDecimal languageId) {
		LOGGER.info("Entered into getRemittanceDesc(BigDecimal remitId, BigDecimal languageId) Method");
		LOGGER.info("remitId =" + remitId);
		LOGGER.info("languageId =" + languageId);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");
		criteria.setFetchMode("remittanceModeDescription.languageType", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.setFetchMode("remittanceModeDescription.remittanceModeMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.remittanceModeMaster", "remittanceModeMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId", remitId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the getRemittanceDesc Method");
		return ((List<RemittanceModeDescription>) findAll(criteria)).get(0).getRemittanceDescription();
	}

	@Override
	public List<PopulateData> getDeliveryListByCountryBankRemit(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId,
			BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingbankId,
			BigDecimal remittanceId, BigDecimal languageId) {

		List<PopulateData> lstDeliveryDetails = new ArrayList<PopulateData>();
		LOGGER.info("======Start getDeliveryListByCountryBankRemit ========");
		LOGGER.info("appLicationCountryId :" + appLicationCountryId);
		LOGGER.info("beneBankId :" + beneBankId);
		LOGGER.info("beneBankBranchId :" + beneBankBranchId);
		LOGGER.info("countryId :" + countryId);
		LOGGER.info("beneCurrencyId :" + beneCurrencyId);
		LOGGER.info("serviceMasterId :" + serviceMasterId);
		LOGGER.info("routingCountryId :" + routingCountryId);
		LOGGER.info("routingbankId :" + routingbankId);
		LOGGER.info("remitId :" + remittanceId);
		LOGGER.info("======End getDeliveryListByCountryBankRemit ========");
		String sql = "SELECT DELIVERY_MODE_ID FROM ( " + "SELECT DISTINCT F.DELIVERY_MODE_ID " + " FROM   V_EX_ROUTING_DETAILS F "
				+ " WHERE  F.APPLICATION_COUNTRY_ID= "
				+ appLicationCountryId
				+ " AND    F.BENE_BANK_ID =  "
				+ beneBankId
				+ " AND    F.BENE_BANK_BRANCH_ID= "
				+ beneBankBranchId
				+ " AND    F.COUNTRY_ID = "
				+ countryId
				+ " AND    F.CURRENCY_ID  =  "
				+ beneCurrencyId
				+ " AND    F.SERVICE_MASTER_ID  = "
				+ serviceMasterId
				+ " AND    F.ROUTING_BANK_ID   =  DECODE("
				+ serviceMasterId
				+ ",101,"
				+ beneBankId
				+ ",F.ROUTING_BANK_ID)"
				+ " AND    F.ROUTING_COUNTRY_ID = "
				+ routingCountryId
				+ " AND    F.ROUTING_BANK_ID = "
				+ routingbankId
				+ " AND    F.REMITTANCE_MODE_ID = " + remittanceId + " ORDER BY F.DELIVERY_MODE_ID)";

		LOGGER.info("getDeliveryListByCountryBankRemit :" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		List<BigDecimal> rows = query.list();
		for (BigDecimal viewRoutingDetails : rows) {
			PopulateData lstRBankData = new PopulateData();
			lstRBankData.setPopulateId(viewRoutingDetails);
			String lstDeliveryName = getDeliveryDesc(viewRoutingDetails, languageId);
			if (lstDeliveryName != null) {
				lstRBankData.setPopulateName(lstDeliveryName);
			}
			lstDeliveryDetails.add(lstRBankData);
		}
		return lstDeliveryDetails;
	}

	public String getDeliveryDesc(BigDecimal deliveryModeId, BigDecimal languageId) {
		LOGGER.info("Entered into getDeliveryDesc(BigDecimal deliveryModeId, BigDecimal languageId) Method");
		LOGGER.info("deliveryModeId =" + deliveryModeId);
		LOGGER.info("languageId =" + languageId);
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");
		criteria.setFetchMode("deliveryModeDesc.deliveryMode", FetchMode.JOIN);
		criteria.createAlias("deliveryModeDesc.deliveryMode", "deliveryMode", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("deliveryMode.deliveryModeId", deliveryModeId));
		criteria.setFetchMode("deliveryModeDesc.languageType", FetchMode.SELECT);
		criteria.createAlias("deliveryModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the getDeliveryDesc method");

		return ((List<DeliveryModeDesc>) findAll(criteria)).get(0).getEnglishDeliveryName();
	}

	@Override
	public HashMap<String, String> isAmlTranxAmountCheckForRemittance(BigDecimal appcountryId, BigDecimal beneCountryId, BigDecimal customerId,
			BigDecimal benificiarySeqId, BigDecimal fcamount) throws AMGException {
		LOGGER.info("Entered into isAmlTranxAmountCheckForRemittance(BigDecimal appcountryId, BigDecimal beneCountryId, BigDecimal customerId, BigDecimal benificiarySeqId, BigDecimal fcamount)  method ");
		LOGGER.info("Procedure Name =EX_CUSTOMER_AML_TRNX_CHECK");
		LOGGER.info("Customer Id ---------- > " + customerId);
		LOGGER.info("App country Id ------------> " + appcountryId);
		LOGGER.info("fc amount -------------- > " + fcamount);
		int customer_id = customerId.intValue();
		int country_id = appcountryId.intValue();
		int fc_amount = fcamount.intValue();
		int beneCountry = beneCountryId.intValue();
		int beneseqId = benificiarySeqId.intValue();
		LOGGER.info("isAmlTranxAmountCheckForRemittance appcountryId :" + appcountryId);
		LOGGER.info("isAmlTranxAmountCheckForRemittance beneCountryId :" + beneCountryId);
		LOGGER.info("isAmlTranxAmountCheckForRemittance customerId :" + customerId);
		LOGGER.info("isAmlTranxAmountCheckForRemittance benificiarySeqId :" + benificiarySeqId);
		LOGGER.info("isAmlTranxAmountCheckForRemittance fcamount :" + fcamount);
		@SuppressWarnings("rawtypes")
		HashMap<String, String> amlCheckMap = new HashMap<String, String>();
		Connection connection = null;
		try {
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.info("Problem while getting the connection " + e.getMessage());
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { call EX_CUSTOMER_AML_TRNX_CHECK (?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?, "
					+ "?, ?, ?,?, ?," + " ?, ?, ?, ?,?,?) } ";
			cs = connection.prepareCall(call);
			cs.setInt(1, country_id);
			cs.setInt(2, beneCountry);
			cs.setInt(3, customer_id);
			cs.setInt(4, beneseqId);
			cs.setInt(5, fc_amount);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.registerOutParameter(9, java.sql.Types.VARCHAR);
			cs.registerOutParameter(10, java.sql.Types.VARCHAR);
			cs.registerOutParameter(11, java.sql.Types.VARCHAR);
			cs.registerOutParameter(12, java.sql.Types.INTEGER);
			cs.registerOutParameter(13, java.sql.Types.INTEGER);
			cs.registerOutParameter(14, java.sql.Types.INTEGER);
			cs.registerOutParameter(15, java.sql.Types.INTEGER);
			cs.registerOutParameter(16, java.sql.Types.INTEGER);
			cs.registerOutParameter(17, java.sql.Types.INTEGER);
			cs.registerOutParameter(18, java.sql.Types.INTEGER);
			cs.registerOutParameter(19, java.sql.Types.INTEGER);
			cs.registerOutParameter(20, java.sql.Types.INTEGER);
			cs.registerOutParameter(21, java.sql.Types.INTEGER);
			cs.registerOutParameter(22, java.sql.Types.INTEGER);
			cs.registerOutParameter(23, java.sql.Types.INTEGER);
			cs.registerOutParameter(24, java.sql.Types.INTEGER);
			cs.registerOutParameter(25, java.sql.Types.INTEGER);
			cs.registerOutParameter(26, java.sql.Types.INTEGER);
			cs.registerOutParameter(27, java.sql.Types.INTEGER);
			cs.registerOutParameter(28, java.sql.Types.INTEGER);
			cs.registerOutParameter(29, java.sql.Types.INTEGER);
			cs.registerOutParameter(30, java.sql.Types.VARCHAR);
			cs.registerOutParameter(31, java.sql.Types.VARCHAR);
			cs.execute();
			String out1 = cs.getString(6);
			String out2 = cs.getString(7);
			String out3 = cs.getString(8);
			String out4 = cs.getString(9);
			String out5 = cs.getString(10);
			String out6 = cs.getString(11);
			BigDecimal out7 = cs.getBigDecimal(12);
			BigDecimal out8 = cs.getBigDecimal(13);
			BigDecimal out9 = cs.getBigDecimal(14);
			BigDecimal out10 = cs.getBigDecimal(15);
			BigDecimal out11 = cs.getBigDecimal(16);
			BigDecimal out12 = cs.getBigDecimal(17);
			BigDecimal out13 = cs.getBigDecimal(18);
			BigDecimal out14 = cs.getBigDecimal(19);
			BigDecimal out15 = cs.getBigDecimal(20);
			BigDecimal out16 = cs.getBigDecimal(21);
			BigDecimal out17 = cs.getBigDecimal(22);
			BigDecimal out18 = cs.getBigDecimal(23);
			BigDecimal out19 = cs.getBigDecimal(24);
			BigDecimal out20 = cs.getBigDecimal(25);
			BigDecimal out21 = cs.getBigDecimal(26);
			BigDecimal out22 = cs.getBigDecimal(27);
			BigDecimal out23 = cs.getBigDecimal(28);
			BigDecimal out24 = cs.getBigDecimal(29);
			String out25 = cs.getString(30);
			String out26 = cs.getString(31);

			amlCheckMap.put("AUTHTYPE1", out1 == null ? "" : out1.toString());
			amlCheckMap.put("MESSAGE1", out2 == null ? "" : out2.toString());
			amlCheckMap.put("AUTHTYPE2", out3 == null ? "" : out3.toString());
			amlCheckMap.put("MESSAGE2", out4 == null ? "" : out4.toString());
			amlCheckMap.put("AUTHTYPE3", out5 == null ? "" : out5.toString());
			amlCheckMap.put("MESSAGE3", out6 == null ? "" : out6.toString());
			amlCheckMap.put("RANGE1FROM", out7 == null ? "" : out7.toString());
			// amlCheckMap.put("RANGE1TO", out8 == null ? "" : out9.toString());
			amlCheckMap.put("RANGE1TO", out8 == null ? "" : out8.toString());
			amlCheckMap.put("RANGE1COUNT", out9 == null ? "0" : out9.toString());
			amlCheckMap.put("RANGE2FROM", out10 == null ? "" : out10.toString());
			amlCheckMap.put("RANGE2TO", out11 == null ? "" : out11.toString());
			amlCheckMap.put("RANGE2COUNT", out12 == null ? "0" : out12.toString());
			amlCheckMap.put("RANGE3FROM", out13 == null ? "" : out13.toString());
			amlCheckMap.put("RANGE3TO", out14 == null ? "" : out14.toString());
			amlCheckMap.put("RANGE3COUNT", out15 == null ? "0" : out15.toString());
			amlCheckMap.put("RANGE4FROM", out16 == null ? "" : out16.toString());
			amlCheckMap.put("RANGE4TO", out17 == null ? "" : out17.toString());
			amlCheckMap.put("RANGE4COUNT", out18 == null ? "0" : out18.toString());
			amlCheckMap.put("RANGE5FROM", out19 == null ? "" : out19.toString());
			amlCheckMap.put("RANGE5TO", out20 == null ? "" : out20.toString());
			amlCheckMap.put("RANGE5COUNT", out21 == null ? "0" : out21.toString());
			amlCheckMap.put("RANGE6FROM", out22 == null ? "" : out22.toString());
			amlCheckMap.put("RANGE6TO", out23 == null ? "" : out23.toString());
			amlCheckMap.put("RANGE6COUNT", out24 == null ? "0" : out24.toString());
			amlCheckMap.put("AUTHTYPE4", out25 == null ? "" : out25);
			amlCheckMap.put("MESSAGE4", out26 == null ? "" : out26);

			LOGGER.info("isAmlTranxAmountCheckForRemittance EX_CUSTOMER_AML_TRNX_CHECK out1 :" + out1);
			LOGGER.info("isAmlTranxAmountCheckForRemittance EX_CUSTOMER_AML_TRNX_CHECK out2 :" + out2);
			LOGGER.info("isAmlTranxAmountCheckForRemittance EX_CUSTOMER_AML_TRNX_CHECK out3 :" + out3);
			LOGGER.info("isAmlTranxAmountCheckForRemittance EX_CUSTOMER_AML_TRNX_CHECK out4 :" + out4);
			LOGGER.info("isAmlTranxAmountCheckForRemittance EX_CUSTOMER_AML_TRNX_CHECK out5 :" + out5);
			LOGGER.info("isAmlTranxAmountCheckForRemittance EX_CUSTOMER_AML_TRNX_CHECK out6 :" + out6);
			LOGGER.info("isAmlTranxAmountCheckForRemittance EX_CUSTOMER_AML_TRNX_CHECK out7 :" + out7);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out8 :" + out8);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out9 :" + out9);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out10 :" + out10);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out11 :" + out11);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out12 :" + out12);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out13 :" + out13);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out14 :" + out14);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out15 :" + out15);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out16 :" + out16);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out17 :" + out17);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out18 :" + out18);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out19 :" + out19);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out20 :" + out20);
			LOGGER.info("isAmlTranxAmountCheck EX_CUSTOMER_AML_TRNX_CHECK out21 :" + out21);
			LOGGER.info("isAmlTranxAmountCheckForRemittance EX_CUSTOMER_AML_TRNX_CHECK out22 :" + out22);
			LOGGER.info("isAmlTranxAmountCheckForRemittance EX_CUSTOMER_AML_TRNX_CHECK out23 :" + out23);
			LOGGER.info("isAmlTranxAmountCheckForRemittance EX_CUSTOMER_AML_TRNX_CHECK out24 :" + out24);
			LOGGER.info("isAmlTranxAmountCheckForRemittance EX_CUSTOMER_AML_TRNX_CHECK out25 :" + out25);
			LOGGER.info("isAmlTranxAmountCheckForRemittance EX_CUSTOMER_AML_TRNX_CHECK out26 :" + out26);

		} catch (SQLException e) {
			LOGGER.info("Problem Occured While Procedure calling time " + e.getMessage());
			String erromsg = "EX_CUSTOMER_AML_TRNX_CHECK" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Problem Occured While Procedure calling time " + e.getMessage());
				String erromsg = "EX_CUSTOMER_AML_TRNX_CHECK" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return amlCheckMap;
	}

	@Override
	public String getAdditionalCheckProcedure(BigDecimal appLicationCountryId, BigDecimal customerId, BigDecimal branchId, BigDecimal beneId,
			BigDecimal beneCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, String beneAccountNo, BigDecimal serviceMasterId,
			BigDecimal routingCountryId, BigDecimal routingBankId, BigDecimal routingBankBranchId, BigDecimal remittanceModeId,
			BigDecimal deliveryModeId, BigDecimal sourceOfIncomeId, BigDecimal exchangeRateApplied, BigDecimal localCommisionCurrencyId,
			BigDecimal localCommisionAmount, BigDecimal localChargeCurrencyId, BigDecimal localchargeAmount, BigDecimal localDelivCurrencyId,
			BigDecimal localDeliAmount, BigDecimal serviceProvider, BigDecimal foreignCurrencyId, BigDecimal foreignTrnxAmount,
			BigDecimal localNetCurrecnyId, BigDecimal localNetTrnxAmount, String beneSwiftBank1, String beneSwiftBank2, String errorMessage)
					throws AMGException {
		LOGGER.info("======Start getAdditionalCheckProcedure ========");
		LOGGER.info("Procedure Name =EX_APPL_ADDL_CHECKS");

		LOGGER.info("appLicationCountryId :" + appLicationCountryId);
		LOGGER.info("customerId :" + customerId);
		LOGGER.info("branchId :" + branchId);
		LOGGER.info("beneId :" + beneId);
		LOGGER.info("beneCountryId :" + beneCountryId);
		LOGGER.info("beneBankId :" + beneBankId);
		LOGGER.info("beneBankBranchId :" + beneBankBranchId);
		LOGGER.info("beneAccountNo :" + beneAccountNo);
		LOGGER.info("serviceMasterId :" + serviceMasterId);
		LOGGER.info("routingCountryId :" + routingCountryId);
		LOGGER.info("routingBankId :" + routingBankId);
		LOGGER.info("routingBankBranchId :" + routingBankBranchId);
		LOGGER.info("remittanceModeId :" + remittanceModeId);
		LOGGER.info("deliveryModeId :" + deliveryModeId);
		LOGGER.info("sourceOfIncomeId :" + sourceOfIncomeId);
		LOGGER.info("exchangeRateApplied :" + exchangeRateApplied);
		LOGGER.info("localCommisionCurrencyId :" + localCommisionCurrencyId);
		LOGGER.info("localCommisionAmount :" + localCommisionAmount);
		LOGGER.info("localChargeCurrencyId :" + localChargeCurrencyId);
		LOGGER.info("localchargeAmount :" + localchargeAmount);
		LOGGER.info("localDelivCurrencyId :" + localDelivCurrencyId);
		LOGGER.info("localDeliAmount :" + localDeliAmount);
		LOGGER.info("serviceProvider :" + serviceProvider);
		LOGGER.info("foreignCurrencyId :" + foreignCurrencyId);
		LOGGER.info("foreignTrnxAmount :" + foreignTrnxAmount);
		LOGGER.info("localNetCurrecnyId :" + localNetCurrecnyId);
		LOGGER.info("localNetTrnxAmount :" + localNetTrnxAmount);
		LOGGER.info("beneSwiftBank1 :" + beneSwiftBank1);
		LOGGER.info("beneSwiftBank2 :" + beneSwiftBank2);
		LOGGER.info("errorMessage :" + errorMessage);
		LOGGER.info("======End getAdditionalCheckProcedure ========");
		Connection connection = null;
		String errString = null;
		try {
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			String call = " { call EX_APPL_ADDL_CHECKS (?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?, "
					+ "?, ?, ?,?, ?," + " ?, ?, ?, ?,?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, appLicationCountryId);
			cs.setBigDecimal(2, customerId);
			cs.setBigDecimal(3, branchId);
			cs.setBigDecimal(4, beneId);
			cs.setBigDecimal(5, beneCountryId);
			cs.setBigDecimal(6, beneBankId);
			cs.setBigDecimal(7, beneBankBranchId);
			cs.setString(8, beneAccountNo);
			cs.setBigDecimal(9, serviceMasterId);
			cs.setBigDecimal(10, routingCountryId);
			cs.setBigDecimal(11, routingBankId);
			cs.setBigDecimal(12, routingBankBranchId);
			cs.setBigDecimal(13, remittanceModeId);
			cs.setBigDecimal(14, deliveryModeId);
			cs.setBigDecimal(15, sourceOfIncomeId);
			cs.setBigDecimal(16, exchangeRateApplied);
			cs.setBigDecimal(17, localCommisionCurrencyId);
			cs.setBigDecimal(18, localCommisionAmount);
			cs.setBigDecimal(19, localChargeCurrencyId);
			cs.setBigDecimal(20, localchargeAmount);
			cs.setBigDecimal(21, localDelivCurrencyId);
			cs.setBigDecimal(22, localDeliAmount);
			cs.setBigDecimal(23, serviceProvider);
			cs.setBigDecimal(24, foreignCurrencyId);
			cs.setBigDecimal(25, foreignTrnxAmount);
			cs.setBigDecimal(26, localNetCurrecnyId);
			cs.setBigDecimal(27, localNetTrnxAmount);
			cs.setString(28, beneSwiftBank1);
			cs.setString(29, beneSwiftBank2);
			cs.registerOutParameter(30, java.sql.Types.VARCHAR);
			cs.execute();
			errString = cs.getString(30);
		} catch (SQLException e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_APPL_ADDL_CHECKS" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error While Procedure Calling " + e.getMessage());
				String erromsg = "EX_APPL_ADDL_CHECKS" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return errString;
	}

	// procedure is not used now EX_RECHECK_APPLICATION
	@Override
	public HashMap<String, String> getRemittanceDeliveryFromRecheckApplicationProcedure(BigDecimal appLicationCountryId, BigDecimal customerId,
			BigDecimal branchId, BigDecimal beneId, BigDecimal beneCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId,
			BigDecimal beneAccountNo, BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingBankId,
			BigDecimal routingBankBranchId, BigDecimal remittanceModeId, BigDecimal deliveryModeId, BigDecimal exchangeRateApplied,
			BigDecimal localCommisionCurrencyId, BigDecimal localCommisionAmount, BigDecimal localChargeCurrencyId, BigDecimal localchargeAmount,
			BigDecimal localDelivCurrencyId, BigDecimal localDeliAmount, BigDecimal foreignCurrencyId, BigDecimal foreignTrnxAmount,
			BigDecimal localNetCurrecnyId, BigDecimal localNetTrnxAmount) throws AMGException {
		HashMap<String, String> rtnValues = new HashMap<String, String>();
		LOGGER.info("======Start getRemittanceDeliveryFromRecheckApplicationProcedure ========");
		LOGGER.info("Procedure Name= EX_RECHECK_APPLICATION");
		LOGGER.info("appLicationCountryId :" + appLicationCountryId);
		LOGGER.info("customerId :" + customerId);
		LOGGER.info("branchId :" + branchId);
		LOGGER.info("beneId :" + beneId);
		LOGGER.info("beneCountryId :" + beneCountryId);
		LOGGER.info("beneBankId :" + beneBankId);
		LOGGER.info("beneBankBranchId :" + beneBankBranchId);
		LOGGER.info("beneAccountNo :" + beneAccountNo);
		LOGGER.info("serviceMasterId :" + serviceMasterId);
		LOGGER.info("routingCountryId :" + routingCountryId);
		LOGGER.info("routingBankId :" + routingBankId);
		LOGGER.info("routingBankBranchId :" + routingBankBranchId);
		LOGGER.info("remittanceModeId :" + remittanceModeId);
		LOGGER.info("deliveryModeId :" + deliveryModeId);
		LOGGER.info("exchangeRateApplied :" + exchangeRateApplied);
		LOGGER.info("localCommisionCurrencyId :" + localCommisionCurrencyId);
		LOGGER.info("localCommisionAmount :" + localCommisionAmount);
		LOGGER.info("localChargeCurrencyId :" + localChargeCurrencyId);
		LOGGER.info("localchargeAmount :" + localchargeAmount);
		LOGGER.info("localDelivCurrencyId :" + localDelivCurrencyId);
		LOGGER.info("localDeliAmount :" + localDeliAmount);
		LOGGER.info("foreignCurrencyId :" + foreignCurrencyId);
		LOGGER.info("foreignTrnxAmount :" + foreignTrnxAmount);
		LOGGER.info("localNetCurrecnyId :" + localNetCurrecnyId);
		LOGGER.info("localNetTrnxAmount :" + localNetTrnxAmount);
		// Added below Parameters .
		/*
		 * LOGGER.info("localNetTrnxAmount :"+localNetTrnxAmount);
		 * LOGGER.info("localNetTrnxAmount :"+localNetTrnxAmount);
		 * LOGGER.info("localNetTrnxAmount :"+localNetTrnxAmount);
		 * LOGGER.info("localNetTrnxAmount :"+localNetTrnxAmount);
		 * LOGGER.info("localNetTrnxAmount :"+localNetTrnxAmount);
		 */
		// Code Ended here.
		LOGGER.info("======End getRemittanceDeliveryFromRecheckApplicationProcedure ========");
		Connection connection = null;
		String errString = null;
		try {
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			String call = " { call EX_RECHECK_APPLICATION (?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?,"
					+ " ?, ?, ?, ?, ?," + " ?, ?," + " ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, appLicationCountryId);
			cs.setBigDecimal(2, customerId);
			cs.setBigDecimal(3, branchId);
			cs.setBigDecimal(4, beneId);
			cs.setBigDecimal(5, beneCountryId);
			cs.setBigDecimal(6, beneBankId);
			cs.setBigDecimal(7, beneBankBranchId);
			cs.setBigDecimal(8, beneAccountNo);
			cs.setBigDecimal(9, serviceMasterId);
			cs.setBigDecimal(10, routingCountryId);
			cs.setBigDecimal(11, routingBankId);
			cs.setBigDecimal(12, routingBankBranchId);
			cs.setBigDecimal(13, remittanceModeId);
			cs.setBigDecimal(14, deliveryModeId);
			cs.setBigDecimal(15, exchangeRateApplied);
			cs.setBigDecimal(16, localCommisionCurrencyId);
			cs.setBigDecimal(17, localCommisionAmount);
			cs.setBigDecimal(18, localChargeCurrencyId);
			cs.setBigDecimal(19, localchargeAmount);
			cs.setBigDecimal(20, localDelivCurrencyId);
			cs.setBigDecimal(21, localDeliAmount);
			cs.setBigDecimal(22, foreignCurrencyId);
			cs.setBigDecimal(23, foreignTrnxAmount);
			cs.setBigDecimal(24, localNetCurrecnyId);
			cs.setBigDecimal(25, localNetTrnxAmount);
			cs.registerOutParameter(26, java.sql.Types.INTEGER);
			cs.registerOutParameter(27, java.sql.Types.INTEGER);
			cs.registerOutParameter(28, java.sql.Types.INTEGER);
			cs.registerOutParameter(29, java.sql.Types.INTEGER);
			cs.registerOutParameter(30, java.sql.Types.INTEGER);
			cs.registerOutParameter(31, java.sql.Types.INTEGER);
			cs.registerOutParameter(32, java.sql.Types.VARCHAR);
			cs.execute();
			BigDecimal pNewServiceMasterId = cs.getBigDecimal(26);
			BigDecimal pNewRoutingCountryId = cs.getBigDecimal(27);
			BigDecimal pNewRoutingBankId = cs.getBigDecimal(28);
			BigDecimal pNewRoutingBranchId = cs.getBigDecimal(29);
			BigDecimal pNewRemittanceModeId = cs.getBigDecimal(30);
			BigDecimal pNewDeliveryModeId = cs.getBigDecimal(31);
			errString = cs.getString(32);
			rtnValues.put("P_NEW_SERVICE_MASTER_ID", pNewServiceMasterId == null ? "0" : pNewServiceMasterId.toPlainString());
			rtnValues.put("P_NEW_ROUTING_COUNTRY_ID", pNewRoutingCountryId == null ? "0" : pNewRoutingCountryId.toPlainString());
			rtnValues.put("P_NEW_ROUTING_BANK_ID", pNewRoutingBankId == null ? "0" : pNewRoutingBankId.toPlainString());
			rtnValues.put("P_NEW_ROUTING_BANK_BRANCH_ID", pNewRoutingBranchId == null ? "0" : pNewRoutingBranchId.toPlainString());
			rtnValues.put("P_NEW_REMITTANCE_MODE_ID", pNewRemittanceModeId == null ? "0" : pNewRemittanceModeId.toPlainString());
			rtnValues.put("P_NEW_DELIVERY_MODE_ID", pNewDeliveryModeId == null ? "0" : pNewDeliveryModeId.toPlainString());
			rtnValues.put("P_ERROR_MESSAGE", errString);
		} catch (SQLException e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_RECHECK_APPLICATION" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error While Procedure Calling " + e.getMessage());
				String erromsg = "EX_RECHECK_APPLICATION" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return rtnValues;
	}

	@Override
	public HashMap<String, String> toFetchDetilaFromAddtionalBenficiaryDetails(BigDecimal beneficaryMasterId, BigDecimal beneficaryBankId,
			BigDecimal beneficaryBankBranchId, BigDecimal beneAccNumSeqId, BigDecimal routingCountry, BigDecimal routingBank,
			BigDecimal routingBranch, BigDecimal serviceMasterId, BigDecimal applicationCountryId, BigDecimal currencyId, BigDecimal remitMode,
			BigDecimal deliveryMode) throws AMGException {
		HashMap<String, String> addtionalValues = new HashMap<String, String>();
		LOGGER.info("=====EX_GET_ADDL_BENE_DETAILS =Start toFetchDetilaFromAddtionalBenficiaryDetails ========");
		LOGGER.info("Procedure Name= EX_GET_ADDL_BENE_DETAILS ");
		LOGGER.info("beneficaryMasterId :" + beneficaryMasterId);
		LOGGER.info("beneficaryBankId :" + beneficaryBankId);
		LOGGER.info("beneficaryBankBranchId :" + beneficaryBankBranchId);
		LOGGER.info("beneAccNumSeqId :" + beneAccNumSeqId);
		LOGGER.info("routingCountry :" + routingCountry);
		LOGGER.info("routingBank :" + routingBank);
		LOGGER.info("routingBranch :" + routingBranch);
		LOGGER.info("serviceMasterId :" + serviceMasterId);
		LOGGER.info("applicationCountryId :" + applicationCountryId);
		LOGGER.info("currencyId :" + currencyId);
		LOGGER.info("remitMode :" + remitMode);
		LOGGER.info("deliveryMode :" + deliveryMode);
		LOGGER.info("======End toFetchDetilaFromAddtionalBenficiaryDetails ========");
		Connection connection = null;
		String errString = null;
		try {
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			String call = " { call EX_GET_ADDL_BENE_DETAILS (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )} ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, beneficaryMasterId);
			cs.setBigDecimal(2, beneficaryBankId);
			cs.setBigDecimal(3, beneficaryBankBranchId);
			cs.setBigDecimal(4, beneAccNumSeqId);
			cs.setBigDecimal(5, routingCountry);
			cs.setBigDecimal(6, routingBank);
			cs.setBigDecimal(7, routingBranch);
			cs.setBigDecimal(8, serviceMasterId);
			cs.setBigDecimal(9, applicationCountryId);
			cs.setBigDecimal(10, currencyId);
			cs.setBigDecimal(11, remitMode);
			cs.setBigDecimal(12, deliveryMode);
			cs.registerOutParameter(13, java.sql.Types.VARCHAR);
			cs.registerOutParameter(14, java.sql.Types.VARCHAR);
			cs.registerOutParameter(15, java.sql.Types.INTEGER);
			cs.registerOutParameter(16, java.sql.Types.INTEGER);
			cs.registerOutParameter(17, java.sql.Types.INTEGER);
			cs.registerOutParameter(18, java.sql.Types.VARCHAR);
			cs.registerOutParameter(19, java.sql.Types.VARCHAR);
			cs.registerOutParameter(20, java.sql.Types.VARCHAR);
			cs.registerOutParameter(21, java.sql.Types.VARCHAR);
			cs.registerOutParameter(22, java.sql.Types.VARCHAR);
			cs.registerOutParameter(23, java.sql.Types.VARCHAR);
			cs.registerOutParameter(24, java.sql.Types.VARCHAR);
			cs.execute();
			String beneBankName = cs.getString(13);
			String beneBranchName = cs.getString(14);
			BigDecimal pStateId = cs.getBigDecimal(15);
			BigDecimal pDistrctId = cs.getBigDecimal(16);
			BigDecimal pCityId = cs.getBigDecimal(17);
			String beneName = cs.getString(18);
			String beneFirstName = cs.getString(19);
			String beneSecondName = cs.getString(20);
			String beneThirdName = cs.getString(21);
			String beneFourthName = cs.getString(22);
			String beneFifthName = cs.getString(23);
			errString = cs.getString(24);
			addtionalValues.put("P_BENE_BANK_NAME", beneBankName);
			addtionalValues.put("P_BENE_BRANCH_NAME", beneBranchName);
			addtionalValues.put("P_BENE_STATE_ID", pStateId == null ? "0" : pStateId.toPlainString());
			addtionalValues.put("P_BENE_DISTRICT_ID", pDistrctId == null ? "0" : pDistrctId.toPlainString());
			addtionalValues.put("P_BENE_CITY_ID", pCityId == null ? "0" : pCityId.toPlainString());
			addtionalValues.put("P_BENE_NAME", beneName);
			addtionalValues.put("P_BENEFICIARY_FIRST_NAME", beneFirstName);
			addtionalValues.put("P_BENEFICIARY_SECOND_NAME", beneSecondName);
			addtionalValues.put("P_BENEFICIARY_THIRD_NAME", beneThirdName);
			addtionalValues.put("P_BENEFICIARY_FOURTH_NAME", beneFourthName);
			addtionalValues.put("P_BENEFICIARY_FIFTH_NAME", beneFifthName);
			addtionalValues.put("P_ERROR_MESSAGE", errString);
			System.out.println("EX_GET_ADDL_BENE_DETAILS addtionalValues :" + addtionalValues.toString());
		} catch (SQLException e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_GET_ADDL_BENE_DETAILS" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error While Procedure Calling " + e.getMessage());
				String erromsg = "EX_GET_ADDL_BENE_DETAILS" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return addtionalValues;
	}

	@Override
	public String toFtechPurtherInstractionErrorMessaage(BigDecimal applicationCountyId, BigDecimal routingCountryId, BigDecimal routingBankId,
			BigDecimal currencyId, BigDecimal remittanceId, BigDecimal deliveryId, String furtherInstruction,BigDecimal beneBankCountryId) throws AMGException {
		LOGGER.info("======Start toFtechPurtherInstractionErrorMessaage ========");
		LOGGER.info("Procedure Name = EX_P_FURTHER_INSTR ");
		LOGGER.info("applicationCountyId :" + applicationCountyId);
		LOGGER.info("routingCountryId :" + routingCountryId);
		LOGGER.info("routingBankId :" + routingBankId);
		LOGGER.info("currencyId :" + currencyId);
		LOGGER.info("remittanceId :" + remittanceId);
		LOGGER.info("deliveryId :" + deliveryId);
		LOGGER.info("furtherInstruction :" + furtherInstruction);
		LOGGER.info("BeneBankCountry :" + beneBankCountryId);
		LOGGER.info("======End toFtechPurtherInstractionErrorMessaage ========");
		Connection connection = null;
		String errString = null;
		try {
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			String call = " { call EX_P_FURTHER_INSTR (?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, applicationCountyId);
			cs.setBigDecimal(2, routingCountryId);
			cs.setBigDecimal(3, routingBankId);
			cs.setBigDecimal(4, currencyId);
			cs.setBigDecimal(5, remittanceId);
			cs.setBigDecimal(6, deliveryId);
			cs.setString(7, furtherInstruction == null ? "" : furtherInstruction);
			cs.setBigDecimal(8, deliveryId);
			cs.registerOutParameter(9, java.sql.Types.VARCHAR);
			cs.execute();
			errString = cs.getString(9);

			System.out.println("errString :" + errString);

		} catch (SQLException e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_P_FURTHER_INSTR" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error While Procedure Calling " + e.getMessage());
				String erromsg = "EX_P_FURTHER_INSTR" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return errString;
	}

	@Override
	public HashMap<String, String> toFtechSwiftBankProcedure(BigDecimal applicationCountryId, BigDecimal routingCountryId, BigDecimal currencyId,
			BigDecimal remittanceId, BigDecimal deliveryId, String fieldName, String beneficiarySwiftBank,BigDecimal beneBankCountryId) throws AMGException {
		HashMap<String, String> swiftProcedureValues = new HashMap<String, String>();
		LOGGER.info("======Start toFtechSwiftBankProcedure ========");
		LOGGER.info("Procedure Name = EX_P_CHECK_SWIFT_BANK ");
		LOGGER.info("applicationCountryId :" + applicationCountryId);
		LOGGER.info("routingCountryId :" + routingCountryId);
		LOGGER.info("currencyId :" + currencyId);
		LOGGER.info("remittanceId :" + remittanceId);
		LOGGER.info("deliveryId :" + deliveryId);
		LOGGER.info("fieldName :" + fieldName);
		LOGGER.info("beneficiarySwiftBank :" + beneficiarySwiftBank);
		LOGGER.info("BeneBankCountry :" + beneBankCountryId);
		LOGGER.info("======End toFtechSwiftBankProcedure ========");
		Connection connection = null;
		String errString = null;
		try {
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			String call = " { call EX_P_CHECK_SWIFT_BANK   (?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, applicationCountryId);
			cs.setBigDecimal(2, routingCountryId);
			cs.setBigDecimal(3, currencyId);
			cs.setBigDecimal(4, remittanceId);
			cs.setBigDecimal(5, deliveryId);
			cs.setString(6, fieldName);
			cs.setString(7, beneficiarySwiftBank);
			cs.setBigDecimal(8, beneBankCountryId);
			cs.registerOutParameter(9, java.sql.Types.VARCHAR);
			cs.execute();
			errString = cs.getString(9);
			swiftProcedureValues.put("P_ERROR_MESSAGE", errString);
		} catch (SQLException e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_P_CHECK_SWIFT_BANK" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error While Procedure Calling " + e.getMessage());
				String erromsg = "EX_P_CHECK_SWIFT_BANK" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return swiftProcedureValues;
	}

	@Override
	public BigDecimal toFetchBankIdBasedOnBankName(String bankName) {
		LOGGER.info("Entered into toFetchBankIdBasedOnBankName Method");
		LOGGER.info("bankName =" + bankName);
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		criteria.add(Restrictions.eq("bankMaster.bankFullName", bankName));
		List<BankMaster> lstBankMasters = (List<BankMaster>) criteria.getExecutableCriteria(getSession()).list();
		if (lstBankMasters != null && lstBankMasters.size() != 0) {
			LOGGER.info("Exited from the toFetchBankIdBasedOnBankName method");
			return lstBankMasters.get(0).getBankId();
		} else {
			LOGGER.info("Exited from the toFetchBankIdBasedOnBankName method");
			return null;
		}
	}

	@Override
	public BigDecimal toFetchBankBranchIdBasedOnBankBranchName(String branchName) {
		LOGGER.info(" Entered into toFetchBankBranchIdBasedOnBankBranchName() Method");
		LOGGER.info("branchName =" + branchName);
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		criteria.add(Restrictions.eq("countryBranch.branchName", branchName));
		List<CountryBranch> lstCountryBranch = (List<CountryBranch>) criteria.getExecutableCriteria(getSession()).list();
		if (lstCountryBranch != null && lstCountryBranch.size() != 0) {
			LOGGER.info("Exited from the toFetchBankBranchIdBasedOnBankBranchName Method ");
			return lstCountryBranch.get(0).getCountryBranchId();
		} else {
			return null;
		}
	}

	@Override
	public List<PopulateData> fetchingViewSwiftMasterByCountryId(BigDecimal beneCountyId) {
		LOGGER.info(" fetchingViewSwiftMasterByCountryId(BigDecimal beneCountyId) Method");
		LOGGER.info(" beneCountyId =" + beneCountyId);
		List<PopulateData> lstSwiftMasterDetails = new ArrayList<PopulateData>();
		DetachedCriteria criteria = DetachedCriteria.forClass(SwiftMasterView.class, "swiftMasterView");
		criteria.add(Restrictions.eq("swiftMasterView.countryId", beneCountyId));
		System.out.println("beneCountyId :" + beneCountyId);
		List<SwiftMasterView> lstSwiftMaster = (List<SwiftMasterView>) findAll(criteria);
		for (SwiftMasterView lstSwiftRecords : lstSwiftMaster) {
			PopulateData lsrSwift = new PopulateData();
			lsrSwift.setPopulateId(lstSwiftRecords.getSwiftId());
			lsrSwift.setPopulateName(lstSwiftRecords.getSwiftBank() == null ? " " : lstSwiftRecords.getSwiftBank().concat("-")
					.concat(lstSwiftRecords.getBankName() == null ? " " : lstSwiftRecords.getBankName()).concat(" ")
					.concat(lstSwiftRecords.getAddress1() == null ? " " : lstSwiftRecords.getAddress1()).concat(" ")
					.concat(lstSwiftRecords.getAddress2() == null ? " " : lstSwiftRecords.getAddress2()).concat(" ")
					.concat(lstSwiftRecords.getAddress3() == null ? " " : lstSwiftRecords.getAddress3()));
			lstSwiftMasterDetails.add(lsrSwift);
		}
		LOGGER.info("Exited from the fetchingViewSwiftMasterByCountryId() Method");
		return lstSwiftMasterDetails;
	}

	// new
	/*@Override
	public HashMap<String, String> getExchangeRateValues(HashMap<String, String> inputValues) throws AMGException {
		LOGGER.info("Entered into getExchangeRateValues(HashMap<String, String> inputValues) Method");
		HashMap<String, String> outputValues = new HashMap<String, String>();
		Connection connection = null;
		try {
			// connection = DBConnection.getdataconnection();
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.error("PROBLEM OCCURED WHILE GETTTING CONECTION");
			e.printStackTrace();
		}
		LOGGER.info("!!!!!! getExchangeRateValues IN PUT VALUES  !!!!!!!!! ==  " + inputValues.toString());
		LOGGER.info("!Calling  EX_GET_EXCHANGE_RATE Procedure with INPUT VALUES  !!!!!");
		LOGGER.info(inputValues.toString());
		CallableStatement cs;
		try {
			String call = " { call EX_GET_EXCHANGE_RATE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("P_APPLICATION_COUNTRY_ID")));
			cs.setBigDecimal(2, new BigDecimal(inputValues.get("P_ROUTING_COUNTRY_ID")));
			cs.setBigDecimal(3, new BigDecimal(inputValues.get("P_BRANCH_ID")));
			cs.setBigDecimal(4, new BigDecimal(inputValues.get("P_COMPANY_ID")));
			cs.setBigDecimal(5, new BigDecimal(inputValues.get("P_ROUTING_BANK_ID")));
			cs.setBigDecimal(6, new BigDecimal(inputValues.get("P_SERVICE_MASTER_ID")));
			cs.setBigDecimal(7, new BigDecimal(inputValues.get("P_DELIVERY_MODE_ID")));
			cs.setBigDecimal(8, new BigDecimal(inputValues.get("P_REMITTANCE_MODE_ID")));
			cs.setBigDecimal(9, new BigDecimal(inputValues.get("P_FOREIGN_CURRENCY_ID")));
			cs.setBigDecimal(10, new BigDecimal(inputValues.get("P_SELECTED_CURRENCY_ID")));
			cs.setBigDecimal(11, new BigDecimal(inputValues.get("P_CUSTOMER_ID")));
			cs.setString(12, inputValues.get("P_CUSTOMER_TYPE"));
			cs.setString(13, inputValues.get("P_LOYALTY_POINTS_IND"));
			cs.setString(14, inputValues.get("P_SPECIAL_DEAL_RATE"));
			cs.setString(15, inputValues.get("P_OVERSEAS_CHRG_IND"));
			cs.setBigDecimal(16, new BigDecimal(inputValues.get("P_SELECTED_CURRENCY_AMOUNT")));
			cs.setString(17, inputValues.get("P_SPOT_RATE"));
			cs.setString(18, inputValues.get("P_CASH_ROUND_IND"));
			cs.setBigDecimal(19, new BigDecimal(inputValues.get("P_ROUTING_BANK_BRANCH_ID")));
			cs.setBigDecimal(20, new BigDecimal(inputValues.get("P_BENE_ID")));
			cs.setBigDecimal(21, new BigDecimal(inputValues.get("P_BENE_COUNTRY_ID")));
			cs.setBigDecimal(22, new BigDecimal(inputValues.get("P_BENE_BANK_ID")));
			cs.setBigDecimal(23, new BigDecimal(inputValues.get("P_BENE_BANK_BRANCH_ID")));
			cs.setString(24, inputValues.get("P_BENE_ACCOUNT_NO"));
			cs.setBigDecimal(25, new BigDecimal(inputValues.get("P_APPROVAL_YEAR")));
			cs.setBigDecimal(26, new BigDecimal(inputValues.get("P_APPROVAL_NO")));
			cs.setString(27, inputValues.get("P_USER_TYPE")); // Constant C 
			cs.setString(28, inputValues.get("P_PAYMENT_MODE")); // null
			cs.setString(29, inputValues.get("P_AMOUNT_UPDOWN")); // null

			cs.registerOutParameter(30, java.sql.Types.INTEGER);
			cs.registerOutParameter(31, java.sql.Types.INTEGER);
			cs.registerOutParameter(32, java.sql.Types.INTEGER);
			cs.registerOutParameter(33, java.sql.Types.INTEGER);
			cs.registerOutParameter(34, java.sql.Types.INTEGER);
			cs.registerOutParameter(35, java.sql.Types.INTEGER);
			cs.registerOutParameter(36, java.sql.Types.INTEGER);
			cs.registerOutParameter(37, java.sql.Types.INTEGER);
			cs.registerOutParameter(38, java.sql.Types.INTEGER);
			cs.registerOutParameter(39, java.sql.Types.INTEGER);
			cs.registerOutParameter(40, java.sql.Types.VARCHAR);
			cs.execute();// teUpdate();
			outputValues.put("P_EXCHANGE_RATE_APPLIED", cs.getBigDecimal(30) == null ? "0" : cs.getBigDecimal(30).toPlainString());
			outputValues.put("P_LOCAL_CHARGE_AMOUNT", cs.getBigDecimal(31) == null ? "0" : cs.getBigDecimal(31).toPlainString());
			outputValues.put("P_LOCAL_COMMISION_AMOUNT", cs.getBigDecimal(32) == null ? "0" : cs.getBigDecimal(32).toPlainString());
			outputValues.put("P_LOCAL_GROSS_AMOUNT", cs.getBigDecimal(33) == null ? "0" : cs.getBigDecimal(33).toPlainString());
			outputValues.put("P_LOYALTY_AMOUNT", cs.getBigDecimal(34) == null ? "0" : cs.getBigDecimal(34).toPlainString());
			outputValues.put("P_LOCAL_NET_PAYABLE", cs.getBigDecimal(35) == null ? "0" : cs.getBigDecimal(35).toPlainString());
			outputValues.put("P_LOCAL_NET_SENT", cs.getBigDecimal(36) == null ? "0" : cs.getBigDecimal(36).toPlainString());
			outputValues.put("P_NEW_REMITTANCE_MODE_ID", cs.getBigDecimal(37) == null ? "0" : cs.getBigDecimal(37).toPlainString());
			outputValues.put("P_NEW_DELIVERY_MODE_ID", cs.getBigDecimal(38) == null ? "0" : cs.getBigDecimal(38).toPlainString());
			outputValues.put("P_ICASH_COST_RATE", cs.getBigDecimal(39) == null ? "0" : cs.getBigDecimal(39).toPlainString());
			outputValues.put("P_ERROR_MESSAGE", cs.getString(40) == null ? "" : cs.getString(40));
			System.out.println("!!!!!! getExchangeRateValues outputValues VALUES  !!!!!!!!! ==  " + outputValues.toString());
			LOGGER.info("!After Calling  EX_GET_EXCHANGE_RATE Procedure returns OUTPUT VALUES  !!!!!");
			LOGGER.info(outputValues.toString());
		} catch (SQLException e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_GET_EXCHANGE_RATE" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} catch (Exception e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_GET_EXCHANGE_RATE" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error While Procedure Calling " + e.getMessage());
				String erromsg = "EX_GET_EXCHANGE_RATE" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return outputValues;
	}*/

	// OLD
	@Override
	public HashMap<String, String> getExchangeRateValues(HashMap<String, String> inputValues) throws AMGException {
		LOGGER.info("Entered into getExchangeRateValues(HashMap<String, String> inputValues) Method");
		HashMap<String, String> outputValues = new HashMap<String, String>();
		Connection connection = null;
		try {
			// connection = DBConnection.getdataconnection();
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.error("PROBLEM OCCURED WHILE GETTTING CONECTION");
			e.printStackTrace();
		}
		LOGGER.info("!!!!!! getExchangeRateValues IN PUT VALUES  !!!!!!!!! ==  " + inputValues.toString());
		LOGGER.info("!Calling  EX_GET_EXCHANGE_RATE Procedure with INPUT VALUES  !!!!!");
		LOGGER.info(inputValues.toString());
		CallableStatement cs;
		try {
			String call = " { call EX_GET_EXCHANGE_RATE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
			//String call = " { call GA_GET_EXCHANGE_RATE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("P_APPLICATION_COUNTRY_ID")));
			cs.setBigDecimal(2, new BigDecimal(inputValues.get("P_ROUTING_COUNTRY_ID")));
			cs.setBigDecimal(3, new BigDecimal(inputValues.get("P_BRANCH_ID")));
			cs.setBigDecimal(4, new BigDecimal(inputValues.get("P_COMPANY_ID")));
			cs.setBigDecimal(5, new BigDecimal(inputValues.get("P_ROUTING_BANK_ID")));
			cs.setBigDecimal(6, new BigDecimal(inputValues.get("P_SERVICE_MASTER_ID")));
			cs.setBigDecimal(7, new BigDecimal(inputValues.get("P_DELIVERY_MODE_ID")));
			cs.setBigDecimal(8, new BigDecimal(inputValues.get("P_REMITTANCE_MODE_ID")));
			cs.setBigDecimal(9, new BigDecimal(inputValues.get("P_FOREIGN_CURRENCY_ID")));
			cs.setBigDecimal(10, new BigDecimal(inputValues.get("P_SELECTED_CURRENCY_ID")));
			cs.setBigDecimal(11, new BigDecimal(inputValues.get("P_CUSTOMER_ID")));
			cs.setString(12, inputValues.get("P_CUSTOMER_TYPE"));
			cs.setString(13, inputValues.get("P_LOYALTY_POINTS_IND"));
			cs.setString(14, inputValues.get("P_SPECIAL_DEAL_RATE"));
			cs.setString(15, inputValues.get("P_OVERSEAS_CHRG_IND"));
			cs.setBigDecimal(16, new BigDecimal(inputValues.get("P_SELECTED_CURRENCY_AMOUNT")));
			cs.setString(17, inputValues.get("P_SPOT_RATE"));
			cs.setString(18, inputValues.get("P_CASH_ROUND_IND"));
			cs.setBigDecimal(19, new BigDecimal(inputValues.get("P_ROUTING_BANK_BRANCH_ID")));
			cs.setBigDecimal(20, new BigDecimal(inputValues.get("P_BENE_ID")));
			cs.setBigDecimal(21, new BigDecimal(inputValues.get("P_BENE_COUNTRY_ID")));
			cs.setBigDecimal(22, new BigDecimal(inputValues.get("P_BENE_BANK_ID")));
			cs.setBigDecimal(23, new BigDecimal(inputValues.get("P_BENE_BANK_BRANCH_ID")));
			cs.setString(24, inputValues.get("P_BENE_ACCOUNT_NO"));// 24--INPUT

			cs.setBigDecimal(25, new BigDecimal(inputValues.get("P_APPROVAL_YEAR")));// 25--INPUT
			cs.setBigDecimal(26, new BigDecimal(inputValues.get("P_APPROVAL_NO")));// 26--INPUT

			cs.registerOutParameter(27, java.sql.Types.INTEGER);
			cs.registerOutParameter(28, java.sql.Types.INTEGER);
			cs.registerOutParameter(29, java.sql.Types.INTEGER);
			cs.registerOutParameter(30, java.sql.Types.INTEGER);
			cs.registerOutParameter(31, java.sql.Types.INTEGER);
			cs.registerOutParameter(32, java.sql.Types.INTEGER);
			cs.registerOutParameter(33, java.sql.Types.INTEGER);
			cs.registerOutParameter(34, java.sql.Types.INTEGER);
			cs.registerOutParameter(35, java.sql.Types.INTEGER);
			cs.registerOutParameter(36, java.sql.Types.INTEGER);
			cs.registerOutParameter(37, java.sql.Types.VARCHAR);
			cs.execute();// teUpdate();
			outputValues.put("P_EXCHANGE_RATE_APPLIED", cs.getBigDecimal(27) == null ? "0" : cs.getBigDecimal(27).toPlainString());
			outputValues.put("P_LOCAL_CHARGE_AMOUNT", cs.getBigDecimal(28) == null ? "0" : cs.getBigDecimal(28).toPlainString());
			outputValues.put("P_LOCAL_COMMISION_AMOUNT", cs.getBigDecimal(29) == null ? "0" : cs.getBigDecimal(29).toPlainString());
			outputValues.put("P_LOCAL_GROSS_AMOUNT", cs.getBigDecimal(30) == null ? "0" : cs.getBigDecimal(30).toPlainString());
			outputValues.put("P_LOYALTY_AMOUNT", cs.getBigDecimal(31) == null ? "0" : cs.getBigDecimal(31).toPlainString());
			outputValues.put("P_LOCAL_NET_PAYABLE", cs.getBigDecimal(32) == null ? "0" : cs.getBigDecimal(32).toPlainString());
			outputValues.put("P_LOCAL_NET_SENT", cs.getBigDecimal(33) == null ? "0" : cs.getBigDecimal(33).toPlainString());
			outputValues.put("P_NEW_REMITTANCE_MODE_ID", cs.getBigDecimal(34) == null ? "0" : cs.getBigDecimal(34).toPlainString());
			outputValues.put("P_NEW_DELIVERY_MODE_ID", cs.getBigDecimal(35) == null ? "0" : cs.getBigDecimal(35).toPlainString());
			outputValues.put("P_ICASH_COST_RATE", cs.getBigDecimal(36) == null ? "0" : cs.getBigDecimal(36).toPlainString());
			outputValues.put("P_ERROR_MESSAGE", cs.getString(37) == null ? "" : cs.getString(37));
			System.out.println("!!!!!! getExchangeRateValues outputValues VALUES  !!!!!!!!! ==  " + outputValues.toString());
			LOGGER.info("!After Calling  EX_GET_EXCHANGE_RATE Procedure returns OUTPUT VALUES  !!!!!");
			LOGGER.info(outputValues.toString());
		} catch (SQLException e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_GET_EXCHANGE_RATE" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} catch (Exception e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_GET_EXCHANGE_RATE" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error While Procedure Calling " + e.getMessage());
				String erromsg = "EX_GET_EXCHANGE_RATE" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return outputValues;
	}

	@Override
	public BigDecimal getBeneficaryAccountId(String beneAccNum, BigDecimal bankId, BigDecimal branchId, BigDecimal currencyId) {
		LOGGER.info("Entered into getBeneficaryAccountId(String beneAccNum) Method called ");
		LOGGER.info("beneAccNum =" + beneAccNum);
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		criteria.add(Restrictions.eq("beneficaryAccount.bankAccountNumber", beneAccNum));

		criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.bank", "bank", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bank.bankId", bankId));

		criteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.bankBranch", "bankBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankBranch.bankBranchId", branchId));

		criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.currencyId", "currencyId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("currencyId.currencyId", currencyId));

		List<BeneficaryAccount> beneAccDetails = (List<BeneficaryAccount>) findAll(criteria);
		if (beneAccDetails != null && beneAccDetails.size() != 0) {
			LOGGER.info("Exited from the getBeneficaryAccountId Method");
			return beneAccDetails.get(0).getBeneficaryAccountSeqId();
		} else {
			LOGGER.info("Exited from the getBeneficaryAccountId Method");
			return null;
		}
	}

	@Override
	public void saveAllApplTransaction(HashMap<String, Object> mapAllDetailPersonalRemittanceApplSave) throws AMGException {
		LOGGER.info("Entered into saveAllApplTransaction() method ");
		RemittanceApplication saveApplTrnx = (RemittanceApplication) mapAllDetailPersonalRemittanceApplSave.get("EX_APPL_TRNX");
		RemittanceAppBenificiary saveApplBene = (RemittanceAppBenificiary) mapAllDetailPersonalRemittanceApplSave.get("EX_APPL_BENE");
		List<RemitApplAml> saveApplAmlList = (List<RemitApplAml>) mapAllDetailPersonalRemittanceApplSave.get("EX_APPL_AML");
		List<AdditionalInstructionData> saveApplAddlData = (List<AdditionalInstructionData>) mapAllDetailPersonalRemittanceApplSave.get("EX_APPL_ADDL_DATA");
		BigDecimal spotRatePk = (BigDecimal) mapAllDetailPersonalRemittanceApplSave.get("SPOT_RATE_PK");
		BigDecimal financialYear = (BigDecimal) mapAllDetailPersonalRemittanceApplSave.get("FinancialYear");
		// spl customer deal
		List<CustomerSpecialDealAppl> lstCustSplDealReq = (List<CustomerSpecialDealAppl>) mapAllDetailPersonalRemittanceApplSave.get("EX_CUSTOMER_SPECIAL_DEAL_APPL");

		List<AuthorizedLog> lstAuthorizedLog= (List<AuthorizedLog>)mapAllDetailPersonalRemittanceApplSave.get("AMLLOGSAVE");

		// mobile number saving in fs_customer , fs_customer_contacts_id
		String mobileChange = (String) mapAllDetailPersonalRemittanceApplSave.get("Cust_Mobile_Change");
		
		//AMTB COUPON.
		AmtbCoupon saveAmtbCoupon = (AmtbCoupon) mapAllDetailPersonalRemittanceApplSave.get("EX_AMTB_COUPON");

		// place order
		RatePlaceOrder placeOrder = (RatePlaceOrder) mapAllDetailPersonalRemittanceApplSave.get("EX_RATE_PLACE_ORDER");

		try {

			// customer id for all application trnx should status 'D'
			/*String status = null;
			if (saveApplTrnx != null) {
				updateRemittanceApplication(saveApplTrnx.getFsCustomer().getCustomerId(),status);
			}*/

			// save the EX_APPL_TRNX
			if (saveApplTrnx != null) {
				String processIn = Constants.U;
				String docrefNumber = getNextDocumentReferenceNumber(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(),
						new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_APPLICATION), financialYear, processIn,
						sessionStateManage.getCountryBranchCode());
				saveApplTrnx.setDocumentNo(new BigDecimal(docrefNumber));
				getSession().saveOrUpdate(saveApplTrnx);
			}
			// save the EX_APPL_BENE
			if (saveApplBene != null) {
				saveApplBene.setDocumentNo(saveApplTrnx.getDocumentNo());
				getSession().saveOrUpdate(saveApplBene);
			}
			// save the EX_APPL_AML
			if (saveApplAmlList != null) {
				for (RemitApplAml remitApplAml : saveApplAmlList) {
					getSession().saveOrUpdate(remitApplAml);
				}
			}
			// save the EX_APPL_ADDL_DATA
			if (saveApplAddlData != null && saveApplAddlData.size() != 0) {
				for (AdditionalInstructionData additionalInstructionData : saveApplAddlData) {
					additionalInstructionData.setDocumentNo(saveApplTrnx.getDocumentNo());
					getSession().saveOrUpdate(additionalInstructionData);
				}
			}
			// save customer special customer deal application
			if (lstCustSplDealReq != null) {
				for (CustomerSpecialDealAppl custSplDealAppl : lstCustSplDealReq) {
					custSplDealAppl.setApplicationDocumentNumber(saveApplTrnx.getDocumentNo());
					getSession().save(custSplDealAppl);
				}
			}
			
			// spot rate update application doc no
			/*if (spotRatePk != null) {
				SpecialRateRequest specialRateRequest = (SpecialRateRequest) getSession().get(SpecialRateRequest.class, spotRatePk);
				specialRateRequest.setApplicationCompanyId(saveApplTrnx.getFsCompanyMaster().getCompanyId());
				specialRateRequest.setApplicationDocumentId(saveApplTrnx.getExDocument().getDocumentID());
				specialRateRequest.setApplicationDocumentNo(saveApplTrnx.getDocumentNo());
				specialRateRequest.setApplicationFinanceYear(saveApplTrnx.getDocumentFinancialyear());
				getSession().update(specialRateRequest);
				
				RatePlaceOrder ratePlaceOrder = (RatePlaceOrder) getSession().get(RatePlaceOrder.class, spotRatePk);
				ratePlaceOrder.setApplDocumentFinanceYear(saveApplTrnx.getDocumentFinancialyear());
				ratePlaceOrder.setApplDocumentNumber(saveApplTrnx.getDocumentNo());
				getSession().update(ratePlaceOrder);
			}*/
			
			if(lstAuthorizedLog!=null && lstAuthorizedLog.size()>0)
			{
				for(AuthorizedLog  authorizedLog :lstAuthorizedLog)
				{
					getSession().save(authorizedLog);
				}
			}
			// mobile number save
			if (mobileChange != null && mobileChange.equalsIgnoreCase(Constants.Yes)) {
				// saving in fs_customer
				BigDecimal customerId = (BigDecimal) mapAllDetailPersonalRemittanceApplSave.get("Customer_Id");
				BigDecimal customerRefNo = (BigDecimal) mapAllDetailPersonalRemittanceApplSave.get("Customer_Reference");
				String mobileNumber = (String) mapAllDetailPersonalRemittanceApplSave.get("Cust_Mobile_No");
				
				BigDecimal otpNo = (BigDecimal) mapAllDetailPersonalRemittanceApplSave.get("OTP_NO");
				String authorizedBy = (String) mapAllDetailPersonalRemittanceApplSave.get("Authorized_By");
				String authorizedRemarks = (String) mapAllDetailPersonalRemittanceApplSave.get("Authorized_Remarks");
				
				if (customerId != null && mobileNumber != null) {
					Customer customer = (Customer) getSession().get(Customer.class, customerId);
					customer.setMobile(mobileNumber);
					customer.setUpdatedBy(sessionStateManage.getUserName());
					customer.setLastUpdated(new Date());
					getSession().update(customer);
				}
				
				// saving in fs_contact_details_id
				BigDecimal customerContactId = (BigDecimal) mapAllDetailPersonalRemittanceApplSave.get("Cust_Contact_Id");
				if (customerContactId != null && mobileNumber != null) {
					ContactDetail customerContactDetails = (ContactDetail) getSession().get(ContactDetail.class, customerContactId);
					customerContactDetails.setMobile(mobileNumber);
					customerContactDetails.setUpdatedBy(sessionStateManage.getUserName());
					customerContactDetails.setLastUpdated(new Date());
					getSession().update(customerContactDetails);
				}

				// saving in fs_customer_mobile_log
				// deactivate all mobile number of customer in fs_custome_mobile_log
				DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerMobileLogModel.class, "customerMobileLogModel");
				dCriteria.add(Restrictions.eq("customerMobileLogModel.customerId", customerId));
				dCriteria.add(Restrictions.eq("customerMobileLogModel.isActive", Constants.Yes));
				List<CustomerMobileLogModel> lstCustomerMobileLog = (List<CustomerMobileLogModel>) findAll(dCriteria);

				if(lstCustomerMobileLog != null && lstCustomerMobileLog.size() != 0){
					for (CustomerMobileLogModel customerMobileLogModel : lstCustomerMobileLog) {
						CustomerMobileLogModel custMobLog = (CustomerMobileLogModel) getSession().get(CustomerMobileLogModel.class, customerMobileLogModel.getCustomerMobileId());

						if(custMobLog != null && custMobLog.getOtpNo() != null && custMobLog.getOtpVerifiedBy() == null && authorizedBy == null && custMobLog.getOtpNo().compareTo(otpNo)==0){
							custMobLog.setOtpVerifiedBy(sessionStateManage.getUserName());
							custMobLog.setOtpVerifiedDate(new Date());
						}else if(custMobLog != null && custMobLog.getOtpNo() != null && custMobLog.getOtpVerifiedBy() == null && authorizedBy != null){
							custMobLog.setOtpVerifiedBy(authorizedBy);
							custMobLog.setOtpVerifiedDate(new Date());
							custMobLog.setOtpVerifiedRemarks(authorizedRemarks);
						}else{
							// no need to update otp details
							System.out.println("NO OTP");
						}
						mobileNumber = custMobLog.getMobile();
						custMobLog.setModifiedBy(sessionStateManage.getUserName());
						custMobLog.setModifiedDate(new Date());
						getSession().update(custMobLog);
					}
				}

				// customer mobile number modified need to call Procedure
				String SQl = "Update CUSMAS set MOBNO = '" + mobileNumber + "'" + " where CUSREF= " + customerRefNo;
				getSession().createSQLQuery(SQl).executeUpdate();

			}
			
            //Added by Rabil to Store AMTB COUPON Details
			if(saveAmtbCoupon!=null){
				saveAmtbCoupon.setApplDocumentNo(saveApplTrnx.getDocumentNo());
				getSession().saveOrUpdate(saveAmtbCoupon);
			}
			
			//place order update
			if(placeOrder != null && placeOrder.getRatePlaceOrderId() != null){
				RatePlaceOrder ratePlaceOrder = (RatePlaceOrder) getSession().get(RatePlaceOrder.class, placeOrder.getRatePlaceOrderId());
				BigDecimal applDocNumder = ratePlaceOrder.getApplDocumentNumber();
				if(applDocNumder == null){
					
					ratePlaceOrder.setAppointmentTime(placeOrder.getAppointmentTime());
					ratePlaceOrder.setSourceOfincomeId(placeOrder.getSourceOfincomeId());
					//ratePlaceOrder.setCollectionMode(paymentMode);					
					ratePlaceOrder.setServiceMasterId(placeOrder.getServiceMasterId());
					ratePlaceOrder.setRemittanceModeId(placeOrder.getRemittanceModeId());
					ratePlaceOrder.setDeliveryModeId(placeOrder.getDeliveryModeId());
					ratePlaceOrder.setRoutingBranchId(placeOrder.getRoutingBranchId());
					ratePlaceOrder.setApplDocumentFinanceYear(saveApplTrnx.getDocumentFinancialyear());
					ratePlaceOrder.setApplDocumentNumber(saveApplTrnx.getDocumentNo());
					ratePlaceOrder.setTransactionAmountPaid(saveApplTrnx.getLocalTranxAmount());
					
					getSession().update(ratePlaceOrder);
				}else{
					throw new AMGException("SPL APPROVAL IS USED for"+saveApplTrnx.getDocumentFinancialyear() + " / " + saveApplTrnx.getDocumentNo());
				}
				
			}
			

		} catch (Exception e) {
			LOGGER.info("Problem to redirect: " + e);
			throw new AMGException(e.getMessage());
		}
	}

	@Override
	public void updateRemittanceApplication(BigDecimal customerId, String status) {

		LOGGER.info("Entered into updateRemittanceApplication Method");
		LOGGER.info("remitId =" + customerId);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplication.class, "remittanceApplication");
		criteria.setFetchMode("remittanceApplication.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.add(Restrictions.eq("remittanceApplication.applicaitonStatus", Constants.S));
		criteria.add(Restrictions.isNull("remittanceApplication.transactionDocumentNo"));
		//criteria.add(Restrictions.eq("remittanceApplication.isactive", Constants.Yes));

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

		criteria.add(Restrictions.between("remittanceApplication.createdDate", fromDate, toDate));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<RemittanceApplication> remitApp = (List<RemittanceApplication>)findAll(criteria);

		for (RemittanceApplication remitanceAppPk : remitApp) {
			RemittanceApplication remittanceApplication = (RemittanceApplication) getSession().get(RemittanceApplication.class, remitanceAppPk.getRemittanceApplicationId());
			remittanceApplication.setApplicaitonStatus(status);
			remittanceApplication.setTransactionFinancialyear(null);
			remittanceApplication.setTransactionDocumentNo(null);
			remittanceApplication.setBlackListIndicator(null);
			//remittanceApplication.setIsactive(Constants.D);
			remittanceApplication.setModifiedBy(sessionStateManage.getUserName());
			remittanceApplication.setModifiedDate(new Date());

			getSession().update(remittanceApplication);
		}	
	}

	@Override
	public String getExCheckCashLimitProcedure(BigDecimal applicationCountyId, BigDecimal customerId, BigDecimal paymentModeId, BigDecimal colleAmount)
			throws AMGException {
		Connection connection = null;
		String msgOutPut = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			LOGGER.info("Entered into getExCheckCashLimitProcedure method called ");
			LOGGER.info("Procedure Name = EX_CHECK_CASH_LIMIT");
			LOGGER.info("getExCheckCashLimitProcedure applicationCountyId :" + applicationCountyId);
			LOGGER.info("getExCheckCashLimitProcedure applicationCountyId :" + customerId);
			LOGGER.info("getExCheckCashLimitProcedure applicationCountyId :" + paymentModeId);
			LOGGER.info("getExCheckCashLimitProcedure applicationCountyId :" + colleAmount);
			String call = " { call EX_CHECK_CASH_LIMIT (?,?,?, ?, ?) } ";
			//String call = " { call EX_REFUND_CASH_LIMIT (?,?,?, ?, ?) } "; 
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, applicationCountyId);
			cs.setBigDecimal(2, customerId);
			cs.setBigDecimal(3, paymentModeId);
			cs.setBigDecimal(4, colleAmount);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.execute();// teUpdate();
			msgOutPut = cs.getString(5) == null ? "" : cs.getString(5);
			LOGGER.info("getExCheckCashLimitProcedure msgOutPut :" + msgOutPut);
		} catch (SQLException e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_CHECK_CASH_LIMIT" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error While Procedure Calling " + e.getMessage());
				String erromsg = "EX_CHECK_CASH_LIMIT" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return msgOutPut;
	}

	@Override
	public List<AdditionalBankDetailsView> getAmiecDetails(BigDecimal currencyId, BigDecimal bankId, BigDecimal remittanceModeId,
			BigDecimal deleveryModeId, BigDecimal countryId, String flexiField) {
		LOGGER.info("Entered into getAmiecDetails(BigDecimal currencyId, BigDecimal bankId, BigDecimal remittanceModeId, BigDecimal deleveryModeId, BigDecimal countryId, String flexiField) Method called ");
		LOGGER.info("currencyId =" + currencyId);
		LOGGER.info("bankId =" + bankId);
		LOGGER.info("remittanceModeId =" + remittanceModeId);
		LOGGER.info("countryId =" + countryId);
		LOGGER.info("flexiField =" + flexiField);
		DetachedCriteria critiria = DetachedCriteria.forClass(AdditionalBankDetailsView.class, "additionalBankDetailsView");
		critiria.add(Restrictions.eq("additionalBankDetailsView.currencyId", currencyId));
		critiria.add(Restrictions.eq("additionalBankDetailsView.bankId", bankId));
		critiria.add(Restrictions.eq("additionalBankDetailsView.remittanceId", remittanceModeId));
		critiria.add(Restrictions.eq("additionalBankDetailsView.deliveryId", deleveryModeId));
		critiria.add(Restrictions.eq("additionalBankDetailsView.countryId", countryId));
		critiria.add(Restrictions.eq("additionalBankDetailsView.flexField", flexiField));
		critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<AdditionalBankDetailsView> additionalList = (List<AdditionalBankDetailsView>) findAll(critiria);
		LOGGER.info("Exited from the getAmiecDetails() method ");
		return additionalList;
	}

	@Override
	public HashMap<String, String> exPBankIndicatorsProcedureCheck(HashMap<String, String> inputValues,
			List<AddAdditionalBankData> listAdditionalBankDataTable) throws AMGException {
		LOGGER.info("Entered into exPBankIndicatorsProcedureCheck(HashMap<String, String> inputValues, List<AddAdditionalBankData> listAdditionalBankDataTable)  Method");
		HashMap<String, String> outputValues = new HashMap<String, String>();
		Connection connection = null;
		try {
			// connection = DBConnection.getdataconnection();
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.info("Error While getting connection " + e.getMessage());
			e.printStackTrace();
		}
		HashMap<String, String> amicCodeLst = new HashMap<String, String>();
		for (AddAdditionalBankData dynamicList : listAdditionalBankDataTable) {
			String amiecdec = dynamicList.getVariableName();
			String amicCode = null;
			if (amiecdec != null) {
				String[] amiecdecValues = amiecdec.split("-");
				if (amiecdecValues.length > 0) {
					amicCode = amiecdecValues[0];
					String flexField = dynamicList.getFlexiField();
					if (amicCode != null) {
						if (flexField != null && flexField.equalsIgnoreCase(Constants.INDIC1)) {
							amicCodeLst.put("P_INDIC1", amicCode);
						}
						if (flexField != null && flexField.equalsIgnoreCase(Constants.INDIC2)) {
							amicCodeLst.put("P_INDIC2", amicCode);
						}
						if (flexField != null && flexField.equalsIgnoreCase(Constants.INDIC3)) {
							amicCodeLst.put("P_INDIC3", amicCode);
						}
						if (flexField != null && flexField.equalsIgnoreCase(Constants.INDIC4)) {
							amicCodeLst.put("P_INDIC4", amicCode);
						}
						if (flexField != null && flexField.equalsIgnoreCase(Constants.INDIC5)) {
							amicCodeLst.put("P_INDIC5", amicCode);
						}
					}
				}
			}
		}
		LOGGER.info("EX_P_BANK_INDICATORS !!!!!!exPBankIndicatorsProcedureCheck  IN PUT VALUES  !!!!!!!!! ==  " + inputValues.toString()
				+ amicCodeLst.toString());
		CallableStatement cs;
		try {
			String call = " { call EX_P_BANK_INDICATORS (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("P_APPLICATION_COUNTRY_ID")));
			cs.setBigDecimal(2, new BigDecimal(inputValues.get("P_ROUTING_COUNTRY_ID")));
			cs.setBigDecimal(3, new BigDecimal(inputValues.get("P_CURRENCY_ID")));
			cs.setBigDecimal(4, new BigDecimal(inputValues.get("P_ROUTING_BANK_ID")));
			cs.setBigDecimal(5, new BigDecimal(inputValues.get("P_REMITTANCE_MODE_ID")));
			cs.setBigDecimal(6, new BigDecimal(inputValues.get("P_DELIVERY_MODE_ID")));
			cs.setString(7, amicCodeLst.get("P_INDIC1") == null ? "" : amicCodeLst.get("P_INDIC1"));
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.setString(9, amicCodeLst.get("P_INDIC2") == null ? "" : amicCodeLst.get("P_INDIC2"));
			cs.registerOutParameter(10, java.sql.Types.VARCHAR);
			cs.setString(11, amicCodeLst.get("P_INDIC3") == null ? "" : amicCodeLst.get("P_INDIC3"));
			cs.registerOutParameter(12, java.sql.Types.VARCHAR);
			cs.setString(13, amicCodeLst.get("P_INDIC4") == null ? "" : amicCodeLst.get("P_INDIC4"));
			cs.registerOutParameter(14, java.sql.Types.VARCHAR);
			cs.setString(15, amicCodeLst.get("P_INDIC5") == null ? "" : amicCodeLst.get("P_INDIC5"));
			cs.registerOutParameter(16, java.sql.Types.VARCHAR);
			cs.setBigDecimal(17, new BigDecimal(inputValues.get("P_BENE_BANK_COUNTRY")));
			cs.execute();// teUpdate();
			outputValues.put("P_ERROR_MESSAGE1", cs.getString(8) == null ? "" : cs.getString(8));
			outputValues.put("P_ERROR_MESSAGE2", cs.getString(10) == null ? "" : cs.getString(10));
			outputValues.put("P_ERROR_MESSAGE3", cs.getString(12) == null ? "" : cs.getString(12));
			outputValues.put("P_ERROR_MESSAGE4", cs.getString(14) == null ? "" : cs.getString(14));
			outputValues.put("P_ERROR_MESSAGE5", cs.getString(16) == null ? "" : cs.getString(16));
		} catch (SQLException e) {
			LOGGER.error("Problem While Procedure calling " + e.getMessage());
			String erromsg = "EX_P_BANK_INDICATORS" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Problem While Procedure calling " + e.getMessage());
				String erromsg = "EX_P_BANK_INDICATORS" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		LOGGER.info("EX_P_BANK_INDICATORS !!!!!!OUT PUT VALUES !!!!!!!!! ===  " + outputValues.toString());
		return outputValues;
	}

	@Override
	public List<AdditionalDataDisplayView> getAdditionalDataFromServiceApplicability(BigDecimal applicationCountryId, BigDecimal countryId,
			BigDecimal currencyId, BigDecimal remittanceModeId, BigDecimal deliveryModeId) throws AMGException {
		LOGGER.info("Entered into getAdditionalDataFromServiceApplicability(BigDecimal applicationCountryId, BigDecimal countryId, BigDecimal currencyId, BigDecimal remittanceModeId, BigDecimal deliveryModeId) Method ");
		LOGGER.info("applicationCountryId =" + applicationCountryId);
		LOGGER.info("countryId =" + countryId);
		LOGGER.info("currencyId =" + currencyId);
		LOGGER.info("remittanceModeId =" + remittanceModeId);
		LOGGER.info("deliveryModeId =" + deliveryModeId);

		List<AdditionalDataDisplayView> lstAdditionalDataDisplayView = new ArrayList<AdditionalDataDisplayView>();
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalDataDisplayView.class, "additionalDataDisplayView");
			criteria.add(Restrictions.eq("additionalDataDisplayView.applicationCountryId", applicationCountryId));
			criteria.add(Restrictions.eq("additionalDataDisplayView.routingCountryId", countryId));
			criteria.add(Restrictions.eq("additionalDataDisplayView.routingCurrencyId", currencyId));
			criteria.add(Restrictions.eq("additionalDataDisplayView.remittanceModeId", remittanceModeId));
			criteria.add(Restrictions.eq("additionalDataDisplayView.deliveryModeId", deliveryModeId));
			Disjunction lstjunction = Restrictions.disjunction();
			lstjunction.add(Restrictions.eq("flexField", Constants.INDIC1));
			lstjunction.add(Restrictions.eq("flexField", Constants.INDIC2));
			lstjunction.add(Restrictions.eq("flexField", Constants.INDIC3));
			lstjunction.add(Restrictions.eq("flexField", Constants.INDIC4));
			lstjunction.add(Restrictions.eq("flexField", Constants.INDIC5));
			criteria.add(lstjunction);
			criteria.add(Restrictions.eq("isActive", Constants.Yes));
			criteria.add(Restrictions.eq("isRendered", Constants.Yes));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			lstAdditionalDataDisplayView = (List<AdditionalDataDisplayView>) findAll(criteria);
		} catch (Exception e) {
			throw new AMGException(e.getMessage());
		}
		LOGGER.info("Exited from the getAdditionalDataFromServiceApplicability() Method ");
		return lstAdditionalDataDisplayView;
	}

	@Override
	public HashMap<String, String> getSwitInstrustionFromServiceApplicability(HashMap<String, String> inputValues) throws AMGException {
		LOGGER.info("Entered into getSwitInstrustionFromServiceApplicability() Method");
		HashMap<String, String> outValues = new HashMap<String, String>();
		try {
			String benSwiftBank1 = futherInsSwitBank(inputValues, Constants.BENEFICIARY_SWIFT_BANK1);
			String benSwiftBank2 = futherInsSwitBank(inputValues, Constants.BENEFICIARY_SWIFT_BANK2);
			String beneInstrustion = futherInsSwitBank(inputValues, Constants.INSTRUCTION);
			String beneAddressCheck = futherInsSwitBank(inputValues, Constants.BNFADDR3);
			outValues.put("BENEFICIARY_SWIFT_BANK1", benSwiftBank1 == null ? "N" : benSwiftBank1);
			outValues.put("BENEFICIARY_SWIFT_BANK2", benSwiftBank2 == null ? "N" : benSwiftBank2);
			outValues.put("INSTRUCTION", beneInstrustion == null ? "N" : beneInstrustion);
			outValues.put("BNFADDR1", beneAddressCheck == null ? "N" : beneAddressCheck);
		} catch (Exception e) {
			throw new AMGException(e.getMessage());
		}
		LOGGER.info("Exited from the getSwitInstrustionFromServiceApplicability method");
		return outValues;
	}

	private String futherInsSwitBank(HashMap<String, String> inputValues, String flexFieldName) {
		LOGGER.info("Entered into futherInsSwitBank() method called ");
		LOGGER.info("FlexifieldName =" + flexFieldName);
		String hqlQuery = "select a.isRendered from  AdditionalDataDisplayView a where a.isActive='Y' and a.applicationCountryId =  :applicationCountryId1  "
				+ " and a.routingCountryId = :routingCountryId1 and a.routingCurrencyId = :routingCurrencyId1  "
				+ " and a.remittanceModeId = :remittanceModeId1 and a.deliveryModeId = :deliveryModeId1 and   a.flexField =  :flexFieldName1";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("applicationCountryId1", new BigDecimal(inputValues.get("APPLICATION_COUNTRY_ID")));
		query.setParameter("routingCountryId1", new BigDecimal(inputValues.get("ROUTING_COUNTRY_ID")));
		query.setParameter("routingCurrencyId1", new BigDecimal(inputValues.get("ROUTING_CURRENCY_ID")));
		query.setParameter("remittanceModeId1", new BigDecimal(inputValues.get("REMITTANCE_MODE_ID")));
		query.setParameter("deliveryModeId1", new BigDecimal(inputValues.get("DELIVERY_MODE_ID")));
		query.setParameter("flexFieldName1", flexFieldName);
		List<String> lstName = query.list();
		String rtnString = null;
		if (lstName.size() > 0) {
			rtnString = lstName.get(0);
		} else {
			rtnString = "N";
		}
		return rtnString;
	}

	@Override
	public List<Employee> getEmployeeDetails(String userName) {
		LOGGER.info("Entered into getEmployeeDetails(String userName) Method ");
		LOGGER.info("Username =" + userName);
		DetachedCriteria critiria = DetachedCriteria.forClass(Employee.class, "employee");
		critiria.add(Restrictions.eq("employee.userName", userName));
		critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<Employee> employeeList = (List<Employee>) findAll(critiria);
		LOGGER.info("Exited from the getEmployeeDetails method ");
		return employeeList;
	}

	@Override
	public List<CustomerDeclerationView> getCustomerDeclerationData(BigDecimal applicationCountryId, BigDecimal documentFinanceYear,
			BigDecimal documentid, BigDecimal documentNo) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerDeclerationView.class, "customerDeclerationView");
		dCriteria.add(Restrictions.eq("customerDeclerationView.applicationCountryId", applicationCountryId));
		dCriteria.add(Restrictions.eq("customerDeclerationView.collectionDocumentFinanceYear", documentFinanceYear));
		dCriteria.add(Restrictions.eq("customerDeclerationView.collectionDocumentId", documentid));
		dCriteria.add(Restrictions.eq("customerDeclerationView.collectionDocumentNo", documentNo));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CustomerDeclerationView> list = (List<CustomerDeclerationView>) findAll(dCriteria);
		return list;

	}

	@Override
	public List<CustomerRemittanceTransactionView> getBeneficaryTxnInquiryList(BigDecimal customerNo) {
		LOGGER.info("Entered into getBeneficaryTxnInquiryList()  Method Called ");
		LOGGER.info("View Name =  VW_EX_TRANSACTION_INQUIRY ");
		LOGGER.info("!!!!!!customerNo!!!!!!!!!" + customerNo);
		List<CustomerRemittanceTransactionView> list = new ArrayList<CustomerRemittanceTransactionView>();
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerRemittanceTransactionView.class, "customerRemittanceTransactionView");
			dCriteria.add(Restrictions.eq("customerRemittanceTransactionView.customerReference", customerNo));
			dCriteria.add(Restrictions.isNotNull("transactionTypeDesc"));
			dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			list = (List<CustomerRemittanceTransactionView>) findAll(dCriteria);
		} catch (Exception e) {
			LOGGER.info("Problem Occured When VIEW Calling=" + e.getMessage());
		}
		return list;
	}

	/**
	 * Added by Rabil
	 */

	@Override
	public HashMap<String, String> getValidateCustomerProcedure(BigDecimal applicationCountryId, BigDecimal customerId, String userName,
			String usertype) {
		Connection connection = null;
		String errorMessage = null;
		HashMap<String, String> finalValues = new HashMap<String, String>();
		try {
			LOGGER.info("getValidateCustomerProcedure input parameter :" + applicationCountryId + "\t customerId :" + customerId + "\t User Name :"
					+ userName + "\t User Type :" + usertype);

			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;

			String call = " { call EX_P_VALIDATE_CUSTOMER (?,?,?,?,?,?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, applicationCountryId);
			cs.setBigDecimal(2, customerId);
			cs.setString(3, usertype);
			cs.setString(4, userName);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);

			cs.execute();
			finalValues.put("ERROR_MESSAGE", cs.getString(5) == null ? null : cs.getString(5));
			finalValues.put("INDICATOR", cs.getString(6) == null ? null : cs.getString(6));
			//

			LOGGER.info("getValidateCustomerProcedure out parameter :" + finalValues);
			System.out.println("getValidateCustomerProcedure out parameter :" + finalValues.get("INDICATOR"));
			return finalValues;

		} catch (Exception e) {
			LOGGER.info("Error While getting connection " + e.getMessage());
			e.printStackTrace();
			errorMessage = "EX_P_VALIDATE_CUSTOMER  " + e.getMessage();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Problem While Procedure calling " + e.getMessage());
				errorMessage = "EX_P_VALIDATE_CUSTOMER" + " : " + e.getMessage();
			}
		}
		return finalValues;
	}

	@Override
	public String getValidateBeneficiaryProcedure(BigDecimal applicationCountryId, BigDecimal customerId, String userName, BigDecimal beneMasSeqId,
			BigDecimal beneAccNumSeqId) {
		Connection connection = null;
		String errorMessage = null;
		try {
			LOGGER.info("EX_P_VALIDATE_BENEFICIARY  input parameter :" + applicationCountryId + "\t customerId :" + customerId + "\t User Name :"
					+ userName + "\t beneMasSeqId:" + beneMasSeqId + "\t beneAccNumSeqId:" + beneAccNumSeqId);

			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;

			String call = " { call EX_P_VALIDATE_BENEFICIARY (?,?,?,?,?,?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, applicationCountryId);
			cs.setBigDecimal(2, customerId);
			cs.setString(3, userName);
			cs.setBigDecimal(4, beneMasSeqId);
			cs.setBigDecimal(5, beneAccNumSeqId);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);

			cs.execute();//
			errorMessage = cs.getString(6) == null ? null : cs.getString(6);
			LOGGER.info("EX_P_VALIDATE_BENEFICIARY out parameter :" + errorMessage);
			System.out.println("EX_P_VALIDATE_BENEFICIARY out parameter :" + errorMessage);
			return errorMessage;

		} catch (Exception e) {
			LOGGER.info("Error While getting connection " + e.getMessage());
			e.printStackTrace();
			errorMessage = "EX_P_VALIDATE_BENEFICIARY   " + e.getMessage();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Problem While Procedure calling " + e.getMessage());
				String erromsg = "EX_P_VALIDATE_BENEFICIARY " + " : " + e.getMessage();

			}
		}
		return errorMessage;
	}

	@Override
	public String getBannedNameCheckProcedure(BigDecimal applicationCountryId, String englishName, String localName) {
		Connection connection = null;
		String errorMessage = null;
		try {
			LOGGER.info("EX_P_BANNED_NAME_CHECK input parameter :" + applicationCountryId + "\t englishName :" + englishName + "\t localName Name :"
					+ localName);

			// System.out.println("getValidateCustomerProcedure input parameter :"+applicationCountryId+"\t customerId :"+customerId+"\t User Name :"+userName);

			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;

			String call = " { call EX_P_BANNED_NAME_CHECK (?,?,?,?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, applicationCountryId);
			cs.setString(2, englishName);
			cs.setString(3, localName);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.execute();
			errorMessage = cs.getString(4) == null ? null : cs.getString(4);
			LOGGER.info("EX_P_BANNED_NAME_CHECK out parameter :" + errorMessage);
			System.out.println("getValidateCustomerProcedure out parameter :" + errorMessage);
			return errorMessage;

		} catch (Exception e) {
			LOGGER.info("Error While getting connection " + e.getMessage());
			e.printStackTrace();
			errorMessage = "EX_P_BANNED_NAME_CHECK  " + e.getMessage();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Problem While Procedure calling " + e.getMessage());
				String erromsg = "EX_P_BANNED_NAME_CHECK" + " : " + e.getMessage();

			}
		}
		return errorMessage;
	}

	/**
	 * @Auth : Rabil
	 * @Date :08/12/2015
	 * @param :
	 * @return :Service Name
	 */

	@Override
	public List<PopulateData> getServiceList(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId,
			BigDecimal countryId, BigDecimal beneCurrencyId, String serviceGroupCode) {
		try {
			LOGGER.info("getServiceList method appLicationCountryId :" + appLicationCountryId);
			LOGGER.info("beneBankId :" + beneBankId);
			LOGGER.info("beneBankBranchId :" + beneBankBranchId);
			LOGGER.info("countryId :" + countryId);
			LOGGER.info("beneCurrencyId :" + beneCurrencyId);
			LOGGER.info("serviceGroupCode :" + serviceGroupCode);
			List<PopulateData> serviceList = new ArrayList<PopulateData>();
			String sqlQuery = "SELECT DISTINCT f.SERVICE_CODE,F.SERVICE_DESCRIPTION,  F.SERVICE_MASTER_ID " + " FROM   V_EX_ROUTING_DETAILS F "
					+ " WHERE  BENE_BANK_ID = " + beneBankId + " AND    BENE_BANK_BRANCH_ID = " + beneBankBranchId + " " + " AND    F.COUNTRY_ID ="
					+ countryId + " AND    F.CURRENCY_ID  = " + beneCurrencyId + " " + " AND    F.SERVICE_GROUP_CODE  = '" + serviceGroupCode
					+ "' AND  APPLICATION_COUNTRY_ID=" + appLicationCountryId + " ";

			LOGGER.info("getServiceList :" + sqlQuery);

			SQLQuery query = getSession().createSQLQuery(sqlQuery);
			List<Object> rows = query.list();
			List list = query.list();
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				PopulateData lstService = new PopulateData();
				Object[] row = (Object[]) iterator.next();
				for (int col = 0; col < row.length; col++) {
					lstService.setPopulateId(new BigDecimal(row[2].toString()));
					lstService.setPopulateName(row[1] == null ? "" : row[1].toString());
				}
				serviceList.add(lstService);
			}
			return serviceList;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CurrencyWiseDenomination> getCurrencyDenominations(BigDecimal currencyId, BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyWiseDenomination.class, "currencyWiseDenomination");

		dCriteria.setFetchMode("currencyWiseDenomination.exCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("currencyWiseDenomination.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrencyMaster.currencyId", currencyId));

		dCriteria.setFetchMode("currencyWiseDenomination.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("currencyWiseDenomination.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		dCriteria.add(Restrictions.isNotNull("currencyWiseDenomination.approvedBy"));
		dCriteria.add(Restrictions.eq("currencyWiseDenomination.isActive", Constants.Yes));

		dCriteria.addOrder(Order.desc("currencyWiseDenomination.denominationAmount"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyWiseDenomination> list = (List<CurrencyWiseDenomination>) findAll(dCriteria);
		return list;
	}

	@Override
	public List<ViewStock> toCheckStockForView(BigDecimal countryId, String userName, String countryBranchId, BigDecimal companyId, String currecyId) {
		String sql = " LOG_DATE = trunc(sysdate) ";

		LOGGER.info("countryId :"+countryId);
		LOGGER.info("userName :"+userName);
		LOGGER.info("countryBranchId :"+countryBranchId);
		LOGGER.info("currecyId :"+currecyId);


		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewStock.class, "viewStock");
		dCriteria.add(Restrictions.eq("viewStock.currencyId", new BigDecimal(currecyId)));
		dCriteria.add(Restrictions.eq("viewStock.countryBranchId", new BigDecimal(countryBranchId)));
		dCriteria.add(Restrictions.eq("viewStock.countryId", countryId));
		dCriteria.add(Restrictions.eq("viewStock.oracleUser", userName));
		dCriteria.add(Restrictions.sqlRestriction(sql));
		dCriteria.addOrder(Order.desc("viewStock.denominationAmount"));


		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ViewStock> data = (List<ViewStock>) dCriteria.getExecutableCriteria(getSession()).list();
		System.out.println("data :"+data.size());

		List<ViewStock> finalData = new ArrayList<ViewStock>();
		for (ViewStock Stock : data) {
			if (new SimpleDateFormat("dd/MMM/yy").format(Stock.getLogDate()).equalsIgnoreCase(new SimpleDateFormat("dd/MMM/yy").format(new Date()))) {
				finalData.add(Stock);
			}
		}
		System.out.println("finalData :"+finalData.size());

		return finalData;

	}

	@Override
	public List<DebitAutendicationView> getdebitAutendicationList() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DebitAutendicationView.class, "debitAutendicationView");
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<DebitAutendicationView>) findAll(dCriteria);
	}

	@Override
	public List<DebitAutendicationView> getdebitAutendicationListByUserId(String colAuthorizedby, String pwd) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(DebitAutendicationView.class, "debitAutendicationView");
		dCriteria.add(Restrictions.eq("debitAutendicationView.userName", colAuthorizedby));
		dCriteria.add(Restrictions.eq("debitAutendicationView.password", pwd));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<DebitAutendicationView>) findAll(dCriteria);
	}

	@Override
	public HashMap<String, String> getBannedBankCheckProcedure(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneMasSeqId) {

		Connection connection = null;

		HashMap<String, String> addtionalErrorMessage = new HashMap<String, String>();
		try {

			LOGGER.info("EX_P_BANNED_BANK_CHECK input parameter :" + appLicationCountryId + "\t beneBankId :" + beneBankId + "\t beneMasSeqId  :"
					+ beneMasSeqId);

			CallableStatement cs = null;
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_BANNED_BANK_CHECK(?,?,?,?,?,?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, appLicationCountryId);
			cs.setBigDecimal(2, beneBankId);
			cs.setBigDecimal(3, beneMasSeqId);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.execute();

			addtionalErrorMessage.put("P_ALERT_MESSAGE", cs.getString(4) == null ? "" : cs.getString(4));
			addtionalErrorMessage.put("P_ERROR_MESSAGE", cs.getString(5) == null ? "" : cs.getString(5));
			addtionalErrorMessage.put("P_BLIST_IND", cs.getString(6) == null ? "" : cs.getString(6));

			LOGGER.info("EX_P_BANNED_BANK_CHECK !!!!!!OUT PUT VALUES !!!!!!!!! ===  " + addtionalErrorMessage.toString());

			return addtionalErrorMessage;

		} catch (Exception e) {
			LOGGER.info("Error While getting connection " + e.getMessage());
			e.printStackTrace();
			addtionalErrorMessage.put("P_ERROR_MESSAGE", "EX_P_BANNED_BANK_CHECK " + e.getMessage());

		} finally {
			try {

				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Problem While Procedure calling " + e.getMessage());
				String erromsg = "EX_P_BANNED_NAME_CHECK" + " : " + e.getMessage();
			}
		}
		LOGGER.info("EX_P_BANNED_BANK_CHECK !!!!!!OUT PUT VALUES !!!!!!!!! ===  " + addtionalErrorMessage.toString());
		return addtionalErrorMessage;
	}

	@Override
	public String insertEMOSLIVETransfer(BigDecimal appcountryId, BigDecimal companyId, BigDecimal documentId, BigDecimal financialYr,
			BigDecimal documentNo) throws AMGException {
		LOGGER.info("Entered into insertEMOSLIVETransfer()  Method Called ");
		LOGGER.info("Procedure Name =  EX_INSERT_EMOS_TRANSFER_LIVE ");
		LOGGER.info("!!!!!!appcountryId!!!!!!!!!" + appcountryId);
		LOGGER.info("!!!!!!companyId!!!!!!!!!" + companyId);
		LOGGER.info("!!!!!!documentId!!!!!!!!!" + documentId);
		LOGGER.info("!!!!!!financialYr!!!!!!!!!" + financialYr);
		LOGGER.info("!!!!!!documentNo!!!!!!!!!" + documentNo);
		String outMessage = new String();
		Connection connection = null;
		try {
			// connection = DBConnection.getdataconnection();
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { call EX_INSERT_EMOS_TRANSFER_LIVE (?, ?, ?, ?, ?,?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, appcountryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, documentId);
			cs.setBigDecimal(4, financialYr);
			cs.setBigDecimal(5, documentNo);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.execute();
			outMessage = cs.getString(6);
			LOGGER.info("outMessage =" + outMessage);
		} catch (SQLException e) {
			LOGGER.info("Problem Occured When Procedure Calling=" + e.getMessage());
			String erromsg = "EX_INSERT_EMOS_TRANSFER_LIVE" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Problem Occured When Procedure Calling=" + e.getMessage());
				String erromsg = "EX_INSERT_EMOS_TRANSFER_LIVE" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		LOGGER.info("!!!!!!outLst out!!!!!!!!!" + outMessage);
		LOGGER.info("Exited from the insertEMOSLIVETransfer()  Method ");
		return outMessage;
	}

	@Override
	public HashMap<String, String> getloyalityPointsFromProcedure(BigDecimal customerRef, Date documentDate) {
		LOGGER.info("Procedure Name =  EX_P_REMIT_RECEIPT_REPORT ");
		LOGGER.info("!!!!!!customerRef!!!!!!!!!" + customerRef);
		LOGGER.info("!!!!!!documentDate!!!!!!!!!" + documentDate);
		HashMap<String, String> loyaltiPointMap = new HashMap<String, String>();
		Connection connection = null;
		try {
			// connection = DBConnection.getdataconnection();
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { call EX_P_REMIT_RECEIPT_REPORT (?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, customerRef);
			cs.setDate(2, new java.sql.Date(documentDate.getTime()));

			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.execute();

			String out1 = cs.getString(3);
			String out2 = cs.getString(4);
			String out3 = cs.getString(5);
			String out4 = cs.getString(6);
			String out5 = cs.getString(7);
			String out6 = cs.getString(8);

			loyaltiPointMap.put("P_LTY_STR1", out1 == null ? "" : out1.toString());
			loyaltiPointMap.put("P_LTY_STR2", out2 == null ? "" : out2.toString());
			loyaltiPointMap.put("P_INS_STR1", out3 == null ? "" : out3.toString());
			loyaltiPointMap.put("P_INS_STR2", out4 == null ? "" : out4.toString());
			loyaltiPointMap.put("P_INS_STR_AR1", out5 == null ? "" : out5.toString());
			loyaltiPointMap.put("P_INS_STR_AR2", out6 == null ? "" : out6.toString());

		} catch (SQLException e) {
			LOGGER.info("Problem Occured When Procedure Calling=" + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Problem Occured When Procedure Calling=" + e.getMessage());
			}
		}
		return loyaltiPointMap;
	}

	@Override
	public List<PendingTransferRequestEnquiryView> getAllRecordsFromApplicationDetailView(BigDecimal countryBranchId) {
		LOGGER.info("Entered into getAllRecordsFromApplicationDetailView(BigDecimal countryBranchId) Method");
		LOGGER.info("(BigDecimal countryBranchId) =" + countryBranchId);
		DetachedCriteria criteria = DetachedCriteria.forClass(PendingTransferRequestEnquiryView.class, "pendingTransferRequestEnquiryView");
		criteria.add(Restrictions.eq("pendingTransferRequestEnquiryView.countryBranchId", countryBranchId));
		List<PendingTransferRequestEnquiryView> shoppingCartDetails = (List<PendingTransferRequestEnquiryView>) findAll(criteria);
		LOGGER.info("Exited from the getAllRecordsFromApplicationDetailView(BigDecimal countryBranchId) ");
		return shoppingCartDetails;
	}

	@Override
	public List<CustomerIdProof> getCustomerDetailsThroughCusReg(String customerId) {
		LOGGER.info("Entered into getCustomerDetails(String customerId, BigDecimal cardType) method ");
		LOGGER.info("CustomerId =" + customerId);
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		criteria.add(Restrictions.eq("customerIdProof.identityInt", customerId));
		criteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.INNER_JOIN);
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from getCustomerDetails(String customerId, BigDecimal cardType) method");
		return (List<CustomerIdProof>) findAll(criteria);
	}

	@Override
	public PaymentModeDesc getvoucherModeId(String v, BigDecimal languageId) {
		LOGGER.info("V " + v);
		LOGGER.info("languageId " + languageId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
		dCriteria.setFetchMode("paymentModeDesc.paymentMode", FetchMode.JOIN);
		dCriteria.createAlias("paymentModeDesc.paymentMode", "paymentMode", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("paymentMode.paymentCode", v));
		dCriteria.setFetchMode("paymentModeDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("paymentModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", languageId));
		dCriteria.add(Restrictions.eq("paymentMode.isActive", Constants.Yes));
		List<PaymentModeDesc> list = (List<PaymentModeDesc>) findAll(dCriteria);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public List<RemittanceTransaction> getAllRemittanceBasedonCollectionDoc(Collect collectDB) {
		LOGGER.info(" getAllRemittanceBasedonCollectionDoc table ");
		LOGGER.info(" collectDB records " + collectDB.toString());
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");
		dCriteria.add(Restrictions.eq("remittanceTransaction.collectionDocId", collectDB.getDocumentCode()));
		dCriteria.add(Restrictions.eq("remittanceTransaction.collectionDocFinanceYear", collectDB.getDocumentFinanceYear()));
		dCriteria.add(Restrictions.eq("remittanceTransaction.collectionDocumentNo", collectDB.getDocumentNo()));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceTransaction> lstRemitTrnx = (List<RemittanceTransaction>) findAll(dCriteria);
		LOGGER.info(" lstRemitTrnx Size " + lstRemitTrnx.iterator());
		return lstRemitTrnx;
	}

	public String getNextDocumentReferenceNumber(BigDecimal countryId, BigDecimal companyId, BigDecimal documentId, BigDecimal financialYear,
			String processIn, BigDecimal branchId) {

		int out = 0;
		// Added by Rabil 18072015
		LOGGER.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO countryId :" + countryId);
		LOGGER.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO companyId :" + companyId);
		LOGGER.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO documentId :" + documentId);
		LOGGER.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO financialYear :" + financialYear);
		LOGGER.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO processIn :" + processIn);
		LOGGER.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO branchId :" + branchId);

		Connection connection = null;
		// connection = DBConnection.getdataconnection();
		connection = getDataSourceFromHibernateSession();
		CallableStatement cs;
		try {
			cs = connection.prepareCall(" { call EX_TO_GEN_NEXT_DOC_SERIAL_NO(?,?,?,?,?,?,?,?,?)}");
			cs.setBigDecimal(1, countryId);
			cs.setBigDecimal(2, branchId);
			cs.setBigDecimal(3, companyId);
			cs.setBigDecimal(4, documentId);
			cs.setBigDecimal(5, financialYear);
			cs.setString(6, processIn);
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.registerOutParameter(9, java.sql.Types.VARCHAR);
			// cs.executeUpdate();
			cs.execute();
			out = cs.getInt(7);
			String a = cs.getString(8);
			String b = cs.getString(9);

			System.out.println("out :" + out + "\t a :" + a + "\t b:" + b);

			LOGGER.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO out Value :" + out);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return String.valueOf(out);
	}

	@Override
	public BigDecimal saveTempCollectionwithDetailsandTempCurrencyAdjust(TempCollection tempCollection, List<TempCollectDetail> tempDetailsList,
			List<TempForeignCurrencyAdjust> tempAdjustList, List<AuthorizedLog> lstAuthenStoredRecords) throws Exception {
		BigDecimal collectionId;
		try {
			collectionId = saveCollection(tempCollection);
			for (TempCollectDetail detail : tempDetailsList) {
				detail.setCashCollectionId(tempCollection);
				saveDetail(detail);
			}
			for (TempForeignCurrencyAdjust adjust : tempAdjustList) {
				adjust.setTempCollection(tempCollection);
				saveAdjust(adjust);
			}
			for (AuthorizedLog authorizedlog : lstAuthenStoredRecords) {
				LOGGER.info("Entering into AuthorizedLog method");
				getSession().save(authorizedlog);
				LOGGER.info("Exit into AuthorizedLog method");
			}
		} catch (Exception e) {
			LOGGER.info("Exception occured" + e);
			throw new Exception(e);
		}
		return collectionId;
	}

	public BigDecimal saveCollection(TempCollection tempCollection) {
		LOGGER.info("Entering into saveCollection method");
		return (BigDecimal) getSession().save(tempCollection);
	}

	public void saveDetail(TempCollectDetail TempCollectDetail) {
		LOGGER.info("Entering into saveDetail method");
		getSession().saveOrUpdate(TempCollectDetail);
		LOGGER.info("Exit into saveDetail method");
	}

	public void saveAdjust(TempForeignCurrencyAdjust tempForeignCurrencyAdjust) {
		LOGGER.info("Entering into saveAdjust method");
		getSession().saveOrUpdate(tempForeignCurrencyAdjust);
		LOGGER.info("Exit into saveAdjust method");
	}

	/*@Override
	public BigDecimal getRemittanceApplicationstatus(BigDecimal customerId, BigDecimal documentNo) {
		LOGGER.info("Entered into getRemittanceApplicationstatus(BigDecimal customerId, BigDecimal documentNo) Method ");
		LOGGER.info("customerId = " + customerId);
		LOGGER.info("documentNo = " + documentNo);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplication.class, "remittanceApplication");
		criteria.setFetchMode("remittanceApplication.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.add(Restrictions.eq("remittanceApplication.documentNo", documentNo));
		criteria.add(Restrictions.isNull("remittanceApplication.applicaitonStatus"));
		List<RemittanceApplication> remittanceAppList = findAll(criteria);
		LOGGER.info("Exited getRemittanceApplicationstatus(BigDecimal customerId, BigDecimal documentNo) Method ");
		if (!remittanceAppList.isEmpty()) {
			return remittanceAppList.get(0).getRemittanceApplicationId();
		}
		return null;
	}*/

	@Override
	public BigDecimal getReceiptPaymentTableStatus(BigDecimal customerId, BigDecimal documentNo) {
		LOGGER.info("Entered into getReceiptPaymentTableStatus(BigDecimal customerId, BigDecimal documentNo) Method");
		LOGGER.info("customerId =" + customerId);
		LOGGER.info("documentNo =" + documentNo);
		DetachedCriteria criteria = DetachedCriteria.forClass(ReceiptPaymentApp.class, "receiptPaymentApp");
		criteria.add(Restrictions.eq("receiptPaymentApp.customerId", customerId));
		criteria.add(Restrictions.eq("receiptPaymentApp.documentNo", documentNo));
		criteria.add(Restrictions.isNull("receiptPaymentApp.applicationStatus"));
		List<ReceiptPaymentApp> receiptPaymentList = findAll(criteria);
		LOGGER.info("Exited from getReceiptPaymentTableStatus(BigDecimal customerId, BigDecimal documentNo) Method");
		if (!receiptPaymentList.isEmpty()) {
			return receiptPaymentList.get(0).getReceiptId();
		}
		return null;
	}

	@Override
	public List<PendingTransferRequestEnquiryView> getAllRecordsFromApplicationDetailView(BigDecimal countryBranchId, Date startDate, Date endDate,BigDecimal customerReference) {
		LOGGER.info("Entered into getAllRecordsFromApplicationDetailView(BigDecimal countryBranchId,Date startDate , Date endDate) Method");
		LOGGER.info("(BigDecimal countryBranchId) =" + countryBranchId +"\t customerReference :"+customerReference);
		List<PendingTransferRequestEnquiryView> listView = new ArrayList<PendingTransferRequestEnquiryView>();

		String startDateStr= null;
		String endDateStr =null;

		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		
		if(startDate!= null ){ 
			startDateStr = dateformat.format(startDate);
		}else{
			startDateStr = dateformat.format(new Date());
		}

		if(endDate!= null){
			endDateStr = dateformat.format(endDate);
		}else{
			endDateStr = dateformat.format(new Date());
		}

		String sql    = " ACCOUNT_MMYYYY = trunc(TO_DATE('" + startDateStr + "','dd/MM/yyyy'),'MONTH')";
		String sqlBet = " trunc(DOCUMENT_DATE) between to_date('" + startDateStr + "' ,'dd/MM/yyyy') AND to_date('" + endDateStr + "','dd/MM/yyyy')";
		
		DetachedCriteria criteria = DetachedCriteria.forClass(PendingTransferRequestEnquiryView.class, "pendingTransferRequestEnquiryView");
		if(countryBranchId != null){
			criteria.add(Restrictions.eq("pendingTransferRequestEnquiryView.countryBranchId", countryBranchId));
			
			if(customerReference != null){
				criteria.add(Restrictions.eq("pendingTransferRequestEnquiryView.customerRef", customerReference));
				if(startDateStr != null && endDateStr != null){
					criteria.add(Restrictions.sqlRestriction(sql));
					criteria.add(Restrictions.sqlRestriction(sqlBet));
				}
			}else if(startDateStr != null && endDateStr != null){
				criteria.add(Restrictions.sqlRestriction(sql));
				criteria.add(Restrictions.sqlRestriction(sqlBet));
			}else{
				// no records
			}
			
			listView = (List<PendingTransferRequestEnquiryView>) findAll(criteria);
		}

		return listView;
	}

	@Override
	public String declartionLimitCheck(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal customerId, Date documentDate,
			String paymentMode) throws AMGException {
		// TODO Auto-generated method stub
		LOGGER.info("Entered into declartionLimitCheck(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal customerId, Date documentDate, BigDecimal limit , String paymentMode) Method");
		LOGGER.info("Procedure Name =  EX_F_DECLARATION_LIMIT");
		LOGGER.info("applcountryId =" + applicationCountryId);
		LOGGER.info("companyId =" + companyId);
		LOGGER.info("customerId =" + customerId);
		LOGGER.info("documentDate =" + documentDate);
		LOGGER.info("paymentMode =" + paymentMode);
		String roundvalue = "";
		Connection connection = null;
		try {
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.error("Error Occured in EX_F_DECLARATION_LIMIT Functiona call");
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { ? = call EX_F_DECLARATION_LIMIT (?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.registerOutParameter(1, java.sql.Types.VARCHAR);
			cs.setBigDecimal(2, applicationCountryId);
			cs.setBigDecimal(3, companyId);
			cs.setBigDecimal(4, customerId);

			java.sql.Date sqlDate = new java.sql.Date(documentDate.getTime());
			cs.setDate(5, sqlDate);
			cs.setString(6, paymentMode);
			cs.execute();
			roundvalue = cs.getString(1);
			LOGGER.info("roundingTotalNetAmountbyFunction applcountryId:" + applicationCountryId);
			LOGGER.info("roundingTotalNetAmountbyFunction companyId:" + companyId);
			LOGGER.info("roundingTotalNetAmountbyFunction documentDate:" + documentDate);
			LOGGER.info("roundingTotalNetAmountbyFunction customerId:" + customerId);
			LOGGER.info("roundingTotalNetAmountbyFunction paymentMode:" + paymentMode);
		} catch (SQLException e) {
			LOGGER.info("Error while calling EX_F_DECLARATION_LIMIT Function ");
			String erromsg = "EX_F_DECLARATION_LIMIT" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Error while calling EX_F_DECLARATION_LIMIT FunctioRmn ");
				String erromsg = "EX_F_DECLARATION_LIMIT" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		LOGGER.info("Exited from the roundingTotalNetAmountbyFunction method");
		return roundvalue;
	}

	@Override
	public void saveKnetLogDetails(KnetLog knetLog) {
		getSession().save(knetLog);
	}

	public static String getCurrentDateWithFormat() {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		String month = new SimpleDateFormat("MMM").format(calendar.getTime());
		String year = new SimpleDateFormat("yy").format(calendar.getTime());

		System.out.println(Calendar.getInstance().get(Calendar.MONTH));
		return "01-" + month + "-" + year;
	}

	@Override
	public HashMap<BigDecimal, BigDecimal> beneficiaryCount(List<BigDecimal> lstBeneId) {
		LOGGER.info("Entered from the beneficiaryCount method");
		HashMap<BigDecimal, BigDecimal> lstBeneIdCountFromRemitBene = new HashMap<BigDecimal, BigDecimal>();
		HashMap<BigDecimal, BigDecimal> lstBeneIdCountFromTransfer = new HashMap<BigDecimal, BigDecimal>();
		HashMap<BigDecimal, BigDecimal> totalCountFromBothTables = new HashMap<BigDecimal, BigDecimal>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yy");
		Date currentdate = null;
		Date acyymmdateformate = null;
		String acyymmdate = getCurrentDateWithFormat();
		LOGGER.info("Accyymm date : " + acyymmdate);
		try {
			currentdate = df.parse(df.format(new Date()));
			acyymmdateformate = df.parse(acyymmdate);
			if (lstBeneId.size() != 0) {
				for (BigDecimal bigDecimal : lstBeneId) {
					// records from EX_Remit_bene - Java Records
					String hql = "select count(*) " + "from " + "RemittanceTranxBenificiary as remittanceTranxBenificiary "
							+ "where to_date(to_char(remittanceTranxBenificiary.createdDate, 'DD-MON-YY'), 'DD-MON-YY') = :currentdate  "
							+ "and remittanceTranxBenificiary.beneficiaryId = :beneId ";
					Query query = getSession().createQuery(hql);
					query.setParameter("currentdate", currentdate);
					query.setParameter("beneId", bigDecimal);
					Long count = (Long) query.uniqueResult();
					lstBeneIdCountFromRemitBene.put(bigDecimal, new BigDecimal(count));

					// based on beneficiary_master_id need to get
					// bene_map_seq_id
					DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");
					criteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
					criteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
					criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", bigDecimal));
					List<BeneficaryRelationship> remittanceAppList = findAll(criteria);

					// Sample query
					/*
					 * Query - select count(*) from TRANSFER where
					 * to_date(to_char(CRTDAT, 'DD-MON-YY'), 'DD-MON-YY') =
					 * '29-MAY-16' and BENE_SEQ in
					 * (7628428,7628428,7637325,7638176,7628428) and JAVA_TRNX
					 * is null and to_date(to_char(ACYYMM, 'MONTH'), 'MONTH') =
					 * '01-MAY-16';
					 */

					if (!remittanceAppList.isEmpty()) {
						BigDecimal total = bigDecimal.ZERO;
						String mapSeqId = null;
						for (BeneficaryRelationship beneficaryRelationship : remittanceAppList) {
							if (beneficaryRelationship.getMapSequenceId() != null) {
								mapSeqId = (mapSeqId != null ? mapSeqId.concat(",") : "").concat(beneficaryRelationship.getMapSequenceId()
										.toPlainString());
							}
						}

						// records from Transfer - OLD EMOs where Java is null
						if (mapSeqId != null) {
							String hql1 = "select count(*) from  TRANSFER where trunc(CRTDAT) = :currentdate and BENE_SEQ in (" + mapSeqId
									+ ") and JAVA_TRNX is null and ACYYMM = trunc(sysdate,'MONTH') ";
							Query query1 = getSession().createSQLQuery(hql1);
							query1.setParameter("currentdate", currentdate);
							List<Object> object = query1.list();
							Object countFromTransfer = object.get(0);
							System.out.println(countFromTransfer);
							total = total.add((BigDecimal) countFromTransfer);
						}
						lstBeneIdCountFromTransfer.put(bigDecimal, total);
					}

					// total count
					totalCountFromBothTables.put(bigDecimal,
							lstBeneIdCountFromRemitBene.get(bigDecimal).add(lstBeneIdCountFromTransfer.get(bigDecimal)));
				}
			}
			LOGGER.info("Exited from the beneficiaryCount method" + totalCountFromBothTables.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return totalCountFromBothTables;
	}

	@Override
	public List<AuthicationLimitCheckView> parameterLimitCheckForSameBene() {
		// String hql =
		// "SELECT A.AUTHORIZATION_TYPE,INITCAP(A.AUTH_MESSAGE), A.AUTH_LIMIT FROM V_EX_AUTH_LIMIT A WHERE  A.AUTHORIZATION_TYPE = '3' AND NVL(A.AUTH_LIMIT,0) >  0";
		DetachedCriteria criteria = DetachedCriteria.forClass(AuthicationLimitCheckView.class, "authicationLimitCheckView");
		criteria.add(Restrictions.eq("authicationLimitCheckView.authorizationType", Constants.LimitBeneficiaryPerDay_authorization_Type)); // Constants.Percentage_authorization_Type
		// =
		// 3
		criteria.add(Restrictions.isNotNull("authicationLimitCheckView.authLimit"));
		List<AuthicationLimitCheckView> authicationview = findAll(criteria);
		return authicationview;
	}

	@Override
	public List<AuthicationLimitCheckView> parameterDeTails_AUTH_View(String authorization_Type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AuthicationLimitCheckView.class, "authicationLimitCheckView");
		criteria.add(Restrictions.eq("authicationLimitCheckView.authorizationType", authorization_Type));
		// criteria.add(Restrictions.isNotNull("authicationLimitCheckView.authLimit"));
		List<AuthicationLimitCheckView> authicationview = findAll(criteria);
		return authicationview;
	}

	@Override
	public List<CustomerSpecialDealAppl> fetchAllCustSplDealByApplTrnxDoc(BigDecimal applTrnxApplDocNum) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerSpecialDealAppl.class, "customerSpecialDealAppl");
		criteria.add(Restrictions.eq("customerSpecialDealAppl.applicationDocumentNumber", applTrnxApplDocNum));
		List<CustomerSpecialDealAppl> customerSpecialAppl = findAll(criteria);

		return customerSpecialAppl;
	}

	@Override
	public List<BeneficaryAccount> getCashProductDetails(BigDecimal beneficaryAccountSeqId) throws AMGException {

		LOGGER.info(" BeneficaryAccount method ");
		LOGGER.info(" BeneficaryAccount Seq Id " + beneficaryAccountSeqId);

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		dCriteria.add(Restrictions.eq("beneficaryAccount.beneficaryAccountSeqId", beneficaryAccountSeqId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryAccount> lstBeneficaryAccount = (List<BeneficaryAccount>) findAll(dCriteria);
		LOGGER.info(" lstRemitTrnx Size " + lstBeneficaryAccount.size());

		return lstBeneficaryAccount;
	}

	@Override
	public List<PopulateData> getRemittanceListByCountryBankForCash(BigDecimal serviceGroupId) throws AMGException {
		LOGGER.info("getRemittanceListByCountryBankForCash method ");
		LOGGER.info("Service Group Id " + serviceGroupId);

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewServiceRemittance.class, "viewServiceRemittance");
		dCriteria.add(Restrictions.eq("viewServiceRemittance.serviceGroupId", serviceGroupId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewServiceRemittance> lstViewServiceRemittance = (List<ViewServiceRemittance>) findAll(dCriteria);
		LOGGER.info(" lstViewServiceRemittance Size " + lstViewServiceRemittance.size());

		List<PopulateData> lstRemittanceDetails = new ArrayList<PopulateData>();

		for (ViewServiceRemittance viewServiceRemittance : lstViewServiceRemittance) {
			PopulateData lstRBankData = new PopulateData();
			// String con = viewRoutingDetails.toString();
			lstRBankData.setPopulateId(viewServiceRemittance.getRemittanceModeId());
			lstRBankData.setPopulateName(viewServiceRemittance.getRemittanceModeDesc());

			lstRemittanceDetails.add(lstRBankData);
		}
		return lstRemittanceDetails;

	}

	@Override
	public List<PopulateData> getDeliverylistByRemitIdForCash(BigDecimal remittanceModeId) throws AMGException {
		LOGGER.info("getDeliverylistByRemitIdForCash method ");
		LOGGER.info("Service Group Id " + remittanceModeId);

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewDeliveryModeMap.class, "viewDeliveryModeMap");
		// dCriteria.add(Restrictions.eq("viewDeliveryModeMap.remittanceModeId",
		// remittanceModeId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewDeliveryModeMap> lstViewDeliveryModeMap = (List<ViewDeliveryModeMap>) findAll(dCriteria);
		LOGGER.info(" lstViewDeliveryModeMap Size " + lstViewDeliveryModeMap.size());

		List<PopulateData> lstRemittanceDetails = new ArrayList<PopulateData>();

		for (ViewDeliveryModeMap viewDeliveryModeMap : lstViewDeliveryModeMap) {
			PopulateData lstRBankData = new PopulateData();
			// String con = viewRoutingDetails.toString();
			lstRBankData.setPopulateId(viewDeliveryModeMap.getDeliveryModeId());
			lstRBankData.setPopulateName(viewDeliveryModeMap.getDeliveryModeDesc());

			lstRemittanceDetails.add(lstRBankData);
		}
		return lstRemittanceDetails;

	}

	@Override
	public int updtaeCustEmail(BigDecimal customerId, String custemailId) throws AMGException {

		String SQl = "Update FS_CUSTOMER set EMAIL = '" + custemailId + "'" + " where CUSTOMER_ID= " + customerId;
		int ui = getSession().createSQLQuery(SQl).executeUpdate();
		System.out.println("ui");
		return ui;
	}

	@Override
	public List<ApplicationSetup> getEmailFromAppSetup(BigDecimal companyId, BigDecimal applicationCountryId) throws AMGException {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ApplicationSetup.class, "applicationSetup");

		dCriteria.add(Restrictions.eq("applicationSetup.appSetupCountryMaster.countryId", applicationCountryId));

		dCriteria.add(Restrictions.eq("applicationSetup.fsCompanyMaster.companyId", companyId));

		List<ApplicationSetup> lstApplicationSetup = (List<ApplicationSetup>) findAll(dCriteria);

		return lstApplicationSetup;
	}

	// new
	/*@Override
	public HashMap<String, String> getRemitExchangeEquivalentAount(HashMap<String, String> inputValues) throws AMGException {
		LOGGER.info("Entered into getRemitExchangeEquivalentAount(HashMap<String, String> inputValues) Method");
		HashMap<String, String> outputValues = new HashMap<String, String>();
		Connection connection = null;
		try {
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.error("PROBLEM OCCURED WHILE GETTTING CONECTION");
			e.printStackTrace();
		}
		LOGGER.info("!!!!!! getRemitExchangeEquivalentAount IN PUT VALUES  !!!!!!!!! ==  " + inputValues.toString());
		LOGGER.info("!Calling  EX_P_GET_REMIT_EQUIV_AM Procedure with INPUT VALUES  !!!!!");
		LOGGER.info(inputValues.toString());
		CallableStatement cs;
		try {
			String call = " { call EX_P_GET_REMIT_EQUIV_AMT (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("P_APPLICATION_COUNTRY_ID")));
			cs.setBigDecimal(2, new BigDecimal(inputValues.get("P_ROUTING_COUNTRY_ID")));
			cs.setBigDecimal(3, new BigDecimal(inputValues.get("P_BRANCH_ID")));
			cs.setBigDecimal(4, new BigDecimal(inputValues.get("P_COMPANY_ID")));
			cs.setBigDecimal(5, new BigDecimal(inputValues.get("P_ROUTING_BANK_ID")));
			cs.setBigDecimal(6, new BigDecimal(inputValues.get("P_SERVICE_MASTER_ID")));
			cs.setBigDecimal(7, new BigDecimal(inputValues.get("P_DELIVERY_MODE_ID")));
			cs.setBigDecimal(8, new BigDecimal(inputValues.get("P_REMITTANCE_MODE_ID")));
			cs.setBigDecimal(9, new BigDecimal(inputValues.get("P_FOREIGN_CURRENCY_ID")));
			cs.setBigDecimal(10, new BigDecimal(inputValues.get("P_SELECTED_CURRENCY_ID")));
			cs.setString(11, inputValues.get("P_CUSTOMER_TYPE"));
			cs.setBigDecimal(12, new BigDecimal(inputValues.get("P_SELECTED_CURRENCY_AMOUNT")));
			cs.setString(13, Constants.C); // user type is constant C - Counter
			cs.registerOutParameter(14, java.sql.Types.INTEGER);
			cs.registerOutParameter(15, java.sql.Types.INTEGER);
			cs.registerOutParameter(16, java.sql.Types.VARCHAR);
			cs.execute();
			outputValues.put("P_EQUIV_CURRENCY_ID", cs.getBigDecimal(14) == null ? "0" : cs.getBigDecimal(14).toPlainString());
			outputValues.put("P_EQUIV_GROSS_AMOUNT", cs.getBigDecimal(15) == null ? "0" : cs.getBigDecimal(15).toPlainString());
			outputValues.put("P_ERROR_MESSAGE", cs.getString(16) == null ? "" : cs.getString(16));
			System.out.println("!!!!!! getRemitExchangeEquivalentAount outputValues VALUES  !!!!!!!!! ==  " + outputValues.toString());
			LOGGER.info("!After Calling  EX_P_GET_REMIT_EQUIV_AM Procedure returns OUTPUT VALUES  !!!!!");
			LOGGER.info(outputValues.toString());
		} catch (SQLException e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_P_GET_REMIT_EQUIV_AM" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} catch (Exception e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_P_GET_REMIT_EQUIV_AM" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error While Procedure Calling " + e.getMessage());
				String erromsg = "EX_P_GET_REMIT_EQUIV_AM" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return outputValues;
	}*/

	// old
	@Override
	public HashMap<String, String> getRemitExchangeEquivalentAount(HashMap<String, String> inputValues) throws AMGException {
		LOGGER.info("Entered into getRemitExchangeEquivalentAount(HashMap<String, String> inputValues) Method");
		HashMap<String, String> outputValues = new HashMap<String, String>();
		Connection connection = null;
		try {
			// connection = DBConnection.getdataconnection();
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.error("PROBLEM OCCURED WHILE GETTTING CONECTION");
			e.printStackTrace();
		}
		LOGGER.info("!!!!!! getRemitExchangeEquivalentAount IN PUT VALUES  !!!!!!!!! ==  " + inputValues.toString());
		LOGGER.info("!Calling  EX_P_GET_REMIT_EQUIV_AM Procedure with INPUT VALUES  !!!!!");
		LOGGER.info(inputValues.toString());
		CallableStatement cs;
		try {
			/*String call = " { call EX_P_GET_REMIT_EQUIV_AMT (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("P_APPLICATION_COUNTRY_ID")));
			cs.setBigDecimal(2, new BigDecimal(inputValues.get("P_ROUTING_COUNTRY_ID")));
			cs.setBigDecimal(3, new BigDecimal(inputValues.get("P_BRANCH_ID")));
			cs.setBigDecimal(4, new BigDecimal(inputValues.get("P_COMPANY_ID")));
			cs.setBigDecimal(5, new BigDecimal(inputValues.get("P_ROUTING_BANK_ID")));
			cs.setBigDecimal(6, new BigDecimal(inputValues.get("P_SERVICE_MASTER_ID")));
			cs.setBigDecimal(7, new BigDecimal(inputValues.get("P_DELIVERY_MODE_ID")));
			cs.setBigDecimal(8, new BigDecimal(inputValues.get("P_REMITTANCE_MODE_ID")));
			cs.setBigDecimal(9, new BigDecimal(inputValues.get("P_FOREIGN_CURRENCY_ID")));
			cs.setBigDecimal(10, new BigDecimal(inputValues.get("P_SELECTED_CURRENCY_ID")));
			cs.setString(11, inputValues.get("P_CUSTOMER_TYPE"));
			cs.setBigDecimal(12, new BigDecimal(inputValues.get("P_SELECTED_CURRENCY_AMOUNT")));
			cs.registerOutParameter(13, java.sql.Types.INTEGER);
			cs.registerOutParameter(14, java.sql.Types.INTEGER);
			cs.registerOutParameter(15, java.sql.Types.VARCHAR);

			cs.execute();// teUpdate();
			outputValues.put("P_EQUIV_CURRENCY_ID", cs.getBigDecimal(13) == null ? "0" : cs.getBigDecimal(13).toPlainString());
			outputValues.put("P_EQUIV_GROSS_AMOUNT", cs.getBigDecimal(14) == null ? "0" : cs.getBigDecimal(14).toPlainString());

			outputValues.put("P_ERROR_MESSAGE", cs.getString(15) == null ? "" : cs.getString(15));
			System.out.println("!!!!!! getRemitExchangeEquivalentAount outputValues VALUES  !!!!!!!!! ==  " + outputValues.toString());
			LOGGER.info("!After Calling  EX_P_GET_REMIT_EQUIV_AM Procedure returns OUTPUT VALUES  !!!!!");
			LOGGER.info(outputValues.toString());*/
			

			String call = " { call EX_P_GET_REMIT_EQUIV_AMT_RATE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("P_APPLICATION_COUNTRY_ID")));
			cs.setBigDecimal(2, new BigDecimal(inputValues.get("P_ROUTING_COUNTRY_ID")));
			cs.setBigDecimal(3, new BigDecimal(inputValues.get("P_BRANCH_ID")));
			cs.setBigDecimal(4, new BigDecimal(inputValues.get("P_COMPANY_ID")));
			cs.setBigDecimal(5, new BigDecimal(inputValues.get("P_ROUTING_BANK_ID")));
			cs.setBigDecimal(6, new BigDecimal(inputValues.get("P_SERVICE_MASTER_ID")));
			cs.setBigDecimal(7, new BigDecimal(inputValues.get("P_DELIVERY_MODE_ID")));
			cs.setBigDecimal(8, new BigDecimal(inputValues.get("P_REMITTANCE_MODE_ID")));
			cs.setBigDecimal(9, new BigDecimal(inputValues.get("P_FOREIGN_CURRENCY_ID")));
			cs.setBigDecimal(10, new BigDecimal(inputValues.get("P_SELECTED_CURRENCY_ID")));
			cs.setString(11, inputValues.get("P_CUSTOMER_TYPE"));
			cs.setBigDecimal(12, new BigDecimal(inputValues.get("P_SELECTED_CURRENCY_AMOUNT")));
			cs.registerOutParameter(13, java.sql.Types.INTEGER);
			cs.registerOutParameter(14, java.sql.Types.INTEGER);
			cs.registerOutParameter(15, java.sql.Types.INTEGER);
			cs.registerOutParameter(16, java.sql.Types.VARCHAR);
			cs.execute();
			outputValues.put("P_EXCHANGE_RATE", cs.getBigDecimal(13) == null ? "0" : cs.getBigDecimal(13).toPlainString());
			outputValues.put("P_EQUIV_CURRENCY_ID", cs.getBigDecimal(14) == null ? "0" : cs.getBigDecimal(14).toPlainString());
			outputValues.put("P_EQUIV_GROSS_AMOUNT", cs.getBigDecimal(15) == null ? "0" : cs.getBigDecimal(15).toPlainString());
			outputValues.put("P_ERROR_MESSAGE", cs.getString(16) == null ? "" : cs.getString(16));
			System.out.println("!!!!!! getRemitExchangeEquivalentAount outputValues VALUES  !!!!!!!!! ==  " + outputValues.toString());
			LOGGER.info("!After Calling  EX_P_GET_REMIT_EQUIV_AM Procedure returns OUTPUT VALUES  !!!!!");
			LOGGER.info(outputValues.toString());
		
		} catch (SQLException e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_P_GET_REMIT_EQUIV_AM" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} catch (Exception e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_P_GET_REMIT_EQUIV_AM" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error While Procedure Calling " + e.getMessage());
				String erromsg = "EX_P_GET_REMIT_EQUIV_AM" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return outputValues;
	}

	@Override
	public List<BenificiaryListView> getBeneficiaryCountryList(BigDecimal cutomerNo, BigDecimal beneCountryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BenificiaryListView.class, "benificiaryListView");
		criteria.add(Restrictions.eq("benificiaryListView.customerId", cutomerNo));
		if (beneCountryId != null) {
			criteria.add(Restrictions.eq("benificiaryListView.benificaryCountry", beneCountryId));
		}
		criteria.addOrder(Order.desc("benificiaryListView.createdDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BenificiaryListView> lstBeneView = (List<BenificiaryListView>) findAll(criteria);

		return lstBeneView;
	}

	@Override
	public List<PopulateData> getBeneficaryList(BigDecimal customerNo) {

		List<PopulateData> lstRemittanceDetails = new ArrayList<PopulateData>();
		//String hqlQuery = "select distinct a.benificaryCountry,a.benificaryBankCountryName from  BenificiaryListView a where a.customerId =  :customerId ORDER BY a.benificaryBankCountryName asc";
		if(customerNo != null){
			String hqlQuery = "Select Distinct BENEFICARY_COUNTRY , BENEFICARY_BANK_COUNTRY_NAME , COUNTRY_CODE from VW_BENEFICIARY_COUNTRY where CUSTOMER_ID = :customerId ORDER BY BENEFICARY_BANK_COUNTRY_NAME Asc";
			Query query = getSession().createSQLQuery(hqlQuery);
			query.setParameter("customerId", customerNo);

			List<Object[]> list = query.list();
			

			if (list == null || list.isEmpty()){
				// noo records
			}else{
				for (Object object : list) {
					Object[] li = (Object[]) object;
					if (li.length > 0) {
						if (li[0] != null && li[1] != null && li[2] != null) {
							PopulateData lstRBankData = new PopulateData();
							lstRBankData.setPopulateId(new BigDecimal(li[0].toString()));
							lstRBankData.setPopulateCode(li[2].toString());
							lstRBankData.setPopulateName(li[1].toString());
							lstRemittanceDetails.add(lstRBankData);
						}
					}
				}
			}
		}

		return lstRemittanceDetails;
	}

	public Map<BigDecimal, String> fetchAllCountryCode() {

		Map<BigDecimal, String> lstCountryCode = new HashMap<BigDecimal, String>();
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CountryMaster> countryNameList = (List<CountryMaster>) findAll(criteria);
		if (countryNameList.size() != 0) {
			for (CountryMaster countryMaster : countryNameList) {
				lstCountryCode.put(countryMaster.getCountryId(), countryMaster.getCountryCode());
			}
		} else {
			if (lstCountryCode != null || !lstCountryCode.isEmpty()) {
				lstCountryCode.clear();
			}
		}

		return lstCountryCode;
	}

	@Override
	public String getCountryCode(BigDecimal countryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
		criteria.add(Restrictions.eq("countryMaster.countryId", countryId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CountryMaster> countryNameList = (List<CountryMaster>) findAll(criteria);
		if (countryNameList.size() > 0) {
			return countryNameList.get(0).getCountryCode();
		} else {
			return null;
		}
	}

	@Override
	public BigDecimal getLastEmosRemittanceCountry(BigDecimal customerNo) throws AMGException {

		BigDecimal beneCountry = null;
		Connection connection = null;
		try {
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.error("Error Occured in  gettting connection");
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { ? = call EX_F_LAST_REMIT_BENE_CNTY (?) } ";
			cs = connection.prepareCall(call);
			cs.registerOutParameter(1, java.sql.Types.INTEGER);
			cs.setBigDecimal(2, customerNo);
			cs.execute();
			beneCountry = cs.getBigDecimal(1);

		} catch (SQLException e) {
			LOGGER.info("Error while calling  function  ");
			String erromsg = "" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				String erromsg = " " + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}

		return beneCountry;

	}

	@Override
	public String getBeneficiaryStatusForEdit(HashMap<String, String> inputValues) throws AMGException {

		String beneStatusForEdit = null;
		Connection connection = null;
		try {
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.error("Error Occured in  gettting connection");
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { ? = call EX_F_CHECK_BENE_EDIT(?,?,?) } ";
			cs = connection.prepareCall(call);
			cs.registerOutParameter(1, java.sql.Types.VARCHAR);
			cs.setBigDecimal(2, new BigDecimal(inputValues.get("P_BENE_MASTER_SEQ")));
			cs.setBigDecimal(3, new BigDecimal(inputValues.get("P_BENE_ACCOUNT_SEQ")));
			cs.setBigDecimal(4, new BigDecimal(inputValues.get("P_CUSTOMER_ID")));
			cs.execute();
			beneStatusForEdit = cs.getString(1);

		} catch (SQLException e) {
			LOGGER.info("Error while calling  function  ");
			String erromsg = "" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				String erromsg = " " + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return beneStatusForEdit;
	}

	/*@Override
	public void saveBeneficiaryEdit(HashMap<String, String> inputValues, BeneficaryMasterLog beneficaryMasterLog) throws Exception {

		try {

			String beneficiaryAccountSeqId = inputValues.get("BeneficiaryAccountSeqId");
			String beneficaryMasterSeqId = inputValues.get("BeneficaryMasterSeqId");
			String beneficiaryStateId = inputValues.get("BeneficiaryStateId");
			String beneficiaryStateName = inputValues.get("BeneficiaryStateName");
			String beneficiaryAccountType = inputValues.get("BeneficiaryAccountType");
			String beneficiaryDistId = inputValues.get("BeneficiaryDistId");
			String beneficiaryDistName = inputValues.get("BeneficiaryDistName");
			String beneficiaryDistIdValue = inputValues.get("BeneficiaryDistIdValue");
			String beneficiaryCityId = inputValues.get("BeneficiaryCityId");
			String beneficiaryCityName = inputValues.get("BeneficiaryCityName");
			String BeneficiaryCityIdValue = inputValues.get("BeneficiaryDistIdValue");
			String beneficiaryCountryMobilePhoneNumber = inputValues.get("BeneficiaryCountryMobilePhoneNumber");
			String beneficiaryCountryMobilePhoneNumberValue = inputValues.get("BeneficiaryCountryMobilePhoneNumberValue");
			String beneficiaryCountryTelePhoneNumber = inputValues.get("BeneficiaryCountryTelePhoneNumber");
			String beneficiaryCountryTelePhoneNumberValue = inputValues.get("BeneficiaryCountryTelePhoneNumberValue");
			String telePhoneSeqIdValue = inputValues.get("TelePhoneSeqIdValue");
			String telePhoneSeqId = inputValues.get("TelePhoneSeqId");
			String applicationCountryId = inputValues.get("ApplicationCountryId");
			String userId = inputValues.get("UseId");
			String teleCode = inputValues.get("TeleCode");
			String newFirstName = inputValues.get("NewFirstName");
			String newSecondName = inputValues.get("NewSecondName");
			String newThirdName = inputValues.get("NewThirdName");
			String newFourthName = inputValues.get("NewFourthName");
			String newFifthName = inputValues.get("NewFifthName");
			String newFirstNameLocal = inputValues.get("NewFirstNameLocal");
			String newSecondNameLocal = inputValues.get("NewSecondNameLocal");
			String newThirdNameLocal = inputValues.get("NewThirdNameLocal");
			String newFourthNameLocal = inputValues.get("NewFourthNameLocal");
			String beneficaryNameModified = inputValues.get("BeneficaryNameModified");

			BeneficaryMaster beneficaryMaster = (BeneficaryMaster) getSession().get(BeneficaryMaster.class, new BigDecimal(beneficaryMasterSeqId));

			BeneficaryAccount beneficaryAccount = (BeneficaryAccount) getSession().get(BeneficaryAccount.class,
					new BigDecimal(beneficiaryAccountSeqId));

			if (beneficiaryStateId != null) {
				StateMaster stateMaster = new StateMaster();
				stateMaster.setStateId(new BigDecimal(beneficiaryStateId));
				beneficaryMaster.setFsStateMaster(stateMaster);
			}

			if (beneficiaryStateName != null) {
				beneficaryMaster.setStateName(beneficiaryStateName);
			}

			if (beneficiaryDistIdValue.equalsIgnoreCase("YES")) {
				if (beneficiaryDistId != null) {
					DistrictMaster districtMaster = new DistrictMaster();
					districtMaster.setDistrictId(new BigDecimal(beneficiaryDistId));
					beneficaryMaster.setFsDistrictMaster(districtMaster);

					if (beneficiaryDistName != null) {
						beneficaryMaster.setDistrictName(beneficiaryDistName);
					}
				}

			}

			if (BeneficiaryCityIdValue.equalsIgnoreCase("YES")) {
				if (beneficiaryCityId != null) {
					CityMaster cityMaster = new CityMaster();
					cityMaster.setCityId(new BigDecimal(beneficiaryCityId));
					beneficaryMaster.setFsCityMaster(cityMaster);

					if (beneficiaryCityName != null) {
						beneficaryMaster.setCityName(beneficiaryCityName);
					}
				}
			}

			if (beneficaryNameModified != null && beneficaryNameModified.equalsIgnoreCase(Constants.YES)) {
				beneficaryMaster.setFirstName(newFirstName);
				beneficaryMaster.setSecondName(newSecondName);
				beneficaryMaster.setThirdName(newThirdName);
				beneficaryMaster.setFourthName(newFourthName);
				beneficaryMaster.setFifthName(newFifthName);
				beneficaryMaster.setLocalFirstName(newFirstNameLocal);
				beneficaryMaster.setLocalSecondName(newSecondNameLocal);
				beneficaryMaster.setLocalThirdName(newThirdNameLocal);
				beneficaryMaster.setLocalFourthName(newFourthNameLocal);
			}

			beneficaryMaster.setModifiedDate(new Date());
			beneficaryMaster.setModifiedBy(userId);

			getSession().saveOrUpdate(beneficaryMaster);

			// log for beneficary Master
			if (beneficaryNameModified != null && beneficaryNameModified.equalsIgnoreCase(Constants.YES) && beneficaryMasterLog != null) {
				getSession().save(beneficaryMasterLog);
			}

			if (beneficiaryAccountType != null) {
				beneficaryAccount.setBankAccountTypeId(new BigDecimal(beneficiaryAccountType));
			}

			if (beneficaryAccount.getBankCode() == null || beneficaryAccount.getBankBranchCode() == null) {
				if (beneficaryAccount.getServicegropupId().getServiceGroupCode().equalsIgnoreCase(Constants.CashCode)) {
					// setting Cash Product - // setting bank code
					if (beneficaryAccount.getBankCode() == null) {
						if (beneficaryAccount.getBank().getBankId() != null && beneficaryAccount.getBankCode() == null) {
							List<ViewRoutingAgents> lstAgentsBanks = fetchAllRoutingAgents(beneficaryAccount.getBeneApplicationCountry()
									.getCountryId(), beneficaryAccount.getBeneficaryCountry().getCountryId(), beneficaryAccount.getServicegropupId()
									.getServiceGroupId(), beneficaryAccount.getBank().getBankId());
							for (ViewRoutingAgents banksview : lstAgentsBanks) {
								if (banksview.getAgentBankId().compareTo(beneficaryAccount.getBank().getBankId()) == 0) {
									beneficaryAccount.setBankCode(banksview.getAgentBankCode());
									break;
								}
							}
						}
					}

					// setting bank branch code
					if (beneficaryAccount.getBankBranchCode() == null) {
						if (beneficaryAccount.getBankBranch().getBankBranchId() != null && beneficaryAccount.getBankBranchCode() == null) {
							List<ViewRoutingAgentLocations> lstAgentLocationForCash = fetchAllRoutingAgentLocations(beneficaryAccount
									.getBeneApplicationCountry().getCountryId(), beneficaryAccount.getBeneficaryCountry().getCountryId(),
									beneficaryAccount.getServicegropupId().getServiceGroupId(), beneficaryAccount.getBank().getBankId(),
									beneficaryAccount.getBankBranch().getBankBranchId());
							for (ViewRoutingAgentLocations banksBranchview : lstAgentLocationForCash) {
								if (banksBranchview.getBankBranchId().compareTo(beneficaryAccount.getBankBranch().getBankBranchId()) == 0) {
									beneficaryAccount.setBankBranchCode(banksBranchview.getBranchCode());
									break;
								}
							}
						}
					}

				} else {
					// setting Banking Channel Product - // setting bank code
					if (beneficaryAccount.getBankCode() == null) {
						if (beneficaryAccount.getBank().getBankId() != null && beneficaryAccount.getBankCode() == null) {
							List<BanksView> lstBankFromView = getBankListFromView(beneficaryAccount.getBeneApplicationCountry().getCountryId(),
									beneficaryAccount.getBeneficaryCountry().getCountryId(), beneficaryAccount.getServicegropupId()
									.getServiceGroupId(), beneficaryAccount.getBank().getBankId());
							for (BanksView banksview : lstBankFromView) {
								if (banksview.getBankId().compareTo(beneficaryAccount.getBank().getBankId()) == 0) {
									beneficaryAccount.setBankCode(banksview.getBankCode());
									break;
								}
							}
						}
					}

					// setting bank branch code
					if (beneficaryAccount.getBankBranchCode() == null) {
						if (beneficaryAccount.getBankBranch().getBankBranchId() != null && beneficaryAccount.getBankBranchCode() == null) {
							List<BankBranchView> lstBankbranchView = getBranchListfromViewwithStateMissing(beneficaryAccount.getBank().getBankId(),
									beneficaryAccount.getBankBranch().getBankBranchId());
							for (BankBranchView bankBranchView : lstBankbranchView) {
								if (bankBranchView.getBankBranchId().compareTo(beneficaryAccount.getBankBranch().getBankBranchId()) == 0) {
									beneficaryAccount.setBankBranchCode(bankBranchView.getBranchCode());
									break;
								}
							}
						}
					}
				}
			}

			beneficaryAccount.setModifiedDate(new Date());
			beneficaryAccount.setModifiedBy(userId);

			getSession().saveOrUpdate(beneficaryAccount);

			if (beneficiaryCountryMobilePhoneNumberValue.equalsIgnoreCase("YES") || beneficiaryCountryTelePhoneNumberValue.equalsIgnoreCase("YES")) {
				if (telePhoneSeqIdValue.equalsIgnoreCase("YES")) {
					BeneficaryContact beneficaryContact = (BeneficaryContact) getSession().get(BeneficaryContact.class,
							new BigDecimal(telePhoneSeqId));
					if (beneficiaryCountryMobilePhoneNumber != null) {
						beneficaryContact.setMobileNumber(new BigDecimal(beneficiaryCountryMobilePhoneNumber));
					} else {
						beneficaryContact.setMobileNumber(null);
					}
					if (beneficiaryCountryTelePhoneNumber != null) {
						beneficaryContact.setTelephoneNumber(beneficiaryCountryTelePhoneNumber);
					} else {
						beneficaryContact.setTelephoneNumber(null);
					}
					if (teleCode != null) {
						beneficaryContact.setCountryTelCode(teleCode);
					}
					beneficaryContact.setModifiedDate(new Date());
					beneficaryContact.setModifiedBy(userId);

					getSession().saveOrUpdate(beneficaryContact);

				} else {
					BeneficaryContact beneficaryContact = new BeneficaryContact();
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(applicationCountryId));
					beneficaryContact.setApplicationCountryId(countryMaster);
					beneficaryContact.setBeneficaryMaster(beneficaryMaster);

					if (teleCode != null) {
						beneficaryContact.setCountryTelCode(teleCode);
					}
					beneficaryContact.setCreatedBy(userId);
					beneficaryContact.setCreatedDate(new Date());
					beneficaryContact.setIsActive(Constants.Yes);
					if (beneficiaryCountryMobilePhoneNumber != null) {
						beneficaryContact.setMobileNumber(new BigDecimal(beneficiaryCountryMobilePhoneNumber));
					} else {
						beneficaryContact.setMobileNumber(null);
					}

					if (beneficiaryCountryTelePhoneNumber != null) {
						beneficaryContact.setTelephoneNumber(beneficiaryCountryTelePhoneNumber);
					} else {
						beneficaryContact.setTelephoneNumber(null);
					}

					getSession().saveOrUpdate(beneficaryContact);

				}
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}*/

	public List<BanksView> getBankListFromView(BigDecimal appCountryId, BigDecimal bankCountryId, BigDecimal serviceGroupId, BigDecimal bankId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BanksView.class, "banksView");

		dCriteria.add(Restrictions.eq("banksView.applicationCountryId", appCountryId));
		dCriteria.add(Restrictions.eq("banksView.bankCountryId", bankCountryId));
		dCriteria.add(Restrictions.eq("banksView.serviceGroupId", serviceGroupId));
		dCriteria.add(Restrictions.eq("banksView.bankId", bankId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BanksView> bankList = (List<BanksView>) findAll(dCriteria);

		return bankList;
	}

	public List<BankBranchView> getBranchListfromViewwithStateMissing(BigDecimal benifisBankId, BigDecimal benifisBankBranchId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranchView.class, "bankBranchView");
		criteria.add(Restrictions.eq("bankBranchView.bankId", benifisBankId));
		criteria.add(Restrictions.eq("bankBranchView.bankBranchId", benifisBankBranchId));

		criteria.addOrder(Order.asc("bankBranchView.branchFullName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankBranchView>) findAll(criteria);

	}

	public List<ViewRoutingAgents> fetchAllRoutingAgents(BigDecimal appcountryId, BigDecimal countryId, BigDecimal serviceGoupId, BigDecimal banKId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewRoutingAgents.class, "viewRoutingAgents");

		dCriteria.add(Restrictions.eq("viewRoutingAgents.applicationCountryId", appcountryId));
		dCriteria.add(Restrictions.eq("viewRoutingAgents.routingCountryId", countryId));
		dCriteria.add(Restrictions.eq("viewRoutingAgents.serviceGroupId", serviceGoupId));
		dCriteria.add(Restrictions.eq("viewRoutingAgents.agentBankId", banKId));

		dCriteria.addOrder(Order.asc("viewRoutingAgents.routingBankId"));

		List<ViewRoutingAgents> lstAllData = (List<ViewRoutingAgents>) findAll(dCriteria);

		return lstAllData;
	}

	public List<ViewRoutingAgentLocations> fetchAllRoutingAgentLocations(BigDecimal appcountryId, BigDecimal countryId, BigDecimal serviceGoupId,
			BigDecimal agentbankId, BigDecimal agentbankbranchId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewRoutingAgentLocations.class, "viewRoutingAgentLocations");
		dCriteria.add(Restrictions.eq("viewRoutingAgentLocations.applicationCountryId", appcountryId));
		dCriteria.add(Restrictions.eq("viewRoutingAgentLocations.routingCountryId", countryId));
		dCriteria.add(Restrictions.eq("viewRoutingAgentLocations.serviceGroupId", serviceGoupId));
		dCriteria.add(Restrictions.eq("viewRoutingAgentLocations.agentBankId", agentbankId));
		dCriteria.add(Restrictions.eq("viewRoutingAgentLocations.bankBranchId", agentbankbranchId));

		dCriteria.addOrder(Order.asc("viewRoutingAgentLocations.routingBankId"));

		List<ViewRoutingAgentLocations> lstAllData = (List<ViewRoutingAgentLocations>) findAll(dCriteria);

		return lstAllData;
	}

	@Override
	public List<BeneficaryContact> getTelePhoneSeqId(BigDecimal beneficaryMasterSeqId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "benificiaryTelephone");
		criteria.setFetchMode("benificiaryTelephone.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("benificiaryTelephone.beneficaryMaster", "beneficaryMasterbeneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.isNotNull("benificiaryTelephone.telephoneNumber"));
		// criteria.add(Restrictions.isNotNull("benificiaryTelephone.mobileNumber"));
		criteria.add(Restrictions.eq("beneficaryMasterbeneficaryMaster.beneficaryMasterSeqId", beneficaryMasterSeqId));
		// criteria.add(Restrictions.eq("benificiaryTelephone.isActive",
		// Constants.Yes));
		// criteria.add(Restrictions.isNotNull("benificiaryTelephone.telephoneNumber"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BeneficaryContact>) findAll(criteria);
	}

	/*@Override
	public void saveBeneficiaryContactEdit(HashMap<String, String> inputValues, BeneficaryMasterLog beneficaryMasterLog) throws Exception {
		try {

			String beneficaryMasterSeqId = inputValues.get("BeneficaryMasterSeqId");
			String beneficiaryCountryMobilePhoneNumber = inputValues.get("BeneficiaryCountryMobilePhoneNumber");
			String beneficiaryCountryMobilePhoneNumberValue = inputValues.get("BeneficiaryCountryMobilePhoneNumberValue");
			String beneficiaryCountryTelePhoneNumber = inputValues.get("BeneficiaryCountryTelePhoneNumber");
			String beneficiaryCountryTelePhoneNumberValue = inputValues.get("BeneficiaryCountryTelePhoneNumberValue");
			String telePhoneSeqIdValue = inputValues.get("TelePhoneSeqIdValue");
			String telePhoneSeqId = inputValues.get("TelePhoneSeqId");
			String applicationCountryId = inputValues.get("ApplicationCountryId");
			String userId = inputValues.get("UseId");
			String teleCode = inputValues.get("TeleCode");

			String newFirstName = inputValues.get("NewFirstName");
			String newSecondName = inputValues.get("NewSecondName");
			String newThirdName = inputValues.get("NewThirdName");
			String newFourthName = inputValues.get("NewFourthName");
			String newFifthName = inputValues.get("NewFifthName");
			String newFirstNameLocal = inputValues.get("NewFirstNameLocal");
			String newSecondNameLocal = inputValues.get("NewSecondNameLocal");
			String newThirdNameLocal = inputValues.get("NewThirdNameLocal");
			String newFourthNameLocal = inputValues.get("NewFourthNameLocal");
			String beneficaryNameModified = inputValues.get("BeneficaryNameModified");

			BeneficaryMaster beneficaryMaster = (BeneficaryMaster) getSession().get(BeneficaryMaster.class, new BigDecimal(beneficaryMasterSeqId));
			if (beneficaryNameModified != null && beneficaryNameModified.equalsIgnoreCase(Constants.YES)) {
				beneficaryMaster.setFirstName(newFirstName);
				beneficaryMaster.setSecondName(newSecondName);
				beneficaryMaster.setThirdName(newThirdName);
				beneficaryMaster.setFourthName(newFourthName);
				beneficaryMaster.setFifthName(newFifthName);
				beneficaryMaster.setLocalFirstName(newFirstNameLocal);
				beneficaryMaster.setLocalSecondName(newSecondNameLocal);
				beneficaryMaster.setLocalThirdName(newThirdNameLocal);
				beneficaryMaster.setLocalFourthName(newFourthNameLocal);
			}
			beneficaryMaster.setModifiedDate(new Date());
			beneficaryMaster.setModifiedBy(userId);
			getSession().saveOrUpdate(beneficaryMaster);

			// log for beneficary Master
			if (beneficaryNameModified != null && beneficaryNameModified.equalsIgnoreCase(Constants.YES) && beneficaryMasterLog != null) {
				getSession().save(beneficaryMasterLog);
			}

			if (beneficiaryCountryMobilePhoneNumberValue.equalsIgnoreCase("YES") || beneficiaryCountryTelePhoneNumberValue.equalsIgnoreCase("YES")) {
				if (telePhoneSeqIdValue.equalsIgnoreCase("YES")) {
					BeneficaryContact beneficaryContact = (BeneficaryContact) getSession().get(BeneficaryContact.class,
							new BigDecimal(telePhoneSeqId));
					if (beneficiaryCountryMobilePhoneNumber != null) {
						beneficaryContact.setMobileNumber(new BigDecimal(beneficiaryCountryMobilePhoneNumber));
					} else {
						beneficaryContact.setMobileNumber(null);
					}
					if (beneficiaryCountryTelePhoneNumber != null) {
						beneficaryContact.setTelephoneNumber(beneficiaryCountryTelePhoneNumber);
					} else {
						beneficaryContact.setTelephoneNumber(null);
					}
					if (teleCode != null) {
						beneficaryContact.setCountryTelCode(teleCode);
					}
					beneficaryContact.setModifiedDate(new Date());
					beneficaryContact.setModifiedBy(userId);

					getSession().saveOrUpdate(beneficaryContact);
				} else {
					BeneficaryContact beneficaryContact = new BeneficaryContact();
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(applicationCountryId));
					beneficaryContact.setApplicationCountryId(countryMaster);
					beneficaryContact.setBeneficaryMaster(beneficaryMaster);
					if (teleCode != null) {
						beneficaryContact.setCountryTelCode(teleCode);
					}

					beneficaryContact.setCreatedBy(userId);
					beneficaryContact.setCreatedDate(new Date());
					beneficaryContact.setIsActive(Constants.Yes);
					if (beneficiaryCountryMobilePhoneNumber != null) {
						beneficaryContact.setMobileNumber(new BigDecimal(beneficiaryCountryMobilePhoneNumber));
					} else {
						beneficaryContact.setMobileNumber(null);
					}
					if (beneficiaryCountryTelePhoneNumber != null) {
						beneficaryContact.setTelephoneNumber(beneficiaryCountryTelePhoneNumber);
					} else {
						beneficaryContact.setTelephoneNumber(null);
					}

					getSession().saveOrUpdate(beneficaryContact);

				}
			}

		} catch (Exception e) {

			throw new Exception(e.getMessage());
		}
	}*/

	@Override
	public List<BeneficaryContact> getTelePhoneSeqIdBasedOnNum(BigDecimal telephoneNumberSelect, BigDecimal beneficaryMasterSeqId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "benificiaryTelephone");
		criteria.setFetchMode("benificiaryTelephone.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("benificiaryTelephone.beneficaryMaster", "beneficaryMasterbeneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("benificiaryTelephone.beneficaryTelephoneSeqId", telephoneNumberSelect));
		criteria.add(Restrictions.eq("beneficaryMasterbeneficaryMaster.beneficaryMasterSeqId", beneficaryMasterSeqId));
		// criteria.add(Restrictions.eq("benificiaryTelephone.isActive",
		// Constants.Yes));
		// criteria.add(Restrictions.isNotNull("benificiaryTelephone.telephoneNumber"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BeneficaryContact>) findAll(criteria);
	}

	@Override
	public List<BeneficaryContact> getMobileSeqId(BigDecimal beneficaryMasterSeqId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryContact.class, "benificiaryTelephone");
		criteria.setFetchMode("benificiaryTelephone.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("benificiaryTelephone.beneficaryMaster", "beneficaryMasterbeneficaryMaster", JoinType.INNER_JOIN);
		// criteria.add(Restrictions.isNotNull("benificiaryTelephone.telephoneNumber"));
		criteria.add(Restrictions.isNotNull("benificiaryTelephone.mobileNumber"));
		criteria.add(Restrictions.eq("beneficaryMasterbeneficaryMaster.beneficaryMasterSeqId", beneficaryMasterSeqId));
		// criteria.add(Restrictions.eq("benificiaryTelephone.isActive",
		// Constants.Yes));
		// criteria.add(Restrictions.isNotNull("benificiaryTelephone.telephoneNumber"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BeneficaryContact>) findAll(criteria);
	}

	@Override
	public HashMap<String, String> getSpecialApprovalCheck(HashMap<String, String> inputValues) throws AMGException {

		HashMap<String, String> hmOutputValues = new HashMap<String, String>();
		Connection connection = null;
		try {
			LOGGER.info("EX_SPECIAL_APPROVAL_CHECK getSpecialApprovalCheck foreignCurrencyId :" + inputValues.toString());

			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.error("Error Occured in  gettting connection");
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " {call EX_SPECIAL_APPROVAL_CHECK(?,?,?,?,?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("P_APPROVAL_YEAR")));
			cs.setBigDecimal(2, new BigDecimal(inputValues.get("P_APPROVAL_NO")));
			cs.setBigDecimal(3, new BigDecimal(inputValues.get("P_FOREIGN_CURRENCY_ID")));
			cs.setBigDecimal(4, new BigDecimal(inputValues.get("P_FOREIGN_AMT")));
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.execute();
			hmOutputValues.put("P_ERROR_MESSAG", cs.getString(5));
			LOGGER.info("EX_SPECIAL_APPROVAL_CHECK hmOutputValues values:" + hmOutputValues.toString());

		} catch (SQLException e) {
			LOGGER.info("Error while calling  function  ");
			String erromsg = "EX_SPECIAL_APPROVAL_CHECK " + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				String erromsg = "EX_SPECIAL_APPROVAL_CHECK " + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return hmOutputValues;
	}

	@Override
	public List<RemittanceBranchWiseEnquiryView> showRemittanceBranchWise(BigDecimal branchId, String userName, Date documentDate) {

		List<RemittanceBranchWiseEnquiryView> branchEnqListTemp = new ArrayList<RemittanceBranchWiseEnquiryView>();
		String sql = " ACCOUNT_MMYYYY = trunc(sysdate,'MONTH')";

		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceBranchWiseEnquiryView.class, "remittanceBranchWiseEnquiryView");
		criteria.add(Restrictions.eq("remittanceBranchWiseEnquiryView.countryBranchId", branchId));
		if (userName != null) {
			criteria.add(Restrictions.eq("remittanceBranchWiseEnquiryView.createdBy", userName));
		}
		// 28-07-2016
		criteria.add(Restrictions.sqlRestriction(sql));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RemittanceBranchWiseEnquiryView> branchEnqList = (List<RemittanceBranchWiseEnquiryView>) findAll(criteria);
		for (RemittanceBranchWiseEnquiryView view : branchEnqList) {
			if (new SimpleDateFormat("dd-MM-yy").format(view.getDocumentDate()).equals(new SimpleDateFormat("dd-MM-yy").format(documentDate))) {
				branchEnqListTemp.add(view);
			}
		}
		return branchEnqListTemp;
	}


	@Override
	public List<BeneficaryMaster> isBeneficaryMasterExistInDb(String firstName, String secondName, BigDecimal beneCountryId) {
		LOGGER.info("Entered into isBeneficaryMasterExistInDb Method");
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryMaster.class, "beneficaryMaster");
		criteria.add(Restrictions.eq("beneficaryMaster.firstName", firstName));
		criteria.add(Restrictions.eq("beneficaryMaster.secondName", secondName));
		criteria.setFetchMode("beneficaryMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", beneCountryId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the getAllMasterValues(BigDecimal masterId) Method");
		return (List<BeneficaryMaster>) findAll(criteria);
	}

	@Override
	public HashMap<String, String> pValidateCustomerTelephoneDetails(HashMap<String, String> fetchContactDetails) throws AMGException {

		LOGGER.info("Entered into pValidateCustomerTelephoneDetails(HashMap<String, String> inputValue) Method ");
		LOGGER.info("Procedure Name = EX_P_VALIDATE_CUST_TEL");
		LOGGER.info("APPLICATION_COUNTRY_ID : " + fetchContactDetails.get("APPLICATION_COUNTRY_ID"));
		LOGGER.info("CUSTOMER_ID : " + fetchContactDetails.get("CUSTOMER_ID"));

		HashMap<String, String> outputValues = new HashMap<String, String>();

		Connection connection = null;
		BigDecimal contactDtId = null;
		String telephoneNumber = null;
		String errString = null;

		try {
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			String call = " { call EX_P_VALIDATE_CUST_TEL (?, ?, ?, ?, ? ) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, new BigDecimal(fetchContactDetails.get("APPLICATION_COUNTRY_ID")));
			cs.setBigDecimal(2, new BigDecimal(fetchContactDetails.get("CUSTOMER_ID")));
			cs.registerOutParameter(3, java.sql.Types.NUMERIC);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.execute();
			contactDtId = cs.getBigDecimal(3);
			telephoneNumber = cs.getString(4);
			errString = cs.getString(5);
			if (contactDtId != null) {
				outputValues.put("P_CONTACT_ID", contactDtId.toPlainString());
				outputValues.put("P_TELEPHONE_NUMBER", telephoneNumber);
				outputValues.put("P_ERROR_MESSAGE", errString);
			} else {
				errString = "Customer Contact Details Not Available, Please Update Contact Details in Customer Registration";
				outputValues.put("P_ERROR_MESSAGE", errString);
			}

		} catch (SQLException e) {
			String erromsg = "EX_P_VALIDATE_CUST_TEL" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Problem Occured While Procedure calling " + e.getMessage());
				String erromsg = "EX_P_VALIDATE_CUST_TEL" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}

		return outputValues;
	}

	@Override
	public void deleteBeneAccountRecord(BigDecimal beneAccountSeqId, BigDecimal beneRelationSeqId, String status ) {

		if (beneAccountSeqId != null) {
			if (status.equalsIgnoreCase(Constants.Yes)) {
				BeneficaryAccount beneAccount = (BeneficaryAccount) getSession().get(BeneficaryAccount.class, beneAccountSeqId);
				beneAccount.setIsActive(Constants.Yes);
				beneAccount.setModifiedBy(sessionStateManage.getUserName());
				beneAccount.setModifiedDate(new Date());
				getSession().update(beneAccount);
			}
		}

		if (beneRelationSeqId != null) {
			BeneficaryRelationship beneRelation = (BeneficaryRelationship) getSession().get(BeneficaryRelationship.class, beneRelationSeqId);
			beneRelation.setIsActive(status);

			beneRelation.setModifiedBy(sessionStateManage.getUserName());
			beneRelation.setModifiedDate(new Date());
			getSession().update(beneRelation);
		}

	}

	@Override
	public List<PopulateData> getRemittanceListByCountryBankForCashProduct(BigDecimal appLicationCountryId, BigDecimal beneBankId,
			BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId,
			BigDecimal routingbankId, BigDecimal routingbankBankBranchId, BigDecimal languageId) {

		List<PopulateData> lstRemittanceDetails = new ArrayList<PopulateData>();
		LOGGER.info("======Start getRemittanceListByCountryBank ========");
		LOGGER.info("appLicationCountryId :" + appLicationCountryId);
		LOGGER.info("beneBankId :" + beneBankId);
		LOGGER.info("beneBankBranchId :" + beneBankBranchId);
		LOGGER.info("countryId :" + countryId);
		LOGGER.info("beneCurrencyId :" + beneCurrencyId);
		LOGGER.info("serviceMasterId :" + serviceMasterId);
		LOGGER.info("routingCountryId :" + routingCountryId);
		LOGGER.info("routingbankId :" + routingbankId);
		LOGGER.info("routingbankBankBranchId :" + routingbankBankBranchId);
		LOGGER.info("======End getRemittanceListByCountryBank ========");
		String sql = "SELECT REMITTANCE_MODE_ID FROM ( " + "SELECT DISTINCT F.REMITTANCE_MODE_ID " + " FROM   V_EX_ROUTING_DETAILS F "
				+ " WHERE  F.APPLICATION_COUNTRY_ID= "
				+ appLicationCountryId
				+ " AND    F.BENE_BANK_ID =  "
				+ beneBankId
				+ " AND    F.BENE_BANK_BRANCH_ID= "
				+ beneBankBranchId
				+ " AND    F.COUNTRY_ID = "
				+ countryId
				+ " AND    F.CURRENCY_ID  =  "
				+ beneCurrencyId
				+ " AND    F.SERVICE_MASTER_ID  = "
				+ serviceMasterId
				+ " AND    F.ROUTING_BANK_ID   =  DECODE("
				+ serviceMasterId
				+ ",101,"
				+ beneBankId
				+ ",F.ROUTING_BANK_ID)"
				+ " AND    F.ROUTING_COUNTRY_ID = "
				+ routingCountryId
				+ " AND    F.ROUTING_BANK_ID = "
				+ routingbankId
				+ " AND F.BANK_BRANCH_ID = "
				+ routingbankBankBranchId + ")";

		LOGGER.info("getRemittanceListByCountryBank :" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		List<BigDecimal> rows = query.list();
		for (BigDecimal viewRoutingDetails : rows) {
			PopulateData lstRBankData = new PopulateData();
			// String con = viewRoutingDetails.toString();
			lstRBankData.setPopulateId(viewRoutingDetails);
			String lstRemittanceName = getRemittanceDesc(viewRoutingDetails, languageId);
			if (lstRemittanceName != null) {
				lstRBankData.setPopulateName(lstRemittanceName);
			}
			lstRemittanceDetails.add(lstRBankData);
		}
		return lstRemittanceDetails;
	}

	@Override
	public List<PopulateData> getDeliveryListByCountryBankRemitForCashProduct(BigDecimal appLicationCountryId, BigDecimal beneBankId,
			BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId,
			BigDecimal routingbankId, BigDecimal routingbankBankBranchId, BigDecimal remittanceId, BigDecimal languageId) {

		List<PopulateData> lstDeliveryDetails = new ArrayList<PopulateData>();
		LOGGER.info("======Start getDeliveryListByCountryBankRemit ========");
		LOGGER.info("appLicationCountryId :" + appLicationCountryId);
		LOGGER.info("beneBankId :" + beneBankId);
		LOGGER.info("beneBankBranchId :" + beneBankBranchId);
		LOGGER.info("countryId :" + countryId);
		LOGGER.info("beneCurrencyId :" + beneCurrencyId);
		LOGGER.info("serviceMasterId :" + serviceMasterId);
		LOGGER.info("routingCountryId :" + routingCountryId);
		LOGGER.info("routingbankId :" + routingbankId);
		LOGGER.info("routingbankBankBranchId :" + routingbankBankBranchId);
		LOGGER.info("remitId :" + remittanceId);
		LOGGER.info("======End getDeliveryListByCountryBankRemit ========");
		String sql = "SELECT DELIVERY_MODE_ID FROM ( " + "SELECT DISTINCT F.DELIVERY_MODE_ID " + " FROM   V_EX_ROUTING_DETAILS F "
				+ " WHERE  F.APPLICATION_COUNTRY_ID= "
				+ appLicationCountryId
				+ " AND    F.BENE_BANK_ID =  "
				+ beneBankId
				+ " AND    F.BENE_BANK_BRANCH_ID= "
				+ beneBankBranchId
				+ " AND    F.COUNTRY_ID = "
				+ countryId
				+ " AND    F.CURRENCY_ID  =  "
				+ beneCurrencyId
				+ " AND    F.SERVICE_MASTER_ID  = "
				+ serviceMasterId
				+ " AND    F.ROUTING_BANK_ID   =  DECODE("
				+ serviceMasterId
				+ ",101,"
				+ beneBankId
				+ ",F.ROUTING_BANK_ID)"
				+ " AND    F.ROUTING_COUNTRY_ID = "
				+ routingCountryId
				+ " AND    F.ROUTING_BANK_ID = "
				+ routingbankId
				+ " AND F.BANK_BRANCH_ID = "
				+ routingbankBankBranchId + " AND    F.REMITTANCE_MODE_ID = " + remittanceId + " )";

		LOGGER.info("getDeliveryListByCountryBankRemit :" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		List<BigDecimal> rows = query.list();
		for (BigDecimal viewRoutingDetails : rows) {
			PopulateData lstRBankData = new PopulateData();
			lstRBankData.setPopulateId(viewRoutingDetails);
			String lstDeliveryName = getDeliveryDesc(viewRoutingDetails, languageId);
			if (lstDeliveryName != null) {
				lstRBankData.setPopulateName(lstDeliveryName);
			}
			lstDeliveryDetails.add(lstRBankData);
		}
		return lstDeliveryDetails;
	}

	@Override
	public void deleteShoppingCartForFCSale(ShoppingCartDataTableBean shoppingCartData) {

		LOGGER.info("deleteShoppingCartForFCSale");
		LOGGER.info("Company Id : " + shoppingCartData.getCompanyId());
		LOGGER.info("Document Id : " + shoppingCartData.getDocumentId());
		LOGGER.info("Document Year : " + shoppingCartData.getDocumentFinanceYear());
		LOGGER.info("Document Number : " + shoppingCartData.getDocumentNo());

		DetachedCriteria criteria = DetachedCriteria.forClass(ReceiptPaymentApp.class, "receiptPaymentApp");

		criteria.add(Restrictions.eq("receiptPaymentApp.companyId", shoppingCartData.getCompanyId()));
		criteria.add(Restrictions.eq("receiptPaymentApp.documentCode", shoppingCartData.getDocumentId())); // document
		// Id
		// is
		// carrying
		// doc
		// code
		// from
		// view
		criteria.add(Restrictions.eq("receiptPaymentApp.documentFinanceYear", shoppingCartData.getDocumentFinanceYear()));
		criteria.add(Restrictions.eq("receiptPaymentApp.documentNo", shoppingCartData.getDocumentNo()));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ReceiptPaymentApp> lstReceiptPayment = (List<ReceiptPaymentApp>) findAll(criteria);

		for (ReceiptPaymentApp receiptPaymentApp : lstReceiptPayment) {
			receiptPaymentApp.setIsActive(Constants.D);
			getSession().update(receiptPaymentApp);
		}

	}

	@Override
	public Boolean checkEFTAndTTForICASHProduct(HashMap<String, String> checkEFTAndTTForICASH) {

		LOGGER.info("Entered into checkEFTAndTTForICASHProduct Method ");

		Boolean checkEFTorTTIcash = false;

		String beneCountryCode = checkEFTAndTTForICASH.get("Bene_Country_Code");
		String beneBankAlphaCode = checkEFTAndTTForICASH.get("Bene_Bank_Aplha_Code");
		String routingBankAlphaCode = checkEFTAndTTForICASH.get("Routing_Bank_Aplha_Code");
		String routingCountryAlphaCode = checkEFTAndTTForICASH.get("Routing_Country_Aplha_Code");
		String beneCurrencyAlphaCode = checkEFTAndTTForICASH.get("Bene_Currency_Aplha_Code");
		String agentStatus = checkEFTAndTTForICASH.get("Agent_Status");
		String remittanceMode = checkEFTAndTTForICASH.get("Remittance_Mode_Code");

		LOGGER.info("Bene_Country_Code : " + beneCountryCode);
		LOGGER.info("Bene_Bank_Aplha_Code : " + beneBankAlphaCode);
		LOGGER.info("Routing_Bank_Aplha_Code : " + routingBankAlphaCode);
		LOGGER.info("Routing_Country_Aplha_Code : " + routingCountryAlphaCode);
		LOGGER.info("Bene_Currency_Aplha_Code : " + beneCurrencyAlphaCode);
		LOGGER.info("Agent_Status : " + agentStatus);
		LOGGER.info("Remittance_Mode_Code : " + remittanceMode);

		String sql = null;

		if (remittanceMode.equalsIgnoreCase(Constants.EFTCode)) {
			sql = " SELECT COUNT(*) FROM EX_SRV_PROV_HO_AGENTS A, EX_SRV_PROV_AGENTS B, EX_BENE_AGENT C "
					+ " WHERE  A.AGENT_HO_CODE  = B.AGENT_CODE " + " AND    A.AGENT_HO_CODE  = C.AGENT_CODE " + " AND    C.COUNTRY        = '"
					+ beneCountryCode + "' " // bene country
					+ " AND    C.BNKCOD         = '" + beneBankAlphaCode + "' " // bene
					// Bank
					+ " AND    A.BNKCOD          = '" + routingBankAlphaCode + "' " // routing
					// Bank
					+ " AND    A.COUNTRY        = '" + routingCountryAlphaCode + "' " // routing
					// country
					+ " AND    A.CURCOD        = '" + beneCurrencyAlphaCode + "' " // bene
					// currency
					+ " AND    B.AGENT_STATUS  = '" + agentStatus + "' " // agent
					// status
					+ " AND    A.REMTMOD        = '" + remittanceMode + "' ";// remittance
			// mode
		}

		if (remittanceMode.equalsIgnoreCase(Constants.TTCodeForRemittance)) {
			sql = " SELECT COUNT(*) FROM EX_SRV_PROV_HO_AGENTS A, EX_SRV_PROV_AGENTS B " + " WHERE  A.AGENT_HO_CODE  = B.AGENT_CODE "
					+ " AND    A.BNKCOD          = '" + routingBankAlphaCode + "' " // routing
					// Bank
					+ " AND    A.COUNTRY        = '" + routingCountryAlphaCode + "' " // routing
					// country
					+ " AND    A.CURCOD        = '" + beneCurrencyAlphaCode + "' " // bene
					// currency
					+ " AND    B.AGENT_STATUS  = '" + agentStatus + "' " // agent
					// status
					+ " AND    A.REMTMOD        = '" + remittanceMode + "' ";// remittance
			// mode
		}

		LOGGER.info("EX_SRV_PROV_HO_AGENTS query=" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		List<BigDecimal> rows = query.list();
		for (BigDecimal viewRoutingDetails : rows) {
			if (viewRoutingDetails != null && viewRoutingDetails.compareTo(BigDecimal.ZERO) != 0) {
				checkEFTorTTIcash = true;
				break;
			}
		}

		return checkEFTorTTIcash;
	}

	@Override
	public List<ViewHODirectEFT> fetchAgentforEFTICASHProduct(HashMap<String, String> fetchAgentEFTAndTTForICASH) {

		LOGGER.info("Entered into checkEFTAndTTForICASHProduct Method ");

		String beneCountryCode = fetchAgentEFTAndTTForICASH.get("Bene_Country_Code");
		String beneBankAlphaCode = fetchAgentEFTAndTTForICASH.get("Bene_Bank_Aplha_Code");
		String routingBankAlphaCode = fetchAgentEFTAndTTForICASH.get("Routing_Bank_Aplha_Code");
		String routingCountryAlphaCode = fetchAgentEFTAndTTForICASH.get("Routing_Country_Aplha_Code");
		String beneCurrencyAlphaCode = fetchAgentEFTAndTTForICASH.get("Bene_Currency_Aplha_Code");
		String agentStatus = fetchAgentEFTAndTTForICASH.get("Agent_Status");
		String remittanceMode = fetchAgentEFTAndTTForICASH.get("Remittance_Mode_Code");

		LOGGER.info("Bene_Country_Code : " + beneCountryCode);
		LOGGER.info("Bene_Bank_Aplha_Code : " + beneBankAlphaCode);
		LOGGER.info("Routing_Bank_Aplha_Code : " + routingBankAlphaCode);
		LOGGER.info("Routing_Country_Aplha_Code : " + routingCountryAlphaCode);
		LOGGER.info("Bene_Currency_Aplha_Code : " + beneCurrencyAlphaCode);
		LOGGER.info("Agent_Status : " + agentStatus);
		LOGGER.info("Remittance_Mode_Code : " + remittanceMode);

		String sql = " SELECT * FROM V_HO_DIRECT_EFT " + " WHERE  BENE_BNKCOD  = '" + beneBankAlphaCode + "' " // bene
				// country
				+ " AND    BNKCOD       = '" + routingBankAlphaCode + "' " // bene
				// Bank
				+ " AND    COUNTRY      = '" + routingCountryAlphaCode + "' " // routing
				// Bank
				+ " AND    CURCOD       = '" + beneCurrencyAlphaCode + "' " // routing
				// country
				+ " AND    REMTMOD      = '" + remittanceMode + "'";// remittance
		// mode

		LOGGER.info("V_HO_DIRECT_EFT query=" + sql);
		SQLQuery query = getSession().createSQLQuery(sql).addEntity(ViewHODirectEFT.class);
		List<ViewHODirectEFT> lstViewHODirect = query.list();
		return lstViewHODirect;
	}

	@Override
	public List<ViewHODirectInDirect> fetchAgentforTTICASHProduct(HashMap<String, String> fetchAgentEFTAndTTForICASH) {

		LOGGER.info("Entered into fetchAgentforTTICASHProduct Method ");

		String beneCountryCode = fetchAgentEFTAndTTForICASH.get("Bene_Country_Code");
		String beneBankAlphaCode = fetchAgentEFTAndTTForICASH.get("Bene_Bank_Aplha_Code");
		String routingBankAlphaCode = fetchAgentEFTAndTTForICASH.get("Routing_Bank_Aplha_Code");
		String routingCountryAlphaCode = fetchAgentEFTAndTTForICASH.get("Routing_Country_Aplha_Code");
		String beneCurrencyAlphaCode = fetchAgentEFTAndTTForICASH.get("Bene_Currency_Aplha_Code");
		String agentStatus = fetchAgentEFTAndTTForICASH.get("Agent_Status");
		String remittanceMode = fetchAgentEFTAndTTForICASH.get("Remittance_Mode_Code");

		LOGGER.info("Bene_Country_Code : " + beneCountryCode);
		LOGGER.info("Bene_Bank_Aplha_Code : " + beneBankAlphaCode);
		LOGGER.info("Routing_Bank_Aplha_Code : " + routingBankAlphaCode);
		LOGGER.info("Routing_Country_Aplha_Code : " + routingCountryAlphaCode);
		LOGGER.info("Bene_Currency_Aplha_Code : " + beneCurrencyAlphaCode);
		LOGGER.info("Agent_Status : " + agentStatus);
		LOGGER.info("Remittance_Mode_Code : " + remittanceMode);

		String sql = " SELECT * FROM V_HO_DIRECT_INDIRECT " + " WHERE  BNKCOD       = '" + routingBankAlphaCode + "' " // bene
				// Bank
				+ " AND    COUNTRY      = '" + routingCountryAlphaCode + "' " // routing
				// country
				+ " AND    CURCOD       = '" + beneCurrencyAlphaCode + "' " // bene
				// Currency
				+ " AND    REMTMOD      = '" + remittanceMode + "'"; // remittance
		// mode

		LOGGER.info("V_HO_DIRECT_INDIRECT query=" + sql);
		SQLQuery query = getSession().createSQLQuery(sql).addEntity(ViewHODirectInDirect.class);
		List<ViewHODirectInDirect> lstViewHODirectInDirect = query.list();
		return lstViewHODirectInDirect;
	}

	@Override
	public List<ViewSubAgent> fetchSubAgentsForICash(HashMap<String, String> fetchSubAgentForICASH) {

		LOGGER.info("Entered into fetchAgentforTTICASHProduct Method ");

		String routingBankAlphaCode = fetchSubAgentForICASH.get("Routing_Bank_Aplha_Code");
		String routingCountryAlphaCode = fetchSubAgentForICASH.get("Routing_Country_Aplha_Code");
		String beneCurrencyAlphaCode = fetchSubAgentForICASH.get("Bene_Currency_Aplha_Code");
		String agentStatus = fetchSubAgentForICASH.get("Agent_Status");
		String remittanceMode = fetchSubAgentForICASH.get("Remittance_Mode_Code");
		String agentHOCode = fetchSubAgentForICASH.get("Agent_HO_Code");
		String stateCode = fetchSubAgentForICASH.get("State_Code");

		LOGGER.info("Routing_Bank_Aplha_Code : " + routingBankAlphaCode);
		LOGGER.info("Routing_Country_Aplha_Code : " + routingCountryAlphaCode);
		LOGGER.info("Bene_Currency_Aplha_Code : " + beneCurrencyAlphaCode);
		LOGGER.info("Agent_Status : " + agentStatus);
		LOGGER.info("Remittance_Mode_Code : " + remittanceMode);
		LOGGER.info("Agent_HO_Code : " + agentHOCode);
		LOGGER.info("State_Code : " + stateCode);

		String sql = " SELECT * FROM V_SUB_AGENT " + " WHERE  BNKCOD        = '" + routingBankAlphaCode + "' " // bene
				// Bank
				+ " AND    COUNTRY       = '" + routingCountryAlphaCode + "' " // routing
				// Country
				+ " AND    CURCOD        = '" + beneCurrencyAlphaCode + "' " // bene
				// currency
				+ " AND    REMTMOD       = '" + remittanceMode + "' " // remittance
				// mode
				+ " AND    AGENT_HO_CODE = '" + agentHOCode + "' " // agent HO
				// Code
				+ " AND    (STATE_CODE  =  '" + stateCode + "' OR STATE = 'NO STATE')" // state
				// code
				+ " AND    AGENT_CODE  <>  '" + agentHOCode + "' "; // remittance
		// mode

		LOGGER.info("V_SUB_AGENT query=" + sql);
		SQLQuery query = getSession().createSQLQuery(sql).addEntity(ViewSubAgent.class);
		List<ViewSubAgent> lstViewSubAgent = query.list();
		return lstViewSubAgent;
	}

	@Override
	public List<ViewStatesForICASH> fetchStateForICash(HashMap<String, String> fetchStateForICASH) {
		// TODO Auto-generated method stub

		// String beneCountryCode = fetchStateForICASH.get("Bene_Country_Code");
		String routingCountryCode = fetchStateForICASH.get("Routing_Country_Code");

		LOGGER.info("Routing_Country_Code : " + routingCountryCode);

		String sql = " SELECT * FROM V_STATE " + " WHERE  COUNTRY = '" + routingCountryCode + "' " // routing
				// Country
				+ " ORDER BY STATE "; // remittance mode

		LOGGER.info("V_STATE query=" + sql);
		SQLQuery query = getSession().createSQLQuery(sql).addEntity(ViewStatesForICASH.class);
		List<ViewStatesForICASH> lstViewStates = query.list();

		return lstViewStates;
	}

	@Override
	public String checkICASHProduct(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal collectionDocumentCode,
			BigDecimal collectionDocumentYear, BigDecimal collectionDocumentNo, BigDecimal bankId) throws AMGException {

		String errorMsg = null;

		LOGGER.info("Entered into checkICASHProduct Method ");
		LOGGER.info("applicationCountryId = " + applicationCountryId);
		LOGGER.info("companyId = " + companyId);
		LOGGER.info("collectionDocumentCode = " + collectionDocumentCode);
		LOGGER.info("collectionDocumentYear = " + collectionDocumentYear);
		LOGGER.info("collectionDocumentNo = " + collectionDocumentNo);
		LOGGER.info("ICASH bankId = " + bankId);

		DetachedCriteria dCriteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");

		dCriteria.setFetchMode("remittanceTransaction.applicationCountryId", FetchMode.JOIN);
		dCriteria.createAlias("remittanceTransaction.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("applicationCountryId.countryId", applicationCountryId));

		dCriteria.setFetchMode("remittanceTransaction.companyId", FetchMode.JOIN);
		dCriteria.createAlias("remittanceTransaction.companyId", "companyId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("companyId.companyId", companyId));

		dCriteria.add(Restrictions.eq("remittanceTransaction.collectionDocCode", collectionDocumentCode));
		dCriteria.add(Restrictions.eq("remittanceTransaction.collectionDocFinanceYear", collectionDocumentYear));
		dCriteria.add(Restrictions.eq("remittanceTransaction.collectionDocumentNo", collectionDocumentNo));

		dCriteria.setFetchMode("remittanceTransaction.bankId", FetchMode.JOIN);
		dCriteria.createAlias("remittanceTransaction.bankId", "bankId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankId.bankId", bankId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceTransaction> lstRemitTrnx = (List<RemittanceTransaction>) findAll(dCriteria);
		LOGGER.info("Exited checkICASHProduct Method ");

		if (lstRemitTrnx != null && !lstRemitTrnx.isEmpty()) {

			Connection connection = null;

			try {
				connection = getDataSourceFromHibernateSession();

				CallableStatement cs;

				String call = " { call INSERT_SERVICE_PIN_JAVA (?,?,?,?,?,?) } ";
				cs = connection.prepareCall(call);
				cs.setBigDecimal(1, companyId);
				cs.setBigDecimal(2, collectionDocumentCode);
				cs.setBigDecimal(3, collectionDocumentYear);
				cs.setBigDecimal(4, collectionDocumentNo);
				cs.setBigDecimal(5, bankId);
				cs.registerOutParameter(6, java.sql.Types.VARCHAR);
				cs.execute();
				errorMsg = cs.getString(6);

			} catch (SQLException e) {
				LOGGER.info("Error while calling  function  ");
				String erromsg = "INSERT_SERVICE_PIN_JAVA " + " : " + e.getMessage();
				throw new AMGException(erromsg);
			} catch (Exception e) {
				LOGGER.error("Error Occured in  gettting connection");
				String erromsg = "INSERT_SERVICE_PIN_JAVA " + " : " + e.getMessage();
				throw new AMGException(erromsg);
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					String erromsg = "INSERT_SERVICE_PIN_JAVA " + " : " + e.getMessage();
					throw new AMGException(erromsg);
				} catch (Exception e) {
					LOGGER.error("Error Occured in  gettting connection");
					String erromsg = "INSERT_SERVICE_PIN_JAVA " + " : " + e.getMessage();
					throw new AMGException(erromsg);
				}
			}

		}

		return errorMsg;
	}

	@Override
	public List<RemittanceTransaction> fetchRemittanceTransactionDetails(BigDecimal applicationCountryId, BigDecimal companyId,
			BigDecimal financeYear, BigDecimal documentNumber) {

		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");

		criteria.setFetchMode("remittanceTransaction.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("applicationCountryId.countryId", applicationCountryId));

		criteria.setFetchMode("remittanceTransaction.companyId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.companyId", "companyId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("companyId.companyId", companyId));

		criteria.add(Restrictions.eq("remittanceTransaction.documentFinanceYear", financeYear));
		criteria.add(Restrictions.eq("remittanceTransaction.documentNo", documentNumber));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RemittanceTransaction> objList = (List<RemittanceTransaction>) findAll(criteria);

		return objList;
	}

	@Override
	public List<ProductGroup> fetchProductGroup(BigDecimal bankId, BigDecimal currencyId) {
		LOGGER.info("Entered into fetchProductGroup Method");
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductGroup.class, "productGroup");
		criteria.add(Restrictions.eq("productGroup.bankId", bankId));
		criteria.add(Restrictions.eq("productGroup.currencyId", currencyId));
		criteria.addOrder(Order.asc("productGroup.productGroupSerial"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from the fetchProductGroup Method");
		return (List<ProductGroup>) findAll(criteria);
	}

	@Override
	public void saveProductGroup(List<ProductGroup> lstProdGroup) throws AMGException {
		try {
			for (ProductGroup productGroup : lstProdGroup) {
				getSession().saveOrUpdate(productGroup);
			}
		} catch (Exception e) {
			throw new AMGException(e.getMessage());
		}
	}

	@Override
	public void updateProductGroupIsActive(BigDecimal productGroupId, String status) {

		ProductGroup productGroup = (ProductGroup) getSession().get(ProductGroup.class, productGroupId);
		productGroup.setIsActive(status);
		productGroup.setModifiedBy(sessionStateManage.getUserName());
		productGroup.setModifiedDate(new Date());
		getSession().update(productGroup);

	}

	@Override
	public void updateDeclarationIndicator(BigDecimal tempcolloctionPk) {

		TempCollection tempCollectionObj = (TempCollection) getSession().get(TempCollection.class, tempcolloctionPk);
		tempCollectionObj.setCashDeclarationIndicator(Constants.Yes);
		getSession().update(tempCollectionObj);

	}

	@Override
	public void updateDeclarationIndicatorTotal(BigDecimal tempcolloctionPk) {
		// TODO Auto-generated method stub
		TempCollection tempCollectionObj = (TempCollection) getSession().get(TempCollection.class, tempcolloctionPk);
		tempCollectionObj.setTotalAmountDeclarationIndicator(Constants.Yes);
		getSession().update(tempCollectionObj);
	}

	@Override
	public List<Collect> checkDeclarationIndicatorsInCollectionTable(BigDecimal collctionDocNo, BigDecimal year) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Collect.class, "collect");
		criteria.add(Restrictions.eq("collect.documentNo", collctionDocNo));
		criteria.add(Restrictions.eq("collect.documentFinanceYear", year));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<Collect>) findAll(criteria);

	}

	@Override
	public BigDecimal getLoyaltyPointFromFunction(BigDecimal applicationcountryId, BigDecimal cutomerRefernce) {
		// TODO Auto-generated method stub
		BigDecimal loyaltyPoints = new BigDecimal(0);
		Connection connection = null;

		LOGGER.info("EX_FN_GET_LOYALTY_POINTS P_APPLICATION_COUNTRY_ID :" + applicationcountryId);
		LOGGER.info("EX_FN_GET_LOYALTY_POINTS cutomerRefernce :" + cutomerRefernce);

		try {
			connection = getDataSourceFromHibernateSession();

			CallableStatement cs;

			String call = " { ? = call EX_FN_GET_LOYALTY_POINTS (?,?) } ";
			cs = connection.prepareCall(call);
			cs.registerOutParameter(1, java.sql.Types.INTEGER);
			cs.setBigDecimal(2, applicationcountryId);
			cs.setBigDecimal(3, cutomerRefernce);

			cs.execute();
			loyaltyPoints = new BigDecimal(cs.getInt(1));
			LOGGER.info("getLoyaltyPointFromFunction loyaltyPoints:" + loyaltyPoints);

		} catch (SQLException e) {
			LOGGER.info("Error while calling  function  ");
			String erromsg = "EX_FN_GET_LOYALTY_POINTS " + " : " + e.getMessage();
			LOGGER.info("Error while calling  function  " + erromsg);
			return loyaltyPoints;
		} catch (Exception e) {
			LOGGER.error("Error Occured in  gettting connection");
			String erromsg = "EX_FN_GET_LOYALTY_POINTS " + " : " + e.getMessage();
			LOGGER.info("Error while calling  function  " + erromsg);
			return loyaltyPoints;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				String erromsg = "EX_FN_GET_LOYALTY_POINTS " + " : " + e.getMessage();

			} catch (Exception e) {
				LOGGER.error("Error Occured in  gettting connection");
				String erromsg = "EX_FN_GET_LOYALTY_POINTS " + " : " + e.getMessage();

			}
		}

		return loyaltyPoints;
	}

	@Override
	public List<AdditionalDataDisplayView> tofetchRenderAddtionalDetails(BigDecimal appcountryId, String flexField) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalDataDisplayView.class, "additionalDataDisplayView");
		criteria.add(Restrictions.eq("additionalDataDisplayView.applicationCountryId", appcountryId));
		criteria.add(Restrictions.eq("additionalDataDisplayView.flexField", flexField));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AdditionalDataDisplayView> lstAdditionalDataDisplayViews=(List<AdditionalDataDisplayView>) findAll(criteria);
		return lstAdditionalDataDisplayViews;
	}

	@Override
	public void updateBeneMasterDetails(BigDecimal masterId,String beneHouseNo, String beneFlatNo, String beneStreetNo) {
		BeneficaryMaster beneficaryMaster=(BeneficaryMaster) getSession().get(BeneficaryMaster.class, masterId);
		beneficaryMaster.setBuildingNo(beneHouseNo);
		beneficaryMaster.setFlatNo(beneFlatNo);
		beneficaryMaster.setStreetNo(beneStreetNo);
		getSession().update(beneficaryMaster);

	}

	@Override
	public HashMap<String, String> toValidateBeneAddrMandCheck(HashMap<String, String> inputValuesBeneAdd) throws AMGException {
		Connection connection = null;
		CallableStatement cs = null;
		HashMap<String, String> rtnValues = new HashMap<String, String>();
		LOGGER.info("getRoutingBankSetupDetails :" + inputValuesBeneAdd.toString());
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_BENEFICARY_ADDRESS (?,?,?,?,?,?,?,?,?,?,?) } ";
			cs = connection.prepareCall(call);
			// In Parameters
			cs.setBigDecimal(1, new BigDecimal(inputValuesBeneAdd.get("P_APPLICATION_COUNTRY_ID")));
			cs.setBigDecimal(2, new BigDecimal(inputValuesBeneAdd.get("P_ROUTING_COUNTRY_ID")));
			cs.setBigDecimal(3, new BigDecimal(inputValuesBeneAdd.get("P_ROUTING_BANK_ID")));
			cs.setBigDecimal(4, new BigDecimal(inputValuesBeneAdd.get("P_FOREIGN_CURRENCY_ID")));
			cs.setBigDecimal(5, new BigDecimal(inputValuesBeneAdd.get("P_REMITTANCE_MODE_ID")));
			cs.setBigDecimal(6, new BigDecimal(inputValuesBeneAdd.get("P_DELIVERY_MODE_ID")));
			cs.setString(7, inputValuesBeneAdd.get("P_BENEFICARY_HOUSE_NO"));
			cs.setString(8, inputValuesBeneAdd.get("P_BENEFICARY_FLAT_NO"));
			cs.setString(9, inputValuesBeneAdd.get("P_BENEFICARY_STREET_NO"));
			cs.setBigDecimal(10, new BigDecimal(inputValuesBeneAdd.get("P_BENE_COUNTRY_ID")));
			// Out Parameters
			cs.registerOutParameter(11, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			String pErrorMesg = cs.getString(11);

			rtnValues.put("P_ERROR_MESSAGE", pErrorMesg);
		} catch (SQLException e) {
			String erromsg = "EX_P_BENEFICARY_ADDRESS" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				cs.close();
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Problem Occured While Procedure calling " + e.getMessage());
				String erromsg = "EX_P_BENEFICARY_ADDRESS" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		LOGGER.info("Exited from the toValidateBeneAddrMandCheck method  calling ");
		return rtnValues;
	}

	@Override
	public List<BeneficaryMaster> toFetchbeneHouseDetails(BigDecimal beneficaryMasterSeqId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryMaster.class, "beneficaryMaster");
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficaryMasterSeqId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryMaster> lstBeneficaryMaster=(List<BeneficaryMaster>) findAll(criteria);
		return lstBeneficaryMaster;
	}

	@Override
	public List<SwiftMaster> getSwiftCodeSearch(BigDecimal countryId,
			String swiftCode, String swiftBankName) {


		List<SwiftMaster> lstSwiftMaster = new ArrayList<SwiftMaster>();
		if(swiftCode!=null && !swiftCode.equalsIgnoreCase(""))
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(SwiftMaster.class, "swiftMaster");

			criteria.add(Restrictions.like("swiftMaster.swiftBIC", swiftCode, MatchMode.ANYWHERE));

			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

			lstSwiftMaster= (List<SwiftMaster>) findAll(criteria);

		}
		if(swiftBankName!=null && !swiftBankName.equalsIgnoreCase("") )
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(SwiftMaster.class, "swiftMaster");

			//criteria.add(Restrictions.eq("swiftMaster.bankName", swiftBankName));
			criteria.add(Restrictions.like("swiftMaster.bankName", swiftBankName, MatchMode.ANYWHERE));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

			lstSwiftMaster= (List<SwiftMaster>) findAll(criteria);


		}
		return lstSwiftMaster;

	}

	@Override
	public String getSwiftBankNameFromSwiftBic(String swiftBic) {

		String hqlQuery="select a.bankName from  SwiftMaster a where a.swiftBIC =  :pswiftBIC";

		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("pswiftBIC", swiftBic);

		List<String> lstIdentity =query.list();

		String rtnIdentity="";
		if(lstIdentity!=null && lstIdentity.size()>0)
		{
			rtnIdentity=lstIdentity.get(0);
		}

		return rtnIdentity;
	}



	@Override
	public void deleteBeneAccountRecordPesonal(BigDecimal beneAccountSeqId,
			BigDecimal beneRelationSeqId, String status, String remarks) {
		if (beneAccountSeqId != null) {
			if (status.equalsIgnoreCase(Constants.Yes)) {
				BeneficaryAccount beneAccount = (BeneficaryAccount) getSession().get(BeneficaryAccount.class, beneAccountSeqId);
				beneAccount.setIsActive(Constants.Yes);
				beneAccount.setModifiedBy(sessionStateManage.getUserName());
				beneAccount.setModifiedDate(new Date());
				getSession().update(beneAccount);
			}
		}

		if (beneRelationSeqId != null) {
			BeneficaryRelationship beneRelation = (BeneficaryRelationship) getSession().get(BeneficaryRelationship.class, beneRelationSeqId);
			beneRelation.setIsActive(status);
			beneRelation.setRemarks( remarks);
			beneRelation.setModifiedBy(sessionStateManage.getUserName());
			beneRelation.setModifiedDate(new Date());
			getSession().update(beneRelation);
		}


	}

	/*@Override
	public void saveBeneficiaryEditManager(HashMap<String, String> inputValues,BeneficaryMasterLog beneficaryMasterLog) throws Exception {


		try {

			String beneficiaryAccountSeqId = inputValues.get("BeneficiaryAccountSeqId");
			String beneficaryMasterSeqId = inputValues.get("BeneficaryMasterSeqId");
			String beneficiaryStateId = inputValues.get("BeneficiaryStateId");
			String beneficiaryStateName = inputValues.get("BeneficiaryStateName");
			String beneficiaryAccountType = inputValues.get("BeneficiaryAccountType");
			String beneficiaryDistId = inputValues.get("BeneficiaryDistId");
			String beneficiaryDistName = inputValues.get("BeneficiaryDistName");
			String beneficiaryDistIdValue = inputValues.get("BeneficiaryDistIdValue");
			String beneficiaryCityId = inputValues.get("BeneficiaryCityId");
			String beneficiaryCityName = inputValues.get("BeneficiaryCityName");
			String BeneficiaryCityIdValue = inputValues.get("BeneficiaryDistIdValue");
			String beneficiaryCountryMobilePhoneNumber = inputValues.get("BeneficiaryCountryMobilePhoneNumber");
			String beneficiaryCountryMobilePhoneNumberValue = inputValues.get("BeneficiaryCountryMobilePhoneNumberValue");
			String beneficiaryCountryTelePhoneNumber = inputValues.get("BeneficiaryCountryTelePhoneNumber");
			String beneficiaryCountryTelePhoneNumberValue = inputValues.get("BeneficiaryCountryTelePhoneNumberValue");
			String telePhoneSeqIdValue = inputValues.get("TelePhoneSeqIdValue");
			String telePhoneSeqId = inputValues.get("TelePhoneSeqId");
			String applicationCountryId = inputValues.get("ApplicationCountryId");
			String userId = inputValues.get("UseId");
			String teleCode = inputValues.get("TeleCode");
			String newFirstName = inputValues.get("NewFirstName");
			String newSecondName = inputValues.get("NewSecondName");
			String newThirdName = inputValues.get("NewThirdName");
			String newFourthName = inputValues.get("NewFourthName");
			String newFifthName = inputValues.get("NewFifthName");
			String newFirstNameLocal = inputValues.get("NewFirstNameLocal");
			String newSecondNameLocal = inputValues.get("NewSecondNameLocal");
			String newThirdNameLocal = inputValues.get("NewThirdNameLocal");
			String newFourthNameLocal = inputValues.get("NewFourthNameLocal");
			String beneficaryNameModified = inputValues.get("BeneficaryNameModified");
			//Relation added 24/08/2016
			String relationId=inputValues.get("BeneficiaryRelationId");
			String beneficaryRelSeqId=inputValues.get("BeneRelationSeqId");
			String beneRelationSeqValue=inputValues.get("BeneRelationSeqValue");
			String beneficiaryCountryRelationValue=inputValues.get("BeneficiaryCountryRelationValue");
			String beneficiaryBranch=inputValues.get("BeneficiaryBranch");
			String beneSwiftCode=inputValues.get("BeneSwiftCode");
			String beneficiaryBranchCode=inputValues.get("BeneficiaryBranchCode");
			//ended 24/08/2016

			BeneficaryMaster beneficaryMaster = (BeneficaryMaster) getSession().get(BeneficaryMaster.class, new BigDecimal(beneficaryMasterSeqId));

			BeneficaryAccount beneficaryAccount = (BeneficaryAccount) getSession().get(BeneficaryAccount.class,
					new BigDecimal(beneficiaryAccountSeqId));

			if (beneficiaryStateId != null) {
				StateMaster stateMaster = new StateMaster();
				stateMaster.setStateId(new BigDecimal(beneficiaryStateId));
				beneficaryMaster.setFsStateMaster(stateMaster);
			}

			if (beneficiaryStateName != null) {
				beneficaryMaster.setStateName(beneficiaryStateName);
			}

			if (beneficiaryDistIdValue.equalsIgnoreCase("YES")) {
				if (beneficiaryDistId != null) {
					DistrictMaster districtMaster = new DistrictMaster();
					districtMaster.setDistrictId(new BigDecimal(beneficiaryDistId));
					beneficaryMaster.setFsDistrictMaster(districtMaster);

					if (beneficiaryDistName != null) {
						beneficaryMaster.setDistrictName(beneficiaryDistName);
					}
				}

			}else{

				beneficaryMaster.setFsDistrictMaster(null);
				beneficaryMaster.setDistrictName(beneficiaryDistName);
			}

			if (BeneficiaryCityIdValue.equalsIgnoreCase("YES")) {
				if (beneficiaryCityId != null) {
					CityMaster cityMaster = new CityMaster();
					cityMaster.setCityId(new BigDecimal(beneficiaryCityId));
					beneficaryMaster.setFsCityMaster(cityMaster);

					if (beneficiaryCityName != null) {
						beneficaryMaster.setCityName(beneficiaryCityName);
					}
				}
			}else{


				beneficaryMaster.setFsCityMaster(null);

				beneficaryMaster.setCityName(beneficiaryCityName);
			}

			if (beneficaryNameModified != null && beneficaryNameModified.equalsIgnoreCase(Constants.YES)) {
				beneficaryMaster.setFirstName(newFirstName);
				beneficaryMaster.setSecondName(newSecondName);
				beneficaryMaster.setThirdName(newThirdName);
				beneficaryMaster.setFourthName(newFourthName);
				beneficaryMaster.setFifthName(newFifthName);
				beneficaryMaster.setLocalFirstName(newFirstNameLocal);
				beneficaryMaster.setLocalSecondName(newSecondNameLocal);
				beneficaryMaster.setLocalThirdName(newThirdNameLocal);
				beneficaryMaster.setLocalFourthName(newFourthNameLocal);
			}

			beneficaryMaster.setModifiedDate(new Date());
			beneficaryMaster.setModifiedBy(userId);

			getSession().saveOrUpdate(beneficaryMaster);

			// log for beneficary Master
			if (beneficaryNameModified != null && beneficaryNameModified.equalsIgnoreCase(Constants.YES) && beneficaryMasterLog != null) {
				getSession().save(beneficaryMasterLog);
			}

			if (beneficiaryAccountType != null) {
				beneficaryAccount.setBankAccountTypeId(new BigDecimal(beneficiaryAccountType));
			}
			//added koti 24/08/2016
			BankBranch bankBranch=new BankBranch();
			if(beneficiaryBranch != null){
				bankBranch.setBankBranchId(new BigDecimal(beneficiaryBranch));
			}
			beneficaryAccount.setBankBranch(bankBranch);
			beneficaryAccount.setSwiftCode(beneSwiftCode);

			if(beneficiaryBranchCode != null){
				beneficaryAccount.setBankBranchCode(new BigDecimal(beneficiaryBranchCode));
			}else{
				beneficaryAccount.setBankBranchCode(null);
			}
			//ended 24/08/2016

			if (beneficaryAccount.getBankCode() == null || beneficaryAccount.getBankBranchCode() == null) {
				if (beneficaryAccount.getServicegropupId().getServiceGroupCode().equalsIgnoreCase(Constants.CashCode)) {
					// setting Cash Product - // setting bank code
					if (beneficaryAccount.getBankCode() == null) {
						if (beneficaryAccount.getBank().getBankId() != null && beneficaryAccount.getBankCode() == null) {
							List<ViewRoutingAgents> lstAgentsBanks = fetchAllRoutingAgents(beneficaryAccount.getBeneApplicationCountry()
									.getCountryId(), beneficaryAccount.getBeneficaryCountry().getCountryId(), beneficaryAccount.getServicegropupId()
									.getServiceGroupId(), beneficaryAccount.getBank().getBankId());
							for (ViewRoutingAgents banksview : lstAgentsBanks) {
								if (banksview.getAgentBankId().compareTo(beneficaryAccount.getBank().getBankId()) == 0) {
									beneficaryAccount.setBankCode(banksview.getAgentBankCode());
									break;
								}
							}
						}
					}

					// setting bank branch code
					if (beneficaryAccount.getBankBranchCode() == null) {
						if (beneficaryAccount.getBankBranch().getBankBranchId() != null && beneficaryAccount.getBankBranchCode() == null) {
							List<ViewRoutingAgentLocations> lstAgentLocationForCash = fetchAllRoutingAgentLocations(beneficaryAccount
									.getBeneApplicationCountry().getCountryId(), beneficaryAccount.getBeneficaryCountry().getCountryId(),
									beneficaryAccount.getServicegropupId().getServiceGroupId(), beneficaryAccount.getBank().getBankId(),
									beneficaryAccount.getBankBranch().getBankBranchId());
							for (ViewRoutingAgentLocations banksBranchview : lstAgentLocationForCash) {
								if (banksBranchview.getBankBranchId().compareTo(beneficaryAccount.getBankBranch().getBankBranchId()) == 0) {
									beneficaryAccount.setBankBranchCode(banksBranchview.getBranchCode());
									break;
								}
							}
						}
					}

				} else {
					// setting Banking Channel Product - // setting bank code
					if (beneficaryAccount.getBankCode() == null) {
						if (beneficaryAccount.getBank().getBankId() != null && beneficaryAccount.getBankCode() == null) {
							List<BanksView> lstBankFromView = getBankListFromView(beneficaryAccount.getBeneApplicationCountry().getCountryId(),
									beneficaryAccount.getBeneficaryCountry().getCountryId(), beneficaryAccount.getServicegropupId()
									.getServiceGroupId(), beneficaryAccount.getBank().getBankId());
							for (BanksView banksview : lstBankFromView) {
								if (banksview.getBankId().compareTo(beneficaryAccount.getBank().getBankId()) == 0) {
									beneficaryAccount.setBankCode(banksview.getBankCode());
									break;
								}
							}
						}
					}

					// setting bank branch code
					if (beneficaryAccount.getBankBranchCode() == null) {
						if (beneficaryAccount.getBankBranch().getBankBranchId() != null && beneficaryAccount.getBankBranchCode() == null) {
							List<BankBranchView> lstBankbranchView = getBranchListfromViewwithStateMissing(beneficaryAccount.getBank().getBankId(),
									beneficaryAccount.getBankBranch().getBankBranchId());
							for (BankBranchView bankBranchView : lstBankbranchView) {
								if (bankBranchView.getBankBranchId().compareTo(beneficaryAccount.getBankBranch().getBankBranchId()) == 0) {
									beneficaryAccount.setBankBranchCode(bankBranchView.getBranchCode());
									break;
								}
							}
						}
					}
				}
			}

			beneficaryAccount.setModifiedDate(new Date());
			beneficaryAccount.setModifiedBy(userId);

			getSession().saveOrUpdate(beneficaryAccount);

			if (beneficiaryCountryMobilePhoneNumberValue.equalsIgnoreCase("YES") || beneficiaryCountryTelePhoneNumberValue.equalsIgnoreCase("YES")) {
				if (telePhoneSeqIdValue.equalsIgnoreCase("YES")) {
					BeneficaryContact beneficaryContact = (BeneficaryContact) getSession().get(BeneficaryContact.class,
							new BigDecimal(telePhoneSeqId));
					if (beneficiaryCountryMobilePhoneNumber != null) {
						beneficaryContact.setMobileNumber(new BigDecimal(beneficiaryCountryMobilePhoneNumber));
					} else {
						beneficaryContact.setMobileNumber(null);
					}
					if (beneficiaryCountryTelePhoneNumber != null) {
						beneficaryContact.setTelephoneNumber(beneficiaryCountryTelePhoneNumber);
					} else {
						beneficaryContact.setTelephoneNumber(null);
					}
					if (teleCode != null) {
						beneficaryContact.setCountryTelCode(teleCode);
					}
					beneficaryContact.setModifiedDate(new Date());
					beneficaryContact.setModifiedBy(userId);

					getSession().saveOrUpdate(beneficaryContact);

				} else {
					BeneficaryContact beneficaryContact = new BeneficaryContact();
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(applicationCountryId));
					beneficaryContact.setApplicationCountryId(countryMaster);
					beneficaryContact.setBeneficaryMaster(beneficaryMaster);

					if (teleCode != null) {
						beneficaryContact.setCountryTelCode(teleCode);
					}
					beneficaryContact.setCreatedBy(userId);
					beneficaryContact.setCreatedDate(new Date());
					beneficaryContact.setIsActive(Constants.Yes);
					if (beneficiaryCountryMobilePhoneNumber != null) {
						beneficaryContact.setMobileNumber(new BigDecimal(beneficiaryCountryMobilePhoneNumber));
					} else {
						beneficaryContact.setMobileNumber(null);
					}

					if (beneficiaryCountryTelePhoneNumber != null) {
						beneficaryContact.setTelephoneNumber(beneficiaryCountryTelePhoneNumber);
					} else {
						beneficaryContact.setTelephoneNumber(null);
					}

					getSession().saveOrUpdate(beneficaryContact);

				}
			}

			//bene relation 24/08/2016
			if (beneRelationSeqValue.equalsIgnoreCase("YES")) {
				if(beneficiaryCountryRelationValue.equalsIgnoreCase("YES")){
					BeneficaryRelationship beneficaryRelationship=(BeneficaryRelationship) getSession().get(BeneficaryRelationship.class, new BigDecimal(beneficaryRelSeqId));
					Relations relations=new Relations();
					relations.setRelationsId(new BigDecimal(relationId));
					beneficaryRelationship.setRelations(relations);
					beneficaryRelationship.setModifiedDate(new Date());
					beneficaryRelationship.setModifiedBy(userId);

					getSession().saveOrUpdate(beneficaryRelationship);
				}else{
					BeneficaryRelationship beneficaryRelationship=(BeneficaryRelationship) getSession().get(BeneficaryRelationship.class, new BigDecimal(beneficaryRelSeqId));
					Relations relations=new Relations();
					relations.setRelationsId(new BigDecimal(relationId));
					beneficaryRelationship.setRelations(relations);
					beneficaryRelationship.setModifiedDate(new Date());
					beneficaryRelationship.setModifiedBy(userId);

					getSession().saveOrUpdate(beneficaryRelationship);
				}
			}
			//ended 24/08/2016

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}*/

	@Override
	public String toFetchRoleName(BigDecimal roleId) {
		String roleName=null;
		DetachedCriteria criteria = DetachedCriteria.forClass(RoleMaster.class, "roleMaster");
		criteria.add(Restrictions.eq("roleMaster.roleId", roleId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RoleMaster> lstRoleMasters=(List<RoleMaster>) findAll(criteria);
		if(lstRoleMasters.size()>0){
			roleName=lstRoleMasters.get(0).getRoleName();
		}
		return roleName;
	}

	@Override
	public void saveBeneficiaryContactEdit(HashMap<String, String> inputValues) throws Exception{
		try {

			String beneficiaryAccountSeqId = inputValues.get("BeneficiaryAccountSeqId");
			
			String beneficaryMasterSeqId = inputValues.get("BeneficaryMasterSeqId");
			String beneficiaryCountryMobilePhoneNumber = inputValues.get("BeneficiaryCountryMobilePhoneNumber");
			String beneficiaryCountryMobilePhoneNumberValue = inputValues.get("BeneficiaryCountryMobilePhoneNumberValue");
			String beneficiaryCountryTelePhoneNumber = inputValues.get("BeneficiaryCountryTelePhoneNumber");
			String beneficiaryCountryTelePhoneNumberValue = inputValues.get("BeneficiaryCountryTelePhoneNumberValue");
			String telePhoneSeqIdValue = inputValues.get("TelePhoneSeqIdValue");
			String telePhoneSeqId = inputValues.get("TelePhoneSeqId");
			String applicationCountryId = inputValues.get("ApplicationCountryId");
			String userId = inputValues.get("UseId");
			String teleCode = inputValues.get("TeleCode");

			String newFirstName = inputValues.get("NewFirstName");
			String newSecondName = inputValues.get("NewSecondName");
			String newThirdName = inputValues.get("NewThirdName");
			String newFourthName = inputValues.get("NewFourthName");
			String newFifthName = inputValues.get("NewFifthName");
			String newFirstNameLocal = inputValues.get("NewFirstNameLocal");
			String newSecondNameLocal = inputValues.get("NewSecondNameLocal");
			String newThirdNameLocal = inputValues.get("NewThirdNameLocal");
			String newFourthNameLocal = inputValues.get("NewFourthNameLocal");
			String newFifthNameLocal = inputValues.get("NewFifthNameLocal");
			
			String newSwiftCode = inputValues.get("NewSwiftCode");
			
			String beneficaryNameModified = inputValues.get("BeneficaryNameModified");

			BeneficaryMaster beneficaryMaster = (BeneficaryMaster) getSession().get(BeneficaryMaster.class, new BigDecimal(beneficaryMasterSeqId));
			if (beneficaryNameModified != null && beneficaryNameModified.equalsIgnoreCase(Constants.YES)) {
				beneficaryMaster.setFirstName(newFirstName);
				beneficaryMaster.setSecondName(newSecondName);
				beneficaryMaster.setThirdName(newThirdName);
				beneficaryMaster.setFourthName(newFourthName);
				beneficaryMaster.setFifthName(newFifthName);
				beneficaryMaster.setLocalFirstName(newFirstNameLocal);
				beneficaryMaster.setLocalSecondName(newSecondNameLocal);
				beneficaryMaster.setLocalThirdName(newThirdNameLocal);
				beneficaryMaster.setLocalFourthName(newFourthNameLocal);
				beneficaryMaster.setLocalFifthName(newFifthNameLocal);
			}
			beneficaryMaster.setModifiedDate(new Date());
			beneficaryMaster.setModifiedBy(userId);
			getSession().saveOrUpdate(beneficaryMaster);
			
			BeneficaryAccount beneficaryAccount = (BeneficaryAccount) getSession().get(BeneficaryAccount.class,	new BigDecimal(beneficiaryAccountSeqId));
			beneficaryAccount.setSwiftCode(newSwiftCode);
			getSession().saveOrUpdate(beneficaryAccount);


			if (beneficiaryCountryMobilePhoneNumberValue.equalsIgnoreCase("YES") || beneficiaryCountryTelePhoneNumberValue.equalsIgnoreCase("YES")) {
				if (telePhoneSeqIdValue.equalsIgnoreCase("YES")) {
					BeneficaryContact beneficaryContact = (BeneficaryContact) getSession().get(BeneficaryContact.class,
							new BigDecimal(telePhoneSeqId));
					if (beneficiaryCountryMobilePhoneNumber != null) {
						beneficaryContact.setMobileNumber(new BigDecimal(beneficiaryCountryMobilePhoneNumber));
					} else {
						beneficaryContact.setMobileNumber(null);
					}
					if (beneficiaryCountryTelePhoneNumber != null) {
						beneficaryContact.setTelephoneNumber(beneficiaryCountryTelePhoneNumber);
					} else {
						beneficaryContact.setTelephoneNumber(null);
					}
					if (teleCode != null) {
						beneficaryContact.setCountryTelCode(teleCode);
					}else{
						beneficaryContact.setCountryTelCode(teleCode);
					}
					beneficaryContact.setModifiedDate(new Date());
					beneficaryContact.setModifiedBy(userId);

					getSession().saveOrUpdate(beneficaryContact);
				} else {
					BeneficaryContact beneficaryContact = new BeneficaryContact();
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(applicationCountryId));
					beneficaryContact.setApplicationCountryId(countryMaster);
					beneficaryContact.setBeneficaryMaster(beneficaryMaster);
					if (teleCode != null) {
						beneficaryContact.setCountryTelCode(teleCode);
					}else{
						beneficaryContact.setCountryTelCode(teleCode);
					}
					beneficaryContact.setCreatedBy(userId);
					beneficaryContact.setCreatedDate(new Date());
					beneficaryContact.setIsActive(Constants.Yes);
					if (beneficiaryCountryMobilePhoneNumber != null) {
						beneficaryContact.setMobileNumber(new BigDecimal(beneficiaryCountryMobilePhoneNumber));
					} else {
						beneficaryContact.setMobileNumber(null);
					}
					if (beneficiaryCountryTelePhoneNumber != null) {
						beneficaryContact.setTelephoneNumber(beneficiaryCountryTelePhoneNumber);
					} else {
						beneficaryContact.setTelephoneNumber(null);
					}

					getSession().saveOrUpdate(beneficaryContact);

				}
			}

		} catch (Exception e) {

			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void saveBeneficiaryEditManager(HashMap<String, String> inputValues) throws Exception{


		try {

			String beneficiaryAccountSeqId = inputValues.get("BeneficiaryAccountSeqId");
			String beneficaryMasterSeqId = inputValues.get("BeneficaryMasterSeqId");
			String beneficiaryCountryId = inputValues.get("BeneficiaryCountryId");
			//String beneficiaryCountryName = inputValues.get("BeneficiaryCountryName");
			String beneficiaryStateId = inputValues.get("BeneficiaryStateId");
			String beneficiaryStateName = inputValues.get("BeneficiaryStateName");
			String beneficiaryAccountType = inputValues.get("BeneficiaryAccountType");
			String beneficiaryDistId = inputValues.get("BeneficiaryDistId");
			String beneficiaryDistName = inputValues.get("BeneficiaryDistName");
			String beneficiaryDistIdValue = inputValues.get("BeneficiaryDistIdValue");
			String beneficiaryCityId = inputValues.get("BeneficiaryCityId");
			String beneficiaryCityName = inputValues.get("BeneficiaryCityName");
			String BeneficiaryCityIdValue = inputValues.get("BeneficiaryDistIdValue");
			String beneficiaryCountryMobilePhoneNumber = inputValues.get("BeneficiaryCountryMobilePhoneNumber");
			String beneficiaryCountryMobilePhoneNumberValue = inputValues.get("BeneficiaryCountryMobilePhoneNumberValue");
			String beneficiaryCountryTelePhoneNumber = inputValues.get("BeneficiaryCountryTelePhoneNumber");
			String beneficiaryCountryTelePhoneNumberValue = inputValues.get("BeneficiaryCountryTelePhoneNumberValue");
			String telePhoneSeqIdValue = inputValues.get("TelePhoneSeqIdValue");
			String telePhoneSeqId = inputValues.get("TelePhoneSeqId");
			String applicationCountryId = inputValues.get("ApplicationCountryId");
			String userId = inputValues.get("UseId");
			String teleCode = inputValues.get("TeleCode");
			String newFirstName = inputValues.get("NewFirstName");
			String newSecondName = inputValues.get("NewSecondName");
			String newThirdName = inputValues.get("NewThirdName");
			String newFourthName = inputValues.get("NewFourthName");
			String newFifthName = inputValues.get("NewFifthName");
			String newFirstNameLocal = inputValues.get("NewFirstNameLocal");
			String newSecondNameLocal = inputValues.get("NewSecondNameLocal");
			String newThirdNameLocal = inputValues.get("NewThirdNameLocal");
			String newFourthNameLocal = inputValues.get("NewFourthNameLocal");
			String newFifthNameLocal = inputValues.get("NewFifthNameLocal");
			String newSwiftCode = inputValues.get("NewSwiftCode");
			
			String beneficaryNameModified = inputValues.get("BeneficaryNameModified");
			//Relation added 24/08/2016
			String relationId=inputValues.get("BeneficiaryRelationId");
			String beneficaryRelSeqId=inputValues.get("BeneRelationSeqId");
			String beneRelationSeqValue=inputValues.get("BeneRelationSeqValue");
			String beneficiaryCountryRelationValue=inputValues.get("BeneficiaryCountryRelationValue");
			String beneficiaryBranch=inputValues.get("BeneficiaryBranch");
			String beneSwiftCode=inputValues.get("BeneSwiftCode");
			String beneficiaryBranchCode=inputValues.get("BeneficiaryBranchCode");
			String nationality = inputValues.get("Nationality");
			//ended 24/08/2016

			BeneficaryMaster beneficaryMaster = (BeneficaryMaster) getSession().get(BeneficaryMaster.class, new BigDecimal(beneficaryMasterSeqId));

			BeneficaryAccount beneficaryAccount = (BeneficaryAccount) getSession().get(BeneficaryAccount.class,
					new BigDecimal(beneficiaryAccountSeqId));
			
			if(beneficiaryCountryId != null){
				CountryMaster benecountryMaster = new CountryMaster();
				benecountryMaster.setCountryId(new BigDecimal(beneficiaryCountryId));
				beneficaryMaster.setFsCountryMaster(benecountryMaster);
			}
			
			if (beneficiaryStateId != null) {
				StateMaster stateMaster = new StateMaster();
				stateMaster.setStateId(new BigDecimal(beneficiaryStateId));
				beneficaryMaster.setFsStateMaster(stateMaster);
			}

			if (beneficiaryStateName != null) {
				beneficaryMaster.setStateName(beneficiaryStateName);
			}

			if (beneficiaryDistIdValue.equalsIgnoreCase("YES")) {
				if (beneficiaryDistId != null) {
					DistrictMaster districtMaster = new DistrictMaster();
					districtMaster.setDistrictId(new BigDecimal(beneficiaryDistId));
					beneficaryMaster.setFsDistrictMaster(districtMaster);

					if (beneficiaryDistName != null) {
						beneficaryMaster.setDistrictName(beneficiaryDistName);
					}
				}else{
					beneficaryMaster.setFsDistrictMaster(null);
					beneficaryMaster.setDistrictName(null);
				}
			}else{
				beneficaryMaster.setFsDistrictMaster(null);
				beneficaryMaster.setDistrictName(null);
			}

			if (BeneficiaryCityIdValue.equalsIgnoreCase("YES")) {
				if (beneficiaryCityId != null) {
					CityMaster cityMaster = new CityMaster();
					cityMaster.setCityId(new BigDecimal(beneficiaryCityId));
					beneficaryMaster.setFsCityMaster(cityMaster);

					if (beneficiaryCityName != null) {
						beneficaryMaster.setCityName(beneficiaryCityName);
					}
				}else{
					beneficaryMaster.setFsCityMaster(null);
					beneficaryMaster.setCityName(null);
				}
			}else{
				beneficaryMaster.setFsCityMaster(null);
				beneficaryMaster.setCityName(null);
			}

			if (beneficaryNameModified != null && beneficaryNameModified.equalsIgnoreCase(Constants.YES)) {
				beneficaryMaster.setFirstName(newFirstName);
				beneficaryMaster.setSecondName(newSecondName);
				beneficaryMaster.setThirdName(newThirdName);
				beneficaryMaster.setFourthName(newFourthName);
				beneficaryMaster.setFifthName(newFifthName);
				beneficaryMaster.setLocalFirstName(newFirstNameLocal);
				beneficaryMaster.setLocalSecondName(newSecondNameLocal);
				beneficaryMaster.setLocalThirdName(newThirdNameLocal);
				beneficaryMaster.setLocalFourthName(newFourthNameLocal);
				beneficaryMaster.setLocalFifthName(newFifthNameLocal);
			}

			beneficaryMaster.setNationality(nationality);
			beneficaryMaster.setModifiedDate(new Date());
			beneficaryMaster.setModifiedBy(userId);

			getSession().saveOrUpdate(beneficaryMaster);


			if (beneficiaryAccountType != null) {
				beneficaryAccount.setBankAccountTypeId(new BigDecimal(beneficiaryAccountType));
			}
			//added koti 24/08/2016
			BankBranch bankBranch=new BankBranch();
			if(beneficiaryBranch != null){
				bankBranch.setBankBranchId(new BigDecimal(beneficiaryBranch));
			}
			beneficaryAccount.setBankBranch(bankBranch);
			beneficaryAccount.setSwiftCode(beneSwiftCode);

			if(beneficiaryBranchCode != null){
				beneficaryAccount.setBankBranchCode(new BigDecimal(beneficiaryBranchCode));
			}else{
				beneficaryAccount.setBankBranchCode(null);
			}
			//ended 24/08/2016

			if (beneficaryAccount.getBankCode() == null || beneficaryAccount.getBankBranchCode() == null) {
				if (beneficaryAccount.getServicegropupId().getServiceGroupCode().equalsIgnoreCase(Constants.CashCode)) {
					// setting Cash Product - // setting bank code
					if (beneficaryAccount.getBankCode() == null) {
						if (beneficaryAccount.getBank().getBankId() != null && beneficaryAccount.getBankCode() == null) {
							List<ViewRoutingAgents> lstAgentsBanks = fetchAllRoutingAgents(beneficaryAccount.getBeneApplicationCountry()
									.getCountryId(), beneficaryAccount.getBeneficaryCountry().getCountryId(), beneficaryAccount.getServicegropupId()
									.getServiceGroupId(), beneficaryAccount.getBank().getBankId());
							for (ViewRoutingAgents banksview : lstAgentsBanks) {
								if (banksview.getAgentBankId().compareTo(beneficaryAccount.getBank().getBankId()) == 0) {
									beneficaryAccount.setBankCode(banksview.getAgentBankCode());
									break;
								}
							}
						}
					}

					// setting bank branch code
					if (beneficaryAccount.getBankBranchCode() == null) {
						if (beneficaryAccount.getBankBranch().getBankBranchId() != null && beneficaryAccount.getBankBranchCode() == null) {
							List<ViewRoutingAgentLocations> lstAgentLocationForCash = fetchAllRoutingAgentLocations(beneficaryAccount
									.getBeneApplicationCountry().getCountryId(), beneficaryAccount.getBeneficaryCountry().getCountryId(),
									beneficaryAccount.getServicegropupId().getServiceGroupId(), beneficaryAccount.getBank().getBankId(),
									beneficaryAccount.getBankBranch().getBankBranchId());
							for (ViewRoutingAgentLocations banksBranchview : lstAgentLocationForCash) {
								if (banksBranchview.getBankBranchId().compareTo(beneficaryAccount.getBankBranch().getBankBranchId()) == 0) {
									beneficaryAccount.setBankBranchCode(banksBranchview.getBranchCode());
									break;
								}
							}
						}
					}

				} else {
					// setting Banking Channel Product - // setting bank code
					if (beneficaryAccount.getBankCode() == null) {
						if (beneficaryAccount.getBank().getBankId() != null && beneficaryAccount.getBankCode() == null) {
							List<BanksView> lstBankFromView = getBankListFromView(beneficaryAccount.getBeneApplicationCountry().getCountryId(),
									beneficaryAccount.getBeneficaryCountry().getCountryId(), beneficaryAccount.getServicegropupId()
									.getServiceGroupId(), beneficaryAccount.getBank().getBankId());
							for (BanksView banksview : lstBankFromView) {
								if (banksview.getBankId().compareTo(beneficaryAccount.getBank().getBankId()) == 0) {
									beneficaryAccount.setBankCode(banksview.getBankCode());
									break;
								}
							}
						}
					}

					// setting bank branch code
					if (beneficaryAccount.getBankBranchCode() == null) {
						if (beneficaryAccount.getBankBranch().getBankBranchId() != null && beneficaryAccount.getBankBranchCode() == null) {
							List<BankBranchView> lstBankbranchView = getBranchListfromViewwithStateMissing(beneficaryAccount.getBank().getBankId(),
									beneficaryAccount.getBankBranch().getBankBranchId());
							for (BankBranchView bankBranchView : lstBankbranchView) {
								if (bankBranchView.getBankBranchId().compareTo(beneficaryAccount.getBankBranch().getBankBranchId()) == 0) {
									beneficaryAccount.setBankBranchCode(bankBranchView.getBranchCode());
									break;
								}
							}
						}
					}
				}
			}

			beneficaryAccount.setModifiedDate(new Date());
			beneficaryAccount.setModifiedBy(userId);
			beneficaryAccount.setSwiftCode(newSwiftCode);

			getSession().saveOrUpdate(beneficaryAccount);

			if (beneficiaryCountryMobilePhoneNumberValue.equalsIgnoreCase("YES") || beneficiaryCountryTelePhoneNumberValue.equalsIgnoreCase("YES")) {
				if (telePhoneSeqIdValue.equalsIgnoreCase("YES")) {
					BeneficaryContact beneficaryContact = (BeneficaryContact) getSession().get(BeneficaryContact.class,
							new BigDecimal(telePhoneSeqId));
					if (beneficiaryCountryMobilePhoneNumber != null) {
						beneficaryContact.setMobileNumber(new BigDecimal(beneficiaryCountryMobilePhoneNumber));
					} else {
						beneficaryContact.setMobileNumber(null);
					}
					if (beneficiaryCountryTelePhoneNumber != null) {
						beneficaryContact.setTelephoneNumber(beneficiaryCountryTelePhoneNumber);
					} else {
						beneficaryContact.setTelephoneNumber(null);
					}
					if (teleCode != null) {
						beneficaryContact.setCountryTelCode(teleCode);
					}else{
						beneficaryContact.setCountryTelCode(teleCode);
					}
					beneficaryContact.setModifiedDate(new Date());
					beneficaryContact.setModifiedBy(userId);

					getSession().saveOrUpdate(beneficaryContact);

				} else {
					BeneficaryContact beneficaryContact = new BeneficaryContact();
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(applicationCountryId));
					beneficaryContact.setApplicationCountryId(countryMaster);
					beneficaryContact.setBeneficaryMaster(beneficaryMaster);

					if (teleCode != null) {
						beneficaryContact.setCountryTelCode(teleCode);
					}else{
						beneficaryContact.setCountryTelCode(teleCode);
					}

					beneficaryContact.setCreatedBy(userId);
					beneficaryContact.setCreatedDate(new Date());
					beneficaryContact.setIsActive(Constants.Yes);
					if (beneficiaryCountryMobilePhoneNumber != null) {
						beneficaryContact.setMobileNumber(new BigDecimal(beneficiaryCountryMobilePhoneNumber));
					} else {
						beneficaryContact.setMobileNumber(null);
					}

					if (beneficiaryCountryTelePhoneNumber != null) {
						beneficaryContact.setTelephoneNumber(beneficiaryCountryTelePhoneNumber);
					} else {
						beneficaryContact.setTelephoneNumber(null);
					}

					getSession().saveOrUpdate(beneficaryContact);

				}
			}

			//bene relation 24/08/2016
			if (beneRelationSeqValue.equalsIgnoreCase("YES")) {
				if(beneficiaryCountryRelationValue.equalsIgnoreCase("YES")){
					BeneficaryRelationship beneficaryRelationship=(BeneficaryRelationship) getSession().get(BeneficaryRelationship.class, new BigDecimal(beneficaryRelSeqId));
					Relations relations=new Relations();
					relations.setRelationsId(new BigDecimal(relationId));
					beneficaryRelationship.setRelations(relations);
					beneficaryRelationship.setModifiedDate(new Date());
					beneficaryRelationship.setModifiedBy(userId);

					getSession().saveOrUpdate(beneficaryRelationship);
				}else{
					BeneficaryRelationship beneficaryRelationship=(BeneficaryRelationship) getSession().get(BeneficaryRelationship.class, new BigDecimal(beneficaryRelSeqId));
					Relations relations=new Relations();
					relations.setRelationsId(new BigDecimal(relationId));
					beneficaryRelationship.setRelations(relations);
					beneficaryRelationship.setModifiedDate(new Date());
					beneficaryRelationship.setModifiedBy(userId);

					getSession().saveOrUpdate(beneficaryRelationship);
				}
			}
			//ended 24/08/2016

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	@Override
	public void saveBeneficiaryEdit(HashMap<String, String> inputValues)throws Exception {



		try {

			String beneficiaryAccountSeqId = inputValues.get("BeneficiaryAccountSeqId");
			String beneficaryMasterSeqId = inputValues.get("BeneficaryMasterSeqId");
			String beneficiaryStateId = inputValues.get("BeneficiaryStateId");
			String beneficiaryStateName = inputValues.get("BeneficiaryStateName");
			String beneficiaryAccountType = inputValues.get("BeneficiaryAccountType");
			String beneficiaryDistId = inputValues.get("BeneficiaryDistId");
			String beneficiaryDistName = inputValues.get("BeneficiaryDistName");
			String beneficiaryDistIdValue = inputValues.get("BeneficiaryDistIdValue");
			String beneficiaryCityId = inputValues.get("BeneficiaryCityId");
			String beneficiaryCityName = inputValues.get("BeneficiaryCityName");
			String BeneficiaryCityIdValue = inputValues.get("BeneficiaryDistIdValue");
			String beneficiaryCountryMobilePhoneNumber = inputValues.get("BeneficiaryCountryMobilePhoneNumber");
			String beneficiaryCountryMobilePhoneNumberValue = inputValues.get("BeneficiaryCountryMobilePhoneNumberValue");
			String beneficiaryCountryTelePhoneNumber = inputValues.get("BeneficiaryCountryTelePhoneNumber");
			String beneficiaryCountryTelePhoneNumberValue = inputValues.get("BeneficiaryCountryTelePhoneNumberValue");
			String telePhoneSeqIdValue = inputValues.get("TelePhoneSeqIdValue");
			String telePhoneSeqId = inputValues.get("TelePhoneSeqId");
			String applicationCountryId = inputValues.get("ApplicationCountryId");
			String userId = inputValues.get("UseId");
			String teleCode = inputValues.get("TeleCode");
			String newFirstName = inputValues.get("NewFirstName");
			String newSecondName = inputValues.get("NewSecondName");
			String newThirdName = inputValues.get("NewThirdName");
			String newFourthName = inputValues.get("NewFourthName");
			String newFifthName = inputValues.get("NewFifthName");
			String newFirstNameLocal = inputValues.get("NewFirstNameLocal");
			String newSecondNameLocal = inputValues.get("NewSecondNameLocal");
			String newThirdNameLocal = inputValues.get("NewThirdNameLocal");
			String newFourthNameLocal = inputValues.get("NewFourthNameLocal");
			String newFifthNameLocal = inputValues.get("NewFifthNameLocal");
			String beneficaryNameModified = inputValues.get("BeneficaryNameModified");

			BeneficaryMaster beneficaryMaster = (BeneficaryMaster) getSession().get(BeneficaryMaster.class, new BigDecimal(beneficaryMasterSeqId));

			BeneficaryAccount beneficaryAccount = (BeneficaryAccount) getSession().get(BeneficaryAccount.class,
					new BigDecimal(beneficiaryAccountSeqId));

			if (beneficiaryStateId != null) {
				StateMaster stateMaster = new StateMaster();
				stateMaster.setStateId(new BigDecimal(beneficiaryStateId));
				beneficaryMaster.setFsStateMaster(stateMaster);
			}

			if (beneficiaryStateName != null) {
				beneficaryMaster.setStateName(beneficiaryStateName);
			}

			if (beneficiaryDistIdValue.equalsIgnoreCase("YES")) {
				if (beneficiaryDistId != null) {
					DistrictMaster districtMaster = new DistrictMaster();
					districtMaster.setDistrictId(new BigDecimal(beneficiaryDistId));
					beneficaryMaster.setFsDistrictMaster(districtMaster);

					if (beneficiaryDistName != null) {
						beneficaryMaster.setDistrictName(beneficiaryDistName);
					}
				}

			}

			if (BeneficiaryCityIdValue.equalsIgnoreCase("YES")) {
				if (beneficiaryCityId != null) {
					CityMaster cityMaster = new CityMaster();
					cityMaster.setCityId(new BigDecimal(beneficiaryCityId));
					beneficaryMaster.setFsCityMaster(cityMaster);

					if (beneficiaryCityName != null) {
						beneficaryMaster.setCityName(beneficiaryCityName);
					}
				}
			}

			if (beneficaryNameModified != null && beneficaryNameModified.equalsIgnoreCase(Constants.YES)) {
				beneficaryMaster.setFirstName(newFirstName);
				beneficaryMaster.setSecondName(newSecondName);
				beneficaryMaster.setThirdName(newThirdName);
				beneficaryMaster.setFourthName(newFourthName);
				beneficaryMaster.setFifthName(newFifthName);
				beneficaryMaster.setLocalFirstName(newFirstNameLocal);
				beneficaryMaster.setLocalSecondName(newSecondNameLocal);
				beneficaryMaster.setLocalThirdName(newThirdNameLocal);
				beneficaryMaster.setLocalFourthName(newFourthNameLocal);
				beneficaryMaster.setLocalFifthName(newFifthNameLocal);
			}

			beneficaryMaster.setModifiedDate(new Date());
			beneficaryMaster.setModifiedBy(userId);

			getSession().saveOrUpdate(beneficaryMaster);


			if (beneficiaryAccountType != null) {
				beneficaryAccount.setBankAccountTypeId(new BigDecimal(beneficiaryAccountType));
			}

			if (beneficaryAccount.getBankCode() == null || beneficaryAccount.getBankBranchCode() == null) {
				if (beneficaryAccount.getServicegropupId().getServiceGroupCode().equalsIgnoreCase(Constants.CashCode)) {
					// setting Cash Product - // setting bank code
					if (beneficaryAccount.getBankCode() == null) {
						if (beneficaryAccount.getBank().getBankId() != null && beneficaryAccount.getBankCode() == null) {
							List<ViewRoutingAgents> lstAgentsBanks = fetchAllRoutingAgents(beneficaryAccount.getBeneApplicationCountry()
									.getCountryId(), beneficaryAccount.getBeneficaryCountry().getCountryId(), beneficaryAccount.getServicegropupId()
									.getServiceGroupId(), beneficaryAccount.getBank().getBankId());
							for (ViewRoutingAgents banksview : lstAgentsBanks) {
								if (banksview.getAgentBankId().compareTo(beneficaryAccount.getBank().getBankId()) == 0) {
									beneficaryAccount.setBankCode(banksview.getAgentBankCode());
									break;
								}
							}
						}
					}

					// setting bank branch code
					if (beneficaryAccount.getBankBranchCode() == null) {
						if (beneficaryAccount.getBankBranch().getBankBranchId() != null && beneficaryAccount.getBankBranchCode() == null) {
							List<ViewRoutingAgentLocations> lstAgentLocationForCash = fetchAllRoutingAgentLocations(beneficaryAccount
									.getBeneApplicationCountry().getCountryId(), beneficaryAccount.getBeneficaryCountry().getCountryId(),
									beneficaryAccount.getServicegropupId().getServiceGroupId(), beneficaryAccount.getBank().getBankId(),
									beneficaryAccount.getBankBranch().getBankBranchId());
							for (ViewRoutingAgentLocations banksBranchview : lstAgentLocationForCash) {
								if (banksBranchview.getBankBranchId().compareTo(beneficaryAccount.getBankBranch().getBankBranchId()) == 0) {
									beneficaryAccount.setBankBranchCode(banksBranchview.getBranchCode());
									break;
								}
							}
						}
					}

				} else {
					// setting Banking Channel Product - // setting bank code
					if (beneficaryAccount.getBankCode() == null) {
						if (beneficaryAccount.getBank().getBankId() != null && beneficaryAccount.getBankCode() == null) {
							List<BanksView> lstBankFromView = getBankListFromView(beneficaryAccount.getBeneApplicationCountry().getCountryId(),
									beneficaryAccount.getBeneficaryCountry().getCountryId(), beneficaryAccount.getServicegropupId()
									.getServiceGroupId(), beneficaryAccount.getBank().getBankId());
							for (BanksView banksview : lstBankFromView) {
								if (banksview.getBankId().compareTo(beneficaryAccount.getBank().getBankId()) == 0) {
									beneficaryAccount.setBankCode(banksview.getBankCode());
									break;
								}
							}
						}
					}

					// setting bank branch code
					if (beneficaryAccount.getBankBranchCode() == null) {
						if (beneficaryAccount.getBankBranch().getBankBranchId() != null && beneficaryAccount.getBankBranchCode() == null) {
							List<BankBranchView> lstBankbranchView = getBranchListfromViewwithStateMissing(beneficaryAccount.getBank().getBankId(),
									beneficaryAccount.getBankBranch().getBankBranchId());
							for (BankBranchView bankBranchView : lstBankbranchView) {
								if (bankBranchView.getBankBranchId().compareTo(beneficaryAccount.getBankBranch().getBankBranchId()) == 0) {
									beneficaryAccount.setBankBranchCode(bankBranchView.getBranchCode());
									break;
								}
							}
						}
					}
				}
			}

			beneficaryAccount.setModifiedDate(new Date());
			beneficaryAccount.setModifiedBy(userId);

			getSession().saveOrUpdate(beneficaryAccount);

			if (beneficiaryCountryMobilePhoneNumberValue.equalsIgnoreCase("YES") || beneficiaryCountryTelePhoneNumberValue.equalsIgnoreCase("YES")) {
				if (telePhoneSeqIdValue.equalsIgnoreCase("YES")) {
					BeneficaryContact beneficaryContact = (BeneficaryContact) getSession().get(BeneficaryContact.class,
							new BigDecimal(telePhoneSeqId));
					if (beneficiaryCountryMobilePhoneNumber != null) {
						beneficaryContact.setMobileNumber(new BigDecimal(beneficiaryCountryMobilePhoneNumber));
					} else {
						beneficaryContact.setMobileNumber(null);
					}
					if (beneficiaryCountryTelePhoneNumber != null) {
						beneficaryContact.setTelephoneNumber(beneficiaryCountryTelePhoneNumber);
					} else {
						beneficaryContact.setTelephoneNumber(null);
					}
					if (teleCode != null) {
						beneficaryContact.setCountryTelCode(teleCode);
					}else{
						beneficaryContact.setCountryTelCode(teleCode);
					}
					beneficaryContact.setModifiedDate(new Date());
					beneficaryContact.setModifiedBy(userId);

					getSession().saveOrUpdate(beneficaryContact);

				} else {
					BeneficaryContact beneficaryContact = new BeneficaryContact();
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(applicationCountryId));
					beneficaryContact.setApplicationCountryId(countryMaster);
					beneficaryContact.setBeneficaryMaster(beneficaryMaster);

					if (teleCode != null) {
						beneficaryContact.setCountryTelCode(teleCode);
					}else{
						beneficaryContact.setCountryTelCode(teleCode);
					}
					beneficaryContact.setCreatedBy(userId);
					beneficaryContact.setCreatedDate(new Date());
					beneficaryContact.setIsActive(Constants.Yes);
					if (beneficiaryCountryMobilePhoneNumber != null) {
						beneficaryContact.setMobileNumber(new BigDecimal(beneficiaryCountryMobilePhoneNumber));
					} else {
						beneficaryContact.setMobileNumber(null);
					}

					if (beneficiaryCountryTelePhoneNumber != null) {
						beneficaryContact.setTelephoneNumber(beneficiaryCountryTelePhoneNumber);
					} else {
						beneficaryContact.setTelephoneNumber(null);
					}

					getSession().saveOrUpdate(beneficaryContact);

				}
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<CustomerIdProof> getCustomerDetailsFromCustomerId(String civilId, BigDecimal cardType, BigDecimal customerId) {
		LOGGER.info("Entered into getCustomerDetails(String customerId, BigDecimal cardType) method ");
		LOGGER.info("CivilId =" + civilId);
		LOGGER.info("cardType =" + cardType);
		LOGGER.info("customerId =" + customerId);
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		criteria.add(Restrictions.eq("customerIdProof.identityInt", civilId));
		criteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId", cardType));
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.addOrder(Order.desc("customerIdProof.creationDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CustomerIdProof> lstCustomerIdPrrof = (List<CustomerIdProof>) findAll(criteria);
		LOGGER.info("Exited from getCustomerDetails(String customerId, BigDecimal cardType) method");
		return lstCustomerIdPrrof;
	}

	public List<AllBeneficiaryView> getAllBeneficiaryt(BigDecimal cutomerNo,BigDecimal beneCountryId){
		DetachedCriteria criteria = DetachedCriteria.forClass(AllBeneficiaryView.class, "allBeneficiaryView");
		criteria.add(Restrictions.eq("allBeneficiaryView.customerId", cutomerNo));
		if (beneCountryId != null) {
			criteria.add(Restrictions.eq("allBeneficiaryView.beneCountry", beneCountryId));
		}
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AllBeneficiaryView> lstBeneView = (List<AllBeneficiaryView>) findAll(criteria);
		return lstBeneView;
	}
	
	public Boolean checkCorporateBranchForSignature(BigDecimal countryBranchId){
		Boolean corporateSigCheck= true;
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		criteria.add(Restrictions.eq("countryBranch.countryBranchId", countryBranchId));
		criteria.add(Restrictions.eq("countryBranch.isActive", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CountryBranch> lstBeneView = (List<CountryBranch>) findAll(criteria);
		if(lstBeneView!=null && lstBeneView.size()>0){
			if(lstBeneView.get(0).getDigitalSignInd()!=null){
				if(lstBeneView.get(0).getDigitalSignInd().equalsIgnoreCase(Constants.Yes)){
					corporateSigCheck = false;
				}
			}
		}
		return corporateSigCheck;
	}

	@SuppressWarnings("deprecation")
	@Override
	public HashMap<String,Object> fetchTempCollection(BigDecimal documentCode, BigDecimal cutomerId, BigDecimal trnxAmt) {

		HashMap<String,Object> lstTempDt = new HashMap<String, Object>();

		DetachedCriteria criteria = DetachedCriteria.forClass(TempCollection.class, "tempCollection");

		criteria.add(Restrictions.eq("tempCollection.documentCode", documentCode));

		criteria.setFetchMode("tempCollection.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("tempCollection.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", cutomerId));

		criteria.add(Restrictions.eq("tempCollection.netAmount", trnxAmt));

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

		criteria.add(Restrictions.between("tempCollection.createdDate", fromDate, toDate));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TempCollection> lsttempCollect = (List<TempCollection>) findAll(criteria);

		lstTempDt.put("TempCollect", lsttempCollect);

		if(lsttempCollect != null && lsttempCollect.size() != 0){
			TempCollection tempCollection = lsttempCollect.get(0);

			// TempCollectDetail Table
			DetachedCriteria criteria1 = DetachedCriteria.forClass(TempCollectDetail.class, "tempCollectDetail");

			criteria1.setFetchMode("tempCollectDetail.cashCollectionId", FetchMode.JOIN);
			criteria1.createAlias("tempCollectDetail.cashCollectionId", "cashCollectionId", JoinType.INNER_JOIN);

			criteria1.add(Restrictions.eq("cashCollectionId.collectionId", tempCollection.getCollectionId()));

			criteria1.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<TempCollectDetail> lsttempCollectDt = (List<TempCollectDetail>) findAll(criteria1);

			lstTempDt.put("TempCollectDT", lsttempCollectDt);

			// TempForeignCurrencyAdjust Table
			DetachedCriteria criteria2 = DetachedCriteria.forClass(TempForeignCurrencyAdjust.class, "tempForeignCurrencyAdjust");

			criteria2.setFetchMode("tempForeignCurrencyAdjust.tempCollection", FetchMode.JOIN);
			criteria2.createAlias("tempForeignCurrencyAdjust.tempCollection", "tempCollection", JoinType.INNER_JOIN);

			criteria2.add(Restrictions.eq("tempCollection.collectionId", tempCollection.getCollectionId()));

			criteria1.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<TempForeignCurrencyAdjust> tempForeignCurrencyAdjust = (List<TempForeignCurrencyAdjust>) findAll(criteria2);

			lstTempDt.put("TempForeCurrAdj", tempForeignCurrencyAdjust);

		}

		return lstTempDt;
	}

	@Override
	public void saveRefundTempCurrencyAdjust(List<TempForeignCurrencyAdjust> tempAdjustList) {
		try {
			for (TempForeignCurrencyAdjust adjust : tempAdjustList) {
				saveAdjust(adjust);
			}
		} catch (Exception e) {
			LOGGER.info("Exception occured"+e);
		}
	}

	@Override
	public List<CustomerIdProof> getCustomerDetailsActiveRec(String customerId, BigDecimal cardType) {
		LOGGER.info("Entered into getCustomerDetails(String customerId, BigDecimal cardType) method ");
		LOGGER.info("CustomerId =" + customerId);
		LOGGER.info("cardType =" + cardType);
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		criteria.add(Restrictions.eq("customerIdProof.identityInt", customerId));
		criteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId", cardType));
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.isActive", Constants.Yes));
		criteria.addOrder(Order.desc("customerIdProof.creationDate"));
		criteria.add(Restrictions.eq("customerIdProof.identityStatus", Constants.Yes));
		
		Date currentDate = null;
		try {
			currentDate = formatter.parse(formatter.format(new Date()));
			System.out.println("Today : " + currentDate);
			if (currentDate != null) {
				criteria.add(Restrictions.ge("customerIdProof.identityExpiryDate", currentDate));
			}
		} catch (ParseException e) {
			LOGGER.info("Err getCustomerDetails"+e.getMessage());
		}
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CustomerIdProof> lstCustomerIdPrrof = (List<CustomerIdProof>) findAll(criteria);
		LOGGER.info("Exited from getCustomerDetails(String customerId, BigDecimal cardType) method");
		return lstCustomerIdPrrof;
	}

	@Override
	public String validateDebitCardNo(HashMap<String, Object> inputValues) throws AMGException {
		Connection connection = null;
		CallableStatement cs = null;
		String errorMsg = null;
		LOGGER.info("validateDebitCardNo :" + inputValues.toString());
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_VALIDATE_DEBITCARD (?,?,?,?,?,?) } ";
			cs = connection.prepareCall(call);

			// In Parameters
			cs.setBigDecimal(1, (BigDecimal) inputValues.get("P_APPLICATION_COUNTRY_ID"));
			cs.setBigDecimal(2, (BigDecimal) inputValues.get("P_CUSTOMER_ID"));
			cs.setBigDecimal(3, (BigDecimal) inputValues.get("P_DEBIT_CARD"));
			cs.setString(4, (String) inputValues.get("P_DB_CARD_NAME"));
			cs.setString(5, (String) inputValues.get("P_BANK_CODE"));
			// Out Parameters
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			errorMsg = cs.getString(6);
		} catch (SQLException e) {
			String erromsg = "EX_P_VALIDATE_DEBITCARD" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				cs.close();
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Problem Occured While Procedure calling " + e.getMessage());
				String erromsg = "EX_P_VALIDATE_DEBITCARD" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		LOGGER.info("Exited from the validateDebitCardNo method  calling ");
		return errorMsg;
	}

	@Override
	public HashMap<String, Object> saveAllRemittanceTransaction(HashMap<String, Object> mapAllDetailPersonalRemittanceSave) throws AMGException {
		LOGGER.info("Entered into saveAllRemittanceTransaction() method ");
		HashMap<String, Object> lstOutParam = new HashMap<String, Object>();
		Connection connection = null;
		CallableStatement cs;
		String errormsg = null;
		BigDecimal cFinYear = null;
		BigDecimal collectionNumber = null;

		try {
			BigDecimal countryId = (BigDecimal) mapAllDetailPersonalRemittanceSave.get("CountryId");
			BigDecimal companyId = (BigDecimal) mapAllDetailPersonalRemittanceSave.get("CompanyId");
			BigDecimal customerId = (BigDecimal) mapAllDetailPersonalRemittanceSave.get("CustomerId");
			String userName = (String) mapAllDetailPersonalRemittanceSave.get("UserName");
			BigDecimal noofTrnx = (BigDecimal) mapAllDetailPersonalRemittanceSave.get("NoofTrnx");
			BigDecimal tempDocCode = (BigDecimal) mapAllDetailPersonalRemittanceSave.get("TempDocCode");
			BigDecimal tempCollectionId = (BigDecimal) mapAllDetailPersonalRemittanceSave.get("TempCollectionId");

			try {
				connection= getDataSourceFromHibernateSession();
			} catch (Exception e) {
				e.printStackTrace();
			}

			LOGGER.info("Parameter 1 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCountryId(): "+countryId);
			LOGGER.info("Parameter 2 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCompanyId(): "+companyId);
			LOGGER.info("Parameter 3 saveRemittance EX_INSERT_REMITTANCE_TRANX getCustomerNo(): "+customerId);
			LOGGER.info("Parameter 4 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionmanage.getUserName(): "+userName);
			LOGGER.info("Parameter 5 saveRemittance EX_INSERT_REMITTANCE_TRANX getColremittanceNo() :"+noofTrnx);
			LOGGER.info("Parameter 6 saveRemittance EX_INSERT_REMITTANCE_TRANX tempCollection.getDocumentCode(): "+tempDocCode);
			LOGGER.info("Parameter 7 saveRemittance EX_INSERT_REMITTANCE_TRANX collectionId: "+tempCollectionId);

			// calling procedure
			cs = connection.prepareCall(" { call EX_INSERT_REMITTANCE_TRANX (?,?,?,?,?,?,?,?,?,?)}");
			cs.setBigDecimal(1, countryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, customerId);
			cs.setString(4, userName);
			cs.setBigDecimal(5, noofTrnx);
			cs.setBigDecimal(6, tempDocCode);
			cs.setBigDecimal(7,tempCollectionId);
			cs.registerOutParameter(8, java.sql.Types.INTEGER);
			cs.registerOutParameter(9, java.sql.Types.INTEGER);
			cs.registerOutParameter(10, java.sql.Types.VARCHAR);
			cs.execute();

			cFinYear = cs.getBigDecimal(8);
			collectionNumber = cs.getBigDecimal(9);
			errormsg = cs.getString(10);

			LOGGER.info("Out1 -----> cFinYear : "+cFinYear);
			LOGGER.info("Out2------>collectionNumber : "+collectionNumber);
			LOGGER.info("Out3------->errormsg : "+errormsg);

			lstOutParam.put("CollectionDocNo", collectionNumber);
			lstOutParam.put("CollectionFinYear", cFinYear);
			lstOutParam.put("ErrorMsg", errormsg);

		} catch (Exception e) {
			LOGGER.info("Problem to redirect: " + e);
			throw new AMGException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new AMGException(e.getMessage());
			} catch (Exception e) {
				throw new AMGException(e.getMessage());
			}
		}

		return lstOutParam;
	}

	@Override
	public List<RemittanceApplicationView> fetchRemitTrnxViewByPin(String pinNo) {
		LOGGER.info("Entered into fetchRemitTrnxViewByPin(String pinNo) Method");
		LOGGER.info("pinNo =" + pinNo);

		List<RemittanceApplicationView> lstremit = new ArrayList<RemittanceApplicationView>();

		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplicationView.class, "remittanceApplicationView");
			criteria.add(Restrictions.eq("remittanceApplicationView.pinNo", pinNo));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			LOGGER.info("Exited from getRecordsRemittanceReceipt Method ");

			lstremit = (List<RemittanceApplicationView>) findAll(criteria);

		} catch (Exception e) {
			LOGGER.info("Problem to redirect: " + e.getMessage());
		}

		LOGGER.info("Exit from fetchRemitTrnxViewByPin(String pinNo) Method : "+ lstremit.size());

		return lstremit;
	}

	@Override
	public List<BenificiaryListView> getBeneficiaryDtFromView(BigDecimal cutomerNo,BigDecimal beneCountryId,BigDecimal beneMasterSeqId,BigDecimal currencyId,String bankCode,BigDecimal beneAccountSeqId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BenificiaryListView.class, "benificiaryListView");
		criteria.add(Restrictions.eq("benificiaryListView.customerId", cutomerNo));
		if (beneCountryId != null) {
			criteria.add(Restrictions.eq("benificiaryListView.benificaryCountry", beneCountryId));
		}

		criteria.add(Restrictions.eq("benificiaryListView.beneficaryMasterSeqId", beneMasterSeqId));
		criteria.add(Restrictions.eq("benificiaryListView.currencyId", currencyId));
		criteria.add(Restrictions.eq("benificiaryListView.bankCode", bankCode));
		criteria.add(Restrictions.eq("benificiaryListView.beneficiaryAccountSeqId", beneAccountSeqId));
		criteria.addOrder(Order.desc("benificiaryListView.createdDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BenificiaryListView> lstBeneView = (List<BenificiaryListView>) findAll(criteria);

		return lstBeneView;
	}

	@Override
	public HashMap<String, Object> fetchRemitAmlTrnxDetails(BigDecimal remittanceTrnxId,BigDecimal authType) {

		System.out.println("remittanceTrnxId :"+remittanceTrnxId+"\t authType :"+authType);
		
		HashMap<String, Object> lstmap = new HashMap<String, Object>();
		String queryString = "Select AUTHORIZED_BY,CREATED_DATE from Ex_Remit_Aml where REMITTANCE_TRANSACTION_ID = ? and AUTH_TYP = ?";
		
	  SQLQuery query = (SQLQuery) getSession().createSQLQuery(queryString).setBigDecimal(0, remittanceTrnxId).setBigDecimal(1, authType);
		
		List list = query.list();
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			for (int col = 0; col < row.length; col++) {			
				lstmap.put("AUTHORIZED_BY", row[0]);
				lstmap.put("CREATED_DATE", row[1]);
			}
		}

		return lstmap;
	}

	@Override
	public List<IntraTrnxModel> fetchRemitTrnxByPin(String pinNo) {

		DetachedCriteria criteria = DetachedCriteria.forClass(IntraTrnxModel.class, "intraTrnxModel");
		criteria.add(Restrictions.eq("intraTrnxModel.pinNumber", pinNo));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exited from getRecordsRemittanceReceipt Method ");
		List<IntraTrnxModel> lstRemitTrnx = (List<IntraTrnxModel>) findAll(criteria);

		return lstRemitTrnx;
	}

	//For Company information ADDED BY VISWA --START
	public List<CompanyMaster> getCompanyMaster(BigDecimal companyId){

		DetachedCriteria dCriteria = DetachedCriteria.forClass(CompanyMaster.class, "companyMaster");
		dCriteria.add(Restrictions.eq("companyMaster.companyId", companyId));
		List<CompanyMaster> list=(List<CompanyMaster>) findAll(dCriteria);

		if(list.isEmpty()) {
			LOGGER.info("Company ID not found");
			return null;

		}
		LOGGER.info("Exit into getCompanyMaster method");
		return list;

	}
	//For Company information ADDED BY VISWA --END

	@Override
	public List<PopulateData> getServiceListForPO(
			BigDecimal appLicationCountryId, BigDecimal beneBankId,
			BigDecimal beneBankBranchId, BigDecimal countryId,
			BigDecimal beneCurrencyId, String serviceGroupCode,
			BigDecimal routingCountryId, BigDecimal routingBankId) {
		try {
			LOGGER.info("getServiceList method appLicationCountryId :" + appLicationCountryId);
			LOGGER.info("beneBankId :" + beneBankId);
			LOGGER.info("beneBankBranchId :" + beneBankBranchId);
			LOGGER.info("countryId :" + countryId);
			LOGGER.info("beneCurrencyId :" + beneCurrencyId);
			LOGGER.info("serviceGroupCode :" + serviceGroupCode);
			List<PopulateData> serviceList = new ArrayList<PopulateData>();
			String sqlQuery = "SELECT DISTINCT f.SERVICE_CODE,F.SERVICE_DESCRIPTION,  F.SERVICE_MASTER_ID " + " FROM   V_EX_ROUTING_DETAILS F "
					+ " WHERE  BENE_BANK_ID = " + beneBankId + " AND    BENE_BANK_BRANCH_ID = " + beneBankBranchId + " " + " AND    F.COUNTRY_ID ="
					+ countryId + " AND    F.CURRENCY_ID  = " + beneCurrencyId + " " + " AND    F.SERVICE_GROUP_CODE  = '" + serviceGroupCode
					+ "' AND  APPLICATION_COUNTRY_ID=" + appLicationCountryId + " AND F.Routing_Country_Id  = " +routingCountryId+ " AND    F.Routing_Bank_Id =" +routingBankId;

			LOGGER.info("getServiceList :" + sqlQuery);

			SQLQuery query = getSession().createSQLQuery(sqlQuery);
			List<Object> rows = query.list();
			List list = query.list();
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				PopulateData lstService = new PopulateData();
				Object[] row = (Object[]) iterator.next();
				for (int col = 0; col < row.length; col++) {
					lstService.setPopulateId(new BigDecimal(row[2].toString()));
					lstService.setPopulateName(row[1] == null ? "" : row[1].toString());
				}
				serviceList.add(lstService);
			}
			return serviceList;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public HashMap<String, Object> fetchLocalAuthDetails(BigDecimal documentId, BigDecimal documentNo, BigDecimal documentfyr,String authType) {
		
		System.out.println("documentId :"+documentId+"\t authType :"+authType+"\t document No :"+documentNo);
		
		HashMap<String, Object> lstmap1 = new HashMap<String, Object>();
		HashMap<String, Object> lstmap = new HashMap<String, Object>();
		
		String queryString1 = "select HIGH_VALUE_TRANX , FC_HIGH_VALUE_TRANX from EX_REMIT_TRNX where DOCUMENT_ID = ? and DOCUMENT_FINANCE_YEAR = ? and DOCUMENT_NO = ? and COMPANY_ID = ?";
		SQLQuery query1 = (SQLQuery) getSession().createSQLQuery(queryString1).setBigDecimal(0, documentId).setBigDecimal(1,documentfyr).setBigDecimal(2, documentNo).setString(3, sessionStateManage.getCompanyId().toPlainString());
		List list1 = query1.list();
		Iterator iterator1 = list1.iterator();
		while (iterator1.hasNext()) {
			Object[] row = (Object[]) iterator1.next();
			for (int col = 0; col < row.length; col++) {			
				lstmap1.put("HIGH_VALUE_TRANX", row[0]);
				lstmap1.put("FC_HIGH_VALUE_TRANX", row[1]);
			}
		}
		
		if(authType != null && authType.equalsIgnoreCase("FC HIGH VALUE")){
			if(lstmap1.get("FC_HIGH_VALUE_TRANX") != null && lstmap1.get("FC_HIGH_VALUE_TRANX").toString().equalsIgnoreCase(Constants.Yes)){
				lstmap.put("STATUS", Constants.Yes);
			}else{
				lstmap.put("STATUS", Constants.No);
			}
		}else if(authType != null && authType.equalsIgnoreCase("LOCAL HIGH VALUE")){
			if(lstmap1.get("HIGH_VALUE_TRANX") != null && lstmap1.get("HIGH_VALUE_TRANX").toString().equalsIgnoreCase(Constants.Yes)){
				lstmap.put("STATUS", Constants.Yes);
			}else{
				lstmap.put("STATUS", Constants.No);
			}
		}else{
			// wrong data
		}
		
		String queryString = "select CREATED_BY AUTHORIZED_BY ,to_char(CREATED_DATE,'dd/MM/yyyy hh:mm:ss') CREATED_DATE from VW_LOCAL_FOREIGN_AUTH where DOCUMENT_ID =? and DOCUMENT_FINANCE_YEAR =? and DOCUMENT_NO = ? and authorized_type =?";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(queryString).setBigDecimal(0, documentId).setBigDecimal(1,documentfyr).setBigDecimal(2, documentNo).setString(3, authType);

		List list = query.list();
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			for (int col = 0; col < row.length; col++) {			
				lstmap.put("AUTHORIZED_BY", row[0]);
				lstmap.put("CREATED_DATE", row[1]);
			}
		}

		return lstmap;
	}
	
	@Override
	public BigDecimal getCurrencyId(BigDecimal countryId){
		
		BigDecimal currencyId = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMasterView.class, "currencyMasterView");
		
		criteria.add(Restrictions.eq("currencyMasterView.countryId", countryId));
		List<CurrencyMasterView> lstCurrencyMasterView = (List<CurrencyMasterView>) findAll(criteria);
		if(lstCurrencyMasterView!=null && lstCurrencyMasterView.size()>0)
		{
			currencyId=lstCurrencyMasterView.get(0).getCurrencyId();
		}
		return currencyId;
	}

	@Override
	public void saveCustomerMobileLog(CustomerMobileLogModel custMobLog) {

		// deactivate all mobile number of customer in fs_custome_mobile_log
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerMobileLogModel.class, "customerMobileLogModel");
		dCriteria.add(Restrictions.eq("customerMobileLogModel.customerId", custMobLog.getCustomerId()));
		dCriteria.add(Restrictions.eq("customerMobileLogModel.isActive", Constants.Yes));
		List<CustomerMobileLogModel> lstCustomerMobileLog = (List<CustomerMobileLogModel>) findAll(dCriteria);

		if(lstCustomerMobileLog != null && lstCustomerMobileLog.size() != 0){
			for (CustomerMobileLogModel customerMobileLogModel : lstCustomerMobileLog) {
				CustomerMobileLogModel custMobLogData = (CustomerMobileLogModel) getSession().get(CustomerMobileLogModel.class, customerMobileLogModel.getCustomerMobileId());
				custMobLogData.setIsActive(Constants.D);
				custMobLogData.setModifiedBy(sessionStateManage.getUserName());
				custMobLogData.setModifiedDate(new Date());
				getSession().update(custMobLogData);
			}
		}

		// save new record for same customer
		getSession().save(custMobLog);
	}

	@Override
	public List<CustomerIdProof> getCustomerTypeByCustomer(BigDecimal customerId) {
		LOGGER.info("Entered into getCustomerTypeByCustomer(String customerId) method ");
		LOGGER.info("CustomerId =" + customerId);
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		
		criteria.setFetchMode("customerIdProof.fsBizComponentDataByCustomerTypeId", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.add(Restrictions.eq("fsCustomer.isActive", Constants.Yes));
		
		criteria.addOrder(Order.desc("customerIdProof.creationDate"));
		criteria.add(Restrictions.eq("customerIdProof.identityStatus", Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CustomerIdProof> lstCustomerIdPrrof = (List<CustomerIdProof>) findAll(criteria);
		LOGGER.info("Exited from getCustomerDetails(String customerId, BigDecimal cardType) method");
		return lstCustomerIdPrrof;
	}

	@Override
	public boolean chkRoutingSetup(HashMap<String, Object> routingSetUp) throws AMGException {
		
		Boolean chkRouting = false;
		
		//BigDecimal appCountryId = (BigDecimal) routingSetUp.get("APPLICATION_COUNTRY_ID");
		BigDecimal routingCountryId = (BigDecimal) routingSetUp.get("COUNTRY_ID");
		//BigDecimal currencyId = (BigDecimal) routingSetUp.get("CURRENCY_ID");
		//BigDecimal countryBranchId = (BigDecimal) routingSetUp.get("COUNTRY_BRANCH_ID");
		BigDecimal routingBankId = (BigDecimal) routingSetUp.get("BANK_ID");
		BigDecimal serviceMasterId = (BigDecimal) routingSetUp.get("SERVICE_MASTER_ID");
		//BigDecimal remittanceMasterId = null;
		//BigDecimal deliveryMasterId = null;
		//BigDecimal foreignCurrencyId = (BigDecimal) routingSetUp.get("P_FOREIGN_CURRENCY_ID");
		//BigDecimal selectedCurrencyId = (BigDecimal) routingSetUp.get("P_SELECTED_CURRENCY_ID");
		//String customerType = (String) routingSetUp.get("P_CUSTOMER_TYPE");
		//BigDecimal selectedCurrencyAmount = (BigDecimal) routingSetUp.get("P_SELECTED_CURRENCY_AMOUNT");
		
		DetachedCriteria criteria = DetachedCriteria.forClass(RoutingHeader.class, "routingHeader");
		
		criteria.setFetchMode("routingHeader.exRoutingCountryId", FetchMode.JOIN);
		criteria.createAlias("routingHeader.exRoutingCountryId", "exRoutingCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exRoutingCountryId.countryId", routingCountryId));
		
		criteria.setFetchMode("routingHeader.exRoutingBankId", FetchMode.JOIN);
		criteria.createAlias("routingHeader.exRoutingBankId", "exRoutingBankId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exRoutingBankId.bankId", routingBankId));
		
		criteria.setFetchMode("routingHeader.exServiceId", FetchMode.JOIN);
		criteria.createAlias("routingHeader.exServiceId", "exServiceId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exServiceId.serviceId", serviceMasterId));
		
		criteria.add(Restrictions.eq("routingHeader.isActive", Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RoutingHeader> lstRoutingHeader = (List<RoutingHeader>) findAll(criteria);
		
		if(lstRoutingHeader != null && lstRoutingHeader.size() != 0){
			chkRouting = true;
		}
		
		return chkRouting;
	}

	@Override
	public List<ViewAmtbCoupon> getAmtbCouponFromView(String IdNumber) {
		String sql = " COUPON_NO not in(select  COUPON_NO from EX_AMTB_COUPON where IDENTITY_INT='"+IdNumber+"')";
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewAmtbCoupon.class, "viewAmtbCoupon");
		dCriteria.add(Restrictions.eq("viewAmtbCoupon.idNo", IdNumber));
		dCriteria.add(Restrictions.sqlRestriction(sql));
		List<ViewAmtbCoupon> list=(List<ViewAmtbCoupon>) findAll(dCriteria);
		return list;
	}

	@Override
	public ViewAmtbCoupon getAmtbCouponAmountFromView(BigDecimal couponNo, String IdNumber) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ViewAmtbCoupon.class, "viewAmtbCoupon");
		detachedCriteria.add(Restrictions.eq("viewAmtbCoupon.idNo", IdNumber));
		detachedCriteria.add(Restrictions.eq("viewAmtbCoupon.couponNo", couponNo));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit from  getCustomerList(String identityType) method");
		return ((ViewAmtbCoupon) detachedCriteria.getExecutableCriteria(getSession()).list().get(0));
	}
	
	@Override
	public BigDecimal getRemittanceApplicationstatus(BigDecimal customerId,BigDecimal documentNo,BigDecimal documentId,String status,BigDecimal documentFinanceYearId,BigDecimal companyId) {

		LOGGER.info("Entered into getRemittanceApplicationstatus(BigDecimal customerId, BigDecimal documentNo) Method ");
		LOGGER.info("customerId = " + customerId);
		LOGGER.info("documentNo = " + documentNo);
		LOGGER.info("documentId = " + documentId);
		LOGGER.info("companyId = " + companyId);
		LOGGER.info("documentFinanceYearId = " + documentFinanceYearId);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplication.class, "remittanceApplication");
		
		criteria.setFetchMode("remittanceApplication.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		
		criteria.setFetchMode("remittanceApplication.exDocument", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.exDocument", "exDocument", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exDocument.documentID", documentId));
		
		criteria.setFetchMode("remittanceApplication.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		criteria.setFetchMode("remittanceApplication.exUserFinancialYearByDocumentFinanceYear", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.exUserFinancialYearByDocumentFinanceYear", "exUserFinancialYearByDocumentFinanceYear", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exUserFinancialYearByDocumentFinanceYear.financialYearID", documentFinanceYearId));
		
		criteria.add(Restrictions.eq("remittanceApplication.documentNo", documentNo));
		
		if(status != null){
			criteria.add(Restrictions.isNull("remittanceApplication.applicaitonStatus"));
		}
		List<RemittanceApplication> remittanceAppList = findAll(criteria);
		LOGGER.info("Exited getRemittanceApplicationstatus(BigDecimal customerId, BigDecimal documentNo) Method ");
		if (!remittanceAppList.isEmpty()) {
			return remittanceAppList.get(0).getRemittanceApplicationId();
		}
		
		return null;
	}

	@Override
	public BigDecimal getRemittanceApplicationPk(BigDecimal customerId,	BigDecimal documentNo, BigDecimal documentId,BigDecimal documentFinanceYearId, BigDecimal companyId) {
		LOGGER.info("Entered into getRemittanceApplicationPk(BigDecimal customerId, BigDecimal documentNo) Method ");
		LOGGER.info("customerId = " + customerId);
		LOGGER.info("documentNo = " + documentNo);


		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplication.class, "remittanceApplication");

		criteria.setFetchMode("remittanceApplication.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));

		criteria.setFetchMode("remittanceApplication.exDocument", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.exDocument", "exDocument", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exDocument.documentID", documentId));

		criteria.setFetchMode("remittanceApplication.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));

		criteria.setFetchMode("remittanceApplication.exUserFinancialYearByDocumentFinanceYear", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.exUserFinancialYearByDocumentFinanceYear", "exUserFinancialYearByDocumentFinanceYear", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exUserFinancialYearByDocumentFinanceYear.financialYearID", documentFinanceYearId));

		criteria.add(Restrictions.eq("remittanceApplication.documentNo", documentNo));
		
		List<RemittanceApplication> remittanceAppList = findAll(criteria);
		
		if(remittanceAppList != null && remittanceAppList.size() != 0){
			return remittanceAppList.get(0).getRemittanceApplicationId();
		}
		
		LOGGER.info("Exited getRemittanceApplicationPk(BigDecimal customerId, BigDecimal documentNo) Method ");
		return null;
	}
	
	@Override
	public HashMap<String, Object> getRoutingSetupForCashProduct(HashMap<String, Object> inputValues) {
		
		HashMap<String, Object> routingSetUpdetails = new HashMap<String, Object>();
		
		BigDecimal appLicationCountryId = (BigDecimal) inputValues.get("APPLICATION_COUNTRY_ID");
		BigDecimal beneCurrencyId = (BigDecimal) inputValues.get("CURRENCY_ID");
		BigDecimal serviceMasterId = (BigDecimal) inputValues.get("SERVICE_MASTER_ID");
		BigDecimal routingCountryId = (BigDecimal) inputValues.get("ROUTING_COUNTRY_ID");
		BigDecimal routingbankId = (BigDecimal) inputValues.get("ROUTING_BANK_ID");
		BigDecimal beneficiaryCountryId = (BigDecimal) inputValues.get("BENEFICIARY_COUNTRY_ID");
		
		String sql1 = "SELECT DISTINCT F.BRANCH_APPLICABILITY " + " FROM  EX_ROUTING_DETAILS F "
				+ " WHERE  F.APPLICATION_COUNTRY_ID = "
				+ appLicationCountryId
				+ " AND    F.COUNTRY_ID = "
				+ beneficiaryCountryId
				+ " AND    F.CURRENCY_ID  =  "
				+ beneCurrencyId
				+ " AND    F.SERVICE_MASTER_ID  = "
				+ serviceMasterId
				+ " AND    F.ROUTING_COUNTRY_ID = "
				+ routingCountryId
				+ " AND    F.ROUTING_BANK_ID = "
				+ routingbankId
				+ " AND F.ISACTIVE = '"+ Constants.Yes + "'";
		
		LOGGER.info("getRemittanceListByCountryBank :" + sql1);
		SQLQuery query1 = getSession().createSQLQuery(sql1);
		List<String> rows1 = query1.list();
		int i = 0;
		String branchApplicability = null;
		for (String viewRoutingDetails : rows1) {
			branchApplicability = viewRoutingDetails;
			i++;
		}
		
		String sql = null;
		if(branchApplicability != null && i == 1){

			routingSetUpdetails.put("BranchApplicability", branchApplicability);

			if(branchApplicability.equalsIgnoreCase(Constants.ALL)){
				sql = "SELECT DISTINCT F.BANK_BRANCH_ID " + " FROM  EX_ROUTING_DETAILS F "
						+ " WHERE  F.APPLICATION_COUNTRY_ID = "
						+ appLicationCountryId
						+ " AND    F.COUNTRY_ID = "
						+ beneficiaryCountryId
						+ " AND    F.CURRENCY_ID  =  "
						+ beneCurrencyId
						+ " AND    F.SERVICE_MASTER_ID  = "
						+ serviceMasterId
						+ " AND    F.ROUTING_COUNTRY_ID = "
						+ routingCountryId
						+ " AND    F.ROUTING_BANK_ID = "
						+ routingbankId
						+ " AND F.ISACTIVE = '"+ Constants.Yes + "'";

				LOGGER.info("getRemittanceListByCountryBank :" + sql);
				SQLQuery query = getSession().createSQLQuery(sql);
				List<BigDecimal> rows = query.list();
				for (BigDecimal viewRoutingDetails : rows) {
					routingSetUpdetails.put("RoutingBankBranchId", viewRoutingDetails);
				}
			}
		}
		
		return routingSetUpdetails;
	}

	@Override
	public String getExCheckRefundCashLimitProcedure(
			BigDecimal applicationCountyId, BigDecimal customerId,
			BigDecimal paymentModeId, BigDecimal colleAmount)
			throws AMGException {
		Connection connection = null;
		String msgOutPut = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			LOGGER.info("Entered into getExCheckRefundCashLimitProcedure method called ");
			LOGGER.info("Procedure Name = EX_REFUND_CASH_LIMIT ");
			LOGGER.info("getExCheckCashLimitProcedure applicationCountyId :" + applicationCountyId);
			LOGGER.info("getExCheckCashLimitProcedure applicationCountyId :" + customerId);
			LOGGER.info("getExCheckCashLimitProcedure applicationCountyId :" + paymentModeId);
			LOGGER.info("getExCheckCashLimitProcedure applicationCountyId :" + colleAmount);
			String call = " { call EX_REFUND_CASH_LIMIT (?,?,?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, applicationCountyId);
			cs.setBigDecimal(2, customerId);
			cs.setBigDecimal(3, paymentModeId);
			cs.setBigDecimal(4, colleAmount);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.execute();// teUpdate();
			msgOutPut = cs.getString(5) == null ? "" : cs.getString(5);
			LOGGER.info("getExCheckRefundCashLimitProcedure msgOutPut :" + msgOutPut);
		} catch (SQLException e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_REFUND_CASH_LIMIT" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error While Procedure Calling " + e.getMessage());
				String erromsg = "EX_REFUND_CASH_LIMIT" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return msgOutPut;
	}


}

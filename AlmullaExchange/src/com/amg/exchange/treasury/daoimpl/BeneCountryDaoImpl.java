package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.treasury.dao.IBeneCountryDao;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.util.Constants;

@SuppressWarnings("serial")
@Repository
public class BeneCountryDaoImpl<T> extends CommonDaoImpl<T> implements
IBeneCountryDao<T>, Serializable {
	@SuppressWarnings("unchecked")
	@Override
	public List<CountryMaster> lstCountrs(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
		dCriteria.setFetchMode("countryMaster.fsCountryMasterDescs", FetchMode.JOIN);
		dCriteria.createAlias("countryMaster.fsCountryMasterDescs", "fsCountryMasterDescs", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMasterDescs.countryId", countryId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CountryMaster>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceMasterDesc> lstServices(BigDecimal languageId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ServiceMasterDesc.class, "serviceMasterDesc");
		dCriteria.setFetchMode("serviceMasterDesc.serviceMaster", FetchMode.JOIN);
		dCriteria.createAlias("serviceMasterDesc.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("serviceMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", languageId));
		dCriteria.add(Restrictions.eq("serviceMaster.isActive",Constants.Yes));
		dCriteria.addOrder(Order.asc("serviceMasterDesc.localServiceDescription"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ServiceMasterDesc>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemittanceModeDescription> lstRemitancemode(BigDecimal serviceId, BigDecimal languageId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");
		dCriteria.setFetchMode("remittanceModeDescription.remittanceModeMaster", FetchMode.JOIN);
		dCriteria.createAlias("remittanceModeDescription.remittanceModeMaster", "remittanceModeMaster", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("remittanceModeMaster.serviceMaster", FetchMode.JOIN);
		dCriteria.createAlias("remittanceModeMaster.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("remittanceModeDescription.languageType", FetchMode.JOIN);
		dCriteria.createAlias("remittanceModeDescription.languageType", "languageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", languageId));
		dCriteria.add(Restrictions.eq("serviceMaster.serviceId", serviceId));
		dCriteria.add(Restrictions.eq( "remittanceModeMaster.isActive",Constants.Yes));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RemittanceModeDescription>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal languageId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");
		dCriteria.setFetchMode("deliveryModeDesc.deliveryMode", FetchMode.JOIN);
		dCriteria.createAlias("deliveryModeDesc.deliveryMode", "deliveryMode", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq( "deliveryMode.isActive",Constants.Yes));
		dCriteria.setFetchMode("deliveryModeDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("deliveryModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", languageId));
		dCriteria.addOrder(Order.asc("deliveryModeDesc.englishDeliveryName"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<DeliveryModeDesc>) findAll(dCriteria);
	}

	@Override
	public void save(BeneCountryService beneCountryservice) {
		getSession().saveOrUpdate(beneCountryservice);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BeneCountryService> validateBene(BigDecimal applId, BigDecimal countryId, BigDecimal currncyId, BigDecimal serviceId, BigDecimal remitanceId, BigDecimal deliveryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BeneCountryService.class, "beneCountryService");
		System.out.println("@@@@" + applId + "####" + countryId + "$$$$" + currncyId + "%%%%" + serviceId + "****" + remitanceId + "^^^^^" + deliveryId);
		dCriteria.setFetchMode("beneCountryService.applicationCountryId", FetchMode.JOIN);
		dCriteria.createAlias("beneCountryService.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("applicationCountryId.countryId", applId));
		dCriteria.setFetchMode("beneCountryService.beneCountryId", FetchMode.JOIN);
		dCriteria.createAlias("beneCountryService.beneCountryId", "beneCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("beneCountryId.countryId", countryId));
		dCriteria.setFetchMode("beneCountryService.currencyId", FetchMode.JOIN);
		dCriteria.createAlias("beneCountryService.currencyId", "currencyId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("currencyId.currencyId", currncyId));
		dCriteria.setFetchMode("beneCountryService.remitanceId", FetchMode.JOIN);
		dCriteria.createAlias("beneCountryService.remitanceId", "remitanceId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("remitanceId.remittanceModeId", remitanceId));
		dCriteria.setFetchMode("beneCountryService.serviceId", FetchMode.JOIN);
		dCriteria.createAlias("beneCountryService.serviceId", "serviceId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("serviceId.serviceId", serviceId));
		dCriteria.setFetchMode("beneCountryService.deliveryId", FetchMode.JOIN);
		dCriteria.createAlias("beneCountryService.deliveryId", "deliveryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("deliveryId.deliveryModeId", deliveryId));
		// dCriteria.add(Restrictions.eq("beneCountryService.isActive", "Y"));
		System.out.println("#$$$$$$$$#$#" + findAll(dCriteria).size());
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BeneCountryService>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BeneCountryService> validateBenedata() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BeneCountryService.class, "beneCountryService");
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BeneCountryService>) findAll(dCriteria);
	}

	@Override
	public String getCurrency(BigDecimal currencyID) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		dCriteria.add(Restrictions.eq("currencyMaster.currencyId", currencyID));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return ((CurrencyMaster) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCurrencyName();
	}

	@Override
	public String getRemitanceCode(BigDecimal remitanceId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RemittanceModeMaster.class, "remittanceModeMaster");
		dCriteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId", remitanceId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return ((RemittanceModeMaster) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getRemittance();
	}

	@Override
	public String getDeliveryCode(BigDecimal deliveryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DeliveryMode.class, "deliveryMode");
		dCriteria.add(Restrictions.eq("deliveryMode.deliveryModeId", deliveryId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return ((DeliveryMode) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getDeliveryMode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BeneCountryService> viewAllRecords(BigDecimal applId, BigDecimal countryId, BigDecimal currncyId,BigDecimal serviceId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BeneCountryService.class, "beneCountryService");
		dCriteria.setFetchMode("beneCountryService.applicationCountryId", FetchMode.JOIN);
		dCriteria.createAlias("beneCountryService.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("applicationCountryId.countryId", applId));
		dCriteria.setFetchMode("beneCountryService.beneCountryId", FetchMode.JOIN);
		dCriteria.createAlias("beneCountryService.beneCountryId", "beneCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("beneCountryId.countryId", countryId));
		dCriteria.setFetchMode("beneCountryService.currencyId", FetchMode.JOIN);
		dCriteria.createAlias("beneCountryService.currencyId", "currencyId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("currencyId.currencyId", currncyId));

		/*dCriteria.setFetchMode("beneCountryService.serviceId", FetchMode.JOIN);
		dCriteria.createAlias("beneCountryService.serviceId", "serviceId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("serviceId.serviceId", serviceId));*/

		//dCriteria.add(Restrictions.ne("beneCountryService.isActive", "U"));
		System.out.println("#$$$$$$$$#$#" + findAll(dCriteria).size());
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BeneCountryService>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BeneCountryService> approvalBeanList(BigDecimal applId) {
		System.out.println("Entering into approvalBeanList method :BeneCountryDaoImpl");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BeneCountryService.class, "beneCountryService");
		dCriteria.setFetchMode("beneCountryService.applicationCountryId", FetchMode.JOIN);
		dCriteria.createAlias("beneCountryService.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("applicationCountryId.countryId", applId));
		dCriteria.add(Restrictions.eq("beneCountryService.isActive", Constants.U));
		dCriteria.add(Restrictions.isNull("beneCountryService.approvedBy"));
		dCriteria.add(Restrictions.isNull("beneCountryService.approvedDate"));
		System.out.println("#$$$$$$$$#$#" + findAll(dCriteria).size());
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		System.out.println("Exit into approvalBeanList method :BeneCountryDaoImpl");
		return (List<BeneCountryService>) findAll(dCriteria);
	}

	@Override
	public void update(BeneCountryService benecountryservice) {
		System.out.println("Entering into update method :BeneCountryDaoImpl");
		getSession().saveOrUpdate(benecountryservice);
		System.out.println("Exit into update method :BeneCountryDaoImpl");
	}

	@Override
	public void delete(BeneCountryService benecountryservice) {
		System.out.println("Entering into delete method :BeneCountryDaoImpl");
		getSession().delete(benecountryservice);
		System.out.println("Exit into delete method :BeneCountryDaoImpl");

	}

	@Override
	public void delete(BigDecimal bnCntryservicepk) {
		BeneCountryService beneCountryService=(BeneCountryService) getSession().get(BeneCountryService.class, bnCntryservicepk);
		getSession().delete(beneCountryService);
	}

	@Override
	public String approveRecord(BigDecimal bnCntryservicepk, String userName) {
		String approveMsg;
		BeneCountryService beneCountryService = (BeneCountryService) getSession().get(BeneCountryService.class, bnCntryservicepk);
		String approvedUser=beneCountryService.getApprovedBy();
		if(approvedUser==null)
		{
			beneCountryService.setIsActive(Constants.Yes);
			beneCountryService.setApprovedBy(userName);
			beneCountryService.setApprovedDate(new Date());
			getSession().update(beneCountryService);
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BeneficaryAccount> checkDuplicateForCash(BigDecimal masterSeqId,BigDecimal bankCountryId,BigDecimal serviceGroupId,BigDecimal serviceProviderBankId, BigDecimal currencyId,BigDecimal agentBankId, BigDecimal agentBankBranchId) {

		/*DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");

		if(masterSeqId != null){
			criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", masterSeqId));
		}

		if(bankCountryId != null){
			criteria.setFetchMode("beneficaryAccount.beneficaryCountry", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.beneficaryCountry", "beneficaryCountry", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("beneficaryCountry.countryId", bankCountryId));
		}

		if(serviceGroupId != null){
			criteria.setFetchMode("beneficaryAccount.servicegropupId", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.servicegropupId", "servicegropupId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("servicegropupId.serviceGroupId", serviceGroupId));
		}

		if(agentBankId != null){
			criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.bank", "exBankMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("exBankMaster.bankId", agentBankId));
		}

		if(agentBankBranchId != null){
			criteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.bankBranch", "exBankBranch", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("exBankBranch.bankBranchId", agentBankBranchId));
		}

		if(currencyId != null){
			criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.currencyId", "currency", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("currency.currencyId", currencyId));
		}

		if(serviceProviderBankId != null){
			criteria.setFetchMode("beneficaryAccount.serviceProvider", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.serviceProvider", "serviceProvider", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("serviceProvider.bankId", serviceProviderBankId));
		}

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BeneficaryAccount> accountNumberList = (List<BeneficaryAccount>) findAll(criteria);

		return accountNumberList;*/

		// fetching the records if duplicate
		List<BeneficaryAccount> accountNumberList = new ArrayList<BeneficaryAccount>();

		String sql = null;
		if(masterSeqId != null){
			sql = "SELECT * FROM EX_BENEFICARY_ACCOUNT F"
					+ " WHERE  F.BENEFICARY_COUNTRY = "
					+ bankCountryId
					+ " AND    F.SERVICE_GROUP_ID = "
					+ serviceGroupId
					+ " AND    F.CURRENCY_ID  =  "
					+ currencyId
					+ " AND    F.SERVICE_PROVIDER = "
					+ serviceProviderBankId
					+ " AND F.BENEFICARY_MASTER_SEQ_ID = "
					+ masterSeqId
					+ " AND    F.BANK_ID = "
					+ agentBankId
					+ " AND    F.BANK_BRANCH_ID = "
					+ agentBankBranchId
					+ " AND    F.BANK_ACCOUNT_NUMBER is null ";
		}else{
			sql = "SELECT * FROM EX_BENEFICARY_ACCOUNT F"
					+ " WHERE  F.BENEFICARY_COUNTRY = "
					+ bankCountryId
					+ " AND    F.SERVICE_GROUP_ID = "
					+ serviceGroupId
					+ " AND    F.CURRENCY_ID  =  "
					+ currencyId
					+ " AND    F.SERVICE_PROVIDER = "
					+ serviceProviderBankId
					+ " AND    F.BANK_ID = "
					+ agentBankId
					+ " AND    F.BANK_BRANCH_ID = "
					+ agentBankBranchId
					+ " AND    F.BANK_ACCOUNT_NUMBER is null ";
		}

		SQLQuery query = getSession().createSQLQuery(sql).addEntity(BeneficaryAccount.class);
		accountNumberList = query.list();

		return accountNumberList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BeneficaryAccount> checkDuplicateForBankingChannel(BigDecimal masterSeqId,BigDecimal bankCountryId,BigDecimal serviceGroupId, BigDecimal bankId, BigDecimal bankBranchId,String accountNumber, BigDecimal currencyId) {

		/*DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");

		if(masterSeqId != null){
			criteria.setFetchMode("beneficaryAccount.beneficaryMaster", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", masterSeqId));
		}

		if(bankCountryId != null){
			criteria.setFetchMode("beneficaryAccount.beneficaryCountry", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.beneficaryCountry", "beneficaryCountry", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("beneficaryCountry.countryId", bankCountryId));
		}

		if(serviceGroupId != null){
			criteria.setFetchMode("beneficaryAccount.servicegropupId", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.servicegropupId", "servicegropupId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("servicegropupId.serviceGroupId", serviceGroupId));
		}

		if(bankId != null){
			criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.bank", "exBankMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		}

		if(currencyId != null){
			criteria.setFetchMode("beneficaryAccount.currencyId", FetchMode.JOIN);
			criteria.createAlias("beneficaryAccount.currencyId", "currency", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("currency.currencyId", currencyId));
		}

		if(accountNumber != null){
			criteria.add(Restrictions.eq("beneficaryAccount.bankAccountNumber", accountNumber));
		}*/
		
		// new logic need to check weather branch id need to add or not - ex_bank_master added one column
		String checkBranchId = fetchBankMasterBranchCheck(bankCountryId,bankId);
		
		// fetching the records if duplicate
		List<BeneficaryAccount> accountNumberList = new ArrayList<BeneficaryAccount>();
		
		String sql = null;
		
		if(checkBranchId != null && checkBranchId.equalsIgnoreCase(Constants.Y)){
			if(masterSeqId != null){
				sql = "SELECT * FROM EX_BENEFICARY_ACCOUNT F"
						+ " WHERE  F.BENEFICARY_COUNTRY = "
						+ bankCountryId
						+ " AND    F.SERVICE_GROUP_ID = "
						+ serviceGroupId
						+ " AND    F.CURRENCY_ID  =  "
						+ currencyId
						+ " AND    F.BANK_ACCOUNT_NUMBER  =  '" + accountNumber + "'"
						+ " AND    F.BANK_ID = "
						+ bankId
						+ " AND    F.BANK_BRANCH_ID = "
						+ bankBranchId
						+ " AND F.BENEFICARY_MASTER_SEQ_ID = "
						+ masterSeqId
						+ " AND    F.BANK_ACCOUNT_NUMBER is not null ";
			}else{
				sql = "SELECT * FROM EX_BENEFICARY_ACCOUNT F"
						+ " WHERE  F.BENEFICARY_COUNTRY = "
						+ bankCountryId
						+ " AND    F.SERVICE_GROUP_ID = "
						+ serviceGroupId
						+ " AND    F.CURRENCY_ID  =  "
						+ currencyId
						+ " AND    F.BANK_ACCOUNT_NUMBER  =  '" + accountNumber + "'"
						+ " AND    F.BANK_ID = "
						+ bankId
						+ " AND    F.BANK_BRANCH_ID = "
						+ bankBranchId
						+ " AND    F.BANK_ACCOUNT_NUMBER is not null ";
			}
		}else{
			if(masterSeqId != null){
				sql = "SELECT * FROM EX_BENEFICARY_ACCOUNT F"
						+ " WHERE  F.BENEFICARY_COUNTRY = "
						+ bankCountryId
						+ " AND    F.SERVICE_GROUP_ID = "
						+ serviceGroupId
						+ " AND    F.CURRENCY_ID  =  "
						+ currencyId
						+ " AND    F.BANK_ACCOUNT_NUMBER  =  '" + accountNumber + "'"
						+ " AND    F.BANK_ID = "
						+ bankId
						+ " AND F.BENEFICARY_MASTER_SEQ_ID = "
						+ masterSeqId
						+ " AND    F.BANK_ACCOUNT_NUMBER is not null ";
			}else{
				sql = "SELECT * FROM EX_BENEFICARY_ACCOUNT F"
						+ " WHERE  F.BENEFICARY_COUNTRY = "
						+ bankCountryId
						+ " AND    F.SERVICE_GROUP_ID = "
						+ serviceGroupId
						+ " AND    F.CURRENCY_ID  =  "
						+ currencyId
						+ " AND    F.BANK_ACCOUNT_NUMBER  =  '" + accountNumber + "'"
						+ " AND    F.BANK_ID = "
						+ bankId
						+ " AND    F.BANK_ACCOUNT_NUMBER is not null ";
			}
		}

		SQLQuery query = getSession().createSQLQuery(sql).addEntity(BeneficaryAccount.class);
		accountNumberList = query.list();

		return accountNumberList;
	}
	
	public String fetchBankMasterBranchCheck(BigDecimal bankCountryId,BigDecimal bankId){
		
		String bankBranchStatus = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		
		criteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", bankCountryId));
		
		criteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankMaster> chkbankBranch = (List<BankMaster>) findAll(criteria);
		
		if(chkbankBranch != null && chkbankBranch.size() != 0){
			BankMaster bankMaster = chkbankBranch.get(0);
			bankBranchStatus = bankMaster.getBankBranchCheck();
		}
		
		return bankBranchStatus;
	}

	@Override
	public List<BeneficaryRelationship> checkBenificaryRelationExist(BigDecimal beneficaryMasterSeqId, BigDecimal beneficaryAccountSeqId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");

		criteria.setFetchMode("beneficaryRelationship.beneficaryMaster", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryMaster", "beneficaryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryMaster.beneficaryMasterSeqId", beneficaryMasterSeqId));

		criteria.setFetchMode("beneficaryRelationship.customerId", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.customerId", "customerId", JoinType.INNER_JOIN);

		criteria.setFetchMode("beneficaryRelationship.beneficaryAccount", FetchMode.JOIN);
		criteria.createAlias("beneficaryRelationship.beneficaryAccount", "beneficaryAccount", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneficaryAccount.beneficaryAccountSeqId", beneficaryAccountSeqId));

		//criteria.add(Restrictions.eq("beneficaryRelationship.isActive", Constants.Yes));

		criteria.add(Restrictions.eq("beneficaryMaster.isActive", Constants.Yes));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneficaryRelationship> beneficaryRship = (List<BeneficaryRelationship>) findAll(criteria);

		return beneficaryRship;
	}
}

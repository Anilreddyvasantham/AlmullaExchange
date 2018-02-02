package com.amg.exchange.remittance.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.ViewRemarks;
import com.amg.exchange.remittance.dao.ICustomerBankDao;
import com.amg.exchange.remittance.model.BankDebitCardLengthViewModel;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.CustomerDBCardDetailsView;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.treasury.bean.BankPrefixDataTable;
import com.amg.exchange.treasury.model.BankPrefix;
import com.amg.exchange.util.Constants;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

@SuppressWarnings("rawtypes")
@Repository
public class CustomerBankDaoImpl extends CommonDaoImpl implements ICustomerBankDao,Serializable {
 
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerBank> customerBanksAvailable(BigDecimal customerId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(CustomerBank.class,"customerBank");
		
		/*criteria.setFetchMode("customerBank.fsBankMaster", FetchMode.JOIN);
		criteria.createAlias("customerBank.fsBankMaster", "fsBankMaster",JoinType.INNER_JOIN);*/
		
		criteria.setFetchMode("customerBank.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerBank.fsCustomer", "fsCustomer",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		//criteria.add(Restrictions.eq("fsBankMaster.bankId", fsBankMaster));
		criteria.add(Restrictions.eq("customerBank.collectionMode",Constants.COLLECTIONMODE));
		criteria.add(Restrictions.eq("customerBank.isActive",Constants.Y));
		
		criteria.addOrder(Order.desc("customerBank.bankCode"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CustomerBank>  banksList=(List<CustomerBank>)findAll(criteria);
		 
		return banksList;
	}
	

	@Override
	public void save(CustomerBank customerDetails) {
		getSession().save(customerDetails);
	}
	
	@Override
	public  Boolean isExist(BigDecimal fsCustomer,BigDecimal bankId,String cardno){
		DetachedCriteria criteria=DetachedCriteria.forClass(CustomerBank.class,"customerBank");
		
		criteria.setFetchMode("customerBank.fsBankMaster", FetchMode.JOIN);
		criteria.createAlias("customerBank.fsBankMaster", "fsBankMaster",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerBank.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerBank.fsCustomer", "fsCustomer",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCustomer.customerId",fsCustomer));
		criteria.add(Restrictions.eq("fsBankMaster.bankId",bankId));
		criteria.add(Restrictions.eq("customerBank.debitCard",cardno));
		
		List<CustomerBank> list=(List<CustomerBank>)findAll(criteria);
		if(list.size()>0){
			return true;
		}
		else{
			return false;
		}
	
	}

	@Override
	public List<CustomerDBCardDetailsView> customerBanksView(BigDecimal customerId) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria=DetachedCriteria.forClass(CustomerDBCardDetailsView.class,"customerDBCardDetailsView");
		
		criteria.add(Restrictions.eq("customerDBCardDetailsView.customerId", customerId));
		
		List<CustomerDBCardDetailsView> listCardDetails=(List<CustomerDBCardDetailsView>)findAll(criteria);
		
		return listCardDetails;
	}

	@Override
	public List<BankDebitCardLengthViewModel> bankCardLengthView(String bankcode) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria=DetachedCriteria.forClass(BankDebitCardLengthViewModel.class,"bankDebitCardLengthViewModel");
		criteria.add(Restrictions.eq("bankDebitCardLengthViewModel.bankCode", bankcode));
		List<BankDebitCardLengthViewModel> listCardLength=(List<BankDebitCardLengthViewModel>)findAll(criteria);
		return listCardLength;
	}

	//Added on 06102015
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerBank> customerBanks(BigDecimal customerId, BigDecimal bankId) {
		
		DetachedCriteria criteria=DetachedCriteria.forClass(CustomerBank.class,"customerBank");
		
		criteria.setFetchMode("customerBank.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerBank.fsCustomer", "fsCustomer",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId",customerId));
		
		criteria.setFetchMode("customerBank.fsBankMaster", FetchMode.JOIN);
		criteria.createAlias("customerBank.fsBankMaster", "fsBankMaster",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBankMaster.bankId", bankId));
		
		criteria.add(Restrictions.eq("customerBank.collectionMode",Constants.COLLECTIONMODE));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CustomerBank> banksList = (List<CustomerBank>)findAll(criteria);
		 
		return banksList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewBankDetails> customerBanks(BigDecimal customerId, String bankCode) {
		List<ViewBankDetails> lstbankDetails = new ArrayList<ViewBankDetails>();
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerBank.class, "customerBank");
		criteria.setFetchMode("customerBank.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerBank.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.setFetchMode("customerBank.fsBankMaster", FetchMode.JOIN);
		criteria.createAlias("customerBank.fsBankMaster", "fsBankMaster", JoinType.INNER_JOIN);
		if (bankCode != null) {
			criteria.add(Restrictions.eq("customerBank.bankCode", bankCode));
		}
		criteria.add(Restrictions.eq("customerBank.isActive", Constants.Yes));
		criteria.add(Restrictions.eq("customerBank.collectionMode", Constants.COLLECTIONMODE));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CustomerBank> banksList = (List<CustomerBank>) findAll(criteria);
		
		if(banksList != null && banksList.size() != 0){
			for (CustomerBank customerBank : banksList) {
				ViewBankDetails lstofBanks = new ViewBankDetails();
				lstofBanks.setChequeBankId(customerBank.getFsBankMaster().getBankId());
				lstofBanks.setChequeBankCode(customerBank.getBankCode());
				// for displaying purpose
				if (customerBank.getBankCode() != null) {
					HashMap bankNameandShortName = getBankandShortNameFromView(customerBank.getBankCode());
					Iterator<Map.Entry<String, String>> entries = bankNameandShortName.entrySet().iterator();
					if (bankNameandShortName!=null && !bankNameandShortName.isEmpty()) {
						while (entries.hasNext()) {
							Map.Entry<String, String> entry = entries.next();
							lstofBanks.setBankFullName(entry.getKey() == null ? "" : entry.getKey());
							lstofBanks.setBankShortName(entry.getValue() == null ? "" : entry.getValue());
						}
					}
				}
				lstbankDetails.add(lstofBanks);
			}
		}else{
			DetachedCriteria dcriteria = DetachedCriteria.forClass(ViewBankDetails.class, "viewBankDetails");
			
			dcriteria.add(Restrictions.eq("viewBankDetails.chequeBankCode", bankCode));
			List<ViewBankDetails> vbanks = (List<ViewBankDetails>) findAll(dcriteria);
			if(vbanks != null && vbanks.size() != 0){
				lstbankDetails.addAll(vbanks);
			}
		}
		
		
		return lstbankDetails;
	}
	
	
	public HashMap getBankandShortNameFromView(String bankCode) {
		HashMap<String, String> bankNameandShortName = new HashMap<String, String>();
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewBankDetails.class, "bankDetailsFromView");
		criteria.add(Restrictions.eq("bankDetailsFromView.chequeBankCode", bankCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<ViewBankDetails> bankFullNameDesc = (List<ViewBankDetails>) findAll(criteria);
		if (bankFullNameDesc.size() != 0) {
			ViewBankDetails bankdetails = bankFullNameDesc.get(0);
			bankNameandShortName.put(bankdetails.getBankFullName(), bankdetails.getBankShortName());
		}
		return bankNameandShortName;
	}
	
	/*public String getBankNameFromView(String bankCode) {
		String bankName = null;
		DetachedCriteria criteria=DetachedCriteria.forClass(ViewBankDetails.class,"bankDetailsFromView");

		criteria.add(Restrictions.eq("bankDetailsFromView.chequeBankCode", bankCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewBankDetails> bankFullNameDesc = (List<ViewBankDetails>)findAll(criteria);
		
		if(bankFullNameDesc.size() != 0){
			ViewBankDetails bankdetails = bankFullNameDesc.get(0);
			bankName = bankdetails.getBankFullName();
		}
		
		return bankName;
	}*/
	
	@Override
	public List<ViewBankDetails> getCustomerLocalBankListFromView(BigDecimal countryId,String bankCode) {
		
		DetachedCriteria criteria=DetachedCriteria.forClass(ViewBankDetails.class,"bankDetailsFromView");

		criteria.add(Restrictions.eq("bankDetailsFromView.applicationCountryId", countryId));
		
		criteria.add(Restrictions.eq("bankDetailsFromView.chequeBankCode", bankCode));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<ViewBankDetails> listCardLength = (List<ViewBankDetails>)findAll(criteria);
		
		return listCardLength;
	}


	@Override
	public List<CustomerBank> fetchcustomerBanksDetails(BigDecimal customerId,String bankCode) {
		DetachedCriteria criteria=DetachedCriteria.forClass(CustomerBank.class,"customerBank");
		
		/*criteria.setFetchMode("customerBank.fsBankMaster", FetchMode.JOIN);
		criteria.createAlias("customerBank.fsBankMaster", "fsBankMaster",JoinType.INNER_JOIN);*/
		
		criteria.setFetchMode("customerBank.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerBank.fsCustomer", "fsCustomer",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		if(bankCode != null){
			criteria.add(Restrictions.eq("customerBank.bankCode", bankCode));
		}
		criteria.add(Restrictions.eq("customerBank.collectionMode",Constants.COLLECTIONMODE));
		
		criteria.add(Restrictions.eq("customerBank.isActive",Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CustomerBank>  banksList=(List<CustomerBank>)findAll(criteria);
		 
		return banksList;
	}

	@Override
	public List<CustomerBank> checkDebitCardNoAlreadyExist(String debitcardNo)
	{
		DetachedCriteria criteria=DetachedCriteria.forClass(CustomerBank.class,"customerBank");
		
		criteria.add(Restrictions.eq("customerBank.debitCard", debitcardNo));
		
		criteria.add(Restrictions.eq("customerBank.collectionMode",Constants.COLLECTIONMODE));
		
		criteria.add(Restrictions.eq("customerBank.isActive",Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CustomerBank>  banksList=(List<CustomerBank>)findAll(criteria);
		
		return banksList;
	}
	
	@Override
	public List<ViewBankDetails>  getChequeBnakIdFromView(String bankCode) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewBankDetails.class, "bankDetailsFromView");
		criteria.add(Restrictions.eq("bankDetailsFromView.chequeBankCode", bankCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<ViewBankDetails> bankFullNameDesc = (List<ViewBankDetails>) findAll(criteria);
		
		return bankFullNameDesc;
	}


	@Override
	public void saveOrUpdate(CustomerBank customerDetails) {
		if(customerDetails.getCustomerBankId() != null){
			getSession().update(customerDetails); 
		}else if(customerDetails.getCustomerBankId() == null){
			getSession().save(customerDetails);
		}
	}

	@Override
	public List<BankPrefix> getBankPrefix(String bankCode,BigDecimal bankId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(BankPrefix.class, "bankPrefix");
		criteria.add(Restrictions.eq("bankPrefix.bankCode", bankCode));
		criteria.add(Restrictions.eq("bankPrefix.isActive", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankPrefix> lstBankPrefix = (List<BankPrefix>) findAll(criteria);
		return lstBankPrefix;
	}
	
	@Override
	public boolean checkDebitCardWithDeActiveStatus(String debitcardNo)
	{
		boolean checkdeactiveBanksSts=false;
		String sql="SELECT COUNT(*) FROM EX_CUSTOMER_BANK WHERE PKG_ENCRYPT.DECRYPT_STRING(DEBIT_CARD,'"+Constants.EncriptionKey+"') = '"+debitcardNo+"' "
				+ " and ISACTIVE='"+Constants.D+"'";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		List<BigDecimal> rows = query.list();
		
		for (BigDecimal deactiveBanks : rows) {
			if (deactiveBanks != null && deactiveBanks.compareTo(BigDecimal.ZERO) != 0) {
				checkdeactiveBanksSts = true;
				break;
			}
		}
		
		
		return checkdeactiveBanksSts;
	}
	
	@Override
	public void deactivateCustomerBank(BigDecimal customerBankPkId,String userName,String remarks)
	{
		CustomerBank customerBank=(CustomerBank) getSession().get(CustomerBank.class, customerBankPkId);
		
		customerBank.setIsActive(Constants.D);
		customerBank.setStsRemarks(remarks);
		customerBank.setModifiedBy(userName);
		customerBank.setModifiedDate(new Date());
		
		getSession().update(customerBank);
	
	}
	
	@Override
	public List<BankPrefix> checkBankprefixExist(String bankPrefix)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BankPrefix.class, "bankPrefix");
		criteria.add(Restrictions.eq("bankPrefix.bankPrefix", bankPrefix));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<BankPrefix> lstBankPrefix = (List<BankPrefix>) findAll(criteria);
		return lstBankPrefix;
	}
	
	@Override
	public List<BigDecimal> checkDebitCardWithActiveStatus(String debitcardNo,String prefix,String suffix,BigDecimal bankId)
	{
		List<BigDecimal> lstCustmerId= new ArrayList<BigDecimal>();
		
		String sql="SELECT CUSTOMER_ID FROM EX_CUSTOMER_BANK WHERE PKG_ENCRYPT.DECRYPT_STRING(DEBIT_CARD,'"+Constants.EncriptionKey+"') = '"+debitcardNo+"' "
				+ " and ISACTIVE='"+Constants.Yes+"' AND BANK_PREFIX = '"+prefix+"' AND BANK_SUFFIX = '"+suffix+"' AND BANK_ID = "+bankId+" ";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		lstCustmerId = query.list();
		
		return lstCustmerId;
	}
	
	public List<CustomerIdProof> getCustomerBasedOnId(BigDecimal customerId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(
				CustomerIdProof.class, "customerIdProof");

		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer",
				JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId",
				JoinType.INNER_JOIN);
		
		criteria.setFetchMode("fsBizComponentDataByIdentityTypeId.fsBusinessComponent", FetchMode.JOIN);
		criteria.createAlias("fsBizComponentDataByIdentityTypeId.fsBusinessComponent", "fsBusinessComponent",
				JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.add(Restrictions.eq("customerIdProof.identityStatus", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CustomerIdProof> idproofList = (List<CustomerIdProof>) findAll(criteria);
		return idproofList;
	}
	
	@Override
	public List<BigDecimal> getCustomerbankIdForDeactivatee(String debitcardNo,String prefix,String suffix,BigDecimal bankId)
	{
	
		List<BigDecimal> lstCustmerId= new ArrayList<BigDecimal>();
		
		String sql="SELECT CUSTOMER_BANK_ID FROM EX_CUSTOMER_BANK WHERE PKG_ENCRYPT.DECRYPT_STRING(DEBIT_CARD,'"+Constants.EncriptionKey+"') = '"+debitcardNo+"' "
				+ " AND ISACTIVE='"+Constants.Yes+"' AND BANK_PREFIX = '"+prefix+"' AND BANK_SUFFIX = '"+suffix+"' AND BANK_ID = "+bankId+" ";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		//BigDecimal custmerId = (BigDecimal) query.uniqueResult();
		lstCustmerId = query.list();
		
		return lstCustmerId;
	}
	
	
	@Override
	public List<BankPrefix> getBankPrefix(String  bankCode)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BankPrefix.class, "bankPrefix");
		criteria.add(Restrictions.eq("bankPrefix.bankCode", bankCode));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<BankPrefix> lstBankPrefix = (List<BankPrefix>) findAll(criteria);
		return lstBankPrefix;
	}
	
	@Override
	public List<BankPrefix> getBankPrefixBasedOnPrefix(String  bankCode,String bankPrefix)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BankPrefix.class, "bankPrefix");
		criteria.add(Restrictions.eq("bankPrefix.bankCode", bankCode));
		criteria.add(Restrictions.eq("bankPrefix.bankPrefix", bankPrefix));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<BankPrefix> lstBankPrefix = (List<BankPrefix>) findAll(criteria);
		return lstBankPrefix;
	}


	@Override
	public void saveOrUpdate(BankPrefix bankPrefix) {
		getSession().saveOrUpdate(bankPrefix);
	}


	@Override
	public void updateBankPrefix(BankPrefixDataTable bankPrefixDataTable) {
		BankPrefix bankPrefix = (BankPrefix) getSession().get(BankPrefix.class, bankPrefixDataTable.getBankPrefixPkId());
		
		if (bankPrefixDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			bankPrefix.setIsActive(Constants.U);
			bankPrefix.setApprovedBy(null);
			bankPrefix.setApprovedDate(null);
			bankPrefix.setModifiedBy(bankPrefixDataTable.getModifiedBy());
			bankPrefix.setModifiedDate(bankPrefixDataTable.getModifiedDate());
		} else if (bankPrefixDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			bankPrefix.setIsActive(Constants.D);
			bankPrefix.setApprovedBy(null);
			bankPrefix.setApprovedDate(null);
			bankPrefix.setModifiedBy(bankPrefixDataTable.getModifiedBy());
			bankPrefix.setModifiedDate(bankPrefixDataTable.getModifiedDate());
			bankPrefix.setRemarks(bankPrefixDataTable.getRemarks());
		}
		getSession().update(bankPrefix);
		
		
	}


	@Override
	public void deleteFromDb(BigDecimal bankPrefixpkId) {
		
		BankPrefix bankPrefix = (BankPrefix) getSession().get(BankPrefix.class, bankPrefixpkId);
		
		getSession().delete(bankPrefix);
	}
	
	@Override
	public String approve(BigDecimal bankPrefixPkId,String userName) {
		String approveMsg;
		BankPrefix bankPrefix=(BankPrefix) getSession().get(BankPrefix.class, bankPrefixPkId);
		String approvedUser=bankPrefix.getApprovedBy();
		if(approvedUser==null)
		{
			bankPrefix.setModifiedBy(userName);
			bankPrefix.setModifiedDate(new Date());
			bankPrefix.setApprovedBy(userName);
			bankPrefix.setApprovedDate(new Date());
			bankPrefix.setIsActive(Constants.Yes);
			getSession().saveOrUpdate(bankPrefix);
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;
	}


	@Override
	public List<BankPrefix> getBankPrefixApproval(String bankCode) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankPrefix.class, "bankPrefix");
		criteria.add(Restrictions.eq("bankPrefix.bankCode", bankCode));
		criteria.add(Restrictions.eq("bankPrefix.isActive", Constants.U));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<BankPrefix> lstBankPrefix = (List<BankPrefix>) findAll(criteria);
				
		return lstBankPrefix;
	}


	@Override
	public List<ViewRemarks> getViewRemarks() {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewRemarks.class, "viewRemarks");
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<ViewRemarks> lstViewRemarks = (List<ViewRemarks>) findAll(criteria);
		return lstViewRemarks;
	}


	@Override
	public void saveAllDebitCardsBanks(List<CustomerBank> lstBankCards) {
		if(lstBankCards != null && lstBankCards.size() != 0){
			for (CustomerBank customerBank : lstBankCards) {
				if(customerBank.getCustomerBankId() != null && customerBank.getFsBankMaster().getBankId() != null &&  customerBank.getFsCustomer().getCustomerId() != null 
						&&  customerBank.getBankCode() != null &&  customerBank.getDebitCard() != null &&  customerBank.getCreatedBy() != null && customerBank.getCreatedDate() != null 
						&&  customerBank.getCustomerReference() != null){
					getSession().update(customerBank);
				}else if(customerBank.getCustomerBankId() == null && customerBank.getFsBankMaster().getBankId() != null &&  customerBank.getFsCustomer().getCustomerId() != null 
						&&  customerBank.getBankCode() != null &&  customerBank.getDebitCard() != null &&  customerBank.getCreatedBy() != null && customerBank.getCreatedDate() != null 
						&&  customerBank.getCustomerReference() != null){
					getSession().save(customerBank);
				}else{
					// wrong
					System.out.println(customerBank.getCustomerBankId());
				}
			}
		}
	}


	@Override
	public List<CustomerBank> fetchcustomerBanksDetails(String debitcardNo,	String prefix, String suffix, BigDecimal bankId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerBank.class, "customerBank");
		
		String sql = "PKG_ENCRYPT.DECRYPT_STRING(DEBIT_CARD,'"+Constants.EncriptionKey+"') = '"+debitcardNo+"' ";
		
		criteria.add(Restrictions.eq("customerBank.bankPrefix", prefix));
		criteria.add(Restrictions.eq("customerBank.bankSuffix", suffix));
		
		criteria.setFetchMode("customerBank.fsBankMaster", FetchMode.JOIN);
		criteria.createAlias("customerBank.fsBankMaster", "fsBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBankMaster.bankId", bankId));
		
		criteria.add(Restrictions.eq("customerBank.isActive", Constants.Yes));
		
		criteria.add(Restrictions.sqlRestriction(sql));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CustomerBank> lstcustomerBank = (List<CustomerBank>) findAll(criteria);

		return lstcustomerBank;
	}
}

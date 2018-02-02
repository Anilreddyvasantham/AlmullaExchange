package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.treasury.dao.ISpecialCustomerDealRequestDao;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.HighValueDealRequestView;
import com.amg.exchange.treasury.viewModel.SpecialCustomerValueDatedView;
import com.amg.exchange.util.Constants;

@Repository
public class SpecialCustomerDealRequestDaoImpl<T> extends CommonDaoImpl<T> implements ISpecialCustomerDealRequestDao<T> {

	

	@Override
	public String getNextDocumentSerialNumber(int countryId, int companyId,
			int documentId, int financialYear, String processIn) {

		int out=0;
	
		Connection connection=null;
		connection = getDataSourceFromHibernateSession();
		/*try {
			connection = DBConnection.getdataconnection();
		} catch (ClassNotFoundException | IOException
				| SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		CallableStatement cs;
		try {
			cs = connection.prepareCall(" { call EX_TO_GEN_NEXT_DOC_SERIAL_NO(?,?,?,?,?,?,?,?)}");
			cs.setInt(1, countryId);
			cs.setInt(2, companyId);
			cs.setInt(3, documentId);
			cs.setInt(4, financialYear);
			cs.setString(5, processIn);
			cs.registerOutParameter(6, java.sql.Types.INTEGER);
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			//cs.executeUpdate();
			cs.execute();
			out =cs.getInt(6);
			String a=cs.getString(7);
			String b=cs.getString(8);
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return String.valueOf(out);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<UserFinancialYear> getUserFinancialYear(Date currentDate) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFinancialYear.class, "userFinancialYear");

		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			Date S = formatter.parse(formatter.format(currentDate));
			System.out.println("Today : " + S);

			if(currentDate!=null){

				detachedCriteria.add(Restrictions.le("userFinancialYear.financialYearBegin", S))
				.add(Restrictions.ge("userFinancialYear.financialYearEnd", S));

			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		return (List<UserFinancialYear>) findAll(detachedCriteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerIdProof> dataCust(String id) {
			DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
			
			criteria.add(Restrictions.eq("customerIdProof.identityInt", id));
			
			criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
			criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
			
			return (List<CustomerIdProof>) findAll(criteria);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveData(CustomerSpecialDealRequest customerSpecialDealRequest) {
		
		saveOrUpdate((T) customerSpecialDealRequest);
	}


	@Override
	public BigDecimal getCustomerDetails(BigDecimal customerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "customerDetails");
		criteria.setFetchMode("customerDetails.fsBizComponentDataByCustomerTypeId",FetchMode.JOIN);
		criteria.createAlias("customerDetails.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBizComponentDataByCustomerTypeId.componentDataId", customerId));
		
		return (BigDecimal) findAll(criteria);
	}

	@Override
	public List<CustomerSpecialDealRequest> getDocumentSerialityNumberFromDB() {
		DetachedCriteria criteria=DetachedCriteria.forClass(CustomerSpecialDealRequest.class);
		return (List<CustomerSpecialDealRequest>) findAll(criteria);
		
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Document> getDocumentDescription(BigDecimal id,BigDecimal languageId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Document.class,"documentIdDetalis");
		criteria.add(Restrictions.eq("documentIdDetalis.documentCode", id));
		
		criteria.setFetchMode("documentIdDetalis.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("documentIdDetalis.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		
		// Add Language Condition
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		
		return (List<Document>) findAll(criteria);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<CustomerSpecialDealRequest> getCustSpDocNo(BigDecimal docFinyearId,BigDecimal docNO,BigDecimal companyId,BigDecimal documentId) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomerSpecialDealRequest.class, "customerSpecialDealRequest");
		
		detachedCriteria.add(Restrictions.eq("customerSpecialDealRequest.documentNumber", docNO));
		
		detachedCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCompanyMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCompanyMaster", "customerSpeacialDealReqCompanyMaster", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("customerSpeacialDealReqCompanyMaster.companyId", companyId));
		
		detachedCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqDocument", FetchMode.JOIN);
		detachedCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqDocument", "customerSpeacialDealReqDocument", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("customerSpeacialDealReqDocument.documentID", documentId));
		
		/*detachedCriteria.setFetchMode("customerSpecialDealRequest.documentFinancialYear", FetchMode.JOIN);
		detachedCriteria.createAlias("customerSpecialDealRequest.documentFinancialYear", "documentFinancialYear", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("documentFinancialYear.financialYearID", docFinyearId));*/
		
		detachedCriteria.add(Restrictions.eq("documentFinancialYear.documentFinanYear", docFinyearId)); // document_finance_year
		
		detachedCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCustomer", FetchMode.JOIN);
		detachedCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCustomer", "customerSpeacialDealReqCustomer", JoinType.INNER_JOIN);
		
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CustomerSpecialDealRequest> data=(List<CustomerSpecialDealRequest>) findAll(detachedCriteria);
		
		return data;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomerIdentity(BigDecimal custID) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class, "customer");
		detachedCriteria.add(Restrictions.eq("customer.customerId", custID));
		detachedCriteria.setFetchMode("customer.fsCustomerIdProofs", FetchMode.JOIN);
		detachedCriteria.createAlias("customer.fsCustomerIdProofs", "fsCustomerIdProofs", JoinType.LEFT_OUTER_JOIN);
		List<Customer> data = (List<Customer>) findAll(detachedCriteria);
		
		return data;
	}

	@Override
	public int getValidUpTo(String custType) {
		DetachedCriteria criteria=DetachedCriteria.forClass(SpecialCustomerValueDatedView.class,"specialCustomerValueDatedView");
		criteria.add(Restrictions.eq("specialCustomerValueDatedView.customerType",custType));
		List<SpecialCustomerValueDatedView> specialCustomerValueDatedView=(List<SpecialCustomerValueDatedView>) findAll(criteria);
		int validdate=0;
		for(SpecialCustomerValueDatedView spCusValDateView:specialCustomerValueDatedView){
		 validdate= Integer.parseInt( spCusValDateView.getValidDays().toString());
		}
		return validdate ;
	}

	@Override
	public String getBankCountryNameForUpdate(BigDecimal bankCountryId,BigDecimal languageId) {
		
		DetachedCriteria criteria=DetachedCriteria.forClass(CountryMasterDesc.class,"countryMasterDesc");
		
		
		criteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId",bankCountryId));
		
		criteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId",languageId));
		

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CountryMasterDesc> data = (List<CountryMasterDesc>) findAll(criteria);
		if(data!=null && data.size()>0){
		return data.get(0).getCountryName();
		}else{
			return null;
		}
		
		
		
		/*String hqlQuery="select bankAcctNo from  BankAccountDetails   where bankAcctDetId =  :bankAccountId";

		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("bankAccountId", bankAccountId);

		List<String> lstIdentity =query.list();

		String bankAcctNo=null;
		if(lstIdentity.size()>0)
		{
			bankAcctNo=lstIdentity.get(0);
		}
		return bankAcctNo;*/
		
	}

	@Override
	public String getBankNameForUpdate(BigDecimal bankId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(BankMaster.class,"bankMaster");
		criteria.add(Restrictions.eq("bankMaster.bankId",bankId));
		List<BankMaster> data = (List<BankMaster>) findAll(criteria);
		return data.get(0).getBankFullName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getCurrencyForUpdate(BigDecimal currencyId) {
		String currencyName = null;
		DetachedCriteria criteria=DetachedCriteria.forClass(CurrencyMaster.class,"currencyMaster");
		criteria.add(Restrictions.eq("currencyMaster.currencyId",currencyId));
		List<CurrencyMaster> data = (List<CurrencyMaster>) findAll(criteria);
		if(data.size() != 0){
			CurrencyMaster currency = data.get(0);
			currencyName =  currency.getCurrencyName();
		}
		return currencyName;
	}

	@Override
	public String getBankAccountNumberForUpdate(BigDecimal bankAccountId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(BankAccountDetails.class,"bankAccountDetails");
		criteria.add(Restrictions.eq("bankAccountDetails.bankAcctDetId",bankAccountId));
		List<BankAccountDetails> data = (List<BankAccountDetails>) findAll(criteria);
		return data.get(0).getBankAcctNo();
	}

	@Override
	public String getCivilIdForUpadate(BigDecimal customerId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(CustomerIdProof.class,"customerIdProof");
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId",customerId));
		List<CustomerIdProof> data = (List<CustomerIdProof>) findAll(criteria);
		return data.get(0).getIdentityInt();
	}

	@Override
	public String getUserFinancialYearForUpadate(BigDecimal financialYearId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(UserFinancialYear.class,"userFinancialYear");
		criteria.add(Restrictions.eq("userFinancialYear.financialYearID",financialYearId));
		List<UserFinancialYear> data = (List<UserFinancialYear>) findAll(criteria);
		return data.get(0).getFinancialYear().toString();
	}

	@Override
	public String getCompanyNameForUpdate(BigDecimal companyId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(CompanyMasterDesc.class,"companyMasterDesc");
		criteria.setFetchMode("companyMasterDesc.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId",companyId));
		List<CompanyMasterDesc> data = (List<CompanyMasterDesc>) findAll(criteria);
		return data.get(0).getCompanyName();
	}

	@Override
	public String getDocumentNameForUpdate(BigDecimal documentId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Document.class,"document");
		criteria.add(Restrictions.eq("document.documentID",documentId));
		List<Document> data = (List<Document>) findAll(criteria);
		return data.get(0).getDocumentDesc();
	}

	@Override
	public void updateFCAmount(BigDecimal customerSpecialDealReqId,
			BigDecimal fcAmountForUpdate,String userName) {
		
		CustomerSpecialDealRequest cusSpDeal=(CustomerSpecialDealRequest) getSession().get(CustomerSpecialDealRequest.class, customerSpecialDealReqId);
		cusSpDeal.setForeignCurrencyAmount(fcAmountForUpdate);
		cusSpDeal.setModifiedBy(userName);
		cusSpDeal.setModifiedDate(new Date());
		getSession().update(cusSpDeal);
	}

	

	@Override
	public List<CompanyMasterDesc> getAllCompanyList(BigDecimal companyId,
			BigDecimal languageId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CompanyMasterDesc.class, "companyMasterDesc");
		criteria.setFetchMode("companyMasterDesc.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		criteria.setFetchMode("companyMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		criteria.addOrder(Order.asc("companyMasterDesc.companyName"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<CompanyMasterDesc>)findAll(criteria);
	}

	@Override
	public List<BankApplicability> getBankAccordingToBankCountry(BigDecimal countryId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class, "bankMaster");
		
		
		return (List<BankApplicability>)findAll(criteria);
	}

/*	@Override
	public List<BankApplicability> populateBankBasedOnBankCountry(BigDecimal bankCountryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
		
		criteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", bankCountryId));
		return (List<BankApplicability>)findAll(criteria);
	}*/

	@Override
	public List<BankMaster> getBankListBasedOnCountry(BigDecimal countryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		criteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));
		criteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		//criteria.addOrder(Order.asc("bankMaster.bankFullName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<BankMaster>)findAll(criteria);
	}

	@Override
	public List<BankApplicability> populateBankAccordingToBankCountry(List<BankMaster> bankList) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class, "bankMasterList");
		criteria.setFetchMode("bankMasterList.bankMaster", FetchMode.JOIN);
		criteria.createAlias("bankMasterList.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		Set<BigDecimal> bank=new HashSet<BigDecimal>();

			for(int i=0;i<bankList.size();i++){
				BankMaster ba=bankList.get(i);
				bank.add(ba.getBankId());
			}
			
		criteria.add(Restrictions.in("bankMasterList.bankMaster.bankId", bank));
	 
		//criteria.setProjection(Projections.distinct(Projections.property("bankId")));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankApplicability>)findAll(criteria);
	}



	@Override
	public BigDecimal getDocumentPk(BigDecimal documentCode) {
		
		BigDecimal documentId = null;
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class, "document");
		criteria.add(Restrictions.eq("document.documentCode", documentCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<Document> documentList = (List<Document>) findAll(criteria);
		
		if(documentList != null && documentList.size() != 0){
			Document doc = documentList.get(0);
			documentId = doc.getDocumentID();
		}
		
		return documentId;
	}
	
	
	@Override
	public List<BankAccountDetails> getCurrencyBasedOnCountry(BigDecimal bankCountryId){
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		
		criteria.setFetchMode("bankAccountDetails.fsCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("bankAccountDetails.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("bankAccountDetails.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("exBankMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("exBankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", bankCountryId));
		
		criteria.add(Restrictions.eq("bankAccountDetails.recordStatus", Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		
		List<BankAccountDetails> bankaccounlist =(List<BankAccountDetails>) findAll(criteria);
		List<BankAccountDetails> bankaccounNewlist =new ArrayList<BankAccountDetails>();
		List<BigDecimal> duplicateCheck =new ArrayList<BigDecimal>();
		if(bankaccounlist.size()!=0){
			for(BankAccountDetails bankaccountDe :bankaccounlist){
				if(!duplicateCheck.contains(bankaccountDe.getFsCurrencyMaster().getCurrencyId())){
					duplicateCheck.add(bankaccountDe.getFsCurrencyMaster().getCurrencyId());
					bankaccounNewlist.add(bankaccountDe);
				}
			}
		}
		
		return bankaccounNewlist;
		
	}
	
	
	@Override
	public List<HighValueDealRequestView> getHighValueDealRequestDetails(BigDecimal application,BigDecimal currencyId){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(HighValueDealRequestView.class, "highValueDealRequestView");
		
		criteria.add(Restrictions.eq("highValueDealRequestView.countryId", application));
		if(currencyId!=null){
			criteria.add(Restrictions.eq("highValueDealRequestView.currencyId", currencyId));	
		}
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<HighValueDealRequestView> documentList=(List<HighValueDealRequestView>) findAll(criteria);
		
		return documentList;
	}
	
	@Override
	public List<CurrencyOtherInformation> getCurrencyDetails(BigDecimal currencyId){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyOtherInformation.class, "currencyOtherInformation");
		
		
		criteria.setFetchMode("currencyOtherInformation.exCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("currencyOtherInformation.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exCurrencyMaster.currencyId", currencyId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CurrencyOtherInformation> currencyList=(List<CurrencyOtherInformation>) findAll(criteria);
		
		return currencyList;
	}
	
	
}

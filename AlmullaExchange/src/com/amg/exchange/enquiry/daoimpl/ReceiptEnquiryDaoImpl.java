package com.amg.exchange.enquiry.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.enquiry.dao.IReceiptEnquiryDao;
import com.amg.exchange.enquiry.model.ViewFundSummaryDetails;
import com.amg.exchange.miscellaneous.model.ViewVwRemittanceTransaction;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.util.Constants;

@Repository
public class ReceiptEnquiryDaoImpl extends CommonDaoImpl implements IReceiptEnquiryDao {

	@Override
	public List<ViewVwRemittanceTransaction> toFetchAllDetils(BigDecimal documentFinancialYear, BigDecimal documentNo) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ViewVwRemittanceTransaction.class, "viewVwRemittanceTransaction");
		detachedCriteria.add(Restrictions.eq("viewVwRemittanceTransaction.documentFinYear", documentFinancialYear));
		detachedCriteria.add(Restrictions.eq("viewVwRemittanceTransaction.documentNo", documentNo));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewVwRemittanceTransaction> lstRemittanceTransaction=(List<ViewVwRemittanceTransaction>) findAll(detachedCriteria);
		return lstRemittanceTransaction;
	}

	@Override
	public List<CountryMasterDesc> getBankCounty(BigDecimal exchangeCountry,BigDecimal languageId) {
		List<CountryMasterDesc> lstCountryModeDesc = new ArrayList<CountryMasterDesc>();
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneCountryService.class, "beneCountryService");
		criteria.add(Restrictions.eq("beneCountryService.isActive", Constants.Yes));
		criteria.setFetchMode("beneCountryService.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("applicationCountryId.countryId", exchangeCountry));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BeneCountryService> lstBeneCountryService= (List<BeneCountryService>) findAll(criteria);
		if(lstBeneCountryService.size()>0)
		{
			HashSet<BigDecimal>  setCountry= new HashSet<BigDecimal>();
			for(BeneCountryService beneCountryService :lstBeneCountryService)
			{
				BigDecimal countryId=beneCountryService.getBeneCountryId().getCountryId();
				setCountry.add(countryId);
			}

			for(BigDecimal countryId:setCountry)
			{
				DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");

				dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
				dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster",  JoinType.INNER_JOIN);
				//dCriteria.add(Restrictions.eq("fsCountryMaster.isActive", Constants.Yes));
				dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

				dCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
				dCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType",  JoinType.INNER_JOIN);
				dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

				dCriteria.addOrder(Order.asc("countryMasterDesc.countryName"));

				dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

				List<CountryMasterDesc> countryMasterDescList=(List<CountryMasterDesc>)findAll(dCriteria); 

				if(countryMasterDescList.size()>0)
				{
					for(CountryMasterDesc countryMasterDesc:countryMasterDescList){
						lstCountryModeDesc.add(countryMasterDesc);
					}

				}
			}
		}
		return lstCountryModeDesc;
	}

	@Override
	public List<ViewFundSummaryDetails> toFetchAllDetilsFromView(BigDecimal exchangeCountry, BigDecimal bankCountry, BigDecimal bankId, BigDecimal currencyId,Date projectionDate) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ViewFundSummaryDetails.class, "viewFundSummaryDetails");
		if(exchangeCountry != null){
			detachedCriteria.add(Restrictions.eq("viewFundSummaryDetails.applicationCountryId", exchangeCountry));
		}
		if(bankCountry != null){
			detachedCriteria.add(Restrictions.eq("viewFundSummaryDetails.bankCountryId", bankCountry));
		}
		if(bankId != null){
			detachedCriteria.add(Restrictions.eq("viewFundSummaryDetails.bankId", bankId));
		}
		if(currencyId != null){
			detachedCriteria.add(Restrictions.eq("viewFundSummaryDetails.currencyId", currencyId));      
		}
		if (projectionDate != null) {
			detachedCriteria.add(Restrictions.eq("viewFundSummaryDetails.projectionDate", projectionDate));
		}
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewFundSummaryDetails> lstViewFundSummaryDetails=(List<ViewFundSummaryDetails>) findAll(detachedCriteria);
		return lstViewFundSummaryDetails;
	}

	@Override
	public List<TreasuryDealHeader> toFetchAllBankFromTT(BigDecimal bankCountry) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TreasuryDealHeader.class, "treasuryDealHeader");
		//country
		detachedCriteria.setFetchMode("treasuryDealHeader.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("treasuryDealHeader.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		//detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", bankCountry));
		//bank
		detachedCriteria.setFetchMode("treasuryDealHeader.exBankMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("treasuryDealHeader.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		//Company
		detachedCriteria.setFetchMode("treasuryDealHeader.fsCompanyMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("treasuryDealHeader.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		//ttAmount
		detachedCriteria.add(Restrictions.gt("treasuryDealHeader.ttAmount", new BigDecimal(0)));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TreasuryDealHeader> lstTreasuryDealHeader=(List<TreasuryDealHeader>) findAll(detachedCriteria);
		return lstTreasuryDealHeader;
	}

	@Override
	public BigDecimal toFetchFcAmount(BigDecimal treasureDealId,String lineType) {
		BigDecimal treasureDealDetailFCAmount=null;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TreasuryDealDetail.class, "treasuryDealDetail");
		detachedCriteria.setFetchMode("treasuryDealDetail.treasuryDealHeader", FetchMode.JOIN);
		detachedCriteria.createAlias("treasuryDealDetail.treasuryDealHeader", "treasuryDealHeader", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("treasuryDealHeader.treasuryDealHeaderId", treasureDealId));
		detachedCriteria.add(Restrictions.eq("treasuryDealDetail.lineType", lineType));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TreasuryDealDetail> lstTreasuryDealDetail=(List<TreasuryDealDetail>) findAll(detachedCriteria);
		if(lstTreasuryDealDetail.size() !=0){
			treasureDealDetailFCAmount=lstTreasuryDealDetail.get(0).getFcAmount();   
		}
		return treasureDealDetailFCAmount;
	}

	@Override
	public List<CountryMasterDesc> getBusinessCounty(BigDecimal languageId, BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster",  JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.businessCountry", Constants.Yes));
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		dCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType",  JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		dCriteria.addOrder(Order.asc("countryMasterDesc.countryName"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CountryMasterDesc> countryMasterDescList=(List<CountryMasterDesc>)findAll(dCriteria); 
		return countryMasterDescList;
	}

	@Override
	public List<Employee> toFetchEmployeeDetils(BigDecimal countryId, BigDecimal locationId, BigDecimal employeeId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Employee.class, "employee");
		dCriteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		dCriteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("employee.countryId", countryId));
		dCriteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", locationId));
		dCriteria.add(Restrictions.eq("employee.employeeId", employeeId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Employee> employeeList = (List<Employee>) findAll(dCriteria);
		return employeeList;
	}

	@Override
	public List<CountryBranch> getAllBranchDetails(BigDecimal countryId, BigDecimal locationId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		dCriteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryBranch.countryMaster", "countryMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("countryMaster.countryId", countryId));
		dCriteria.add(Restrictions.eq("countryBranch.isActive", Constants.Yes));
		dCriteria.add(Restrictions.ne("countryBranch.countryBranchId", locationId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("countryBranch.branchName"));
		List<CountryBranch> countryBranchList = (List<CountryBranch>) findAll(dCriteria);
		return countryBranchList;
	}

	@Override
	public void saveAllValues(BigDecimal employeePk, BigDecimal toLocationId, BigDecimal webServiceUserName, String webServicePassword, String toLocationIpAddress) {
		Employee employee= (Employee) getSession().get(Employee.class, employeePk);
		CountryBranch branch=new CountryBranch();
		branch.setCountryBranchId(toLocationId);
		employee.setFsCountryBranch(branch);
		employee.setWuUsername(webServiceUserName);
		employee.setWuPassword(webServicePassword);
		employee.setIpAddress(toLocationIpAddress);
		getSession().saveOrUpdate(employee);    
	}

	@Override
	public List<ViewVwRemittanceTransaction> getCustomerReceiptDetails(BigDecimal collectionDocumentYear, BigDecimal collectionDocumentNo) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewVwRemittanceTransaction.class, "viewVwRemittanceTransaction");
		dCriteria.add(Restrictions.eq("viewVwRemittanceTransaction.collectionDocFinYear", collectionDocumentYear));
		dCriteria.add(Restrictions.eq("viewVwRemittanceTransaction.collectionDocNo", collectionDocumentNo));
		dCriteria.add(Restrictions.eq("viewVwRemittanceTransaction.collectionDocCode", new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION)));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewVwRemittanceTransaction> list = (List<ViewVwRemittanceTransaction>) findAll(dCriteria);

		return list;
	}



}

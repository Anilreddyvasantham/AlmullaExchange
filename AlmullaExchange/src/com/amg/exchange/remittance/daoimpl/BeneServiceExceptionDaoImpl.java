package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.dao.IBeneServiceExceptionDao;
import com.amg.exchange.remittance.model.BeneServiceExceptionSetup;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.DocumentSeriality;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.util.Constants;

@SuppressWarnings("unchecked")
@Repository
public class BeneServiceExceptionDaoImpl<T> extends CommonDaoImpl<T> implements IBeneServiceExceptionDao {

	public BeneServiceExceptionDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void saveBeneServiceExceptionSetup(BeneServiceExceptionSetup beneServiceException){
		getSession().saveOrUpdate(beneServiceException);
	}
	@Override
	public List<BankBranch> getBankBranchList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode, BigDecimal toBranchCode) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");

			criteria.setFetchMode("bankBranch.fsCountryMaster", FetchMode.JOIN);
			criteria.createAlias("bankBranch.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

			criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

			criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
			criteria.createAlias("bankBranch.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);

			criteria.add(Restrictions.eq("exBankMaster.bankId", BankId));

			if (formBranchCode != null && toBranchCode != null)
				criteria.add(Restrictions.between("branchCode", formBranchCode, toBranchCode));
			
			criteria.addOrder(Order.asc("bankBranch.branchCode"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankBranch> objList = (List<BankBranch>) findAll(criteria);
		System.out.println("Controll come inside method ===================== > " + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}

	
	@Override
	public boolean getBanKBranchFromBeneExceptionSetup(BigDecimal countryId, BigDecimal bankId, BigDecimal branchId) {

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
	public List<BeneServiceExceptionSetup> getBeneServiceExceptionSetupList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode, BigDecimal toBranchCode, String isActive) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BeneServiceExceptionSetup.class, "beneServiceExceptionSetup");

		if (formBranchCode == null && toBranchCode == null) {
			System.out.println("Controll come inside method ===================== >if ");
			criteria.setFetchMode("beneServiceExceptionSetup.countryId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.countryId", "countryId", JoinType.INNER_JOIN);

			criteria.add(Restrictions.eq("countryId.countryId", countryId));

			criteria.setFetchMode("beneServiceExceptionSetup.bankId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankId", "bankId", JoinType.INNER_JOIN);

			criteria.add(Restrictions.eq("bankId.bankId", BankId));

			criteria.setFetchMode("beneServiceExceptionSetup.bankBranchId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankBranchId", "bankBranchId", JoinType.LEFT_OUTER_JOIN);

			//criteria.add(Restrictions.eq("beneServiceExceptionSetup.isActive", isActive));

			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			// objList = (List<BeneServiceExceptionSetup>) findAll(criteria);
		} else {
			System.out.println("Controll come inside method ===================== >else ");

			criteria.setFetchMode("beneServiceExceptionSetup.countryId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.countryId", "countryId", JoinType.INNER_JOIN);

			criteria.add(Restrictions.eq("countryId.countryId", countryId));

			criteria.setFetchMode("beneServiceExceptionSetup.bankId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankId", "bankId", JoinType.INNER_JOIN);

			criteria.add(Restrictions.eq("bankId.bankId", BankId));

			criteria.setFetchMode("beneServiceExceptionSetup.bankBranchId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankBranchId", "bankBranchId", JoinType.LEFT_OUTER_JOIN);

			criteria.add(Restrictions.between("beneServiceExceptionSetup.branchCodeNo", formBranchCode.toString(), toBranchCode.toString()));

			//criteria.add(Restrictions.eq("beneServiceExceptionSetup.isActive", isActive));

			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		}

		List<BeneServiceExceptionSetup> objList = (List<BeneServiceExceptionSetup>) findAll(criteria);
		System.out.println("Controll come inside method ===================== > " + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}

	
	@Override
	public List<BeneServiceExceptionSetup> getBeneDeleteServiceExceptionSetupList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode, BigDecimal toBranchCode, String isActive) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BeneServiceExceptionSetup.class, "beneServiceExceptionSetup");

		List<BeneServiceExceptionSetup> objList = null;

		if (formBranchCode == null && toBranchCode == null) {
			System.out.println("Controll come inside method ===================== >if ");
			criteria.setFetchMode("beneServiceExceptionSetup.countryId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.countryId", "countryId", JoinType.INNER_JOIN);

			criteria.add(Restrictions.eq("countryId.countryId", countryId));

			criteria.setFetchMode("beneServiceExceptionSetup.bankId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankId", "bankId", JoinType.INNER_JOIN);

			criteria.add(Restrictions.eq("bankId.bankId", BankId));

			criteria.setFetchMode("beneServiceExceptionSetup.bankBranchId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankBranchId", "bankBranchId", JoinType.LEFT_OUTER_JOIN);

			criteria.add(Restrictions.ne("beneServiceExceptionSetup.isActive", isActive));

			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			objList = (List<BeneServiceExceptionSetup>) findAll(criteria);
		} else {
			System.out.println("Controll come inside method ===================== >else ");

			criteria.setFetchMode("beneServiceExceptionSetup.countryId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.countryId", "countryId", JoinType.INNER_JOIN);

			criteria.add(Restrictions.eq("countryId.countryId", countryId));

			criteria.setFetchMode("beneServiceExceptionSetup.bankId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankId", "bankId", JoinType.INNER_JOIN);

			criteria.add(Restrictions.eq("bankId.bankId", BankId));

			criteria.setFetchMode("beneServiceExceptionSetup.bankBranchId", FetchMode.JOIN);
			criteria.createAlias("beneServiceExceptionSetup.bankBranchId", "bankBranchId", JoinType.LEFT_OUTER_JOIN);

			criteria.add(Restrictions.between("beneServiceExceptionSetup.branchCodeNo", formBranchCode.toString(), toBranchCode.toString()));

			criteria.add(Restrictions.ne("beneServiceExceptionSetup.isActive", isActive));

			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			objList = (List<BeneServiceExceptionSetup>) findAll(criteria);
		}

		if (objList.isEmpty())
			return null;

		return objList;
	}
	
	
	@Override	
	public List<BeneCountryService>  getBeneCountryAllServiceList(BigDecimal countryId,String isActive){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneCountryService.class,"beneCountryService");
		
		criteria.setFetchMode("beneCountryService.beneCountryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.beneCountryId", "beneCountryId", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("beneCountryId.countryId", countryId));
		
		
		criteria.setFetchMode("beneCountryService.remitanceId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.remitanceId", "remitanceId", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("beneCountryService.deliveryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.deliveryId", "deliveryId", JoinType.INNER_JOIN);
		
		
		
		criteria.add(Restrictions.eq("beneCountryService.isActive", isActive));
		
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
	List<BeneCountryService> objList = (List<BeneCountryService>) findAll(criteria);
		
		System.out.println("Controll come inside method ===================== > "+objList.size());
		
		if(objList.isEmpty())
			return null;
		
		return objList;
	}

	@Override
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal deliveryId, BigDecimal langId,String isActive) {

		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliverymodedesc");

		criteria.setFetchMode("deliverymodedesc.languageType", FetchMode.JOIN);
		criteria.createAlias("deliverymodedesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", langId));

		criteria.setFetchMode("deliverymodedesc.deliveryMode", FetchMode.JOIN);
		criteria.createAlias("deliverymodedesc.deliveryMode", "deliveryMode", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("deliveryMode.deliveryModeId", deliveryId));
		criteria.add(Restrictions.eq("deliveryMode.isActive", isActive));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<DeliveryModeDesc> objList = (List<DeliveryModeDesc>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;

	}

	@Override
	public List<RemittanceModeDescription> listRemittanceDesc(BigDecimal remittanceId, BigDecimal langId,String isActive) {

		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");

		criteria.setFetchMode("remittanceModeDescription.languageType", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", langId));

		criteria.setFetchMode("remittanceModeDescription.remittanceModeMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.remittanceModeMaster", "remittanceModeMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId", remittanceId));
		criteria.add(Restrictions.eq("remittanceModeMaster.isActive", isActive));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RemittanceModeDescription> objList = (List<RemittanceModeDescription>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;
	}
	
	@Override
	public boolean isExistBeneExceptionSetup(BigDecimal remittanceId,BigDecimal deliveryId,BigDecimal bankId,BigDecimal bankBranchId,String isActive){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneServiceExceptionSetup.class, "beneServiceExceptionSetup");

		criteria.setFetchMode("beneServiceExceptionSetup.bankId", FetchMode.JOIN);
		criteria.createAlias("beneServiceExceptionSetup.bankId", "bankId", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("bankId.bankId", bankId));

		criteria.setFetchMode("beneServiceExceptionSetup.bankBranchId", FetchMode.JOIN);
		criteria.createAlias("beneServiceExceptionSetup.bankBranchId", "bankBranchId", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("bankBranchId.bankBranchId", bankBranchId));
		
		criteria.setFetchMode("beneServiceExceptionSetup.remittanceModeId", FetchMode.JOIN);
		criteria.createAlias("beneServiceExceptionSetup.remittanceModeId", "remittanceModeId", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("remittanceModeId.remittanceModeId", remittanceId));
		
		criteria.setFetchMode("beneServiceExceptionSetup.deliveryId", FetchMode.JOIN);
		criteria.createAlias("beneServiceExceptionSetup.deliveryId", "deliveryId", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("deliveryId.deliveryModeId", deliveryId));
		
		criteria.add(Restrictions.eq("beneServiceExceptionSetup.isActive", isActive));
		
		

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BeneServiceExceptionSetup> objList = (List<BeneServiceExceptionSetup>) findAll(criteria);
		
		if (objList.isEmpty()) {
			return true;
		}

		return false;


	}
	
	@Override
	public void activateRecord(BigDecimal exceptionSetupId, String userName) {
		BeneServiceExceptionSetup beneServiceExceptionSetup = (BeneServiceExceptionSetup) getSession().get(BeneServiceExceptionSetup.class, exceptionSetupId);
		beneServiceExceptionSetup.setIsActive(Constants.U);
		beneServiceExceptionSetup.setModifiedBy(userName);
		beneServiceExceptionSetup.setModifiedDate(new Date());
		beneServiceExceptionSetup.setApprovedBy(null);
		beneServiceExceptionSetup.setApprovedDate(null);
		beneServiceExceptionSetup.setRemarks(null);
		getSession().update(beneServiceExceptionSetup);

	}
	
	@Override
	public List<BankBranch> getBankbranchlist(BigDecimal bankId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class,
				"bankBranch");
		criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.exBankMaster", "exBankMaster",
				JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		criteria.addOrder(Order.asc("bankBranch.branchFullName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankBranch>) findAll(criteria);
	}
	
	@Override
	public List<CurrencyMaster> getCurrencyList(BigDecimal countryId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(
				CurrencyMaster.class, "currencyMaster");
		criteria.setFetchMode("currencyMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("currencyMaster.fsCountryMaster",
				"fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<CurrencyMaster>) findAll(criteria);
	}
	@Override
	public List<RemittanceModeDescription> listRemittanceDesc(BigDecimal remittanceId,BigDecimal langId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass( RemittanceModeDescription.class, "remittanceModeDescription");
		
		criteria.setFetchMode("remittanceModeDescription.languageType",FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.languageType","languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", langId));
		
		criteria.setFetchMode("remittanceModeDescription.remittanceModeMaster",FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.remittanceModeMaster","remittanceModeMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId", remittanceId));
		criteria.add(Restrictions.eq("remittanceModeMaster.isActive", Constants.Yes));
		
		
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		// TODO Auto-generated method stub
		return (List<RemittanceModeDescription>)findAll(criteria);
	}
	
	@Override
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal deliveryId ,BigDecimal langId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(
				DeliveryModeDesc.class, "deliverymodedesc");
		
		criteria.setFetchMode("deliverymodedesc.languageType",FetchMode.JOIN);
		criteria.createAlias("deliverymodedesc.languageType","languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", langId));
		
		criteria.setFetchMode("deliverymodedesc.deliveryMode",FetchMode.JOIN);
		criteria.createAlias("deliverymodedesc.deliveryMode","deliveryMode", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("deliveryMode.deliveryModeId", deliveryId));
		criteria.add(Restrictions.eq("deliveryMode.isActive", Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<DeliveryModeDesc>) findAll(criteria);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<BeneServiceExceptionSetup> getExceptionSetupListForActiveInactive() {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneServiceExceptionSetup.class, "beneServiceExceptionSetup");
		List<String> activeStatuslist = new ArrayList<String>();
		activeStatuslist.add(Constants.Yes);
		activeStatuslist.add(Constants.D);
		// criteria.add(Restrictions.isNotNull("bankMaster.approvedBy"));
		// criteria.add(Restrictions.isNotNull("bankMaster.approvedDate"));
		criteria.add(Restrictions.in("beneServiceExceptionSetup.isActive", activeStatuslist));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BeneServiceExceptionSetup>) findAll(criteria);
	}
}

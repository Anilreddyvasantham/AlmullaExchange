package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.treasury.bean.StandardInstructionDataTableForDetails;
import com.amg.exchange.treasury.dao.IStandardInstructionsDao;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.model.StandardInstruction;
import com.amg.exchange.treasury.model.StandardInstructionDetails;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Repository
public class StandardInstructionsDaoImpl<T> extends CommonDaoImpl<T> implements IStandardInstructionsDao<T>, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	SessionStateManage session=new SessionStateManage();

	@SuppressWarnings("unchecked")
	@Override
	public List<StandardInstruction> getParentStandardInstruction(BigDecimal companyId,BigDecimal bankId,BigDecimal currencyId,String instructionType,BigDecimal accountDetId) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstruction.class, "standardInstruction");
		
		dCriteria.setFetchMode("standardInstruction.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		dCriteria.setFetchMode("standardInstruction.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId)); 
		
		dCriteria.setFetchMode("standardInstruction.exCurrenyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exCurrenyMaster", "exCurrenyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyMaster.currencyId", currencyId)); 
		
		dCriteria.setFetchMode("standardInstruction.bankAccountDetailsId", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.bankAccountDetailsId", "bankAccountDetailsId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankAccountDetailsId.bankAcctDetId", accountDetId)); 
		
		dCriteria.add(Restrictions.eq("standardInstruction.intructionType", instructionType));
		
		dCriteria.add(Restrictions.ne("standardInstruction.isActive", Constants.D)); 
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<StandardInstruction> pdata = (List<StandardInstruction>)findAll(dCriteria);
		
		
	 	return pdata;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StandardInstructionDetails> getStandardInstruction(BigDecimal companyId,BigDecimal bankId,BigDecimal currencyId,String isActive,BigDecimal instructionRefNumber) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstructionDetails.class, "standardInstructionDetails");
		
		
		dCriteria.setFetchMode("standardInstructionDetails.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstructionDetails.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		dCriteria.setFetchMode("standardInstructionDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstructionDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId)); 
		
		dCriteria.setFetchMode("standardInstructionDetails.exCurrenyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstructionDetails.exCurrenyMaster", "exCurrenyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyMaster.currencyId", currencyId)); 
		
		dCriteria.add(Restrictions.eq("standardInstructionDetails.isActive", isActive)); 
		
		/*dCriteria.setFetchMode("standardInstructionDetails.exstandardInstructionId", FetchMode.JOIN);
		dCriteria.createAlias("standardInstructionDetails.exstandardInstructionId", "exstandardInstructionId", JoinType.INNER_JOIN);*/
		
	//	dCriteria.add(Restrictions.eq("standardInstructionDetails.standardInsructionNumber", instructionRefNumber));
		
		dCriteria.addOrder(Order.asc("standardInstructionDetails.standardInstrnDetailsId"));
	
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<StandardInstructionDetails> data = (List<StandardInstructionDetails>)findAll(dCriteria);
		
		
	 	return data;
		
	}	
	
	@Override
	public void savestandardInstruction(StandardInstruction standardInstruction) {
		getSession().saveOrUpdate(standardInstruction);
	}
	
	@Override
	public void savestandardInstructionDetails(StandardInstructionDetails savestandardInstructionDetails) {
		getSession().save(savestandardInstructionDetails);
	}
	
	@Override
	public void updatestandardInstructionDetails(StandardInstructionDetails savestandardInstructionDetails) {
		getSession().saveOrUpdate(savestandardInstructionDetails);
	}
	
	@Override
	public void delete(BigDecimal pkofStndInstDetails,String username) {	
		
		StandardInstructionDetails stndInst = (StandardInstructionDetails) getSession().get(StandardInstructionDetails.class, pkofStndInstDetails);
		stndInst.setIsActive(Constants.D);
		stndInst.setModifiedBy(username);
		stndInst.setModifiedDate(new Date());
		getSession().update(stndInst);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StandardInstruction> getStanddardDesc() {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstruction.class, "standardInstruction");
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<StandardInstruction> data = (List<StandardInstruction>)findAll(dCriteria);	
		
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StandardInstruction> getValues(BigDecimal companyId,BigDecimal bankId,BigDecimal currencyId,BigDecimal accountDetId,String instructionType) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstruction.class, "standardInstruction");
		
		
		dCriteria.setFetchMode("standardInstruction.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		dCriteria.setFetchMode("standardInstruction.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId)); 
		
		dCriteria.setFetchMode("standardInstruction.exCurrenyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exCurrenyMaster", "exCurrenyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyMaster.currencyId", currencyId)); 
		
		dCriteria.setFetchMode("standardInstruction.bankAccountDetailsId", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.bankAccountDetailsId", "bankAccountDetailsId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankAccountDetailsId.bankAcctDetId", accountDetId)); 
		
		
	//    dCriteria.add(Restrictions.eq("standardInstruction.isActive", Constants.Yes)); 
		
		dCriteria.add(Restrictions.eq("standardInstruction.intructionType", instructionType)); 
		
		dCriteria.addOrder(Order.asc("standardInstruction.intructionType"));
	
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<StandardInstruction> pdata = (List<StandardInstruction>)findAll(dCriteria);
		
		
	 	return pdata;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void finalSaveOrUpdate(HashMap<String, Object> saveMapInfo) throws Exception {
		// TODO Auto-generated method stub
		
		StandardInstruction saveStndInstrn = (StandardInstruction)saveMapInfo.get("StandardInstruction");
		List<StandardInstructionDetails> saveStndInstrnDetails =  (List<StandardInstructionDetails>)saveMapInfo.get("StandardInstructionDetails");
		
		try{
			//Standard Instructions  
			getSession().saveOrUpdate(saveStndInstrn);
			
			//Standard Instructions Details
			for(StandardInstructionDetails lstStndInstrnDetails : saveStndInstrnDetails){
				getSession().saveOrUpdate(lstStndInstrnDetails);
			}
			
		}catch(Exception e){
			throw e;
		}
		
		
	}

	@Override
	public List<StandardInstruction> getAllParentStandardInstruction() {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstruction.class, "standardInstruction");
		
		dCriteria.setFetchMode("standardInstruction.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("standardInstruction.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("standardInstruction.exCurrenyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exCurrenyMaster", "exCurrenyMaster", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("standardInstruction.isActive", Constants.U));
	
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<StandardInstruction> pdata = (List<StandardInstruction>)findAll(dCriteria);
		
	 	return pdata;
		
	}

	@Override
	public BigDecimal getAllCountofStndInstrnDetails(BigDecimal pkofStandardInstrn) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Criteria criteria = session.createCriteria(StandardInstructionDetails.class, "standardInstructionDetails");
		criteria.setProjection(Projections.rowCount());
		criteria.setFetchMode("standardInstructionDetails.exstandardInstructionId", FetchMode.JOIN);
		criteria.createAlias("standardInstructionDetails.exstandardInstructionId", "exstandardInstructionId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exstandardInstructionId.standardInstructionId", pkofStandardInstrn));
		criteria.add(Restrictions.ne("standardInstructionDetails.isActive", Constants.D)); 
		return new BigDecimal((Long) criteria.uniqueResult());
	}

	@Override
	public void deleteStndInstrn(BigDecimal pkofStndInstId, String username) {
		// TODO Auto-generated method stub
		StandardInstruction stndInst = (StandardInstruction) getSession().get(StandardInstruction.class, pkofStndInstId);
		stndInst.setIsActive(Constants.D);
		stndInst.setModifiedBy(username);
		stndInst.setModifiedDate(new Date());
		getSession().update(stndInst);
	}
	
	@Override
	public List<StandardInstructionDetails> getStandardInstructionDesc(BigDecimal standardPk){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstructionDetails.class, "standardInstructionDetails");
		
		dCriteria.setFetchMode("standardInstructionDetails.exstandardInstructionId", FetchMode.JOIN);
		dCriteria.createAlias("standardInstructionDetails.exstandardInstructionId", "exstandardInstructionId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exstandardInstructionId.standardInstructionId", standardPk));
		dCriteria.addOrder(Order.asc("standardInstructionDetails.lineNumber"));
		dCriteria.add(Restrictions.eq("standardInstructionDetails.isActive", Constants.U));
		
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		 return (List<StandardInstructionDetails>) findAll(dCriteria);
	}
	

	
	@Override
	public void approveRecord(BigDecimal instructionPk,String userName){
		StandardInstruction stndInst = (StandardInstruction) getSession().get(StandardInstruction.class, instructionPk);
		stndInst.setIsActive(Constants.Yes);
		stndInst.setApproveBy(userName);
		stndInst.setApproveDate(new Date());
		getSession().update(stndInst);
		
		List<StandardInstructionDetails> standardInsList=getStandardInstructionDesc(instructionPk);
		
		for(StandardInstructionDetails standadInsDesc:standardInsList){
			
			StandardInstructionDetails stndInstDesc = (StandardInstructionDetails) getSession().get(StandardInstructionDetails.class, standadInsDesc.getStandardInstrnDetailsId());
			stndInstDesc.setIsActive(Constants.Yes);
			stndInstDesc.setApproveBy(userName);
			stndInstDesc.setApproveDate(new Date());
			stndInstDesc.setRemarks("");
			getSession().update(stndInstDesc);
		}
		
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankApplicability> getAllBankApplicablity() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
		
		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);
		
		BigDecimal localBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_LOCAL_BANK);
		BigDecimal corresBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_CORR_BANK);
		BigDecimal fundingBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_FUND_BANK);
		BigDecimal serviceProviderBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_SERVICEPRO_BANK);
		
		Disjunction lstjunction =  Restrictions.disjunction();
		
		if(localBankIndicatorId != null){
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId", corresBankIndicatorId));
		}
		
		if(corresBankIndicatorId != null){
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId", localBankIndicatorId));
		}
		
		if(fundingBankIndicatorId != null){
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId", fundingBankIndicatorId));
		}
		
		if(serviceProviderBankIndicatorId != null){
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId", serviceProviderBankIndicatorId));
		}
		
		dCriteria.add(lstjunction);
		
		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<BankApplicability> lstBankApp = (List<BankApplicability>)findAll(dCriteria);
		
		return lstBankApp;
	}
	
	@SuppressWarnings("unchecked")
	public BigDecimal fetchLocalBankIndicator(String bankIndicatorCode){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankIndicator.class, "bankIndicator");
		
		dCriteria.add(Restrictions.eq("bankIndicator.bankIndicatorCode", bankIndicatorCode));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<BankIndicator> lstBankIndId = (List<BankIndicator>)findAll(dCriteria);
		
		if(lstBankIndId.size() != 0){
			return ((List<BankIndicator>)findAll(dCriteria)).get(0).getBankIndicatorId();
		}
		
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StandardInstructionDetails> getStandardInstructionDetils(BigDecimal companyId, BigDecimal bankId, BigDecimal currencyId,String instructionType,BigDecimal accountDetId) {
         DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstructionDetails.class, "standardInstructionDetails");
		
		
		dCriteria.setFetchMode("standardInstructionDetails.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstructionDetails.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		dCriteria.setFetchMode("standardInstructionDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstructionDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId)); 
		
		dCriteria.setFetchMode("standardInstructionDetails.exCurrenyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstructionDetails.exCurrenyMaster", "exCurrenyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyMaster.currencyId", currencyId)); 
		
		dCriteria.setFetchMode("standardInstructionDetails.bankAccountDetailsId", FetchMode.JOIN);
		dCriteria.createAlias("standardInstructionDetails.bankAccountDetailsId", "bankAccountDetailsId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankAccountDetailsId.bankAcctDetId", accountDetId)); 
		 
		
		dCriteria.add(Restrictions.eq("standardInstructionDetails.intructionType", instructionType));
		
	//	dCriteria.add(Restrictions.eq("standardInstructionDetails.isActive", Constants.Yes)); 
		
	//	dCriteria.add(Restrictions.ne("standardInstructionDetails.isActive", Constants.D)); 
		
		dCriteria.addOrder(Order.asc("standardInstructionDetails.standardInstrnDetailsId"));
	
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		
		List<StandardInstructionDetails> data = (List<StandardInstructionDetails>)findAll(dCriteria);
		
		
	 	return data;
	}

	@Override
	public List<StandardInstruction> getAllListBasedonCombitiion(BigDecimal companyId, BigDecimal bankId, BigDecimal currencyId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstruction.class, "standardInstruction");
		
		dCriteria.setFetchMode("standardInstruction.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		dCriteria.setFetchMode("standardInstruction.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		
		dCriteria.setFetchMode("standardInstruction.exCurrenyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exCurrenyMaster", "exCurrenyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyMaster.currencyId", currencyId));
		
		dCriteria.add(Restrictions.eq("standardInstruction.isActive", Constants.U));
	
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<StandardInstruction> pdata = (List<StandardInstruction>)findAll(dCriteria);
		
	 	return pdata;
	}
	
	
	@Override
	public List<BankAccountDetails> populateAccountNumber(BigDecimal companyId, BigDecimal bankId, BigDecimal currencyId){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		
		dCriteria.setFetchMode("bankAccountDetails.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		dCriteria.setFetchMode("bankAccountDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		
		dCriteria.setFetchMode("bankAccountDetails.fsCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCurrencyMaster.currencyId", currencyId));
		
		dCriteria.add(Restrictions.eq("bankAccountDetails.recordStatus", Constants.Yes));
	
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<BankAccountDetails> pdata = (List<BankAccountDetails>)findAll(dCriteria);
		
	 	return pdata;
	}
	
	@Override
	public String getfundGlno(BigDecimal bankAccountDetId){
		
		String fundGlNo = null;
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		

		dCriteria.add(Restrictions.eq("bankAccountDetails.bankAcctDetId", bankAccountDetId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<BankAccountDetails> pdata = (List<BankAccountDetails>)findAll(dCriteria);
		if(pdata.size()>0){
			fundGlNo = pdata.get(0).getFundGlno();
		}
		
	 	return fundGlNo;
	}
	
	@Override
	public String getAccountNo(BigDecimal bankAccountDetId){
		
		String fundGlNo = null;
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		

		dCriteria.add(Restrictions.eq("bankAccountDetails.bankAcctDetId", bankAccountDetId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<BankAccountDetails> pdata = (List<BankAccountDetails>)findAll(dCriteria);
		if(pdata.size()>0){
			fundGlNo = pdata.get(0).getBankAcctNo();
		}
		
	 	return fundGlNo;
	}
	
	@Override
	public List<StandardInstructionDetails> getStandInstrDetailList(BigDecimal standardInstrutpk){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstructionDetails.class, "standardInstructionDetails");
		
		
		dCriteria.setFetchMode("standardInstructionDetails.exstandardInstructionId", FetchMode.JOIN);
		dCriteria.createAlias("standardInstructionDetails.exstandardInstructionId", "exstandardInstructionId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exstandardInstructionId.standardInstructionId", standardInstrutpk));
		
		dCriteria.add(Restrictions.ne("standardInstructionDetails.isActive", Constants.Yes));
		dCriteria.add(Restrictions.ne("standardInstructionDetails.isActive", Constants.D));

		dCriteria.addOrder(Order.asc("standardInstructionDetails.lineNumber"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<StandardInstructionDetails> pdata = (List<StandardInstructionDetails>)findAll(dCriteria);
	
	 	return pdata;
	}
	
	

	@Override
	public List<StandardInstruction> getStandInstrList(BigDecimal standardInstrutpk){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstruction.class, "standardInstruction");
		
		
		dCriteria.add(Restrictions.eq("standardInstruction.standardInstructionId", standardInstrutpk));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<StandardInstruction> pdata = (List<StandardInstruction>)findAll(dCriteria);
	
	 	return pdata;
	}
	
	@Override
	public Boolean checkAlreadyApprove(BigDecimal standardInstrutpk){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstruction.class, "standardInstruction");
		dCriteria.add(Restrictions.eq("standardInstruction.standardInstructionId", standardInstrutpk));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<StandardInstruction> pdata = (List<StandardInstruction>)findAll(dCriteria);
		
		String isActive =null;
		String approvedBy = null;
		Date approvedDate = null;
		Boolean checkApprove = false;
		if(pdata.size()>0){
			StandardInstruction standardInstruction = 	pdata.get(0);
			isActive = standardInstruction.getIsActive();
			approvedBy = standardInstruction.getApproveBy();
			approvedDate = standardInstruction.getApproveDate();
		}
	
		if(isActive.equalsIgnoreCase(Constants.Yes) && approvedBy!=null && approvedDate!=null){
			checkApprove = true;
		}else{
			checkApprove = false;
		}
	 	
		return checkApprove;
	}
	
	
	public void activateRecord(BigDecimal standardDelPk,String userName,BigDecimal lineNumber){
		StandardInstructionDetails service=(StandardInstructionDetails) getSession().get(StandardInstructionDetails.class, standardDelPk);
		service.setIsActive(Constants.U);
		service.setLineNumber(lineNumber);
		service.setModifiedBy(userName);
		service.setModifiedDate(new Date());
		service.setApproveBy(null);
		service.setApproveDate(null);
		service.setRemarks(null);
		getSession().update(service);

	}
	
	public void deleteRecordPermanently(BigDecimal standardDelPk,String userName){
		StandardInstructionDetails service=(StandardInstructionDetails) getSession().get(StandardInstructionDetails.class, standardDelPk);
		getSession().delete(service);

	}
	
	public void deletePartially(BigDecimal standardDelPk,String userName, String remarks,BigDecimal lineNumber){
		StandardInstructionDetails service=(StandardInstructionDetails) getSession().get(StandardInstructionDetails.class, standardDelPk);
		service.setIsActive(Constants.D);
		service.setLineNumber(lineNumber);
		service.setModifiedBy(userName);
		service.setModifiedDate(new Date());
		service.setApproveBy(null);
		service.setApproveDate(null);
		service.setRemarks(remarks);
		getSession().update(service);
	}

	@Override
	public void updateRecord(BigDecimal standardDelPk, String InstructDescription,BigDecimal lineNumber) {
		StandardInstructionDetails service=(StandardInstructionDetails) getSession().get(StandardInstructionDetails.class, standardDelPk);
		service.setIsActive(Constants.U);
		service.setLineNumber(lineNumber);
		service.setLineDescription(InstructDescription);
		service.setModifiedBy( session.getUserName());
		service.setModifiedDate( new Date());
		service.setApproveBy( null);
		service.setApproveDate( null);
		service.setRemarks( null);
		getSession().update(service);
		
	}


}

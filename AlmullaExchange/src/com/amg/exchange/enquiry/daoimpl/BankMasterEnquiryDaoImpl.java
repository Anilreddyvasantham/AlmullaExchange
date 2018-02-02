package com.amg.exchange.enquiry.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.enquiry.dao.IBankMasterEnquiryDao;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankContactDetails;
import com.amg.exchange.treasury.model.BankIndicatorDescription;
import com.amg.exchange.treasury.model.ZoneMasterDesc;

@Repository
public class BankMasterEnquiryDaoImpl extends CommonDaoImpl implements IBankMasterEnquiryDao,Serializable{

	@Override
	public List<BankIndicatorDescription> getAllBankIndicators(BigDecimal languageId){
		List<BankIndicatorDescription> bankIndicatorNewList = new ArrayList<BankIndicatorDescription>(); 
		
		DetachedCriteria criteria = DetachedCriteria.forClass(BankIndicatorDescription.class,"bankIndicatorDescription");
		boolean flag=false;
		criteria.setFetchMode("bankIndicatorDescription.bankIndicator",FetchMode.JOIN);
		criteria.createAlias("bankIndicatorDescription.bankIndicator", "bankIndicator",JoinType.INNER_JOIN);	
		
		criteria.setFetchMode("bankIndicatorDescription.languageType",FetchMode.JOIN);
		criteria.createAlias("bankIndicatorDescription.languageType", "languageType",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId",languageId));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		 List<BankIndicatorDescription> bankIndicatorList = findAll(criteria);
		 for(BankIndicatorDescription bankIndicator:bankIndicatorList){
			 if(bankIndicatorNewList.size()==0){
				 bankIndicatorNewList.add(bankIndicator);
			 }else if(bankIndicatorNewList.size()>0){
				 for(int i=0;i<bankIndicatorNewList.size();i++){
				 if(bankIndicatorNewList.get(i).getBankIndicator().getBankIndicatorCode().equalsIgnoreCase(bankIndicator.getBankIndicator().getBankIndicatorCode())){
					 flag = false;
					 break ;
				 }else{
					 flag = true;
				 }
				}
				 if(flag==true){
					 bankIndicatorNewList.add(bankIndicator);
				 }
			 }
		 }
		 
		return bankIndicatorNewList;
	}

	@Override
	public List<BankAccountLength> getAllBankLengthRecords(BigDecimal bankId){
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountLength.class,"bankAccountLength");
		
		criteria.setFetchMode("bankAccountLength.bankMaster",FetchMode.JOIN);
		criteria.createAlias("bankAccountLength.bankMaster", "bankMaster",JoinType.INNER_JOIN);	
				
		criteria.add(Restrictions.eq("bankMaster.bankId",bankId));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return findAll(criteria);
	}
	
	@Override
	public List<BankContactDetails> getAllBankContactDetailsRecords(BigDecimal bankId){
		DetachedCriteria criteria = DetachedCriteria.forClass(BankContactDetails.class,"bankContactDetails");
		
		criteria.setFetchMode("bankContactDetails.exBankMaster",FetchMode.JOIN);
		criteria.createAlias("bankContactDetails.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);	
		
		criteria.setFetchMode("bankContactDetails.exZone",FetchMode.JOIN);
		criteria.createAlias("bankContactDetails.exZone", "exZone",JoinType.INNER_JOIN);
				
		criteria.add(Restrictions.eq("exBankMaster.bankId",bankId));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return findAll(criteria);
	}
	
	@Override
	public List<BankAccountDetails> getAllBankAccountDetailsRecords(BigDecimal bankId){
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountDetails.class,"bankAccountDetails");
		
		criteria.setFetchMode("bankAccountDetails.exBankMaster",FetchMode.JOIN);
		criteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);	
				
		criteria.setFetchMode("bankAccountDetails.fsCountryMaster",FetchMode.JOIN);
		criteria.createAlias("bankAccountDetails.fsCountryMaster", "fsCountryMaster",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("exBankMaster.bankId",bankId));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return findAll(criteria);
	}
	
	@Override
	public String getZoneName(BigDecimal zoneId,BigDecimal languageId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ZoneMasterDesc.class,"zoneMasterDesc");
		
		criteria.setFetchMode("zoneMasterDesc.zone",FetchMode.JOIN);
		criteria.createAlias("zoneMasterDesc.zone", "zone",JoinType.INNER_JOIN);	
		criteria.add(Restrictions.eq("zone.zoneId",zoneId));
		
		criteria.setFetchMode("zoneMasterDesc.languageType",FetchMode.JOIN);
		criteria.createAlias("zoneMasterDesc.languageType", "languageType",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId",languageId));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<ZoneMasterDesc> zonelist = findAll(criteria);
		if(zonelist.size()>0){
			return zonelist.get(0).getZoneDescription();
		}else{
		return null;
		}
	}
	
	@Override
	public String getBankIndicatorName(BigDecimal bankIndId,BigDecimal languageId){
		DetachedCriteria criteria = DetachedCriteria.forClass(BankIndicatorDescription.class,"bankIndicatorDescription");
		
		criteria.setFetchMode("bankIndicatorDescription.bankIndicator",FetchMode.JOIN);
		criteria.createAlias("bankIndicatorDescription.bankIndicator", "bankIndicator",JoinType.INNER_JOIN);	
		criteria.add(Restrictions.eq("bankIndicator.bankIndicatorId",bankIndId));
		
		criteria.setFetchMode("bankIndicatorDescription.languageType",FetchMode.JOIN);
		criteria.createAlias("bankIndicatorDescription.languageType", "languageType",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId",languageId));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<BankIndicatorDescription> bankIndList = findAll(criteria);
		if(bankIndList.size()>0){
			return bankIndList.get(0).getBankIndicatorDescription();
		}else{
		return null;
		}
	}
	
	@Override
	public List<BankApplicability> getBankMasterDetails(BigDecimal bankCountryId,BigDecimal bankIndId){
		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class,"bankApplicability");
		
		criteria.setFetchMode("bankApplicability.bankMaster",FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankMaster", "bankMaster",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("bankApplicability.fsCountryMaster",FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId",bankCountryId));
		
		criteria.setFetchMode("bankApplicability.bankInd",FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankInd", "bankInd",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankInd.bankIndicatorId",bankIndId));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return findAll(criteria);
	}
	
	@Override
	public String getBankAccountTypeName(BigDecimal accounTypeId,BigDecimal languageId){
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountTypeDesc.class,"bankAccountTypeDesc");
		
		criteria.setFetchMode("bankAccountTypeDesc.bankAccountTypeId",FetchMode.JOIN);
		criteria.createAlias("bankAccountTypeDesc.bankAccountTypeId", "bankAccountTypeId",JoinType.INNER_JOIN);	
		criteria.add(Restrictions.eq("bankAccountTypeId.bankAccountTypeId",accounTypeId));
		
		criteria.setFetchMode("bankAccountTypeDesc.languageId",FetchMode.JOIN);
		criteria.createAlias("bankAccountTypeDesc.languageId", "languageId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId",languageId));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<BankAccountTypeDesc> bankAccountTypeList = findAll(criteria);
		if(bankAccountTypeList.size()>0){
			return bankAccountTypeList.get(0).getBankAccountTypeDesc();
		}else{
		return null;
		}
	}
	
}

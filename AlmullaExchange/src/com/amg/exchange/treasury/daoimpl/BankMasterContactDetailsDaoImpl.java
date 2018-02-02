package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.treasury.dao.IBankMasterContactDetailsDao;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankContactDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.ZoneMasterDesc;
import com.amg.exchange.util.Constants;

@SuppressWarnings("serial")
@Repository
public class BankMasterContactDetailsDaoImpl<T> extends CommonDaoImpl<T> implements IBankMasterContactDetailsDao<T>, Serializable {

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<BankMaster> getBankMasterInfo() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankMaster.class, "bankmaster");
		detachedCriteria.addOrder(Order.asc("bankFullName"));// ascending order
																// applied nag
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankMaster>) findAll(detachedCriteria);
	}

	@Override
	public void saveBankMasterContactDetails(BankContactDetails bankContact) {
		getSession().saveOrUpdate(bankContact);

	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<BankContactDetails> getbankContactInfo(BigDecimal bankId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankContactDetails.class, "bankmasterContactInfo");

		detachedCriteria.setFetchMode("bankmasterContactInfo.exBankMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankmasterContactInfo.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		
		detachedCriteria.setFetchMode("bankmasterContactInfo.exZone", FetchMode.JOIN);
		detachedCriteria.createAlias("bankmasterContactInfo.exZone", "exZone", JoinType.INNER_JOIN);

		// detachedCriteria.add(Restrictions.eq("bankmasterContactInfo.recordStatus",
		// "Y"));
		detachedCriteria.add(Restrictions.disjunction().add(Restrictions.eq("bankmasterContactInfo.recordStatus", Constants.U)).add(Restrictions.eq("bankmasterContactInfo.recordStatus", Constants.D)));

		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankContactDetails>) findAll(detachedCriteria);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<BankMaster> getbankCountryInfo(BigDecimal bankId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankMaster.class, "bankmaster");

		detachedCriteria.add(Restrictions.eq("bankmaster.bankId", bankId));

		detachedCriteria.setFetchMode("bankmaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankmaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankMaster>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ZoneMasterDesc> getZoneList(BigDecimal languageId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ZoneMasterDesc.class, "zoneMasterDesc");
		detachedCriteria.setFetchMode("zoneMasterDesc.languageType", FetchMode.JOIN);
		detachedCriteria.createAlias("zoneMasterDesc.languageType", "languageType",JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("languageType.languageId", languageId));
		/*detachedCriteria.add(Restrictions.eq("zoneMasterDesc.languageType", languageId));*/
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ZoneMasterDesc>) findAll(detachedCriteria);
	}

	@Override
	public void removeContactDetails(BigDecimal bankContactDetailsId, String userName, boolean delete) {
		BankContactDetails bankAccountLength = (BankContactDetails) getSession().get(BankContactDetails.class, bankContactDetailsId);
		if (delete) {
			bankAccountLength.setRecordStatus(Constants.D);
			bankAccountLength.setModifier(userName);
			bankAccountLength.setUpdateDate(new Date());
		} else {
			bankAccountLength.setRecordStatus(Constants.U);
			bankAccountLength.setModifier(userName);
			bankAccountLength.setUpdateDate(new Date());
		}

		getSession().update(bankAccountLength);
	}

	@Override
	public String getZoneName(BigDecimal zoneId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ZoneMasterDesc.class, "zoneMasterDesc");
		
		detachedCriteria.setFetchMode("zoneMasterDesc.zone", FetchMode.JOIN);
		detachedCriteria.createAlias("zoneMasterDesc.zone", "zone", JoinType.INNER_JOIN);

		
		detachedCriteria.add(Restrictions.eq("zone.zoneId", zoneId));

		String zoneDescription="";
		List<ZoneMasterDesc> lstZoneMasterDesc=(List<ZoneMasterDesc>) findAll(detachedCriteria);
		if(lstZoneMasterDesc.size()>0)
		{
			zoneDescription=lstZoneMasterDesc.get(0).getZoneDescription();
		}
		//return ((ZoneMasterDesc) detachedCriteria.getExecutableCriteria(getSession()).list().get(0)).getZoneDescription();
		return zoneDescription;
	}

	@Override
	public List<BankContactDetails> getpkContactId(BigDecimal bankId, BigDecimal zoneId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankContactDetails.class, "contactdetails");

		detachedCriteria.setFetchMode("contactdetails.exBankMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("contactdetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		
		detachedCriteria.setFetchMode("contactdetails.exZone", FetchMode.JOIN);
		detachedCriteria.createAlias("contactdetails.exZone", "exZone", JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.eq("exZone.zoneId", zoneId));

		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		// TODO Auto-generated method stub
		return (List<BankContactDetails>) findAll(detachedCriteria);
		// ((CountryMasterDesc)
		// dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCountryName();
	}

	@Override
	public List<BankContactDetails> getContactList(BigDecimal bankId) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankContactDetails.class, "contactdetails");

		detachedCriteria.setFetchMode("contactdetails.exBankMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("contactdetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

	//	detachedCriteria.add(Restrictions.eq("contactdetails.recordStatus", Constants.U));
		
		detachedCriteria.add(Restrictions.disjunction().add( Restrictions.eq("contactdetails.recordStatus", Constants.U)).add(Restrictions.eq("contactdetails.recordStatus", Constants.D )).add(Restrictions.eq("contactdetails.recordStatus", Constants.Yes )));

		/*detachedCriteria.setFetchMode("zoneMasterDesc.zone", FetchMode.JOIN);
		detachedCriteria.createAlias("zoneMasterDesc.zone", "zone", JoinType.INNER_JOIN);*/
		detachedCriteria.setFetchMode("contactdetails.exZone", FetchMode.JOIN);
		detachedCriteria.createAlias("contactdetails.exZone", "exZone", JoinType.INNER_JOIN);

		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankContactDetails>) findAll(detachedCriteria);
	}

	@Override
	public List<BankAccountLength> getlistBanklength(BigDecimal bankId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankAccountLength.class, "banlength");

		detachedCriteria.setFetchMode("banlength.bankMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("banlength.bankMaster", "bankMaster", JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));

		return (List<BankAccountLength>) findAll(detachedCriteria);
	}



}

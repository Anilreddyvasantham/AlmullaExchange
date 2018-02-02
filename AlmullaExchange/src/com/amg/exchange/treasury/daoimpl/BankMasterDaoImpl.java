package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.treasury.dao.IBankMasterDao;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankContactDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;

@SuppressWarnings("serial")
@Repository
public class BankMasterDaoImpl<T> extends CommonDaoImpl<T> implements
IBankMasterDao<T>, Serializable {

	@SuppressWarnings("unchecked")
	@Override
	public void saveBankMasterInfo(BankMaster bankMasterinfo) {

		try{

			saveOrUpdate((T) bankMasterinfo);
		}catch(Exception e){
			getSession().getTransaction().rollback();
			System.out.println("Procedure Exception===================="+e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<BankMaster> getBankMasterInfo(String bankCode) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass( BankMaster.class, "bankmaster");

		detachedCriteria.setFetchMode("bankmaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankmaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.eq("bankmaster.bankCode", bankCode));
		detachedCriteria.add(Restrictions.eq("bankmaster.recordStatus", Constants.Yes));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankMaster>) findAll(detachedCriteria);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<BankMaster> getBankMasterInfoForApproval(String bankCode) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass( BankMaster.class, "bankmaster");

		detachedCriteria.setFetchMode("bankmaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankmaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.eq("bankmaster.bankCode", bankCode));

		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankMaster>) findAll(detachedCriteria);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<BankMaster> getAllBankMasterInfo(String bankCode) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass( BankMaster.class, "bankmaster");

		detachedCriteria.setFetchMode("bankmaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankmaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.eq("bankmaster.bankCode", bankCode));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankMaster>) findAll(detachedCriteria);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<BankMaster> getBankMasterInfoById(BigDecimal bankId) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass( BankMaster.class, "bankmaster");

		detachedCriteria.setFetchMode("bankmaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankmaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.eq("bankmaster.bankId", bankId));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankMaster>) findAll(detachedCriteria);
	}

	@Override
	public void saveAll(HashMap<String, Object> saveMap)
			throws Exception {

		try{
			BankMaster bankMaster=(BankMaster) saveMap.get("BankMasterMap");

			List<BankContactDetails> lstBankContactDetails =(List<BankContactDetails>) saveMap.get("BankContactDetails");

			List<BankAccountLength> lstBankAccountLength =(List<BankAccountLength>) saveMap.get("BankAccountLength");

			getSession().saveOrUpdate(bankMaster);

			if(lstBankContactDetails!=null && lstBankContactDetails.size()>0)
			{
				for(BankContactDetails bankContactDetails :lstBankContactDetails)
				{
					getSession().saveOrUpdate(bankContactDetails);
				}
			}else
			{
				throw new Exception("Bank contact details not exist");
			}

			if(lstBankAccountLength!=null && lstBankAccountLength.size()>0)
			{
				for(BankAccountLength bankAccountLength :lstBankAccountLength)
				{
					getSession().saveOrUpdate(bankAccountLength);
				}
			}/*else
			{
				throw new Exception("Bank account length details not exist");
			}*/

		}catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}

	}

	@Override
	public HashMap<String, String> callPopulateBankMaster(
			HashMap<String, String> inputValues) throws AMGException {

		HashMap<String, String> outputValues = new HashMap<String, String>();
		Connection connection = null;
		String errString = null;

		try {
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			String call = " { call EX_P_POPULATE_BANK_MASTER (?, ? ) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("BANK_ID")));
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.execute();
			errString = cs.getString(2);
			outputValues.put("P_ERROR_MESSAGE", errString);

		} catch (SQLException e) {
			String erromsg = "EX_P_POPULATE_BANK_MASTER" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				String erromsg = "EX_P_POPULATE_BANK_MASTER" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}

		return outputValues;

	}



	@Override
	public void procErrorToUnApprove(BigDecimal bankIdInternal,List<BigDecimal> contactList, List<BigDecimal> lengthLst) {
		BankMaster bankMaster=(BankMaster) getSession().get(BankMaster.class, bankIdInternal);
		//contactList
		for (BigDecimal bigDecimal : contactList) {
			BankContactDetails bankContactDetails=(BankContactDetails) getSession().get(BankContactDetails.class, bigDecimal);
			bankContactDetails.setRecordStatus(Constants.U);
			bankContactDetails.setApprovedBy(null);
			bankContactDetails.setApprovedDate(null);
			getSession().update(bankContactDetails);
		}
		//bank Account length
		for (BigDecimal lengthPk : lengthLst) {
			BankAccountLength bankAccountLength=(BankAccountLength) getSession().get(BankAccountLength.class, lengthPk);
			bankAccountLength.setRecordStatus(Constants.U);
			getSession().update(bankAccountLength);
		}
		bankMaster.setRecordStatus(Constants.U);
		bankMaster.setApprovedBy(null);
		bankMaster.setApprovedDate(null);
		getSession().update(bankMaster);
	}

	@Override
	public List<BankMaster> fetchBankServiceProvider() {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankMaster.class, "bankmaster");

		detachedCriteria.setFetchMode("bankmaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankmaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.in("bankmaster.bankCode", new String[]{Constants.WU_BANK_CODE,Constants.MONEYGRAM_BANK_CODE,Constants.HOMESEND_BANK_CODE}));
		detachedCriteria.add(Restrictions.eq("bankmaster.recordStatus", Constants.Yes));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankMaster>) findAll(detachedCriteria);
	}



}

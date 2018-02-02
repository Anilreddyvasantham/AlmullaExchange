package com.amg.exchange.bankBranchUpload.daoimpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.bankBranchUpload.bean.BankBranchUploadExcelList;
import com.amg.exchange.bankBranchUpload.dao.BankBranchUploadDAO;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;

@Repository
@SuppressWarnings("unchecked")
public class BankBranchUploadDAOImpl<T> extends CommonDaoImpl<T> implements BankBranchUploadDAO {
	

	@Override
	public List<CountryMasterDesc> getAllCountryList(BigDecimal langId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class,"countryMasterDesc");

		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster",JoinType.INNER_JOIN);

		dCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType",JoinType.INNER_JOIN);

		dCriteria.addOrder(Order.asc("countryMasterDesc.countryName"));
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", langId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CountryMasterDesc>)findAll(dCriteria);		
	}

	@Override
	public List<BankMaster> getBankList(BigDecimal countryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");

		criteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankMaster.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.addOrder(Order.asc("bankMaster.bankFullName"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<BankMaster>) findAll(criteria);			
	}


	private BigDecimal getStateId(String stateName,BigDecimal langId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");

		criteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		criteria.createAlias("stateMasterDesc.fsStateMaster","fsStateMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("stateMasterDesc.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("stateMasterDesc.stateName", stateName));
		criteria.add(Restrictions.eq("fsLanguageType.languageId", langId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List<StateMasterDesc> stateDesc = (List<StateMasterDesc>) findAll(criteria);
		if(stateDesc!=null && !stateDesc.isEmpty()){
			return stateDesc.get(0).getFsStateMaster().getStateId();
		} else{
			return null;
		}

	}


	private BigDecimal getDistrictId(String districtName,BigDecimal langId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DistrictMasterDesc.class, "districtMasterDesc");

		criteria.setFetchMode("districtMasterDesc.fsDistrictMaster", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsDistrictMaster","fsDistrictMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("districtMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("districtMasterDesc.district", districtName));
		criteria.add(Restrictions.eq("fsLanguageType.languageId", langId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List<DistrictMasterDesc> districtDesc = (List<DistrictMasterDesc>) findAll(criteria);	
		if(districtDesc!=null && !districtDesc.isEmpty()){
			return districtDesc.get(0).getFsDistrictMaster().getDistrictId();
		}
		return null;			
	}


	private BigDecimal getCityId(String cityName,BigDecimal langId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CityMasterDesc.class, "cityMasterDesc");

		criteria.setFetchMode("cityMasterDesc.fsCityMaster", FetchMode.JOIN);
		criteria.createAlias("cityMasterDesc.fsCityMaster","fsCityMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("cityMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("cityMasterDesc.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("cityMasterDesc.cityName", cityName));
		criteria.add(Restrictions.eq("fsLanguageType.languageId", langId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List<CityMasterDesc> cityDesc = (List<CityMasterDesc>) findAll(criteria);
		if(cityDesc!=null && !cityDesc.isEmpty()){
			return cityDesc.get(0).getFsCityMaster().getCityId();
		}
		return null;				
	}	


	private List<BankBranch> getIsactiveRecords(BigDecimal bankId , String branchIfscCode) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");

		criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.exBankMaster","exBankMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		criteria.add(Restrictions.eq("bankBranch.branchIfsc", branchIfscCode));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<BankBranch>) findAll(criteria);				
	}

	private List<BankBranch> getIsactiveRecordsBranchName(BigDecimal bankId , String branchName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");

		criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.exBankMaster","exBankMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		criteria.add(Restrictions.eq("bankBranch.branchFullName", branchName));
		criteria.add(Restrictions.isNull("bankBranch.branchIfsc"));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<BankBranch>) findAll(criteria);				
	}

	@Override
	public String getBankCode(BigDecimal bankid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");

		criteria.add(Restrictions.eq("bankMaster.bankId", bankid));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return ((BankMaster) criteria.getExecutableCriteria(getSession()).list().get(0)).getBankCode();
	}

	private BigDecimal getMaxLength(BigDecimal bankId) {
		BigDecimal maxBranchCode=null;
		String hqlQuery="select distinct Max(a.branchCode) from  BankBranch a where a.exBankMaster.bankId = :pbankID";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("pbankID", bankId);
		List<BigDecimal> lstBankBranch =query.list();
		if(lstBankBranch.size()>0){
			maxBranchCode=lstBankBranch.get(0);
		}
		return maxBranchCode;		
	}

	@Override
	public HashMap<String, List<BankBranchUploadExcelList>> checkAndSaveOrUpdateBankBranch(BigDecimal countryId,BigDecimal bankId,List<BankBranchUploadExcelList> neftBranchUploadExcelList,BigDecimal langId,String userName)
	{
		HashMap<String, List<BankBranchUploadExcelList>> rtnMap = new HashMap<String, List<BankBranchUploadExcelList>>();

		List<BankBranchUploadExcelList> deleteListNeftBranchUploadExcel = new ArrayList<BankBranchUploadExcelList>();
		List<BankBranchUploadExcelList> inValidListNeftBranchUploadExcel = new ArrayList<BankBranchUploadExcelList>();
		List<BankBranchUploadExcelList> updateListNeftBranchUploadExcel = new ArrayList<BankBranchUploadExcelList>();
		List<BankBranchUploadExcelList> insertListNeftBranchUploadExcel = new ArrayList<BankBranchUploadExcelList>();		

		String bankCode = getBankCode(bankId);

		if(neftBranchUploadExcelList!=null  && neftBranchUploadExcelList.size()>0)
		{
			for(BankBranchUploadExcelList neftBranchUploadExcel:neftBranchUploadExcelList)
			{				
				if(bankCode!=null && !bankCode.isEmpty()) {
					if(neftBranchUploadExcel.getBank()!=null && neftBranchUploadExcel.getBank().equalsIgnoreCase(bankCode)){

						List<BankBranch> lstBankBranch=null;
						if(neftBranchUploadExcel.getIfsc()==null || neftBranchUploadExcel.getIfsc().isEmpty()) {
							if(neftBranchUploadExcel.getBranchName()!=null && !neftBranchUploadExcel.getBranchName().isEmpty()){
								lstBankBranch=getIsactiveRecordsBranchName(bankId,neftBranchUploadExcel.getBranchName());
							} else{
								neftBranchUploadExcel.setRemarks("Invalid Record!");
								neftBranchUploadExcel.setColumnInvalid("INVALIDFORMAT");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}
							
						} else {
							lstBankBranch=getIsactiveRecords(bankId,neftBranchUploadExcel.getIfsc());
						}

						if(lstBankBranch!=null && lstBankBranch.size()>0)
						{
							//Update Existing records.
							BankBranch bankBranch=lstBankBranch.get(0);
							if(bankBranch.getIsactive().equalsIgnoreCase(Constants.D))
							{
								neftBranchUploadExcel.setBankBranchId(bankBranch.getBankBranchId());
								neftBranchUploadExcel.setRemarks("Record is Deleted!");
								neftBranchUploadExcel.setColumnInvalid("RECORDDELETED");
								deleteListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}
							if(bankBranch.getIsactive().equalsIgnoreCase(Constants.No))
							{
								neftBranchUploadExcel.setBankBranchId(bankBranch.getBankBranchId());
								neftBranchUploadExcel.setRemarks("Record is inactive!");
								neftBranchUploadExcel.setColumnInvalid("RECORDINVALID");
								deleteListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}

							if(neftBranchUploadExcel.getMicr()!=null && neftBranchUploadExcel.getMicr().length() > 10){
								neftBranchUploadExcel.setBankBranchId(bankBranch.getBankBranchId());
								neftBranchUploadExcel.setRemarks("Micr field should not exceed 10 characters!");
								neftBranchUploadExcel.setColumnInvalid("MICRINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}else{
								bankBranch.setMicrCode(neftBranchUploadExcel.getMicr());
							}

							if(neftBranchUploadExcel.getBranchName()!=null && neftBranchUploadExcel.getBranchName().length() > 60){
								neftBranchUploadExcel.setBankBranchId(bankBranch.getBankBranchId());
								neftBranchUploadExcel.setRemarks("Branch field should not exceed 60 characters!");
								neftBranchUploadExcel.setColumnInvalid("BRANCHINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}else{
								bankBranch.setBranchFullName(neftBranchUploadExcel.getBranchName());
							}	
							
							if(neftBranchUploadExcel.getBranchShortName()!=null && neftBranchUploadExcel.getBranchShortName().length() < 30){
								bankBranch.setBranchShortName(neftBranchUploadExcel.getBranchShortName());
							}


							if(neftBranchUploadExcel.getAddr2()!=null && neftBranchUploadExcel.getAddr2().length() > 60){
								neftBranchUploadExcel.setBankBranchId(bankBranch.getBankBranchId());
								neftBranchUploadExcel.setRemarks("Address field should not exceed 120 characters!");
								neftBranchUploadExcel.setColumnInvalid("ADDRINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}else{
								bankBranch.setAddress1(neftBranchUploadExcel.getAddr1());
								bankBranch.setAddress2(neftBranchUploadExcel.getAddr2());
							}

							if(neftBranchUploadExcel.getContact()!=null && neftBranchUploadExcel.getContact().length() > 30){
								neftBranchUploadExcel.setBankBranchId(bankBranch.getBankBranchId());
								neftBranchUploadExcel.setRemarks("Contact field should not exceed 30 characters!");
								neftBranchUploadExcel.setColumnInvalid("CONTACTINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}else{
								bankBranch.setTelephoneNo(neftBranchUploadExcel.getContact());
							}						

							bankBranch.setModifiedBy(userName);
							bankBranch.setModifiedDate(new Date());
							CountryMaster countryMaster = new CountryMaster();
							countryMaster.setCountryId(countryId);
							bankBranch.setFsCountryMaster(countryMaster);

							BigDecimal stateId = getStateId(neftBranchUploadExcel.getState(), langId);
							if (stateId != null) {
								StateMaster stateMaster = new StateMaster();
								stateMaster.setStateId(stateId);
								bankBranch.setFsStateMaster(stateMaster);
							}else
							{
								neftBranchUploadExcel.setBankBranchId(bankBranch.getBankBranchId());
								neftBranchUploadExcel.setRemarks("Invalid State Id!");
								neftBranchUploadExcel.setColumnInvalid("STATEINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}

							BigDecimal districtId = getDistrictId(neftBranchUploadExcel.getDistrict(),langId);
							if(districtId!=null) {
								DistrictMaster districtMaster = new DistrictMaster();
								districtMaster.setDistrictId(districtId);
								bankBranch.setFsDistrictMaster(districtMaster);
							}else
							{
								neftBranchUploadExcel.setBankBranchId(bankBranch.getBankBranchId());
								neftBranchUploadExcel.setRemarks("Invalid District Id!");
								neftBranchUploadExcel.setColumnInvalid("DISTRICTINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}

							BigDecimal cityId = getCityId(neftBranchUploadExcel.getCity(),langId);
							if(cityId!=null) {
								CityMaster cityMaster = new CityMaster();
								cityMaster.setCityId(cityId);
								bankBranch.setFsCityMaster(cityMaster);
							}/*else
							{
								neftBranchUploadExcel.setBankBranchId(bankBranch.getBankBranchId());
								neftBranchUploadExcel.setRemarks("Invalid City Id!");
								neftBranchUploadExcel.setColumnInvalid("CITYINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}	*/

							getSession().saveOrUpdate(bankBranch);
							
							updateListNeftBranchUploadExcel.add(neftBranchUploadExcel);
						} else
						{
							//Save New records.											

							BankBranch bankBranch= new BankBranch();

							BankMaster bankMaster = new BankMaster();
							bankMaster.setBankId(bankId);
							bankBranch.setExBankMaster(bankMaster);		

							if(neftBranchUploadExcel.getIfsc()!=null && neftBranchUploadExcel.getIfsc().length() > 40){
								neftBranchUploadExcel.setRemarks("IFSC field should not exceed 40 characters!");
								neftBranchUploadExcel.setColumnInvalid("IFSCINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}else{
								bankBranch.setBranchIfsc(neftBranchUploadExcel.getIfsc());
							}

							BigDecimal branchCode = getMaxLength(bankId);
							if(branchCode!=null){
								bankBranch.setBranchCode(branchCode.add(new BigDecimal(1)));
							} else {
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}		

							bankBranch.setIsactive(Constants.Y);

							if(neftBranchUploadExcel.getMicr()!=null && neftBranchUploadExcel.getMicr().length() > 10){
								neftBranchUploadExcel.setRemarks("Micr field should not exceed 10 characters!");
								neftBranchUploadExcel.setColumnInvalid("MICRINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}else{
								bankBranch.setMicrCode(neftBranchUploadExcel.getMicr());
							}

							if(neftBranchUploadExcel.getBranchName()!=null && neftBranchUploadExcel.getBranchName().length() > 60){
								neftBranchUploadExcel.setRemarks("Branch field should not exceed 60 characters!");
								neftBranchUploadExcel.setColumnInvalid("BRANCHINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}else{
								bankBranch.setBranchFullName(neftBranchUploadExcel.getBranchName());
							}		
							
							if(neftBranchUploadExcel.getBranchShortName()!=null && neftBranchUploadExcel.getBranchShortName().length() < 30){
								bankBranch.setBranchShortName(neftBranchUploadExcel.getBranchShortName());
							}

							if(neftBranchUploadExcel.getAddr2()!=null && neftBranchUploadExcel.getAddr2().length() > 60){
								neftBranchUploadExcel.setRemarks("Address field should not exceed 120 characters!");
								neftBranchUploadExcel.setColumnInvalid("ADDRINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}else{
								bankBranch.setAddress1(neftBranchUploadExcel.getAddr1());
								bankBranch.setAddress2(neftBranchUploadExcel.getAddr2());
							}

							if(neftBranchUploadExcel.getContact()!=null && neftBranchUploadExcel.getContact().length() > 30){
								neftBranchUploadExcel.setRemarks("Contact field should not exceed 30 characters!");
								neftBranchUploadExcel.setColumnInvalid("CONTACTINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}else{
								bankBranch.setTelephoneNo(neftBranchUploadExcel.getContact());
							}
							bankBranch.setCreatedBy(userName);
							bankBranch.setCreatedDate(new Date());
							
							CountryMaster countryMaster = new CountryMaster();
							countryMaster.setCountryId(countryId);
							bankBranch.setFsCountryMaster(countryMaster);

							BigDecimal stateId = getStateId(neftBranchUploadExcel.getState(), langId);
							if (stateId != null) {
								StateMaster stateMaster = new StateMaster();
								stateMaster.setStateId(stateId);
								bankBranch.setFsStateMaster(stateMaster);
							} else {
								neftBranchUploadExcel.setRemarks("Invalid State Id!");
								neftBranchUploadExcel.setColumnInvalid("STATEINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}

							BigDecimal districtId = getDistrictId(neftBranchUploadExcel.getDistrict(),langId);
							if(districtId!=null) {
								DistrictMaster districtMaster = new DistrictMaster();
								districtMaster.setDistrictId(districtId);
								bankBranch.setFsDistrictMaster(districtMaster);
							}else
							{
								neftBranchUploadExcel.setRemarks("Invalid District Id!");
								neftBranchUploadExcel.setColumnInvalid("DISTRICTINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}

							BigDecimal cityId = getCityId(neftBranchUploadExcel.getCity(),langId);
							if(cityId!=null) {
								CityMaster cityMaster = new CityMaster();
								cityMaster.setCityId(cityId);
								bankBranch.setFsCityMaster(cityMaster);
							}/*else
							{
								neftBranchUploadExcel.setRemarks("Invalid City Id!");
								neftBranchUploadExcel.setColumnInvalid("CITYINVALID");
								inValidListNeftBranchUploadExcel.add(neftBranchUploadExcel);
								continue;
							}	*/					

							getSession().saveOrUpdate(bankBranch);
							
							insertListNeftBranchUploadExcel.add(neftBranchUploadExcel);
						}	
					} 
				}											
			}
		}

		rtnMap.put("DELTEBRANCH", deleteListNeftBranchUploadExcel);
		rtnMap.put("INVALIDBRANCH", inValidListNeftBranchUploadExcel);
		rtnMap.put("UPDATEBRANCH", updateListNeftBranchUploadExcel);
		rtnMap.put("INSERTBRANCH", insertListNeftBranchUploadExcel);
		return rtnMap;
	}



	public boolean updateExcelRecords(BankBranch bankBranch)throws Exception{

		boolean rtnSts=false;
		Connection con=null;
		PreparedStatement preparedStatement=null;
		try{
			String QuerySQL=null;
			con = getDataSourceFromHibernateSession();
			con.setAutoCommit(false);
			if(bankBranch.getBankBranchId()==null)
			{
				QuerySQL="INSERT INTO EX_BANK_BRANCH(BANK_ID,BRANCH_IFSC,MICR_CODE,BRANCH_FULL_NAME,ADDRESS1,ADDRESS2,TELEPHONE_NO,CITY_ID,DISTRICT_ID,STATE_ID,BRANCH_CODE,ISACTIVE,BANK_BRANCH_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			} else{
				QuerySQL="UPDATE EX_BANK_BRANCH SET BANK_ID=? ,BRANCH_IFSC=? ,MICR_CODE=? ,BRANCH_FULL_NAME=?, ADDRESS1=?, ADDRESS2=?, TELEPHONE_NO=? ,CITY_ID=?, DISTRICT_ID=?, STATE_ID=?, BRANCH_CODE=?, ISACTIVE=? WHERE BANK_BRANCH_ID=?";
			}
			String nextValue=getNextReferenceNo();
			preparedStatement = con.prepareStatement(QuerySQL);
			preparedStatement.setBigDecimal(1, bankBranch.getExBankMaster().getBankId());
			preparedStatement.setString(2, bankBranch.getBranchIfsc());
			preparedStatement.setString(3, bankBranch.getMicrCode());
			preparedStatement.setString(4, bankBranch.getBranchFullName());
			preparedStatement.setString(5, bankBranch.getAddress1());
			preparedStatement.setString(6, bankBranch.getAddress2());
			preparedStatement.setString(7, bankBranch.getTelephoneNo());
			preparedStatement.setBigDecimal(8, bankBranch.getFsCityMaster().getCityId());
			preparedStatement.setBigDecimal(9, bankBranch.getFsDistrictMaster().getDistrictId());
			preparedStatement.setBigDecimal(10, bankBranch.getFsStateMaster().getStateId());
			preparedStatement.setBigDecimal(11, bankBranch.getBranchCode());
			preparedStatement.setString(12, bankBranch.getIsactive());
			if(bankBranch.getBankBranchId()!=null){
				preparedStatement.setBigDecimal(13, bankBranch.getBankBranchId());
			}else
			{
				preparedStatement.setBigDecimal(13, new BigDecimal(nextValue));
			}

			preparedStatement.executeUpdate();
			con.commit();
			rtnSts=true;		
		}catch (Exception e) {
			try {
				rtnSts=false;
				con.rollback();
				con.clearWarnings();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally
		{
			if(con!=null)
			{
				try{
					con.close();
					preparedStatement.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return rtnSts;
	}
	public String getNextReferenceNo() {
		SQLQuery sqlQuery = super.getSession().createSQLQuery("SELECT EX_BANK_BRANCH_SEQ.NEXTVAL FROM DUAL");
		return sqlQuery.uniqueResult().toString();
	}
}

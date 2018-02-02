package com.amg.exchange.remittance.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.remittance.dao.ISwiftMasterDao;
import com.amg.exchange.remittance.model.SwiftMaster;
import com.amg.exchange.remittance.model.SwiftMasterCountryView;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class SwiftMasterDaoImpl extends CommonDaoImpl implements ISwiftMasterDao, Serializable {

	  private static final long serialVersionUID = 7065043640884152730L;

	  @Override
	  public void saveOrUpdate(SwiftMaster swiftMaster) {

		    getSession().saveOrUpdate(swiftMaster);
	  }

	  @SuppressWarnings("unchecked")
	  @Override
	  public List<SwiftMaster> getSwiftRecord(String swiftCountryCode, String swiftBankCode, String branchCode) {
		    DetachedCriteria dcriteria = DetachedCriteria.forClass(SwiftMaster.class, "swiftMaster");

		    dcriteria.add(Restrictions.eq("swiftMaster.swiftCountryCode", swiftCountryCode));
		    dcriteria.add(Restrictions.eq("swiftMaster.bankCode", swiftBankCode));
		    dcriteria.add(Restrictions.eq("swiftMaster.swiftBranchCode", branchCode));

		    dcriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		    return (List<SwiftMaster>) findAll(dcriteria);
	  }

	  @SuppressWarnings("unchecked")
	  public List<SwiftMaster> getAllActiveDeActiveSwift() {
		    DetachedCriteria dcriteria = DetachedCriteria.forClass(SwiftMaster.class, "swiftMaster");
		    // dcriteria.setFetchMode("swiftMaster.swiftCountryId",FetchMode.JOIN);
		    // dcriteria.createAlias("swiftMaster.swiftCountryId",
		    // "swiftCountryId",JoinType.INNER_JOIN);

		    // dcriteria.setFetchMode("swiftMaster.swiftBranchId",FetchMode.JOIN);
		    // dcriteria.createAlias("swiftMaster.swiftBranchId",
		    // "swiftBranchId",JoinType.INNER_JOIN);

		    // dcriteria.setFetchMode("swiftMaster.bankId",FetchMode.JOIN);
		    // dcriteria.createAlias("swiftMaster.bankId",
		    // "bankId",JoinType.INNER_JOIN);

		    /*
		     * List<String> activeStatuslist = new ArrayList<String>();
		     * activeStatuslist.add("Y"); activeStatuslist.add("D");
		     * dcriteria.add(Restrictions.in("swiftMaster.isActive",
		     * activeStatuslist));
		     */

		    dcriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		    return (List<SwiftMaster>) findAll(dcriteria);

	  }

	  @SuppressWarnings("unchecked")
	  public String getBranchFullName(BigDecimal branchId) {

		    DetachedCriteria dcriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		    dcriteria.add(Restrictions.eq("bankBranch.bankBranchId", branchId));

		    List<BankBranch> listbranch = findAll(dcriteria);

		    return listbranch.get(0).getBranchFullName();

	  }

	  @SuppressWarnings("unchecked")
	  public List<SwiftMaster> getAllSwiftForApprove() {

		    DetachedCriteria dcriteria = DetachedCriteria.forClass(SwiftMaster.class, "swiftMaster");

		    dcriteria.add(Restrictions.eq("swiftMaster.isActive", Constants.U));
		    dcriteria.add(Restrictions.isNull("swiftMaster.approvedBy"));
		    dcriteria.add(Restrictions.isNull("swiftMaster.approvedDate"));

		    dcriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		    return (List<SwiftMaster>) findAll(dcriteria);

	  }

	  @Override
	  @SuppressWarnings("unchecked")
	  public String getBankFullName(BigDecimal bankId) {
		    DetachedCriteria dcriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		    dcriteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		    List<BankMaster> listbranch = findAll(dcriteria);
		    if (listbranch.size() > 0) {
			      return listbranch.get(0).getBankFullName();
		    } else {
			      return null;
		    }

	  }

	  @SuppressWarnings("unchecked")
	  @Override
	  public List<SwiftMaster> getSwiftEnquiry(String swiftCountryCode, String swiftBankCode) {

		    DetachedCriteria dcriteria = DetachedCriteria.forClass(SwiftMaster.class, "swiftMaster");

		    if (swiftCountryCode != null) {
			      dcriteria.add(Restrictions.eq("swiftMaster.swiftCountryCode", swiftCountryCode));
		    }
		    if (swiftBankCode != null) {
			      dcriteria.add(Restrictions.eq("swiftMaster.bankCode", swiftBankCode));
		    }

		    // dcriteria.add(Restrictions.eq(
		    // "swiftMaster.swiftBranchId.bankBranchId",branchId));

		    dcriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		    return (List<SwiftMaster>) findAll(dcriteria);
	  }

	  @Override
	  public void delete(SwiftMaster swiftMaster) {
		    getSession().delete(swiftMaster);

	  }

	  @Override
	  public String approveRecord(BigDecimal swiftMasterPk, String userName) {

		    String approveMsg;
		    SwiftMaster swiftMaster = (SwiftMaster) getSession().get(SwiftMaster.class, swiftMasterPk);
		    String approvedUser = swiftMaster.getApprovedBy();
		    if (approvedUser == null) {
			      swiftMaster.setApprovedBy(userName);
			      swiftMaster.setApprovedDate(new Date());
			      swiftMaster.setIsActive(Constants.Yes);
			      getSession().update(swiftMaster);
			      approveMsg = "Success";
		    } else {
			      approveMsg = "Fail";
		    }
		    return approveMsg;
	  }

	  @Override
	  public String getBranchName(String branchCode) {
		  DetachedCriteria dcriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		  
		    dcriteria.add(Restrictions.like("bankBranch.branchCodeAsString", branchCode, MatchMode.EXACT).ignoreCase());
			 
		    dcriteria.setProjection(Projections.property("bankBranch.branchFullName"));
		    dcriteria.addOrder(Order.asc("bankBranch.branchCodeAsString"));
		    dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			 
			List<String>  branchList =  (List<String>)findAll(dcriteria);
		 	  
		    if (branchList.size() > 0) {
			      return branchList.get(0);
		    } else {
			      return null;
		    }
		   /* DetachedCriteria dcriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		    dcriteria.add(Restrictions.eq("bankBranch.branchCode", new BigDecimal(branchCode)));

		    List<BankBranch> listbranch = findAll(dcriteria);
		    if (listbranch.size() > 0) {
			      return listbranch.get(0).getBranchFullName();
		    } else {
			      return null;
		    }*/

	  }

	  @Override
	  public String getBankFullNameBasedOnCode(String bankCode) {
		    DetachedCriteria dcriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		    dcriteria.add(Restrictions.eq("bankMaster.bankCode", bankCode));

		    List<BankMaster> listbranch = findAll(dcriteria);
		    if (listbranch.size() > 0) {
			      return listbranch.get(0).getBankFullName();
		    } else {
			      return null;
		    }

	  }

	  @Override
	  public BigDecimal getBankId(String bankCode) {
		    System.out.println("=========" + bankCode);
		    DetachedCriteria dcriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		    dcriteria.add(Restrictions.eq("bankMaster.bankCode", bankCode));

		    List<BankMaster> listbranch = findAll(dcriteria);
		    if (listbranch.size() > 0) {
			      return listbranch.get(0).getBankId();
		    } else {
			      return BigDecimal.ZERO;
		    }

	  }

	  @Override
	  public BigDecimal getCountryId(String countryCode) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");

		    criteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("fsCountryMaster.countryAlpha2Code", countryCode));
 
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<CountryMasterDesc> countryNameList = (List<CountryMasterDesc>) findAll(criteria);

		    if (countryNameList != null && countryNameList.size() > 0) {
			      return countryNameList.get(0).getFsCountryMaster().getCountryId();
		    } else {
			      return BigDecimal.ZERO;
		    }

	  }

	  @SuppressWarnings("unchecked")
	@Override
	  public BigDecimal getBranchId(String branchCode) {
		  
		  
		   DetachedCriteria dcriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		   	    
		    dcriteria.add(Restrictions.like("bankBranch.branchCodeAsString", branchCode, MatchMode.EXACT).ignoreCase());
			 
		    dcriteria.setProjection(Projections.property("bankBranch.bankBranchId"));
		    dcriteria.addOrder(Order.asc("bankBranch.bankBranchId"));
		    dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<BigDecimal>  branchList =  (List<BigDecimal>)findAll(dcriteria);
		    	  
		    if (branchList.size() > 0) {
			      return branchList.get(0);
		    } else {
			      return BigDecimal.ZERO;
		    }
		    /*DetachedCriteria dcriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		    dcriteria.add(Restrictions.eq("bankBranch.branchCode", new BigDecimal(branchCode)));

		    List<BankBranch> listbranch = findAll(dcriteria);
		    if (listbranch.size() > 0) {

			      return listbranch.get(0).getBankBranchId();
		    } else {
			      return BigDecimal.ZERO;
		    }*/

	  }

	  @SuppressWarnings("unchecked")
	  @Override
	  public List<SwiftMaster> toFetchAllActiveRecords() {
		    DetachedCriteria dcriteria = DetachedCriteria.forClass(SwiftMaster.class, "swiftMaster");
		    dcriteria.add(Restrictions.eq("swiftMaster.isActive", Constants.Yes));
		    dcriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		    List<SwiftMaster> lSwiftMasters = (List<SwiftMaster>) findAll(dcriteria);
		    return lSwiftMasters;
	  }

	@Override
	public String getSwiftBicFromBankBranch(BigDecimal branchId,
			BigDecimal bankId) {
		  DetachedCriteria dcriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		  dcriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		  dcriteria.createAlias("bankBranch.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);
		  dcriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		  dcriteria.add(Restrictions.eq("bankBranch.isactive", Constants.Yes));
		  dcriteria.add(Restrictions.eq("bankBranch.bankBranchId", branchId));
	 	 List<BankBranch> banBranchList =  ((List<BankBranch>) findAll(dcriteria));
		  
	 	 if(banBranchList.size()>0){
	 		 return banBranchList.get(0).getSwiftBic();
	 	 }else{
	 	 		   return null;
	             }
	}

	@Override
	public String getCountryNameBasedOnCountryAlphaCode(String alphaCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		
		criteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster",  JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCountryMaster.countryAlpha2Code", alphaCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CountryMasterDesc> countryNameList= (List<CountryMasterDesc>)findAll(criteria);
		
		if(countryNameList!=null && countryNameList.size()>0) {
		return countryNameList.get(0).getCountryName();
		}else{
			return null;
		}
 
	}

	@Override
	public BigDecimal getBranchIdForPerticular(String branchCode,
			BigDecimal BankId) {
		   DetachedCriteria dcriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		   dcriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
			 dcriteria.createAlias("bankBranch.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);
		    dcriteria.add(Restrictions.like("bankBranch.branchCodeAsString", branchCode, MatchMode.EXACT).ignoreCase());
			 
		    dcriteria.setProjection(Projections.property("bankBranch.bankBranchId"));
		    dcriteria.add(Restrictions.eq("exBankMaster.bankId", BankId));
		    dcriteria.addOrder(Order.asc("bankBranch.bankBranchId"));
		    dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<BigDecimal>  branchList =  (List<BigDecimal>)findAll(dcriteria);
		    	  
		    if (branchList.size() > 0) {
			      return branchList.get(0);
		    } else {
			      return BigDecimal.ZERO;
		    }
		
	 
	}

	@Override
	public List<BankBranch> getBranchList( ) {
		DetachedCriteria dcriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		dcriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		dcriteria.createAlias("bankBranch.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);
	    dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankBranch>  branchList =  (List<BankBranch>)findAll(dcriteria);
		return branchList;
	}

	@Override
	public List<BankMaster> getBankMasterListBasedOnCode( ) {
		DetachedCriteria dcriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	    List<BankMaster>  bankList =  (List<BankMaster>)findAll(dcriteria);
		return bankList;
	}
	
	public List<String> getAllSwiftBank(String swiftBank){
		DetachedCriteria dcriteria = DetachedCriteria.forClass(SwiftMaster.class, "swiftMaster");
		dcriteria.add(Restrictions.like("swiftMaster.bankCode", swiftBank,
				MatchMode.START));
		//dcriteria.addOrder(Order.asc("swiftMaster.bankCode"));		
		dcriteria.setProjection(Projections.distinct(Projections.property("swiftMaster.bankCode")));
		
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);		
		List<String>  list =  (List<String>)findAll(dcriteria);
		List<SwiftMaster> list1 = null;
		if (null != list) {
			if (!list.isEmpty()) {
				return list;
			}
		}
		return null;
	}
	
	public List<String> getAllSwiftLocation(String swiftBankId, String swiftCountryId, String swiftLocation){
	
		DetachedCriteria dcriteria = DetachedCriteria.forClass(SwiftMaster.class, "swiftMaster");
		dcriteria.add(Restrictions.like("swiftMaster.swiftLocation", swiftLocation,
				MatchMode.START));		
		dcriteria.setProjection(Projections.distinct(Projections.property("swiftMaster.swiftLocation")));
		
		dcriteria.add(Restrictions.eq("swiftMaster.bankCode", swiftBankId));
		dcriteria.add(Restrictions.eq("swiftMaster.swiftCountryCode", swiftCountryId));		
		//dcriteria.addOrder(Order.asc("swiftMaster.swiftLocation"));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<String>  list =  (List<String>)findAll(dcriteria);
		List<SwiftMaster> list1 = null;
		if (null != list) {
			if (!list.isEmpty()) {
				return list;
			}
		}
		return null;
		
	}
	public List<String> getAllSwiftBranch(String swiftBankId, String swiftCountryId,String swiftLocation,String swiftBranch){
		DetachedCriteria dcriteria = DetachedCriteria.forClass(SwiftMaster.class, "swiftMaster");
		dcriteria.add(Restrictions.like("swiftMaster.swiftBranchCode", swiftBranch,
				MatchMode.START));		
		dcriteria.add(Restrictions.eq("swiftMaster.bankCode", swiftBankId));
		dcriteria.add(Restrictions.eq("swiftMaster.swiftCountryCode", swiftCountryId));
		dcriteria.add(Restrictions.eq("swiftMaster.swiftLocation", swiftLocation));
		//dcriteria.addOrder(Order.asc("swiftMaster.swiftBranchCode"));
		dcriteria.setProjection(Projections.distinct(Projections.property("swiftMaster.swiftBranchCode")));
		
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);		
		List<String>  list =  (List<String>)findAll(dcriteria);
		
		List<SwiftMaster> list1 = null;
		if (null != list) {
			if (!list.isEmpty()) {
				return list;
			}
		}
		return null;
	}
	
	public List<SwiftMaster> getSwiftInfo(String swiftBankCode,String swiftCountryCode, String location, String branchCode){
		DetachedCriteria dcriteria = DetachedCriteria.forClass(SwiftMaster.class, "swiftMaster");
		
		dcriteria.add(Restrictions.eq("swiftMaster.bankCode", swiftBankCode));
		dcriteria.add(Restrictions.eq("swiftMaster.swiftCountryCode", swiftCountryCode));
		dcriteria.add(Restrictions.eq("swiftMaster.swiftLocation", location));
		dcriteria.add(Restrictions.eq("swiftMaster.swiftBranchCode", branchCode));
		
		List<SwiftMaster>  list =  (List<SwiftMaster>)findAll(dcriteria);
		
		if (null != list) {
			if (!list.isEmpty()) {
				return list;
			}
		}
		return null;
	}
	
	public List<SwiftMasterCountryView> getAllSwiftCountry(String swiftBankCode){
		DetachedCriteria dcriteria = DetachedCriteria.forClass(SwiftMasterCountryView.class, "swiftMasterCountryView");
		//dcriteria.add(Restrictions.eq("swiftMasterCountryView.bankCode", swiftBankCode));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<SwiftMasterCountryView>  list =  (List<SwiftMasterCountryView>)findAll(dcriteria);
		
		if (null != list) {
			if (!list.isEmpty()) {
				return list;
			}
		}
		return null;
	}
	
	
}

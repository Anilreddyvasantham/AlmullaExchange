package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.bean.BulkDealApprovalDataTable;
import com.amg.exchange.treasury.bean.DealTrackerInfoDTBean;
import com.amg.exchange.treasury.bean.DealTrackerViewTicketDataTable;
import com.amg.exchange.treasury.bean.FXDetailInfoBean;
import com.amg.exchange.treasury.dao.IDealTrackerInfoDao;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.StandardInstruction;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;
import com.amg.exchange.treasury.model.ViewCorrespondingBankCountry;
import com.amg.exchange.treasury.model.VwExBulkFxDeal;
import com.amg.exchange.treasury.viewModel.CurrencyMasterView;
import com.amg.exchange.treasury.viewModel.DealTrackerTicketView;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.DateUtil;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("serial")
@Repository
public class DealTrackerInfoDaoImpl<T> extends CommonDaoImpl<T> implements  IDealTrackerInfoDao<T> ,Serializable{
	
	Logger log = Logger.getLogger(FXDetailInfoBean.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DealTrackerTicketView> getDealTrackerTicketViewInfo(Date dealDate){
		
		SessionStateManage sessionManage = new SessionStateManage();

		DetachedCriteria criteria = DetachedCriteria.forClass(DealTrackerTicketView.class, "dealTrackerTicketView");
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yy");
		String date11=dateformat.format(dealDate);
		List<DealTrackerTicketView> lstRtnDealTrackerTicketView =new ArrayList<DealTrackerTicketView>();
		try {

			List<DealTrackerTicketView> lstKWDRtnDealTrackerTicketView =new ArrayList<DealTrackerTicketView>();
			List<DealTrackerTicketView> lstOMNRtnDealTrackerTicketView =new ArrayList<DealTrackerTicketView>();

			Date date = dateformat.parse(date11);
			criteria.add(Restrictions.eq("dealTrackerTicketView.timeofDeal", date));

			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

			List<DealTrackerTicketView> lstDealTrackerTicketView=(List<DealTrackerTicketView>) findAll(criteria);

			for(DealTrackerTicketView dealTrackerTicketView :lstDealTrackerTicketView)
			{
				List<TreasuryDealHeader> lstTreasuryDealHeader= getTreasuryHrdForDealTracker(date,dealTrackerTicketView.getDealId().trim());
				if(lstTreasuryDealHeader.size()==0)
				{
					String commentText=getCountryDealFromCommentText(dealTrackerTicketView.getCommentText());
					if(commentText.equalsIgnoreCase(Constants.DEAL_TRACKER_KW_CONBY))
					{
						lstKWDRtnDealTrackerTicketView.add(dealTrackerTicketView);
					}
					if(commentText.equalsIgnoreCase(Constants.DEAL_TRACKER_OM_CONBY))
					{
						lstOMNRtnDealTrackerTicketView.add(dealTrackerTicketView);
					}
				}
			}
			if(sessionManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE))//if("KW".equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE))
			{
				lstRtnDealTrackerTicketView=lstKWDRtnDealTrackerTicketView;
			}

			if(sessionManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE))//if("OM".equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE))
			{
				lstRtnDealTrackerTicketView=lstOMNRtnDealTrackerTicketView;
			}


		} catch (ParseException e) {
			e.printStackTrace();
		}

		return lstRtnDealTrackerTicketView;


	}

	@SuppressWarnings("unchecked")
	public List<TreasuryDealHeader> getTreasuryHrdForDealTracker(Date dealDate,String dealID) 
	{

		String hql = "from TreasuryDealHeader as treasuryDealHeader where to_date(to_char(treasuryDealHeader.documentDate, 'DD-MON-YY'), 'DD-MON-YY') = :date  and treasuryDealHeader.reutersReference = :returnRef";
		Query query = getSession().createQuery(hql); 
		query.setParameter("date", dealDate);
		query.setParameter("returnRef", dealID); 
		List<TreasuryDealHeader> lstTreasuryDealHeader=query.list();


		/* SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String date11=dateformat.format(dealDate);
		Date date = dateformat.parse(date11); 




		Calendar cal = Calendar.getInstance();
        cal.setTime(dealDate);


		Date date1 =DateUtils.truncate(dealDate, Calendar.DATE);

		 SimpleDateFormat dateFormatForSearch = new SimpleDateFormat("dd/MM/yyyy"); 
		 String dateAsStr = dateFormatForSearch.format(date1.getTime());

		System.out.println(date1);
		DetachedCriteria criteria = DetachedCriteria.forClass(TreasuryDealHeader.class, "treasuryDealHeader");

		criteria.add(Restrictions.eq("treasuryDealHeader.documentDate", new java.sql.Timestamp(date1.getTime())));
		criteria.add(Restrictions.eq("treasuryDealHeader.reutersReference", dealID));

		//criteria.add(Restrictions.sqlRestriction("trunc(DOCUMENT_DATE)=to_date('" + dateAsStr + "','dd/mm/yyyy')")); 


		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<TreasuryDealHeader> lstTreasuryDealHeader=(List<TreasuryDealHeader>) findAll(criteria);*/

		return lstTreasuryDealHeader;

	}

	@Override
	public HashMap<String, Object> getIDsFromCode(DealTrackerInfoDTBean dealTrackerInfoDTBean) {

		HashMap<String, Object> rtnMap= new HashMap<String, Object>();


		List<BankMaster> lstBankMasterDealWith=getBankDetails(dealTrackerInfoDTBean.getDealWith());

		if(lstBankMasterDealWith.size()>0)
		{
			for( BankMaster bankMaster: lstBankMasterDealWith)
			{
				rtnMap.put("DealWithBankCode", bankMaster.getBankCode());
				rtnMap.put("DealWithBankID", bankMaster.getBankId());
			}
			rtnMap.put("DealWithBank", Boolean.TRUE);

		}else
		{
			rtnMap.put("DealWithBank", Boolean.FALSE);
		}


		List<BankMaster> lstPDBankMaster=getBankDetails(dealTrackerInfoDTBean.getPdBank());

		if(lstPDBankMaster.size()>0)
		{
			rtnMap.put("PDBank", Boolean.TRUE);

			for( BankMaster bankMaster: lstPDBankMaster)
			{
				rtnMap.put("PDBankCode", bankMaster.getBankCode());
				rtnMap.put("PDBankID", bankMaster.getBankId());
			}
		}else
		{
			rtnMap.put("PDBank", Boolean.FALSE);
		}



		List<BankMaster> lstSDBankMaster=getBankDetails(dealTrackerInfoDTBean.getSdBank());

		if(lstSDBankMaster.size()>0)
		{
			rtnMap.put("SDBank", Boolean.TRUE);
			for( BankMaster bankMaster: lstSDBankMaster)
			{
				rtnMap.put("SDBankCode", bankMaster.getBankCode());
				rtnMap.put("SDBankID", bankMaster.getBankId());
			}

		}else
		{
			rtnMap.put("SDBank", Boolean.FALSE);
		}

		List<CurrencyMasterView> lstPDCurrencyMasterView=getCurrencyDetails(dealTrackerInfoDTBean.getPdCurrency());

		if(lstPDCurrencyMasterView.size()>0)
		{
			for( CurrencyMasterView currencyMasterView: lstPDCurrencyMasterView)
			{
				rtnMap.put("PDCurrencyCode", currencyMasterView.getCurrencyCode());
				rtnMap.put("PDCurrencyID", currencyMasterView.getCurrencyId());
				rtnMap.put("PDCurrencyName", currencyMasterView.getCurrencyName());
			}

			List<BankAccountDetails> lstBankAccountDetails= getCurrencyBasedOnBankCountry((BigDecimal)rtnMap.get("PDBankID"),(BigDecimal)rtnMap.get("PDCurrencyID"));
			if(lstBankAccountDetails.size()>0)
			{
				rtnMap.put("PDCurrency", Boolean.TRUE);

			}else
			{
				rtnMap.put("PDCurrency", Boolean.FALSE);
			}

		}else
		{
			rtnMap.put("PDCurrency", Boolean.FALSE);
		}



		List<CurrencyMasterView> lstSDCurrencyMasterView=getCurrencyDetails(dealTrackerInfoDTBean.getSdCurrency());

		if(lstSDCurrencyMasterView.size()>0)
		{
			for( CurrencyMasterView currencyMasterView: lstSDCurrencyMasterView)
			{
				rtnMap.put("SDCurrencyCode", currencyMasterView.getCurrencyCode());
				rtnMap.put("SDCurrencyID", currencyMasterView.getCurrencyId());
				rtnMap.put("SDCurrencyName", currencyMasterView.getCurrencyName());
			}
			List<BankAccountDetails> lstBankAccountDetails= getCurrencyBasedOnBankCountry((BigDecimal)rtnMap.get("SDBankID"),(BigDecimal)rtnMap.get("SDCurrencyID"));
			if(lstBankAccountDetails.size()>0)
			{
				rtnMap.put("SDCurrency", Boolean.TRUE);

			}else
			{
				rtnMap.put("SDCurrency", Boolean.FALSE);
			}

		}else
		{
			rtnMap.put("SDCurrency", Boolean.FALSE);
		}



		return rtnMap;
	}

	@SuppressWarnings("unchecked")
	public List<BankMaster> getBankDetails(String bankCode)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");

		criteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));

		criteria.add(Restrictions.eq("bankMaster.reutersBankName", bankCode));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankMaster> lstBankMaster=(List<BankMaster>) findAll(criteria);

		return lstBankMaster;
	}

	@SuppressWarnings("unchecked")
	public List<CurrencyMasterView> getCurrencyDetails(String currencyCode)
	{
		DetachedCriteria dcriteria = DetachedCriteria.forClass(CurrencyMasterView.class, "currencyMasterView");

		dcriteria.add(Restrictions.eq("currencyMasterView.reutersCurrency", currencyCode));
		//dcriteria.add(Restrictions.eq("currencyMasterView.isactive", Constants.Yes));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyMasterView> lstCurrencyMasterView=(List<CurrencyMasterView>) findAll(dcriteria);

		return lstCurrencyMasterView;
	}

	private String getCountryDealFromCommentText(String commentText)
	{
		SessionStateManage sessionManage = new SessionStateManage();
		String countryName=null;
		if(sessionManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE))//if("KW".equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE))
		{

			if(commentText!=null)
			{
				countryName=commentText.substring(0,5);
			}
		}

		if(sessionManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE))//if("OM".equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE))
		{

			if(commentText!=null)
			{
				countryName=commentText.substring(0,2);
			}
		}
		return countryName;
	}


	@SuppressWarnings("unchecked")
	public List<BankAccountDetails> getCurrencyBasedOnBankCountry(BigDecimal bankId,BigDecimal currencyID) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");

		dCriteria.add(Restrictions.eq("bankAccountDetails.recordStatus", Constants.Yes));

		dCriteria.setFetchMode("bankAccountDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		dCriteria.setFetchMode("bankAccountDetails.fsCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsCurrencyMaster.currencyId", currencyID));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankAccountDetails>)findAll(dCriteria);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DealTrackerTicketView> getDealTrackerTicketView(Date dealDate,BigDecimal countryId,String currencyCode,String bankCode) throws AMGException {

		SessionStateManage sessionManage = new SessionStateManage();
		DetachedCriteria criteria = DetachedCriteria.forClass(DealTrackerTicketView.class, "dealTrackerTicketView");
		//Criteria criteria = getSession().createCriteria(DealTrackerTicketView.class, "dealTrackerTicketView");
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
		//SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy/MM/dd");
		String sysdate=dateformat.format(dealDate);
		System.out.println("sysdate :"+sysdate);
		
		List<DealTrackerTicketView> lstRtnDealTrackerTicketView =new ArrayList<DealTrackerTicketView>();
		try {

			List<DealTrackerTicketView> lstKWDRtnDealTrackerTicketView =new ArrayList<DealTrackerTicketView>();
			List<DealTrackerTicketView> lstOMNRtnDealTrackerTicketView =new ArrayList<DealTrackerTicketView>();

			Date date = dateformat.parse(sysdate);
			//String sqlDate ="to_date(TIMEOFDEAL,'dd/MM/yy')=to_date('"+sysdate+"','dd/MM/yy')";
			
			String sqlDate =" TIMEOFDEAL=to_date('"+sysdate+"','dd/MM/yy')";
			
			
			
			/*Calendar cal = new GregorianCalendar();
			cal.setTime(date);

			Date today90 = cal.getTime();
			SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
			String minExpDate = form.format(today90);*/
			
		/*	
			String hql = "from DealTrackerTicketView as dealTrackerTicketView where to_date(to_char(dealTrackerTicketView.timeofDeal, 'dd/MM/yyyy'), 'dd/MM/yyyy') = :date";
			Query query = getSession().createQuery(hql); 
			query.setParameter("date", today90);
			//query.setParameter("returnRef", dealID); 
			List<DealTrackerTicketView> lstTreasuryDealHeader1=query.list();*/
			
			criteria.add(Restrictions.sqlRestriction(sqlDate));
			
			//criteria.add(Restrictions.ge("dealTrackerTicketView.timeofDeal", date));
			
			
		
			
			
			/*if(countryId != null){
				criteria.add(Restrictions.eq("dealTrackerTicketView.countryId", countryId));
			}*/
			
			if(currencyCode != null){
				criteria.add(Restrictions.eq("dealTrackerTicketView.pdCurrency", currencyCode));
			}
			
			if(bankCode != null){
				criteria.add(Restrictions.eq("dealTrackerTicketView.pdBank", bankCode));
			}
			

			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

			
			List<DealTrackerTicketView> lstDealTrackerTicketView=(List<DealTrackerTicketView>)findAll(criteria);
			System.out.println("criteria.list()---RABIL--> :"+lstDealTrackerTicketView.size());
			//List<DealTrackerTicketView> lstDealTrackerTicketView= criteria.list();
			
			/*
			for(DealTrackerTicketView dealTrackerTicketView :lstDealTrackerTicketView)
			{
				//dateformat
				String minExpDate1 = dateformat.format(dealTrackerTicketView.getTimeofDeal());
				Date 
				if()
				dateformat.fdealTrackerTicketView.getTimeofDeal()
			}*/

			for(DealTrackerTicketView dealTrackerTicketView :lstDealTrackerTicketView)
			{
				List<TreasuryDealHeader> lstTreasuryDealHeader= getTreasuryHrdForDealTracker(date,dealTrackerTicketView.getDealId().trim());
				if(lstTreasuryDealHeader.size()==0)
				{
					String commentText=getCountryDealFromCommentText(dealTrackerTicketView.getCommentText());
					if(commentText.equalsIgnoreCase(Constants.DEAL_TRACKER_KW_CONBY))
					{
						lstKWDRtnDealTrackerTicketView.add(dealTrackerTicketView);
					}
					if(commentText.equalsIgnoreCase(Constants.DEAL_TRACKER_OM_CONBY))
					{
						lstOMNRtnDealTrackerTicketView.add(dealTrackerTicketView);
					}
				}
			}
			if(sessionManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE))//if("KW".equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE))
			{
				lstRtnDealTrackerTicketView=lstKWDRtnDealTrackerTicketView;
			}

			if(sessionManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE))//if("OM".equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE))
			{
				lstRtnDealTrackerTicketView=lstOMNRtnDealTrackerTicketView;
			}

		} catch (ParseException e) {
			throw new AMGException(e.getMessage());
		}

		return lstRtnDealTrackerTicketView;

	}

	@Override
	public HashMap<String, Object> getIDsFromCodeDealTickets(DealTrackerViewTicketDataTable dealTrackerInfoDTBean,BigDecimal countryId,BigDecimal companyId) {

		HashMap<String, Object> rtnMap= new HashMap<String, Object>();

		List<BankMaster> lstBankMasterDealWith=getBankDetails(dealTrackerInfoDTBean.getDealWith());

		if(lstBankMasterDealWith.size()>1)
		{
			rtnMap.put("DealWithMultipleBnk", Boolean.FALSE);
			
		}else if(lstBankMasterDealWith.size()==1)
		{
			for( BankMaster bankMaster: lstBankMasterDealWith)
			{
				rtnMap.put("DealWithBankCode", bankMaster.getBankCode());
				rtnMap.put("DealWithBankID", bankMaster.getBankId());
			}
			rtnMap.put("DealWithBank", Boolean.TRUE);

		}else
		{
			rtnMap.put("DealWithBank", Boolean.FALSE);
		}

		List<BankMaster> lstPDBankMaster=getBankDetails(dealTrackerInfoDTBean.getPdBankCode());

		BigDecimal pdBankID=null;
		if(lstPDBankMaster.size()>1)
		{
			rtnMap.put("PDMultiBank", Boolean.FALSE);
			
		} else if(lstPDBankMaster.size()==1)
		{
			rtnMap.put("PDBank", Boolean.TRUE);

			for( BankMaster bankMaster: lstPDBankMaster)
			{
				rtnMap.put("PDBankCode", bankMaster.getBankCode());
				rtnMap.put("PDBankID", bankMaster.getBankId());
				pdBankID=bankMaster.getBankId();
			}
		}else
		{
			rtnMap.put("PDBank", Boolean.FALSE);
		}

		List<BankMaster> lstSDBankMaster=getBankDetails(dealTrackerInfoDTBean.getSdBankCode());
		BigDecimal sdBankID=null;
		if(lstSDBankMaster.size()>1)
		{
			rtnMap.put("SDMultiBank", Boolean.FALSE);
			
		}else if(lstSDBankMaster.size()==1)
		{
			rtnMap.put("SDBank", Boolean.TRUE);
			for( BankMaster bankMaster: lstSDBankMaster)
			{
				rtnMap.put("SDBankCode", bankMaster.getBankCode());
				rtnMap.put("SDBankID", bankMaster.getBankId());
				sdBankID=bankMaster.getBankId();
			}

		}else
		{
			rtnMap.put("SDBank", Boolean.FALSE);
		}

		List<CurrencyMasterView> lstPDCurrencyMasterView=getCurrencyDetails(dealTrackerInfoDTBean.getPdCurrencyCode());
		
		if(lstPDCurrencyMasterView.size()>0)
		{
			for( CurrencyMasterView currencyMasterView: lstPDCurrencyMasterView)
			{
				rtnMap.put("PDCurrencyCode", currencyMasterView.getCurrencyCode());
				rtnMap.put("PDCurrencyID", currencyMasterView.getCurrencyId());
				rtnMap.put("PDCurrencyName", currencyMasterView.getCurrencyName());
				break;
			}

			List<BankAccountDetails> lstBankAccountDetails= getCurrencyBasedOnBankCountry((BigDecimal)rtnMap.get("PDBankID"),(BigDecimal)rtnMap.get("PDCurrencyID"));
			if(lstBankAccountDetails.size()>1)
			{
				rtnMap.put("PDMultipleAccount", Boolean.FALSE);
				
			}else if(lstBankAccountDetails.size()==1)
			{
				rtnMap.put("PDCurrency", Boolean.TRUE);
				rtnMap.put("PDAcountDetId", lstBankAccountDetails.get(0).getBankAcctDetId());
				rtnMap.put("PDAcountGlNO", lstBankAccountDetails.get(0).getGlno());
			}else
			{
				rtnMap.put("PDCurrency", Boolean.FALSE);
			}

			
		}else
		{
			rtnMap.put("PDCurrency", Boolean.FALSE);
		}

		List<CurrencyMasterView> lstSDCurrencyMasterView=getCurrencyDetails(dealTrackerInfoDTBean.getSdCurrencyCode());
		
		if(lstSDCurrencyMasterView.size()>0)
		{
			for( CurrencyMasterView currencyMasterView: lstSDCurrencyMasterView)
			{
				rtnMap.put("SDCurrencyCode", currencyMasterView.getCurrencyCode());
				rtnMap.put("SDCurrencyID", currencyMasterView.getCurrencyId());
				rtnMap.put("SDCurrencyName", currencyMasterView.getCurrencyName());
				break;
			}
			List<BankAccountDetails> lstBankAccountDetails= getCurrencyBasedOnBankCountry((BigDecimal)rtnMap.get("SDBankID"),(BigDecimal)rtnMap.get("SDCurrencyID"));
			if(lstBankAccountDetails.size()>0)
			{
				rtnMap.put("SDCurrency", Boolean.TRUE);
				
				

			}else
			{
				rtnMap.put("SDCurrency", Boolean.FALSE);
			}

		}else
		{
			rtnMap.put("SDCurrency", Boolean.FALSE);
		}
		
		//PD INStrn Checking
		
		List<StandardInstruction> lstPDStandardInstruction=getInstrnNumber((BigDecimal)rtnMap.get("PDBankID"),(BigDecimal)rtnMap.get("PDCurrencyID"),countryId,companyId,(BigDecimal)rtnMap.get("PDAcountDetId"),"PD");
		
		if(lstPDStandardInstruction.size()==1)
		{
			rtnMap.put("PDStndInstrn", Boolean.TRUE);
			rtnMap.put("PDStndInstrnId", lstPDStandardInstruction.get(0).getStandardInstructionId());
		}else
		{
			rtnMap.put("PDStndInstrn", Boolean.FALSE);
		}
		
		//SD INStrn Checking
		
		List<StandardInstruction> lstSDStandardInstruction=getInstrnNumber((BigDecimal)rtnMap.get("SDBankID"),(BigDecimal)rtnMap.get("SDCurrencyID"),countryId,companyId,dealTrackerInfoDTBean.getAccountDetId(),"SD");
		
		if(lstSDStandardInstruction.size()==1)
		{
			rtnMap.put("SDStndInstrn", Boolean.TRUE);
			rtnMap.put("SDStndInstrnId", lstSDStandardInstruction.get(0).getStandardInstructionId());
		}else
		{
			rtnMap.put("SDStndInstrn", Boolean.FALSE);
		}
		
		//Account balance check to calculate average rate
		
		String fundGlAcountNo= getfundGlno(dealTrackerInfoDTBean.getAccountDetId());
		
		if(fundGlAcountNo==null)
		{
			rtnMap.put("FundGlAcountNo", Boolean.FALSE);
		}else
		{
			rtnMap.put("FundGlAcountNo", Boolean.TRUE);
			rtnMap.put("SDGlAcountNo", fundGlAcountNo);
		}
		List<AccountBalance> lstAccountBalance=getSaleAvgRate((BigDecimal)rtnMap.get("SDBankID"),(BigDecimal)rtnMap.get("SDCurrencyID"), fundGlAcountNo);
		if(lstAccountBalance.size() != 0){
			for (AccountBalance accountBalance : lstAccountBalance) {
				
				if(accountBalance.getForeignBalance()!=null)
				{
					rtnMap.put("Balance", Boolean.TRUE);
					
					BigDecimal saleAmt= dealTrackerInfoDTBean.getSaleAmount();
					if(saleAmt.compareTo(accountBalance.getForeignBalance())>0)
					{
						rtnMap.put("LessBalance", Boolean.FALSE);
					}else
					{
						rtnMap.put("LessBalance", Boolean.TRUE);
					}
					if(accountBalance.getAverageRate()!=null)
					{
						rtnMap.put("AvgRate", Boolean.TRUE);
					}else
					{
						rtnMap.put("AvgRate", Boolean.FALSE);
					}
				}else
				{
					rtnMap.put("Balance", Boolean.FALSE);
				}
				
			}
		}else
		{
			rtnMap.put("Balance", Boolean.FALSE);
		}
				
		return rtnMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyMaster> getCurrencyNameandCurrencyIdBasedonQuoteName(String quoteName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		criteria.add(Restrictions.eq("currencyMaster.quoteName", quoteName));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyMaster> currencyMasterList= (List<CurrencyMaster>)findAll(criteria);
		return currencyMasterList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveAllFXDealBank(HashMap<String, Object> saveMapInfo) throws Exception {
		
		TreasuryDealHeader saveTreasuryDH = (TreasuryDealHeader) saveMapInfo.get("TreasuryHeader");
		TreasuryDealDetail savePurchaseTDetails = (TreasuryDealDetail) saveMapInfo.get("PurchaseTreasuryDetails");
		List<TreasuryStandardInstruction> lstPurchaseTSI = (List<TreasuryStandardInstruction>) saveMapInfo.get("ListPurchaseTreasurySI");
		TreasuryDealDetail saveSaleTDetails = (TreasuryDealDetail) saveMapInfo.get("SaleTreasuryDetails");
		List<TreasuryStandardInstruction> lstSaleTSI = (List<TreasuryStandardInstruction>) saveMapInfo.get("ListSaleTreasurySI");
		
		
		
		try {
			// save the TreasuryDealHeader		
			getSession().saveOrUpdate(saveTreasuryDH);

			// save the TreasuryDealDetail For Purchase
			getSession().saveOrUpdate(savePurchaseTDetails);
			
			// save the TreasuryStandardInstruction For Purchase of Instructions
			if (lstPurchaseTSI.size() != 0) {
				for (TreasuryStandardInstruction lstPSI : lstPurchaseTSI) {
					getSession().saveOrUpdate(lstPSI);
				}
			}
			
			// save the TreasuryDealDetail For Sale
			getSession().saveOrUpdate(saveSaleTDetails);
			
			// save the TreasuryStandardInstruction For Sale of Instructions
			if (lstSaleTSI.size() != 0) {
				for (TreasuryStandardInstruction lstSSI : lstSaleTSI) {
					getSession().saveOrUpdate(lstSSI);
				}
			}
			
			// save the TreasuryDealDetail For SP
			if(saveMapInfo.get("SPTreasuryDealDetails")!=null)
			{
				TreasuryDealDetail saveSPPurchaseTDetails = (TreasuryDealDetail) saveMapInfo.get("SPTreasuryDealDetails");
				
				if(saveSPPurchaseTDetails!=null)
				{
					getSession().saveOrUpdate(saveSPPurchaseTDetails);
				}
			}
			
			// save the TreasuryStandardInstruction For SP of Instructions
			if(saveMapInfo.get("SPTreasuryStdIns")!=null)
			{
				List<TreasuryStandardInstruction> lstSPTSI = (List<TreasuryStandardInstruction>) saveMapInfo.get("SPTreasuryStdIns");
				if (lstSPTSI.size() != 0) {
					for (TreasuryStandardInstruction lstSPSI : lstSPTSI) {
						getSession().saveOrUpdate(lstSPSI);
					}
				}
			}
			
			int isUpdated = 	updateExTickets(saveTreasuryDH);
			boolean issue = false;
			if(isUpdated != 1){
				issue = true;
			}
			
			if (isUpdated < 1)
			{
				throw new Exception("Approved");
			} else if (isUpdated > 1){
				throw new Exception("Duplicate");
			}
			
			if(issue)
			{
				throw new Exception("OtherException");
			}
			
			//saveUnApprovedGLEntry(saveTreasuryDH);
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
			throw e;
		}

	}

	
	public List<StandardInstruction> getInstrnNumber(BigDecimal bankId,BigDecimal currencyId,BigDecimal countryId,BigDecimal companyId,BigDecimal bankAccDtlId,String instType) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstruction.class, "standardInstruction");

		System.out.println("***************" + bankId + " @@  " + currencyId);

		dCriteria.setFetchMode("standardInstruction.exBankMaster",	FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exBankMaster","exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		dCriteria.setFetchMode("standardInstruction.exCurrenyMaster",	FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exCurrenyMaster","exCurrenyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyMaster.currencyId", currencyId));
		
		
		dCriteria.setFetchMode("standardInstruction.bankAccountDetailsId",	FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.bankAccountDetailsId","bankAccountDetailsId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankAccountDetailsId.bankAcctDetId", bankAccDtlId));
		
		dCriteria.setFetchMode("standardInstruction.exStandardInstructionForAllicationCountry",	FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exStandardInstructionForAllicationCountry","exStandardInstructionForAllicationCountry", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exStandardInstructionForAllicationCountry.countryId", countryId));

		dCriteria.setFetchMode("standardInstruction.fsCompanyMaster",	FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.fsCompanyMaster","fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		dCriteria.add(Restrictions.eq("standardInstruction.isActive",Constants.Yes));
		
		dCriteria.add(Restrictions.eq("standardInstruction.intructionType",instType));

		dCriteria.addOrder(Order.asc("standardInstruction.standardInstructionId"));
		

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<StandardInstruction> pdata = (List<StandardInstruction>) findAll(dCriteria);

		System.out.println("size ::::::::::::::::::getInstrnNumber:::::::::::::::::::::::::"+ pdata.size());

		return pdata;

	}

	@Override
	public List<BankAccountDetails> getBankAccountDetlsBasedOnCode(
			String bankCode, String currencyCode) {
		
		List<BankMaster> lstBankMasterDealWith=getBankDetails(bankCode);
		BigDecimal bankId=null;
		for( BankMaster bankMaster: lstBankMasterDealWith)
		{
			bankId=bankMaster.getBankId();
			
		}
		
		List<CurrencyMasterView> lstSDCurrencyMasterView=getCurrencyDetails(currencyCode);
		BigDecimal currencyId=null;
		if(lstSDCurrencyMasterView.size()>0)
		{
			for( CurrencyMasterView currencyMasterView: lstSDCurrencyMasterView)
			{
				currencyId= currencyMasterView.getCurrencyId();
				break;
			}
	
		}
		
		List<BankAccountDetails> lstBankAccountDetails= getCurrencyBasedOnBankCountry(bankId,currencyId);
		
		return lstBankAccountDetails;
	}
	
	public List<AccountBalance> getSaleAvgRate(BigDecimal bankId,
			BigDecimal currencyId, String accountNo) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				AccountBalance.class, "accountBalance");

		/*
		 * dCriteria.setFetchMode("accountBalance.bankMaster", FetchMode.JOIN);
		 * dCriteria.createAlias("accountBalance.bankMaster", "bankMaster",
		 * JoinType.INNER_JOIN);
		 * dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		 */

		SessionStateManage sessionManage = new SessionStateManage();
		dCriteria.setFetchMode("accountBalance.companyMaster", FetchMode.JOIN);
		dCriteria.createAlias("accountBalance.companyMaster", "companyMaster",
				JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("companyMaster.companyId",
				sessionManage.getCompanyId()));

		dCriteria.setFetchMode("accountBalance.exCurrencyMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("accountBalance.exCurrencyMaster",
				"exCurrencyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions
				.eq("exCurrencyMaster.currencyId", currencyId));

		// dCriteria.add(Restrictions.eq("accountBalance.accountNo",
		// accountNo));
		// dCriteria.setFetchMode("accountBalance.exBankAccountDetails",
		// FetchMode.JOIN);
		// dCriteria.createAlias("accountBalance.exBankAccountDetails",
		// "exBankAccountDetails", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("accountBalance.glSlNumber", accountNo));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<AccountBalance> data = (List<AccountBalance>) findAll(dCriteria);

		return data;
	}
	
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
	
	public void saveUnApprovedGLEntry(TreasuryDealHeader treasuryDealHeader) throws Exception{
		// TODO Auto-generated method stub
		// call procedure
		/*
		 * getSession().getNamedQuery("EX_TREASURY_UAPPV_UGL")
		 * .setBigDecimal("applicationCountryId",
		 * sessionManage.getCountryId()) .setBigDecimal("companyId",
		 * sessionManage.getCompanyId()) .setBigDecimal("documentID",
		 * saveTreasuryDH.getExDocument().getDocumentID())
		 * .setBigDecimal("userFinanceYear",
		 * saveTreasuryDH.getUserFinanceYear())
		 * .setBigDecimal("treasuryDocumentNumber",
		 * saveTreasuryDH.getTreasuryDocumentNumber()) .executeUpdate();
		 */

		Connection connection = null;

		// connection = DBConnection.getdataconnection();
		connection = getDataSourceFromHibernateSession();
		CallableStatement cs;
		try {
			cs = connection.prepareCall(" { call EX_TREASURY_UAPPV_UGL(?,?,?,?,?)}");
			cs.setBigDecimal(1, treasuryDealHeader.getFsCountryMaster().getCountryId());
			cs.setBigDecimal(2, treasuryDealHeader.getFsCompanyMaster().getCompanyId());
			cs.setBigDecimal(3, treasuryDealHeader.getExDocument().getDocumentID());
			cs.setBigDecimal(4, treasuryDealHeader.getUserFinanceYear());
			cs.setBigDecimal(5, treasuryDealHeader.getTreasuryDocumentNumber());
			cs.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error============+" + e.getMessage());
			throw new  Exception( e.getMessage());
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<BankMaster> getBankMasterInfo(String bankCode) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass( BankMaster.class, "bankmaster");

		detachedCriteria.setFetchMode("bankmaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankmaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		
		detachedCriteria.add(Restrictions.eq("bankmaster.reutersBankName", bankCode));
		detachedCriteria.add(Restrictions.eq("bankmaster.recordStatus", Constants.Yes));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankMaster>) findAll(detachedCriteria);
	}

	@Override
	public String getSplitIndicatorFromBankMaster(String bankCode) {
		String splitIndcator=null;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass( BankMaster.class, "bankmaster");

		detachedCriteria.setFetchMode("bankmaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankmaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("bankmaster.bankCode", bankCode));
		detachedCriteria.add(Restrictions.eq("bankmaster.recordStatus", Constants.Yes));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankMaster> bankList = (List<BankMaster>)findAll(detachedCriteria);
		if(bankList.size()>0){
		return bankList.get(0).getSplitIndicator();
		}else{
		return splitIndcator;
		}
	}
	
	/* @Override
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

	  }*/
	
	

	@Override
	public  HashMap< String, String>  getForeignCurrrencyAmountFrmSpclCustomer( 
			BigDecimal docFinYear, BigDecimal docNo) {
	 
		HashMap<String,String> listValues=new HashMap<String, String>();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomerSpecialDealRequest.class, "customerSpecialDealRequest");
		
		detachedCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCustomer", FetchMode.JOIN);
		detachedCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCustomer", "customerSpeacialDealReqCustomer", JoinType.INNER_JOIN);
		
		detachedCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqBankMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqBankMaster", "customerSpeacialDealReqBankMaster", JoinType.INNER_JOIN);
		
		detachedCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCurrencyMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCurrencyMaster", "customerSpeacialDealReqCurrencyMaster", JoinType.INNER_JOIN);
		
		detachedCriteria.setFetchMode("customerSpecialDealRequest.documentFinancialYear", FetchMode.JOIN);
		detachedCriteria.createAlias("customerSpecialDealRequest.documentFinancialYear", "documentFinancialYear", JoinType.INNER_JOIN);
		// detachedCriteria.add(Restrictions.eq("documentFinancialYear.financialYearID", docFinYear));
		
		detachedCriteria.add(Restrictions.eq("customerSpecialDealRequest.documentNumber", docNo));
				
	 if(docFinYear!=null){
		 BigDecimal yearId=getFinancialYearId(docFinYear);
		 detachedCriteria.add(Restrictions.eq("documentFinancialYear.financialYearID", yearId));
	 }
	
 
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CustomerSpecialDealRequest> data=(List<CustomerSpecialDealRequest>) findAll(detachedCriteria);

		if(data.size()>0){
 	    	listValues.put("CUSTOMERREFERENCE", data.get( 0).getCustomerSpeacialDealReqCustomer().getCustomerReference().toString() );
	     	listValues.put("CUST_SPECIALDEALREQUESTID", data.get(0).getCustomerSpecialDealReqId().toString());
	     	listValues.put("FCAMOUNT", data.get(0).getForeignCurrencyAmount().toString() );
		   return  listValues;
	  }else{
			return null;
	   }
	
	}
	
/*public String getCustomerNameBasedOnCustomerId(BigDecimal customerId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "fscustomer");
		
		criteria.add(Restrictions.eq("fscustomer.customerId", customerId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Customer> idproofList= (List<Customer>)findAll(criteria);
		return idproofList.get(0).getShortName();
	}
	*/

	@Override
	public BigDecimal getCurrencyId(String currencyCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMasterView.class,"currencyMasterView");
	
		criteria.add(Restrictions.eq("currencyMasterView.reutersCurrency", currencyCode));
	 	criteria.add(Restrictions.eq("currencyMaster.isactive",  Constants.Yes));
		List<CurrencyMasterView> data=(List<CurrencyMasterView>) findAll(criteria);
		if(data.size()>0){
			return data.get( 0).getCurrencyId();
		}
		else{
		return null;
		}
	}

	@Override
	public BigDecimal getBankId(String bankCode) {
		DetachedCriteria dcriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
	    dcriteria.add(Restrictions.eq("bankMaster.bankCode", bankCode));
	    dcriteria.add(Restrictions.eq("bankMaster.recordStatus",  Constants.Yes));
	    
		List<BankMaster> data=(List<BankMaster>) findAll(dcriteria);
	  
	    if (data.size() > 0) {
		      return data.get(0).getBankId();
	    } else {
		      return null;
	    }

	}

	@Override
	public BigDecimal getFinancialYearId(BigDecimal finacialYear) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFinancialYear.class, "userFinancialYear");
		detachedCriteria.add(Restrictions.eq("userFinancialYear.financialYear", finacialYear));
		List<UserFinancialYear> data=(List<UserFinancialYear>) findAll(detachedCriteria);
		if(data.size()>0){
			return data.get(0).getFinancialYearID();
		}else{
			return null;
			}
		
	}

 

	@Override
	public BigDecimal getCustomerRefBasedOnCustomerId(BigDecimal customerId) {
     BigDecimal customerRef=null;
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "fscustomer");
		
		criteria.add(Restrictions.eq("fscustomer.customerId", customerId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Customer> idproofList= (List<Customer>)findAll(criteria);
	 if(idproofList.size()>0){
		 return idproofList.get(0).getCustomerReference();
	 }else{
		return customerRef;
	 }
	}

	@Override
	public List<ViewCorrespondingBankCountry> getCorrespondingbankCountryList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewCorrespondingBankCountry.class, "ViewCorrespondingBankCountry");
		List<ViewCorrespondingBankCountry> lstViewCorrespondingBankCountry= (List<ViewCorrespondingBankCountry>)findAll(criteria);
		return lstViewCorrespondingBankCountry;
	}

	@Override
	public List<DealTrackerTicketView> getDealTrackerTicketwithNativeQuery(Date currentDate, BigDecimal countryId, String currencyCode, String bankCode) throws AMGException {
		SessionStateManage sessionManage = new SessionStateManage();
		DetachedCriteria criteria = DetachedCriteria.forClass(DealTrackerTicketView.class, "dealTrackerTicketView");
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yy");
		String sysdate = dateformat.format(currentDate);
		List<DealTrackerTicketView> lstRtnDealTrackerTicketView = new ArrayList<DealTrackerTicketView>();
		try {
			List<DealTrackerTicketView> lstKWDRtnDealTrackerTicketView = new ArrayList<DealTrackerTicketView>();
			List<DealTrackerTicketView> lstOMNRtnDealTrackerTicketView = new ArrayList<DealTrackerTicketView>();
			Date date = dateformat.parse(sysdate);
			criteria.add(Restrictions.eq("dealTrackerTicketView.timeofDeal", date));
			/*
			 * if(countryId != null){
			 * criteria.add(Restrictions.eq("dealTrackerTicketView.countryId",
			 * countryId)); }
			 */
			if (currencyCode != null) {
				criteria.add(Restrictions.eq("dealTrackerTicketView.pdCurrency", currencyCode));
			}
			if (bankCode != null) {
				criteria.add(Restrictions.eq("dealTrackerTicketView.pdBank", bankCode));
			}
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			// List<DealTrackerTicketView>
			// lstDealTrackerTicketView=(List<DealTrackerTicketView>)
			// findAll(criteria);
			List<Object> objList = new ArrayList<Object>();
			int day;
			int month;
			int year;
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			int currMonth = cal.get(Calendar.MONTH);
			month = currMonth + 1;
			year = cal.get(Calendar.YEAR);
			day = cal.get(Calendar.DATE);
			StringBuffer query = new StringBuffer(
					"select SLNO,DEALID,TIMEOFDEAL,DEALERNAME,DEALWITH,CONCLUDED_BY,COMMENTTEXT,REUTER_REFERENCE,PD_CURRENCY,SD_CURRENCY,PD_BANK,SD_BANK,PD_VALUE_DATE,SD_VALUE_DATE ,PD_FC_AMT,SALE_AMOUNT,EXCHANGE_RATE_PD,HVD_YEAR,HVD_NO  from  V_EX_TICKETS where to_char(timeofdeal,'dd/mm/yyyy') ='"
							+ day + "/" + month + "/" + year + "'");// =
																	// '20/11/2015';"
			/*
			 * String query = "SELECT * FROM V_EX_TICKETS WHERE V_EX_TICKETS = "
			 * + remittanceModeId + " AND DELIVERY_MODE_ID = " + delivaryId +
			 * " AND APPLICATION_COUNTRY_ID = " + applicationCountryId +
			 * " AND COUNTRY_ID = " + countryId + " AND CURRENCY_ID = " +
			 * currencyId;
			 */
			if (currencyCode != null) {
				query.append("AND PD_CURRENCY=" + currencyCode);
			}
			if (bankCode != null) {
				query.append("AND PD_BANK=" + bankCode);
			}
			System.out.println(query);
			String finalQuery = query.toString();
			objList = getSession().createSQLQuery(finalQuery).list();
			List<DealTrackerTicketView> lstDealTrackerTicketView = new ArrayList<DealTrackerTicketView>();
			for (int i = 0; i < objList.size(); i++) {
				DealTrackerTicketView dealTrackerTicketView = new DealTrackerTicketView();
				Object[] object = (Object[]) objList.get(i);
				if (object[0] != null) {
					dealTrackerTicketView.setSlNo(new BigDecimal(String.valueOf(object[0])));
				}
				if (object[1] != null) {
					dealTrackerTicketView.setDealId(String.valueOf(object[1]));
				}
				if (object[2] != null) {
					dealTrackerTicketView.setTimeofDeal(DateUtil.convertStringToDate(String.valueOf(object[2])));// ///////////////////////////
				}
				if (object[3] != null) {
					dealTrackerTicketView.setDealerName(String.valueOf(object[3]));
				}
				if (object[4] != null) {
					dealTrackerTicketView.setDealWith(String.valueOf(object[4]));
				}
				if (object[5] != null) {
					dealTrackerTicketView.setConcludedBy(String.valueOf(object[5]));
				}
				if (object[6] != null) {
					dealTrackerTicketView.setCommentText(String.valueOf(object[6]));
				}
				if (object[7] != null) {
					dealTrackerTicketView.setReuterReference(String.valueOf(object[7]));
				}
				if (object[8] != null) {
					dealTrackerTicketView.setPdCurrency(String.valueOf(object[8]));
				}
				if (object[9] != null) {
					dealTrackerTicketView.setSdCurrency(String.valueOf(object[9]));
				}
				if (object[10] != null) {
					dealTrackerTicketView.setPdBank(String.valueOf(object[10]));
				}
				if (object[11] != null) {
					dealTrackerTicketView.setSdBank(String.valueOf(object[11]));
				}
				if (object[12] != null) {
					dealTrackerTicketView.setPdValueDate(DateUtil.convertStringToDate(String.valueOf(object[12])));// //////////////////////
				}
				if (object[13] != null) {
					dealTrackerTicketView.setSdValueDate(DateUtil.convertStringToDate(String.valueOf(object[13])));
				}
				if (object[14] != null) {
					dealTrackerTicketView.setPdFcAmt(new BigDecimal(String.valueOf(object[14])));
				}
				if (object[15] != null) {
					dealTrackerTicketView.setSaleAmount(new BigDecimal(String.valueOf(object[15])));
				}
				if (object[16] != null) {
					dealTrackerTicketView.setPdExchangerate(new BigDecimal(String.valueOf(object[16])));
				}
				if (object[17] != null) {
					dealTrackerTicketView.setHighValueYear(new BigDecimal(String.valueOf(object[17])));
				}
				if (object[18] != null) {
					dealTrackerTicketView.setHighValueNO(new BigDecimal(String.valueOf(object[18])));
				}
				lstDealTrackerTicketView.add(dealTrackerTicketView);
			}
			for (DealTrackerTicketView dealTrackerTicketView : lstDealTrackerTicketView) {
				List<TreasuryDealHeader> lstTreasuryDealHeader = getTreasuryHrdForDealTracker(date, dealTrackerTicketView.getDealId().trim());
				if (lstTreasuryDealHeader.size() == 0) {
					String commentText = getCountryDealFromCommentText(dealTrackerTicketView.getCommentText());
					if (commentText.equalsIgnoreCase(Constants.DEAL_TRACKER_KW_CONBY)) {
						lstKWDRtnDealTrackerTicketView.add(dealTrackerTicketView);
					}
					if (commentText.equalsIgnoreCase(Constants.DEAL_TRACKER_OM_CONBY)) {
						lstOMNRtnDealTrackerTicketView.add(dealTrackerTicketView);
					}
				}
			}
			if (sessionManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE))// if("KW".equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE))
			{
				lstRtnDealTrackerTicketView = lstKWDRtnDealTrackerTicketView;
			}
			if (sessionManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE))// if("OM".equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE))
			{
				lstRtnDealTrackerTicketView = lstOMNRtnDealTrackerTicketView;
			}
		} catch (ParseException e) {
			throw new AMGException(e.getMessage());
		}
		return lstRtnDealTrackerTicketView;
	}
	
	@Override
	public HashMap<String, Object> ValidateDealIDWhileUpdate(
			String dealID, Date dealDate) {

		HashMap<String, Object> retDelaMap = new HashMap<String, Object>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealHeader.class, "treasuryDealHeader");
		dCriteria.add(Restrictions.eq("treasuryDealHeader.reutersReference",
				dealID));
		dCriteria.add(Restrictions.eq("treasuryDealHeader.documentDate",
				dealDate));
		
		List<TreasuryDealHeader> lstTreasuryDealHeader = (List<TreasuryDealHeader>) findAll(dCriteria);

		if (lstTreasuryDealHeader.size() != 0) {
			retDelaMap.put("DealID", Boolean.TRUE);
		} else {
			retDelaMap.put("DealID", Boolean.FALSE);
		}

		return retDelaMap;
	}
	
	private int updateExTickets(TreasuryDealHeader saveTreasuryDH)
	{
		String SQl="Update EX_TICKETS set DEAL_COMPANY_ID = "+saveTreasuryDH.getFsCompanyMaster().getCompanyId()+" , "
				+ " DEAL_DOCUMENT_FINANCE_YEAR="+saveTreasuryDH.getUserFinanceYear()+"  , "
						+ " DEAL_DOCUMENT_ID= "+saveTreasuryDH.getExDocument().getDocumentID()+","
				+ "  DEAL_DOCUMENT_NUMBER= " +saveTreasuryDH.getTreasuryDocumentNumber()+ " where DEALID= '"+saveTreasuryDH.getReutersReference()+"' AND DEAL_DOCUMENT_NUMBER IS NULL";
				 //+ " and TIMEOFDEAL = "+saveTreasuryDH.getDocumentDate();
		
		int ui=getSession().createSQLQuery(SQl).executeUpdate();
		System.out.println("ui");
		return ui;
	}
	
	public List<TreasuryDealDetail> getBulkFXDealUnApprovalRecords(BigDecimal companyId,BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealDetail.class, "treasuryDealDetail");
	 
		/*dCriteria.setFetchMode("treasuryDealDetail.treasuryDealDocument",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealDocument","treasuryDealDocument", JoinType.INNER_JOIN);*/
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealCompanyMaster",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealCompanyMaster","treasuryDealCompanyMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealHeader",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealHeader","treasuryDealHeader", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealCountryMaster",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealCountryMaster","treasuryDealCountryMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealDetailBankAccountDetails",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealDetailBankAccountDetails","treasuryDealDetailBankAccountDetails", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealDetailCurrencyMaster",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealDetailCurrencyMaster","treasuryDealDetailCurrencyMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealBankMaster",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealBankMaster","treasuryDealBankMaster", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("treasuryDealHeader.dealWithType",Constants.Fx_BankDealType));
		
		//dCriteria.add(Restrictions.eq("treasuryDealHeader.treasuryDealHeaderId",treasuryDealHeaderId));
		dCriteria.add(Restrictions.eq("treasuryDealHeader.isActive", Constants.U));
		dCriteria.add(Restrictions.eq("treasuryDealDetail.lineType", Constants.PD));
		
		if(companyId!=null)
		{
			dCriteria.add(Restrictions.eq("treasuryDealCompanyMaster.companyId", companyId));
		}
		if(countryId!=null)
		{
			dCriteria.add(Restrictions.eq("treasuryDealCountryMaster.countryId", countryId));
		}
		
		
		if(currencyId!=null)
		{
			dCriteria.add(Restrictions.eq("treasuryDealDetailCurrencyMaster.currencyId", currencyId));
		}
		
		
		if(bankId!=null)
		{
			dCriteria.add(Restrictions.eq("treasuryDealBankMaster.bankId", bankId));
		}
		
		
			
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TreasuryDealDetail> lstTreasuryDealDetail=(List<TreasuryDealDetail>) findAll(dCriteria);
		
		return lstTreasuryDealDetail;
 
	}
	
	public List<BulkDealApprovalDataTable> getBulkUnApproveDealHrDetails(BigDecimal companyId,BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId)
	{
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealHeader.class, "treasuryDealHeader");
		dCriteria.add(Restrictions.eq("treasuryDealHeader.dealWithType",Constants.Fx_BankDealType));
		dCriteria.add(Restrictions.eq("treasuryDealHeader.isActive", Constants.U));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TreasuryDealHeader> lstTreasuryDealHeader=(List<TreasuryDealHeader>) findAll(dCriteria);
		
		List<BulkDealApprovalDataTable> lstBulkDealApprovalDataTable= new ArrayList<BulkDealApprovalDataTable>();
		for(TreasuryDealHeader treasuryDealHeader :lstTreasuryDealHeader)
		{
			BulkDealApprovalDataTable bulkDealApprovalDataTable=new BulkDealApprovalDataTable();
			
			bulkDealApprovalDataTable.setTreasuryHeaderId(treasuryDealHeader.getTreasuryDealHeaderId());
			bulkDealApprovalDataTable.setDealNo(treasuryDealHeader.getTreasuryDocumentNumber());
			bulkDealApprovalDataTable.setDealYear(treasuryDealHeader.getUserFinanceYear());
			bulkDealApprovalDataTable.setExchangeRate(treasuryDealHeader.getPurchaseExchangeRate());
			bulkDealApprovalDataTable.setPurchaseAmount(treasuryDealHeader.getTotalPurchaseFCAmt());
			bulkDealApprovalDataTable.setSaleAmount(treasuryDealHeader.getSaleAmount());
			//bulkDealApprovalDataTable.setSaleBank(saleBank);
			bulkDealApprovalDataTable.setDealBankId(treasuryDealHeader.getExBankMaster().getBankId());
			bulkDealApprovalDataTable.setDealBankName(treasuryDealHeader.getExBankMaster().getBankFullName());
			bulkDealApprovalDataTable.setDealDate(treasuryDealHeader.getValueDate());
			if(treasuryDealHeader.getReutersIndicator()== null)
			{
				bulkDealApprovalDataTable.setAutoManual("Manual");
			}else
			{
				bulkDealApprovalDataTable.setAutoManual("Reuters");
			}
			lstBulkDealApprovalDataTable.add(bulkDealApprovalDataTable);
			
		}
		
		return lstBulkDealApprovalDataTable;
	}
	public String bulkDealApprove(List<BigDecimal> lstTreasuryHrdId,String userName)
	{
		String rtnMesseage="failure";
		if(lstTreasuryHrdId!=null && lstTreasuryHrdId.size()>0)
		{
			boolean multiUserCheck=false;
			for(BigDecimal treasuryHrdId:lstTreasuryHrdId)
			{
				TreasuryDealHeader treasuryDealHeader = (TreasuryDealHeader) getSession().get(TreasuryDealHeader.class, treasuryHrdId);
				
				if(treasuryDealHeader!=null && treasuryDealHeader.getIsActive().equalsIgnoreCase(Constants.Yes))
				{
					multiUserCheck=true;
					break;
				}
			}
			
			if(!multiUserCheck)
			{
				for(BigDecimal treasuryHrdId:lstTreasuryHrdId)
				{
					TreasuryDealHeader treasuryDealHeader = (TreasuryDealHeader) getSession().get(TreasuryDealHeader.class, treasuryHrdId);
					treasuryDealHeader.setApprovedBy(userName);
					treasuryDealHeader.setApprovedDate(new Date());
					treasuryDealHeader.setIsActive(Constants.Yes);
					
					getSession().saveOrUpdate(treasuryDealHeader);
				}
				rtnMesseage="Sucess";
			}else
			{
				rtnMesseage="AlreadyApproval";
			}
			
		}
		return rtnMesseage;
	}
	
	public void bulkDealApprovalDealEmos(BigDecimal treasuryHrdId) throws Exception
	{
		
		Connection connection = null;
		
		TreasuryDealHeader treasuryDealHeader = (TreasuryDealHeader) getSession().get(TreasuryDealHeader.class, treasuryHrdId);
		
		if(treasuryDealHeader!=null && treasuryDealHeader.getIsActive().equalsIgnoreCase(Constants.Y))
		{
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			try {
				cs = connection.prepareCall(" { call EX_TREASURY_APPROVE_EMOS_DEAL(?,?)}"); 
				cs.setBigDecimal(1,treasuryDealHeader.getUserFinanceYear());
				cs.setBigDecimal(2, treasuryDealHeader.getTreasuryDocumentNumber());
				cs.executeUpdate();
			} catch (SQLException e) {
				  log.error( "Error While Approving  "+ e.getMessage());
					String erromsg = "Error While Approving " + " : " + e.getMessage();
					throw new Exception(erromsg);
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		
	}
	
	public String bulkDealApproveWithProcedure(BigDecimal companyId,BigDecimal contryId, List<BigDecimal> lstTreasuryHrdId,String userName) throws Exception
	{	
		String rtnMesseage="failure";
		if(lstTreasuryHrdId!=null && lstTreasuryHrdId.size()>0)
		{
		  for(BigDecimal treasuryHrdId:lstTreasuryHrdId)
			{
			 try {
			    	bulkDealApprovalDealEmos(treasuryHrdId);
			    	rtnMesseage = "Sucess";
			 	} catch (Exception e) {
				 // TODO Auto-generated catch block
					e.printStackTrace();
				}
   			  }
 			}
		return rtnMesseage;
	}	
	
	
	
	/*
	public String bulkDealApproveWithProcedure(BigDecimal companyId,BigDecimal contryId, List<BigDecimal> lstTreasuryHrdId,String userName) throws Exception
	{
		String rtnMesseage="failure";
		if(lstTreasuryHrdId!=null && lstTreasuryHrdId.size()>0)
		{
			
			boolean multiUserCheck=false;
			for(BigDecimal treasuryHrdId:lstTreasuryHrdId)
			{
				TreasuryDealHeader treasuryDealHeader = (TreasuryDealHeader) getSession().get(TreasuryDealHeader.class, treasuryHrdId);
				
				if(treasuryDealHeader!=null && treasuryDealHeader.getIsActive().equalsIgnoreCase(Constants.Yes))
				{
					multiUserCheck=true;
					break;
				}
			}
			
			if(!multiUserCheck)
			{
				Connection connection = null;

				// connection = DBConnection.getdataconnection();
				
				for(BigDecimal treasuryHrdId:lstTreasuryHrdId)
				{
					TreasuryDealHeader treasuryDealHeader = (TreasuryDealHeader) getSession().get(TreasuryDealHeader.class, treasuryHrdId);
					
					if(treasuryDealHeader!=null && treasuryDealHeader.getIsActive().equalsIgnoreCase(Constants.U))
					{
						connection = getDataSourceFromHibernateSession();
						CallableStatement cs;
						try {
							//cs = connection.prepareCall(" { call EX_TREASURY_UAPPV_UGL(?,?,?,?,?)}");  EX_TREASURY_UAPPV_UGL_BULK
							cs = connection.prepareCall(" { call EX_TREASURY_UAPPV_UGL_BULK_1(?,?,?,?,?)}"); 
							cs.setBigDecimal(1,contryId);
							cs.setBigDecimal(2, companyId);
							cs.setBigDecimal(3, treasuryDealHeader.getExDocument().getDocumentID());
							cs.setBigDecimal(4, treasuryDealHeader.getUserFinanceYear());
							cs.setBigDecimal(5, treasuryDealHeader.getTreasuryDocumentNumber());
							cs.executeUpdate();
							rtnMesseage="Sucess";
						} catch (SQLException e) {
							  log.error( "Error While Approving  "+ e.getMessage());
								String erromsg = "Error While Approving " + " : " + e.getMessage();
								throw new Exception(erromsg);
						} finally {
							if (connection != null) {
								try {
									connection.close();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
				
			}else
			{

				rtnMesseage="AlreadyApproval";
			
			}
			
			
			
						
		}
		return rtnMesseage;
	}
	*/
		
	public String bulkDealDelete(List<BigDecimal> lstTreasuryHrdId,String userName)
	{
		
		String rtnMesseage=null;
		
		if(lstTreasuryHrdId!=null && lstTreasuryHrdId.size()>0)
		{
			
			boolean multiUserCheck=false;
			for(BigDecimal treasuryHrdId:lstTreasuryHrdId)
			{
				TreasuryDealHeader treasuryDealHeader = (TreasuryDealHeader) getSession().get(TreasuryDealHeader.class, treasuryHrdId);
				
				if(treasuryDealHeader!=null && treasuryDealHeader.getIsActive().equalsIgnoreCase(Constants.Yes))
				{
					multiUserCheck=true;
					break;
				}
			}
			if(!multiUserCheck)
			{
				for(BigDecimal treasuryHrdId:lstTreasuryHrdId)
				{
					TreasuryDealHeader treasuryDealHeader = (TreasuryDealHeader) getSession().get(TreasuryDealHeader.class, treasuryHrdId);
					treasuryDealHeader.setModifiedBy(userName);
					treasuryDealHeader.setModifiedDate(new Date());
					treasuryDealHeader.setIsActive(Constants.D);
					
					getSession().saveOrUpdate(treasuryDealHeader);
				}
				rtnMesseage="Sucess";
			}else
			{
				rtnMesseage="AlreadyApproval";
			}
			
		}
		return rtnMesseage;
	}
	public List<VwExBulkFxDeal> getViewBulckFxDDealApproval(BigDecimal companyId,BigDecimal countryId,BigDecimal bankCountryId,BigDecimal bankId)
	{
		DetachedCriteria dCriteria = DetachedCriteria.forClass(VwExBulkFxDeal.class, "vwExBulkFxDeal");
		 
		if(companyId!=null)
		{
			dCriteria.add(Restrictions.eq("vwExBulkFxDeal.companyId", companyId));
		}
		if(countryId!=null)
		{
			dCriteria.add(Restrictions.eq("vwExBulkFxDeal.applicationCountryId", countryId));
		}
		
		
		if(bankCountryId!=null)
		{
			dCriteria.add(Restrictions.eq("vwExBulkFxDeal.bankCountryId", bankCountryId));
		}
		
		
		if(bankId!=null)
		{
			dCriteria.add(Restrictions.eq("vwExBulkFxDeal.dealWithBankId", bankId));
		}
		dCriteria.addOrder(Order.asc("vwExBulkFxDeal.documentNumber"));
		
		
			
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<VwExBulkFxDeal> lstVwExBulkFxDeal=(List<VwExBulkFxDeal>) findAll(dCriteria);
		return lstVwExBulkFxDeal;
		
	}
}

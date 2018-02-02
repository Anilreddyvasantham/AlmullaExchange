package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IDealTrackerInfoService;
import com.amg.exchange.treasury.service.IFXDetailInformationService;
import com.amg.exchange.treasury.viewModel.DealTrackerTicketView;
import com.amg.exchange.util.SessionStateManage;


@Component("dealTrackerInfoBean")
@Scope("session")

public class DealTrackerInfoBean<T> implements Serializable {

	/**
	 * DealTrackerInfoBean<T>
	 */
	private static final long serialVersionUID = 1L;

	private Date dealDate;
	private String dealTrackValueDate;
	private Date minDate;

	private List <DealTrackerInfoDTBean> lstDealTrackerInfoDTBean= new ArrayList<DealTrackerInfoDTBean>();
	private Date effectiveMinDate = new Date();
	private SessionStateManage sessionManage = new SessionStateManage();
	
	@Autowired
	IDealTrackerInfoService<T> iDealTrackerInfoService;
	
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	FXDetailInfoBean<T> fXDetailInfoBean1;

	@Autowired
	IFXDetailInformationService<T> fXDetailInformationService;
	
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public String getDealTrackValueDate() {
		return dealTrackValueDate;
	}
	public void setDealTrackValueDate(String dealTrackValueDate) {
		this.dealTrackValueDate = dealTrackValueDate;
	}
	public List<DealTrackerInfoDTBean> getLstDealTrackerInfoDTBean() {
		return lstDealTrackerInfoDTBean;
	}
	public void setLstDealTrackerInfoDTBean(
			List<DealTrackerInfoDTBean> lstDealTrackerInfoDTBean) {
		this.lstDealTrackerInfoDTBean = lstDealTrackerInfoDTBean;
	}
	
	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}
	
	
	public Date getMinDate() {
		return new Date();
	}
	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}
	public List <DealTrackerInfoDTBean> addRecordsToDataTable()
	{
		List <DealTrackerInfoDTBean> lstLocalDealTrackerInfoDTBean= new ArrayList<DealTrackerInfoDTBean>();
		try {
			List<DealTrackerTicketView> lstDealTrackerTicketView = iDealTrackerInfoService.getDealTrackerTicketViewInfo(new SimpleDateFormat("dd/MM/yyyy").parse(getDealTrackValueDate()));
			
			for(DealTrackerTicketView dealTrackerTicketView:lstDealTrackerTicketView)
			{
				
				if(dealTrackerTicketView.getPdBank()!=null){
					
					DealTrackerInfoDTBean dealTrackerInfoDTBean= new DealTrackerInfoDTBean();

					dealTrackerInfoDTBean.setSlNo(dealTrackerTicketView.getSlNo());
					dealTrackerInfoDTBean.setDealId(dealTrackerTicketView.getDealId());
					dealTrackerInfoDTBean.setTimeofDeal(dealTrackerTicketView.getTimeofDeal());
					dealTrackerInfoDTBean.setDealerName(dealTrackerTicketView.getDealerName());
					dealTrackerInfoDTBean.setDealWith(dealTrackerTicketView.getDealWith());
					dealTrackerInfoDTBean.setConcludedBy(dealTrackerTicketView.getConcludedBy());
					dealTrackerInfoDTBean.setCommentText(dealTrackerTicketView.getCommentText());
					dealTrackerInfoDTBean.setReturnReference(dealTrackerTicketView.getReuterReference());
					dealTrackerInfoDTBean.setPdCurrency(dealTrackerTicketView.getPdCurrency());
					dealTrackerInfoDTBean.setSdCurrency(dealTrackerTicketView.getSdCurrency());

					List<BankMaster> bankList = generalService.getAllBankCodeFromBankMaster(dealTrackerTicketView.getPdBank());
					if(bankList.size()>0){
						dealTrackerInfoDTBean.setPdBankName(bankList.get(0).getBankFullName());
					}else{
						dealTrackerInfoDTBean.setPdBankName(dealTrackerTicketView.getPdBank());
					}
					dealTrackerInfoDTBean.setPdBank(dealTrackerTicketView.getPdBank());
					dealTrackerInfoDTBean.setSdBank(dealTrackerTicketView.getSdBank());
					dealTrackerInfoDTBean.setPdValueDate(dealTrackerTicketView.getPdValueDate());
					dealTrackerInfoDTBean.setSdValueDate(dealTrackerTicketView.getSdValueDate());
					dealTrackerInfoDTBean.setPdFcAmt(dealTrackerTicketView.getPdFcAmt());
					dealTrackerInfoDTBean.setSaleAmount(dealTrackerTicketView.getSaleAmount());
					dealTrackerInfoDTBean.setCommentText(dealTrackerTicketView.getCommentText());
					dealTrackerInfoDTBean.setPdExchangerate(dealTrackerTicketView.getPdExchangerate());


					lstLocalDealTrackerInfoDTBean.add(dealTrackerInfoDTBean);
				}
				
				
			}
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return lstLocalDealTrackerInfoDTBean;
		
	}
	public void onDateSelect(SelectEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		setDealTrackValueDate(format.format(event.getObject()));
		addRecords();
	}
	
	public void addRecords(){
		List <DealTrackerInfoDTBean> lstLocalDealTrackerInfoDTBean=addRecordsToDataTable();
		
		setLstDealTrackerInfoDTBean(lstLocalDealTrackerInfoDTBean);

	}

	public void clearCache()
	{
		try {
			lstDealTrackerInfoDTBean.clear();
			setDealDate(null);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/DealTrackerInfoBean.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public void callFromFxDealBank()
	{
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/DealTrackerInfoBean.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void getSelectRecordFrom(DealTrackerInfoDTBean dealTrackerInfoDTBean)
	{
		
		
		HashMap<String, Object> rtnCodeMap=iDealTrackerInfoService.getIDsFromCode(dealTrackerInfoDTBean);
		
		//Validation check
		Boolean dealWithBank=(Boolean)rtnCodeMap.get("DealWithBank");
		if(!dealWithBank.booleanValue())
		{
			RequestContext.getCurrentInstance().execute("dealWithBank.show();");
			return;
		}
		
		Boolean pdBank=(Boolean)rtnCodeMap.get("PDBank");
		if(!pdBank.booleanValue())
		{
			RequestContext.getCurrentInstance().execute("pdBank.show();");
			return;
		}
		
		Boolean sdBank=(Boolean)rtnCodeMap.get("SDBank");
		if(!sdBank.booleanValue())
		{
			RequestContext.getCurrentInstance().execute("sDBank.show();");
			return;
		}
		
		Boolean pdCurrency=(Boolean)rtnCodeMap.get("PDCurrency");
		if(!pdCurrency.booleanValue())
		{
			RequestContext.getCurrentInstance().execute("pdCurrency.show();");
			return;
		}
		
		Boolean sdCurrency=(Boolean)rtnCodeMap.get("SDCurrency");
		if(!sdCurrency.booleanValue())
		{
			RequestContext.getCurrentInstance().execute("sdCurrency.show();");
			return;
		}
		
		if(dealTrackerInfoDTBean.getDealId()!=null)
		{
			HashMap<String, Object> retDelaMap = null;
			try {
				retDelaMap = fXDetailInformationService.ValidateDealID(dealTrackerInfoDTBean.getDealId().trim(),new SimpleDateFormat("dd/MM/yyyy").parse(getDealTrackValueDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Boolean checkDealId=(Boolean)retDelaMap.get("DealID");
			
			if(checkDealId.booleanValue())
			{
				RequestContext.getCurrentInstance().execute("dealidexist.show();");
				return;
			}
		}
		
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			context.redirect("../treasury/DealTrackerFXDealWithBnakBean.xhtml");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		fXDetailInfoBean1.callFromDealTracker(dealTrackerInfoDTBean,rtnCodeMap);
	}
	public void exit() throws IOException {
        if(sessionManage.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	public void refreshList(Date prevouseDealDate)
	{
		setDealTrackValueDate(new SimpleDateFormat("dd/MM/yyyy").format(prevouseDealDate));
		List <DealTrackerInfoDTBean> lstLocalDealTrackerInfoDTBean=addRecordsToDataTable();
		
		setLstDealTrackerInfoDTBean(lstLocalDealTrackerInfoDTBean);
	}
	
	public void exitFromDealTracker(){
		addRecords();
		fXDetailInfoBean1.setSuccessRender(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/DealTrackerInfoBean.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void callDeal()
	{
		addRecords();
	}
}

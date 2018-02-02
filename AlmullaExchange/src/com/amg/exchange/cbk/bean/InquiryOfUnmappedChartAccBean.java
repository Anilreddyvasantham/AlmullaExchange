package com.amg.exchange.cbk.bean;

import java.io.IOException;

import javax.faces.context.FacesContext;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cbk.model.ViewExUnmappedGLS;
import com.amg.exchange.cbk.service.CBKReportsService;

@Component("inquiryOfUnmappedChartAccBean")
@Scope("session")
public class InquiryOfUnmappedChartAccBean {
	
	@Autowired
	CBKReportsService cBKReportsService;
	
	private List<ViewExUnmappedGLS> viewExUnmappedGLS = null;
	
	
	
	
	
	public void pageNavigation() {
		try {		
			List<ViewExUnmappedGLS> getViewExUnmappedGLSList = cBKReportsService.getViewExUnmappedGLSList();
			setViewExUnmappedGLS(getViewExUnmappedGLSList);
			
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../cbk/inquiryOfUnmappedChartAcc.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	//Generate getters and setters.

	public CBKReportsService getcBKReportsService() {
		return cBKReportsService;
	}
	public void setcBKReportsService(CBKReportsService cBKReportsService) {
		this.cBKReportsService = cBKReportsService;
	}
	public List<ViewExUnmappedGLS> getViewExUnmappedGLS() {
		return viewExUnmappedGLS;
	}
	public void setViewExUnmappedGLS(List<ViewExUnmappedGLS> viewExUnmappedGLS) {
		this.viewExUnmappedGLS = viewExUnmappedGLS;
	}	

}

package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.remittance.service.ICustomerAlmTransationCheckService;
import com.amg.exchange.util.SessionStateManage;

@Component("customerAlmTrasactionCheck")
@Scope("session")
public class CustomerAlmTransationCheckBean<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	List<CustomerAlmTrasactionCheckProcedure> almcheckList=new ArrayList<CustomerAlmTrasactionCheckProcedure>();
	
	
	CustomerAlmTrasactionCheckProcedure almcheckSinglerow=new CustomerAlmTrasactionCheckProcedure();
	
	SessionStateManage session=new SessionStateManage();
	
	
	@Autowired
	ICustomerAlmTransationCheckService<T> icustomerAlmTransationCheckService;


	public List<CustomerAlmTrasactionCheckProcedure> getAlmcheckList() {
		almcheckList.clear();
		almcheckSinglerow=icustomerAlmTransationCheckService.getCustomerAlmTrasactionCheckService(session.getCountryId(),new BigDecimal(165));
		
		almcheckList.add(almcheckSinglerow);
		
		return almcheckList;
	}


	public void setAlmcheckList(
			List<CustomerAlmTrasactionCheckProcedure> almcheckList) {
		this.almcheckList = almcheckList;
	}
	
	
	
	
	/*public List<CustomerAlmTrasactionCheckProcedure> getAlmcheck() {
		almcheckSinglerow=icustomerAlmTransationCheckService.getCustomerAlmTrasactionCheckService(new BigDecimal(120),new BigDecimal(165));
		
		almcheckList.add(almcheckSinglerow);
		return almcheckList;
	}

	public void setAlmcheck(List<CustomerAlmTrasactionCheckProcedure> almcheckList) {
		this.almcheckList = almcheckList;
	}
*/
	
	
	
	
	

}

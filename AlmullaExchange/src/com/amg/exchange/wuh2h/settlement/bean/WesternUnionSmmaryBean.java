package com.amg.exchange.wuh2h.settlement.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.wuh2h.service.IWUH2HService;
import com.amg.exchange.wuh2h.settlement.model.WUTxnSummaryView;
import com.amg.exchange.wuh2h.settlement.service.IWUH2HSettlementService;

@SuppressWarnings({ "unused" })
@Component("wuh2hSummary")
@Scope("session")
public class WesternUnionSmmaryBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger log = Logger.getLogger(WesternUnionSmmaryBean.class);

	/*
	 * Autowire Configuration
	 */

	@Autowired
	IWUH2HSettlementService iwuh2hService;

	@Autowired
	IGeneralService<T> generalService;

	public WesternUnionSmmaryBean() {
	}

	/*
	 * Western Union Page Navigation
	 */
	public void pageNavigation() {
		try {
			setTransactionDate(null);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wuh2h/wuh2hsummaryparam.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Properties
	List<WUTxnSummary> wuSummaryTxnList = new ArrayList<WUTxnSummary>();
	List<WUTransaction> summaryList = new ArrayList<WUTransaction>();
	private Date transactionDate;
	private String errorMessage;

	public List<WUTxnSummary> getWuSummaryTxnList() {
		return wuSummaryTxnList;
	}
	public void setWuSummaryTxnList(List<WUTxnSummary> wuSummaryTxnList) {
		this.wuSummaryTxnList = wuSummaryTxnList;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	// Fetch Transaction summary report data
	public void fetchSummaryReport(Date txnDate){

		if(summaryList!=null || !summaryList.isEmpty()){
			summaryList.clear();
		}
		if(wuSummaryTxnList!=null || !wuSummaryTxnList.isEmpty()){
			wuSummaryTxnList.clear();
		}

		List<WUTxnSummaryView> summeryViewList = iwuh2hService.getWUTransactionSummary(txnDate);
		List<WUTxnSummaryView> summeryViewList1; 

		WUTransaction wuTransaction = null;
		BigDecimal totalCount = new BigDecimal(0);
		BigDecimal totalAmount = new BigDecimal(0);

		for(WUTxnSummaryView summeryView:summeryViewList){
			wuTransaction = new WUTransaction();
			totalCount = new BigDecimal(0);
			totalAmount = new BigDecimal(0);

			wuTransaction.setCurrencyCode(summeryView.getCurrencyCode());
			wuTransaction.setLocationCode(summeryView.getLocationCode());
			wuTransaction.setUnsettledCount(summeryView.getUnsettledCount());
			wuTransaction.setUnsettledAmount(summeryView.getUnsettledAmount());
			wuTransaction.setSettledCount(summeryView.getSettledCount());
			wuTransaction.setSettledAmount(summeryView.getSettledAmount());
			wuTransaction.setType(summeryView.getType());

			totalCount = totalCount.add(summeryView.getUnsettledCount().add(summeryView.getSettledCount()));
			totalAmount = totalAmount.add(summeryView.getUnsettledAmount().add(summeryView.getSettledAmount()));

			wuTransaction.setTotalCount(totalCount);
			wuTransaction.setTotalAmount(totalAmount);

			summaryList.add(wuTransaction);
		}

		WUTxnSummary wuTxnSummary = new WUTxnSummary();

		wuTxnSummary.setSummaryList(summaryList);
		wuTxnSummary.setTransactionDate(txnDate);

		wuSummaryTxnList.add(wuTxnSummary);

	}

	// Init summary report
	private JasperPrint jasperPrint;
	public void summeryReportInit() throws JRException {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");

		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(wuSummaryTxnList);

		String reportPath = realPath + "//reports//design//wutxnsummaryrpt.jasper";
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);

	}

	//Generate summary report
	public void generateWUTxnSummaryReport() {
		ServletOutputStream servletOutputStream = null;
		try {

			fetchSummaryReport(getTransactionDate());
			summeryReportInit();

			byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);

			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.setHeader("Cache-Control", "cache, must-revalidate");
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=WUTxnSummary.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();

		} catch (NullPointerException ne) {
			log.info("Exception in generateWUTxnSummaryReport:" + ne.getMessage());
			setErrorMessage("Method Name::generateWUTxnSummaryReport");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		} catch (Exception exception) {
			log.info("Exception in generateWUTxnSummaryReport:" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		} finally {
			if (servletOutputStream != null) {
				try {
					servletOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	public void clear(){
		setTransactionDate(null);
	}
	
	public void exit() throws IOException {
		if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

}

package com.amg.exchange.wuh2h.bean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.wuh2h.model.VwBrnc;
import com.amg.exchange.wuh2h.model.WUH2HTransactionView;
import com.amg.exchange.wuh2h.service.IWUBranchWiseTransService;
import com.amg.exchange.wuh2h.settlement.bean.WUH2HTransactionInfo;

@Component("wUBranchWiseTransactionCountMBean")
@Scope("session")
public class WUBranchWiseTransactionCountMBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date fromDate;
	private Date toDate;
	private String errorMessage;

	private List<WUH2HTransactionInfo> wuh2hTransactionInfoList = null;
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	IWUBranchWiseTransService wUBranchWiseTransService;





	public void pageNavigation() {
		try {
			clear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wuh2h/wuBranchWiseTransactionCount.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void clear(){
		setFromDate(null);
		setToDate(null);
	}	


	public void generateWUH2HTxnExcelReport(){
		try{
			if(getFromDate()==null || getToDate()==null){
				RequestContext.getCurrentInstance().execute("selectFromToDates.show();");
				return;			
			}else{
				List<WUH2HTransactionInfo> lstWuh2hTransactionInfo = new ArrayList<WUH2HTransactionInfo>();
				//List<WUH2HTransactionView> txnList = wUBranchWiseTransService.getBranchwiseTransactionCountList(new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION),new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT),sessionStateManage.getCompanyId(),sessionStateManage.getCountryId(),getFromDate(),getToDate());

				HashMap<String,Object> sendReceiceTransList = wUBranchWiseTransService.branchWiseSendRecieveList(getFromDate(),getToDate());
				//List<VwBrnc> locList = (List<VwBrnc>) sendReceiceTransList.get("BRANCHLIST");
				HashMap<BigDecimal, WUH2HTransactionView> sendTransaction= (HashMap<BigDecimal, WUH2HTransactionView>) sendReceiceTransList.get("SENDTRANSACTION");
				HashMap<BigDecimal, WUH2HTransactionView> receiveTransaction = (HashMap<BigDecimal, WUH2HTransactionView>) sendReceiceTransList.get("RECEIVETRANSACTION");

				// checking for send trnx and receive trnx at once and deleting receive trnx if any left out will cover in next for loop
				for(Map.Entry<BigDecimal, WUH2HTransactionView> vwBrnc : sendTransaction.entrySet()){
					
					WUH2HTransactionInfo wUH2HTransactionInfo = new WUH2HTransactionInfo();
					
					if(sendTransaction.get(vwBrnc.getKey()) != null){
						wUH2HTransactionInfo.setBranchName(sendTransaction.get(vwBrnc.getKey()).getBranchName());
						wUH2HTransactionInfo.setSendTxnCount(sendTransaction.get(vwBrnc.getKey()).getSendCount());
						wUH2HTransactionInfo.setSendTxnAmount(sendTransaction.get(vwBrnc.getKey()).getSendTotalTxnAmount());
					}else{
						wUH2HTransactionInfo.setSendTxnCount(BigDecimal.ZERO);
						wUH2HTransactionInfo.setSendTxnAmount(BigDecimal.ZERO);
					}
					if(receiveTransaction.get(vwBrnc.getKey()) != null){
						wUH2HTransactionInfo.setBranchName(receiveTransaction.get(vwBrnc.getKey()).getBranchName());
						wUH2HTransactionInfo.setReceiveTxnCount(receiveTransaction.get(vwBrnc.getKey()).getSendCount());
						wUH2HTransactionInfo.setReceiveTxnAmount(receiveTransaction.get(vwBrnc.getKey()).getSendTotalTxnAmount());
						receiveTransaction.remove(vwBrnc.getKey());
					}else{
						wUH2HTransactionInfo.setReceiveTxnCount(BigDecimal.ZERO);
						wUH2HTransactionInfo.setReceiveTxnAmount(BigDecimal.ZERO);
					}

					lstWuh2hTransactionInfo.add(wUH2HTransactionInfo);
					
				}
				
				// if send trnx missed any trnx need to cover in this loop
				for (Map.Entry<BigDecimal, WUH2HTransactionView> vwBrnc1 : receiveTransaction.entrySet()) {
					WUH2HTransactionInfo wUH2HTransactionInfo = new WUH2HTransactionInfo();

					if(sendTransaction.get(vwBrnc1.getKey()) != null){
						wUH2HTransactionInfo.setBranchName(sendTransaction.get(vwBrnc1.getKey()).getBranchName());
						wUH2HTransactionInfo.setSendTxnCount(sendTransaction.get(vwBrnc1.getKey()).getSendCount());
						wUH2HTransactionInfo.setSendTxnAmount(sendTransaction.get(vwBrnc1.getKey()).getSendTotalTxnAmount());
					}else{
						wUH2HTransactionInfo.setSendTxnCount(BigDecimal.ZERO);
						wUH2HTransactionInfo.setSendTxnAmount(BigDecimal.ZERO);
					}
					if(receiveTransaction.get(vwBrnc1.getKey()) != null){
						wUH2HTransactionInfo.setBranchName(receiveTransaction.get(vwBrnc1.getKey()).getBranchName());
						wUH2HTransactionInfo.setReceiveTxnCount(receiveTransaction.get(vwBrnc1.getKey()).getSendCount());
						wUH2HTransactionInfo.setReceiveTxnAmount(receiveTransaction.get(vwBrnc1.getKey()).getSendTotalTxnAmount());
					}else{
						wUH2HTransactionInfo.setReceiveTxnCount(BigDecimal.ZERO);
						wUH2HTransactionInfo.setReceiveTxnAmount(BigDecimal.ZERO);
					}

					lstWuh2hTransactionInfo.add(wUH2HTransactionInfo);
				}

				/*for(VwBrnc vwBrnc : locList){

					sendCount = BigDecimal.ZERO;
					sendAmount = BigDecimal.ZERO;
					receiveCount = BigDecimal.ZERO;
					receiveAmount = BigDecimal.ZERO;

					WUH2HTransactionInfo wUH2HTransactionInfo = new WUH2HTransactionInfo();
					for(WUH2HTransactionView sendTrns : sendTransaction){
						if(sendTrns.getLocCode().compareTo(vwBrnc.getLocCod())==0){
							sendCount = sendTrns.getSendCount();
							sendAmount = sendTrns.getSendTotalTxnAmount();

							for(WUH2HTransactionView receiveTrns : receiveTransaction){
								if(receiveTrns.getLocCode().compareTo(vwBrnc.getLocCod())==0){
									receiveCount = receiveTrns.getReceiveCount();
									receiveAmount = receiveTrns.getReceiveTotalTxnAmount();
									break;
								}						
							}
						}

						if(sendCount.compareTo(new BigDecimal(0)) > 0){
							wUH2HTransactionInfo.setBranchName(vwBrnc.getFuDesc());
							wUH2HTransactionInfo.setSendTxnCount(sendCount);
							wUH2HTransactionInfo.setSendTxnAmount(sendAmount);

							if(receiveCount.compareTo(new BigDecimal(0))> 0){
								wUH2HTransactionInfo.setReceiveTxnCount(receiveCount);
								wUH2HTransactionInfo.setReceiveTxnAmount(receiveAmount);
							} else{
								wUH2HTransactionInfo.setReceiveTxnCount(new BigDecimal(0));
								wUH2HTransactionInfo.setReceiveTxnAmount(new BigDecimal(0));
							}
							lstWuh2hTransactionInfo.add(wUH2HTransactionInfo);
						}else{

							if(receiveCount.compareTo(new BigDecimal(0))> 0){
								wUH2HTransactionInfo.setBranchName(vwBrnc.getFuDesc());
								wUH2HTransactionInfo.setSendTxnCount(new BigDecimal(0));
								wUH2HTransactionInfo.setSendTxnAmount(new BigDecimal(0));
								wUH2HTransactionInfo.setReceiveTxnCount(receiveCount);
								wUH2HTransactionInfo.setReceiveTxnAmount(receiveAmount);
								lstWuh2hTransactionInfo.add(wUH2HTransactionInfo);
							}
						}										
					}

				}*/

				setWuh2hTransactionInfoList(lstWuh2hTransactionInfo);	


				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("Transaction Report");
				sheet.addMergedRegion(CellRangeAddress.valueOf("A3:A4"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("B3:C3"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("D3:E3"));

				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
				String fromDate = dateformat.format(getFromDate());
				String toDate = dateformat.format(getToDate());

				Object[][] datatypes = {
						{ "From Date", fromDate, "", "", "To Date", toDate },
						{},
						{ "Branch Name", "Send Transaction", "",
							"Receive Transaction","" },
							{ "", "Count", "Amount", "Count", "Amount" } 

				};


				int rowNum = 0;
				System.out.println("Creating excel");

				for (Object[] datatype : datatypes) {
					Row row = sheet.createRow(rowNum++);
					int colNum = 0;
					for (Object field : datatype) {
						Cell cell = row.createCell(colNum++);

						if (rowNum == 3 || rowNum == 4) {
							CellStyle style = workbook.createCellStyle();
							style.setBorderBottom(CellStyle.BORDER_THICK);
							style.setBorderLeft(CellStyle.BORDER_THICK);
							style.setBorderRight(CellStyle.BORDER_THICK);
							style.setBorderTop(CellStyle.BORDER_THICK);

							style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
							style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
							style.setRightBorderColor(IndexedColors.BLACK.getIndex());
							style.setTopBorderColor(IndexedColors.BLACK.getIndex());

							cell.setCellStyle(style);
						}
						if (field instanceof String) {
							cell.setCellValue((String) field);
						} else if (field instanceof Integer) {
							cell.setCellValue((Integer) field);
						}
					}
				}

				rowNum=4;
				int colNum = 0;
				int count=0;
				int lsitSize=getWuh2hTransactionInfoList().size();

				while(count<lsitSize)
				{
					WUH2HTransactionInfo wuH2HTransactionInfo  = wuh2hTransactionInfoList.get(count);

					Row row = sheet.createRow(rowNum++);

					Cell cell1 = row.createCell(colNum++);
					cell1.setCellValue(wuH2HTransactionInfo.getBranchName());

					Cell cell2 = row.createCell(colNum++);
					cell2.setCellValue(wuH2HTransactionInfo.getSendTxnCount().toString());

					Cell cell3 = row.createCell(colNum++);
					cell3.setCellValue(wuH2HTransactionInfo.getSendTxnAmount().toString());

					Cell cell4 = row.createCell(colNum++);
					cell4.setCellValue(wuH2HTransactionInfo.getReceiveTxnCount().toString());

					Cell cell5 = row.createCell(colNum++);
					cell5.setCellValue(wuH2HTransactionInfo.getReceiveTxnAmount().toString());
					colNum = 0;
					count=count+1;
				}

				clear();

				try {
					FacesContext fc = FacesContext.getCurrentInstance();

					HttpServletResponse httpServletResponse = (HttpServletResponse) fc.getExternalContext().getResponse();
					httpServletResponse.reset();

					httpServletResponse.setHeader("Cache-Control", "cache, must-revalidate");
					httpServletResponse.setContentType("application/vnd.ms-excel");
					httpServletResponse.addHeader("Content-disposition", "attachment; filename=WUH2HTxnreport.xlsx");

					workbook.write(httpServletResponse.getOutputStream());

					fc.responseComplete();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.out.println("Done");
				setWuh2hTransactionInfoList(null);
			}

		}catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");		
		}
	}



	public void toDateSelected(){
		if(getFromDate()==null){
			setToDate(null);
			RequestContext.getCurrentInstance().execute("fromDate.show();");			
		} else if(getToDate().compareTo(getFromDate())<0){
			setToDate(null);
			RequestContext.getCurrentInstance().execute("toDtLessThanFromDt.show();");
		}
	}
	
	public void fromDateSelected(){
		if(getToDate()!=null){
			if(getToDate().compareTo(getFromDate())<0){
				setToDate(null);
				RequestContext.getCurrentInstance().execute("toDtLessThanFromDt.show();");
			}
		}
	}










	//Getters and setters.

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<WUH2HTransactionInfo> getWuh2hTransactionInfoList() {
		return wuh2hTransactionInfoList;
	}

	public void setWuh2hTransactionInfoList(
			List<WUH2HTransactionInfo> wuh2hTransactionInfoList) {
		this.wuh2hTransactionInfoList = wuh2hTransactionInfoList;
	}

	public SessionStateManage getSessionStateManage() {
		return sessionStateManage;
	}

	public void setSessionStateManage(SessionStateManage sessionStateManage) {
		this.sessionStateManage = sessionStateManage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

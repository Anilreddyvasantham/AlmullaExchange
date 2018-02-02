package com.amg.exchange.cbk.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cbk.model.CBKDetails;
import com.amg.exchange.cbk.model.CBKHeader;
import com.amg.exchange.cbk.model.CBKLines;
import com.amg.exchange.cbk.model.CBKTotals;
import com.amg.exchange.cbk.service.CBKReportsService;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.jvprocess.model.ViewAccountCategory;
import com.amg.exchange.jvprocess.model.ViewAccountClass;
import com.amg.exchange.jvprocess.model.ViewActivitycenterAcnt;
import com.amg.exchange.jvprocess.model.ViewBusinessoperationAlev2;
import com.amg.exchange.jvprocess.model.ViewFranchiseAlev3;
import com.amg.exchange.jvprocess.model.ViewProductAlev4;
import com.amg.exchange.jvprocess.model.ViewmainActivityALev1;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings({ "unused","rawtypes" })
@Component("cbkReportsBean")
@Scope("session")
public class CBKReportsBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger log = Logger.getLogger(CBKReportsBean.class);

	/*
	 * Autowire Configuration
	 */
	@Autowired
	CBKReportsService cBKReportsService;
	@Autowired
	IGeneralService<T> iGeneralService;

	public CBKReportsBean() {

	}

	public void pageNavigation() {
		try {
			clearMain();
			getCompanyCode();
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../cbk/cbkreportsinfo.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	public void fetchCBKInfoHead(){
		
		clearCbkHdrDetails();
		setSelectedCbkInfoList(null);
		try{
			if(getReportId()!=null)
			{
				List<CBKLines> lstCBKLines = cBKReportsService.getcbkHdrLinesDetails(getReportId(), sessionStateManage.getCountryId());

				if(lstCBKLines!=null && lstCBKLines.size()>0)
				{
					CopyOnWriteArrayList<CBKInfoDataTable> lstNewCBKInfoDataTable= new CopyOnWriteArrayList<CBKInfoDataTable>();
					int i=0;
					for(CBKLines cBKLines :lstCBKLines)
					{
						CBKHeader cBKHeader=cBKLines.getCbkId();

						setReportName(cBKHeader.getReportName());
						setReportFrequency(cBKHeader.getFrequencyId());

						CBKInfoDataTable lcbkInfoDataTable = new CBKInfoDataTable();

						lcbkInfoDataTable.setLineIndex( new BigDecimal(i));
						lcbkInfoDataTable.setLineNo(cBKLines.getLineNo());
						lcbkInfoDataTable.setLineDescription(cBKLines.getLineDescription());
						lcbkInfoDataTable.setLineType(cBKLines.getLineType());
						lcbkInfoDataTable.setPrintOrder(cBKLines.getPrintOrder());
						lcbkInfoDataTable.setPrintStatus(cBKLines.getPrintOption());
						lcbkInfoDataTable.setTotalLevel(cBKLines.getTotalLevel());
						lcbkInfoDataTable.setCbkCode(cBKLines.getCbkReference());
						//lcbkInfoDataTable.setCbkLineSeqId(cBKLines.getCbkLineId());
						lcbkInfoDataTable.setCbkHdrSeqId(cBKHeader.getCbkId());
						lcbkInfoDataTable.setCbkLineSeqId(cBKLines.getCbkLineId());
						lcbkInfoDataTable.setCbkLineCreatedBy(cBKLines.getCreatedBy());
						lcbkInfoDataTable.setCbkLineCreatedDate(cBKLines.getCreatedDate());
						lcbkInfoDataTable.setCbkLineModifiedBy(cBKLines.getModifiedBy());
						lcbkInfoDataTable.setCbkLineModifiedDate(cBKLines.getModifiedDate());
						
						setCbkHdrSeqId(cBKHeader.getCbkId());
						setCbkHdrCreateby(cBKHeader.getCreatedBy());
						setCbkhdrCreatedDate(cBKHeader.getCreatedDate());
						//setReportId(cBKHeader.getReportNo());

						lstNewCBKInfoDataTable.add(i,lcbkInfoDataTable);
						i=i+1;
					}
					setCbkInfoList(lstNewCBKInfoDataTable);
				}else
				{
					List<CBKHeader> lstCBKHeader = cBKReportsService.getcbkHdrDetails(getReportId(), sessionStateManage.getCountryId());
					if(lstCBKHeader!=null && lstCBKHeader.size()>0)
					{
						for(CBKHeader cBKHeader:lstCBKHeader)
						{
							setReportName(cBKHeader.getReportName());
							setReportFrequency(cBKHeader.getFrequencyId());
							setCbkHdrSeqId(cBKHeader.getCbkId());
							setCbkHdrCreateby(cBKHeader.getCreatedBy());
							setCbkhdrCreatedDate(cBKHeader.getCreatedDate());
						}
					}
				}
			}
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::fetchCBKInfoHead");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}





		/*CBKInfoDataTable cbkInfo = null;
		setCbkInfoList(null);	

		for(int i=0;i<=4;i++){
			i=i+1;
			cbkInfo = new CBKInfoDataTable();

			BigDecimal lno = new BigDecimal(i);
			cbkInfo.setLineNo(lno);
			cbkInfo.setLineDescription("");
			//cbkInfo.setLineType("Description");
			cbkInfo.setPrintOrder(new BigDecimal(1));
			cbkInfo.setPrintStatus("Y");
			cbkInfo.setTotalLevel(new BigDecimal(4));
			cbkInfo.setCbkCode("300"+i);

			cbkInfo.setTotalLevel(new BigDecimal(4));
			cbkInfoList.add(cbkInfo);
		}*/

	}	

	public void fetchCBKInfoDetails(){
		LineDetailsDataTable lineDetailInfo = null;
		if (cbkInfoDetailList != null || !cbkInfoDetailList.isEmpty()) {
			cbkInfoDetailList.clear();
		}		
		for(int i=0;i<=3;i++){
			lineDetailInfo = new LineDetailsDataTable();
			i=i+1;
			lineDetailInfo.setCompanyCode("20");
			/*BigDecimal lno = new BigDecimal(i);
			cbkInfo.setLineNo(lno);
			cbkInfo.setLineDescription("");
			cbkInfo.setLineType("Description");
			cbkInfo.setPrintOrder(new BigDecimal(1));
			cbkInfo.setPrintStatus("Y");
			cbkInfo.setTotalLevel(new BigDecimal(4));
			cbkInfo.setCbkCode("300"+i);
			 */
			cbkInfoDetailList.add(lineDetailInfo);
		}
	}

	public void fetchCBKInfoTotal(){	
		LineTotalDataTable lineTotalInfo = null;
		setCbkInfoList(null);	
		for(int i=0;i<=3;i++){
			lineTotalInfo = new LineTotalDataTable();
			i=i+1;
			/*BigDecimal lno = new BigDecimal(i);
			cbkInfo.setLineNo(lno);
			cbkInfo.setLineDescription("");
			cbkInfo.setLineType("Description");
			cbkInfo.setPrintOrder(new BigDecimal(1));
			cbkInfo.setPrintStatus("Y");
			cbkInfo.setTotalLevel(new BigDecimal(4));
			cbkInfo.setCbkCode("300"+i);
			 */
			cbkInfoTotalList.add(lineTotalInfo);
		}

	}

	/*public void addNewRow(SelectEvent event){

		//cbkInfoList.add(cbkInfoList.indexOf(event.getObject())+1, new CBKInfoDataTable());


		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../cbk/cbkreportsinfo.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	public void exitcbk() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../cbk/cbkreportsinfo.xhtml");
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::exitcbk()" + ne.getMessage());
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}


	public void clearMain(){
		setReportId(null);
		setReportFrequency(null);
		setReportName(null);
		setCbkInfoList(null);
		setCbkHdrSeqId(null);
		setSelectBKInfoDataTableForDelete(null);

	}
	public void clearCbkHdrDetails()
	{
		setCbkHdrSeqId(null);
		setCbkHdrCreateby(null);
		setCbkhdrCreatedDate(null);
		setReportFrequency(null);
		setReportName(null);
		setCbkInfoList(null);
		setLstCBKTotalLineNo(null);
		
	}


	/*
	 * Properties
	 * 
	 *
	 */

	private String reportId;
	private String reportName;
	private String reportFrequency;
	private String errorMessage;
	private BigDecimal cbkHdrSeqId;
	private String cbkHdrCreateby;
	private Date cbkhdrCreatedDate;
	private BigDecimal cbkLineSeqId;
	private String cbkLineDescription;
	private List<CBKTotalLineNo> lstCBKTotalLineNo;
	private BigDecimal cbkTotalLineNo;

	CopyOnWriteArrayList<CBKInfoDataTable> cbkInfoList= null;

	LinkedList<CBKInfoDataTable> selectedInfoList = new LinkedList<CBKInfoDataTable>();
	List<LineDetailsDataTable> cbkInfoDetailList = new ArrayList<LineDetailsDataTable>();
	CopyOnWriteArrayList<LineTotalDataTable> cbkInfoTotalList =null;

	
	
	public BigDecimal getCbkTotalLineNo() {
		return cbkTotalLineNo;
	}

	public void setCbkTotalLineNo(BigDecimal cbkTotalLineNo) {
		this.cbkTotalLineNo = cbkTotalLineNo;
	}

	public List<CBKTotalLineNo> getLstCBKTotalLineNo() {
		return lstCBKTotalLineNo;
	}

	public void setLstCBKTotalLineNo(List<CBKTotalLineNo> lstCBKTotalLineNo) {
		this.lstCBKTotalLineNo = lstCBKTotalLineNo;
	}

	public String getCbkLineDescription() {
		return cbkLineDescription;
	}

	public void setCbkLineDescription(String cbkLineDescription) {
		this.cbkLineDescription = cbkLineDescription;
	}

	public BigDecimal getCbkLineSeqId() {
		return cbkLineSeqId;
	}

	public void setCbkLineSeqId(BigDecimal cbkLineSeqId) {
		this.cbkLineSeqId = cbkLineSeqId;
	}

	public String getCbkHdrCreateby() {
		return cbkHdrCreateby;
	}

	public void setCbkHdrCreateby(String cbkHdrCreateby) {
		this.cbkHdrCreateby = cbkHdrCreateby;
	}

	public Date getCbkhdrCreatedDate() {
		return cbkhdrCreatedDate;
	}

	public void setCbkhdrCreatedDate(Date cbkhdrCreatedDate) {
		this.cbkhdrCreatedDate = cbkhdrCreatedDate;
	}

	public BigDecimal getCbkHdrSeqId() {
		return cbkHdrSeqId;
	}

	public void setCbkHdrSeqId(BigDecimal cbkHdrSeqId) {
		this.cbkHdrSeqId = cbkHdrSeqId;
	}

	public String getReportId() {
		return reportId;
	}


	public void setReportId(String reportId) {
		this.reportId = reportId;
	}


	public String getReportName() {
		return reportName;
	}


	public void setReportName(String reportName) {
		this.reportName = reportName;
	}


	public String getReportFrequency() {
		return reportFrequency;
	}


	public void setReportFrequency(String reportFrequency) {
		this.reportFrequency = reportFrequency;
	}


	public CopyOnWriteArrayList<CBKInfoDataTable> getCbkInfoList() {
		return cbkInfoList;
	}

	public void setCbkInfoList(CopyOnWriteArrayList<CBKInfoDataTable> cbkInfoList) {
		this.cbkInfoList = cbkInfoList;
	}

	public List<LineDetailsDataTable> getCbkInfoDetailList() {
		return cbkInfoDetailList;
	}


	public void setCbkInfoDetailList(List<LineDetailsDataTable> cbkInfoDetailList) {
		this.cbkInfoDetailList = cbkInfoDetailList;
	}


	public CopyOnWriteArrayList<LineTotalDataTable> getCbkInfoTotalList() {
		return cbkInfoTotalList;
	}


	public void setCbkInfoTotalList(CopyOnWriteArrayList<LineTotalDataTable> cbkInfoTotalList) {
		this.cbkInfoTotalList = cbkInfoTotalList;
	}


	public LinkedList<CBKInfoDataTable> getSelectedInfoList() {
		return selectedInfoList;
	}


	public void setSelectedInfoList(LinkedList<CBKInfoDataTable> selectedInfoList) {
		this.selectedInfoList = selectedInfoList;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public void deleteCbkLine(CBKInfoDataTable selectBKInfoDataTable)
	{
		try{
			setSelectBKInfoDataTableForDelete(null);
			
			CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();
			boolean checkHigertotLevel=false;
			int deleteCount=0;
			if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()!=0)
			{
				
				for(CBKInfoDataTable lcBKInfoDataTable :lstExistCBKInfoDataTable)
				{
					if((lcBKInfoDataTable.getLineNo()!=null && selectBKInfoDataTable.getLineNo()!=null) && (lcBKInfoDataTable.getTotalLevel()!=null &&selectBKInfoDataTable.getTotalLevel()!=null))
					{
						if(lcBKInfoDataTable.getLineNo().compareTo(selectBKInfoDataTable.getLineNo())!=0)
						{
							if(lcBKInfoDataTable.getTotalLevel().compareTo(selectBKInfoDataTable.getTotalLevel()) ==0)
							{
								
								checkHigertotLevel=true;
								deleteCount=deleteCount+1;
								
							}else if(lcBKInfoDataTable.getTotalLevel().compareTo(selectBKInfoDataTable.getTotalLevel()) ==1)
							{
								checkHigertotLevel=true;
							}
						}
						
					}


				}
				
			}
			
			if(checkHigertotLevel && deleteCount ==0)
			{
				RequestContext.getCurrentInstance().execute("checkHigertotLevel.show();");
				return;
			}
			
			if(selectBKInfoDataTable!=null)
			{
				if(selectBKInfoDataTable.getCbkHdrSeqId()!=null && selectBKInfoDataTable.getCbkLineSeqId()!=null)
				{
					String recordExist=cBKReportsService.checkCbkDetailsAndTotalCount(selectBKInfoDataTable.getCbkHdrSeqId(), selectBKInfoDataTable.getCbkLineSeqId(), selectBKInfoDataTable.getLineType(),sessionStateManage.getUserName());
					if(recordExist!=null && recordExist.equalsIgnoreCase(Constants.Yes))
					{
						setSelectBKInfoDataTableForDelete(selectBKInfoDataTable);
						RequestContext.getCurrentInstance().execute("confirmDelete.show();");
						return;
					}
				}
			}
			
			
			int insertIndex=0;
			BigDecimal lastLineNo=BigDecimal.ZERO;
			CopyOnWriteArrayList<CBKInfoDataTable> lstNewCBKInfoDataTable= new CopyOnWriteArrayList<CBKInfoDataTable>();
			
			BigDecimal selectedIndex= selectBKInfoDataTable.getLineIndex();
			if(selectedIndex.compareTo(BigDecimal.ZERO)==0)
			{
				
				for(int i =1; i<lstExistCBKInfoDataTable.size(); i++)
				{
					
					CBKInfoDataTable cBKInfoDataTable=lstExistCBKInfoDataTable.get(i);
					CBKInfoDataTable lcbkInfoDataTable = new CBKInfoDataTable();

					lcbkInfoDataTable.setLineIndex(new BigDecimal(insertIndex));
					lcbkInfoDataTable.setLineNo(new BigDecimal(i));
					lcbkInfoDataTable.setLineDescription(cBKInfoDataTable.getLineDescription());
					lcbkInfoDataTable.setLineType(cBKInfoDataTable.getLineType());
					lcbkInfoDataTable.setPrintOrder(cBKInfoDataTable.getPrintOrder());
					lcbkInfoDataTable.setPrintStatus(cBKInfoDataTable.getPrintStatus());
					lcbkInfoDataTable.setTotalLevel(cBKInfoDataTable.getTotalLevel());
					lcbkInfoDataTable.setCbkCode(cBKInfoDataTable.getCbkCode());
					lcbkInfoDataTable.setCbkHdrSeqId(cBKInfoDataTable.getCbkHdrSeqId());
					lcbkInfoDataTable.setCbkLineSeqId(cBKInfoDataTable.getCbkLineSeqId());
					
					lstNewCBKInfoDataTable.add(insertIndex,lcbkInfoDataTable);
					insertIndex=insertIndex+1;
	
				}
				setCbkInfoList(null);
				setCbkInfoList(lstNewCBKInfoDataTable);
				saveFirstRow();
			}else
			{
				
				if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()!=0)
				{
					
					for(int i =0; i<=selectedIndex.intValue()-1;i++)
					{
						
						CBKInfoDataTable cBKInfoDataTable=lstExistCBKInfoDataTable.get(i);
						CBKInfoDataTable lcbkInfoDataTable = new CBKInfoDataTable();

						lcbkInfoDataTable.setLineIndex(new BigDecimal(i));
						lcbkInfoDataTable.setLineNo(cBKInfoDataTable.getLineNo());
						lcbkInfoDataTable.setLineDescription(cBKInfoDataTable.getLineDescription());
						lcbkInfoDataTable.setLineType(cBKInfoDataTable.getLineType());
						lcbkInfoDataTable.setPrintOrder(cBKInfoDataTable.getPrintOrder());
						lcbkInfoDataTable.setPrintStatus(cBKInfoDataTable.getPrintStatus());
						lcbkInfoDataTable.setTotalLevel(cBKInfoDataTable.getTotalLevel());
						lcbkInfoDataTable.setCbkCode(cBKInfoDataTable.getCbkCode());
						lcbkInfoDataTable.setCbkHdrSeqId(cBKInfoDataTable.getCbkHdrSeqId());
						lcbkInfoDataTable.setCbkLineSeqId(cBKInfoDataTable.getCbkLineSeqId());
						
						lstNewCBKInfoDataTable.add(i,cBKInfoDataTable);
						
						
						lastLineNo=cBKInfoDataTable.getLineNo();
						insertIndex=i;
		
					}
					
					int remainIndex=selectedIndex.intValue()+1;
					for(int i =remainIndex; i<lstExistCBKInfoDataTable.size();i++)
					{
						insertIndex=insertIndex+1;
						lastLineNo=lastLineNo.add(BigDecimal.ONE);
						CBKInfoDataTable cBKInfoDataTable=lstExistCBKInfoDataTable.get(i);

						CBKInfoDataTable lcbkInfoDataTable = new CBKInfoDataTable();

						lcbkInfoDataTable.setLineIndex(new BigDecimal(insertIndex));
						lcbkInfoDataTable.setLineNo(lastLineNo);
						lcbkInfoDataTable.setLineDescription(cBKInfoDataTable.getLineDescription());
						lcbkInfoDataTable.setLineType(cBKInfoDataTable.getLineType());
						lcbkInfoDataTable.setPrintOrder(cBKInfoDataTable.getPrintOrder());
						lcbkInfoDataTable.setPrintStatus(cBKInfoDataTable.getPrintStatus());
						lcbkInfoDataTable.setTotalLevel(cBKInfoDataTable.getTotalLevel());
						lcbkInfoDataTable.setCbkCode(cBKInfoDataTable.getCbkCode());
						lcbkInfoDataTable.setCbkHdrSeqId(cBKInfoDataTable.getCbkHdrSeqId());
						lcbkInfoDataTable.setCbkLineSeqId(cBKInfoDataTable.getCbkLineSeqId());

						lstNewCBKInfoDataTable.add(insertIndex,lcbkInfoDataTable);
						
					}
					
					setCbkInfoList(null);
					setCbkInfoList(lstNewCBKInfoDataTable);
					saveFirstRow();
				}else
				{

				}
			}
			
			
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::deleteCbkLine");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}


	public void addCBKLines(CBKInfoDataTable selectBKInfoDataTable)
	{
		try{
			CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();

			if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()>0)
			{
				for(CBKInfoDataTable cBKInfoDataTable :lstExistCBKInfoDataTable)
				{
					boolean rtnValue=checkCBKLinesMandatory(cBKInfoDataTable);
					if(!rtnValue)
					{
						return;
					}
				}
			}
			BigDecimal selectedIndex= selectBKInfoDataTable.getLineIndex();

			/*if(selectedIndex.compareTo(BigDecimal.ZERO)==0)
			{
				saveFirstRow();
			}else
			{
				saveFirstTime(selectBKInfoDataTable);
			}
			 */		

			CopyOnWriteArrayList<CBKInfoDataTable> lstNewCBKInfoDataTable= new CopyOnWriteArrayList<CBKInfoDataTable>();
			int insertIndex=0;
			BigDecimal lastLineNo=BigDecimal.ZERO;

			if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()!=0)
			{

				for(int i =0; i<=selectedIndex.intValue();i++)
				{
					CBKInfoDataTable cBKInfoDataTable=lstExistCBKInfoDataTable.get(i);

					CBKInfoDataTable lcbkInfoDataTable = new CBKInfoDataTable();

					lcbkInfoDataTable.setLineIndex(new BigDecimal(i));
					lcbkInfoDataTable.setLineNo(cBKInfoDataTable.getLineNo());
					lcbkInfoDataTable.setLineDescription(cBKInfoDataTable.getLineDescription());
					lcbkInfoDataTable.setLineType(cBKInfoDataTable.getLineType());
					lcbkInfoDataTable.setPrintOrder(cBKInfoDataTable.getPrintOrder());
					lcbkInfoDataTable.setPrintStatus(cBKInfoDataTable.getPrintStatus());
					lcbkInfoDataTable.setTotalLevel(cBKInfoDataTable.getTotalLevel());
					lcbkInfoDataTable.setCbkCode(cBKInfoDataTable.getCbkCode());
					lcbkInfoDataTable.setCbkHdrSeqId(cBKInfoDataTable.getCbkHdrSeqId());
					lcbkInfoDataTable.setCbkLineSeqId(cBKInfoDataTable.getCbkLineSeqId());
					lcbkInfoDataTable.setCbkLineCreatedBy(cBKInfoDataTable.getCbkLineCreatedBy());
					lcbkInfoDataTable.setCbkLineCreatedDate(cBKInfoDataTable.getCbkLineCreatedDate());
					lcbkInfoDataTable.setCbkLineModifiedBy(cBKInfoDataTable.getCbkLineModifiedBy());
					lcbkInfoDataTable.setCbkLineModifiedDate(cBKInfoDataTable.getCbkLineModifiedDate());
					lstNewCBKInfoDataTable.add(i,lcbkInfoDataTable);
					lastLineNo=cBKInfoDataTable.getLineNo();
					insertIndex=i;
				}

				CBKInfoDataTable lcbkInfoDataTable1 = new CBKInfoDataTable();

				//BigDecimal lno = new BigDecimal(insertIndex);
				lcbkInfoDataTable1.setLineIndex(new BigDecimal(insertIndex).add(BigDecimal.ONE));
				lcbkInfoDataTable1.setLineNo(lastLineNo.add(BigDecimal.ONE));
				lcbkInfoDataTable1.setLineDescription("");
				//cbkInfo.setLineType("Description");
				//lcbkInfoDataTable.setPrintOrder();
				lcbkInfoDataTable1.setPrintStatus("Y");
				//lcbkInfoDataTable.setTotalLevel(new BigDecimal(4));
				//lcbkInfoDataTable.setCbkCode("300"+1);

				//lcbkInfoDataTable.setTotalLevel();

				//CopyOnWriteArrayList<CBKInfoDataTable> lstCBKInfoDataTable= new CopyOnWriteArrayList<CBKInfoDataTable>();
				/*if(insertIndex.intValue()==1)
				{
					lstExistCBKInfoDataTable.add(insertIndex.intValue()-1,lcbkInfoDataTable);
				}*/
				insertIndex=insertIndex+1;	
				lstNewCBKInfoDataTable.add(insertIndex,lcbkInfoDataTable1);

				//lastLineNo=lastLineNo.add(BigDecimal.ONE);

				int remainIndex=selectedIndex.intValue()+1;
				for(int i =remainIndex; i<lstExistCBKInfoDataTable.size();i++)
				{
					insertIndex=insertIndex+1;
					CBKInfoDataTable cBKInfoDataTable=lstExistCBKInfoDataTable.get(i);

					CBKInfoDataTable lcbkInfoDataTable = new CBKInfoDataTable();

					//int indexValue=cBKInfoDataTable.getLineIndex().add(BigDecimal.ONE).intValue();

					lcbkInfoDataTable.setLineIndex(new BigDecimal(insertIndex));
					lcbkInfoDataTable.setLineNo(cBKInfoDataTable.getLineNo().add(BigDecimal.ONE));
					lcbkInfoDataTable.setLineDescription(cBKInfoDataTable.getLineDescription());
					lcbkInfoDataTable.setLineType(cBKInfoDataTable.getLineType());
					lcbkInfoDataTable.setPrintOrder(cBKInfoDataTable.getPrintOrder());
					lcbkInfoDataTable.setPrintStatus(cBKInfoDataTable.getPrintStatus());
					lcbkInfoDataTable.setTotalLevel(cBKInfoDataTable.getTotalLevel());
					lcbkInfoDataTable.setCbkCode(cBKInfoDataTable.getCbkCode());
					lcbkInfoDataTable.setCbkHdrSeqId(cBKInfoDataTable.getCbkHdrSeqId());
					lcbkInfoDataTable.setCbkLineSeqId(cBKInfoDataTable.getCbkLineSeqId());
					lcbkInfoDataTable.setCbkLineCreatedBy(cBKInfoDataTable.getCbkLineCreatedBy());
					lcbkInfoDataTable.setCbkLineCreatedDate(cBKInfoDataTable.getCbkLineCreatedDate());
					lcbkInfoDataTable.setCbkLineModifiedBy(cBKInfoDataTable.getCbkLineModifiedBy());
					lcbkInfoDataTable.setCbkLineModifiedDate(cBKInfoDataTable.getCbkLineModifiedDate());
					
					lstNewCBKInfoDataTable.add(insertIndex,lcbkInfoDataTable);

				}
				//Collections.sort(lstNewCBKInfoDataTable,new CbkReportLineComp());
				setCbkInfoList(null);
				setCbkInfoList(lstNewCBKInfoDataTable);




				/*

				//CBKInfoDataTable existCBKInfoDataTable =lstExistCBKInfoDataTable.get(lstExistCBKInfoDataTable.size()-1);
				CBKInfoDataTable existCBKInfoDataTable = Collections.max(lstExistCBKInfoDataTable, new CbkReportLineComp());

				insertIndex=existCBKInfoDataTable.getLineIndex();
				insertIndex=insertIndex.add(BigDecimal.ONE);*/
			}else
			{
				/*lstExistCBKInfoDataTable= new CopyOnWriteArrayList<CBKInfoDataTable>();
				insertIndex=BigDecimal.ONE;*/
			}
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::addCBKLines");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}


	}

	public void addCBKEmptyLines()
	{
		try{
			boolean rtnValue= checkMandatory();
			if(!rtnValue)
			{
				return;
			}
			BigDecimal insertIndex= BigDecimal.ZERO;
			BigDecimal lastNo= BigDecimal.ZERO;
			CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();
			if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()!=0)
			{
				CBKInfoDataTable existCBKInfoDataTable =lstExistCBKInfoDataTable.get(lstExistCBKInfoDataTable.size()-1);
				//CBKInfoDataTable existCBKInfoDataTable =lstExistCBKInfoDataTable.get(lstExistCBKInfoDataTable.size()-1);
				//CBKInfoDataTable existCBKInfoDataTable = Collections.max(lstExistCBKInfoDataTable, new CbkReportLineComp());

				insertIndex=existCBKInfoDataTable.getLineIndex();
				lastNo=existCBKInfoDataTable.getLineNo().add(BigDecimal.ONE);
				insertIndex=insertIndex.add(BigDecimal.ONE);

			}else
			{
				lstExistCBKInfoDataTable= new CopyOnWriteArrayList<CBKInfoDataTable>();
				//insertIndex=BigDecimal.ONE;
				lastNo=BigDecimal.ONE;
			}

			CBKInfoDataTable lcbkInfoDataTable = new CBKInfoDataTable();

			//BigDecimal lno = new BigDecimal(insertIndex);
			lcbkInfoDataTable.setLineIndex(insertIndex);
			lcbkInfoDataTable.setLineNo(lastNo);
			lcbkInfoDataTable.setLineDescription("");
			//cbkInfo.setLineType("Description");
			//lcbkInfoDataTable.setPrintOrder();
			lcbkInfoDataTable.setPrintStatus("Y");
			//lcbkInfoDataTable.setTotalLevel(new BigDecimal(4));
			//lcbkInfoDataTable.setCbkCode("300"+1);

			//lcbkInfoDataTable.setTotalLevel();

			//CopyOnWriteArrayList<CBKInfoDataTable> lstCBKInfoDataTable= new CopyOnWriteArrayList<CBKInfoDataTable>();
			/*if(insertIndex.intValue()==1)
			{
				lstExistCBKInfoDataTable.add(insertIndex.intValue()-1,lcbkInfoDataTable);
			}*/
			lstExistCBKInfoDataTable.add(insertIndex.intValue(),lcbkInfoDataTable);


			setCbkInfoList(lstExistCBKInfoDataTable);
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::addCBKEmptyLines");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}


	}

	public void addCBKLinesFirstRow(CBKInfoDataTable selectBKInfoDataTable)
	{
		try{

			CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();

			if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()>0)
			{
				for(CBKInfoDataTable cBKInfoDataTable :lstExistCBKInfoDataTable)
				{
					boolean rtnValue=checkCBKLinesMandatory(cBKInfoDataTable);
					if(!rtnValue)
					{
						return;
					}
				}
			}

			//saveFirstTime(selectBKInfoDataTable);

			CopyOnWriteArrayList<CBKInfoDataTable> lstNewCBKInfoDataTable= new CopyOnWriteArrayList<CBKInfoDataTable>();
			int insertIndex=0;
			BigDecimal lastLineNo=BigDecimal.ZERO;

			if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()!=0)
			{

				CBKInfoDataTable lcbkInfoDataTable1 = new CBKInfoDataTable();

				//BigDecimal lno = new BigDecimal(insertIndex);
				lcbkInfoDataTable1.setLineIndex(BigDecimal.ZERO);
				lcbkInfoDataTable1.setLineNo(BigDecimal.ONE);
				lcbkInfoDataTable1.setLineDescription(null);
				lcbkInfoDataTable1.setPrintStatus("Y");

				lstNewCBKInfoDataTable.add(insertIndex,lcbkInfoDataTable1);

				for(int i =0; i<lstExistCBKInfoDataTable.size();i++)
				{
					insertIndex=insertIndex+1;
					CBKInfoDataTable cBKInfoDataTable=lstExistCBKInfoDataTable.get(i);

					CBKInfoDataTable lcbkInfoDataTable = new CBKInfoDataTable();

					lcbkInfoDataTable.setLineIndex(new BigDecimal(insertIndex));
					lcbkInfoDataTable.setLineNo(cBKInfoDataTable.getLineNo().add(BigDecimal.ONE));
					lcbkInfoDataTable.setLineDescription(cBKInfoDataTable.getLineDescription());
					lcbkInfoDataTable.setLineType(cBKInfoDataTable.getLineType());
					lcbkInfoDataTable.setPrintOrder(cBKInfoDataTable.getPrintOrder());
					lcbkInfoDataTable.setPrintStatus(cBKInfoDataTable.getPrintStatus());
					lcbkInfoDataTable.setTotalLevel(cBKInfoDataTable.getTotalLevel());
					lcbkInfoDataTable.setCbkCode(cBKInfoDataTable.getCbkCode());
					lcbkInfoDataTable.setCbkHdrSeqId(cBKInfoDataTable.getCbkHdrSeqId());
					lcbkInfoDataTable.setCbkLineSeqId(cBKInfoDataTable.getCbkLineSeqId());

					lstNewCBKInfoDataTable.add(insertIndex,lcbkInfoDataTable);

				}

				setCbkInfoList(null);
				setCbkInfoList(lstNewCBKInfoDataTable);

			}else
			{

			}

		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::addCBKLinesFirstRow");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	class CbkReportLineComp implements Comparator<CBKInfoDataTable> {

		@Override
		public int compare(CBKInfoDataTable e1, CBKInfoDataTable e2) {
			return e1.getLineIndex().compareTo(e2.getLineIndex());
		}
	}

	/*public CBKInfoDataTable addExistForReorder(CBKInfoDataTable cbkInfoDataTable)
	{
		CBKInfoDataTable lcbkInfoDataTable = new CBKInfoDataTable();

		lcbkInfoDataTable.setLineIndex(cbkInfoDataTable.getLineIndex());
		lcbkInfoDataTable.setLineNo(cbkInfoDataTable.getLineNo());
		lcbkInfoDataTable.setLineDescription(cbkInfoDataTable.getLineDescription());
		lcbkInfoDataTable.setLineType(cbkInfoDataTable.getLineType());
		lcbkInfoDataTable.setPrintOrder(cbkInfoDataTable.getPrintOrder());
		lcbkInfoDataTable.setPrintStatus(cbkInfoDataTable.getPrintStatus());
		lcbkInfoDataTable.setTotalLevel(cbkInfoDataTable.getTotalLevel());
		lcbkInfoDataTable.setCbkCode(cbkInfoDataTable.getCbkCode());
		return lcbkInfoDataTable;
	}

	public void addFirstTimeRecord(CBKInfoDataTable cbkInfoDataTable)
	{

	}*/

	public void saveAll()
	{
		try{
			boolean rtnValue= checkMandatory();
			if(!rtnValue)
			{
				return;
			}
			CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();

			if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()>0)
			{
				for(CBKInfoDataTable cBKInfoDataTable :lstExistCBKInfoDataTable)
				{
					boolean rtnLineValue=checkCBKLinesMandatory(cBKInfoDataTable);
					if(!rtnLineValue)
					{
						return;
					}
				}
			}else
			{
				RequestContext.getCurrentInstance().execute("norecord.show();");
				return;
			}
			CBKHeader cBKHeader=saveCbkHeader();
			List<CBKLines> lstCBKLines=saveCbkLines(cBKHeader);

			java.util.HashMap<String ,BigDecimal> rtnMap=cBKReportsService.saveMainCBKHeaderAndLines(cBKHeader, lstCBKLines);
			RequestContext.getCurrentInstance().execute("complete.show();");
			clearMain();
			
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::saveAll");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}



	}
	public CBKHeader saveCbkHeader()
	{

		CBKHeader cBKHeader= new CBKHeader();
		cBKHeader.setApplicationCountryId(sessionStateManage.getCountryId());
		cBKHeader.setFrequencyId(getReportFrequency());
		cBKHeader.setReportName(getReportName());
		cBKHeader.setReportNo(getReportId());


		if(getCbkHdrSeqId()!= null)
		{
			cBKHeader.setCbkId(getCbkHdrSeqId());
			cBKHeader.setCreatedBy(getCbkHdrCreateby());
			cBKHeader.setCreatedDate(getCbkhdrCreatedDate());
			cBKHeader.setModifiedBy(sessionStateManage.getUserName());
			cBKHeader.setModifiedDate(new Date());
		}else
		{
			cBKHeader.setCreatedBy(sessionStateManage.getUserName());
			cBKHeader.setCreatedDate(new Date());

		}
		cBKHeader.setIsactive(Constants.Yes);


		return cBKHeader;
	}

	public void clearCbkHdr()
	{
		setReportFrequency(null);
		setReportName(null);
		setReportId(null);
		setCbkHdrSeqId(null);
		setCbkHdrCreateby(null);
		setCbkhdrCreatedDate(null);
	}
	public List<CBKLines> saveCbkLines(CBKHeader cBKHeader)
	{
		List<CBKLines> lstCBKLines= new ArrayList<CBKLines>();

		CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();
		if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()!=0)
		{
			for(CBKInfoDataTable cBKInfoDataTable :lstExistCBKInfoDataTable)
			{
				CBKLines cBKLines = new CBKLines();
				cBKLines.setApplicationCountryId(sessionStateManage.getCountryId());
				cBKLines.setCbkId(cBKHeader);
				cBKLines.setCbkReference(cBKInfoDataTable.getCbkCode());
				cBKLines.setLineDescription(cBKInfoDataTable.getLineDescription());
				cBKLines.setLineNo(cBKInfoDataTable.getLineNo());
				cBKLines.setLineType(cBKInfoDataTable.getLineType());
				cBKLines.setPrintOption(cBKInfoDataTable.getPrintStatus());
				cBKLines.setPrintOrder(cBKInfoDataTable.getPrintOrder());
				cBKLines.setTotalLevel(cBKInfoDataTable.getTotalLevel());

				if(cBKInfoDataTable.getCbkLineSeqId()!=null)
				{
					cBKLines.setCbkLineId(cBKInfoDataTable.getCbkLineSeqId());
					cBKLines.setCreatedBy(cBKInfoDataTable.getCbkLineCreatedBy());
					cBKLines.setCreatedDate(cBKInfoDataTable.getCbkLineCreatedDate());
					cBKLines.setModifiedBy(sessionStateManage.getUserName());
					cBKLines.setModifiedDate(new Date());

				}else
				{
					cBKLines.setCreatedBy(sessionStateManage.getUserName());
					cBKLines.setCreatedDate(new Date());
				}
				cBKLines.setIsActive(Constants.Yes);
				lstCBKLines.add(cBKLines);
			}
		}


		return lstCBKLines;
	}

	public void nextInfo(SelectEvent event){

		try{
			boolean rtnValue= checkMandatory();
			if(!rtnValue)
			{
				return;
			}

			if (selectedInfoList != null || !selectedInfoList.isEmpty()) {
				selectedInfoList.clear();
			}		
			selectedInfoList.add((CBKInfoDataTable) event.getObject());	
			CBKInfoDataTable cBKInfoDataTable = selectedInfoList.get(0);

			saveFirstRow();
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::nextInfo");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

		//saveFirstTime(cBKInfoDataTable);

		//System.out.println(cbkInfoList.indexOf(event.getObject()));

		/*CBKInfoDataTable cBKInfoDataTable = selectedInfoList.get(0);

		CBKHeader cBKHeader=saveCbkHeader();
		CBKLines cBKLines =saveSelectCBKLines(cBKInfoDataTable,cBKHeader);

		java.util.HashMap<String ,BigDecimal> rtnMap=cBKReportsService.saveCBKHeaderAndLines(cBKHeader, cBKLines);

		BigDecimal  cbkHdrId=rtnMap.get("CBKHRDID");
		BigDecimal cbkLinesId=rtnMap.get("CBKLINEID");

		cBKInfoDataTable.setCbkHdrSeqId(cbkHdrId);
		cBKInfoDataTable.setCbkLineSeqId(cbkLinesId);

		CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();
		lstExistCBKInfoDataTable.remove(cBKInfoDataTable.getLineIndex().intValue());
		lstExistCBKInfoDataTable.add(cBKInfoDataTable.getLineIndex().intValue(),cBKInfoDataTable);

		setCbkInfoList(lstExistCBKInfoDataTable);*/




		/*if(cbkInfo.getLineType().equalsIgnoreCase("details")){
			fetchCBKInfoDetails();
			try {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../cbk/cbkreportsdetailinfo.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		if(cbkInfo.getLineType().equalsIgnoreCase("total")){
			fetchCBKInfoTotal();
			try {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../cbk/cbkreportstotalinfo.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}*/
	}

	public CBKLines saveSelectCBKLines(CBKInfoDataTable cBKInfoDataTable,CBKHeader cBKHeader)
	{

		CBKLines cBKLines = new CBKLines();
		cBKLines.setApplicationCountryId(sessionStateManage.getCountryId());
		cBKLines.setCbkId(cBKHeader);
		cBKLines.setCbkReference(cBKInfoDataTable.getCbkCode());
		cBKLines.setLineDescription(cBKInfoDataTable.getLineDescription());
		cBKLines.setLineNo(cBKInfoDataTable.getLineNo());
		cBKLines.setLineType(cBKInfoDataTable.getLineType());
		cBKLines.setPrintOption(cBKInfoDataTable.getPrintStatus());
		cBKLines.setPrintOrder(cBKInfoDataTable.getPrintOrder());
		cBKLines.setTotalLevel(cBKInfoDataTable.getTotalLevel());

		if(cBKInfoDataTable.getCbkLineSeqId()!=null)
		{
			cBKLines.setCbkLineId(cBKInfoDataTable.getCbkLineSeqId());
			cBKLines.setCreatedBy(cBKInfoDataTable.getCbkLineCreatedBy());
			cBKLines.setCreatedDate(cBKInfoDataTable.getCbkLineCreatedDate());
			cBKLines.setModifiedBy(sessionStateManage.getUserName());
			cBKLines.setModifiedDate(new Date());

		}
		cBKLines.setIsActive(Constants.Yes);

		return cBKLines;
	}

	public boolean checkMandatory()
	{
		boolean rtnValue= true;
		if(getReportId()==null)
		{
			RequestContext.getCurrentInstance()
			.execute("reportid.show();");
			rtnValue= false;
			return rtnValue;
		}

		if(getReportName()== null)
		{
			RequestContext.getCurrentInstance()
			.execute("reportName.show();");
			rtnValue= false;
			return rtnValue;
		}
		if(getReportFrequency()== null)
		{
			RequestContext.getCurrentInstance()
			.execute("frequency.show();");
			rtnValue= false;
			return rtnValue;
		}

		return rtnValue;
	}

	public boolean checkCBKLinesMandatory(CBKInfoDataTable selectBKInfoDataTable)
	{
		boolean rtnValue= true;
		if(selectBKInfoDataTable.getLineDescription()==null || selectBKInfoDataTable.getLineDescription().equalsIgnoreCase(""))
		{
			RequestContext.getCurrentInstance()
			.execute("lineDescription.show();");
			rtnValue= false;
			return rtnValue;
		}

		if(selectBKInfoDataTable.getLineType()==null)
		{
			RequestContext.getCurrentInstance()
			.execute("lineType.show();");
			rtnValue= false;
			return rtnValue;
		}

		if(selectBKInfoDataTable.getPrintStatus()==null)
		{
			RequestContext.getCurrentInstance()
			.execute("printStatus.show();");
			rtnValue= false;
			return rtnValue;
		}

		if(selectBKInfoDataTable.getPrintStatus()!=null && selectBKInfoDataTable.getPrintStatus().equalsIgnoreCase(Constants.Yes))
		{
			if(selectBKInfoDataTable.getPrintOrder()==null)
			{
				RequestContext.getCurrentInstance()
				.execute("printOrder.show();");
				rtnValue= false;
				return rtnValue;
			}
		}
		

		if(selectBKInfoDataTable.getPrintOrder()!=null)
		{
			if(selectBKInfoDataTable.getPrintOrder().compareTo(BigDecimal.ZERO)< 0 || selectBKInfoDataTable.getPrintOrder().compareTo(BigDecimal.ZERO)==0) 
			{
				RequestContext.getCurrentInstance()
				.execute("printOrderZero.show();");
				rtnValue= false;
				return rtnValue;
			}

		}

		if(selectBKInfoDataTable.getLineType()!=null && selectBKInfoDataTable.getLineType().equalsIgnoreCase("TOT"))
		{
			
			if(selectBKInfoDataTable.getTotalLevel()==null) 
			{
				RequestContext.getCurrentInstance()
				.execute("totalLevel.show();");
				rtnValue= false;
				return rtnValue;
			}
			
			
		}

		return rtnValue;
	}
	public void saveFirstTime(CBKInfoDataTable cBKInfoDataTable)
	{
		try{
			CBKHeader cBKHeader=saveCbkHeader();
			cBKHeader.setCbkId(getCbkHdrSeqId());

			CBKLines cBKLines =saveSelectCBKLines(cBKInfoDataTable,cBKHeader);
			cBKLines.setCbkLineId(cBKInfoDataTable.getCbkLineSeqId());

			java.util.HashMap<String ,BigDecimal> rtnMap=cBKReportsService.saveCBKHeaderAndLines(cBKHeader, cBKLines);

			BigDecimal  cbkHdrId=rtnMap.get("CBKHRDID");
			BigDecimal cbkLinesId=rtnMap.get("CBKLINEID");

			cBKInfoDataTable.setCbkHdrSeqId(cbkHdrId);
			cBKInfoDataTable.setCbkLineSeqId(cbkLinesId);

			CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();
			lstExistCBKInfoDataTable.remove(cBKInfoDataTable.getLineIndex().intValue());
			lstExistCBKInfoDataTable.add(cBKInfoDataTable.getLineIndex().intValue(),cBKInfoDataTable);

			setCbkInfoList(null);
			setCbkInfoList(lstExistCBKInfoDataTable);
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::saveFirstTime");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}
	public void checkPrintOrder(CBKInfoDataTable cBKInfoDataTable)
	{
		try{
			if(cBKInfoDataTable.getPrintOrder()!=null)
			{
				if(cBKInfoDataTable.getPrintOrder().compareTo(BigDecimal.ZERO)< 0 || cBKInfoDataTable.getPrintOrder().compareTo(BigDecimal.ZERO)==0) 
				{
					cBKInfoDataTable.setPrintOrder(null);
					RequestContext.getCurrentInstance()
					.execute("printOrderZero.show();");

					return ;
				}

			}

			CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();
			if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()!=0)
			{
				boolean checkDuplicate=false;
				for(CBKInfoDataTable lcBKInfoDataTable :lstExistCBKInfoDataTable)
				{
					if((lcBKInfoDataTable.getLineNo()!=null && cBKInfoDataTable.getLineNo()!=null) && (lcBKInfoDataTable.getPrintOrder()!=null &&cBKInfoDataTable.getPrintOrder()!=null))
					{
						if(lcBKInfoDataTable.getLineNo().compareTo(cBKInfoDataTable.getLineNo())!=0)
						{
							if(lcBKInfoDataTable.getPrintOrder().compareTo(cBKInfoDataTable.getPrintOrder())==0)
							{
								cBKInfoDataTable.setPrintOrder(null);
								checkDuplicate=true;
								break;
							}
						}
					}


				}
				if(checkDuplicate)
				{
					RequestContext.getCurrentInstance()
					.execute("printOrderDuplicate.show();");
					return;
				}
			}
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::checkPrintOrder");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}


	}

	public void checkCbkCode(CBKInfoDataTable cBKInfoDataTable)
	{
		try{
			CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();
			if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()!=0)
			{
				boolean checkDuplicate=false;
				for(CBKInfoDataTable lcBKInfoDataTable :lstExistCBKInfoDataTable)
				{
					if((lcBKInfoDataTable.getLineNo()!=null && cBKInfoDataTable.getLineNo()!=null) && (lcBKInfoDataTable.getCbkCode()!=null &&cBKInfoDataTable.getCbkCode()!=null))
					{
						if(lcBKInfoDataTable.getLineNo().compareTo(cBKInfoDataTable.getLineNo())!=0)
						{
							if(lcBKInfoDataTable.getCbkCode().compareTo(cBKInfoDataTable.getCbkCode())==0)
							{
								cBKInfoDataTable.setCbkCode(null);
								checkDuplicate=true;
								break;
							}
						}
					}


				}
				if(checkDuplicate)
				{
					RequestContext.getCurrentInstance()
					.execute("cbkCodeDuplicate.show();");
					return;
				}
			}
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::checkCbkCode");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}


	}

	public void checkTotalLevel(CBKInfoDataTable cBKInfoDataTable)
	{
		
		try{
			CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();
			List<BigDecimal> lstTotalLvel= new ArrayList<BigDecimal>();
			
			
			
			if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()!=0)
			{
				boolean allowTotalLvl=false;
				//boolean singaltotal=false;
				
				for(int i =0; i<lstExistCBKInfoDataTable.size();i++)
				{
					CBKInfoDataTable lcBKInfoDataTable=lstExistCBKInfoDataTable.get(i);
					if(lcBKInfoDataTable.getLineType()!=null && lcBKInfoDataTable.getLineType().equalsIgnoreCase("TOT"))
					{
						if(lcBKInfoDataTable.getLineNo().compareTo(cBKInfoDataTable.getLineNo())!=0)
						{
							if(lcBKInfoDataTable.getTotalLevel()!=null)
							{
								lstTotalLvel.add(lcBKInfoDataTable.getTotalLevel());
							}
							
						}
					
					}
					
				}
				BigDecimal nextTotalLvl=BigDecimal.ZERO;
				if(lstTotalLvel.size()>0)
				{
					BigDecimal maxTotalLvl=Collections.max(lstTotalLvel);
					nextTotalLvl=maxTotalLvl.add(BigDecimal.ONE);
					
					for(CBKInfoDataTable lcBKInfoDataTable :lstExistCBKInfoDataTable)
					{
						if((lcBKInfoDataTable.getLineNo()!=null && cBKInfoDataTable.getLineNo()!=null) && (lcBKInfoDataTable.getTotalLevel()!=null &&cBKInfoDataTable.getTotalLevel()!=null) && (lcBKInfoDataTable.getLineType()!=null &&cBKInfoDataTable.getLineType()!=null))
						{
							if(lcBKInfoDataTable.getLineType().equalsIgnoreCase("TOT"))
							{
															
								if(lcBKInfoDataTable.getLineNo().compareTo(cBKInfoDataTable.getLineNo())!=0)
								{
									BigDecimal subTotalLvl= lcBKInfoDataTable.getTotalLevel().subtract(BigDecimal.ONE);
								
									if(lcBKInfoDataTable.getTotalLevel().compareTo(cBKInfoDataTable.getTotalLevel())==0)
									{
										allowTotalLvl=true;
										//singaltotal=false;
										break;
									}else if(cBKInfoDataTable.getTotalLevel().compareTo(subTotalLvl)==0)
									{
										
										allowTotalLvl=true;
										//singaltotal=false;
										break;
									}else if(cBKInfoDataTable.getTotalLevel().compareTo(nextTotalLvl)==0)
									{
										allowTotalLvl=true;
										//singaltotal=false;
										break;
									}else
									{
										allowTotalLvl=false;
										//singaltotal=false;
									}
								}/*else
								{
									singaltotal=true;
								}*/
							}/*else
							{
								singaltotal=true;
							}
	*/

						}

					}
					
					if(!allowTotalLvl)
					{
						cBKInfoDataTable.setTotalLevel(null);
						RequestContext.getCurrentInstance()
						.execute("checkTotalLevelValue.show();");
						return;
					}
					
				}else
				{
					//nextTotalLvl=BigDecimal.ONE;
				}
				
				
				
			}
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::checkTotalLevel");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			
		}


	
	}
	public void saveFirstRow()
	{
		try{
			CBKHeader cBKHeader=saveCbkHeader();
			List<CBKLines> lstCBKLines=saveCbkLines(cBKHeader);

			java.util.HashMap<String ,BigDecimal> rtnMap=cBKReportsService.saveMainCBKHeaderAndLines(cBKHeader, lstCBKLines);
			fetchCBKInfoHead();
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::saveFirstRow");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}


	}
	public void confirmExit()
	{
		RequestContext.getCurrentInstance().execute("confirmExit.show();");
	}
	public void mainExit() throws IOException {
		if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	
	public void chrckTotalLevel(CBKInfoDataTable cBKInfoDataTable )
	{
		/*if(cBKInfoDataTable.getLineType()!=null && cBKInfoDataTable.getLineType().equalsIgnoreCase("TOT"))
		{
			cBKInfoDataTable.setTotalLevel(null);
		}*/
		
		cBKInfoDataTable.setTotalLevel(null);
	}

	/*private List<CBKInfoDataTable> selectedCbkInfoList = null;
	public List<CBKInfoDataTable> getSelectedCbkInfoList() {
		return selectedCbkInfoList;
	}

	public void setSelectedCbkInfoList(List<CBKInfoDataTable> selectedCbkInfoList) {
		this.selectedCbkInfoList = selectedCbkInfoList;
	}

	public void lineTypeSelected(){
		setSelectedCbkInfoList(null);
	}

	public void checkBoxSelected(SelectEvent event){

		CBKInfoDataTable cBKInfoDataTable = ((CBKInfoDataTable)event.getObject());
		if (cBKInfoDataTable.getLineType()==null || cBKInfoDataTable.getLineType().equalsIgnoreCase("")) {
			RequestContext.getCurrentInstance().execute(
					"selectLineType.show();");
					return;
		} 
		
			
		CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();
		if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()>0)
		{
			for(CBKInfoDataTable lcBKInfoDataTable :lstExistCBKInfoDataTable)
			{
				boolean rtnValue=checkCBKLinesMandatory(lcBKInfoDataTable);
				if(!rtnValue)
				{
					return;
				}
			}
		}
		nextInfo(event);

		if (cBKInfoDataTable.getLineType()==null || cBKInfoDataTable.getLineType().equalsIgnoreCase("")) {
			RequestContext.getCurrentInstance().execute(
					"selectLineType.show();");
		}else if(cBKInfoDataTable.getLineType().equalsIgnoreCase("DET")) {
			RequestContext.getCurrentInstance().execute("dailogPage.show();");
		} else if(cBKInfoDataTable.getLineType().equalsIgnoreCase("TOT")){
			RequestContext.getCurrentInstance().execute("dailogCbkTotal.show();");
		}
		
	}*/

	/**
	 * 
	 * Start Code By Ram.
	 * 
	 */
	
	/*private List<ViewmainActivityALev1> valev1 = null;
	private List<ViewBusinessoperationAlev2> valev2 = null;
	private List<ViewFranchiseAlev3> valev3 = null;
	private List<ViewProductAlev4> valev4 = null;
	//private List<LineDetailsDataTable> lineDetailsDataTable = null;
	private List<ViewActivitycenterAcnt> viewActivitycenterAcnt = null;*/
	
	private List<CBKInfoDataTable> selectedCbkInfoList = null;
	
	CopyOnWriteArrayList<LineDetailsDataTable> lineDetailsDataTable= null;
	private String companyCodeDispaly;
	
	//private String description;
	/*private String selectedValev1Code;
	private String selectedValev2Code;
	private String selectedValev3Code;
	private String selectedValev4Code;
	
	private BigDecimal selectedActCenter;
	private String selectedAccClass;
	private String selectedAccCategory;*/
	
	
	/*private void clearTableValues() {
		setSelectedValev1Code(null);
		setSelectedValev2Code(null);
		setSelectedValev3Code(null);
		setSelectedValev4Code(null);
		
		setSelectedActCenter(null);
		setSelectedAccClass(null);
		setSelectedAccCategory(null);
		setDescription(null);
	}*/
	
	private LineDetailsDataTable dropDownValues(LineDetailsDataTable dataTable){
		
		
		//V_ALEV1
		if(getValev1List()== null)
		{
			List<ViewmainActivityALev1> valev1List = cBKReportsService.getViewAlev1List();
			dataTable.setValev1(valev1List);
			setValev1List(valev1List);
		}else
		{
			dataTable.setValev1(getValev1List());
		}
		
		
		/*//V_ALEV2
		List<ViewBusinessoperationAlev2> valev2List = cBKReportsService.getViewAlev2List(dataTable.getSelectedValev1Code());
		dataTable.setValev2(valev2List);
		
		//V_ALEV3
		List<ViewFranchiseAlev3> valev3List = cBKReportsService.getViewAlev3List(dataTable.getSelectedValev2Code());
		dataTable.setValev3(valev3List);
		
		//V_ALEV4
		List<ViewProductAlev4> valev4List = cBKReportsService.getViewAlev4List(dataTable.getSelectedValev3Code());
		dataTable.setValev4(valev4List);	*/	
		
		//Activity center
		if(getViewActivitycenterAcntList()==null)
		{
			List<ViewActivitycenterAcnt> viewActivitycenterAcntList = cBKReportsService.getViewActivitycenterAcntList();
			dataTable.setViewActivitycenterAcnt(viewActivitycenterAcntList);
			setViewActivitycenterAcntList(viewActivitycenterAcntList);
		}else
		{
			dataTable.setViewActivitycenterAcnt(getViewActivitycenterAcntList());
		}
		
		if(getLstViewAccountCategory()==null)
		{
			List<ViewAccountCategory> lstViewAccountCategory=cBKReportsService.getViewAccountCategoryList();
			dataTable.setLstViewAccountCategory(lstViewAccountCategory);
			setLstViewAccountCategory(lstViewAccountCategory);
			
		}else
		{
			dataTable.setLstViewAccountCategory(getLstViewAccountCategory());
		}
		
		if(getLstViewAccountClass()==null)
		{
			List<ViewAccountClass> lstViewAccountClass=cBKReportsService.getViewAccountClassList();
			dataTable.setLstViewAccountClass(lstViewAccountClass);
			setLstViewAccountClass(lstViewAccountClass);
		}else
		{
			dataTable.setLstViewAccountClass(getLstViewAccountClass());
		}
		
		
		return dataTable;
		
		
	}
	
	public void checkBoxSelected(CBKInfoDataTable cBKInfoDataTable){
		
		
		try{
			
			//clearTableValues();
			setSelectedCbkInfoList(null);
			//CBKInfoDataTable cBKInfoDataTable = ((CBKInfoDataTable)event.getObject());
			if (cBKInfoDataTable.getLineType()==null || cBKInfoDataTable.getLineType().equalsIgnoreCase("")) {
				RequestContext.getCurrentInstance().execute(
						"selectLineType.show();");
						return;
			} 
			
			BigDecimal selectedIndex= cBKInfoDataTable.getLineIndex();	
			CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable1=getCbkInfoList();
			if(lstExistCBKInfoDataTable1!=null && lstExistCBKInfoDataTable1.size()>0)
			{
				for(CBKInfoDataTable lcBKInfoDataTable :lstExistCBKInfoDataTable1)
				{
					boolean rtnValue=checkCBKLinesMandatory(lcBKInfoDataTable);
					if(!rtnValue)
					{
						return;
					}
				}
			}
			/*if(cBKInfoDataTable.getLineType() != null && cBKInfoDataTable.getLineType().equalsIgnoreCase("DES"))
			{
				return;
			}*/
			saveFirstRow();
			CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();
			
			if (cBKInfoDataTable.getLineType()==null || cBKInfoDataTable.getLineType().equalsIgnoreCase("")) {
				RequestContext.getCurrentInstance().execute(
						"selectLineType.show();");
			} else if(cBKInfoDataTable.getLineType() != null && cBKInfoDataTable.getLineType().equalsIgnoreCase("DET")) {	
				clearCbkDetailsView();
				cBKInfoDataTable =lstExistCBKInfoDataTable.get(selectedIndex.intValue());
				
				
				setCbkLineSeqId(cBKInfoDataTable.getCbkLineSeqId());
				setCbkHdrSeqId(cBKInfoDataTable.getCbkHdrSeqId());
				setCbkLineDescription(cBKInfoDataTable.getLineDescription());
				boolean recordExist=fetchCbkDetals(cBKInfoDataTable);
				if(!recordExist)
				{
					CopyOnWriteArrayList<LineDetailsDataTable> lineDetailDataTable = new CopyOnWriteArrayList<LineDetailsDataTable>();
					LineDetailsDataTable dataTable = new LineDetailsDataTable();
					dataTable.setLineIndex(BigDecimal.ZERO);
					//dataTable.setLineNo(cBKInfoDataTable.getLineNo());
					dataTable.setCompanyCode(getCompanyCodeDispaly());
					dataTable.setDescription(cBKInfoDataTable.getLineDescription());
					
					LineDetailsDataTable dataTable1 =dropDownValues(dataTable);
					
					dataTable1.setCbkHdrSeqId(cBKInfoDataTable.getCbkHdrSeqId());
					dataTable1.setCbkLineSeqId(cBKInfoDataTable.getCbkLineSeqId());
					dataTable1.setCbkDetalisSeqId(cBKInfoDataTable.getCbkDetalisSeqId());
					
					lineDetailDataTable.add(0,dataTable1);
					
					setLineDetailsDataTable(null);
					setLineDetailsDataTable(lineDetailDataTable);
					
					
					
					
				}
				RequestContext.getCurrentInstance().execute("dailogPage.show();");
			} else if(cBKInfoDataTable.getLineType() != null && cBKInfoDataTable.getLineType().equalsIgnoreCase("TOT")){
				
				//LineTotalDataTable
				List<CBKTotalLineNo> lstCBKTotalLineNo= new ArrayList<CBKTotalLineNo>();
				
				if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()>0)
				{
					cBKInfoDataTable =lstExistCBKInfoDataTable.get(selectedIndex.intValue());
					setCbkLineSeqId(cBKInfoDataTable.getCbkLineSeqId());
					setCbkHdrSeqId(cBKInfoDataTable.getCbkHdrSeqId());
					setCbkLineDescription(cBKInfoDataTable.getLineDescription());
					
					for(int i =0; i<=selectedIndex.intValue()-1;i++)
					{
						CBKInfoDataTable lcBKInfoDataTable=lstExistCBKInfoDataTable.get(i);
						if(lcBKInfoDataTable.getLineType() != null && !lcBKInfoDataTable.getLineType().equalsIgnoreCase("DES"))
						{
							if(lcBKInfoDataTable.getLineType().endsWith("TOT"))
							{
								if(cBKInfoDataTable.getTotalLevel()!=null && lcBKInfoDataTable.getTotalLevel()!=null)
								{
									if(cBKInfoDataTable.getLineNo().compareTo(lcBKInfoDataTable.getLineNo())!=0)
									{
										if( (cBKInfoDataTable.getTotalLevel().compareTo(lcBKInfoDataTable.getTotalLevel())==1))//(cBKInfoDataTable.getTotalLevel().compareTo(lcBKInfoDataTable.getTotalLevel())==0 )||
										{
											CBKTotalLineNo cBKTotalLineNo = new CBKTotalLineNo();
											
											cBKTotalLineNo.setCbkLineNo(lcBKInfoDataTable.getLineNo());
											cBKTotalLineNo.setCbkLineSeqId(lcBKInfoDataTable.getCbkLineSeqId());
											cBKTotalLineNo.setLineDesc(lcBKInfoDataTable.getLineDescription());
											
											lstCBKTotalLineNo.add(cBKTotalLineNo);
										}

									}
																	}
							}else
							{
								CBKTotalLineNo cBKTotalLineNo = new CBKTotalLineNo();
								
								cBKTotalLineNo.setCbkLineNo(lcBKInfoDataTable.getLineNo());
								cBKTotalLineNo.setCbkLineSeqId(lcBKInfoDataTable.getCbkLineSeqId());
								cBKTotalLineNo.setLineDesc(lcBKInfoDataTable.getLineDescription());
								
								lstCBKTotalLineNo.add(cBKTotalLineNo);
							}
							
							
						}
						
					}
				}
				setLstCBKTotalLineNo(lstCBKTotalLineNo);
				setCbkTotalLineNo(cBKInfoDataTable.getLineNo());
				
				boolean recordExist=fetchCbkTotals(cBKInfoDataTable);
				if(!recordExist)
				{
					CopyOnWriteArrayList<LineTotalDataTable> lstLineTotalDataTable = new CopyOnWriteArrayList<LineTotalDataTable>();
					
					LineTotalDataTable lineTotalDataTable = new LineTotalDataTable();
					lineTotalDataTable.setLineIndex(BigDecimal.ZERO);
					lineTotalDataTable.setLineDescription(cBKInfoDataTable.getLineDescription());
					lineTotalDataTable.setTotalLineNo(cBKInfoDataTable.getLineNo());
					lineTotalDataTable.setLstLineNo(lstCBKTotalLineNo);
					lineTotalDataTable.setCbkHdrSeqId(cBKInfoDataTable.getCbkHdrSeqId());
					lineTotalDataTable.setCbkLineSeqId(cBKInfoDataTable.getCbkLineSeqId());
					lineTotalDataTable.setComputation("+");
					
					lstLineTotalDataTable.add(0,lineTotalDataTable);
					
					setCbkInfoTotalList(null);
					setCbkInfoTotalList(lstLineTotalDataTable);
					
				}
				RequestContext.getCurrentInstance().execute("dailogCbkTotal.show();");
			}	
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::checkBoxSelected");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			
		}
			
		
	}
	
	public void lineTypeSelected(){
		//setSelectedCbkInfoList(null);
	}
	
	public void backSelected(){/*
		if(getSelectedValev1Code()!=null){
			if(getSelectedValev2Code() == null){
				RequestContext.getCurrentInstance().execute("activityCode.show();");
			}else if(getSelectedValev3Code() == null){
				RequestContext.getCurrentInstance().execute("activityCode.show();");
			}else if(getSelectedValev4Code() == null){
				RequestContext.getCurrentInstance().execute("activityCode.show();");
			}
		} else if(getSelectedAccClass()==null && getSelectedAccCategory()==null){
			RequestContext.getCurrentInstance().execute("selAccClassAndCategory.show();");
		} else if(getSelectedAccClass()!=null && getSelectedAccCategory()!=null){
			RequestContext.getCurrentInstance().execute("selAccClassAndCategory.show();");
		} 
	*/}
	
	public void exit(){
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getLineDetailsList(LineDetailsDataTable lineDetailsDataTable) {
		
		
		

		try{
			/*CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();

			if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()>0)
			{
				for(CBKInfoDataTable cBKInfoDataTable :lstExistCBKInfoDataTable)
				{
					boolean rtnValue=checkCBKLinesMandatory(cBKInfoDataTable);
					if(!rtnValue)
					{
						return;
					}
				}
			}
			BigDecimal selectedIndex= selectBKInfoDataTable.getLineIndex();*/

			/*if(selectedIndex.compareTo(BigDecimal.ZERO)==0)
			{
				saveFirstRow();
			}else
			{
				saveFirstTime(selectBKInfoDataTable);
			}
			 */
			BigDecimal selectedIndex= lineDetailsDataTable.getLineIndex();
			CopyOnWriteArrayList<LineDetailsDataTable> lstExistLineDetailsDataTable=getLineDetailsDataTable();
			CopyOnWriteArrayList<LineDetailsDataTable> lstNewLineDetailsDataTable= new CopyOnWriteArrayList<LineDetailsDataTable>();
			
			
			int sizecbkDetail=lstExistLineDetailsDataTable.size()-1;
			if(selectedIndex.intValue()==sizecbkDetail)
			{
				LineDetailsDataTable dataTable = new LineDetailsDataTable();
				
				dataTable.setLineIndex(new BigDecimal(sizecbkDetail).add(BigDecimal.ONE));
				
				dataTable.setDescription(lineDetailsDataTable.getDescription());
				dataTable.setCompanyCode(getCompanyCodeDispaly());
				dataTable.setCbkHdrSeqId(lineDetailsDataTable.getCbkHdrSeqId());
				dataTable.setCbkLineSeqId(lineDetailsDataTable.getCbkLineSeqId());
				
				
				LineDetailsDataTable dataTable1 =dropDownValues(dataTable);
				
				
				
				lstExistLineDetailsDataTable.add(sizecbkDetail+1,dataTable1);
				
				setLineDetailsDataTable(null);
				setLineDetailsDataTable(lstExistLineDetailsDataTable);
				return;
			}

			
			int insertIndex=1;
			BigDecimal lastLineNo=BigDecimal.ZERO;
			BigDecimal cbkHdrSeqId=BigDecimal.ZERO;
			BigDecimal cbkLineSeqId=BigDecimal.ZERO;
			
			if(lstExistLineDetailsDataTable!=null && lstExistLineDetailsDataTable.size()!=0)
			{
				String lineDescription= null;
				for(int i =0; i<=selectedIndex.intValue();i++)
				{
					
					LineDetailsDataTable lLineDetailsDataTable=lstExistLineDetailsDataTable.get(i);
					
					
					lineDescription=lLineDetailsDataTable.getDescription();
					LineDetailsDataTable dataTable = new LineDetailsDataTable();
					dataTable.setLineIndex(new BigDecimal(i));
					//dataTable.setLineNo(cBKInfoDataTable.getLineNo());
					dataTable.setCompanyCode(getCompanyCodeDispaly());
					dataTable.setDescription(lLineDetailsDataTable.getDescription());
					
					LineDetailsDataTable dataTable1 =dropDownValues(dataTable);
					dataTable1.setSelectedValev1Code(lLineDetailsDataTable.getSelectedValev1Code());
					if(lLineDetailsDataTable.getSelectedValev1Code()!=null)
					{
						List<ViewBusinessoperationAlev2> valev2List =loadAle2CodeAdd(lLineDetailsDataTable);
						dataTable1.setValev2(valev2List);
					}
					
					dataTable1.setSelectedValev2Code(lLineDetailsDataTable.getSelectedValev2Code());
					if(lLineDetailsDataTable.getSelectedValev2Code()!=null)
					{
						List<ViewFranchiseAlev3> valev3List=loadAle3CodeAdd(lLineDetailsDataTable);
						dataTable1.setValev3(valev3List);
					}
					dataTable1.setSelectedValev3Code(lLineDetailsDataTable.getSelectedValev3Code());
					if(lLineDetailsDataTable.getSelectedValev3Code()!=null)
					{
						List<ViewProductAlev4> valev4List =loadAle4CodeAdd(lLineDetailsDataTable);
						dataTable1.setValev4(valev4List);
					}
					dataTable1.setSelectedValev4Code(lLineDetailsDataTable.getSelectedValev4Code());
					dataTable1.setSelectedActCenter(lLineDetailsDataTable.getSelectedActCenter());
					dataTable1.setSelectedAccClass(lLineDetailsDataTable.getSelectedAccClass());
					dataTable1.setSelectedAccCategory(lLineDetailsDataTable.getSelectedAccCategory());
					dataTable1.setCbkHdrSeqId(lLineDetailsDataTable.getCbkHdrSeqId());
					dataTable1.setCbkLineSeqId(lLineDetailsDataTable.getCbkLineSeqId());
					dataTable1.setCbkDetalisSeqId(lLineDetailsDataTable.getCbkDetalisSeqId());
					dataTable1.setCbkDetailsCreatedBy(lLineDetailsDataTable.getCbkDetailsCreatedBy());
					dataTable1.setCbkDetailsCreatedDate(lLineDetailsDataTable.getCbkDetailsCreatedDate());
					dataTable1.setCbkDetailsModifiedBy(lLineDetailsDataTable.getCbkDetailsModifiedBy());
					dataTable1.setCbkDetailsModifiedDate(lLineDetailsDataTable.getCbkDetailsModifiedDate());
					
					
					lstNewLineDetailsDataTable.add(i,dataTable1);
					insertIndex=i;
					cbkHdrSeqId=lLineDetailsDataTable.getCbkHdrSeqId();
					cbkLineSeqId=lLineDetailsDataTable.getCbkLineSeqId();
					//setLineDetailsDataTable(lstNewLineDetailsDataTable);
					
					
					
					
					
				}

				LineDetailsDataTable dataTable = new LineDetailsDataTable();
				
				dataTable.setLineIndex(new BigDecimal(insertIndex).add(BigDecimal.ONE));
				
				dataTable.setDescription(lineDescription);
				dataTable.setCompanyCode(getCompanyCodeDispaly());
				dataTable.setCbkHdrSeqId(cbkHdrSeqId);
				dataTable.setCbkLineSeqId(cbkLineSeqId);
				
				insertIndex=insertIndex+1;	
				LineDetailsDataTable dataTable1 =dropDownValues(dataTable);
				
				
				
				lstNewLineDetailsDataTable.add(insertIndex,dataTable1);
				
				
				

				int remainIndex=selectedIndex.intValue()+1;
				for(int i =remainIndex; i<lstExistLineDetailsDataTable.size();i++)
				{
					insertIndex=insertIndex+1;
					
					
					
					LineDetailsDataTable lLineDetailsDataTable=lstExistLineDetailsDataTable.get(i);
					
					
					
					LineDetailsDataTable dataTable2 = new LineDetailsDataTable();
					dataTable2.setLineIndex(new BigDecimal(insertIndex));
					//dataTable.setLineNo(cBKInfoDataTable.getLineNo());
					dataTable2.setCompanyCode(getCompanyCodeDispaly());
					dataTable2.setDescription(lLineDetailsDataTable.getDescription());
					
					LineDetailsDataTable dataTable3 =dropDownValues(dataTable2);
					
					dataTable3.setSelectedValev1Code(lLineDetailsDataTable.getSelectedValev1Code());
					if(lLineDetailsDataTable.getSelectedValev1Code()!=null)
					{
						List<ViewBusinessoperationAlev2> valev2List =loadAle2CodeAdd(lLineDetailsDataTable);
						dataTable3.setValev2(valev2List);
					}
					dataTable3.setSelectedValev2Code(lLineDetailsDataTable.getSelectedValev2Code());
					if(lLineDetailsDataTable.getSelectedValev2Code()!=null)
					{
						List<ViewFranchiseAlev3> valev3List=loadAle3CodeAdd(lLineDetailsDataTable);
						dataTable3.setValev3(valev3List);
					}

					dataTable3.setSelectedValev3Code(lLineDetailsDataTable.getSelectedValev3Code());
					if(lLineDetailsDataTable.getSelectedValev3Code()!=null)
					{
						List<ViewProductAlev4> valev4List =loadAle4CodeAdd(lLineDetailsDataTable);
						dataTable3.setValev4(valev4List);
					}

					dataTable3.setSelectedValev4Code(lLineDetailsDataTable.getSelectedValev4Code());
					dataTable3.setSelectedActCenter(lLineDetailsDataTable.getSelectedActCenter());
					dataTable3.setSelectedAccClass(lLineDetailsDataTable.getSelectedAccClass());
					dataTable3.setSelectedAccCategory(lLineDetailsDataTable.getSelectedAccCategory());
					
					dataTable3.setCbkHdrSeqId(lLineDetailsDataTable.getCbkHdrSeqId());
					dataTable3.setCbkLineSeqId(lLineDetailsDataTable.getCbkLineSeqId());
					dataTable3.setCbkDetalisSeqId(lLineDetailsDataTable.getCbkDetalisSeqId());
					dataTable3.setCbkDetailsCreatedBy(lLineDetailsDataTable.getCbkDetailsCreatedBy());
					dataTable3.setCbkDetailsCreatedDate(lLineDetailsDataTable.getCbkDetailsCreatedDate());
					dataTable3.setCbkDetailsModifiedBy(lLineDetailsDataTable.getCbkDetailsModifiedBy());
					dataTable3.setCbkDetailsModifiedDate(lLineDetailsDataTable.getCbkDetailsModifiedDate());
					
					
					lstNewLineDetailsDataTable.add(insertIndex,dataTable3);
										
				}
				//Collections.sort(lstNewCBKInfoDataTable,new CbkReportLineComp());
				setLineDetailsDataTable(null);
				setLineDetailsDataTable(lstNewLineDetailsDataTable);
				



				/*

				//CBKInfoDataTable existCBKInfoDataTable =lstExistCBKInfoDataTable.get(lstExistCBKInfoDataTable.size()-1);
				CBKInfoDataTable existCBKInfoDataTable = Collections.max(lstExistCBKInfoDataTable, new CbkReportLineComp());

				insertIndex=existCBKInfoDataTable.getLineIndex();
				insertIndex=insertIndex.add(BigDecimal.ONE);*/
			}else
			{
				/*lstExistCBKInfoDataTable= new CopyOnWriteArrayList<CBKInfoDataTable>();
				insertIndex=BigDecimal.ONE;*/
			}
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::getLineDetailsList");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}


	}

	public CopyOnWriteArrayList<LineDetailsDataTable> getLineDetailsDataTable() {
		return lineDetailsDataTable;
	}

	public void setLineDetailsDataTable(
			CopyOnWriteArrayList<LineDetailsDataTable> lineDetailsDataTable) {
		this.lineDetailsDataTable = lineDetailsDataTable;
	}

	public List<CBKInfoDataTable> getSelectedCbkInfoList() {
		return selectedCbkInfoList;
	}

	public void setSelectedCbkInfoList(List<CBKInfoDataTable> selectedCbkInfoList) {
		this.selectedCbkInfoList = selectedCbkInfoList;
	}
	
	
	public String getCompanyCodeDispaly() {
		return companyCodeDispaly;
	}

	public void setCompanyCodeDispaly(String companyCodeDispaly) {
		this.companyCodeDispaly = companyCodeDispaly;
	}

	public void loadAle2Code(LineDetailsDataTable lineDetailsDataTable)
	{
		try{
			List<ViewBusinessoperationAlev2> valev2List = cBKReportsService.getViewAlev2List(lineDetailsDataTable.getSelectedValev1Code());
			lineDetailsDataTable.setSelectedValev2Code(null);
			lineDetailsDataTable.setSelectedValev3Code(null);
			lineDetailsDataTable.setSelectedValev4Code(null);
			lineDetailsDataTable.setValev3(null);
			lineDetailsDataTable.setValev4(null);	
			lineDetailsDataTable.setValev2(valev2List);
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::loadAle2Code");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		
	}
	
	public void loadAle3Code(LineDetailsDataTable lineDetailsDataTable)
	{
		try{
			List<ViewFranchiseAlev3> valev3List = cBKReportsService.getViewAlev3List(lineDetailsDataTable.getSelectedValev1Code()+lineDetailsDataTable.getSelectedValev2Code());
			lineDetailsDataTable.setSelectedValev3Code(null);
			lineDetailsDataTable.setSelectedValev4Code(null);
			lineDetailsDataTable.setValev4(null);
			lineDetailsDataTable.setValev3(valev3List);
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::loadAle3Code");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		

	}
	public void loadAle4Code(LineDetailsDataTable lineDetailsDataTable)
	{
		try{
			List<ViewProductAlev4> valev4List = cBKReportsService.getViewAlev4List(lineDetailsDataTable.getSelectedValev1Code()+lineDetailsDataTable.getSelectedValev2Code()+lineDetailsDataTable.getSelectedValev3Code());
			lineDetailsDataTable.setSelectedValev4Code(null);
			lineDetailsDataTable.setValev4(valev4List);	
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::loadAle4Code");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		
			
	}
	
	
	public List<ViewBusinessoperationAlev2>  loadAle2CodeAdd(LineDetailsDataTable lineDetailsDataTable)
	{
		List<ViewBusinessoperationAlev2> valev2List = cBKReportsService.getViewAlev2List(lineDetailsDataTable.getSelectedValev1Code());
		
		return valev2List;
	}
	
	public List<ViewFranchiseAlev3> loadAle3CodeAdd(LineDetailsDataTable lineDetailsDataTable)
	{
		List<ViewFranchiseAlev3> valev3List = cBKReportsService.getViewAlev3List(lineDetailsDataTable.getSelectedValev1Code()+lineDetailsDataTable.getSelectedValev2Code());
		
		//lineDetailsDataTable.setValev3(valev3List);
		return valev3List;
	}
	public List<ViewProductAlev4> loadAle4CodeAdd(LineDetailsDataTable lineDetailsDataTable)
	{
		List<ViewProductAlev4> valev4List = cBKReportsService.getViewAlev4List(lineDetailsDataTable.getSelectedValev1Code()+lineDetailsDataTable.getSelectedValev2Code()+lineDetailsDataTable.getSelectedValev3Code());
		
		//lineDetailsDataTable.setValev4(valev4List);	
		return valev4List;
	}
	
	public void getCompanyCode()
	{
		BigDecimal companyCodeValue = BigDecimal.ZERO;
		List<CompanyMasterDesc> companyCode = iGeneralService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
		if(companyCode != null && !companyCode.isEmpty() && companyCode.get(0).getFsCompanyMaster() != null){
			companyCodeValue = companyCode.get(0).getFsCompanyMaster().getCompanyCode();
			setCompanyCodeDispaly(companyCodeValue.toString());
		}
		
	}
	public List<CBKDetails> lstSaveCbkDetails()
	{
		
		List<CBKDetails> lstCBKDetails=new ArrayList<CBKDetails>();
		try{
			
			CopyOnWriteArrayList<LineDetailsDataTable> lstExistLineDetailsDataTable=getLineDetailsDataTable();

			
			if(lstExistLineDetailsDataTable!=null && lstExistLineDetailsDataTable.size()!=0)
			{
				for(LineDetailsDataTable cineDetailsDataTable :lstExistLineDetailsDataTable)
				{
					CBKDetails cBKDetails = new CBKDetails();
					cBKDetails.setAccountCategory(cineDetailsDataTable.getSelectedAccCategory());
					cBKDetails.setActivityCenter(cineDetailsDataTable.getSelectedActCenter());
					cBKDetails.setAccountClass(cineDetailsDataTable.getSelectedAccClass());
					
					StringBuilder sb= new StringBuilder();
					if(cineDetailsDataTable.getSelectedValev1Code()!=null)
					{
						sb.append(cineDetailsDataTable.getSelectedValev1Code());
					}
					
					if(cineDetailsDataTable.getSelectedValev2Code()!=null)
					{
						sb.append(cineDetailsDataTable.getSelectedValev2Code());
					}
					if(cineDetailsDataTable.getSelectedValev3Code()!=null)
					{
						sb.append(cineDetailsDataTable.getSelectedValev3Code());
					}
					if(cineDetailsDataTable.getSelectedValev4Code()!=null)
					{
						sb.append(cineDetailsDataTable.getSelectedValev4Code());
					}
					
					cBKDetails.setActivityCode(sb.toString());
					cBKDetails.setApplicationCountryId(sessionStateManage.getCountryId());
					cBKDetails.setCbkDetailsId(cineDetailsDataTable.getCbkDetalisSeqId());
					
					CBKHeader cBKHeader = new CBKHeader();
					cBKHeader.setCbkId(cineDetailsDataTable.getCbkHdrSeqId());
					cBKDetails.setCbkId(cBKHeader);
					
					CBKLines cBKLines =new CBKLines();
					cBKLines.setCbkLineId(cineDetailsDataTable.getCbkLineSeqId());
					cBKDetails.setCbkLineId(cBKLines);
					
					cBKDetails.setCompanyCode(new BigDecimal(cineDetailsDataTable.getCompanyCode()));
					cBKDetails.setIsActive(Constants.Yes);
					
					if(cineDetailsDataTable.getCbkDetalisSeqId()!=null)
					{
						cBKDetails.setCreatedBy(cineDetailsDataTable.getCbkDetailsCreatedBy());
						cBKDetails.setCreatedDate(cineDetailsDataTable.getCbkDetailsCreatedDate());
						cBKDetails.setModifiedBy(sessionStateManage.getUserName());
						cBKDetails.setModifiedDate(new Date());
						
					}else
					{
						cBKDetails.setCreatedBy(sessionStateManage.getUserName());
						cBKDetails.setCreatedDate(new Date());
					}
					lstCBKDetails.add(cBKDetails);
				}
				
			}
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			
			setErrorMessage("Method Name::lstSaveCbkDetails");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return lstCBKDetails;
		} catch (Exception exception) {
			
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return lstCBKDetails;
		}
		
		
		
		return lstCBKDetails;
	}
	
	public void saveCbkDetails()
	{
		try{
			
			CopyOnWriteArrayList<LineDetailsDataTable> lstExistLineDetailsDataTable=getLineDetailsDataTable();
			if(lstExistLineDetailsDataTable!=null && lstExistLineDetailsDataTable.size()>0)
			{
				List<CBKDetails> lstCBKDetails=lstSaveCbkDetails();
				cBKReportsService.saveCbkDetails(lstCBKDetails);
				RequestContext.getCurrentInstance().execute("dailogPage.hide();");
			}else
			{
				RequestContext.getCurrentInstance().execute("norecord.show();");
			}
			
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			
			setErrorMessage("Method Name::saveCbkDetails");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			
		} catch (Exception exception) {
			
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			
		}
		
	}
	
	public boolean fetchCbkDetals(CBKInfoDataTable cBKInfoDataTable) throws Exception
	{
		boolean recordExist=false;
		try{
			
			List<CBKDetails> lstCBKDetails =cBKReportsService.getCbkDetails(cBKInfoDataTable.getCbkHdrSeqId(), cBKInfoDataTable.getCbkLineSeqId());
			CopyOnWriteArrayList<LineDetailsDataTable> lineDetailDataTable = new CopyOnWriteArrayList<LineDetailsDataTable>();
			if(lstCBKDetails!=null && lstCBKDetails.size()>0)
			{
				int insertIndex=0;
				recordExist=true;
				for(CBKDetails cBKDetails:lstCBKDetails)
				{
					LineDetailsDataTable dataTable = new LineDetailsDataTable();
					dataTable.setLineIndex(new BigDecimal(insertIndex));
					
					dataTable.setCompanyCode(getCompanyCodeDispaly());
					dataTable.setDescription(cBKDetails.getCbkLineId().getLineDescription());
					
					//LineDetailsDataTable dataTable1 =dropDownValues(dataTable);
					
					
					LineDetailsDataTable dataTable3 =dropDownValues(dataTable);
					String activityCode= cBKDetails.getActivityCode();
					
					String valev1=null;
					String valev2=null;
					String valev3=null;
					String valev4=null;
					if(activityCode!=null)
					{
						if(activityCode.length()>=2)
						{
							valev1=activityCode.substring(0,2);
							
						}
						if(activityCode.length()>=4)
						{
							valev2=activityCode.substring(2,4);
							
						}
						if(activityCode.length()>=6)
						{
							valev3=activityCode.substring(4,6);
							
						}
						if(activityCode.length()>=8)
						{
							valev4=activityCode.substring(6,8);
							
						}
					}
					if(valev1!=null)
					{
						dataTable3.setSelectedValev1Code(valev1);
					}
					
					List<ViewBusinessoperationAlev2> valev2List =loadAle2CodeAdd(dataTable3);
					dataTable3.setValev2(valev2List);
					
					if(valev2!=null)
					{
						dataTable3.setSelectedValev2Code(valev2);
					}
					
					List<ViewFranchiseAlev3> valev3List=loadAle3CodeAdd(dataTable3);
					dataTable3.setValev3(valev3List);
					if(valev3!=null)
					{
						dataTable3.setSelectedValev3Code(valev3);
						
					}
					List<ViewProductAlev4> valev4List =loadAle4CodeAdd(dataTable3);
					dataTable3.setValev4(valev4List);
					
					if(valev4!=null)
					{
						dataTable3.setSelectedValev4Code(valev4);
						
					}

					
					dataTable3.setSelectedActCenter(cBKDetails.getActivityCenter());
					dataTable3.setSelectedAccClass(cBKDetails.getAccountClass());
					dataTable3.setSelectedAccCategory(cBKDetails.getAccountCategory());
					
					dataTable3.setCbkHdrSeqId(cBKDetails.getCbkId().getCbkId());
					dataTable3.setCbkLineSeqId(cBKDetails.getCbkLineId().getCbkLineId());
					dataTable3.setCbkDetalisSeqId(cBKDetails.getCbkDetailsId());
					dataTable3.setCbkDetailsCreatedBy(cBKDetails.getCreatedBy());
					dataTable3.setCbkDetailsCreatedDate(cBKDetails.getCreatedDate());
					dataTable3.setCbkDetailsModifiedBy(cBKDetails.getModifiedBy());
					dataTable3.setCbkDetailsModifiedDate(cBKDetails.getModifiedDate());
					
					/*dataTable1.setCbkHdrSeqId(cBKDetails.getCbkId().getCbkId());
					dataTable1.setCbkLineSeqId(cBKDetails.getCbkLineId().getCbkLineId());
					dataTable1.setCbkDetalisSeqId(cBKDetails.getCbkDetailsId());*/
					
					
					lineDetailDataTable.add(insertIndex,dataTable3);
					insertIndex=insertIndex+1;
				}
			}
			
			
			
			setLineDetailsDataTable(null);
			setLineDetailsDataTable(lineDetailDataTable);
			
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			recordExist=false;
			throw new Exception(ne.getMessage());
			/*setErrorMessage("Method Name::fetchCbkDetals");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");*/
			//return recordExist;
		} catch (Exception exception) {
			recordExist=false;
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			throw new Exception(exception.getMessage());
			/*setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return recordExist;*/
		}
		
		
		return recordExist;
	}
	
	
	public void loadAccountClass(LineDetailsDataTable lineDetailsDataTable)
	{
		/*List<ViewAccountClass> lstViewAccountClass=cBKReportsService.getViewAccountClassList();
		lineDetailsDataTable.setLstViewAccountClass(lstViewAccountClass);*/
		lineDetailsDataTable.setSelectedAccCategory(null);
		//lineDetailsDataTable.setLstViewAccountCategory(null);
	}
	public void loadAccountCategory(LineDetailsDataTable lineDetailsDataTable)
	{
		/*List<ViewAccountCategory> lstViewAccountCategory=cBKReportsService.getViewAccountCategoryList();
		lineDetailsDataTable.setLstViewAccountCategory(lstViewAccountCategory);*/
		
		lineDetailsDataTable.setSelectedAccClass(null);
		//lineDetailsDataTable.setLstViewAccountClass(null);
	}
	public void exitCbkDetails()
	{
		setSelectedCbkInfoList(null);
		setCbkLineSeqId(null);
		//setCbkHdrSeqId(null);
		setCbkLineDescription(null);
		setLineDetailsDataTable(null);
		setLstCBKTotalLineNo(null);
		setCbkTotalLineNo(null);
		
	}

	
	public void addCBKDetailsFirstRow(LineDetailsDataTable lineDetailsDataTable)
	{
		try{

			CopyOnWriteArrayList<LineDetailsDataTable> lstExistLineDetailsDataTable=getLineDetailsDataTable();

			CopyOnWriteArrayList<LineDetailsDataTable> lstNewLineDetailsDataTable= new CopyOnWriteArrayList<LineDetailsDataTable>();
			int insertIndex=0;
			BigDecimal lastLineNo=BigDecimal.ZERO;

			if(lstExistLineDetailsDataTable!=null && lstExistLineDetailsDataTable.size()!=0)
			{


				LineDetailsDataTable dataTable = new LineDetailsDataTable();
				dataTable.setLineIndex(BigDecimal.ZERO);
				//dataTable.setLineNo(cBKInfoDataTable.getLineNo());
				dataTable.setCompanyCode(getCompanyCodeDispaly());
				dataTable.setDescription(lineDetailsDataTable.getDescription());
				
				LineDetailsDataTable ldataTable1 =dropDownValues(dataTable);
				
				ldataTable1.setCbkHdrSeqId(lineDetailsDataTable.getCbkHdrSeqId());
				ldataTable1.setCbkLineSeqId(lineDetailsDataTable.getCbkLineSeqId());
				//ldataTable1.setCbkDetalisSeqId(lineDetailsDataTable.getCbkDetalisSeqId());
				
				
				
				lstNewLineDetailsDataTable.add(insertIndex,ldataTable1);

				for(int i =0; i<lstExistLineDetailsDataTable.size();i++)
				{
					insertIndex=insertIndex+1;
					LineDetailsDataTable frmLineDetailsDataTable=lstExistLineDetailsDataTable.get(i);

					LineDetailsDataTable clineDetailsDataTable = new LineDetailsDataTable();

					LineDetailsDataTable ldataTable = new LineDetailsDataTable();
					ldataTable.setLineIndex(new BigDecimal(insertIndex));
					//dataTable.setLineNo(cBKInfoDataTable.getLineNo());
					ldataTable.setCompanyCode(getCompanyCodeDispaly());
					ldataTable.setDescription(frmLineDetailsDataTable.getDescription());
					
					LineDetailsDataTable dataTable1 =dropDownValues(ldataTable);
					dataTable1.setSelectedValev1Code(frmLineDetailsDataTable.getSelectedValev1Code());
					if(frmLineDetailsDataTable.getSelectedValev1Code()!=null)
					{
						List<ViewBusinessoperationAlev2> valev2List =loadAle2CodeAdd(frmLineDetailsDataTable);
						dataTable1.setValev2(valev2List);
					}
					
					dataTable1.setSelectedValev2Code(frmLineDetailsDataTable.getSelectedValev2Code());
					if(frmLineDetailsDataTable.getSelectedValev2Code()!=null)
					{
						List<ViewFranchiseAlev3> valev3List=loadAle3CodeAdd(frmLineDetailsDataTable);
						dataTable1.setValev3(valev3List);
					}
					dataTable1.setSelectedValev3Code(frmLineDetailsDataTable.getSelectedValev3Code());
					if(frmLineDetailsDataTable.getSelectedValev3Code()!=null)
					{
						List<ViewProductAlev4> valev4List =loadAle4CodeAdd(frmLineDetailsDataTable);
						dataTable1.setValev4(valev4List);
					}
					dataTable1.setSelectedValev4Code(frmLineDetailsDataTable.getSelectedValev4Code());
					dataTable1.setSelectedActCenter(frmLineDetailsDataTable.getSelectedActCenter());
					dataTable1.setSelectedAccClass(frmLineDetailsDataTable.getSelectedAccClass());
					dataTable1.setSelectedAccCategory(frmLineDetailsDataTable.getSelectedAccCategory());
					dataTable1.setCbkHdrSeqId(frmLineDetailsDataTable.getCbkHdrSeqId());
					dataTable1.setCbkLineSeqId(frmLineDetailsDataTable.getCbkLineSeqId());
					dataTable1.setCbkDetalisSeqId(frmLineDetailsDataTable.getCbkDetalisSeqId());
					dataTable1.setCbkDetailsCreatedBy(frmLineDetailsDataTable.getCbkDetailsCreatedBy());
					dataTable1.setCbkDetailsCreatedDate(frmLineDetailsDataTable.getCbkDetailsCreatedDate());
					dataTable1.setCbkDetailsModifiedBy(frmLineDetailsDataTable.getCbkDetailsModifiedBy());
					dataTable1.setCbkDetailsModifiedDate(frmLineDetailsDataTable.getCbkDetailsModifiedDate());
					
					
					lstNewLineDetailsDataTable.add(insertIndex,dataTable1);
					

				}

				setLineDetailsDataTable(null);
				setLineDetailsDataTable(lstNewLineDetailsDataTable);

			}else
			{

			}

		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::addCBKLinesFirstRow");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}
	
	public void deleteCbkDetails(LineDetailsDataTable lineDetailsDataTable)
	{
		try{
			
			if(lineDetailsDataTable!=null && lineDetailsDataTable.getCbkDetalisSeqId()!=null)
			{
				boolean deleteSts= cBKReportsService.deleteCbkDetails(lineDetailsDataTable.getCbkDetalisSeqId(), sessionStateManage.getUserName());
			}
			CopyOnWriteArrayList<LineDetailsDataTable> lstExistLineDetailsDataTable=getLineDetailsDataTable();

			CopyOnWriteArrayList<LineDetailsDataTable> lstNewLineDetailsDataTable= new CopyOnWriteArrayList<LineDetailsDataTable>();
			int insertIndex=0;
			BigDecimal lastLineNo=BigDecimal.ZERO;
			
			
			BigDecimal selectedIndex= lineDetailsDataTable.getLineIndex();
			
			if(selectedIndex.compareTo(BigDecimal.ZERO)==0)
			{
				
				for(int i =1; i<lstExistLineDetailsDataTable.size(); i++)
				{
					
					LineDetailsDataTable frmLineDetailsDataTable=lstExistLineDetailsDataTable.get(i);

					LineDetailsDataTable clineDetailsDataTable = new LineDetailsDataTable();

					LineDetailsDataTable ldataTable = new LineDetailsDataTable();
					ldataTable.setLineIndex(new BigDecimal(insertIndex));
					//dataTable.setLineNo(cBKInfoDataTable.getLineNo());
					ldataTable.setCompanyCode(getCompanyCodeDispaly());
					ldataTable.setDescription(frmLineDetailsDataTable.getDescription());
					
					LineDetailsDataTable dataTable1 =dropDownValues(ldataTable);
					dataTable1.setSelectedValev1Code(frmLineDetailsDataTable.getSelectedValev1Code());
					if(frmLineDetailsDataTable.getSelectedValev1Code()!=null)
					{
						List<ViewBusinessoperationAlev2> valev2List =loadAle2CodeAdd(frmLineDetailsDataTable);
						dataTable1.setValev2(valev2List);
					}
					
					dataTable1.setSelectedValev2Code(frmLineDetailsDataTable.getSelectedValev2Code());
					if(frmLineDetailsDataTable.getSelectedValev2Code()!=null)
					{
						List<ViewFranchiseAlev3> valev3List=loadAle3CodeAdd(frmLineDetailsDataTable);
						dataTable1.setValev3(valev3List);
					}
					dataTable1.setSelectedValev3Code(frmLineDetailsDataTable.getSelectedValev3Code());
					if(frmLineDetailsDataTable.getSelectedValev3Code()!=null)
					{
						List<ViewProductAlev4> valev4List =loadAle4CodeAdd(frmLineDetailsDataTable);
						dataTable1.setValev4(valev4List);
					}
					dataTable1.setSelectedValev4Code(frmLineDetailsDataTable.getSelectedValev4Code());
					dataTable1.setSelectedActCenter(frmLineDetailsDataTable.getSelectedActCenter());
					dataTable1.setSelectedAccClass(frmLineDetailsDataTable.getSelectedAccClass());
					dataTable1.setSelectedAccCategory(frmLineDetailsDataTable.getSelectedAccCategory());
					dataTable1.setCbkHdrSeqId(frmLineDetailsDataTable.getCbkHdrSeqId());
					dataTable1.setCbkLineSeqId(frmLineDetailsDataTable.getCbkLineSeqId());
					dataTable1.setCbkDetalisSeqId(frmLineDetailsDataTable.getCbkDetalisSeqId());
					dataTable1.setCbkDetailsCreatedBy(frmLineDetailsDataTable.getCbkDetailsCreatedBy());
					dataTable1.setCbkDetailsCreatedDate(frmLineDetailsDataTable.getCbkDetailsCreatedDate());
					dataTable1.setCbkDetailsModifiedBy(frmLineDetailsDataTable.getCbkDetailsModifiedBy());
					dataTable1.setCbkDetailsModifiedDate(frmLineDetailsDataTable.getCbkDetailsModifiedDate());
					
					lstNewLineDetailsDataTable.add(insertIndex,dataTable1);
					insertIndex=insertIndex+1;
				}
				setLineDetailsDataTable(null);
				setLineDetailsDataTable(lstNewLineDetailsDataTable);
			}else
			{
				
				if(lstExistLineDetailsDataTable!=null && lstExistLineDetailsDataTable.size()!=0)
				{
					
					for(int i =0; i<=selectedIndex.intValue()-1;i++)
					{
						
						LineDetailsDataTable frmLineDetailsDataTable=lstExistLineDetailsDataTable.get(i);

						LineDetailsDataTable clineDetailsDataTable = new LineDetailsDataTable();

						LineDetailsDataTable ldataTable = new LineDetailsDataTable();
						ldataTable.setLineIndex(new BigDecimal(i));
						//dataTable.setLineNo(cBKInfoDataTable.getLineNo());
						ldataTable.setCompanyCode(getCompanyCodeDispaly());
						ldataTable.setDescription(frmLineDetailsDataTable.getDescription());
						
						LineDetailsDataTable dataTable1 =dropDownValues(ldataTable);
						dataTable1.setSelectedValev1Code(frmLineDetailsDataTable.getSelectedValev1Code());
						if(frmLineDetailsDataTable.getSelectedValev1Code()!=null)
						{
							List<ViewBusinessoperationAlev2> valev2List =loadAle2CodeAdd(frmLineDetailsDataTable);
							dataTable1.setValev2(valev2List);
						}
						
						dataTable1.setSelectedValev2Code(frmLineDetailsDataTable.getSelectedValev2Code());
						if(frmLineDetailsDataTable.getSelectedValev2Code()!=null)
						{
							List<ViewFranchiseAlev3> valev3List=loadAle3CodeAdd(frmLineDetailsDataTable);
							dataTable1.setValev3(valev3List);
						}
						dataTable1.setSelectedValev3Code(frmLineDetailsDataTable.getSelectedValev3Code());
						if(frmLineDetailsDataTable.getSelectedValev3Code()!=null)
						{
							List<ViewProductAlev4> valev4List =loadAle4CodeAdd(frmLineDetailsDataTable);
							dataTable1.setValev4(valev4List);
						}
						dataTable1.setSelectedValev4Code(frmLineDetailsDataTable.getSelectedValev4Code());
						dataTable1.setSelectedActCenter(frmLineDetailsDataTable.getSelectedActCenter());
						dataTable1.setSelectedAccClass(frmLineDetailsDataTable.getSelectedAccClass());
						dataTable1.setSelectedAccCategory(frmLineDetailsDataTable.getSelectedAccCategory());
						dataTable1.setCbkHdrSeqId(frmLineDetailsDataTable.getCbkHdrSeqId());
						dataTable1.setCbkLineSeqId(frmLineDetailsDataTable.getCbkLineSeqId());
						dataTable1.setCbkDetalisSeqId(frmLineDetailsDataTable.getCbkDetalisSeqId());
						dataTable1.setCbkDetailsCreatedBy(frmLineDetailsDataTable.getCbkDetailsCreatedBy());
						dataTable1.setCbkDetailsCreatedDate(frmLineDetailsDataTable.getCbkDetailsCreatedDate());
						dataTable1.setCbkDetailsModifiedBy(frmLineDetailsDataTable.getCbkDetailsModifiedBy());
						dataTable1.setCbkDetailsModifiedDate(frmLineDetailsDataTable.getCbkDetailsModifiedDate());
						
						lstNewLineDetailsDataTable.add(i,dataTable1);
						insertIndex=i;
						
						
		
					}
					
					int remainIndex=selectedIndex.intValue()+1;
					for(int i =remainIndex; i<lstExistLineDetailsDataTable.size();i++)
					{
						insertIndex=insertIndex+1;
						LineDetailsDataTable frmLineDetailsDataTable=lstExistLineDetailsDataTable.get(i);

						LineDetailsDataTable clineDetailsDataTable = new LineDetailsDataTable();

						LineDetailsDataTable ldataTable = new LineDetailsDataTable();
						ldataTable.setLineIndex(new BigDecimal(insertIndex));
						//dataTable.setLineNo(cBKInfoDataTable.getLineNo());
						ldataTable.setCompanyCode(getCompanyCodeDispaly());
						ldataTable.setDescription(frmLineDetailsDataTable.getDescription());
						
						LineDetailsDataTable dataTable1 =dropDownValues(ldataTable);
						dataTable1.setSelectedValev1Code(frmLineDetailsDataTable.getSelectedValev1Code());
						if(frmLineDetailsDataTable.getSelectedValev1Code()!=null)
						{
							List<ViewBusinessoperationAlev2> valev2List =loadAle2CodeAdd(frmLineDetailsDataTable);
							dataTable1.setValev2(valev2List);
						}
						
						dataTable1.setSelectedValev2Code(frmLineDetailsDataTable.getSelectedValev2Code());
						if(frmLineDetailsDataTable.getSelectedValev2Code()!=null)
						{
							List<ViewFranchiseAlev3> valev3List=loadAle3CodeAdd(frmLineDetailsDataTable);
							dataTable1.setValev3(valev3List);
						}
						dataTable1.setSelectedValev3Code(frmLineDetailsDataTable.getSelectedValev3Code());
						if(frmLineDetailsDataTable.getSelectedValev3Code()!=null)
						{
							List<ViewProductAlev4> valev4List =loadAle4CodeAdd(frmLineDetailsDataTable);
							dataTable1.setValev4(valev4List);
						}
						dataTable1.setSelectedValev4Code(frmLineDetailsDataTable.getSelectedValev4Code());
						dataTable1.setSelectedActCenter(frmLineDetailsDataTable.getSelectedActCenter());
						dataTable1.setSelectedAccClass(frmLineDetailsDataTable.getSelectedAccClass());
						dataTable1.setSelectedAccCategory(frmLineDetailsDataTable.getSelectedAccCategory());
						dataTable1.setCbkHdrSeqId(frmLineDetailsDataTable.getCbkHdrSeqId());
						dataTable1.setCbkLineSeqId(frmLineDetailsDataTable.getCbkLineSeqId());
						dataTable1.setCbkDetalisSeqId(frmLineDetailsDataTable.getCbkDetalisSeqId());
						dataTable1.setCbkDetailsCreatedBy(frmLineDetailsDataTable.getCbkDetailsCreatedBy());
						dataTable1.setCbkDetailsCreatedDate(frmLineDetailsDataTable.getCbkDetailsCreatedDate());
						dataTable1.setCbkDetailsModifiedBy(frmLineDetailsDataTable.getCbkDetailsModifiedBy());
						dataTable1.setCbkDetailsModifiedDate(frmLineDetailsDataTable.getCbkDetailsModifiedDate());
						
						lstNewLineDetailsDataTable.add(insertIndex,dataTable1);
						
						
					}
					
					setLineDetailsDataTable(null);
					setLineDetailsDataTable(lstNewLineDetailsDataTable);
				}else
				{

				}
			}
			
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::deleteCbkDetails");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	
	public void addCBKDetailsEmptyLines()
	{
		try{
			setSelectedCbkInfoList(null);
			CopyOnWriteArrayList<LineDetailsDataTable> lstExistLineDetailsDataTable=getLineDetailsDataTable();
			BigDecimal insertIndex= BigDecimal.ZERO;
			
			
			if(lstExistLineDetailsDataTable!=null && lstExistLineDetailsDataTable.size()!=0)
			{
				LineDetailsDataTable frmLineDetailsDataTable=lstExistLineDetailsDataTable.get(lstExistLineDetailsDataTable.size()-1);
				
				insertIndex=frmLineDetailsDataTable.getLineIndex();
				
				insertIndex=insertIndex.add(BigDecimal.ONE);
				
			}else
			{
				lstExistLineDetailsDataTable= new CopyOnWriteArrayList<LineDetailsDataTable>();
			}
			
			//CopyOnWriteArrayList<LineDetailsDataTable> lineDetailDataTable = new CopyOnWriteArrayList<LineDetailsDataTable>();
			LineDetailsDataTable dataTable = new LineDetailsDataTable();
			dataTable.setLineIndex(insertIndex);
			//dataTable.setLineNo(cBKInfoDataTable.getLineNo());
			dataTable.setCompanyCode(getCompanyCodeDispaly());
			dataTable.setDescription(getCbkLineDescription());
			
			LineDetailsDataTable dataTable1 =dropDownValues(dataTable);
			
			dataTable1.setCbkHdrSeqId(getCbkHdrSeqId());
			dataTable1.setCbkLineSeqId(getCbkLineSeqId());
			//dataTable1.setCbkDetalisSeqId("");
			
			
			lstExistLineDetailsDataTable.add(insertIndex.intValue(),dataTable1);
			
		
			
			setLineDetailsDataTable(null);
			setLineDetailsDataTable(lstExistLineDetailsDataTable);
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::addCBKDetailsEmptyLines");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		
		
		
	}
	public List<CBKTotals> saveCbkTotalList()
	{
		CopyOnWriteArrayList<LineTotalDataTable> lstExistLineTotalDataTable= getCbkInfoTotalList();
		
		List<CBKTotals> lstCBKTotals= new ArrayList<CBKTotals>();
		
		for(LineTotalDataTable lineTotalDataTable:lstExistLineTotalDataTable)
		{
			CBKTotals cBKTotals = new CBKTotals();
			cBKTotals.setApplicationCountryId(sessionStateManage.getCountryId());
			cBKTotals.setCalcLogic(lineTotalDataTable.getComputation());
			cBKTotals.setTotalLineSeqId(lineTotalDataTable.getCbkLineSeqId());//lineTotalDataTable.getSelectedLineNo()
			
			CBKHeader cBKHeader = new CBKHeader();
			cBKHeader.setCbkId(lineTotalDataTable.getCbkHdrSeqId());
			cBKTotals.setCbkId(cBKHeader);
			
			CBKLines cBKLines = new CBKLines();
			cBKLines.setCbkLineId(lineTotalDataTable.getSelectedLineNo());//lineTotalDataTable.getCbkLineSeqId()
			cBKTotals.setCbkLineId(cBKLines);
			
			cBKTotals.setCbkTotalsId(lineTotalDataTable.getCbkTotalsSeqId());
			
			if(lineTotalDataTable.getCbkTotalsSeqId()!=null)
			{
				cBKTotals.setCreatedBy(lineTotalDataTable.getCbkTotalsCreatedBy());
				cBKTotals.setCreatedDate(lineTotalDataTable.getCbkTotalsCreatedDate());
				
				cBKTotals.setModifiedBy(sessionStateManage.getUserName());
				cBKTotals.setModifiedDate(new Date());
			}else
			{
				cBKTotals.setCreatedBy(sessionStateManage.getUserName());
				cBKTotals.setCreatedDate(new Date());
			}
			cBKTotals.setIsActive(Constants.Yes);
			lstCBKTotals.add(cBKTotals);
		}
		return lstCBKTotals;
	}
	
	public void addCbkTotalsRow(LineTotalDataTable lineTotalDataTable)
	{
		
		try{
			CopyOnWriteArrayList<LineTotalDataTable> lstExistLineTotalDataTable= getCbkInfoTotalList();
			if(lstExistLineTotalDataTable!=null && lstExistLineTotalDataTable.size()>0)
			{
				for(LineTotalDataTable llineTotalDataTable :lstExistLineTotalDataTable)
				{
					boolean checkMandatory =checkMandatoryCbkTotal(llineTotalDataTable);
					if(checkMandatory)
					{
						return;
					}
				}
			}
			
			BigDecimal selectedIndex= lineTotalDataTable.getLineIndex();
			//CopyOnWriteArrayList<LineTotalDataTable> lstExistLineTotalDataTable= getCbkInfoTotalList();

			CopyOnWriteArrayList<LineTotalDataTable> lstNewLineTotalDataTable= new CopyOnWriteArrayList<LineTotalDataTable>();
			
			int insertIndex=1;
			BigDecimal lastTotalLineNo=BigDecimal.ZERO;
			BigDecimal cbkHdrSeqId=BigDecimal.ZERO;
			BigDecimal cbkLineSeqId=BigDecimal.ZERO;
			List<CBKTotalLineNo> lastcbkTotalNo=null;
			if(lstExistLineTotalDataTable!=null && lstExistLineTotalDataTable.size()>0)
			{
				String lineDescription= null;
				for(int i =0; i<=selectedIndex.intValue();i++)
				{
					LineTotalDataTable exstLineTotalDataTable =lstExistLineTotalDataTable.get(i);
				
					lineDescription=exstLineTotalDataTable.getLineDescription();
					
					LineTotalDataTable addLineTotalDataTable= new LineTotalDataTable();
					
					addLineTotalDataTable.setLineIndex(new BigDecimal(i));
					addLineTotalDataTable.setLineDescription(lineDescription);
					addLineTotalDataTable.setLstLineNo(exstLineTotalDataTable.getLstLineNo());
					addLineTotalDataTable.setSelectedLineNo(exstLineTotalDataTable.getSelectedLineNo());
					addLineTotalDataTable.setTotalLineNo(exstLineTotalDataTable.getTotalLineNo());
					addLineTotalDataTable.setComputation(exstLineTotalDataTable.getComputation());
					addLineTotalDataTable.setCbkHdrSeqId(exstLineTotalDataTable.getCbkHdrSeqId());
					addLineTotalDataTable.setCbkLineSeqId(exstLineTotalDataTable.getCbkLineSeqId());
					addLineTotalDataTable.setCbkTotalsSeqId(exstLineTotalDataTable.getCbkTotalsSeqId());
					addLineTotalDataTable.setCbkTotalsCreatedBy(exstLineTotalDataTable.getCbkTotalsCreatedBy());
					addLineTotalDataTable.setCbkTotalsCreatedDate(exstLineTotalDataTable.getCbkTotalsCreatedDate());
					addLineTotalDataTable.setCbkTotalsModifiedBy(exstLineTotalDataTable.getCbkTotalsModifiedBy());
					addLineTotalDataTable.setCbkTotalsModifiedDate(exstLineTotalDataTable.getCbkTotalsModifiedDate());
					
					lstNewLineTotalDataTable.add(i,addLineTotalDataTable);
					
					insertIndex=i;
					cbkHdrSeqId=exstLineTotalDataTable.getCbkHdrSeqId();
					cbkLineSeqId=exstLineTotalDataTable.getCbkLineSeqId();
					lastTotalLineNo=exstLineTotalDataTable.getTotalLineNo();
					lastcbkTotalNo=exstLineTotalDataTable.getLstLineNo();
				}
				
				
				LineTotalDataTable addLineTotalDataTable= new LineTotalDataTable();
				
				addLineTotalDataTable.setLineIndex(new BigDecimal(insertIndex).add(BigDecimal.ONE));
				addLineTotalDataTable.setLineDescription(lineDescription);
				addLineTotalDataTable.setLstLineNo(lastcbkTotalNo);
				addLineTotalDataTable.setTotalLineNo(lastTotalLineNo);
				addLineTotalDataTable.setCbkHdrSeqId(cbkHdrSeqId);
				addLineTotalDataTable.setCbkLineSeqId(cbkLineSeqId);
				addLineTotalDataTable.setComputation("+");

				insertIndex=insertIndex+1;	
				lstNewLineTotalDataTable.add(insertIndex ,addLineTotalDataTable);
				
				int remainIndex=selectedIndex.intValue()+1;
				for(int i =remainIndex; i<lstExistLineTotalDataTable.size();i++)
				{
					insertIndex=insertIndex+1;
					
					LineTotalDataTable exstLineTotalDataTable=lstExistLineTotalDataTable.get(i);
					
					LineTotalDataTable addRLineTotalDataTable= new LineTotalDataTable();
					
					addRLineTotalDataTable.setLineIndex(new BigDecimal(insertIndex));
					addRLineTotalDataTable.setLineDescription(lineDescription);
					addRLineTotalDataTable.setLstLineNo(exstLineTotalDataTable.getLstLineNo());
					addRLineTotalDataTable.setSelectedLineNo(exstLineTotalDataTable.getSelectedLineNo());
					addRLineTotalDataTable.setTotalLineNo(exstLineTotalDataTable.getTotalLineNo());
					addRLineTotalDataTable.setComputation(exstLineTotalDataTable.getComputation());
					addRLineTotalDataTable.setCbkHdrSeqId(exstLineTotalDataTable.getCbkHdrSeqId());
					addRLineTotalDataTable.setCbkLineSeqId(exstLineTotalDataTable.getCbkLineSeqId());
					addRLineTotalDataTable.setCbkTotalsSeqId(exstLineTotalDataTable.getCbkTotalsSeqId());
					addRLineTotalDataTable.setCbkTotalsCreatedBy(exstLineTotalDataTable.getCbkTotalsCreatedBy());
					addRLineTotalDataTable.setCbkTotalsCreatedDate(exstLineTotalDataTable.getCbkTotalsCreatedDate());
					addRLineTotalDataTable.setCbkTotalsModifiedBy(exstLineTotalDataTable.getCbkTotalsModifiedBy());
					addRLineTotalDataTable.setCbkTotalsModifiedDate(exstLineTotalDataTable.getCbkTotalsModifiedDate());
					
					lstNewLineTotalDataTable.add(insertIndex,addRLineTotalDataTable);
					
				}
	
				setCbkInfoTotalList(null);
				setCbkInfoTotalList(lstNewLineTotalDataTable);
				
			}
			
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::addCbkTotalsRow");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
				
	}
	
	public void addCbkTotalFirstRow(LineTotalDataTable lineTotalDataTable)
	{
		try{
			CopyOnWriteArrayList<LineTotalDataTable> lstExistLineTotalDataTable= getCbkInfoTotalList();
			if(lstExistLineTotalDataTable!=null && lstExistLineTotalDataTable.size()>0)
			{
				for(LineTotalDataTable llineTotalDataTable :lstExistLineTotalDataTable)
				{
					boolean checkMandatory =checkMandatoryCbkTotal(llineTotalDataTable);
					if(checkMandatory)
					{
						return;
					}
				}
			}
			
			//CopyOnWriteArrayList<LineTotalDataTable> lstExistLineTotalDataTable= getCbkInfoTotalList();

			CopyOnWriteArrayList<LineTotalDataTable> lstNewLineTotalDataTable= new CopyOnWriteArrayList<LineTotalDataTable>();
			
			int insertIndex=0;
			
			LineTotalDataTable addLineTotalDataTable= new LineTotalDataTable();
			
			addLineTotalDataTable.setLineIndex(BigDecimal.ZERO);
			addLineTotalDataTable.setLineDescription(lineTotalDataTable.getLineDescription());
			addLineTotalDataTable.setLstLineNo(lineTotalDataTable.getLstLineNo());
			addLineTotalDataTable.setTotalLineNo(lineTotalDataTable.getTotalLineNo());
			//addLineTotalDataTable.setSelectedLineNo(lineTotalDataTable.getSelectedLineNo());
			addLineTotalDataTable.setComputation("+");
			addLineTotalDataTable.setCbkHdrSeqId(lineTotalDataTable.getCbkHdrSeqId());
			addLineTotalDataTable.setCbkLineSeqId(lineTotalDataTable.getCbkLineSeqId());
			//addLineTotalDataTable.setCbkTotalsSeqId(lineTotalDataTable.getCbkTotalsSeqId());
			//addLineTotalDataTable.setCbkTotalsCreatedBy(lineTotalDataTable.getCbkTotalsCreatedBy());
			//addLineTotalDataTable.setCbkTotalsCreatedDate(lineTotalDataTable.getCbkTotalsCreatedDate());
			//addLineTotalDataTable.setCbkTotalsModifiedBy(lineTotalDataTable.getCbkTotalsModifiedBy());
			//addLineTotalDataTable.setCbkTotalsModifiedDate(lineTotalDataTable.getCbkTotalsModifiedDate());
			
			lstNewLineTotalDataTable.add(insertIndex,addLineTotalDataTable);
			
			for(int i =0; i<lstExistLineTotalDataTable.size();i++)
			{
				insertIndex=insertIndex+1;
				LineTotalDataTable existLineTotalDataTable=lstExistLineTotalDataTable.get(i);
				
				LineTotalDataTable addRLineTotalDataTable= new LineTotalDataTable();
				addRLineTotalDataTable.setLineIndex(new BigDecimal(insertIndex));
				addRLineTotalDataTable.setLineDescription(existLineTotalDataTable.getLineDescription());
				addRLineTotalDataTable.setLstLineNo(existLineTotalDataTable.getLstLineNo());
				addRLineTotalDataTable.setTotalLineNo(existLineTotalDataTable.getTotalLineNo());
				addRLineTotalDataTable.setSelectedLineNo(existLineTotalDataTable.getSelectedLineNo());
				addRLineTotalDataTable.setComputation(existLineTotalDataTable.getComputation());
				addRLineTotalDataTable.setCbkHdrSeqId(existLineTotalDataTable.getCbkHdrSeqId());
				addRLineTotalDataTable.setCbkLineSeqId(existLineTotalDataTable.getCbkLineSeqId());
				addRLineTotalDataTable.setCbkTotalsSeqId(existLineTotalDataTable.getCbkTotalsSeqId());
				addRLineTotalDataTable.setCbkTotalsCreatedBy(existLineTotalDataTable.getCbkTotalsCreatedBy());
				addRLineTotalDataTable.setCbkTotalsCreatedDate(existLineTotalDataTable.getCbkTotalsCreatedDate());
				addRLineTotalDataTable.setCbkTotalsModifiedBy(existLineTotalDataTable.getCbkTotalsModifiedBy());
				addRLineTotalDataTable.setCbkTotalsModifiedDate(existLineTotalDataTable.getCbkTotalsModifiedDate());
				
				lstNewLineTotalDataTable.add(insertIndex,addRLineTotalDataTable);
				
			}
			
			setCbkInfoTotalList(null);
			setCbkInfoTotalList(lstNewLineTotalDataTable);
			
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::addCbkTotalFirstRow");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	
	public void deleteCbkTotals(LineTotalDataTable lineTotalDataTable)
	{
		
		try{
			if(lineTotalDataTable!=null && lineTotalDataTable.getCbkTotalsSeqId()!=null)
			{
				boolean deleteSts=cBKReportsService.deleteCbkTotals(lineTotalDataTable.getCbkTotalsSeqId(), sessionStateManage.getUserName());
			}
			CopyOnWriteArrayList<LineTotalDataTable> lstExistLineTotalDataTable= getCbkInfoTotalList();

			CopyOnWriteArrayList<LineTotalDataTable> lstNewLineTotalDataTable= new CopyOnWriteArrayList<LineTotalDataTable>();
			
			int insertIndex=0;
			
			BigDecimal selectedIndex= lineTotalDataTable.getLineIndex();
			
			if(selectedIndex.compareTo(BigDecimal.ZERO)==0)
			{
				for(int i =1; i<lstExistLineTotalDataTable.size(); i++)
				{
					
					LineTotalDataTable frmRLineTotalDataTable =lstExistLineTotalDataTable.get(i);
					
					LineTotalDataTable addRLineTotalDataTable= new LineTotalDataTable();
					
					addRLineTotalDataTable.setLineIndex(new BigDecimal(insertIndex));
					addRLineTotalDataTable.setLineDescription(frmRLineTotalDataTable.getLineDescription());
					addRLineTotalDataTable.setLstLineNo(frmRLineTotalDataTable.getLstLineNo());
					addRLineTotalDataTable.setSelectedLineNo(frmRLineTotalDataTable.getSelectedLineNo());
					addRLineTotalDataTable.setTotalLineNo(frmRLineTotalDataTable.getTotalLineNo());
					addRLineTotalDataTable.setComputation(frmRLineTotalDataTable.getComputation());
					addRLineTotalDataTable.setCbkHdrSeqId(frmRLineTotalDataTable.getCbkHdrSeqId());
					addRLineTotalDataTable.setCbkLineSeqId(frmRLineTotalDataTable.getCbkLineSeqId());
					addRLineTotalDataTable.setCbkTotalsSeqId(frmRLineTotalDataTable.getCbkTotalsSeqId());
					addRLineTotalDataTable.setCbkTotalsCreatedBy(frmRLineTotalDataTable.getCbkTotalsCreatedBy());
					addRLineTotalDataTable.setCbkTotalsCreatedDate(frmRLineTotalDataTable.getCbkTotalsCreatedDate());
					addRLineTotalDataTable.setCbkTotalsModifiedBy(frmRLineTotalDataTable.getCbkTotalsModifiedBy());
					addRLineTotalDataTable.setCbkTotalsModifiedDate(frmRLineTotalDataTable.getCbkTotalsModifiedDate());
					
					lstNewLineTotalDataTable.add(insertIndex,addRLineTotalDataTable);
					insertIndex=insertIndex+1;
				}
				setCbkInfoTotalList(null);
				setCbkInfoTotalList(lstNewLineTotalDataTable);
			}else
			{
				if(lstExistLineTotalDataTable!=null && lstExistLineTotalDataTable.size()!=0)
				{
					
					for(int i =0; i<=selectedIndex.intValue()-1;i++)
					{

						LineTotalDataTable exstLineTotalDataTable =lstExistLineTotalDataTable.get(i);
					
						//lineDescription=exstLineTotalDataTable.getLineDescription();
						
						LineTotalDataTable addLineTotalDataTable= new LineTotalDataTable();
						
						addLineTotalDataTable.setLineIndex(new BigDecimal(i));
						addLineTotalDataTable.setLineDescription(exstLineTotalDataTable.getLineDescription());
						addLineTotalDataTable.setLstLineNo(exstLineTotalDataTable.getLstLineNo());
						addLineTotalDataTable.setSelectedLineNo(exstLineTotalDataTable.getSelectedLineNo());
						addLineTotalDataTable.setTotalLineNo(exstLineTotalDataTable.getTotalLineNo());
						addLineTotalDataTable.setComputation(exstLineTotalDataTable.getComputation());
						addLineTotalDataTable.setCbkHdrSeqId(exstLineTotalDataTable.getCbkHdrSeqId());
						addLineTotalDataTable.setCbkLineSeqId(exstLineTotalDataTable.getCbkLineSeqId());
						addLineTotalDataTable.setCbkTotalsSeqId(exstLineTotalDataTable.getCbkTotalsSeqId());
						addLineTotalDataTable.setCbkTotalsCreatedBy(exstLineTotalDataTable.getCbkTotalsCreatedBy());
						addLineTotalDataTable.setCbkTotalsCreatedDate(exstLineTotalDataTable.getCbkTotalsCreatedDate());
						addLineTotalDataTable.setCbkTotalsModifiedBy(exstLineTotalDataTable.getCbkTotalsModifiedBy());
						addLineTotalDataTable.setCbkTotalsModifiedDate(exstLineTotalDataTable.getCbkTotalsModifiedDate());
						
						lstNewLineTotalDataTable.add(i,addLineTotalDataTable);
						
						insertIndex=i;
						//cbkHdrSeqId=exstLineTotalDataTable.getCbkHdrSeqId();
						//cbkLineSeqId=exstLineTotalDataTable.getCbkLineSeqId();
						
						
					
					}
					
					
					int remainIndex=selectedIndex.intValue()+1;
					for(int i =remainIndex; i<lstExistLineTotalDataTable.size();i++)
					{
						insertIndex=insertIndex+1;
						
						LineTotalDataTable exstLineTotalDataTable=lstExistLineTotalDataTable.get(i);
						
						LineTotalDataTable addRLineTotalDataTable= new LineTotalDataTable();
						
						addRLineTotalDataTable.setLineIndex(new BigDecimal(insertIndex));
						addRLineTotalDataTable.setLineDescription(lineTotalDataTable.getLineDescription());
						addRLineTotalDataTable.setLstLineNo(exstLineTotalDataTable.getLstLineNo());
						addRLineTotalDataTable.setSelectedLineNo(exstLineTotalDataTable.getSelectedLineNo());
						addRLineTotalDataTable.setTotalLineNo(exstLineTotalDataTable.getTotalLineNo());
						addRLineTotalDataTable.setComputation(exstLineTotalDataTable.getComputation());
						addRLineTotalDataTable.setCbkHdrSeqId(exstLineTotalDataTable.getCbkHdrSeqId());
						addRLineTotalDataTable.setCbkLineSeqId(exstLineTotalDataTable.getCbkLineSeqId());
						addRLineTotalDataTable.setCbkTotalsSeqId(exstLineTotalDataTable.getCbkTotalsSeqId());
						addRLineTotalDataTable.setCbkTotalsCreatedBy(exstLineTotalDataTable.getCbkTotalsCreatedBy());
						addRLineTotalDataTable.setCbkTotalsCreatedDate(exstLineTotalDataTable.getCbkTotalsCreatedDate());
						addRLineTotalDataTable.setCbkTotalsModifiedBy(exstLineTotalDataTable.getCbkTotalsModifiedBy());
						addRLineTotalDataTable.setCbkTotalsModifiedDate(exstLineTotalDataTable.getCbkTotalsModifiedDate());
						
						lstNewLineTotalDataTable.add(insertIndex,addRLineTotalDataTable);
					}
					setCbkInfoTotalList(null);
					setCbkInfoTotalList(lstNewLineTotalDataTable);
				}
			}
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::deleteCbkTotals");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			
		}
		
	}
	
	public void saveCbkTotalMain()
	{

		try{
			CopyOnWriteArrayList<LineTotalDataTable> lstExistLineTotalDataTable= getCbkInfoTotalList();
			if(lstExistLineTotalDataTable!=null && lstExistLineTotalDataTable.size()>0)
			{
				for(LineTotalDataTable lineTotalDataTable :lstExistLineTotalDataTable)
				{
					boolean checkMandatory =checkMandatoryCbkTotal(lineTotalDataTable);
					if(checkMandatory)
					{
						return;
					}
				}
			}else
			{
				RequestContext.getCurrentInstance().execute("norecord.show();");
				return;
			}
			
			 List<CBKTotals> lstCBKTotals= saveCbkTotalList();
			 cBKReportsService.saveCbkTotals(lstCBKTotals);
			 RequestContext.getCurrentInstance().execute("dailogCbkTotal.hide();");
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::saveCbkTotalMain");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			
		}
		
	
	}
	
	public boolean fetchCbkTotals(CBKInfoDataTable cBKInfoDataTable) throws Exception
	{

		boolean recordExist=false;
		
		try{
			List<CBKTotals> lstCBKTotals =cBKReportsService.getCBKTotals(cBKInfoDataTable.getCbkHdrSeqId(), cBKInfoDataTable.getCbkLineSeqId());
			CopyOnWriteArrayList<LineTotalDataTable> lstNewLineTotalDataTable= new CopyOnWriteArrayList<LineTotalDataTable>();
			if(lstCBKTotals!=null && lstCBKTotals.size()>0)
			{
				int insertIndex=0;
				recordExist=true;
				setLstCBKTotalLineNo(null);
				setCbkTotalLineNo(null);
				CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();
				
				BigDecimal selectedIndex= cBKInfoDataTable.getLineIndex();
				
				//LineTotalDataTable
				List<CBKTotalLineNo> lstCBKTotalLineNo= new ArrayList<CBKTotalLineNo>();
				
				if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()>0)
				{
					for(int i =0; i<=selectedIndex.intValue()-1;i++)
					{
						CBKInfoDataTable lcBKInfoDataTable=lstExistCBKInfoDataTable.get(i);
						if(!lcBKInfoDataTable.getLineType().equalsIgnoreCase("DES"))
						{
							if(lcBKInfoDataTable.getLineType().endsWith("TOT"))
							{
								if(cBKInfoDataTable.getTotalLevel()!=null && lcBKInfoDataTable.getTotalLevel()!=null)
								{
									if(cBKInfoDataTable.getLineNo().compareTo(lcBKInfoDataTable.getLineNo())!=0)
									{
										if((cBKInfoDataTable.getTotalLevel().compareTo(lcBKInfoDataTable.getTotalLevel())==1))//(cBKInfoDataTable.getTotalLevel().compareTo(lcBKInfoDataTable.getTotalLevel())==0 )|| 
										{
											CBKTotalLineNo cBKTotalLineNo = new CBKTotalLineNo();
											
											cBKTotalLineNo.setCbkLineNo(lcBKInfoDataTable.getLineNo());
											cBKTotalLineNo.setCbkLineSeqId(lcBKInfoDataTable.getCbkLineSeqId());
											cBKTotalLineNo.setLineDesc(lcBKInfoDataTable.getLineDescription());
											
											lstCBKTotalLineNo.add(cBKTotalLineNo);
										}

									}
																	}
							}else
							{
								CBKTotalLineNo cBKTotalLineNo = new CBKTotalLineNo();
								
								cBKTotalLineNo.setCbkLineNo(lcBKInfoDataTable.getLineNo());
								cBKTotalLineNo.setCbkLineSeqId(lcBKInfoDataTable.getCbkLineSeqId());
								cBKTotalLineNo.setLineDesc(lcBKInfoDataTable.getLineDescription());
								
								lstCBKTotalLineNo.add(cBKTotalLineNo);
							}
							
							
						}
						
					}
				}
				setLstCBKTotalLineNo(lstCBKTotalLineNo);
				setCbkTotalLineNo(cBKInfoDataTable.getLineNo());
				
				for(CBKTotals cBKTotals:lstCBKTotals)
				{
					LineTotalDataTable lineTotalDataTable = new LineTotalDataTable();
					lineTotalDataTable.setLineIndex(new BigDecimal(insertIndex));
					lineTotalDataTable.setLineDescription(cBKInfoDataTable.getLineDescription());
					lineTotalDataTable.setTotalLineNo(cBKInfoDataTable.getLineNo());
					lineTotalDataTable.setLstLineNo(lstCBKTotalLineNo);
					
					lineTotalDataTable.setSelectedLineNo(cBKTotals.getCbkLineId().getCbkLineId());//cBKTotals.getTotalLineSeqId()
					lineTotalDataTable.setComputation(cBKTotals.getCalcLogic());
					lineTotalDataTable.setCbkHdrSeqId(cBKTotals.getCbkId().getCbkId());
					lineTotalDataTable.setCbkLineSeqId(cBKTotals.getTotalLineSeqId());//cBKTotals.getCbkLineId().getCbkLineId()
					lineTotalDataTable.setCbkTotalsSeqId(cBKTotals.getCbkTotalsId());
					lineTotalDataTable.setCbkTotalsCreatedBy(cBKTotals.getCreatedBy());
					lineTotalDataTable.setCbkTotalsCreatedDate(cBKTotals.getCreatedDate());
					lineTotalDataTable.setCbkTotalsModifiedBy(cBKTotals.getModifiedBy());
					lineTotalDataTable.setCbkTotalsModifiedDate(cBKTotals.getModifiedDate());
					
					lstNewLineTotalDataTable.add(insertIndex,lineTotalDataTable);
					insertIndex=insertIndex+1;
				}
				
			}
			setCbkInfoTotalList(null);
			setCbkInfoTotalList(lstNewLineTotalDataTable);
				
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			throw new Exception(ne.getMessage());
			/*setErrorMessage("Method Name::fetchCbkTotals");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");*/
			
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			throw new Exception(exception.getMessage());
			/*setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");*/
			
		}
		return recordExist;
	
	}
	
	public boolean checkMandatoryCbkTotal(LineTotalDataTable lineTotalDataTable)
	{
		boolean checkMandatory=false;
		if(lineTotalDataTable!=null)
		{
			if(lineTotalDataTable.getComputation()==null)
			{
				checkMandatory=true;
				RequestContext.getCurrentInstance().execute("checkComputation.show();");
				return checkMandatory;
			}
			if(lineTotalDataTable.getSelectedLineNo()==null)
			{
				checkMandatory=true;
				RequestContext.getCurrentInstance().execute("checkLineNo.show();");
				return checkMandatory;
			}
		}else
		{
			checkMandatory=true;
			RequestContext.getCurrentInstance().execute("checkComputation.show();");
			return checkMandatory;
		}
		
		return checkMandatory;
	}
	
	public void addCBKTotalEmptyRow()
	{
		try{
			
			CopyOnWriteArrayList<LineTotalDataTable> lstExistLineTotalDataTable= getCbkInfoTotalList();
			
			BigDecimal insertIndex= BigDecimal.ZERO;
			
			
			if(lstExistLineTotalDataTable!=null && lstExistLineTotalDataTable.size()!=0)
			{
				LineTotalDataTable lineTotalDataTable=lstExistLineTotalDataTable.get(lstExistLineTotalDataTable.size()-1);

				boolean checkMandatory =checkMandatoryCbkTotal(lineTotalDataTable);
				if(checkMandatory)
				{
					return;
				}
				
				insertIndex=lineTotalDataTable.getLineIndex();
				
				insertIndex=insertIndex.add(BigDecimal.ONE);
				
			}else
			{
				lstExistLineTotalDataTable= new CopyOnWriteArrayList<LineTotalDataTable>();
			}
		
			
			
			List<CBKTotalLineNo> lstCBKTotalLineNo= getLstCBKTotalLineNo();
			
			
			
			LineTotalDataTable lineTotalDataTable = new LineTotalDataTable();
			lineTotalDataTable.setLineIndex(insertIndex);
			lineTotalDataTable.setLineDescription(getCbkLineDescription());
			lineTotalDataTable.setTotalLineNo(getCbkTotalLineNo());
			lineTotalDataTable.setLstLineNo(lstCBKTotalLineNo);
			lineTotalDataTable.setCbkHdrSeqId(getCbkHdrSeqId());
			lineTotalDataTable.setCbkLineSeqId(getCbkLineSeqId());
			lineTotalDataTable.setSelectedLineNo(null);
			lineTotalDataTable.setComputation("+");
			
			lstExistLineTotalDataTable.add(insertIndex.intValue(),lineTotalDataTable);
			
			
			setCbkInfoTotalList(null);
			setCbkInfoTotalList(lstExistLineTotalDataTable);
			
			
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::addCBKTotalEmptyRow");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			
		}
	}
	public void checkLineNoDuplicate(LineTotalDataTable plineTotalDataTable)
	{

		try{
			CopyOnWriteArrayList<LineTotalDataTable> lstExistLineTotalDataTable= getCbkInfoTotalList();
			if(lstExistLineTotalDataTable!=null && lstExistLineTotalDataTable.size()>0)
			{
				boolean checkDuplicate=false;
				for(LineTotalDataTable lineTotalDataTable :lstExistLineTotalDataTable)
				{
					if((lineTotalDataTable.getSelectedLineNo()!=null && lineTotalDataTable.getLineIndex()!=null) && (plineTotalDataTable.getSelectedLineNo()!=null && plineTotalDataTable.getLineIndex()!=null))
					{
						if(lineTotalDataTable.getLineIndex().compareTo(plineTotalDataTable.getLineIndex())!=0)
						{
							if(lineTotalDataTable.getSelectedLineNo().compareTo(plineTotalDataTable.getSelectedLineNo())==0)
							{
								plineTotalDataTable.setSelectedLineNo(null);
								checkDuplicate=true;
								break;
							}
						}
					}
				}
				if(checkDuplicate)
				{
					RequestContext.getCurrentInstance()
					.execute("duplicateTotalLineNo.show();");
					return;
				}
			}

			
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::checkLineNoDuplicate");
			RequestContext.getCurrentInstance()
			.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}
	private CBKInfoDataTable selectBKInfoDataTableForDelete;
	
	public CBKInfoDataTable getSelectBKInfoDataTableForDelete() {
		return selectBKInfoDataTableForDelete;
	}

	public void setSelectBKInfoDataTableForDelete(
			CBKInfoDataTable selectBKInfoDataTableForDelete) {
		this.selectBKInfoDataTableForDelete = selectBKInfoDataTableForDelete;
	}

	public void deleteCbkAllChils()
	{
		CBKInfoDataTable selectBKInfoDataTable=getSelectBKInfoDataTableForDelete();
		if(selectBKInfoDataTable!=null)
		cBKReportsService.deleteConfirmation(selectBKInfoDataTable.getCbkHdrSeqId(), selectBKInfoDataTable.getCbkLineSeqId(),sessionStateManage.getUserName());
		
		CopyOnWriteArrayList<CBKInfoDataTable> lstExistCBKInfoDataTable=getCbkInfoList();
		
		int insertIndex=0;
		BigDecimal lastLineNo=BigDecimal.ZERO;
		CopyOnWriteArrayList<CBKInfoDataTable> lstNewCBKInfoDataTable= new CopyOnWriteArrayList<CBKInfoDataTable>();
		
		BigDecimal selectedIndex= selectBKInfoDataTable.getLineIndex();
		if(selectedIndex.compareTo(BigDecimal.ZERO)==0)
		{
			
			for(int i =1; i<lstExistCBKInfoDataTable.size(); i++)
			{
				
				CBKInfoDataTable cBKInfoDataTable=lstExistCBKInfoDataTable.get(i);
				CBKInfoDataTable lcbkInfoDataTable = new CBKInfoDataTable();

				lcbkInfoDataTable.setLineIndex(new BigDecimal(insertIndex));
				lcbkInfoDataTable.setLineNo(new BigDecimal(i));
				lcbkInfoDataTable.setLineDescription(cBKInfoDataTable.getLineDescription());
				lcbkInfoDataTable.setLineType(cBKInfoDataTable.getLineType());
				lcbkInfoDataTable.setPrintOrder(cBKInfoDataTable.getPrintOrder());
				lcbkInfoDataTable.setPrintStatus(cBKInfoDataTable.getPrintStatus());
				lcbkInfoDataTable.setTotalLevel(cBKInfoDataTable.getTotalLevel());
				lcbkInfoDataTable.setCbkCode(cBKInfoDataTable.getCbkCode());
				lcbkInfoDataTable.setCbkHdrSeqId(cBKInfoDataTable.getCbkHdrSeqId());
				lcbkInfoDataTable.setCbkLineSeqId(cBKInfoDataTable.getCbkLineSeqId());
				
				lstNewCBKInfoDataTable.add(insertIndex,lcbkInfoDataTable);
				insertIndex=insertIndex+1;

			}
			setCbkInfoList(null);
			setCbkInfoList(lstNewCBKInfoDataTable);
			saveFirstRow();
		}else
		{
			
			if(lstExistCBKInfoDataTable!=null && lstExistCBKInfoDataTable.size()!=0)
			{
				
				for(int i =0; i<=selectedIndex.intValue()-1;i++)
				{
					
					CBKInfoDataTable cBKInfoDataTable=lstExistCBKInfoDataTable.get(i);
					CBKInfoDataTable lcbkInfoDataTable = new CBKInfoDataTable();

					lcbkInfoDataTable.setLineIndex(new BigDecimal(i));
					lcbkInfoDataTable.setLineNo(cBKInfoDataTable.getLineNo());
					lcbkInfoDataTable.setLineDescription(cBKInfoDataTable.getLineDescription());
					lcbkInfoDataTable.setLineType(cBKInfoDataTable.getLineType());
					lcbkInfoDataTable.setPrintOrder(cBKInfoDataTable.getPrintOrder());
					lcbkInfoDataTable.setPrintStatus(cBKInfoDataTable.getPrintStatus());
					lcbkInfoDataTable.setTotalLevel(cBKInfoDataTable.getTotalLevel());
					lcbkInfoDataTable.setCbkCode(cBKInfoDataTable.getCbkCode());
					lcbkInfoDataTable.setCbkHdrSeqId(cBKInfoDataTable.getCbkHdrSeqId());
					lcbkInfoDataTable.setCbkLineSeqId(cBKInfoDataTable.getCbkLineSeqId());
					
					lstNewCBKInfoDataTable.add(i,cBKInfoDataTable);
					
					
					lastLineNo=cBKInfoDataTable.getLineNo();
					insertIndex=i;
	
				}
				
				int remainIndex=selectedIndex.intValue()+1;
				for(int i =remainIndex; i<lstExistCBKInfoDataTable.size();i++)
				{
					insertIndex=insertIndex+1;
					lastLineNo=lastLineNo.add(BigDecimal.ONE);
					CBKInfoDataTable cBKInfoDataTable=lstExistCBKInfoDataTable.get(i);

					CBKInfoDataTable lcbkInfoDataTable = new CBKInfoDataTable();

					lcbkInfoDataTable.setLineIndex(new BigDecimal(insertIndex));
					lcbkInfoDataTable.setLineNo(lastLineNo);
					lcbkInfoDataTable.setLineDescription(cBKInfoDataTable.getLineDescription());
					lcbkInfoDataTable.setLineType(cBKInfoDataTable.getLineType());
					lcbkInfoDataTable.setPrintOrder(cBKInfoDataTable.getPrintOrder());
					lcbkInfoDataTable.setPrintStatus(cBKInfoDataTable.getPrintStatus());
					lcbkInfoDataTable.setTotalLevel(cBKInfoDataTable.getTotalLevel());
					lcbkInfoDataTable.setCbkCode(cBKInfoDataTable.getCbkCode());
					lcbkInfoDataTable.setCbkHdrSeqId(cBKInfoDataTable.getCbkHdrSeqId());
					lcbkInfoDataTable.setCbkLineSeqId(cBKInfoDataTable.getCbkLineSeqId());

					lstNewCBKInfoDataTable.add(insertIndex,lcbkInfoDataTable);
					
				}
				
				setCbkInfoList(null);
				setCbkInfoList(lstNewCBKInfoDataTable);
				saveFirstRow();
			}else
			{

			}
		}
		
	}
	
	private List<ViewmainActivityALev1> valev1List ;
	private List<ViewActivitycenterAcnt> viewActivitycenterAcntList;
	private List<ViewAccountCategory> lstViewAccountCategory;
	private List<ViewAccountClass> lstViewAccountClass;

	public List<ViewmainActivityALev1> getValev1List() {
		return valev1List;
	}

	public void setValev1List(List<ViewmainActivityALev1> valev1List) {
		this.valev1List = valev1List;
	}

	public List<ViewActivitycenterAcnt> getViewActivitycenterAcntList() {
		return viewActivitycenterAcntList;
	}

	public void setViewActivitycenterAcntList(
			List<ViewActivitycenterAcnt> viewActivitycenterAcntList) {
		this.viewActivitycenterAcntList = viewActivitycenterAcntList;
	}

	public List<ViewAccountCategory> getLstViewAccountCategory() {
		return lstViewAccountCategory;
	}

	public void setLstViewAccountCategory(
			List<ViewAccountCategory> lstViewAccountCategory) {
		this.lstViewAccountCategory = lstViewAccountCategory;
	}

	public List<ViewAccountClass> getLstViewAccountClass() {
		return lstViewAccountClass;
	}

	public void setLstViewAccountClass(List<ViewAccountClass> lstViewAccountClass) {
		this.lstViewAccountClass = lstViewAccountClass;
	}
	
	public void clearCbkDetailsView()
	{
		setValev1List(null);
		setViewActivitycenterAcntList(null);
		setLstViewAccountCategory(null);
		setLstViewAccountClass(null);
	}
}

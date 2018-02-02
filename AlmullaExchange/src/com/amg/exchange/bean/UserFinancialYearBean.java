/**
 * 
 */
package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IUserFinancialYearService;
import com.amg.exchange.complaint.bean.ComplaintAssignedDataTable;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.util.CollectionUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Subramaniam
 * 
 */
@Component("userFinancialYearBean")
@Scope("session")
public class UserFinancialYearBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UserFinancialYearBean.class);

	private BigDecimal financialYearID;
	private BigDecimal financialYear;
	private Date financialYearBegin;
	private Date financialYearEnd;
	private String fullDesc;
	public String shortDesc;
	private Date NewBeginYear;
	private String createdBy;
	private Date createdDate;
	public String modifiedBy;
	private Date modifiedDate;

	private Boolean ifEditClicked = false;

	private Boolean booRenderDataTable = false;

	private String errorMessage;

	public List<UserFinancialYear> userFinanceYearList = new ArrayList<UserFinancialYear>();
	public List<UserFinancialYearDataTable> lstFromDb = new CopyOnWriteArrayList<UserFinancialYearDataTable>();
	public List<UserFinancialYearDataTable> lstFromNewList = new CopyOnWriteArrayList<UserFinancialYearDataTable>();

	private UserFinancialYearDataTable userFinancialYearDataTableobj = null;

	public UserFinancialYearDataTable getUserFinancialYearDataTableobj() {
		return userFinancialYearDataTableobj;
	}

	public void setUserFinancialYearDataTableobj(UserFinancialYearDataTable userFinancialYearDataTableobj) {
		this.userFinancialYearDataTableobj = userFinancialYearDataTableobj;
	}

	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	IUserFinancialYearService userFinancialYearService;

	public IUserFinancialYearService getUserFinancialYearService() {
		return userFinancialYearService;
	}

	public void setUserFinancialYearService(IUserFinancialYearService userFinancialYearService) {
		this.userFinancialYearService = userFinancialYearService;
	}

	public BigDecimal getFinancialYearID() {
		return financialYearID;
	}

	public void setFinancialYearID(BigDecimal financialYearID) {
		this.financialYearID = financialYearID;
	}

	public BigDecimal getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(BigDecimal financialYear) {
		this.financialYear = financialYear;
	}

	public Date getFinancialYearBegin() {
		return financialYearBegin;
	}

	public void setFinancialYearBegin(Date financialYearBegin) {
		this.financialYearBegin = financialYearBegin;
	}

	public Date getFinancialYearEnd() {
		return financialYearEnd;
	}

	public void setFinancialYearEnd(Date financialYearEnd) {
		this.financialYearEnd = financialYearEnd;
	}

	public String getFullDesc() {
		return fullDesc;
	}

	public void setFullDesc(String fullDesc) {
		this.fullDesc = fullDesc;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public Date getNewBeginYear() {
		return NewBeginYear;
	}

	public void setNewBeginYear(Date newBeginYear) {
		NewBeginYear = newBeginYear;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public List<UserFinancialYear> getUserFinanceYearList() {
		return userFinanceYearList;
	}

	public void setUserFinanceYearList(List<UserFinancialYear> userFinanceYearList) {
		this.userFinanceYearList = userFinanceYearList;
	}

	public List<UserFinancialYearDataTable> getLstFromDb() {
		return lstFromDb;
	}

	public void setLstFromDb(List<UserFinancialYearDataTable> lstFromDb) {
		this.lstFromDb = lstFromDb;
	}

	public Boolean getIfEditClicked() {
		return ifEditClicked;
	}

	public void setIfEditClicked(Boolean ifEditClicked) {
		this.ifEditClicked = ifEditClicked;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void addDataTable() {
		lstFromDb.clear();

		userFinanceYearList = getUserFinancialYearService().getUserFinancialYearList();

		if (userFinanceYearList != null && userFinanceYearList.size() > 0) {

			for (UserFinancialYear userFinancialYear : userFinanceYearList) {

				UserFinancialYearDataTable userFinancialYearDataTable = new UserFinancialYearDataTable();

				userFinancialYearDataTable.setFinancialYear(userFinancialYear.getFinancialYear());
				userFinancialYearDataTable.setCreatedBy(userFinancialYear.getCreatedBy());
				userFinancialYearDataTable.setFinancialYearBegin(userFinancialYear.getFinancialYearBegin());
				userFinancialYearDataTable.setFinancialYearEnd(userFinancialYear.getFinancialYearEnd());
				userFinancialYearDataTable.setFullDesc(userFinancialYear.getFullDesc());
				userFinancialYearDataTable.setShortDesc(userFinancialYear.getShortDesc());

				lstFromDb.add(userFinancialYearDataTable);

			}
		} else {

		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void userFinancialYearPageNavigation() {

		try {
			clearAll();
			addDataTable();
			setBooRenderDataTable(true);
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "userfinancialyearmaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/userfinancialyearmaster.xhtml");

		}catch (Exception exception) {
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());

		}

	}

	public void exit() {
		LOGGER.info("Enter in exit method ");

		try {
			if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");

			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");

			}

		} catch (Exception exception) {
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());

		}

		LOGGER.info("Exit from exit method ");
	}

	public List<BigDecimal> autoCompleteData(String qry) {
		List<BigDecimal> list = null;
		try {

			if (qry.length() > 0) {
				list = getUserFinancialYearService().getUserFinancialYear(new BigDecimal(qry));
			} else {

				return null;
			}
		} catch (Exception exception) {
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::" + exception.getMessage());

		}
		return list;
	}

	public void callProcedure() {

		Boolean isExist = getUserFinancialYearService().isExistUserFinancialYear(getFinancialYear());

		if (!isExist) {

			try {
				Hashtable<String, Date> hTable = new Hashtable<String, Date>();
				hTable = getUserFinancialYearService().getFinancialDatevalues(getFinancialYear());
				Date startDate = (Date) hTable.get("startDate");
				Date endDate = (Date) hTable.get("endDate");
				System.out.println(startDate);
				System.out.println(endDate);

				SimpleDateFormat df = new SimpleDateFormat("yyyy");
				String year = df.format(startDate);

				SimpleDateFormat df1 = new SimpleDateFormat("yy");
				String year1 = df1.format(endDate);

				setFinancialYearBegin(startDate);
				setFinancialYearEnd(endDate);

				setFullDesc("FINANCIAL YEAR" + "\t" + year + "/" + year1);
				setShortDesc("FY-" + year + "" + year1);
				setNewBeginYear(startDate);

			} catch (Exception e) {
				LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
				setFinancialYear(null);
				setFinancialYearBegin(null);
				setFinancialYearEnd(null);
				setFullDesc(null);
				setShortDesc(null);
				setNewBeginYear(null);

				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		} else {
			setFinancialYear(null);
			setFinancialYearBegin(null);
			setFinancialYearEnd(null);
			setFullDesc(null);
			setShortDesc(null);
			setNewBeginYear(null);

			RequestContext.getCurrentInstance().execute("dataexist.show();");
			return;
		}

	}

	public void clearAll() {
		setFinancialYear(null);
		setFinancialYearBegin(null);
		setFinancialYearEnd(null);
		setFullDesc(null);
		setShortDesc(null);
		setNewBeginYear(null);
		// setFinancialYearID(null);

	}

	public Boolean duplicateCheck() {
		boolean isFlag = false;
		if (lstFromDb.size() > 0) {
			for (UserFinancialYearDataTable dataTable : lstFromDb) {
				if (dataTable.getFinancialYear().equals(getFinancialYear())) {

					isFlag = true;
				}
			}
		}
		return isFlag;
	}

	public void addRecordDataTable() {

		if (duplicateCheck()) {
			
			RequestContext.getCurrentInstance().execute("datatable.show();");
			return;
		} else {

			try {

				UserFinancialYearDataTable userFinancialYearDataTable = new UserFinancialYearDataTable();

				userFinancialYearDataTable.setFinancialYear(getFinancialYear());
				userFinancialYearDataTable.setFinancialYearBegin(getFinancialYearBegin());
				userFinancialYearDataTable.setFinancialYearEnd(getFinancialYearEnd());
				userFinancialYearDataTable.setNewBeginYear(getNewBeginYear());
				userFinancialYearDataTable.setCreatedBy(sessionStateManage.getUserName());
				userFinancialYearDataTable.setCreatedDate(new Date());
				userFinancialYearDataTable.setFullDesc(getFullDesc());
				userFinancialYearDataTable.setShortDesc(getShortDesc());
				userFinancialYearDataTable.setFinancialYearID(getFinancialYearID());

				if (getFinancialYearID() != null) {

				} else {
					userFinancialYearDataTable.setIfEditClicked(true);
				}

				lstFromDb.add(userFinancialYearDataTable);

				if (getFinancialYearID() == null) {
					lstFromNewList.add(userFinancialYearDataTable);
				}
				clearAll();
			} catch (NullPointerException ne) {

			} catch (Exception exception) {

			}

		}

	}

	public void save() {

		for (UserFinancialYearDataTable userFinancialYearDataTable : lstFromDb) {
			if (userFinancialYearDataTable.getIfEditClicked() == true) {

				UserFinancialYear userFinancialYear = new UserFinancialYear();

				userFinancialYear.setFinancialYear(userFinancialYearDataTable.getFinancialYear());
				userFinancialYear.setFinancialYearBegin(userFinancialYearDataTable.getFinancialYearBegin());
				userFinancialYear.setFinancialYearEnd(userFinancialYearDataTable.getFinancialYearEnd());
				userFinancialYear.setNewBeginYear(userFinancialYearDataTable.getNewBeginYear());
				userFinancialYear.setCreatedBy(userFinancialYearDataTable.getCreatedBy());
				userFinancialYear.setCreatedDate(userFinancialYearDataTable.getCreatedDate());
				userFinancialYear.setModifiedBy(userFinancialYearDataTable.getModifiedBy());
				userFinancialYear.setModifiedDate(userFinancialYearDataTable.getModifiedDate());
				userFinancialYear.setFullDesc(userFinancialYearDataTable.getFullDesc());
				userFinancialYear.setShortDesc(userFinancialYearDataTable.getShortDesc());

				getUserFinancialYearService().save(userFinancialYear);

				RequestContext.getCurrentInstance().execute("completed.show();");
			}

		}

	}

	public void checkRelationcodeisNumber(FacesContext context, UIComponent component, Object value) {
		try {
			BigDecimal val = new BigDecimal(value.toString());
			@SuppressWarnings("unused")
			boolean isNUmber = CollectionUtil.isIntegerValue(val);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(" Relations Code should be number", " Relations Code should be number");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

}

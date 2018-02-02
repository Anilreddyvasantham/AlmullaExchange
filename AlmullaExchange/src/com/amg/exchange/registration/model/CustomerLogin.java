package com.amg.exchange.registration.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;

/*******************************************************************************************************************

		 File		: CustomerLogin.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 5:09:36 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 25-Feb-2015
 						By		: Nazish Ehsan Hashmi
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/

@Entity
@Table(name = "FS_CUSTOMER_LOGIN" )
public class CustomerLogin implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal custLoginId;
	private Customer fsCustomer;
	private LanguageType fsLanguageType;
	private CountryMaster fsCountryMaster;
	private CompanyMaster fsCompanyMaster;
	private String userName;
	private String password;
	private String secondaryPassword;
	
	private BizComponentData fsBizComponentDataBySecurityQuestion1;
	private BizComponentData fsBizComponentDataBySecurityQuestion2;
	private BizComponentData fsBizComponentDataBySecurityQuestion3;
	private BizComponentData fsBizComponentDataBySecurityQuestion4;
	private BizComponentData fsBizComponentDataBySecurityQuestion5;
	
	private String securityAnswer1;
	private String securityAnswer2;
	private String securityAnswer3;
	private String securityAnswer4;
	private String securityAnswer5;
	private String createdBy;
	private String updatedBy;
	private Date creationDate;
	private Date lastUpdated;
	private String userType;
	private String email;
	private String caption;
	private String imageUrl;
	private String loginType;
	private Date lockDt;
	private BigDecimal lockCnt;
	private String unlockBy;
	private String unLockIp;
	private Date unlockDt;
	private String resetBy;
	private String hresetBy;
	private String hresetIp;
	private Date hresetkDt;
	private String resetIp;
	private Date resetDt;
	private String status;

	public CustomerLogin() {
	}

	public CustomerLogin(BigDecimal custLoginId, String password) {
		this.custLoginId = custLoginId;
		this.password = password;
	}

	public CustomerLogin(BigDecimal custLoginId, Customer fsCustomer,
			LanguageType fsLanguageType, CountryMaster fsCountryMaster,
			CompanyMaster fsCompanyMaster, String userName, String password,
			String secondaryPassword,
			BizComponentData fsBizComponentDataBySecurityQuestion1,
			BizComponentData fsBizComponentDataBySecurityQuestion2,
			BizComponentData fsBizComponentDataBySecurityQuestion3,
			BizComponentData fsBizComponentDataBySecurityQuestion4,
			BizComponentData fsBizComponentDataBySecurityQuestion5,
			String securityAnswer1, String securityAnswer2,
			String securityAnswer3, String securityAnswer4,
			String securityAnswer5, String createdBy, String updatedBy,
			Date creationDate, Date lastUpdated, String userType, String email,
			String caption, String imageUrl, String loginType, Date lockDt,
			BigDecimal lockCnt, String unlockBy, String unLockIp,
			Date unlockDt, String resetBy, String hresetBy, String hresetIp,
			Date hresetkDt, String resetIp, Date resetDt,String status) {
		super();
		this.custLoginId = custLoginId;
		this.fsCustomer = fsCustomer;
		this.fsLanguageType = fsLanguageType;
		this.fsCountryMaster = fsCountryMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.userName = userName;
		this.password = password;
		this.secondaryPassword = secondaryPassword;
		this.fsBizComponentDataBySecurityQuestion1 = fsBizComponentDataBySecurityQuestion1;
		this.fsBizComponentDataBySecurityQuestion2 = fsBizComponentDataBySecurityQuestion2;
		this.fsBizComponentDataBySecurityQuestion3 = fsBizComponentDataBySecurityQuestion3;
		this.fsBizComponentDataBySecurityQuestion4 = fsBizComponentDataBySecurityQuestion4;
		this.fsBizComponentDataBySecurityQuestion5 = fsBizComponentDataBySecurityQuestion5;
		this.securityAnswer1 = securityAnswer1;
		this.securityAnswer2 = securityAnswer2;
		this.securityAnswer3 = securityAnswer3;
		this.securityAnswer4 = securityAnswer4;
		this.securityAnswer5 = securityAnswer5;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.creationDate = creationDate;
		this.lastUpdated = lastUpdated;
		this.userType = userType;
		this.email = email;
		this.caption = caption;
		this.imageUrl = imageUrl;
		this.loginType = loginType;
		this.lockDt = lockDt;
		this.lockCnt = lockCnt;
		this.unlockBy = unlockBy;
		this.unLockIp = unLockIp;
		this.unlockDt = unlockDt;
		this.resetBy = resetBy;
		this.hresetBy = hresetBy;
		this.hresetIp = hresetIp;
		this.hresetkDt = hresetkDt;
		this.resetIp = resetIp;
		this.resetDt = resetDt;
		this.status = status;
	}

	@Id
	@GeneratedValue(generator="fs_customer_login_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_customer_login_seq" ,sequenceName="FS_CUSTOMER_LOGIN_SEQ",allocationSize=1)		
	@Column(name = "CUST_LOGIN_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCustLoginId() {
		return this.custLoginId;
	}

	public void setCustLoginId(BigDecimal custLoginId) {
		this.custLoginId = custLoginId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return this.fsCustomer;
	}

	public void setFsCustomer(Customer fsCustomer) {
		this.fsCustomer = fsCustomer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getFsLanguageType() {
		return this.fsLanguageType;
	}

	public void setFsLanguageType(LanguageType fsLanguageType) {
		this.fsLanguageType = fsLanguageType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}
	
	@Column(name = "USER_NAME", length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "PASSWORD", nullable = false, length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "SECONDARY_PASSWORD", length = 100)
	public String getSecondaryPassword() {
		return this.secondaryPassword;
	}

	public void setSecondaryPassword(String secondaryPassword) {
		this.secondaryPassword = secondaryPassword;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECURITY_QUESTION1")
	public BizComponentData getFsBizComponentDataBySecurityQuestion1() {
		return fsBizComponentDataBySecurityQuestion1;
	}

	public void setFsBizComponentDataBySecurityQuestion1(BizComponentData fsBizComponentDataBySecurityQuestion1) {
		this.fsBizComponentDataBySecurityQuestion1 = fsBizComponentDataBySecurityQuestion1;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECURITY_QUESTION2")
	public BizComponentData getFsBizComponentDataBySecurityQuestion2() {
		return fsBizComponentDataBySecurityQuestion2;
	}

	public void setFsBizComponentDataBySecurityQuestion2(BizComponentData fsBizComponentDataBySecurityQuestion2) {
		this.fsBizComponentDataBySecurityQuestion2 = fsBizComponentDataBySecurityQuestion2;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECURITY_QUESTION3")
	public BizComponentData getFsBizComponentDataBySecurityQuestion3() {
		return fsBizComponentDataBySecurityQuestion3;
	}

	public void setFsBizComponentDataBySecurityQuestion3(BizComponentData fsBizComponentDataBySecurityQuestion3) {
		this.fsBizComponentDataBySecurityQuestion3 = fsBizComponentDataBySecurityQuestion3;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECURITY_QUESTION4")
	public BizComponentData getFsBizComponentDataBySecurityQuestion4() {
		return fsBizComponentDataBySecurityQuestion4;
	}

	public void setFsBizComponentDataBySecurityQuestion4(BizComponentData fsBizComponentDataBySecurityQuestion4) {
		this.fsBizComponentDataBySecurityQuestion4 = fsBizComponentDataBySecurityQuestion4;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECURITY_QUESTION5")
	public BizComponentData getFsBizComponentDataBySecurityQuestion5() {
		return fsBizComponentDataBySecurityQuestion5;
	}

	public void setFsBizComponentDataBySecurityQuestion5(BizComponentData fsBizComponentDataBySecurityQuestion5) {
		this.fsBizComponentDataBySecurityQuestion5 = fsBizComponentDataBySecurityQuestion5;
	}

	@Column(name = "SECURITY_ANSWER1", length = 200)
	public String getSecurityAnswer1() {
		return this.securityAnswer1;
	}

	public void setSecurityAnswer1(String securityAnswer1) {
		this.securityAnswer1 = securityAnswer1;
	}

	@Column(name = "SECURITY_ANSWER2", length = 200)
	public String getSecurityAnswer2() {
		return this.securityAnswer2;
	}

	public void setSecurityAnswer2(String securityAnswer2) {
		this.securityAnswer2 = securityAnswer2;
	}

	@Column(name = "SECURITY_ANSWER3", length = 200)
	public String getSecurityAnswer3() {
		return this.securityAnswer3;
	}

	public void setSecurityAnswer3(String securityAnswer3) {
		this.securityAnswer3 = securityAnswer3;
	}

	@Column(name = "SECURITY_ANSWER4", length = 200)
	public String getSecurityAnswer4() {
		return this.securityAnswer4;
	}

	public void setSecurityAnswer4(String securityAnswer4) {
		this.securityAnswer4 = securityAnswer4;
	}

	@Column(name = "SECURITY_ANSWER5", length = 200)
	public String getSecurityAnswer5() {
		return this.securityAnswer5;
	}

	public void setSecurityAnswer5(String securityAnswer5) {
		this.securityAnswer5 = securityAnswer5;
	}

	@Column(name = "CREATED_BY", length = 30)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "UPDATED_BY", length = 30)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "CREATION_DATE")
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "LAST_UPDATED")
	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Column(name = "USER_TYPE", length = 30)
	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Column(name = "EMAIL", length = 30)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CAPTION", length = 30)
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Column(name = "IMAGE_PATH", length = 30)
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Column(name = "LOGIN_TYP")
	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	@Column(name = "LOCK_DT")
	public Date getLockDt() {
		return lockDt;
	}

	public void setLockDt(Date lockDt) {
		this.lockDt = lockDt;
	}

	@Column(name = "LOCK_CNT")
	public BigDecimal getLockCnt() {
		return lockCnt;
	}

	public void setLockCnt(BigDecimal lockCnt) {
		this.lockCnt = lockCnt;
	}

	@Column(name = "UNLOCK_BY")
	public String getUnlockBy() {
		return unlockBy;
	}

	public void setUnlockBy(String unlockBy) {
		this.unlockBy = unlockBy;
	}

	@Column(name = "UNLOCK_IP")
	public String getUnLockIp() {
		return unLockIp;
	}

	public void setUnLockIp(String unLockIp) {
		this.unLockIp = unLockIp;
	}

	@Column(name = "UNLOCK_DT")
	public Date getUnlockDt() {
		return unlockDt;
	}

	public void setUnlockDt(Date unlockDt) {
		this.unlockDt = unlockDt;
	}

	@Column(name = "RESET_BY")
	public String getResetBy() {
		return resetBy;
	}

	public void setResetBy(String resetBy) {
		this.resetBy = resetBy;
	}

	@Column(name = "HRESET_BY")
	public String getHresetBy() {
		return hresetBy;
	}

	public void setHresetBy(String hresetBy) {
		this.hresetBy = hresetBy;
	}

	@Column(name = "HRESET_IP")
	public String getHresetIp() {
		return hresetIp;
	}

	public void setHresetIp(String hresetIp) {
		this.hresetIp = hresetIp;
	}

	@Column(name = "HRESET_DT")
	public Date getHresetkDt() {
		return hresetkDt;
	}

	public void setHresetkDt(Date hresetkDt) {
		this.hresetkDt = hresetkDt;
	}

	@Column(name = "RESET_IP")
	public String getResetIp() {
		return resetIp;
	}

	public void setResetIp(String resetIp) {
		this.resetIp = resetIp;
	}

	@Column(name = "RESET_DT")
	public Date getResetDt() {
		return resetDt;
	}

	public void setResetDt(Date resetDt) {
		this.resetDt = resetDt;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}

package com.amg.exchange.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;

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


/*******************************************************************************************************************

File		: Marketingdata.java

Project	: AlmullaExchange

Package	: com.amg.exchange.model

Created	:	
				Date	: 12-Dec-2014 
				By		: Ramakrishna
				Revision:

 

Description: TODO 

********************************************************************************************************************/
@SuppressWarnings("serial")
@Entity
@Table(name = "EX_MARKETING_DATA")
public class MarketingData implements Serializable {
	
	private BigDecimal markdataId;
	private String loginHeaderTitle;
	private Blob   loginHeaderlogo;
    private String loginHeaderText;
    private String loginHeaderArabicTitle;
    private Blob   loginHeaderArabiclogo;
    private String loginHeaderArabicText;
    private String innerHeaderTitle;
    private Blob   innerHeaderlogo;
    private String innerHeaderText;
    private String innerHeaderArabicTitle;
    private Blob   innerHeaderArabiclogo;
    private String innerHeaderArabicText;
    private String loginFooterText;
    private String innerFooterText;
    private Blob   loginBannerImage;
    private String loginNewsEvent;
    private CountryMaster applicationCountry;
    private LanguageType marklangtype;
    
    public MarketingData()
    {
    	
    }

	MarketingData(BigDecimal markdataId, String loginHeaderTitle,
			Blob loginHeaderlogo, String loginHeaderText,
			String loginHeaderArabicTitle, Blob loginHeaderArabiclogo,
			String loginHeaderArabicText, String innerHeaderTitle,
			Blob   innerHeaderlogo,String innerHeaderText,
			String innerHeaderArabicTitle,Blob innerHeaderArabiclogo,
			String innerHeaderArabiText,String logingFooterText,
			Blob loginBannerImage,String loginNewsEvent,
			CountryMaster applicationCountry,LanguageType marklangtype)
    {
		this.markdataId=markdataId;
		this.loginHeaderTitle=loginHeaderTitle;
		this.loginHeaderlogo=loginHeaderlogo;
		this.loginHeaderText=loginHeaderText;
		this.loginHeaderArabicText=loginHeaderArabicText;
		this.loginHeaderArabiclogo=loginHeaderArabiclogo;
		this.loginHeaderArabicText=loginHeaderArabicText;
		this.innerHeaderTitle=innerHeaderTitle;
		this.innerHeaderlogo=innerHeaderlogo;
		this.innerHeaderText=innerHeaderText;
		this.innerHeaderArabicTitle=innerHeaderArabicTitle;
		this.innerHeaderArabiclogo=innerHeaderArabiclogo;
		this.innerHeaderArabicText=innerHeaderArabiText;
		this.loginBannerImage=loginBannerImage;
		this.loginNewsEvent=loginNewsEvent;
		this.applicationCountry=applicationCountry;
    	this.marklangtype=marklangtype;
    }
	
	@Id
	@GeneratedValue(generator="ex_marketing_data_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_marketing_data_seq",sequenceName="EX_MARKETING_DATA_SEQ",allocationSize=1)
	@Column(name = "MARKETING_DATA_ID", unique = true, nullable = false)
    public BigDecimal getMarkdataId() {
		return markdataId;
	}
	public void setMarkdataId(BigDecimal markdataId) {
		this.markdataId = markdataId;
	}
	@Column(name="LOGIN_HEADER_TITLE", length=80)
	public String getLoginHeaderTitle() {
		return loginHeaderTitle;
	}
	public void setLoginHeaderTitle(String loginHeaderTitle) {
		this.loginHeaderTitle = loginHeaderTitle;
	}
	@Column(name="LOGIN_HEADER_LOGO")
	public Blob getLoginHeaderlogo() {
		return loginHeaderlogo;
	}
	public void setLoginHeaderlogo(Blob loginHeaderlogo) {
		this.loginHeaderlogo = loginHeaderlogo;
	}
	@Column(name="LOGIN_HEADER_TEXT", length=80)
	public String getLoginHeaderText() {
		return loginHeaderText;
	}
	public void setLoginHeaderText(String loginHeaderText) {
		this.loginHeaderText = loginHeaderText;
	}
	@Column(name="LOGIN_HEADER_ARABIC_TITLE", length=80)
	public String getLoginHeaderArabicTitle() {
		return loginHeaderArabicTitle;
	}
	public void setLoginHeaderArabicTitle(String loginHeaderArabicTitle) {
		this.loginHeaderArabicTitle = loginHeaderArabicTitle;
	}
	@Column(name="LOGIN_HEADER_ARABIC_LOGO")
	public Blob getLoginHeaderArabiclogo() {
		return loginHeaderArabiclogo;
	}
	public void setLoginHeaderArabiclogo(Blob loginHeaderArabiclogo) {
		this.loginHeaderArabiclogo = loginHeaderArabiclogo;
	}
	@Column(name="LOGIN_HEADER_ARABIC_TEXT", length=80)
	public String getLoginHeaderArabicText() {
		return loginHeaderArabicText;
	}
	public void setLoginHeaderArabicText(String loginHeaderArabicText) {
		this.loginHeaderArabicText = loginHeaderArabicText;
	}
	@Column(name="INNER_HEADER_TITLE", length=80)
	public String getInnerHeaderTitle() {
		return innerHeaderTitle;
	}
	public void setInnerHeaderTitle(String innerHeaderTitle) {
		this.innerHeaderTitle = innerHeaderTitle;
	}
	@Column(name="INNER_HEADER_LOGO")
	public Blob getInnerHeaderlogo() {
		return innerHeaderlogo;
	}
	public void setInnerHeaderlogo(Blob innerHeaderlogo) {
		this.innerHeaderlogo = innerHeaderlogo;
	}
	@Column(name="INNER_HEADER_TEXT", length=80)
	public String getInnerHeaderText() {
		return innerHeaderText;
	}
	public void setInnerHeaderText(String innerHeaderText) {
		this.innerHeaderText = innerHeaderText;
	}
	@Column(name="INNER_HEADER_ARABIC_TITLE", length=80)
	public String getInnerHeaderArabicTitle() {
		return innerHeaderArabicTitle;
	}
	public void setInnerHeaderArabicTitle(String innerHeaderArabicTitle) {
		this.innerHeaderArabicTitle = innerHeaderArabicTitle;
	}
	@Column(name="INNER_HEADER_ARABIC_LOGO")
	public Blob getInnerHeaderArabiclogo() {
		return innerHeaderArabiclogo;
	}
	public void setInnerHeaderArabiclogo(Blob innerHeaderArabiclogo) {
		this.innerHeaderArabiclogo = innerHeaderArabiclogo;
	}
	@Column(name="INNER_HEADER_ARABIC_TEXT", length=80)
	public String getInnerHeaderArabicText() {
		return innerHeaderArabicText;
	}
	public void setInnerHeaderArabicText(String innerHeaderArabicText) {
		this.innerHeaderArabicText = innerHeaderArabicText;
	}
	@Column(name="LOGIN_FOOTER_TEXT", length=80)
	public String getLoginFooterText() {
		return loginFooterText;
	}
	public void setLoginFooterText(String loginFooterText) {
		this.loginFooterText = loginFooterText;
	}
	@Column(name="INNER_FOOTER_TEXT", length=80)
	public String getInnerFooterText() {
		return innerFooterText;
	}
	public void setInnerFooterText(String innerFooterText) {
		this.innerFooterText = innerFooterText;
	}
	@Column(name="LOGIN_BANNER_IMAGE")
	public Blob getLoginBannerImage() {
		return loginBannerImage;
	}
	public void setLoginBannerImage(Blob loginBannerImage) {
		this.loginBannerImage = loginBannerImage;
	}
	@Column(name="LOGIN_NEWS_EVENTS", length=4000)
	public String getLoginNewsEvent() {
		return loginNewsEvent;
	}
	public void setLoginNewsEvent(String loginNewsEvent) {
		this.loginNewsEvent = loginNewsEvent;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountry() {
		return applicationCountry;
	}
	
	public void setApplicationCountry(CountryMaster applicationCountry) {
		this.applicationCountry = applicationCountry;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getMarklangtype() {
		return marklangtype;
	}

	public void setMarklangtype(LanguageType marklangtype) {
		this.marklangtype = marklangtype;
	}
	
	
	
	
}

package com.amg.exchange.registration.model;

import java.math.BigDecimal;

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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;

@Entity
@Table(name = "FS_SUCCESS_MESSAGE" )
public class SuccessMessage implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal messageId;
	private LanguageType fsLanguageType;
	private CountryMaster fsCountryMessage;
	private String seccessMsg = null;

	public SuccessMessage() {
	}

	public SuccessMessage(BigDecimal messageId) {
		this.messageId = messageId;
	}

	public SuccessMessage(BigDecimal messageId, LanguageType fsLanguageType, 
									   CountryMaster fsCountryMessage, String seccessMsg) {

		this.messageId = messageId;
		this.fsLanguageType = fsLanguageType;
		this.fsCountryMessage = fsCountryMessage;
		this.setSeccessMsg(seccessMsg);
	}

	@Id
	/*@TableGenerator(name="messageid",table="fs_messageidpk",pkColumnName="messageidkey",pkColumnValue="messageidvalue",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="messageid")
*/	
	@GeneratedValue(generator="fs_success_message_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_success_message_seq" ,sequenceName="FS_SUCCESS_MESSAGE_SEQ",allocationSize=1)	
	@Column(name = "SUCCESS_MSG_PK",unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getMessageId() {
		return this.messageId;
	}
	public void setMessageId(BigDecimal messageId) {
		this.messageId = messageId;
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
	public CountryMaster getFsCountryMessage() {
		return fsCountryMessage;
	}
	public void setFsCountryMessage(CountryMaster fsCountryMessage) {
		this.fsCountryMessage = fsCountryMessage;
	}

	@Column(name="MESSAGE_DESC")
	public String getSeccessMsg() {
		return seccessMsg;
	}
	public void setSeccessMsg(String seccessMsg) {
		this.seccessMsg = seccessMsg;
	}
	
}

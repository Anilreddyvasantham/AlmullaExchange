package com.amg.exchange.jvprocess.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_EX_ACLS")
public class ViewAccountClass implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String aclscod;
	private String aclsdes;
	/*private String aclsint;
	private BigDecimal subind;
	private String subcre;
	private String curcod;
	private String sumind;
	private Date crtdat;
	private Date upddat;
	private String creator;
	private String modifier;
	private String recv_pay_ind;
	private BigDecimal fcty_id;*/
	
	
	
	
	
	@Id
	@Column(name="ACLSCOD")
	public String getAclscod() {
		return aclscod;
	}
	public void setAclscod(String aclscod) {
		this.aclscod = aclscod;
	}
	
	@Column(name="ACLSDES")
	public String getAclsdes() {
		return aclsdes;
	}
	public void setAclsdes(String aclsdes) {
		this.aclsdes = aclsdes;
	}
	
	/*@Column(name="ACLSINT")
	public String getAclsint() {
		return aclsint;
	}
	public void setAclsint(String aclsint) {
		this.aclsint = aclsint;
	}
	@Column(name="SUBIND")
	public BigDecimal getSubind() {
		return subind;
	}
	public void setSubind(BigDecimal subind) {
		this.subind = subind;
	}
	
	@Column(name="SUBCRE")
	public String getSubcre() {
		return subcre;
	}
	public void setSubcre(String subcre) {
		this.subcre = subcre;
	}
	
	@Column(name="CURCOD")
	public String getCurcod() {
		return curcod;
	}
	public void setCurcod(String curcod) {
		this.curcod = curcod;
	}
	
	@Column(name="SUMIND")
	public String getSumind() {
		return sumind;
	}
	public void setSumind(String sumind) {
		this.sumind = sumind;
	}
	
	@Column(name="CRTDAT")
	public Date getCrtdat() {
		return crtdat;
	}
	public void setCrtdat(Date crtdat) {
		this.crtdat = crtdat;
	}
	
	@Column(name="UPDDAT")
	public Date getUpddat() {
		return upddat;
	}
	public void setUpddat(Date upddat) {
		this.upddat = upddat;
	}
	
	@Column(name="CREATOR")
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name="MODIFIER")
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	@Column(name="RECV_PAY_IND")
	public String getRecv_pay_ind() {
		return recv_pay_ind;
	}
	public void setRecv_pay_ind(String recv_pay_ind) {
		this.recv_pay_ind = recv_pay_ind;
	}
	
	@Column(name="FCTY_ID")
	public BigDecimal getFcty_id() {
		return fcty_id;
	}
	public void setFcty_id(BigDecimal fcty_id) {
		this.fcty_id = fcty_id;
	}*/

}
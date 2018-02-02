package com.amg.exchange.jvprocess.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Author Nazish Ehsan Hashmi
 * Created Date - 03-09-2015
 */
@Entity
@Table(name="V_ALEV3")
public class ViewFranchiseAlev3 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String al2Com;
	private String al3Cod;
	private String al3Com;
	private String al3Des;
	private String al3Int;
	@Id
	@Column(name="AL2COM")
	public String getAl2Com() {
		return al2Com;
	}
	
	@Column(name="AL3COD")
	public String getAl3Cod() {
		return al3Cod;
	}
	
	@Column(name="AL3COM")
	public String getAl3Com() {
		return al3Com;
	}
	
	@Column(name="AL3DES")
	public String getAl3Des() {
		return al3Des;
	}
	
	@Column(name="AL3INT")
	public String getAl3Int() {
		return al3Int;
	}
	public void setAl2Com(String al2Com) {
		this.al2Com = al2Com;
	}
	public void setAl3Cod(String al3Cod) {
		this.al3Cod = al3Cod;
	}
	public void setAl3Com(String al3Com) {
		this.al3Com = al3Com;
	}
	public void setAl3Des(String al3Des) {
		this.al3Des = al3Des;
	}
	public void setAl3Int(String al3Int) {
		this.al3Int = al3Int;
	}
	
}

package com.amg.exchange.jvprocess.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author Nazish Ehsan Hashmi
 * Created Date - 03-09-2015
 */
@Entity
@Table(name="V_ALEV2")
public class ViewBusinessoperationAlev2 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String al1Cod;
	private String al2Cod;
	private String al2Com;
	private String al2Des;
	private String al2Int;
	
	
	/*@Id
	@Column(name="AL1COD")
	public String getAl1Com() {
		return al1Com;
	}*/
	@Id
	@Column(name="AL1COD")
	public String getAl1Cod() {
		return al1Cod;
	}

	public void setAl1Cod(String al1Cod) {
		this.al1Cod = al1Cod;
	}

	@Column(name="AL2COD")
	public String getAl2Cod() {
		return al2Cod;
	}
	
	@Column(name="AL2COM")
	public String getAl2Com() {
		return al2Com;
	}
	
	@Column(name="AL2DES")
	public String getAl2Des() {
		return al2Des;
	}
	
	@Column(name="AL2INT")
	public String getAl2Int() {
		return al2Int;
	}
	/*public void setAl1Com(String al1Com) {
		this.al1Com = al1Com;
	}*/
	public void setAl2Cod(String al2Cod) {
		this.al2Cod = al2Cod;
	}
	public void setAl2Com(String al2Com) {
		this.al2Com = al2Com;
	}
	public void setAl2Des(String al2Des) {
		this.al2Des = al2Des;
	}
	public void setAl2Int(String al2Int) {
		this.al2Int = al2Int;
	}

	
}

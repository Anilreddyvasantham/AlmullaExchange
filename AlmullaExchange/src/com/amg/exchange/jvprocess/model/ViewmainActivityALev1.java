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
@Table(name="V_ALEV1")
public class ViewmainActivityALev1 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String al1Cod;
	private String al1Des;
	private String al1Int;
	
	@Id
	@Column(name="AL1COD")
	public String getAl1Cod() {
		return al1Cod;
	}
	
	@Column(name="AL1DES")
	public String getAl1Des() {
		return al1Des;
	}
	
	@Column(name="AL1INT")
	public String getAl1Int() {
		return al1Int;
	}
	public void setAl1Cod(String al1Cod) {
		this.al1Cod = al1Cod;
	}
	public void setAl1Des(String al1Des) {
		this.al1Des = al1Des;
	}
	public void setAl1Int(String al1Int) {
		this.al1Int = al1Int;
	}
	
	
}

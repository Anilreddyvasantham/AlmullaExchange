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
@Table(name="V_ALEV4")
public class ViewProductAlev4 implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String al3Com;
private String al4Cod;
private String al4Com;
private String al4Des;
private String al4Int;

@Id
@Column(name="AL3COM")
public String getAl3Com() {
	return al3Com;
}

@Column(name="AL4COD")
public String getAl4Cod() {
	return al4Cod;
}

@Column(name="AL4COM")
public String getAl4Com() {
	return al4Com;
}

@Column(name="AL4DES")
public String getAl4Des() {
	return al4Des;
}

@Column(name="AL4INT")
public String getAl4Int() {
	return al4Int;
}
public void setAl3Com(String al3Com) {
	this.al3Com = al3Com;
}
public void setAl4Cod(String al4Cod) {
	this.al4Cod = al4Cod;
}
public void setAl4Com(String al4Com) {
	this.al4Com = al4Com;
}
public void setAl4Des(String al4Des) {
	this.al4Des = al4Des;
}
public void setAl4Int(String al4Int) {
	this.al4Int = al4Int;
}


}

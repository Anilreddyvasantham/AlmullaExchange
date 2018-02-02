package com.amg.exchange.foreigncurrency.bean;


import java.io.Serializable;
import java.math.BigDecimal;

public class ForeignLocalCurrencyDataTable implements Serializable {

   private static final long serialVersionUID = 1L;
   private int serial;
   private BigDecimal item;
   private String qty="";
   private String price;
   private int stock;
   private BigDecimal denominationId;
   private BigDecimal currencyId ; 
   private String denominationDesc;
   private BigDecimal denominationAmount;
   
   
   private BigDecimal pkDenom;
   
   public ForeignLocalCurrencyDataTable(){
	   
   }
   
   public ForeignLocalCurrencyDataTable(int serial, BigDecimal item, String qty, String price, int stock, BigDecimal pkDenom, BigDecimal denominationId, BigDecimal currencyId, String denominationDesc, BigDecimal denominationAmount) {
	   this.serial = serial;
       this.item = item;
       this.qty = qty;
       this.price = price;
       this.stock = stock;
       this.denominationId =denominationId;
       this.pkDenom = pkDenom;
       this.currencyId = currencyId;
       this.denominationDesc = denominationDesc;
       this.denominationAmount = denominationAmount;
   }
      
   
   public int getSerial() {
		return serial;
	}
	public void setSerial(int serial) {
		this.serial = serial;
	}
	
   public BigDecimal getItem() {
       return item;
   }
   public void setItem(BigDecimal item) {
       this.item = item;
   }

   public String getPrice() {
       return price;
   }
   public void setPrice(String price) {
       this.price = price;
   }

   public String getQty() {
       return qty;
   }
   public void setQty(String qty) {
       this.qty = qty;
   }

	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}

	public BigDecimal getPkDenom() {
		return pkDenom;
	}

	public void setPkDenom(BigDecimal pkDenom) {
		this.pkDenom = pkDenom;
	}

	public BigDecimal getDenominationId() {
		return denominationId;
	}

	public void setDenominationId(BigDecimal denominationId) {
		this.denominationId = denominationId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getDenominationDesc() {
		return denominationDesc;
	}

	public void setDenominationDesc(String denominationDesc) {
		this.denominationDesc = denominationDesc;
	}
	public BigDecimal getDenominationAmount() {
		return denominationAmount;
	}
	public void setDenominationAmount(BigDecimal denominationAmount) {
		this.denominationAmount = denominationAmount;
	}
    
}

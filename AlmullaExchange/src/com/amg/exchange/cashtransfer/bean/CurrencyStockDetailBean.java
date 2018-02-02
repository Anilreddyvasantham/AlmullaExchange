package com.amg.exchange.cashtransfer.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class CurrencyStockDetailBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// Header Properties

	private BigDecimal oneLakh;
	private BigDecimal fiftyThousand;
	private BigDecimal twentyThousand;
	private BigDecimal tenThousand;
	private BigDecimal fiveThousand;
	private BigDecimal twoThousand;
	private BigDecimal thousand;
	private BigDecimal fiveHundred;
	private BigDecimal twoHundred;
	private BigDecimal hundred;
	private BigDecimal fifty;
	private BigDecimal twenty;
	private BigDecimal ten;
	private BigDecimal five;
	private BigDecimal two;
	private BigDecimal one;
	private BigDecimal pointFive;
	private BigDecimal pointTwoFive;
	private BigDecimal pointOne;
	private BigDecimal pointZeroFive;
	private BigDecimal pointZeroTwo;
	private BigDecimal pointZeroOne;
	private BigDecimal pointZeroZeroFive;
	private BigDecimal pointZeroZeroOne;
	private String currencyName;

	// Modified row properties
	private BigDecimal oneLakhs;
	private BigDecimal fiftyThousands;
	private BigDecimal twentyThousands;
	private BigDecimal tenThousands;
	private BigDecimal fiveThousands;
	private BigDecimal twoThousands;
	private BigDecimal thousands;
	private BigDecimal fiveHundreds;
	private BigDecimal twoHundreds;
	private BigDecimal hundreds;
	private BigDecimal fiftys;
	private BigDecimal twentys;
	private BigDecimal tens;
	private BigDecimal fives;
	private BigDecimal twos;
	private BigDecimal ones;
	private BigDecimal pointFives;
	private BigDecimal pointTwoFives;
	private BigDecimal pointOnes;
	private BigDecimal pointZeroFives;
	private BigDecimal pointZeroTwos;
	private BigDecimal pointZeroOnes;
	private BigDecimal pointZeroZeroFives;
	private BigDecimal pointZeroZeroOnes;

	private BigDecimal currencyId;
	private BigDecimal rowTotal;
	private BigDecimal modRowTotal;

	private boolean booNilCurrency;
	private BigDecimal denominationId;

	private BigDecimal physicalNoteTotal;
	private BigDecimal systemNoteTotal;
	private BigDecimal physicalAmountTotal;
	private BigDecimal systemAmountTotal;
	private BigDecimal diffAmountTotal;

	// adjustment
	private BigDecimal oneLakhAdj;
	private BigDecimal fiftyThousandAdj;
	private BigDecimal twentyThousandAdj;
	private BigDecimal tenThousandAdj;
	private BigDecimal fiveThousandAdj;
	private BigDecimal twoThousandAdj;
	private BigDecimal thousandAdj;
	private BigDecimal fiveHundredAdj;
	private BigDecimal twoHundredAdj;
	private BigDecimal hundredAdj;
	private BigDecimal fiftyAdj;
	private BigDecimal twentyAdj;
	private BigDecimal tenAdj;
	private BigDecimal fiveAdj;
	private BigDecimal twoAdj;
	private BigDecimal oneAdj;
	private BigDecimal pointFiveAdj;
	private BigDecimal pointTwoFiveAdj;
	private BigDecimal pointOneAdj;
	private BigDecimal pointZeroFiveAdj;
	private BigDecimal pointZeroTwoAdj;
	private BigDecimal pointZeroOneAdj;
	private BigDecimal pointZeroZeroFiveAdj;
	private BigDecimal pointZeroZeroOneAdj;

	// adjustment
	private BigDecimal oneLakhDenomId;
	private BigDecimal fiftyThousandDenomId;
	private BigDecimal twentyThousandDenomId;
	private BigDecimal tenThousandDenomId;
	private BigDecimal fiveThousandDenomId;
	private BigDecimal twoThousandDenomId;
	private BigDecimal thousandDenomId;
	private BigDecimal fiveHundredDenomId;
	private BigDecimal twoHundredDenomId;
	private BigDecimal hundredDenomId;
	private BigDecimal fiftyDenomId;
	private BigDecimal twentyDenomId;
	private BigDecimal tenDenomId;
	private BigDecimal fiveDenomId;
	private BigDecimal twoDenomId;
	private BigDecimal oneDenomId;
	private BigDecimal pointFiveDenomId;
	private BigDecimal pointTwoFiveDenomId;
	private BigDecimal pointOneDenomId;
	private BigDecimal pointZeroFiveDenomId;
	private BigDecimal pointZeroTwoDenomId;
	private BigDecimal pointZeroOneDenomId;
	private BigDecimal pointZeroZeroFiveDenomId;
	private BigDecimal pointZeroZeroOneDenomId;

	private Boolean booOneLakhs;
	private Boolean booFiftyThousands;
	private Boolean booTwentyThousands;
	private Boolean booTenThousands;
	private Boolean booFiveThousands;
	private Boolean booTwoThousands;
	private Boolean booThousands;
	private Boolean booFiveHundreds;
	private Boolean booTwoHundreds;
	private Boolean booHundreds;
	private Boolean booFiftys;
	private Boolean booTwentys;
	private Boolean booTens;
	private Boolean booFives;
	private Boolean booTwos;
	private Boolean booOnes;
	private Boolean booPointFives;
	private Boolean booPointTwoFives;
	private Boolean booPointOnes;
	private Boolean booPointZeroFives;
	private Boolean booPointZeroTwos;
	private Boolean booPointZeroOnes;
	private Boolean booPointZeroZeroFives;
	private Boolean booPointZeroZeroOnes;

	private BigDecimal oneLakhDenomAmount;
	private BigDecimal fiftyThousandDenomAmount;
	private BigDecimal twentyThousandDenomAmount;
	private BigDecimal tenThousandDenomAmount;
	private BigDecimal fiveThousandDenomAmount;
	private BigDecimal twoThousandDenomAmount;
	private BigDecimal thousandDenomAmount;
	private BigDecimal fiveHundredDenomAmount;
	private BigDecimal twoHundredDenomAmount;
	private BigDecimal hundredDenomAmount;
	private BigDecimal fiftyDenomAmount;
	private BigDecimal twentyDenomAmount;
	private BigDecimal tenDenomAmount;
	private BigDecimal fiveDenomAmount;
	private BigDecimal twoDenomAmount;
	private BigDecimal oneDenomAmount;
	private BigDecimal pointFiveDenomAmount;
	private BigDecimal pointTwoFiveDenomAmount;
	private BigDecimal pointOneDenomAmount;
	private BigDecimal pointZeroFiveDenomAmount;
	private BigDecimal pointZeroTwoDenomAmount;
	private BigDecimal pointZeroOneDenomAmount;
	private BigDecimal pointZeroZeroFiveDenomAmount;
	private BigDecimal pointZeroZeroOneDenomAmount;

	private BigDecimal oneLakhPNotes;
	private BigDecimal fiftyThousandPNotes;
	private BigDecimal twentyThousandPNotes;
	private BigDecimal tenThousandPNotes;
	private BigDecimal fiveThousandPNotes;
	private BigDecimal twoThousandPNotes;
	private BigDecimal thousandPNotes;
	private BigDecimal fiveHundredPNotes;
	private BigDecimal twoHundredPNotes;
	private BigDecimal hundredPNotes;
	private BigDecimal fiftyPNotes;
	private BigDecimal twentyPNotes;
	private BigDecimal tenPNotes;
	private BigDecimal fivePNotes;
	private BigDecimal twoPNotes;
	private BigDecimal onePNotes;
	private BigDecimal pointFivePNotes;
	private BigDecimal pointTwoFivePNotes;
	private BigDecimal pointOnePNotes;
	private BigDecimal pointZeroFivePNotes;
	private BigDecimal pointZeroTwoPNotes;
	private BigDecimal pointZeroOnePNotes;
	private BigDecimal pointZeroZeroFivePNotes;
	private BigDecimal pointZeroZeroOnePNotes;

	public CurrencyStockDetailBean() {
		super();
	}

	public BigDecimal getThousand() {
		return thousand;
	}

	public void setThousand(BigDecimal thousand) {
		this.thousand = thousand;
	}

	public BigDecimal getFiveHundred() {
		return fiveHundred;
	}

	public void setFiveHundred(BigDecimal fiveHundred) {
		this.fiveHundred = fiveHundred;
	}

	public BigDecimal getHundred() {
		return hundred;
	}

	public void setHundred(BigDecimal hundred) {
		this.hundred = hundred;
	}

	public BigDecimal getFifty() {
		return fifty;
	}

	public void setFifty(BigDecimal fifty) {
		this.fifty = fifty;
	}

	public BigDecimal getTwenty() {
		return twenty;
	}

	public void setTwenty(BigDecimal twenty) {
		this.twenty = twenty;
	}

	public BigDecimal getTen() {
		return ten;
	}

	public void setTen(BigDecimal ten) {
		this.ten = ten;
	}

	public BigDecimal getFive() {
		return five;
	}

	public void setFive(BigDecimal five) {
		this.five = five;
	}

	public BigDecimal getTwo() {
		return two;
	}

	public void setTwo(BigDecimal two) {
		this.two = two;
	}

	public BigDecimal getOne() {
		return one;
	}

	public void setOne(BigDecimal one) {
		this.one = one;
	}

	public BigDecimal getPointFive() {
		return pointFive;
	}

	public void setPointFive(BigDecimal pointFive) {
		this.pointFive = pointFive;
	}

	public BigDecimal getPointTwoFive() {
		return pointTwoFive;
	}

	public void setPointTwoFive(BigDecimal pointTwoFive) {
		this.pointTwoFive = pointTwoFive;
	}

	public BigDecimal getPointOne() {
		return pointOne;
	}

	public void setPointOne(BigDecimal pointOne) {
		this.pointOne = pointOne;
	}

	public BigDecimal getPointZeroFive() {
		return pointZeroFive;
	}

	public void setPointZeroFive(BigDecimal pointZeroFive) {
		this.pointZeroFive = pointZeroFive;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public BigDecimal getRowTotal() {
		return rowTotal;
	}

	public void setRowTotal(BigDecimal rowTotal) {
		this.rowTotal = rowTotal;
	}

	public BigDecimal getOneLakhs() {
		return oneLakhs;
	}

	public void setOneLakhs(BigDecimal oneLakhs) {
		this.oneLakhs = oneLakhs;
	}

	public BigDecimal getFiftyThousand() {
		return fiftyThousand;
	}

	public void setFiftyThousand(BigDecimal fiftyThousand) {
		this.fiftyThousand = fiftyThousand;
	}

	public BigDecimal getTwentyThousand() {
		return twentyThousand;
	}

	public void setTwentyThousand(BigDecimal twentyThousand) {
		this.twentyThousand = twentyThousand;
	}

	public BigDecimal getTenThousand() {
		return tenThousand;
	}

	public void setTenThousand(BigDecimal tenThousand) {
		this.tenThousand = tenThousand;
	}

	public BigDecimal getFiveThousand() {
		return fiveThousand;
	}

	public void setFiveThousand(BigDecimal fiveThousand) {
		this.fiveThousand = fiveThousand;
	}

	public BigDecimal getTwoThousand() {
		return twoThousand;
	}

	public void setTwoThousand(BigDecimal twoThousand) {
		this.twoThousand = twoThousand;
	}

	public BigDecimal getPointZeroTwo() {
		return pointZeroTwo;
	}

	public void setPointZeroTwo(BigDecimal pointZeroTwo) {
		this.pointZeroTwo = pointZeroTwo;
	}

	public BigDecimal getPointZeroOne() {
		return pointZeroOne;
	}

	public void setPointZeroOne(BigDecimal pointZeroOne) {
		this.pointZeroOne = pointZeroOne;
	}

	public BigDecimal getPointZeroZeroFive() {
		return pointZeroZeroFive;
	}

	public void setPointZeroZeroFive(BigDecimal pointZeroZeroFive) {
		this.pointZeroZeroFive = pointZeroZeroFive;
	}

	public BigDecimal getPointZeroZeroOne() {
		return pointZeroZeroOne;
	}

	public void setPointZeroZeroOne(BigDecimal pointZeroZeroOne) {
		this.pointZeroZeroOne = pointZeroZeroOne;
	}

	public BigDecimal getThousands() {
		return thousands;
	}

	public void setThousands(BigDecimal thousands) {
		this.thousands = thousands;
	}

	public BigDecimal getFiveHundreds() {
		return fiveHundreds;
	}

	public void setFiveHundreds(BigDecimal fiveHundreds) {
		this.fiveHundreds = fiveHundreds;
	}

	public BigDecimal getHundreds() {
		return hundreds;
	}

	public void setHundreds(BigDecimal hundreds) {
		this.hundreds = hundreds;
	}

	public BigDecimal getFiftys() {
		return fiftys;
	}

	public void setFiftys(BigDecimal fiftys) {
		this.fiftys = fiftys;
	}

	public BigDecimal getTwentys() {
		return twentys;
	}

	public void setTwentys(BigDecimal twentys) {
		this.twentys = twentys;
	}

	public BigDecimal getTens() {
		return tens;
	}

	public void setTens(BigDecimal tens) {
		this.tens = tens;
	}

	public BigDecimal getFives() {
		return fives;
	}

	public void setFives(BigDecimal fives) {
		this.fives = fives;
	}

	public BigDecimal getTwos() {
		return twos;
	}

	public void setTwos(BigDecimal twos) {
		this.twos = twos;
	}

	public BigDecimal getOnes() {
		return ones;
	}

	public void setOnes(BigDecimal ones) {
		this.ones = ones;
	}

	public BigDecimal getPointFives() {
		return pointFives;
	}

	public void setPointFives(BigDecimal pointFives) {
		this.pointFives = pointFives;
	}

	public BigDecimal getPointTwoFives() {
		return pointTwoFives;
	}

	public void setPointTwoFives(BigDecimal pointTwoFives) {
		this.pointTwoFives = pointTwoFives;
	}

	public BigDecimal getPointOnes() {
		return pointOnes;
	}

	public void setPointOnes(BigDecimal pointOnes) {
		this.pointOnes = pointOnes;
	}

	public BigDecimal getPointZeroFives() {
		return pointZeroFives;
	}

	public void setPointZeroFives(BigDecimal pointZeroFives) {
		this.pointZeroFives = pointZeroFives;
	}

	public BigDecimal getModRowTotal() {
		return modRowTotal;
	}

	public void setModRowTotal(BigDecimal modRowTotal) {
		this.modRowTotal = modRowTotal;
	}

	public boolean isBooNilCurrency() {
		return booNilCurrency;
	}

	public void setBooNilCurrency(boolean booNilCurrency) {
		this.booNilCurrency = booNilCurrency;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getOneLakh() {
		return oneLakh;
	}

	public void setOneLakh(BigDecimal oneLakh) {
		this.oneLakh = oneLakh;
	}

	public BigDecimal getFiftyThousands() {
		return fiftyThousands;
	}

	public void setFiftyThousands(BigDecimal fiftyThousands) {
		this.fiftyThousands = fiftyThousands;
	}

	public BigDecimal getTwentyThousands() {
		return twentyThousands;
	}

	public void setTwentyThousands(BigDecimal twentyThousands) {
		this.twentyThousands = twentyThousands;
	}

	public BigDecimal getTenThousands() {
		return tenThousands;
	}

	public void setTenThousands(BigDecimal tenThousands) {
		this.tenThousands = tenThousands;
	}

	public BigDecimal getFiveThousands() {
		return fiveThousands;
	}

	public void setFiveThousands(BigDecimal fiveThousands) {
		this.fiveThousands = fiveThousands;
	}

	public BigDecimal getTwoThousands() {
		return twoThousands;
	}

	public void setTwoThousands(BigDecimal twoThousands) {
		this.twoThousands = twoThousands;
	}

	public BigDecimal getPointZeroTwos() {
		return pointZeroTwos;
	}

	public void setPointZeroTwos(BigDecimal pointZeroTwos) {
		this.pointZeroTwos = pointZeroTwos;
	}

	public BigDecimal getPointZeroOnes() {
		return pointZeroOnes;
	}

	public void setPointZeroOnes(BigDecimal pointZeroOnes) {
		this.pointZeroOnes = pointZeroOnes;
	}

	public BigDecimal getPointZeroZeroFives() {
		return pointZeroZeroFives;
	}

	public void setPointZeroZeroFives(BigDecimal pointZeroZeroFives) {
		this.pointZeroZeroFives = pointZeroZeroFives;
	}

	public BigDecimal getPointZeroZeroOnes() {
		return pointZeroZeroOnes;
	}

	public void setPointZeroZeroOnes(BigDecimal pointZeroZeroOnes) {
		this.pointZeroZeroOnes = pointZeroZeroOnes;
	}

	public boolean isBooOneLakhs() {
		return booOneLakhs;
	}

	public void setBooOneLakhs(boolean booOneLakhs) {
		this.booOneLakhs = booOneLakhs;
	}

	public BigDecimal getDenominationId() {
		return denominationId;
	}

	public void setDenominationId(BigDecimal denominationId) {
		this.denominationId = denominationId;
	}

	public BigDecimal getOneLakhAdj() {
		return oneLakhAdj;
	}

	public void setOneLakhAdj(BigDecimal oneLakhAdj) {
		this.oneLakhAdj = oneLakhAdj;
	}

	public BigDecimal getFiftyThousandAdj() {
		return fiftyThousandAdj;
	}

	public void setFiftyThousandAdj(BigDecimal fiftyThousandAdj) {
		this.fiftyThousandAdj = fiftyThousandAdj;
	}

	public BigDecimal getTwentyThousandAdj() {
		return twentyThousandAdj;
	}

	public void setTwentyThousandAdj(BigDecimal twentyThousandAdj) {
		this.twentyThousandAdj = twentyThousandAdj;
	}

	public BigDecimal getTenThousandAdj() {
		return tenThousandAdj;
	}

	public void setTenThousandAdj(BigDecimal tenThousandAdj) {
		this.tenThousandAdj = tenThousandAdj;
	}

	public BigDecimal getFiveThousandAdj() {
		return fiveThousandAdj;
	}

	public void setFiveThousandAdj(BigDecimal fiveThousandAdj) {
		this.fiveThousandAdj = fiveThousandAdj;
	}

	public BigDecimal getTwoThousandAdj() {
		return twoThousandAdj;
	}

	public void setTwoThousandAdj(BigDecimal twoThousandAdj) {
		this.twoThousandAdj = twoThousandAdj;
	}

	public BigDecimal getThousandAdj() {
		return thousandAdj;
	}

	public void setThousandAdj(BigDecimal thousandAdj) {
		this.thousandAdj = thousandAdj;
	}

	public BigDecimal getFiveHundredAdj() {
		return fiveHundredAdj;
	}

	public void setFiveHundredAdj(BigDecimal fiveHundredAdj) {
		this.fiveHundredAdj = fiveHundredAdj;
	}

	public BigDecimal getHundredAdj() {
		return hundredAdj;
	}

	public void setHundredAdj(BigDecimal hundredAdj) {
		this.hundredAdj = hundredAdj;
	}

	public BigDecimal getFiftyAdj() {
		return fiftyAdj;
	}

	public void setFiftyAdj(BigDecimal fiftyAdj) {
		this.fiftyAdj = fiftyAdj;
	}

	public BigDecimal getTwentyAdj() {
		return twentyAdj;
	}

	public void setTwentyAdj(BigDecimal twentyAdj) {
		this.twentyAdj = twentyAdj;
	}

	public BigDecimal getTenAdj() {
		return tenAdj;
	}

	public void setTenAdj(BigDecimal tenAdj) {
		this.tenAdj = tenAdj;
	}

	public BigDecimal getFiveAdj() {
		return fiveAdj;
	}

	public void setFiveAdj(BigDecimal fiveAdj) {
		this.fiveAdj = fiveAdj;
	}

	public BigDecimal getTwoAdj() {
		return twoAdj;
	}

	public void setTwoAdj(BigDecimal twoAdj) {
		this.twoAdj = twoAdj;
	}

	public BigDecimal getOneAdj() {
		return oneAdj;
	}

	public void setOneAdj(BigDecimal oneAdj) {
		this.oneAdj = oneAdj;
	}

	public BigDecimal getPointFiveAdj() {
		return pointFiveAdj;
	}

	public void setPointFiveAdj(BigDecimal pointFiveAdj) {
		this.pointFiveAdj = pointFiveAdj;
	}

	public BigDecimal getPointTwoFiveAdj() {
		return pointTwoFiveAdj;
	}

	public void setPointTwoFiveAdj(BigDecimal pointTwoFiveAdj) {
		this.pointTwoFiveAdj = pointTwoFiveAdj;
	}

	public BigDecimal getPointOneAdj() {
		return pointOneAdj;
	}

	public void setPointOneAdj(BigDecimal pointOneAdj) {
		this.pointOneAdj = pointOneAdj;
	}

	public BigDecimal getPointZeroFiveAdj() {
		return pointZeroFiveAdj;
	}

	public void setPointZeroFiveAdj(BigDecimal pointZeroFiveAdj) {
		this.pointZeroFiveAdj = pointZeroFiveAdj;
	}

	public BigDecimal getPointZeroTwoAdj() {
		return pointZeroTwoAdj;
	}

	public void setPointZeroTwoAdj(BigDecimal pointZeroTwoAdj) {
		this.pointZeroTwoAdj = pointZeroTwoAdj;
	}

	public BigDecimal getPointZeroOneAdj() {
		return pointZeroOneAdj;
	}

	public void setPointZeroOneAdj(BigDecimal pointZeroOneAdj) {
		this.pointZeroOneAdj = pointZeroOneAdj;
	}

	public BigDecimal getPointZeroZeroFiveAdj() {
		return pointZeroZeroFiveAdj;
	}

	public void setPointZeroZeroFiveAdj(BigDecimal pointZeroZeroFiveAdj) {
		this.pointZeroZeroFiveAdj = pointZeroZeroFiveAdj;
	}

	public BigDecimal getPointZeroZeroOneAdj() {
		return pointZeroZeroOneAdj;
	}

	public void setPointZeroZeroOneAdj(BigDecimal pointZeroZeroOneAdj) {
		this.pointZeroZeroOneAdj = pointZeroZeroOneAdj;
	}

	public BigDecimal getOneLakhDenomId() {
		return oneLakhDenomId;
	}

	public void setOneLakhDenomId(BigDecimal oneLakhDenomId) {
		this.oneLakhDenomId = oneLakhDenomId;
	}

	public BigDecimal getFiftyThousandDenomId() {
		return fiftyThousandDenomId;
	}

	public void setFiftyThousandDenomId(BigDecimal fiftyThousandDenomId) {
		this.fiftyThousandDenomId = fiftyThousandDenomId;
	}

	public BigDecimal getTwentyThousandDenomId() {
		return twentyThousandDenomId;
	}

	public void setTwentyThousandDenomId(BigDecimal twentyThousandDenomId) {
		this.twentyThousandDenomId = twentyThousandDenomId;
	}

	public BigDecimal getTenThousandDenomId() {
		return tenThousandDenomId;
	}

	public void setTenThousandDenomId(BigDecimal tenThousandDenomId) {
		this.tenThousandDenomId = tenThousandDenomId;
	}

	public BigDecimal getFiveThousandDenomId() {
		return fiveThousandDenomId;
	}

	public void setFiveThousandDenomId(BigDecimal fiveThousandDenomId) {
		this.fiveThousandDenomId = fiveThousandDenomId;
	}

	public BigDecimal getTwoThousandDenomId() {
		return twoThousandDenomId;
	}

	public void setTwoThousandDenomId(BigDecimal twoThousandDenomId) {
		this.twoThousandDenomId = twoThousandDenomId;
	}

	public BigDecimal getThousandDenomId() {
		return thousandDenomId;
	}

	public void setThousandDenomId(BigDecimal thousandDenomId) {
		this.thousandDenomId = thousandDenomId;
	}

	public BigDecimal getFiveHundredDenomId() {
		return fiveHundredDenomId;
	}

	public void setFiveHundredDenomId(BigDecimal fiveHundredDenomId) {
		this.fiveHundredDenomId = fiveHundredDenomId;
	}

	public BigDecimal getHundredDenomId() {
		return hundredDenomId;
	}

	public void setHundredDenomId(BigDecimal hundredDenomId) {
		this.hundredDenomId = hundredDenomId;
	}

	public BigDecimal getFiftyDenomId() {
		return fiftyDenomId;
	}

	public void setFiftyDenomId(BigDecimal fiftyDenomId) {
		this.fiftyDenomId = fiftyDenomId;
	}

	public BigDecimal getTwentyDenomId() {
		return twentyDenomId;
	}

	public void setTwentyDenomId(BigDecimal twentyDenomId) {
		this.twentyDenomId = twentyDenomId;
	}

	public BigDecimal getTenDenomId() {
		return tenDenomId;
	}

	public void setTenDenomId(BigDecimal tenDenomId) {
		this.tenDenomId = tenDenomId;
	}

	public BigDecimal getFiveDenomId() {
		return fiveDenomId;
	}

	public void setFiveDenomId(BigDecimal fiveDenomId) {
		this.fiveDenomId = fiveDenomId;
	}

	public BigDecimal getTwoDenomId() {
		return twoDenomId;
	}

	public void setTwoDenomId(BigDecimal twoDenomId) {
		this.twoDenomId = twoDenomId;
	}

	public BigDecimal getOneDenomId() {
		return oneDenomId;
	}

	public void setOneDenomId(BigDecimal oneDenomId) {
		this.oneDenomId = oneDenomId;
	}

	public BigDecimal getPointFiveDenomId() {
		return pointFiveDenomId;
	}

	public void setPointFiveDenomId(BigDecimal pointFiveDenomId) {
		this.pointFiveDenomId = pointFiveDenomId;
	}

	public BigDecimal getPointTwoFiveDenomId() {
		return pointTwoFiveDenomId;
	}

	public void setPointTwoFiveDenomId(BigDecimal pointTwoFiveDenomId) {
		this.pointTwoFiveDenomId = pointTwoFiveDenomId;
	}

	public BigDecimal getPointOneDenomId() {
		return pointOneDenomId;
	}

	public void setPointOneDenomId(BigDecimal pointOneDenomId) {
		this.pointOneDenomId = pointOneDenomId;
	}

	public BigDecimal getPointZeroFiveDenomId() {
		return pointZeroFiveDenomId;
	}

	public void setPointZeroFiveDenomId(BigDecimal pointZeroFiveDenomId) {
		this.pointZeroFiveDenomId = pointZeroFiveDenomId;
	}

	public BigDecimal getPointZeroTwoDenomId() {
		return pointZeroTwoDenomId;
	}

	public void setPointZeroTwoDenomId(BigDecimal pointZeroTwoDenomId) {
		this.pointZeroTwoDenomId = pointZeroTwoDenomId;
	}

	public BigDecimal getPointZeroOneDenomId() {
		return pointZeroOneDenomId;
	}

	public void setPointZeroOneDenomId(BigDecimal pointZeroOneDenomId) {
		this.pointZeroOneDenomId = pointZeroOneDenomId;
	}

	public BigDecimal getPointZeroZeroFiveDenomId() {
		return pointZeroZeroFiveDenomId;
	}

	public void setPointZeroZeroFiveDenomId(BigDecimal pointZeroZeroFiveDenomId) {
		this.pointZeroZeroFiveDenomId = pointZeroZeroFiveDenomId;
	}

	public BigDecimal getPointZeroZeroOneDenomId() {
		return pointZeroZeroOneDenomId;
	}

	public void setPointZeroZeroOneDenomId(BigDecimal pointZeroZeroOneDenomId) {
		this.pointZeroZeroOneDenomId = pointZeroZeroOneDenomId;
	}

	public BigDecimal getTwoHundred() {
		return twoHundred;
	}

	public void setTwoHundred(BigDecimal twoHundred) {
		this.twoHundred = twoHundred;
	}

	public BigDecimal getTwoHundreds() {
		return twoHundreds;
	}

	public void setTwoHundreds(BigDecimal twoHundreds) {
		this.twoHundreds = twoHundreds;
	}

	public BigDecimal getTwoHundredAdj() {
		return twoHundredAdj;
	}

	public void setTwoHundredAdj(BigDecimal twoHundredAdj) {
		this.twoHundredAdj = twoHundredAdj;
	}

	public BigDecimal getTwoHundredDenomId() {
		return twoHundredDenomId;
	}

	public void setTwoHundredDenomId(BigDecimal twoHundredDenomId) {
		this.twoHundredDenomId = twoHundredDenomId;
	}

	public Boolean getBooOneLakhs() {
		return booOneLakhs;
	}

	public void setBooOneLakhs(Boolean booOneLakhs) {
		this.booOneLakhs = booOneLakhs;
	}

	public Boolean getBooFiftyThousands() {
		return booFiftyThousands;
	}

	public void setBooFiftyThousands(Boolean booFiftyThousands) {
		this.booFiftyThousands = booFiftyThousands;
	}

	public Boolean getBooTwentyThousands() {
		return booTwentyThousands;
	}

	public void setBooTwentyThousands(Boolean booTwentyThousands) {
		this.booTwentyThousands = booTwentyThousands;
	}

	public Boolean getBooTenThousands() {
		return booTenThousands;
	}

	public void setBooTenThousands(Boolean booTenThousands) {
		this.booTenThousands = booTenThousands;
	}

	public Boolean getBooFiveThousands() {
		return booFiveThousands;
	}

	public void setBooFiveThousands(Boolean booFiveThousands) {
		this.booFiveThousands = booFiveThousands;
	}

	public Boolean getBooTwoThousands() {
		return booTwoThousands;
	}

	public void setBooTwoThousands(Boolean booTwoThousands) {
		this.booTwoThousands = booTwoThousands;
	}

	public Boolean getBooThousands() {
		return booThousands;
	}

	public void setBooThousands(Boolean booThousands) {
		this.booThousands = booThousands;
	}

	public Boolean getBooFiveHundreds() {
		return booFiveHundreds;
	}

	public void setBooFiveHundreds(Boolean booFiveHundreds) {
		this.booFiveHundreds = booFiveHundreds;
	}

	public Boolean getBooTwoHundreds() {
		return booTwoHundreds;
	}

	public void setBooTwoHundreds(Boolean booTwoHundreds) {
		this.booTwoHundreds = booTwoHundreds;
	}

	public Boolean getBooHundreds() {
		return booHundreds;
	}

	public void setBooHundreds(Boolean booHundreds) {
		this.booHundreds = booHundreds;
	}

	public Boolean getBooFiftys() {
		return booFiftys;
	}

	public void setBooFiftys(Boolean booFiftys) {
		this.booFiftys = booFiftys;
	}

	public Boolean getBooTwentys() {
		return booTwentys;
	}

	public void setBooTwentys(Boolean booTwentys) {
		this.booTwentys = booTwentys;
	}

	public Boolean getBooTens() {
		return booTens;
	}

	public void setBooTens(Boolean booTens) {
		this.booTens = booTens;
	}

	public Boolean getBooFives() {
		return booFives;
	}

	public void setBooFives(Boolean booFives) {
		this.booFives = booFives;
	}

	public Boolean getBooTwos() {
		return booTwos;
	}

	public void setBooTwos(Boolean booTwos) {
		this.booTwos = booTwos;
	}

	public Boolean getBooOnes() {
		return booOnes;
	}

	public void setBooOnes(Boolean booOnes) {
		this.booOnes = booOnes;
	}

	public Boolean getBooPointFives() {
		return booPointFives;
	}

	public void setBooPointFives(Boolean booPointFives) {
		this.booPointFives = booPointFives;
	}

	public Boolean getBooPointTwoFives() {
		return booPointTwoFives;
	}

	public void setBooPointTwoFives(Boolean booPointTwoFives) {
		this.booPointTwoFives = booPointTwoFives;
	}

	public Boolean getBooPointOnes() {
		return booPointOnes;
	}

	public void setBooPointOnes(Boolean booPointOnes) {
		this.booPointOnes = booPointOnes;
	}

	public Boolean getBooPointZeroFives() {
		return booPointZeroFives;
	}

	public void setBooPointZeroFives(Boolean booPointZeroFives) {
		this.booPointZeroFives = booPointZeroFives;
	}

	public Boolean getBooPointZeroTwos() {
		return booPointZeroTwos;
	}

	public void setBooPointZeroTwos(Boolean booPointZeroTwos) {
		this.booPointZeroTwos = booPointZeroTwos;
	}

	public Boolean getBooPointZeroOnes() {
		return booPointZeroOnes;
	}

	public void setBooPointZeroOnes(Boolean booPointZeroOnes) {
		this.booPointZeroOnes = booPointZeroOnes;
	}

	public Boolean getBooPointZeroZeroFives() {
		return booPointZeroZeroFives;
	}

	public void setBooPointZeroZeroFives(Boolean booPointZeroZeroFives) {
		this.booPointZeroZeroFives = booPointZeroZeroFives;
	}

	public Boolean getBooPointZeroZeroOnes() {
		return booPointZeroZeroOnes;
	}

	public void setBooPointZeroZeroOnes(Boolean booPointZeroZeroOnes) {
		this.booPointZeroZeroOnes = booPointZeroZeroOnes;
	}

	public BigDecimal getOneLakhDenomAmount() {
		return oneLakhDenomAmount;
	}

	public void setOneLakhDenomAmount(BigDecimal oneLakhDenomAmount) {
		this.oneLakhDenomAmount = oneLakhDenomAmount;
	}

	public BigDecimal getThousandDenomAmount() {
		return thousandDenomAmount;
	}

	public void setThousandDenomAmount(BigDecimal thousandDenomAmount) {
		this.thousandDenomAmount = thousandDenomAmount;
	}

	public BigDecimal getFiftyThousandDenomAmount() {
		return fiftyThousandDenomAmount;
	}

	public void setFiftyThousandDenomAmount(BigDecimal fiftyThousandDenomAmount) {
		this.fiftyThousandDenomAmount = fiftyThousandDenomAmount;
	}

	public BigDecimal getTwentyThousandDenomAmount() {
		return twentyThousandDenomAmount;
	}

	public void setTwentyThousandDenomAmount(
			BigDecimal twentyThousandDenomAmount) {
		this.twentyThousandDenomAmount = twentyThousandDenomAmount;
	}

	public BigDecimal getTenThousandDenomAmount() {
		return tenThousandDenomAmount;
	}

	public void setTenThousandDenomAmount(BigDecimal tenThousandDenomAmount) {
		this.tenThousandDenomAmount = tenThousandDenomAmount;
	}

	public BigDecimal getFiveThousandDenomAmount() {
		return fiveThousandDenomAmount;
	}

	public void setFiveThousandDenomAmount(BigDecimal fiveThousandDenomAmount) {
		this.fiveThousandDenomAmount = fiveThousandDenomAmount;
	}

	public BigDecimal getTwoThousandDenomAmount() {
		return twoThousandDenomAmount;
	}

	public void setTwoThousandDenomAmount(BigDecimal twoThousandDenomAmount) {
		this.twoThousandDenomAmount = twoThousandDenomAmount;
	}

	public BigDecimal getFiveHundredDenomAmount() {
		return fiveHundredDenomAmount;
	}

	public void setFiveHundredDenomAmount(BigDecimal fiveHundredDenomAmount) {
		this.fiveHundredDenomAmount = fiveHundredDenomAmount;
	}

	public BigDecimal getTwoHundredDenomAmount() {
		return twoHundredDenomAmount;
	}

	public void setTwoHundredDenomAmount(BigDecimal twoHundredDenomAmount) {
		this.twoHundredDenomAmount = twoHundredDenomAmount;
	}

	public BigDecimal getHundredDenomAmount() {
		return hundredDenomAmount;
	}

	public void setHundredDenomAmount(BigDecimal hundredDenomAmount) {
		this.hundredDenomAmount = hundredDenomAmount;
	}

	public BigDecimal getFiftyDenomAmount() {
		return fiftyDenomAmount;
	}

	public void setFiftyDenomAmount(BigDecimal fiftyDenomAmount) {
		this.fiftyDenomAmount = fiftyDenomAmount;
	}

	public BigDecimal getTwentyDenomAmount() {
		return twentyDenomAmount;
	}

	public void setTwentyDenomAmount(BigDecimal twentyDenomAmount) {
		this.twentyDenomAmount = twentyDenomAmount;
	}

	public BigDecimal getTenDenomAmount() {
		return tenDenomAmount;
	}

	public void setTenDenomAmount(BigDecimal tenDenomAmount) {
		this.tenDenomAmount = tenDenomAmount;
	}

	public BigDecimal getFiveDenomAmount() {
		return fiveDenomAmount;
	}

	public void setFiveDenomAmount(BigDecimal fiveDenomAmount) {
		this.fiveDenomAmount = fiveDenomAmount;
	}

	public BigDecimal getTwoDenomAmount() {
		return twoDenomAmount;
	}

	public void setTwoDenomAmount(BigDecimal twoDenomAmount) {
		this.twoDenomAmount = twoDenomAmount;
	}

	public BigDecimal getOneDenomAmount() {
		return oneDenomAmount;
	}

	public void setOneDenomAmount(BigDecimal oneDenomAmount) {
		this.oneDenomAmount = oneDenomAmount;
	}

	public BigDecimal getPointFiveDenomAmount() {
		return pointFiveDenomAmount;
	}

	public void setPointFiveDenomAmount(BigDecimal pointFiveDenomAmount) {
		this.pointFiveDenomAmount = pointFiveDenomAmount;
	}

	public BigDecimal getPointTwoFiveDenomAmount() {
		return pointTwoFiveDenomAmount;
	}

	public void setPointTwoFiveDenomAmount(BigDecimal pointTwoFiveDenomAmount) {
		this.pointTwoFiveDenomAmount = pointTwoFiveDenomAmount;
	}

	public BigDecimal getPointOneDenomAmount() {
		return pointOneDenomAmount;
	}

	public void setPointOneDenomAmount(BigDecimal pointOneDenomAmount) {
		this.pointOneDenomAmount = pointOneDenomAmount;
	}

	public BigDecimal getPointZeroFiveDenomAmount() {
		return pointZeroFiveDenomAmount;
	}

	public void setPointZeroFiveDenomAmount(BigDecimal pointZeroFiveDenomAmount) {
		this.pointZeroFiveDenomAmount = pointZeroFiveDenomAmount;
	}

	public BigDecimal getPointZeroTwoDenomAmount() {
		return pointZeroTwoDenomAmount;
	}

	public void setPointZeroTwoDenomAmount(BigDecimal pointZeroTwoDenomAmount) {
		this.pointZeroTwoDenomAmount = pointZeroTwoDenomAmount;
	}

	public BigDecimal getPointZeroOneDenomAmount() {
		return pointZeroOneDenomAmount;
	}

	public void setPointZeroOneDenomAmount(BigDecimal pointZeroOneDenomAmount) {
		this.pointZeroOneDenomAmount = pointZeroOneDenomAmount;
	}

	public BigDecimal getPointZeroZeroFiveDenomAmount() {
		return pointZeroZeroFiveDenomAmount;
	}

	public void setPointZeroZeroFiveDenomAmount(
			BigDecimal pointZeroZeroFiveDenomAmount) {
		this.pointZeroZeroFiveDenomAmount = pointZeroZeroFiveDenomAmount;
	}

	public BigDecimal getPointZeroZeroOneDenomAmount() {
		return pointZeroZeroOneDenomAmount;
	}

	public void setPointZeroZeroOneDenomAmount(
			BigDecimal pointZeroZeroOneDenomAmount) {
		this.pointZeroZeroOneDenomAmount = pointZeroZeroOneDenomAmount;
	}

	public BigDecimal getOneLakhPNotes() {
		return oneLakhPNotes;
	}

	public void setOneLakhPNotes(BigDecimal oneLakhPNotes) {
		this.oneLakhPNotes = oneLakhPNotes;
	}

	public BigDecimal getFiftyThousandPNotes() {
		return fiftyThousandPNotes;
	}

	public void setFiftyThousandPNotes(BigDecimal fiftyThousandPNotes) {
		this.fiftyThousandPNotes = fiftyThousandPNotes;
	}

	public BigDecimal getTwentyThousandPNotes() {
		return twentyThousandPNotes;
	}

	public void setTwentyThousandPNotes(BigDecimal twentyThousandPNotes) {
		this.twentyThousandPNotes = twentyThousandPNotes;
	}

	public BigDecimal getTenThousandPNotes() {
		return tenThousandPNotes;
	}

	public void setTenThousandPNotes(BigDecimal tenThousandPNotes) {
		this.tenThousandPNotes = tenThousandPNotes;
	}

	public BigDecimal getFiveThousandPNotes() {
		return fiveThousandPNotes;
	}

	public void setFiveThousandPNotes(BigDecimal fiveThousandPNotes) {
		this.fiveThousandPNotes = fiveThousandPNotes;
	}

	public BigDecimal getTwoThousandPNotes() {
		return twoThousandPNotes;
	}

	public void setTwoThousandPNotes(BigDecimal twoThousandPNotes) {
		this.twoThousandPNotes = twoThousandPNotes;
	}

	public BigDecimal getThousandPNotes() {
		return thousandPNotes;
	}

	public void setThousandPNotes(BigDecimal thousandPNotes) {
		this.thousandPNotes = thousandPNotes;
	}

	public BigDecimal getFiveHundredPNotes() {
		return fiveHundredPNotes;
	}

	public void setFiveHundredPNotes(BigDecimal fiveHundredPNotes) {
		this.fiveHundredPNotes = fiveHundredPNotes;
	}

	public BigDecimal getTwoHundredPNotes() {
		return twoHundredPNotes;
	}

	public void setTwoHundredPNotes(BigDecimal twoHundredPNotes) {
		this.twoHundredPNotes = twoHundredPNotes;
	}

	public BigDecimal getHundredPNotes() {
		return hundredPNotes;
	}

	public void setHundredPNotes(BigDecimal hundredPNotes) {
		this.hundredPNotes = hundredPNotes;
	}

	public BigDecimal getFiftyPNotes() {
		return fiftyPNotes;
	}

	public void setFiftyPNotes(BigDecimal fiftyPNotes) {
		this.fiftyPNotes = fiftyPNotes;
	}

	public BigDecimal getTwentyPNotes() {
		return twentyPNotes;
	}

	public void setTwentyPNotes(BigDecimal twentyPNotes) {
		this.twentyPNotes = twentyPNotes;
	}

	public BigDecimal getTenPNotes() {
		return tenPNotes;
	}

	public void setTenPNotes(BigDecimal tenPNotes) {
		this.tenPNotes = tenPNotes;
	}

	public BigDecimal getFivePNotes() {
		return fivePNotes;
	}

	public void setFivePNotes(BigDecimal fivePNotes) {
		this.fivePNotes = fivePNotes;
	}

	public BigDecimal getTwoPNotes() {
		return twoPNotes;
	}

	public void setTwoPNotes(BigDecimal twoPNotes) {
		this.twoPNotes = twoPNotes;
	}

	public BigDecimal getOnePNotes() {
		return onePNotes;
	}

	public void setOnePNotes(BigDecimal onePNotes) {
		this.onePNotes = onePNotes;
	}

	public BigDecimal getPointFivePNotes() {
		return pointFivePNotes;
	}

	public void setPointFivePNotes(BigDecimal pointFivePNotes) {
		this.pointFivePNotes = pointFivePNotes;
	}

	public BigDecimal getPointTwoFivePNotes() {
		return pointTwoFivePNotes;
	}

	public void setPointTwoFivePNotes(BigDecimal pointTwoFivePNotes) {
		this.pointTwoFivePNotes = pointTwoFivePNotes;
	}

	public BigDecimal getPointOnePNotes() {
		return pointOnePNotes;
	}

	public void setPointOnePNotes(BigDecimal pointOnePNotes) {
		this.pointOnePNotes = pointOnePNotes;
	}

	public BigDecimal getPointZeroFivePNotes() {
		return pointZeroFivePNotes;
	}

	public void setPointZeroFivePNotes(BigDecimal pointZeroFivePNotes) {
		this.pointZeroFivePNotes = pointZeroFivePNotes;
	}

	public BigDecimal getPointZeroTwoPNotes() {
		return pointZeroTwoPNotes;
	}

	public void setPointZeroTwoPNotes(BigDecimal pointZeroTwoPNotes) {
		this.pointZeroTwoPNotes = pointZeroTwoPNotes;
	}

	public BigDecimal getPointZeroOnePNotes() {
		return pointZeroOnePNotes;
	}

	public void setPointZeroOnePNotes(BigDecimal pointZeroOnePNotes) {
		this.pointZeroOnePNotes = pointZeroOnePNotes;
	}

	public BigDecimal getPointZeroZeroFivePNotes() {
		return pointZeroZeroFivePNotes;
	}

	public void setPointZeroZeroFivePNotes(BigDecimal pointZeroZeroFivePNotes) {
		this.pointZeroZeroFivePNotes = pointZeroZeroFivePNotes;
	}

	public BigDecimal getPointZeroZeroOnePNotes() {
		return pointZeroZeroOnePNotes;
	}

	public void setPointZeroZeroOnePNotes(BigDecimal pointZeroZeroOnePNotes) {
		this.pointZeroZeroOnePNotes = pointZeroZeroOnePNotes;
	}

	public BigDecimal getPhysicalNoteTotal() {
		return physicalNoteTotal;
	}

	public void setPhysicalNoteTotal(BigDecimal physicalNoteTotal) {
		this.physicalNoteTotal = physicalNoteTotal;
	}

	public BigDecimal getSystemNoteTotal() {
		return systemNoteTotal;
	}

	public void setSystemNoteTotal(BigDecimal systemNoteTotal) {
		this.systemNoteTotal = systemNoteTotal;
	}

	public BigDecimal getPhysicalAmountTotal() {
		return physicalAmountTotal;
	}

	public void setPhysicalAmountTotal(BigDecimal physicalAmountTotal) {
		this.physicalAmountTotal = physicalAmountTotal;
	}

	public BigDecimal getSystemAmountTotal() {
		return systemAmountTotal;
	}

	public void setSystemAmountTotal(BigDecimal systemAmountTotal) {
		this.systemAmountTotal = systemAmountTotal;
	}

	public BigDecimal getDiffAmountTotal() {
		return diffAmountTotal;
	}

	public void setDiffAmountTotal(BigDecimal diffAmountTotal) {
		this.diffAmountTotal = diffAmountTotal;
	}

	// Adjusment

}

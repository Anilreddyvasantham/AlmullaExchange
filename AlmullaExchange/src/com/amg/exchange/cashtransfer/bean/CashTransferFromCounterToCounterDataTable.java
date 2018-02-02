package com.amg.exchange.cashtransfer.bean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CashTransferFromCounterToCounterDataTable {

	private static final long serialVersionUID = 1L;
	private BigDecimal curencyId;
	private String currencyName;
	private BigDecimal thousands;
	private BigDecimal fiveHundreds;
	private BigDecimal twoHundreds;
	private BigDecimal hundreds;
	private BigDecimal fifties;
	private BigDecimal twenties;
	private BigDecimal tens;
	private BigDecimal fives;
	private BigDecimal ones;
	private BigDecimal pointfives;
	private BigDecimal pointTwoFives;
	private BigDecimal pointOnes;
	private BigDecimal pointZeroFives;
	private BigDecimal totalTransfer;
	
	//for input fields
	private BigDecimal inputThousands;
	private BigDecimal inputFiveHundreds;
	private BigDecimal inputTwoHundreds;
	private BigDecimal inputHundreds;
	private BigDecimal inputFifties;
	private BigDecimal inputTwenties;
	private BigDecimal inputTens;
	private BigDecimal inputFives;
	private BigDecimal inputOnes;
	private BigDecimal inputPointfives;
	private BigDecimal inputPointTwoFives;
	private BigDecimal inputPointOnes;
	private BigDecimal inputPointZeroFives;
	private BigDecimal inputTotalTransfer;
	
	
	//for denominationId 
	private BigDecimal thousandsDeniminationId;
	private BigDecimal fiveHundredsDeniminationId;
	private BigDecimal twoHundredsDeniminationId;
	private BigDecimal hundredsDeniminationId;
	private BigDecimal fiftiesDeniminationId;
	private BigDecimal twentiesDeniminationId;
	private BigDecimal tensDeniminationId;
	private BigDecimal fivesDeniminationId;
	private BigDecimal onesDeniminationId;
	private BigDecimal pointfivesDeniminationId;
	private BigDecimal pointTwoFivesDeniminationId;
	private BigDecimal pointOnesDeniminationId;
	private BigDecimal pointZeroFivesDeniminationId;
	
	//for calculating foreign currency adjustment amount
	private BigDecimal inputThousandsAdjAmount;
	private BigDecimal inputFiveHundredsAdjAmount;
	private BigDecimal inputTwoHundredsAdjAmount;
	private BigDecimal inputHundredsAdjAmount;
	private BigDecimal inputFiftiesAdjAmount;
	private BigDecimal inputTwentiesAdjAmount;
	private BigDecimal inputTensAdjAmount;
	private BigDecimal inputFivesAdjAmount;
	private BigDecimal inputOnesAdjAmount;
	private BigDecimal inputPointfivesAdjAmount;
	private BigDecimal inputPointTwoFivesAdjAmount;
	private BigDecimal inputPointOnesAdjAmount;
	private BigDecimal inputPointZeroFivesAdjAmount;
	
	
	// puting all data table input fields into map
	private Map<BigDecimal,BigDecimal> thousandMap=new HashMap<BigDecimal,BigDecimal>();
	private Map<BigDecimal,BigDecimal> fiveHundredMap=new HashMap<BigDecimal,BigDecimal>();
	private Map<BigDecimal,BigDecimal> twoHundredMap=new HashMap<BigDecimal,BigDecimal>();
	private Map<BigDecimal,BigDecimal> hundredMap=new HashMap<BigDecimal,BigDecimal>();
	private Map<BigDecimal,BigDecimal> fiftieMap=new HashMap<BigDecimal,BigDecimal>();
	private Map<BigDecimal,BigDecimal> twentieMap=new HashMap<BigDecimal,BigDecimal>();
	private Map<BigDecimal,BigDecimal> tenMap=new HashMap<BigDecimal,BigDecimal>();
	private Map<BigDecimal,BigDecimal> fiveMap=new HashMap<BigDecimal,BigDecimal>();
	private Map<BigDecimal,BigDecimal> oneMap=new HashMap<BigDecimal,BigDecimal>();
	private Map<BigDecimal,BigDecimal> pointfiveMap=new HashMap<BigDecimal,BigDecimal>();
	private Map<BigDecimal,BigDecimal> pointTwoFiveMap=new HashMap<BigDecimal,BigDecimal>();
	private Map<BigDecimal,BigDecimal> pointOneMap=new HashMap<BigDecimal,BigDecimal>();
	private Map<BigDecimal,BigDecimal> pointZeroFiveMap=new HashMap<BigDecimal,BigDecimal>();
	


	public CashTransferFromCounterToCounterDataTable(BigDecimal curencyId,
			String currencyName, BigDecimal thousands, BigDecimal fiveHundreds,
			BigDecimal twoHundreds, BigDecimal hundreds, BigDecimal fifties,
			BigDecimal twenties, BigDecimal tens, BigDecimal fives,
			BigDecimal ones, BigDecimal pointfives, BigDecimal pointTwoFives,
			BigDecimal pointOnes, BigDecimal pointZeroFives,
			BigDecimal totalTransfer, BigDecimal inputThousands,
			BigDecimal inputFiveHundreds, BigDecimal inputTwoHundreds,
			BigDecimal inputHundreds, BigDecimal inputFifties,
			BigDecimal inputTwenties, BigDecimal inputTens,
			BigDecimal inputFives, BigDecimal inputOnes,
			BigDecimal inputPointfives, BigDecimal inputPointTwoFives,
			BigDecimal inputPointOnes, BigDecimal inputPointZeroFives,
			BigDecimal inputTotalTransfer, BigDecimal thousandsDeniminationId,
			BigDecimal fiveHundredsDeniminationId,
			BigDecimal twoHundredsDeniminationId,
			BigDecimal hundredsDeniminationId,
			BigDecimal fiftiesDeniminationId,
			BigDecimal twentiesDeniminationId, BigDecimal tensDeniminationId,
			BigDecimal fivesDeniminationId, BigDecimal onesDeniminationId,
			BigDecimal pointfivesDeniminationId,
			BigDecimal pointTwoFivesDeniminationId,
			BigDecimal pointOnesDeniminationId,
			BigDecimal pointZeroFivesDeniminationId,
			BigDecimal inputThousandsAdjAmount,
			BigDecimal inputFiveHundredsAdjAmount,
			BigDecimal inputTwoHundredsAdjAmount,
			BigDecimal inputHundredsAdjAmount,
			BigDecimal inputFiftiesAdjAmount,
			BigDecimal inputTwentiesAdjAmount, BigDecimal inputTensAdjAmount,
			BigDecimal inputFivesAdjAmount, BigDecimal inputOnesAdjAmount,
			BigDecimal inputPointfivesAdjAmount,
			BigDecimal inputPointTwoFivesAdjAmount,
			BigDecimal inputPointOnesAdjAmount,
			BigDecimal inputPointZeroFivesAdjAmount,
			Map<BigDecimal, BigDecimal> thousandMap,
			Map<BigDecimal, BigDecimal> fiveHundredMap,
			Map<BigDecimal, BigDecimal> twoHundredMap,
			Map<BigDecimal, BigDecimal> hundredMap,
			Map<BigDecimal, BigDecimal> fiftieMap,
			Map<BigDecimal, BigDecimal> twentieMap,
			Map<BigDecimal, BigDecimal> tenMap,
			Map<BigDecimal, BigDecimal> fiveMap,
			Map<BigDecimal, BigDecimal> oneMap,
			Map<BigDecimal, BigDecimal> pointfiveMap,
			Map<BigDecimal, BigDecimal> pointTwoFiveMap,
			Map<BigDecimal, BigDecimal> pointOneMap,
			Map<BigDecimal, BigDecimal> pointZeroFiveMap) {
		super();
		this.curencyId = curencyId;
		this.currencyName = currencyName;
		this.thousands = thousands;
		this.fiveHundreds = fiveHundreds;
		this.twoHundreds = twoHundreds;
		this.hundreds = hundreds;
		this.fifties = fifties;
		this.twenties = twenties;
		this.tens = tens;
		this.fives = fives;
		this.ones = ones;
		this.pointfives = pointfives;
		this.pointTwoFives = pointTwoFives;
		this.pointOnes = pointOnes;
		this.pointZeroFives = pointZeroFives;
		this.totalTransfer = totalTransfer;
		this.inputThousands = inputThousands;
		this.inputFiveHundreds = inputFiveHundreds;
		this.inputTwoHundreds = inputTwoHundreds;
		this.inputHundreds = inputHundreds;
		this.inputFifties = inputFifties;
		this.inputTwenties = inputTwenties;
		this.inputTens = inputTens;
		this.inputFives = inputFives;
		this.inputOnes = inputOnes;
		this.inputPointfives = inputPointfives;
		this.inputPointTwoFives = inputPointTwoFives;
		this.inputPointOnes = inputPointOnes;
		this.inputPointZeroFives = inputPointZeroFives;
		this.inputTotalTransfer = inputTotalTransfer;
		this.thousandsDeniminationId = thousandsDeniminationId;
		this.fiveHundredsDeniminationId = fiveHundredsDeniminationId;
		this.twoHundredsDeniminationId = twoHundredsDeniminationId;
		this.hundredsDeniminationId = hundredsDeniminationId;
		this.fiftiesDeniminationId = fiftiesDeniminationId;
		this.twentiesDeniminationId = twentiesDeniminationId;
		this.tensDeniminationId = tensDeniminationId;
		this.fivesDeniminationId = fivesDeniminationId;
		this.onesDeniminationId = onesDeniminationId;
		this.pointfivesDeniminationId = pointfivesDeniminationId;
		this.pointTwoFivesDeniminationId = pointTwoFivesDeniminationId;
		this.pointOnesDeniminationId = pointOnesDeniminationId;
		this.pointZeroFivesDeniminationId = pointZeroFivesDeniminationId;
		this.inputThousandsAdjAmount = inputThousandsAdjAmount;
		this.inputFiveHundredsAdjAmount = inputFiveHundredsAdjAmount;
		this.inputTwoHundredsAdjAmount = inputTwoHundredsAdjAmount;
		this.inputHundredsAdjAmount = inputHundredsAdjAmount;
		this.inputFiftiesAdjAmount = inputFiftiesAdjAmount;
		this.inputTwentiesAdjAmount = inputTwentiesAdjAmount;
		this.inputTensAdjAmount = inputTensAdjAmount;
		this.inputFivesAdjAmount = inputFivesAdjAmount;
		this.inputOnesAdjAmount = inputOnesAdjAmount;
		this.inputPointfivesAdjAmount = inputPointfivesAdjAmount;
		this.inputPointTwoFivesAdjAmount = inputPointTwoFivesAdjAmount;
		this.inputPointOnesAdjAmount = inputPointOnesAdjAmount;
		this.inputPointZeroFivesAdjAmount = inputPointZeroFivesAdjAmount;
		this.thousandMap = thousandMap;
		this.fiveHundredMap = fiveHundredMap;
		this.twoHundredMap = twoHundredMap;
		this.hundredMap = hundredMap;
		this.fiftieMap = fiftieMap;
		this.twentieMap = twentieMap;
		this.tenMap = tenMap;
		this.fiveMap = fiveMap;
		this.oneMap = oneMap;
		this.pointfiveMap = pointfiveMap;
		this.pointTwoFiveMap = pointTwoFiveMap;
		this.pointOneMap = pointOneMap;
		this.pointZeroFiveMap = pointZeroFiveMap;
	}

	public CashTransferFromCounterToCounterDataTable() {
	}

	
	public BigDecimal getThousandsDeniminationId() {
		return thousandsDeniminationId;
	}

	public void setThousandsDeniminationId(BigDecimal thousandsDeniminationId) {
		this.thousandsDeniminationId = thousandsDeniminationId;
	}

	public BigDecimal getFiveHundredsDeniminationId() {
		return fiveHundredsDeniminationId;
	}

	public void setFiveHundredsDeniminationId(BigDecimal fiveHundredsDeniminationId) {
		this.fiveHundredsDeniminationId = fiveHundredsDeniminationId;
	}

	public BigDecimal getTwoHundredsDeniminationId() {
		return twoHundredsDeniminationId;
	}

	public void setTwoHundredsDeniminationId(BigDecimal twoHundredsDeniminationId) {
		this.twoHundredsDeniminationId = twoHundredsDeniminationId;
	}

	public BigDecimal getHundredsDeniminationId() {
		return hundredsDeniminationId;
	}

	public void setHundredsDeniminationId(BigDecimal hundredsDeniminationId) {
		this.hundredsDeniminationId = hundredsDeniminationId;
	}

	public BigDecimal getFiftiesDeniminationId() {
		return fiftiesDeniminationId;
	}

	public void setFiftiesDeniminationId(BigDecimal fiftiesDeniminationId) {
		this.fiftiesDeniminationId = fiftiesDeniminationId;
	}

	public BigDecimal getTwentiesDeniminationId() {
		return twentiesDeniminationId;
	}

	public void setTwentiesDeniminationId(BigDecimal twentiesDeniminationId) {
		this.twentiesDeniminationId = twentiesDeniminationId;
	}

	public BigDecimal getTensDeniminationId() {
		return tensDeniminationId;
	}

	public void setTensDeniminationId(BigDecimal tensDeniminationId) {
		this.tensDeniminationId = tensDeniminationId;
	}

	public BigDecimal getFivesDeniminationId() {
		return fivesDeniminationId;
	}

	public void setFivesDeniminationId(BigDecimal fivesDeniminationId) {
		this.fivesDeniminationId = fivesDeniminationId;
	}

	public BigDecimal getOnesDeniminationId() {
		return onesDeniminationId;
	}

	public void setOnesDeniminationId(BigDecimal onesDeniminationId) {
		this.onesDeniminationId = onesDeniminationId;
	}

	public BigDecimal getPointfivesDeniminationId() {
		return pointfivesDeniminationId;
	}

	public void setPointfivesDeniminationId(BigDecimal pointfivesDeniminationId) {
		this.pointfivesDeniminationId = pointfivesDeniminationId;
	}

	public BigDecimal getPointTwoFivesDeniminationId() {
		return pointTwoFivesDeniminationId;
	}

	public void setPointTwoFivesDeniminationId(
			BigDecimal pointTwoFivesDeniminationId) {
		this.pointTwoFivesDeniminationId = pointTwoFivesDeniminationId;
	}

	public BigDecimal getPointOnesDeniminationId() {
		return pointOnesDeniminationId;
	}

	public void setPointOnesDeniminationId(BigDecimal pointOnesDeniminationId) {
		this.pointOnesDeniminationId = pointOnesDeniminationId;
	}

	public BigDecimal getPointZeroFivesDeniminationId() {
		return pointZeroFivesDeniminationId;
	}

	public void setPointZeroFivesDeniminationId(
			BigDecimal pointZeroFivesDeniminationId) {
		this.pointZeroFivesDeniminationId = pointZeroFivesDeniminationId;
	}

	public BigDecimal getInputThousandsAdjAmount() {
		return inputThousandsAdjAmount;
	}



	public void setInputThousandsAdjAmount(BigDecimal inputThousandsAdjAmount) {
		this.inputThousandsAdjAmount = inputThousandsAdjAmount;
	}



	public BigDecimal getInputFiveHundredsAdjAmount() {
		return inputFiveHundredsAdjAmount;
	}



	public void setInputFiveHundredsAdjAmount(BigDecimal inputFiveHundredsAdjAmount) {
		this.inputFiveHundredsAdjAmount = inputFiveHundredsAdjAmount;
	}



	public BigDecimal getInputTwoHundredsAdjAmount() {
		return inputTwoHundredsAdjAmount;
	}



	public void setInputTwoHundredsAdjAmount(BigDecimal inputTwoHundredsAdjAmount) {
		this.inputTwoHundredsAdjAmount = inputTwoHundredsAdjAmount;
	}



	public BigDecimal getInputHundredsAdjAmount() {
		return inputHundredsAdjAmount;
	}



	public void setInputHundredsAdjAmount(BigDecimal inputHundredsAdjAmount) {
		this.inputHundredsAdjAmount = inputHundredsAdjAmount;
	}



	public BigDecimal getInputFiftiesAdjAmount() {
		return inputFiftiesAdjAmount;
	}



	public void setInputFiftiesAdjAmount(BigDecimal inputFiftiesAdjAmount) {
		this.inputFiftiesAdjAmount = inputFiftiesAdjAmount;
	}



	public BigDecimal getInputTwentiesAdjAmount() {
		return inputTwentiesAdjAmount;
	}



	public void setInputTwentiesAdjAmount(BigDecimal inputTwentiesAdjAmount) {
		this.inputTwentiesAdjAmount = inputTwentiesAdjAmount;
	}



	public BigDecimal getInputTensAdjAmount() {
		return inputTensAdjAmount;
	}



	public void setInputTensAdjAmount(BigDecimal inputTensAdjAmount) {
		this.inputTensAdjAmount = inputTensAdjAmount;
	}



	public BigDecimal getInputFivesAdjAmount() {
		return inputFivesAdjAmount;
	}



	public void setInputFivesAdjAmount(BigDecimal inputFivesAdjAmount) {
		this.inputFivesAdjAmount = inputFivesAdjAmount;
	}



	public BigDecimal getInputOnesAdjAmount() {
		return inputOnesAdjAmount;
	}



	public void setInputOnesAdjAmount(BigDecimal inputOnesAdjAmount) {
		this.inputOnesAdjAmount = inputOnesAdjAmount;
	}



	public BigDecimal getInputPointfivesAdjAmount() {
		return inputPointfivesAdjAmount;
	}



	public void setInputPointfivesAdjAmount(BigDecimal inputPointfivesAdjAmount) {
		this.inputPointfivesAdjAmount = inputPointfivesAdjAmount;
	}



	public BigDecimal getInputPointTwoFivesAdjAmount() {
		return inputPointTwoFivesAdjAmount;
	}



	public void setInputPointTwoFivesAdjAmount(
			BigDecimal inputPointTwoFivesAdjAmount) {
		this.inputPointTwoFivesAdjAmount = inputPointTwoFivesAdjAmount;
	}



	public BigDecimal getInputPointOnesAdjAmount() {
		return inputPointOnesAdjAmount;
	}



	public void setInputPointOnesAdjAmount(BigDecimal inputPointOnesAdjAmount) {
		this.inputPointOnesAdjAmount = inputPointOnesAdjAmount;
	}



	public BigDecimal getInputPointZeroFivesAdjAmount() {
		return inputPointZeroFivesAdjAmount;
	}



	public void setInputPointZeroFivesAdjAmount(
			BigDecimal inputPointZeroFivesAdjAmount) {
		this.inputPointZeroFivesAdjAmount = inputPointZeroFivesAdjAmount;
	}


	public Map<BigDecimal, BigDecimal> getThousandMap() {
		return thousandMap;
	}


	public void setThousandMap(Map<BigDecimal, BigDecimal> thousandMap) {
		this.thousandMap = thousandMap;
	}



	public Map<BigDecimal, BigDecimal> getFiveHundredMap() {
		return fiveHundredMap;
	}



	public void setFiveHundredMap(Map<BigDecimal, BigDecimal> fiveHundredMap) {
		this.fiveHundredMap = fiveHundredMap;
	}



	public Map<BigDecimal, BigDecimal> getTwoHundredMap() {
		return twoHundredMap;
	}


	public void setTwoHundredMap(Map<BigDecimal, BigDecimal> twoHundredMap) {
		this.twoHundredMap = twoHundredMap;
	}

	public Map<BigDecimal, BigDecimal> getHundredMap() {
		return hundredMap;
	}

	public void setHundredMap(Map<BigDecimal, BigDecimal> hundredMap) {
		this.hundredMap = hundredMap;
	}

	public Map<BigDecimal, BigDecimal> getFiftieMap() {
		return fiftieMap;
	}

	public void setFiftieMap(Map<BigDecimal, BigDecimal> fiftieMap) {
		this.fiftieMap = fiftieMap;
	}

	public Map<BigDecimal, BigDecimal> getTwentieMap() {
		return twentieMap;
	}

	public void setTwentieMap(Map<BigDecimal, BigDecimal> twentieMap) {
		this.twentieMap = twentieMap;
	}


	public Map<BigDecimal, BigDecimal> getTenMap() {
		return tenMap;
	}

	public void setTenMap(Map<BigDecimal, BigDecimal> tenMap) {
		this.tenMap = tenMap;
	}

	public Map<BigDecimal, BigDecimal> getFiveMap() {
		return fiveMap;
	}


	public void setFiveMap(Map<BigDecimal, BigDecimal> fiveMap) {
		this.fiveMap = fiveMap;
	}

	public Map<BigDecimal, BigDecimal> getOneMap() {
		return oneMap;
	}

	public void setOneMap(Map<BigDecimal, BigDecimal> oneMap) {
		this.oneMap = oneMap;
	}

	public Map<BigDecimal, BigDecimal> getPointfiveMap() {
		return pointfiveMap;
	}


	public void setPointfiveMap(Map<BigDecimal, BigDecimal> pointfiveMap) {
		this.pointfiveMap = pointfiveMap;
	}


	public Map<BigDecimal, BigDecimal> getPointTwoFiveMap() {
		return pointTwoFiveMap;
	}


	public void setPointTwoFiveMap(Map<BigDecimal, BigDecimal> pointTwoFiveMap) {
		this.pointTwoFiveMap = pointTwoFiveMap;
	}

	public Map<BigDecimal, BigDecimal> getPointOneMap() {
		return pointOneMap;
	}

	public void setPointOneMap(Map<BigDecimal, BigDecimal> pointOneMap) {
		this.pointOneMap = pointOneMap;
	}


	public Map<BigDecimal, BigDecimal> getPointZeroFiveMap() {
		return pointZeroFiveMap;
	}


	public void setPointZeroFiveMap(Map<BigDecimal, BigDecimal> pointZeroFiveMap) {
		this.pointZeroFiveMap = pointZeroFiveMap;
	}


	public BigDecimal getCurencyId() {
		return curencyId;
	}
	public void setCurencyId(BigDecimal curencyId) {
		this.curencyId = curencyId;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
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
	public BigDecimal getTwoHundreds() {
		return twoHundreds;
	}
	public void setTwoHundreds(BigDecimal twoHundreds) {
		this.twoHundreds = twoHundreds;
	}
	public BigDecimal getHundreds() {
		return hundreds;
	}
	public void setHundreds(BigDecimal hundreds) {
		this.hundreds = hundreds;
	}
	public BigDecimal getFifties() {
		return fifties;
	}
	public void setFifties(BigDecimal fifties) {
		this.fifties = fifties;
	}
	public BigDecimal getTwenties() {
		return twenties;
	}
	public void setTwenties(BigDecimal twenties) {
		this.twenties = twenties;
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
	public BigDecimal getOnes() {
		return ones;
	}
	public void setOnes(BigDecimal ones) {
		this.ones = ones;
	}
	public BigDecimal getPointfives() {
		return pointfives;
	}
	public void setPointfives(BigDecimal pointfives) {
		this.pointfives = pointfives;
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
	public BigDecimal getTotalTransfer() {
		return totalTransfer;
	}
	public void setTotalTransfer(BigDecimal totalTransfer) {
		this.totalTransfer = totalTransfer;
	}
	public BigDecimal getInputThousands() {
		return inputThousands;
	}
	public void setInputThousands(BigDecimal inputThousands) {
		this.inputThousands = inputThousands;
	}
	public BigDecimal getInputFiveHundreds() {
		return inputFiveHundreds;
	}
	public void setInputFiveHundreds(BigDecimal inputFiveHundreds) {
		this.inputFiveHundreds = inputFiveHundreds;
	}
	public BigDecimal getInputTwoHundreds() {
		return inputTwoHundreds;
	}
	public void setInputTwoHundreds(BigDecimal inputTwoHundreds) {
		this.inputTwoHundreds = inputTwoHundreds;
	}
	public BigDecimal getInputHundreds() {
		return inputHundreds;
	}
	public void setInputHundreds(BigDecimal inputHundreds) {
		this.inputHundreds = inputHundreds;
	}
	public BigDecimal getInputFifties() {
		return inputFifties;
	}
	public void setInputFifties(BigDecimal inputFifties) {
		this.inputFifties = inputFifties;
	}
	public BigDecimal getInputTwenties() {
		return inputTwenties;
	}
	public void setInputTwenties(BigDecimal inputTwenties) {
		this.inputTwenties = inputTwenties;
	}
	public BigDecimal getInputTens() {
		return inputTens;
	}
	public void setInputTens(BigDecimal inputTens) {
		this.inputTens = inputTens;
	}
	public BigDecimal getInputFives() {
		return inputFives;
	}
	public void setInputFives(BigDecimal inputFives) {
		this.inputFives = inputFives;
	}
	public BigDecimal getInputOnes() {
		return inputOnes;
	}
	public void setInputOnes(BigDecimal inputOnes) {
		this.inputOnes = inputOnes;
	}
	public BigDecimal getInputPointfives() {
		return inputPointfives;
	}
	public void setInputPointfives(BigDecimal inputPointfives) {
		this.inputPointfives = inputPointfives;
	}
	public BigDecimal getInputPointTwoFives() {
		return inputPointTwoFives;
	}
	public void setInputPointTwoFives(BigDecimal inputPointTwoFives) {
		this.inputPointTwoFives = inputPointTwoFives;
	}
	public BigDecimal getInputPointOnes() {
		return inputPointOnes;
	}
	public void setInputPointOnes(BigDecimal inputPointOnes) {
		this.inputPointOnes = inputPointOnes;
	}
	public BigDecimal getInputPointZeroFives() {
		return inputPointZeroFives;
	}
	public void setInputPointZeroFives(BigDecimal inputPointZeroFives) {
		this.inputPointZeroFives = inputPointZeroFives;
	}
	public BigDecimal getInputTotalTransfer() {
		return inputTotalTransfer;
	}
	public void setInputTotalTransfer(BigDecimal inputTotalTransfer) {
		this.inputTotalTransfer = inputTotalTransfer;
	}
	

	
}

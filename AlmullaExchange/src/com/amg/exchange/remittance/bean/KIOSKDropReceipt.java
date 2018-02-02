package com.amg.exchange.remittance.bean;

import java.io.Serializable;

public class KIOSKDropReceipt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String deviceProductName;
	private String deviceSerialNumber;
	private String locationName;
	private String inputNumber;
	private String inputSubNumber;
	
	private String outputNumber;
	private String outputSubNumber;
	private String bagNumber;
	private String noteJam;
	private String tranAmount;
	
	private String dateTime;
	private String tranCycle;
	private String containerCycle;
	private String containerNumber;
	private String TotalNumberOfNotes;
	public String getDeviceProductName() {
		return deviceProductName;
	}
	public void setDeviceProductName(String deviceProductName) {
		this.deviceProductName = deviceProductName;
	}
	public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}
	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}
	
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getInputNumber() {
		return inputNumber;
	}
	public void setInputNumber(String inputNumber) {
		this.inputNumber = inputNumber;
	}
	public String getInputSubNumber() {
		return inputSubNumber;
	}
	public void setInputSubNumber(String inputSubNumber) {
		this.inputSubNumber = inputSubNumber;
	}
	public String getOutputNumber() {
		return outputNumber;
	}
	public void setOutputNumber(String outputNumber) {
		this.outputNumber = outputNumber;
	}
	public String getOutputSubNumber() {
		return outputSubNumber;
	}
	public void setOutputSubNumber(String outputSubNumber) {
		this.outputSubNumber = outputSubNumber;
	}
	public String getBagNumber() {
		return bagNumber;
	}
	public void setBagNumber(String bagNumber) {
		this.bagNumber = bagNumber;
	}
	public String getNoteJam() {
		return noteJam;
	}
	public void setNoteJam(String noteJam) {
		this.noteJam = noteJam;
	}
	public String getTranAmount() {
		return tranAmount;
	}
	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getTranCycle() {
		return tranCycle;
	}
	public void setTranCycle(String tranCycle) {
		this.tranCycle = tranCycle;
	}
	public String getContainerCycle() {
		return containerCycle;
	}
	public void setContainerCycle(String containerCycle) {
		this.containerCycle = containerCycle;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public String getTotalNumberOfNotes() {
		return TotalNumberOfNotes;
	}
	public void setTotalNumberOfNotes(String totalNumberOfNotes) {
		TotalNumberOfNotes = totalNumberOfNotes;
	}
	
	
	
}

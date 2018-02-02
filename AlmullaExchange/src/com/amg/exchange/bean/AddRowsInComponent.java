package com.amg.exchange.bean;

public class AddRowsInComponent {

	 private String COMPANY;
	 private String APPLICATION_NAME;
	 private String SCREEN_NAME;
	 private String COMPONENT_CODE;
	 private String COMPONENT_NAME;

	 public AddRowsInComponent(String COMPANY, String APPLICATION_NAME, String SCREEN_NAME, String COMPONENT_CODE, String COMPONENT_NAME){
		 this.COMPANY = COMPANY;
		 this.APPLICATION_NAME = APPLICATION_NAME;
		 this.SCREEN_NAME = SCREEN_NAME;
		 this.COMPONENT_CODE = COMPONENT_CODE;
		 this.COMPONENT_NAME = COMPONENT_NAME;
	 }

	 public String getCOMPANY() {
		 return COMPANY;
	 }

	 public void setCOMPANY(String cOMPANY) {
		 COMPANY = cOMPANY;
	 }

	 public String getAPPLICATION_NAME() {
		 return APPLICATION_NAME;
	 }

	 public void setAPPLICATION_NAME(String aPPLICATION_NAME) {
		 APPLICATION_NAME = aPPLICATION_NAME;
	 }

	 public String getSCREEN_NAME() {
		 return SCREEN_NAME;
	 }

	 public void setSCREEN_NAME(String sCREEN_NAME) {
		 SCREEN_NAME = sCREEN_NAME;
	 }

	 public String getCOMPONENT_CODE() {
		 return COMPONENT_CODE;
	 }

	 public void setCOMPONENT_CODE(String cOMPONENT_CODE) {
		 COMPONENT_CODE = cOMPONENT_CODE;
	 }

	 public String getCOMPONENT_NAME() {
		 return COMPONENT_NAME;
	 }

	 public void setCOMPONENT_NAME(String cOMPONENT_NAME) {
		 COMPONENT_NAME = cOMPONENT_NAME;
	 }
	
}

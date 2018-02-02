/**
 * FNSServicesLookupRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.amg.exchange.finscan.webservice;

public class FNSServicesLookupRequest  implements java.io.Serializable {
    private java.lang.String organization;

    private java.lang.String userName;

    private java.lang.String password;

    private java.lang.String clientSource;

    private com.amg.exchange.finscan.webservice.SLComplianceList[] complianceLists;

    private com.amg.exchange.finscan.webservice.SLSearchTypeEnum searchType;

    private java.lang.String clientID;

    private com.amg.exchange.finscan.webservice.SLClientStatusEnum clientStatus;

    private com.amg.exchange.finscan.webservice.SLGenderEnum gender;

    private java.lang.String nameLine;

    private com.amg.exchange.finscan.webservice.SLAlternateName[] alternateNames;

    private java.lang.String addressLine1;

    private java.lang.String addressLine2;

    private java.lang.String addressLine3;

    private java.lang.String addressLine4;

    private java.lang.String addressLine5;

    private java.lang.String addressLine6;

    private java.lang.String addressLine7;

    private java.lang.String specificElement;

    private com.amg.exchange.finscan.webservice.SLClientSearchTypeEnum clientSearchType;

    private com.amg.exchange.finscan.webservice.SLYesNoEnum returnComplianceRecords;

    private com.amg.exchange.finscan.webservice.SLYesNoEnum addClient;

    private com.amg.exchange.finscan.webservice.SLYesNoEnum sendToReview;

    private int iterativeSearchMaxCount;

    private int[] userFieldsSearch;

    private com.amg.exchange.finscan.webservice.SLYesNoEnum updateUserFields;

    private java.lang.String userField1Label;

    private java.lang.String userField1Value;

    private java.lang.String userField2Label;

    private java.lang.String userField2Value;

    private java.lang.String userField3Label;

    private java.lang.String userField3Value;

    private java.lang.String userField4Label;

    private java.lang.String userField4Value;

    private java.lang.String userField5Label;

    private java.lang.String userField5Value;

    private java.lang.String userField6Label;

    private java.lang.String userField6Value;

    private java.lang.String userField7Label;

    private java.lang.String userField7Value;

    private java.lang.String userField8Label;

    private java.lang.String userField8Value;

    private java.lang.String comment;

    private java.lang.String passthroughDescription;

    private java.lang.String passthroughValue;

    public FNSServicesLookupRequest() {
    }

    public FNSServicesLookupRequest(
           java.lang.String organization,
           java.lang.String userName,
           java.lang.String password,
           java.lang.String clientSource,
           com.amg.exchange.finscan.webservice.SLComplianceList[] complianceLists,
           com.amg.exchange.finscan.webservice.SLSearchTypeEnum searchType,
           java.lang.String clientID,
           com.amg.exchange.finscan.webservice.SLClientStatusEnum clientStatus,
           com.amg.exchange.finscan.webservice.SLGenderEnum gender,
           java.lang.String nameLine,
           com.amg.exchange.finscan.webservice.SLAlternateName[] alternateNames,
           java.lang.String addressLine1,
           java.lang.String addressLine2,
           java.lang.String addressLine3,
           java.lang.String addressLine4,
           java.lang.String addressLine5,
           java.lang.String addressLine6,
           java.lang.String addressLine7,
           java.lang.String specificElement,
           com.amg.exchange.finscan.webservice.SLClientSearchTypeEnum clientSearchType,
           com.amg.exchange.finscan.webservice.SLYesNoEnum returnComplianceRecords,
           com.amg.exchange.finscan.webservice.SLYesNoEnum addClient,
           com.amg.exchange.finscan.webservice.SLYesNoEnum sendToReview,
           int iterativeSearchMaxCount,
           int[] userFieldsSearch,
           com.amg.exchange.finscan.webservice.SLYesNoEnum updateUserFields,
           java.lang.String userField1Label,
           java.lang.String userField1Value,
           java.lang.String userField2Label,
           java.lang.String userField2Value,
           java.lang.String userField3Label,
           java.lang.String userField3Value,
           java.lang.String userField4Label,
           java.lang.String userField4Value,
           java.lang.String userField5Label,
           java.lang.String userField5Value,
           java.lang.String userField6Label,
           java.lang.String userField6Value,
           java.lang.String userField7Label,
           java.lang.String userField7Value,
           java.lang.String userField8Label,
           java.lang.String userField8Value,
           java.lang.String comment,
           java.lang.String passthroughDescription,
           java.lang.String passthroughValue) {
           this.organization = organization;
           this.userName = userName;
           this.password = password;
           this.clientSource = clientSource;
           this.complianceLists = complianceLists;
           this.searchType = searchType;
           this.clientID = clientID;
           this.clientStatus = clientStatus;
           this.gender = gender;
           this.nameLine = nameLine;
           this.alternateNames = alternateNames;
           this.addressLine1 = addressLine1;
           this.addressLine2 = addressLine2;
           this.addressLine3 = addressLine3;
           this.addressLine4 = addressLine4;
           this.addressLine5 = addressLine5;
           this.addressLine6 = addressLine6;
           this.addressLine7 = addressLine7;
           this.specificElement = specificElement;
           this.clientSearchType = clientSearchType;
           this.returnComplianceRecords = returnComplianceRecords;
           this.addClient = addClient;
           this.sendToReview = sendToReview;
           this.iterativeSearchMaxCount = iterativeSearchMaxCount;
           this.userFieldsSearch = userFieldsSearch;
           this.updateUserFields = updateUserFields;
           this.userField1Label = userField1Label;
           this.userField1Value = userField1Value;
           this.userField2Label = userField2Label;
           this.userField2Value = userField2Value;
           this.userField3Label = userField3Label;
           this.userField3Value = userField3Value;
           this.userField4Label = userField4Label;
           this.userField4Value = userField4Value;
           this.userField5Label = userField5Label;
           this.userField5Value = userField5Value;
           this.userField6Label = userField6Label;
           this.userField6Value = userField6Value;
           this.userField7Label = userField7Label;
           this.userField7Value = userField7Value;
           this.userField8Label = userField8Label;
           this.userField8Value = userField8Value;
           this.comment = comment;
           this.passthroughDescription = passthroughDescription;
           this.passthroughValue = passthroughValue;
    }


    /**
     * Gets the organization value for this FNSServicesLookupRequest.
     * 
     * @return organization
     */
    public java.lang.String getOrganization() {
        return organization;
    }


    /**
     * Sets the organization value for this FNSServicesLookupRequest.
     * 
     * @param organization
     */
    public void setOrganization(java.lang.String organization) {
        this.organization = organization;
    }


    /**
     * Gets the userName value for this FNSServicesLookupRequest.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this FNSServicesLookupRequest.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }


    /**
     * Gets the password value for this FNSServicesLookupRequest.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this FNSServicesLookupRequest.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the clientSource value for this FNSServicesLookupRequest.
     * 
     * @return clientSource
     */
    public java.lang.String getClientSource() {
        return clientSource;
    }


    /**
     * Sets the clientSource value for this FNSServicesLookupRequest.
     * 
     * @param clientSource
     */
    public void setClientSource(java.lang.String clientSource) {
        this.clientSource = clientSource;
    }


    /**
     * Gets the complianceLists value for this FNSServicesLookupRequest.
     * 
     * @return complianceLists
     */
    public com.amg.exchange.finscan.webservice.SLComplianceList[] getComplianceLists() {
        return complianceLists;
    }


    /**
     * Sets the complianceLists value for this FNSServicesLookupRequest.
     * 
     * @param complianceLists
     */
    public void setComplianceLists(com.amg.exchange.finscan.webservice.SLComplianceList[] complianceLists) {
        this.complianceLists = complianceLists;
    }


    /**
     * Gets the searchType value for this FNSServicesLookupRequest.
     * 
     * @return searchType
     */
    public com.amg.exchange.finscan.webservice.SLSearchTypeEnum getSearchType() {
        return searchType;
    }


    /**
     * Sets the searchType value for this FNSServicesLookupRequest.
     * 
     * @param searchType
     */
    public void setSearchType(com.amg.exchange.finscan.webservice.SLSearchTypeEnum searchType) {
        this.searchType = searchType;
    }


    /**
     * Gets the clientID value for this FNSServicesLookupRequest.
     * 
     * @return clientID
     */
    public java.lang.String getClientID() {
        return clientID;
    }


    /**
     * Sets the clientID value for this FNSServicesLookupRequest.
     * 
     * @param clientID
     */
    public void setClientID(java.lang.String clientID) {
        this.clientID = clientID;
    }


    /**
     * Gets the clientStatus value for this FNSServicesLookupRequest.
     * 
     * @return clientStatus
     */
    public com.amg.exchange.finscan.webservice.SLClientStatusEnum getClientStatus() {
        return clientStatus;
    }


    /**
     * Sets the clientStatus value for this FNSServicesLookupRequest.
     * 
     * @param clientStatus
     */
    public void setClientStatus(com.amg.exchange.finscan.webservice.SLClientStatusEnum clientStatus) {
        this.clientStatus = clientStatus;
    }


    /**
     * Gets the gender value for this FNSServicesLookupRequest.
     * 
     * @return gender
     */
    public com.amg.exchange.finscan.webservice.SLGenderEnum getGender() {
        return gender;
    }


    /**
     * Sets the gender value for this FNSServicesLookupRequest.
     * 
     * @param gender
     */
    public void setGender(com.amg.exchange.finscan.webservice.SLGenderEnum gender) {
        this.gender = gender;
    }


    /**
     * Gets the nameLine value for this FNSServicesLookupRequest.
     * 
     * @return nameLine
     */
    public java.lang.String getNameLine() {
        return nameLine;
    }


    /**
     * Sets the nameLine value for this FNSServicesLookupRequest.
     * 
     * @param nameLine
     */
    public void setNameLine(java.lang.String nameLine) {
        this.nameLine = nameLine;
    }


    /**
     * Gets the alternateNames value for this FNSServicesLookupRequest.
     * 
     * @return alternateNames
     */
    public com.amg.exchange.finscan.webservice.SLAlternateName[] getAlternateNames() {
        return alternateNames;
    }


    /**
     * Sets the alternateNames value for this FNSServicesLookupRequest.
     * 
     * @param alternateNames
     */
    public void setAlternateNames(com.amg.exchange.finscan.webservice.SLAlternateName[] alternateNames) {
        this.alternateNames = alternateNames;
    }


    /**
     * Gets the addressLine1 value for this FNSServicesLookupRequest.
     * 
     * @return addressLine1
     */
    public java.lang.String getAddressLine1() {
        return addressLine1;
    }


    /**
     * Sets the addressLine1 value for this FNSServicesLookupRequest.
     * 
     * @param addressLine1
     */
    public void setAddressLine1(java.lang.String addressLine1) {
        this.addressLine1 = addressLine1;
    }


    /**
     * Gets the addressLine2 value for this FNSServicesLookupRequest.
     * 
     * @return addressLine2
     */
    public java.lang.String getAddressLine2() {
        return addressLine2;
    }


    /**
     * Sets the addressLine2 value for this FNSServicesLookupRequest.
     * 
     * @param addressLine2
     */
    public void setAddressLine2(java.lang.String addressLine2) {
        this.addressLine2 = addressLine2;
    }


    /**
     * Gets the addressLine3 value for this FNSServicesLookupRequest.
     * 
     * @return addressLine3
     */
    public java.lang.String getAddressLine3() {
        return addressLine3;
    }


    /**
     * Sets the addressLine3 value for this FNSServicesLookupRequest.
     * 
     * @param addressLine3
     */
    public void setAddressLine3(java.lang.String addressLine3) {
        this.addressLine3 = addressLine3;
    }


    /**
     * Gets the addressLine4 value for this FNSServicesLookupRequest.
     * 
     * @return addressLine4
     */
    public java.lang.String getAddressLine4() {
        return addressLine4;
    }


    /**
     * Sets the addressLine4 value for this FNSServicesLookupRequest.
     * 
     * @param addressLine4
     */
    public void setAddressLine4(java.lang.String addressLine4) {
        this.addressLine4 = addressLine4;
    }


    /**
     * Gets the addressLine5 value for this FNSServicesLookupRequest.
     * 
     * @return addressLine5
     */
    public java.lang.String getAddressLine5() {
        return addressLine5;
    }


    /**
     * Sets the addressLine5 value for this FNSServicesLookupRequest.
     * 
     * @param addressLine5
     */
    public void setAddressLine5(java.lang.String addressLine5) {
        this.addressLine5 = addressLine5;
    }


    /**
     * Gets the addressLine6 value for this FNSServicesLookupRequest.
     * 
     * @return addressLine6
     */
    public java.lang.String getAddressLine6() {
        return addressLine6;
    }


    /**
     * Sets the addressLine6 value for this FNSServicesLookupRequest.
     * 
     * @param addressLine6
     */
    public void setAddressLine6(java.lang.String addressLine6) {
        this.addressLine6 = addressLine6;
    }


    /**
     * Gets the addressLine7 value for this FNSServicesLookupRequest.
     * 
     * @return addressLine7
     */
    public java.lang.String getAddressLine7() {
        return addressLine7;
    }


    /**
     * Sets the addressLine7 value for this FNSServicesLookupRequest.
     * 
     * @param addressLine7
     */
    public void setAddressLine7(java.lang.String addressLine7) {
        this.addressLine7 = addressLine7;
    }


    /**
     * Gets the specificElement value for this FNSServicesLookupRequest.
     * 
     * @return specificElement
     */
    public java.lang.String getSpecificElement() {
        return specificElement;
    }


    /**
     * Sets the specificElement value for this FNSServicesLookupRequest.
     * 
     * @param specificElement
     */
    public void setSpecificElement(java.lang.String specificElement) {
        this.specificElement = specificElement;
    }


    /**
     * Gets the clientSearchType value for this FNSServicesLookupRequest.
     * 
     * @return clientSearchType
     */
    public com.amg.exchange.finscan.webservice.SLClientSearchTypeEnum getClientSearchType() {
        return clientSearchType;
    }


    /**
     * Sets the clientSearchType value for this FNSServicesLookupRequest.
     * 
     * @param clientSearchType
     */
    public void setClientSearchType(com.amg.exchange.finscan.webservice.SLClientSearchTypeEnum clientSearchType) {
        this.clientSearchType = clientSearchType;
    }


    /**
     * Gets the returnComplianceRecords value for this FNSServicesLookupRequest.
     * 
     * @return returnComplianceRecords
     */
    public com.amg.exchange.finscan.webservice.SLYesNoEnum getReturnComplianceRecords() {
        return returnComplianceRecords;
    }


    /**
     * Sets the returnComplianceRecords value for this FNSServicesLookupRequest.
     * 
     * @param returnComplianceRecords
     */
    public void setReturnComplianceRecords(com.amg.exchange.finscan.webservice.SLYesNoEnum returnComplianceRecords) {
        this.returnComplianceRecords = returnComplianceRecords;
    }


    /**
     * Gets the addClient value for this FNSServicesLookupRequest.
     * 
     * @return addClient
     */
    public com.amg.exchange.finscan.webservice.SLYesNoEnum getAddClient() {
        return addClient;
    }


    /**
     * Sets the addClient value for this FNSServicesLookupRequest.
     * 
     * @param addClient
     */
    public void setAddClient(com.amg.exchange.finscan.webservice.SLYesNoEnum addClient) {
        this.addClient = addClient;
    }


    /**
     * Gets the sendToReview value for this FNSServicesLookupRequest.
     * 
     * @return sendToReview
     */
    public com.amg.exchange.finscan.webservice.SLYesNoEnum getSendToReview() {
        return sendToReview;
    }


    /**
     * Sets the sendToReview value for this FNSServicesLookupRequest.
     * 
     * @param sendToReview
     */
    public void setSendToReview(com.amg.exchange.finscan.webservice.SLYesNoEnum sendToReview) {
        this.sendToReview = sendToReview;
    }


    /**
     * Gets the iterativeSearchMaxCount value for this FNSServicesLookupRequest.
     * 
     * @return iterativeSearchMaxCount
     */
    public int getIterativeSearchMaxCount() {
        return iterativeSearchMaxCount;
    }


    /**
     * Sets the iterativeSearchMaxCount value for this FNSServicesLookupRequest.
     * 
     * @param iterativeSearchMaxCount
     */
    public void setIterativeSearchMaxCount(int iterativeSearchMaxCount) {
        this.iterativeSearchMaxCount = iterativeSearchMaxCount;
    }


    /**
     * Gets the userFieldsSearch value for this FNSServicesLookupRequest.
     * 
     * @return userFieldsSearch
     */
    public int[] getUserFieldsSearch() {
        return userFieldsSearch;
    }


    /**
     * Sets the userFieldsSearch value for this FNSServicesLookupRequest.
     * 
     * @param userFieldsSearch
     */
    public void setUserFieldsSearch(int[] userFieldsSearch) {
        this.userFieldsSearch = userFieldsSearch;
    }


    /**
     * Gets the updateUserFields value for this FNSServicesLookupRequest.
     * 
     * @return updateUserFields
     */
    public com.amg.exchange.finscan.webservice.SLYesNoEnum getUpdateUserFields() {
        return updateUserFields;
    }


    /**
     * Sets the updateUserFields value for this FNSServicesLookupRequest.
     * 
     * @param updateUserFields
     */
    public void setUpdateUserFields(com.amg.exchange.finscan.webservice.SLYesNoEnum updateUserFields) {
        this.updateUserFields = updateUserFields;
    }


    /**
     * Gets the userField1Label value for this FNSServicesLookupRequest.
     * 
     * @return userField1Label
     */
    public java.lang.String getUserField1Label() {
        return userField1Label;
    }


    /**
     * Sets the userField1Label value for this FNSServicesLookupRequest.
     * 
     * @param userField1Label
     */
    public void setUserField1Label(java.lang.String userField1Label) {
        this.userField1Label = userField1Label;
    }


    /**
     * Gets the userField1Value value for this FNSServicesLookupRequest.
     * 
     * @return userField1Value
     */
    public java.lang.String getUserField1Value() {
        return userField1Value;
    }


    /**
     * Sets the userField1Value value for this FNSServicesLookupRequest.
     * 
     * @param userField1Value
     */
    public void setUserField1Value(java.lang.String userField1Value) {
        this.userField1Value = userField1Value;
    }


    /**
     * Gets the userField2Label value for this FNSServicesLookupRequest.
     * 
     * @return userField2Label
     */
    public java.lang.String getUserField2Label() {
        return userField2Label;
    }


    /**
     * Sets the userField2Label value for this FNSServicesLookupRequest.
     * 
     * @param userField2Label
     */
    public void setUserField2Label(java.lang.String userField2Label) {
        this.userField2Label = userField2Label;
    }


    /**
     * Gets the userField2Value value for this FNSServicesLookupRequest.
     * 
     * @return userField2Value
     */
    public java.lang.String getUserField2Value() {
        return userField2Value;
    }


    /**
     * Sets the userField2Value value for this FNSServicesLookupRequest.
     * 
     * @param userField2Value
     */
    public void setUserField2Value(java.lang.String userField2Value) {
        this.userField2Value = userField2Value;
    }


    /**
     * Gets the userField3Label value for this FNSServicesLookupRequest.
     * 
     * @return userField3Label
     */
    public java.lang.String getUserField3Label() {
        return userField3Label;
    }


    /**
     * Sets the userField3Label value for this FNSServicesLookupRequest.
     * 
     * @param userField3Label
     */
    public void setUserField3Label(java.lang.String userField3Label) {
        this.userField3Label = userField3Label;
    }


    /**
     * Gets the userField3Value value for this FNSServicesLookupRequest.
     * 
     * @return userField3Value
     */
    public java.lang.String getUserField3Value() {
        return userField3Value;
    }


    /**
     * Sets the userField3Value value for this FNSServicesLookupRequest.
     * 
     * @param userField3Value
     */
    public void setUserField3Value(java.lang.String userField3Value) {
        this.userField3Value = userField3Value;
    }


    /**
     * Gets the userField4Label value for this FNSServicesLookupRequest.
     * 
     * @return userField4Label
     */
    public java.lang.String getUserField4Label() {
        return userField4Label;
    }


    /**
     * Sets the userField4Label value for this FNSServicesLookupRequest.
     * 
     * @param userField4Label
     */
    public void setUserField4Label(java.lang.String userField4Label) {
        this.userField4Label = userField4Label;
    }


    /**
     * Gets the userField4Value value for this FNSServicesLookupRequest.
     * 
     * @return userField4Value
     */
    public java.lang.String getUserField4Value() {
        return userField4Value;
    }


    /**
     * Sets the userField4Value value for this FNSServicesLookupRequest.
     * 
     * @param userField4Value
     */
    public void setUserField4Value(java.lang.String userField4Value) {
        this.userField4Value = userField4Value;
    }


    /**
     * Gets the userField5Label value for this FNSServicesLookupRequest.
     * 
     * @return userField5Label
     */
    public java.lang.String getUserField5Label() {
        return userField5Label;
    }


    /**
     * Sets the userField5Label value for this FNSServicesLookupRequest.
     * 
     * @param userField5Label
     */
    public void setUserField5Label(java.lang.String userField5Label) {
        this.userField5Label = userField5Label;
    }


    /**
     * Gets the userField5Value value for this FNSServicesLookupRequest.
     * 
     * @return userField5Value
     */
    public java.lang.String getUserField5Value() {
        return userField5Value;
    }


    /**
     * Sets the userField5Value value for this FNSServicesLookupRequest.
     * 
     * @param userField5Value
     */
    public void setUserField5Value(java.lang.String userField5Value) {
        this.userField5Value = userField5Value;
    }


    /**
     * Gets the userField6Label value for this FNSServicesLookupRequest.
     * 
     * @return userField6Label
     */
    public java.lang.String getUserField6Label() {
        return userField6Label;
    }


    /**
     * Sets the userField6Label value for this FNSServicesLookupRequest.
     * 
     * @param userField6Label
     */
    public void setUserField6Label(java.lang.String userField6Label) {
        this.userField6Label = userField6Label;
    }


    /**
     * Gets the userField6Value value for this FNSServicesLookupRequest.
     * 
     * @return userField6Value
     */
    public java.lang.String getUserField6Value() {
        return userField6Value;
    }


    /**
     * Sets the userField6Value value for this FNSServicesLookupRequest.
     * 
     * @param userField6Value
     */
    public void setUserField6Value(java.lang.String userField6Value) {
        this.userField6Value = userField6Value;
    }


    /**
     * Gets the userField7Label value for this FNSServicesLookupRequest.
     * 
     * @return userField7Label
     */
    public java.lang.String getUserField7Label() {
        return userField7Label;
    }


    /**
     * Sets the userField7Label value for this FNSServicesLookupRequest.
     * 
     * @param userField7Label
     */
    public void setUserField7Label(java.lang.String userField7Label) {
        this.userField7Label = userField7Label;
    }


    /**
     * Gets the userField7Value value for this FNSServicesLookupRequest.
     * 
     * @return userField7Value
     */
    public java.lang.String getUserField7Value() {
        return userField7Value;
    }


    /**
     * Sets the userField7Value value for this FNSServicesLookupRequest.
     * 
     * @param userField7Value
     */
    public void setUserField7Value(java.lang.String userField7Value) {
        this.userField7Value = userField7Value;
    }


    /**
     * Gets the userField8Label value for this FNSServicesLookupRequest.
     * 
     * @return userField8Label
     */
    public java.lang.String getUserField8Label() {
        return userField8Label;
    }


    /**
     * Sets the userField8Label value for this FNSServicesLookupRequest.
     * 
     * @param userField8Label
     */
    public void setUserField8Label(java.lang.String userField8Label) {
        this.userField8Label = userField8Label;
    }


    /**
     * Gets the userField8Value value for this FNSServicesLookupRequest.
     * 
     * @return userField8Value
     */
    public java.lang.String getUserField8Value() {
        return userField8Value;
    }


    /**
     * Sets the userField8Value value for this FNSServicesLookupRequest.
     * 
     * @param userField8Value
     */
    public void setUserField8Value(java.lang.String userField8Value) {
        this.userField8Value = userField8Value;
    }


    /**
     * Gets the comment value for this FNSServicesLookupRequest.
     * 
     * @return comment
     */
    public java.lang.String getComment() {
        return comment;
    }


    /**
     * Sets the comment value for this FNSServicesLookupRequest.
     * 
     * @param comment
     */
    public void setComment(java.lang.String comment) {
        this.comment = comment;
    }


    /**
     * Gets the passthroughDescription value for this FNSServicesLookupRequest.
     * 
     * @return passthroughDescription
     */
    public java.lang.String getPassthroughDescription() {
        return passthroughDescription;
    }


    /**
     * Sets the passthroughDescription value for this FNSServicesLookupRequest.
     * 
     * @param passthroughDescription
     */
    public void setPassthroughDescription(java.lang.String passthroughDescription) {
        this.passthroughDescription = passthroughDescription;
    }


    /**
     * Gets the passthroughValue value for this FNSServicesLookupRequest.
     * 
     * @return passthroughValue
     */
    public java.lang.String getPassthroughValue() {
        return passthroughValue;
    }


    /**
     * Sets the passthroughValue value for this FNSServicesLookupRequest.
     * 
     * @param passthroughValue
     */
    public void setPassthroughValue(java.lang.String passthroughValue) {
        this.passthroughValue = passthroughValue;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FNSServicesLookupRequest)) return false;
        FNSServicesLookupRequest other = (FNSServicesLookupRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.organization==null && other.getOrganization()==null) || 
             (this.organization!=null &&
              this.organization.equals(other.getOrganization()))) &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.clientSource==null && other.getClientSource()==null) || 
             (this.clientSource!=null &&
              this.clientSource.equals(other.getClientSource()))) &&
            ((this.complianceLists==null && other.getComplianceLists()==null) || 
             (this.complianceLists!=null &&
              java.util.Arrays.equals(this.complianceLists, other.getComplianceLists()))) &&
            ((this.searchType==null && other.getSearchType()==null) || 
             (this.searchType!=null &&
              this.searchType.equals(other.getSearchType()))) &&
            ((this.clientID==null && other.getClientID()==null) || 
             (this.clientID!=null &&
              this.clientID.equals(other.getClientID()))) &&
            ((this.clientStatus==null && other.getClientStatus()==null) || 
             (this.clientStatus!=null &&
              this.clientStatus.equals(other.getClientStatus()))) &&
            ((this.gender==null && other.getGender()==null) || 
             (this.gender!=null &&
              this.gender.equals(other.getGender()))) &&
            ((this.nameLine==null && other.getNameLine()==null) || 
             (this.nameLine!=null &&
              this.nameLine.equals(other.getNameLine()))) &&
            ((this.alternateNames==null && other.getAlternateNames()==null) || 
             (this.alternateNames!=null &&
              java.util.Arrays.equals(this.alternateNames, other.getAlternateNames()))) &&
            ((this.addressLine1==null && other.getAddressLine1()==null) || 
             (this.addressLine1!=null &&
              this.addressLine1.equals(other.getAddressLine1()))) &&
            ((this.addressLine2==null && other.getAddressLine2()==null) || 
             (this.addressLine2!=null &&
              this.addressLine2.equals(other.getAddressLine2()))) &&
            ((this.addressLine3==null && other.getAddressLine3()==null) || 
             (this.addressLine3!=null &&
              this.addressLine3.equals(other.getAddressLine3()))) &&
            ((this.addressLine4==null && other.getAddressLine4()==null) || 
             (this.addressLine4!=null &&
              this.addressLine4.equals(other.getAddressLine4()))) &&
            ((this.addressLine5==null && other.getAddressLine5()==null) || 
             (this.addressLine5!=null &&
              this.addressLine5.equals(other.getAddressLine5()))) &&
            ((this.addressLine6==null && other.getAddressLine6()==null) || 
             (this.addressLine6!=null &&
              this.addressLine6.equals(other.getAddressLine6()))) &&
            ((this.addressLine7==null && other.getAddressLine7()==null) || 
             (this.addressLine7!=null &&
              this.addressLine7.equals(other.getAddressLine7()))) &&
            ((this.specificElement==null && other.getSpecificElement()==null) || 
             (this.specificElement!=null &&
              this.specificElement.equals(other.getSpecificElement()))) &&
            ((this.clientSearchType==null && other.getClientSearchType()==null) || 
             (this.clientSearchType!=null &&
              this.clientSearchType.equals(other.getClientSearchType()))) &&
            ((this.returnComplianceRecords==null && other.getReturnComplianceRecords()==null) || 
             (this.returnComplianceRecords!=null &&
              this.returnComplianceRecords.equals(other.getReturnComplianceRecords()))) &&
            ((this.addClient==null && other.getAddClient()==null) || 
             (this.addClient!=null &&
              this.addClient.equals(other.getAddClient()))) &&
            ((this.sendToReview==null && other.getSendToReview()==null) || 
             (this.sendToReview!=null &&
              this.sendToReview.equals(other.getSendToReview()))) &&
            this.iterativeSearchMaxCount == other.getIterativeSearchMaxCount() &&
            ((this.userFieldsSearch==null && other.getUserFieldsSearch()==null) || 
             (this.userFieldsSearch!=null &&
              java.util.Arrays.equals(this.userFieldsSearch, other.getUserFieldsSearch()))) &&
            ((this.updateUserFields==null && other.getUpdateUserFields()==null) || 
             (this.updateUserFields!=null &&
              this.updateUserFields.equals(other.getUpdateUserFields()))) &&
            ((this.userField1Label==null && other.getUserField1Label()==null) || 
             (this.userField1Label!=null &&
              this.userField1Label.equals(other.getUserField1Label()))) &&
            ((this.userField1Value==null && other.getUserField1Value()==null) || 
             (this.userField1Value!=null &&
              this.userField1Value.equals(other.getUserField1Value()))) &&
            ((this.userField2Label==null && other.getUserField2Label()==null) || 
             (this.userField2Label!=null &&
              this.userField2Label.equals(other.getUserField2Label()))) &&
            ((this.userField2Value==null && other.getUserField2Value()==null) || 
             (this.userField2Value!=null &&
              this.userField2Value.equals(other.getUserField2Value()))) &&
            ((this.userField3Label==null && other.getUserField3Label()==null) || 
             (this.userField3Label!=null &&
              this.userField3Label.equals(other.getUserField3Label()))) &&
            ((this.userField3Value==null && other.getUserField3Value()==null) || 
             (this.userField3Value!=null &&
              this.userField3Value.equals(other.getUserField3Value()))) &&
            ((this.userField4Label==null && other.getUserField4Label()==null) || 
             (this.userField4Label!=null &&
              this.userField4Label.equals(other.getUserField4Label()))) &&
            ((this.userField4Value==null && other.getUserField4Value()==null) || 
             (this.userField4Value!=null &&
              this.userField4Value.equals(other.getUserField4Value()))) &&
            ((this.userField5Label==null && other.getUserField5Label()==null) || 
             (this.userField5Label!=null &&
              this.userField5Label.equals(other.getUserField5Label()))) &&
            ((this.userField5Value==null && other.getUserField5Value()==null) || 
             (this.userField5Value!=null &&
              this.userField5Value.equals(other.getUserField5Value()))) &&
            ((this.userField6Label==null && other.getUserField6Label()==null) || 
             (this.userField6Label!=null &&
              this.userField6Label.equals(other.getUserField6Label()))) &&
            ((this.userField6Value==null && other.getUserField6Value()==null) || 
             (this.userField6Value!=null &&
              this.userField6Value.equals(other.getUserField6Value()))) &&
            ((this.userField7Label==null && other.getUserField7Label()==null) || 
             (this.userField7Label!=null &&
              this.userField7Label.equals(other.getUserField7Label()))) &&
            ((this.userField7Value==null && other.getUserField7Value()==null) || 
             (this.userField7Value!=null &&
              this.userField7Value.equals(other.getUserField7Value()))) &&
            ((this.userField8Label==null && other.getUserField8Label()==null) || 
             (this.userField8Label!=null &&
              this.userField8Label.equals(other.getUserField8Label()))) &&
            ((this.userField8Value==null && other.getUserField8Value()==null) || 
             (this.userField8Value!=null &&
              this.userField8Value.equals(other.getUserField8Value()))) &&
            ((this.comment==null && other.getComment()==null) || 
             (this.comment!=null &&
              this.comment.equals(other.getComment()))) &&
            ((this.passthroughDescription==null && other.getPassthroughDescription()==null) || 
             (this.passthroughDescription!=null &&
              this.passthroughDescription.equals(other.getPassthroughDescription()))) &&
            ((this.passthroughValue==null && other.getPassthroughValue()==null) || 
             (this.passthroughValue!=null &&
              this.passthroughValue.equals(other.getPassthroughValue())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getOrganization() != null) {
            _hashCode += getOrganization().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getClientSource() != null) {
            _hashCode += getClientSource().hashCode();
        }
        if (getComplianceLists() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getComplianceLists());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getComplianceLists(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSearchType() != null) {
            _hashCode += getSearchType().hashCode();
        }
        if (getClientID() != null) {
            _hashCode += getClientID().hashCode();
        }
        if (getClientStatus() != null) {
            _hashCode += getClientStatus().hashCode();
        }
        if (getGender() != null) {
            _hashCode += getGender().hashCode();
        }
        if (getNameLine() != null) {
            _hashCode += getNameLine().hashCode();
        }
        if (getAlternateNames() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAlternateNames());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAlternateNames(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAddressLine1() != null) {
            _hashCode += getAddressLine1().hashCode();
        }
        if (getAddressLine2() != null) {
            _hashCode += getAddressLine2().hashCode();
        }
        if (getAddressLine3() != null) {
            _hashCode += getAddressLine3().hashCode();
        }
        if (getAddressLine4() != null) {
            _hashCode += getAddressLine4().hashCode();
        }
        if (getAddressLine5() != null) {
            _hashCode += getAddressLine5().hashCode();
        }
        if (getAddressLine6() != null) {
            _hashCode += getAddressLine6().hashCode();
        }
        if (getAddressLine7() != null) {
            _hashCode += getAddressLine7().hashCode();
        }
        if (getSpecificElement() != null) {
            _hashCode += getSpecificElement().hashCode();
        }
        if (getClientSearchType() != null) {
            _hashCode += getClientSearchType().hashCode();
        }
        if (getReturnComplianceRecords() != null) {
            _hashCode += getReturnComplianceRecords().hashCode();
        }
        if (getAddClient() != null) {
            _hashCode += getAddClient().hashCode();
        }
        if (getSendToReview() != null) {
            _hashCode += getSendToReview().hashCode();
        }
        _hashCode += getIterativeSearchMaxCount();
        if (getUserFieldsSearch() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUserFieldsSearch());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUserFieldsSearch(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUpdateUserFields() != null) {
            _hashCode += getUpdateUserFields().hashCode();
        }
        if (getUserField1Label() != null) {
            _hashCode += getUserField1Label().hashCode();
        }
        if (getUserField1Value() != null) {
            _hashCode += getUserField1Value().hashCode();
        }
        if (getUserField2Label() != null) {
            _hashCode += getUserField2Label().hashCode();
        }
        if (getUserField2Value() != null) {
            _hashCode += getUserField2Value().hashCode();
        }
        if (getUserField3Label() != null) {
            _hashCode += getUserField3Label().hashCode();
        }
        if (getUserField3Value() != null) {
            _hashCode += getUserField3Value().hashCode();
        }
        if (getUserField4Label() != null) {
            _hashCode += getUserField4Label().hashCode();
        }
        if (getUserField4Value() != null) {
            _hashCode += getUserField4Value().hashCode();
        }
        if (getUserField5Label() != null) {
            _hashCode += getUserField5Label().hashCode();
        }
        if (getUserField5Value() != null) {
            _hashCode += getUserField5Value().hashCode();
        }
        if (getUserField6Label() != null) {
            _hashCode += getUserField6Label().hashCode();
        }
        if (getUserField6Value() != null) {
            _hashCode += getUserField6Value().hashCode();
        }
        if (getUserField7Label() != null) {
            _hashCode += getUserField7Label().hashCode();
        }
        if (getUserField7Value() != null) {
            _hashCode += getUserField7Value().hashCode();
        }
        if (getUserField8Label() != null) {
            _hashCode += getUserField8Label().hashCode();
        }
        if (getUserField8Value() != null) {
            _hashCode += getUserField8Value().hashCode();
        }
        if (getComment() != null) {
            _hashCode += getComment().hashCode();
        }
        if (getPassthroughDescription() != null) {
            _hashCode += getPassthroughDescription().hashCode();
        }
        if (getPassthroughValue() != null) {
            _hashCode += getPassthroughValue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FNSServicesLookupRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "FNSServicesLookupRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "organization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientSource");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "clientSource"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complianceLists");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "complianceLists"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLComplianceList"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLComplianceList"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "searchType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLSearchTypeEnum"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "clientID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "clientStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLClientStatusEnum"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gender");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "gender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLGenderEnum"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameLine");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "nameLine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alternateNames");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "alternateNames"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLAlternateName"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLAlternateName"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressLine1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "addressLine1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressLine2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "addressLine2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressLine3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "addressLine3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressLine4");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "addressLine4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressLine5");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "addressLine5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressLine6");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "addressLine6"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressLine7");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "addressLine7"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specificElement");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "specificElement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientSearchType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "clientSearchType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLClientSearchTypeEnum"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("returnComplianceRecords");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "returnComplianceRecords"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLYesNoEnum"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addClient");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "addClient"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLYesNoEnum"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sendToReview");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "sendToReview"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLYesNoEnum"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("iterativeSearchMaxCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "iterativeSearchMaxCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userFieldsSearch");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userFieldsSearch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://innovativesystems.com/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateUserFields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "updateUserFields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLYesNoEnum"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField1Label");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField1Label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField1Value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField1Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField2Label");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField2Label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField2Value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField2Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField3Label");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField3Label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField3Value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField3Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField4Label");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField4Label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField4Value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField4Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField5Label");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField5Label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField5Value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField5Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField6Label");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField6Label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField6Value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField6Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField7Label");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField7Label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField7Value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField7Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField8Label");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField8Label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userField8Value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "userField8Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "comment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passthroughDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "passthroughDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passthroughValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "passthroughValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}

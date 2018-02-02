
package com.amg.exchange.finscan.webservice;

import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.xml.rpc.ServiceFactory;
import javax.xml.rpc.Stub;

public class FNSServicesLookupSoapClient {
	
    com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_PortType fnsPort;
    com.amg.exchange.finscan.webservice.FNSServicesLookup   fnsService;
  
   /**
    * This is the default constructor
    *
    * All One Time settings for the Bank's WebServices need to be done here
    * e.g. any stubs/skeletons that need to be initialized are done here.
    * The stubs/skeletons are generated from the WSDL file provided by Banks
    *
    */
  
   public FNSServicesLookupSoapClient() throws Exception {
    
     // Send Transaction Initialization
     fnsService      = new FNSServicesLookupLocator();
     fnsPort         = fnsService.getFNSServicesLookupSoap();
    
   }
    /**
     * @param sSearchType
     * @param sGender
     * @param sClientStatus
     * @param sAddClient
     * @param sUpdateUserFields
     * @param sReturnComplianceRecords
     * @param sAddressLine1
     * @param sAddressLine2
     * @param sAddressLine3
     * @param sAddressLine4
     * @param sAddressLine5
     * @param sAddressLine6
     * @param sAddressLine7
     * @param sCategoryName
     * @param sIsMandatory
     * @param sIsSelected
     * @param sListCode
     * @param sListName
     * @param sCategories
     * @param sComplianceLists
     * @param sUserField1Label
     * @param sUserField1Value
     * @param sUserField2Label
     * @param sUserField2Value
     * @param sUserField3Label
     * @param sUserField3Value
     * @param sUserField4Label
     * @param sUserField4Value
     * @param sNameLine
     * @param sUserField8Value
     * @param sClientID
     * @param sClientSearchType
     * @param sAmlSts
     * @return
     */
    public String  amlServiceSearch(String sSearchType, String sGender, String sClientStatus, 
                                     String sAddClient, String sUpdateUserFields,
                                     String sReturnComplianceRecords, String sAddressLine1,
                                     String sAddressLine2, String sAddressLine3, String sAddressLine4,
                                     String sAddressLine5, String sAddressLine6, String sAddressLine7,
                                     String sCategoryName, String sIsMandatory, String sIsSelected, String sListCode,
                                     String sListName, String sCategories, String sComplianceLists,
                                     String sUserField1Label, String sUserField1Value, String sUserField2Label,
                                     String sUserField2Value, String sUserField3Label, String sUserField3Value,
                                     String sUserField4Label, String sUserField4Value, String sNameLine,
                                     String sArabicName, String sClientID, String sClientSearchType,String sAmlSts
                                     ) {

     String result = "noCall";
     String commResult = null; 
     
     FNSServicesLookupRequest  searchRequest = new FNSServicesLookupRequest();
     FNSServicesLookupResponse response      = new FNSServicesLookupResponse();

      try {
         
         SLAlternateName[]           scrAltName    = new SLAlternateName[1];
         com.amg.exchange.finscan.webservice.FNSServicesLookupSoapClient myPort = new com.amg.exchange.finscan.webservice.FNSServicesLookupSoapClient();
         
         
        

        // initialize credentials ( Mandatory ) Production
        searchRequest.setOrganization("almulla");
        searchRequest.setUserName("WebService");
        searchRequest.setPassword("webservice");
        //searchRequest.setPassword("webservice");
//         searchRequest.setUserName("SOommen");
//         searchRequest.setPassword("tandem123$%");
//           searchRequest.setUserName("admin");
//          searchRequest.setPassword("admin");
        
        //Test Credentials
//         searchRequest.setOrganization("almulla");
//         searchRequest.setUserName("webcall");
//         searchRequest.setPassword("passwordweb");
        
        // initialize parameters  ( Mandatory ) 
        searchRequest.setClientSource(sClientSearchType);  
        
        // Search Type   ( Mandatory ) 
        if (SLSearchTypeEnum._Individual.equalsIgnoreCase(sSearchType) ) { 
            searchRequest.setSearchType(SLSearchTypeEnum.Individual);
        } else if (SLSearchTypeEnum._Organization.equalsIgnoreCase(sSearchType) ) { 
            searchRequest.setSearchType(SLSearchTypeEnum.Organization);
        } else if (SLSearchTypeEnum._SpecificElement.equalsIgnoreCase(sSearchType) ) { 
            searchRequest.setSearchType(SLSearchTypeEnum.SpecificElement);
        }else{
             //result = "Search Type not passed correctly";
              result = "Search Type Error-0";
             return result;
        }
        
        
       //  Gender Specification ( Mandatory ) 
        if(SLGenderEnum._Unknown.equalsIgnoreCase(sGender)) {
            searchRequest.setGender(SLGenderEnum.Unknown);
        } else if(SLGenderEnum._Female.equalsIgnoreCase(sGender)) {
            searchRequest.setGender(SLGenderEnum.Female);
        }else if(SLGenderEnum._Male.equalsIgnoreCase(sGender)) {
            searchRequest.setGender(SLGenderEnum.Male);
        }else{
           //result = "Gender not passed correctly";
            result = "Gender Error-0";
           return result;
        }
        
        //  Client Status ( Mandatory )
        if(SLClientStatusEnum._Active.equalsIgnoreCase(sClientStatus)){ 
            searchRequest.setClientStatus(SLClientStatusEnum.Active);
        } else if(SLClientStatusEnum._Inactive.equalsIgnoreCase(sClientStatus)){ 
            searchRequest.setClientStatus(SLClientStatusEnum.Inactive);
        }else{
             result = "Client StS Error-0";
             return result;
        }
         
        //  Add Client status ( Mandatory )
        if(SLYesNoEnum._No.equalsIgnoreCase(sAddClient) ){
            searchRequest.setAddClient(SLYesNoEnum.No);
        }else if(SLYesNoEnum._Yes.equalsIgnoreCase(sAddClient) ){
            searchRequest.setAddClient(SLYesNoEnum.Yes);
        }else{
           result = "Add Client Error-0";
           return result;
        }
        
        // Update user Files  ( Mandatory ) 
        if(SLYesNoEnum._No.equalsIgnoreCase(sUpdateUserFields) ){
            searchRequest.setUpdateUserFields(SLYesNoEnum.No);
        }else if(SLYesNoEnum._Yes.equalsIgnoreCase(sUpdateUserFields) ){
            searchRequest.setUpdateUserFields(SLYesNoEnum.Yes);
        }else{
           result = "Update User Error-0";
           return result;
        }
    
        // Return Complaince Records ( Mandatory ) 
        if(SLYesNoEnum._No.equalsIgnoreCase(sReturnComplianceRecords) ){
            searchRequest.setReturnComplianceRecords(SLYesNoEnum.No);
        }else if(SLYesNoEnum._Yes.equalsIgnoreCase(sReturnComplianceRecords) ){
            searchRequest.setReturnComplianceRecords(SLYesNoEnum.Yes);
        }else{
           result = "Ret Compl Error-0";
           return result;
        }

         
         // initialize Address Lines
        searchRequest.setAddressLine1(sAddressLine1); // address1
        searchRequest.setAddressLine2(sAddressLine2); // address2
        searchRequest.setAddressLine3(sAddressLine3); // address3
        searchRequest.setAddressLine4(sAddressLine4); // city
        searchRequest.setAddressLine5(sAddressLine5); // countryState
        searchRequest.setAddressLine6(sAddressLine6); // zipPostCode
        searchRequest.setAddressLine7(sAddressLine7);
         
         SLComplianceListCategory[] compCategory = new SLComplianceListCategory[2];
        
        // Check the Category search Type and set the categories
        // Individuals
        // TODO: Kindly, remove the below comments on Individuals_HR
        if(searchRequest.getSearchType()._Individual.equalsIgnoreCase(sSearchType)){
            
            compCategory[0] = new  SLComplianceListCategory();
            compCategory[0].setCategoryName("Individuals");
            
            compCategory[1] = new  SLComplianceListCategory();
            compCategory[1].setCategoryName("Individuals_HR"); 
            
        } else if(searchRequest.getSearchType()._Organization.equalsIgnoreCase(sSearchType)){
            compCategory[0] = new  SLComplianceListCategory();
            compCategory[0].setCategoryName("Entities"); 
            
            compCategory[1] = new  SLComplianceListCategory();
            compCategory[1].setCategoryName("Entities_HR");
        }

        
        for(int i = 0; i < compCategory.length; i ++ ){
        
            if(SLYesNoEnum._No.equalsIgnoreCase(sIsMandatory) ){
               compCategory[i].setIsMandatory(SLYesNoEnum.No);
            }else if(SLYesNoEnum._Yes.equalsIgnoreCase(sIsMandatory) ){
                compCategory[i].setIsMandatory(SLYesNoEnum.Yes);
            }else{
               result = "Ret Compl Error-0";
               return result;
            }
        
            if(SLYesNoEnum._No.equalsIgnoreCase(sIsSelected) ){
                compCategory[i].setIsSelected(SLYesNoEnum.No);
            }else if(SLYesNoEnum._Yes.equalsIgnoreCase(sIsSelected) ){
                compCategory[i].setIsSelected(SLYesNoEnum.Yes);
            }else{
               result = "Ret Compl Error-0";
               return result;
            }
        }
    //      }
       
         // Initialize Compliance List Object
          SLComplianceList[]  compList = new SLComplianceList[1];
          compList[0] = new SLComplianceList();
          compList[0].setListCode(sListCode);
          compList[0].setListName(sListName);
          
          compList[0].setCategories(compCategory);
          searchRequest.setComplianceLists(compList);
          
          
          //searchRequest.set
           //scrAltName[0].setClientSearchType(SLClientSearchTypeEnum.IterativeSearch);
         
         
         
          // initialize User Fields
          searchRequest.setUserField1Label(sUserField1Label);
          searchRequest.setUserField1Value(sUserField1Value); // Country
          //searchRequest.setUserField1Value("");
          searchRequest.setUserField2Label(sUserField2Label);
          searchRequest.setUserField2Value(sUserField2Value); // DOB
          // searchRequest.setUserField2Value(""); // DOB
          searchRequest.setUserField3Label(sUserField3Label);
          searchRequest.setUserField3Value(sUserField3Value); // National ID
          // searchRequest.setUserField3Value(""); // NID
         searchRequest.setUserField4Label(sUserField4Label);
         searchRequest.setUserField4Value(sUserField4Value); // PP NO
          // searchRequest.setUserField4Value(""); // PP Number
          searchRequest.setUserField5Label("");
          searchRequest.setUserField5Value("");
          searchRequest.setUserField6Label("");
          searchRequest.setUserField6Value("");
          searchRequest.setUserField7Label("");
          searchRequest.setUserField7Value("");
          searchRequest.setUserField8Label("");
          searchRequest.setUserField8Value("");

          searchRequest.setSpecificElement("");
          searchRequest.setComment("");
          searchRequest.setPassthroughValue("");
          searchRequest.setPassthroughDescription("");
          
          searchRequest.setIterativeSearchMaxCount(5);
          
         int iterfields[] = new int[1];
         
         // Checks the PassPort Number is passed as argument or not
         // TODO: Need to change the value back to UserField4 when we migrate to LIVE environment
         //if(sUserField3Value != null && sUserField3Value.length() > 2){
          if(sUserField4Value != null && sUserField4Value.length() > 2){
             iterfields[0] = 4;
              //iterfields[0] = 3;
             
             // Sets the passport Number screening option as "Yes"
             searchRequest.setUserFieldsSearch(iterfields);
             
             // Sets the Send to Review option as "Yes"
              searchRequest.setSendToReview(SLYesNoEnum.Yes);
         }else if ( sAmlSts !=null && !sAmlSts.equalsIgnoreCase("New")){
             searchRequest.setSendToReview(SLYesNoEnum.Yes);
         }else if(searchRequest.getSearchType()._Organization.equalsIgnoreCase(sSearchType)){ // For Corporate Customer Customer
             searchRequest.setSendToReview(SLYesNoEnum.Yes);  
         }else{
             // Sets the Send to Review option as "Yes"
              searchRequest.setSendToReview(SLYesNoEnum.No);
         }
          
          
          
         // Get the Client Search Type whether English or Arabic
         String aa= sClientID.substring(sClientID.indexOf("-")+1,sClientID.length());
         
          // Call SSQSearch for English Name if not empty
          //if(aa.equalsIgnoreCase("E")){   
             searchRequest.setNameLine(sNameLine); 
             searchRequest.setUserField8Value("");
             searchRequest.setClientID(sClientID);
             
             //searchRequest.setClientSearchType( SLClientSearchTypeEnum.FinScanFullName);
             //searchRequest.setClientSearchType(SLClientSearchTypeEnum.EqualWithWildcardedInitials);
             
             searchRequest.setClientSearchType(SLClientSearchTypeEnum.IterativeSearch);
          //}

         if ( sArabicName != null && sArabicName.length() > 2 ) { 
             scrAltName[0] = new SLAlternateName();
             scrAltName[0].setNameLine(sArabicName);
             scrAltName[0].setClientSearchType(SLClientSearchTypeEnum.IterativeSearch);
             //scrAltName[0].setClientSearchType(SLClientSearchTypeEnum.FinScanFullName);
             // Sets the Arabic Name to the Request
             searchRequest.setAlternateNames(scrAltName);
         }
         
        
         
         response = myPort.SLLookup(searchRequest);
        		 
         
         //System.out.println("Send to review is : "  + searchRequest.getSendToReview());
         
         
         int retVal = 0;
         int safCount = 0;
         int pendCount = 0;
         int hitCount  = 0;
         
         retVal = response.getReturned();//    returned;
         safCount = response.getSafeCount();//      safeCount;
         pendCount = response.getPendingCount();      //pendingCount;
         hitCount  = response.getHitCount();   //.hitCount;
         
         String finalResult = response.getStatus().toString();
         
         if(finalResult.equalsIgnoreCase("error")){
          searchRequest.setClientSearchType(SLClientSearchTypeEnum.FinScanFullName);
         scrAltName[0] = new SLAlternateName();
         scrAltName[0].setNameLine(sArabicName);
         scrAltName[0].setClientSearchType(SLClientSearchTypeEnum.FinScanFullName);
         //scrAltName[0].setClientSearchType(SLClientSearchTypeEnum.FinScanFullName);
         // Sets the Arabic Name to the Request
         searchRequest.setAlternateNames(scrAltName);
         response = myPort.SLLookup(searchRequest);
         
         finalResult = response.getStatus().toString();
         retVal = response.getReturned();//    returned;
         safCount = response.getSafeCount();//      safeCount;
         pendCount = response.getPendingCount();      //pendingCount;
         hitCount  = response.getHitCount();   //.hitCount;
         }
         
        
         
         // Need to check, because the ERROR status also has the returned value as Zero, which will
         // definelty get mess up the entire logic
         if(!finalResult.equalsIgnoreCase("error")){
         
             if( retVal > 0 ){
               // Checks whether the passport number is get screened or not.
               if(sUserField4Value != null && sUserField4Value.length() > 0)
               {  // Passport Number is send for screening and found again return Value, so tag this as fail.
                   //if (finalResult.equalsIgnoreCase("PASS") && (retVal == safCount ))
                  if (finalResult.equalsIgnoreCase("PASS") && (searchRequest.getSendToReview().equals(SLYesNoEnum.Yes) ))
                   {
                       commResult = "PASS"+"-0";
                   }else if (finalResult.equalsIgnoreCase("PENDING") && (searchRequest.getSendToReview().equals(SLYesNoEnum.Yes) ))
                   {
                       commResult = "PENDING"+"-"+pendCount;
                   }else if (finalResult.equalsIgnoreCase("FAIL") && (searchRequest.getSendToReview().equals(SLYesNoEnum.Yes) ))
                   {
                       commResult = "FAIL"+"-"+hitCount;
                   }
                   else{
                       commResult = finalResult+"-" + retVal;
                   }
                                
               }else{  // Passport Screening is not done, so that we will send back response as Pending. 
                   commResult = "PENDING"+"-" + retVal;
               }
             } else if(searchRequest.getSendToReview().equals(SLYesNoEnum.Yes) && finalResult.equalsIgnoreCase("Pending") ){
                     commResult = "PENDING"+"-" + pendCount;
             }else if(searchRequest.getSendToReview().equals(SLYesNoEnum.Yes) && finalResult.equalsIgnoreCase("Fail") ){
                     commResult = "Fail"+"-0";
             }else if ( (retVal == 0 && pendCount == 0) && 
                        ( finalResult.equalsIgnoreCase("PASS") && searchRequest.getSendToReview().equals(SLYesNoEnum.Yes) )) {
               commResult = "PASS"+"-" + retVal;
              }
         }else{
             if( ! (    finalResult.equalsIgnoreCase("Pending") 
                     && finalResult.equalsIgnoreCase("Pass")) ){
                    commResult = finalResult+"-" + retVal;
                }
         }
      //System.out.println("The Result returned from the web services is : " + commResult);
        
     }catch (Exception e) {
        e.printStackTrace();
     }

    return commResult;

    }

     /**
      * @param args
      */
     public static void main(String[] args) {
         try {
         
         
             com.amg.exchange.finscan.webservice.FNSServicesLookupSoapClient myPort = new com.amg.exchange.finscan.webservice.FNSServicesLookupSoapClient();
             System.out.println("calling " + myPort.getEndpoint());
             
           
             // Add your own code here
             FNSServicesLookupRequest request = new FNSServicesLookupRequest();
             FNSServicesLookupResponse response = new FNSServicesLookupResponse();
             
             String result = "";
             
             
            
             //System.out.println("Search Type:" + SLSearchTypeEnum.Individual.toString());

               
                 // Call Search
                 try
                 {     
                /** * @param sSearchType   * @param sGender             * @param sClientStatus
                * @param sAddClient        * @param sUpdateUserFields   * @param sReturnComplianceRecords
                * @param sAddressLine1     * @param sAddressLine2       * @param sAddressLine3            
                * @param sAddressLine4     * @param sAddressLine5       * @param sAddressLine6       
                * @param sAddressLine7     * @param sCategoryName       * @param sIsMandatory      
                * @param sIsSelected       * @param sListCode           * @param sListName
                * @param sCategories       * @param sComplianceLists    * @param sUserField1Label    
                * @param sUserField1Value  * @param sUserField2Label    * @param sUserField2Value  
                * @param sUserField3Label  * @param sUserField3Value    * @param sUserField4Label
                * @param sUserField4Value  * @param sNameLine           * @param sUserField8Value   
                * @param sClientID         * @param sClientSearchType   * @param sAmlSts
                * @return
                */
 
            result =  myPort.amlServiceSearch( "Organization"    ,"UNKNOWN"         ,"Active",
                                                "Yes"          ,"Yes"           ,"Yes"         ,
                                                 " "           ," "            ," "           ,
                                                 " "           ," "            ," "           ,
                                                 " "           ,"Full Source"  ,"Yes"         ,
                                                 "Yes"         ,"WC"           ,"World-Check" ,
                                                 "  "          ,"  "           ,"Country"     ,
                                                "EGYPT"            ,"Date of Birth",""    ,   
                                                "National ID Number" ,""         , "Passport ID" ,
                                                 "","MOHAMED ABDULWAHAB MOHAMED","",
                                                  "11434471-E"    ,"TEST"            , ""); 

     System.out.println("Results: " + result );
     System.out.println("Running");
                 } 
                 catch (Exception e) 
                 {
                     e.printStackTrace();
                 }
           System.out.println("Running");
        } catch (Exception ex) {
             ex.printStackTrace();
         }
     }
    /**
    Custom FinScan Wrapper

    **/
    public FNSServicesLookupResponse SLLookup(FNSServicesLookupRequest req) throws java.rmi.RemoteException {
        return fnsPort.SLLookup(req);
    }
    
    
    
    
    public String getEndpoint() {
        return (String) ((Stub) fnsPort)._getProperty(Stub.ENDPOINT_ADDRESS_PROPERTY);
    }
    
    public void setEndpoint(String endpoint) {
        ((Stub) fnsPort)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, endpoint);
    }
    
    public String getPassword() {
        return (String) ((Stub) fnsPort)._getProperty(Stub.PASSWORD_PROPERTY);
    }
    
    public void setPassword(String password) {
        ((Stub) fnsPort)._setProperty(Stub.PASSWORD_PROPERTY, password);
    }
    
    public String getUsername() {
        return (String) ((Stub) fnsPort)._getProperty(Stub.USERNAME_PROPERTY);
    }
    
    public void setUsername(String username) {
        ((Stub) fnsPort)._setProperty(Stub.USERNAME_PROPERTY, username);
    }
    
    public void setMaintainSession(boolean maintainSession) {
        ((Stub) fnsPort)._setProperty(Stub.SESSION_MAINTAIN_PROPERTY, Boolean.valueOf(maintainSession));
    }
    
    public boolean getMaintainSession() {
        return ((Boolean) ((Stub) fnsPort)._getProperty(Stub.SESSION_MAINTAIN_PROPERTY)).booleanValue();
    }
    
  
    
}

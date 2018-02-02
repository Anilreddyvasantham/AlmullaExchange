/**
 * SmsServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.amg.exchange.send_sms;

public interface SmsServiceSoap_PortType extends java.rmi.Remote
   {
      public java.lang.String sendSMS(
	    java.lang.String msisdn,
	    java.lang.String msg,
	    java.lang.String lang,
	    java.lang.String senderID,
	    java.lang.String UName,
	    java.lang.String password,
	    java.lang.String accountID,
	    java.lang.String transactionID) throws java.rmi.RemoteException;

      public java.lang.String sendSMSService(
	    java.lang.String msisdn,
	    java.lang.String msg,
	    java.lang.String lang,
	    java.lang.String senderID,
	    java.lang.String UName,
	    java.lang.String password,
	    java.lang.String accountID,
	    java.lang.String transactionID) throws java.rmi.RemoteException;

      public java.lang.String balanceEnquiry(java.lang.String UName, java.lang.String password, java.lang.String accountID) throws java.rmi.RemoteException;

      public java.lang.String getActiveSenderID(java.lang.String UName, java.lang.String password, java.lang.String accountID) throws java.rmi.RemoteException;

      public java.lang.String getServerTime(java.lang.String UName, java.lang.String password, java.lang.String accountID) throws java.rmi.RemoteException;
   }

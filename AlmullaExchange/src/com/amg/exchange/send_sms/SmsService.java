/**
 * SmsService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.amg.exchange.send_sms;

public interface SmsService extends javax.xml.rpc.Service
   {
      public java.lang.String getSmsServiceSoapAddress();

      public SmsServiceSoap_PortType getSmsServiceSoap() throws javax.xml.rpc.ServiceException;

      public SmsServiceSoap_PortType getSmsServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;

      public java.lang.String getSmsServiceSoap12Address();

      public SmsServiceSoap_PortType getSmsServiceSoap12() throws javax.xml.rpc.ServiceException;

      public SmsServiceSoap_PortType getSmsServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
   }

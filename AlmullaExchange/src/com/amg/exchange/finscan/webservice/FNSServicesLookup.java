/**
 * FNSServicesLookup.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.amg.exchange.finscan.webservice;

public interface FNSServicesLookup extends javax.xml.rpc.Service {

/**
 * FinScan Custom Services Interface
 */
    public java.lang.String getFNSServicesLookupSoap12Address();

    public com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_PortType getFNSServicesLookupSoap12() throws javax.xml.rpc.ServiceException;

    public com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_PortType getFNSServicesLookupSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getFNSServicesLookupSoapAddress();

    public com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_PortType getFNSServicesLookupSoap() throws javax.xml.rpc.ServiceException;

    public com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_PortType getFNSServicesLookupSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}

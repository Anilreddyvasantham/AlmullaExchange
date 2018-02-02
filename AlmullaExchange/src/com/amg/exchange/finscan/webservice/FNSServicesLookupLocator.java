/**
 * FNSServicesLookupLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.amg.exchange.finscan.webservice;

public class FNSServicesLookupLocator extends org.apache.axis.client.Service implements com.amg.exchange.finscan.webservice.FNSServicesLookup {

/**
 * FinScan Custom Services Interface
 */

    public FNSServicesLookupLocator() {
    }


    public FNSServicesLookupLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FNSServicesLookupLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FNSServicesLookupSoap12
    private java.lang.String FNSServicesLookupSoap12_address = "http://amlapplsrv/finscan31/FNSServicesLookup.asmx";

    public java.lang.String getFNSServicesLookupSoap12Address() {
        return FNSServicesLookupSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FNSServicesLookupSoap12WSDDServiceName = "FNSServicesLookupSoap12";

    public java.lang.String getFNSServicesLookupSoap12WSDDServiceName() {
        return FNSServicesLookupSoap12WSDDServiceName;
    }

    public void setFNSServicesLookupSoap12WSDDServiceName(java.lang.String name) {
        FNSServicesLookupSoap12WSDDServiceName = name;
    }

    public com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_PortType getFNSServicesLookupSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FNSServicesLookupSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFNSServicesLookupSoap12(endpoint);
    }

    public com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_PortType getFNSServicesLookupSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.amg.exchange.finscan.webservice.FNSServicesLookupSoap12Stub _stub = new com.amg.exchange.finscan.webservice.FNSServicesLookupSoap12Stub(portAddress, this);
            _stub.setPortName(getFNSServicesLookupSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFNSServicesLookupSoap12EndpointAddress(java.lang.String address) {
        FNSServicesLookupSoap12_address = address;
    }


    // Use to get a proxy class for FNSServicesLookupSoap
    private java.lang.String FNSServicesLookupSoap_address = "http://amlapplsrv/finscan31/FNSServicesLookup.asmx";

    public java.lang.String getFNSServicesLookupSoapAddress() {
        return FNSServicesLookupSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FNSServicesLookupSoapWSDDServiceName = "FNSServicesLookupSoap";

    public java.lang.String getFNSServicesLookupSoapWSDDServiceName() {
        return FNSServicesLookupSoapWSDDServiceName;
    }

    public void setFNSServicesLookupSoapWSDDServiceName(java.lang.String name) {
        FNSServicesLookupSoapWSDDServiceName = name;
    }

    public com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_PortType getFNSServicesLookupSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FNSServicesLookupSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFNSServicesLookupSoap(endpoint);
    }

    public com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_PortType getFNSServicesLookupSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_BindingStub _stub = new com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_BindingStub(portAddress, this);
            _stub.setPortName(getFNSServicesLookupSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFNSServicesLookupSoapEndpointAddress(java.lang.String address) {
        FNSServicesLookupSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.amg.exchange.finscan.webservice.FNSServicesLookupSoap12Stub _stub = new com.amg.exchange.finscan.webservice.FNSServicesLookupSoap12Stub(new java.net.URL(FNSServicesLookupSoap12_address), this);
                _stub.setPortName(getFNSServicesLookupSoap12WSDDServiceName());
                return _stub;
            }
            if (com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_BindingStub _stub = new com.amg.exchange.finscan.webservice.FNSServicesLookupSoap_BindingStub(new java.net.URL(FNSServicesLookupSoap_address), this);
                _stub.setPortName(getFNSServicesLookupSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("FNSServicesLookupSoap12".equals(inputPortName)) {
            return getFNSServicesLookupSoap12();
        }
        else if ("FNSServicesLookupSoap".equals(inputPortName)) {
            return getFNSServicesLookupSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://innovativesystems.com/", "FNSServicesLookup");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://innovativesystems.com/", "FNSServicesLookupSoap12"));
            ports.add(new javax.xml.namespace.QName("http://innovativesystems.com/", "FNSServicesLookupSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FNSServicesLookupSoap12".equals(portName)) {
            setFNSServicesLookupSoap12EndpointAddress(address);
        }
        else 
if ("FNSServicesLookupSoap".equals(portName)) {
            setFNSServicesLookupSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

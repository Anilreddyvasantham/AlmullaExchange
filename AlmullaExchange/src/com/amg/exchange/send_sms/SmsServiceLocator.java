/**
 * SmsServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.amg.exchange.send_sms;

public class SmsServiceLocator extends org.apache.axis.client.Service implements SmsService
   {

      public SmsServiceLocator()
	 {
	 }

      public SmsServiceLocator(org.apache.axis.EngineConfiguration config)
	 {
	    super(config);
	 }

      public SmsServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException
	 {
	    super(wsdlLoc, sName);
	 }

      // Use to get a proxy class for SmsServiceSoap
      private java.lang.String SmsServiceSoap_address = "http://secure1.future-club.com/BulkSMSwebserviceV1/SmsService.asmx";

      public java.lang.String getSmsServiceSoapAddress()
	 {
	    return SmsServiceSoap_address;
	 }

      // The WSDD service name defaults to the port name.
      private java.lang.String SmsServiceSoapWSDDServiceName = "SmsServiceSoap";

      public java.lang.String getSmsServiceSoapWSDDServiceName()
	 {
	    return SmsServiceSoapWSDDServiceName;
	 }

      public void setSmsServiceSoapWSDDServiceName(java.lang.String name)
	 {
	    SmsServiceSoapWSDDServiceName = name;
	 }

      public SmsServiceSoap_PortType getSmsServiceSoap() throws javax.xml.rpc.ServiceException
	 {
	    java.net.URL endpoint;
	    try
	       {
		  endpoint = new java.net.URL(SmsServiceSoap_address);
	       }
	    catch (java.net.MalformedURLException e)
	       {
		  throw new javax.xml.rpc.ServiceException(e);
	       }
	    return getSmsServiceSoap(endpoint);
	 }

      public SmsServiceSoap_PortType getSmsServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException
	 {
	    try
	       {
		  SmsServiceSoap_BindingStub _stub = new SmsServiceSoap_BindingStub(portAddress, this);
		  _stub.setPortName(getSmsServiceSoapWSDDServiceName());
		  return _stub;
	       }
	    catch (org.apache.axis.AxisFault e)
	       {
		  return null;
	       }
	 }

      public void setSmsServiceSoapEndpointAddress(java.lang.String address)
	 {
	    SmsServiceSoap_address = address;
	 }

      // Use to get a proxy class for SmsServiceSoap12
      private java.lang.String SmsServiceSoap12_address = "http://secure1.future-club.com/BulkSMSwebserviceV1/SmsService.asmx";

      public java.lang.String getSmsServiceSoap12Address()
	 {
	    return SmsServiceSoap12_address;
	 }

      // The WSDD service name defaults to the port name.
      private java.lang.String SmsServiceSoap12WSDDServiceName = "SmsServiceSoap12";

      public java.lang.String getSmsServiceSoap12WSDDServiceName()
	 {
	    return SmsServiceSoap12WSDDServiceName;
	 }

      public void setSmsServiceSoap12WSDDServiceName(java.lang.String name)
	 {
	    SmsServiceSoap12WSDDServiceName = name;
	 }

      public SmsServiceSoap_PortType getSmsServiceSoap12() throws javax.xml.rpc.ServiceException
	 {
	    java.net.URL endpoint;
	    try
	       {
		  endpoint = new java.net.URL(SmsServiceSoap12_address);
	       }
	    catch (java.net.MalformedURLException e)
	       {
		  throw new javax.xml.rpc.ServiceException(e);
	       }
	    return getSmsServiceSoap12(endpoint);
	 }

      public SmsServiceSoap_PortType getSmsServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException
	 {
	    try
	       {
		  SmsServiceSoap12Stub _stub = new SmsServiceSoap12Stub(portAddress, this);
		  _stub.setPortName(getSmsServiceSoap12WSDDServiceName());
		  return _stub;
	       }
	    catch (org.apache.axis.AxisFault e)
	       {
		  return null;
	       }
	 }

      public void setSmsServiceSoap12EndpointAddress(java.lang.String address)
	 {
	    SmsServiceSoap12_address = address;
	 }

      /**
       * For the given interface, get the stub implementation. If this service has no port for the given interface, then
       * ServiceException is thrown. This service has multiple ports for a given interface; the proxy implementation returned may
       * be indeterminate.
       */
      public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException
	 {
	    try
	       {
		  if (SmsServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface))
		     {
			SmsServiceSoap_BindingStub _stub = new SmsServiceSoap_BindingStub(new java.net.URL(SmsServiceSoap_address), this);
			_stub.setPortName(getSmsServiceSoapWSDDServiceName());
			return _stub;
		     }
		  if (SmsServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface))
		     {
			SmsServiceSoap12Stub _stub = new SmsServiceSoap12Stub(new java.net.URL(SmsServiceSoap12_address), this);
			_stub.setPortName(getSmsServiceSoap12WSDDServiceName());
			return _stub;
		     }
	       }
	    catch (java.lang.Throwable t)
	       {
		  throw new javax.xml.rpc.ServiceException(t);
	       }
	    throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	 }

      /**
       * For the given interface, get the stub implementation. If this service has no port for the given interface, then
       * ServiceException is thrown.
       */
      public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException
	 {
	    if (portName == null)
	       {
		  return getPort(serviceEndpointInterface);
	       }
	    java.lang.String inputPortName = portName.getLocalPart();
	    if ("SmsServiceSoap".equals(inputPortName))
	       {
		  return getSmsServiceSoap();
	       }
	    else if ("SmsServiceSoap12".equals(inputPortName))
	       {
		  return getSmsServiceSoap12();
	       }
	    else
	       {
		  java.rmi.Remote _stub = getPort(serviceEndpointInterface);
		  ((org.apache.axis.client.Stub) _stub).setPortName(portName);
		  return _stub;
	       }
	 }

      public javax.xml.namespace.QName getServiceName()
	 {
	    return new javax.xml.namespace.QName("http://tempuri.org/SmsService/SmsService", "SmsService");
	 }

      private java.util.HashSet ports = null;

      public java.util.Iterator getPorts()
	 {
	    if (ports == null)
	       {
		  ports = new java.util.HashSet();
		  ports.add(new javax.xml.namespace.QName("http://tempuri.org/SmsService/SmsService", "SmsServiceSoap"));
		  ports.add(new javax.xml.namespace.QName("http://tempuri.org/SmsService/SmsService", "SmsServiceSoap12"));
	       }
	    return ports.iterator();
	 }

      /**
       * Set the endpoint address for the specified port name.
       */
      public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException
	 {

	    if ("SmsServiceSoap".equals(portName))
	       {
		  setSmsServiceSoapEndpointAddress(address);
	       }
	    else if ("SmsServiceSoap12".equals(portName))
	       {
		  setSmsServiceSoap12EndpointAddress(address);
	       }
	    else
	       { // Unknown Port Name
		  throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
	       }
	 }

      /**
       * Set the endpoint address for the specified port name.
       */
      public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException
	 {
	    setEndpointAddress(portName.getLocalPart(), address);
	 }

   }

/**
 * FNSServicesLookupResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.amg.exchange.finscan.webservice;

public class FNSServicesLookupResponse  implements java.io.Serializable {
    private com.amg.exchange.finscan.webservice.SLResultTypeEnum status;

    private java.lang.String message;

    private int returned;

    private int notReturned;

    private int resultsCount;

    private int hitCount;

    private int pendingCount;

    private int safeCount;

    private com.amg.exchange.finscan.webservice.SLComplianceRecord[] complianceRecords;

    private java.lang.String clientID;

    private java.lang.String version;

    public FNSServicesLookupResponse() {
    }

    public FNSServicesLookupResponse(
           com.amg.exchange.finscan.webservice.SLResultTypeEnum status,
           java.lang.String message,
           int returned,
           int notReturned,
           int resultsCount,
           int hitCount,
           int pendingCount,
           int safeCount,
           com.amg.exchange.finscan.webservice.SLComplianceRecord[] complianceRecords,
           java.lang.String clientID,
           java.lang.String version) {
           this.status = status;
           this.message = message;
           this.returned = returned;
           this.notReturned = notReturned;
           this.resultsCount = resultsCount;
           this.hitCount = hitCount;
           this.pendingCount = pendingCount;
           this.safeCount = safeCount;
           this.complianceRecords = complianceRecords;
           this.clientID = clientID;
           this.version = version;
    }


    /**
     * Gets the status value for this FNSServicesLookupResponse.
     * 
     * @return status
     */
    public com.amg.exchange.finscan.webservice.SLResultTypeEnum getStatus() {
        return status;
    }


    /**
     * Sets the status value for this FNSServicesLookupResponse.
     * 
     * @param status
     */
    public void setStatus(com.amg.exchange.finscan.webservice.SLResultTypeEnum status) {
        this.status = status;
    }


    /**
     * Gets the message value for this FNSServicesLookupResponse.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this FNSServicesLookupResponse.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the returned value for this FNSServicesLookupResponse.
     * 
     * @return returned
     */
    public int getReturned() {
        return returned;
    }


    /**
     * Sets the returned value for this FNSServicesLookupResponse.
     * 
     * @param returned
     */
    public void setReturned(int returned) {
        this.returned = returned;
    }


    /**
     * Gets the notReturned value for this FNSServicesLookupResponse.
     * 
     * @return notReturned
     */
    public int getNotReturned() {
        return notReturned;
    }


    /**
     * Sets the notReturned value for this FNSServicesLookupResponse.
     * 
     * @param notReturned
     */
    public void setNotReturned(int notReturned) {
        this.notReturned = notReturned;
    }


    /**
     * Gets the resultsCount value for this FNSServicesLookupResponse.
     * 
     * @return resultsCount
     */
    public int getResultsCount() {
        return resultsCount;
    }


    /**
     * Sets the resultsCount value for this FNSServicesLookupResponse.
     * 
     * @param resultsCount
     */
    public void setResultsCount(int resultsCount) {
        this.resultsCount = resultsCount;
    }


    /**
     * Gets the hitCount value for this FNSServicesLookupResponse.
     * 
     * @return hitCount
     */
    public int getHitCount() {
        return hitCount;
    }


    /**
     * Sets the hitCount value for this FNSServicesLookupResponse.
     * 
     * @param hitCount
     */
    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }


    /**
     * Gets the pendingCount value for this FNSServicesLookupResponse.
     * 
     * @return pendingCount
     */
    public int getPendingCount() {
        return pendingCount;
    }


    /**
     * Sets the pendingCount value for this FNSServicesLookupResponse.
     * 
     * @param pendingCount
     */
    public void setPendingCount(int pendingCount) {
        this.pendingCount = pendingCount;
    }


    /**
     * Gets the safeCount value for this FNSServicesLookupResponse.
     * 
     * @return safeCount
     */
    public int getSafeCount() {
        return safeCount;
    }


    /**
     * Sets the safeCount value for this FNSServicesLookupResponse.
     * 
     * @param safeCount
     */
    public void setSafeCount(int safeCount) {
        this.safeCount = safeCount;
    }


    /**
     * Gets the complianceRecords value for this FNSServicesLookupResponse.
     * 
     * @return complianceRecords
     */
    public com.amg.exchange.finscan.webservice.SLComplianceRecord[] getComplianceRecords() {
        return complianceRecords;
    }


    /**
     * Sets the complianceRecords value for this FNSServicesLookupResponse.
     * 
     * @param complianceRecords
     */
    public void setComplianceRecords(com.amg.exchange.finscan.webservice.SLComplianceRecord[] complianceRecords) {
        this.complianceRecords = complianceRecords;
    }


    /**
     * Gets the clientID value for this FNSServicesLookupResponse.
     * 
     * @return clientID
     */
    public java.lang.String getClientID() {
        return clientID;
    }


    /**
     * Sets the clientID value for this FNSServicesLookupResponse.
     * 
     * @param clientID
     */
    public void setClientID(java.lang.String clientID) {
        this.clientID = clientID;
    }


    /**
     * Gets the version value for this FNSServicesLookupResponse.
     * 
     * @return version
     */
    public java.lang.String getVersion() {
        return version;
    }


    /**
     * Sets the version value for this FNSServicesLookupResponse.
     * 
     * @param version
     */
    public void setVersion(java.lang.String version) {
        this.version = version;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FNSServicesLookupResponse)) return false;
        FNSServicesLookupResponse other = (FNSServicesLookupResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            this.returned == other.getReturned() &&
            this.notReturned == other.getNotReturned() &&
            this.resultsCount == other.getResultsCount() &&
            this.hitCount == other.getHitCount() &&
            this.pendingCount == other.getPendingCount() &&
            this.safeCount == other.getSafeCount() &&
            ((this.complianceRecords==null && other.getComplianceRecords()==null) || 
             (this.complianceRecords!=null &&
              java.util.Arrays.equals(this.complianceRecords, other.getComplianceRecords()))) &&
            ((this.clientID==null && other.getClientID()==null) || 
             (this.clientID!=null &&
              this.clientID.equals(other.getClientID()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion())));
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
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        _hashCode += getReturned();
        _hashCode += getNotReturned();
        _hashCode += getResultsCount();
        _hashCode += getHitCount();
        _hashCode += getPendingCount();
        _hashCode += getSafeCount();
        if (getComplianceRecords() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getComplianceRecords());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getComplianceRecords(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getClientID() != null) {
            _hashCode += getClientID().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FNSServicesLookupResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "FNSServicesLookupResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLResultTypeEnum"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("returned");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "returned"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notReturned");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "notReturned"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultsCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "resultsCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hitCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "hitCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pendingCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "pendingCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("safeCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "safeCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complianceRecords");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "complianceRecords"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLComplianceRecord"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLComplianceRecord"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "clientID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "version"));
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

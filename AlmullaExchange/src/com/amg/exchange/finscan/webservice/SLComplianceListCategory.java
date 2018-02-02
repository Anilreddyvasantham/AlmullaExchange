/**
 * SLComplianceListCategory.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.amg.exchange.finscan.webservice;

public class SLComplianceListCategory  implements java.io.Serializable {
    private java.lang.String categoryName;

    private com.amg.exchange.finscan.webservice.SLYesNoEnum isSelected;

    private com.amg.exchange.finscan.webservice.SLYesNoEnum isMandatory;

    public SLComplianceListCategory() {
    }

    public SLComplianceListCategory(
           java.lang.String categoryName,
           com.amg.exchange.finscan.webservice.SLYesNoEnum isSelected,
           com.amg.exchange.finscan.webservice.SLYesNoEnum isMandatory) {
           this.categoryName = categoryName;
           this.isSelected = isSelected;
           this.isMandatory = isMandatory;
    }


    /**
     * Gets the categoryName value for this SLComplianceListCategory.
     * 
     * @return categoryName
     */
    public java.lang.String getCategoryName() {
        return categoryName;
    }


    /**
     * Sets the categoryName value for this SLComplianceListCategory.
     * 
     * @param categoryName
     */
    public void setCategoryName(java.lang.String categoryName) {
        this.categoryName = categoryName;
    }


    /**
     * Gets the isSelected value for this SLComplianceListCategory.
     * 
     * @return isSelected
     */
    public com.amg.exchange.finscan.webservice.SLYesNoEnum getIsSelected() {
        return isSelected;
    }


    /**
     * Sets the isSelected value for this SLComplianceListCategory.
     * 
     * @param isSelected
     */
    public void setIsSelected(com.amg.exchange.finscan.webservice.SLYesNoEnum isSelected) {
        this.isSelected = isSelected;
    }


    /**
     * Gets the isMandatory value for this SLComplianceListCategory.
     * 
     * @return isMandatory
     */
    public com.amg.exchange.finscan.webservice.SLYesNoEnum getIsMandatory() {
        return isMandatory;
    }


    /**
     * Sets the isMandatory value for this SLComplianceListCategory.
     * 
     * @param isMandatory
     */
    public void setIsMandatory(com.amg.exchange.finscan.webservice.SLYesNoEnum isMandatory) {
        this.isMandatory = isMandatory;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SLComplianceListCategory)) return false;
        SLComplianceListCategory other = (SLComplianceListCategory) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.categoryName==null && other.getCategoryName()==null) || 
             (this.categoryName!=null &&
              this.categoryName.equals(other.getCategoryName()))) &&
            ((this.isSelected==null && other.getIsSelected()==null) || 
             (this.isSelected!=null &&
              this.isSelected.equals(other.getIsSelected()))) &&
            ((this.isMandatory==null && other.getIsMandatory()==null) || 
             (this.isMandatory!=null &&
              this.isMandatory.equals(other.getIsMandatory())));
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
        if (getCategoryName() != null) {
            _hashCode += getCategoryName().hashCode();
        }
        if (getIsSelected() != null) {
            _hashCode += getIsSelected().hashCode();
        }
        if (getIsMandatory() != null) {
            _hashCode += getIsMandatory().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SLComplianceListCategory.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLComplianceListCategory"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoryName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "categoryName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isSelected");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "isSelected"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLYesNoEnum"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isMandatory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "isMandatory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLYesNoEnum"));
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

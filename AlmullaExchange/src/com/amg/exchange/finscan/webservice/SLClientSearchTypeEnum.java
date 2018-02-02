/**
 * SLClientSearchTypeEnum.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.amg.exchange.finscan.webservice;

public class SLClientSearchTypeEnum implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SLClientSearchTypeEnum(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _FinScanFullName = "FinScanFullName";
    public static final java.lang.String _EqualWithWildcardedInitials = "EqualWithWildcardedInitials";
    public static final java.lang.String _PartialOnly = "PartialOnly";
    public static final java.lang.String _SoundexOnly = "SoundexOnly";
    public static final java.lang.String _PartialAndSoundex = "PartialAndSoundex";
    public static final java.lang.String _IterativeSearch = "IterativeSearch";
    public static final SLClientSearchTypeEnum FinScanFullName = new SLClientSearchTypeEnum(_FinScanFullName);
    public static final SLClientSearchTypeEnum EqualWithWildcardedInitials = new SLClientSearchTypeEnum(_EqualWithWildcardedInitials);
    public static final SLClientSearchTypeEnum PartialOnly = new SLClientSearchTypeEnum(_PartialOnly);
    public static final SLClientSearchTypeEnum SoundexOnly = new SLClientSearchTypeEnum(_SoundexOnly);
    public static final SLClientSearchTypeEnum PartialAndSoundex = new SLClientSearchTypeEnum(_PartialAndSoundex);
    public static final SLClientSearchTypeEnum IterativeSearch = new SLClientSearchTypeEnum(_IterativeSearch);
    public java.lang.String getValue() { return _value_;}
    public static SLClientSearchTypeEnum fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        SLClientSearchTypeEnum enumeration = (SLClientSearchTypeEnum)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SLClientSearchTypeEnum fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SLClientSearchTypeEnum.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLClientSearchTypeEnum"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}

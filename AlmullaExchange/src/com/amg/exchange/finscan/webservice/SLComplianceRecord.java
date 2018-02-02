/**
 * SLComplianceRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.amg.exchange.finscan.webservice;

public class SLComplianceRecord  implements java.io.Serializable {
    private java.lang.String status;

    private java.lang.String reason;

    private java.lang.String comments;

    private java.lang.String clientSourceName;

    private java.lang.String clientSourceCode;

    private java.lang.String clientSourceID;

    private java.lang.String complistName;

    private java.lang.String complistListCode;

    private java.lang.String complistListID;

    private java.lang.String complistID;

    private com.amg.exchange.finscan.webservice.SLYesNoEnum enmupdated;

    private com.amg.exchange.finscan.webservice.SLYesNoEnum enmreportedon;

    private java.lang.String nameline;

    private java.lang.String rank;

    private java.lang.String ranktype;

    private java.lang.String rankweight;

    private java.lang.String origin;

    private java.lang.String version;

    private java.lang.String secondsviewed;

    private java.lang.String parentOrAlias;

    private java.lang.String linkSingleStringName;

    private com.amg.exchange.finscan.webservice.SLYesNoEnum enmCommentsOnlyChange;

    public SLComplianceRecord() {
    }

    public SLComplianceRecord(
           java.lang.String status,
           java.lang.String reason,
           java.lang.String comments,
           java.lang.String clientSourceName,
           java.lang.String clientSourceCode,
           java.lang.String clientSourceID,
           java.lang.String complistName,
           java.lang.String complistListCode,
           java.lang.String complistListID,
           java.lang.String complistID,
           com.amg.exchange.finscan.webservice.SLYesNoEnum enmupdated,
           com.amg.exchange.finscan.webservice.SLYesNoEnum enmreportedon,
           java.lang.String nameline,
           java.lang.String rank,
           java.lang.String ranktype,
           java.lang.String rankweight,
           java.lang.String origin,
           java.lang.String version,
           java.lang.String secondsviewed,
           java.lang.String parentOrAlias,
           java.lang.String linkSingleStringName,
           com.amg.exchange.finscan.webservice.SLYesNoEnum enmCommentsOnlyChange) {
           this.status = status;
           this.reason = reason;
           this.comments = comments;
           this.clientSourceName = clientSourceName;
           this.clientSourceCode = clientSourceCode;
           this.clientSourceID = clientSourceID;
           this.complistName = complistName;
           this.complistListCode = complistListCode;
           this.complistListID = complistListID;
           this.complistID = complistID;
           this.enmupdated = enmupdated;
           this.enmreportedon = enmreportedon;
           this.nameline = nameline;
           this.rank = rank;
           this.ranktype = ranktype;
           this.rankweight = rankweight;
           this.origin = origin;
           this.version = version;
           this.secondsviewed = secondsviewed;
           this.parentOrAlias = parentOrAlias;
           this.linkSingleStringName = linkSingleStringName;
           this.enmCommentsOnlyChange = enmCommentsOnlyChange;
    }


    /**
     * Gets the status value for this SLComplianceRecord.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this SLComplianceRecord.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the reason value for this SLComplianceRecord.
     * 
     * @return reason
     */
    public java.lang.String getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this SLComplianceRecord.
     * 
     * @param reason
     */
    public void setReason(java.lang.String reason) {
        this.reason = reason;
    }


    /**
     * Gets the comments value for this SLComplianceRecord.
     * 
     * @return comments
     */
    public java.lang.String getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this SLComplianceRecord.
     * 
     * @param comments
     */
    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }


    /**
     * Gets the clientSourceName value for this SLComplianceRecord.
     * 
     * @return clientSourceName
     */
    public java.lang.String getClientSourceName() {
        return clientSourceName;
    }


    /**
     * Sets the clientSourceName value for this SLComplianceRecord.
     * 
     * @param clientSourceName
     */
    public void setClientSourceName(java.lang.String clientSourceName) {
        this.clientSourceName = clientSourceName;
    }


    /**
     * Gets the clientSourceCode value for this SLComplianceRecord.
     * 
     * @return clientSourceCode
     */
    public java.lang.String getClientSourceCode() {
        return clientSourceCode;
    }


    /**
     * Sets the clientSourceCode value for this SLComplianceRecord.
     * 
     * @param clientSourceCode
     */
    public void setClientSourceCode(java.lang.String clientSourceCode) {
        this.clientSourceCode = clientSourceCode;
    }


    /**
     * Gets the clientSourceID value for this SLComplianceRecord.
     * 
     * @return clientSourceID
     */
    public java.lang.String getClientSourceID() {
        return clientSourceID;
    }


    /**
     * Sets the clientSourceID value for this SLComplianceRecord.
     * 
     * @param clientSourceID
     */
    public void setClientSourceID(java.lang.String clientSourceID) {
        this.clientSourceID = clientSourceID;
    }


    /**
     * Gets the complistName value for this SLComplianceRecord.
     * 
     * @return complistName
     */
    public java.lang.String getComplistName() {
        return complistName;
    }


    /**
     * Sets the complistName value for this SLComplianceRecord.
     * 
     * @param complistName
     */
    public void setComplistName(java.lang.String complistName) {
        this.complistName = complistName;
    }


    /**
     * Gets the complistListCode value for this SLComplianceRecord.
     * 
     * @return complistListCode
     */
    public java.lang.String getComplistListCode() {
        return complistListCode;
    }


    /**
     * Sets the complistListCode value for this SLComplianceRecord.
     * 
     * @param complistListCode
     */
    public void setComplistListCode(java.lang.String complistListCode) {
        this.complistListCode = complistListCode;
    }


    /**
     * Gets the complistListID value for this SLComplianceRecord.
     * 
     * @return complistListID
     */
    public java.lang.String getComplistListID() {
        return complistListID;
    }


    /**
     * Sets the complistListID value for this SLComplianceRecord.
     * 
     * @param complistListID
     */
    public void setComplistListID(java.lang.String complistListID) {
        this.complistListID = complistListID;
    }


    /**
     * Gets the complistID value for this SLComplianceRecord.
     * 
     * @return complistID
     */
    public java.lang.String getComplistID() {
        return complistID;
    }


    /**
     * Sets the complistID value for this SLComplianceRecord.
     * 
     * @param complistID
     */
    public void setComplistID(java.lang.String complistID) {
        this.complistID = complistID;
    }


    /**
     * Gets the enmupdated value for this SLComplianceRecord.
     * 
     * @return enmupdated
     */
    public com.amg.exchange.finscan.webservice.SLYesNoEnum getEnmupdated() {
        return enmupdated;
    }


    /**
     * Sets the enmupdated value for this SLComplianceRecord.
     * 
     * @param enmupdated
     */
    public void setEnmupdated(com.amg.exchange.finscan.webservice.SLYesNoEnum enmupdated) {
        this.enmupdated = enmupdated;
    }


    /**
     * Gets the enmreportedon value for this SLComplianceRecord.
     * 
     * @return enmreportedon
     */
    public com.amg.exchange.finscan.webservice.SLYesNoEnum getEnmreportedon() {
        return enmreportedon;
    }


    /**
     * Sets the enmreportedon value for this SLComplianceRecord.
     * 
     * @param enmreportedon
     */
    public void setEnmreportedon(com.amg.exchange.finscan.webservice.SLYesNoEnum enmreportedon) {
        this.enmreportedon = enmreportedon;
    }


    /**
     * Gets the nameline value for this SLComplianceRecord.
     * 
     * @return nameline
     */
    public java.lang.String getNameline() {
        return nameline;
    }


    /**
     * Sets the nameline value for this SLComplianceRecord.
     * 
     * @param nameline
     */
    public void setNameline(java.lang.String nameline) {
        this.nameline = nameline;
    }


    /**
     * Gets the rank value for this SLComplianceRecord.
     * 
     * @return rank
     */
    public java.lang.String getRank() {
        return rank;
    }


    /**
     * Sets the rank value for this SLComplianceRecord.
     * 
     * @param rank
     */
    public void setRank(java.lang.String rank) {
        this.rank = rank;
    }


    /**
     * Gets the ranktype value for this SLComplianceRecord.
     * 
     * @return ranktype
     */
    public java.lang.String getRanktype() {
        return ranktype;
    }


    /**
     * Sets the ranktype value for this SLComplianceRecord.
     * 
     * @param ranktype
     */
    public void setRanktype(java.lang.String ranktype) {
        this.ranktype = ranktype;
    }


    /**
     * Gets the rankweight value for this SLComplianceRecord.
     * 
     * @return rankweight
     */
    public java.lang.String getRankweight() {
        return rankweight;
    }


    /**
     * Sets the rankweight value for this SLComplianceRecord.
     * 
     * @param rankweight
     */
    public void setRankweight(java.lang.String rankweight) {
        this.rankweight = rankweight;
    }


    /**
     * Gets the origin value for this SLComplianceRecord.
     * 
     * @return origin
     */
    public java.lang.String getOrigin() {
        return origin;
    }


    /**
     * Sets the origin value for this SLComplianceRecord.
     * 
     * @param origin
     */
    public void setOrigin(java.lang.String origin) {
        this.origin = origin;
    }


    /**
     * Gets the version value for this SLComplianceRecord.
     * 
     * @return version
     */
    public java.lang.String getVersion() {
        return version;
    }


    /**
     * Sets the version value for this SLComplianceRecord.
     * 
     * @param version
     */
    public void setVersion(java.lang.String version) {
        this.version = version;
    }


    /**
     * Gets the secondsviewed value for this SLComplianceRecord.
     * 
     * @return secondsviewed
     */
    public java.lang.String getSecondsviewed() {
        return secondsviewed;
    }


    /**
     * Sets the secondsviewed value for this SLComplianceRecord.
     * 
     * @param secondsviewed
     */
    public void setSecondsviewed(java.lang.String secondsviewed) {
        this.secondsviewed = secondsviewed;
    }


    /**
     * Gets the parentOrAlias value for this SLComplianceRecord.
     * 
     * @return parentOrAlias
     */
    public java.lang.String getParentOrAlias() {
        return parentOrAlias;
    }


    /**
     * Sets the parentOrAlias value for this SLComplianceRecord.
     * 
     * @param parentOrAlias
     */
    public void setParentOrAlias(java.lang.String parentOrAlias) {
        this.parentOrAlias = parentOrAlias;
    }


    /**
     * Gets the linkSingleStringName value for this SLComplianceRecord.
     * 
     * @return linkSingleStringName
     */
    public java.lang.String getLinkSingleStringName() {
        return linkSingleStringName;
    }


    /**
     * Sets the linkSingleStringName value for this SLComplianceRecord.
     * 
     * @param linkSingleStringName
     */
    public void setLinkSingleStringName(java.lang.String linkSingleStringName) {
        this.linkSingleStringName = linkSingleStringName;
    }


    /**
     * Gets the enmCommentsOnlyChange value for this SLComplianceRecord.
     * 
     * @return enmCommentsOnlyChange
     */
    public com.amg.exchange.finscan.webservice.SLYesNoEnum getEnmCommentsOnlyChange() {
        return enmCommentsOnlyChange;
    }


    /**
     * Sets the enmCommentsOnlyChange value for this SLComplianceRecord.
     * 
     * @param enmCommentsOnlyChange
     */
    public void setEnmCommentsOnlyChange(com.amg.exchange.finscan.webservice.SLYesNoEnum enmCommentsOnlyChange) {
        this.enmCommentsOnlyChange = enmCommentsOnlyChange;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SLComplianceRecord)) return false;
        SLComplianceRecord other = (SLComplianceRecord) obj;
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
            ((this.reason==null && other.getReason()==null) || 
             (this.reason!=null &&
              this.reason.equals(other.getReason()))) &&
            ((this.comments==null && other.getComments()==null) || 
             (this.comments!=null &&
              this.comments.equals(other.getComments()))) &&
            ((this.clientSourceName==null && other.getClientSourceName()==null) || 
             (this.clientSourceName!=null &&
              this.clientSourceName.equals(other.getClientSourceName()))) &&
            ((this.clientSourceCode==null && other.getClientSourceCode()==null) || 
             (this.clientSourceCode!=null &&
              this.clientSourceCode.equals(other.getClientSourceCode()))) &&
            ((this.clientSourceID==null && other.getClientSourceID()==null) || 
             (this.clientSourceID!=null &&
              this.clientSourceID.equals(other.getClientSourceID()))) &&
            ((this.complistName==null && other.getComplistName()==null) || 
             (this.complistName!=null &&
              this.complistName.equals(other.getComplistName()))) &&
            ((this.complistListCode==null && other.getComplistListCode()==null) || 
             (this.complistListCode!=null &&
              this.complistListCode.equals(other.getComplistListCode()))) &&
            ((this.complistListID==null && other.getComplistListID()==null) || 
             (this.complistListID!=null &&
              this.complistListID.equals(other.getComplistListID()))) &&
            ((this.complistID==null && other.getComplistID()==null) || 
             (this.complistID!=null &&
              this.complistID.equals(other.getComplistID()))) &&
            ((this.enmupdated==null && other.getEnmupdated()==null) || 
             (this.enmupdated!=null &&
              this.enmupdated.equals(other.getEnmupdated()))) &&
            ((this.enmreportedon==null && other.getEnmreportedon()==null) || 
             (this.enmreportedon!=null &&
              this.enmreportedon.equals(other.getEnmreportedon()))) &&
            ((this.nameline==null && other.getNameline()==null) || 
             (this.nameline!=null &&
              this.nameline.equals(other.getNameline()))) &&
            ((this.rank==null && other.getRank()==null) || 
             (this.rank!=null &&
              this.rank.equals(other.getRank()))) &&
            ((this.ranktype==null && other.getRanktype()==null) || 
             (this.ranktype!=null &&
              this.ranktype.equals(other.getRanktype()))) &&
            ((this.rankweight==null && other.getRankweight()==null) || 
             (this.rankweight!=null &&
              this.rankweight.equals(other.getRankweight()))) &&
            ((this.origin==null && other.getOrigin()==null) || 
             (this.origin!=null &&
              this.origin.equals(other.getOrigin()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion()))) &&
            ((this.secondsviewed==null && other.getSecondsviewed()==null) || 
             (this.secondsviewed!=null &&
              this.secondsviewed.equals(other.getSecondsviewed()))) &&
            ((this.parentOrAlias==null && other.getParentOrAlias()==null) || 
             (this.parentOrAlias!=null &&
              this.parentOrAlias.equals(other.getParentOrAlias()))) &&
            ((this.linkSingleStringName==null && other.getLinkSingleStringName()==null) || 
             (this.linkSingleStringName!=null &&
              this.linkSingleStringName.equals(other.getLinkSingleStringName()))) &&
            ((this.enmCommentsOnlyChange==null && other.getEnmCommentsOnlyChange()==null) || 
             (this.enmCommentsOnlyChange!=null &&
              this.enmCommentsOnlyChange.equals(other.getEnmCommentsOnlyChange())));
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
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        if (getComments() != null) {
            _hashCode += getComments().hashCode();
        }
        if (getClientSourceName() != null) {
            _hashCode += getClientSourceName().hashCode();
        }
        if (getClientSourceCode() != null) {
            _hashCode += getClientSourceCode().hashCode();
        }
        if (getClientSourceID() != null) {
            _hashCode += getClientSourceID().hashCode();
        }
        if (getComplistName() != null) {
            _hashCode += getComplistName().hashCode();
        }
        if (getComplistListCode() != null) {
            _hashCode += getComplistListCode().hashCode();
        }
        if (getComplistListID() != null) {
            _hashCode += getComplistListID().hashCode();
        }
        if (getComplistID() != null) {
            _hashCode += getComplistID().hashCode();
        }
        if (getEnmupdated() != null) {
            _hashCode += getEnmupdated().hashCode();
        }
        if (getEnmreportedon() != null) {
            _hashCode += getEnmreportedon().hashCode();
        }
        if (getNameline() != null) {
            _hashCode += getNameline().hashCode();
        }
        if (getRank() != null) {
            _hashCode += getRank().hashCode();
        }
        if (getRanktype() != null) {
            _hashCode += getRanktype().hashCode();
        }
        if (getRankweight() != null) {
            _hashCode += getRankweight().hashCode();
        }
        if (getOrigin() != null) {
            _hashCode += getOrigin().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        if (getSecondsviewed() != null) {
            _hashCode += getSecondsviewed().hashCode();
        }
        if (getParentOrAlias() != null) {
            _hashCode += getParentOrAlias().hashCode();
        }
        if (getLinkSingleStringName() != null) {
            _hashCode += getLinkSingleStringName().hashCode();
        }
        if (getEnmCommentsOnlyChange() != null) {
            _hashCode += getEnmCommentsOnlyChange().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SLComplianceRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLComplianceRecord"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "reason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientSourceName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "clientSourceName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientSourceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "clientSourceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientSourceID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "clientSourceID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complistName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "complistName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complistListCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "complistListCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complistListID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "complistListID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complistID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "complistID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enmupdated");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "enmupdated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLYesNoEnum"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enmreportedon");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "enmreportedon"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://innovativesystems.com/", "SLYesNoEnum"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameline");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "nameline"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rank");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "rank"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ranktype");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "ranktype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rankweight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "rankweight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("origin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "origin"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secondsviewed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "secondsviewed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentOrAlias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "parentOrAlias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linkSingleStringName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "linkSingleStringName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enmCommentsOnlyChange");
        elemField.setXmlName(new javax.xml.namespace.QName("http://innovativesystems.com/", "enmCommentsOnlyChange"));
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

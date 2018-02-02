package com.amg.exchange.common.model;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_AVAIL_OBJECTS")
public class AvailableDatabaseObjects implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal ObjectId;
	private String objectName;
	private String objectType;
	private Date createdDate;

	@Id
	@Column(name = "AVAIL_OBJECTS_ID")
	public BigDecimal getObjectId() {
		return ObjectId;
	}

	public void setObjectId(BigDecimal objectId) {
		ObjectId = objectId;
	}

	@Column(name = "OBJECT_NAME")
	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	@Column(name = "OBJECT_TYPE")
	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	@Column(name = "CREATED")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}

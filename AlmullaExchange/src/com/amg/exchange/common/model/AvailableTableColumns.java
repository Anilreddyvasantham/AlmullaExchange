package com.amg.exchange.common.model;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_AVAIL_OBJECTS_COLUMNS")
public class AvailableTableColumns implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private BigDecimal ObjectColumnId;
	private String tableName;
	private String columnId;
	private String columnName;
	private String dataType;
	private BigDecimal dataLength;
	private String nullable;

	@Id
	@Column(name = "AVAIL_OBJECTS_COL_ID")
	public BigDecimal getObjectColumnId() {
		return ObjectColumnId;
	}

	public void setObjectColumnId(BigDecimal objectColumnId) {
		ObjectColumnId = objectColumnId;
	}

	@Column(name = "TABLE_NAME")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "COLUMN_ID")
	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	@Column(name = "COLUMN_NAME")
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Column(name = "DATA_TYPE")
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name = "DATA_LENGTH")
	public BigDecimal getDataLength() {
		return dataLength;
	}

	public void setDataLength(BigDecimal dataLength) {
		this.dataLength = dataLength;
	}

	@Column(name = "NULLABLE")
	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
}

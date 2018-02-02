package com.amg.exchange.treasury.model;
// default package
// Generated Jul 10, 2014 5:34:26  Created by Chennai ODC

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * ExSupplierInstructionDetail Created by Chennai ODC
 */
@Entity
@Table(name = "EX_SUPPLIER_INSTRUCTION_DETAIL")
public class SupplierInstructionDetail implements Serializable {

	/**
	 * Generated Serialzable UID
	 */
	private static final long serialVersionUID = -8495421843887401468L;
	private BigDecimal supplierInstDetailId;
	private SupplierInstructionMaster exSupplierInstructionMaster;
	private short msglno;
	private String msgdes;
	private String isstatus;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;

	public SupplierInstructionDetail() {
	}

	public SupplierInstructionDetail(BigDecimal supplierInstDetailId,
			SupplierInstructionMaster exSupplierInstructionMaster,
			short msglno) {
		this.supplierInstDetailId = supplierInstDetailId;
		this.exSupplierInstructionMaster = exSupplierInstructionMaster;
		this.msglno = msglno;
	}

	public SupplierInstructionDetail(BigDecimal supplierInstDetailId,
			SupplierInstructionMaster exSupplierInstructionMaster,
			short msglno, String msgdes, String isstatus, String createdBy,
			Date createdDate, String modifiedBy,
			Date modifiedDate) {
		this.supplierInstDetailId = supplierInstDetailId;
		this.exSupplierInstructionMaster = exSupplierInstructionMaster;
		this.msglno = msglno;
		this.msgdes = msgdes;
		this.isstatus = isstatus;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
	
	@Id
	@GeneratedValue(generator="ex_suppl_instruct_deta_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_suppl_instruct_deta_seq",sequenceName="EX_SUPPL_INSTRUCT_DETA_SEQ",allocationSize=1)
	@Column(name = "SUPPLIER_INST_DETAIL_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getSupplierInstDetailId() {
		return this.supplierInstDetailId;
	}

	public void setSupplierInstDetailId(BigDecimal supplierInstDetailId) {
		this.supplierInstDetailId = supplierInstDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPPLIER_INST_ID", nullable = false)
	public SupplierInstructionMaster getExSupplierInstructionMaster() {
		return this.exSupplierInstructionMaster;
	}

	public void setExSupplierInstructionMaster(
			SupplierInstructionMaster exSupplierInstructionMaster) {
		this.exSupplierInstructionMaster = exSupplierInstructionMaster;
	}

	@Column(name = "MSGLNO", nullable = false, precision = 4, scale = 0)
	public short getMsglno() {
		return this.msglno;
	}

	public void setMsglno(short msglno) {
		this.msglno = msglno;
	}

	@Column(name = "MSGDES", length = 80)
	public String getMsgdes() {
		return this.msgdes;
	}

	public void setMsgdes(String msgdes) {
		this.msgdes = msgdes;
	}

	@Column(name = "ISSTATUS", length = 1)
	public String getIsstatus() {
		return this.isstatus;
	}

	public void setIsstatus(String isstatus) {
		this.isstatus = isstatus;
	}

	@Column(name = "CREATED_BY", length = 15)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 15)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}

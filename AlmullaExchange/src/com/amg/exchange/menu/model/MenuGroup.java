package com.amg.exchange.menu.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EX_MENU_GROUP")
public class MenuGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal menuGroupId;
	private String menuGroupCode;
	private String englishDescription;
	private String localDescription;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private String icon;

	public MenuGroup() {

	}

	public MenuGroup(BigDecimal menuGroupId, String menuGroupCode, String englishDescription, String localDescription, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String isActive, String icon) {
		super();
		this.menuGroupId = menuGroupId;
		this.menuGroupCode = menuGroupCode;
		this.englishDescription = englishDescription;
		this.localDescription = localDescription;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isActive = isActive;
		this.icon = icon;
	}

	@Id
	@GeneratedValue(generator = "ex_menu_group_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_menu_group_seq", sequenceName = "EX_MENU_GROUP_SEQ", allocationSize = 1)
	@Column(name = "MENU_GROUP_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getMenuGroupId() {
		return menuGroupId;
	}

	public void setMenuGroupId(BigDecimal menuGroupId) {
		this.menuGroupId = menuGroupId;
	}

	@Column(name = "MENU_GROUP_CODE")
	public String getMenuGroupCode() {
		return menuGroupCode;
	}

	public void setMenuGroupCode(String menuGroupCode) {
		this.menuGroupCode = menuGroupCode;
	}

	@Column(name = "MENU_GROUP_ENG_DESC")
	public String getEnglishDescription() {
		return englishDescription;
	}

	public void setEnglishDescription(String englishDescription) {
		this.englishDescription = englishDescription;
	}

	@Column(name = "MENU_GROUP_LOC_DESC")
	public String getLocalDescription() {
		return localDescription;
	}

	public void setLocalDescription(String localDescription) {
		this.localDescription = localDescription;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "ICON")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}

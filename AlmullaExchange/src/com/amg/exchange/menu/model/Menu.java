package com.amg.exchange.menu.model;

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

@Entity
@Table(name = "EX_MENU")
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal menuId;
	private String menuCode;
	private String englishDescription;
	private String localDescription;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String pagePath;
	private MenuGroup menuGroupId;
	private String isActive;
	private String icon;

	public Menu() {

	}

	public Menu(BigDecimal menuId, String menuCode, String englishDescription, String localDescription, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String pagePath, MenuGroup menuGroupId, String isActive, String icon) {
		this.menuId = menuId;
		this.menuCode = menuCode;
		this.englishDescription = englishDescription;
		this.localDescription = localDescription;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.pagePath = pagePath;
		this.menuGroupId = menuGroupId;
		this.isActive = isActive;
		this.icon = icon;
	}

	@Id
	@GeneratedValue(generator = "ex_menu_group_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_menu_group_seq", sequenceName = "EX_MENU_GROUP_SEQ", allocationSize = 1)
	@Column(name = "MENU_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getMenuId() {
		return menuId;
	}

	public void setMenuId(BigDecimal menuId) {
		this.menuId = menuId;
	}

	@Column(name = "MENU_CODE")
	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	@Column(name = "MENU_ENG_DESC")
	public String getEnglishDescription() {
		return englishDescription;
	}

	public void setEnglishDescription(String englishDescription) {
		this.englishDescription = englishDescription;
	}

	@Column(name = "MENU_LOC_DESC")
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

	@Column(name = "PAGE_PATH")
	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MENU_GROUP_ID")
	public MenuGroup getMenuGroupId() {
		return menuGroupId;
	}

	public void setMenuGroupId(MenuGroup menuGroupId) {
		this.menuGroupId = menuGroupId;
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

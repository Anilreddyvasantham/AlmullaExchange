package com.amg.exchange.menu.model;

import java.io.Serializable;
import java.math.BigDecimal;

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

import com.amg.exchange.registration.model.RoleMaster;

@Entity
@Table(name = "EX_ROLEWISE_MENU")
public class RoleWiseMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal roleWiseMenuId;
	private Menu menuId;
	private MenuGroup menuGroupId;
	private RoleMaster roleId;
	private String isRequired;
	private BigDecimal menuOrder;

	public RoleWiseMenu() {

	}

	public RoleWiseMenu(BigDecimal roleWiseMenuId, Menu menuId, MenuGroup menuGroupId, RoleMaster roleId, String isRequired, BigDecimal menuOrder) {
		super();
		this.roleWiseMenuId = roleWiseMenuId;
		this.menuId = menuId;
		this.menuGroupId = menuGroupId;
		this.roleId = roleId;
		this.isRequired = isRequired;
		this.menuOrder = menuOrder;
	}

	@Id
	@GeneratedValue(generator = "ex_rolewise_menu_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_rolewise_menu_seq", sequenceName = "EX_ROLEWISE_MENU_SEQ", allocationSize = 1)
	@Column(name = "ROLEWISE_MENU_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getRoleWiseMenuId() {
		return roleWiseMenuId;
	}

	public void setRoleWiseMenuId(BigDecimal roleWiseMenuId) {
		this.roleWiseMenuId = roleWiseMenuId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MENU_ID")
	public Menu getMenuId() {
		return menuId;
	}

	public void setMenuId(Menu menuId) {
		this.menuId = menuId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MENU_GROUP_ID")
	public MenuGroup getMenuGroupId() {
		return menuGroupId;
	}

	public void setMenuGroupId(MenuGroup menuGroupId) {
		this.menuGroupId = menuGroupId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	public RoleMaster getRoleId() {
		return roleId;
	}

	public void setRoleId(RoleMaster roleId) {
		this.roleId = roleId;
	}

	@Column(name = "IS_REQUIRED")
	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	@Column(name = "MENU_ORDER")
	public BigDecimal getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(BigDecimal menuOrder) {
		this.menuOrder = menuOrder;
	}

}

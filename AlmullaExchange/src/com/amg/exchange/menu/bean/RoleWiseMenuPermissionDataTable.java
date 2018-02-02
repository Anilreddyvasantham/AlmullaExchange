/**
 * 
 */
package com.amg.exchange.menu.bean;

import java.math.BigDecimal;

/**
 * @author Subramaniam
 * 
 */
public class RoleWiseMenuPermissionDataTable {

	private BigDecimal roleWiseMenuId;
	private BigDecimal menuId;
	private BigDecimal menuGroupId;
	private BigDecimal roleId;
	private String isRequired;
	private BigDecimal menuOrder;

	private String menuDescription;
	private String menuGroupDescription;
	private String roleName;
	
	private Boolean selectedrecord;

	public BigDecimal getRoleWiseMenuId() {
		return roleWiseMenuId;
	}

	public void setRoleWiseMenuId(BigDecimal roleWiseMenuId) {
		this.roleWiseMenuId = roleWiseMenuId;
	}

	public BigDecimal getMenuId() {
		return menuId;
	}

	public void setMenuId(BigDecimal menuId) {
		this.menuId = menuId;
	}

	public BigDecimal getMenuGroupId() {
		return menuGroupId;
	}

	public void setMenuGroupId(BigDecimal menuGroupId) {
		this.menuGroupId = menuGroupId;
	}

	public BigDecimal getRoleId() {
		return roleId;
	}

	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public BigDecimal getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(BigDecimal menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getMenuDescription() {
		return menuDescription;
	}

	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}

	public String getMenuGroupDescription() {
		return menuGroupDescription;
	}

	public void setMenuGroupDescription(String menuGroupDescription) {
		this.menuGroupDescription = menuGroupDescription;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getSelectedrecord() {
		return selectedrecord;
	}

	public void setSelectedrecord(Boolean selectedrecord) {
		this.selectedrecord = selectedrecord;
	}

	
}

package com.amg.exchange.menu.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amg.exchange.menu.model.Menu;
import com.amg.exchange.menu.model.MenuGroup;
import com.amg.exchange.menu.model.RoleWiseMenu;
import com.amg.exchange.menu.service.IRoleWiseMenuPermissionService;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.util.Constants;

@Component("rolewiseMenuPermission")
public class RoleWiseMenuPermissionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal roleMasterId;
	private BigDecimal menuGroupId;

	private List<MenuGroup> lstOfMenuGroup = new ArrayList<MenuGroup>();
	private List<RoleMaster> lstOfRole = new ArrayList<RoleMaster>();
	private List<RoleWiseMenu> lstOfRolewiseMenu = new ArrayList<RoleWiseMenu>();
	private List<RoleWiseMenu> lstOfRoleMenu = new ArrayList<RoleWiseMenu>();
	private List<Menu> lstOfMenu = new ArrayList<Menu>();

	private List<RoleWiseMenuPermissionDataTable> lstOfRoleWiseDataTable = new ArrayList<RoleWiseMenuPermissionDataTable>();

	@Autowired
	IRoleWiseMenuPermissionService<T> rolewiseMenuPermissionService;
	
	public RoleWiseMenuPermissionBean(){
		setRoleMasterId(null);
		setMenuGroupId(null);
		lstOfRoleWiseDataTable.clear();
	}

	public BigDecimal getRoleMasterId() {
		return roleMasterId;
	}

	public void setRoleMasterId(BigDecimal roleMasterId) {
		this.roleMasterId = roleMasterId;
	}

	public BigDecimal getMenuGroupId() {
		return menuGroupId;
	}

	public void setMenuGroupId(BigDecimal menuGroupId) {
		this.menuGroupId = menuGroupId;
	}

	public List<MenuGroup> getLstOfMenuGroup() {
		return lstOfMenuGroup;
	}

	public void setLstOfMenuGroup(List<MenuGroup> lstOfMenuGroup) {
		this.lstOfMenuGroup = lstOfMenuGroup;
	}

	public List<RoleMaster> getLstOfRole() {
		return lstOfRole;
	}

	public void setLstOfRole(List<RoleMaster> lstOfRole) {
		this.lstOfRole = lstOfRole;
	}

	public List<RoleWiseMenu> getLstOfRolewiseMenu() {
		return lstOfRolewiseMenu;
	}

	public void setLstOfRolewiseMenu(List<RoleWiseMenu> lstOfRolewiseMenu) {
		this.lstOfRolewiseMenu = lstOfRolewiseMenu;
	}

	public List<RoleWiseMenu> getLstOfRoleMenu() {
		return lstOfRoleMenu;
	}

	public void setLstOfRoleMenu(List<RoleWiseMenu> lstOfRoleMenu) {
		this.lstOfRoleMenu = lstOfRoleMenu;
	}

	public List<RoleWiseMenuPermissionDataTable> getLstOfRoleWiseDataTable() {
		return lstOfRoleWiseDataTable;
	}

	public void setLstOfRoleWiseDataTable(List<RoleWiseMenuPermissionDataTable> lstOfRoleWiseDataTable) {
		this.lstOfRoleWiseDataTable = lstOfRoleWiseDataTable;
	}

	public List<Menu> getLstOfMenu() {
		return lstOfMenu;
	}

	public void setLstOfMenu(List<Menu> lstOfMenu) {
		this.lstOfMenu = lstOfMenu;
	}

	public IRoleWiseMenuPermissionService<T> getRolewiseMenuPermissionService() {
		return rolewiseMenuPermissionService;
	}

	public void setRolewiseMenuPermissionService(IRoleWiseMenuPermissionService<T> rolewiseMenuPermissionService) {
		this.rolewiseMenuPermissionService = rolewiseMenuPermissionService;
	}

	public List<RoleMaster> getRoleMasterList() {

		lstOfRole = getRolewiseMenuPermissionService().getRoleList();

		return lstOfRole;
	}

	public List<MenuGroup> getMenuGroupList() {

		lstOfMenuGroup = getRolewiseMenuPermissionService().getMenuGroupList();

		return lstOfMenuGroup;
	}

	public void saveRolewiseMenu() {
	
		lstOfMenu = getRolewiseMenuPermissionService().getMenuList();

		RoleWiseMenu rolewiseMenu = null;
		if (lstOfMenu != null) {

			for (Menu menu : lstOfMenu) {
				rolewiseMenu = new RoleWiseMenu();
				rolewiseMenu.setIsRequired(Constants.No);

				Menu menu1 = new Menu();
				menu1.setMenuId(menu.getMenuId());
				rolewiseMenu.setMenuId(menu1);

				MenuGroup menuGroup = new MenuGroup();
				menuGroup.setMenuGroupId(menu.getMenuGroupId().getMenuGroupId());
				rolewiseMenu.setMenuGroupId(menuGroup);

				RoleMaster roleMaster = new RoleMaster();
				roleMaster.setRoleId(getRoleMasterId());
				rolewiseMenu.setRoleId(roleMaster);

				lstOfRoleMenu = getRolewiseMenuPermissionService().getRoleWiseMenuList(getRoleMasterId(), menu.getMenuId(), menu.getMenuGroupId().getMenuGroupId());
				if (lstOfRoleMenu == null) {
					getRolewiseMenuPermissionService().saveRoleWisePermissionMenu(rolewiseMenu);
				}
			}
		}
		addToDataTable(getRoleMasterId(),getMenuGroupId());
		System.out.println("id ="+getRoleMasterId()+"-"+getMenuGroupId());
	}

	public void addToDataTable(BigDecimal roleId,BigDecimal groupid) {
		lstOfRoleWiseDataTable.clear();
		// lstOfRolewiseMenu.clear();
		lstOfRolewiseMenu = getRolewiseMenuPermissionService().getRoleWiseMenuList(roleId, groupid);

		if (lstOfRolewiseMenu == null) {
			lstOfRoleWiseDataTable.clear();
		} else {

			for (RoleWiseMenu rolewiseMenu : lstOfRolewiseMenu) {

				RoleWiseMenuPermissionDataTable rolewiseMenuPermissionDataTable = new RoleWiseMenuPermissionDataTable();

				rolewiseMenuPermissionDataTable.setRoleWiseMenuId(rolewiseMenu.getRoleWiseMenuId());
				rolewiseMenuPermissionDataTable.setRoleId(rolewiseMenu.getRoleId().getRoleId());
				rolewiseMenuPermissionDataTable.setMenuId(rolewiseMenu.getMenuId().getMenuId());
				rolewiseMenuPermissionDataTable.setMenuGroupId(rolewiseMenu.getMenuGroupId().getMenuGroupId());

				rolewiseMenuPermissionDataTable.setRoleName(rolewiseMenu.getRoleId().getRoleName());
				rolewiseMenuPermissionDataTable.setMenuDescription(rolewiseMenu.getMenuId().getEnglishDescription());
				rolewiseMenuPermissionDataTable.setMenuGroupDescription(rolewiseMenu.getMenuGroupId().getEnglishDescription());

				if (rolewiseMenu.getIsRequired().equals(Constants.Yes)) {
					rolewiseMenuPermissionDataTable.setSelectedrecord(true);

				} else {
					rolewiseMenuPermissionDataTable.setSelectedrecord(false);
				}
				lstOfRoleWiseDataTable.add(rolewiseMenuPermissionDataTable);

			}
		}
	}

	public void UpdateToRolewiseMenu() {

		if (lstOfRoleWiseDataTable.size() > 0) {
			for (RoleWiseMenuPermissionDataTable rolewiseMenuPermissionDataTable : lstOfRoleWiseDataTable) {
				if (rolewiseMenuPermissionDataTable.getSelectedrecord() != null) {
					// if(rolewiseMenuPermissionDataTable.getSelectedrecord().equals(true)){

					conformToDeActivteRoleWiseMenu(rolewiseMenuPermissionDataTable);
					// }
				}
			}
		}
	}

	public void conformToDeActivteRoleWiseMenu(RoleWiseMenuPermissionDataTable rolewiseMenuPermissionDataTable) {

		String grantRevoke = null;

		if (rolewiseMenuPermissionDataTable.getSelectedrecord().equals(true)) {
			grantRevoke = Constants.Yes;
		} else if (rolewiseMenuPermissionDataTable.getSelectedrecord().equals(false)) {
			grantRevoke = Constants.No;
		}

		getRolewiseMenuPermissionService().activateRoleWiseMenu(rolewiseMenuPermissionDataTable.getRoleWiseMenuId(), grantRevoke);
		RequestContext.getCurrentInstance().execute("update.show();");
	}
	
	public RoleWiseMenuPermissionDataTable getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(RoleWiseMenuPermissionDataTable selectedRow) {
		this.selectedRow = selectedRow;
	}

	private RoleWiseMenuPermissionDataTable selectedRow;
	
}

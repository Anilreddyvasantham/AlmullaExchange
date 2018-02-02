package com.amg.exchange.menu.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.menu.dao.IRoleWiseMenuPermissionDao;
import com.amg.exchange.menu.model.Menu;
import com.amg.exchange.menu.model.MenuGroup;
import com.amg.exchange.menu.model.RoleWiseMenu;
import com.amg.exchange.menu.service.IRoleWiseMenuPermissionService;
import com.amg.exchange.registration.model.RoleMaster;

@Service("roleWiseMenuPermissionServiceImpl")
public class RoleWiseMenuPermissionServiceImpl<T> implements IRoleWiseMenuPermissionService<T> {

	@Autowired
	IRoleWiseMenuPermissionDao<T> rolewiseMenuPermissionDao;

	public IRoleWiseMenuPermissionDao<T> getRolewiseMenuPermissionDao() {
		return rolewiseMenuPermissionDao;
	}

	public void setRolewiseMenuPermissionDao(IRoleWiseMenuPermissionDao<T> rolewiseMenuPermissionDao) {
		this.rolewiseMenuPermissionDao = rolewiseMenuPermissionDao;
	}

	@Override
	@Transactional
	public void saveRoleWisePermissionMenu(RoleWiseMenu rolewiseMenu) {
		getRolewiseMenuPermissionDao().saveRoleWisePermissionMenu(rolewiseMenu);
	}

	@Override
	@Transactional
	public List<Menu> getMenuList() {
		return getRolewiseMenuPermissionDao().getMenuList();
	}

	@Override
	@Transactional
	public List<RoleMaster> getRoleList() {
		return getRolewiseMenuPermissionDao().getRoleList();
	}

	@Override
	@Transactional
	public List<RoleWiseMenu> getRoleWiseMenuList(BigDecimal roleId, BigDecimal menuGroupId) {
		return getRolewiseMenuPermissionDao().getRoleWiseMenuList(roleId, menuGroupId);
	}

	@Override
	@Transactional
	public List<RoleWiseMenu> getRoleWiseMenuList(BigDecimal roleId, BigDecimal menuId, BigDecimal menuGroupId) {
		return getRolewiseMenuPermissionDao().getRoleWiseMenuList(roleId, menuId, menuGroupId);
	}

	@Override
	@Transactional
	public List<RoleWiseMenu> getRoleWiseMenuList() {
		return getRolewiseMenuPermissionDao().getRoleWiseMenuList();
	}

	@Override
	@Transactional
	public void activateRoleWiseMenu(BigDecimal rolewiseMenuId, String userName) {
		getRolewiseMenuPermissionDao().activateRoleWiseMenu(rolewiseMenuId, userName);
	}

	@Override
	@Transactional
	public List<RoleWiseMenu> getRolewiseMenuList(BigDecimal roleId) {
		return getRolewiseMenuPermissionDao().getRolewiseMenuList(roleId);
	}

	@Override
	@Transactional
	public List<MenuGroup> getMenuGroupList() {
		return getRolewiseMenuPermissionDao().getMenuGroupList();
	}

}

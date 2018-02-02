/**
 * 
 */
package com.amg.exchange.menu.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.menu.model.Menu;
import com.amg.exchange.menu.model.MenuGroup;
import com.amg.exchange.menu.model.RoleWiseMenu;
import com.amg.exchange.registration.model.RoleMaster;

/**
 * @author Subramaniam
 * 
 */
public interface IRoleWiseMenuPermissionDao<T> {

	public void saveRoleWisePermissionMenu(RoleWiseMenu rolewiseMenu);

	public List<Menu> getMenuList();

	public List<RoleMaster> getRoleList();

	public List<RoleWiseMenu> getRoleWiseMenuList(BigDecimal roleId, BigDecimal menuGroupId);

	public List<RoleWiseMenu> getRoleWiseMenuList(BigDecimal roleId, BigDecimal menuId, BigDecimal menuGroupId);

	public List<RoleWiseMenu> getRoleWiseMenuList();

	public void activateRoleWiseMenu(BigDecimal rolewiseMenuId, String userName);

	public List<RoleWiseMenu> getRolewiseMenuList(BigDecimal roleId);

	public List<MenuGroup> getMenuGroupList();

}

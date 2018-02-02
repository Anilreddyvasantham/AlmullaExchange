package com.amg.exchange.menu.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.menu.dao.IRoleWiseMenuPermissionDao;
import com.amg.exchange.menu.model.Menu;
import com.amg.exchange.menu.model.MenuGroup;
import com.amg.exchange.menu.model.RoleWiseMenu;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;

@SuppressWarnings("unchecked")
@Repository
public class RoleWiseMenuPermissionDaoImpl<T> extends CommonDaoImpl<T> implements Serializable, IRoleWiseMenuPermissionDao<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5529124157524436124L;

	@Override
	public void saveRoleWisePermissionMenu(RoleWiseMenu rolewiseMenu) {
		saveOrUpdate((T) rolewiseMenu);
	}

	@Override
	public List<Menu> getMenuList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class, "menu");

		List<Menu> objList = (List<Menu>) findAll(criteria);
		List<?> list = CommonUtil.nullCheck(objList);
		return (List<Menu>) list;

	}

	@Override
	public List<RoleMaster> getRoleList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(RoleMaster.class, "roleMaster");

		List<RoleMaster> objList = (List<RoleMaster>) findAll(criteria);
		List<?> list = CommonUtil.nullCheck(objList);
		return (List<RoleMaster>) list;

	}

	@Override
	public List<RoleWiseMenu> getRoleWiseMenuList(BigDecimal roleId, BigDecimal menuGroupId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(RoleWiseMenu.class, "rolewiseMenu");

		criteria.setFetchMode("rolewiseMenu.menuId", FetchMode.JOIN);
		criteria.createAlias("rolewiseMenu.menuId", "menuId", JoinType.INNER_JOIN);

		criteria.setFetchMode("rolewiseMenu.menuGroupId", FetchMode.JOIN);
		criteria.createAlias("rolewiseMenu.menuGroupId", "menuGroupId", JoinType.INNER_JOIN);

		criteria.setFetchMode("rolewiseMenu.roleId", FetchMode.JOIN);
		criteria.createAlias("rolewiseMenu.roleId", "roleId", JoinType.INNER_JOIN);
		if (menuGroupId != null) {
			criteria.add(Restrictions.eq("menuGroupId.menuGroupId", menuGroupId));
		}
		if (roleId != null) {
			criteria.add(Restrictions.eq("roleId.roleId", roleId));
		}
		criteria.addOrder(Order.asc("menuId.englishDescription"));

		List<RoleWiseMenu> objList = (List<RoleWiseMenu>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;

	}

	@Override
	public List<RoleWiseMenu> getRoleWiseMenuList(BigDecimal roleId, BigDecimal menuId, BigDecimal menuGroupId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(RoleWiseMenu.class, "rolewiseMenu");

		criteria.setFetchMode("rolewiseMenu.menuId", FetchMode.JOIN);
		criteria.createAlias("rolewiseMenu.menuId", "menuId", JoinType.INNER_JOIN);

		criteria.setFetchMode("rolewiseMenu.menuGroupId", FetchMode.JOIN);
		criteria.createAlias("rolewiseMenu.menuGroupId", "menuGroupId", JoinType.INNER_JOIN);

		criteria.setFetchMode("rolewiseMenu.roleId", FetchMode.JOIN);
		criteria.createAlias("rolewiseMenu.roleId", "roleId", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("roleId.roleId", roleId));
		criteria.add(Restrictions.eq("menuId.menuId", menuId));
		criteria.add(Restrictions.eq("menuGroupId.menuGroupId", menuGroupId));

		List<RoleWiseMenu> objList = (List<RoleWiseMenu>) findAll(criteria);
		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public List<RoleWiseMenu> getRoleWiseMenuList() {

		DetachedCriteria criteria = DetachedCriteria.forClass(RoleWiseMenu.class, "rolewiseMenu");

		criteria.setFetchMode("rolewiseMenu.menuId", FetchMode.JOIN);
		criteria.createAlias("rolewiseMenu.menuId", "menuId", JoinType.INNER_JOIN);

		criteria.setFetchMode("rolewiseMenu.menuGroupId", FetchMode.JOIN);
		criteria.createAlias("rolewiseMenu.menuGroupId", "menuGroupId", JoinType.INNER_JOIN);

		criteria.setFetchMode("rolewiseMenu.roleId", FetchMode.JOIN);
		criteria.createAlias("rolewiseMenu.roleId", "roleId", JoinType.INNER_JOIN);

		List<RoleWiseMenu> objList = (List<RoleWiseMenu>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;

	}

	@Override
	public void activateRoleWiseMenu(BigDecimal rolewiseMenuId, String userName) {
		RoleWiseMenu roleWiseMenu = (RoleWiseMenu) getSession().get(RoleWiseMenu.class, rolewiseMenuId);
		roleWiseMenu.setIsRequired(userName);

		getSession().update(roleWiseMenu);

	}

	@Override
	public List<RoleWiseMenu> getRolewiseMenuList(BigDecimal roleId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(RoleWiseMenu.class, "roleWiseMenu");

		criteria.setFetchMode("roleWiseMenu.menuId", FetchMode.JOIN);
		criteria.createAlias("roleWiseMenu.menuId", "menuId", JoinType.INNER_JOIN);

		criteria.setFetchMode("roleWiseMenu.menuGroupId", FetchMode.JOIN);
		criteria.createAlias("roleWiseMenu.menuGroupId", "menuGroupId", JoinType.INNER_JOIN);

		criteria.setFetchMode("roleWiseMenu.roleId", FetchMode.JOIN);
		criteria.createAlias("roleWiseMenu.roleId", "roleId", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("roleWiseMenu.roleId.roleId", roleId));
		criteria.add(Restrictions.eq("roleWiseMenu.isRequired", Constants.Yes));
		criteria.add(Restrictions.eq("menuGroupId.isActive", Constants.Yes));
		criteria.add(Restrictions.eq("menuId.isActive", Constants.Yes));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RoleWiseMenu> objList = (List<RoleWiseMenu>) findAll(criteria);
		// List<?> list = CommonUtil.nullCheck(objList);
		return (List<RoleWiseMenu>) objList;

	}

	@Override
	public List<MenuGroup> getMenuGroupList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MenuGroup.class, "menuGroup");
		criteria.addOrder(Order.asc("menuGroup.menuGroupId"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<MenuGroup> objList = (List<MenuGroup>) findAll(criteria);
		// List<?> list = CommonUtil.nullCheck(objList);
		return (List<MenuGroup>) objList;

	}

}

package com.amg.exchange.menu.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.graphicimage.GraphicImage;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.menu.model.MenuGroup;
import com.amg.exchange.menu.model.RoleWiseMenu;
import com.amg.exchange.menu.service.IRoleWiseMenuPermissionService;
import com.amg.exchange.util.SessionStateManage;

@Component("menuDisplay")
@Scope("session")
public class MenuDisplayBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	IRoleWiseMenuPermissionService<T> roleWiseMenuPermissionService;

	// Create Session
	SessionStateManage session = new SessionStateManage();

	private MenuModel menubar = null;;

	private List<RoleWiseMenu> rolewiseMenuList = new ArrayList<RoleWiseMenu>();
	private List<MenuGroup> menuGroupList = new ArrayList<MenuGroup>();

	DefaultSubMenu menu = null;
	DefaultSubMenu menuGroupItem = null;

	public MenuDisplayBean() {

	}

	public MenuModel getMenubar() {
		menuGroupList.clear();
		rolewiseMenuList.clear();
		menubar = new DefaultMenuModel();
		menu = new DefaultSubMenu();
		menu.setIcon("xlogo");
		
		String menuGroupItemDescription = null;
		String menuItemDescription = null;

		rolewiseMenuList = roleWiseMenuPermissionService.getRolewiseMenuList(new BigDecimal(session.getRoleId()));

		menuGroupList = roleWiseMenuPermissionService.getMenuGroupList();
		if (!menuGroupList.isEmpty()) {
			for (MenuGroup menuGroup : menuGroupList) {

				if (session.getLanguageId().intValue() == 1) {
					menuGroupItemDescription = menuGroup.getEnglishDescription();
				} else if (session.getLanguageId().intValue() == 2) {
					menuGroupItemDescription = menuGroup.getLocalDescription();
				}
				menuGroupItem = new DefaultSubMenu(menuGroupItemDescription);
				menuGroupItem.setIcon("ui-icon-gear");
				if (!rolewiseMenuList.isEmpty()) {
					for (RoleWiseMenu roleWiseMenu : rolewiseMenuList) {

						if (menuGroup.getMenuGroupId().intValue() == roleWiseMenu.getMenuGroupId().getMenuGroupId().intValue()) {

							if (session.getLanguageId().intValue() == 1) {
								menuItemDescription = roleWiseMenu.getMenuId().getEnglishDescription();
							} else if (session.getLanguageId().intValue() == 2) {
								menuItemDescription = roleWiseMenu.getMenuId().getLocalDescription();
							}

							DefaultMenuItem menuItem = new DefaultMenuItem(menuItemDescription);
							menuItem.setUpdate("message");
							// menuItem.setCommand("");
							menuItem.setUrl(roleWiseMenu.getMenuId().getPagePath());
							menuItem.setIcon("ui-icon-gripsmall-diagonal-se");
							menuGroupItem.addElement(menuItem);
						}
					}
				}

				if (menuGroupItem.getElementsCount() > 0) {
					menu.addElement(menuGroupItem);
				}
			}
		}
		this.menubar.addElement(menu);

		return menubar;
	}

	public void setMenubar(MenuModel menubar) {
		this.menubar = menubar;
	}

	public String ajaxAction() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ajax Update"));
		return "";
	}

	public String nonAjaxAction() {
		return "";
	}

	public String openAction() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Open action!"));
		return "";
	}
}
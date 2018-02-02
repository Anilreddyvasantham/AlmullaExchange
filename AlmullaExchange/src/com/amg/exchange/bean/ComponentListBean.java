package com.amg.exchange.bean;

import java.io.Serializable;

import com.amg.exchange.common.model.ComponentType;
import com.amg.exchange.common.model.ComponentTypeDetail;

@SuppressWarnings("serial")
public class ComponentListBean implements Serializable {

	private boolean multiple;
	private boolean visibility;
	private boolean minlength;
	private boolean maxlength;
	private boolean numeric;
	private boolean alphabet;
	private boolean special;
	private boolean mandatory;
	private boolean readonly;
	private boolean enable;
	private boolean defaultValue;
	private ComponentType componentType;
	private ComponentTypeDetail componentTypeDetail;
	private int sno;
	private String componentTypeName;

	public ComponentListBean() {

	}

	public ComponentListBean(boolean multiple, boolean visibility,
			boolean minlength, boolean maxlength, boolean numeric,
			boolean alphabet, boolean special, boolean mandatory,
			boolean readonly, boolean enable, boolean defaultValue,
			ComponentType componentType,
			ComponentTypeDetail componentTypeDetail, int sno,
			String componentTypeName) {

		this.multiple = multiple;
		this.visibility = visibility;
		this.minlength = minlength;
		this.maxlength = maxlength;
		this.numeric = numeric;
		this.alphabet = alphabet;
		this.special = special;
		this.mandatory = mandatory;
		this.readonly = readonly;
		this.enable = enable;
		this.defaultValue = defaultValue;
		this.componentType = componentType;
		this.componentTypeDetail = componentTypeDetail;
		this.sno = sno;
		this.componentTypeName = componentTypeName;
	}

	public String getComponentTypeName() {
		return componentTypeName;
	}

	public void setComponentTypeName(String componentTypeName) {
		this.componentTypeName = componentTypeName;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public boolean isMinlength() {
		return minlength;
	}

	public void setMinlength(boolean minlength) {
		this.minlength = minlength;
	}

	public boolean isMaxlength() {
		return maxlength;
	}

	public void setMaxlength(boolean maxlength) {
		this.maxlength = maxlength;
	}

	public boolean isNumeric() {
		return numeric;
	}

	public void setNumeric(boolean numeric) {
		this.numeric = numeric;
	}

	public boolean isAlphabet() {
		return alphabet;
	}

	public void setAlphabet(boolean alphabet) {
		this.alphabet = alphabet;
	}

	public boolean isSpecial() {
		return special;
	}

	public void setSpecial(boolean special) {
		this.special = special;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean isDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	public ComponentType getComponentType() {
		return componentType;
	}

	public void setComponentType(ComponentType componentType) {
		this.componentType = componentType;
	}

	public ComponentTypeDetail getComponentTypeDetail() {
		return componentTypeDetail;
	}

	public void setComponentTypeDetail(ComponentTypeDetail componentTypeDetail) {
		this.componentTypeDetail = componentTypeDetail;
	}
}

package com.amg.exchange.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.ComponentType;
import com.amg.exchange.common.model.ComponentTypeDetail;
import com.amg.exchange.common.service.IComponentTypeService;

@SuppressWarnings("serial")
@Component("componenttype")
@RequestScoped
public class ComponentTypeBean<T> implements Serializable {

	private static final Logger logger = Logger.getLogger(ComponentTypeBean.class);
	
	private int sno = 1;
	private List<ComponentType> lstComponentType;
	private List<ComponentTypeDetail> lstComponentTypeDetail = new ArrayList<ComponentTypeDetail>();
	private List<ComponentListBean> lstComponentListBean = new ArrayList<ComponentListBean>();

	@Autowired
	IComponentTypeService<T> componentTypeService;
	
	public List<ComponentType> getLstComponentType() {
		return lstComponentType;
	}

	public void setLstComponentType(List<ComponentType> lstComponentType) {
		this.lstComponentType = lstComponentType;
	}
	
	public List<ComponentListBean> getLstComponentListBean() {
		return lstComponentListBean;
	}

	public void setLstComponentListBean(List<ComponentListBean> lstComponentListBean) {
		this.lstComponentListBean = lstComponentListBean;
	}
	
	public List<ComponentTypeDetail> getLstComponentTypeDetail() {
		return lstComponentTypeDetail;
	}

	public void setLstComponentTypeDetail(
			List<ComponentTypeDetail> lstComponentTypeDetail) {
		this.lstComponentTypeDetail = lstComponentTypeDetail;
	}

	public List<ComponentListBean> getComponentTypeList(){

		if(lstComponentListBean==null || lstComponentListBean.size()==0){
			prepareComponentTypeList();
		}
		return lstComponentListBean;
	}
	
	public List<ComponentListBean> prepareComponentTypeList(){
		
		ComponentListBean componentListBean;
		ComponentTypeDetail componentTypeDetail;
		try{
			setLstComponentType(componentTypeService.getComponentTypeList());
			lstComponentListBean = new ArrayList<ComponentListBean>();
			if(getLstComponentType()!=null && getLstComponentType().size()>0){
				sno = 1;
				for(ComponentType componentType:getLstComponentType()){
					
					componentTypeDetail = componentType.getComponentTypeDetail();
					componentListBean = new ComponentListBean();
					componentListBean.setComponentType(componentType);
					componentListBean.setComponentTypeDetail(componentTypeDetail);

					componentListBean.setMultiple(getBoolean(componentTypeDetail.getIsmultiple()));
					componentListBean.setVisibility(getBoolean(componentTypeDetail.getVisibility()));
					componentListBean.setMinlength(getBoolean(componentTypeDetail.getMinlength()));
					componentListBean.setMaxlength(getBoolean(componentTypeDetail.getMaxlength()));
					componentListBean.setNumeric(getBoolean(componentTypeDetail.getNumeric()));
					componentListBean.setAlphabet(getBoolean(componentTypeDetail.getAlphabet()));
					componentListBean.setSpecial(getBoolean(componentTypeDetail.getSpecial()));
					componentListBean.setMandatory(getBoolean(componentTypeDetail.getMandatory()));
					componentListBean.setReadonly(getBoolean(componentTypeDetail.getReadonly()));
					componentListBean.setEnable(getBoolean(componentTypeDetail.getEnable()));
					componentListBean.setDefaultValue(getBoolean(componentTypeDetail.getDefaultValue()));
					
					componentListBean.setSno(sno++);
					componentListBean.setComponentTypeName(componentType.getComponentTypeName());
					
					lstComponentListBean.add(componentListBean);
				}
			}
		}catch(Exception e){
			logger.error("Unable to fetch component type list, due to :: "+e);
		}
		return lstComponentListBean; 
	}
	
	public void addComponentTypeData(){
		
		ComponentTypeDetail componentTypeDetail = new ComponentTypeDetail();
		ComponentType componentType = new ComponentType();
		
		ComponentListBean componentListBean = new ComponentListBean();
		
		componentListBean.setComponentType(componentType);
		componentListBean.setComponentTypeDetail(componentTypeDetail);

		componentListBean.setMultiple(true);
		componentListBean.setVisibility(true);
		componentListBean.setMinlength(true);
		componentListBean.setMaxlength(true);
		componentListBean.setNumeric(true);
		componentListBean.setAlphabet(true);
		componentListBean.setSpecial(true);
		componentListBean.setMandatory(true);
		componentListBean.setReadonly(true);
		componentListBean.setEnable(true);
		componentListBean.setDefaultValue(true);
		
		componentListBean.setSno(sno++);
		componentListBean.setComponentTypeName("");
		
		lstComponentListBean.add(componentListBean);
	}
	
	public void reset(){
		
		sno = 1;
		lstComponentType = null;
		lstComponentTypeDetail = new ArrayList<ComponentTypeDetail>();
		lstComponentListBean = new ArrayList<ComponentListBean>();
	}
	
	public void saveData(){
		
		lstComponentType.clear();
		lstComponentTypeDetail.clear();
		ComponentType componentType;
		ComponentTypeDetail componentTypeDetail;
		
		java.util.Date currentDate = new java.util.Date();
		for(ComponentListBean bean:lstComponentListBean){
			
			componentType = bean.getComponentType();
			componentTypeDetail = bean.getComponentTypeDetail();
			
			if(componentType.getComponentTypeId()==null){
				
				componentType.setIsActive("Y");
				componentType.setCreatedBy("ROOT");
				componentType.setCreatedTime(currentDate);
			}
			
			componentType.setUpdatedBy("ROOT");
			componentType.setUpdatedTime(currentDate);
			componentType.setComponentTypeName(bean.getComponentTypeName());
			
			componentTypeDetail.setIsmultiple(getString(bean.isMultiple()));
			componentTypeDetail.setVisibility(getString(bean.isVisibility()));
			componentTypeDetail.setMinlength(getString(bean.isMinlength()));
			componentTypeDetail.setMaxlength(getString(bean.isMaxlength()));
			componentTypeDetail.setNumeric(getString(bean.isNumeric()));
			componentTypeDetail.setAlphabet(getString(bean.isAlphabet()));
			componentTypeDetail.setSpecial(getString(bean.isSpecial()));
			componentTypeDetail.setMandatory(getString(bean.isMandatory()));
			componentTypeDetail.setReadonly(getString(bean.isReadonly()));
			componentTypeDetail.setEnable(getString(bean.isEnable()));
			componentTypeDetail.setDefaultValue(getString(bean.isDefaultValue()));
			componentTypeDetail.setComponentType(componentType);
			
			/*lstComponentType.add(componentType);*/
			lstComponentTypeDetail.add(componentTypeDetail);
		}
		
		componentTypeService.storeData(lstComponentTypeDetail);
		reset();
	}
	
	private boolean getBoolean(String data){
		
		return data.equalsIgnoreCase("y")?true:false;
	}

	private String getString(boolean data){
		
		return data ? "Y":"N";
	}
	
}

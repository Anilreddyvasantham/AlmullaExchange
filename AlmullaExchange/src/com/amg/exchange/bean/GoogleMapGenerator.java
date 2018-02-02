/**
 /**
 * "Nazish
 */
package com.amg.exchange.bean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.GoogleMapAddress;
import com.amg.exchange.registration.service.IGoogleMapService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component(value = "googleMapGenerator")
@Scope("request")
public class GoogleMapGenerator<T> implements Serializable {

	  /**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String address;
	private final static Logger log = Logger.getLogger(GoogleMapGenerator.class.getName()); 
    private static final String GEO_CODE_SERVER = "http://maps.googleapis.com/maps/api/geocode/json?";
    
    private Marker marker;
    private MapModel simpleModel;
    
    @Autowired
    IGoogleMapService<T> igoogleMapService;
    
    @Autowired
    
    IGeneralService<T> generalService;
    
    SessionStateManage sessionStateManage = new SessionStateManage();
    
    /**
     * for get map model
     * @return
     */
    public MapModel getSimpleModel() {
        return simpleModel;
    }
    
    
    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Selected", marker.getTitle()));
    }
      
    public Marker getMarker() {
        return marker;
    }
 
    /**
	 * @return the address
	 */
    
 
  
  private BigDecimal countryId;
  private BigDecimal stateId;
  private BigDecimal districtId;
  private BigDecimal cityId;
  
  private BigDecimal contactType;
  
  private BigDecimal empcountryId;
  private BigDecimal empstateId;
  private BigDecimal empdistrictId;
  private BigDecimal empcityId;
  
  
  
  
	public BigDecimal getCountryId() {
	return countryId;
}

public void setCountryId(BigDecimal countryId) {
	this.countryId = countryId;
}

public BigDecimal getStateId() {
	return stateId;
}

public void setStateId(BigDecimal stateId) {
	this.stateId = stateId;
}

public BigDecimal getDistrictId() {
	return districtId;
}

public void setDistrictId(BigDecimal districtId) {
	this.districtId = districtId;
}

public BigDecimal getCityId() {
	return cityId;
}

public void setCityId(BigDecimal cityId) {
	this.cityId = cityId;
}


	public BigDecimal getContactType() {
	return contactType;
}

public void setContactType(BigDecimal contactType) {
	this.contactType = contactType;
}



public BigDecimal getEmpcountryId() {
	return empcountryId;
}

public void setEmpcountryId(BigDecimal empcountryId) {
	this.empcountryId = empcountryId;
}

public BigDecimal getEmpstateId() {
	return empstateId;
}

public void setEmpstateId(BigDecimal empstateId) {
	this.empstateId = empstateId;
}

public BigDecimal getEmpdistrictId() {
	return empdistrictId;
}

public void setEmpdistrictId(BigDecimal empdistrictId) {
	this.empdistrictId = empdistrictId;
}

public BigDecimal getEmpcityId() {
	return empcityId;
}

public void setEmpcityId(BigDecimal empcityId) {
	this.empcityId = empcityId;
}
List<GoogleMapSuccessDatatableBean> googleListData = new ArrayList<GoogleMapSuccessDatatableBean>();
public void callFromOnlineRegEmployment(BigDecimal countryId,BigDecimal stateId,BigDecimal districtId,BigDecimal cityId){
	setEmpcountryId(countryId);
	setEmpstateId(stateId);
	setEmpdistrictId(districtId);
	setEmpcityId(cityId);
	
}

	public void callFromOnlineRegistration(List<ContactDetails> contactDetailList) {
		
	
		for(ContactDetails contactList:contactDetailList){
			
			if(contactList.getContactType().intValue() == generalService.getComponentId(Constants.RESIDENCE,sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId().intValue()){
			GoogleMapSuccessDatatableBean googledataList = new GoogleMapSuccessDatatableBean();
			
			googledataList.setContactType(contactList.getContactType());
			googledataList.setCountryId(contactList.getCountry());
			googledataList.setStateId(contactList.getState());
			googledataList.setDistrictId(contactList.getDistrict());
			googledataList.setCityId(contactList.getCity());
			googleListData.add(googledataList);
		}
		}
	
	
		
	}
	
	public void callFromOnlineRegistrationEmployeeInfo(BigDecimal countryId,BigDecimal stateId, BigDecimal districtId, BigDecimal cityId){
		setCountryId(countryId);
		setDistrictId(districtId);
		setCityId(cityId);
		
	}
    
    
	public String getAddress() {
		
	
		
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		
		
		 this.address = address;
	}


	/**
	 * For getting Latitude & Longitude
	 * @return
	 */
	SessionStateManage sessionState = new SessionStateManage();
	public String gmap(){
		
		int languageID = 1;
		if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("languageCode")) {
			languageID = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("languageCode").toString().equalsIgnoreCase("ar")?2:1;
		}
		simpleModel = new DefaultMapModel();
		
	
		String code = getAddress();
		
		String pos="";
        	
	        simpleModel = new DefaultMapModel();
	        
	        for(GoogleMapSuccessDatatableBean googlemapListBean:googleListData){
	        	pos = igoogleMapService.getCountryLongLattStr(googlemapListBean.getCountryId());
	        
	        	List<GoogleMapAddress> addressList = igoogleMapService.getAddress(googlemapListBean.getCountryId(), googlemapListBean.getStateId(),googlemapListBean.getDistrictId(), googlemapListBean.getCityId(),new BigDecimal(languageID));
	        	if(addressList.size()>0){
       if(googlemapListBean.getContactType().intValue()== generalService.getComponentId(Constants.RESIDENCE,sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId().intValue()){//PK
			String address = igoogleMapService.getAddressStr(googlemapListBean.getCountryId(), googlemapListBean.getStateId(),googlemapListBean.getDistrictId(), googlemapListBean.getCityId(),new BigDecimal(languageID));
			String longitude = igoogleMapService.getLongitudeStr(googlemapListBean.getCountryId(), googlemapListBean.getStateId(),googlemapListBean.getDistrictId(), googlemapListBean.getCityId());
		    String lattitude = igoogleMapService.getLattitudeStr(googlemapListBean.getCountryId(), googlemapListBean.getStateId(),googlemapListBean.getDistrictId(), googlemapListBean.getCityId());
		    LatLng coord1 = new LatLng(Double.parseDouble(longitude),Double.parseDouble(lattitude));
		    simpleModel.addOverlay(new Marker(coord1, address));
		  
       }
	
			  if(getCountryId()!=null && getStateId()!=null && getDistrictId()!=null && getCityId()!=null){//PK
			  String addressoffice = igoogleMapService.getAddressStr(getCountryId(), getStateId(), getDistrictId(), getCityId(),new BigDecimal(languageID));
			  String longitudeoff = igoogleMapService.getLongitudeStr(getCountryId(), getStateId(), getDistrictId(), getCityId());
		      String lattitudeoff = igoogleMapService.getLattitudeStr(getCountryId(), getStateId(), getDistrictId(), getCityId());
			
 	          LatLng coord2 = new LatLng(Double.parseDouble(longitudeoff),Double.parseDouble(lattitudeoff));
            simpleModel.addOverlay(new Marker(coord2, addressoffice,"konyaalti.png", "http://maps.google.com/mapfiles/ms/micons/green-dot.png"));
			  }
        
	        }
	        }
       
        return pos;
       
	}
		
	}
	

	
	

	
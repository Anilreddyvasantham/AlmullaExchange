package com.amg.exchange.registration.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

	
	@Entity
	@Table(name = "FS_GOOGLE_MAP_ADDRESS" )
	public class GoogleMapAddress implements java.io.Serializable {

		
		
		private static final long serialVersionUID = 1L;
		private BigDecimal googleMapAddId;
		private BigDecimal countryId;
		private BigDecimal districtId;
		private BigDecimal stateId;
		private BigDecimal cityId;
		private String longitude;
		private String latitude;
		private String address;
		private BigDecimal languageId;
		private String countryLongLatt;
		public GoogleMapAddress() {
		
		}
		
		public GoogleMapAddress(BigDecimal googleMapAddId) {
			this.googleMapAddId = googleMapAddId;
			
		}

		public GoogleMapAddress(BigDecimal googleMapAddId,
				BigDecimal countryId, BigDecimal districtId,
				BigDecimal stateId, BigDecimal cityId,
				String longitude, String latitude,BigDecimal languageId, String address,String countryLongLatt) {
			super();
			this.googleMapAddId = googleMapAddId;
			this.countryId = countryId;
			this.stateId = stateId;
			this.districtId = districtId;
			this.cityId = cityId;
			this.longitude = longitude;
			this.latitude = latitude;
			this.address = address;
			this.countryLongLatt = countryLongLatt;
			this.languageId = languageId;
		}

		@Id
		@GeneratedValue(generator="fs_google_map_address_seq",strategy=GenerationType.SEQUENCE)
		@SequenceGenerator(name="fs_google_map_address_seq",sequenceName="FS_GOOGLE_MAP_ADDRESS_SEQ",allocationSize=1)
		@Column(name = "GOOGLE_MAP_ADD_ID", unique = true, nullable = false, precision = 22, scale = 0)
		public BigDecimal getGoogleMapAddId() {
			return googleMapAddId;
		}

		public void setGoogleMapAddId(BigDecimal googleMapAddId) {
			this.googleMapAddId = googleMapAddId;
		}

		@Column(name = "COUNTRY_ID")
		public BigDecimal getCountryId() {
			return countryId;
		}

		public void setCountryId(BigDecimal countryId) {
			this.countryId = countryId;
		}

		@Column(name = "DISTRICT_ID")
		public BigDecimal getDistrictId() {
			return districtId;
		}

		public void setDistrictId(BigDecimal districtId) {
			this.districtId = districtId;
		}

		@Column(name = "STATE_ID")
		public BigDecimal getStateId() {
			return stateId;
		}

		public void setStateId(BigDecimal stateId) {
			this.stateId = stateId;
		}

		@Column(name = "CITY_ID")
		public BigDecimal getCityId() {
			return cityId;
		}

		public void setCityId(BigDecimal cityId) {
			this.cityId = cityId;
		}

		@Column(name = "LONGITUDE")
		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
		@Column(name = "LATITUDE")
		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		@Column(name = "ADDRESSS", length = 200)
		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		@Column(name = "LANGUAGE_ID")
		public BigDecimal getLanguageId() {
			return languageId;
		}

		public void setLanguageId(BigDecimal languageId) {
			this.languageId = languageId;
		}

		@Column(name = "COUNTRY_LONG_LATT")
		public String getCountryLongLatt() {
			return countryLongLatt;
		}

		public void setCountryLongLatt(String countryLongLatt) {
			this.countryLongLatt = countryLongLatt;
		}
		
		
}

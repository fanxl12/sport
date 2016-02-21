package com.agitation.sport.entity;

import java.util.Date;

import javax.persistence.Table;

//JPA标识
@Table(name = "sport_company")
public class Company extends BaseEntity {

	private String address;
	private double longitude; //经度
	private double latitude; //纬度
	private Date updateDate; 
	private long areaId; //地区Id
	
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getAreaId() {
		return areaId;
	}
	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}
	

}

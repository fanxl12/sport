package com.agitation.sport.entity;

import java.util.Date;

import javax.persistence.Table;

//JPA标识
@Table(name = "sport_advertisement")
public class Advertisement extends BaseEntity {

	private String url;
	private int type;  //广告是课程的还是比赛的
	private String website; //广告对应的网址
	private Date updateDate;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	

}

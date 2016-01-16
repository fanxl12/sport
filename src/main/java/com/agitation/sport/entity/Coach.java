package com.agitation.sport.entity;

import java.util.Date;

import javax.persistence.Table;

//JPA标识
@Table(name = "sport_coach")
public class Coach extends BaseEntity {

	private String url; //教练头像
	private Date updateDate;
	private String coachTime; //执教年限
	private String honor; //荣誉，以逗号分割存储
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCoachTime() {
		return coachTime;
	}
	public void setCoachTime(String coachTime) {
		this.coachTime = coachTime;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getHonor() {
		return honor;
	}
	public void setHonor(String honor) {
		this.honor = honor;
	}
	

}

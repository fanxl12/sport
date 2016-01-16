package com.agitation.sport.entity;

import java.util.Date;

import javax.persistence.Table;

//JPA标识
@Table(name = "sport_courseType")
public class CourseType extends BaseEntity {

	private int type;  //课程的还是比赛的分类
	private Date updateDate;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	

}

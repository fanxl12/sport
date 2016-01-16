package com.agitation.sport.entity;

import java.util.Date;

import javax.persistence.Table;

//JPA标识
@Table(name = "sport_collect")
public class Collect extends IdEntity {

	private Date createDate;
	private int courseId; //课程id
	private int userId; //收藏人Id
	private int matchId; //比赛Id

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getMatchId() {
		return matchId;
	}
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}
	

}

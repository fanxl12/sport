package com.agitation.sport.entity;

import java.util.Date;

import javax.persistence.Table;

//JPA标识
@Table(name = "sport_advice")
public class Advice extends IdEntity {

	private Date createDate;
	private int courseId; //课程id
	private int userId; //评价人Id
	private int score; //评分
	private int matchId; //比赛Id
	private String content; //评价内容

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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getMatchId() {
		return matchId;
	}
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

}

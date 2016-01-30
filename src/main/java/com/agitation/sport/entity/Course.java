package com.agitation.sport.entity;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

//JPA标识
@Table(name = "sport_course")
public class Course extends BaseEntity {

	private String url; //默认图片地址
	private Date updateDate; //更新时间
	private double price; //价格
	private double orginalPrice; //原价
	private int companyId; //课程上课场馆Id
	private Date startTime; //课程上课时间
	private Date stopTime; //课程下课时间
	private Date endTime; //截止报名时间
	private int totalNumber; //课程总数量
	private int buyNumber; //已购买数量
	private String introduce; //课程介绍
	private String notice; //须知
	private Coach coach; //教练
	private int totalScore; //总评分
	private int courseTypeId;
	private Catalog parentCatalog;
	private Catalog childCatalog;
	
	private int classType; //0是课程  1是公开课
	
	private int areaId; //区的Id
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getOrginalPrice() {
		return orginalPrice;
	}
	public void setOrginalPrice(double orginalPrice) {
		this.orginalPrice = orginalPrice;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public int getBuyNumber() {
		return buyNumber;
	}
	public void setBuyNumber(int buyNumber) {
		this.buyNumber = buyNumber;
	}
	public String getIntroduce() {
		return introduce;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getStopTime() {
		return stopTime;
	}
	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public Coach getCoach() {
		return coach;
	}
	public void setCoach(Coach coach) {
		this.coach = coach;
	}
	public void setCoachId(Long id) {
		if(coach == null) coach = new Coach();
		coach.setId(id);
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public int getCourseTypeId() {
		return courseTypeId;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	
	public void setCourseTypeId(int courseTypeId) {
		this.courseTypeId = courseTypeId;
	}
	
	@ManyToOne
	@JoinColumn(name = "parent_catalog_id")
	public Catalog getParentCatalog() {
		return parentCatalog;
	}
	public void setParentCatalog(Catalog parentCatalog) {
		this.parentCatalog = parentCatalog;
	}
	
	@ManyToOne
	@JoinColumn(name = "child_catalog_id")
	public Catalog getChildCatalog() {
		return childCatalog;
	}
	public void setChildCatalog(Catalog childCatalog) {
		this.childCatalog = childCatalog;
	}
	
	public void setParentCatalogId(Long id){
		if(parentCatalog == null) parentCatalog = new Catalog();
		parentCatalog.setId(id);
	}
	
	public void setChildCatalogId(Long id){
		if(childCatalog == null) childCatalog = new Catalog();
		childCatalog.setId(id);
	}
	
	public void setParentCatalogName(String catalogName){
		if(parentCatalog == null) parentCatalog = new Catalog();
		parentCatalog.setName(catalogName);
	}
	
	public void setChildCatalogName(String catalogName){
		if(childCatalog == null) childCatalog = new Catalog();
		childCatalog.setName(catalogName);
	}
	public int getClassType() {
		return classType;
	}
	public void setClassType(int classType) {
		this.classType = classType;
	}
	

}

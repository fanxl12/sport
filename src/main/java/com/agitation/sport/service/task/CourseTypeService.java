package com.agitation.sport.service.task;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agitation.sport.entity.CourseType;
import com.agitation.sport.repository.CourseTypeDao;

//Spring Bean的标识.
@Component
public class CourseTypeService {

	@Autowired
	private CourseTypeDao courseTypeDao;

	public CourseType getCourseType(Long id) {
		return courseTypeDao.findOne(id);
	}
	
	public List<CourseType> getAllCourseTypes(){
		return courseTypeDao.findAll();
	}
	
	public List<Map<String, Object>> getAllCourseTypeList(Map<String, Object> param){
		return courseTypeDao.findAllByMobile(param);
	}
	
	@Transactional(readOnly = false)
	public void saveCourseType(CourseType entity) {
		courseTypeDao.save(entity);
	}
	

	@Transactional(readOnly = false)
	public void deleteCourseType(Long id) {
		courseTypeDao.delete(id);
	}

	public void updateCourseType(CourseType entity) {
		courseTypeDao.update(entity);
	}

}

package com.agitation.sport.service.task;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agitation.sport.entity.Course;
import com.agitation.sport.entity.MyPage;
import com.agitation.sport.repository.CourseDao;

//Spring Bean的标识.
@Component
public class CourseService {

	@Autowired
	private CourseDao courseDao;

	public Course getCourse(Long id) {
		return courseDao.findOne(id);
	}
	
	public List<Course> getAllCourses(MyPage<Map<String, Object>> page){
		return courseDao.findAll(page);
	}
	
	public List<Map<String, Object>> getAllCourseList(MyPage<Map<String, Object>> page){
		
		return courseDao.findAllByMobile(page);
	}
	
	public List<Map<String, Object>> getOpenCourses(MyPage<Map<String, Object>> page){
		return courseDao.getOpenCourses(page);
	}
	
	public Map<String, Object> getCourseDetail(Map<String, Object> param){
		return courseDao.getCourseDetail(param);
	}

	@Transactional(readOnly = false)
	public void saveCourse(Course entity) {
		courseDao.save(entity);
	}
	

	@Transactional(readOnly = false)
	public void deleteCourse(Long id) {
		courseDao.delete(id);
	}

	public void updateCourse(Course entity) {
		courseDao.update(entity);
	}
	
	public Map<String, Object> getSellerByCourseId(Map<String, Object> param){
		return courseDao.getSellerByCourseId(param);
	}
	
	public List<Map<String, Object>> getPastOpenCourse(MyPage<Map<String, Object>> page){
		return courseDao.getPastOpenCourse(page);
	}
	
}

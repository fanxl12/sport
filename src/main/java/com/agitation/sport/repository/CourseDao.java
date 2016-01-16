package com.agitation.sport.repository;

import java.util.List;
import java.util.Map;

import com.agitation.sport.entity.Course;
import com.agitation.sport.entity.MyPage;

@MyBatisRepository
public interface CourseDao {
	
	void save(Course course);

	Course findOne(Long id);
	
	List<Course> findAll(MyPage<Map<String, Object>> page);
	
	//查询课程列表
	List<Map<String, Object>> findAllByMobile(MyPage<Map<String, Object>> page);
	
	//获取公开课
	List<Map<String, Object>> getOpenCourses(MyPage<Map<String, Object>> page);
	
	int delete(Long id);
	
	void update(Course course);
	
	Map<String, Object> getCourseDetail(Map<String, Object> param);
	
	Map<String, Object> getSellerByCourseId(Map<String, Object> param);
}

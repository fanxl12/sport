package com.agitation.sport.repository;

import java.util.List;
import java.util.Map;

import com.agitation.sport.entity.CourseType;

@MyBatisRepository
public interface CourseTypeDao {
	
	void save(CourseType courseType);

	CourseType findOne(Long id);
	
	List<CourseType> findAll();
	
	List<Map<String, Object>> findAllByMobile(Map<String, Object> param);
	
	int delete(Long id);
	
	void update(CourseType courseType);
}

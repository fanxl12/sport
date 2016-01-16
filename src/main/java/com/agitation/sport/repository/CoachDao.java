package com.agitation.sport.repository;

import java.util.List;
import java.util.Map;

import com.agitation.sport.entity.Coach;

@MyBatisRepository
public interface CoachDao {
	
	void save(Coach coach);

	Coach findOne(Long id);
	
	List<Coach> findAll();
	
	Map<String, Object> findCoachByMobile(Map<String, Object> param);
	
	int delete(Long id);
	
	void update(Coach coach);
}

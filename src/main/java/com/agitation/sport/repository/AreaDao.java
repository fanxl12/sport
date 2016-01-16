package com.agitation.sport.repository;

import java.util.List;
import java.util.Map;

import com.agitation.sport.entity.Area;

@MyBatisRepository
public interface AreaDao {
	
	void save(Area area);

	Area findOne(Long id);
	
	List<Map<String, Object>> findAll();
	
	int delete(Long id);
	
	void update(Area area);
}

package com.agitation.sport.repository;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface CollectDao {
	
	int save(Map<String, Object> param);

	List<Map<String, Object>> getMyCollects(Map<String, Object> param);
	
	int delete(Long id);
	
	Map<String, Object> getOne(Map<String, Object> param);
}

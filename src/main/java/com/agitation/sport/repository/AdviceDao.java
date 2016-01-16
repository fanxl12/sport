package com.agitation.sport.repository;

import java.util.List;
import java.util.Map;

import com.agitation.sport.entity.Advice;
import com.agitation.sport.entity.MyPage;

@MyBatisRepository
public interface AdviceDao {
	
	void save(Map<String, Object> param);

	List<Map<String, Object>> findCourseAdvices(MyPage<Map<String, Object>> page);
	
	int delete(Long id);
	
	void update(Advice advice);
}

package com.agitation.sport.repository;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface OrderDao {
	
	void save(Map<String, Object> param);

	List<Map<String, Object>> getCourseOrders(Map<String, Object> param);
	
	int payCourse(Map<String, Object> param);
	
	int updateCourse(Map<String, Object> param);
	
	int delete(String id);
	
	Map<String, Object> getOrderNumber(Map<String, Object> param);
	
	Map<String, Object> getUserInfoByOrderId(Map<String, Object> param);
	
	int confirmOrder(Map<String, Object> param);
	
	List<Map<String, Object>> getCourseOrder4Seller(Map<String, Object> param);
}

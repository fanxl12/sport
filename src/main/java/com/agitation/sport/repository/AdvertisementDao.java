package com.agitation.sport.repository;

import java.util.List;
import java.util.Map;

import com.agitation.sport.entity.Advertisement;

@MyBatisRepository
public interface AdvertisementDao {
	
	void save(Advertisement advertisement);

	Advertisement findOne(Long id);
	
	List<Advertisement> findAll();
	
	//手机查询广告
	List<Map<String, Object>> findAllByMobile(Map<String, Object> param);
	
	int delete(Long id);
	
	void update(Advertisement advertisement);
}

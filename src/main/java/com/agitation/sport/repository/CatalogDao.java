package com.agitation.sport.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.agitation.sport.entity.Catalog;

@MyBatisRepository
public interface CatalogDao {
	
	List<Map<String, Object>> getCourseParentCatalog();
	
	List<Map<String, Object>> getCourseChildCatalog(Map<String, Object> param);
	
	List<Map<String, Object>> findCourseList(Map<String, Object> param);
	
	Catalog findOne(Long id);

	List<Catalog> findByUserId(Long userId);

	List<Catalog> getParentCatalogs();
	
	List<Catalog> getAllCatalogs();

	List<Catalog> getChildCatalogs(Map<String, Object> param);
	
	List<Map<String, Object>> getAllCatalogList();

	List<Catalog> getNewCatalogs(Date lastUpdateDate);
	
	List<Catalog> getUpdatedCatalogs(Date date);

	void deleteByUserId(Long id);

	List<Catalog> findAll();
	
	void updateField(Map<String, Object> params);

	void save(Catalog newCatalog);

	void update(Catalog catalog);

	int delete(Long id);

	List<Map<String, Object>> getJsonDataList();
	
	int batchDeleteCatalog(Long[] ids);
	
	int batchDeleteChildCatalog(Long[] ids);
}

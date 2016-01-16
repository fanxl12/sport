package com.agitation.sport.service.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agitation.sport.entity.Catalog;
import com.agitation.sport.repository.CatalogDao;

//Spring Bean的标识.
@Component
public class CatalogService {

	@Autowired
	private CatalogDao catalogDao;
	
	public List<Map<String, Object>> getCourseParentCatalog(){
		return catalogDao.getCourseParentCatalog();
	}
	
	public List<Map<String, Object>> getCourseChildCatalog(Map<String, Object> params){
		return catalogDao.getCourseChildCatalog(params);
	}
	
	public List<Map<String, Object>> getCourseList(Map<String, Object> params){
		return catalogDao.findCourseList(params);
	}

	public Catalog getCatalog(Long id) {
		return catalogDao.findOne(id);
	}
	
	public void updateField(Map<String, Object> params){
		catalogDao.updateField(params);
	}
	
	public List<Catalog> getAllCatalogs(){
		return catalogDao.getAllCatalogs();
	}
	
	public List<Map<String, Object>> getAllCatalogList(){
		return catalogDao.getAllCatalogList();
	}

	@Transactional(readOnly = false)
	public void saveCatalog(Catalog entity) {
		catalogDao.save(entity);
	}
	

	@Transactional(readOnly = false)
	public void deleteCatalog(Long id) {
		catalogDao.delete(id);
	}

	public void updateCatalog(Catalog entity) {
		catalogDao.update(entity);
	}

	public List<Catalog> getAllCatalog() {
		return (List<Catalog>) catalogDao.findAll();
	}

	public List<Catalog> getNewCatalogs(Date lastUpdateDate) {
		return (List<Catalog>) catalogDao.getNewCatalogs(lastUpdateDate);
	}

	public List<Catalog> getUpdatedCatalogs(Date lastUpdateDate) {
		return (List<Catalog>) catalogDao.getUpdatedCatalogs(lastUpdateDate);
	}

	public List<Catalog> getParentList() {
		return (List<Catalog>) catalogDao.getParentCatalogs();
	}
	
	public int batchDeleteCatalog(Long[] ids){
		return catalogDao.batchDeleteCatalog(ids);
	}
	
	public int batchDeleteChildCatalog(Long[] ids){
		return catalogDao.batchDeleteChildCatalog(ids);
	}

	public List<Catalog> getChildCatalog() {
		return getChildCatalog(null);
	}

	public List<Catalog> getChildCatalog(Long parentId) {
		Map<String, Object> param = null;
		if (parentId != null) {
			param = new HashMap<String, Object>();
			param.put("parentId", parentId);
		}
		return (List<Catalog>) catalogDao.getChildCatalogs(param);
	}

	@Autowired
	public void setCatalogDao(CatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}
}

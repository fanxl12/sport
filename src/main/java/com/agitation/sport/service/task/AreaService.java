package com.agitation.sport.service.task;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agitation.sport.entity.Area;
import com.agitation.sport.repository.AreaDao;

//Spring Bean的标识.
@Component
public class AreaService {

	@Autowired
	private AreaDao areaDao;

	public Area getArea(Long id) {
		return areaDao.findOne(id);
	}
	
	public List<Map<String, Object>> getAllAreas(){
		return areaDao.findAll();
	}
	
	@Transactional(readOnly = false)
	public void saveArea(Area entity) {
		areaDao.save(entity);
	}
	

	@Transactional(readOnly = false)
	public void deleteArea(Long id) {
		areaDao.delete(id);
	}

	public void updateArea(Area entity) {
		areaDao.update(entity);
	}

}

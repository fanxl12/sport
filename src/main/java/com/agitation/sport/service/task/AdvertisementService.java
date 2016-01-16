package com.agitation.sport.service.task;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agitation.sport.entity.Advertisement;
import com.agitation.sport.repository.AdvertisementDao;

//Spring Bean的标识.
@Component
public class AdvertisementService {

	@Autowired
	private AdvertisementDao advertisementDao;

	public Advertisement getAdvertisement(Long id) {
		return advertisementDao.findOne(id);
	}
	
	public List<Advertisement> getAllAdvertisements(){
		return advertisementDao.findAll();
	}
	
	public List<Map<String, Object>> getAllAdvertisementList(Map<String, Object> param){
		return advertisementDao.findAllByMobile(param);
	}

	@Transactional(readOnly = false)
	public void saveAdvertisement(Advertisement entity) {
		advertisementDao.save(entity);
	}
	

	@Transactional(readOnly = false)
	public void deleteAdvertisement(Long id) {
		advertisementDao.delete(id);
	}

	public void updateAdvertisement(Advertisement entity) {
		advertisementDao.update(entity);
	}

}

package com.agitation.sport.service.task;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agitation.sport.entity.Coach;
import com.agitation.sport.repository.CoachDao;

//Spring Bean的标识.
@Component
public class CoachService {

	@Autowired
	private CoachDao coachDao;

	public Coach getCoach(Long id) {
		return coachDao.findOne(id);
	}
	
	public List<Coach> getAllCoachs(){
		return coachDao.findAll();
	}
	
	public Map<String, Object> findCoachByMobile(Map<String, Object> param){
		return coachDao.findCoachByMobile(param);
	}

	@Transactional(readOnly = false)
	public void saveCoach(Coach entity) {
		coachDao.save(entity);
	}
	

	@Transactional(readOnly = false)
	public void deleteCoach(Long id) {
		coachDao.delete(id);
	}

	public void updateCoach(Coach entity) {
		coachDao.update(entity);
	}

}

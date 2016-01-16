package com.agitation.sport.service.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agitation.sport.repository.CollectDao;
import com.agitation.sport.service.account.ShiroDbRealm.ShiroUser;

//Spring Bean的标识.
@Component
public class CollectService {

	@Autowired
	private CollectDao collectDao;

	public List<Map<String, Object>> getMyCollects(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", getCurrentUserId());
		return collectDao.getMyCollects(param);
	}
	
	public Map<String, Object> getOne(Map<String, Object> param){
		if(param!=null){
			param.put("userId", getCurrentUserId());
		}
		return collectDao.getOne(param);
	}

	@Transactional(readOnly = false)
	public int saveCollect(Map<String, Object> param) {
		if(param!=null){
			param.put("userId", getCurrentUserId());
		}
		return collectDao.save(param);
	}
	

	@Transactional(readOnly = false)
	public void deleteCollect(Long id) {
		collectDao.delete(id);
	}

	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}

}

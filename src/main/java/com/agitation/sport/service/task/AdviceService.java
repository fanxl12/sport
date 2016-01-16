package com.agitation.sport.service.task;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agitation.sport.entity.Advice;
import com.agitation.sport.entity.MyPage;
import com.agitation.sport.repository.AdviceDao;
import com.agitation.sport.repository.OrderDao;
import com.agitation.sport.service.account.ShiroDbRealm.ShiroUser;

//Spring Bean的标识.
@Component
public class AdviceService {

	@Autowired
	private AdviceDao adviceDao;
	
	@Autowired
	private OrderDao orderDao;

	public List<Map<String, Object>> findCourseAdvices(MyPage<Map<String, Object>> page){
		return adviceDao.findCourseAdvices(page);
	}

	@Transactional(readOnly = false)
	public void saveAdvice(Map<String, Object> param) {
		if(param!=null){
			param.put("userId", getCurrentUserId());
		}
		adviceDao.save(param);
		orderDao.updateCourse(param);
	}
	

	@Transactional(readOnly = false)
	public void deleteAdvice(Long id) {
		adviceDao.delete(id);
	}

	public void updateAdvice(Advice entity) {
		adviceDao.update(entity);
	}
	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}

}

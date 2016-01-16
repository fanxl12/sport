package com.agitation.sport.service.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agitation.sport.repository.OrderDao;
import com.agitation.sport.service.account.ShiroDbRealm.ShiroUser;

//Spring Bean的标识.
@Component
public class OrderService {

	@Autowired
	private OrderDao orderDao;

	public List<Map<String, Object>> getCourseOrders(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", getCurrentUserId());
		return orderDao.getCourseOrders(param);
	}

	@Transactional(readOnly = false)
	public void saveOrder(Map<String, Object> param) {
		if(param!=null){
			param.put("userId", getCurrentUserId());
		}
		orderDao.save(param);
	}
	
	public void payCourse(Map<String, Object> param){
		orderDao.payCourse(param);
	}
	
	public void updateCourse(Map<String, Object> param){
		orderDao.updateCourse(param);
	}

	@Transactional(readOnly = false)
	public boolean deleteOrder(String id) {
		return orderDao.delete(id)>0;
	}

	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
	
	public Map<String, Object> getOrderNumber(Map<String, Object> param){
		if(param!=null){
			param.put("userId", getCurrentUserId());
		}
		return orderDao.getOrderNumber(param);
	}
	
	public boolean confirmOrder(Map<String, Object> param){
		param.put("user", SecurityUtils.getSubject().getPrincipal());
		return orderDao.confirmOrder(param)>0;
	}
	
	public Map<String, Object> getUserInfoByOrderId(Map<String, Object> param){
		return orderDao.getUserInfoByOrderId(param);
	}
	
	public List<Map<String, Object>> getCourseOrder4Seller(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user", SecurityUtils.getSubject().getPrincipal());
		return orderDao.getCourseOrder4Seller(param);
	}

}

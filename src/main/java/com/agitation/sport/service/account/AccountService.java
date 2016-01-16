/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.agitation.sport.service.account;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;

import com.agitation.sport.entity.User;
import com.agitation.sport.repository.UserDao;
import com.agitation.sport.service.ServiceException;
import com.agitation.sport.service.account.ShiroDbRealm.ShiroUser;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional
public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	private UserDao userDao;
	private Clock clock = Clock.DEFAULT;

	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}

	public User getUser(Long id) {
		return userDao.findOne(id);
	}

	/**
	 * 根据登录名查找用户
	 * @param loginName
	 * @return User
	 */
	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}

	/**
	 * 用户注册
	 * @param user
	 */
	public void registerUser(User user) {
		entryptPassword(user);
		user.setRegisterDate(clock.getCurrentDate());
		userDao.save(user);
	}

	/**
	 * 更改用户密码
	 * @param user
	 */
	public void updatePw(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		userDao.updatePw(user);
	}
	
	/**
	 * 更改用户设备Tokens
	 * @param user
	 */
	public void updateDeviceTokens(User user) {
		userDao.updateDeviceTokens(user);
	}
	

	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	/**
	 * 对密码进行加密处理
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}
	
	public void updateUser(Map<String, Object> param, Map<String, Object> result){
		String action = param.get("action")+"";
		User user = new User(getCurrentUserId(param));
		if("updateName".equals(action)){
			user.setName(param.get("name")+"");
			userDao.updateName(user);
		}else if("updateSex".equals(action)){
			user.setSex(Boolean.parseBoolean(param.get("sex")+""));
			userDao.updateSex(user);
		}else if("updateAge".equals(action)){
			user.setAge(Integer.parseInt(param.get("age")+""));
			if(param.get("birthday")!=null){
				SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd"); 
				try {
					user.setBirthday(time.parse(param.get("birthday")+""));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			userDao.updateAge(user);
		}else if("updatePhoneNumber".equals(action)){
			user.setPhoneNumber(param.get("phoneNumber")+"");
			userDao.updatePhoneNumber(user);
		}else if("updateAddress".equals(action)){
			user.setAddress(param.get("address")+"");
			userDao.updateAddress(user);
		}else{
			result.put("result", false);
			result.put("error", "action字段传递错误,请检查!");
		}
	}
	
	public void updateUserHead(User user){
		userDao.updateHead(user);
	}
	
	private Long getCurrentUserId(Map<String, Object> param){
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()){
			ShiroUser user = (ShiroUser) currentUser.getPrincipal();
			return user.id;
		}else{
			currentUser.login(new UsernamePasswordToken(param.get("userName")+"", param.get("passWord")+""));
			ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			return user.id;
		}
	}
}

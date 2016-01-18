package com.agitation.sport.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.agitation.sport.entity.User;
import com.agitation.sport.service.account.AccountService;
import com.agitation.sport.service.account.ShiroDbRealm.ShiroUser;
import com.agitation.sport.util.FileUtil;
import com.agitation.sport.util.SMSService;
import com.agitation.sport.util.SecurityAliUtils;


@Controller
@RequestMapping(value = "/baseApi")
public class ApiLoginController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private FileUtil fileUtil;
	
	@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> login(@RequestParam Map<String, Object> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Subject currentUser = SecurityUtils.getSubject();
		boolean loginSuccess = false;
		// 用户已经登录过了，直接进主页面
		if (currentUser.isAuthenticated()) {
			loginSuccess = true;
		}
		
		String sign = param.get("signStr")+ "";
		String jia = param.get("jia")+"";
		String yuan = SecurityAliUtils.decryptStr(jia);
		if(SecurityAliUtils.checkSign(yuan, sign)){
			System.out.println("数据正确，解密结果:"+yuan);
		}else{
			System.out.println("数据错误，解密结果:"+yuan);
		}
		
		try {
			currentUser.login(new UsernamePasswordToken(param.get("userName")+"", param.get("passWord")+""));
			loginSuccess = true;
			ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			if(!param.get("roles").equals(user.getRoles())){
				result.put("result", false);
				if(param.get("roles").equals("user")){
					result.put("error", "您还未注册卖家版账号, 请注册后登录");
				}else{
					result.put("error", "此账号是卖家APP账号, 请重新注册登录");
				}
				return result;
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("sex", user.isSex());
			data.put("age", user.getAge());
			data.put("address", user.getAddress());
			data.put("head", user.getHead());
			data.put("phoneNumber", user.getPhoneNumber());
			data.put("name", user.getName());
			data.put("id", user.id);
			result.put("retData", data);
		}catch (CannotGetJdbcConnectionException e) {
			result.put("result", false);
			return result;
		} catch (Exception e) {
			int i = ExceptionUtils.indexOfThrowable(e, CannotGetJdbcConnectionException.class);
			if (i >= 0) {
				result.put("error", "不能连接数据库,请联系管理员");
				result.put("result", false);
				return result;
			}
			result.put("error", "用户名或密码错，请核对！");
			result.put("result", false);
			return result;
		}
		
		result.put("result", loginSuccess);
		return result;
	}
	
	@RequestMapping(value = "/logout", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "true");
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/register",  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> vipRegister(@RequestParam Map<String, Object> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		//首先根据手机号码去查询是否注册过 ，有注册提醒用户已注册
		User user = accountService.findUserByLoginName(param.get("userName")+"");
		result.put("result", true);
		if (user!=null) {
			result.put("result", false);
			result.put("error", "该手机号已被注册，请更换号码");
		}else{
			User registerUser = new User();
			registerUser.setLoginName(param.get("userName")+"");
			registerUser.setName(param.get("userName")+"");
			registerUser.setPlainPassword(param.get("passWord")+"");
			registerUser.setPhoneNumber(param.get("userName")+"");
			registerUser.setRoles("user");
			accountService.registerUser(registerUser);
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getPhoneCode",  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getPhoneCode(@RequestParam Map<String, Object> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(param.get("phoneNumber")==null || param.get("phoneNumber").toString().trim().length()!=11){
			result.put("result", false);
			result.put("error", "手机号码错误");
		}else{
			SMSService.sendCode(param.get("action")+"", param.get("phoneNumber")+"", result);
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updatePw",  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> updatePw(@RequestParam Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		//首先根据手机号码去查询是否注册过 ，有注册提醒用户已注册
		User user = accountService.findUserByLoginName(param.get("userName")+"");
		result.put("result", true);
		if (user!=null) {
			user.setPlainPassword(param.get("passWord")+"");
			accountService.updatePw(user);
		}else{
			result.put("result", false);
			result.put("error", "该手机号码未注册,请重新填写！");
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateUser",  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateUser(@RequestParam Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		accountService.updateUser(param, result);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateUserHead", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> commitTickey(@RequestParam(required=false) Map<String, Object> param, 
			MultipartHttpServletRequest request){
		
		User user = new User(getCurrentUserId());
		
		User old = accountService.getUser(getCurrentUserId());
		String oldPath = fileUtil.getRealPath() + old.getHead();
		
		List<MultipartFile> fileList = request.getFiles("file");
		writeFileToDisk(fileList, user);
		accountService.updateUserHead(user);
		
		File file = new File(oldPath);
		if(file.exists()){
			file.delete();
		}
		
//		Map<String, Object> retData = new HashMap<String, Object>();
//		retData.put("head", user.getHead());
//		result.put("retData", retData);
		return new ResponseEntity<String>(user.getHead(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateDeviceTokens",  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateDeviceTokens(@RequestParam Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = new User(getCurrentUserId());
		if(param.get("deviceTokens")!=null && StringUtils.isNoneEmpty(param.get("deviceTokens")+"")){
			user.setDeviceTokens(param.get("deviceTokens")+"");
			accountService.updateDeviceTokens(user);
			result.put("result", true);
		}else{
			result.put("result", false);
			result.put("error", "deviceTokens为空");
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	/**
	 * 保存上传的文件
	 * @param fileList
	 * @param user
	 */
	private void writeFileToDisk(List<MultipartFile> fileList, User user) {
		for (MultipartFile file : fileList) {
			InputStream is = null;
			FileOutputStream fos = null;
			
			String name = file.getOriginalFilename();
			String fileType = name.substring(name.lastIndexOf("."));
			
			String shortPath = "/static/upload/userHead/";
			String fileName = new Date().getTime() + fileType;
			
			// 获取本地存储路径
			String path = fileUtil.createMultiFolders(shortPath) + fileName; // 获取本地存储路径
			File savePath = new File(path); // 新建一个文件
			
			try {
				System.out.println(file.getOriginalFilename()+"--->"+file.getSize());
				is =  file.getInputStream();
				fos = new FileOutputStream(savePath);
				
				byte[] buffer = new byte[1024];
				int len=0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				} 
				fos.flush();
				
				user.setHead(shortPath+fileName);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	private Long getCurrentUserId(){
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
	

}

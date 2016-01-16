package com.agitation.sport.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agitation.sport.push.PushServices;
import com.agitation.sport.service.task.CourseService;
import com.agitation.sport.service.task.OrderService;

/**
 * Catalog的Restful API的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/api/v1/order")
public class OrderRestController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CourseService courseService;
	
	// 获取课程订单
	@RequestMapping(value = "/getCourseOrderList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getCourseOrderList() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		Map<String, Object> retData = new HashMap<String, Object>();
		List<Map<String, Object>> courseOrderList = orderService.getCourseOrders();
		retData.put("courseOrderList", courseOrderList);
		result.put("retData", retData);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	// 商户获取课程订单
	@RequestMapping(value = "/getCourseOrder4Seller", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getCourseOrder4Seller() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		Map<String, Object> retData = new HashMap<String, Object>();
		List<Map<String, Object>> courseOrderList = orderService.getCourseOrder4Seller();
		retData.put("courseOrderList", courseOrderList);
		result.put("retData", retData);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	//提交订单接口
	@RequestMapping(value = "/commitCourse", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> commitCourse(@RequestParam Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		Map<String, Object> retData = new HashMap<String, Object>();
		double totalMoney = Double.parseDouble(param.get("totalMoney")+"");
		String moneyStr = totalMoney*100+"";
		String id = "HSD"+System.currentTimeMillis() + moneyStr.substring(0, 2);
		param.put("id", id);
		
		orderService.saveOrder(param);
		
		retData.put("orderId", id);
		retData.put("name", param.get("name"));
		result.put("retData", retData);
		
//		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		
//		String name = user.getName()+"用户";
//		String courseName = param.get("name")+"";
//		String time = DateUtil.strDateToStr(param.get("startTime")+"");
		
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	//课程订单支付接口
	@RequestMapping(value = "/payCourse", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> payCourse(@RequestParam Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		// Map<String, Object> retData = new HashMap<String, Object>();
		orderService.payCourse(param);
		//推送订单购买信息给场馆
		Map<String, Object> sellInfo = courseService.getSellerByCourseId(param);
		if(sellInfo!=null){
			String name = sellInfo.get("loginName")+"";
			if(StringUtils.isNoneEmpty(name)){
				try {
					PushServices.sendMsgToAndroidCustomCast(name, "新订单通知", "有用户购买了您的课程,请尽快查看确认哦", "课程购买提醒", false);
					PushServices.sendMsgToIOSCustomCast(name, "新订单通知");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// result.put("retData", retData);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	//获取我的中心订单数量
	@RequestMapping(value = "/getOrderNumber", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getOrderNumber() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		Map<String, Object> param = new HashMap<String, Object>();
		result.put("retData", orderService.getOrderNumber(param));
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	//课程订单删除接口
	@RequestMapping(value = "/deleteOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> deleteOrder(@RequestParam Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(orderService.deleteOrder(param.get("id")+"")){
			result.put("result", true);
		}else{
			result.put("result", false);
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	//课程订单确认
	@RequestMapping(value = "/confirmOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> confirmOrder(HttpServletRequest request, @RequestParam Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(param==null || param.get("id")==null){
			result.put("result", false);
			result.put("error", "订单id为null");
		}else{
			if(orderService.confirmOrder(param)){
				result.put("result", true);
				//推送订单确认信息给购买用户
				Map<String, Object> userInfo = orderService.getUserInfoByOrderId(param);
				if(userInfo!=null){
					String name = userInfo.get("loginName")+"";
					if(StringUtils.isNoneEmpty(name)){
						try {
							PushServices.sendMsgToAndroidCustomCast(name, "订单确认通知", "您的订单已经被确认, 请查看", "订单确认提醒", true);
							PushServices.sendMsgToIOSCustomCast(name, "订单确认通知");
//							if(UtilHelper.isAndroidDevice(request.getHeader("User-Agent"))){
//								
//							}else{
//								
//							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}else{
				result.put("result", false);
				result.put("error", "订单确认失败");
			}
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	

}

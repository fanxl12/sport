package com.agitation.sport.rest;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agitation.sport.entity.MyPage;
import com.agitation.sport.service.task.AdviceService;

/**
 * Catalog的Restful API的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/api/v1/advice")
public class AdviceRestController {

	@Autowired
	private AdviceService adviceService;
	
	@RequestMapping(value = "/getAdviceList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getAdviceList(@RequestParam Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		Map<String, Object> retData = new HashMap<String, Object>();
		
		MyPage<Map<String, Object>> page = new MyPage<Map<String, Object>>();
		if(param.get("pageNumber")==null || StringUtils.isEmpty(param.get("pageNumber")+"")){
			page.setNumber(1); 
		}else{
			page.setNumber(Integer.parseInt(param.get("pageNumber")+""));
			page.setSize(Integer.parseInt(param.get("pageSize")+""));
		}
	    page.setParams(param);
		retData.put("advices", adviceService.findCourseAdvices(page));
		result.put("retData", retData);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/saveAdvice", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> saveAdvice(@RequestParam Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		adviceService.saveAdvice(param);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
}

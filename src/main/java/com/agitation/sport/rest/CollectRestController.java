package com.agitation.sport.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agitation.sport.service.task.CollectService;

/**
 * Catalog的Restful API的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/api/v1/collect")
public class CollectRestController {

	@Autowired
	private CollectService collectService;
	
	@RequestMapping(value = "/getCollectList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getCollectList(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		Map<String, Object> retData = new HashMap<String, Object>();
		retData.put("collects", collectService.getMyCollects());
		result.put("retData", retData);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> save(@RequestParam Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> collect = collectService.getOne(param);
		if (collect==null || collect.size()==0) {
			int count = collectService.saveCollect(param);
			if(count>0){
				result.put("result", true);
				Map<String, Object> retData = new HashMap<String, Object>();
				retData.put("collectionId", param.get("id"));
				result.put("retData", retData);
			}else{
				result.put("result", false);
				result.put("error", "收藏失败");
			}
		}else{
			result.put("result", false);
			result.put("error", "此课程已经收藏过，不能再次收藏");
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteCourse", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> deleteCourse(@RequestParam Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		if(param.get("collectionId")!=null && !"null".equals(param.get("collectionId"))){
			result.put("result", true);
			Long collectionId = Long.parseLong(param.get("collectionId")+"");
			collectService.deleteCollect(collectionId);
		}else{
			result.put("result", false);
			result.put("error", "collectionId不能为null");
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
}

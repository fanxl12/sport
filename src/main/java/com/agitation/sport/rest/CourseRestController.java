package com.agitation.sport.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;
import org.springside.modules.beanvalidator.BeanValidators;

import com.agitation.sport.entity.Catalog;
import com.agitation.sport.entity.MyPage;
import com.agitation.sport.service.task.AdvertisementService;
import com.agitation.sport.service.task.AreaService;
import com.agitation.sport.service.task.CatalogService;
import com.agitation.sport.service.task.CourseService;
import com.agitation.sport.service.task.CourseTypeService;
import com.agitation.sport.util.CompareDis;
import com.agitation.sport.util.DateUtil;
import com.agitation.sport.util.FileUtil;
import com.agitation.sport.util.UtilHelper;

/**
 * Catalog的Restful API的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/api/v1/course")
public class CourseRestController {

	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private CourseService coureseService;
	
	@Autowired
	private AdvertisementService advertisementService;
	
	@Autowired
	private FileUtil fileUtil;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private CourseTypeService courseTypeService;

	@Autowired
	private Validator validator;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Catalog> list() {
		return catalogService.getAllCatalog();
	}
	
	@RequestMapping(value = "/getCourseParentCatalog", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getCourseParentCatalog(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		Map<String, Object> retData = new HashMap<String, Object>();
		
		String imageProfix = fileUtil.getImageProfix();
		retData.put("imageProfix", imageProfix);
		
		retData.put("parentCatalogs", catalogService.getCourseParentCatalog());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", 0);
		retData.put("adversitements", advertisementService.getAllAdvertisementList(param));
		result.put("retData", retData);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getCourseChildCatalog", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getCourseChildCatalog(@RequestParam Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		Map<String, Object> retData = new HashMap<String, Object>();
		retData.put("childCatalogs", catalogService.getCourseChildCatalog(param));
		result.put("retData", retData);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getCourseDetail", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getCourseDetail(@RequestParam Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		Map<String, Object> retData = new HashMap<String, Object>();
		retData.put("course", coureseService.getCourseDetail(param));
		result.put("retData", retData);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getCourseList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getCourseList(@RequestParam Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		Map<String, Object> retData = new HashMap<String, Object>();
		
		if(param.get("sort")==null || "distanceSort".equals(param.get("sort")) || "defaultSort".equals(param.get("sort"))){
			param.put("sort", "a.price");
			param.put("sortByDistance", true);
		}
		
		MyPage<Map<String, Object>> page = new MyPage<Map<String, Object>>();
		if(param.get("pageNumber")==null || StringUtils.isEmpty(param.get("pageNumber")+"")){
			page.setNumber(1); 
		}else{
			page.setNumber(Integer.parseInt(param.get("pageNumber")+""));
			page.setSize(Integer.parseInt(param.get("pageSize")+""));
		}
	    page.setParams(param);
		
		List<Map<String, Object>> courseList = coureseService.getAllCourseList(page);
		
		List<Map<String, Object>> list = sortByData(courseList, param, result);
		
		retData.put("courseList", list);
		
		result.put("retData", retData);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getMenuData", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getMenuData(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		Map<String, Object> retData = new HashMap<String, Object>();
		setScreenData(retData);
		result.put("retData", retData);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	private void setScreenData(Map<String, Object> retData){
		
		//排序参数
		List<Map<String, Object>> sortList = new ArrayList<Map<String, Object>>();
		Map<String, Object> sort1 = new HashMap<String, Object>();
		sort1.put("sort", "defaultSort");
		sort1.put("name", "默认排序");
		
		Map<String, Object> sort2 = new HashMap<String, Object>();
		sort2.put("sort", "distanceSort");
		sort2.put("name", "距离优先");
		
		Map<String, Object> sort3 = new HashMap<String, Object>();
		sort3.put("sort", "a.price");
		sort3.put("name", "价格优先");
		
		Map<String, Object> sort4 = new HashMap<String, Object>();
		sort4.put("sort", "a.totalScore DESC");
		sort4.put("name", "评价优先");
		
		sortList.add(sort1);
		sortList.add(sort2);
		sortList.add(sort3);
		sortList.add(sort4);
		
		retData.put("sortList", sortList);
		
		//时间参数
		Date currentDate = new Date(System.currentTimeMillis());
		List<Map<String, Object>> timeList = new ArrayList<Map<String, Object>>();
		for(int i=0; i<4; i++){
			Map<String, Object> time = new HashMap<String, Object>();
			if(i==0){
				time.put("name", DateUtil.getWeekOfDate(currentDate, i));
			}else if(i==1){
				time.put("name", DateUtil.getWeekOfDate(currentDate, i));
			}else if(i==2){
				time.put("name", DateUtil.getWeekOfDate(currentDate, i));
			}else if(i==3){
				time.put("name", DateUtil.getWeekOfDate(currentDate, i));
			}
			
			List<Map<String, Object>> dayList = new ArrayList<Map<String,Object>>();
			Map<String, Object> allDay = new HashMap<String, Object>();
			allDay.put("name", "全天(06:00 - 22:00)");
			allDay.put("showName", time.get("name"));
			allDay.put("startTime", DateUtil.getDateOfDay(currentDate, i)+" 06:00");
			allDay.put("endTime", DateUtil.getDateOfDay(currentDate, i)+" 22:00");
			dayList.add(allDay);
			
			Map<String, Object> morning = new HashMap<String, Object>();
			morning.put("name", "上午(06:00 - 11:00)");
			morning.put("showName", time.get("name")+" 上午");
			morning.put("startTime", DateUtil.getDateOfDay(currentDate, i)+" 06:00");
			morning.put("endTime", DateUtil.getDateOfDay(currentDate, i)+" 11:00");
			dayList.add(morning);
			
			Map<String, Object> noon = new HashMap<String, Object>();
			noon.put("name", "中午(11:00 - 14:00)");
			noon.put("showName", time.get("name")+" 中午");
			noon.put("startTime", DateUtil.getDateOfDay(currentDate, i)+" 11:00");
			noon.put("endTime", DateUtil.getDateOfDay(currentDate, i)+" 14:00");
			dayList.add(noon);
			
			Map<String, Object> afternoon = new HashMap<String, Object>();
			afternoon.put("name", "下午(14:00 - 18:30)");
			afternoon.put("showName", time.get("name")+" 下午");
			afternoon.put("startTime", DateUtil.getDateOfDay(currentDate, i)+" 14:00");
			afternoon.put("endTime", DateUtil.getDateOfDay(currentDate, i)+" 18:30");
			dayList.add(afternoon);
			
			Map<String, Object> night = new HashMap<String, Object>();
			night.put("name", "晚上(18:30 - 22:00)");
			night.put("showName", time.get("name")+" 晚上");
			night.put("startTime", DateUtil.getDateOfDay(currentDate, i)+" 18:30");
			night.put("endTime", DateUtil.getDateOfDay(currentDate, i)+" 22:00");
			dayList.add(night);
			time.put("child", dayList);
			timeList.add(time);
		}
		retData.put("timeList", timeList);
		
		//位置参数
		List<Map<String, Object>> areas = areaService.getAllAreas();
		List<Map<String, Object>> nearByList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> all = new HashMap<String, Object>();
		all.put("name", "全城");
		all.put("value", 0);
		nearByList.add(all);
		Map<String, Object> fiveMi = new HashMap<String, Object>();
		fiveMi.put("name", "500m");
		fiveMi.put("value", 500);
		nearByList.add(fiveMi);
		Map<String, Object> tenMi = new HashMap<String, Object>();
		tenMi.put("name", "1000m");
		tenMi.put("value", 1000);
		nearByList.add(tenMi);
		Map<String, Object> threeMi = new HashMap<String, Object>();
		threeMi.put("name", "1000m");
		threeMi.put("value", 1000);
		nearByList.add(threeMi);
		Map<String, Object> fiftyMi = new HashMap<String, Object>();
		fiftyMi.put("name", "5000m");
		fiftyMi.put("value", 5000);
		nearByList.add(fiftyMi);
		
		Map<String, Object> nearBy = new HashMap<String, Object>();
		nearBy.put("name", "附近");
		nearBy.put("child", nearByList);
		areas.add(0, nearBy);
		
		retData.put("areas", areas);
		
		//类型参数
		retData.put("courseTypes", courseTypeService.getAllCourseTypes());
	}
	
	@RequestMapping(value = "/getOpenCourse", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getOpenCourse(@RequestParam Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		Map<String, Object> retData = new HashMap<String, Object>();
		if(param.get("sort")==null || StringUtils.isEmpty(param.get("sort")+"") || "defaultSort".equals(param.get("sort"))){
			param.put("sort", "a.price");
		}
		
		MyPage<Map<String, Object>> page = new MyPage<Map<String, Object>>();
		if(param.get("pageNumber")==null || StringUtils.isEmpty(param.get("pageNumber")+"")){
			page.setNumber(1); 
		}else{
			page.setNumber(Integer.parseInt(param.get("pageNumber")+""));
			page.setSize(Integer.parseInt(param.get("pageSize")+""));
		}
	    page.setParams(param);
		
		List<Map<String, Object>> courseList = coureseService.getOpenCourses(page);
		
		List<Map<String, Object>> list = sortByData(courseList, param, result);
		
		retData.put("courseList", list);
		
		result.put("retData", retData);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	private List<Map<String, Object>> sortByData(List<Map<String, Object>> courseList, Map<String, Object> param, Map<String, Object> result){
		
		//根据经纬度计算距离
		if(param.get("longitude")!=null && param.get("latitude")!=null){
			double longitude = Double.parseDouble(param.get("longitude")+"");
			double latitude = Double.parseDouble(param.get("latitude")+"");
			
			for (Map<String, Object> item : courseList) {
				double pLong = Double.parseDouble(item.get("longitude")+"");
				double pLat = Double.parseDouble(item.get("latitude")+"");
				double distance = UtilHelper.getDistance(longitude, latitude, pLong, pLat);
				item.put("distance", distance);
			}
			
			//距离排序 和默认排序
			if(Boolean.parseBoolean(param.get("sortByDistance")+"")){
				//进行距离的升序排序
				Collections.sort(courseList, new CompareDis());
			}
			
			if(param.get("range")!=null && !"0".equals(param.get("range"))  && StringUtils.isNotEmpty(param.get("range")+"")){
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				double range = Double.parseDouble(param.get("range")+"");
				for (Map<String, Object> item : courseList) {
					double distance = Double.parseDouble(item.get("distance")+"");
					if(range>distance){
						list.add(item);
					}
				}
				return list;
			}
		}
		return courseList;
	}
	
	//--------------------------------------------------------------------------------
	

	@RequestMapping(value = "getChildCatalog/{parentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getChildCatalog(@PathVariable("parentId") Long parentId) {
		List<Catalog> catalogs = catalogService.getChildCatalog(parentId);
		if (catalogs == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Catalog>>(catalogs, HttpStatus.OK);
	}
	
	@RequestMapping(value = "saveCatalog/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> saveCatalog(@PathVariable("name") String name) {
		Catalog catalog = new Catalog();
		catalog.setName(name);
		catalogService.saveCatalog(catalog);
		return new ResponseEntity(catalog, HttpStatus.OK);
	}
	
	@RequestMapping(value = "saveChildCatalog/{parentId}/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> saveChildCatalog(@PathVariable("parentId") Long id, @PathVariable("name") String name) {
		Catalog catalog = new Catalog();
		Catalog parentCatalog = new Catalog();
		parentCatalog.setId(id);
		catalog.setParentCatalog(parentCatalog);
		catalog.setName(name);
		catalogService.saveCatalog(catalog);
		return new ResponseEntity(catalog, HttpStatus.OK);
	}
	
	@RequestMapping(value="updateField", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateField(@RequestBody Map<String, Object> params) {
		catalogService.updateField(params);
		return new ResponseEntity(null, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody Catalog catalog, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<Catalog>> failures = validator.validate(catalog);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存任务
		catalogService.saveCatalog(catalog);

		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		Long id = catalog.getId();
		URI uri = uriBuilder.path("/api/v1/catalog/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Catalog catalog) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<Catalog>> failures = validator.validate(catalog);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		catalogService.saveCatalog(catalog);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		catalogService.deleteCatalog(id);
	}
	
//	/**
//	 * 取出Shiro中的当前用户Id.
//	 */
//	private Long getCurrentUserId() {
//		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
//		return user.id;
//	}
}

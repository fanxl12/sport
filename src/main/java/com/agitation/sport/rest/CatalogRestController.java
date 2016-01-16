package com.agitation.sport.rest;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.shiro.SecurityUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;
import org.springside.modules.beanvalidator.BeanValidators;

import com.agitation.sport.entity.Catalog;
import com.agitation.sport.service.account.ShiroDbRealm.ShiroUser;
import com.agitation.sport.service.task.CatalogService;

/**
 * Catalog的Restful API的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/api/v1/catalog")
public class CatalogRestController {

	@Autowired
	private CatalogService catalogService;

	@Autowired
	private Validator validator;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Catalog> list() {
		return catalogService.getAllCatalog();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") Long id) {
		Catalog catalog = catalogService.getCatalog(id);
		if (catalog == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(catalog, HttpStatus.OK);
	}
	@RequestMapping(value = "getChildCatalog/{parentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getChildCatalog(@PathVariable("parentId") Long parentId) {
		List<Catalog> catalogs = catalogService.getChildCatalog(parentId);
		if (catalogs == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(catalogs, HttpStatus.OK);
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
	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}

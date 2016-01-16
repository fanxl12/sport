package com.agitation.sport.web.task;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agitation.sport.entity.CourseType;
import com.agitation.sport.service.task.CourseTypeService;
import com.agitation.sport.util.FileUtil;
import com.google.common.collect.Maps;

/**
 * CourseType管理的Controller, 使用Restful风格的Urls:
 * 
 * List page     : GET /CourseType/
 * Create page   : GET /CourseType/create
 * Create action : POST /CourseType/create
 * Update page   : GET /CourseType/update/{id}
 * Update action : POST /CourseType/update
 * Delete action : GET /CourseType/delete/{id}
 * 
 * @author fanxl
 */
@Controller
@RequestMapping(value = "/courseType")
public class CourseTypeController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("id", "ID");
		sortTypes.put("code", "编号");
		sortTypes.put("name", "名称");
	}

	@Autowired
	private CourseTypeService courseTypeService;
	
	@Autowired
	private FileUtil fileUtil;
	

	@RequestMapping(value = "")
	public String list(@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request) {
		model.addAttribute("action", "create");
		model.addAttribute("courseTypes", courseTypeService.getAllCourseTypes());
		return "courseType/courseTypeList";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("courseType", new CourseType());
		model.addAttribute("action", "create");
		return "courseType/courseTypeForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid CourseType newCourseType, RedirectAttributes redirectAttributes) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		courseTypeService.saveCourseType(newCourseType);
		redirectAttributes.addFlashAttribute("message", "创建分类成功");
		return "redirect:/courseType/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,ServletRequest request) {
		
		model.addAttribute("courseType", courseTypeService.getCourseType(id));
		model.addAttribute("action", "update");
		return "courseType/courseType";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid CourseType courseType, RedirectAttributes redirectAttributes) {
		courseTypeService.updateCourseType(courseType);
		redirectAttributes.addFlashAttribute("message", "更新类别成功");
		return "redirect:/courseType/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		
		CourseType courseType = courseTypeService.getCourseType(id);
		
		courseTypeService.deleteCourseType(id);
		redirectAttributes.addFlashAttribute("message", "删除类别成功");
		return "redirect:/courseType/";
	}

}

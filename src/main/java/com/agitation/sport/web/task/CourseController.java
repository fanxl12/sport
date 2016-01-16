package com.agitation.sport.web.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agitation.sport.entity.Course;
import com.agitation.sport.entity.MyPage;
import com.agitation.sport.service.task.AreaService;
import com.agitation.sport.service.task.CatalogService;
import com.agitation.sport.service.task.CoachService;
import com.agitation.sport.service.task.CompanyService;
import com.agitation.sport.service.task.CourseService;
import com.agitation.sport.service.task.CourseTypeService;
import com.agitation.sport.util.FileUtil;
import com.google.common.collect.Maps;

/**
 * Course管理的Controller, 使用Restful风格的Urls:
 * 
 * List page     : GET /Course/
 * Create page   : GET /Course/create
 * Create action : POST /Course/create
 * Update page   : GET /Course/update/{id}
 * Update action : POST /Course/update
 * Delete action : GET /Course/delete/{id}
 * 
 * @author fanxl
 */
@Controller
@RequestMapping(value = "/course")
public class CourseController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("id", "ID");
		sortTypes.put("code", "编号");
		sortTypes.put("name", "名称");
	}

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private FileUtil fileUtil;
	
	@Autowired
	private CoachService coachService;
	
	@Autowired
	private CourseTypeService courseTypeService;
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private CompanyService companyService;
	
	@InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		// 不要删除下行注释!!! 将来"yyyy-MM-dd"将配置到properties文件中
		// SimpleDateFormat dateFormat = new SimpleDateFormat(getText("date.format", request.getLocale()));
		System.out.println(request.getParameter("startTime"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
//		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "")
	public String list(@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("classType", 0);
		
		MyPage<Map<String, Object>> page = new MyPage<Map<String, Object>>();  
	    page.setNumber(pageNumber); 
	    page.setParams(param);
		
	    model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("pageData", page);
		model.addAttribute("action", "create");
		model.addAttribute("courses", courseService.getAllCourses(page));
		return "course/courseList";
	}
	
	@RequestMapping(value = "open")
	public String openList(@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("classType", 1);
		
		MyPage<Map<String, Object>> page = new MyPage<Map<String, Object>>();  
	    page.setNumber(pageNumber); 
	    page.setParams(param);
		
		model.addAttribute("action", "create");
		model.addAttribute("courses", courseService.getAllCourses(page));
		return "course/openCourseList";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("course", new Course());
		model.addAttribute("allCoachs", coachService.getAllCoachs());
		model.addAttribute("allCourseTypes", courseTypeService.getAllCourseTypes());
		model.addAttribute("parentCatalogs", catalogService.getParentList());
		model.addAttribute("areas", areaService.getAllAreas());
		model.addAttribute("companys", companyService.getAllCompanys());
		model.addAttribute("action", "create");
		return "course/courseForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Course newCourse, RedirectAttributes redirectAttributes) {
		newCourse.setClassType(0);
		courseService.saveCourse(newCourse);
		redirectAttributes.addFlashAttribute("message", "创建课程成功");
		return "redirect:/course";
	}
	
	@RequestMapping(value = "createOpen", method = RequestMethod.GET)
	public String createOpenForm(Model model) {
		model.addAttribute("course", new Course());
		model.addAttribute("allCoachs", coachService.getAllCoachs());
		model.addAttribute("allCourseTypes", courseTypeService.getAllCourseTypes());
		model.addAttribute("areas", areaService.getAllAreas());
		model.addAttribute("companys", companyService.getAllCompanys());
		model.addAttribute("action", "createOpen");
		return "course/openCourseForm";
	}
	
	@RequestMapping(value = "createOpen", method = RequestMethod.POST)
	public String createOpen(@Valid Course newCourse, RedirectAttributes redirectAttributes) {
		newCourse.setClassType(1);
		courseService.saveCourse(newCourse);
		redirectAttributes.addFlashAttribute("message", "创建公开课成功");
		return "redirect:/course/open";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model, ServletRequest request) {
		model.addAttribute("allCoachs", coachService.getAllCoachs());
		model.addAttribute("allCourseTypes", courseTypeService.getAllCourseTypes());
		model.addAttribute("parentCatalogs", catalogService.getParentList());
		model.addAttribute("areas", areaService.getAllAreas());
		model.addAttribute("companys", companyService.getAllCompanys());
		model.addAttribute("course", courseService.getCourse(id));
		model.addAttribute("action", "update");
		return "course/courseForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid Course course, RedirectAttributes redirectAttributes) {
		courseService.updateCourse(course);
		redirectAttributes.addFlashAttribute("message", "更新课程成功");
		return "redirect:/course/";
	}
	
	@RequestMapping(value = "openDelete/{id}")
	public String openDelete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		
		courseService.deleteCourse(id);
		redirectAttributes.addFlashAttribute("message", "删除公开课成功");
		return "redirect:/course/open";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		
		courseService.deleteCourse(id);
		redirectAttributes.addFlashAttribute("message", "删除课程成功");
		return "redirect:/course/";
	}

}

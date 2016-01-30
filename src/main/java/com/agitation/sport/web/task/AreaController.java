package com.agitation.sport.web.task;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agitation.sport.entity.Area;
import com.agitation.sport.service.task.AreaService;
import com.agitation.sport.util.FileUtil;
import com.google.common.collect.Maps;

/**
 * Area管理的Controller, 使用Restful风格的Urls:
 * 
 * List page     : GET /Area/
 * Create page   : GET /Area/create
 * Create action : POST /Area/create
 * Update page   : GET /Area/update/{id}
 * Update action : POST /Area/update
 * Delete action : GET /Area/delete/{id}
 * 
 * @author fanxl
 */
@Controller
@RequestMapping(value = "/area")
public class AreaController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("id", "ID");
		sortTypes.put("code", "编号");
		sortTypes.put("name", "名称");
	}
	
	private static Logger logger = LoggerFactory.getLogger(AreaController.class);

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private FileUtil fileUtil;
	

	@RequestMapping(value = "")
	public String list(@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request) {
		model.addAttribute("action", "create");
		model.addAttribute("areas", areaService.getAllAreas());
		return "area/areaList";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("area", new Area());
		model.addAttribute("action", "create");
		return "area/areaForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Area newArea, RedirectAttributes redirectAttributes) {
		areaService.saveArea(newArea);
		redirectAttributes.addFlashAttribute("message", "创建地区成功");
		return "redirect:/area";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,ServletRequest request) {
		
		model.addAttribute("area", areaService.getArea(id));
		model.addAttribute("action", "update");
		return "area/areaForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid Area area, RedirectAttributes redirectAttributes) {
		areaService.updateArea(area);
		redirectAttributes.addFlashAttribute("message", "更新地区成功");
		return "redirect:/area/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		areaService.deleteArea(id);
		redirectAttributes.addFlashAttribute("message", "删除地区成功");
		return "redirect:/area/";
	}
	
	
	
	
}

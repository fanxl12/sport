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

import com.agitation.sport.entity.Company;
import com.agitation.sport.service.task.AreaService;
import com.agitation.sport.service.task.CompanyService;
import com.google.common.collect.Maps;

/**
 * Company管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /Company/ Create page : GET /Company/create Create action :
 * POST /Company/create Update page : GET /Company/update/{id} Update action :
 * POST /Company/update Delete action : GET /Company/delete/{id}
 * 
 * @author fanxl
 */
@Controller
@RequestMapping(value = "/company")
public class CompanyController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("id", "ID");
		sortTypes.put("code", "编号");
		sortTypes.put("name", "名称");
	}

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "")
	public String list(
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			Model model, ServletRequest request) {
		model.addAttribute("action", "create");
		model.addAttribute("companys", companyService.getAllCompanys());
		return "company/companyList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("company", new Company());
		model.addAttribute("action", "create");
		model.addAttribute("areas", areaService.getAllAreas());
		return "company/companyForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Company newCompany,
			RedirectAttributes redirectAttributes) {
		companyService.saveCompany(newCompany);
		redirectAttributes.addFlashAttribute("message", "创建场馆成功");
		return "redirect:/company";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("company", companyService.getCompany(id));
		model.addAttribute("areas", areaService.getAllAreas());
		model.addAttribute("action", "update");
		return "company/companyForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid Company company,
			RedirectAttributes redirectAttributes) {
		companyService.updateCompany(company);
		redirectAttributes.addFlashAttribute("message", "更新场馆成功");
		return "redirect:/company/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		companyService.deleteCompany(id);
		redirectAttributes.addFlashAttribute("message", "删除场馆成功");
		return "redirect:/company/";
	}

}

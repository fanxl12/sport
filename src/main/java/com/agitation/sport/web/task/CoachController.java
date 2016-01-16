package com.agitation.sport.web.task;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agitation.sport.entity.Coach;
import com.agitation.sport.service.task.CoachService;
import com.agitation.sport.util.FileUtil;
import com.google.common.collect.Maps;

/**
 * Coach管理的Controller, 使用Restful风格的Urls:
 * 
 * List page     : GET /Coach/
 * Create page   : GET /Coach/create
 * Create action : POST /Coach/create
 * Update page   : GET /Coach/update/{id}
 * Update action : POST /Coach/update
 * Delete action : GET /Coach/delete/{id}
 * 
 * @author fanxl
 */
@Controller
@RequestMapping(value = "/coach")
public class CoachController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("id", "ID");
		sortTypes.put("code", "编号");
		sortTypes.put("name", "名称");
	}

	@Autowired
	private CoachService coachService;
	
	@Autowired
	private FileUtil fileUtil;
	

	@RequestMapping(value = "")
	public String list(@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request) {
		model.addAttribute("action", "create");
		model.addAttribute("coachs", coachService.getAllCoachs());
		return "coach/coachList";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("coach", new Coach());
		model.addAttribute("action", "create");
		return "coach/coachForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Coach newCoach, @RequestParam("file") CommonsMultipartFile mediaFile,
			RedirectAttributes redirectAttributes) {
		try {
			createFile(mediaFile, newCoach);
		} catch (Exception e) {
			e.printStackTrace();
		}
		coachService.saveCoach(newCoach);
		redirectAttributes.addFlashAttribute("message", "创建教练成功");
		return "redirect:/coach";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("coach", coachService.getCoach(id));
		model.addAttribute("action", "update");
		return "coach/coachForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid Coach coach, RedirectAttributes redirectAttributes) {
		coachService.updateCoach(coach);
		redirectAttributes.addFlashAttribute("message", "更新教练成功");
		return "redirect:/coach/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		coachService.deleteCoach(id);
		redirectAttributes.addFlashAttribute("message", "删除教练成功");
		return "redirect:/coach/";
	}
	
	//导入文件方法------------------------------------------------
	//创建文件
	private void createFile(CommonsMultipartFile mediaFile, Coach coach) throws Exception {
		if (!mediaFile.isEmpty()) {
			String fileType = getFileType(mediaFile.getOriginalFilename());
			String shortPath = "/static/upload/courseBg/";

			String fileName = new Date().getTime() + fileType;
			String path = fileUtil.createMultiFolders(shortPath) + fileName; // 获取本地存储路径
			
			coach.setUrl(shortPath+fileName);

			File file = new File(path); // 新建一个文件
			mediaFile.getFileItem().write(file); // 将上传的文件写入新建的文件中
		}
	}
	
	private String getFileType(String originalFilename) {
		if (StringUtils.isNotEmpty(originalFilename)) {
			int dotPosition = originalFilename.lastIndexOf(".");
			return (dotPosition == -1) ? null : originalFilename.substring(dotPosition).toLowerCase();
		}
		return null;
	}
}

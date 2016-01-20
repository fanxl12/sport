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

import com.agitation.sport.entity.Advertisement;
import com.agitation.sport.service.task.AdvertisementService;
import com.agitation.sport.util.FileUtil;
import com.google.common.collect.Maps;

/**
 * Advertisement管理的Controller, 使用Restful风格的Urls:
 * 
 * List page     : GET /Advertisement/
 * Create page   : GET /Advertisement/create
 * Create action : POST /Advertisement/create
 * Update page   : GET /Advertisement/update/{id}
 * Update action : POST /Advertisement/update
 * Delete action : GET /Advertisement/delete/{id}
 * 
 * @author fanxl
 */
@Controller
@RequestMapping(value = "/advertisement")
public class AdvertisementController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("id", "ID");
		sortTypes.put("code", "编号");
		sortTypes.put("name", "名称");
	}

	@Autowired
	private AdvertisementService advertisementService;
	
	@Autowired
	private FileUtil fileUtil;
	

	@RequestMapping(value = "")
	public String list(@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request) {
		model.addAttribute("action", "create");
		model.addAttribute("advertisements", advertisementService.getAllAdvertisements());
		return "advertisement/advertisementList";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("advertisement", new Advertisement());
		model.addAttribute("action", "create");
		return "advertisement/advertisementForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Advertisement newAdvertisement, @RequestParam("file") CommonsMultipartFile mediaFile,
			RedirectAttributes redirectAttributes) {
		try {
			createFile(mediaFile, newAdvertisement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		advertisementService.saveAdvertisement(newAdvertisement);
		redirectAttributes.addFlashAttribute("message", "创建广告成功");
		return "redirect:/advertisement/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,ServletRequest request) {
		
		model.addAttribute("advertisement", advertisementService.getAdvertisement(id));
		model.addAttribute("action", "update");
		return "advertisement/advertisementForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid Advertisement advertisement, RedirectAttributes redirectAttributes) {
		advertisementService.updateAdvertisement(advertisement);
		redirectAttributes.addFlashAttribute("message", "更新广告成功");
		return "redirect:/advertisement/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		
		Advertisement advertisement = advertisementService.getAdvertisement(id);
		if(advertisement!=null){
			String url = advertisement.getUrl();
			String path = fileUtil.getRealPath()+url;
			File file = new File(path);
			if(file.exists()){
				file.delete();
			}
		}
		
		advertisementService.deleteAdvertisement(id);
		redirectAttributes.addFlashAttribute("message", "删除广告成功");
		return "redirect:/advertisement/";
	}
	
	//导入文件方法------------------------------------------------
	//创建文件
	private void createFile(CommonsMultipartFile mediaFile, Advertisement advertisement) throws Exception {
		if (!mediaFile.isEmpty()) {
			String fileType = getFileType(mediaFile.getOriginalFilename());
			String shortPath = "/static/upload/courseBg/";

			String fileName = new Date().getTime() + fileType;
			String path = fileUtil.createMultiFolders(shortPath) + fileName; // 获取本地存储路径
			
			advertisement.setUrl(shortPath+fileName);

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

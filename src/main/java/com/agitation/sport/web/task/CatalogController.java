package com.agitation.sport.web.task;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agitation.sport.entity.Catalog;
import com.agitation.sport.service.account.ShiroDbRealm.ShiroUser;
import com.agitation.sport.service.task.CatalogService;
import com.agitation.sport.util.FileUtil;
import com.google.common.collect.Maps;

/**
 * Catalog管理的Controller, 使用Restful风格的Urls:
 * 
 * List page     : GET /catalog/
 * Create page   : GET /catalog/create
 * Create action : POST /catalog/create
 * Update page   : GET /catalog/update/{id}
 * Update action : POST /catalog/update
 * Delete action : GET /catalog/delete/{id}
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/catalog")
public class CatalogController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("id", "ID");
		sortTypes.put("code", "编号");
		sortTypes.put("name", "名称");
	}

	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private FileUtil fileUtil;
	

	@RequestMapping(value = "")
	public String list(@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request) {
		initList(model);
		model.addAttribute("action", "create");
		model.addAttribute("catalogs", catalogService.getAllCatalog());
		return "catalog/catalogList";
	}
	
	public void initList( Model model) {
		model.addAttribute("allParents", catalogService.getParentList());

	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("catalog", new Catalog());
		model.addAttribute("action", "create");
		initList(model);
		return "catalog/catalogForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Catalog newCatalog, @RequestParam("file") CommonsMultipartFile mediaFile,
			RedirectAttributes redirectAttributes) {
		
		try {
			createFile(mediaFile, newCatalog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		catalogService.saveCatalog(newCatalog);
		redirectAttributes.addFlashAttribute("message", "创建类别成功");
		return "redirect:/catalog/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,ServletRequest request) {
		
		initList(model);
		model.addAttribute("catalog", catalogService.getCatalog(id));
		model.addAttribute("action", "update");
		return "catalog/catalogForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid Catalog catalog, RedirectAttributes redirectAttributes) {
		catalogService.updateCatalog(catalog);
		redirectAttributes.addFlashAttribute("message", "更新类别成功");
		return "redirect:/catalog/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		
		Catalog catalog = catalogService.getCatalog(id);
		if(catalog!=null){
			String url = catalog.getUrl();
			String path = fileUtil.getRealPath()+url;
			File file = new File(path);
			if(file.exists()){
				file.delete();
			}
		}
		catalogService.deleteCatalog(id);
		redirectAttributes.addFlashAttribute("message", "删除类别成功");
		return "redirect:/catalog/";
	}
	
	@RequestMapping(value = "deleteChildCatalog/{id}")
	public String deleteChildCatalog(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		catalogService.deleteCatalog(id);
		redirectAttributes.addFlashAttribute("message", "删除子品类成功");
		return "redirect:/catalog/";
	}
	
	@RequestMapping(value = "batchDelete")
	public String batchDelete(Long[] ids ,RedirectAttributes redirectAttributes) {
		if(ids!=null && ids.length>0){
			catalogService.batchDeleteChildCatalog(ids);
			catalogService.batchDeleteCatalog(ids);
		}
		redirectAttributes.addFlashAttribute("message", "删除品类成功");
		return "redirect:/catalog/";
	}

	/**
	 * 使用@ModelAttribute, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Catalog对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此本方法在该方法中执行.
	 */
	@ModelAttribute("preloadCatalog")
	public Catalog getCatalog(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			return catalogService.getCatalog(id);
		}
		return null;
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
	
	//导入文件方法------------------------------------------------
	//创建文件
	private void createFile(CommonsMultipartFile mediaFile, Catalog catalog) throws Exception {
		if (!mediaFile.isEmpty()) {
			String fileType = getFileType(mediaFile.getOriginalFilename());
			String shortPath = "/static/upload/courseBg/";

			String fileName = new Date().getTime() + fileType;
			String path = fileUtil.createMultiFolders(shortPath) + fileName; // 获取本地存储路径
			
			catalog.setUrl(shortPath+fileName);

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

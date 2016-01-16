package com.agitation.sport.util;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.agitation.sport.service.account.ShiroDbRealm.ShiroUser;
import com.fasterxml.jackson.core.JsonGenerator;

@Component
public class FileUtil {
	
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	private String realPath;
	private static final String DATA_DIR = "/static/data/";
	private String imageProfix;

	private JsonGenerator jsonGenerator = null;
	
//	@Autowired
//	private ImportDataDao importDataDao;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(FileUtil.class.getResource("/").getPath());

	}

	// public static String mkDir(String path) {
	// StringTokenizer st = new StringTokenizer(path, "/");
	// String path1 = st.nextToken() + "/";
	// String path2 = path1;
	// while (st.hasMoreTokens()) {
	// path1 = st.nextToken() + "/";
	// path2 += path1;
	// File inbox = new File(path2);
	// if (!inbox.exists())
	// inbox.mkdir();
	// }
	// return path;
	// }

	public String createMultiFolders(String path) {
		if (path == null)
			return path;

		path = (getRealPath() + path).replace("\\", "/").replace("//", "/");
		StringTokenizer st = new StringTokenizer(path, "/");
		String path1 = st.nextToken() + "/";
		String path2 = System.getProperty("os.name").indexOf("Windows") > -1 ? path1 : "/" + path1;

		while (st.hasMoreTokens()) {
			path1 = st.nextToken() + "/";
			path2 += path1;
			File inbox = new File(path2);
			if (!inbox.exists())
				inbox.mkdir();
		}

		return path;
	}

//	public String createDataPath() {
//		return createMultiFolders(DATA_DIR);
//	}
//
//	public String getDataPath() {
//		return getRealPath(DATA_DIR);
//	}

	public static String getFileType(String originalFilename) {
		if (StringUtils.isNotEmpty(originalFilename)) {
			int dotPosition = originalFilename.lastIndexOf(".");
			return (dotPosition == -1) ? null : originalFilename.substring(dotPosition).toLowerCase();
		}
		return null;
	}

	public String getRealPath() {

		return realPath;
	}

	public String getRealPath(String fileName) {
		return (realPath + fileName).replace("//", "/");
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	
	public String getImageProfix() {
		return imageProfix;
	}

	public void setImageProfix(String imageProfix) {
		this.imageProfix = imageProfix;
	}

//	public String createFile(String entity, Object data) {
//		String filePath = getDataPath() + "/" + entity + ".json";
//
//		File file = new File(filePath);
//		if (file.exists())
//			file.delete();
//		file = new File(filePath);
//		try {
//			// 生成JSON文件
//			jsonGenerator = new ObjectMapper().getJsonFactory().createJsonGenerator(file, JsonEncoding.UTF8);
//			jsonGenerator.writeObject(data);
//
//			return filePath;
//		} catch (IOException e) {
//			logger.error("生成JSON时出错", e);
//			return null;
//		}
//	}

	// public JsonGenerator getJsonGenerator() {
	// try {
	// if (jsonGenerator == null)
	// jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return jsonGenerator;
	// }

	public void setJsonGenerator(JsonGenerator jsonGenerator) {
		this.jsonGenerator = jsonGenerator;
	}

	// public ObjectMapper getObjectMapper() {
	// if (om == null)
	// om = new ObjectMapper();
	//
	// return om;
	// }
	//
	// public void setObjectMapper(ObjectMapper objectMapper) {
	//
	// this.om = objectMapper;
	// }
	
	
	//创建文件
	public void createFile(HttpServletRequest request, CommonsMultipartFile mediaFile) throws Exception {
		if (!mediaFile.isEmpty()) {
			String fileType = getFileType(mediaFile.getOriginalFilename());
			String shortPath = "/static/upload/";

			String fileName = new Date().getTime() + fileType;
//			String path = request.getSession().getServletContext().getRealPath("/") + shortPath + fileName; // 获取本地存储路径
			String path = this.createMultiFolders(shortPath) + fileName;

			File file = new File(path); // 新建一个文件
//			mediaFile.getFileItem().write(file); // 将上传的文件写入新建的文件中
//			saveImportData(file, request.getRemoteAddr());
			// 可能在读EXCEL的时候关掉了文件，所以这里要重新打开
			file = new File(path);
			if (file.exists())
				file.delete();
		}
	}
	
//	@Transactional(readOnly = false)
//	private void saveImportData(File file, String ip) throws IOException {
//		List<List<List<Object>>> list = ReadExcel.readExcel(file);
//		if (list == null) {
//			throw new ServiceException("数据表格式不正确，读取数据为空");
//		}
//		insertImportData(list.get(0));
//	}
	
//	@SuppressWarnings("unchecked")
//	private void insertImportData(List<List<Object>> list) throws IOException{
//		String tableName = list.get(0).get(0).toString();  //得到表名称
//		
//		List<Object> tableKey = list.get(1); 
//		
//		Map<Integer, Object> result = new HashMap<Integer, Object>();
//		List<String>  fieldList = new ArrayList<String>();
//		fieldList.add("skuId");
//		for (int j = 0; j < tableKey.size(); j++) {
//			String key = tableKey.get(j).toString();
//			
//			if(key.toString().contains(".")){  //说明是需要查的字段
//				getBaseInfoList(result, fieldList, j, key, true);
//			}else{
//				fieldList.add(key);
//			}
//		}
//		
//		for (int i = 0; i < 3; i++) {
//			list.remove(0);
//		}
//		
//		StringBuilder sb=new StringBuilder();
//		for (int col = 0; col < tableKey.size(); col++) {  //循环列
//			String keyWord = tableKey.get(col).toString();
//			if(keyWord.toString().contains("code") || keyWord.toString().contains("color")){
//				//
//			}
//			
//			
//			
//			if(!keyWord.toString().contains(".")) continue;
//			
//			String[] keys = keyWord.toString().split("[.]");
//			boolean insertIfNotExisted=false;
//			for(int n=2;n<keys.length;n++){
//				if("insert".equalsIgnoreCase(keys[n])){
//					insertIfNotExisted=true;
//					break;
//				}
//			}
//			Map<String, Object> newBaseInfo=null;
//			Set<Integer> newBaseIndexs=new HashSet<Integer>();
//			Map<String, Object> source = (Map<String, Object>) result.get(col);
//			for (int row = 0; row < list.size(); row++) {  //循环行
//				List<Object> tableValues = list.get(row); //得到要存入的值，然后根据这个值去查相应的Id
//				Long id = getIdByField(source, tableValues.get(col)+"");
//				if(id != null){
//						tableValues.set(col, id);
//				}else{
//					if(insertIfNotExisted){
//						if(newBaseInfo==null){
//							newBaseInfo=new HashMap<String, Object>();
//							Set<String> baseInfoList=new HashSet<String>();
//							newBaseInfo.put("tableName", keys[0]);
//							newBaseInfo.put("userId", getCurrentUserId());
//							newBaseInfo.put("valueList", baseInfoList);
//							newBaseInfo.put("field", keys[1]);
//						}
//						Set<String> baseInfoList= (Set<String>) newBaseInfo.get("valueList");
//						baseInfoList.add(tableValues.get(col)+"");
//						newBaseIndexs.add(row);
//					}else{
//						sb.append("第").append(row+4).append("行")
//						.append(" 第").append((char)(((int)'A')+col)).append("列")
//						.append("值:").append(tableValues.get(col)).append("在表:")
//						.append(keys[0]).append("中不存在!<br/>");
//					}
//				}
//			}
//			
//			if(newBaseInfo!=null){
//				importDataDao.insertNewField(newBaseInfo);
//				//重新加载保存后的基础数据
//				getBaseInfoList(result, fieldList, col, keyWord, false);
//				for(Integer row :newBaseIndexs){
//					List<Object> tableValues = list.get(row);
//					Long id = getIdByField((Map<String, Object>) result.get(col), tableValues.get(col)+"");
//					if(id != null){
//						tableValues.set(col,id);
//					}else{
//						sb.append(" 第").append((char)(((int)'A')+row)).append("列")
//						.append("值:").append(tableValues.get(col)).append("在表:")
//						.append(keys[0]).append("中不存在!<br/>");
//					}
//				}
//			}
//		 }
//		
//		//如果导入出错，就抛出异常信息
//		if(sb.length()>0){
//			throw new ServiceException(sb.toString());
//		}
//		
//		insertSheet(tableName, fieldList, list); //插入数据
//	}
	
//	private void insertSheet(String tableName, List<String> fieldList, List<List<Object>> sheetData) {
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("userId", getCurrentUserId());
//		param.put("tableName", tableName);
//		param.put("fieldList", fieldList);
//		param.put("valueList", sheetData);
//		importDataDao.insertBatch(param);
//	}
//	
//	private void getBaseInfoList(Map<Integer, Object> result,
//			List<String> fieldList, int j, String key, boolean isAdd) {
//		String[] keys = key.toString().split("[.]");
//		Map<String, Object> search = new HashMap<String, Object>();
//		search.put("tableName", keys[0]);
//		search.put("field", keys[1]);
//		result.put(j, importDataDao.getAllByField(search));
//		if(isAdd)fieldList.add(keys[0].substring(keys[0].indexOf('_')+1)+"Id");
//	}
	
	private Long getIdByField(Map<String, Object> source, String value){
		@SuppressWarnings("unchecked")
		Map<String, Object> baseInfo = (Map<String, Object>) source.get(value);
		return baseInfo!=null?(Long)baseInfo.get("id"):null;
	}
	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
	
}

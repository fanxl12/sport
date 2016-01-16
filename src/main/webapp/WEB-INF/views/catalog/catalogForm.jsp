<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springside.org.cn/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<form id="inputForm" action="${ctx}/catalog/${action}" method="post" class="form-horizontal" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${catalog.id}"/>
		<fieldset>
			<legend><small>管理课程类别</small></legend>
			<div class="control-group">
				<label for="catalog_parentCatalog" class="control-label">上级分类:</label>
				<div class="controls">
					<form:bsradiobuttons path="catalog.parentCatalog.id" items="${allParents}" itemLabel="name" itemValue="id" labelCssClass="inline"/>
				</div>
			</div>
			<div class="control-group">
				<label for="catalog_name" class="control-label">名称:</label>
				<div class="controls">
					<input type="text" id="catalog_name" name="name"  value="${catalog.name}" class="input-large required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="file" class="control-label">背景图:</label>
				<div class="controls">
					 <input type="file" id="ad_file" name="file"  class="input-large required" />
				</div>
			</div>	
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#catalog_code").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>


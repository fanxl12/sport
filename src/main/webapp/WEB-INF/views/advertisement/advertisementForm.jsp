<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springside.org.cn/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<form id="inputForm" action="${ctx}/advertisement/${action}" method="post" class="form-horizontal" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${advertisement.id}"/>
		<fieldset>
			<legend><small>广告管理</small></legend>
			<div class="control-group">
				<label for="advertisement_name" class="control-label">名称:</label>
				<div class="controls">
					<input type="text" id="advertisement_name" name="name"  value="${advertisement.name}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label for="advertisement_website" class="control-label">网址:</label>
				<div class="controls">
					<input type="text" id="advertisement_website" name="website"  value="${advertisement.website}" class="input-large required"/>
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
			$("#advertisement_code").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>


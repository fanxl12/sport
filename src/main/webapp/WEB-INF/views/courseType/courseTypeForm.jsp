<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springside.org.cn/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<form id="inputForm" action="${ctx}/courseType/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${courseType.id}"/>
		<fieldset>
			<legend><small>分类管理</small></legend>
			<div class="control-group">
				<label for="courseType_name" class="control-label">名称:</label>
				<div class="controls">
					<input type="text" id="courseType_name" name="name"  value="${courseType.name}" class="input-large"/>
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
			$("#courseType_code").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>


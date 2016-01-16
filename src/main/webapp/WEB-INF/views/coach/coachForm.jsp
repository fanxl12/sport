<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springside.org.cn/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<form id="inputForm" action="${ctx}/coach/${action}" method="post" class="form-horizontal" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${coach.id}"/>
		<input type="hidden" name="url" value="${coach.url}"/>
		<fieldset>
			<legend><small>教练管理</small></legend>
			<div class="control-group">
				<label for="coach_name" class="control-label">名称:</label>
				<div class="controls">
					<input type="text" id="coach_name" name="name"  value="${coach.name}" class="input-large required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="coach_coachTime" class="control-label">执教:</label>
				<div class="controls">
					<input type="text" id="coach_coachTime" name="coachTime"  value="${coach.coachTime}" class="input-large required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="coach_honor" class="control-label">荣誉:</label>
				<div class="controls">
					<input type="text" id="coach_honor" name="honor" value="${coach.honor}" class="input-large required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="file" class="control-label">头像:</label>
				<div class="controls">
					 <input type="file" id="ad_file" name="file"  class="input-large" />
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
			$("#coach_code").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>


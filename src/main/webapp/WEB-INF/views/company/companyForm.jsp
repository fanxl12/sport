<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springside.org.cn/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<form id="inputForm" action="${ctx}/company/${action}" method="post" class="form-horizontal" >
		<input type="hidden" name="id" value="${company.id}"/>
		<fieldset>
			<legend><small>场馆管理</small></legend>
			<div class="control-group">
				<label for="company_name" class="control-label">场馆名称:</label>
				<div class="controls">
					<input type="text" id="company_name" name="name"  value="${company.name}" class="input-large required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="company_address" class="control-label">场馆地址:</label>
				<div class="controls">
					<input type="text" id="company_address" name="address"  value="${company.address}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label for="company_longitude" class="control-label">位置经度:</label>
				<div class="controls">
					<input type="text" id="company_longitude" name="longitude"  value="${company.longitude}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label for="company_latitude" class="control-label">位置纬度:</label>
				<div class="controls">
					<input type="text" id="company_latitude" name="latitude"  value="${company.latitude}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label for="course_area" class="control-label">地区:</label>
				<div class="controls">
					<select size="1" name="areaId" >
					  <c:forEach var="area" items="${areas}">
						   <option value="${area.id}" 
						   <c:if test="${area.id==company.areaId}">selected</c:if>>
						   <c:out value="${area.name}"/>
						   </option>
					   </c:forEach>
					</select>
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
			$("#company_name").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>


<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springside.org.cn/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
<link href="${ctx}/sport/static/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="${ctx}/sport/static/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script> 
<script type="text/javascript" src="${ctx}/sport/static/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript">
$(document).ready(function() {
	//聚焦第一个输入框
	$("#course_name").focus();
	//为inputForm注册validate函数
	$("#inputForm").validate();
	
	$('#startTime').datetimepicker({
		language:  'zh-CN',
		weekStart: 1,
		forceParse: 0,
		autoclose: 1,
		startView: 2,
		format: 'yyyy-mm-dd hh:ii'
	});
	
	$('#stopTime').datetimepicker({
		language:  'zh-CN',
		weekStart: 1,
		forceParse: 0,
		autoclose: 1,
		startView: 2,
		format: 'yyyy-mm-dd hh:ii'
	});
	
	$('#endTime').datetimepicker({
		language:  'zh-CN',
		weekStart: 1,
		forceParse: 0,
		autoclose: 1,
		startView: 2,
		format: 'yyyy-mm-dd hh:ii'
	});
})

function getChildCatalogs(parentId){
		if(parentId == "商品大类") return;
		$.ajax({ 
	           type: "get", 
               url: "${ctx}/sport/api/v1/catalog/getChildCatalog/" + parentId, 
               dataType: "json", 
               success: function (data) { 
            	   addOption(data);
               }, 
               error: function (XMLHttpRequest, textStatus, errorThrown) { 
                       alert(errorThrown); 
               } 
       });
}

function addOption(data){
	var childCatalogSelect = document.getElementById("childCatalogId");
	childCatalogSelect.length=0;
	var optionElement=document.createElement("option");
	optionElement.setAttribute("value","");
	var optionTextElement=document.createTextNode("课程小类");
	optionElement.appendChild(optionTextElement);
	
	childCatalogSelect.appendChild(optionElement);
	for(var i=0; i<data.length;i++){
		var optionElement=document.createElement("option");
		optionElement.setAttribute("value",data[i].id);
		var optionTextElement=document.createTextNode(data[i].name);
		optionElement.appendChild(optionTextElement);
		
		childCatalogSelect.appendChild(optionElement);
	}
} 

</script>
</head>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<form id="inputForm" action="${ctx}/course/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${course.id}"/>
		<fieldset>
			<legend><small>课程管理</small></legend>
			<div class="control-group">
				<label for="course_name" class="control-label">名称:</label>
				<div class="controls">
					<input type="text" id="course_name" name="name"  value="${course.name}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label for="course_price" class="control-label">课程价格:</label>
				<div class="controls">
					<input type="text" id="course_price" name="price"  value="${course.price}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label for="course_orginalPrice" class="control-label">课程原价:</label>
				<div class="controls">
					<input type="text" id="course_orginalPrice" name="orginalPrice"  value="${course.orginalPrice}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label for="course_startTime" class="control-label">课程上课时间:</label>
				<div class="controls">
					<input type="text" class="input-large" name="startTime" value="<fmt:formatDate value="${course.startTime}"  pattern="yyyy-mm-dd hh:mm"/>"  id="startTime">
				</div>
			</div>
			<div class="control-group">
				<label for="course_stopTime" class="control-label">课程下课时间:</label>
				<div class="controls">
					<input type="text" class="input-large" name="stopTime"  value="<fmt:formatDate value="${course.stopTime}" pattern="yyyy-mm-dd hh:mm"/>" id="stopTime">
				</div>
			</div>
			<div class="control-group">
				<label for="course_endTime" class="control-label">课程报名截止时间:</label>
				<div class="controls">
					<input type="text" class="input-large" name="endTime" value="<fmt:formatDate value="${course.endTime}"  pattern="yyyy-mm-dd hh:mm"/>" id="endTime">
				</div>
			</div>
			<div class="control-group">
				<label for="course_totalNumber" class="control-label">课程数量:</label>
				<div class="controls">
					<input type="text" id="course_totalNumber" name="totalNumber"  value="${course.totalNumber}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label for="course_introduce" class="control-label">课程介绍:</label>
				<div class="controls">
					<textarea id="course_introduce" name="introduce" class="required" rows="6">${course.introduce}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label for="course_notice" class="control-label">购买须知:</label>
				<div class="controls">
					<textarea id="course_notice" name="notice" class="required" rows="4">${course.notice}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label for="course_courseType" class="control-label">课程类别:</label>
				<div class="controls">
					<form:bsradiobuttons path="course.courseTypeId" items="${allCourseTypes}" itemLabel="name" itemValue="id" labelCssClass="inline"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="course_coach" class="control-label">教练:</label>
				<div class="controls">
					<form:bsradiobuttons path="course.coach.id" items="${allCoachs}" itemLabel="name" itemValue="id" labelCssClass="inline"/>
				</div>
			</div>
			<div class="control-group">
				<label for="product_parentCatalog" class="control-label">大类:</label>
				<div class="controls">
					<select size="1" name="parentCatalog.id" onchange="getChildCatalogs(this.value)">
					<option value="">课程大类</option>
					  <c:forEach var="parentCatalog" items="${parentCatalogs}">
						   <option value="${parentCatalog.id}" 
						   <c:if test="${parentCatalog.id==course.parentCatalog.id}">selected</c:if>>
						   <c:out value="${parentCatalog.name}"/>
						   </option>
					   </c:forEach>
					</select>
					小类:
					<select id="childCatalogId" size="1" name="childCatalog.id">
					   <option value="">课程小类</option>
				  		<c:forEach var="childCatalog" items="${childCatalogs}">
					   		<option value="${childCatalog.id}" 
					   		<c:if test="${childCatalog.id==course.childCatalog.id}">selected</c:if>
					   		><c:out value="${childCatalog.name}"/></option>
				   		</c:forEach>
					</select> 
				</div>
			</div>
			<div class="control-group">
				<label for="course_area" class="control-label">地区:</label>
				<div class="controls">
					<select size="1" name="areaId" >
					  <c:forEach var="area" items="${areas}">
						   <option value="${area.id}" 
						   <c:if test="${area.id==course.areaId}">selected</c:if>>
						   <c:out value="${area.name}"/>
						   </option>
					   </c:forEach>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label for="course_company" class="control-label">场馆:</label>
				<div class="controls">
					<select size="1" name="companyId" >
					  <c:forEach var="company" items="${companys}">
						   <option value="${company.id}" 
						   <c:if test="${company.id==course.companyId}">selected</c:if>>
						   <c:out value="${company.name}"/>
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
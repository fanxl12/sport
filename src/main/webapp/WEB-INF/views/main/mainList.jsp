<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>High运动首页</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>

	<div>
		<a class="btn" href="${ctx}/course">查看课程</a>
		<a class="btn" href="${ctx}/course/open">查看公开课</a>
		<a class="btn" href="${ctx}/company">查看场馆 </a>
		<a class="btn" href="${ctx}/catalog">查看课程分类</a>
		<a class="btn" href="${ctx}/advertisement">查看广告</a>
		<a class="btn" href="${ctx}/coach">查看教练 </a>
		<a class="btn" href="${ctx}/area">查看地区 </a>
	</div>
	
</body>
</html>

<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="header">
	<div id="title">
	    <h1><a href="${ctx}">High运动后台管理系统</a>
	    <shiro:user>
			<div class="btn-group pull-right">
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
					<i class="icon-user"></i> <shiro:principal property="name"/>
					<span class="caret"></span>
				</a>
			
				<ul class="dropdown-menu">
					<shiro:hasRole name="admin">
						<li><a href="${ctx}/main">首页</a></li>
						<li><a href="${ctx}/admin/user">用户管理</a></li>
						<li class="divider"></li>
					</shiro:hasRole>
<%-- 					<li><a href="${ctx}/api">APIs</a></li> --%>
					<li><a href="${ctx}/profile">资料编辑</a></li>
					<li><a href="${ctx}/logout">退出登录</a></li>
				</ul>
			</div>
		</shiro:user>
		</h1>
	</div>
</div>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<script type="text/javascript">
	function deleteConfirm(id){
		if(confirm("确认要删除此教练吗?")){
			window.location.href="${ctx}/coach/delete/" + id ;
		}else{
		}
	}
</script>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty message}">
	<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
</c:if>
	<div class="row-fluid">
		<div class="span8">
			<form class="form-search" action="#">
				<label>商品名称：</label> <input type="text" name="search_LIKE_name" class="input-medium" value="${param.search_LIKE_name}"> 
				<button type="submit" class="btn" id="search_btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>ID</th><th>名称</th><th>执教</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${coachs}" var="coach">
			<tr>
				<td><a href="${ctx}/coach/update/${coach.id}">${coach.id}</a></td>
				<td>${coach.name}</td>
				<td>${coach.coachTime}</td>
				<td><a href="javascript:deleteConfirm(${coach.id})">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<div>
		<a class="btn" href="${ctx}/coach/create">创建教练数据</a>
	</div>
	


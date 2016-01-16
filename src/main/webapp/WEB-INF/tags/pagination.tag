<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page"
	required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer"
	required="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	int current = page.getNumber();
	int begin = Math.max(1, current - paginationSize / 2);
	int end = Math.min(begin + (paginationSize - 1),
			page.getTotalPages());

	request.setAttribute("current", current);
	request.setAttribute("begin", begin);
	request.setAttribute("end", end);
%>


<div class="row">
	<div class="col-md-8 col-sm-12">
		<ul class="pagination">
		
			<%
			if (page.hasPreviousPage()) {
		%>
		<li><a href="?page=1&sortType=${sortType}&${searchParams}"
			aria-label="Previous"><span aria-hidden="true">&lt;&lt;</span></a></li>
		<li><a
			href="?page=${current-1}&sortType=${sortType}&${searchParams}"
			aria-label="Previous"><span aria-hidden="true">&lt;</span></a></li>
		<%
			} else {
		%>
		<li class="disabled"><a href="#">&lt;&lt;</a></li>
		<li class="disabled"><a href="#">&lt;</a></li>
		<%
			}
		%>


		<c:forEach var="i" begin="${begin}" end="${end}">
			<c:choose>
				<c:when test="${i == current}">
					<li class="active"><a
						href="?page=${i}&sortType=${sortType}&${searchParams}">${i}</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="?page=${i}&sortType=${sortType}&${searchParams}">${i}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<%
			if (page.hasNextPage()) {
		%>
		<li><a
			href="?page=${current+1}&sortType=${sortType}&${searchParams}"
			aria-label="Next"><span aria-hidden="true">&gt;</span></a></li>
		<li><a
			href="?page=${page.totalPages}&sortType=${sortType}&${searchParams}"
			aria-label="Next"><span aria-hidden="true">&gt;&gt;</span></a></li>
		<%
			} else {
		%>
		<li class="disabled"><a href="#">&gt;</a></li>
		<li class="disabled"><a href="#">&gt;&gt;</a></li>
		<%
			}
		%>
		</ul>
	</div>
	<div class="col-md-2 col-sm-4 items-info text-right">${pageData.total}个商品 共${pageData.totalPages}页</div>
</div>




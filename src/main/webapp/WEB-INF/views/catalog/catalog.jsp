<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>商品类别管理</title>
<script src="${ctx}/static/js/jquery.serialize-object.min.js"></script>
<style type="text/css">
.marginTop {
	margin-top: 4px
}

.table th, .table td {
    text-align: center;
	vertical-align: middle;
}

.form-horizontal .control-label {
	width: 80px;
}

.form-horizontal .controls {
	margin-left: 90px;
	*margin-left: 0;
}

.form-horizontal .form-actions {
	padding-left: 90px;
}

.hiddenInput{
	visibility:hidden;
}
</style>
<script type="text/javascript">
	
 function deleteConfirm(id){
		if(confirm("确认要删除子品类吗?")){
			window.location.href="${ctx}/catalog/deleteChildCatalog/" + id ;
		}
	}

 $().ready(function() {
	  $("#checkall").click( 
		  function(){ 
			  if(this.checked){ 
			 		 $("input[name='ids']").each(function(){this.checked=true;}); 
			  }else{ 
			 	 $("input[name='ids']").each(function(){this.checked=false;}); 
			  } 
		 } 
	);
	  $("#inputForm").validate({
	         submitHandler:function(form){
	        	 var formData = $("form").serializeObject();
	        	 if(formData.btnDelete=="删除"){
	        		 if($('[name="ids"]:checked').length>0){
	        			 if(confirm("确认要删除选中的品类吗?")){
	        				 form.submit();
	        			 }
	        		 }else{
	        			 alert("请勾选要删除的品类");
	        		 }
	        	 }else{
	        		 //saveCatalog(formData);
	        	 }
	         }    
	     });
	 });
 
 function saveCatalog(formData){
	 $.ajax({ 
	     type: "get", 
         url: "${ctx}/api/v1/catalog/saveCatalog/" + formData, 
         dataType: "json", 
         success: function (data) { 
         	location.reload();
         }, 
         error: function (XMLHttpRequest, textStatus, errorThrown) { 
                 alert(errorThrown); 
         } 
 	});
 }
 
 	function addChildCatalog(parentId, childCatalog){
 		$.ajax({ 
	        type: "get", 
            url: "${ctx}/api/v1/catalog/saveChildCatalog/" + parentId + "/" + childCatalog, 
            dataType: "json", 
            success: function (data) { 
            	location.reload();
            }, 
            error: function (XMLHttpRequest, textStatus, errorThrown) { 
                    alert(errorThrown); 
            } 
    	});
 	}
 	
 	function updateCatalog(id, value){
 		  $.ajax({
 			    type: "post",
 			    url: "${ctx}/api/v1/catalog/updateField", //your valid url
 			    contentType: "application/json", //this is required for spring 3 - ajax to work (at least for me)
 			    data: JSON.stringify( { id: id, field: "name", value:value }), //json object or array of json objects
 			    success: function(result) {
 			    	
 			    },
 			    error: function(){
 			        alert('failure');
 			    }
 			});
 	  }
 	
 	function updateChildCatalog(id, value){
		  $.ajax({
			    type: "post",
			    url: "${ctx}/api/v1/catalog/updateField", //your valid url
			    contentType: "application/json", //this is required for spring 3 - ajax to work (at least for me)
			    data: JSON.stringify( { id: id, field: "name", value:value }), //json object or array of json objects
			    success: function(result) {
			    	
			    },
			    error: function(){
			        alert('failure');
			    }
			});
	  }
 	
</script>
</head>

<body>
	<div class="row-fluid marginTop">
		<div class="span10">
			<c:if test="${not empty message}">
				<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
			</c:if>
			<form id="inputForm" action="${ctx}/catalog/batchDelete">
				<div class="row-fluid marginTop">
					<input name="btnDelete" class="btn btn-danger cancel" type="submit" value="删除"/>
				</div>
				
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead><tr><th><input type="checkbox" id="checkall"></th><th>ID</th><th>大类名称</th><th>子类名称</th></tr></thead>
					<tbody>
					<c:forEach items="${catalogs}" var="catalog" varStatus="index">
						<c:set var="childLength" value="${fn:length(catalog.childCatalogs)}"/>
						<tr>
							<td rowspan="${childLength+1}"><input type="checkbox" name="ids" value="${catalog.id}"></td>
							<td rowspan="${childLength+1}">${catalog.id}</td>
							<td rowspan="${childLength+1}"><input type="text" id="catalog_name" value="${catalog.name}" class="required" onchange="updateCatalog(${catalog.id}, this.value)"/></td>
							<td><input type="text" placeholder="新建  ${catalog.name}  的子类" onchange="addChildCatalog(${catalog.id}, this.value)" /></td>
						</tr>
						<c:forEach items="${catalog.childCatalogs}" var="childCatalog" varStatus="index">
							<tr><td onMouseOver="show(this)" onMouseOut="hiddenInput(this)"><input type="text" value="${childCatalog.name}" onchange="updateChildCatalog(${childCatalog.id}, this.value)"/> <a class="btn btn-danger hiddenInput" href="javascript:deleteConfirm(${childCatalog.id})">删除</a></td></tr>
						</c:forEach>
					
					
					</c:forEach>
					<tr>
						<td></td>
						<td></td>
						<td><input type="text" id="catalog_name" name="name" placeholder="输入大类" onchange="saveCatalog(this.value)"/></td>
						<td></td>
					</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>

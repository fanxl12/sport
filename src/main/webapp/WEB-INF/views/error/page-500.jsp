<%@page import="java.io.StringWriter"%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%@page import="java.io.*"%>
<%response.setStatus(200);%>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
%>
<head>
 <!-- BEGIN PAGE LEVEL JAVASCRIPTS(REQUIRED ONLY FOR CURRENT PAGE) -->
    <script type="text/javascript" src="assets/plugins/fancybox/source/jquery.fancybox.pack.js"></script>
    <script type="text/javascript" src="assets/plugins/bxslider/jquery.bxslider.min.js"></script>
    <!-- slider for products -->

    <script type="text/javascript">
      jQuery(document).ready(function() {
        
      });
    </script>
    <!-- END PAGE LEVEL JAVASCRIPTS -->
</head>

    <div class="main">
      <div class="container">
        <!-- BEGIN SIDEBAR & CONTENT -->
        <div class="row margin-bottom-40">
          <!-- BEGIN CONTENT -->
          <div class="col-md-12 col-sm-12">
            <div class="content-page page-500">
               <div class="number">
                  500
               </div>
               <div class="details">
                <h3>后台程序执行错误</h3>
                <p>
                    <input type="button" onclick="detailError.style.display='block';" value="显示详细错误"/>
                </p>                
               </div>
			   	<div class="alert alert-error" id="detailError" style="display:none">
				<h3>错误详细信息：</h3>
			            <%=ex.getClass().getName() + "(" + ex.getLocalizedMessage() + ")"%><br />
			            <%
			                StringWriter sw = new StringWriter();
			                PrintWriter pw = new PrintWriter(sw);
			                ex.printStackTrace(pw);
			                out.print(sw);
			            %>
			        </div>
            </div>
          </div>
          <!-- END CONTENT -->
        </div>
        <!-- END SIDEBAR & CONTENT -->
      </div>
    </div>

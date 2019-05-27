<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="<c:url value="/assets/css/bootstrap.min.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/assets/css/font-awesome.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/assets/css/main.css" />" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="<c:url value="/home" />"> Application - Computer Database </a>
            <div class="navbar-brand pull-right">
            	<form id="loginForm" action="/authenticate" method="POST" class="form-inline">
                	<spring:message code="login" var="userLogin"/>
                    <input type="text" name="login" class="form-control" placeholder="${userLogin}"/>
                    <spring:message code="password" var="userPwd"/>
                    <input type="password" name="password" class="form-control" placeholder="${userPwd}"/>
                    <input type="submit" value="${userLogin}"
                        class="btn btn-primary" />
            	</form>
            </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
        	<div class="row">
        		<div class="col-lg-5 col-md-12">
        		<h1><spring:message code="welcome.title" /></h1>
        		</div>
        		<div class="col-lg-7 col-md-12">
        			<c:if test='${feedback.getMessage() != null}'>
        				<p class="alert alert-${feedback.getStatus()}">${feedback.getMessage()}</p>
        			</c:if>
        		</div>
        	</div> 
        </div>
        <div class="container" style="margin-top: 10px;">
       		<form:form action="/users" method="POST" modelAttribute="createUserFormData" id="createUser">
       			<fieldset>
       				 <div class="form-group">
                     	<spring:message code="login" var="userLogin"/>
                        <form:label path="login">${userLogin}</form:label>
                        <form:input type="text" class="form-control" path="login" placeholder="${userLogin}" />
                       	<small class="text-danger"><spring:message code="login.is.empty"/></small>
                        <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                        <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                     </div>
                     <div class="form-group">
                     	<spring:message code="password" var="userPwd"/>
                        <form:label path="password">${userPwd}</form:label>
                        <form:input type="password" class="form-control" path="password" placeholder="${userPwd}" />
                       	<small class="text-danger"><spring:message code="password.is.invalid"/></small>
                        <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                        <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                     </div>
                     <div class="form-group">
                     	<spring:message code="confirmation" var="pwdConfirm"/>
                        <form:label path="confirmation">${pwdConfirm}</form:label>
                        <form:input type="password" class="form-control" path="confirmation" placeholder="${pwdConfirm}" />
                       	<small class="text-danger"><spring:message code="passwords.do.not.match"/></small>
                        <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                        <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                     </div>
       			</fieldset>
       			<spring:message code="create" var="create"/>
       			<input type="submit" class="btn btn-primary pull-right" value="${create}"/>
       		</form:form>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container">
			<a class="navbar-brand" href="/home?lang=fr">Français</a>
        	<a class="navbar-brand" href="/home?lang=en">English</a>
		</div>
    </footer>
<script src="/assets/js/jquery.min.js"></script>
<script src="/assets/js/bootstrap.min.js"></script>
<script src="/assets/js/home.js"></script>
</body>
</html>
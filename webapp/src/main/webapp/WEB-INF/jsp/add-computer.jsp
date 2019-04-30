<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="/assets/css/bootstrap.min.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/assets/css/font-awesome.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/assets/css/main.css" />" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="/computers"> Application - Computer Database </a>
             <div class="pull-right">
            	<a class="navbar-brand" href="/computers/add?lang=fr">Français</a>
        		<a class="navbar-brand" href="/computers/add?lang=en">English</a>
        		<a class="narvbar-brand btn btn-danger" href="/logout"><i class="fa fa-power-off"></i></a>            
            </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                	<div class="row">
                		<div class="col-lg-5 col-md-12">
	                		<h1><spring:message code="computer.add"/></h1>
	                	</div>
	                    <div class="col-lg-7 col-md-12">
		        			<c:if test='${feedback.getMessage() != null}'>
		        				<p class="alert alert-${feedback.getStatus()}">${feedback.getMessage()}</p>
		        			</c:if>
		        		</div>
                	</div>
                    <form:form id="addComputer" action="/computers/add" method="POST" modelAttribute="computerDTO">
                        <fieldset>
                            <div class="form-group">
                            	<spring:message code="computer.name" var="computerName"/>
                                <form:label path="name">${computerName}</form:label>
                                <form:input type="text" class="form-control" path="name" placeholder="${computerName}" />
                                <small class="text-danger"><spring:message code="name.cannot.be.empty" /></small>
                                <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                            </div>
                            <div class="form-group">
                            	<spring:message code="introduction.date" var="introductionDate"/>
                                <form:label path="introduced">${introductionDate}</form:label>
                                <form:input type="date" class="form-control" path="introduced" placeholder="${introductionDate}" />
                            	<small><spring:message code="both.dates.are.set"/></small>
                            	<span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                            </div>
                            <div class="form-group">
                                <spring:message code="discontinuation.date" var="discontinuationDate"/>
                                <form:label path="discontinued">${discontinuationDate}</form:label>
                                <form:input type="date" class="form-control" path="discontinued" placeholder="${discontinuationDate}" />
                            	<small class="text-danger"><spring:message code="both.dates.are.set"/></small>
                            	<span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                            </div>
                            <div class="form-group">
                                <form:label path="companyId"><spring:message code="company"/></form:label>
                                <form:select path="companyId" class="form-control">
                                    <form:option value=""><spring:message code="no.company"/></form:option>
                                	<c:forEach items="${companies}" var="company">
									    <form:option value="${company.getId()}">${company.getName()}</form:option>
									</c:forEach>
                                </form:select>
                                <small><spring:message code="company.is.optional"/></small>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            <a href="/computers" class="btn btn-default"><spring:message code="cancel"/></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
<script src="<c:url value="/assets/js/jquery.min.js"/>"></script>
<script src="<c:url value="/assets/js/addComputer.js"/>"></script>
</body>
</html>
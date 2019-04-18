<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                	<div class="row">
                		<div class="col-lg-5 col-md-12">
	                		<h1>Add Computer</h1>
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
                                <form:label path="name">Computer name</form:label>
                                <form:input type="text" class="form-control" path="name" placeholder="Computer name" />
                                <small class="text-danger">Name can't be empty.</small>
                                <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                            </div>
                            <div class="form-group">
                                <form:label path="introduced">Introduced date</form:label>
                                <form:input type="date" class="form-control" path="introduced" placeholder="Introduced date" />
                            	<small>Both introduction and discontinuation date are optional.</small>
                            	<span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                            </div>
                            <div class="form-group">
                                <form:label path="discontinued">Discontinued date</form:label>
                                <form:input type="date" class="form-control" path="discontinued" placeholder="Discontinued date" />
                            	<small class="text-danger">If both are set, discontinuation date must be after introduction date.</small>
                            	<span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                            </div>
                            <div class="form-group">
                                <form:label path="companyId">Company</form:label>
                                <form:select path="companyId" class="form-control">
                                    <form:option value="">No Company</form:option>
                                	<c:forEach items="${companies}" var="company">
									    <form:option value="${company.getId()}">${company.getName()}</form:option>
									</c:forEach>
                                </form:select>
                                <small>Company is optional.</small>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="/computers" class="btn btn-default">Cancel</a>
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
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="/assets/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="/assets/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="/assets/css/main.css" rel="stylesheet" media="screen">
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
                  
                    <h1>Edit Computer</h1>

                    <form:form class="editForm" action="/computers/${computerDTO.getId()}/edit" method="POST" modelAttribute="computerDTO">
                        <form:input type="hidden" path="id" id="id"/>
                        
                        <fieldset>
                        	<div class="form-group">
                        		<form:label path="name">Computer name</form:label>
								<form:input type="text" id="name" path="name" class="form-control"/>
								<small class="text-danger">Name can't be empty.</small>
			                    <span class="glyphicon glyphicon-remove form-control-feedback"></span>
			                    <span class="glyphicon glyphicon-ok form-control-feedback"></span>
							</div>
							<div class="form-group">
								<form:label path="introduced">Introduced date</form:label>
								<form:input type="date" id="introduced" path="introduced" class="form-control"/>
								<small>Both introduction and discontinuation date are optional.</small>
			                    <span class="glyphicon glyphicon-remove form-control-feedback"></span>
			                    <span class="glyphicon glyphicon-ok form-control-feedback"></span>
							</div>
							 <div class="form-group">
								<form:label path="discontinued">Discontinued date</form:label>
								<form:input type="date" id="discontinued" path="discontinued" class="form-control" value="${computer.getDiscontinued()}"/>
								<small class="text-danger">If both are set, discontinuation date must be after introduction date.</small>
			                    <span class="glyphicon glyphicon-remove form-control-feedback"></span>
			                    <span class="glyphicon glyphicon-ok form-control-feedback"></span>
							</div>
                            <div class="form-group">
                            	<form:label path="companyId">Company</form:label>
	                            <form:select class="form-control" path="companyId" id="company">
									<form:option value="">No company</form:option>
			                        <c:forEach items="${companies}" var="company">
				                    	<form:option value="${company.getId()}">${company.getName()}</form:option>
									</c:forEach>
								</form:select>  
                            </div>         
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="/computers" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
    <script type="text/javascript" src="/assets/js/jquery.min.js"></script>
    <script type="text/javascript" src="/assets/js/editComputer.js"></script>  
</body>
</html>
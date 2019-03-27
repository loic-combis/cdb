<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${contextPath}/assets/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${contextPath}/assets/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${contextPath}/assets/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
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
                    <form id="addComputer" action="add-computer" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" name="name" id="computerName" placeholder="Computer name">
                                <small class="text-danger">Name can't be empty.</small>
                                <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" name="introduced" id="introduced" placeholder="Introduced date">
                            	<small>Both introduction and discontinuation date are optional.</small>
                            	<span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" name="discontinued" id="discontinued" placeholder="Discontinued date">
                            	<small class="text-danger">If both are set, discontinuation date must be after introduction date.</small>
                            	<span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select name="company" class="form-control" id="companyId" >
                                	<option value="-1">No company</option>
                                	<c:forEach items="${companies}" var="company">
									    <option value="${company.getId()}">${company.getName()}</option>
									</c:forEach>
                                </select>
                                <small>Company is optional.</small>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="${contextPath}/list-computers" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="${contextPath}/assets/js/jquery.min.js"></script>
    <script src="${contextPath}/assets/js/addComputer.js"></script>
</body>
</html>
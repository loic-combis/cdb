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
            <a class="navbar-brand" href="<c:url value="/computers" />"> Application - Computer Database </a>
            <div class="pull-right">
            	<a class="navbar-brand" href="/computers?lang=fr">Français</a>
        		<a class="navbar-brand" href="/computers?lang=en">English</a>
        		<a class="narvbar-brand btn btn-danger" href="/logout"><i class="fa fa-power-off"></i></a>
            </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
        	<div class="row">
        		<div class="col-lg-5 col-md-12">
	        		<h1 id="homeTitle"> ${pagination.getItemCount()} <spring:message code="computers.found" /></h1>
        		</div>
        		<div class="col-lg-7 col-md-12">
        			<c:if test='${feedback.getMessage() != null}'>
        				<p class="alert alert-${feedback.getStatus()}">${feedback.getMessage()}</p>
        			</c:if>
        		</div>
        	</div>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="<c:url value="/computers?page=1&itemPerPage=${pagination.getItemPerPage()}" />" method="GET" class="form-inline">
                        <spring:message code="search" var="searchLabel"/>
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="${searchLabel}" value="${search}"/>
                        <spring:message code="filter" var="filterLabel"/>
                        <input type="submit" id="searchsubmit" value="${filterLabel}"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="<c:url value="/computers/add" />"><spring:message code="computer.add" /></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="edit" /></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="<c:url value="/computers/delete"/>" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            <a href="<c:url value="/computers?itemPerPage=${pagination.getItemPerPage()}&search=${search}&orderby=name" />">
                            	<spring:message code="computer.name" />
                            	<c:if test='${orderBy.equals("name")}'>
	                        		<i class="fa fa-chevron-down"></i>
	                        	</c:if>
                            </a>
                        </th>
                        <th>
                            <a href="<c:url value="/computers?itemPerPage=${pagination.getItemPerPage()}&search=${search}&orderby=introduced" />">
                            	<spring:message code="introduction.date" />
                            	<c:if test='${orderBy.equals("introduced")}'>
	                        		<i class="fa fa-chevron-down"></i>
	                        	</c:if>
                           </a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <a href="<c:url value="/computers?itemPerPage=${pagination.getItemPerPage()}&search=${search}&orderby=discontinued"/>">
                            	<spring:message code="discontinuation.date" />
                            	<c:if test='${orderBy.equals("discontinued")}'>
	                        		<i class="fa fa-chevron-down"></i>
	                        	</c:if>
                            </a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                          <a href="<c:url value="/computers?itemPerPage=${pagination.getItemPerPage()}&search=${search}&orderby=company"/>">
                            <spring:message code="company" />
                          	<c:if test='${orderBy.equals("company")}'>
                        		<i class="fa fa-chevron-down"></i>
                        	</c:if>
                          </a>
                        </th>
						<th class="editMode">
                        	<spring:message code="save" />
						</th>
                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach items="${computers}" var="computer">
           				<tr> 
							<td class="editMode">
								<input type="checkbox" name="cb" class="cb" value="${computer.getId()}">
							</td>
							<td class="viewMode">${computer.getName()}</td>
							        
							<td class="viewMode">${computer.getIntroduced()}</td>
							        
							<td class="viewMode">${computer.getDiscontinued()}</td>
							        
							<td class="viewMode">${computer.getCompanyName()}</td>
							        
							<td class="editMode"><a href="/computers/${computer.getId()}/edit" class="btn btn-warning"><spring:message code="edit" /></a></td>							        
						</tr>
					</c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
                    <a href="<c:url value="/computers?page=${pagination.previous()}&itemPerPage=${pagination.getItemPerPage()}&search=${search}&orderby=${orderBy}"/>" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              </li>
              <li>
              	<a class="${pagination.compareCurrent(1)}" href="<c:url value="/computers?page=1&itemPerPage=${pagination.getItemPerPage()}&search=${search}&orderby=${orderBy}"/>">1</a>
              </li>
              <c:if test="${pagination.currentPageStart() - 1 > 1}">
                <li><a href="#">...</a></li>
              </c:if>
              <c:forEach 
              	var="count" 
              	begin="${pagination.currentPageStart()}" 
              	end="${pagination.currentPageEnd()}" 
              	step="1"
              >
              	<li>
              		<a class="${pagination.compareCurrent(count)}" href="<c:url value="/computers?page=${count}&itemPerPage=${pagination.getItemPerPage()}&search=${search}&orderby=${orderBy}"/>">${count}</a>
              	</li>
              </c:forEach>
              <c:if test="${pagination.currentPageEnd() + 1 < pagination.lastPage()}">
                <li><a href="#">...</a></li>
              </c:if>
              <c:if test="${pagination.lastPage() > 1}">
	              <li>
	              	<a 	class="${pagination.compareCurrent(pagination.lastPage())}"
	              		href="<c:url value="/computers?page=${pagination.lastPage()}&itemPerPage=${pagination.getItemPerPage()}&search=${search}&orderby=${orderBy}"/>">${pagination.lastPage()}</a>
	              </li>
              </c:if>
              <li>
                <a
                	href="<c:url value="/computers?page=${pagination.next()}&itemPerPage=${pagination.getItemPerPage()}&search=${search}&orderby=${orderBy}"/>" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        	</ul>
		
		</div>
        <div  class="btn-group btn-group-sm pull-right" role="group" style="transform:translateY(-50px);">
        	<c:forEach items="${pagination.getSizes()}" var="size" >
        	<a 
        	class="btn ${pagination.compareItemPerPage(size)}"
        	href="<c:url value="/computers?page=1&itemPerPage=${size}&search=${search}&orderby=${orderBy}"/>" class="btn btn-default">${size}</a>
        	</c:forEach>
        </div>
    </footer>
<script src="<c:url value="/assets/js/jquery.min.js"/>"></script>
<script src="<c:url value="/assets/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/assets/js/dashboard.js"/>"></script>

</body>
</html>
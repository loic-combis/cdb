<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        </div>
    </header>

    <section id="main">
        <div class="container">
        	<div class="row">
        		<div class="col-lg-5 col-md-12">
	        		<h1 id="homeTitle"> ${pagination.getItemCount()} Computer(s) found.</h1>
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
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" value="${search}"/>
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="<c:url value="/computers/add" />">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
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
                            	Computer name
                            	<c:if test='${orderBy.equals("name")}'>
	                        		<i class="fa fa-chevron-down"></i>
	                        	</c:if>
                            </a>
                        </th>
                        <th>
                            <a href="<c:url value="/computers?itemPerPage=${pagination.getItemPerPage()}&search=${search}&orderby=introduced" />">
                            	Introduction date
                            	<c:if test='${orderBy.equals("introduced")}'>
	                        		<i class="fa fa-chevron-down"></i>
	                        	</c:if>
                           </a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <a href="<c:url value="/computers?itemPerPage=${pagination.getItemPerPage()}&search=${search}&orderby=discontinued"/>">
                            	Discontinuation date
                            	<c:if test='${orderBy.equals("discontinued")}'>
	                        		<i class="fa fa-chevron-down"></i>
	                        	</c:if>
                            </a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                          <a href="<c:url value="/computers?itemPerPage=${pagination.getItemPerPage()}&search=${search}&orderby=company"/>">
                          	Company
                          	<c:if test='${orderBy.equals("company")}'>
                        		<i class="fa fa-chevron-down"></i>
                        	</c:if>
                          </a>
                        </th>
						<th class="editMode">
							Save
						</th>
                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach items="${computers}" var="computer">
           				<tr> 
           					<form method="POST" class="editForm" id="form-${computer.getId()}" action="<c:url value="/computers/edit"/>">
           						<fieldset>     
							       	<td class="editMode">
							       		<input type="checkbox" name="cb" class="cb" value="${computer.getId()}">
							        </td>
							        <td class="viewMode">${computer.getName()}</td>
							        <td class="editMode">
								        <div class="form-group">
								        	<input type="hidden" name="computerId" value="${computer.getId()}"/>
								    		<input type="text" id="name-${computer.getId()}" name="name" class="form-control" value="${computer.getName()}"/>
								        	<small class="text-danger">Name can't be empty.</small>
			                                <span class="glyphicon glyphicon-remove form-control-feedback"></span>
			                                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
								        </div>
							        </td>
							        <td class="viewMode">${computer.getIntroduced()}</td>
							        <td class="editMode">
								        <div class="form-group">
								    		<input type="date" id="introduced-${computer.getId()}" name="introduced" class="form-control" value="${computer.getIntroduced()}"/>
								        	<small>Both introduction and discontinuation date are optional.</small>
			                            	<span class="glyphicon glyphicon-remove form-control-feedback"></span>
			                                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
								        </div>
							        </td>
							        <td class="viewMode">${computer.getDiscontinued()}</td>
							        <td class="editMode">
								        <div class="form-group">
								    		<input type="date" id="discontinued-${computer.getId()}" name="discontinued" class="form-control" value="${computer.getDiscontinued()}"/>
									        <small class="text-danger">If both are set, discontinuation date must be after introduction date.</small>
			                            	<span class="glyphicon glyphicon-remove form-control-feedback"></span>
			                                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
								        </div>
							        </td>
							        <td class="viewMode">${computer.getCompanyName()}</td>
							        <td class="editMode">
							        	<select class="form-control" name="companyId" id="company-${computer.getId()}">
							        		<option value="">No company</option>
		                                	<c:forEach items="${companies}" var="company">
											    <option ${company.getId() == computer.getCompanyId() ? 'selected' : ''} value="${company.getId()}">${company.getName()}</option>
											</c:forEach>
							        	</select>
							        </td>
							        <td class="editMode"><input class="btn btn-success" type="submit" value="Save"/></td>							        
							    </fieldset>
							</form>
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
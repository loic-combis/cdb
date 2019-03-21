<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="${contextPath}/assets/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${contextPath}/assets/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${contextPath}/assets/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${contextPath}/list-computer"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${computerCount} Computer(s) found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="${contextPath}/add-computer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="${contextPath}/list-computers" method="POST">
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
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
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
					        <td>${computer.getName()}</td>
					        <td>${computer.getIntroductionDate()}</td>
					        <td>${computer.getDiscontinuationDate()}</td>
					        <td>${computer.getCompany().getName()}</td>  
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
                    <a href="${contextPath}/list-computers?page=${currentPage == 1 ? 1 : currentPage - 1}&itemPerPage=${itemPerPage}" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              </li>
              <c:forEach 
              	var="count" 
              	begin="${minPage}" 
              	end="${maxPage}" 
              	step="1"
              >
              	<li>
              		<a class='${count == currentPage ? "current" : ""}' href="${contextPath}/list-computers?page=${count}&itemPerPage=${itemPerPage}">${count}</a>
              	</li>
              </c:forEach>
              <li>
                <a 
                	href="${contextPath}/list-computers?page=${currentPage == maxPage ? currentPage : currentPage + 1}&itemPerPage=${itemPerPage}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        	</ul>
		</div>
        <div  class="btn-group btn-group-sm pull-right" role="group" style="transform:translateY(-50px);">
        	<c:forEach items="${sizes}" var="size" >
        	<a 
        	class="btn ${size == itemPerPage ? 'current' : ''}"
        	href="${contextPath}/list-computers?page=1&itemPerPage=${size}" class="btn btn-default">${size}</a>
        	</c:forEach>
        </div>
    </footer>
<script src="${contextPath}/assets/js/jquery.min.js"></script>
<script src="${contextPath}/assets/js/bootstrap.min.js"></script>
<script src="${contextPath}/assets/js/dashboard.js"></script>

</body>
</html>
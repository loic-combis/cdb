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
			<div class="alert alert-danger">
				Error ${errorCode} : ${errorMessage}
				<br/>
				<!-- stacktrace -->
			</div>
		</div>
	</section>

	<script src="/assets/js/jquery.min.js"></script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/assets/js/dashboard.js"></script>

</body>
</html>
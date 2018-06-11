<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Sign In</title>
		<link type="text/css" rel="stylesheet" href="webjars/bootstrap/4.1.1/dist/css/bootstrap.min.css">
		<link type="text/css" rel="stylesheet" href="stylesheets/global.css">
		<script type="text/javascript" src="webjars/bootstrap/4.1.1/dist/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="webjars/jquery/3.1.1/jquery.min.js"></script>
		<script type="text/javascript" src="webjars/popper.js/1.14.3/dist/umd/popper.min.js"></script>
	</head>
<body>
	<div class="container-fluid bg">
		<div class="row">
			<div class="col-md-4 col-sm-4 col-xs-12"></div>
			<div class="col-md-4 col-sm-4 col-xs-12">
				<!-- Form Start -->
				<form class="form-container" action="\login.do" method="post">
					<h1>Sing Up</h1>
					<div class="form-group">
						<label for="InputEmail">Email Address</label>
						<input type="email" class="form-control" id="email" name="userId" placeholder="Email">
					</div>
					<div class="form-group">
						<label for="InputPassword">Password</label>
						<input type="password" class="form-control" id="password" name="password" placeholder="Password">
					</div>
					<button type="submit" class="btn btn-success btn-block">Sign In</button>
				</form>
			</div>
		</div>
		<div class="col-md-4 col-sm-4 col-xs-12"></div>
	</div>
</body>
</html>
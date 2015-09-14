<html class="no-js">
<head>
    <meta charset="utf-8">
    <title>Sensefy Search</title>
    <meta content="" name="description">
    <meta content="width=device-width" name="viewport">
    <link type="image/x-icon" href="favicon.ico" rel="shortcut icon">
    <link type="image/x-icon" href="favicon.ico" rel="icon">

    <link type="text/css" rel="stylesheet" href="//fonts.googleapis.com/css?family=Open+Sans:400,600">
    <link href="css/semantic.css" rel="stylesheet">
    <link href="css/master.css" rel="stylesheet">
    <link href="css/devices.css" rel="stylesheet">

</head>
<body id="sensefy" ng-controller="RootController" ng-app="SalzburgDemo" class="ng-scope">
<div ng-view="" class="ui page grid wrapper ng-scope">    <div id="loginState" class="three column row ng-scope">
        <div class="five wide column hidden">&nbsp;</div>

        <div class="ui six wide column form segment">

            <div id="header" class="ui inverted blue menu mgn-btm-xl">
                <img alt="Sensefy Logo" src="css/sensefylogo.png">
            </div>
			<#if RequestParameters['error']??>
			
				<div class="alert alert-danger">
					
				</div>
	            <div ng-click="errors.login = !errors.login" ng-show="errors.login" class="ui icon negative message ng-hide"  style="display:block;">
	                
	                <div class="content">
	                    <div class="header">Sorry!</div>
	                    <p >There was a problem logging in. Please try again.</p>
	                </div>
	            </div>
	            
            </#if>
            <form name="loginform" role="form" action="login" method="post" class="ng-pristine ng-invalid ng-invalid-required">
            <div class="field">
                <label translate="txtUsername" for="username" class="ng-scope">Username</label>
                <div class="ui left icon input">
                    <i class="user icon"></i>
                    <input type="text" autofocus="autofocus" ng-keyup="$event.keyCode == 13 ? doLogin() : null" required="" ng-model="user.username" placeholder="Username" name="username" id="username" class="ng-pristine ng-invalid ng-invalid-required">
                </div>
            </div>
            <div class="field">
                <label translate="txtPassword" for="password" class="ng-scope">Password</label>
                <div class="ui left icon input">
                    <i class="lock icon"></i>
                    <input type="password" ng-keyup="$event.keyCode == 13 ? doLogin() : null" required="" ng-model="user.password" placeholder="Password" name="password" id="password" class="ng-pristine ng-invalid ng-invalid-required">
                </div>
            </div>
           
            <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <input type="submit" value="Login" id="loginButton" class="ui yellow submit button">
            <div ng-show="doingLogin" class="loader-position ng-hide" id="circleG"  style="display:none;">
                <div class="circleG" id="circleG_1">
                </div>
                <div class="circleG" id="circleG_2">
                </div>
                <div class="circleG" id="circleG_3">
                </div>
            </div>
            </form>
        </div>
        <div class="five wide column hidden">&nbsp;</div>
    </div>
</div>
<!-- ngInclude: 'views/layout/footer.html' --><div ng-include="'views/layout/footer.html'" class="ng-scope"><footer class="ui footer inverted full center ng-scope">
    <p>&copy; 2007 - 2015 <a href="#">Zaizi Limited</a>. All rights reserved.</p>
</footer></div>


</body></html>
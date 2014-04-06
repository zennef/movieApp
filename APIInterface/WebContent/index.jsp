<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Pick Your Flick</title>

    	<!-- Bootstrap core CSS -->
    	<link href="css/bootstrap.min.css" rel="stylesheet">

    	<!-- Custom styles for this template -->
    	<link href="css/screen.css" rel="stylesheet">

    	<!-- Just for debugging purposes. Don't actually copy this line! -->
    	<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    	<!--[if lt IE 9]>
      	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      	<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    	<![endif]-->	
		<link rel="stylesheet" href="css/supersized.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="theme/supersized.shutter.css" type="text/css" media="screen" />
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery.easing.min.js"></script>
		
		<script type="text/javascript" src="js/supersized.3.2.7.min.js"></script>
		<script type="text/javascript" src="theme/supersized.shutter.min.js"></script>
		
		<script type="text/javascript">
			
			jQuery(function($){
				
				$.supersized({
				
					// Functionality
					slide_interval          :   3000,		// Length between transitions
					transition              :   1, 			// 0-None, 1-Fade, 2-Slide Top, 3-Slide Right, 4-Slide Bottom, 5-Slide Left, 6-Carousel Right, 7-Carousel Left
					transition_speed		    :   700,		// Speed of transition
															   
					// Components							
					slide_links				:	'blank',	// Individual links for each slide (Options: false, 'num', 'name', 'blank')
					slides: [     // Slideshow Images
            			{image : 'img/1.jpg', title : 'movies', thumb : 'movies'},
            			{image : 'img/2.jpg', title : 'movies', thumb : 'movies'},
            			{image : 'img/3.jpg', title : 'movies', thumb : 'movies'},
            			{image : 'img/4.jpg', title : 'movies', thumb : 'movies'},
            			{image : 'img/5.jpg', title : 'movies', thumb : 'movies'}
          			]  	
				});
		    });
		    
		</script>
	</head>
<body>
	<div class="site-wrapper">
      <div class="site-wrapper-inner">
        <div class="cover-container">

          <div class="masthead clearfix">
            <div class="inner">
              <h3 class="masthead-brand white">Pick Your Flick</h3>
              <ul class="nav masthead-nav">
                <li class="active white"><a href="#">Search</a></li>
                <li class='white'><a href="#">Top Charts</a></li>
              </ul>
            </div>
          </div>

          <div class="inner cover">
            <h1 class="cover-heading white">Find a Movie</h1>
            <p class="lead white">Search Amazon, Redbox, and iTunes for movies and TV shows to watch tonight.</p>
            <form action="/APIInterface/GetMovie" method="post">
              <div class='col-xs-10'>
              <input class='form-control input-lg' type="text" name="mediaName" placeholder="Video Search"/>
            </div>
              <button class='btn-lg btn-danger'>Submit</button>
              <button class='btn-lg btn-link' onClick='/APIInterface/getTopMovies'>Top Charts</button>
            </form>
          </div>

          <footer class="mastfoot">
            <div class="inner">
              <p>This is a project by Cam Lewis, Eric Dixon, and Mike Garcia for CS 313 - Web Engineering 2</p>
            </div>
          </footer>

        </div>
      </div>
    </div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->

    <script src="js/bootstrap.min.js"></script>
    <script src="js/docs.min.js"></script>
</body>
</html>

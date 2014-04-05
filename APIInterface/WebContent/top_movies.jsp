<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Flicks</title>

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
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>		
	</head>
<body>

	<div class="site-wrapper">

      <div class="site-wrapper-inner">

        <div class="cover-container">

          <div class="masthead clearfix">
            <div class="inner">
              <h3 class="masthead-brand">Flicks</h3>
              <ul class="nav masthead-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="#">Features</a></li>
                <li><a href="#">Contact</a></li>
              </ul>
            </div>
          </div>
          <div class="row">
            <img class='col-md-4' src="${poster}">
            <h1>Movie: ${movieName}</h1>
            <h4>Price: ${itunePrice}</h4>
            <h4>RedBox Title: ${redBoxTitle}</h4>
            <h4>RedBox Director: ${redBoxDirector}</h4>
            <div>
              <div id="player"></div>
            </div>
          </div>
            <script> 
            // 2. This code loads the IFrame Player API code asynchronously.
            var tag = document.createElement('script');
  
            tag.src = "https://www.youtube.com/iframe_api";
            var firstScriptTag = document.getElementsByTagName('script')[0];
            firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
  
            // 3. This function creates an <iframe> (and YouTube player)
            //    after the API code downloads.
            var player;
            function onYouTubeIframeAPIReady() {
              player = new YT.Player('player', {
                height: '390',
                width: '640',
                videoId: "${trailerId}"
              });
            }
  
            // 4. The API will call this function when the video player is ready.
            function onPlayerReady(event) {
              event.target.playVideo();
            }
  
            // 5. The API calls this function when the player's state changes.
            //    The function indicates that when playing a video (state=1),
            //    the player should play for six seconds and then stop.
            var done = false;
            function onPlayerStateChange(event) {
              if (event.data == YT.PlayerState.PLAYING && !done) {
                setTimeout(stopVideo, 6000);
                done = true;
              }
            }
          function stopVideo() {
            player.stopVideo();
          }
          </script> 

          <div class="mastfoot">
            <div class="inner">
              <p>This is a project by Cam Lewis, Eric Dixon, and Mike Garcia for CS 313 - Web Engineering 2</p>
            </div>
          </div>

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
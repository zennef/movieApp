<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Movie: ${movieName}</h1>
	<h4>Price: ${itunePrice}</h4>
	<img src="${poster}">
	<h4>RedBox Title: ${redBoxTitle}</h4>
	<h4>RedBox Director: ${redBoxDirector}</h4>
	
	<div>
    	<div id="player"></div>
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
		
</body>
</html>
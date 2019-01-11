<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>  
<html>  
<head>  
    <title>WebSocket/SockJS Echo Sample (Adapted from Tomcat's echo sample)</title>  
  
    <script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>  
  
    <script type="text/javascript">  
    var url = 'ws://' + window.location.host + '/qcwifi/test.ws';
    var sock = new WebSocket(url);

    sock.onopen = function() {
        console.log('Opening');
        sock.send('Marco!');
    };

    sock.onmessage = function(e) {
        console.log('Received Message: ', e.data);
        setTimeout(function() {
            sayMarco()
        }, 2000);
    };

    sock.onclose = function() {
        console.log('Closing');
    };

    function sayMarco() {
        console.log('Sending Marco!');
        sock.send('Marco!');
    }
    </script>  
</head>  
<body>  
</body>  
</html>  
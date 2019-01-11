var stompClient = null;

function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	} else {
		$("#conversation").hide();
	}
	$("#greetings").html("");
}

function connect() {
	var socket = new SockJS('http://www.minihu.qcwifi.ltd:8080/qcwifi-soc/qcsocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({
	    login: 'mylogin',
	    passcode: 'mypasscode',
	    'client-id': 'my-client-id'
	}, function(frame) {
		setConnected(true);
		stompClient.subscribe('/topic/greetings', function(greeting) {
			showGreeting(JSON.parse(greeting.body).content);
		});
	});
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
}

function sendName() {
	stompClient.send("/soc/hello", {}, JSON.stringify({
		'name' : $("#name").val()
	}));
}

function showGreeting(message) {
	$("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
//	$("#connect").click(function() {
		connect();
//	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#send").click(function() {
		sendName();
	});
});

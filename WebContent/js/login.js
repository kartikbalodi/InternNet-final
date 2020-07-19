function login() {
	clearLoginError();
	$('#login').click(function(cl){
	cl.preventDefault();

	var userID;
    var username = $('#inputEmail').val();
    var password = $('#inputPassword').val();
    var req = JSON.stringify({
        username : username,
        password : password
      });
    
    var url1 = './TransferID';

    // The request parameters
    var url = './login';

    ajax('POST', url, req,
      // successful callback
      function(res) {
        var result = JSON.parse(res);

        // successfully logged in
        if (result.status === 'SUCCESS') {
        	window.location.href = "index.html";
        	localStorage.setItem('user',username);
        }
        else if(result.status === 'FAIL1' || result.status === 'FAIL2'){
        	$('#login-error').html(result.error);
        	localStorage.clear();
        }
      },
      // error
      function() {
        showLoginError();
      },
      true);
    
    ajax('POST', ur1, req, function(res){
    	var results = JSON.parse(res);
    	if(results.status === 'SUCCESS'){
    		userID = results.userID;
    		localStorage.setItem('userID', userID);
    	}
    	else{
    		console.log('UserID does not exist');
    	}
    });
    
    
	}); 
}

 function showLoginError() {
	$('#login-error').html('Invalid username or password');
 }

 function clearLoginError() {
    $('#login-error').html('');
 }
  
function ajax(method, url, data, successCallback, errorCallback) {
	    var xhr = new XMLHttpRequest();

	    xhr.open(method, url, true);

	    xhr.onload = function() {
	      if (xhr.status === 200) {
	        successCallback(xhr.responseText);
	      } else {
	        errorCallback();
	      }
	    };

	    xhr.onerror = function() {
	      console.error("The request couldn't be completed.");
	      errorCallback();
	    };

	    if (data === null) {
	      xhr.send();
	    } else {
	      xhr.setRequestHeader("Content-Type",
	        "application/json;charset=utf-8");
	      xhr.send(data);
	    }
}

$(document).ready(function() { 
	// Redirect to index if already logged in
	if(localStorage.getItem('user') != null) {
		window.location.href = 'index.html';
		return;
	}
	
	login();	 
});
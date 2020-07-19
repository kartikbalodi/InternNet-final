$(document).ready(function() {
	// Redirect to index if already logged in
	if(localStorage.getItem('user') != null) {
		window.location.href = 'index.html';
		return;
	}
	
	// Handle profile image select
	var imageLink = "https://www.bootdey.com/img/Content/avatar/avatar1.png"; // default img1
	$(".dropdown-menu div img").click(function() {
		imageLink = this.src;
	})
	
	// On sign in click
	$('#submit').click(function(e) {
		e.preventDefault();
		// User input
		let user = JSON.stringify({
			firstname : $('#firstName').val(),
			lastname : $('#lastName').val(),
			username : $('#inputUsername').val(),
			password: $('#inputPassword').val(),
			confirmPassword: $('#confirmPassword').val(),
			location: $('#location').val(),
			imageLink: imageLink
		});
		
		$.post('TransferID', user, function(data){
    
    	var results = JSON.parse(res);
    	if(results.status === 'SUCCESS'){
    		userID = results.userID;
    	}
    	else{
    		console.log('UserID does not exist');
    	}
    });
		
		// Check for authentification
		$.post('register', user, function(data) {
			window.location.href = 'index.html';
			localStorage.setItem('user', $('#inputUsername').val());
		})
		
	    $.post('TransferID', user, function(data){
	    	var results = JSON.parse(res);
	    	if(results.status === 'SUCCESS'){
	    		localStorage.setItem('userID', results.userID);
	    	}
	    	else{
	    		console.log('UserID does not exist');
	    	}
	    });
		
		
		$.fail(function(error) {
			$('#error').html(error.responseJSON.error);
			localStorage.clear();
		});
	});
});

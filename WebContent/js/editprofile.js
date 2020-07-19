function PopulateData(){
	 var user = localStorage.getItem('user');
	 var url = './Profile';
	 var req = JSON.stringify({
		      username : user
	});
	 ajax('POST',url, req, function(res) {
			var result = JSON.parse(res);
			var user = result[0];
	        var firstName = user.firstName;
	        var lastName = user.lastName;
	    	var location = user.location;
	    	
	    	$('#fName').html(firstName);
	    	$('#lName').html(lastName);
	    	$('#local').html(location);
	 });
}


function MakeChange(){
	var firstName = $('#firstName').val();
	var lastName = $('#lastName').val();
	var location = $('#location').val();
	var photo = $('#profilePhoto').val();
	
	var url = './EditProfile';
	var req = JSON.stringify({
		firstName: firstName,
		lastName: lastName,
		location: location,
		photo: photo
	});
	
	ajax('POST', url, req, function(res){
		
		
		
		
	});
	
}


function ajax(method, url, data, successCallback) {
    var xhr = new XMLHttpRequest();

    xhr.open(method, url, true);

    xhr.onload = function() {
      if (xhr.status === 200) {
        successCallback(xhr.responseText);
      }else{
    	  console.log("False ");
      }
      
    };

    xhr.onerror = function() {
      console.error("The request couldn't be completed.");
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
	 PopulateData();
	 MakeChange();
});
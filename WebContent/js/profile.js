var remove = false;
var ids;

function GetFavorite(){
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
        var image = user.imageLink;
    	var location = user.location;
    
    	var fullName = firstName + " " + lastName;
    	
    	$('#name').html(fullName);
    	$('#location').html(location);
    	$('#profileImg').attr('src', image);
    	
    for(let i=1; i<result.length; i++){
    	var intern = JSON.parse(result[i]);
    	var company = intern.company;
    	var jobTitle = intern.jobTitle;
    	var position = intern.position;
    	var location = intern.location;
    	var appStatus = intern.appStatus;
    	var appDeadline = intern.appDeadline;
    	var dateApplied = intern.dateApplied;
    	
    	localStorage.setItem('obj' + i, JSON.stringify(intern));
	    let content = [];
	    
	    content.push(
    '<tr>' +
		'<td>' +	
	     company + 
	     '</td>' +
		'<td>' + 
	     position + 
	     '</td>' + 
		 '<td>' + 
	     location + 
	     '</td>' + 
		 '<td>' + 
	     dateApplied +
	     '</td>' + 
		 '<td>' + 
	     appStatus + 
	     '</td>' + 
		 '<td>' + 
		 '<button name="edit" class="btn btn-info">' +
		 '  <i class="fas fa-edit"></i>' +
		 '</button>' + 
		 '<button name="del" class="btn btn-danger">' +
		 '<i class="fas fa-trash"></i>' + 
		 '</button>' + 
		 '</td>' + 
	  '</tr>');
	    
	    $('#itableContent').append(content.join(''));  	
	}
	});
 }



function Remove(){
	$('#information').on("click", ".remove",function(cl){
		cl.preventDefault();
		var id= $(this).attr('id');
		var user = localStorage.getItem('user');
	    console.log("ID ");
	    console.log(id);
	    $('#information').find('#' + id).html('');
	    $('#information').find('.hline').find('#' + id).html('');
			var bookId = ids[0][id];
			console.log(ids);
			console.log(bookId);
			var req = JSON.stringify({
				username: user,
				book_id: bookId
			});
			var url2 = './RemoveBook';
			ajax('POST', url2, req, function(rest){
				var result = JSON.parse(rest);
				if(result.status === "OK"){
					console.log("Remove Success");
				}
			}, true);
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
	console.log("usss");
	 GetFavorite();
	 Remove();
});
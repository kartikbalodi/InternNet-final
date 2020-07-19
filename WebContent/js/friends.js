$(document).ready(function() {
  // Redirect to login if not logged in
  if (localStorage.getItem('user') == null) {
    window.location.href = 'login.html';
    return;
  }

  // Handle search
  $('#search').click(function(e) {
    e.preventDefault();
    var searchInput = $('#searchInput').val();

    // Clear html first
    $('#find-friends').html('');

    // Dynamically load html
    $.get('Profile?query=' + searchInput, function(data) {
      console.log(data);
      for (let i = 0; i < data.length; i++) {
        let searchFriendHTML =
          '<div class="friend">' +
          '										<div class="row">' +
          '											<div class="col-md-2 col-sm-2">' +
          '												<img' +
          `													src="${data[i].imageLink}"` +
          '													alt="user" class="profile-photo-lg" />' +
          '											</div>' +
          '											<div class="col-md-7 col-sm-7">' +
          '												<h5>' +
          `													<a class="text-dark">${data[i].firstName} ${data[i].lastName}</a>` +
          '												</h5>' +
          '												<p>' +
          '													<i class="fas fa-ellipsis-h"></i> Currently applying to' +
          '													Microsoft' +
          '												</p>' +
          '											</div>' +
          '											<div class="col-md-3 col-sm-3">' +
          '												<button class="btn btn-info btn-block pull-right">' +
          '													<i class="fas fa-info-circle"></i> See More' +
          '												</button>' +
          '												<button class="btn btn-success btn-block pull-right">' +
          '													<i class="fas fa-user-plus"></i> Add Friend' +
          '												</button>' +
          '											</div>' +
          '										</div>' +
          '									</div>';
        $('#find-friends').append(searchFriendHTML);
      }
    });
  });

  // On sign in click
  $('#submit').click(function(e) {
    e.preventDefault();
    // User input
    let user = JSON.stringify({
      firstname: $('#firstName').val(),
      lastname: $('#lastName').val(),
      username: $('#inputUsername').val(),
      password: $('#inputPassword').val(),
      confirmPassword: $('#confirmPassword').val(),
      location: $('#location').val(),
      imageLink: imageLink
    });

    // Check for authentification
    $.post('register', user, function(data) {
      window.location.href = 'index.html';
      localStorage.setItem('user', $('#inputUsername').val());
    }).fail(function(error) {
      $('#error').html(error.responseJSON.error);
      localStorage.clear();
    });
  });
});

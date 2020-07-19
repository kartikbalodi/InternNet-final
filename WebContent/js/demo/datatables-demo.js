// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable({
    columnDefs: [{ targets: 'no-sort', orderable: false }]
  });
});

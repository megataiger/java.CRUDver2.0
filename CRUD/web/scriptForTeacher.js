$(document).ready(function () {
   $.post(
       "selectAllTeachers",
       function (data) {
           $("#teachers").html(data);
       }
   );
});
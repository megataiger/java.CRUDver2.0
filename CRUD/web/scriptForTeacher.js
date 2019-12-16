$(document).ready(function () {
   $.post(
       "selectAllTeachers",
       function (data) {
           $("#teachers").html(data);
           $("td.id").hide();
           $("#groups").hide();
       }
   );
   
   $("#teachers").on("click", ".name", function () {
       var td = $(this);
       var name = $(td).text();
       var tr = $(td).parent();
       $(this).html("<input id='newNameTeacher' value='" + name + "' type='text'>");
       var input = $("#newNameTeacher");
       $(input).click(function (evt) {
           evt.stopPropagation();
       })
       $(input).select();
       $(input).keypress(function (evt) {
           if (evt.keyCode === 13) {
               name = $(input).val();
               var tds = $(tr).children();
               var id = $(tds[0]).text();
               $.post(
                   "updateTeacher", {
                       name : name,
                       id : id
                   }, function (data) {
                       td.html(name);
                   }
               );
           }
       });
       
       $(input).blur(function () {
           $(td).html(name);
       })
   });

    $("#teachers").on("click", ".date", function () {
        var td = $(this);
        var date = $(td).text();
        var tr = $(td).parent();
        $(this).html("<input id='newDateTeacher' value='" + date + "' type='date'>");
        var input = $("#newDateTeacher");
        $(input).click(function (evt) {
            evt.stopPropagation();
        })
        $(input).select();
        $(input).keypress(function (evt) {
            if (evt.keyCode === 13) {
                date = $(input).val();
                var tds = $(tr).children();
                var id = $(tds[0]).text();
                $.post(
                    "updateTeacher", {
                        date : date,
                        id : id
                    }, function (data) {
                        td.html(date);
                    }
                );
            }
        });

        $(input).blur(function () {
            $(td).html(date);
        })
    });

    $("#teachers").on("click", ".gender", function () {
        var text = $(this).text();
        var td = $(this);
        var tr = $(this).parent();

        $(tr).click();

        $(this).html("<select></select>");
        var select = $(td).children();

        $.post("gen", {
            gender : text
        }, function (data) {
            $(select).html(data);
        });

        $(select).click(function (evt) {
            evt.stopPropagation();
        });

        $(select).focus();

        $(select).change(function (){
            var gender = $(this).val()

            $(td).html(gender);

            var cells = $(tr).children();

            $.post("updateTeacher", {
                id : $(cells[0]).text(),
                gender : gender
            }, function (data) {
                console.log(data);
            });
        });

        $(select).blur(function (){
            $(td).html($(this).val());
        });
    });

    $("#teachers").on("click", ".del", function (evt) {
        evt.preventDefault();

        var td = $(this).parent();
        var tr = $(td).parent();
        var cells = $(tr).children();
        var id = $(cells[0]).text();

        $.get("deleteTeacher", {
           id : id
       }, function () {
            $.post(
                "selectAllTeachers",
                function (data) {
                    $("#teachers").html(data);
                    $("td.id").hide();
                }
            );
        });
    });

    $("#addTeacher").submit(function (evt) {
        evt.preventDefault();

        $.post("insertTeacher",
            $(this).serialize(),
            function () {
                $.post(
                    "selectAllTeachers",
                    function (data) {
                        $("#teachers").html(data);
                        $("td.id").hide();
                    }
                );
            });
    });

    $("#findTeachers").submit(function (evt) {
        evt.preventDefault();

        $.post("selectAllTeachers",
            $(this).serialize(),
            function (data) {
                $("#teachers").html(data);
                $("td.id").hide();
            })
    });

    $("#teachers").on("click", ".list", function (evt) {
        evt.preventDefault();

        var td = $(this).parent();
        var tr = $(td).parent();
        var cells = $(tr).children();
        var id = $(cells[0]).text();
        var name = $(cells[1]).text();

        $("#name").attr("class", id);
        $("#name").text(name);

        $.get("getGroup", {
            id : id
        }, function (data) {
            $("#groups").show();
            $("#tableGroup").html(data);
            $("#searchGroup").attr("class", "view");
            });
        });

    $("#addGroup").click(function () {
        var id = $("#name").attr("class");

        $(this).css({"border" : "0px"});
        $("#viewGroup").css({"border" : "1px solid black"});

        $.get("getNewGroups", {
            id : id
        }, function (data) {
            $("#tableGroup").html(data);
            $("#searchGroup").attr("class", "add");
        })
    })

    $("#viewGroup").click(function () {
        var id = $("#name").attr("class");

        $(this).css({"border" : "0px"});
        $("#addGroup").css({"border" : "1px solid black"});

        $.get("getGroup", {
            id : id
        }, function (data) {
            $("#tableGroup").html(data);
            $("#searchGroup").attr("class", "view");
        })
    })

    $("#tableGroup").on("click", ".deleteGroup", function (evt) {
        evt.preventDefault();

        var td = $(this).parent();
        var tr = $(td).parent();
        var cells = $(tr).children();
        var number = $(cells[0]).text();

        var id = $("#name").attr("class");

        $.get("deleteGroupForTeacher", {
            id : id,
            number : number
        }, function () {
            $("#viewGroup").click();
        });
    })

    $("#tableGroup").on("click", ".addGroup", function (evt) {
        evt.preventDefault();

        var td = $(this).parent();
        var tr = $(td).parent();
        var cells = $(tr).children();
        var number = $(cells[0]).text();

        var id = $("#name").attr("class");

        $.get("addGroupForTeacher", {
            id : id,
            number : number
        }, function () {
            $("#addGroup").click();
        });
    })

    $("#searchGroup").keyup(function () {
        if ($(this).attr("class") === "view") {
            var id = $("#name").attr("class");

            $.get("getGroup", {
                id : id,
                number : $(this).val()
            }, function (data) {
                $("#tableGroup").html(data);
            });
        } else if ($(this).attr("class") === "add") {
            var id = $("#name").attr("class");

            $.get("getNewGroups", {
                id : id,
                number : $(this).val()
            }, function (data) {
                $("#tableGroup").html(data);
            });
        }
    })
});
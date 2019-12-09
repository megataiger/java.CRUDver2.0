$(document).ready(function () {

    $.post("selectAllGroup", function (data) {
        $("#groups").html(data);
        $("td.id").hide();
        $("#newMenu").hide();
    })

    $("#groups").on("click", ".number", function () {
        var text = $(this).text();
        var tr = $(this).parent();
        var td = $(this);

        $(this).html("<input type='text' value='" + text + "'>");
        var input = $(this).children();

        $(input).click(function (evt) {
            evt.stopPropagation();
        })

        $(input).select();

        $(input).blur(function () {
            text = $(this).val();
            $(td).html(text);
            var result = $(tr).children();
            $.post("upGroup", {
                id: $(result[0]).text(),
                number: text
            }, function (data) {
                console.log(data)
            })
        })

        $(input).keypress(function (evt) {
            if (evt.keyCode == 13) {
                text = $(this).val();

                (td).html(text);
                var result = $(tr).children();

                $.post("upGroup", {
                    id: $(result[0]).text(),
                    number: text
                }, function (data) {
                    console.log(data)
                })
            }
        })
    })

    $("#groups").on("click", ".del", function (evt) {
        evt.preventDefault();
        var td = $(this).parent();
        var tr = $(td).parent();
        var result = $(tr).children();
        var id = $(result[0]).text();

        $(tr).remove();

        $.post("delGroup", {
            id: id
        }, function (data) {
            console.log(data);

            $.post("selectAllGroup", function (data) {
                $("#groups").html(data);
                $("td.id").hide();
            })
        })
    })

    $("#addGroup").keydown(function (evt) {
        if (evt.keyCode == 13) {

            $.post("insGroup", {
                number: $(this).val()
            }, function (data) {

                $.post("selectAllGroup", function (data) {
                    $("#groups").html(data);
                    $("td.id").hide();
                })

                $(this).val("");
            })
        }
    })

    $("#addGroup").blur(function () {
        $(this).val("");
    })

    $("#search").keyup(function () {
        if ($(this).val() == "") {

            $.post("selectAllGroup", function (data) {

                $("#groups").html(data);

                $("td.id").hide();
            })
        } else {

            $.post("selectAllGroup", {
                number: $(this).val()
            }, function (data) {

                $("#groups").html(data);

                $("td.id").hide();
            })
        }
    })

    $("#groups").on("click", ".teachers", function (evt) {
        evt.preventDefault();
        var td = $(this).parent();
        var tr = $(td).parent();
        var tds = $(tr).children();
        var number = $(tds[1]).text();

        $("#num").html(number)

        $("#view").click();

        $("#divSearch").html("<input id='searchTeacher' type='text' class='view'>");

        $("#newMenu").show();
    })

    $("#view").on("click", function () {
        var number = $("#num").text();

        $.get("getTeachers", {
            number: number
        }, function (data) {

            $("#teachers").html(data);

            $("#searchTeacher").attr("class", "view");
        })
    })

        $("#add").on("click", function () {
            var number = $("#num").text();

            $.get("getNewTeachers", {
                number: number
            }, function (data) {

                $("#teachers").html(data);

                $("#searchTeacher").attr("class", "add");
            })
        })

        $("#teachers").on("click", ".add", function (evt) {
            evt.preventDefault();
            var idTeacher = $(this).attr("href");
            var numberGroup = $("#num").text();

            $.post("addTeacherForGroup", {
                id: idTeacher,
                number: numberGroup
            }, function () {

                $("#add").click();
            })
        })

        $("#teachers").on("click", ".del", function (evt) {
            evt.preventDefault();
            var idTeacher = $(this).attr("href");
            var numberGroup = $("#num").text();

            $.post("deleteTeacherForGroup", {
                id: idTeacher,
                number: numberGroup
            }, function () {

                $("#view").click();
            })
        })

        $("#divSearch").on("keyup", "#searchTeacher", function () {

            var list = $(this).attr("class");

            if (list == "view") {

                $.get("getTeachers", {
                    number: $("#num").text(),
                    name: $(this).val()
                }, function (data) {

                    $("#teachers").html(data);
                })
            } else {

                $.get("getNewTeachers", {
                    number: $("#num").text(),
                    name: $(this).val()
                }, function (data) {

                    $("#teachers").html(data);
                })
            }
        })
    })
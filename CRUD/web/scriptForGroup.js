$(document).ready(function () {
    $("td.id").hide();
    $("td.number").click(function (evt) {
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
                id : $(result[0]).text(),
                number : text
            }, function (data) {
                console.log(data)
            })
        })
        $(input).keypress(function (evt) {
            if (evt.keyCode == 13) {
                text = $(this).val();
                $(td).html(text);
                var result = $(tr).children();
                $.post("upGroup", {
                    id : $(result[0]).text(),
                    number : text
                }, function (data) {
                    console.log(data)
                })
            }
        })
    })
    $("a.del").click(function (evt) {
        evt.preventDefault();
        var td = $(this).parent();
        var tr = $(td).parent();
        var result = $(tr).children();
        var id = $(result[0]).text();
        $(tr).remove();
        $.post("delGroup", {
            id : id
        }, function (data) {
            console.log(data);
            $.post("selGroup", function (data) {
                $("tbody").html(data);
                $("td.id").hide();
            })
        })
    })

    $("input.add").focus(function () {
        $(this).blur(function () {
            $(this).val("");
        })
        $(this).keypress(function (evt) {
            if (evt.keyCode == 13) {
                $.post("insGroup", {
                    number : $(this).val()
                }, function (data) {
                    console.log(data);
                    $.post("selGroup", function (data) {
                        $("tbody").html(data);
                        $("td.id").hide();
                    })
                })
                $(this).val("");
            }
        })
    })

    $("input.search").keypress(function (evt) {
        if (evt.keyCode == 13) {
            $.post("searchGroup", {
                number : $(this).val()
            }, function (data) {
                $("tbody").html(data);
                $("td.id").hide();
            })
        }
    })

    $("a.teachers").click(function (evt) {
        evt.preventDefault();
        var td = $(this).parent();
        var tr = $(td).parent();
        var result = $(tr).children();
        var id = $(result[0]).text();
        $.get("getTeachers", {
            id : id
        }, function (data) {
            var tbody = $("div.menu").find("tbody");
            $(tbody).html(data);
        })
        $.get("getNewTeachers", {
            id : id
        }, function (data) {
            var select = $("div.menu").find("select");
            $(select).html(data);
        })
    })
})
$(document).ready(function () {
    $("td.id").hide();
    $("td.open").click(function () {
        var text = $(this).text();
        $(this).html("<input type='text' value='" + text + "'>");
        var input = $(this).children();
        var tr = $(this).parent();
        $(tr).click();
        $(input).click(function (evt) {
            evt.stopPropagation();
        })
        $(input).select();
        $(input).blur(function (evt) {
            var text = $(this).val();
            var td = $(this).parent();
            $(td).html(text);
            var result = $(tr).children();
            $.post("up", {
                id : $(result[0]).text(),
                name : $(result[1]).text(),
                date : $(result[2]).text()
            }, updateResult);
        })
        $(input).keypress(function (evt) {
            if (evt.keyCode == 13) {
                var text = $(this).val();
                var td = $(this).parent();
                $(td).html(text);
                var result = $(tr).children();
                $.post("up", {
                    id: $(result[0]).text(),
                    name: $(result[1]).text(),
                    date: $(result[2]).text()
                }, updateResult);
            }
        })
    })
    function updateResult(data) {
            console.log(data);
    }

    $("td.gender").click(function () {
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
        })
        $(select).focus();
        $(select).change(function (){
            var gender = $(this).val()
            $(td).html(gender);
            var cells = $(tr).children();
            $.post("stg", {
                id : $(cells[0]).text(),
                gender : gender
            }, function (data) {
                console.log(data);
            })
        })
        $(select).blur(function (){
            $(td).html($(this).val());
        })
    })

    $("td.group").click(function () {
        var group = $(this).text();
        var td = $(this);
        var tr = $(this).parent();
        $(this).html("<select></select>");
        var select = $(this).children();
        $.post("getGroups", {
            number : group
        }, function (data) {
            $(select).html(data);
        })
        $(select).click(function (evt) {
            evt.stopPropagation();
        })
        $(select).focus();
        $(select).change(function () {
            var text = $(select).val();
            $(td).html(text);
            var tds = $(tr).children();
            $.post("setGroup", {
                id : $(tds[0]).text(),
                number : text
            }, function (data) {
                console.log(data);
            })
        })
        $(select).blur(function () {
            $(td).html(group);
        })
    })

    $("td.date").click(function () {
        var date = $(this).text();
        var tr = $(this).parent();
        $(this).html("<input type=\"date\" value=\"" + date + "\">");
        var input = $(this).children();
        $(input).click(function (evt) {
            evt.stopPropagation();
        })
        var td = $(this);
        var res = $(tr).children();
        $(input).focus();
        $(input).change(function () {
            var newDate = $(this).val();
            console.log(newDate);
            $.post("setDate", {
                id : $(res[0]).text(),
                newDate : newDate
            }, function (data) {
                console.log(data);
            })
            $(td).html(newDate);
        })
        $(input).blur(function () {
            $(td).html(date);
        })
    })

    $("button").click(function () {
        var param = $("div.search").children();
        var crit = $(param[0]).val();
        var field = $(param[1]).val();
        $.post("selByCrit", {
            crit : crit,
            field : field
        }, function (data) {
            $("tbody").html(data);
            $("td.id").hide();
        })
    })
})

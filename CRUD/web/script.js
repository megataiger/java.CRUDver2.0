$(document).ready(function () {

    $.post("selByCrit", function (data) {

        $("#students").html(data);

        $("td.id").hide();
    });

    $.post("getGroups", function (data) {
        $(".allGroup").html(data);
    });

    $("#students").on("click", ".open", function () {
        var text = $(this).text();
        $(this).html("<input type='text' value='" + text + "'>");
        var input = $(this).children();
        var tr = $(this).parent();

        $(tr).click();

        $(input).click(function (evt) {
            evt.stopPropagation();
        });

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
        });

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
        });
    });

    function updateResult(data) {
            console.log(data);
    }

    $("#students").on("click", ".gender", function () {
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

            $.post("stg", {
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

    $("#students").on("click", ".group", function () {
        var group = $(this).text();
        var td = $(this);
        var tr = $(this).parent();

        $(this).html("<select></select>");
        var select = $(this).children();

        $.post("getGroups", {
            number : group
        }, function (data) {
            $(select).html(data);
        });

        $(select).click(function (evt) {
            evt.stopPropagation();
        });

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
            });
        });
        $(select).blur(function () {
            $(td).html(group);
        });
    });

    $("#students").on("click", ".date", function () {
        var date = $(this).text();
        var tr = $(this).parent();
        $(this).html("<input type=\"date\" value=\"" + date + "\">");
        var input = $(this).children();
        $(input).click(function (evt) {
            evt.stopPropagation();
        });
        var td = $(this);
        var res = $(tr).children();
        $(input).focus();
        $(input).keypress(function (evt) {
            if (evt.keyCode === 13) {
                var newDate = $(this).val();
                console.log(newDate);
                $.post("setDate", {
                    id: $(res[0]).text(),
                    newDate: newDate
                }, function (data) {
                    console.log(data);
                });
                $(td).html(newDate);
            }
        });
        $(input).blur(function () {
            $(td).html(date);
        });
    });

    $("#st").submit(function (evt) {
        evt.preventDefault();
        var form = $(this);
        $.post(
            "selByCrit",
            form.serializeArray(),
            function (data) {
                $("#students").html(data);

                $("td.id").hide();
            });
    });

    $("#addStudent").submit(function (evt) {
        evt.preventDefault();
        $.post(
            "ins",
            $(this).serialize(),
            function () {
                $.post("selByCrit", function (data) {

                    $("#students").html(data);

                    $("td.id").hide();
                });
            }
            );
    });

    $("#students").on("click", "#del", function (evt) {
        evt.preventDefault();
        var id = $(this).attr("href");
        $.get("del", {
            number : id
        }, function () {
            $.post("selByCrit", function (data) {

                $("#students").html(data);

                $("td.id").hide();
            });
        });
    });
});


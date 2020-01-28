$(document).ready(function () {

    var tableStudents = $("#students");

    var table = tableStudents.DataTable({
        language : {
            "processing": "Подождите...",
            "search": "Поиск:",
            "lengthMenu": "Показать _MENU_ записей",
            "info": "Записи с _START_ до _END_ из _TOTAL_ записей",
            "infoEmpty": "Записи с 0 до 0 из 0 записей",
            "infoFiltered": "(отфильтровано из _MAX_ записей)",
            "infoPostFix": "",
            "loadingRecords": "Загрузка записей...",
            "zeroRecords": "Записи отсутствуют.",
            "emptyTable": "В таблице отсутствуют данные",
            "paginate": {
                "first": "Первая",
                "previous": "Предыдущая",
                "next": "Следующая",
                "last": "Последняя"
            },
            "aria": {
                "sortAscending": ": активировать для сортировки столбца по возрастанию",
                "sortDescending": ": активировать для сортировки столбца по убыванию"
            },
            "select": {
                "rows": {
                    "_": "Выбрано записей: %d",
                    "0": "Кликните по записи для выбора",
                    "1": "Выбрана одна запись"
                }
            }
        },
        select: true,
        serverSide: true,
        ajax: {
            url : "selectStudentsByCriterion",
            type : "POST"
        },
        columns : [
            { "className": "id students",
                "name" : "id",
                "data" : "id",
                "title" : "ID"
            },
            { "className": "name students",
                "name" : "name",
                "data" : "name",
                "title" : "Ф.И.О"
            },
            { "className": "birthday students",
                "name" : "birthday",
                "data" : "birthday",
                "title" : "День рождения"
            },
            { "className": "gender students",
                "name" : "gender",
                "data" : "gender",
                "title" : "Пол"

            },
            { "className": "group students",
                "name" : "group",
                "data" : "group",
                "title" : "Группа"

            },
            {
                "className": "students",
                "orderable": false,
                "name" : "delete",
                "data" : "delete",
                "title" : "Действие",
                "render": function (data) {
                    return '<a class="deleteStudent" href="'+ data +'">' +
                        '<img title="Удалить" ' +
                        'src="../image/bascet.png"></a>';
                }
            }
        ]
    });

    var prompt = $("#promptToSearch");

    var promptToAdd = $("#promptToAdd");

    $(promptToAdd).hide();

    tableStudents.on("click", "td.name", function () {
        setNameStudent(this, table);
    });

    tableStudents.on("click", "td.birthday", function () {
        setBirthdayStudent(this, table);
    });

    tableStudents.on("click", "td.gender", function () {
        setGenderStudent(this, table);
    });

    tableStudents.on("click", "td.group", function () {
        setGroupStudent(this, table, prompt);
    });

    tableStudents.on("click", "a.deleteStudent", function (evt) {
        deleteStudent(this, evt, table);
    });

    var addStudent = $("#addStudent");

    addStudent.submit(function (evt) {
        insertStudent(this, evt, table);
    });

    addStudent.on("keyup", "#inputGroupStudent", function () {
        promptToAddNewStudent(this, promptToAdd);
    });

    tableStudents.on('order.dt', function () {
        var order = table.order();
        if ((order[0][1] === 'desc')&&(order[0][0]) === 0) {
            var row = $("#students").children("tbody").children(":first-child");
            $(row).css("background-color", "#00ff14");
            setTimeout(function() {
                $(row).css({
                "background-color" : "#FFFFFF",
                "transition" : "3s"
            })
            }, 1000);
        }
    });

    $("#addGroup").on("click", "td", function () {
        $("#inputGroupStudent").replaceWith("<input id='inputGroupStudent' " +
            "name='groupStudent' type='text' value='" + $(this).text() + "'>");
        $(promptToAdd).hide();
    });
});

function setNameStudent(cell, table) {
    var page = table.page();
    var oldName = $(cell).text();
    $(cell).html("<input class='update' type='text' value='" + oldName + "'>");
    var inputName = $(cell).children();
    var string = $(cell).parent();

    $(inputName).click(function (evt) {
        evt.stopPropagation();
    });

    $(inputName).select();

    $(inputName).blur(function () {
        var fieldName = $(this).parent();
        $(fieldName).html(oldName);
    });

    $(inputName).keypress(function (evt) {
        if (evt.keyCode === 13) {
            var newName = $(this).val();
            var arrayField = $(string).children();

            $.post("../UpdateStudent", {
                idStudent: $(arrayField[0]).text(),
                newNameStudent: newName
            }, function () {
                table.draw('page');
            });
        }
    });
}

function setBirthdayStudent(cell, table) {
    var oldBirthday = $(cell).text();
    var string = $(cell).parent();

    var arrayNumbers = oldBirthday.split('.');

    $(cell).html("<input type=\"date\" value=\"" + arrayNumbers[2] +
        "-" + arrayNumbers[1] + "-" + arrayNumbers[0] + "\">");

    var inputBirthday = $(cell).children();
    $(inputBirthday).click(function (evt) {
        evt.stopPropagation();
    });

    var fieldBirthday = $(cell);
    var arrayField = $(string).children();

    $(inputBirthday).focus();
    $(inputBirthday).keypress(function (evt) {
        if (evt.keyCode === 13) {
            var newBirthday = $(this).val();

            $.post("../UpdateStudent", {
                idStudent : $(arrayField[0]).text(),
                newBirthdayStudent : newBirthday
            }, function () {
                table.draw('page');
            });
        }
    });
    $(inputBirthday).blur(function () {
        $(fieldBirthday).html(oldBirthday);
    });
}

function setGenderStudent(cell, table) {
    var oldGender = $(cell).text();
    var fieldGender = $(cell);
    var string = $(cell).parent();

    $(fieldGender).html("<select></select>");
    var selectGender = $(fieldGender).children();

    $.post("getGender", {
        gender : oldGender
    }, function (data) {
        var arrayGender = JSON.parse(data);
        var options = "";
        for (var i = 0; i < Object.keys(arrayGender).length; i++) {
            var option = "";
            if (arrayGender[i]["selected"]) {
                option = "<option value='" +
                    arrayGender[i]["value"] + "' selected='selected'>" +
                    arrayGender[i]["gender"] + "</option>";
            } else {
                option = "<option value='" +
                    arrayGender[i]["value"] + "'>" +
                    arrayGender[i]["gender"] + "</option>";
            }
            options = options + option;
        }
        $(selectGender).html(options);
    });

    $(selectGender).click(function (evt) {
        evt.stopPropagation();
    });

    $(selectGender).focus();

    $(selectGender).change(function (){
        var newGender = $(this).val();

        var arrayField = $(string).children();

        $.post("../UpdateStudent", {
            idStudent : $(arrayField[0]).text(),
            genderStudent : newGender
        }, function () {
            table.draw('page')
        });
    });

    $(selectGender).blur(function (){
        $(fieldGender).html(oldGender);
    });
}

function setGroupStudent(cell, table, prompt) {
    var oldGroup = $(cell).text();
    var fieldGroup = $(cell);
    var string = $(fieldGroup).parent();

    $(fieldGroup).html("<input id='groupStudent' type='text' size='4' value=" +
        oldGroup + ">");
    var inputGroup = $(fieldGroup).children();

    $(inputGroup).click(function (evt) {
        evt.stopPropagation();
    });

    $(inputGroup).focus();

    $(inputGroup).keyup(function (evt) {
        searchByGroup(this, prompt);
        if (evt.keyCode === 13) {
            var newGroup = $(this).val();
            var arrayField = $(string).children();

            $.post("../UpdateStudent", {
                idStudent : $(arrayField[0]).text(),
                numberGroup : newGroup
            }, function () {
                table.draw('page');
                prompt.hide();
            });
        }
    });

    $(inputGroup).blur(function () {
        $(fieldGroup).html(oldGroup);
        prompt.hide();
    });
}

function insertStudent(form, evt, table) {
    evt.preventDefault();
    $.post(
        "insertStudent",
        $(form).serialize(),
        function () {
            table
                .order( [ 0, 'desc' ] )
                .draw();
        }
    );
}

function deleteStudent(bascet, evt, table) {
    evt.preventDefault();

    var idStudent = $(bascet).attr("href");
    $.get("deleteStudent", {
        idStudent : idStudent
    }, function () {
        var info = table.page.info();
        if (info.pages > 0) {
            if (info.recordsTotal-1 > info.page * info.length) {
                table.draw( 'page' )
            } else {
                table.page( 'previous' ).draw( 'page' )
            }
        }
    });
}

function searchByGroup(field, prompt) {
    if($(field).val() === "") {
        $(prompt).hide();
    } else {
        $(prompt).show();
        var width = $(field).width();
        var x = $(field).offset().left + width;
        var y = $(field).offset().top;
        $(prompt).offset({ top: y, left: x });
        $("#prompt").width(width);
        $.post("../searchGroups", {
            number: $(field).val()
        }, function (data) {
            var arrayGroup = JSON.parse(data);
            var tablePrompt = "";
            for (var i = 0 ; i < Object.keys(arrayGroup).length; i++) {
                var option = "<tr><td>" + arrayGroup[i] + "</td></tr>\n";
                tablePrompt = tablePrompt + option;
            }
            $("#prompt").html(tablePrompt);
        })
    }
}

function promptToAddNewStudent(input, promptToAdd) {
    if($(input).val() === "") {
        $(promptToAdd).hide();
    } else {
        $(promptToAdd).show();
        var x = $(input).offset().left;
        var width = $(input).width();
        var y = $(input).offset().top;
        y = y + $(input).height() + 6;
        $(promptToAdd).offset({ top: y, left: x });
        $("#addGroup").width(width);
        $.post("../searchGroups", {
            number: $(input).val()
        }, function (data) {
            var arrayGroup = JSON.parse(data);
            var tablePrompt = "";
            for (var i = 0 ; i < Object.keys(arrayGroup).length; i++) {
                var option = "<tr><td>" + arrayGroup[i] + "</td></tr>\n";
                tablePrompt = tablePrompt + option;
            }
            $("#addGroup").html(tablePrompt);
        })
    }
}
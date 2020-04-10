$(document).ready(function () {

    var tableStudents = $("#students");

    var table = tableStudents.DataTable({
        language: {
            "url": "/resources/javaScript/Russian.json"
        },
        select: true,
        serverSide: true,
        ajax: {
            url: "students/selectStudentsByCriterion",
            type: "GET"
        },
        columns: [
            {
                "className": "id dt-center",
                "name": "id",
                "data": "id",
                "title": "ID"
            },
            {
                "className": "name dt-center",
                "name": "name",
                "data": "name",
                "title": "Ф.И.О"
            },
            {
                "className": "date dt-center",
                "name": "date",
                "data": "date",
                "title": "Дата рождения"
            },
            {
                "className": "gender dt-center",
                "name": "gender",
                "data": "gender",
                "title": "Пол"

            },
            {
                "className": "group dt-center",
                "name": "group.number",
                "data": "group",
                "title": "Группа"

            },
            {
                "className": "dt-center",
                "orderable": false,
                "name": "delete",
                "data": "delete",
                "title": "Действие",
                "render": function (data) {
                    return '<a class="deleteStudent" href="' + data + '">' +
                        '<img title="Удалить" ' +
                        'src="/resources/image/bascet.png"></a>';
                }
            }
        ]
    });

    tableStudents.on("click", "td.name", function () {
        setNameStudent(this, table);
    });

    tableStudents.on("click", "td.date", function () {
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

    tableStudents.on('order.dt', function () {
        var order = table.order();

        if ((order[0][1] === 'desc') && (order[0][0]) === 0) {

            var row = $("#students")
                .children("tbody")
                .children(":first-child");

            $(row).css("background-color", "#00ff14");

            setTimeout(function () {
                $(row).css({
                    "background-color": "#FFFFFF",
                    "transition": "3s"
                })
            }, 1000);
        }
    });

    $(addStudent).on("click", "#plus", function (evt) {
        viewMenuGroup(evt);
    });

    $('#myModal__close, #myOverlay').click(function () {
        $('#myModal').animate({opacity: 0}, 198,
            function () {
                $(this).css('display', 'none');
                $('#myOverlay').fadeOut(297);
            });
    });

    $(addStudent).on("click", "#my_group", function () {
        $("#groupNum").replaceWith("<img id='plus' src='/resources/image/plus1.png'>")
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
            var id = $(string).children(".id");

            $.post("students/setNameStudent", {
                idStudent: $(id).text(),
                newNameStudent: newName
            }, function (data) {
                table.draw('page');
                createMessage(data);
            });
        }
    });
}

function setBirthdayStudent(cell, table) {
    var oldBirthday = $(cell).text();
    var string = $(cell).parent();

    var arrayNumbers = oldBirthday.split('.');

    var day = arrayNumbers[0];
    var month = arrayNumbers[1];
    var year = arrayNumbers[2];

    $(cell).html("<input type=\"date\" value=\"" + year +
        "-" + month + "-" + day + "\">");

    var inputBirthday = $(cell).children();
    $(inputBirthday).click(function (evt) {
        evt.stopPropagation();
    });

    var fieldBirthday = $(cell);
    var id = $(string).children(".id");

    $(inputBirthday).focus();

    $(inputBirthday).keypress(function (evt) {
        if (evt.keyCode === 13) {
            var newBirthday = $(this).val();

            $.post("students/setBirthdayStudent", {
                idStudent: $(id).text(),
                newBirthdayStudent: newBirthday
            }, function (data) {
                table.draw('page');
                createMessage(data);
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

    $.post("students/getGender", {
        gender: oldGender
    }, function (data) {
        var arrayGender = data;
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
            console.log(arrayGender[i]["gender"]);
            options = options + option;
        }
        $(selectGender).html(options);
    });

    $(selectGender).click(function (evt) {
        evt.stopPropagation();
    });

    $(selectGender).focus();

    $(selectGender).change(function () {
        var newGender = $(this).val();

        var id = $(string).children(".id");

        $.post("students/setGenderStudent", {
            idStudent: $(id).text(),
            genderStudent: newGender
        }, function (data) {
            table.draw('page');
            createMessage(data);
        });
    });

    $(selectGender).blur(function () {
        $(fieldGender).html(oldGender);
    });
}

function setGroupStudent(cell, table, prompt) {
    var fieldGroup = $(cell);
    var string = $(fieldGroup).parent();
    var id = $(string).children(".id");

    $('#myOverlay').fadeIn(297, function () {
        $('#myModal')
            .css('display', 'block')
            .animate({opacity: 1}, 198);
    });
    getTableGroup();

    $("#addGroup").on("click", ".chooseGroup", function (evt) {
        evt.preventDefault();
        var groupId = $(this).attr("href");
        $.post("students/setGroupStudent", {
            idStudent: $(id).text(),
            numberGroup: groupId
        }, function (data) {
            table.draw('page');
            createMessage(data);
        });

        $('#myModal').animate({opacity: 0}, 198,
            function () {
                $(this).css('display', 'none');
                $('#myOverlay').fadeOut(297);
        });
        $("#addGroup").off("click", ".chooseGroup");
    });
}

function insertStudent(form, evt, table) {
    evt.preventDefault();
    $.post(
        "students/insertStudent",
        $(form).serialize(),
        function () {
            table
                .order([0, 'desc'])
                .draw();
        }
    );
}

function deleteStudent(bascet, evt, table) {
    evt.preventDefault();

    var idStudent = $(bascet).attr("href");

    $.get("students/deleteStudent", {
        idStudent: idStudent
    }, function () {
        var info = table.page.info();
        if (info.pages > 0) {
            if (info.recordsTotal - 1 > info.page * info.length) {
                table.draw('page')
            } else {
                table
                    .page('previous')
                    .draw('page')
            }
        }
    });
}

function viewMenuGroup(evt) {
    evt.preventDefault();
    $('#myOverlay').fadeIn(297, function () {
        $('#myModal')
            .css('display', 'block')
            .animate({opacity: 1}, 198);
    });
    getTableGroup();

    $("#addGroup").on("click", ".chooseGroup", function (evt) {
        evt.preventDefault();

        var groupId = $(this).attr("href");
        var cell = $(this).parent();
        var string = $(cell).parent();
        var groupNumber = $(string).children(".number").text();

        $("#plus").replaceWith("<p id='groupNum'><input type='radio' name='group' " +
            "value=" + groupId + " checked>" + groupNumber + "  <img id='my_group' src='/resources/image/close1.png'></p>");
        $('#myModal').animate({opacity: 0}, 198,
            function () {
                $(this).css('display', 'none');
                $('#myOverlay').fadeOut(297);
            });
        $("#addGroup").off("click", ".chooseGroup");
    });
}

function getTableGroup() {
    if (!$.fn.DataTable.isDataTable('#addGroup')) {
        return $("#addGroup").DataTable({
            lengthChange: false,
            info: false,
            scrollY: "200px",
            language: {
                "url": "/resources/javaScript/Russian.json"
            },
            serverSide: true,
            ajax: {
                url: "groups/selectGroups",
                type: "GET",
                data: function (d) {
                    d.teacherId = $("#nameChooseTeacher").attr("class");
                }
            },
            columns: [
                {
                    "className": "number dt-center",
                    "name": "number",
                    "data": "number",
                    "title": "Номер"

                },
                {
                    "orderable": false,
                    "className": "choose dt-center",
                    "name": "id",
                    "data": "id",
                    "title": "Действие",
                    "render": function (data) {
                        return '<a class="chooseGroup" href="' + data + '">' +
                            '<img title="Выбрать" ' +
                            'src="/resources/image/tick.png"></a>';
                    }
                }
            ]
        })
    } else {
        return false;
    }
}

function createMessage(data) {
    if (data["response"]) {
        $("body").append("<div id='response'>" +
            data["message"] +
            "</div>");

        var response = $("#response");
        setTimeout(function () {
            response.remove();
        }, 1000);
} else {
        $("body").append("<div id='response'>" +
            data["message"] +
            "</div>");

        var response = $("#response");
        setTimeout(function () {
            response.remove();
        }, 1000);
    }
}
$(document).ready(function () {

    var tableTeachers = getTeachersTable();

    $("#groups").hide();

    var tableTeachersListner = $("#teachers");

    var tableGroupsTeachersListner = $("#tableGroup");

    $(tableTeachersListner).on("click", "td.nameTeacher", function () {
        setNameTeacher(this);
    });

    $(tableTeachersListner).on("click", "td.birthdayTeacher", function () {
        setBirthdayTeacher(this, tableTeachers);
    });

    $(tableTeachersListner).on("click", "td.genderTeacher", function () {
        setGenderTeacher(this, tableTeachers);
    });

    $(tableTeachersListner).on("click", "a.deleteTeacher", function (evt) {
        deleteTeacher(this, evt, tableTeachers);
    });

    $("#addTeacher").submit(function (evt) {
        insertTeacher(tableTeachers, evt, this);
    });

    $(tableTeachersListner).on("click", "a.listOfGroup", function (evt) {
        viewMenuGroups(evt, this);
    });

    $("#addGroup").click(function () {
        clickToAddGroupsOfTeacher(this);
    });

    $("#viewGroup").click(function () {
        clickToViewGroupsOfTeacher(this);
    });

    $(tableGroupsTeachersListner).on("click", ".deleteGroup", function (evt) {
        deleteGroupOfTeacher(evt, tableGroupsTeachersListner, this);
    });

    $(tableGroupsTeachersListner).on("click", ".addGroup", function (evt) {
        addGroupForTeacher(evt, tableGroupsTeachersListner, this);
    });

    $("#close").click(function () {
        var groups = $(this).parent();
        $(groups).hide();
    });

    tableTeachersListner.on('order.dt', function () {
        var order = tableTeachers.order();
        if ((order[0][1] === 'desc') && (order[0][0]) === 0) {
            var row = $("#teachers").children("tbody").children(":first-child");
            $(row).css("background-color", "#00ff14");
            setTimeout(function () {
                $(row).css({
                    "background-color": "#FFFFFF",
                    "transition": "3s"
                })
            }, 1000);
        }
    });
});

function setNameTeacher(cell) {
    var fieldName = $(cell);
    var oldName = $(fieldName).text();
    var string = $(fieldName).parent();
    $(fieldName).html("<input id='newNameTeacher' value='" + oldName + "' type='text'>");
    var inputName = $("#newNameTeacher");
    $(inputName).click(function (evt) {
        evt.stopPropagation();
    });
    $(inputName).select();
    $(inputName).keypress(function (evt) {
        if (evt.keyCode === 13) {
            var newName = $(inputName).val();
            var teacherId = $(string).attr("id");
            $.post(
                "teachers/setNameTeacher", {
                    nameTeacher: newName,
                    teacherId: teacherId
                }, function () {
                    fieldName.html(newName);
                }
            );
        }
    });

    $(inputName).blur(function () {
        $(fieldName).html(oldName);
    })
}

function setBirthdayTeacher(cell, table) {
    var fieldBirthday = $(cell);
    var oldBirthday = $(fieldBirthday).text();
    var string = $(fieldBirthday).parent();

    var arrayNumbers = oldBirthday.split('.');

    var day = arrayNumbers[0];
    var month = arrayNumbers[1];
    var year = arrayNumbers[2];

    $(fieldBirthday).html("<input id='newDateTeacher' " +
        "type=\"date\" value=\"" + year +
        "-" + month + "-" + day + "\">");

    var inputBirthday = $("#newDateTeacher");
    $(inputBirthday).click(function (evt) {
        evt.stopPropagation();
    });
    $(inputBirthday).select();
    $(inputBirthday).keypress(function (evt) {
        if (evt.keyCode === 13) {
            var newBirthday = $(inputBirthday).val();
            var teacherId = $(string).attr("id");
            $.post(
                "teachers/setBirthdayTeacher", {
                    newBirthday: newBirthday,
                    teacherId: teacherId
                }, function () {
                    table.draw();
                }
            );
        }
    });

    $(inputBirthday).blur(function () {
        $(fieldBirthday).html(oldBirthday);
    })
}

function setGenderTeacher(cell, table) {
    var oldGender = $(cell).text();
    var fieldGender = $(cell);
    var string = $(fieldGender).parent();

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

        $.post("teachers/setGenderTeacher", {
            teacherId: $(string).attr("id"),
            newGender: newGender
        }, function () {
            table.draw();
        });
    });

    $(selectGender).blur(function () {
        $(fieldGender).html(oldGender);
    });
}

function deleteTeacher(bascet, evt, table) {
    evt.preventDefault();

    var fieldOfOperations = $(bascet).parent();
    var string = $(fieldOfOperations).parent();
    var teacherId = $(string).attr("id");

    $.get("teachers/deleteTeacher", {
        teacherId: teacherId
    }, function () {
        var info = table.page.info();
        if (info.pages > 0) {
            if (info.recordsTotal - 1 > info.page * info.length) {
                table.draw('page')
            } else {
                table.page('previous').draw('page')
            }
        }
    });
}

function insertTeacher(table, evt, form) {
    evt.preventDefault();

    $.post("teachers/insertTeacher",
        $(form).serialize(),
        function () {
            table
                .order([0, 'desc'])
                .draw();
        });
}

function viewMenuGroups(evt, cell) {
    evt.preventDefault();

    var fieldOfOperations = $(cell).parent();
    var string = $(fieldOfOperations).parent();
    var teacherId = $(string).children(".id").text();
    var nameTeacher = $(string).children(".nameTeacher").text();

    var nameChooseTeacher = $("#nameChooseTeacher");

    nameChooseTeacher.attr("class", teacherId);
    nameChooseTeacher.text(nameTeacher);

    $("#viewGroup").css({"border": "0px"});
    $("#addGroup").css({"border": "1px solid black"});

    if (!$.fn.DataTable.isDataTable('#tableGroup')) {
        getTeacherGroupsTable();
    } else if ($('#tableGroup').DataTable().ajax.url() === "../getGroups") {
        $('#tableGroup')
            .DataTable()
            .draw();
    } else {
        $("#tableGroup")
            .DataTable()
            .destroy();
        getTeacherGroupsTable();
    }

    $("#menuGroupsAndAddTeacher").show();
    $("#groups").show();
    $("#searchGroup").attr("class", "view");
}

function clickToAddGroupsOfTeacher(button) {
    $(button).css({"border": "0px"});
    $("#viewGroup").css({"border": "1px solid black"});

    if (!$.fn.DataTable.isDataTable('#tableGroup')) {
        getTeacherNewGroupsTable();
    } else if ($('#tableGroup').DataTable().ajax.url() === "getNewGroupsTeacher") {
        $('#tableGroup')
            .DataTable()
            .draw();
    } else {
        $("#tableGroup")
            .DataTable()
            .destroy();
        getTeacherNewGroupsTable();
    }
}

function clickToViewGroupsOfTeacher(button) {
    $(button).css({"border": "0px"});
    $("#addGroup").css({"border": "1px solid black"});

    if (!$.fn.DataTable.isDataTable('#tableGroup')) {
        getTeacherGroupsTable();
    } else if ($('#tableGroup').DataTable().ajax.url() === "getGroupsTeacher") {
        $('#tableGroup')
            .DataTable()
            .draw();
    } else {
        $("#tableGroup")
            .DataTable()
            .destroy();
        getTeacherGroupsTable();
    }
}

function deleteGroupOfTeacher(evt, table, cell) {
    evt.preventDefault();

    var groupId = $(cell).attr("href");

    var teacherId = $("#nameChooseTeacher").attr("class");

    $.get("teachers/deleteGroupForTeacher", {
        teacherId: teacherId,
        groupId: groupId
    }, function () {
        var tableTeacher = table.DataTable();
        var info = tableTeacher.page.info();
        if (info.pages > 0) {
            if (info.recordsTotal - 1 > info.page * info.length) {
                tableTeacher.draw('page')
            } else {
                tableTeacher
                    .page('previous')
                    .draw('page')
            }
        }
    });
}

function addGroupForTeacher(evt, table, cell) {
    evt.preventDefault();

    var groupId = $(cell).attr("href");

    var teacherId = $("#nameChooseTeacher").attr("class");

    $.get("teachers/addGroupForTeacher", {
        teacherId: teacherId,
        groupId: groupId
    }, function () {
        var tableTeacher = table.DataTable();
        var info = tableTeacher.page.info();
        if (info.pages > 0) {
            if (info.recordsTotal - 1 > info.page * info.length) {
                tableTeacher.draw('page')
            } else {
                tableTeacher
                    .page('previous')
                    .draw('page')
            }
        }
    });
}

function getTeachersTable() {
    return $("#teachers").DataTable({
        language: {
            "url": "/resources/javaScript/Russian.json"
        },
        serverSide: true,
        ajax: {
            url: "teachers/selectTeachers",
            type: "GET"
        },
        columns: [
            {
                "className": "id",
                "name": "id",
                "data": "id",
                "title": "ID"

            },
            {
                "className": "nameTeacher",
                "name": "name",
                "data": "name",
                "title": "ФИО"
            },
            {
                "className": "birthdayTeacher",
                "name": "date",
                "data": "date",
                "title": "Дата рождения"
            },
            {
                "className": "genderTeacher",
                "name": "gender",
                "data": "gender",
                "title": "Пол"
            },
            {
                "orderable": false,
                "name": "delete",
                "data": "delete",
                "title": "Действие",
                "render": function (data) {
                    return '<a class="deleteTeacher" href="' + data + '">' +
                        '<img title="Удалить" ' +
                        'src="/resources/image/bascet.png"></a>' +
                        '<a class="listOfGroup" href="' + data + '">' +
                        '<img src="/resources/image/list.png" title="Список групп"></a>';
                }
            }
        ]
    });
}

function getTeacherGroupsTable() {
    return $("#tableGroup").DataTable({
        language: {
            "url": "/resources/javaScript/Russian.json"
        },
        serverSide: true,
        ajax: {
            url: "teachers/getGroupsTeacher",
            type: "GET",
            data: function (d) {
                d.teacherId = $("#nameChooseTeacher").attr("class");
            }
        },
        columns: [
            {
                "className": "number",
                "name": "number",
                "data": "number",
                "title": "Номер"

            },
            {
                "orderable": false,
                "name": "delete",
                "data": "id",
                "title": "Действие",
                "render": function (data) {
                    return '<a class="deleteGroup" href="' + data + '">' +
                        '<img title="Удалить" ' +
                        'src="/resources/image/bascet.png"></a>';
                }
            }
        ]
    });
}

function getTeacherNewGroupsTable() {
    return $("#tableGroup").DataTable({
        language: {
            "url": "/resources/javaScript/Russian.json"
        },
        serverSide: true,
        ajax: {
            url: "teachers/getNewGroupsTeacher",
            type: "GET",
            data: function (d) {
                d.teacherId = $("#nameChooseTeacher").attr("class");
            }
        },
        columns: [
            {
                "className": "number",
                "name": "number",
                "data": "number",
                "title": "Номер"

            },
            {
                "orderable": false,
                "name": "delete",
                "data": "id",
                "title": "Действие",
                "render": function (data) {
                    return '<a class="addGroup" href="' + data + '">' +
                        '<img title="Добавить" ' +
                        'src="/resources/image/plus.png"></a>';
                }
            }
        ]
    });
}
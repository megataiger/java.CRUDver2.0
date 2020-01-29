$(document).ready(function () {
    var tableGroup = $("#groups");

    var table = tableGroup.DataTable({
        language: {
            "url": "../javaScript/Russian.json"
        },
        serverSide: true,
        ajax: {
            url: "selectAllGroup",
            type: "POST"
        },
        columns: [
            {
                "className": "id groups",
                "name": "id",
                "data": "id",
                "title": "ID"
            },
            {
                "className": "number groups",
                "name": "number",
                "data": "number",
                "title": "Номер"
            },
            {
                "orderable": false,
                "className": "lists groups",
                "name": "students",
                "data": "id",
                "title": "Списки",
                "render": function (data) {
                    return '<a class="students" href="' + data + '">' +
                        'Студенты</a><br><a class="teachers" href="' + data + '">' +
                        'Преподавтели</a>';
                }
            },
            {
                "orderable": false,
                "className": "delete groups",
                "name": "delete",
                "data": "id",
                "title": "Действие",
                "render": function (data) {
                    return '<a class="deleteGroup" href="' + data + '">' +
                        '<img title="Удалить" ' +
                        'src="../image/bascet.png"></a>';
                }
            }
        ]
    });

    $("#menuTeachersOfGroup").hide();

    tableGroup.on("click", "td.number", function () {
        setNumberGroup(this, table);
    });

    tableGroup.on("click", "a.deleteGroup", function (evt) {
        deleteGroup(this, evt, table);
    });

    $("#addGroup").keydown(function (evt) {
        insertGroup(this, evt, table);
    });

    tableGroup.on("click", "a.teachers", function (evt) {
        viewTeachersMenu(this, evt);
    });

    tableGroup.on("click", "a.students", function (evt) {
        viewStudentsMenu(this, evt);
    });

    $("#viewTeachers").on("click", function () {
        clickToViewTeachers(this);
    });

    $("#addTeachers").on("click", function () {
        clickToAddTeachers(this);
    });

    var tableTeachers = $("#teachersOfGroup");

    tableTeachers.on("click", ".addTeacher", function (evt) {
        addTeacherForGroup(this, evt, tableTeachers);
    });

    tableTeachers.on("click", ".deleteTeacher", function (evt) {
        deleteTeacherForGroup(this, evt, tableTeachers);
    });

    tableGroup.on('order.dt', function () {
        var order = table.order();
        if ((order[0][1] === 'desc') && (order[0][0]) === 0) {
            var row = $("#groups").children("tbody").children(":first-child");
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

function insertGroup(inputNumber, evt, table) {
    if (evt.keyCode === 13) {

        $.post("insertGroup", {
            numberGroup: $(inputNumber).val()
        }, function () {
            table
                .order([0, 'desc'])
                .draw();
        });
    }
}

function deleteGroup(bascet, evt, table) {
    evt.preventDefault();

    var fieldDelete = $(bascet).parent();
    var string = $(fieldDelete).parent();
    var idGroup = $(string)
        .children(".id")
        .text();

    $.post("deleteGroup", {
        idGroup: idGroup
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
    })
}

function setNumberGroup(cell, table) {
    var oldNumber = $(cell).text();
    var string = $(cell).parent();
    var fieldNumber = $(cell);

    $(fieldNumber).html("<input class='number' type='text' value='"
        + oldNumber + "' size='4'>");
    var inputNumber = $(fieldNumber).children();

    $(inputNumber).click(function (evt) {
        evt.stopPropagation();
    });

    $(inputNumber).select();

    $(inputNumber).blur(function () {
        $(fieldNumber).html(oldNumber);
    });

    $(inputNumber).keypress(function (evt) {
        if (evt.keyCode === 13) {
            var newNumber = $(this).val();

            $.post("upGroup", {
                idGroup: $(string).children(".id").text(),
                numberGroup: newNumber
            }, function () {
                table.draw('page');
            })
        }
    })
}

function viewTeachersMenu(require, evt) {
    evt.preventDefault();
    var fieldTeachers = $(require).parent();
    var string = $(fieldTeachers).parent();
    var numberGroup = $(string)
        .children(".number")
        .text();

    $("#numberGroup").html(numberGroup);

    $("#viewTeachers").click();

    $("#menuTeachersOfGroup").show();

    $("#menuStudentsOfGroup").hide();
}

function viewStudentsMenu(require, evt) {
    evt.preventDefault();
    var fieldStudents = $(require).parent();
    var string = $(fieldStudents).parent();
    var numberGroup = $(string)
        .children(".number")
        .text();

    $("#numberGroup").html(numberGroup);

    $("#menuStudentsOfGroup").show();

    $("#menuTeachersOfGroup").hide();

    if (!$.fn.DataTable.isDataTable('#studentsOfGroup')) {
        getStudentOfGroup();
    } else {
        $('#studentsOfGroup')
            .DataTable()
            .draw();
    }
}

function clickToViewTeachers(button) {
    var numberGroup = $("#numberGroup").text();

    $(button).css({"border": "0px"});
    $("#addTeachers").css({"border": "1px solid black"});

    if (!$.fn.DataTable.isDataTable('#teachersOfGroup')) {
        getTeachersOfGroup();
    } else if ($('#teachersOfGroup').DataTable().ajax.url() === "../getTeachers") {
        $('#teachersOfGroup')
            .DataTable()
            .draw();
    } else {
        $("#teachersOfGroup")
            .DataTable()
            .destroy();
        getTeachersOfGroup();
    }
}

function clickToAddTeachers(button) {
    var numberGroup = $("#numberGroup").text();

    $(button).css({"border": "0px"});
    $("#viewTeachers").css({"border": "1px solid black"});

    if (!$.fn.DataTable.isDataTable('#teachersOfGroup')) {
        getNewTeachersOfGroup();
    } else if ($('#teachersOfGroup').DataTable().ajax.url() === "../getNewTeachers") {
        $('#teachersOfGroup')
            .DataTable()
            .draw();
    } else {
        $("#teachersOfGroup")
            .DataTable()
            .destroy();
        getNewTeachersOfGroup();
    }
}

function addTeacherForGroup(require, evt, table) {
    evt.preventDefault();
    var idTeacher = $(require).attr("href");
    var numberGroup = $("#numberGroup").text();

    $.post("../addTeacherForGroup", {
        idTeacher: idTeacher,
        numberGroup: numberGroup
    }, function () {
        var tableGroup = table.DataTable();
        var info = tableGroup.page.info();
        if (info.pages > 0) {
            if (info.recordsTotal - 1 > info.page * info.length) {
                tableGroup.draw('page')
            } else {
                tableGroup
                    .page('previous')
                    .draw('page')
            }
        }
    })
}

function deleteTeacherForGroup(require, evt, table) {
    evt.preventDefault();
    var idTeacher = $(require).attr("href");
    var numberGroup = $("#numberGroup").text();

    $.post("../deleteTeacherForGroup", {
        idTeacher: idTeacher,
        numberGroup: numberGroup
    }, function () {
        var tableGroup = table.DataTable();
        var info = tableGroup.page.info();
        if (info.pages > 0) {
            if (info.recordsTotal - 1 > info.page * info.length) {
                tableGroup.draw('page')
            } else {
                tableGroup
                    .page('previous')
                    .draw('page')
            }
        }
    })
}

function getTeachersOfGroup() {
    return $("#teachersOfGroup").DataTable({
        language: {
            "url": "../javaScript/Russian.json"
        },
        serverSide: true,
        ajax: {
            url: "../getTeachers",
            type: "GET",
            data: function (d) {
                d.numberGroup = $("#numberGroup").text();
            }
        },
        columns: [
            {
                "className": "name teacher",
                "name": "name",
                "data": "name",
                "title": "Ф.И.О"
            },
            {
                "className": "birthday teacher",
                "name": "birthday",
                "data": "birthday",
                "title": "Дата рождения"
            },
            {
                "orderable": false,
                "className": "delete teacher",
                "name": "students",
                "data": "id",
                "title": "Действие",
                "render": function (data) {
                    return '<a class="deleteTeacher" href="' + data + '">' +
                        '<img src="../image/bascet.png"></a>';
                }
            }
        ]
    });
}

function getNewTeachersOfGroup() {
    return $("#teachersOfGroup").DataTable({
        language: {
            "url": "../javaScript/Russian.json"
        },
        serverSide: true,
        ajax: {
            url: "../getNewTeachers",
            type: "GET",
            data: function (d) {
                d.numberGroup = $("#numberGroup").text();
            }
        },
        columns: [
            {
                "className": "name teacher",
                "name": "name",
                "data": "name",
                "title": "Ф.И.О"
            },
            {
                "className": "birthday teacher",
                "name": "birthday",
                "data": "birthday",
                "title": "Дата рождения"
            },
            {
                "orderable": false,
                "className": "add teacher",
                "name": "students",
                "data": "id",
                "title": "Действие",
                "render": function (data) {
                    return '<a class="addTeacher" href="' + data + '">' +
                        '<img src="../image/plus.png"></a>';
                }
            }
        ]
    });
}

function getStudentOfGroup() {
    return $("#studentsOfGroup").DataTable({
        language: {
            "url": "../javaScript/Russian.json"
        },
        serverSide: true,
        ajax: {
            url: "../selectStudent",
            type: "GET",
            data: function (d) {
                d.numberGroup = $("#numberGroup").text();
            }
        },
        columns: [
            {
                "className": "name student",
                "name": "name",
                "data": "name",
                "title": "Ф.И.О"
            },
            {
                "className": "birthday student",
                "name": "birthday",
                "data": "birthday",
                "title": "Дата рождения"
            }
        ]
    });
}
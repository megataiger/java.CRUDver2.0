$(document).ready(function () {
    var tableGroup = $("#groups");

    var table = tableGroup.DataTable({
        language: {
            "url": "/resources/javaScript/Russian.json"
        },
        serverSide: true,
        ajax: {
            url: "groups/selectGroups",
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
                "className": "number dt-center",
                "name": "number",
                "data": "number",
                "title": "Номер"
            },
            {
                "orderable": false,
                "className": "lists dt-center",
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
                "className": "delete dt-center",
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

        $.post("groups/insertGroup", {
            number: $(inputNumber).val()
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
    var groupId = $(string)
        .children(".id")
        .text();

    $.post("groups/deleteGroup", {
        groupId: groupId
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
    var fieldNumber = $(cell);
    var string = $(cell).parent();
    var groupId = $(string)
        .children(".id")
        .text();

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

            $.post("groups/setNumberGroup", {
                groupId: groupId,
                numberGroup: newNumber
            }, function (data) {
                table.draw('page');
                createMessage(data)
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
    var groupId = $(string)
        .children(".id")
        .text();

    $("#numberGroup").html(numberGroup).attr("class", groupId);

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

    var groupid = $(string).children(".id").text();

    $("#numberGroup").html(numberGroup).attr("class", groupid);

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
    $(button).css({"border": "0px"});
    $("#addTeachers").css({"border": "1px solid black"});

    if (!$.fn.DataTable.isDataTable('#teachersOfGroup')) {
        getTeachersOfGroup();
    } else if ($('#teachersOfGroup').DataTable().ajax.url() === "groups/getTeachersGroup") {
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
    $(button).css({"border": "0px"});
    $("#viewTeachers").css({"border": "1px solid black"});

    if (!$.fn.DataTable.isDataTable('#teachersOfGroup')) {
        getNewTeachersOfGroup();
    } else if ($('#teachersOfGroup').DataTable().ajax.url() === "groups/getNewTeachersGroup") {
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
    var teacherId = $(require).attr("href");
    var groupId = $("#numberGroup").attr("class");

    $.post("groups/addTeacherForGroup", {
        teacherId: teacherId,
        groupId: groupId
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
    var teacherId = $(require).attr("href");
    var groupId = $("#numberGroup").attr("class");

    $.post("groups/deleteTeacherForGroup", {
        teacherId: teacherId,
        groupId: groupId
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
            "url": "/resources/javaScript/Russian.json"
        },
        serverSide: true,
        ajax: {
            url: "groups/getTeachersGroup",
            type: "GET",
            data: function (d) {
                d.groupId = $("#numberGroup").attr("class");
            }
        },
        columns: [
            {
                "className": "name dt-center",
                "name": "name",
                "data": "name",
                "title": "Ф.И.О"
            },
            {
                "className": "birthday dt-center",
                "name": "date",
                "data": "date",
                "title": "Дата рождения"
            },
            {
                "orderable": false,
                "className": "delete dt-center",
                "name": "students",
                "data": "id",
                "title": "Действие",
                "render": function (data) {
                    return '<a class="deleteTeacher" href="' + data + '">' +
                        '<img src="/resources/image/bascet.png"></a>';
                }
            }
        ]
    });
}

function getNewTeachersOfGroup() {
    return $("#teachersOfGroup").DataTable({
        language: {
            "url": "/resources/javaScript/Russian.json"
        },
        serverSide: true,
        ajax: {
            url: "groups/getNewTeachersGroup",
            type: "GET",
            data: function (d) {
                d.groupId = $("#numberGroup").attr("class");
            }
        },
        columns: [
            {
                "className": "name dt-center",
                "name": "name",
                "data": "name",
                "title": "Ф.И.О"
            },
            {
                "className": "birthday dt-center",
                "name": "date",
                "data": "date",
                "title": "Дата рождения"
            },
            {
                "orderable": false,
                "className": "add dt-center",
                "name": "students",
                "data": "id",
                "title": "Действие",
                "render": function (data) {
                    return '<a class="addTeacher" href="' + data + '">' +
                        '<img src="/resources/image/plus.png"></a>';
                }
            }
        ]
    });
}

function getStudentOfGroup() {
    return $("#studentsOfGroup").DataTable({
        language: {
            "url": "/resources/javaScript/Russian.json"
        },
        serverSide: true,
        ajax: {
            url: "groups/selectStudentGroup",
            type: "GET",
            data: function (d) {
                d.groupId = $("#numberGroup").attr("class");
            }
        },
        columns: [
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
            }
        ]
    });
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
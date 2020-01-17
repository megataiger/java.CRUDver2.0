var language = {
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
};

$(document).ready(function () {
    var tableGroup = $("#groups");

    var table = tableGroup.DataTable({
        language: language,
        serverSide: true,
        ajax: {
            url: "selectAllGroup",
            type: "POST"
        },
        columns: [
            {
                "orderable": false,
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

});

function insertGroup(inputNumber, evt, table) {
    if (evt.keyCode === 13) {

        $.post("insertGroup", {
            numberGroup: $(inputNumber).val()
        }, function () {
            table.draw('page');
        });
    }
}

function deleteGroup(bascet, evt, table) {
    evt.preventDefault();

    var fieldDelete = $(bascet).parent();
    var string = $(fieldDelete).parent();
    var idGroup = $(string).children(".id").text();

    $.post("deleteGroup", {
        idGroup: idGroup
    }, function () {
        table.draw('page');
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
    var numberGroup = $(string).children(".number").text();

    $("#numberGroup").html(numberGroup);

    $("#viewTeachers").click();

    $("#menuTeachersOfGroup").show();

    $("#menuStudentsOfGroup").hide();
}

function viewStudentsMenu(require, evt) {
    evt.preventDefault();
    var fieldStudents = $(require).parent();
    var string = $(fieldStudents).parent();
    var numberGroup = $(string).children(".number").text();

    $("#numberGroup").html(numberGroup);

    $("#menuStudentsOfGroup").show();

    $("#menuTeachersOfGroup").hide();

    if (!$.fn.DataTable.isDataTable('#studentsOfGroup')) {
        getStudentOfGroup();
    } else {
        $('#studentsOfGroup').DataTable().draw();
    }
}

function clickToViewTeachers(button) {
    var numberGroup = $("#numberGroup").text();

    $(button).css({"border": "0px"});
    $("#addTeachers").css({"border": "1px solid black"});

    if (!$.fn.DataTable.isDataTable('#teachersOfGroup')) {
        getTeachersOfGroup();
    } else if ($('#teachersOfGroup').DataTable().ajax.url() === "../getTeachers") {
        $('#teachersOfGroup').DataTable().draw();
    } else {
        $("#teachersOfGroup").DataTable().destroy();
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
        $('#teachersOfGroup').DataTable().draw();
    } else {
        $("#teachersOfGroup").DataTable().destroy();
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
        table.DataTable().draw('page');
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
        table.DataTable().draw('page');
    })
}

function getTeachersOfGroup() {
    return $("#teachersOfGroup").DataTable({
        language: language,
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
                "title": "Списки",
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
        language: language,
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
                "title": "Списки",
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
        language: language,
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
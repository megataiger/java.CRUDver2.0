var languege = {
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
    "paginate":
        {
            "first": "Первая",
            "previous": "Предыдущая",
            "next": "Следующая",
            "last": "Последняя"
        },
    "aria":
        {
            "sortAscending": ": активировать для сортировки столбца по возрастанию",
            "sortDescending": ": активировать для сортировки столбца по убыванию"
        },
    "select":
        {
            "rows":
                {
                    "_": "Выбрано записей: %d",
                    "0": "Кликните по записи для выбора",
                    "1": "Выбрана одна запись"
                }
        }
    };

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

    $("#teachers").on('order.dt', function ( e, settings, order) {
        var order = tableTeachers.order();
        if ((order[0][1] === 'desc')&&(order[0][0]) === 0) {
            var row = $("#teachers").children("tbody").children(":first-child");
            $(row).css("background-color", "#00ff14");
            setTimeout(function() {
                $(row).css({
                    "background-color" : "#FFFFFF",
                    "transition" : "3s"
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
            var idTeacher = $(string).attr("id");
            $.post(
                "../updateTeacher", {
                    nameTeacher : newName,
                    idTeacher : idTeacher
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

    $(fieldBirthday).html("<input id='newDateTeacher' " +
        "type=\"date\" value=\"" + arrayNumbers[2] +
        "-" + arrayNumbers[1] + "-" + arrayNumbers[0] + "\">");

    var inputBirthday = $("#newDateTeacher");
    $(inputBirthday).click(function (evt) {
        evt.stopPropagation();
    });
    $(inputBirthday).select();
    $(inputBirthday).keypress(function (evt) {
        if (evt.keyCode === 13) {
            var newBirthday = $(inputBirthday).val();
            var idTeacher = $(string).attr("id");
            $.post(
                "../updateTeacher", {
                    newBirthday : newBirthday,
                    idTeacher : idTeacher
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

        $.post("../updateTeacher", {
            idTeacher : $(string).attr("id"),
            newGender : newGender
        }, function () {
            table.draw();
        });
    });

    $(selectGender).blur(function (){
        $(fieldGender).html(oldGender);
    });
}

function deleteTeacher(bascet, evt, table) {
    evt.preventDefault();

    var fieldOfOperations = $(bascet).parent();
    var string = $(fieldOfOperations).parent();
    var idTeacher = $(string).attr("id");

    $.get("../deleteTeacher", {
        idTeacher : idTeacher
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

function insertTeacher(table, evt, form) {
    evt.preventDefault();

    $.post("../insertTeacher",
        $(form).serialize(),
        function () {
            table
                .order( [ 0, 'desc' ] )
                .draw();
        });
}

function viewMenuGroups(evt, cell) {
    evt.preventDefault();

    var fieldOfOperations = $(cell).parent();
    var string = $(fieldOfOperations).parent();
    var idTeacher = $(string).children(".id").text();
    var nameTeacher = $(string).children(".nameTeacher").text();

    $("#nameChooseTeacher").attr("class", idTeacher);
    $("#nameChooseTeacher").text(nameTeacher);

    $("#viewGroup").css({"border" : "0px"});
    $("#addGroup").css({"border" : "1px solid black"});

    if (!$.fn.DataTable.isDataTable('#tableGroup')) {
        getTeacherGroupsTable();
    } else if ($('#tableGroup').DataTable().ajax.url() === "../getGroups") {
        $('#tableGroup').DataTable().draw();
    } else {
        $("#tableGroup").DataTable().destroy();
        getTeacherGroupsTable();
    }

    $("#menuGroupsAndAddTeacher").show();
    $("#groups").show();
    $("#searchGroup").attr("class", "view");
}

function clickToAddGroupsOfTeacher(button) {
    $(button).css({"border" : "0px"});
    $("#viewGroup").css({"border" : "1px solid black"});

    if (!$.fn.DataTable.isDataTable('#tableGroup')) {
        getTeacherNewGroupsTable();
    } else if ($('#tableGroup').DataTable().ajax.url() === "../getNewGroups") {
        $('#tableGroup').DataTable().draw();
    } else {
        $("#tableGroup").DataTable().destroy();
        getTeacherNewGroupsTable();
    }
}

function clickToViewGroupsOfTeacher(button) {
    $(button).css({"border" : "0px"});
    $("#addGroup").css({"border" : "1px solid black"});

    if (!$.fn.DataTable.isDataTable('#tableGroup')) {
        getTeacherGroupsTable();
    } else if ($('#tableGroup').DataTable().ajax.url() === "../getGroups") {
        $('#tableGroup').DataTable().draw();
    } else {
        $("#tableGroup").DataTable().destroy();
        getTeacherGroupsTable();
    }
}

function deleteGroupOfTeacher(evt, table, cell) {
    evt.preventDefault();

    var fieldDeleteGroup = $(cell).parent();
    var stringGroup = $(fieldDeleteGroup).parent();
    var numberGroup = $(stringGroup).children(".number").text();;

    var idTeacher = $("#nameChooseTeacher").attr("class");

    $.get("../deleteGroupForTeacher", {
        idTeacher : idTeacher,
        numberGroup : numberGroup
    }, function () {
        var tableTeacher = table.DataTable();
        var info = tableTeacher.page.info();
        if (info.pages > 0) {
            if (info.recordsTotal-1 > info.page * info.length) {
                tableTeacher.draw( 'page' )
            } else {
                tableTeacher.page( 'previous' ).draw( 'page' )
            }
        }
    });
}

function addGroupForTeacher(evt, table, cell) {
    evt.preventDefault();

    var fieldAddGroup = $(cell).parent();
    var stringGroup = $(fieldAddGroup).parent();
    var numberGroup = $(stringGroup).children(".number").text();

    console.log($(fieldAddGroup).text());

    var idTeacher = $("#nameChooseTeacher").attr("class");

    $.get("../addGroupForTeacher", {
        idTeacher : idTeacher,
        numberGroup : numberGroup
    }, function () {
        var tableTeacher = table.DataTable();
        var info = tableTeacher.page.info();
        if (info.pages > 0) {
            if (info.recordsTotal-1 > info.page * info.length) {
                tableTeacher.draw( 'page' )
            } else {
                tableTeacher.page( 'previous' ).draw( 'page' )
            }
        }
    });
}

function getTeachersTable() {
    return $("#teachers").DataTable({
        language : languege,
        serverSide: true,
        ajax: {
            url : "../selectTeachers",
            type : "POST"
        },
        columns : [
            { "className": "id",
                "name" : "id",
                "data" : "id",
                "title" : "ID"

            },
            { "className": "nameTeacher",
                "name" : "name",
                "data" : "name",
                "title" : "ФИО"
            },
            { "className": "birthdayTeacher",
                "name" : "birthday",
                "data" : "birthday",
                "title" : "Дата рождения"
            },
            { "className": "genderTeacher",
                "name" : "gender",
                "data" : "gender",
                "title" : "Пол"
            },
            {
                "orderable": false,
                "name" : "delete",
                "data" : "delete",
                "title" : "Действие",
                "render": function (data) {
                    return '<a class="deleteTeacher" href="' + data + '">' +
                        '<img title="Удалить" ' +
                        'src="../image/bascet.png"></a>' +
                        '<a class="listOfGroup" href="' + data + '">' +
                        '<img src="../image/list.png" title="Список групп"></a>';
                }
            }
        ]
    });
}

function getTeacherGroupsTable() {
    return $("#tableGroup").DataTable({
        language : languege,
        serverSide: true,
        ajax: {
            url : "../getGroup",
            type : "GET",
            data : function ( d ) {
                d.idTeacher = $("#nameChooseTeacher").attr("class");
            }
        },
        columns : [
            { "className": "number",
                "name" : "number",
                "data" : "number",
                "title" : "Номер"

            },
            {
                "orderable": false,
                "name" : "delete",
                "data" : "number",
                "title" : "Действие",
                "render": function (data) {
                    return '<a class="deleteGroup" href="' + data + '">' +
                        '<img title="Удалить" ' +
                        'src="../image/bascet.png"></a>';
                }
            }
        ]
    });
}

function getTeacherNewGroupsTable() {
    return $("#tableGroup").DataTable({
        language : languege,
        serverSide: true,
        ajax: {
            url : "../getNewGroups",
            type : "GET",
            data : function ( d ) {
                d.idTeacher = $("#nameChooseTeacher").attr("class");
            }
        },
        columns : [
            { "className": "number",
                "name" : "number",
                "data" : "number",
                "title" : "Номер"

            },
            {
                "orderable": false,
                "name" : "delete",
                "data" : "number",
                "title" : "Действие",
                "render": function (data) {
                    return '<a class="addGroup" href="' + data + '">' +
                        '<img title="Добавить" ' +
                        'src="../image/plus.png"></a>';
                }
            }
        ]
    });
}
$(document).ready(function () {

    var tableTeachers = getTeachersTable();

    var tableGroupsTeachers = getTeacherGroupsTable();

    $("#groups").hide();

    var tableTeachersListner = $("#teachers");

    var tableGroupsTeachersListner = $("#tableGroup");

    $(tableTeachersListner).on("click", "td.nameTeacher", function () {
        setNameTeacher(this);
    });

    $(tableTeachersListner).on("click", "td.birthdayTeacher", function () {
        setBirthdayTeacher(this);
    });

    $(tableTeachersListner).on("click", "td.genderTeacher", function () {
        setGenderTeacher(this);
    });

    $(tableTeachersListner).on("click", ".deleteTeacher", function (evt) {
        deleteTeacher(this, evt, tableTeachers);
    });

    $("#addTeacher").submit(function (evt) {
        insertTeacher(tableTeachers, evt, this);
    });

    $("#findTeachers").submit(function (evt) {
        searchTeacher(tableTeachers, evt);
    });

    $(tableTeachersListner).on("click", ".listOfGroup", function (evt) {
        viewMenuGroups(tableTeachers, tableGroupsTeachers, evt, this);
    });

    $("#addGroup").click(function () {
        clickToAddGroupsOfTeacher(tableGroupsTeachers);
    });

    $("#viewGroup").click(function () {
        clickToViewGroupsOfTeacher(tableGroupsTeachers);
    });

    $(tableGroupsTeachersListner).on("click", ".deleteGroup", function (evt) {
        deleteGroupOfTeacher(evt, tableGroupsTeachers, this);
    });

    $(tableGroupsTeachersListner).on("click", ".addGroup", function (evt) {
        addGroupForTeacher(evt, tableGroupsTeachers, this);
    });

    $("#close").click(function () {
       var groups = $(this).parent();
       $(groups).hide();
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

function setBirthdayTeacher(cell) {
    var fieldBirthday = $(cell);
    var oldBirthday = $(fieldBirthday).text();
    var string = $(fieldBirthday).parent();
    $(fieldBirthday).html("<input id='newDateTeacher' value='" + oldBirthday + "' type='date'>");
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
                    fieldBirthday.html(newBirthday);
                }
            );
        }
    });

    $(inputBirthday).blur(function () {
        $(fieldBirthday).html(oldBirthday);
    })
}

function setGenderTeacher(cell) {
    var oldGender = $(cell).text();
    var fieldGender = $(cell);
    var string = $(fieldGender).parent();

    $(fieldGender).html("<select></select>");
    var selectGender = $(fieldGender).children();

    $.post("../getGender", {
        gender : oldGender
    }, function (data) {
        $(selectGender).html(data);
    });

    $(selectGender).click(function (evt) {
        evt.stopPropagation();
    });

    $(selectGender).focus();

    $(selectGender).change(function (){
        var newGender = $(this).val();

        $(fieldGender).html(newGender);

        $.post("updateTeacher", {
            idTeacher : $(string).attr("id"),
            newGender : newGender
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
        table.draw('page');
    });
}

function insertTeacher(table, evt, form) {
    evt.preventDefault();

    $.post("../insertTeacher",
        $(form).serialize(),
        function () {
            table.draw('page');
        });
}

function searchTeacher(table, evt) {
    evt.preventDefault();

    table.draw();
}

function viewMenuGroups(tableTeachers, tableGroups, evt, cell) {
    evt.preventDefault();

    var fieldOfOperations = $(cell).parent();
    var string = $(fieldOfOperations).parent();
    var idTeacher = $(string).attr("id");
    var row = tableTeachers.row("#" + idTeacher);
    var column = tableTeachers.columns(".nameTeacher");
    var nameTeacher = tableTeachers.cell(row, column).data();

    $("#nameChooseTeacher").attr("class", idTeacher);
    $("#nameChooseTeacher").text(nameTeacher);

    tableGroups.draw();

    $("#menuGroupsAndAddTeacher").show();
    $("#groups").show();
    $("#searchGroup").attr("class", "view");
}

function clickToAddGroupsOfTeacher(table) {

    $(this).css({"border" : "0px"});
    $("#viewGroup").css({"border" : "1px solid black"});

    table.ajax.url("../getNewGroups");
    table.draw();
}

function clickToViewGroupsOfTeacher(table) {
    $(this).css({"border" : "0px"});
    $("#addGroup").css({"border" : "1px solid black"});

    table.ajax.url("../getGroup");
    table.draw();
}

function deleteGroupOfTeacher(evt, table, cell) {
    evt.preventDefault();

    var fieldDeleteGroup = $(cell).parent();
    var stringGroup = $(fieldDeleteGroup).parent();
    var arrayFields = $(stringGroup).children();
    var numberGroup = $(arrayFields[0]).text();

    var idTeacher = $("#nameChooseTeacher").attr("class");

    $.get("../deleteGroupForTeacher", {
        idTeacher : idTeacher,
        numberGroup : numberGroup
    }, function () {
        table.draw();
    });
}

function addGroupForTeacher(evt, table, cell) {
    evt.preventDefault();

    var fieldAddGroup = $(cell).parent();
    var stringGroup = $(fieldAddGroup).parent();
    var arrayFields = $(stringGroup).children();
    var numberGroup = $(arrayFields[0]).text();

    var idTeacher = $("#nameChooseTeacher").attr("class");

    $.get("../addGroupForTeacher", {
        idTeacher : idTeacher,
        numberGroup : numberGroup
    }, function () {
        table.draw();
    });
}

function getTeachersTable() {
    return $("#teachers").DataTable({
        autoWidth : false,
        searching : false,
        stateSave: true,
        language : {
            "processing": "Подождите...",
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
        serverSide: true,
        ajax: {
            url : "../experimentSelect",
            type : "POST",
            data : function ( d ) {
                d.nameTeacher = $("#findTeachers").find("input[name=nameTeacher]").val();
                d.dateTeacher = $("#findTeachers").find("input[name=dateTeacher]").val();
                d.genderTeacher = $("#findTeachers").find("select[name=genderTeacher]").val();
            }
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
                "name" : "date",
                "data" : "date",
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
                "title" : "Действие"
            }
        ]
    });
}

function getTeacherGroupsTable() {
    return $("#tableGroup").DataTable({
        autoWidth : false,
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
                "data" : "delete",
                "title" : "Действие"
            }
        ]
    });
}
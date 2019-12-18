$(document).ready(function () {

    startPage();

    setNumberGroup();

    deleteGroup();

    insertGroup();

    searchGroup();

    viewTeachersMenu();

    viewStudentsMenu();

    clickToViewTeachers();

    clickToAddTeachers();

    addTeacherForGroup();

    deleteTeacherForGroup();

    searchTeacher();

    searchStudent();
});

function startPage() {
    $.post("selectAllGroup", function (data) {
        $("#groups").html(data);
        $("td.id").hide();
        $("#menuTeachersOfGroup").hide();
        $("#menuStudentsOfGroup").hide();
    });
}

function insertGroup() {
    $("#addGroup").keydown(function (evt) {
        if (evt.keyCode === 13) {

            $.post("insertGroup", {
                numberGroup: $(this).val()
            }, function() {
                $.post("selectAllGroup", function (data) {
                    $("#groups").html(data);
                    $("td.id").hide();
                });
            });
        }
    });
}

function deleteGroup() {
    $("#groups").on("click", ".del", function (evt) {
        evt.preventDefault();

        var fieldDelete = $(this).parent();
        var string = $(fieldDelete).parent();
        var arrayFields = $(string).children();
        var idGroup = $(arrayFields[0]).text();

        $(string).remove();

        $.post("deleteGroup", {
            idGroup: idGroup
        }, function () {
            $.post("selectAllGroup", function (data) {
                $("#groups").html(data);
                $("td.id").hide();
            })
        })
    });
}

function setNumberGroup() {
    $("#groups").on("click", ".number", function () {
        var oldNumber = $(this).text();
        var string = $(this).parent();
        var fieldNumber = $(this);

        $(fieldNumber).html("<input class='number' type='text' value='" + oldNumber + "'>");
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
                var arrayFields = $(string).children();

                $.post("upGroup", {
                    idGroup: $(arrayFields[0]).text(),
                    numberGroup: newNumber
                }, function () {
                    (fieldNumber).html(newNumber);
                })
            }
        })
    });
}

function searchGroup() {
    $("#searchGroup").keyup(function () {
        if ($(this).val() === "") {

            $.post("selectAllGroup", function (data) {

                $("#groups").html(data);

                $("td.id").hide();
            })
        } else {

            $.post("selectAllGroup", {
                numberGroup: $(this).val()
            }, function (data) {

                $("#groups").html(data);

                $("td.id").hide();
            })
        }
    });

    $("#addGroup").blur(function () {
        $(this).val("");
    });
}

function viewTeachersMenu() {
    $("#groups").on("click", ".teachers", function (evt) {
        evt.preventDefault();
        var fieldTeachers = $(this).parent();
        var string = $(fieldTeachers).parent();
        var arrayFields = $(string).children();
        var numberGroup = $(arrayFields[1]).text();

        $("#numberGroup").html(numberGroup)

        $("#viewTeachers").click();

        $("#searchTeachersForGroup").html("<input id='searchTeacher' type='text'" +
            " class='view' placeholder=\"Найти преподавателя\">");

        $("#menuTeachersOfGroup").show();

        $("#menuStudentsOfGroup").hide();
    });
}

function viewStudentsMenu() {
    $("#groups").on("click", ".students", function (evt) {
        evt.preventDefault();
        var fieldStudents = $(this).parent();
        var string = $(fieldStudents).parent();
        var arrayFields = $(string).children();
        var numberGroup = $(arrayFields[1]).text();

        $("#numberGroup").html(numberGroup)

        $("#menuStudentsOfGroup").show();

        $("#menuTeachersOfGroup").hide();

        $.post("selectStudent", {
            numberGroup : numberGroup
        }, function (data) {
            $("#studentsOfGroup").html(data);
        })
    });
}

function clickToViewTeachers() {
    $("#viewTeachers").on("click", function () {
        var numberGroup = $("#numberGroup").text();

        $(this).css({"border" : "0px"});
        $("#addTeachers").css({"border" : "1px solid black"});

        $.get("getTeachers", {
            numberGroup: numberGroup
        }, function (data) {

            $("#teachers").html(data);

            $("#searchTeacher").attr("class", "view");
        })
    });
}

function clickToAddTeachers() {
    $("#addTeachers").on("click", function () {
        var numberGroup = $("#numberGroup").text();

        $(this).css({"border" : "0px"});
        $("#viewTeachers").css({"border" : "1px solid black"});

        $.get("getNewTeachers", {
            numberGroup: numberGroup
        }, function (data) {

            $("#teachers").html(data);

            $("#searchTeacher").attr("class", "add");
        })
    });
}

function addTeacherForGroup() {
    $("#teachers").on("click", ".addTeacher", function (evt) {
        evt.preventDefault();
        var idTeacher = $(this).attr("href");
        var numberGroup = $("#numberGroup").text();

        $.post("addTeacherForGroup", {
            idTeacher: idTeacher,
            numberGroup: numberGroup
        }, function () {

            $("#addTeachers").click();
        })
    });
}

function deleteTeacherForGroup() {
    $("#teachers").on("click", ".deleteTeacher", function (evt) {
        evt.preventDefault();
        var idTeacher = $(this).attr("href");
        var numberGroup = $("#numberGroup").text();

        $.post("deleteTeacherForGroup", {
            idTeacher: idTeacher,
            numberGroup: numberGroup
        }, function () {

            $("#viewTeachers").click();
        })
    });
}

function searchTeacher() {
    $("#searchTeachersForGroup").on("keyup", "#searchTeacher", function () {

        var typeList = $(this).attr("class");

        if (typeList === "view") {

            $.get("getTeachers", {
                numberGroup: $("#numberGroup").text(),
                nameTeacher: $(this).val()
            }, function (data) {

                $("#teachers").html(data);
            })
        } else {

            $.get("getNewTeachers", {
                numberGroup: $("#numberGroup").text(),
                nameTeacher: $(this).val()
            }, function (data) {

                $("#teachers").html(data);
            })
        }
    });
}

function searchStudent() {
    $("#searchStudent").on("keyup", function (evt) {

        $.post("selectStudent", {
            numberGroup : $("#numberGroup").text(),
            nameStudent : $(this).val()
        }, function (data) {
            $("#studentsOfGroup").html(data);
        })
    });
}
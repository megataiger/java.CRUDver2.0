$(document).ready(function () {
   
    startPage();
    
    setNameTeacher();

    setBirthdayTeacher();

    setGenderTeacher();

    deleteTeacher();

    insertTeacher();

    searchTeacher();

    viewMenuGroups();

    clickToAddGroupsOfTeacher();

    clickToViewGroupsOfTeacher();

    deleteGroupOfTeacher();

    addGroupForTeacher();

    searchGroup();
});

function startPage() {
    $.post(
        "selectAllTeachers",
        function (data) {
            $("#teachers").html(data);
            $("td.id").hide();
            $("#groups").hide();
        }
    );
}

function setNameTeacher() {
    $("#teachers").on("click", ".nameTeacher", function () {
        var fieldName = $(this);
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
                var arrayFields = $(string).children();
                var idTeacher = $(arrayFields[0]).text();
                $.post(
                    "updateTeacher", {
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
    });
}

function setBirthdayTeacher() {
    $("#teachers").on("click", ".birthdayTeacher", function () {
        var fieldBirthday = $(this);
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
                var arrayFields = $(string).children();
                var idTeacher = $(arrayFields[0]).text();
                $.post(
                    "updateTeacher", {
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
    });
}

function setGenderTeacher() {
    $("#teachers").on("click", ".genderTeacher", function () {
        var oldGender = $(this).text();
        var fieldGender = $(this);
        var string = $(fieldGender).parent();

        $(fieldGender).html("<select></select>");
        var selectGender = $(fieldGender).children();

        $.post("getGender", {
            gender : oldGender
        }, function (data) {
            $(selectGender).html(data);
        });

        $(selectGender).click(function (evt) {
            evt.stopPropagation();
        });

        $(selectGender).focus();

        $(selectGender).change(function (){
            var newGender = $(this).val()

            $(fieldGender).html(newGender);

            var arrayFields = $(string).children();

            $.post("updateTeacher", {
                idTeacher : $(arrayFields[0]).text(),
                newGender : newGender
            });
        });

        $(selectGender).blur(function (){
            $(fieldGender).html(oldGender);
        });
    });
}

function deleteTeacher() {
    $("#teachers").on("click", ".deleteTeacher", function (evt) {
        evt.preventDefault();

        var fieldOfOperations = $(this).parent();
        var string = $(fieldOfOperations).parent();
        var arrayFields = $(string).children();
        var idTeacher = $(arrayFields[0]).text();

        $.get("deleteTeacher", {
            idTeacher : idTeacher
        }, function () {
            $.post(
                "selectAllTeachers",
                function (data) {
                    $("#teachers").html(data);
                    $("td.id").hide();
                }
            );
        });
    });
}

function insertTeacher() {
    $("#addTeacher").submit(function (evt) {
        evt.preventDefault();

        var form = $(this);

        $.post("insertTeacher",
            $(form).serialize(),
            function () {
                $.post(
                    "selectAllTeachers",
                    function (data) {
                        $("#teachers").html(data);
                        $("td.id").hide();
                    }
                );
            });
    });
}

function searchTeacher() {
    $("#findTeachers").submit(function (evt) {
        evt.preventDefault();

        var form = $(this);

        $.post("selectAllTeachers",
            $(form).serialize(),
            function (data) {
                $("#teachers").html(data);
                $("td.id").hide();
            })
    });
}

function viewMenuGroups() {
    $("#teachers").on("click", ".listOfGroup", function (evt) {
        evt.preventDefault();

        var fieldOfOperations = $(this).parent();
        var string = $(fieldOfOperations).parent();
        var arrayFields = $(string).children();
        var idTeacher = $(arrayFields[0]).text();
        var nameTeacher = $(arrayFields[1]).text();

        $("#nameChooseTeacher").attr("class", idTeacher);
        $("#nameChooseTeacher").text(nameTeacher);

        $.get("getGroup", {
            idTeacher : idTeacher
        }, function (data) {
            $("#groups").show();
            $("#tableGroup").html(data);
            $("#searchGroup").attr("class", "view");
        });
    });
}

function clickToAddGroupsOfTeacher() {
    $("#addGroup").click(function () {
        var idTeacher = $("#nameChooseTeacher").attr("class");

        $(this).css({"border" : "0px"});
        $("#viewGroup").css({"border" : "1px solid black"});

        $.get("getNewGroups", {
            idTeacher : idTeacher
        }, function (data) {
            $("#tableGroup").html(data);
            $("#searchGroup").attr("class", "add");
        })
    });
}

function clickToViewGroupsOfTeacher() {
    $("#viewGroup").click(function () {
        var idTeacher = $("#nameChooseTeacher").attr("class");

        $(this).css({"border" : "0px"});
        $("#addGroup").css({"border" : "1px solid black"});

        $.get("getGroup", {
            idTeacher : idTeacher
        }, function (data) {
            $("#tableGroup").html(data);
            $("#searchGroup").attr("class", "view");
        })
    });
}

function deleteGroupOfTeacher() {
    $("#tableGroup").on("click", ".deleteGroup", function (evt) {
        evt.preventDefault();

        var fieldDeleteGroup = $(this).parent();
        var stringGroup = $(fieldDeleteGroup).parent();
        var arrayFields = $(stringGroup).children();
        var numberGroup = $(arrayFields[0]).text();

        var idTeacher = $("#nameChooseTeacher").attr("class");

        $.get("deleteGroupForTeacher", {
            idTeacher : idTeacher,
            numberGroup : numberGroup
        }, function () {
            $("#viewGroup").click();
        });
    });
}

function addGroupForTeacher() {
    $("#tableGroup").on("click", ".addGroup", function (evt) {
        evt.preventDefault();

        var fieldAddGroup = $(this).parent();
        var stringGroup = $(fieldAddGroup).parent();
        var arrayFields = $(stringGroup).children();
        var numberGroup = $(arrayFields[0]).text();

        var idTeacher = $("#nameChooseTeacher").attr("class");

        $.get("addGroupForTeacher", {
            idTeacher : idTeacher,
            numberGroup : numberGroup
        }, function () {
            $("#addGroup").click();
        });
    });
}

function searchGroup() {
    $("#searchGroup").keyup(function () {
        if ($(this).attr("class") === "view") {
            var idTeacher = $("#nameChooseTeacher").attr("class");

            $.get("getGroup", {
                idTeacher : idTeacher,
                numberGroup : $(this).val()
            }, function (data) {
                $("#tableGroup").html(data);
            });
        } else if ($(this).attr("class") === "add") {
            var idTeacher = $("#nameChooseTeacher").attr("class");

            $.get("getNewGroups", {
                idTeacher : idTeacher,
                numberGroup : $(this).val()
            }, function (data) {
                $("#tableGroup").html(data);
            });
        }
    });
}
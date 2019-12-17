$(document).ready(function () {

    startPage();

    setNameStudent();

    setBirthdayStudent();

    setGenderStudent();

    setGroupStudent();

    searchStudents();

    insertStudent();

    deleteStudent();
});

function startPage() {
    $.post("selectStudentsByCriterion", function (data) {

        $("#students").html(data);

        $("td.id").hide();
    });

    $.post("getGroups", function (data) {
        $(".allGroup").html(data);
    });
}

function setNameStudent() {
    $("#students").on("click", ".name", function () {
        var oldName = $(this).text();
        $(this).html("<input type='text' value='" + oldName + "'>");
        var inputName = $(this).children();
        var string = $(this).parent();

        $(inputName).click(function (evt) {
            evt.stopPropagation();
        });

        $(inputName).select();

        $(inputName).blur(function (evt) {
            var fieldName = $(this).parent();
            $(fieldName).html(oldName);
        });

        $(inputName).keypress(function (evt) {
            if (evt.keyCode === 13) {
                var newName = $(this).val();
                var fieldName = $(this).parent();
                var arrayField = $(string).children();

                $.post("setNameStudent", {
                    idStudent: $(arrayField[0]).text(),
                    newNameStudent: newName
                });

                $(fieldName).html(newName);
            }
        });
    });
}

function setBirthdayStudent() {
    $("#students").on("click", ".birthday", function () {
        var oldBirthday = $(this).text();
        var string = $(this).parent();

        $(this).html("<input type=\"date\" value=\"" + oldBirthday + "\">");

        var inputBirthday = $(this).children();
        $(inputBirthday).click(function (evt) {
            evt.stopPropagation();
        });

        var fieldBirthday = $(this);
        var arrayField = $(string).children();

        $(inputBirthday).focus();
        $(inputBirthday).keypress(function (evt) {
            if (evt.keyCode === 13) {
                var newBirthday = $(this).val();

                $.post("setBirthdayStudent", {
                    idStudent : $(arrayField[0]).text(),
                    newBirthdayStudent : newBirthday
                });

                $(fieldBirthday).html(newBirthday);
            }
        });
        $(inputBirthday).blur(function () {
            $(fieldBirthday).html(oldBirthday);
        });
    });
}

function setGenderStudent() {
    $("#students").on("click", ".gender", function () {
        var oldGender = $(this).text();
        var fieldGender = $(this);
        var string = $(this).parent();

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

            var arrayField = $(string).children();

            $.post("setGenderStudent", {
                idStudent : $(arrayField[0]).text(),
                genderStudent : newGender
            });

            $(fieldGender).html(newGender);
        });

        $(selectGender).blur(function (){
            $(fieldGender).html($(this).val());
        });
    });
}

function setGroupStudent() {
    $("#students").on("click", ".group", function () {
        var oldGroup = $(this).text();
        var fieldGroup = $(this);
        var string = $(fieldGroup).parent();

        $(fieldGroup).html("<select></select>");
        var selectGroup = $(fieldGroup).children();

        $.post("getGroups", {
            number : oldGroup
        }, function (data) {
            $(selectGroup).html(data);
        });

        $(selectGroup).click(function (evt) {
            evt.stopPropagation();
        });

        $(selectGroup).focus();

        $(selectGroup).change(function () {
            var newGroup = $(this).val();
            var arrayField = $(string).children();

            $.post("setGroupStudent", {
                idStudent : $(arrayField[0]).text(),
                numberGroup : newGroup
            });

            $(fieldGroup).html(newGroup);
        });
        $(selectGroup).blur(function () {
            $(fieldGroup).html(group);
        });
    });
}

function searchStudents() {
    $("#searchStudents").submit(function (evt) {
        evt.preventDefault();

        var formSearch = $(this);

        $.post(
            "selectStudentsByCriterion",
            formSearch.serializeArray(),
            function (data) {
                $("#students").html(data);

                $("td.id").hide();
            });
    });
}

function insertStudent() {
    $("#addStudent").submit(function (evt) {
        evt.preventDefault();
        $.post(
            "insertStudent",
            $(this).serialize(),
            function () {
                $.post("selectStudentsByCriterion", function (data) {

                    $("#students").html(data);

                    $("td.id").hide();
                });
            }
        );
    });
}

function deleteStudent() {
    $("#students").on("click", ".deleteStudent", function (evt) {
        evt.preventDefault();
        var idStudent = $(this).attr("href");
        $.get("deleteStudent", {
            idStudent : idStudent
        }, function () {
            $.post("selectStudentsByCriterion", function (data) {

                $("#students").html(data);

                $("td.id").hide();
            });
        });
    });
}
package mvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import gsonSerialize.StudentSerialize;
import objectForStrokeBase.Gender;
import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import workWithBase.serviceInterfaces.StudentServiceInterface;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/students")
public class StudentPageController {

    private StudentServiceInterface studentService;

    @Autowired
    public StudentPageController(StudentServiceInterface studentService) {
        this.studentService = studentService;
    }

    @RequestMapping("/selectStudentsByCriterion")
    @ResponseBody
    public String getStudents(HttpServletRequest request,
                              @RequestParam(name = "start") int page,
                              @RequestParam(name = "length") int length,
                              @RequestParam(name = "draw") String draw,
                              @RequestParam(name = "search[value]") String search,
                              @RequestParam(name = "order[0][dir]") String order
                              ) {
        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");

        if(columnName.equals("group")) {
            columnName += ".number";
        }

        order = columnName + " " + order;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", search);
        parameters.put("order", order);
        parameters.put("page", page);
        parameters.put("length", length);

        List students = studentService.getStudents(parameters);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Student.class, new StudentSerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(students));
        result.addProperty("recordsTotal", studentService.getStudents(""));
        result.addProperty("recordsFiltered", studentService.getStudents(search));

        return result.toString();
    }

    @RequestMapping("/setNameStudent")
    @ResponseBody
    public void updateNameStudent(@RequestParam(name = "idStudent") int idStudent,
                              @RequestParam(name = "newNameStudent") String newNameStudent) {
        studentService.updateName(idStudent, newNameStudent);
    }

    @RequestMapping("/setBirthdayStudent")
    @ResponseBody
    public void updateBirthdayStudent
            (@RequestParam(name = "idStudent") int idStudent,
             @RequestParam(name = "newBirthdayStudent") String newBirthdayStudent) {
        studentService.updateBirthday(idStudent, LocalDate.parse(newBirthdayStudent));
    }

    @RequestMapping(value = "/getGender", produces = "application/json")
    @ResponseBody
    public String getGenderList(@RequestParam(name = "gender") String genderInBase) {
        JsonArray resultList = new JsonArray();
        for (Gender e : Gender.values()) {
            JsonObject gender = new JsonObject();
            gender.addProperty("gender", e.getGender());
            gender.addProperty("value", e.toString());
            if (genderInBase.equals(e.getGender())) {
                gender.addProperty("selected", true);
            } else {
                gender.addProperty("selected", false);
            }
            resultList.add(gender);
        }

        return resultList.toString();
    }

    @RequestMapping("/setGenderStudent")
    @ResponseBody
    public void updateGenderStudent(@RequestParam(name = "idStudent") int idStudent,
                                  @RequestParam(name = "genderStudent") String genderStudent) {
        studentService.updateGender(idStudent, genderStudent);
    }

    @RequestMapping("/setGroupStudent")
    @ResponseBody
    public void updateGroupStudent(@RequestParam(name = "idStudent") int idStudent,
                                  @RequestParam(name = "numberGroup") int newNumberGroup) {
        studentService.updateGroup(idStudent, newNumberGroup);
    }

    @RequestMapping("/insertStudent")
    @ResponseBody
    public void insertStudent(@RequestParam(name = "nameStudent") String name,
                              @RequestParam(name = "birthdayStudent") String date,
                              @RequestParam(name = "genderStudent") String genderStudent,
                              @RequestParam(name = "groupStudent") int number) {
        LocalDate birthday = LocalDate.parse(date);
        Gender gender = null;
        for (Gender e : Gender.values()) {
            if (genderStudent.equals(e.toString())) {
                gender = e;
            }
        }

        studentService.insert(name, birthday, gender, number);
    }

    @RequestMapping("/deleteStudent")
    @ResponseBody
    public void deleteStudent(@RequestParam(name = "idStudent") int idStudent) {
        studentService.delete(idStudent);
    }

    @RequestMapping("/searchGroups")
    @ResponseBody
    public String getPromptGroups(@RequestParam(name = "number") int inputNumber) {
        List<Group> groups = studentService.getPromptGroups(inputNumber);
        JsonArray result = new JsonArray();
        for (Group e : groups) {
            result.add(e.getNumber());
        }
        return result.toString();
    }
}
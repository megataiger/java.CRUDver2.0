package mvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import gsonSerialize.GroupSerialize;
import gsonSerialize.StudentSerialize;
import mvc.wrappers.DataTablesWrapper;
import objectForStrokeBase.Gender;
import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import workWithBase.serviceInterfaces.GroupServiceInterface;
import workWithBase.serviceInterfaces.StudentServiceInterface;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
@RequestMapping("/students")
public class StudentPageController {

    private StudentServiceInterface studentService;
    private GroupServiceInterface groupService;

    @Autowired
    public StudentPageController(StudentServiceInterface studentService,
                                 GroupServiceInterface groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @RequestMapping("/selectStudentsByCriterion")
    @ResponseBody
    public String getStudents(HttpServletRequest request) {
        DataTablesWrapper dataTables = new DataTablesWrapper(request);

        Pageable pageable = PageRequest.of(dataTables.getPage(),
                dataTables.getLength(), Sort.by(dataTables.getOrders()));

        Page<Student> students = studentService
                .getStudents(dataTables.getFilter(), pageable);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Student.class, new StudentSerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", dataTables.getDraw());
        result.add("data", gson.toJsonTree(students.getContent()));
        result.addProperty("recordsTotal", studentService.getCount());
        result.addProperty("recordsFiltered", students.getTotalElements());

        return result.toString();
    }

    @RequestMapping("/setNameStudent")
    @ResponseBody
    public void updateNameStudent
            (@RequestParam(name = "idStudent") Student student,
             String newNameStudent) {
        student.setName(newNameStudent);
        studentService.update(student);
    }

    @RequestMapping("/setBirthdayStudent")
    @ResponseBody
    public void updateBirthdayStudent
            (@RequestParam(name = "idStudent") Student student,
             LocalDate newBirthdayStudent) {
        student.setDate(newBirthdayStudent);
        studentService.update(student);
    }

    @RequestMapping(value = "/getGender", produces = "application/json")
    @ResponseBody
    public String getGenderList
            (@RequestParam(name = "gender") String genderInBase) {
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
    public void updateGenderStudent
            (@RequestParam(name = "idStudent") Student student,
             Gender genderStudent) {
        student.setGender(genderStudent);
        studentService.update(student);
    }

    @RequestMapping("/setGroupStudent")
    @ResponseBody
    public void updateGroupStudent
            (@RequestParam(name = "idStudent") Student student,
             @RequestParam(name = "numberGroup") Group group) {
        student.setGroup(group);
        studentService.update(student);
    }

    @RequestMapping("/insertStudent")
    @ResponseBody
    public void insertStudent(Student student) {
        studentService.insert(student);
    }

    @RequestMapping("/deleteStudent")
    @ResponseBody
    public void deleteStudent
            (@RequestParam(name = "idStudent") Student student) {
        studentService.delete(student);
    }

    @RequestMapping("/getGroups")
    @ResponseBody
    public String getGroups(HttpServletRequest request) {
        DataTablesWrapper dataTables = new DataTablesWrapper(request);

        Pageable pageable = PageRequest.of(dataTables.getPage(),
                dataTables.getLength(), Sort.by(dataTables.getOrders()));

        Page<Group> groups = groupService.getGroups(
                dataTables.getFilter(), pageable);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Group.class, new GroupSerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", dataTables.getDraw());
        result.add("data", gson.toJsonTree(groups.getContent()));
        result.addProperty("recordsTotal", groupService.getCount());
        result.addProperty("recordsFiltered",
                groups.getTotalElements());

        return result.toString();
    }
}
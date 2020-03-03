package mvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gsonSerialize.GroupSerialize;
import gsonSerialize.StudentEasySerialize;
import gsonSerialize.TeacherEasySerialize;
import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import objectForStrokeBase.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import workWithBase.serviceInterfaces.GroupServiceInterface;
import workWithBase.serviceInterfaces.StudentServiceInterface;
import workWithBase.serviceInterfaces.TeacherServiceInterface;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/groups")
public class GroupPageController {

    private GroupServiceInterface groupService;
    private StudentServiceInterface studentService;
    private TeacherServiceInterface teacherService;

    @Autowired
    public GroupPageController(GroupServiceInterface groupService,
                               StudentServiceInterface studentService,
                               TeacherServiceInterface teacherService) {
        this.groupService = groupService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @RequestMapping("/selectGroups")
    @ResponseBody
    public String getGroups(HttpServletRequest request,
                            @RequestParam(name = "start") int page,
                            @RequestParam(name = "length") int length,
                            @RequestParam(name = "draw") String draw,
                            @RequestParam(name = "search[value]") String search,
                            @RequestParam(name = "order[0][dir]") String order) {
        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");

        order = columnName + " " + order;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", search);
        parameters.put("order", order);
        parameters.put("page", page);
        parameters.put("length", length);

        List<Group> groups = groupService.getGroups(parameters);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Group.class, new GroupSerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(groups));
        result.addProperty("recordsTotal", groupService.getGroupsLength(""));
        result.addProperty("recordsFiltered", groupService.getGroupsLength(search));

        return result.toString();
    }

    @RequestMapping("/selectStudentGroup")
    @ResponseBody
    public String getStudents(HttpServletRequest request,
                              @RequestParam(name = "numberGroup") int number,
                              @RequestParam(name = "start") int page,
                              @RequestParam(name = "length") int length,
                              @RequestParam(name = "draw") String draw,
                              @RequestParam(name = "search[value]") String search,
                              @RequestParam(name = "order[0][dir]") String order) {
        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");

        order =  columnName + " " + order;

        number = groupService.getByNumber(number).getId();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("groupNumber", number);
        parameters.put("page", page);
        parameters.put("length", length);
        parameters.put("filter", search);
        parameters.put("order", order);

        List<Student> students = studentService.getGroupStudents(parameters);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Student.class, new StudentEasySerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(students));
        result.addProperty("recordsTotal", studentService.getGroupStudentsLength(number, ""));
        result.addProperty("recordsFiltered", studentService.getGroupStudentsLength(number, search));

        return result.toString();
    }

    @RequestMapping("/getTeachersGroup")
    @ResponseBody
    public String getTeachers(HttpServletRequest request,
                              @RequestParam(name = "numberGroup") int number,
                              @RequestParam(name = "start") int page,
                              @RequestParam(name = "length") int length,
                              @RequestParam(name = "draw") String draw,
                              @RequestParam(name = "search[value]") String search,
                              @RequestParam(name = "order[0][dir]") String order) {

        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");

        order = columnName + " " + order;

        number = groupService.getByNumber(number).getId();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("groupNumber", number);
        parameters.put("page", page);
        parameters.put("length", length);
        parameters.put("draw", draw);
        parameters.put("filter", search);
        parameters.put("order", order);
        parameters.put("number", number);

        List<Teacher> teachers = teacherService.getTeachersForGroup(parameters);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Teacher.class, new TeacherEasySerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(teachers));
        result.addProperty("recordsTotal", teacherService.getTeachersForGroupLength(number, ""));
        result.addProperty("recordsFiltered", teacherService.getTeachersForGroupLength(number, search));

        return result.toString();
    }

    @RequestMapping("/getNewTeachersGroup")
    @ResponseBody
    public String getNewTeachers(HttpServletRequest request,
                                 @RequestParam(name = "numberGroup") int number,
                                 @RequestParam(name = "start") int page,
                                 @RequestParam(name = "length") int length,
                                 @RequestParam(name = "draw") String draw,
                                 @RequestParam(name = "search[value]") String search,
                                 @RequestParam(name = "order[0][dir]") String order) {
        int columnNumber = Integer
                .parseInt(request.getParameter("order[0][column]"));
        String columnName = request
                .getParameter("columns[" + columnNumber + "][data]");

        order = columnName + " " + order;

        number = groupService.getByNumber(number).getId();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("groupNumber", number);
        parameters.put("page", page);
        parameters.put("length", length);
        parameters.put("draw", draw);
        parameters.put("filter", search);
        parameters.put("order", order);
        parameters.put("number", number);

        List<Teacher> teachers = teacherService
                .getNewTeachersForGroup(parameters);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Teacher.class, new TeacherEasySerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(teachers));
        result.addProperty("recordsTotal",
                teacherService.getNewTeachersForGroupLength(number, ""));
        result.addProperty("recordsFiltered",
                teacherService.getNewTeachersForGroupLength(number, search));

        return result.toString();
    }

    @RequestMapping("/setNumberGroup")
    @ResponseBody
    public void setNumberGroup(@RequestParam(name = "idGroup") int idGroup,
                               @RequestParam(name = "numberGroup") int newNumber) {
        groupService.setNumber(idGroup, newNumber);
    }

    @RequestMapping("/insertGroup")
    @ResponseBody
    public void insertGroup(@RequestParam(name = "numberGroup") int number) {
        groupService.insert(number);
    }

    @RequestMapping("/deleteGroup")
    @ResponseBody
    public void deleteGroup(@RequestParam(name = "idGroup") int id) {
        groupService.delete(id);
    }

    @RequestMapping("/addTeacherForGroup")
    @ResponseBody
    public void addTeacher(@RequestParam(name = "numberGroup") int numberGroup,
                                   @RequestParam(name = "idTeacher") int idTeacher) {
        groupService.addTeacher(numberGroup, idTeacher);
    }

    @RequestMapping("/deleteTeacherForGroup")
    @ResponseBody
    public void deleteTeacher(@RequestParam(name = "numberGroup") int numberGroup,
                                   @RequestParam(name = "idTeacher") int idTeacher) {
        groupService.deleteTeacher(numberGroup, idTeacher);
    }
}
package mvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gsonSerialize.GroupSerialize;
import gsonSerialize.TeacherSerialize;
import objectForStrokeBase.Gender;
import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import workWithBase.serviceInterfaces.GroupServiceInterface;
import workWithBase.serviceInterfaces.TeacherServiceInterface;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teachers")
public class TeacherPageController {

    private TeacherServiceInterface teacherService;
    private GroupServiceInterface groupService;

    @Autowired
    public TeacherPageController(TeacherServiceInterface teacherService,
                                 GroupServiceInterface groupService) {
        this.teacherService = teacherService;
        this.groupService = groupService;
    }

    @RequestMapping("/selectTeachers")
    @ResponseBody
    public String getTeachers(HttpServletRequest request,
                              @RequestParam(name = "start") int page,
                              @RequestParam(name = "length") int length,
                              @RequestParam(name = "draw") String draw,
                              @RequestParam(name = "search[value]") String search,
                              @RequestParam(name = "order[0][dir]") String order) {

        int orderColumn = Integer.parseInt(request.getParameter("order[0][column]"));
        String nameColumn = request.getParameter("columns[" + orderColumn + "][name]");

        order = nameColumn + " " + order;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", search);
        parameters.put("order", order);
        parameters.put("page", page);
        parameters.put("length", length);
        parameters.put("draw", draw);

        List<Teacher> teachers = teacherService.getTeachers(parameters);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Teacher.class, new TeacherSerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(teachers));
        result.addProperty("recordsTotal", teacherService.getTeachersLength(""));
        result.addProperty("recordsFiltered", teacherService.getTeachersLength(search));

        return result.toString();
    }

    @RequestMapping("/setNameTeacher")
    @ResponseBody
    public void setNameTeacher(@RequestParam(name = "idTeacher") int idTeacher,
                               @RequestParam(name = "nameTeacher") String newName) {
        teacherService.setName(idTeacher, newName);
    }

    @RequestMapping("/setBirthdayTeacher")
    @ResponseBody
    public void setBirthdayTeacher(@RequestParam(name = "idTeacher") int idTeacher,
                                   @RequestParam(name = "newBirthday") String date) {
        LocalDate newBirthday = LocalDate.parse(date);
        teacherService.setBirthday(idTeacher, newBirthday);
    }

    @RequestMapping("/setGenderTeacher")
    @ResponseBody
    public void setGenderTeacher(@RequestParam(name = "idTeacher") int idTeacher,
                                 @RequestParam(name = "newGender") String newGender) {
        teacherService.setGender(idTeacher, newGender);
    }

    @RequestMapping("/insertTeacher")
    @ResponseBody
    public void insertTeacher(@RequestParam(name = "nameTeacher") String name,
                              @RequestParam(name = "birthday") String date,
                              @RequestParam(name = "gender") String genderTeacher) {
        LocalDate birthday = LocalDate.parse(date);
        Gender gender = null;
        for (Gender e : Gender.values()) {
            if (genderTeacher.equals(e.toString())) {
                gender = e;
            }
        }

        teacherService.insert(name, birthday, gender);
    }

    @RequestMapping("/deleteTeacher")
    @ResponseBody
    public void deleteTeacher(@RequestParam(name = "idTeacher") int idTeacher) {
        teacherService.delete(idTeacher);
    }

    @RequestMapping("/getGroupsTeacher")
    @ResponseBody
    public String getGroups(@RequestParam(name = "idTeacher") int idTeacher,
                            @RequestParam(name = "start") int page,
                            @RequestParam(name = "length") int length,
                            @RequestParam(name = "draw") String draw,
                            @RequestParam(name = "search[value]") String search,
                            @RequestParam(name = "order[0][dir]") String order) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", search);
        parameters.put("order", order);
        parameters.put("page", page);
        parameters.put("length", length);
        parameters.put("draw", draw);
        parameters.put("teacherId", idTeacher);

        List<Group> groups = groupService.getGroupsForTeacher(parameters);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Group.class, new GroupSerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(groups));
        result.addProperty("recordsTotal",
                groupService.getGroupsForTeacherLength(idTeacher, ""));
        result.addProperty("recordsFiltered",
                groupService.getGroupsForTeacherLength(idTeacher, search));

        return result.toString();
    }

    @RequestMapping("/getNewGroupsTeacher")
    @ResponseBody
    public String getNewGroups(@RequestParam(name = "idTeacher") int idTeacher,
                            @RequestParam(name = "start") int page,
                            @RequestParam(name = "length") int length,
                            @RequestParam(name = "draw") String draw,
                            @RequestParam(name = "search[value]") String search,
                            @RequestParam(name = "order[0][dir]") String order) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", search);
        parameters.put("order", order);
        parameters.put("page", page);
        parameters.put("length", length);
        parameters.put("draw", draw);
        parameters.put("teacherId", idTeacher);

        List<Group> groups = groupService.getNewGroupsForTeacher(parameters);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Group.class, new GroupSerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(groups));
        result.addProperty("recordsTotal",
                groupService.getNewGroupsForTeacherLength(idTeacher, ""));
        result.addProperty("recordsFiltered",
                groupService.getNewGroupsForTeacherLength(idTeacher, search));

        return result.toString();
    }

    @RequestMapping("/addGroupForTeacher")
    @ResponseBody
    public void addGroup(@RequestParam(name = "idTeacher") int idTeacher,
                         @RequestParam(name = "numberGroup") int numberGroup){
        teacherService.addGroup(idTeacher, numberGroup);
    }

    @RequestMapping("/deleteGroupForTeacher")
    @ResponseBody
    public void deleteGroup(@RequestParam(name = "idTeacher") int idTeacher,
                            @RequestParam(name = "numberGroup") int numberGroup){
        teacherService.deleteGroup(idTeacher, numberGroup);
    }

}
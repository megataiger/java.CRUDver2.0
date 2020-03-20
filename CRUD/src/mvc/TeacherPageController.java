package mvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gsonSerialize.GroupSerialize;
import gsonSerialize.TeacherSerialize;
import mvc.wrappers.DataTablesWrapper;
import objectForStrokeBase.Gender;
import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import workWithBase.serviceInterfaces.GroupServiceInterface;
import workWithBase.serviceInterfaces.TeacherServiceInterface;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@RestController
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

    @RequestMapping(value = "/selectTeachers")
    @ResponseBody
    public String getTeachers(HttpServletRequest request) {

        DataTablesWrapper dataTables = new DataTablesWrapper(request);

        Pageable pageable = PageRequest.of(dataTables.getPage(),
                dataTables.getLength(), Sort.by(dataTables.getOrders()));

        Page<Teacher> teachers = teacherService
                .getTeachers(dataTables.getFilter(), pageable);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Teacher.class, new TeacherSerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", dataTables.getDraw());
        result.add("data", gson.toJsonTree(teachers.getContent()));
        result.addProperty("recordsTotal", teacherService.getCount());
        result.addProperty("recordsFiltered", teachers.getTotalElements());

        return result.toString();
    }

    @RequestMapping("/setNameTeacher")
    @ResponseBody
    public void setNameTeacher
            (@RequestParam("teacherId") Teacher teacher,
             String nameTeacher) {
        teacher.setName(nameTeacher);
        teacherService.save(teacher);
    }

    @RequestMapping("/setBirthdayTeacher")
    @ResponseBody
    public void setBirthdayTeacher
            (@RequestParam("teacherId") Teacher teacher,
             LocalDate newBirthday) {
        teacher.setDate(newBirthday);
        teacherService.save(teacher);
    }

    @RequestMapping("/setGenderTeacher")
    @ResponseBody
    public void setGenderTeacher
            (@RequestParam("teacherId") Teacher teacher,
             Gender newGender) {
        teacher.setGender(newGender);
        teacherService.save(teacher);
    }

    @RequestMapping("/insertTeacher")
    @ResponseBody
    public void insertTeacher(Teacher teacher) {
        teacherService.save(teacher);
    }

    @RequestMapping("/deleteTeacher")
    @ResponseBody
    public void deleteTeacher
            (@RequestParam("teacherId") Teacher teacher) {
        teacherService.delete(teacher);
    }

    @RequestMapping("/getGroupsTeacher")
    @ResponseBody
    public String getGroups(int teacherId, HttpServletRequest request) {
        DataTablesWrapper dataTables = new DataTablesWrapper(request);

        Pageable pageable = PageRequest.of(dataTables.getPage(),
                dataTables.getLength(), Sort.by(dataTables.getOrders()));

        Page<Group> groups = groupService.getGroupsInTeacher(teacherId,
                dataTables.getFilter(), pageable);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Group.class, new GroupSerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", dataTables.getDraw());
        result.add("data", gson.toJsonTree(groups.getContent()));
        result.addProperty("recordsTotal",
                groupService.getCountGroupInTeacher(teacherId));
        result.addProperty("recordsFiltered",
                groups.getTotalElements());

        return result.toString();
    }

    @RequestMapping("/getNewGroupsTeacher")
    @ResponseBody
    public String getNewGroups(int teacherId, HttpServletRequest request) {
        DataTablesWrapper dataTables = new DataTablesWrapper(request);

        Pageable pageable = PageRequest.of(dataTables.getPage(),
                dataTables.getLength(), Sort.by(dataTables.getOrders()));

        Page<Group> groups = groupService.getGroupNotInTeacher(teacherId,
                dataTables.getFilter(), pageable);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Group.class, new GroupSerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", dataTables.getDraw());
        result.add("data", gson.toJsonTree(groups.getContent()));
        result.addProperty("recordsTotal",
                groupService.getCountGroupNotInTeacher(teacherId));
        result.addProperty("recordsFiltered",
                groups.getTotalElements());

        return result.toString();
    }

    @RequestMapping("/addGroupForTeacher")
    @ResponseBody
    public void addGroup(int teacherId, int groupId){
        teacherService.addGroup(teacherId, groupId);
    }

    @RequestMapping("/deleteGroupForTeacher")
    @ResponseBody
    public void deleteGroup(int teacherId, int groupId){
        teacherService.deleteGroup(teacherId, groupId);
    }

}
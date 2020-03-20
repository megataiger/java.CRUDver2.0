package mvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gsonSerialize.GroupSerialize;
import gsonSerialize.StudentEasySerialize;
import gsonSerialize.TeacherEasySerialize;
import mvc.wrappers.DataTablesWrapper;
import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import objectForStrokeBase.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import workWithBase.serviceInterfaces.GroupServiceInterface;
import workWithBase.serviceInterfaces.StudentServiceInterface;
import workWithBase.serviceInterfaces.TeacherServiceInterface;

import javax.servlet.http.HttpServletRequest;

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
    public String getGroups(HttpServletRequest request) {
        DataTablesWrapper dataTables = new DataTablesWrapper(request);

        Pageable pageable = PageRequest.of(dataTables.getPage(),
                dataTables.getLength(), Sort.by(dataTables.getOrders()));

        Page<Group> groups = groupService.getGroups(dataTables.getFilter(),
                pageable);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Group.class, new GroupSerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", dataTables.getDraw());
        result.add("data", gson.toJsonTree(groups.getContent()));
        result.addProperty("recordsTotal", groups.getTotalElements());
        result.addProperty("recordsFiltered", groups.getTotalElements());

        return result.toString();
    }

    @RequestMapping("/selectStudentGroup")
    @ResponseBody
    public String getStudents(int groupId, HttpServletRequest request) {
        DataTablesWrapper dataTables = new DataTablesWrapper(request);

        Pageable pageable = PageRequest.of(dataTables.getPage(),
                dataTables.getLength(), Sort.by(dataTables.getOrders()));

        Page<Student> students = studentService.getGroupStudents(groupId,
                dataTables.getFilter(), pageable);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Student.class, new StudentEasySerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", dataTables.getDraw());
        result.add("data", gson.toJsonTree(students.getContent()));
        result.addProperty("recordsTotal",
                studentService.getCountGroupStudents(groupId));
        result.addProperty("recordsFiltered", students.getTotalElements());

        return result.toString();
    }

    @RequestMapping("/getTeachersGroup")
    @ResponseBody
    public String getTeachers(int groupId, HttpServletRequest request) {
        DataTablesWrapper dataTables = new DataTablesWrapper(request);

        Pageable pageable = PageRequest.of(dataTables.getPage(),
                dataTables.getLength(), Sort.by(dataTables.getOrders()));

        Page<Teacher> teachers = teacherService.getTeacherInGroup(groupId,
                dataTables.getFilter(), pageable);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Teacher.class, new TeacherEasySerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", dataTables.getDraw());
        result.add("data", gson.toJsonTree(teachers.getContent()));
        result.addProperty("recordsTotal",
                teacherService.getCountTeacherInGroup(groupId));
        result.addProperty("recordsFiltered", teachers.getTotalElements());

        return result.toString();
    }

    @RequestMapping("/getNewTeachersGroup")
    @ResponseBody
    public String getNewTeachers(int groupId, HttpServletRequest request) {
        DataTablesWrapper dataTables = new DataTablesWrapper(request);

        Pageable pageable = PageRequest.of(dataTables.getPage(),
                dataTables.getLength(), Sort.by(dataTables.getOrders()));

        Page<Teacher> teachers = teacherService.getTeacherNotInGroup(groupId,
                dataTables.getFilter(), pageable);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Teacher.class, new TeacherEasySerialize())
                .create();

        JsonObject result = new JsonObject();

        result.addProperty("draw", dataTables.getDraw());
        result.add("data", gson.toJsonTree(teachers.getContent()));
        result.addProperty("recordsTotal",
                teacherService.getCountTeacherNotInGroup(groupId));
        result.addProperty("recordsFiltered",
                teachers.getTotalElements());

        return result.toString();
    }

    @RequestMapping("/setNumberGroup")
    @ResponseBody
    public void setNumberGroup(@RequestParam(name = "groupId") Group group,
                               int numberGroup) {
        group.setNumber(numberGroup);
        groupService.save(group);
    }

    @RequestMapping("/insertGroup")
    @ResponseBody
    public void insertGroup(Group group) {
        groupService.save(group);
    }

    @RequestMapping("/deleteGroup")
    @ResponseBody
    public void deleteGroup(@RequestParam(name = "groupId") Group group) {
        groupService.delete(group);
    }

    @RequestMapping("/addTeacherForGroup")
    @ResponseBody
    public void addTeacher(int groupId, int teacherId) {
        groupService.addTeacher(groupId, teacherId);
    }

    @RequestMapping("/deleteTeacherForGroup")
    @ResponseBody
    public void deleteTeacher(int groupId, int teacherId) {
        groupService.removeTeacher(groupId, teacherId);
    }
}
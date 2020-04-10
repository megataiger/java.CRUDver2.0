package mvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gsonSerialize.GroupSerialize;
import gsonSerialize.TeacherSerialize;
import mvc.messageBilder.MessageBilder;
import mvc.validators.TeacherValidator;
import mvc.wrappers.DataTablesWrapper;
import objectForStrokeBase.Gender;
import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import workWithBase.serviceInterfaces.GroupServiceInterface;
import workWithBase.serviceInterfaces.TeacherServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/teachers")
public class TeacherPageController {

    private TeacherServiceInterface teacherService;
    private GroupServiceInterface groupService;
    private TeacherValidator teacherValidator;
    private MessageBilder messageBilder;


    @Autowired
    public TeacherPageController(TeacherServiceInterface teacherService,
                                 GroupServiceInterface groupService,
                                 TeacherValidator teacherValidator,
                                 MessageBilder messageBilder) {
        this.teacherService = teacherService;
        this.groupService = groupService;
        this.teacherValidator = teacherValidator;
        this.messageBilder = messageBilder;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(teacherValidator);
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
    public JsonObject setNameTeacher
            (@ModelAttribute("teacherId") @Valid Teacher teacher,
             BindingResult result,
             String nameTeacher) {
        if (result.hasErrors()) {
            return messageBilder.createErrorMessage(result);
        } else {
            teacher.setName(nameTeacher);
            teacherService.save(teacher);
            return messageBilder.createSuccessMessage();
        }
    }

    @RequestMapping("/setBirthdayTeacher")
    @ResponseBody
    public JsonObject setBirthdayTeacher
            (@ModelAttribute("teacherId") @Valid Teacher teacher,
             BindingResult result,
             LocalDate newBirthday) {
        if (result.hasErrors()) {
            return messageBilder.createErrorMessage(result);
        } else {
            teacher.setDate(newBirthday);
            teacherService.save(teacher);
            return messageBilder.createSuccessMessage();
        }
    }

    @RequestMapping("/setGenderTeacher")
    @ResponseBody
    public JsonObject setGenderTeacher
            (@ModelAttribute("teacherId") Teacher teacher,
             BindingResult result,
             Gender newGender) {
        if (result.hasErrors()) {
            return messageBilder.createErrorMessage(result);
        } else {
            teacher.setGender(newGender);
            teacherService.save(teacher);
            return messageBilder.createSuccessMessage();
        }
    }

    @RequestMapping("/insertTeacher")
    @ResponseBody
    public void insertTeacher(Teacher teacher) {
        teacherService.save(teacher);
    }

    @RequestMapping("/deleteTeacher")
    @ResponseBody
    public JsonObject deleteTeacher
            (@ModelAttribute("teacherId") @Valid Teacher teacher,
             BindingResult result) {
        if (result.hasErrors()) {
            return messageBilder.createErrorMessage(result);
        } else {
            teacherService.delete(teacher);
            return messageBilder.createSuccessMessage();
        }
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
    public void addGroup(@RequestParam("teacherId") Teacher teacher,
                         @RequestParam("groupId") Group group){
        teacherService.addGroup(group, teacher);
    }

    @RequestMapping("/deleteGroupForTeacher")
    @ResponseBody
    @Transactional
    public void deleteGroup(@RequestParam("teacherId") Teacher teacher,
                            @RequestParam("groupId") Group group){
        teacherService.deleteGroup(group, teacher);
    }
}
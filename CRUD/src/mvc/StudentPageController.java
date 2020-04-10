package mvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import gsonSerialize.StudentSerialize;
import mvc.messageBilder.MessageBilder;
import mvc.validators.StudentValidator;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import workWithBase.serviceInterfaces.StudentServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/students")
public class StudentPageController {

    private StudentServiceInterface studentService;
    private StudentValidator studentValidator;
    private MessageBilder messageBilder;

    @Autowired
    public StudentPageController(StudentServiceInterface studentService,
                                 StudentValidator studentValidator,
                                 MessageBilder messageBilder) {
        this.studentService = studentService;
        this.studentValidator = studentValidator;
        this.messageBilder = messageBilder;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(studentValidator);
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
    public JsonObject updateNameStudent
            (@ModelAttribute(name = "idStudent") @Valid Student student,
             BindingResult result,
             String newNameStudent) {
        if (result.hasErrors()) {
            return messageBilder.createErrorMessage(result);
        } else {
            student.setName(newNameStudent);
            studentService.update(student);
            return messageBilder.createSuccessMessage();
        }
    }

    @RequestMapping(value = "/setBirthdayStudent")
    @ResponseBody
    public JsonObject updateBirthdayStudent
            (@ModelAttribute(name = "idStudent") @Valid Student idStudent,
             BindingResult result,
             LocalDate newBirthdayStudent) {
        if (result.hasErrors()) {
            return messageBilder.createErrorMessage(result);
        } else {
            idStudent.setDate(newBirthdayStudent);
            studentService.update(idStudent);
            return messageBilder.createSuccessMessage();
        }
    }

    @RequestMapping(value = "/getGender")
    @ResponseBody
    public JsonArray getGenderList
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

        return resultList;
    }

    @RequestMapping("/setGenderStudent")
    @ResponseBody
    public JsonObject updateGenderStudent
            (@ModelAttribute(name = "idStudent") @Valid Student student,
             BindingResult result,
             Gender genderStudent) {
        if (result.hasErrors()) {
            return messageBilder.createErrorMessage(result);
        } else {
            student.setGender(genderStudent);
            studentService.update(student);
            return messageBilder.createSuccessMessage();
        }
    }

    @RequestMapping("/setGroupStudent")
    @ResponseBody
    public JsonObject updateGroupStudent
            (@ModelAttribute(name = "idStudent") @Valid Student student,
             BindingResult result,
             @RequestParam(name = "numberGroup") Group group) {
        if (result.hasErrors()) {
            return messageBilder.createErrorMessage(result);
        } else {
            student.setGroup(group);
            studentService.update(student);
            return messageBilder.createSuccessMessage();
        }
    }

    @RequestMapping("/insertStudent")
    @ResponseBody
    public void insertStudent(Student student) {
        studentService.insert(student);
    }

    @RequestMapping("/deleteStudent")
    @ResponseBody
    public JsonObject deleteStudent
            (@ModelAttribute(name = "idStudent") @Valid Student student,
             BindingResult result) {
        if (result.hasErrors()) {
            return messageBilder.createErrorMessage(result);
        } else {
            studentService.delete(student);
            return messageBilder.createSuccessMessage();
        }
    }
}
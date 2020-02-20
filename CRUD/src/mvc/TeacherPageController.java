package mvc;

import objectForStrokeBase.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import workWithBase.services.TeacherService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
public class TeacherPageController {

    private TeacherService teacherService;

    @Autowired
    public TeacherPageController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @RequestMapping("/selectTeachers")
    @ResponseBody
    public String getTeachers(HttpServletRequest request,
                              @RequestParam(name = "start") int page,
                              @RequestParam(name = "length") int length,
                              @RequestParam(name = "draw") String draw,
                              @RequestParam(name = "search[value]") String search,
                              @RequestParam(name = "order[0][dir]") String orderBy) {

        int orderColumn = Integer.parseInt(request.getParameter("order[0][column]"));
        String nameColumn = request.getParameter("columns[" + orderColumn + "][name]");

        orderBy = "ORDER BY " + nameColumn + " " + orderBy;

        return teacherService.getTeachers(page, length, search, orderBy, draw);
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

        teacherService.insertTeacher(name, birthday, gender);
    }

    @RequestMapping("/deleteTeacher")
    @ResponseBody
    public void deleteTeacher(@RequestParam(name = "idTeacher") int idTeacher) {
        teacherService.deleteTeacher(idTeacher);
    }

    @RequestMapping("/getGroupsTeacher")
    @ResponseBody
    public String getGroups(@RequestParam(name = "idTeacher") int idTeacher,
                            @RequestParam(name = "start") int page,
                            @RequestParam(name = "length") int length,
                            @RequestParam(name = "draw") String draw,
                            @RequestParam(name = "search[value]") String search,
                            @RequestParam(name = "order[0][dir]") String orderBy) {
        return teacherService.getGroups(idTeacher, page, length, search, orderBy, draw);
    }

    @RequestMapping("/getNewGroupsTeacher")
    @ResponseBody
    public String getNewGroups(@RequestParam(name = "idTeacher") int idTeacher,
                            @RequestParam(name = "start") int page,
                            @RequestParam(name = "length") int length,
                            @RequestParam(name = "draw") String draw,
                            @RequestParam(name = "search[value]") String search,
                            @RequestParam(name = "order[0][dir]") String orderBy) {
        return teacherService.getNewGroups(idTeacher, page, length, search, orderBy, draw);
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
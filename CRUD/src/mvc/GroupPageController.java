package mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import workWithBase.services.GroupService;
import javax.servlet.http.HttpServletRequest;

@Controller
public class GroupPageController {

    private GroupService groupService;
    @Autowired
    public GroupPageController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping("/selectGroups")
    @ResponseBody
    public String getGroups(HttpServletRequest request,
                            @RequestParam(name = "start") int page,
                            @RequestParam(name = "length") int length,
                            @RequestParam(name = "draw") String draw,
                            @RequestParam(name = "search[value]") String search,
                            @RequestParam(name = "order[0][dir]") String orderBy) {
        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");

        orderBy = " ORDER BY " + columnName + " " + orderBy;

        return groupService.getGroups(page, length, search, orderBy, draw);
    }

    @RequestMapping("/selectStudentGroup")
    @ResponseBody
    public String getStudents(HttpServletRequest request,
                              @RequestParam(name = "numberGroup") int number,
                              @RequestParam(name = "start") int page,
                              @RequestParam(name = "length") int length,
                              @RequestParam(name = "draw") String draw,
                              @RequestParam(name = "search[value]") String search,
                              @RequestParam(name = "order[0][dir]") String orderBy) {
        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");

        orderBy = "ORDER BY " + columnName + " " + orderBy;

        return groupService.getStudents(number, page, length, orderBy, search, draw);
    }

    @RequestMapping("/getTeachersGroup")
    @ResponseBody
    public String getTeachers(HttpServletRequest request,
                              @RequestParam(name = "numberGroup") int number,
                              @RequestParam(name = "start") int page,
                              @RequestParam(name = "length") int length,
                              @RequestParam(name = "draw") String draw,
                              @RequestParam(name = "search[value]") String search,
                              @RequestParam(name = "order[0][dir]") String orderBy) {

        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");

        orderBy = "ORDER BY " + columnName + " " + orderBy;

        return groupService.getTeachers(number, page, length, search, orderBy, draw);
    }

    @RequestMapping("/getNewTeachersGroup")
    @ResponseBody
    public String getNewTeachers(HttpServletRequest request,
                                 @RequestParam(name = "numberGroup") int number,
                                 @RequestParam(name = "start") int page,
                                 @RequestParam(name = "length") int length,
                                 @RequestParam(name = "draw") String draw,
                                 @RequestParam(name = "search[value]") String search,
                                 @RequestParam(name = "order[0][dir]") String orderBy) {
        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");

        orderBy = "ORDER BY " + columnName + " " + orderBy;

        return groupService.getNewTeachers(number, page, length, search, orderBy, draw);
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
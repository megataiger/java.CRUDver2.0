package mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import workWithBase.serviceInterfaces.GroupServiceInterface;
import workWithBase.services.GroupService;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class GroupPageController {

    private GroupServiceInterface groupService;
    @Autowired
    public GroupPageController(GroupServiceInterface groupService) {
        this.groupService = groupService;
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
        parameters.put("draw", draw);

        return groupService.getGroups(parameters);
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

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("groupNumber", number);
        parameters.put("page", page);
        parameters.put("length", length);
        parameters.put("draw", draw);
        parameters.put("filter", search);
        parameters.put("order", order);

        return groupService.getStudents(parameters);
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

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("groupNumber", number);
        parameters.put("page", page);
        parameters.put("length", length);
        parameters.put("draw", draw);
        parameters.put("filter", search);
        parameters.put("order", order);
        parameters.put("number", number);

        return groupService.getTeachers(parameters);
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
        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");

        order = columnName + " " + order;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("groupNumber", number);
        parameters.put("page", page);
        parameters.put("length", length);
        parameters.put("draw", draw);
        parameters.put("filter", search);
        parameters.put("order", order);
        parameters.put("number", number);

        return groupService.getNewTeachers(parameters);
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
package servlets.group;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import objectForStrokeBase.Teacher;
import org.json.JSONArray;
import org.json.JSONObject;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.StudentDAO;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class getTeachers extends HttpServlet {
    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
                        throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int number = Integer.parseInt(request.getParameter("numberGroup"));
        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.selectGroupByNumber(number);

        PrintWriter writer = response.getWriter();

        JSONObject result = new JSONObject();
        JSONArray data = new JSONArray();

        int page = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");
        String orderBy = request.getParameter("order[0][dir]");
        String draw = request.getParameter("draw");
        String search = request.getParameter("search[value]");

        TeacherDAO teacherDAO = new TeacherDAO();

        String sort = "ORDER BY " + columnName + " " + orderBy;

        List<Teacher> teachers = teacherDAO.getTeachersForGroup(group.getId(), page, length, sort, search);

        data = getResult(teachers, data);

        result.put("draw", draw);
        result.put("data", data);
        result.put("recordsTotal", teacherDAO.getTeachersForGroup(group.getId(), "").size());
        result.put("recordsFiltered", teacherDAO.getTeachersForGroup(group.getId(), search).size());

        writer.println(result);

        teacherDAO.close();

        groupDAO.close();
    }

    private JSONArray getResult(List<Teacher> teachers, JSONArray array) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        for (Teacher e : teachers) {
            JSONObject teacher = new JSONObject();
            teacher.put("name", e.getName());
            teacher.put("birthday", formatter.format(e.getDate()));
            teacher.put("id", e.getId());

            array.put(teacher);
        }

        return array;
    }
}

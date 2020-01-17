package servlets.group;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import org.json.JSONArray;
import org.json.JSONObject;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class selectStudent extends HttpServlet {
    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();

        JSONObject result = new JSONObject();
        JSONArray data = new JSONArray();

        int page = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        int numberGroup = Integer.parseInt(request.getParameter("numberGroup"));
        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");
        String orderBy = request.getParameter("order[0][dir]");
        String draw = request.getParameter("draw");
        String search = request.getParameter("search[value]");

        StudentDAO studentDAO = new StudentDAO();
        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.selectGroupByNumber(numberGroup);

        String sort = "ORDER BY " + columnName + " " + orderBy;

        List<Student> students = studentDAO.findByGroup(group.getId(), page, length, sort, search);


        data = getResult(students, data);

        result.put("draw", draw);
        result.put("data", data);
        result.put("recordsTotal", studentDAO.findByGroup(group.getId(), "").size());
        result.put("recordsFiltered", studentDAO.findByGroup(group.getId(), search).size());

        writer.println(result);

        studentDAO.close();
    }

    private JSONArray getResult  (List<Student> students, JSONArray array) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        for (Student e : students) {
            JSONObject student = new JSONObject();
            student.put("name", e.getName());
            student.put("birthday", formatter.format(e.getDate()));

            array.put(student);
        }

        return array;
    }
}

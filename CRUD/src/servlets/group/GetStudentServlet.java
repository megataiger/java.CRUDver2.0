package servlets.group;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gsonSerialize.StudentEasySerialize;
import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetStudentServlet extends HttpServlet {
    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();

        JsonObject result = new JsonObject();

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

        List students = studentDAO.findByGroup(group.getId(), page, length, sort, search);

        Gson gson = new GsonBuilder().registerTypeAdapter(Student.class, new StudentEasySerialize())
                .create();

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(students));
     //   result.addProperty("recordsTotal", studentDAO.findByGroup(group.getId(), "").size());
     //   result.addProperty("recordsFiltered", studentDAO.findByGroup(group.getId(), search).size());

        writer.println(result);

        studentDAO.close();
    }
}

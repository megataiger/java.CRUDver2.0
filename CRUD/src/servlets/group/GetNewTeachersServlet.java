package servlets.group;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gsonSerialize.TeacherEasySerialize;
import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetNewTeachersServlet extends HttpServlet {
    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        int number = Integer.parseInt(request.getParameter("numberGroup"));
        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.selectGroupByNumber(number);

        PrintWriter writer = response.getWriter();

        JsonObject result = new JsonObject();

        int page = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");
        String orderBy = request.getParameter("order[0][dir]");
        String draw = request.getParameter("draw");
        String search = request.getParameter("search[value]");

        TeacherDAO teacherDAO = new TeacherDAO();

        String sort = "ORDER BY " + columnName + " " + orderBy;

        Gson gson = new GsonBuilder().registerTypeAdapter(Teacher.class, new TeacherEasySerialize()).create();

        List teachers = teacherDAO.getNewTeachersForGroup(group.getId(), page, length, sort, search);

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(teachers));
        result.addProperty("recordsTotal", teacherDAO.getNewTeachersForGroup(group.getId(), "").size());
        result.addProperty("recordsFiltered", teacherDAO.getNewTeachersForGroup(group.getId(), search).size());

        writer.println(result);

        teacherDAO.close();

        groupDAO.close();
    }
}

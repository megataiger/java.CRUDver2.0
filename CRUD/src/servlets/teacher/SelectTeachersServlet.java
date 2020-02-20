package servlets.teacher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gsonSerialize.TeacherSerialize;
import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SelectTeachersServlet extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JsonObject result = new JsonObject();

        String draw = request.getParameter("draw");
        int page = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String order = request.getParameter("order[0][dir]");
        int orderColumn = Integer.parseInt(request.getParameter("order[0][column]"));
        String nameColumn = request.getParameter("columns[" + orderColumn + "][name]");
        String filter = request.getParameter("search[value]");

        String orderBy = "ORDER BY " + nameColumn + " " + order;

        TeacherDAO teacherDAO = new TeacherDAO();

        List teachers = teacherDAO.selectTeachers(page, length, filter, orderBy);

        Gson gson = new GsonBuilder().registerTypeAdapter(Teacher.class, new TeacherSerialize()).create();

        result.addProperty("draw", draw);
        result.addProperty("recordsTotal", teacherDAO.getAll().size());
        result.addProperty("recordsFiltered", teacherDAO.selectTeachers(filter));
        result.add("data", gson.toJsonTree(teachers));


        PrintWriter writer = response.getWriter();
        writer.print(result);

        teacherDAO.close();
    }
}
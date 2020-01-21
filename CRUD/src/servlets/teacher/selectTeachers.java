package servlets.teacher;

import objectForStrokeBase.Teacher;
import org.json.JSONArray;
import org.json.JSONObject;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class selectTeachers extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        JSONObject result = new JSONObject();

        String draw = request.getParameter("draw");
        int page = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String order = request.getParameter("order[0][dir]");
        int orderColumn = Integer.parseInt(request.getParameter("order[0][column]"));
        String nameColumn = request.getParameter("columns[" + orderColumn +"][name]");
        String filter = request.getParameter("search[value]");

        String orderBy = "ORDER BY " + nameColumn + " " + order;

        TeacherDAO teacherDAO = new TeacherDAO();

        List<Teacher> teachers = teacherDAO.selectTeachers(page, length, filter, orderBy);

        JSONArray array = new JSONArray();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        for(Teacher e : teachers) {
            JSONObject arrayTeacher = new JSONObject();

            arrayTeacher.put("DT_RowId", e.getId());
            arrayTeacher.put("id", e.getId());
            arrayTeacher.put("name", e.getName());
            arrayTeacher.put("birthday", formatter.format(e.getDate()));
            if (e.getGender().equals("MAN")) {
                arrayTeacher.put("gender", "М");
            } else if (e.getGender().equals("WOMAN")) {
                arrayTeacher.put("gender", "Ж");
            } else {
                arrayTeacher.put("gender", "-");
            }
            arrayTeacher.put("delete", e.getId());

            array.put(arrayTeacher);
        }

        result.put("draw", draw);
        result.put("recordsTotal", teacherDAO.getAll().size());
        result.put("recordsFiltered", teacherDAO.selectTeachers(filter).size());
        result.put("data", array);


        PrintWriter writer = response.getWriter();
        writer.print(result);

        teacherDAO.close();
    }
}
package servlets;

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
import java.util.List;

public class experimentSelect extends HttpServlet {
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
        int orderColoumn = Integer.parseInt(request.getParameter("order[0][column]"));
        String nameColumn = request.getParameter("columns[" + orderColoumn +"][name]");

        StringBuilder query = new StringBuilder();
        query.append("FROM Teacher WHERE LOWER(name) LIKE ");

        if (request.getParameter("nameTeacher").equals("")) {
            String name = "'%" + request.getParameter("nameTeacher") + "%' ";
            query.append(name);
        } else {
            String name = "'%" + request.getParameter("nameTeacher") + "%' ";
            query.append(name);
        }

        if (!request.getParameter("dateTeacher").equals("")) {
            String date = "'%" + request.getParameter("dateTeacher") + "%'";
            query.append("AND LOWER(birthday) LIKE ");
            query.append(date);
            query.append(" ");
        }

        if ((!request.getParameter("genderTeacher").equals(""))
                &&(!request.getParameter("genderTeacher").equals("-"))) {
            String gender = request.getParameter("genderTeacher");
            query.append("AND gender = '");
            query.append(gender);
            query.append("' ");
        }

        if(nameColumn.equals("id")){
            nameColumn = "id";
        } else if (nameColumn.equals("name")) {
            nameColumn = "name";
        } else if (nameColumn.equals("date")) {
            nameColumn = "birthday";
        } else if (nameColumn.equals("gender")) {
            nameColumn = "gender";
        }

        query.append("ORDER BY ");
        query.append(nameColumn);
        query.append(" ");
        query.append(order);

        String filter = query.toString();

        TeacherDAO teacherDAO = new TeacherDAO();

        List<Teacher> teachers = teacherDAO.findByFilter(filter, page, length);

        JSONArray array = new JSONArray();

        for(Teacher e : teachers) {
            JSONObject arrayTeacher = new JSONObject();

            arrayTeacher.put("DT_RowId", e.getId());
            arrayTeacher.put("id", e.getId());
            arrayTeacher.put("name", e.getName());
            arrayTeacher.put("date", e.getDate());
            arrayTeacher.put("gender", e.getGender());
            arrayTeacher.put("delete", "<a class=\"deleteTeacher\" href=\"\"><img title='Удалить' src=\"bascet.png\"></a>" +
                    "<a class=\"listOfGroup\" href=\"\"><img src=\"list.png\" title='Список групп'></a>");
            array.put(arrayTeacher);
        }

        result.put("draw", draw);
        result.put("recordsTotal", teacherDAO.getAll().size());
        result.put("recordsFiltered", teacherDAO.countFindByFilter(filter));
        result.put("data", array);


        PrintWriter writer = response.getWriter();
        writer.print(result);

        teacherDAO.close();
    }
}
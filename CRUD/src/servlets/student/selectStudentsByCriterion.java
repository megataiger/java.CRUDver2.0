package servlets.student;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import org.json.JSONArray;
import org.json.JSONObject;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.StudentDAO;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class selectStudentsByCriterion extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int page = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");
        String orderBy = request.getParameter("order[0][dir]");
        String draw = request.getParameter("draw");
        String search = request.getParameter("search[value]");


        JSONObject result = new JSONObject();
        result.put("draw", draw);

        StudentDAO studentDAO = new StudentDAO();

        StringBuilder query = new StringBuilder();
        query.append("FROM Student WHERE LOWER(name) LIKE '%");
        query.append(search);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate birthday = LocalDate.parse(search, formatter);
            query.append("%' OR LOWER(birthday) LIKE '%");
            query.append(birthday);
        } catch (DateTimeParseException e) {
            query.append("%' OR LOWER(birthday) LIKE '%");
            query.append(search);
        }
        query.append("%' OR gender LIKE '");
        query.append(search);

        try {
            GroupDAO groupDAO = new GroupDAO();
            int number = Integer.parseInt(search);
            Group group = groupDAO.selectGroupByNumber(number);
            query.append("' OR group_id LIKE '");
            query.append(group.getId());
            query.append("'");
        } catch (NoResultException e) {
            query.append("'");
        } catch (NumberFormatException e) {
            query.append("'");
        }

        query.append(" ORDER BY ");
        query.append(columnName);
        query.append(" ");
        query.append(orderBy);

        String filter = query.toString();

        JSONArray data = new JSONArray();
        List<Student> students = studentDAO.findByFilter(filter, page, length);

        result.put("data", getResult(students, data));
        result.put("recordsTotal", studentDAO.getAll().size());
        result.put("recordsFiltered", studentDAO.findByFilter(filter).size());

        studentDAO.close();

        PrintWriter writer = response.getWriter();
        writer.print(result);
    }

    private JSONArray getResult(List<Student> list, JSONArray data) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            for (Student e : list) {
                JSONObject student = new JSONObject();

                student.put("id", e.getId());
                student.put("name", e.getName());
                student.put("birthday", formatter.format(e.getDate()));
                if (e.getGender().equals("MAN")) {
                    student.put("gender", "М");
                } else if (e.getGender().equals("WOMAN")) {
                    student.put("gender", "Ж");
                } else {
                    student.put("gender", "-");
                }
                if (e.getGroup() != null) {
                    student.put("group", e.getGroup().getNumber());
                } else {
                    student.put("group", "-");
                }
                student.put("delete", e.getId());

                data.put(student);
            }

        return data;
    }
}

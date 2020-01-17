package servlets;



import objectForStrokeBase.Student;
import org.json.JSONArray;
import org.json.JSONObject;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class example extends HttpServlet {

    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        JSONObject result = new JSONObject();

        String draw = request.getParameter("draw");
        int page = Integer.parseInt(request.getParameter("start"));

        StudentDAO studentDAO = new StudentDAO();
        List<Student> students = studentDAO.get(page);

        JSONArray arrayStudent = new JSONArray();

        int i = 0;

        for (Student e : students) {
            JSONArray array = new JSONArray();
            array.put(e.getId());
            array.put(e.getName());
            array.put(e.getDate());
            array.put(e.getGender());
            array.put(e.getGroup());
            arrayStudent.put(array);
            System.out.println(array);
        }

        result.put("draw", draw);
        result.put("recordsTotal", i);
        result.put("recordsFiltered", i);
        result.put("data", arrayStudent);

        PrintWriter writer = response.getWriter();
        writer.print(result);

        studentDAO.close();
    }
}
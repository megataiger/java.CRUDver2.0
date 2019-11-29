package servlets.student;

import objectForStrokeBase.Student;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class deleteStudent extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("number"));
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.findById(id);
        studentDAO.delete(student);
        resp.sendRedirect("http://10.0.16.10:8080/TestProject_war_exploded/basic.jsp");
    }
}

package servlets.student;

import objectForStrokeBase.Student;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class setDate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        String string = request.getParameter("newDate");
        LocalDate newDate = LocalDate.parse(string);
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.findById(id);
        student.setBirthdayStudent(newDate);
        PrintWriter writer = response.getWriter();
        try {
            studentDAO.update(student);
            writer.println(1);
        } catch (Exception e) {
            writer.println(0);
        }
    }
}

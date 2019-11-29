package servlets.student;

import objectForStrokeBase.Gender;
import objectForStrokeBase.Student;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class setGenderStudent extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String string = request.getParameter("gender");
        int id = Integer.parseInt(request.getParameter("id"));
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.findById(id);
        if(string.equals(Gender.MAN.toString())) {
            student.setGenderStudent(Gender.MAN);
            studentDAO.update(student);
            writer.println(1);
        } else {
            student.setGenderStudent(Gender.WOMAN);
            studentDAO.update(student);
            writer.println(1);
        }
    }
}

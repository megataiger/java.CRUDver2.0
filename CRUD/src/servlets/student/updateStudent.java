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

public class updateStudent extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String birthday = request.getParameter("date");
        LocalDate date = LocalDate.parse(birthday);
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.findById(id);
        if (!student.getName().equals(name)) {
            student.setNameStudent(name);
            studentDAO.update(student);
            writer.print(1);
        } else if (!student.getDate().equals(date)) {
            student.setBirthdayStudent(date);
            studentDAO.update(student);
            writer.print(1);
        } else {
            writer.print(0);
        }
    }
}

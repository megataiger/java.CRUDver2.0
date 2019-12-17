package servlets.student;

import objectForStrokeBase.Student;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class setBirthdayStudent extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("idStudent"));
        String date = request.getParameter("newBirthdayStudent");
        LocalDate birthday = LocalDate.parse(date);

        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.findById(id);

        student.setBirthdayStudent(birthday);
        studentDAO.update(student);

        studentDAO.close();
    }
}

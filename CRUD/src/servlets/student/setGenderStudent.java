package servlets.student;

import objectForStrokeBase.Gender;
import objectForStrokeBase.Student;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class setGenderStudent extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("idStudent"));
        String string = request.getParameter("genderStudent");

        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.findById(id);

        if(string.equals(Gender.MAN.toString())) {
            student.setGenderStudent(Gender.MAN);
            studentDAO.update(student);
        } else {
            student.setGenderStudent(Gender.WOMAN);
            studentDAO.update(student);
        }

        studentDAO.close();
    }
}
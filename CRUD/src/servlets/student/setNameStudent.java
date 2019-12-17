package servlets.student;

import objectForStrokeBase.Student;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class setNameStudent extends HttpServlet {

    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("idStudent"));
        String name = request.getParameter("newNameStudent");

        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.findById(id);

        if (!student.getName().equals(name)) {
            student.setNameStudent(name);
            studentDAO.update(student);
        }

        studentDAO.close();
    }
}

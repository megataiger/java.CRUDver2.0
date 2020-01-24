package servlets.student;

import objectForStrokeBase.Student;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteStudentServlet extends HttpServlet {

    @Override
    protected void doGet
            (HttpServletRequest req, HttpServletResponse resp) {

        int id = Integer.parseInt(req.getParameter("idStudent"));

        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.findById(id);

        studentDAO.delete(student);

        studentDAO.close();
    }
}

package servlets.student;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class setGroupStudent extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("idStudent"));
        int number = Integer.parseInt(request.getParameter("numberGroup"));

        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.findById(id);

        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.selectGroupByNumber(number);

        student.setGroupStudent(group);
        studentDAO.update(student);

        studentDAO.close();
        groupDAO.close();
    }
}

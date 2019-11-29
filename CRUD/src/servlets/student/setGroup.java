package servlets.student;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class setGroup extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        int id = Integer.parseInt(request.getParameter("id"));
        int number = Integer.parseInt(request.getParameter("number"));
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.findById(id);
        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.selectGroupByNumber(number);
        student.setGroupStudent(group);
        studentDAO.update(student);
        PrintWriter writer = response.getWriter();
        writer.println("GOOD");
    }
}

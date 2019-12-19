package servlets.teacher;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class addGroupForTeacher extends HttpServlet {
    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int number = Integer.parseInt(request.getParameter("numberGroup"));
        int idTeacher = Integer.parseInt(request.getParameter("idTeacher"));

        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.selectGroupByNumber(number);

        TeacherDAO teacherDAO = new TeacherDAO();
        Teacher teacher = teacherDAO.findById(idTeacher);

        teacher.addGroup(group);
        teacherDAO.update(teacher);

        teacherDAO.close();
        groupDAO.close();
    }
}

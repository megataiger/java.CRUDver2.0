package servlets.group;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddTeacherForGroupServlet extends HttpServlet {

    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int numberGroup = Integer.parseInt(request.getParameter("numberGroup"));
        int idTeacher = Integer.parseInt(request.getParameter("idTeacher"));

        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.selectGroupByNumber(numberGroup);

        TeacherDAO teacherDAO = new TeacherDAO();
        Teacher teacher = teacherDAO.findById(idTeacher);

        group.addTeacher(teacher);
        groupDAO.update(group);

        teacherDAO.close();
        groupDAO.close();
    }
}

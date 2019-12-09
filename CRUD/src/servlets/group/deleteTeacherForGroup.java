package servlets.group;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class deleteTeacherForGroup extends HttpServlet {

    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int groupNumber = Integer.parseInt(request.getParameter("number"));
        int id = Integer.parseInt(request.getParameter("id"));

        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.selectGroupByNumber(groupNumber);

        TeacherDAO teacherDAO = new TeacherDAO();
        Teacher teacher = teacherDAO.findById(id);

        group.removeTeacher(teacher);
        groupDAO.update(group);

        teacherDAO.close();
        groupDAO.close();
    }
}

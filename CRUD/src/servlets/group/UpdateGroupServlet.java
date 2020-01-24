package servlets.group;

import objectForStrokeBase.Group;
import workWithBase.daoClasses.GroupDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateGroupServlet extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("idGroup"));
        int number = Integer.parseInt(request.getParameter("numberGroup"));

        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.findById(id);

        group.set(number);
        groupDAO.update(group);

        groupDAO.close();
    }
}

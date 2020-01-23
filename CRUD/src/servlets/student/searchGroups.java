package servlets.student;

import objectForStrokeBase.Group;
import org.json.JSONArray;
import workWithBase.daoClasses.GroupDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class searchGroups extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int number = Integer.parseInt(request.getParameter("number"));
        GroupDAO groupDAO = new GroupDAO();
        PrintWriter writer = response.getWriter();

        JSONArray numbers = new JSONArray();

        List<Group> groups = groupDAO.searchGroup(number);

        for (Group e : groups) {
            numbers.put(e.getNumber());
        }

        writer.println(numbers);
    }
}
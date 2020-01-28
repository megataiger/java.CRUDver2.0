package servlets.teacher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gsonSerialize.GroupSerialize;
import objectForStrokeBase.Group;
import workWithBase.daoClasses.GroupDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetNewGroupsForTeacherServlet extends HttpServlet {
    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        JsonObject result = new JsonObject();

        String draw = request.getParameter("draw");
        int page = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String number = request.getParameter("search[value]");
        String order = request.getParameter("order[0][dir]");

        if (request.getParameter("idTeacher") != null) {

            int id = Integer.parseInt(request.getParameter("idTeacher"));

            GroupDAO groupDAO = new GroupDAO();

            List groups = groupDAO.getNewGroupForTeacher(id, page, length, order, number);

            Gson gson = new GsonBuilder().registerTypeAdapter(Group.class, new GroupSerialize())
                    .create();

            result.add("data", gson.toJsonTree(groups));
            result.addProperty("draw", draw);
            result.addProperty("recordsTotal", groupDAO.getNewGroupForTeacher(id, "").size());
            result.addProperty("recordsFiltered", groupDAO.getNewGroupForTeacher(id, number).size());


            PrintWriter writer = response.getWriter();
            writer.println(result);

            groupDAO.close();
        } else {
            PrintWriter writer = response.getWriter();
            writer.println(result);
        }
    }
}

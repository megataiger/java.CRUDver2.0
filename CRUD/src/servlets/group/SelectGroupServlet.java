package servlets.group;

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

public class SelectGroupServlet extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();

        GroupDAO groupDAO = new GroupDAO();

        int page = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");
        String orderBy = request.getParameter("order[0][dir]");
        String draw = request.getParameter("draw");
        String search = request.getParameter("search[value]");

        orderBy = " ORDER BY " + columnName + " " + orderBy;

        JsonObject result = new JsonObject();

        Gson gson = new GsonBuilder().registerTypeAdapter(Group.class, new GroupSerialize()).create();

        List groups = groupDAO.getGroups(search, page, length, orderBy);

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(groups));
        result.addProperty("recordsTotal", groupDAO.getAll().size());
        result.addProperty("recordsFiltered", groupDAO.getGroups(search).size());

        groupDAO.close();

        writer.println(result);
    }
}

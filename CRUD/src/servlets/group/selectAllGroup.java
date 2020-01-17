package servlets.group;

import objectForStrokeBase.Group;
import org.json.JSONArray;
import org.json.JSONObject;
import workWithBase.daoClasses.GroupDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class selectAllGroup extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();

        GroupDAO groupDAO = new GroupDAO();

        int page = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String orderBy = request.getParameter("order[0][dir]");
        String draw = request.getParameter("draw");
        String search = request.getParameter("search[value]");

        JSONObject result = new JSONObject();
        JSONArray data = new JSONArray();

        List<Group> groups = groupDAO.getGroups(search, page, length, orderBy);

        data = getResult(groups, data);

        result.put("draw", draw);
        result.put("data", data);
        result.put("recordsTotal", groupDAO.getAll().size());
        result.put("recordsFiltered", groupDAO.getGroups(search).size());

        groupDAO.close();

        writer.println(result);
    }

    private JSONArray getResult(List<Group> groups, JSONArray array) {
        for (Group e : groups) {
            JSONObject group = new JSONObject();

            group.put("id", e.getId());
            group.put("number", e.getNumber());

            array.put(group);
        }

        return array;
    }
}

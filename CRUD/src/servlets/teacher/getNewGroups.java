package servlets.teacher;

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

public class getNewGroups extends HttpServlet {
    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        JSONObject result = new JSONObject();

        String draw = request.getParameter("draw");
        int page = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String number = request.getParameter("search[value]");
        String order = request.getParameter("order[0][dir]");

        if (request.getParameter("idTeacher") != null) {

            int id = Integer.parseInt(request.getParameter("idTeacher"));

            JSONArray data = new JSONArray();

            GroupDAO groupDAO = new GroupDAO();

            data = getResultForSearch(data, groupDAO.getNewGroupForTeacher(id, page, length, order, number));

            result.put("data", data);
            result.put("draw", draw);
            result.put("recordsTotal", groupDAO.getNewGroupForTeacher(id, "").size());
            result.put("recordsFiltered", groupDAO.getNewGroupForTeacher(id, number).size());


            PrintWriter writer = response.getWriter();
            writer.println(result);

            groupDAO.close();
        } else {
            PrintWriter writer = response.getWriter();
            writer.println(result);
        }
    }

    private JSONArray getResultForSearch(JSONArray data, List<Group> resultList) {
        for (Group e : resultList) {
            JSONObject group = new JSONObject();
            group.put("number", e.getNumber());
            data.put(group);
        }

        return data;
    }
}

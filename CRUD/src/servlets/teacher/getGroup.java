package servlets.teacher;

import org.json.JSONArray;
import org.json.JSONObject;
import workWithBase.daoClasses.GroupDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class getGroup extends HttpServlet {
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

        if (request.getParameter("idTeacher") != null) {

            int id = Integer.parseInt(request.getParameter("idTeacher"));

            JSONArray data = new JSONArray();

            GroupDAO groupDAO = new GroupDAO();
            String number = request.getParameter("search[value]");

            String order = request.getParameter("order[0][dir]");

            data = getResultForSearch(data, groupDAO.findByConWithTeacher(id, number, page, length, order));

            result.put("data", data);
            result.put("draw", draw);
            result.put("recordsTotal", groupDAO.findByConWithTeacher(id).size());
            result.put("recordsFiltered", groupDAO.findByConWithTeacher(id).size());

            PrintWriter writer = response.getWriter();
            writer.println(result);

            groupDAO.close();
        } else {
            JSONObject group = new JSONObject();
            group.put("number", "");
            group.put("delete", "<a class=\"deleteGroup\" href=\"\">" +
                    "<img title='Удалить' src=\"bascet.png\"></a>");

            result.put("data", group);
            result.put("draw", draw);
            result.put("recordsTotal", "0");
            result.put("recordsFiltered", "0");

            PrintWriter writer = response.getWriter();
            writer.println(result);
        }
    }

    private JSONArray getResultForSearch(JSONArray data, List<Object> resultList) {
            for (Object e : resultList) {
                JSONObject group = new JSONObject();
                group.put("number", e);
                group.put("delete", "<a class=\"deleteGroup\" href=\"" + e +
                        "\"><img title='Удалить' src=\"bascet.png\"></a>");
                data.put(group);
            }

        return data;
    }
}

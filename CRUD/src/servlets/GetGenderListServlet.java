package servlets;

import objectForStrokeBase.Gender;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GetGenderListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        String string = request.getParameter("gender");

        JSONArray result = new JSONArray();

        result = createListGender(result, string);

        PrintWriter writer = response.getWriter();
        writer.println(result);

    }

    private JSONArray createListGender(JSONArray array, String genderInBase) {

        for (Gender e : Gender.values()) {
            JSONObject gender = new JSONObject();
            gender.put("gender", e.getGender());
            gender.put("value", e.toString());
            if (genderInBase.equals(e.getGender())) {
                gender.put("selected", true);
            } else {
                gender.put("selected", false);
            }

            array.put(gender);
        }

        return array;
    }
}

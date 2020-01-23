package servlets;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class getGender extends HttpServlet {
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

    private JSONArray createListGender (JSONArray array, String genderInBase) {

        JSONObject gender = new JSONObject();
        if(genderInBase.equals("М")) {
            gender.put("gender", "М");
            gender.put("value", "MAN");
            gender.put("selected", true);

            array.put(gender);

            gender = new JSONObject();

            gender.put("gender", "Ж");
            gender.put("value", "WOMAN");
            gender.put("selected", false);

            array.put(gender);
        } else {
            gender.put("gender", "Ж");
            gender.put("value", "WOMAN");
            gender.put("selected", true);

            array.put(gender);

            gender = new JSONObject();

            gender.put("gender", "М");
            gender.put("value", "MAN");
            gender.put("selected", false);

            array.put(gender);
        }

        return array;
    }
}

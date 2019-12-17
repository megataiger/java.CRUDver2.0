package servlets.student;

import objectForStrokeBase.Gender;

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

        String result;
        if(string.equals(Gender.MAN.toString())) {
            result = "<option value=\"MAN\" selected=\"selected\">"
                    + Gender.MAN + "</option>\n" + "<option value=\"WOMAN\">"
                    + Gender.WOMAN + "</option>\n";
        } else {
            result = "<option value=\"MAN\">" + Gender.MAN + "</option>\n"
                    + "<option value=\"WOMAN\" selected=\"selected\">"
                    + Gender.WOMAN + "</option>\n";
        }

        PrintWriter writer = response.getWriter();
        writer.println(result);

    }
}

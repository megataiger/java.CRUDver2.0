package servlets.student;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gsonSerialize.StudentSerialize;
import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.StudentDAO;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SelectStudentServlet extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        int page = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        int columnNumber = Integer.parseInt(request.getParameter("order[0][column]"));
        String columnName = request.getParameter("columns[" + columnNumber + "][data]");
        String orderBy = request.getParameter("order[0][dir]");
        String draw = request.getParameter("draw");
        String search = request.getParameter("search[value]");


        JsonObject result = new JsonObject();
        result.addProperty("draw", draw);

        StudentDAO studentDAO = new StudentDAO();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate birthday = LocalDate.parse(search, formatter);
            search = birthday.toString();
        } catch (DateTimeParseException e) {
            System.out.println("Не является датой");
        }

        try {
            GroupDAO groupDAO = new GroupDAO();
            int number = Integer.parseInt(search);
            Group group = groupDAO.selectGroupByNumber(number);
            search = "" + group.getId();
        } catch (NoResultException e) {
            System.out.println("Совпадений не найдено");
        } catch (NumberFormatException e) {
            System.out.println("Не является числом");
        }

        orderBy = columnName + " " + orderBy;

        Gson gson = new GsonBuilder().registerTypeAdapter(Student.class, new StudentSerialize()).create();

        List students = studentDAO.findByFilter(search, page, length, orderBy);

        result.add("data", gson.toJsonTree(students));
        result.addProperty("recordsTotal", studentDAO.getAll().size());
        result.addProperty("recordsFiltered", studentDAO.findByFilter(search));

        studentDAO.close();

        PrintWriter writer = response.getWriter();
        writer.print(result);
    }
}

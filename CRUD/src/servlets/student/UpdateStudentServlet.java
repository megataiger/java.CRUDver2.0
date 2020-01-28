package servlets.student;

import objectForStrokeBase.Gender;
import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class UpdateStudentServlet extends HttpServlet {

    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("idStudent"));

        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.findById(id);

        if (request.getParameter("newNameStudent") != null) {
            String name = request.getParameter("newNameStudent");
            student.setNameStudent(name);
        }

        if (request.getParameter("newBirthdayStudent") != null) {
            String date = request.getParameter("newBirthdayStudent");
            LocalDate birthday = LocalDate.parse(date);
            student.setBirthdayStudent(birthday);
        }

        if (request.getParameter("genderStudent") != null) {
            String string = request.getParameter("genderStudent");
            if(string.equals(Gender.MAN.toString())) {
                student.setGenderStudent(Gender.MAN);
            } else {
                student.setGenderStudent(Gender.WOMAN);
            }
        }

        if (request.getParameter("numberGroup") != null) {
            int number = Integer.parseInt(request.getParameter("numberGroup"));
            GroupDAO groupDAO = new GroupDAO();
            Group group = groupDAO.selectGroupByNumber(number);

            student.setGroupStudent(group);
        }

        studentDAO.update(student);

        studentDAO.close();
    }
}

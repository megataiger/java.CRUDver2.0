package workWithBase.daoClasses;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import objectForStrokeBase.Gender;
import org.hibernate.Session;
import org.hibernate.Transaction;
import workWithBase.connectWithBase.HibernateSessionFactoryUtil;
import workWithBase.daoInterfaces.TeacherDAOInterface;
import workWithBase.connectWithBase.SuperTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO  {

    public void save(Teacher teacher) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(teacher);
        transaction.commit();
        session.close();
    }

    public List<Teacher> getAll() {
        List<Teacher> teachers = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from Teacher").list();
        return teachers;
    }

}
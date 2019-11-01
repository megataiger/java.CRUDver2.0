package workWithBase.daoClasses;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import objectForStrokeBase.Gender;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import workWithBase.connectWithBase.HibernateSessionFactoryUtil;
import workWithBase.daoInterfaces.StudentDAOInterface;
import workWithBase.connectWithBase.SuperTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public Student findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Student.class, id);
    }
    public void save(Student student) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(student);
        tx1.commit();
        session.close();
    }

    public List<Student> getAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().
                openSession();
        List<Student> students = session.createQuery("From Student").list();
        session.close();
        return students;
    }

    public void update(Student student) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(student);
        transaction.commit();
        session.close();
    }

    public void delete(Student student) {
        Session session;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        } catch (HibernateException e) {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.delete(student);
        transaction.commit();
        session.close();
    }

}
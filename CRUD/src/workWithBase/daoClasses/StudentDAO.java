package workWithBase.daoClasses;

import objectForStrokeBase.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import workWithBase.connectWithBase.HibernateSessionFactoryUtil;
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

    public List<Student> selectByName (String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().
                openSession();
        Query query = session.createQuery("from Student where lower(name) like :id");
        String param = "%" + name + "%";
        query.setParameter("id", param);
        List<Student> students = query.list();
        return students;
    }
}
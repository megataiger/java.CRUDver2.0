package workWithBase.daoClasses;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import workWithBase.connectWithBase.HibernateSessionFactoryUtil;

import java.time.LocalDate;
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

    public List<Student> findByName (String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().
                openSession();
        Query query = session.createQuery("from Student where lower(name) like :name");
        String param = "%" + name + "%";
        query.setParameter("name", param);
        List<Student> students = query.list();
       // session.close();
        return students;
    }

    public List<Student> findByDate (LocalDate date) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().
                openSession();
        Query query = session.createQuery("from Student where lower(birthday) like :date");
        String param = "%" + date + "%";
        query.setParameter("date", param);
        List<Student> students = query.list();
        session.close();
        return students;
    }

    public List<Student> findByGroup (Group group) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().
                openSession();
        Query query = session.createQuery("from Student where group_id = :id");
        query.setParameter("id", group.getId());
        List<Student> students = query.list();
        session.close();
        return students;
    }
}
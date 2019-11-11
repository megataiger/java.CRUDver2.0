package workWithBase.daoClasses;

import objectForStrokeBase.Teacher;
import workWithBase.connectWithBase.FactoryForDAO;
import workWithBase.daoInterfaces.TeacherDAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

public class TeacherDAO extends FactoryForDAO implements TeacherDAOInterface {

    public Teacher findById(int id) {
        EntityManager entityManager = factory.createEntityManager();
        Teacher teacher = entityManager.find(Teacher.class, id);
        return teacher;
    }

    public void save(Teacher teacher) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(teacher);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Teacher> getAll() {
        EntityManager entityManager = factory.createEntityManager();
        List<Teacher> teachers = entityManager.createQuery("From Teacher").getResultList();
        return teachers;
    }

    public void update(Teacher teacher) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(teacher);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void delete(Teacher teacher) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        teacher = entityManager.merge(teacher);
        entityManager.remove(teacher);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Teacher> findByName (String name) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from Teacher where lower(name) like :name");
        String param = "%" + name + "%";
        query.setParameter("name", param);
        List<Teacher> students = query.getResultList();
        entityManager.close();
        return students;
    }

    public List<Teacher> findByDate (LocalDate date) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from Teacher where lower(birthday) like :date");
        String param = "%" + date + "%";
        query.setParameter("date", param);
        List<Teacher> students = query.getResultList();
        entityManager.close();
        return students;
    }
}
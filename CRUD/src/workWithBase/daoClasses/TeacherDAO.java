package workWithBase.daoClasses;

import objectForStrokeBase.Teacher;
import org.hibernate.Session;
import org.hibernate.Transaction;
import workWithBase.connectWithBase.FactoryForDAO;

import javax.persistence.EntityManager;
import java.util.List;

public class TeacherDAO extends FactoryForDAO {

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
        entityManager.close();
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
}
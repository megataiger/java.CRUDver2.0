package workWithBase.daoClasses;

import objectForStrokeBase.Teacher;
import workWithBase.connectWithBase.FactoryForDAO;
import workWithBase.daoInterfaces.TeacherDAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

public class TeacherDAO extends FactoryForDAO implements TeacherDAOInterface {

    private EntityManager entityManager = factory.createEntityManager();

    public Teacher findById(int id) {
        Teacher teacher = entityManager.find(Teacher.class, id);
        return teacher;
    }

    public void save(Teacher teacher) {
        entityManager.getTransaction().begin();
        entityManager.persist(teacher);
        entityManager.getTransaction().commit();
    }

    public List<Teacher> getAll() {
        List<Teacher> teachers = entityManager.createQuery("From Teacher").getResultList();
        return teachers;
    }

    public void update(Teacher teacher) {
        entityManager.getTransaction().begin();
        entityManager.merge(teacher);
        entityManager.getTransaction().commit();
    }

    public void delete(Teacher teacher) {
        entityManager.getTransaction().begin();
        teacher = entityManager.merge(teacher);
        entityManager.remove(teacher);
        entityManager.getTransaction().commit();
    }

    public List<Teacher> findByName (String name) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from Teacher where lower(name) like :name");
        String param = "%" + name + "%";
        query.setParameter("name", param);
        List<Teacher> teachers = query.getResultList();
        return teachers;
    }

    public List<Teacher> findByDate (LocalDate date) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from Teacher where lower(birthday) like :date");
        String param = "%" + date + "%";
        query.setParameter("date", param);
        List<Teacher> teachers = query.getResultList();
        return teachers;
    }
}
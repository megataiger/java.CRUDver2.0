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
        return entityManager.find(Teacher.class, id);
    }

    public void save(Teacher teacher) {
        entityManager.getTransaction().begin();
        entityManager.persist(teacher);
        entityManager.getTransaction().commit();
    }

    public List getAll() {
        return entityManager.createQuery("From Teacher").getResultList();
    }

    public void update(Teacher teacher) {
        entityManager.getTransaction().begin();
        entityManager.merge(teacher);
        entityManager.getTransaction().commit();
    }

    public void delete(Teacher teacher) {
        entityManager.getTransaction().begin();
        entityManager.remove(teacher);
        entityManager.getTransaction().commit();
    }

    public List findByName (String name) {
        Query query = entityManager.createQuery("from Teacher where lower(name) like :name");
        String param = "%" + name + "%";
        query.setParameter("name", param);
        return query.getResultList();
    }

    public List findByDate (LocalDate date) {
        Query query = entityManager.createQuery("from Teacher where lower(birthday) like :date");
        String param = "%" + date + "%";
        query.setParameter("date", param);
        return query.getResultList();
    }

    public List<Object[]> findByWithoutConWithGroup(int id) {
        Query query = entityManager.createNativeQuery("SELECT teacher.id, name, birthday from Teacher WHERE teacher.id NOT IN " +
                "(SELECT teacher_id FROM group_teacher WHERE group_id = :id)");
        int param = id;
        query.setParameter("id", param);
        return query.getResultList();
    }

    public List<Object[]> findByWithoutConWithGroup(int id, String name) {
        Query query = entityManager.createNativeQuery("SELECT teacher.id, name, birthday from Teacher " +
                "WHERE lower(name) like :string AND teacher.id NOT IN " +
                "(SELECT teacher_id FROM group_teacher WHERE group_id = :id)");
        int param = id;
        query.setParameter("id", param);
        String paramName = "%" + name + "%";
        query.setParameter("string", paramName);
        return query.getResultList();
    }

    public List<Object[]> findByWithConGroup(int id, String name) {
        Query query = entityManager.createNativeQuery("SELECT teacher.id, name, birthday from Teacher" +
                " WHERE lower(name) like :string AND teacher.id IN " +
                "(SELECT teacher_id FROM group_teacher WHERE group_id = :id)");
        int param = id;
        String paramName = "%" + name + "%";
        query.setParameter("string", paramName);
        query.setParameter("id", param);
        return query.getResultList();
    }

    public void close() {
        entityManager.close();
    }
}
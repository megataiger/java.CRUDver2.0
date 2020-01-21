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

    public List selectTeachers
            (int page, int length, String filter, String orderBy) {
        Query query = entityManager.createQuery("SELECT t FROM Teacher t " +
                "WHERE (LOWER(t.name) LIKE '%" + filter + "%') " +
                "OR (t.date LIKE '%" + filter + "%') " +
                "OR (t.gender LIKE '%" + filter + "%') " + orderBy);
        return query.setMaxResults(length).setFirstResult(page).getResultList();
    }

    public List selectTeachers
            (String filter) {
        Query query = entityManager.createQuery("SELECT t FROM Teacher t " +
                "WHERE (LOWER(t.name) LIKE '%" + filter + "%') " +
                "OR (t.date LIKE '%" + filter + "%') " +
                "OR (t.gender LIKE '%" + filter + "%')");
        return query.getResultList();
    }

    public List getTeachersForGroup (int groupId, int page, int length, String orderBy, String filter) {
        Query query =
                entityManager.createQuery("SELECT t FROM Teacher t JOIN t.groups g " +
                        "WHERE g.id = " + groupId + " AND (LOWER(t.name) LIKE '%" +
                        filter + "%' OR t.date LIKE '%" + filter + "%') " + orderBy);
        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public List getTeachersForGroup (int groupId, String filter) {
        Query query =
                entityManager.createQuery("SELECT t FROM Teacher t JOIN t.groups g " +
                        "WHERE g.id = " + groupId + " AND (LOWER(t.name) LIKE '%" +
                        filter + "%' OR t.date LIKE '%" + filter + "%') ");
        return query.getResultList();
    }

    public List getNewTeachersForGroup (int groupId, int page, int length, String orderBy, String filter) {
        Query query =
                entityManager.createQuery("SELECT s FROM Teacher s " +
                        "WHERE (LOWER(s.name) LIKE '%" + filter + "%' OR s.date LIKE '%" + filter + "%') " +
                                "AND s NOT IN " +
                                "(SELECT t FROM Teacher t JOIN t.groups g " +
                                "WHERE g.id = " + groupId + ")" + orderBy);
        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public List getNewTeachersForGroup (int groupId, String filter) {
        Query query =
                entityManager.createQuery("SELECT s FROM Teacher s " +
                        "WHERE (LOWER(s.name) LIKE '%" +
                        filter + "%' OR s.date LIKE '%" + filter + "%') " +
                        "AND s NOT IN " +
                        "(SELECT t FROM Teacher t JOIN t.groups g " +
                        "WHERE g.id = " + groupId + ")");
        return query.getResultList();
    }


    public void close() {
        entityManager.close();
    }
}
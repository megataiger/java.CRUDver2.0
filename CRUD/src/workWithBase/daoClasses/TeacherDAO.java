package workWithBase.daoClasses;

import objectForStrokeBase.Teacher;
import org.springframework.stereotype.Repository;
import workWithBase.connectWithBase.FactoryForDAO;
import workWithBase.daoInterfaces.TeacherDAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Repository
public class TeacherDAO extends FactoryForDAO implements TeacherDAOInterface {

    @PersistenceContext
    private EntityManager entityManager;

    public Teacher findById(int id) {
        return entityManager.find(Teacher.class, id);
    }

    public void save(Teacher teacher) {
        entityManager.persist(teacher);
    }

    public List getAll() {
        return entityManager.createQuery("From Teacher").getResultList();
    }

    public String getTableLength() {
        return entityManager.createQuery("SELECT COUNT(*) from Teacher")
                .getSingleResult()
                .toString();
    }

    public void update(Teacher teacher) {
        entityManager.merge(teacher);
    }

    public void delete(Teacher teacher) {
        entityManager.remove(teacher);
    }

    public List findByName(String name) {
        Query query = entityManager.createQuery("from Teacher where lower(name) like :name");
        String param = "%" + name + "%";
        query.setParameter("name", param);
        return query.getResultList();
    }

    public List findByDate(LocalDate date) {
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

    public String selectTeachers
            (String filter) {
        Query query = entityManager.createQuery("SELECT count(t) FROM Teacher t " +
                "WHERE (LOWER(t.name) LIKE '%" + filter + "%') " +
                "OR (t.date LIKE '%" + filter + "%') " +
                "OR (t.gender LIKE '%" + filter + "%')");
        return query.getSingleResult().toString();
    }

    public List getTeachersForGroup(int groupId, int page, int length, String orderBy, String filter) {
        Query query =
                entityManager.createQuery("SELECT t FROM Teacher t JOIN t.groups g " +
                        "WHERE g.id = " + groupId + " AND (LOWER(t.name) LIKE '%" +
                        filter + "%' OR t.date LIKE '%" + filter + "%') " + orderBy);
        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public String getTeachersForGroup(int groupId, String filter) {
        Query query =
                entityManager.createQuery("SELECT COUNT(t) FROM Teacher t JOIN t.groups g " +
                        "WHERE g.id = " + groupId + " AND (LOWER(t.name) LIKE '%" +
                        filter + "%' OR t.date LIKE '%" + filter + "%') ");
        return query.getSingleResult().toString();
    }

    public List getNewTeachersForGroup(int groupId, int page, int length, String orderBy, String filter) {
        Query query =
                entityManager.createQuery("SELECT s FROM Teacher s " +
                        "WHERE (LOWER(s.name) LIKE '%" + filter + "%' OR s.date LIKE '%" + filter + "%') " +
                        "AND s NOT IN " +
                        "(SELECT t FROM Teacher t JOIN t.groups g " +
                        "WHERE g.id = " + groupId + ")" + orderBy);
        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public String getNewTeachersForGroup(int groupId, String filter) {
        Query query =
                entityManager.createQuery("SELECT COUNT(s) FROM Teacher s " +
                        "WHERE (LOWER(s.name) LIKE '%" +
                        filter + "%' OR s.date LIKE '%" + filter + "%') " +
                        "AND s NOT IN " +
                        "(SELECT t FROM Teacher t JOIN t.groups g " +
                        "WHERE g.id = " + groupId + ")");
        return query.getSingleResult().toString();
    }


    public void close() {
        entityManager.close();
    }
}
package workWithBase.daoClasses;

import objectForStrokeBase.Teacher;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import workWithBase.daoInterfaces.TeacherDAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class TeacherDAO implements TeacherDAOInterface {

    @PersistenceContext
    private EntityManager entityManager;

    public Teacher findById(int id) {
        return entityManager.find(Teacher.class, id);
    }

    public void save(Teacher teacher) {
        entityManager.persist(teacher);
    }

    public List getAll() {
        return entityManager.createQuery("SELECT t FROM Teacher t")
                .getResultList();
    }

    public void update(Teacher teacher) {
        entityManager.merge(teacher);
    }

    public void delete(Teacher teacher) {
        entityManager.remove(teacher);
    }

    public List findByName(String name) {
        Query query = entityManager.createQuery("SELECT t FROM Teacher t " +
                "WHERE LOWER(t.name) LIKE :name");
        String param = "%" + name + "%";
        query.setParameter("name", param);
        return query.getResultList();
    }

    public List findByDate(LocalDate date) {
        Query query = entityManager.createQuery("SELECT t FROM Teacher t " +
                "WHERE LOWER(t.date) LIKE :date");
        String param = "%" + date + "%";
        query.setParameter("date", param);
        return query.getResultList();
    }

    public List<Teacher> getTeachers(Map<String, Object> parameters) {
        String filter = "%" + parameters.get("filter") + "%";
        String order = (String) parameters.get("order");
        int length = (Integer) parameters.get("length");
        int page = (Integer) parameters.get("page");

        TypedQuery<Teacher> query = entityManager.createQuery("SELECT t FROM Teacher t " +
                "WHERE LOWER(t.name) LIKE :filter " +
                "OR LOWER(t.date) LIKE :filter " +
                "OR LOWER(t.gender) LIKE :filter ORDER BY " + order, Teacher.class);
        query.setParameter("filter", filter);

        return query.setMaxResults(length).setFirstResult(page).getResultList();
    }

    public String getTeachers(String filter) {
        filter = "%" + filter + "%";
        Query query = entityManager.createQuery("SELECT count(t) FROM Teacher t " +
                "WHERE LOWER(t.name) LIKE :filter " +
                "OR LOWER(t.date) LIKE :filter " +
                "OR LOWER(t.gender) LIKE :filter");
        query.setParameter("filter", filter);

        return query.getSingleResult().toString();
    }

    public List<Teacher> getTeachersForGroup(Map<String, Object> parameters) {
        String filter = "%" + parameters.get("filter") + "%";
        String order = (String) parameters.get("order");
        int length = (Integer) parameters.get("length");
        int page = (Integer) parameters.get("page");
        int groupId = (Integer) parameters.get("groupNumber");

        TypedQuery<Teacher> query =
                entityManager.createQuery("SELECT t FROM Teacher t " +
                        "JOIN t.groups g " +
                        "WHERE g.id = :groupId " +
                        "AND (LOWER(t.name) LIKE :filter " +
                        "OR LOWER(t.date) LIKE :filter) ORDER BY " + order, Teacher.class);
        query.setParameter("groupId", groupId);
        query.setParameter("filter", filter);

        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public String getTeachersForGroup(int groupId, String filter) {
        filter = "%" + filter + "%";
        Query query =
                entityManager.createQuery("SELECT COUNT(t) FROM Teacher t " +
                        "JOIN t.groups g " +
                        "WHERE g.id = :groupId " +
                        "AND (LOWER(t.name) LIKE :filter " +
                        "OR LOWER(t.date) LIKE :filter)");
        query.setParameter("groupId", groupId);
        query.setParameter("filter", filter);

        return query.getSingleResult().toString();
    }

    public List<Teacher> getNewTeachersForGroup(Map<String, Object> parameters) {
        String filter = "%" + parameters.get("filter") + "%";
        String order = (String) parameters.get("order");
        int length = (Integer) parameters.get("length");
        int page = (Integer) parameters.get("page");
        int groupId = (Integer) parameters.get("groupNumber");

        TypedQuery<Teacher> query = entityManager.createQuery("SELECT s FROM Teacher s " +
                        "WHERE (LOWER(s.name) LIKE :filter " +
                        "OR LOWER(s.date) LIKE :filter) " +
                        "AND s NOT IN " +
                        "(SELECT t FROM Teacher t JOIN t.groups g " +
                        "WHERE g.id = :groupId) ORDER BY " + order, Teacher.class);
        query.setParameter("groupId", groupId);
        query.setParameter("filter", filter);

        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public String getNewTeachersForGroup(int groupId, String filter) {
        filter = "%" + filter + "%";
        Query query = entityManager.createQuery("SELECT COUNT(s) FROM Teacher s " +
                        "WHERE (LOWER(s.name) LIKE :filter " +
                        "OR LOWER(s.date) LIKE :filter) AND s NOT IN " +
                        "(SELECT t FROM Teacher t JOIN t.groups g " +
                        "WHERE g.id = :groupId)");
        query.setParameter("groupId", groupId);
        query.setParameter("filter", filter);

        return query.getSingleResult().toString();
    }
}
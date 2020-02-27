package workWithBase.daoClasses;

import objectForStrokeBase.Group;
import org.springframework.stereotype.Repository;
import workWithBase.daoInterfaces.GroupDAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository
public class GroupDAO implements GroupDAOInterface {

    @PersistenceContext
    private EntityManager entityManager;

    public Group findById(int id) {
        return entityManager.find(Group.class, id);
    }

    public List getAll() {
        return entityManager.createQuery("SELECT g FROM Group g").getResultList();
    }

    public void update(Group group) {
        entityManager.merge(group);
    }

    public Group selectGroupByNumber(int number) {
        Query query = entityManager.createQuery("SELECT g FROM Group g " +
                "WHERE g.number = :number");
        query.setParameter("number", number);
        return (Group) query.getSingleResult();
    }

    public void save(Group group) {
        Query query = entityManager.createQuery("SELECT g FROM Group g " +
                "WHERE g.number = :number");
        query.setParameter("number", group.getNumber());
        try {
            query.getSingleResult();
            System.out.println("Группа с данным номером уже существует");
        } catch (NoResultException e) {
            entityManager.persist(group);
        }
    }

    public void delete(Group group) {
        Query query = entityManager.createQuery("UPDATE Student s " +
                "SET s.group = null WHERE s.group.id = :id");
        query.setParameter("id", group.getId());
        query.executeUpdate();
        entityManager.remove(group);
    }

    public List getGroups(Map<String, Object> parameters) {
        String order = (String) parameters.get("order");
        String filter = "%" + parameters.get("filter") + "%";
        int page = (Integer) parameters.get("page");
        int length = (Integer) parameters.get("length");

        Query query = entityManager.createQuery("SELECT g FROM Group g WHERE " +
                " CAST(g.number as string) LIKE :filter ORDER BY " + order);
        query.setParameter("filter", filter);

        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public String getGroups(String filter) {
        filter = "%" + filter + "%";
        Query query = entityManager.createQuery("SELECT COUNT(g) FROM Group g WHERE " +
                "CAST(g.number as string) LIKE :filter");
        query.setParameter("filter", filter);

        return query.getSingleResult().toString();
    }

    public List getGroupForTeacher
            (Map<String, Object> parameters) {
        String filter = "%" + parameters.get("filter") + "%";
        String order = (String) parameters.get("order");
        int page = (Integer) parameters.get("page");
        int length = (Integer) parameters.get("length");
        int teacherId = (Integer) parameters.get("teacherId");

        Query query = entityManager.createQuery("SELECT g FROM Group g " +
                "JOIN g.teachers t " +
                "WHERE CAST(g.number as string) LIKE :filter " +
                "AND t.id = :teacherId ORDER BY g.number " + order);
        query.setParameter("filter", filter);
        query.setParameter("teacherId", teacherId);

        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public String getGroupForTeacher
            (int teacherId, String filter) {
        filter = "%" + filter + "%";
        Query query = entityManager.createQuery("SELECT COUNT(g) FROM Group g " +
                "JOIN g.teachers t " +
                "WHERE CAST(g.number as string) LIKE :filter " +
                "AND t.id = :teacherId");
        query.setParameter("filter", filter);
        query.setParameter("teacherId", teacherId);

        return query.getSingleResult().toString();
    }

    public List getNewGroupForTeacher
            (Map<String, Object> parameters) {
        String filter = "%" + parameters.get("filter") + "%";
        String order = (String) parameters.get("order");
        int page = (Integer) parameters.get("page");
        int length = (Integer) parameters.get("length");
        int teacherId = (Integer) parameters.get("teacherId");

        Query query = entityManager.createQuery("SELECT s FROM Group s " +
                "WHERE CAST(s.number as string) LIKE :filter AND s NOT IN " +
                "(SELECT g FROM Group g JOIN g.teachers t " +
                "WHERE t.id = :teacherId) ORDER BY s.number " + order);
        query.setParameter("filter", filter);
        query.setParameter("teacherId", teacherId);

        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public String getNewGroupForTeacher
            (int teacherId, String filter) {
        filter = "%" + filter + "%";
        Query query = entityManager.createQuery("SELECT COUNT(s) FROM Group s " +
                "WHERE CAST(s.number as string) LIKE :filter AND s NOT IN " +
                "(SELECT g FROM Group g JOIN g.teachers t " +
                "WHERE t.id = :teacherId)");
        query.setParameter("filter", filter);
        query.setParameter("teacherId", teacherId);

        return query.getSingleResult().toString();
    }

    public List searchGroup(int number) {
        String filter = "%" + number + "%";
        Query query = entityManager.createQuery("SELECT g FROM Group g " +
                "WHERE CAST(g.number as string) LIKE :filter");
        query.setParameter("filter", filter);

        return query.getResultList();
    }
}


package workWithBase.daoClasses;

import objectForStrokeBase.Group;
import org.springframework.stereotype.Repository;
import workWithBase.connectWithBase.FactoryForDAO;
import workWithBase.daoInterfaces.GroupDAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class GroupDAO implements GroupDAOInterface {

    @PersistenceContext
    private EntityManager entityManager;

    public Group findById(int id) {
        return entityManager.find(Group.class, id);
    }

    public List getAll() {
        return entityManager.createQuery("From Group").getResultList();
    }

    public String getLengthTable() {
        return entityManager.createQuery("SELECT COUNT(*) From Group")
                .getSingleResult()
                .toString();
    }

    public void update(Group group) {
        entityManager.merge(group);
    }

    public Group selectGroupByNumber(int number) {
        Query query = entityManager.createQuery("from Group where number = :number");
        query.setParameter("number", number);
        return (Group) query.getSingleResult();
    }

    public void save(Group group) {
        Query query = entityManager.createQuery("from Group where number = :number");
        query.setParameter("number", group.getNumber());
        try {
            query.getSingleResult();
            System.out.println("Группа с данным номером уже существует");
        } catch (NoResultException e) {
            entityManager.persist(group);
        }
    }

    public void delete(Group group) {
        Query query = entityManager.createQuery("update Student set group_id = null where group_id = :id");
        query.setParameter("id", group.getId());
        query.executeUpdate();
        entityManager.remove(group);
    }

    public List getGroups(String filter, int page, int length, String orderType) {
        Query query = entityManager.createQuery("FROM Group WHERE " +
                "number LIKE '%" + filter + "%'" + orderType);
        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public String getGroups(String filter) {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM Group WHERE " +
                "number LIKE '%" + filter + "%'");
        return query.getSingleResult().toString();
    }

    public List getGroupForTeacher
            (int teacherId, int page, int length, String orderBy, String filter) {
        Query query = entityManager.createQuery("SELECT g FROM Group g JOIN g.teachers t " +
                "WHERE g.number LIKE '%" + filter + "%' " +
                "AND t.id = " + teacherId + " ORDER BY g.number " + orderBy);

        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public String getGroupForTeacher
            (int teacherId, String filter) {
        Query query = entityManager.createQuery("SELECT COUNT(g) FROM Group g JOIN g.teachers t " +
                "WHERE g.number LIKE '%" + filter + "%' " +
                "AND t.id = " + teacherId);

        return query.getSingleResult().toString();
    }

    public List getNewGroupForTeacher
            (int teacherId, int page, int length, String orderBy, String filter) {
        Query query = entityManager.createQuery("SELECT s FROM Group s " +
                "WHERE s.number LIKE '%" + filter + "%' AND s NOT IN " +
                "(SELECT g FROM Group g JOIN g.teachers t " +
                "WHERE t.id = " + teacherId + ") ORDER BY s.number " + orderBy);

        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public String getNewGroupForTeacher
            (int teacherId, String filter) {
        Query query = entityManager.createQuery("SELECT COUNT(s) FROM Group s " +
                "WHERE s.number LIKE '%" + filter + "%' AND s NOT IN " +
                "(SELECT g FROM Group g JOIN g.teachers t " +
                "WHERE t.id = " + teacherId + ")");

        return query.getSingleResult().toString();
    }

    public List searchGroup(int number) {
        Query query = entityManager.createQuery("SELECT g FROM Group g " +
                "WHERE g.number LIKE '%" + number + "%'");

        return query.getResultList();
    }

    public void close() {
        entityManager.close();
    }
}


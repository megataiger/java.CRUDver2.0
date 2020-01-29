package workWithBase.daoClasses;

import objectForStrokeBase.Group;
import workWithBase.connectWithBase.FactoryForDAO;
import workWithBase.daoInterfaces.GroapDAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class GroupDAO extends FactoryForDAO implements GroapDAOInterface {

    private EntityManager entityManager = factory.createEntityManager();

    public Group findById(int id) {
        return entityManager.find(Group.class, id);
    }

    public List getAll() {
        return entityManager.createQuery("From Group").getResultList();
    }

    public void update(Group group) {
        entityManager.getTransaction().begin();
        entityManager.merge(group);
        entityManager.getTransaction().commit();
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
            entityManager.getTransaction().begin();
            entityManager.persist(group);
            entityManager.getTransaction().commit();
        }
    }

    public void delete(Group group) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("update Student set group_id = null where group_id = :id");
        query.setParameter("id", group.getId());
        query.executeUpdate();
        entityManager.remove(group);
        entityManager.getTransaction().commit();
    }

    public List getGroups(String filter, int page, int length, String orderType) {
        Query query = entityManager.createQuery("FROM Group WHERE " +
                "number LIKE '%" + filter + "%'" + orderType);
        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public List getGroups(String filter) {
        Query query = entityManager.createQuery("FROM Group WHERE " +
                "number LIKE '%" + filter + "%'");
        return query.getResultList();
    }

    public List getGroupForTeacher
            (int teacherId, int page, int length, String orderBy, String filter) {
        Query query = entityManager.createQuery("SELECT g FROM Group g JOIN g.teachers t " +
                "WHERE g.number LIKE '%" + filter + "%' " +
                "AND t.id = " + teacherId + " ORDER BY g.number " + orderBy);

        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public List getGroupForTeacher
            (int teacherId, String filter) {
        Query query = entityManager.createQuery("SELECT g FROM Group g JOIN g.teachers t " +
                "WHERE g.number LIKE '%" + filter + "%' " +
                "AND t.id = " + teacherId);

        return query.getResultList();
    }

    public List getNewGroupForTeacher
            (int teacherId, int page, int length, String orderBy, String filter) {
        Query query = entityManager.createQuery("SELECT s FROM Group s " +
                "WHERE s.number LIKE '%" + filter + "%' AND s NOT IN " +
                "(SELECT g FROM Group g JOIN g.teachers t " +
                "WHERE t.id = " + teacherId + ") ORDER BY s.number " + orderBy);

        return query.setFirstResult(page).setMaxResults(length).getResultList();
    }

    public List getNewGroupForTeacher
            (int teacherId, String filter) {
        Query query = entityManager.createQuery("SELECT s FROM Group s " +
                "WHERE s.number LIKE '%" + filter + "%' AND s NOT IN " +
                "(SELECT g FROM Group g JOIN g.teachers t " +
                "WHERE t.id = " + teacherId + ")");

        return query.getResultList();
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


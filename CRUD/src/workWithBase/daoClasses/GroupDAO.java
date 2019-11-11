package workWithBase.daoClasses;

import objectForStrokeBase.Group;
import workWithBase.connectWithBase.FactoryForDAO;
import workWithBase.daoInterfaces.GroapDAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class GroupDAO extends FactoryForDAO implements GroapDAOInterface {
    public Group findById(int id) {
        EntityManager entityManager = factory.createEntityManager();
        Group group = entityManager.find(Group.class, id);
        return group;
    }

    public List<Group> getAll() {
        EntityManager entityManager = factory.createEntityManager();
        List<Group> groups = entityManager.createQuery("From Group").getResultList();
        return groups;
    }

    public void update(Group group) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(group);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Group selectGroupByNumber(int number) {
        EntityManager entityManager = factory.createEntityManager();
        Query query = entityManager.createQuery("from Group where number = :number");
        query.setParameter("number", number);
        Group group = (Group) query.getSingleResult();
        return group;
    }

    public void save(Group group) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from Group where number = :number");
        query.setParameter("number", group.getNumber());
        try {
            query.getSingleResult();
            System.out.println("Группа с данным номером уже существует");
        } catch (NoResultException e) {
            entityManager.persist(group);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void delete(Group group) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("update Student set group_id = null where group_id = :id");
        query.setParameter("id", group.getId());
        query.executeUpdate();
        group = entityManager.merge(group);
        entityManager.remove(group);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}


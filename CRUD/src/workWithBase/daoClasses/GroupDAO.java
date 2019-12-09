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
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from Group where number = :number");
        query.setParameter("number", group.getNumber());
        try {
            query.getSingleResult();
            System.out.println("Группа с данным номером уже существует");
        } catch (NoResultException e) {
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

    public List<Object[]> searchGroup (int number) {
        Query query = entityManager.createNativeQuery("SELECT * from `group` where " +
                "`group`.number like \"%" + number + "%\"");
        return query.getResultList();
    }

    public void close() {
        entityManager.close();
    }

}


package workWithBase.daoClasses;

import objectForStrokeBase.Group;
import org.hibernate.Session;
import org.hibernate.Transaction;
import workWithBase.connectWithBase.FactoryForDAO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class GroupDAO extends FactoryForDAO {
    public Group findById(int id) {
        EntityManager entityManager = factory.createEntityManager();
        Group group = entityManager.find(Group.class, id);
        entityManager.close();
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

}


package workWithBase.daoClasses;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import workWithBase.connectWithBase.FactoryForDAO;
import workWithBase.daoInterfaces.StudentDAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

public class StudentDAO extends FactoryForDAO implements StudentDAOInterface {

    private EntityManager entityManager = factory.createEntityManager();

    public Student findById(int id) {
        return entityManager.find(Student.class, id);
    }
    public void save(Student student) {
        entityManager.getTransaction().begin();
        entityManager.merge(student);
        entityManager.getTransaction().commit();
    }

    public List getAll() {
        return entityManager.createQuery("From Student")
                .getResultList();

    }

    public List get(int i) {
        return entityManager.createQuery("FROM Student").setFirstResult(i).setMaxResults(10)
                .getResultList();

    }

    public void update(Student student) {
        entityManager.getTransaction().begin();
        entityManager.merge(student);
        entityManager.getTransaction().commit();
    }

    public void delete(Student student) {
        entityManager.getTransaction().begin();
        entityManager.remove(student);
        entityManager.getTransaction().commit();
    }

    public List findByName (String name) {
        Query query = entityManager.createQuery("from Student where lower(name) like :name");
        String param = "%" + name + "%";
        query.setParameter("name", param);
        return query.getResultList();
    }

    public List findByDate (LocalDate date) {
        Query query = entityManager.createQuery("from Student where lower(birthday) like :date");
        String param = "%" + date + "%";
        query.setParameter("date", param);
        return query.getResultList();
    }

    public List findByGroup (Group group) {
        Query query = entityManager.createQuery("from Student where group_id = :id");
        query.setParameter("id", group.getId());
        return query.getResultList();
    }

    public List findByFilter (String filter, int page, int length) {
        Query query = entityManager.createQuery(filter);
        return query.setMaxResults(length).setFirstResult(page).getResultList();
    }

    public List findByFilter (String filter) {
        Query query = entityManager.createQuery(filter);
        return query.getResultList();
    }

    public List findByGroup
            (int groupId, int page, int length, String orderBy, String filter) {
        Query query = entityManager.createQuery("FROM Student WHERE group_id = :id " +
                "AND (LOWER(name) LIKE '%" + filter + "%' OR birthday LIKE '%" + filter + "%') " +
        orderBy);
        query.setParameter("id", groupId);
        return query.setMaxResults(length).setFirstResult(page).getResultList();
    }

    public List findByGroup (int groupId, String filter) {
        Query query = entityManager.createQuery("from Student where group_id = :id " +
                "AND (LOWER(name) LIKE '%" + filter + "%' OR birthday LIKE '%" + filter + "%') ");
        query.setParameter("id", groupId);
        return query.getResultList();
    }

    public void close() {
        entityManager.close();
    }
}
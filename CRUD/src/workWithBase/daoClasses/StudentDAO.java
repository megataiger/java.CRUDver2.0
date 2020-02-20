package workWithBase.daoClasses;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import org.springframework.stereotype.Repository;
import workWithBase.connectWithBase.FactoryForDAO;
import workWithBase.daoInterfaces.StudentDAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Repository
public class StudentDAO extends FactoryForDAO implements StudentDAOInterface {

    @PersistenceContext
    private EntityManager entityManager;

    public Student findById(int id) {
        return entityManager.find(Student.class, id);
    }

    public void save(Student student) {
        entityManager.merge(student);
    }

    public List getAll() {
        return entityManager.createQuery("From Student")
                .getResultList();
    }

    public String getTableLength() {
        return entityManager.createQuery("SELECT COUNT(s) FROM Student s")
                .getSingleResult().toString();
    }

    public void update(Student student) {
        entityManager.merge(student);
    }

    public void delete(Student student) {
        entityManager.remove(student);
    }

    public List findByName(String name) {
        Query query = entityManager.createQuery("from Student where lower(name) like :name");
        String param = "%" + name + "%";
        query.setParameter("name", param);
        return query.getResultList();
    }

    public List findByDate(LocalDate date) {
        Query query = entityManager.createQuery("from Student where lower(birthday) like :date");
        String param = "%" + date + "%";
        query.setParameter("date", param);
        return query.getResultList();
    }

    public List findByGroup(Group group) {
        Query query = entityManager.createQuery("from Student where group_id = :id");
        query.setParameter("id", group.getId());
        return query.getResultList();
    }

    public String findByGroup(int idGroup) {
        Query query = entityManager.createQuery("SELECT COUNT(*) from Student where group_id = :id");
        query.setParameter("id", idGroup);
        return query.getSingleResult().toString();
    }

    public List findByFilter(String filter, int page, int length, String orderBy) {
        Query query = entityManager.createQuery("SELECT s FROM Student s " +
                "WHERE LOWER(s.name) LIKE '%" + filter + "%' OR s.date LIKE '%" + filter + "%' " +
                "OR s.gender = '" + filter + "' OR s.group LIKE '%" + filter + "%' ORDER BY " + orderBy);
        return query.setMaxResults(length).setFirstResult(page).getResultList();
    }

    public String findByFilter(String filter) {
        Query query = entityManager.createQuery("SELECT COUNT(s) FROM Student s " +
                "WHERE LOWER(s.name) LIKE '%" + filter + "%' OR s.date LIKE '%" + filter + "%' " +
                "OR s.gender = '" + filter + "' OR s.group LIKE '%" + filter + "%'");
        return query.getSingleResult().toString();
    }

    public List findByGroup
            (int groupId, int page, int length, String orderBy, String filter) {
        Query query = entityManager.createQuery("FROM Student WHERE group_id = :id " +
                "AND (LOWER(name) LIKE '%" + filter + "%' OR birthday LIKE '%" + filter + "%') " +
                orderBy);
        query.setParameter("id", groupId);
        return query.setMaxResults(length).setFirstResult(page).getResultList();
    }

    public String findByGroup(int groupId, String filter) {
        Query query = entityManager.createQuery("SELECT COUNT(*) from Student where group_id = :id " +
                "AND (LOWER(name) LIKE '%" + filter + "%' OR birthday LIKE '%" + filter + "%') ");
        query.setParameter("id", groupId);
        return query.getSingleResult().toString();
    }
}
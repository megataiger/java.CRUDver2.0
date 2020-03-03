package workWithBase.daoClasses;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import org.springframework.stereotype.Repository;
import workWithBase.daoInterfaces.StudentDAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class StudentDAO implements StudentDAOInterface {

    @PersistenceContext
    private EntityManager entityManager;

    public Student findById(int id) {
        return entityManager.find(Student.class, id);
    }

    public void save(Student student) {
        entityManager.merge(student);
    }

    public List getAll() {
        return entityManager.createQuery("SELECT s FROM Student s")
                .getResultList();
    }

    public void update(Student student) {
        entityManager.merge(student);
    }

    public void delete(Student student) {
        entityManager.remove(student);
    }

    public List findByName(String name) {
        Query query = entityManager.createQuery("SELECT s FROM Student s " +
                "WHERE LOWER(s.name) LIKE :name");
        String param = "%" + name + "%";
        query.setParameter("name", param);
        return query.getResultList();
    }

    public List findByDate(LocalDate date) {
        Query query = entityManager.createQuery("SELECT s FROM Student s " +
                "WHERE LOWER(s.date) LIKE :date");
        String param = "%" + date + "%";
        query.setParameter("date", param);
        return query.getResultList();
    }

    public List findByGroup(Group group) {
        Query query = entityManager.createQuery("SELECT s FROM Student s " +
                "WHERE s.group.id = :id");
        query.setParameter("id", group.getId());
        return query.getResultList();
    }

    public String findByGroup(int idGroup) {
        Query query = entityManager.createQuery("SELECT COUNT(s) FROM Student s " +
                "WHERE s.group.id = :id");
        query.setParameter("id", idGroup);
        return query.getSingleResult().toString();
    }

    public List<Student> findByFilter(Map<String, Object> parameters) {
        String filter = "%" + parameters.get("filter") + "%";
        String order = (String) parameters.get("order");
        int length = (Integer) parameters.get("length");
        int page = (Integer) parameters.get("page");

        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s" +
                " JOIN FETCH s.group " +
                "WHERE LOWER(s.name) LIKE :filter OR LOWER(s.date) LIKE :filter " +
                "OR s.gender = :filter OR LOWER(s.group.number) LIKE :filter " +
                "ORDER BY s." + order, Student.class);

        query.setParameter("filter", filter);

        return query.setMaxResults(length).setFirstResult(page).getResultList();
    }

    public String findByFilter(String filter) {
        filter = "%" + filter + "%";
        Query query = entityManager.createQuery("SELECT COUNT(s) FROM Student s " +
                "WHERE LOWER(s.name) LIKE :filter OR LOWER(s.date) LIKE :filter " +
                "OR s.gender = :filter OR LOWER(s.group.number) LIKE :filter");

        query.setParameter("filter", filter);

        return query.getSingleResult().toString();
    }

    public List<Student> findByGroup(Map<String, Object> parameters) {
        String filter = "%" + parameters.get("filter") + "%";
        String order = (String) parameters.get("order");
        int length = (Integer) parameters.get("length");
        int page = (Integer) parameters.get("page");
        int number = (Integer) parameters.get("groupNumber");

        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s" +
                " WHERE s.group.id = :number AND (LOWER(s.name) LIKE :filter " +
                "OR LOWER(s.date) LIKE :filter) ORDER BY " + order, Student.class);

        query.setParameter("number", number);
        query.setParameter("filter", filter);

        return query.setMaxResults(length).setFirstResult(page).getResultList();
    }

    public String findByGroup(int number, String filter) {
        filter = "%" + filter + "%";
        Query query =
                entityManager.createQuery("SELECT COUNT(s) FROM Student s " +
                "WHERE s.group.id = :number AND (LOWER(s.name) LIKE :filter " +
                "OR LOWER(s.date) LIKE :filter)");
        query.setParameter("number", number);
        query.setParameter("filter", filter);

        return query.getSingleResult().toString();
    }
}
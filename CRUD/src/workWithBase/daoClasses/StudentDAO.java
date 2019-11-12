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
        Student stud = entityManager.find(Student.class, id);
        return stud;
    }
    public void save(Student student) {
        entityManager.getTransaction().begin();
        entityManager.merge(student);
        entityManager.getTransaction().commit();
    }

    public List<Student> getAll() {
        List<Student> students = entityManager.createQuery("From Student")
                .getResultList();
        return students;

    }

    public void update(Student student) {
        entityManager.getTransaction().begin();
        entityManager.merge(student);
        entityManager.getTransaction().commit();
    }

    public void delete(Student student) {
        entityManager.getTransaction().begin();
        student = entityManager.merge(student);
        System.out.println(student);
        entityManager.remove(student);
        entityManager.getTransaction().commit();
    }

    public List<Student> findByName (String name) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from Student where lower(name) like :name");
        String param = "%" + name + "%";
        query.setParameter("name", param);
        List<Student> students = query.getResultList();
        return students;
    }

    public List<Student> findByDate (LocalDate date) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from Student where lower(birthday) like :date");
        String param = "%" + date + "%";
        query.setParameter("date", param);
        List<Student> students = query.getResultList();
        return students;
    }

    public List<Student> findByGroup (Group group) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from Student where group_id = :id");
        query.setParameter("id", group.getId());
        List<Student> students = query.getResultList();
        return students;
    }
}
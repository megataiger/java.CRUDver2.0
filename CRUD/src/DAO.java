import objectForStrokeBase.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DAO {

    @PersistenceContext
    private EntityManager entityManager;

    void find(int id) {
        System.out.println(entityManager.find(Student.class, id));
    }

    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("resources/spring.xml");

        DAO dao = new DAO();
        dao.find(130);
    }
}
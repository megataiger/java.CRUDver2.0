package workWithBase;

import objectForStrokeBase.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("dao")
public class DAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void view(int id) {
        System.out.println(entityManager.find(Student.class, id));
    }

    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("resources/spring.xml");
        DAO dao = (DAO) context.getBean("dao");
        dao.view(130);
    }
}
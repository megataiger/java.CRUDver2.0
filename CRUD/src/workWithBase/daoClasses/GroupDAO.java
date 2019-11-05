package workWithBase.daoClasses;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import org.hibernate.Session;
import org.hibernate.Transaction;
import workWithBase.connectWithBase.HibernateSessionFactoryUtil;
import workWithBase.daoInterfaces.GroapDAOInterface;
import workWithBase.connectWithBase.SuperTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    public Group findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Group.class, id);
    }

    public List<Group> getAll() {
        List<Group> groups = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from Group").list();
        return groups;
    }

    public void update(Group group) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(group);
        tx1.commit();
        session.close();
    }

}


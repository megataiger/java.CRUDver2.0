package workWithBase.daoClasses;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import workWithBase.connectWithBase.HibernateSessionFactoryUtil;
import workWithBase.daoInterfaces.GroapDAOInterface;
import workWithBase.connectWithBase.SuperTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GroapDAO {
    public Group findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Group.class, id);
    }

}


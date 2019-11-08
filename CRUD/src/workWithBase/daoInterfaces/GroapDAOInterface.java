package workWithBase.daoInterfaces;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;

import java.sql.SQLException;
import java.util.List;

public interface GroapDAOInterface {
    Group findById (int idGroup);

    Group selectGroupByNumber(int numberGroup);

    List<Group> getAll();

    void save(Group group);

    void update(Group group);

    void delete(Group group);
}

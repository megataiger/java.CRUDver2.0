package workWithBase.daoInterfaces;

import objectForStrokeBase.Group;

import java.util.List;

public interface GroapDAOInterface {
    Group findById(int idGroup);

    Group selectGroupByNumber(int numberGroup);

    List getAll();

    void save(Group group);

    void update(Group group);

    void delete(Group group);
}

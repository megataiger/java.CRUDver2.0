package mvc.converters;

import objectForStrokeBase.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import workWithBase.serviceInterfaces.GroupServiceInterface;

public class GroupConverter implements Converter<String, Group> {

    private GroupServiceInterface groupService;

    @Autowired
    public GroupConverter(GroupServiceInterface groupService){
        this.groupService = groupService;
    }

    @Override
    public Group convert(String groupId) {
        int id = Integer.parseInt(groupId);
        return groupService.findById(id);
    }
}
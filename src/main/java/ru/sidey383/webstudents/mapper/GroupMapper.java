package ru.sidey383.webstudents.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sidey383.webstudents.entity.Group;
import ru.sidey383.webstudents.model.CreateGroup;
import ru.sidey383.webstudents.model.GroupResponse;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "students", ignore = true)
    Group map(CreateGroup createGroup);

    GroupResponse map(Group group);

}

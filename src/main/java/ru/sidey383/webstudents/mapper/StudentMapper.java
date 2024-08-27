package ru.sidey383.webstudents.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sidey383.webstudents.entity.Student;
import ru.sidey383.webstudents.model.StudentResponse;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "group", source = "student.group.id")
    StudentResponse map(Student student);

}

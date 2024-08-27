package ru.sidey383.webstudents.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.sidey383.webstudents.entity.Group;
import ru.sidey383.webstudents.entity.Student;
import ru.sidey383.webstudents.mapper.StudentMapper;
import ru.sidey383.webstudents.model.CreateStudent;
import ru.sidey383.webstudents.model.PageData;
import ru.sidey383.webstudents.model.StudentResponse;
import ru.sidey383.webstudents.repository.GroupRepository;
import ru.sidey383.webstudents.repository.StudentRepository;

import java.util.List;

@RestController
@AllArgsConstructor
public class StudentController {

    public static final String STUDENT_PATH = "/api/student";

    public static final String STUDENT_ID_PATH = "/api/student/{id}";

    private final GroupRepository groupRepository;

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    @PostMapping(STUDENT_PATH)
    public StudentResponse createStudent(
            @RequestBody
            CreateStudent createGroup
    ) {
        Group group = groupRepository.findById(createGroup.group())
                .orElseThrow(() -> new IllegalArgumentException("Can't found group.html by id"));
        Student student = new Student();
        student.setGroup(group);
        student.setFullName(createGroup.fullName());
        studentRepository.save(student);
        return studentMapper.map(student);
    }

    @GetMapping(STUDENT_PATH)
    public PageData<StudentResponse> getStudents(
            @RequestParam(defaultValue = "10", required = false)
            int pageSize,
            @RequestParam(defaultValue = "1", required = false)
            int pageNumber,
            @RequestParam(required = false)
            Long group
    ) {
        Page<Student> page = studentRepository.getStudentByGroupIdOrderByFullName(PageRequest.of(pageNumber, pageSize), group);
        List<StudentResponse> responsesData = page.stream().map(studentMapper::map).toList();
        return new PageData<>(responsesData, page.getSize(), page.getTotalPages(), page.getNumber());
    }

    @DeleteMapping(STUDENT_ID_PATH)
    public void deleteStudent(
            @PathVariable
            long id
    ) {
        studentRepository.deleteById(id);
    }

}

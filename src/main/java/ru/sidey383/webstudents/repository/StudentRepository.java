package ru.sidey383.webstudents.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.sidey383.webstudents.entity.Student;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long>, CrudRepository<Student, Long> {

    Page<Student> getStudentByGroupIdOrderByFullName(Pageable pageable, Long id);

}

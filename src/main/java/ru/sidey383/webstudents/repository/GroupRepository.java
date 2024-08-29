package ru.sidey383.webstudents.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.sidey383.webstudents.entity.Group;

public interface GroupRepository extends PagingAndSortingRepository<Group, Long>, CrudRepository<Group, Long> {
}

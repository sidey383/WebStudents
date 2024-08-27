package ru.sidey383.webstudents.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.sidey383.webstudents.entity.Group;

public interface GroupRepository extends PagingAndSortingRepository<Group, Long>, CrudRepository<Group, Long> {

    @Modifying
    @Query("update Group set number = :number where id = :id")
    Group updateNumber(long id, String number);

}

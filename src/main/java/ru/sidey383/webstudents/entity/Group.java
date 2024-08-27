package ru.sidey383.webstudents.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "university_group")
public class Group {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String number;

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
        private Collection<Student> students;

        @Formula("(select count(s.id) from students s where s.group_id = id)")
        private Long studentCount;
}

package ru.sidey383.webstudents.model;

import java.time.LocalDate;

public record StudentResponse (
        Long id,
        String fullName,
        LocalDate createTime,
        long group
) {
}

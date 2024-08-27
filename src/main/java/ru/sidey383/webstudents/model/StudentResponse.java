package ru.sidey383.webstudents.model;

import java.time.LocalDateTime;

public record StudentResponse (
        Long id,
        String fullName,
        LocalDateTime createTime,
        long group
) {
}

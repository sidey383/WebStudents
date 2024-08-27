package ru.sidey383.webstudents.model;

import java.time.Instant;

public record ServiceException (
        Instant timestamp,
        String reason
) {
}

package com.example.studybuddy.events;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void noArgsConstructor_createsObject() {
        assertNotNull(new Event());
    }

    @Test
    void customConstructor_setsFieldsCorrectly() {
        LocalDateTime start = LocalDateTime.of(2026, 1, 20, 16, 0);
        LocalDateTime end = LocalDateTime.of(2026, 1, 20, 18, 0);

        Event e = new Event(
                "Test Event",
                "Description",
                start,
                end,
                "Room 101"
        );

        assertEquals("Test Event", e.getTitle());
        assertEquals("Description", e.getDescription());
        assertEquals(start, e.getStartsAt());
        assertEquals(end, e.getEndsAt());
        assertEquals("Room 101", e.getLocation());
    }
}

package com.example.studybuddy.errors;

public class EventNotFound extends RuntimeException {
    public EventNotFound(Long id) {
        super("Event not found: " + id);
    }
}

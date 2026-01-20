package com.example.studybuddy.events.dto;

import java.time.LocalDateTime;

public class EventRequest {
    public String title;
    public String description;
    public LocalDateTime startsAt;
    public LocalDateTime endsAt;
    public String location;
}

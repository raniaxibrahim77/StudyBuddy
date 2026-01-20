package com.example.studybuddy.events;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@ConditionalOnProperty(name = "app.seed-demo-data", havingValue = "true")
public class EventsSeeder implements CommandLineRunner {

    private final EventRepository events;

    public EventsSeeder(EventRepository events) {
        this.events = events;
    }

    @Override
    public void run(String... args) {

        seedIfMissing(
                "Culture Reimagined – National Culture Day",
                "Event organized by the Faculty of Arts and Design UVT, dedicated to celebrating National Culture Day through contemporary artistic perspectives.",
                LocalDateTime.of(2026, 1, 19, 10, 0),
                LocalDateTime.of(2026, 1, 19, 14, 0),
                "UVT – Faculty of Arts and Design"
        );

        seedIfMissing(
                "Digital Pedagogy Conference – INO-VEST DigiPedia",
                "Conference presenting innovative pedagogical models and educational technologies for pre-university teachers.",
                LocalDateTime.of(2026, 2, 28, 9, 30),
                LocalDateTime.of(2026, 2, 28, 16, 0),
                "UVT Conference Hall"
        );

        seedIfMissing(
                "Culture Marathon – UVT",
                "A full-day cultural marathon proposed by West University of Timișoara, featuring conferences, debates, and artistic moments.",
                LocalDateTime.of(2026, 3, 2, 10, 0),
                LocalDateTime.of(2026, 3, 2, 18, 0),
                "UVT Campus"
        );

        seedIfMissing(
                "StudyBuddy: Exam Prep Meetup",
                "Student-organized study session for exam preparation and peer support.",
                LocalDateTime.of(2026, 3, 5, 16, 0),
                LocalDateTime.of(2026, 3, 5, 18, 0),
                "Library – Study Room 3"
        );

        seedIfMissing(
                "Career & Internship Info Session – IT & Engineering",
                "Information session about internships, career paths, and CV preparation for IT and Engineering students.",
                LocalDateTime.of(2026, 3, 10, 14, 0),
                LocalDateTime.of(2026, 3, 10, 16, 0),
                "UVT – Main Auditorium"
        );
    }

    private void seedIfMissing(String title, String description, LocalDateTime startsAt,
                               LocalDateTime endsAt, String location) {

        // simplest dedupe: title uniqueness in seed data
        boolean exists = events.findAll().stream().anyMatch(e -> title.equalsIgnoreCase(e.getTitle()));
        if (exists) return;

        events.save(new Event(title, description, startsAt, endsAt, location));
    }
}

package com.example.studybuddy.sessions;

import com.example.studybuddy.users.User;
import com.example.studybuddy.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@ConditionalOnProperty(name = "app.seed-demo-data", havingValue = "true")
@RequiredArgsConstructor
public class StudySessionSeeder implements CommandLineRunner {

    private final StudySessionRepository sessions;
    private final UserRepository users;

    @Override
    public void run(String... args) {
        if (sessions.count() > 0) {
            System.out.println("Study sessions already exist. Skipping session seeding.");
            return;
        }
        User raul = users.findByEmail("raul@test.com")
                .orElseGet(() -> users.findAll().get(0));

        User ibrahim = users.findByEmail("ibrahim@test.com")
                .orElse(raul);

        List<StudySession> demoSessions = List.of(

                StudySession.builder()
                        .title("Operating Systems – Exam Prep")
                        .course("Operating Systems")
                        .description("Review scheduling algorithms (FCFS, SJF, Round Robin) and solve sample problems.")
                        .startsAt(LocalDateTime.now().plusDays(2).withHour(18).withMinute(0))
                        .durationMinutes(90)
                        .capacity(8)
                        .language("English")
                        .locationText("Library – Floor 1")
                        .creator(raul)
                        .build(),

                StudySession.builder()
                        .title("Databases – SQL Practice Session")
                        .course("Databases")
                        .description("Hands-on SQL practice: SELECT, JOIN, GROUP BY, HAVING.")
                        .startsAt(LocalDateTime.now().plusDays(3).withHour(17).withMinute(0))
                        .durationMinutes(120)
                        .capacity(6)
                        .language("English")
                        .locationText("Room C203")
                        .creator(ibrahim)
                        .build(),

                StudySession.builder()
                        .title("OOP Project Discussion")
                        .course("Object-Oriented Programming")
                        .description("Discuss project structure, responsibilities, and common mistakes.")
                        .startsAt(LocalDateTime.now().plusDays(4).withHour(16).withMinute(0))
                        .durationMinutes(60)
                        .capacity(5)
                        .language("English")
                        .locationText("Online (Teams)")
                        .creator(raul)
                        .build(),

                StudySession.builder()
                        .title("Math Refresher: Derivatives & Limits")
                        .course("Mathematics")
                        .description("Quick recap of limits, derivatives, and common exam exercises.")
                        .startsAt(LocalDateTime.now().plusDays(5).withHour(15).withMinute(0))
                        .durationMinutes(75)
                        .capacity(10)
                        .language("English")
                        .locationText("Room A101")
                        .creator(ibrahim)
                        .build()
        );

        sessions.saveAll(demoSessions);
        System.out.println("Seeded " + demoSessions.size() + " study sessions.");
    }
}

package com.example.studybuddy.posts;

import com.example.studybuddy.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StudyPosts {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    @Column(length = 1000)
    private String description;

    private String course;
    private String major;
    private LocalDateTime createdAt = LocalDateTime.now();

    @ElementCollection
    private Set<String> tags = new HashSet<>();

    @ManyToOne(optional = false)
    private User author;
}

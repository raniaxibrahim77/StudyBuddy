package com.example.studybuddy.files;

import com.example.studybuddy.posts.StudyPosts;
import com.example.studybuddy.posts.StudyPostsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class FileStorage {

    private final StudyPostsRepository posts;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Path filePath = Path.of("data/posts.json");

    public FileStorage(StudyPostsRepository posts) {
        this.posts = posts;
    }

    @PostConstruct
    public void loadFromFile() {
        try {
            if (Files.exists(filePath)) {
                List<StudyPosts> savedPosts = List.of(
                        mapper.readValue(Files.readString(filePath), StudyPosts[].class)
                );
                savedPosts.forEach(posts::save);
                System.out.println("Loaded posts from file: " + savedPosts.size());
            } else {
                System.out.println("No file found, starting with empty database.");
            }
        } catch (IOException e) {
            System.err.println("Error reading posts.json: " + e.getMessage());
        }
    }

    public void saveToFile() {
        try {
            List<StudyPosts> all = posts.findAll();
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(all));
            System.out.println("Saved " + all.size() + " posts to file.");
        } catch (IOException e) {
            System.err.println("Failed to save posts: " + e.getMessage());
        }
    }
}

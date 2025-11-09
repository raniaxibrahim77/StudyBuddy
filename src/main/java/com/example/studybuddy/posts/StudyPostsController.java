package com.example.studybuddy.posts;

import com.example.studybuddy.files.FileStorage;
import com.example.studybuddy.users.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.studybuddy.errors.UserNotFound;
import com.example.studybuddy.errors.PostNotFound;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class StudyPostsController {

    private final StudyPostsRepository posts;
    private final UserRepository users;
    private final FileStorage fileStorage;

    public record CreatePostReq(
            @NotBlank String title,
            @NotBlank String description,
            String course,
            String major,
            Set<String> tags,
            Long authorId
    ) {}

    @GetMapping
    public List<StudyPosts> all() {
        return posts.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyPosts> one(@PathVariable Long id) {
        return posts.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PostNotFound(id));
    }

    @PostMapping
    public ResponseEntity<StudyPosts> create(@Valid @RequestBody CreatePostReq req) {
        var author = users.findById(req.authorId())
                .orElseThrow(() -> new UserNotFound(req.authorId()));

        var post = StudyPosts.builder()
                .title(req.title())
                .description(req.description())
                .course(req.course())
                .major(req.major())
                .tags(req.tags() == null ? Set.of() : req.tags())
                .author(author)
                .build();

        var saved = posts.save(post);
        fileStorage.saveToFile();
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyPosts> update(@PathVariable Long id,
                                             @RequestBody Map<String, Object> req) {
        return posts.findById(id).map(p -> {
            if (req.containsKey("title")) p.setTitle((String) req.get("title"));
            if (req.containsKey("description")) p.setDescription((String) req.get("description"));
            if (req.containsKey("course")) p.setCourse((String) req.get("course"));
            if (req.containsKey("major")) p.setMajor((String) req.get("major"));
            if (req.containsKey("tags") && req.get("tags") instanceof List<?> list) {
                p.setTags(list.stream().map(String::valueOf).collect(Collectors.toSet()));
            }
            var saved = posts.save(p);
            fileStorage.saveToFile();
            return ResponseEntity.ok(saved);
        }).orElseThrow(() -> new PostNotFound(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!posts.existsById(id)) throw new PostNotFound(id);
        posts.deleteById(id);
        fileStorage.saveToFile();
        return ResponseEntity.noContent().build();
    }
}

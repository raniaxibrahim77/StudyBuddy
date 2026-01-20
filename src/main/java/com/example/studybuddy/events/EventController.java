package com.example.studybuddy.events;

import com.example.studybuddy.events.dto.EventRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.studybuddy.errors.EventNotFound;


import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository events;

    public EventController(EventRepository events) {
        this.events = events;
    }

    @GetMapping
    public List<Event> list() {
        return events.findAllByOrderByStartsAtAsc();
    }

    @GetMapping("/{id}")
    public Event get(@PathVariable Long id) {
        return events.findById(id).orElseThrow(() -> new EventNotFound(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event create(@RequestBody EventRequest req) {
        if (req.title == null || req.title.isBlank()) throw new RuntimeException("title is required");
        if (req.startsAt == null) throw new RuntimeException("startsAt is required");
        return events.save(new Event(req.title, req.description, req.startsAt, req.endsAt, req.location));
    }

    @PutMapping("/{id}")
    public Event update(@PathVariable Long id, @RequestBody EventRequest req) {
        Event e = events.findById(id).orElseThrow(() -> new EventNotFound(id));

        if (req.title != null) e.setTitle(req.title);
        if (req.description != null) e.setDescription(req.description);
        if (req.startsAt != null) e.setStartsAt(req.startsAt);
        e.setEndsAt(req.endsAt);
        if (req.location != null) e.setLocation(req.location);

        return events.save(e);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!events.existsById(id)) {
            throw new EventNotFound(id);
        }
        events.deleteById(id);
    }
}

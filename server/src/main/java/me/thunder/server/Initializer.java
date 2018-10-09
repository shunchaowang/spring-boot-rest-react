package me.thunder.server;

import me.thunder.server.model.Event;
import me.thunder.server.model.Group;
import me.thunder.server.model.GroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

    private final GroupRepository groupRepository;

    public Initializer(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     */
    @Override
    public void run(String... args) {

        Stream.of("Denver JUG", "Utah JUG", "Seattle JUG", "Richmond JUG").forEach(name ->
                groupRepository.save(new Group(name))
        );

        Group djug = groupRepository.findByName("Denver JUG");
        Event event = Event.builder().title("Full Stack Reactive")
                .description("Reactive with Spring Boot + React")
                .date(Instant.parse("2018-12-12T18:00:00.000Z"))
                .build();

        djug.setEvents(Collections.singleton(event));
        groupRepository.save(djug);

        groupRepository.findAll().forEach(System.out::println);
    }
}

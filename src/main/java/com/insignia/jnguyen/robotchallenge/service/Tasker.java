package com.insignia.jnguyen.robotchallenge.service;

import com.github.javafaker.Faker;
import com.insignia.jnguyen.robotchallenge.exception.StartupException;
import com.insignia.jnguyen.robotchallenge.model.CommandEvent;
import com.insignia.jnguyen.robotchallenge.model.Robot;
import com.insignia.jnguyen.robotchallenge.repository.CommandEventRepository;
import com.insignia.jnguyen.robotchallenge.repository.RobotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class Tasker {
    private final RobotRepository robotRepository;
    private final Navigator navigator;

    public void runSimulator(List<CommandEvent> commandEvents) throws StartupException {
        var robot = createRobotAndCommands(commandEvents);

        var route = navigator.makeRoute(robot.getCommandEvents());


    }

    @Transactional
    public Robot createRobotAndCommands(List<CommandEvent> commandEvents) throws StartupException {
        var robot = Robot.builder()
                .name(new Faker().funnyName().name())
                .commandEvents(commandEvents)
                .build();
        return robotRepository.save(robot);
    }

}

package com.insignia.jnguyen.robotchallenge.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.insignia.jnguyen.robotchallenge.model.Facing;
import com.insignia.jnguyen.robotchallenge.model.Position;
import com.insignia.jnguyen.robotchallenge.model.Robot;
import com.insignia.jnguyen.robotchallenge.repository.RobotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PublisherTest {
    @Mock
    RobotRepository robotRepository;

    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(Publisher.class)).addAppender(logWatcher);
    }

    @Test
    void shouldPrintTestOutput() {
        var robot = Robot.builder().id(1L).name("jn").position(Position.builder().y(123).x(321).facing(Facing.EAST).build()).build();

        final long activeRobots = 5123L;
        when(robotRepository.getMaxRobotId()).thenReturn(activeRobots);

        var publisher = new Publisher(robotRepository);
        publisher.report(robot);

        assertTrue(logWatcher.list.get(0).getFormattedMessage().contains("Output: 321,123,EAST | Active robot: Robot 1 (jn) | Number of robots: " + activeRobots));
    }
}
package com.insignia.jnguyen.robotchallenge.util;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static com.insignia.jnguyen.robotchallenge.util.CommandEventGenerator.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileReaderTest {
    @Test
    public void shouldThrowExceptionOnCommandsFileNotFound() {
        assertThrows(
                RuntimeException.class,
                () -> new FileReader().read("not-found.txt"),
                "Exit Application"
        );
    }

    @Test
    public void shouldParseCommandFile() {
        URL url = this.getClass().getResource("/example.txt");
        var result = new FileReader().read(url.getFile());
        MatcherAssert.assertThat(result, contains(generatePlacement("1,2,EAST"), generateMove(), generateLeft(), generateRight(), generateReport()));
    }

    @Test
    public void shouldParseCommandFileScenarioOne() {
        URL url = this.getClass().getResource("/scenario-1.txt");
        var result = new FileReader().read(url.getFile());
        MatcherAssert.assertThat(result, contains(generatePlacement("0,0,NORTH"), generateMove(), generateReport()));
    }

    @Test
    public void shouldParseCommandFileScenarioTwo() {
        URL url = this.getClass().getResource("/scenario-2.txt");
        var result = new FileReader().read(url.getFile());
        MatcherAssert.assertThat(result, contains(generatePlacement("0,0,NORTH"), generateLeft(), generateReport()));
    }

    @Test
    public void shouldParseCommandFileScenarioThree() {
        URL url = this.getClass().getResource("/scenario-3.txt");
        var result = new FileReader().read(url.getFile());
        MatcherAssert.assertThat(result, contains(generatePlacement("1,2,EAST"), generateMove(), generateMove(), generateLeft(), generateMove(), generateReport()));
    }
}
package com.insignia.jnguyen.robotchallenge.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Stream;

import static com.tyro.oss.randomdata.RandomInteger.randomIntegerBetween;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;


@SpringBootTest
class NavigatorTest {
    @MockBean
    Publisher publisher;
    @Autowired
    Navigator navigator;

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3})
    void shouldReturnTrueInBoundsX(int x) {
        assertTrue(navigator.checkPosition(x, 0));
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3})
    void shouldReturnTrueInBoundsY(int y) {
        assertTrue(navigator.checkPosition(0, y));
    }


    @ParameterizedTest
    @MethodSource
    void shouldReturnFalseOutOfXBounds(int x) {
        assertFalse(navigator.checkPosition(x, 0));
    }

    private static Stream<Arguments> shouldReturnFalseOutOfXBounds() {
        return Stream.of(
                arguments(randomIntegerBetween(-100, -1)),
                arguments(-1),
                arguments(randomIntegerBetween(6, 100)),
                arguments(6)
        );
    }
    @ParameterizedTest
    @MethodSource
    void shouldReturnFalseOutOfYBounds(int y) {
        assertFalse(navigator.checkPosition(0, y));
    }

    private static Stream<Arguments> shouldReturnFalseOutOfYBounds() {
        return Stream.of(
                arguments(randomIntegerBetween(-100, -1)),
                arguments(-1),
                arguments(randomIntegerBetween(6, 100)),
                arguments(6)
        );
    }
}
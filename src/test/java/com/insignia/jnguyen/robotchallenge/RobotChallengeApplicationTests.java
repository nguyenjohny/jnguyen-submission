package com.insignia.jnguyen.robotchallenge;

import com.insignia.jnguyen.robotchallenge.util.FileReader;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest
class RobotChallengeApplicationTests {
	@MockBean
	FileReader fileReader;

	@Captor
	ArgumentCaptor<String> inputFilePathCaptor;

	@Test
	void contextLoads() {
		// no wiring issues
	}

	@Test
	public void shouldPassDefaultSpringValueInputFilePath() {
		verify(fileReader).read(inputFilePathCaptor.capture());
		assertEquals(RobotCli.COMMANDS_PATH_DEFAULT, inputFilePathCaptor.getValue());
	}

}

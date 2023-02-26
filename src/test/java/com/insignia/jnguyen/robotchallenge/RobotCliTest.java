package com.insignia.jnguyen.robotchallenge;

import com.insignia.jnguyen.robotchallenge.service.Tasker;
import com.insignia.jnguyen.robotchallenge.util.FileReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RobotCliTest {
    public static final String TEST_FILE_PATH = "special/here.txt";
    @Mock
    FileReader fileReader;
    @Mock
    Tasker tasker;

    @Captor
    ArgumentCaptor<String> inputFilePathCaptor;

    @Test
    public void shouldPassApplicationPropertyInputFilePath() throws Exception {
        var robotCli = new RobotCli(tasker, fileReader);
        ReflectionTestUtils.setField(robotCli, "inputFile", TEST_FILE_PATH);
        robotCli.run("--inputFile=" + TEST_FILE_PATH);

        verify(fileReader).read(inputFilePathCaptor.capture());
        assertEquals(TEST_FILE_PATH, inputFilePathCaptor.getValue());
    }

}
package com.example.schedulingtasks;


import org.awaitility.Durations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ScheduledTasksTest {

    @MockitoSpyBean
    ScheduledTasks tasks;

    @Test
    public void reportCurrentTime() {
       await().atMost(Durations.TEN_SECONDS).untilAsserted(() -> {
           verify(tasks, atLeast(2)).reportCurrentTime();
       });
    }
}

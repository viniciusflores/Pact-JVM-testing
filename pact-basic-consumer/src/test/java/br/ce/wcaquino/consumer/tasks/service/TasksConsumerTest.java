package br.ce.wcaquino.consumer.tasks.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.ce.wcaquino.consumer.tasks.model.Task;
import br.ce.wcaquino.consumer.utils.RequestHelper;

@RunWith(MockitoJUnitRunner.class)
public class TasksConsumerTest {

    private static final String INVALID_URL = "http://invalidURL.com";

    @InjectMocks
    private TasksConsumer consumer = new TasksConsumer(INVALID_URL);

    @Mock
    private RequestHelper helper;

    @Test
    public void shouldGetAnExistingTask() throws ClientProtocolException, IOException {
	// Arrange
	Map<String, String> expectedTasks = new HashMap<>();
	expectedTasks.put("id", "1");
	expectedTasks.put("task", "Task mocked!");
	expectedTasks.put("dueDate", "2000-01-01");

	Mockito.when(helper.get(INVALID_URL + "/todo/1")).thenReturn(expectedTasks);

	// Act
	Task task = consumer.getTask(1L);

	// Assert
	Assert.assertNotNull(task);
	Assert.assertThat(task.getId(), CoreMatchers.is(1L));

    }
}
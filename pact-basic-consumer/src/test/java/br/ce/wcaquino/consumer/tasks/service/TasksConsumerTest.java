package br.ce.wcaquino.consumer.tasks.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.consumer.tasks.model.Task;

public class TasksConsumerTest {

    @Test
    public void shouldGetAnExistingTask() throws ClientProtocolException, IOException {
	// Arrange
	TasksConsumer consumer = new TasksConsumer("invalidURL");

	// Act
	Task task = consumer.getTask(1L);

	// Assert
	Assert.assertNotNull(task);
    }
}

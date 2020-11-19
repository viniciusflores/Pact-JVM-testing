package br.ce.wcaquino.tasksfrontend.pact.consumer;

import org.junit.Rule;
import org.junit.Test;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import br.ce.wcaquino.tasksfrontend.repositories.TasksRepository;

public class DeleteTaskTest {
    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("Tasks", this);

    @Pact(consumer = "TasksFront")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
	return builder.given("There is a task with id = 1").uponReceiving("Delete a task").path("/todo/1")
		.method("DELETE").willRespondWith().status(204).toPact();
    }

    @Test
    @PactVerification
    public void shouldUpdateTask() {
	TasksRepository repo = new TasksRepository(mockProvider.getUrl());
	repo.delete(1L);
    }
}

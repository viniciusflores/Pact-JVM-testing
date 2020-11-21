package br.ce.wcaquino.consumer.tasks.pact.barriga;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.ConsumerPactRunnerKt;
import au.com.dius.pact.consumer.PactVerificationResult;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.model.MockProviderConfig;
import au.com.dius.pact.core.model.RequestResponsePact;
import br.ce.wcaquino.consumer.barriga.service.BarrigaConsumer;

public class CreateAccountTest {
    private final String TOKEN = "JWT eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTI0MjR9.ZHR30fmj9s4gyTcfoMiBMK1GKkwtfWWQ4qOJY3zM8Xc";

    @Test
    public void test() {
	PactDslJsonBody requestBody = new PactDslJsonBody().stringValue("nome", "Acc test");
	PactDslJsonBody responseBody = new PactDslJsonBody().numberType("id").stringValue("nome", "Acc test");

	RequestResponsePact pact = ConsumerPactBuilder.consumer("BasicConsumer").hasPactWith("Barriga")
		.given("There is no account with the name 'Acc test'").uponReceiving("Insert account 'Acc test'")
		.path("/contas").method("POST").matchHeader("Authorization", "JWT .*", TOKEN).body(requestBody)
		.willRespondWith().status(201).body(responseBody).toPact();

	MockProviderConfig config = MockProviderConfig.createDefault();
	PactVerificationResult result = ConsumerPactRunnerKt.runConsumerTest(pact, config, (mockServer, context) -> {
	    BarrigaConsumer consumer = new BarrigaConsumer(mockServer.getUrl());
	    String id = consumer.insertAccount("Acc test", TOKEN);
	    assertThat(id, is(notNullValue()));
	    return null;
	});

	if (result instanceof PactVerificationResult.Error) {
	    throw new RuntimeException(((PactVerificationResult.Error) result).getError());
	}
	assertThat(result, is(instanceOf(PactVerificationResult.Ok.class)));
    }
}

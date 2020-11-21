package br.ce.wcaquino.consumer.tasks.pact.barriga;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import br.ce.wcaquino.consumer.barriga.service.BarrigaConsumer;

public class LoginTest {
    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("Barriga", this);

    @Pact(consumer = "BasicConsumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
	PactDslJsonBody requestBody = new PactDslJsonBody().stringType("email", "vinipact@email.com")
		.stringType("senha", "123456");

	PactDslJsonBody responseBody = new PactDslJsonBody().stringType("token");

	return builder.given("Your user is created").uponReceiving("Signin with a valid user").method("POST")
		.path("/signin").body(requestBody).willRespondWith().status(200).body(responseBody).toPact();
    }

    @Test
    @PactVerification
    public void shouldSignin() throws IOException {
	BarrigaConsumer consumer = new BarrigaConsumer(mockProvider.getUrl());
	String token = consumer.login("vinipact@email.com", "123456");
	assertThat(token, is(notNullValue()));
    }
}

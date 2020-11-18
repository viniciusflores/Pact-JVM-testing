package br.ce.wcaquino.taskbackend.pact.provider;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactUrl;
import au.com.dius.pact.provider.junitsupport.target.Target;
import au.com.dius.pact.provider.junitsupport.target.TestTarget;
import br.ce.wcaquino.taskbackend.TaskBackendApplication;

@RunWith(PactRunner.class)
@Provider("Tasks")
@PactUrl(urls = {
	"file:////home/vinicius/git/Pact-JVM-testing/pact-backend/src/test/resources/BasicConsumer-Tasks.json" })
public class TasksProviderTest {
    @TestTarget
    public final Target target = new HttpTarget("localhost", 8000);

    @BeforeClass
    public static void setup() {
	SpringApplication.run(TaskBackendApplication.class);
    }

    @State("There is a task with id = 1")
    public void saveTask1() {
	// SQL Script to insert the task ...
	// Access Rest API to insert ... }
    }
}

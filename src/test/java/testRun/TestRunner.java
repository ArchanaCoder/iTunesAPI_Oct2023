package testRun;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(

        plugin = { "pretty", "html:target/cucumber-reports.html" },
        features= {"./src/test/java/features/apisearch.feature"},
        glue= "steps",
        tags= "@test",
        monochrome=true, dryRun=false
)

public class TestRunner {
}

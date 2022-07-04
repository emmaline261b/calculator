import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features", glue = {"com.epam.jap.calc"})
public class TestRunner extends AbstractTestNGCucumberTests {
}
package supplysync.erbolspage.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(

        plugin = {"html:target/cucumberReport.html", "json:target/testReport.json"}, //in order to get/configure reports
        features = "src/test/resources/features",   //location of your feature files
        glue = "supplysync/tests", //this is where step defs located
        tags = "@regressionBranches",
        dryRun = false

)

public class TestRunner {

}

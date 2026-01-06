package org.project.tests.run;

import static java.lang.System.out;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.util.Arrays;

import io.cucumber.junit.Cucumber;
//import io.cucumber.ClassFinder;
//import io.cucumber.MultiLoader;
//import io.cucumber.ResourceLoader;
//import io.cucumber.ResourceLoaderClassFinder;
import org.apache.commons.lang3.StringUtils;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

/**
 * <p>Source - https://stackoverflow.com/a
 * <p>Posted by Tihamer
 * <p>Retrieved 2026-01-06, License - CC BY-SA 4.0
 */
public class CucumberRunner {

    /**
     *
     * @param thisPath String
     */
    @ConstructorProperties({"thisPath"})
    public CucumberRunner(String thisPath) {
        // This does all the POI stuff, plus making the data available to
        //   getCellData(sheet, columnName, row) (which you get to define).
        // Any way that makes it return a dynamic value will work here.
    }

    /**
     *
     * @param args String[]
     * @throws IOException throws
     */
    public static void main(String[] args) throws IOException {
        CucumberRunner runner = new CucumberRunner("src/main/resources/User Roles.xlsx");
        runner.run();
    }

    /**
     *
     * @throws IOException throws
     */
    public void run() throws IOException {
        String[] tags = { "@LogoutCloseBrowser" };
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(out));
        out.println("runOneRow- JUnit/Cucumber test with tags: " + Arrays.toString(tags));
        String tagString = tags.toString().replace("[", "").replace("]", "").trim();
        tagString = StringUtils.removeEnd(tagString, ",");
        // The tagString value should look something like:
        // "@Login, @WelcomePage, @FirstScreen, @SecondScreen, @ThirdScreen, @Screen4"
        String[] argv = { "--plugin",  "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/MyCukeReport.html",
            "--snippets",  "camelcase",
            "--glue", "com.myorg.cukes.stepDefinitions", // or wherever
            "--tags",  tagString,
            "src/main/java/com/hhs/cms/cukes/features" };
        /*RuntimeOptions runtimeOptions = new RuntimeOptions(new ArrayList<String>(asList(argv)));
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
        Runtime runtime = new Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);
        runtime.run();
        out.println("Finished runOneRow with exitStatus=" + runtime.exitStatus() + ", iteration=" + iteration + ", userid=" + userId + ", " + password + ", role=" + role);*/
        //Result jUnitResult = junit.run(cukeRunnerClass);
        Result jUnitResult = junit.run(Cucumber.class);
        //resultReport(jUnitResult);
    }

}

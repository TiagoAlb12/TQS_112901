package calculator;

import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatorSteps {

    static final Logger log = getLogger(lookup().lookupClass());

    private Calculator calc;

    private Exception exception;

    @Given("a calculator I just turned on")
    public void setup() {
        calc = new Calculator();
        exception = null;
    }

    @When("I add {int} and {int}")
    public void add(int arg1, int arg2) {
        log.debug("Adding {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("+");
    }

    @When("I substract {int} to {int}")
    public void subtract(int arg1, int arg2) {
        log.debug("Subtracting {} to {}", arg2, arg1);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("-");
    }

    @When("I multiply {int} and {int}")
    public void multiply(int arg1, int arg2) {
        log.debug("Multiplying {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("*");
    }

    @When("I divide {int} by {int}")
    public void divide(int arg1, int arg2) {
        try{
            log.debug("Dividing {} by {}", arg1, arg2);
            calc.push(arg1);
            calc.push(arg2);
            calc.push("/");
            calc.value();
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the result is {double}")
    public void the_result_is(double expected) {
        Number value = calc.value();
        log.debug("Result: {} (expected: {})", value, expected);
        assertEquals(expected, value);
    }

    @Then("the result is an error")
    public void the_result_is_an_error() {
        assertNotNull(exception,"Exception is null");
        assertInstanceOf(ArithmeticException.class, exception);
        log.debug("Result: {}", exception.getMessage());
    }
}
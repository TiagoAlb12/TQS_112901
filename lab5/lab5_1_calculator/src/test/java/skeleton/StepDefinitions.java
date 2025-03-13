package skeleton;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;

public class StepDefinitions {
    @Given("I have {int} cukes in my belly")
    public void I_have_cukes_in_my_belly(int cukes) {
        Belly belly = new Belly();
        belly.eat(cukes);
    }

    @Given("I wait {int} hour")
    public void I_wait_hour(int hours) {
        Belly belly = new Belly();
        belly.waitFor(hours);
    }

    @Then("my belly should growl")
    public void my_belly_should_growl() {
        Belly belly = new Belly();
        assertThat(belly.isGrowling()).isTrue();
    }
}

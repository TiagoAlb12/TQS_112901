import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class JsonTest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    void testTodosEndpointIsAvailable() {
        given()
                .when().get(BASE_URL + "/todos")
                .then().statusCode(200);
    }

    @Test
    void testTodo4ReturnsCorrectTitle() {
        given()
                .when().get(BASE_URL + "/todos/4")
                .then()
                .statusCode(200)
                .body("title", equalTo("et porro tempora"));
    }

    @Test
    void testTodosContainId198And199() {
        given()
                .when().get(BASE_URL + "/todos")
                .then()
                .statusCode(200)
                .body("id", hasItems(198, 199));
    }

    @Test
    void testTodosResponseTimeIsLessThan2Seconds() {
        Response response = given().when().get(BASE_URL + "/todos");
        assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");
    }
}

package itemcrud;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ItemCrudTest {

    private final String BASE_URL = "https://todo.ly/api/";
    private final String USERNAME = "apazahuaychohugoalejandro@gmail.com";
    private final String PASSWORD = "Holasoyyo2002";

    @Test
    @DisplayName("Verify Create Read Update Delete Item - Todo.ly")
    public void verifyCRUDItem() {
        JSONObject body = new JSONObject();
        body.put("Content", "New Item Test");

        // Create item
        Response response = given()
                .auth()
                .preemptive()
                .basic(USERNAME, PASSWORD)
                .body(body.toString())
                .log().all()
                .when()
                .post(BASE_URL + "items.json");
        response.then()
                .statusCode(200)
                .body("Content", equalTo(body.get("Content")))
                .log().all();

        int itemId = response.then().extract().path("Id");

        // Read item
        response = given()
                .auth()
                .preemptive()
                .basic(USERNAME, PASSWORD)
                .log().all()
                .when()
                .get(BASE_URL + "items/" + itemId + ".json");
        response.then()
                .statusCode(200)
                .body("Content", equalTo(body.get("Content")))
                .log().all();

        // Update item
        body.put("Content", "Updated Item Content 234");
        response = given()
                .auth()
                .preemptive()
                .basic(USERNAME, PASSWORD)
                .body(body.toString())
                .log().all()
                .when()
                .put(BASE_URL + "items/" + itemId + ".json");
        response.then()
                .statusCode(200)
                .body("Content", equalTo("Updated Item Content 234"))
                .log().all();

        // Delete item
        response = given()
                .auth()
                .preemptive()
                .basic(USERNAME, PASSWORD)
                .log().all()
                .when()
                .delete(BASE_URL + "items/" + itemId + ".json");
        response.then()
                .statusCode(200)
                .body("Content", equalTo("Updated Item Content 234"))
                .body("Deleted", equalTo(true))
                .log().all();
    }
}

package testSuiteAPI;

import config.ApiConfig;
import factoryRequest.FactoryRequest;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.hamcrest.Matchers.equalTo;

public class CRUDUserTest extends BasicAuthTestBase {

    private String userId;

    @Test
    @DisplayName("Verify the create, update, delete user - todo.ly")
    public void crudUserTest() {
        createUser();
        updateUser();
        deleteUser();
    }

    public void createUser() {
        JSONObject userBody = new JSONObject();
        userBody.put("Email", "newuser2024b2b@todo.ly");
        userBody.put("FullName", "John Doe");
        userBody.put("Password", "securePassword");

        request.setUrl(ApiConfig.CREATE_USER)
                .setHeaders("Content-Type", "application/json")
                .setBody(userBody.toString());

        Response response = FactoryRequest.make("post").send(request);

        response.then().statusCode(200)
                .body("Email", equalTo(userBody.get("Email")))
                .body("FullName", equalTo(userBody.get("FullName")));

        ApiConfig.user = userBody.get("Email").toString();
        ApiConfig.pwd = userBody.get("Password").toString();
        valueAuth = "Basic " + Base64.getEncoder().encodeToString((ApiConfig.user + ":" + ApiConfig.pwd).getBytes());

    }

    public void updateUser() {
        JSONObject updateBody = new JSONObject();
        updateBody.put("Email", "updateduser2024b2b@todo.ly");
        updateBody.put("FullName", "John Updated Doe");

        request.setUrl(ApiConfig.UPDATE_USER)
                .setHeaders(auth, valueAuth)
                .setHeaders("Content-Type", "application/json")
                .setBody(updateBody.toString());

        Response response = FactoryRequest.make("put").send(request);

        response.then().statusCode(200)
                .body("Email", equalTo(updateBody.get("Email")));

        ApiConfig.user = updateBody.get("Email").toString();
        valueAuth = "Basic " + Base64.getEncoder().encodeToString((ApiConfig.user + ":" + ApiConfig.pwd).getBytes());

    }

    public void deleteUser() {
        request.setUrl(ApiConfig.DELETE_USER)
                .setHeaders(auth, valueAuth);

        Response response = FactoryRequest.make("delete").send(request);

        response.then().statusCode(200)
                .body("Email", equalTo(ApiConfig.user))
                .body("Id", equalTo(Integer.parseInt(response.then().extract().path("Id").toString())));

    }
}
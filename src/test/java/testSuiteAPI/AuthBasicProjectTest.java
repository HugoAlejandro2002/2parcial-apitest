package testSuiteAPI;

import config.ApiConfig;
import factoryRequest.FactoryRequest;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.hamcrest.Matchers.equalTo;

public class AuthBasicProjectTest extends BasicAuthTestBase  {
    @Test
    @DisplayName("Create a user, create a project, delete the user, try to create a project again")
    public void createUserProjectAndDeleteUser() {
        createUser();

        JSONObject projectBody = new JSONObject();
        projectBody.put("Content", "First Project");

        request.setUrl(ApiConfig.CREATE_PROJECT)
                .setHeaders(auth, valueAuth)
                .setBody(projectBody.toString());

        Response projectResponse = FactoryRequest.make("post").send(request);
        projectResponse.then().statusCode(200)
                .body("Content", equalTo("First Project"));

        deleteUser();

        request.setUrl(ApiConfig.CREATE_PROJECT)
                .setHeaders(auth, valueAuth)
                .setBody(projectBody.toString());

        projectResponse = FactoryRequest.make("post").send(request);

        projectResponse.then().statusCode(200)
                .body("ErrorMessage", equalTo("Account doesn't exist"))
                .body("ErrorCode", equalTo(105));
    }

    public void createUser() {
        JSONObject userBody = new JSONObject();
        userBody.put("Email", "testuser2024@todo.ly");
        userBody.put("FullName", "Test User");
        userBody.put("Password", "TestPassword123");

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

    public void deleteUser() {

        request.setUrl(ApiConfig.DELETE_USER)
                .setHeaders(auth, valueAuth);

        Response response = FactoryRequest.make("delete").send(request);


        response.then().statusCode(200)
                .body("Email", equalTo(ApiConfig.user))
                .body("FullName", equalTo("Test User"));
    }
}

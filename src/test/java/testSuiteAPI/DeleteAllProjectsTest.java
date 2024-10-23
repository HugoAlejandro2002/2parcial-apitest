package testSuiteAPI;

import config.ApiConfig;
import factoryRequest.FactoryRequest;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;

public class DeleteAllProjectsTest extends BasicAuthTestBase {

    @Test
    @DisplayName("Create 4 projects and delete all")
    public void createAndDeleteAllProjects() {
        createRandomUser();

        List<String> projectIds = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            JSONObject projectBody = new JSONObject();
            projectBody.put("Content", "Project " + i);

            request.setUrl(ApiConfig.CREATE_PROJECT)
                    .setHeaders(auth, valueAuth)
                    .setBody(projectBody.toString());

            Response response = FactoryRequest.make("post").send(request);
            response.then().statusCode(200)
                    .body("Content", equalTo("Project " + i));

            String projectId = response.then().extract().path("Id").toString();
            projectIds.add(projectId);
        }

        for (String id : projectIds) {
            request.setUrl(ApiConfig.DELETE_PROJECT.replace("{id}", id));
            Response deleteResponse = FactoryRequest.make("delete").send(request);
            deleteResponse.then().statusCode(200)
                    .body("Deleted", equalTo(true));
        }
    }

    public void createRandomUser() {
        String randomEmail = "testuser" + UUID.randomUUID().toString().substring(0, 8) + "@todo.ly";
        JSONObject userBody = new JSONObject();
        userBody.put("Email", randomEmail);
        userBody.put("FullName", "Random Test User");
        userBody.put("Password", "RandomPassword123");

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
}

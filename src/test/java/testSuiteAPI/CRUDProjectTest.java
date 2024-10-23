package testSuiteAPI;

import config.ApiConfig;
import factoryRequest.FactoryRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class CRUDProjectTest extends BasicAuthTestBase{

    @Test
    @DisplayName("Verify the create, read, update, delete project - todo.ly")
    public void createProject() {
        request.setUrl(ApiConfig.CREATE_PROJECT)
                .setHeaders(auth, valueAuth).setBody(body.toString());
        response = FactoryRequest.make("post").send(request);
        response.then().statusCode(200).body("Content",equalTo(body.get("Content")));
        String id = response.then().extract().path("Id")+"";

        request.setUrl(ApiConfig.READ_PROJECT.replace("{id}",id));
        response = FactoryRequest.make("get").send(request);
        response.then().statusCode(200).body("Content",equalTo(body.get("Content")));

        body.put("Content","UPB2025");
        request.setUrl(ApiConfig.UPDATE_PROJECT.replace("{id}",id)).setBody(body.toString());
        response = FactoryRequest.make("put").send(request);
        response.then().statusCode(200).body("Content",equalTo(body.get("Content")));

        request.setUrl(ApiConfig.DELETE_PROJECT.replace("{id}",id));
        response = FactoryRequest.make("delete").send(request);
        response.then().statusCode(200).body("Deleted",equalTo(true));
    }

}
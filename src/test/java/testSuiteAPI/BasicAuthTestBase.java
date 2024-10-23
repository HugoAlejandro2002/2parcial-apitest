package testSuiteAPI;

import config.ApiConfig;
import factoryRequest.RequestInfo;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Base64;

public class BasicAuthTestBase {
    public RequestInfo request = new RequestInfo();
    public Response response;
    public String auth="";
    public String valueAuth="";
    public JSONObject body = new JSONObject();

    @BeforeEach
    public void before() {
        body.put("Content","UPB2024");
        auth="Authorization";
        valueAuth="Basic "+ Base64.getEncoder().encodeToString((ApiConfig.user+":"+ApiConfig.pwd).getBytes());
    }

    @AfterEach
    public void after() {
    }
}
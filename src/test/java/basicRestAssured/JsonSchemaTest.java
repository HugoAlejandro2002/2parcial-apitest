package basicRestAssured;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

// https://json-schema.org/draft-04/schema#
//https://www.liquid-technologies.com/online-json-to-schema-converter
public class JsonSchemaTest {

    @Test
    @DisplayName("Verify Create Project by API - Todo.ly JSONObject")
    public void verifyCreateProjectCheckSchema(){
        JSONObject body = new JSONObject();
        body.put("Content","RESTJsonBody");
        body.put("Icon",7);

        JsonSchemaFactory schemaDraft04 = JsonSchemaFactory.newBuilder()
                        .setValidationConfiguration(
                                ValidationConfiguration.newBuilder()
                                .setDefaultVersion(SchemaVersion.DRAFTV4).freeze()

                        ).freeze();

        given()
                .auth()
                .preemptive()
                .basic("api2024@2024.com","12345")
                .body(body.toString())
                .log().all()
       .when()
                .post("https://todo.ly/api/projects.json")
       .then()
                .body( JsonSchemaValidator.
                       matchesJsonSchemaInClasspath("expectedResult2.json")
                       .using(schemaDraft04)
                     )
                .log().all();
    }
}

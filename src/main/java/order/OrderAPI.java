package order;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class OrderAPI {

    private RequestSpecification baseRequestSpec = new RequestSpecBuilder()
            .setBaseUri(URL.HOST)
            .addHeader("Content-type", "application/json")
            .addFilter(new RequestLoggingFilter())
            .addFilter(new ResponseLoggingFilter())
            .setRelaxedHTTPSValidation()
            .build();


    protected ValidatableResponse doGetRequest(String path) {
        return given()
                .spec(baseRequestSpec)
                .get(path)
                .then();
    }

    protected Response doPostRequest(String path, Object body) {
        return given()
                .spec(baseRequestSpec)
                .body(body)
                .post(path)
                .thenReturn();
    }

    protected Response doDeleteRequest(String path) {
        return given()
                .spec(baseRequestSpec)
                .delete(path)
                .thenReturn();
    }

}
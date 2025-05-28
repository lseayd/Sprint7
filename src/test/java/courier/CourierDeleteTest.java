package courier;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import сourier.Courier;
import сourier.CourierAPI;
import сourier.CourierData;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class CourierDeleteTest {

    private final CourierAPI courierApi = new CourierAPI();

    private Courier courierRandom;

    @Before
    public void setUp() {
        RestAssured.baseURI = service.BASE_URL;
        courierRandom = (Courier) CourierData.generateRandom();
    }

    @After
    public void tearDown() {
        try {
            Response responseLogin = courierLogin(courierRandom);
            String courierId = responseLogin.then().extract().path("id").toString();
            courierDelete(courierId);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Удаление курьера")
    @Description("Проверка, что курьера можно удалить")
    public void deleteCourierPositiveTest() {
        courierCreate(courierRandom);
        Response responseLogin = courierLogin(courierRandom);
        String courierId = responseLogin.then().extract().path("id").toString();
        Response response = courierDelete(courierId);
        compareResultToTrue(response, SC_OK);
    }

    // Метод для шага "Создать курьера":
    @Step("Create courier")
    public void courierCreate(Courier courier){
        Response response = courierApi.create(courier);
        printResponseBodyToConsole("Создание курьера: ", response, service.NEED_DETAIL_LOG);
    }

    // Метод для шага "Авторизация курьера":
    @Step("Login courier")
    public Response courierLogin(Courier courier){
        Response response = courierApi.login(courier);
        printResponseBodyToConsole("Авторизация курьера: ", response, service.NEED_DETAIL_LOG);
        return response;
    }

    // Метод для шага "Удалить курьера":
    @Step("Delete courier by id")
    public Response courierDelete(String courierId){
        Response response = courierApi.delete(courierId);
        printResponseBodyToConsole("Удаление курьера: ", response, service.NEED_DETAIL_LOG);
        return response;
    }

    @Step("Compare result to true")
    public void compareResultToTrue(Response response, int statusCode){
        response
                .then()
                .assertThat()
                .log().all()
                .statusCode(statusCode)
                .body("ok", is(true));
    }

    // Метод для шага "Вывести тело ответа в консоль":
    @Step("Print response body to console")
    public void printResponseBodyToConsole(String headerText, Response response, boolean detailedLog){
        if (detailedLog)
            System.out.println(headerText + response.body().asString());
    }

}

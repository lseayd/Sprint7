package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import сourier.CourierAPI;

import static org.junit.Assert.*;

public class CourierCreateTest {

    private CourierAPI courierHttp = new CourierAPI();
    private Courier courier;

    @Before
    public void setUp() {
        courier = new Courier("qwwqww", "11111", "33333");
    }

    @After
    public void endTest() {
        courierHttp.loginCourier(courier);
        courierHttp.deleteCourier(courier);
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Ответ возвращается с кодом 201 и {'ok':true}")
    public void createCourier() {
        Response response = courierHttp.createCourier(courier);
        assertEquals(201, response.statusCode());
        assertEquals(true, response.getBody().jsonPath().get("ok"));

    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Ответ возвращается с кодом 409")
    public void createCloneCourier() {
        Response response1 = courierHttp.createCourier(courier);
        assertEquals(201, response1.statusCode());
        Response response2 = courierHttp.createCourier(courier);
        assertEquals(409, response2.statusCode());
    }

    @Test
    @DisplayName("Проверка создания курьера, если в запросе не передан атрибут login")
    @Description("Ответ возвращается с кодом 400")
    public void createCourierWithoutLogin() {
        Courier courier = new Courier("", "11111", "22222");
        Response response = courierHttp.createCourier(courier);
        assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("Проверка создания курьера, если в запросе не передан атрибут password")
    @Description("Ответ возвращается с кодом 400")
    public void createCourierWithoutPassword() {
        Courier courier = new Courier("qwert", "", "12345");
        Response response = courierHttp.createCourier(courier);
        assertEquals(400, response.statusCode());
    }


}
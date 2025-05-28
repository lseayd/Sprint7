package сourier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import order.OrderAPI;

public class CourierAPI extends OrderAPI {


    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return doPostRequest(URL.CREATE_COURIER, courier);
    }

    @Step("Авторизация курьера")
    public Response loginCourier(Courier courier) {
        Response response = doPostRequest(URL.LOGIN_COURIER, courier);
        Integer id = response.getBody().jsonPath().get("id");
        if (id != null) {
            courier.setId(id);
        }
        return response;
    }

    @Step("Удаление курьера")
    public Response deleteCourier(Courier courier) {
        return doDeleteRequest(URL.DELETE_COURIER + courier.getId());
    }
}

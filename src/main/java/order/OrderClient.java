package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import user.BaseUser;

public class OrderClient extends BaseUser {
    private final String ORDER = "/orders";

    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createOrderWithAuth(String bearerToken, Order order){
        return getSpec()
                .header("Authorization", bearerToken)
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post(ORDER)
                .then();
    }

    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderWithoutAuth(Order order){
        return getSpec()
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post(ORDER)
                .then();
    }

    @Step("Получение списка заказов с авторизацией")
    public ValidatableResponse getOrderListWithAuth(String bearerToken){
        return getSpec()
                .header("Authorization", bearerToken)
                .when()
                .get(ORDER)
                .then();
    }

    @Step("Получение списка заказов без авторизации")
    public ValidatableResponse getOrderListWithoutAuth(){
        return getSpec()
                .when()
                .get(ORDER)
                .then();
    }
}

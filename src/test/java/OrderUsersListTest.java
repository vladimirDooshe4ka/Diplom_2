import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.UserClient;
import user.User;

import static org.hamcrest.CoreMatchers.equalTo;

public class OrderUsersListTest {
    User user;
    UserClient userClient;
    OrderClient orderClient;
    String bearerToken;

    @Before
    public void setup() {
        user = User.getRandomUser();
        userClient = new UserClient();
        orderClient = new OrderClient();
        bearerToken = userClient.createUser(user)
                .statusCode(200)
                .extract().path("accessToken");
    }

    @After
    public void tearDown() {
        userClient.deleteUser(bearerToken);
    }

    @Test
    @DisplayName("Тест успешного получения списка заказов авторизированным пользователем")
    public void getOrderListWithAuthUserTest(){
        ValidatableResponse getOrder = orderClient.getOrderListWithAuth(bearerToken);
        getOrder
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест неудачного получения списка заказов неавторизированным пользователем")
    public void getOrderListWithoutAuthUserTest(){
        ValidatableResponse getOrder = orderClient.getOrderListWithoutAuth();
        getOrder
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import order.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.CoreMatchers.equalTo;

public class OrderCreateTest {

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
    @DisplayName("Тест успешного оформления заказа авторизированным пользователям с ингредиентами")
    public void createOrderWithAuthWithIngredientsTest(){
        Order order = new Order(Order.getRandomRecipe());
        ValidatableResponse postOrders = orderClient.createOrderWithAuth(bearerToken, order);
        postOrders
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест неудачного оформления заказа авторизированным пользователем без ингредиентов")
    public void createOrderWithAuthWithoutIngredientsTest(){
        Order order = new Order();
        ValidatableResponse postOrders = orderClient.createOrderWithAuth(bearerToken, order);
        postOrders
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Тест неудачноого оформления заказа авторизированным пользователем с неправильными хешами ингредиентов")
    public void createOrderWithAuthWithWrongIngredientsTest(){
        Order order = new Order(Order.getWrongRecipe());
        ValidatableResponse postOrders = orderClient.createOrderWithAuth(bearerToken, order);
        postOrders
                .statusCode(500);
    }

    @Test
    @DisplayName("Тест успешного оформления заказа неавторизированным пользователем с ингредиентами")
    public void createOrderWithoutAuthWithIngredientsTest(){
        Order order = new Order(Order.getRandomRecipe());
        ValidatableResponse postOrders = orderClient.createOrderWithoutAuth(order);
        postOrders
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест неудачного оформления заказа неавторизированным пользователем без ингредиентов")
    public void createOrderWithoutAuthWithoutIngredientsTest(){
        Order order = new Order();
        ValidatableResponse postOrders = orderClient.createOrderWithoutAuth(order);
        postOrders
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Тест неудачного оформления заказа неавторизированным пользователем с неправильными хешами ингредиентов")
    public void createOrderWithoutAuthWithWrongIngredientsTest(){
        Order order = new Order(Order.getWrongRecipe());
        ValidatableResponse postOrders = orderClient.createOrderWithoutAuth(order);
        postOrders
                .statusCode(500);
    }
}

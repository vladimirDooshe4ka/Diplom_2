import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserCreateTest {

    User user;
    UserClient userClient;
    User userSecond;
    String bearerToken = "";

    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    @After
    public void tearDown() {
        if (bearerToken.length() > 0)
            userClient.deleteUser(bearerToken);
    }

    @Test
    @DisplayName("Тест успешного создания пользователя со всеми полями")
    public void createUserWithAllFieldsTest(){
        user = User.getRandomUser();
        ValidatableResponse postUser = userClient.createUser(user);
        bearerToken = postUser.extract().path("accessToken");
        postUser
                .statusCode(200)
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("Тест неудачной попытки создания уже существующего пользователя")
    public void canNotCreateDuplicateUserTest(){
        user = User.getRandomUser();
        ValidatableResponse postUser = userClient.createUser(user);
        bearerToken = postUser.extract().path("accessToken");
        postUser
                .statusCode(200)
                .body("success", equalTo(true));
        userSecond = User.getUserDuplicateEmail(user);
        ValidatableResponse postUserSecond = userClient.createUser(userSecond);
        postUserSecond
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));

    }

    @Test
    @DisplayName("Тест неудачной попытки создания пользователя без имени")
    public void canNotCreateUserWithoutNameTest(){
        user = User.getUserWithoutName();
        ValidatableResponse postUser = userClient.createUser(user);
        postUser
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Тест неудачной попытки создания пользователя без email")
    public void canNotCreateUserWithoutEmailTest(){
        user = User.getUserWithoutEmail();
        ValidatableResponse postUser = userClient.createUser(user);
        postUser
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));

    }

    @Test
    @DisplayName("Тест неудачной попытки создания пользователя без пароля")
    public void canNotCreateUserWithoutPasswordTest(){
        user = User.getUserWithoutPassword();
        ValidatableResponse postUser = userClient.createUser(user);
        postUser
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));

    }


}

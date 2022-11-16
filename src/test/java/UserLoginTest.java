import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserCredentials;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserLoginTest {
    User user;
    UserClient userClient;
    String bearerToken;
    UserCredentials userCredentials;

    @Before
    public void setup(){
        user = User.getRandomUser();
        userClient = new UserClient();
        bearerToken = userClient.createUser(user)
                .statusCode(200)
                .extract().path("accessToken");
    }

    @After
    public void tearDown() {
        userClient.deleteUser(bearerToken);
    }

    @Test
    @DisplayName("Тест успешной авторизации пользователя")
    public void loginUserWithValidDataTest(){
        userCredentials = UserCredentials.getCredentials(user);
        ValidatableResponse postUser = userClient.loginUser(userCredentials);
        postUser
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест неудачной авторизации c неверным email")
    public void loginUserWithWrongEmailTest(){
        userCredentials = UserCredentials.getCredentialsWithWrongEmail(user);
        ValidatableResponse postUser = userClient.loginUser(userCredentials);
        postUser
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));

    }

    @Test
    @DisplayName("Тест неудачной авторизации с неверным паролем")
    public void loginUserWithWrongPasswordTest(){
        userCredentials = UserCredentials.getCredentialsWithWrongPassword(user);
        ValidatableResponse postUser = userClient.loginUser(userCredentials);
        postUser
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));

    }
}

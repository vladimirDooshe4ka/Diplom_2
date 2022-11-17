import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import user.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserChangeTest {
    String changePart = "q";
    User user;
    User userChanged;
    UserClient userClient;
    String bearerToken;

    @Before
    public void setUp(){
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
    @DisplayName("Тест успешного изменения имени у авторизированного пользователя")
    public void userChangeNameWithAuthTest(){
        userChanged = new User(user.getEmail(), user.getPassword(), user.getName()+changePart);
        ValidatableResponse patchUser = userClient.changeUserWithAuth(bearerToken, userChanged);
        patchUser
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест успешного изменения email у авторизированного пользователя")
    public void userChangeEmailWithAuthTest(){
        userChanged = new User(user.getEmail() + changePart, user.getPassword(), user.getName());
        ValidatableResponse patchUser = userClient.changeUserWithAuth(bearerToken, userChanged);
        patchUser
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест успешного изменения пароля у авторизированного пользователя")
    public void userChangePasswordWithAuthTest(){
        userChanged = new User(user.getEmail(), user.getPassword()+changePart, user.getName());
        ValidatableResponse patchUser = userClient.changeUserWithAuth(bearerToken, userChanged);
        patchUser
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест нудачного изменения имени у неавторизированного пользователя")
    public void userCanNotChangeNameWithoutAuth(){
        userChanged = new User(user.getEmail(), user.getPassword(), user.getName()+changePart);
        ValidatableResponse patchUser = userClient.changeUserWithoutAuth(userChanged);
        patchUser
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Тест нудачного изменения email у неавторизированного пользователя")
    public void userCanNotChangeEmailWithoutAuth(){
        userChanged = new User(user.getEmail() + changePart, user.getPassword(), user.getName());
        ValidatableResponse patchUser = userClient.changeUserWithoutAuth(userChanged);
        patchUser
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Тест нудачного изменения пароля у неавторизированного пользователя")
    public void userCanNotChangePasswordWithoutAuth(){
        userChanged = new User(user.getEmail(), user.getPassword()+changePart, user.getName());
        ValidatableResponse patchUser = userClient.changeUserWithoutAuth(userChanged);
        patchUser
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}

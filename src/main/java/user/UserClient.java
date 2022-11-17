package user;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserClient extends BaseUser {
    private final String USER_CREATE = "/auth/register";
    private final String USER_LOGIN = "/auth/login";
    private final String USER_CHANGE = "/auth/user";

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return getSpec()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post(USER_CREATE)
                .then()
                .assertThat();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(UserCredentials userCredentials) {
        return getSpec()
                .header("Content-Type", "application/json")
                .body(userCredentials)
                .when()
                .post(USER_LOGIN)
                .then()
                .assertThat();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String bearerToken){
        return getSpec()
                .header("Authorization", bearerToken)
                .when()
                .delete(USER_CHANGE)
                .then()
                .statusCode(202);
    }

    @Step("Изменение пользователя с авторизацией")
    public ValidatableResponse changeUserWithAuth(String bearerToken, User changeUser){
        return getSpec()
                .header("Authorization", bearerToken)
                .header("Content-Type", "application/json")
                .body(changeUser)
                .when()
                .patch(USER_CHANGE)
                .then();
    }

    @Step("Изменение пользователя без авторизации")
    public ValidatableResponse changeUserWithoutAuth(User changeUser){
        return getSpec()
                .header("Content-Type", "application/json")
                .body(changeUser)
                .when()
                .patch(USER_CHANGE)
                .then()
                .assertThat();
    }

}
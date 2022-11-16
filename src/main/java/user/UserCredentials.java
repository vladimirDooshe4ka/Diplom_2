package user;

import io.qameta.allure.Step;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class UserCredentials {

    private String email;
    private String password;

    public UserCredentials(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Step("Формирование данных для авторизации пользователя")
    public static UserCredentials getCredentials(User user){
        return new UserCredentials(
                user.getEmail(),
                user.getPassword()
        );
    }

    @Step("Формирование данных для авторизации пользователя с незарегистированным email")
    public static UserCredentials getCredentialsWithWrongEmail(User user){
        return new UserCredentials(
                RandomStringUtils.randomAlphabetic(9).toLowerCase() + "@loga.ru",
                user.getPassword()
        );
    }

    @Step("Формирование данных для авторизации пользователя с неправильным паролем")
    public static UserCredentials getCredentialsWithWrongPassword(User user){
        return new UserCredentials(
                user.getEmail(),
                RandomStringUtils.randomAlphanumeric(12)
        );
    }
}

package user;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import lombok.Data;

@Data
public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Step("Формирование данных для создания пользователя со всеми полями")
    public static User getRandomUser(){
        return new User(
                RandomStringUtils.randomAlphabetic(9).toLowerCase() + "@liga.ru",
                RandomStringUtils.randomAlphanumeric(9),
                RandomStringUtils.randomAlphabetic(9)
        );
    }

    @Step("Формирование данных для создания пользователя без имени")
    public static User getUserWithoutName(){
        return new User(
                RandomStringUtils.randomAlphabetic(9).toLowerCase() + "@liga.ru",
                RandomStringUtils.randomAlphanumeric(9),
                ""
        );
    }

    @Step("Формирование данных для создания пользователя без email")
    public static User getUserWithoutEmail(){
        return new User(
                "",
                RandomStringUtils.randomAlphanumeric(9),
                RandomStringUtils.randomAlphabetic(9)
        );
    }

    @Step("Формирование данных для создания пользователя без пароля")
    public static User getUserWithoutPassword(){
        return new User(
                RandomStringUtils.randomAlphabetic(9).toLowerCase() + "@liga.ru",
                "",
                RandomStringUtils.randomAlphabetic(9)
        );
    }

    @Step("Формирование данных для создания пользователя с дублирующимся email")
    public static User getUserDuplicateEmail(User user){
        return new User(
                user.getEmail(),
                RandomStringUtils.randomAlphanumeric(9),
                RandomStringUtils.randomAlphabetic(9)
        );
    }


}

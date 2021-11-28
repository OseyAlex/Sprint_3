package praktikumservices.qascooter;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    public String login;
    public String password;
    public String firstName;

    //конструктор для курьера с набором всех полей
    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    //конструктор для курьера с набором обязательных полей
    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    //конструктор для курьера с набором 1 поля для негавтинвого кейса
    public Courier(String login) {
        this.login = login;
    }

    public static Courier getRandomData() {
        // с помощью библиотеки RandomStringUtils генерируем логин
        String login = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем пароль
        String password = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем имя курьера
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    public static Courier getRandomRequiredData() {
        // с помощью библиотеки RandomStringUtils генерируем логин
        String login = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем пароль
        String password = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password);
    }

    public static Courier getRandomLogin() {
        // с помощью библиотеки RandomStringUtils генерируем логин
        String login = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login);
    }
}

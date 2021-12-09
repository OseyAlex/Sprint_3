package praktikumservices.qascooter.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import praktikumservices.qascooter.entity_data_generator.CourierDataGenerator;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class Courier {
    private String login;
    private String password;
    private String firstName;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Courier setLogin(String login) {
        this.login = login;
        return this;
    }

    public Courier setLoginAndPassword(String login, String password) {
        this.login = login;
        this.password = password;
        return this;
    }

    public Courier setLoginPasswordAndFirstName(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        return this;
    }

    public static Courier getWithLoginOnly() {
        return new Courier().setLogin(CourierDataGenerator.getLogin());
    }

    public static Courier getWithLoginAndPassword() {
        return new Courier().setLoginAndPassword(CourierDataGenerator.getLogin(), CourierDataGenerator.getPassword());
    }

    public static Courier getWithLoginPasswordAndFirstName() {
        return new Courier().setLoginPasswordAndFirstName(CourierDataGenerator.getLogin(), CourierDataGenerator.getPassword(), CourierDataGenerator.getFirstName());
    }
}

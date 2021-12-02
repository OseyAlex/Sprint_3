package praktikumservices.qascooter.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class CourierCredentials {

    private String login;
    private String password;

    public CourierCredentials setCourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
        return this;
    }

    public CourierCredentials setCourierLoginCredential(String login) {
        this.login = login;
        return this;
    }
}

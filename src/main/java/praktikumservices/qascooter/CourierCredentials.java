package praktikumservices.qascooter;

public class CourierCredentials {

    public String login;
    public String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierCredentials(String login) {
        this.login = login;
    }
}

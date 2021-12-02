package praktikumservices.qascooter.entity_data_generator;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierDataGenerator {
    private String login;
    private String password;
    private String firstName;

    public static String getLogin() {
        String login = RandomStringUtils.randomAlphabetic(10);
        return login;
    }

    public static String getPassword() {
        String password = RandomStringUtils.randomAlphabetic(10);
        return password;
    }

    public static String getFirstName() {
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return firstName;
    }
}

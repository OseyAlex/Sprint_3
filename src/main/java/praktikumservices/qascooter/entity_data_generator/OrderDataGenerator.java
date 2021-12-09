package praktikumservices.qascooter.entity_data_generator;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderDataGenerator {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;

    public static String getFirstName() {
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return firstName;
    }

    public static String getLastName() {
        String lastName = RandomStringUtils.randomAlphabetic(10);
        return lastName;
    }

    public static String getAddress() {
        String address = RandomStringUtils.randomAlphabetic(10);
        return address;
    }

    public static int getMetroStation() {
        int metroStation = (int) (Math.random() * 3);
        return metroStation;
    }

    public static String getPhone() {
        String phone = "7 " + RandomStringUtils.randomNumeric(10);
        return phone;
    }

    public static int getRentTime() {
        int rentTime = (int) (Math.random() * 3);
        return rentTime;
    }

    //задаём текущую дату
    public static String getDeliveryDate() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String deliveryDate = dateFormat.format(date);
        return deliveryDate;
    }

    public static String getComment() {
        String comment = RandomStringUtils.randomAlphabetic(10);
        return comment;
    }
}

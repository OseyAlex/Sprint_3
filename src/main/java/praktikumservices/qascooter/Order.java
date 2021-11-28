package praktikumservices.qascooter;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Order {
    public final String firstName;
    public final String lastName;
    public final String address;
    public final int metroStation;
    public final String phone;
    public final int rentTime;
    public final String deliveryDate;
    public final String comment;
    public String[] color;

    public Order(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order getOrderdata(String colorVariable) {
        String firstName = RandomStringUtils.randomAlphabetic(10);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphabetic(10);
        int metroStation = (int) (Math.random() * 3);
        String phone = "7 " + RandomStringUtils.randomNumeric(10);
        int rentTime = (int) (Math.random() * 3);
        //задаём текущую дату
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String deliveryDate = dateFormat.format(date);
        String comment = RandomStringUtils.randomAlphabetic(10);
        String[] color = new String[]{colorVariable};
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment,
                color);
    }
}

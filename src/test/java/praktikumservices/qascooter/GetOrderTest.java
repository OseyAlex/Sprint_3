package praktikumservices.qascooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetOrderTest {

    private CourierMethods courierMethods;
    private OrderMethods orderMethods;
    private int courierId;
    private int orderId;
    String color = "GREY";

    @Before
    public void setUp() {
        RestAssured.baseURI = EndPoints.baseURI;
        courierMethods = new CourierMethods();
        orderMethods = new OrderMethods();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @After
    public void tearDown() {
        if (courierId != 0)
            courierMethods.deleteCourierById(courierId);
    }

    @Test
    @DisplayName("Создаём 1 курьера, получаем ID, создаём заказ и вызываем получение заказа")
    public void createOneCourierTest() {
        int expectedOrderCount = 1;

        Courier courier = Courier.getRandomData();

        //создаём курьера
        courierMethods.create(courier);
        //получаем ID
        ValidatableResponse responseLogin = courierMethods.loginCourier(new CourierCredentials(courier.login, courier.password));
        courierId = responseLogin.extract().path("id");
        //создаём заказ
        Order firstOrder = Order.getOrderdata(color);

        int firstOrderTrack = orderMethods.sendPostRequestNewOrder(firstOrder);

        //узнаём id заказа
        orderId = orderMethods.getOrderByTrackId(firstOrderTrack);
        System.out.println(" ID заказа = " + orderId);
        //принимаем заказ для текущего курьера, передавая id Заказа!!!!, не трек номер!!!!
        orderMethods.acceptOrder(courierId, orderId);

        //получаем список заказов для тек. курьера
        int actualOrderCount = orderMethods.getOrder(courierId);
        assertEquals("кол-во заказов не совпадает", expectedOrderCount, actualOrderCount);
    }
}

package praktikumservices.qascooter.orders_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikumservices.qascooter.EndPoints;
import praktikumservices.qascooter.entities.Courier;
import praktikumservices.qascooter.entities.CourierCredentials;
import praktikumservices.qascooter.entities.Order;
import praktikumservices.qascooter.helpers.CourierHelper;
import praktikumservices.qascooter.helpers.OrderHelper;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;

public class GetOrderTest {

    private CourierHelper courierHelper;
    private OrderHelper orderHelper;
    private int courierId;
    private int orderId;
    private int orderTrack;
    String color = "GREY";

    @Before
    public void setUp() {
        RestAssured.baseURI = EndPoints.baseURI;
        courierHelper = new CourierHelper();
        orderHelper = new OrderHelper();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @After
    @Step("Удалить курьера")
    public void tearDown() {
        if (courierId != 0)
            courierHelper.deleteCourierById(courierId);
    }

    @Test
    @DisplayName("Получить список заказов")
    @Description("Получаем список заказов: " +
            " 1. Создаём курьера" +
            " 2. Логиним и получаем ID курьера" +
            " 3. Создаём заказ и получаем track заказа" +
            " 4. Получаем ID заказа для его принятия" +
            " 5. Принимаем заказ" +
            " 6. Получаем список заказов и проверяем что он не пуст")
    public void getOrderTest() {
        Courier courier = Courier.getWithLoginPasswordAndFirstName();

        //создаём курьера
        courierHelper.create(courier);

        //получаем ID
        ValidatableResponse responseLogin = courierHelper.loginCourier(new CourierCredentials().setCourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = responseLogin.extract().path("id");
        //создаём заказ
        Order firstOrder = Order.getOrder(color);
        ValidatableResponse responseOrderCreate = orderHelper.sendPostRequestNewOrder(firstOrder);
        assertThat(responseOrderCreate.extract().statusCode(), equalTo(201));
        orderTrack = responseOrderCreate.extract().path("track");

        //узнаём id заказа
        ValidatableResponse responseOrderByTrackId = orderHelper.getOrderByTrackId(orderTrack);
        assertThat(responseOrderByTrackId.extract().statusCode(), equalTo(200));
        orderId = responseOrderByTrackId.extract().body().path("order.id");

        //принимаем заказ для текущего курьера, передавая id Заказа!!!!, не трек номер!!!!
        ValidatableResponse responseAcceptOrder = orderHelper.acceptOrder(courierId, orderId);
        assertThat(responseAcceptOrder.extract().statusCode(), equalTo(200));

        //получаем список заказов для тек. курьера
        ValidatableResponse responseGetOrder = orderHelper.getOrder(courierId);
        assertThat(responseGetOrder.extract().statusCode(), equalTo(200));
        List<Object> orders = responseGetOrder.extract().jsonPath().getList("orders");
        assertFalse(orders.isEmpty());
    }
}

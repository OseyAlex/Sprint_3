package praktikumservices.qascooter.orders_tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikumservices.qascooter.EndPoints;
import praktikumservices.qascooter.entities.Order;
import praktikumservices.qascooter.entities.TrackOrder;
import praktikumservices.qascooter.helpers.OrderHelper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class CreateOrderColorSetTests {
    public final String colorVariable;

    public CreateOrderColorSetTests(String colorVariable) {
        this.colorVariable = colorVariable;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = EndPoints.baseURI;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Parameterized.Parameters
    public static Object[] getColor() {
        return new Object[][]{
                {"GREY\" , \"BLACK"},
                {"GREY"},
                {"BLACK"},
                {""}
        };
    }

    @Test
    @DisplayName("Создание заказов с различными цветами")
    public void createOrdersWithColorSet() {
        OrderHelper orderHelper =
                new OrderHelper();
        Order order = Order.getOrder(colorVariable);
        ValidatableResponse responseOrderCreate = orderHelper.
                sendPostRequestNewOrder(order);
        assertThat(responseOrderCreate.extract().statusCode(), equalTo(201));
        int orderTrack = responseOrderCreate.extract().path("track");
        assertThat("номер заказа не присвоился", orderTrack, notNullValue());
        //отменяем заказ
        ValidatableResponse responseCancelOrder = orderHelper.
                cancelOrder(new TrackOrder().setTrackOrder(orderTrack));
        assertThat("Заказ не отменяется", responseCancelOrder.extract().statusCode(), equalTo(200));
    }
}

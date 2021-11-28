package praktikumservices.qascooter;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

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
    public void createOrder() {
        OrderMethods orderMethods = new OrderMethods();
        Order order = Order.getOrderdata(colorVariable);
        int orderTrack = orderMethods.sendPostRequestNewOrder(order);
        assertThat("номер заказа присвоился", orderTrack, notNullValue());
        //отменяем заказ
        orderMethods.cancelOrder(new TrackOrder(orderTrack));
    }
}

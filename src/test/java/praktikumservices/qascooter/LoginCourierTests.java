package praktikumservices.qascooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class LoginCourierTests {
    private CourierMethods courierMethods;
    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = EndPoints.baseURI;
        courierMethods = new CourierMethods();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @After
    public void tearDown() {
        if (courierId != 0)
            courierMethods.deleteCourierById(courierId);
    }

    @Test
    @DisplayName("Логин курьера")
    public void loginOneCourier() {
        Courier courier = Courier.getRandomData();
        //создаем курьера
        courierMethods.create(courier);
        //логиним
        ValidatableResponse responseLogin = courierMethods.loginCourier(new CourierCredentials(courier.login, courier.password));
        int statusCode = responseLogin.extract().statusCode();
        courierId = responseLogin.extract().path("id");

        assertThat(statusCode, equalTo(200));
        assertThat(courierId, notNullValue());
    }

    @Test
    @DisplayName("Логин курьера с неверным паролем")
    public void loginCourierWithWrongPasswordTest() {
        Courier courier = Courier.getRandomData();
        String wrongPassword = "WrOnG_pAsSwOrD";

        courierMethods.create(courier);
        ValidatableResponse responseLoginFalse = courierMethods.loginCourier(new CourierCredentials(courier.login, wrongPassword));
        int statusCode = responseLoginFalse.extract().statusCode();
        String actualErrorMessage = responseLoginFalse.extract().path("message");
        String expectedErrorMessage = "Учетная запись не найдена";

        assertThat(statusCode, equalTo(404));
        assertEquals("Сообщение отличается", expectedErrorMessage, actualErrorMessage);

        //узнаем Id курьера, чтобы удалить
        ValidatableResponse responseLogin = courierMethods.loginCourier(new CourierCredentials(courier.login, courier.password));
        courierId = responseLogin.extract().path("id");
    }

    @Test
    @DisplayName("Логин курьера с несуществующим логином")
    public void loginCourierWithWrongLoginTest() {
        Courier courier = Courier.getRandomRequiredData();

        ValidatableResponse responseLoginError = courierMethods.loginCourier(new CourierCredentials(courier.login, courier.password));
        int statusCode = responseLoginError.extract().statusCode();
        String actualErrorMessage = responseLoginError.extract().path("message");
        String expectedErrorMessage = "Учетная запись не найдена";

        assertThat(statusCode, equalTo(404));
        assertEquals("Сообщение отличается", expectedErrorMessage, actualErrorMessage);
    }

    @Test
    @DisplayName("Логин курьера только с логином")
    public void loginCourierWithOnlyLoginTest() {
        Courier courier = Courier.getRandomRequiredData();

        courierMethods.create(courier);
        ValidatableResponse responseLoginFail = courierMethods.loginCourier(new CourierCredentials(courier.login));
        int statusCode = responseLoginFail.extract().statusCode();

        assertThat(statusCode, equalTo(400));

        String actualErrorMessage = responseLoginFail.extract().path("message");
        String expectedErrorMessage = "Недостаточно данных для входа";

        assertEquals("Сообщение отличается", expectedErrorMessage, actualErrorMessage);

        ValidatableResponse responseLogin = courierMethods.loginCourier(new CourierCredentials(courier.login, courier.password));
        courierId = responseLogin.extract().path("id");
    }
}

package praktikumservices.qascooter.courier_tests;

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
import praktikumservices.qascooter.helpers.CourierHelper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class LoginCourierTests {
    private CourierHelper courierHelper;
    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = EndPoints.baseURI;
        courierHelper = new CourierHelper();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @After
    @Step("Удалить курьера")
    public void tearDown() {
        if (courierId != 0)
            courierHelper.deleteCourierById(courierId);
    }

    @Test
    @DisplayName("Логин курьера")
    public void loginOneCourier() {
        Courier courier = Courier.getWithLoginPasswordAndFirstName();
        //создаем курьера
        courierHelper.create(courier);
        //логиним
        ValidatableResponse responseLogin = courierHelper.loginCourier(new CourierCredentials().setCourierCredentials(courier.getLogin(), courier.getPassword()));
        int statusCode = responseLogin.extract().statusCode();
        courierId = responseLogin.extract().path("id");

        assertThat(statusCode, equalTo(200));
        assertThat(courierId, notNullValue());
    }

    @Test
    @DisplayName("Логин курьера с неверным паролем")
    public void loginCourierWithWrongPasswordTest() {
        Courier courier = Courier.getWithLoginPasswordAndFirstName();
        String wrongPassword = "WrOnG_pAsSwOrD";

        courierHelper.create(courier);
        ValidatableResponse responseLoginFalse = courierHelper.loginCourier(new CourierCredentials().setCourierCredentials(courier.getLogin(), wrongPassword));
        int statusCode = responseLoginFalse.extract().statusCode();
        String actualErrorMessage = responseLoginFalse.extract().path("message");
        String expectedErrorMessage = "Учетная запись не найдена";

        assertThat(statusCode, equalTo(404));
        assertEquals("Сообщение отличается", expectedErrorMessage, actualErrorMessage);

        //узнаем Id курьера, чтобы удалить
        ValidatableResponse responseLogin = courierHelper.loginCourier(new CourierCredentials().setCourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = responseLogin.extract().path("id");
    }

    @Test
    @DisplayName("Логин курьера с несуществующим логином")
    public void loginCourierWithWrongLoginTest() {
        Courier courier = Courier.getWithLoginPasswordAndFirstName();

        ValidatableResponse responseLoginError = courierHelper.loginCourier(new CourierCredentials().setCourierCredentials(courier.getLogin(), courier.getPassword()));
        int statusCode = responseLoginError.extract().statusCode();
        String actualErrorMessage = responseLoginError.extract().path("message");
        String expectedErrorMessage = "Учетная запись не найдена";

        assertThat(statusCode, equalTo(404));
        assertEquals("Сообщение отличается", expectedErrorMessage, actualErrorMessage);
    }

    @Test
    @DisplayName("Логин курьера только с логином")
    public void loginCourierWithOnlyLoginTest() {
        Courier courier = Courier.getWithLoginPasswordAndFirstName();

        courierHelper.create(courier);
        ValidatableResponse responseLoginFail = courierHelper.loginCourier(new CourierCredentials().setCourierLoginCredential(courier.getLogin()));
        int statusCode = responseLoginFail.extract().statusCode();

        assertThat(statusCode, equalTo(400));

        String actualErrorMessage = responseLoginFail.extract().path("message");
        String expectedErrorMessage = "Недостаточно данных для входа";

        assertEquals("Сообщение отличается", expectedErrorMessage, actualErrorMessage);

        ValidatableResponse responseLogin = courierHelper.loginCourier(new CourierCredentials().setCourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = responseLogin.extract().path("id");
    }
}

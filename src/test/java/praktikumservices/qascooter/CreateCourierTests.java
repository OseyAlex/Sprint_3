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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTests {
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
    @DisplayName("Создаём 1 курьера")
    public void createOneCourier() {
        Courier courier = Courier.getRandomData();

        ValidatableResponse responseCreate = courierMethods.create(courier);

        int statusCode = responseCreate.extract().statusCode();
        boolean isCourierCreated = responseCreate.extract().path("ok");

        assertThat(statusCode, equalTo(201));
        assertTrue(isCourierCreated);

        //узнаём Id, чтобы удалить
        ValidatableResponse responseLogin = courierMethods.loginCourier(new CourierCredentials(courier.login, courier.password));
        courierId = responseLogin.extract().path("id");
    }

    @Test
    @DisplayName("Создаём 1 курьера c минимальным кол-вом обяз. полей")
    public void createOneCourierWithRequiredFieldsTest() {
        Courier courierRequiredData = Courier.getRandomRequiredData();

        ValidatableResponse response = courierMethods.create(courierRequiredData);

        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");

        assertThat(statusCode, equalTo(201));
        assertTrue(isCourierCreated);

        ValidatableResponse responseLogin = courierMethods.loginCourier(new CourierCredentials(courierRequiredData.login, courierRequiredData.password));
        courierId = responseLogin.extract().path("id");
    }

    @Test
    @DisplayName("Создаём 1 курьера c логином, проверяем что будет ошибка")
    public void createOneCourierWithNotEnoughFieldsTest() {
        Courier courierOnlyLoginData = Courier.getRandomLogin();

        ValidatableResponse response = courierMethods.create(courierOnlyLoginData);
        int statusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");

        String expectedErrorMessage = "Недостаточно данных для создания учетной записи";
        assertThat(statusCode, equalTo(400));
        assertEquals("Сообщение отличается", expectedErrorMessage, actualMessage);
    }

    @Test
    @DisplayName("Создаём 2 курьера c одинаковыми данными")
    public void createTwoCourierTest() {
        Courier courierNegative = Courier.getRandomData();

        courierMethods.create(courierNegative);
        ValidatableResponse responseNegative = courierMethods.create(courierNegative);

        int statusCode = responseNegative.extract().statusCode();
        String actualErrorMessage = responseNegative.extract().path("message");
        String expectedErrorMessage = "Этот логин уже используется. Попробуйте другой.";

        assertThat(statusCode, equalTo(409));
        assertEquals("Сообщение отличается", expectedErrorMessage, actualErrorMessage);

        ValidatableResponse responseLogin = courierMethods.loginCourier(new CourierCredentials(courierNegative.login, courierNegative.password));
        courierId = responseLogin.extract().path("id");
    }
}
